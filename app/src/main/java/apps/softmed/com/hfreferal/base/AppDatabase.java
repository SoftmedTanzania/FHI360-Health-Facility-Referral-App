package apps.softmed.com.hfreferal.base;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import apps.softmed.com.hfreferal.dom.dao.AppDataModelDao;
import apps.softmed.com.hfreferal.dom.dao.PatientModelDao;
import apps.softmed.com.hfreferal.dom.dao.PatientNotificationModelDao;
import apps.softmed.com.hfreferal.dom.dao.PostOfficeModelDao;
import apps.softmed.com.hfreferal.dom.dao.ReferalModelDao;
import apps.softmed.com.hfreferal.dom.objects.AppData;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.PatientsNotification;
import apps.softmed.com.hfreferal.dom.objects.PostOffice;
import apps.softmed.com.hfreferal.dom.objects.Referal;

/**
 * Created by issy on 11/28/17.
 */

@Database(entities = {Patient.class, Referal.class, PatientsNotification.class, PostOffice.class, AppData.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "hfreferal_db")
                            .build();
        }
        return INSTANCE;
    }

    public abstract PatientModelDao patientModel();

    public abstract ReferalModelDao referalModel();

    public abstract PatientNotificationModelDao patientNotificationModelDao();

    public abstract PostOfficeModelDao postOfficeModelDao();

    public abstract AppDataModelDao appDataModelDao();

}
