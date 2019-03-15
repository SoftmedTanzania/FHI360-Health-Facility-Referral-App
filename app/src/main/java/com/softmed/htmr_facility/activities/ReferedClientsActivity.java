package com.softmed.htmr_facility.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;
import com.softmed.htmr_facility.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.softmed.htmr_facility.adapters.ReferredClientsrecyclerAdapter;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.viewmodels.ReferalListViewModel;
import fr.ganfra.materialspinner.MaterialSpinner;

import static com.softmed.htmr_facility.utils.constants.HIV_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.LAB_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.OPD_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.STATUS_COMPLETED;
import static com.softmed.htmr_facility.utils.constants.STATUS_NEW;
import static com.softmed.htmr_facility.utils.constants.TB_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.getReferralStatusValue;

/**
 * Created by issy on 11/16/17.
 */

public class ReferedClientsActivity extends BaseActivity {

    private ReferalListViewModel listViewModel;

    private Toolbar toolbar;
    private RecyclerView clientsRecycler;
    private MaterialSpinner statusSpinner;
    private EditText fromDateText, toDateText, clientNameText, clientCtcNumberText, clientLastName;
    private ProgressView progressView;
    private Button filterButton;
    TextView activityTitle;

    private Date fromDate, toDate;
    private String clientName, clientCtcNumber, lastName;
    private int selectedStatus, serviceID, serviceCategory;
    private boolean notSelectedStatus, notSelectedFromDate, notSelectedTodate;

    private AppDatabase database;

