package apps.softmed.com.hfreferal.fragments;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.adapters.ReferalListRecyclerAdapter;
import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.dom.objects.Client;
import apps.softmed.com.hfreferal.dom.objects.Referal;
import apps.softmed.com.hfreferal.viewmodels.ReferalListViewModel;
import fr.ganfra.materialspinner.MaterialSpinner;

import static apps.softmed.com.hfreferal.utils.constants.HIV_SERVICE_ID;
import static apps.softmed.com.hfreferal.utils.constants.SOURCE_CHW;
import static apps.softmed.com.hfreferal.utils.constants.SOURCE_HF;
import static apps.softmed.com.hfreferal.utils.constants.STATUS_COMPLETED;
import static apps.softmed.com.hfreferal.utils.constants.STATUS_NEW;
import static apps.softmed.com.hfreferal.utils.constants.getReferralStatusValue;

/**
 * Created by issy on 12/10/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ReferralListFragment extends Fragment {

    private ReferalListViewModel listViewModel;

    private Toolbar toolbar;
    private RecyclerView clientRecyclerView, oldClientsRecycler;
    private MaterialSpinner statusSpinner;
    private EditText fromDateText, toDateText, clientNameText, clientCtcNumberText, clientLastName;
    private ProgressView progressView;
    private Button filterButton;

    private List<Client> mClientList = new ArrayList<>();
    private List<Client> mOldClientList = new ArrayList<>();

    private ReferalListRecyclerAdapter adapter;

    private Date fromDate, toDate;
    private String clientName, clientCtcNumber, lastName;
    private int selectedStatus, source;
    private boolean notSelectedStatus, notSelectedFromDate, notSelectedTodate;

    private DatePickerDialog toDatePicker = new DatePickerDialog();
    private DatePickerDialog fromDatePicker = new DatePickerDialog();

    private AppDatabase database;

    public static ReferralListFragment newInstance(int source) {
        Bundle args = new Bundle();
        ReferralListFragment fragment = new ReferralListFragment();
        args.putInt("source", source);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabase(ReferralListFragment.this.getActivity());
        fromDatePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        toDatePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
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
        setupviews(view);

        final String[] status = {STATUS_COMPLETED, STATUS_NEW};
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

                    ReferralListFragment.QueryReferals queryReferals = new ReferralListFragment.QueryReferals(clientName, lastName, clientCtcNumber, fromDate, toDate, selectedStatus, database);
                    queryReferals.execute();

                }else {
                    Toast.makeText(ReferralListFragment.this.getActivity(),"Please Fill in any field ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fromDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Date Picker
                fromDatePicker.show(ReferralListFragment.this.getActivity().getFragmentManager(),"fromDate");
                fromDatePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        /*
                        Cursor vacinationCursor = mydb.getReadableDatabase().rawQuery("SELECT COUNT(*) FROM " + SQLHandler.Tables.VACCINATION_EVENT +
                                        " where " + SQLHandler.VaccinationEventColumns.CHILD_ID + "=? and " +
                                        SQLHandler.VaccinationEventColumns.VACCINATION_STATUS + "= 'true'",
                                new String[]{currentChild.getId()});
                        vacinationCursor.moveToFirst();
                        */

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
                toDatePicker.show(ReferralListFragment.this.getActivity().getFragmentManager(),"toDate");
                toDatePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        /*
                        Cursor vacinationCursor = mydb.getReadableDatabase().rawQuery("SELECT COUNT(*) FROM " + SQLHandler.Tables.VACCINATION_EVENT +
                                        " where " + SQLHandler.VaccinationEventColumns.CHILD_ID + "=? and " +
                                        SQLHandler.VaccinationEventColumns.VACCINATION_STATUS + "= 'true'",
                                new String[]{currentChild.getId()});
                        vacinationCursor.moveToFirst();
                        */

                        toDateText.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        Calendar toCalendar = Calendar.getInstance();
                        toCalendar.set(year, monthOfYear, dayOfMonth);
                        toDate = toCalendar.getTime();
                        notSelectedTodate = false;
                    }

                });
            }
        });


        adapter = new ReferalListRecyclerAdapter(new ArrayList<Referal>(), ReferralListFragment.this.getActivity());
        listViewModel = ViewModelProviders.of(this).get(ReferalListViewModel.class);

        if (source == SOURCE_HF){
            listViewModel.getReferalListHfSource().observe(ReferralListFragment.this, new Observer<List<Referal>>() {
                @Override
                public void onChanged(@Nullable List<Referal> referals) {
                    adapter.addItems(referals);
                }
            });
        }
        else if (source == SOURCE_CHW){
            listViewModel.getReferalListChwSource().observe(ReferralListFragment.this, new Observer<List<Referal>>() {
                @Override
                public void onChanged(@Nullable List<Referal> referals) {
                    adapter.addItems(referals);
                }
            });
        }

        clientRecyclerView.setAdapter(adapter);
        //getReferalClientsList();

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
        List<Referal> fReferrals;
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
            fReferrals = db.referalModel().getFilteredReferal(clientName, lastName, referalStatus, HIV_SERVICE_ID);
            return null;
        }
    }


    private void setupviews(View v){

        filterButton = (Button) v.findViewById(R.id.filter_button);

        progressView = (ProgressView) v.findViewById(R.id.progress_bar);
        progressView.setVisibility(View.INVISIBLE);

        fromDateText = (EditText) v.findViewById(R.id.from_date);
        toDateText = (EditText) v.findViewById(R.id.to_date);
        clientNameText = (EditText) v.findViewById(R.id.client_name_et);
        clientCtcNumberText = (EditText) v.findViewById(R.id.client_ctc_number_et);
        clientLastName = (EditText) v.findViewById(R.id.client_last_name_et);

        statusSpinner = (MaterialSpinner) v.findViewById(R.id.spin_status);

        toolbar = (Toolbar) v.findViewById(R.id.toolbar);

        clientRecyclerView = (RecyclerView) v.findViewById(R.id.clients_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReferralListFragment.this.getActivity());
        clientRecyclerView.setLayoutManager(layoutManager);
        clientRecyclerView.setHasFixedSize(true);

    }

}
