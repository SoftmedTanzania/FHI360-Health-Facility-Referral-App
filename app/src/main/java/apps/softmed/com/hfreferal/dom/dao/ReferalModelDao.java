package apps.softmed.com.hfreferal.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import apps.softmed.com.hfreferal.dom.objects.Referal;
import apps.softmed.com.hfreferal.utils.DateConverter;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 11/28/17.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface ReferalModelDao {
    @Query("select * from Referal where serviceId = :serviceId")
    LiveData<List<Referal>> getAllReferals(int serviceId);

    @Query("select * from Referal where serviceId = :serviceId and facilityId = :fromFacilityId")
    LiveData<List<Referal>> getHivReferredClients(int serviceId, String fromFacilityId);

    @Query("select * from Referal where referralStatus = 0 and serviceId = :serviceId")
    LiveData<List<Referal>> getUnattendedReferals(int serviceId);

    @Query("select count(*) from Referal where referralStatus = 0 and serviceId = :serviceId")
    int geCounttUnattendedReferals(int serviceId);

    @Query("select * from Referal where patient_id = :id")
    LiveData<List<Referal>> getReferalsByPatientId(String id);

    @Query("select * from Referal where id = :id")
    Referal getReferalById(String id);

    @Query("select * from Referal inner join Patient on Referal.patient_id = Patient.patientId where " +
            "Referal.referralStatus = :status and " +
            "Referal.serviceId = :serviceId and "+
            "Patient.patientFirstName like :name COLLATE NOCASE and " +
            "Patient.patientSurname like :lastName COLLATE NOCASE"
    )
    List<Referal> getFilteredReferal(String name, String lastName, int status, int serviceId);

    @Insert(onConflict = REPLACE)
    void addReferal(Referal referal);

    @Update
    int updateReferral(Referal referal);

    @Delete
    void deleteReferal(Referal referal);


    /**
     * TB CASES
     */

    @Query("select * from Referal where serviceId = :serviceId")
    LiveData<List<Referal>> getTbReferralList(int serviceId);

    @Query("select * from Referal inner join Patient on Referal.patient_id = Patient.patientId where " +
            "Referal.referralStatus = :status and " +
            "Referal.serviceId = :serviceId and "+
            "Patient.patientFirstName like :name COLLATE NOCASE and " +
            "Patient.patientSurname like :lastName COLLATE NOCASE"
    )
    List<Referal> getFilteredTbReferals(String name, String lastName, int status, int serviceId);




}
