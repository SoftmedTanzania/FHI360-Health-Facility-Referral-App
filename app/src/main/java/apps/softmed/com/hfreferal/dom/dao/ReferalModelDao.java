package apps.softmed.com.hfreferal.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import apps.softmed.com.hfreferal.dom.objects.Referral;
import apps.softmed.com.hfreferal.utils.DateConverter;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 11/28/17.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface ReferalModelDao {

    @Query("select * from Referral where fromFacilityId = :fromHfId")
    List<Referral> getAllReferralsOfThisFacility(String fromHfId);

    @Query("select * from Referral where serviceId = :serviceId")
    LiveData<List<Referral>> getAllReferals(int serviceId);

    @Query("select * from Referral where serviceId = :serviceId and referralSource = :SourceID")
    LiveData<List<Referral>> getReferralsBySourceId(int serviceId, int SourceID);

    @Query("select * from Referral where serviceId = :serviceId and fromFacilityId = :fromFacilityId order by referralStatus desc")
    LiveData<List<Referral>> getReferredClients(int serviceId, String fromFacilityId);

    @Query("select count(*) from Referral where referralStatus = 0 and serviceId = :serviceId and fromFacilityId = :fromFacilityId")
    int geCountPendingReferalFeedback(int serviceId, String fromFacilityId);

    @Query("select * from Referral where referralStatus = 0 and serviceId = :serviceId")
    LiveData<List<Referral>> getUnattendedReferals(int serviceId);

    @Query("select count(*) from Referral where referralStatus = 0 and serviceId = :serviceId")
    int geCounttUnattendedReferals(int serviceId);

    @Query("select count(*) from Referral where referralStatus = 0 and serviceId = :serviceId and referralSource = :sourceID")
    int getCountSourceReferrals(int serviceId, int sourceID);

    @Query("select * from Referral where patient_id = :id")
    LiveData<List<Referral>> getReferalsByPatientId(String id);

    @Query("select * from Referral where id = :id")
    Referral getReferalById(String id);

    @Query("select * from Referral inner join Patient on Referral.patient_id = Patient.patientId where " +
            "Referral.referralStatus = :status and " +
            "Referral.serviceId = :serviceId and "+
            "Patient.patientFirstName like :name COLLATE NOCASE and " +
            "Patient.patientSurname like :lastName COLLATE NOCASE"
    )
    List<Referral> getFilteredReferal(String name, String lastName, int status, int serviceId);

    @Insert(onConflict = REPLACE)
    void addReferal(Referral referral);

    @Update
    int updateReferral(Referral referral);

    @Delete
    void deleteReferal(Referral referral);


    /**
     * TB CASES
     */

    @Query("select * from Referral where serviceId = :serviceId")
    LiveData<List<Referral>> getTbReferralList(int serviceId);

    @Query("select * from Referral inner join Patient on Referral.patient_id = Patient.patientId where " +
            "Referral.referralStatus = :status and " +
            "Referral.serviceId = :serviceId and "+
            "Patient.patientFirstName like :name COLLATE NOCASE and " +
            "Patient.patientSurname like :lastName COLLATE NOCASE"
    )
    List<Referral> getFilteredTbReferals(String name, String lastName, int status, int serviceId);




}
