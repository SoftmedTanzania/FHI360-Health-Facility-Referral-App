package com.softmed.htmr_facility.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.adapters.HealthFacilitiesAdapter;
import com.softmed.htmr_facility.adapters.ServicesAdapter;
import com.softmed.htmr_facility.api.Endpoints;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.HealthFacilities;
import com.softmed.htmr_facility.dom.objects.Patient;
import com.softmed.htmr_facility.dom.objects.PostOffice;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.dom.objects.ReferralIndicator;
import com.softmed.htmr_facility.dom.objects.ReferralServiceIndicators;
import com.softmed.htmr_facility.utils.ServiceGenerator;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softmed.htmr_facility.base.BaseActivity.session;
import static com.softmed.htmr_facility.utils.SessionManager.KEY_UUID;
import static com.softmed.htmr_facility.utils.constants.ENTRY_NOT_SYNCED;
import static com.softmed.htmr_facility.utils.constants.FACILITY_TO_CHW;
import static com.softmed.htmr_facility.utils.constants.HIV_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.INTERFACILITY;
import static com.softmed.htmr_facility.utils.constants.INTRAFACILITY;
import static com.softmed.htmr_facility.utils.constants.MALARIA_SERVICE_ID;
import static com.softmed.htmr_facility.utils.constants.POST_DATA_TYPE_REFERRAL;
import static com.softmed.htmr_facility.utils.constants.REFERRAL_STATUS_NEW;
import static com.softmed.htmr_facility.utils.constants.TB_SERVICE_ID;

