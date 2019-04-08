package com.softmed.ccm_facility.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.softmed.ccm_facility.base.AppDatabase;
import com.softmed.ccm_facility.dom.objects.Patient;
import com.softmed.ccm_facility.dom.objects.PatientAppointment;

import java.util.List;

/**
 *  Created by issy on 11/28/17.
 *  @issyzac
 *  issyzac.iz@gmail.com
 *  On Project HFReferal
 */

public class PatientsAppointmentsListViewModel extends AndroidViewModel{
    private AppDatabase appDatabase;

    public PatientsAppointmentsListViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public LiveData<List<PatientAppointment>> getCTCAppointments(long todaysDate, long tommorrowsDate) {
        return appDatabase.appointmentModelDao().getAllCTCAppointments(todaysDate,tommorrowsDate);
    }

    public LiveData<List<PatientAppointment>> getMissedCTCAppointments() {
        return appDatabase.appointmentModelDao().getMissedCTCAppointments();
    }

    public LiveData<List<PatientAppointment>> getTBAppointments(long todaysDate, long tommorrowsDate) {
        return appDatabase.appointmentModelDao().getAllTbAppointments(todaysDate,tommorrowsDate);
    }




    public void deleteItem(Patient patient) {
        new deleteAsyncTask(appDatabase).execute(patient);
    }

    private static class deleteAsyncTask extends AsyncTask<Patient, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Patient... params) {
            db.patientModel().deleteAPatient(params[0]);
            return null;
        }

    }

}
