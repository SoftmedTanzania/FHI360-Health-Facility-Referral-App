package apps.softmed.com.hfreferal.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import apps.softmed.com.hfreferal.activities.NewReferalsActivity;
import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.fragments.IssueReferralDialogueFragment;
import fr.ganfra.materialspinner.MaterialSpinner;

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

    public PatientsListAdapter(List<Patient> mItems, Context context){
        this.items = mItems;
    }

    public PatientsListAdapter(){}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        context         = viewGroup.getContext();
        View itemView   = null;
        itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.patient_list_item, viewGroup, false);

        return new PatientsListAdapter.ListViewItemViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int itemPosition){

        final Patient patient = getItem(itemPosition);
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

    private void callReferralFragmentDialogue(Patient patient){
        NewReferalsActivity activity = (NewReferalsActivity) context;
        FragmentManager fm = activity.getSupportFragmentManager();

        IssueReferralDialogueFragment issueReferralDialogueFragment = IssueReferralDialogueFragment.newInstance(patient);
        issueReferralDialogueFragment.show(fm, "referral_fragment_from_adapter");

    }

    private void referalDialogueEvents(Patient patient){

        referalDialogue = new Dialog(context);
        referalDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final View mView = LayoutInflater.from(context).inflate(R.layout.custom_dialogue_layout, null);
        referalDialogue.setContentView(mView);

        TextView patientName = (TextView) mView.findViewById(R.id.patient_name);
        patientName.setText(patient.getPatientFirstName()+ " "+patient.getPatientSurname());

        MaterialSpinner servicesSpinner = (MaterialSpinner) mView.findViewById(R.id.spin_service);
        MaterialSpinner healthFacilitySpinner = (MaterialSpinner) mView.findViewById(R.id.spin_to_facility);

        String[] servicesList = {"TB", "HIV", "MALARIA" };
        String[] hflist = {"Lugalo Hospital", "Kaloleni Dispensary", "Mount Meru Hospital" };

        Button tumaButton = (Button) mView.findViewById(R.id.tuma_button);
        tumaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                referalDialogue.dismiss();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, servicesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicesSpinner.setAdapter(adapter);

        ArrayAdapter<String> hfAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, hflist);
        hfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        healthFacilitySpinner.setAdapter(hfAdapter);

        referalDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        referalDialogue.setCancelable(false);
        referalDialogue.show();

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

}