    private ReferredClientsrecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refered_clients);
        setupview();

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null){
            serviceID = getIntent().getIntExtra("service_id", 0);
            serviceCategory = getIntent().getIntExtra("category", 0);

            String title = "";

            switch (serviceID){
                case OPD_SERVICE_ID:
                    if(serviceCategory==-1){
                        title = getResources().getString(R.string.community_referrals_label);
                    }else if(serviceCategory==HIV_SERVICE_ID){
                        title = getResources().getString(R.string.ctc_referrals);
                    }
                    break;
                case HIV_SERVICE_ID:
                    title = getResources().getString(R.string.referrals_issued)+" | "+getResources().getString(R.string.hiv);
                    break;
                case TB_SERVICE_ID:
                    title = getResources().getString(R.string.referrals_issued)+" | "+getResources().getString(R.string.tb);
                    break;
                default:
                    title = getResources().getString(R.string.referrals_issued);
            }

            activityTitle.setText(title);

        }

        String completedStatus = "";
        String newStatus = "";

        if (BaseActivity.getLocaleString().equals(ENGLISH_LOCALE)){
            newStatus = "New";
            completedStatus = "Attended";
        }else {
            newStatus = "Mpya";
            completedStatus = "Tayari";
        }

        final String[] status = {completedStatus, newStatus};
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(spinAdapter);

        notSelectedStatus = true;
        notSelectedFromDate = true;
        notSelectedTodate = true;

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0){
                    selectedStatus = getReferralStatusValue((String) statusSpinner.getItemAtPosition(i));
                    Log.d("ReferalFilters", "Selected Status is : "+selectedStatus);
                }else {
                    notSelectedStatus = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getInputs()){
                    filterButton.setVisibility(View.INVISIBLE);
                    progressView.setVisibility(View.VISIBLE);

                    ReferedClientsActivity.QueryReferals queryReferals = new ReferedClientsActivity.QueryReferals(clientName, lastName, clientCtcNumber, fromDate, toDate, selectedStatus, database);
                    queryReferals.execute();

                }else {
                    Toast.makeText(ReferedClientsActivity.this,"Please Fill in any field ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fromDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Date Picker
                fromDatePicker.show(getFragmentManager(),"fromDate");
                fromDatePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        fromDateText.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        Calendar fromCalendar = Calendar.getInstance();
                        fromCalendar.set(year, monthOfYear, dayOfMonth);
                        fromDate = fromCalendar.getTime();
                        notSelectedFromDate = false;
                    }

                });

            }
        });

        toDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Date Picker
                toDatePicker.show(getFragmentManager(),"toDate");
                toDatePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        toDateText.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        Calendar toCalendar = Calendar.getInstance();
                        toCalendar.set(year, monthOfYear, dayOfMonth);
                        toDate = toCalendar.getTime();
                        notSelectedTodate = false;
                    }

                });
            }
        });

        adapter = new ReferredClientsrecyclerAdapter(new ArrayList<Referral>(), this);
        listViewModel = ViewModelProviders.of(this).get(ReferalListViewModel.class);
        clientsRecycler.setAdapter(adapter);

        if (serviceCategory == -1){ //Other Referrals Category

            //Get all non basic service Ids
            LiveData<List<Integer>> nonBasicServiceIds = baseDatabase.referralServiceIndicatorsDao().getAllNonBasicServiceIds();
            nonBasicServiceIds.observe(this, new Observer<List<Integer>>() {
                @Override
                public void onChanged(@Nullable List<Integer> integers) {
                    int size = integers == null ? 0:integers.size();
                    int[] categoryIds = new int[size];
                    try {
                        categoryIds[0] = -1;
                    }catch (Exception e){
                        categoryIds = new int[1];
                        categoryIds[0] = -1;
                    }

                    //Get referred clients from a particular service to a specific service
                    LiveData<List<Referral>> referredClients = baseDatabase.referalModel().getReferredClients(serviceID, session.getKeyHfid(), categoryIds);
                    referredClients.observe(ReferedClientsActivity.this, new Observer<List<Referral>>() {
                        @Override
                        public void onChanged(@Nullable List<Referral> referrals) {
                            adapter.addItems(referrals);
                        }
                    });

                }
            });
        }else{
            //Get referred clients from a particular service to a specific service
            LiveData<List<Referral>> referredClients = baseDatabase.referalModel().getReferredClients(serviceID, session.getKeyHfid(), new int[] {serviceCategory});
            referredClients.observe(this, new Observer<List<Referral>>() {
                @Override
                public void onChanged(@Nullable List<Referral> referrals) {
                    adapter.addItems(referrals);
                }
            });
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupview(){

        activityTitle = findViewById(R.id.activity_title);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        filterButton = (Button) findViewById(R.id.filter_button);

        progressView = (ProgressView) findViewById(R.id.progress_bar);
        progressView.setVisibility(View.INVISIBLE);

        fromDateText = (EditText) findViewById(R.id.from_date);
        toDateText = (EditText) findViewById(R.id.to_date);
        clientNameText = (EditText) findViewById(R.id.client_name_et);
        clientCtcNumberText = (EditText) findViewById(R.id.client_ctc_number_et);
        clientLastName = (EditText) findViewById(R.id.client_last_name_et);


        statusSpinner = (MaterialSpinner) findViewById(R.id.spin_status);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        clientsRecycler =  findViewById(R.id.clients_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        clientsRecycler.setLayoutManager(layoutManager);
        clientsRecycler.setHasFixedSize(false);

    }

    private boolean getInputs(){

        if (
                clientNameText.getText().toString().isEmpty() &
                        clientCtcNumberText.getText().toString().isEmpty() &
                        clientLastName.getText().toString().isEmpty() &
                        notSelectedFromDate &
                        notSelectedTodate &
                        notSelectedStatus){
            return false;
        }else {
            clientName = clientNameText.getText().toString().isEmpty() ? "" : clientNameText.getText().toString();
            clientCtcNumber = clientCtcNumberText.getText().toString().isEmpty()? "" : clientCtcNumberText.getText().toString();
            lastName = clientLastName.getText().toString().isEmpty()? "" : clientLastName.getText().toString();
            return true;
        }

    }

    private class QueryReferals extends AsyncTask<Void,Void, Void> {

        String clientName, lastName, ctcNumber;
        AppDatabase db;
        List<Referral> fReferrals;
        int referalStatus;
        Date frmDate, toDate;

        QueryReferals(String name, String lastName, String ctc, Date dateFrom, Date dateTo, int refStatus, AppDatabase database){
            this.clientName = name;
            this.db = database;
            this.referalStatus = refStatus;
            this.frmDate = fromDate;
            this.toDate = dateTo;
            this.lastName = lastName;
            this.ctcNumber = ctc;
            Log.d("", "Status "+refStatus);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.addItems(fReferrals);
            filterButton.setVisibility(View.VISIBLE);
            progressView.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //fReferrals = db.referalModel().getFilteredReferal(clientName, lastName, referalStatus, HIV_SERVICE_ID);
            return null;
        }
    }

}
