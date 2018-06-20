package com.softmed.htmr_facility.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.adapters.AppointmentRecyclerAdapter;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.PatientAppointment;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.viewmodels.PatientsAppointmentsListViewModel;
import com.softmed.htmr_facility.viewmodels.ReferalListViewModel;

import fr.ganfra.materialspinner.MaterialSpinner;

import static android.view.View.GONE;

/**
 * Created by issy on 12/14/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class AppointmentActivity extends BaseActivity {

    private Toolbar toolbar;
    private RecyclerView appointmentRecycler;
    private EditText patientName, fromDate, toDate;
    private MaterialSpinner statusSpinner, appointmentType;
    private AppointmentRecyclerAdapter adapter;
    private mAdapter appointmentTypeAdapter;
    private List<PatientAppointment> ctcAppointmentsList = new ArrayList<>();
    private List<PatientAppointment> tbAppointmentsList = new ArrayList<>();
    private List<String> appointmentTypeList = new ArrayList<>();

    private PatientsAppointmentsListViewModel listViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        setupviews();

        if (toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.client_appointment));
        }

        appointmentTypeList.add(getResources().getString(R.string.ctc));
        appointmentTypeList.add(getResources().getString(R.string.tb));
        appointmentTypeAdapter = new mAdapter(this, R.layout.subscription_plan_items_drop_down, appointmentTypeList);
        appointmentType.setAdapter(appointmentTypeAdapter);


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Long todaysDate = cal.getTime().getTime();
        Long tommorowsDate = cal.getTime().getTime()+48*60*60*1000;

        listViewModel = ViewModelProviders.of(this).get(PatientsAppointmentsListViewModel.class);
        listViewModel.getCTCAppointments(todaysDate,tommorowsDate).observe(AppointmentActivity.this, new Observer<List<PatientAppointment>>() {
            @Override
            public void onChanged(@Nullable List<PatientAppointment> ctcAppList) {
                ctcAppointmentsList = ctcAppList;
            }
        });

        listViewModel.getTBAppointments(todaysDate,tommorowsDate).observe(AppointmentActivity.this, new Observer<List<PatientAppointment>>() {
            @Override
            public void onChanged(@Nullable List<PatientAppointment> tbAppList ) {
                tbAppointmentsList = tbAppList;
            }
        });



        appointmentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == -1){

                }else if (i == 0){
                    findViewById(R.id.tb_appointments_header).setVisibility(GONE);
                    findViewById(R.id.ctc_header).setVisibility(View.VISIBLE);
                    adapter.addItems(ctcAppointmentsList,0);
                }else {
                    findViewById(R.id.ctc_header).setVisibility(GONE);
                    findViewById(R.id.tb_appointments_header).setVisibility(View.VISIBLE);
                    adapter.addItems(tbAppointmentsList,1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter = new AppointmentRecyclerAdapter(ctcAppointmentsList,this,baseDatabase);
        appointmentRecycler.setAdapter(adapter);

        appointmentType.setSelection(1);


    }

    private void setupviews(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appointmentRecycler = (RecyclerView) findViewById(R.id.appointment_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        appointmentRecycler.setLayoutManager(layoutManager);
        appointmentRecycler.setHasFixedSize(true);

        appointmentType = (MaterialSpinner) findViewById(R.id.spin_appointment_type);
        statusSpinner = (MaterialSpinner) findViewById(R.id.spin_status);
        patientName = (EditText) findViewById(R.id.client_name_et);
        fromDate = (EditText) findViewById(R.id.from_date_et);
        toDate = (EditText) findViewById(R.id.to_date_et);

    }

    class mAdapter extends ArrayAdapter<String> {

        List<String> items = new ArrayList<>();
        Context act;

        public mAdapter(Context context, int resource, List<String> mItems) {
            super(context, resource, mItems);
            this.items = mItems;
            act = context;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            LayoutInflater vi = (LayoutInflater) act.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = vi.inflate(R.layout.subscription_plan_items_drop_down, null);

            TextView tvTitle =(TextView)rowView.findViewById(R.id.rowtext);
            tvTitle.setText(items.get(position));

            return rowView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View rowView = convertView;
            LayoutInflater vi = (LayoutInflater) act.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = vi.inflate(R.layout.single_text_spinner_view_item, null);

            TextView tvTitle = (TextView)rowView.findViewById(R.id.rowtext);
            tvTitle.setText(items.get(position));

            return rowView;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void updateItems(List<String> newItems){
            this.items = null;
            this.items = newItems;
            this.notifyDataSetChanged();
        }

    }

}
