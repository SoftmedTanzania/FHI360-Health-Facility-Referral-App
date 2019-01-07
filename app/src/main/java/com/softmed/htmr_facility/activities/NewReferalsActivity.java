package com.softmed.htmr_facility.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.adapters.PatientsListAdapter;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.utils.PatientsSearchFilterUtil;
import com.softmed.htmr_facility.viewmodels.PatientsListViewModel;

import static com.softmed.htmr_facility.utils.constants.HIV_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.OPD_SERVICE_ID;

/**
 * Created by issy on 11/16/17.
 */

public class NewReferalsActivity extends BaseActivity {

    private Toolbar toolbar;
    private RecyclerView patientsRecycler;
    private PatientsListAdapter adapter;
    private TextView activityTitle, searchName, searchCTC, searchVillage;

    private PatientsListViewModel patientsListViewModel;
    private int service;

    private List<Patient> patientsList = new ArrayList<>();
    private List<Patient> listOnSearch  = new ArrayList<>();

    private PatientsSearchFilterUtil patientsSearchFilterUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null){
            service = getIntent().getIntExtra("service", 0);
        }

        if (service == HIV_SERVICE_ID){
            setContentView(R.layout.activity_hiv_new_referrals);
        }else {
            setContentView(R.layout.activity_new_referals);
        }

        setupview();

        patientsSearchFilterUtil = new PatientsSearchFilterUtil(listOnSearch);

        adapter = new PatientsListAdapter(new ArrayList<Patient>(), this, service);
        patientsListViewModel = ViewModelProviders.of(this).get(PatientsListViewModel.class);

        if (service == HIV_SERVICE_ID){
            patientsListViewModel.getHivPatientsOnly().observe(NewReferalsActivity.this, new Observer<List<Patient>>() {
                @Override
                public void onChanged(@Nullable List<Patient> patients) {
                    adapter.addItems(patients);
                }
            });
        }else {
            patientsListViewModel.getPatientsList().observe(NewReferalsActivity.this, new Observer<List<Patient>>() {
                @Override
                public void onChanged(@Nullable List<Patient> patients) {
                    patientsList = patients;
                    listOnSearch = patientsList;

                    patientsSearchFilterUtil.setPatientList(listOnSearch);

                    adapter.addItems(patients);
                }
            });
        }

        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listOnSearch = patientsSearchFilterUtil.searchByNames(charSequence.toString());
                adapter.setPatient(listOnSearch);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchVillage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listOnSearch = patientsSearchFilterUtil.searchByVillage(charSequence.toString());
                adapter.setPatient(listOnSearch);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        patientsRecycler.setAdapter(adapter);

    }

    private void setupview(){
        toolbar = findViewById(R.id.toolbar);

        activityTitle = findViewById(R.id.activity_title);

        patientsRecycler = findViewById(R.id.patients_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        patientsRecycler.setLayoutManager(layoutManager);
        patientsRecycler.setHasFixedSize(true);

        searchName = findViewById(R.id.client_name_et);
        searchCTC = findViewById(R.id.client_ctc_number_et);
        searchVillage = findViewById(R.id.client_village_et);

        String title= "";
        switch (service){
            case HIV_SERVICE_ID:
                title = getResources().getString(R.string.clients_list)+" | "+getResources().getString(R.string.hiv);
                break;
            case OPD_SERVICE_ID:
                title = getResources().getString(R.string.clients_list)+" | OPD";
                break;
            default:
                break;
        }
        activityTitle.setText(title);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

}
