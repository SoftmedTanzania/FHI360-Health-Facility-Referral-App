package apps.softmed.com.hfreferal.base;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import apps.softmed.com.hfreferal.dom.dao.AppDataModelDao;
import apps.softmed.com.hfreferal.dom.dao.HealthFacilitiesModelDao;
import apps.softmed.com.hfreferal.dom.dao.PatientAppointmentModelDao;
import apps.softmed.com.hfreferal.dom.dao.PatientModelDao;
import apps.softmed.com.hfreferal.dom.dao.PatientNotificationModelDao;
import apps.softmed.com.hfreferal.dom.dao.PatientServicesModelDao;
import apps.softmed.com.hfreferal.dom.dao.PostOfficeModelDao;
import apps.softmed.com.hfreferal.dom.dao.ReferalModelDao;
import apps.softmed.com.hfreferal.dom.dao.TbEncounterModelDao;
import apps.softmed.com.hfreferal.dom.dao.TbPatientModelDao;
import apps.softmed.com.hfreferal.dom.objects.AppData;
import apps.softmed.com.hfreferal.dom.objects.HealthFacilities;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.PatientAppointment;
import apps.softmed.com.hfreferal.dom.objects.HealthFacilityServices;
import apps.softmed.com.hfreferal.dom.objects.PatientsNotification;
import apps.softmed.com.hfreferal.dom.objects.PostOffice;
import apps.softmed.com.hfreferal.dom.objects.Referal;
import apps.softmed.com.hfreferal.dom.objects.TbEncounters;
import apps.softmed.com.hfreferal.dom.objects.TbPatient;

/**
 * Created by issy on 11/28/17.
 */

@Database(
        entities = {
                Patient.class,
                Referal.class,
                PatientsNotification.class,
                PostOffice.class,
                AppData.class,
                TbPatient.class,
                TbEncounters.class,
                PatientAppointment.class,
                HealthFacilityServices.class,
                HealthFacilities.class
        },
        version = 1)

public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "htmr_db")
                            .build();
        }
        return INSTANCE;
    }

    public abstract PatientModelDao patientModel();

    public abstract ReferalModelDao referalModel();

    public abstract PatientNotificationModelDao patientNotificationModelDao();

    public abstract PostOfficeModelDao postOfficeModelDao();

    public abstract AppDataModelDao appDataModelDao();

    public abstract TbPatientModelDao tbPatientModelDao();

    public abstract TbEncounterModelDao tbEncounterModelDao();

    public abstract PatientAppointmentModelDao appointmentModelDao();

    public abstract PatientServicesModelDao servicesModelDao();

    public abstract HealthFacilitiesModelDao healthFacilitiesModelDao();

}
