package apps.softmed.com.hfreferal.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.TbClientDetailsActivity;
import apps.softmed.com.hfreferal.TbReferralDetailsActivity;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbClientListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    List<Patient> items;
    private Context context;
    public Dialog referalDialogue;

    public TbClientListAdapter(List<Patient> mItems, Context context){
        this.items = mItems;
    }

    public TbClientListAdapter(){}

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

        holder.clientFirstName.setText(patient.getPatientFirstName());
        holder.clientLastName.setText(patient.getPatientSurname());
        holder.clientVillage.setText(patient.getVillage());
        holder.clientPhoneNumber.setText(patient.getPhone_number());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //refer client popup
                context.startActivity(new Intent(context, TbClientDetailsActivity.class));
            }
        });

        holder.rufaaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                referalDialogueEvents(patient);
            }
        });

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

        TextView clientFirstName, clientLastName, clientVillage, clientPhoneNumber;
        View viewItem;
        Button rufaaButton;

        public ListViewItemViewHolder(View itemView){
            super(itemView);
            this.viewItem   = itemView;

            rufaaButton = (Button) itemView.findViewById(R.id.rufaa_button);

            clientFirstName = (TextView) itemView.findViewById(R.id.client_f_name);
            clientLastName = (TextView) itemView.findViewById(R.id.client_l_name);
            clientVillage = (TextView) itemView.findViewById(R.id.client_village);
            clientPhoneNumber = (TextView) itemView.findViewById(R.id.client_phone_number);

        }

    }
}
