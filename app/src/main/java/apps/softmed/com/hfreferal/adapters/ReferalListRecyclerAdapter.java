package apps.softmed.com.hfreferal.adapters;

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

import apps.softmed.com.hfreferal.ClientsDetailsActivity;
import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.dom.objects.Client;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.Referal;

/**
 * Created by issy on 11/17/17.
 */

public class ReferalListRecyclerAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private List<Referal> items;
    private Context context;
    private AppDatabase database;
    private ListViewItemViewHolder mViewHolder;

    public ReferalListRecyclerAdapter(List<Referal> mItems, Context context){
        this.items = mItems;
        this.database = AppDatabase.getDatabase(context);
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

        final Referal referal = getItem(itemPosition);

        ReferalListRecyclerAdapter.ListViewItemViewHolder holder = (ReferalListRecyclerAdapter.ListViewItemViewHolder) viewHolder;
        mViewHolder = holder;

        new patientDetailsTask(database, referal.getPatient_id(), holder.clientsNames).execute();

        if (referal.getReferralStatus() == 0){
            holder.attendedFlag.setText("New");
            holder.attendedFlag.setTextColor(context.getResources().getColor(R.color.red_a700));
        }else {
            holder.attendedFlag.setText("Attended");
            holder.attendedFlag.setTextColor(context.getResources().getColor(R.color.green_a700));
        }

        holder.ctcNumber.setText(referal.getCtcNumber());
        holder.referralReasons.setText(referal.getReferralReason());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClientsDetailsActivity.class);
                intent.putExtra("referal", referal);
                context.startActivity(intent);
            }
        });

    }

    public void addItems (List<Referal> referalList){
        this.items = referalList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return items.size();
//        return 10;
    }

    private Referal getItem(int position){
        return items.get(position);
    }

    private class ListViewItemViewHolder extends RecyclerView.ViewHolder {

        TextView clientsNames, newFlag, attendedFlag, ctcNumber, referralReasons;
        View viewItem;

        public ListViewItemViewHolder(View itemView){
            super(itemView);
            this.viewItem   = itemView;

            clientsNames = (TextView) itemView.findViewById(R.id.client_name);
            attendedFlag = (TextView) itemView.findViewById(R.id.attended_flag);

            ctcNumber = (TextView) itemView.findViewById(R.id.ctc_number);
            referralReasons = (TextView) itemView.findViewById(R.id.referral_reasons);

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
