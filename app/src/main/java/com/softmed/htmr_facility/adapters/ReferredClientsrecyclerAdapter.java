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
import com.softmed.htmr_facility.activities.FeedbackDetailsActivity;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Referral;

/**
 * Created by issy on 12/7/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ReferredClientsrecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Referral> items;
    private Context context;
    private AppDatabase database;
    private ReferredClientsrecyclerAdapter.ListViewItemViewHolder mViewHolder;

    private final static int HEADER_VIEW = 1;
    private final static int ITEM_VIEW = 2;


    public ReferredClientsrecyclerAdapter(List<Referral> mItems, Context context) {
        this.items = mItems;
        this.database = AppDatabase.getDatabase(context);
    }

    public ReferredClientsrecyclerAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        View itemView = null;
        if (viewType == HEADER_VIEW) {
            itemView = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.refered_clients_list_header, viewGroup, false);
            return new ReferredClientsrecyclerAdapter.ListViewHeaderViewHolder(itemView);
        } else if (viewType == ITEM_VIEW) {
            itemView = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.referred_clients_list_item, viewGroup, false);
            return new ReferredClientsrecyclerAdapter.ListViewItemViewHolder(itemView);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int itemPosition) {

        final Referral referral = getItem(itemPosition);

        if (viewHolder instanceof ReferredClientsrecyclerAdapter.ListViewItemViewHolder) {
            ReferredClientsrecyclerAdapter.ListViewItemViewHolder holder = (ReferredClientsrecyclerAdapter.ListViewItemViewHolder) viewHolder;
            mViewHolder = holder;

            new ReferredClientsrecyclerAdapter.patientDetailsTask(database, referral, holder.clientsNames, holder.serviceName, context).execute();

            holder.referralReasons.setText(referral.getReferralReason());
            holder.referralDate.setText(BaseActivity.simpleDateFormat.format(referral.getReferralDate()));

            if (referral.getReferralStatus() == 1) {
                holder.status.setText(context.getResources().getString(R.string.suceessful_label));
                holder.statusIcon.setBackgroundColor(context.getResources().getColor(R.color.green_400));
            } else if (referral.getReferralStatus() == -1) {
                holder.status.setText(context.getResources().getString(R.string.unsuccessful_label));
                holder.statusIcon.setBackgroundColor(context.getResources().getColor(R.color.red_400));
            } else {
                holder.status.setText(context.getResources().getString(R.string.pending_label));
                holder.statusIcon.setBackgroundColor(context.getResources().getColor(R.color.blue_400));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FeedbackDetailsActivity.class);
                    intent.putExtra("referal", referral);
                    context.startActivity(intent);
                }
            });
        }

    }

    public void addItems(List<Referral> referralList) {
        this.items = referralList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return HEADER_VIEW;

        return ITEM_VIEW;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private Referral getItem(int position) {
        if (items.size() > 0 && position != 0) {
            return items.get(position - 1);
        } else return null;
    }

    private class ListViewItemViewHolder extends RecyclerView.ViewHolder {

        TextView clientsNames, serviceName, referralReasons, referralDate, status;
        View viewItem, statusIcon;

        public ListViewItemViewHolder(View itemView) {
            super(itemView);
            this.viewItem = itemView;

            clientsNames = itemView.findViewById(R.id.client_name);
            serviceName = itemView.findViewById(R.id.service_name);
            status = itemView.findViewById(R.id.status);
            statusIcon = itemView.findViewById(R.id.status_color);
            referralReasons = itemView.findViewById(R.id.referral_reasons);
            referralDate = itemView.findViewById(R.id.ref_date);

        }

    }

    private class ListViewHeaderViewHolder extends RecyclerView.ViewHolder {

        View viewItem;

        public ListViewHeaderViewHolder(View itemView) {
            super(itemView);
            this.viewItem = itemView;
        }

    }

    private static class patientDetailsTask extends AsyncTask<Void, Void, Void> {

        String patientNames, serviceNameString;
        AppDatabase db;
        TextView mText, sname;
        Referral ref;
        Context context;

        patientDetailsTask(AppDatabase database, Referral referral, TextView namesInstance, TextView serviceName, Context context) {
            this.db = database;
            this.ref = referral;
            mText = namesInstance;
            sname = serviceName;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("reckless", "doing name search backgroundically!");
            patientNames = db.patientModel().getPatientName(ref.getPatient_id());
            if (ref.getServiceId() == -1) {
                serviceNameString = context.getResources().getString(R.string.followup_service_name);
            } else {
                serviceNameString = db.referralServiceIndicatorsDao().getServiceNameById(ref.getServiceId());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("reckless", "Done background !" + patientNames);
            mText.setText(patientNames);
            sname.setText(serviceNameString);
            //adapter.notifyDataSetChanged();
        }

    }


}
