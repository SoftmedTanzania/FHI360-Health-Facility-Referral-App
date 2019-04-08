package com.softmed.ccm_facility.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.softmed.ccm_facility.R;
import com.softmed.ccm_facility.activities.TbClientDetailsActivity;
import com.softmed.ccm_facility.activities.TbClientListActivity;
import com.softmed.ccm_facility.base.AppDatabase;
import com.softmed.ccm_facility.dom.objects.Patient;
import com.softmed.ccm_facility.dom.objects.TbPatient;
import com.softmed.ccm_facility.fragments.IssueReferralDialogueFragment;
import com.softmed.ccm_facility.utils.PatientsDiffCallback;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbClientListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    List<Patient> items = Collections.emptyList();
    private Context context;
    private int serviceID;

    public TbClientListAdapter(Context context, int serviceId){
        this.serviceID = serviceId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        context         = viewGroup.getContext();
        View itemView   = null;
        itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.tb_client_list_item, viewGroup, false);

        return new TbClientListAdapter.ListViewItemViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int itemPosition){

        final Patient patient = getItem(itemPosition);

        TbClientListAdapter.ListViewItemViewHolder holder = (TbClientListAdapter.ListViewItemViewHolder) viewHolder;

        //holder.clientTreatment.setText(tbPatient.getTreatment_type());
        holder.clientNames.setText(patient.getPatientFirstName()+" "+patient.getPatientSurname());
        holder.clientVillage.setText(patient.getVillage());
        holder.clientPhoneNumber.setText(patient.getPhone_number());

        new GetPatientDetails(AppDatabase.getDatabase(context), holder).execute(patient);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UpdatePatientInformation and save data
                Intent intent = new Intent(context, TbClientDetailsActivity.class);
                intent.putExtra(TbClientDetailsActivity.CURRENT_PATIENT, patient);
                intent.putExtra(TbClientDetailsActivity.ORIGIN_STATUS , TbClientDetailsActivity.FROM_CLIENTS);
                context.startActivity(intent);
            }
        });

        holder.rufaaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertView alert = new AlertView(context.getResources().getString(R.string.issue_referral), context.getResources().getString(R.string.issue_referral_prompt), AlertStyle.DIALOG);
                alert.addAction(new AlertAction(context.getResources().getString(R.string.answer_no), AlertActionStyle.DEFAULT, action -> {
                    // Action 1 callback
                }));
                alert.addAction(new AlertAction(context.getResources().getString(R.string.answer_yes), AlertActionStyle.NEGATIVE, action -> {
                    // Action 2 callback
                    callReferralFragmentDialogue(patient);
                }));
                alert.show((TbClientListActivity)context);
            }
        });


    }

    private void callReferralFragmentDialogue(Patient patient){
        TbClientListActivity activity = (TbClientListActivity) context;
        FragmentManager fm = activity.getSupportFragmentManager();

        IssueReferralDialogueFragment issueReferralDialogueFragment = IssueReferralDialogueFragment.newInstance(patient, serviceID, UUID.randomUUID()+"");
        issueReferralDialogueFragment.show(fm, "referral_fragment_from_adapter");

    }

    public void addItems (List<Patient> pat){
        this.items = pat;
        notifyDataSetChanged();
    }

    private String getTreatment(int position){

        String treatment = "";

        switch (position){
            case 1:
                treatment = "1 Treat";
                break;
            case 2:
                treatment = "2 Treat";
                break;
            case 3:
                treatment = "3 Treat";
                break;
        }

        return treatment;

    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    private Patient getItem(int position){
        return items.get(position);
    }

    private class ListViewItemViewHolder extends RecyclerView.ViewHolder {

        TextView clientNames, clientVillage, clientPhoneNumber, clientTreatment;
        View viewItem;
        Button rufaaButton;

        public ListViewItemViewHolder(View itemView){
            super(itemView);
            this.viewItem   = itemView;

            rufaaButton =  itemView.findViewById(R.id.rufaa_button);

            clientNames =  itemView.findViewById(R.id.client_f_name);
            clientVillage =  itemView.findViewById(R.id.client_village);
            clientPhoneNumber =  itemView.findViewById(R.id.client_phone_number);
            clientTreatment =  itemView.findViewById(R.id.client_treatment);

        }

    }

    class GetPatientDetails extends AsyncTask<Patient, Void, Void>{

        TbPatient tbPatient;
        AppDatabase database;
        TbClientListAdapter.ListViewItemViewHolder holder;
                //= (TbClientListAdapter.ListViewItemViewHolder) viewHolder;

        GetPatientDetails(AppDatabase db, TbClientListAdapter.ListViewItemViewHolder vh){
            this.database = db;
            this.holder = vh;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                holder.clientTreatment.setText(tbPatient.getTreatment_type());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Patient... args) {
            tbPatient = database.tbPatientModelDao().getCurrentTbPatientByPatientId(args[0].getPatientId());
            return null;
        }
    }

    public void setPatient (List<Patient> newList ){

        PatientsDiffCallback patientsDiffCallback = new PatientsDiffCallback(this.items, newList);

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(patientsDiffCallback);

        this.items = Collections.emptyList();
        this.items = newList;

        diffResult.dispatchUpdatesTo(this);

    }

}
