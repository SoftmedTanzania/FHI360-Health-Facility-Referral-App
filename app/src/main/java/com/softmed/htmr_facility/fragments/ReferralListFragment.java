package com.softmed.htmr_facility.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;
import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.utils.ReferralsSearchFilterUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.softmed.htmr_facility.adapters.ReferalListRecyclerAdapter;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.viewmodels.ReferalListViewModel;
import fr.ganfra.materialspinner.MaterialSpinner;

import static com.softmed.htmr_facility.utils.constants.CHW_TO_FACILITY;
import static com.softmed.htmr_facility.utils.constants.ENGLISH_LOCALE;
import static com.softmed.htmr_facility.utils.constants.HIV_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.INTERFACILITY;
import static com.softmed.htmr_facility.utils.constants.INTRAFACILITY;
import static com.softmed.htmr_facility.utils.constants.LAB_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.OPD_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.STATUS_COMPLETED;
import static com.softmed.htmr_facility.utils.constants.STATUS_NEW;
import static com.softmed.htmr_facility.utils.constants.getReferralStatusValue;

/**
 * Created by issy on 12/10/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ReferralListFragment extends Fragment {

    private ReferalListViewModel listViewModel;

    private Toolbar toolbar;
    private RecyclerView clientRecyclerView, emergencyClientsRecyclerView;
    private MaterialSpinner statusSpinner;
    private EditText fromDateText, toDateText, clientNameText, clientCtcNumberText, clientLastName;
    private TextView ctcNumberOrServiceNameTitle, referralReasonTitle, emptyEmergencyReferrals, otherReferralsTitle, emergencyReferralsTitle;
    private RelativeLayout emergencyReferralsContainer;

    private ReferalListRecyclerAdapter adapter, emergencyAdapter;

    private Date fromDate, toDate;

    //Filter Data
    String clientName, clientCtcNumber, lastName;
    long filterFromDate = 0;
    long filterToDate = 0;
    int[] selectedStatus = new int[]{};
    //Filter Data End

    private int source, service;
    private boolean notSelectedStatus, notSelectedFromDate, notSelectedTodate;

    private DatePickerDialog toDatePicker = new DatePickerDialog();
    private DatePickerDialog fromDatePicker = new DatePickerDialog();

    private ReferralsSearchFilterUtil referralsSearchFilterUtilEmergency;
    private ReferralsSearchFilterUtil referralsSearchFilterUtil;

    private AppDatabase database;

    public static ReferralListFragment newInstance(int source, int service) {
        Bundle args = new Bundle();
        ReferralListFragment fragment = new ReferralListFragment();
        args.putInt("source", source);
        args.putInt("service", service);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabase(ReferralListFragment.this.getActivity());
        fromDatePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        toDatePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));

        referralsSearchFilterUtil = new ReferralsSearchFilterUtil(new ArrayList<>(), this.getContext());
        referralsSearchFilterUtilEmergency = new ReferralsSearchFilterUtil(new ArrayList<>(), this.getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView    = inflater.inflate(R.layout.fragment_referal_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        source = getArguments().getInt("source");
        service = getArguments().getInt("service");
        setupviews(view);

        switch (service){
            case OPD_SERVICE_ID:
                ctcNumberOrServiceNameTitle.setText(getResources().getString(R.string.service));
                referralReasonTitle.setText(getResources().getString(R.string.referral_reasib));
                emergencyReferralsContainer.setVisibility(View.VISIBLE);
                emergencyReferralsTitle.setVisibility(View.VISIBLE);
                otherReferralsTitle.setVisibility(View.VISIBLE);
                break;
            case LAB_SERVICE_ID:
                referralReasonTitle.setText(getResources().getString(R.string.lab_test_type_title));
                ctcNumberOrServiceNameTitle.setText("");
                emergencyReferralsContainer.setVisibility(View.GONE);
                emergencyReferralsTitle.setVisibility(View.GONE);
                otherReferralsTitle.setVisibility(View.GONE);
                break;
            case HIV_SERVICE_ID:
                referralReasonTitle.setText(getResources().getString(R.string.referral_reasib));
                ctcNumberOrServiceNameTitle.setText(getResources().getString(R.string.ctc_number));
                emergencyReferralsContainer.setVisibility(View.GONE);
                emergencyReferralsTitle.setVisibility(View.GONE);
                otherReferralsTitle.setVisibility(View.GONE);
                break;
            default:
                break;
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
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(ReferralListFragment.this.getActivity(), android.R.layout.simple_spinner_item, status);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(spinAdapter);

        notSelectedStatus = true;
        notSelectedFromDate = true;
        notSelectedTodate = true;

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0){
                    selectedStatus = new int[]{getReferralStatusValue((String) statusSpinner.getItemAtPosition(i))};
                }else {
                    notSelectedStatus = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fromDateText.setFocusableInTouchMode(false);
        toDateText.setFocusableInTouchMode(false);

        fromDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Date Picker
                fromDatePicker.show(ReferralListFragment.this.getActivity().getFragmentManager(),"fromDate");
                fromDatePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        fromDateText.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        Calendar fromCalendar = Calendar.getInstance();
                        fromCalendar.set(year, monthOfYear, dayOfMonth);
                        fromDate = fromCalendar.getTime();
                        filterFromDate = fromCalendar.getTimeInMillis();
                        notSelectedFromDate = false;

                        adapter.setReferrals(referralsSearchFilterUtil.searchbyFromDate(filterFromDate));
                        emergencyAdapter.setReferrals(referralsSearchFilterUtilEmergency.searchbyFromDate(filterFromDate));

                    }

                });

            }
        });

        toDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Date Picker
                toDatePicker.show(ReferralListFragment.this.getActivity().getFragmentManager(),"toDate");
                toDatePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        toDateText.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        Calendar toCalendar = Calendar.getInstance();
                        toCalendar.set(year, monthOfYear, dayOfMonth);
                        toDate = toCalendar.getTime();
                        filterToDate = toCalendar.getTimeInMillis();
                        notSelectedTodate = false;

                        adapter.setReferrals(referralsSearchFilterUtil.searchbyToDate(filterFromDate));
                        emergencyAdapter.setReferrals(referralsSearchFilterUtilEmergency.searchbyToDate(filterFromDate));
                    }

                });
            }
        });

        clientNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.setReferrals(referralsSearchFilterUtil.searchByPatientNames(charSequence.toString()));
                emergencyAdapter.setReferrals(referralsSearchFilterUtilEmergency.searchByPatientNames(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        adapter = new ReferalListRecyclerAdapter(new ArrayList<>(), ReferralListFragment.this.getActivity(), service);
        emergencyAdapter = new ReferalListRecyclerAdapter(new ArrayList<>(), ReferralListFragment.this.getActivity(), service);

        clientRecyclerView.setAdapter(adapter);
        emergencyClientsRecyclerView.setAdapter(emergencyAdapter);

        listViewModel = ViewModelProviders.of(this).get(ReferalListViewModel.class);
        if (service == OPD_SERVICE_ID){
            if (source == CHW_TO_FACILITY){
                listViewModel.getAllReferralListFromChw().observe(ReferralListFragment.this, new Observer<List<Referral>>() {
                    @Override
                    public void onChanged(@Nullable List<Referral> referrals) {

                        List<Referral> emergencyReferrals = new ArrayList<>();
                        List<Referral> otherReferrals = new ArrayList<>();

                        for (Referral r : referrals){
                            if (r.isEmergency())
                                emergencyReferrals.add(r);
                            else
                                otherReferrals.add(r);
                        }

                        referralsSearchFilterUtil.setReferrals(otherReferrals);
                        referralsSearchFilterUtil.getEquivalentPatients();

                        referralsSearchFilterUtilEmergency.setReferrals(emergencyReferrals);
                        referralsSearchFilterUtilEmergency.getEquivalentPatients();

                        if (emergencyReferrals.size() > 0){
                            emptyEmergencyReferrals.setVisibility(View.GONE);
                            emergencyClientsRecyclerView.setVisibility(View.VISIBLE);
                        }else {
                            emptyEmergencyReferrals.setVisibility(View.VISIBLE);
                            emergencyClientsRecyclerView.setVisibility(View.GONE);
                        }

                        adapter.setReferrals(otherReferrals);
                        emergencyAdapter.setReferrals(emergencyReferrals);
                    }
                });
            }else{

                //Facility Referrals do not have emergency tag
                emergencyReferralsContainer.setVisibility(View.GONE);
                emergencyReferralsTitle.setVisibility(View.GONE);
                otherReferralsTitle.setVisibility(View.GONE);

                listViewModel.getAllReferralListFromHealthFacilities().observe(ReferralListFragment.this, new Observer<List<Referral>>() {
                    @Override
                    public void onChanged(@Nullable List<Referral> referrals) {
                        referralsSearchFilterUtil.setReferrals(referrals);
                        referralsSearchFilterUtil.getEquivalentPatients();
                        adapter.addItems(referrals);
                    }
                });

            }

        } else if (service == LAB_SERVICE_ID){ //If Service is Lab, list all lab referrals originated from either interfacility or intrafacility
            listViewModel.getlabReferalListHfSource().observe(ReferralListFragment.this, new Observer<List<Referral>>() {
                @Override
                public void onChanged(@Nullable List<Referral> referrals) {
                    referralsSearchFilterUtil.setReferrals(referrals);
                    referralsSearchFilterUtil.getEquivalentPatients();
                    adapter.addItems(referrals);
                }
            });
        } else { //HIV Referrals
            if (source == CHW_TO_FACILITY){
                listViewModel.getReferalListChwSource().observe(ReferralListFragment.this, new Observer<List<Referral>>() {
                    @Override
                    public void onChanged(@Nullable List<Referral> referrals) {
                        referralsSearchFilterUtil.setReferrals(referrals);
                        referralsSearchFilterUtil.getEquivalentPatients();
                        adapter.addItems(referrals);
                    }
                });
            }else{
                listViewModel.getReferalListHfSource().observe(ReferralListFragment.this, new Observer<List<Referral>>() {
                    @Override
                    public void onChanged(@Nullable List<Referral> referrals) {
                        referralsSearchFilterUtil.setReferrals(referrals);
                        referralsSearchFilterUtil.getEquivalentPatients();
                        adapter.addItems(referrals);
                    }
                });
            }
        }

    }

    private boolean getInputs(){
        if (statusSpinner.getSelectedItemPosition() == 0){
            selectedStatus = new int[]{0,1};
        }
        clientName = clientNameText.getText().toString().isEmpty() ? "" : clientNameText.getText().toString();
        clientCtcNumber = clientCtcNumberText.getText().toString().isEmpty()? "" : clientCtcNumberText.getText().toString();
        lastName = clientLastName.getText().toString().isEmpty()? "" : clientLastName.getText().toString();
        return true;
    }

    private class QueryReferals extends AsyncTask<Void,Void, Void> {

        String clientName, lastName, ctcNumber;
        AppDatabase db;
        List<Referral> fReferrals;
        int[] referalStatus;
        long frmDate, toDate;

        QueryReferals(String name, String lastName, String ctc, long dateFrom, long dateTo, int[] refStatus, AppDatabase database){
            this.clientName = name;
            this.db = database;
            this.referalStatus = refStatus;
            this.frmDate = dateFrom;
            this.toDate = dateTo;
            this.lastName = lastName;
            this.ctcNumber = ctc;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.addItems(fReferrals);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (service == OPD_SERVICE_ID){
                if (source == CHW_TO_FACILITY){
                    fReferrals = db.referalModel().getFilteredOpdReferrals(selectedStatus, clientName, new int[] {CHW_TO_FACILITY});
                }else {
                    //fReferrals = db.referalModel().getFilteredOpdReferrals(selectedStatus, clientName, lastName, ctcNumber, frmDate, toDate, new int[] {INTERFACILITY, INTRAFACILITY});
                    fReferrals = db.referalModel().getFilteredOpdReferrals(selectedStatus, clientName, new int[] {INTERFACILITY, INTRAFACILITY});
                }
            }else {
                if (source == CHW_TO_FACILITY){
                    fReferrals = db.referalModel().getFilteredHivAndTbReferrals(selectedStatus, clientName, lastName, ctcNumber, frmDate, toDate, new int[] {CHW_TO_FACILITY}, HIV_SERVICE_ID);
                }else {
                    fReferrals = db.referalModel().getFilteredHivAndTbReferrals(selectedStatus, clientName, lastName, ctcNumber, frmDate, toDate, new int[] {INTERFACILITY, INTRAFACILITY}, HIV_SERVICE_ID);
                }
            }

            return null;
        }
    }

    private void setupviews(View v){

        emergencyReferralsContainer = v.findViewById(R.id.emergency_referrals_container);
        emergencyReferralsTitle  = v.findViewById(R.id.emergency_referrals_title);
        otherReferralsTitle = v.findViewById(R.id.other_referrals_title);
        referralReasonTitle = v.findViewById(R.id.referral_reasons);
        ctcNumberOrServiceNameTitle =  v.findViewById(R.id.ctc_number);
        emptyEmergencyReferrals = v.findViewById(R.id.empty_emergency_referrals);
        fromDateText =  v.findViewById(R.id.from_date);
        toDateText =  v.findViewById(R.id.to_date);
        clientNameText =  v.findViewById(R.id.client_name_et);
        clientCtcNumberText =  v.findViewById(R.id.client_ctc_number_et);
        clientLastName =  v.findViewById(R.id.client_last_name_et);
        statusSpinner =  v.findViewById(R.id.spin_status);
        toolbar =  v.findViewById(R.id.toolbar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReferralListFragment.this.getActivity());
        clientRecyclerView =  v.findViewById(R.id.clients_recycler);
        clientRecyclerView.setLayoutManager(layoutManager);
        clientRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager emergencyLayoutManager = new LinearLayoutManager(ReferralListFragment.this.getActivity());
        emergencyClientsRecyclerView = v.findViewById(R.id.emergency_clients_recycler);
        emergencyClientsRecyclerView.setLayoutManager(emergencyLayoutManager);
        emergencyClientsRecyclerView.setHasFixedSize(false);

    }

}
