package com.softmed.htmr_facility.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.activities.NewReferalsActivity;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.fragments.IssueReferralDialogueFragment;

import static com.softmed.htmr_facility.utils.constants.HIV_SERVICE_ID;

/**
 * Created by issy on 12/6/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class PatientsListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>  {

    List<Patient> items;
    private Context context;
    public Dialog referalDialogue;
    private int serviceID;

    public PatientsListAdapter(List<Patient> mItems, Context context, int serviceID){
        this.items = mItems;
        this.serviceID = serviceID;
    }

    public PatientsListAdapter(){}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        context         = viewGroup.getContext();
        View itemView   = null;
        if (serviceID == HIV_SERVICE_ID){
            itemView = LayoutInflater
                    .from(viewGroup.getContext())
//                    .inflate(R.layout.hiv_client_list_item, viewGroup, false);
                    .inflate(R.layout.patient_list_item, viewGroup, false);
            return new PatientsListAdapter.HivListItemViewHolder(itemView);

        }else {
            itemView = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.patient_list_item, viewGroup, false);

            return new PatientsListAdapter.ListViewItemViewHolder(itemView);
        }

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int itemPosition){

        final Patient patient = getItem(itemPosition);

        if (viewHolder instanceof PatientsListAdapter.HivListItemViewHolder){
            PatientsListAdapter.HivListItemViewHolder holder = (PatientsListAdapter.HivListItemViewHolder) viewHolder;

            holder.clientCTCNumber.setText("N/A");
            holder.clientFirstName.setText(patient.getPatientFirstName());
            holder.clientLastName.setText(patient.getPatientSurname());
            holder.clientVillage.setText(patient.getVillage());
            holder.clientPhoneNumber.setText(patient.getPhone_number());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //refer client popup
//                referalDialogueEvents(patient);
                    callReferralFragmentDialogue(patient);
                }
            });
        }else if (viewHolder instanceof PatientsListAdapter.ListViewItemViewHolder){
            PatientsListAdapter.ListViewItemViewHolder holder = (PatientsListAdapter.ListViewItemViewHolder) viewHolder;

            holder.clientCTCNumber.setText("N/A");
            holder.clientFirstName.setText(patient.getPatientFirstName());
            holder.clientLastName.setText(patient.getPatientSurname());
            holder.clientVillage.setText(patient.getVillage());
            holder.clientPhoneNumber.setText(patient.getPhone_number());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //refer client popup
//                referalDialogueEvents(patient);
                    callReferralFragmentDialogue(patient);
                }
            });
        }

    }

    private void callReferralFragmentDialogue(Patient patient){
        NewReferalsActivity activity = (NewReferalsActivity) context;
        FragmentManager fm = activity.getSupportFragmentManager();

        IssueReferralDialogueFragment issueReferralDialogueFragment = IssueReferralDialogueFragment.newInstance(patient, serviceID);
        Log.d("MIMI", serviceID+"");
        issueReferralDialogueFragment.show(fm, "referral_fragment_from_adapter");

    }

    public void addItems (List<Patient> pat){
        this.items = pat;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return items.size();
//        return 10;
    }

    private Patient getItem(int position){
        return items.get(position);
    }

    private class ListViewItemViewHolder extends RecyclerView.ViewHolder {

        TextView clientFirstName, clientLastName, clientCTCNumber, clientVillage, clientPhoneNumber;
        View viewItem;

        public ListViewItemViewHolder(View itemView){
            super(itemView);
            this.viewItem   = itemView;

            clientFirstName = (TextView) itemView.findViewById(R.id.client_f_name);
            clientLastName = (TextView) itemView.findViewById(R.id.client_l_name);
            clientCTCNumber = (TextView) itemView.findViewById(R.id.client_ctc_number);
            clientVillage = (TextView) itemView.findViewById(R.id.client_village);
            clientPhoneNumber = (TextView) itemView.findViewById(R.id.client_phone_number);

        }

    }

    private class HivListItemViewHolder extends RecyclerView.ViewHolder {

        TextView clientFirstName, clientLastName, clientCTCNumber, clientVillage, clientPhoneNumber;
        View viewItem;

        public HivListItemViewHolder(View itemView){
            super(itemView);
            this.viewItem   = itemView;

            clientFirstName = (TextView) itemView.findViewById(R.id.client_f_name);
            clientLastName = (TextView) itemView.findViewById(R.id.client_l_name);
            clientCTCNumber = (TextView) itemView.findViewById(R.id.client_ctc_number);
            clientVillage = (TextView) itemView.findViewById(R.id.client_village);
            clientPhoneNumber = (TextView) itemView.findViewById(R.id.client_phone_number);

        }

    }

}
