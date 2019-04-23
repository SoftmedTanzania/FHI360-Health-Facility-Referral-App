package com.softmed.htmr_facility_staging.base;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.softmed.htmr_facility_staging.dom.dao.AppDataModelDao;
import com.softmed.htmr_facility_staging.dom.dao.HealthFacilitiesModelDao;
import com.softmed.htmr_facility_staging.dom.dao.LoggedInSessionsModelDao;
import com.softmed.htmr_facility_staging.dom.dao.PatientAppointmentModelDao;
import com.softmed.htmr_facility_staging.dom.dao.PatientModelDao;
import com.softmed.htmr_facility_staging.dom.dao.PatientNotificationModelDao;
import com.softmed.htmr_facility_staging.dom.dao.PatientServicesModelDao;
import com.softmed.htmr_facility_staging.dom.dao.PostOfficeModelDao;
import com.softmed.htmr_facility_staging.dom.dao.ReferalModelDao;
import com.softmed.htmr_facility_staging.dom.dao.ReferralFeedbackModelDao;
import com.softmed.htmr_facility_staging.dom.dao.ReferralIndicatorDao;
import com.softmed.htmr_facility_staging.dom.dao.ReferralServiceIndicatorsDao;
import com.softmed.htmr_facility_staging.dom.dao.TbEncounterModelDao;
import com.softmed.htmr_facility_staging.dom.dao.TbPatientModelDao;
import com.softmed.htmr_facility_staging.dom.dao.UserDataModelDao;
import com.softmed.htmr_facility_staging.dom.objects.AppData;
import com.softmed.htmr_facility_staging.dom.objects.HealthFacilities;
import com.softmed.htmr_facility_staging.dom.objects.LoggedInSessions;
import com.softmed.htmr_facility_staging.dom.objects.Patient;
import com.softmed.htmr_facility_staging.dom.objects.PatientAppointment;
import com.softmed.htmr_facility_staging.dom.objects.HealthFacilityServices;
import com.softmed.htmr_facility_staging.dom.objects.PatientsNotification;
import com.softmed.htmr_facility_staging.dom.objects.PostOffice;
import com.softmed.htmr_facility_staging.dom.objects.Referral;
import com.softmed.htmr_facility_staging.dom.objects.ReferralFeedback;
import com.softmed.htmr_facility_staging.dom.objects.ReferralIndicator;
import com.softmed.htmr_facility_staging.dom.objects.ReferralServiceIndicators;
import com.softmed.htmr_facility_staging.dom.objects.TbEncounters;
import com.softmed.htmr_facility_staging.dom.objects.TbPatient;
import com.softmed.htmr_facility_staging.dom.objects.FacilityChws;
import com.softmed.htmr_facility_staging.dom.objects.UserData;

/**
 * Created by issy on 11/28/17.
 */

@Database(
        entities = {
                Patient.class,
                Referral.class,
                PatientsNotification.class,
                PostOffice.class,
                AppData.class,
                TbPatient.class,
                TbEncounters.class,
                PatientAppointment.class,
                HealthFacilityServices.class,
                HealthFacilities.class,
                UserData.class,
                ReferralIndicator.class,
                ReferralServiceIndicators.class,
                LoggedInSessions.class,
                FacilityChws.class,
                ReferralFeedback.class
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

    public abstract UserDataModelDao userDataModelDao();

    public abstract ReferralIndicatorDao referralIndicatorDao();

    public abstract ReferralServiceIndicatorsDao referralServiceIndicatorsDao();

    public abstract LoggedInSessionsModelDao loggedInSessionsModelDao();

    public abstract ReferralFeedbackModelDao referralFeedbackModelDao();

}