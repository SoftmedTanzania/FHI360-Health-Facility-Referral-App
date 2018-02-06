package com.softmed.htmr_facility.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.activities.ClientsDetailsActivity;
import com.softmed.htmr_facility.activities.OpdReferralDetailsActivity;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Referral;

import static com.softmed.htmr_facility.utils.constants.OPD_SERVICE_ID;

/**
 * Created by issy on 11/17/17.
 */

public class ReferalListRecyclerAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private List<Referral> items;
    private Context context;
    private AppDatabase database;
    private ListViewItemViewHolder mViewHolder;
    private int serviceID;

    public ReferalListRecyclerAdapter(List<Referral> mItems, Context context, int service){
        this.items = mItems;
        this.database = AppDatabase.getDatabase(context);
        this.serviceID = service;
    }

    public ReferalListRecyclerAdapter(){}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        context         = viewGroup.getContext();
        View itemView   = null;
        itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.referal_list_client_item, viewGroup, false);

        return new ReferalListRecyclerAdapter.ListViewItemViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int itemPosition){

        final Referral referral = getItem(itemPosition);

        ReferalListRecyclerAdapter.ListViewItemViewHolder holder = (ReferalListRecyclerAdapter.ListViewItemViewHolder) viewHolder;
        mViewHolder = holder;

        new patientDetailsTask(database, referral.getPatient_id(), holder.clientsNames).execute();

        if (referral.getReferralStatus() == 0){
            holder.attendedFlag.setText("New");
            holder.attendedFlag.setTextColor(context.getResources().getColor(R.color.red_a700));
        }else {
            holder.attendedFlag.setText("Attended");
            holder.attendedFlag.setTextColor(context.getResources().getColor(R.color.green_a700));
        }

        holder.ctcNumber.setText(referral.getCtcNumber());
        holder.referralReasons.setText(referral.getReferralReason());

        holder.referralDate.setText(BaseActivity.simpleDateFormat.format(referral.getReferralDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (serviceID == OPD_SERVICE_ID){
                    Log.d("worships", "service is OPD");
                    Intent intent = new Intent(context, OpdReferralDetailsActivity.class);
                    intent.putExtra("referal", referral);
                    intent.putExtra("service", serviceID);
                    context.startActivity(intent);
                }else {
                    Log.d("worships", "service is not opd "+serviceID);
                    Intent intent = new Intent(context, ClientsDetailsActivity.class);
                    intent.putExtra("referal", referral);
                    intent.putExtra("service", serviceID);
                    context.startActivity(intent);
                }
            }
        });

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

            clientsNames = (TextView) itemView.findViewById(R.id.client_name);
            attendedFlag = (TextView) itemView.findViewById(R.id.attended_flag);
            ctcNumber = (TextView) itemView.findViewById(R.id.ctc_number);
            referralReasons = (TextView) itemView.findViewById(R.id.referral_reasons);
            referralDate = (TextView) itemView.findViewById(R.id.ref_date);

        }

    }

    private void setNames(String names){
        mViewHolder.clientsNames.setText(names);
    }

    private static class patientDetailsTask extends AsyncTask<Void, Void, Void>{

        String patientNames, patientId;
        AppDatabase db;
        TextView mText;

        patientDetailsTask(AppDatabase database, String patientID, TextView namesInstance){
            this.db = database;
            this.patientId = patientID;
            mText = namesInstance;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("reckless", "doing name search backgroundically!");
            patientNames = db.patientModel().getPatientName(patientId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("reckless", "Done background !"+patientNames);
            mText.setText(patientNames);
            //adapter.notifyDataSetChanged();
        }

    }


}
