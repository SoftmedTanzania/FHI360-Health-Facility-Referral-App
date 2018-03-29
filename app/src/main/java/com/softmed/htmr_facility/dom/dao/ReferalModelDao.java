package com.softmed.htmr_facility.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.utils.DateConverter;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 11/28/17.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface ReferalModelDao {

    @Query("select * from Referral where fromFacilityId = :fromHfId")
    List<Referral> getAllReferralsOfThisFacility(String fromHfId);

    @Query("select * from Referral where serviceId = :serviceId order by referralDate desc")
    LiveData<List<Referral>> getAllReferals(int serviceId);

    //Used in OPD
    @Query("select * from Referral where referralType in (:sourceID) and referralStatus = 0 order by referralDate desc")
    LiveData<List<Referral>> getAllReferalsBySource(int[] sourceID);

    @Query("select * from Referral where serviceId = :serviceId and referralStatus = 0 and referralType in (:SourceID) order by referralDate desc")
    LiveData<List<Referral>> getReferralsBySourceId(int serviceId, int[] SourceID);

    @Query("select * from Referral where referralSource = :serviceId and fromFacilityId = :fromFacilityId order by referralDate desc")
    LiveData<List<Referral>> getReferredClients(int serviceId, String fromFacilityId);

    @Query("select count(*) from Referral where referralStatus = 0 and serviceId = :serviceId and fromFacilityId = :fromFacilityId")
    int geCountPendingReferalFeedback(int serviceId, String fromFacilityId);

    @Query("select * from Referral where referralStatus = 0 and serviceId = :serviceId order by referralDate desc")
    LiveData<List<Referral>> getUnattendedReferals(int serviceId);

    @Query("select count(*) from Referral where referralStatus = 0 and serviceId = :serviceId")
    int geCounttUnattendedReferalsByService(int serviceId);

    //Check if referral has already been chained
    @Query("select * from Referral where referralUUID = :currentReferralUUID and referralDate > :currentReferralDate")
    List<Referral> listOfChainedReferrals(String currentReferralUUID, long currentReferralDate);

    /*
    Live Updating Referral Summaries
     */
    @Query("select count(*) from Referral where referralStatus = 0 and referralType in (:sourceID)")
    LiveData<Integer> getLiveCountReferralsBySource(int[] sourceID);

    @Query("select count(*) from Referral where referralStatus = 0 and referralSource = :sourceServiceId and fromFacilityId = :fromFacilityId")
    LiveData<Integer> getLiveCountPendingReferalFeedback(int sourceServiceId, String fromFacilityId);

    @Query("select count(*) from Referral where referralStatus = 0 and (referralType = 1 or referralType = 3)")
    LiveData<Integer> getLiveOPDReferralsCount();

    @Query("select count(*) from Referral where referralStatus = 0 and serviceId = :serviceId and referralType in (:sourceID)")
    LiveData<Integer> getLiveCountUnattendedReferalsByService(int serviceId, int[] sourceID);

    @Query("select count(*) from Referral where referralStatus = 1 and serviceId = :serviceID and fromFacilityId = :fromFacilityID")
    LiveData<Integer> getLiveCountTestedClients(int serviceID, String fromFacilityID);


    @Query("select * from Referral where patient_id = :id order by referralDate desc")
    LiveData<List<Referral>> getReferalsByPatientId(String id);

    @Query("select * from Referral where referral_id = :id")
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
     *LAB QUERIES
     */

    //Getting a list of tested clients
    @Query("select * from Referral where serviceId = 11 and referralStatus = 1 order by referralDate desc")
    LiveData<List<Referral>> getAllTestedReferrals();

    /**
     * TB CASES
     */

    @Query("select * from Referral where serviceId = :serviceId order by referralDate desc")
    LiveData<List<Referral>> getTbReferralList(int serviceId);

    @Query("select * from Referral inner join Patient on Referral.patient_id = Patient.patientId where " +
            "Referral.referralStatus = :status and " +
            "Referral.serviceId = :serviceId and "+
            "Patient.patientFirstName like :name COLLATE NOCASE and " +
            "Patient.patientSurname like :lastName COLLATE NOCASE"
    )
    List<Referral> getFilteredTbReferals(String name, String lastName, int status, int serviceId);

    //OPD Search filter
    @Query("select * from Referral inner join Patient on Referral.patient_id = Patient.patientId where " +
            "Patient.patientFirstName like :fname COLLATE NOCASE or " +
            "Patient.patientSurname like :fname COLLATE NOCASE or "+
            "Patient.ctcNumber like :fname and "+
            //"Referral.referralDate between :fromdt and :todt and "+
            "Referral.referralStatus in (:status) and "+
            "Referral.referralType in (:refType)" )
    List<Referral> getFilteredOpdReferrals(int[] status, String fname, int[] refType);

    @Query("select * from Referral inner join Patient on Referral.patient_id = Patient.patientId where " +
            "Patient.patientFirstName like :fname COLLATE NOCASE and " +
            "Patient.patientSurname like :lname COLLATE NOCASE and "+
            "Patient.ctcNumber like :ctcNumber and " +
            "Referral.referralDate between :fromdt and :todt and "+
            "Referral.referralStatus in (:status) and "+
            "Referral.referralType in (:refType)" )
    List<Referral> getFilteredOpdReferralsWithDates(int[] status, String fname, String lname, String ctcNumber, long fromdt, long todt, int[] refType);

    //Other Sections search filter
    @Query("select * from Referral inner join Patient on Referral.patient_id = Patient.patientId where " +
            "Patient.patientFirstName like :fname COLLATE NOCASE and " +
            "Patient.patientSurname like :lname COLLATE NOCASE and "+
            "Patient.ctcNumber like :ctcNumber and " +
            "Referral.referralDate between :fromdt and :todt and "+
            "Referral.referralStatus in (:status) and "+
            "Referral.referralType in (:refType) and "+
            "Referral.serviceId = :serviceID")
    List<Referral> getFilteredHivAndTbReferrals(int[] status, String fname, String lname, String ctcNumber, long fromdt, long todt, int[] refType, int serviceID);


    //REFERRAL REPORTS QUERIES
    @Query("select count(*) from Referral inner join Patient on Referral.patient_id = Patient.patientId where " +
            "serviceId = :serviceIndicatorID and " +
            "referralDate between :fromDate and :toDate and " +
            "referralType = :referralType and " +
            "Patient.gender = :gender")
    int getAllReferralsByServcieAndDateRange(long serviceIndicatorID, long fromDate, long toDate, int referralType, String gender);


    @Query("select count(*) from Referral inner join Patient on Referral.patient_id = Patient.patientId where " +
            "serviceId = :serviceIndicatorID and " +
            "referralDate between :fromDate and :toDate and " +
            "referralType = :referralType and " +
            "referralStatus = :referralStatus and " +
            "Patient.gender = :gender")
    int getAllReferralsByServcieDateRangeAndReferralStatus(long serviceIndicatorID, long fromDate, long toDate, int referralType, String gender, int referralStatus);


    /*
    Used For the graph report
     */
    @Query("select count(*) from Referral where " +
            "serviceId = :serviceIndicatorID and " +
            "referralDate between :fromDate and :toDate and " +
            "referralType = :referralType")
    int getTotalReceivedReferralsByServiceID(long serviceIndicatorID, long fromDate, long toDate, int referralType);

}
