package com.softmed.htmr_facility.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.activities.ClientsDetailsActivity;
import com.softmed.htmr_facility.activities.OpdReferralDetailsActivity;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.ReferralServiceIndicators;
import com.softmed.htmr_facility.utils.ReferralsDiffCallback;

import static com.softmed.htmr_facility.utils.constants.HIV_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.LAB_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.MALARIA_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.OPD_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.SWAHILI_LOCALE;
import static com.softmed.htmr_facility.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 11/17/17.
 */

public class ReferalListRecyclerAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private List<Referral> items;
    private Context context;
    private AppDatabase database;
    private int serviceID;

    public ReferalListRecyclerAdapter(List<Referral> mItems, Context context, int service){
        this.items = mItems;
        Log.d("Coze","referral list = "+new Gson().toJson(mItems));
        this.database = AppDatabase.getDatabase(context);
        this.serviceID = service;
    }

    public ReferalListRecyclerAdapter(){}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        context         = viewGroup.getContext();
        View itemView   = null;

        if (serviceID == OPD_SERVICE_ID){
            itemView = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.referral_list_opd_client_item, viewGroup, false);
            return new ReferalListRecyclerAdapter.ListViewOPDItemViewHolder(itemView);

        }else {
            itemView = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.referal_list_client_item, viewGroup, false);

            return new ReferalListRecyclerAdapter.ListViewItemViewHolder(itemView);
        }

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int itemPosition){


        final Referral referral = getItem(itemPosition);

        if (viewHolder instanceof ReferalListRecyclerAdapter.ListViewItemViewHolder){
            ReferalListRecyclerAdapter.ListViewItemViewHolder holder = (ReferalListRecyclerAdapter.ListViewItemViewHolder) viewHolder;
            new patientDetailsTask(database, referral, holder.clientsNames, holder.ctcNumber).execute(serviceID);
            if (referral.getReferralStatus() == 0){
                holder.attendedFlag.setText(context.getResources().getString(R.string.new_ref));
                holder.attendedFlag.setTextColor(context.getResources().getColor(R.color.red_400));
            }else {
                holder.attendedFlag.setText(context.getResources().getString(R.string.attended_ref));
                holder.attendedFlag.setTextColor(context.getResources().getColor(R.color.green_400));
            }

            holder.ctcNumber.setText(referral.getCtcNumber());
            if (serviceID == LAB_SERVICE_ID){
                holder.ctcNumber.setText("");
                int labTest = referral.getLabTest();
                String labTestName;
                switch (labTest){
                    case HIV_SERVICE_ID:
                        labTestName = context.getResources().getString(R.string.hiv);
                        break;
                    case TB_SERVICE_ID:
                        labTestName = context.getResources().getString(R.string.tb);
                        break;
                    case MALARIA_SERVICE_ID:
                        labTestName = "Malaria";
                        break;
                    default:
                        labTestName = context.getResources().getString(R.string.unspecified_test_type);
                        break;
                }
                holder.referralReasons.setText(labTestName);
            }else {
                holder.ctcNumber.setText(referral.getCtcNumber());
                holder.referralReasons.setText(referral.getReferralReason());
            }

            holder.referralDate.setText(BaseActivity.simpleDateFormat.format(referral.getAppointmentDate()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("worships", "service is not opd "+serviceID);
                    Intent intent = new Intent(context, ClientsDetailsActivity.class);
                    intent.putExtra("referal", referral);
                    intent.putExtra("service", serviceID);
                    context.startActivity(intent);
                }
            });

        }else if (viewHolder instanceof ReferalListRecyclerAdapter.ListViewOPDItemViewHolder){
            ReferalListRecyclerAdapter.ListViewOPDItemViewHolder holder = (ReferalListRecyclerAdapter.ListViewOPDItemViewHolder) viewHolder;


            new patientDetailsTask(database, referral, holder.clientsNames, holder.serviceName).execute(serviceID);

            if (referral.isEmergency()){
                holder.container.setBackgroundColor(context.getResources().getColor(R.color.red_100));
            }else {
                holder.container.setBackgroundColor(context.getResources().getColor(R.color.white));
            }

            if (referral.getReferralStatus() == 0){
                holder.attendedFlag.setText(context.getResources().getString(R.string.new_ref));
                holder.attendedFlag.setTextColor(context.getResources().getColor(R.color.red_400));
            }else {
                holder.attendedFlag.setText(context.getResources().getString(R.string.attended_ref));
                holder.attendedFlag.setTextColor(context.getResources().getColor(R.color.green_400));
            }

            holder.referralReasons.setText(referral.getReferralReason());

            holder.referralDate.setText(BaseActivity.simpleDateFormat.format(referral.getReferralDate()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("worships", "service is OPD");
                    Intent intent = new Intent(context, OpdReferralDetailsActivity.class);
                    intent.putExtra("referal", referral);
                    intent.putExtra("service", serviceID);
                    context.startActivity(intent);

                }
            });

        }

    }

    public void addItems (List<Referral> referralList){
        this.items = referralList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return items.size();
//        return 10;
    }

    private Referral getItem(int position){
        return items.get(position);
    }

    private class ListViewItemViewHolder extends RecyclerView.ViewHolder {

        TextView clientsNames, attendedFlag, ctcNumber, referralReasons, referralDate;
        View viewItem;

        public ListViewItemViewHolder(View itemView){
            super(itemView);
            this.viewItem   = itemView;

            clientsNames =  itemView.findViewById(R.id.client_name);
            attendedFlag =  itemView.findViewById(R.id.attended_flag);
            ctcNumber =  itemView.findViewById(R.id.ctc_number);
            referralReasons =  itemView.findViewById(R.id.referral_reasons);
            referralDate =  itemView.findViewById(R.id.ref_date);

        }

    }

    private class ListViewOPDItemViewHolder extends RecyclerView.ViewHolder {

        TextView clientsNames, attendedFlag, serviceName, referralReasons, referralDate,sn;
        View viewItem;
        LinearLayout container;

        public ListViewOPDItemViewHolder(View itemView){
            super(itemView);
            this.viewItem   = itemView;

            clientsNames =  itemView.findViewById(R.id.client_name);
            attendedFlag =  itemView.findViewById(R.id.attended_flag);
            serviceName =  itemView.findViewById(R.id.service_name);
            referralReasons =  itemView.findViewById(R.id.referral_reasons);
            referralDate =  itemView.findViewById(R.id.ref_date);
            container = itemView.findViewById(R.id.referral_item_container);
            sn = itemView.findViewById(R.id.sn);

        }

    }

    private static class patientDetailsTask extends AsyncTask<Integer, Void, Void>{

        String patientNames, patientId, serviceNameString, serviceNameStringSw;
        int serviceId;
        Referral ref;
        AppDatabase db;
        TextView mText, serviceNameText;

        patientDetailsTask(AppDatabase database, Referral referral, TextView namesInstance, TextView serviceName){
            this.db = database;
            this.ref = referral;
            this.patientId = ref.getPatient_id();
            serviceNameText = serviceName;
            mText = namesInstance;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            Log.d("reckless", "doing name search backgroundically!");
            serviceId = integers[0];
            try {
                ReferralServiceIndicators indicator = db.referralServiceIndicatorsDao().getServiceById(ref.getServiceId());
                serviceNameStringSw = indicator.getServiceNameSw();
                serviceNameString = indicator.getServiceName();

                patientNames = db.patientModel().getPatientName(patientId);
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("reckless", "Done background !"+patientNames);
            mText.setText(patientNames);
            if (serviceId == OPD_SERVICE_ID){
                Log.d("reckless", "Service is opd, setting service name "+serviceNameString);
                if (BaseActivity.getLocaleString().equals(SWAHILI_LOCALE)){
                    serviceNameText.setText(serviceNameStringSw);
                }else {
                    serviceNameText.setText(serviceNameString);
                }
            }
            //adapter.notifyDataSetChanged();
        }

    }

    public void setReferrals(List<Referral> referralsNewList){

        Log.d("Coze","referral list = "+new Gson().toJson(referralsNewList));
        ReferralsDiffCallback referralsDiffCallback = new ReferralsDiffCallback(this.items, referralsNewList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(referralsDiffCallback);
        this.items = Collections.emptyList();
        this.items = referralsNewList;
        result.dispatchUpdatesTo(this);
    }

}