/**
 * Created by issy on 1/6/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class IssueReferralDialogueFragment extends DialogFragment{

    private TextView patientNames, takeHivTest, takeMalariaTest, takeTBTest, saveButtonText;
    private MaterialSpinner spinnerService, spinnerToHealthFacility;
    private EditText referralReasons, otherClinicalInformation;
    private Button cancelButton;
    private RelativeLayout issueButton;
    private RecyclerView indicatorsRecycler;
    private ToggleSwitch referralToToggle;
    private LinearLayout serviceAndFacilityWrap, indicatorsWrap, labTestsWrap;
    private View indicatorSeparator;
    private CircularProgressView circularProgressView;

    private Patient currentPatient;
    private String referralReasonsValue, otherClinicalInformationValue, toHealthFacilityID;
    private int serviceID;
    private static final String TAG = "IssueReferralDialogueFragment";
    private List<ReferralIndicator> selectedIndicators = new ArrayList<>();

    private AppDatabase database;

    private HealthFacilitiesAdapter healthFacilitiesAdapter;
    List<HealthFacilities> healthFacilities = new ArrayList<>();

    private mAdapter madapter;
    List<String> destinations = new ArrayList<>();

    private ServicesAdapter servicesAdapter;
    private int sourceService;
    List<ReferralServiceIndicators> referralServiceIndicators = new ArrayList<>();

    private int referralType;
    private String forwardUUID;
    private int TEST_TO_TAKE = 0;

    private Endpoints.ReferalService referalService;

    public IssueReferralDialogueFragment() {}

    public static IssueReferralDialogueFragment newInstance(Patient patient, int sourceServiceId, String forwardedUUID) {
        Bundle args = new Bundle();
        IssueReferralDialogueFragment fragment = new IssueReferralDialogueFragment();
        args.putSerializable("currentPatient", patient);
        args.putInt("sourceID", sourceServiceId);
        args.putString("forwardUUID", forwardedUUID+"");
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        referalService = ServiceGenerator.createService(Endpoints.ReferalService.class,
                session.getUserName(),
                session.getUserPass(),
                session.getKeyHfid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialogue_layout, container);
        database = AppDatabase.getDatabase(this.getContext());
        this.getDialog().setCanceledOnTouchOutside(false);
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //this.getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        currentPatient = (Patient) getArguments().getSerializable("currentPatient");
        sourceService = getArguments().getInt("sourceID");
        forwardUUID = getArguments().getString("forwardUUID");

        setupviews(view);

        if (currentPatient != null){
            patientNames.setText(
                    currentPatient.getPatientFirstName()+" "+
                    currentPatient.getPatientMiddleName()+" "+
                    currentPatient.getPatientSurname());
        }

        healthFacilitiesAdapter = new HealthFacilitiesAdapter(this.getContext(),R.layout.subscription_plan_items_drop_down, healthFacilities);
        servicesAdapter = new ServicesAdapter(this.getContext(), R.layout.subscription_plan_items_drop_down , referralServiceIndicators);

        new getFacilitiesAndServices(this.getContext()).execute();

        takeHivTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TEST_TO_TAKE = HIV_SERVICE_ID;
                takeHivTest.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                takeMalariaTest.setBackground(getResources().getDrawable(R.drawable.border_indicators_unselected));
                takeTBTest.setBackground(getResources().getDrawable(R.drawable.border_indicators_unselected));
            }
        });

        takeTBTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TEST_TO_TAKE = TB_SERVICE_ID;
                takeTBTest.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                takeMalariaTest.setBackground(getResources().getDrawable(R.drawable.border_indicators_unselected));
                takeHivTest.setBackground(getResources().getDrawable(R.drawable.border_indicators_unselected));
            }
        });

        takeMalariaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TEST_TO_TAKE = MALARIA_SERVICE_ID;
                takeMalariaTest.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                takeTBTest.setBackground(getResources().getDrawable(R.drawable.border_indicators_unselected));
                takeHivTest.setBackground(getResources().getDrawable(R.drawable.border_indicators_unselected));
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        issueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circularProgressView.setVisibility(View.VISIBLE);
                saveButtonText.setVisibility(View.GONE);
                if (getCurrentInputs()){
                    issueReferral();
                }else {
                    circularProgressView.setVisibility(View.GONE);
                    saveButtonText.setVisibility(View.VISIBLE);
                }
            }
        });

        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*TODO
                * Get the selected sourceService
                * Query all the indicators associated with that sourceService
                * display indicators
                * */

                if (i != -1){

                    ReferralServiceIndicators serviceIndicator = (ReferralServiceIndicators) adapterView.getSelectedItem();
                    if (serviceIndicator.getServiceId() == 11){
                        //If lab service, display the test selection
                        labTestsWrap.setVisibility(View.VISIBLE);
                    }else {
                        labTestsWrap.setVisibility(View.GONE);
                    }

                    selectedIndicators.clear();
                    ReferralServiceIndicators service = (ReferralServiceIndicators) adapterView.getSelectedItem();
                    new getServiceIndicator(database).execute(service.getServiceId());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerToHealthFacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("IssueReferral", i+" : to health Facility");
                HealthFacilities hf = (HealthFacilities)spinnerToHealthFacility.getSelectedItem();
                if (i!=-1){
                    if (hf.getOpenMRSUIID().equals(BaseActivity.getThisFacilityId())){
                        referralType = INTRAFACILITY;
                    }else {
                        toHealthFacilityID = hf.getOpenMRSUIID();
                        referralType = INTERFACILITY;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        referralToToggle.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener(){

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                Log.d("sia", position+" "+isChecked);
                switch (position){
                    case 0:
                        //Inter-facility|intra-facility Referral
                        serviceAndFacilityWrap.setVisibility(View.VISIBLE);
                        indicatorSeparator.setVisibility(View.VISIBLE);
                        referralType = INTERFACILITY;
                        break;
                    case 1:
                        //Facility to CHW referrals
                        serviceAndFacilityWrap.setVisibility(View.GONE);
                        indicatorSeparator.setVisibility(View.GONE);
                        referralType = FACILITY_TO_CHW;
                        break;
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void issueReferral(){

        Referral referral = new Referral();
        List<Long> indicatorIDs = new ArrayList<>();

        long range = 1234567L;
        Random r = new Random();
        long number = (long)(r.nextDouble()*range);

        long today = Calendar.getInstance().getTimeInMillis();

        referral.setReferral_id(number+"");
        referral.setId(number);
        referral.setPatient_id(currentPatient.getPatientId());
        referral.setCommunityBasedHivService("");
        referral.setReferralReason(referralReasonsValue);
        referral.setServiceId(serviceID);
        referral.setReferralUUID(forwardUUID);
        referral.setCtcNumber("");
        referral.setServiceProviderUIID(session.getUserDetails().get(KEY_UUID));
        referral.setServiceProviderGroup("");
        referral.setVillageLeader("");

        referral.setReferralSource(sourceService);
        referral.setReferralType(referralType);

        referral.setReferralDate(today);
        referral.setFacilityId(toHealthFacilityID);
        referral.setFromFacilityId(session.getKeyHfid());
        referral.setReferralStatus(REFERRAL_STATUS_NEW);
        referral.setOtherClinicalInformation(otherClinicalInformationValue);

        referral.setLabTest(TEST_TO_TAKE);

        for (ReferralIndicator indicator : selectedIndicators){
            indicatorIDs.add(indicator.getReferralServiceIndicatorId());
        }

        //Call online issuing of referral and then store locally
        Call<Referral> issueReferral = referalService.postReferral(session.getServiceProviderUUID(), BaseActivity.getReferralRequestBody(referral));
        issueReferral.enqueue(new Callback<Referral>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call<Referral> call, Response<Referral> response) {
                try {
                    Referral receivedReferral = response.body();
                    new AsyncTask<Referral, Void, Void>(){
                        @Override
                        protected Void doInBackground(Referral... referrals) {
                            try {
                                database.referalModel().addReferal(referrals[0]);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            circularProgressView.setVisibility(View.GONE);
                            saveButtonText.setVisibility(View.VISIBLE);
                            toastThis("Referral Stored Successfully");
                            dismiss();
                        }
                    }.execute(receivedReferral);

                }catch (NullPointerException e){
                    e.printStackTrace();

                    //Returned Null from server
                    new AsyncTask<Referral, Void, Void>(){
                        @Override
                        protected Void doInBackground(Referral... args) {
                            //Save referral in local DB
                            PostOffice postOffice = new PostOffice();
                            postOffice.setPost_id(args[0].getReferral_id());
                            postOffice.setPost_data_type(POST_DATA_TYPE_REFERRAL);
                            postOffice.setSyncStatus(ENTRY_NOT_SYNCED);

                            database.postOfficeModelDao().addPostEntry(postOffice);

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            circularProgressView.setVisibility(View.GONE);
                            saveButtonText.setVisibility(View.VISIBLE);
                            toastThis("Referral needs to be synced manually");
                            dismiss();
                        }
                    }.execute(referral);
                }
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onFailure(Call<Referral> call, Throwable t) {
                //Sending referral to server failed, store locally and Add a postOffice data
                new AsyncTask<Referral, Void, Void>(){
                    @Override
                    protected Void doInBackground(Referral... args) {
                        //Save referral in local DB
                        PostOffice postOffice = new PostOffice();
                        postOffice.setPost_id(args[0].getReferral_id());
                        postOffice.setPost_data_type(POST_DATA_TYPE_REFERRAL);
                        postOffice.setSyncStatus(ENTRY_NOT_SYNCED);

                        database.postOfficeModelDao().addPostEntry(postOffice);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        circularProgressView.setVisibility(View.GONE);
                        saveButtonText.setVisibility(View.VISIBLE);
                        toastThis("Referral needs to be synced manually");
                        dismiss();
                    }
                }.execute(referral);
            }
        });

    }

    private boolean getCurrentInputs(){
        if (referralType == FACILITY_TO_CHW){
            serviceID = -1;
        }else {
            if (spinnerService.getSelectedItemPosition() == 0){
                toastThis("Chagua huduma ya kutoa rufaa");
                return false;
            }else {
                ReferralServiceIndicators services = (ReferralServiceIndicators) spinnerService.getSelectedItem();
                serviceID = Integer.parseInt(services.getServiceId()+"");
            }
        }



        referralReasonsValue = referralReasons.getText().toString().equals("") ? "N/A" : referralReasons.getText().toString();
        otherClinicalInformationValue = otherClinicalInformation.getText().toString().equals("") ? "N/A" : otherClinicalInformation.getText().toString();

        return true;

    }

    private void toastThis(String message){
        Toast.makeText(IssueReferralDialogueFragment.this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setupviews(View v) {

        labTestsWrap = v.findViewById(R.id.lab_test_wrap);
        labTestsWrap.setVisibility(View.GONE);

        indicatorsRecycler = (RecyclerView) v.findViewById(R.id.indicators_recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(IssueReferralDialogueFragment.this.getContext(), 3);
        indicatorsRecycler.setLayoutManager(layoutManager);
        indicatorsRecycler.setHasFixedSize(true);

        patientNames =  v.findViewById(R.id.patient_name);

        spinnerService =  v.findViewById(R.id.spin_service);
        spinnerToHealthFacility =  v.findViewById(R.id.spin_to_facility);

        referralReasons =  v.findViewById(R.id.referal_reasons_text);
        otherClinicalInformation =  v.findViewById(R.id.other_clinical_information_text);

        cancelButton =  v.findViewById(R.id.cancel_button);
        issueButton =  v.findViewById(R.id.tuma_button);
        saveButtonText = v.findViewById(R.id.save_button_text);

        circularProgressView = v.findViewById(R.id.progress_view);
        circularProgressView.setVisibility(View.GONE);
        saveButtonText.setVisibility(View.VISIBLE);

        referralToToggle = (ToggleSwitch) v.findViewById(R.id.referral_to_toggle);

        serviceAndFacilityWrap = (LinearLayout) v.findViewById(R.id.service_and_facility_wrap);
        indicatorsWrap = (LinearLayout) v.findViewById(R.id.indicators_wrapper);
        indicatorsWrap.setVisibility(View.GONE);

        takeHivTest = v.findViewById(R.id.hiv_test);
        takeMalariaTest = v.findViewById(R.id.malaria_test);
        takeTBTest = v.findViewById(R.id.tb_test);

        indicatorSeparator = (View) v.findViewById(R.id.indicators_separator);

    }

    class IndicatorsRecyclerAdapter  extends RecyclerView.Adapter<IndicatorsViewHolder> {

        private List<ReferralIndicator> indicators = new ArrayList<>();
        private LayoutInflater mInflater;

        // data is passed into the constructor
        public IndicatorsRecyclerAdapter(Context context, List<ReferralIndicator> items) {
            this.mInflater = LayoutInflater.from(context);
            this.indicators = items;
        }

        // inflates the cell layout from xml when needed
        @Override
        public IssueReferralDialogueFragment.IndicatorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.indicator_grid_item, parent, false);
            IndicatorsViewHolder holder = new IndicatorsViewHolder(view);
            return holder;
        }

        // binds the data to the textview in each cell
        @Override
        public void onBindViewHolder(IndicatorsViewHolder holder, int position) {
            ReferralIndicator indicator = indicators.get(position);
            holder.bindIndicator(indicator);
        }

        // total number of cells
        @Override
        public int getItemCount() {
            return indicators.size();
        }

        // convenience method for getting data at click position
        ReferralIndicator getItem(int id) {
            return indicators.get(id);
        }

    }

    class IndicatorsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView indicatorName;
        private RelativeLayout indicatorWrapper;
        private ReferralIndicator referralIndicator;
        private boolean isSelected = false;

        private IndicatorsViewHolder(View itemView) {
            super(itemView);
            indicatorName = (TextView) itemView.findViewById(R.id.indicator_name);
            indicatorWrapper = (RelativeLayout) itemView.findViewById(R.id.indicator_wrapper);
            itemView.setOnClickListener(this);
        }

        private void bindIndicator(ReferralIndicator indicator){
            this.referralIndicator = indicator;
            indicatorName.setText(referralIndicator.getIndicatorName());
        }

        @Override
        public void onClick(View view) {
            if (this.isSelected){
                isSelected = false;
                selectedIndicators.remove(referralIndicator);
                indicatorWrapper.setBackground(getResources().getDrawable(R.drawable.border_indicators_unselected));
            }else {
                isSelected = true;
                selectedIndicators.add(referralIndicator);
                indicatorWrapper.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }

    }

    class getServiceIndicator extends AsyncTask<Long, Void, Void>{

        AppDatabase database;
        List<ReferralIndicator> referralIndicators;

        getServiceIndicator(AppDatabase db){
            database = db;
        }

        @Override
        protected Void doInBackground(Long... longs) {
            long serviceId = longs[0];
            List<ReferralIndicator> indicators = database.referralIndicatorDao().getIndicatorsByServiceId(serviceId);
            referralIndicators = indicators;
            //TODO: display the indicators
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            IndicatorsRecyclerAdapter adapter = new IndicatorsRecyclerAdapter(getContext(), referralIndicators);
            indicatorsRecycler.setAdapter(adapter);
            if (referralIndicators.size() <= 0){
                //indicatorsWrap.setVisibility(View.GONE);
                indicatorSeparator.setVisibility(View.GONE);
            }else {
                //indicatorsWrap.setVisibility(View.VISIBLE);
                indicatorSeparator.setVisibility(View.VISIBLE);
            }
        }
    }

    class getFacilitiesAndServices extends AsyncTask<Void, Void, Void>{

        List<HealthFacilities> hfs = new ArrayList<>();
        List<ReferralServiceIndicators> serviceIndicators = new ArrayList<>();
        Context context;

        getFacilitiesAndServices(Context ctx){
            this.context = ctx;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            healthFacilitiesAdapter = new HealthFacilitiesAdapter(context,R.layout.subscription_plan_items_drop_down, hfs);
            servicesAdapter = new ServicesAdapter(context, R.layout.subscription_plan_items_drop_down ,serviceIndicators);
            spinnerService.setAdapter(servicesAdapter);
            spinnerToHealthFacility.setAdapter(healthFacilitiesAdapter);
            for (int i = 0; i<hfs.size(); i++){
                if (BaseActivity.getThisFacilityId().equals(hfs.get(i).getOpenMRSUIID())){
                    spinnerToHealthFacility.setSelection(i+1);
                }
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {

            hfs = database.healthFacilitiesModelDao().getAllHealthFacilities();
            serviceIndicators = database.referralServiceIndicatorsDao().getAllServices();

            return null;
        }
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
