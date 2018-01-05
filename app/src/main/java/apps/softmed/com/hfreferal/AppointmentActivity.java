package apps.softmed.com.hfreferal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import apps.softmed.com.hfreferal.adapters.AppointmentRecyclerAdapter;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.dom.objects.PatientAppointment;
import fr.ganfra.materialspinner.MaterialSpinner;

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
    private MaterialSpinner statusSpinner;

    private AppointmentRecyclerAdapter adapter;
    private List<PatientAppointment> appointments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        setupviews();

        if (toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Patient Appointment");
        }

        adapter = new AppointmentRecyclerAdapter(appointments,this,baseDatabase);
        appointmentRecycler.setAdapter(adapter);

        new GetAppointmentList().execute();

    }

    private void setupviews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appointmentRecycler = (RecyclerView) findViewById(R.id.appointment_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        appointmentRecycler.setLayoutManager(layoutManager);
        appointmentRecycler.setHasFixedSize(true);

        statusSpinner = (MaterialSpinner) findViewById(R.id.spin_status);
        patientName = (EditText) findViewById(R.id.client_name_et);
        fromDate = (EditText) findViewById(R.id.from_date_et);
        toDate = (EditText) findViewById(R.id.to_date_et);

    }

    class GetAppointmentList extends AsyncTask<Void, Void, Void>{

        List<PatientAppointment> list;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.addItems(list);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            list = baseDatabase.appointmentModelDao().getAllAppointments();
            return null;
        }

    }

}
