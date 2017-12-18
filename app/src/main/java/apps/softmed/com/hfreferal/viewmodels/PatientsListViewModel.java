package apps.softmed.com.hfreferal.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.dom.objects.Patient;

/**
 *  Created by issy on 11/28/17.
 *  @issyzac
 *  issyzac.iz@gmail.com
 *  On Project HFReferal
 */

public class PatientsListViewModel extends AndroidViewModel{

    private final LiveData<List<Patient>> patientsList;

    private AppDatabase appDatabase;

    public PatientsListViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        patientsList = appDatabase.patientModel().getAllPatients();
    }

    public LiveData<List<Patient>> getPatientsList() {
        return patientsList;
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
