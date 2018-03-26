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
import java.util.UUID;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.activities.TbClientDetailsActivity;
import com.softmed.htmr_facility.activities.TbReferralDetailsActivity;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.PostOffice;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.TbPatient;

import static com.softmed.htmr_facility.utils.constants.ENTRY_NOT_SYNCED;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_REFERRAL_FEEDBACK;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_PATIENT;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_TB_PATIENT;
import static com.softmed.htmr_facility.utils.constants.REFERRAL_STATUS_COMPLETED;

/**
 * Created by issy on 12/14/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbReferralListRecyclerAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>  {

    private List<Referral> items;
    private Context context;
    private AppDatabase database;
    private TbReferralListRecyclerAdapter.ListViewItemViewHolder mViewHolder;

    public TbReferralListRecyclerAdapter(List<Referral> mItems, Context context){
        this.items = mItems;
        this.database = AppDatabase.getDatabase(context);
    }

    public TbReferralListRecyclerAdapter(){}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        context         = viewGroup.getContext();
        View itemView   = null;
        itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.referal_list_client_item, viewGroup, false);

        return new TbReferralListRecyclerAdapter.ListViewItemViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int itemPosition){

        final Referral referral = getItem(itemPosition);

        TbReferralListRecyclerAdapter.ListViewItemViewHolder holder = (TbReferralListRecyclerAdapter.ListViewItemViewHolder) viewHolder;
        mViewHolder = holder;

        new TbReferralListRecyclerAdapter.patientDetailsTask(database, referral.getPatient_id(), holder.clientsNames).execute();

        if (referral.getReferralStatus() == 0){
            holder.attendedFlag.setText("Mpya");
            holder.attendedFlag.setTextColor(context.getResources().getColor(R.color.red_a700));
        }else {
            holder.attendedFlag.setText("Tayari");
            holder.attendedFlag.setTextColor(context.getResources().getColor(R.color.green_a700));
        }

        holder.ctcNumber.setText(referral.getCtcNumber());
        holder.referralReasons.setText(referral.getReferralReason());
        holder.referralDate.setText(BaseActivity.simpleDateFormat.format(referral.getReferralDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 *  Steps:
                 *
                 *  End current referral with default values : Service Given = RECEIVED SUCCESSFULLY
                 *  Add referral entry to postman
                 *  Update Existing Patient Object to CURRENTLY ON TB CLINIC
                 *  Add patient entry to postman
                 *  Create a new TB Client with current patient ID
                 *  Add TbPatient entry to postman
                 *
                 *  Call @TbPatientActivity.java passing current Patient and the newly created TbPatient
                 *
                 */

                new EnrollPatientToTbClinic(database).execute(referral);

                /*Intent intent = new Intent(context, TbReferralDetailsActivity.class);
                intent.putExtra("referal", referral);
                context.startActivity(intent);*/

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
            referralDate = (TextView) itemView.findViewById(R.id.ref_date);
            ctcNumber = (TextView) itemView.findViewById(R.id.ctc_number);
            referralReasons = (TextView) itemView.findViewById(R.id.referral_reasons);

        }

    }

    static class patientDetailsTask extends AsyncTask<Void, Void, Void> {

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

    class EnrollPatientToTbClinic extends AsyncTask<Referral, Void, Void>{

        AppDatabase database;
        Patient patient;
        TbPatient tbPatient;

        EnrollPatientToTbClinic(AppDatabase db){
            this.database = db;
        }

        @Override
        protected Void doInBackground(Referral... referrals) {
            Referral currentReferral = referrals[0];

            //End Current Referral
            currentReferral.setReferralStatus(REFERRAL_STATUS_COMPLETED);
            currentReferral.setServiceGivenToPatient(context.getResources().getString(R.string.received_at_tb_clinic));
            currentReferral.setOtherNotesAndAdvices("");
            database.referalModel().updateReferral(currentReferral);

            //Add Post office entry
            PostOffice postOffice = new PostOffice();
            postOffice.setPost_id(currentReferral.getReferral_id());
            postOffice.setPost_data_type(POST_DATA_REFERRAL_FEEDBACK);
            postOffice.setSyncStatus(ENTRY_NOT_SYNCED);
            database.postOfficeModelDao().addPostEntry(postOffice);

            //Update patient to currently on TB Clinic
            patient = database.patientModel().getPatientById(currentReferral.getPatient_id());
            patient.setCurrentOnTbTreatment(true);
            //TODO : handle CTC Number input at the clinic
            database.patientModel().updatePatient(patient);

            PostOffice patientPost = new PostOffice();
            patientPost.setPost_id(patient.getPatientId());
            patientPost.setPost_data_type(POST_DATA_TYPE_PATIENT);
            patientPost.setSyncStatus(ENTRY_NOT_SYNCED);
            database.postOfficeModelDao().addPostEntry(patientPost);

            //Create a new Tb Patient and add to post office
            tbPatient = new TbPatient();
            tbPatient.setPatientId(Long.parseLong(patient.getPatientId()));
            tbPatient.setTempID(UUID.randomUUID()+"");
            database.tbPatientModelDao().addPatient(tbPatient);

            PostOffice tbPatientPost = new PostOffice();
            tbPatientPost.setPost_id(patient.getPatientId());
            tbPatientPost.setPost_data_type(POST_DATA_TYPE_TB_PATIENT);
            tbPatientPost.setSyncStatus(ENTRY_NOT_SYNCED);
            database.postOfficeModelDao().addPostEntry(tbPatientPost);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Call Tb Clinic activity passing patient and tbpatient
            Intent intent = new Intent(context, TbClientDetailsActivity.class);
            intent.putExtra("patient", patient);
            intent.putExtra("isPatientNew", true);
            context.startActivity(intent);
        }
    }

}
