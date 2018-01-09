package apps.softmed.com.hfreferal.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.adapters.TbClientListAdapter;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.viewmodels.PatientsListViewModel;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbClientListActivity extends BaseActivity {

    private Toolbar toolbar;
    private RecyclerView patientsRecycler;
    private TbClientListAdapter adapter;

    private PatientsListViewModel patientsListViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_referals);
        setupview();

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        adapter = new TbClientListAdapter(new ArrayList<Patient>(), this);
        patientsListViewModel = ViewModelProviders.of(this).get(PatientsListViewModel.class);
        patientsListViewModel.getTbPatientsOnly().observe(TbClientListActivity.this, new Observer<List<Patient>>() {
            @Override
            public void onChanged(@Nullable List<Patient> patients) {
                adapter.addItems(patients);
            }
        });

        patientsRecycler.setAdapter(adapter);

    }

    private void setupview(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        patientsRecycler = (RecyclerView) findViewById(R.id.patients_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        patientsRecycler.setLayoutManager(layoutManager);
        patientsRecycler.setHasFixedSize(true);
    }

}
