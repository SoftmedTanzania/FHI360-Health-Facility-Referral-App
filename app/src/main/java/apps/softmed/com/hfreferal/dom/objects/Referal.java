package apps.softmed.com.hfreferal.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import apps.softmed.com.hfreferal.utils.DateConverter;

/**
 *  Created by issy on 11/28/17.
 */

@Entity
public class Referal implements Serializable{

    private Long id;

    @SerializedName("patientId")
    private String patient_id;

    @NonNull
    @PrimaryKey(autoGenerate = false)
    @SerializedName("referralId")
    private String referral_id;

    @SerializedName("communityBasedHivService")
    private String communityBasedHivService;

    @SerializedName("referralReason")
    private String referralReason;

    /*
    Malaria, Tb or Hiv
     */
    @SerializedName("serviceId")
    private int serviceId;

    @SerializedName("ctcNumber")
    private String ctcNumber;

    @SerializedName("has2WeeksCough")
    private Boolean has2WeeksCough;

    @SerializedName("hasBloodCough")
    private Boolean hasBloodCough;

    @SerializedName("hasSevereSweating")
    private Boolean hasSevereSweating;

    @SerializedName("hasFever")
    private Boolean hasFever;

    @SerializedName("hadWeightLoss")
    private Boolean hadWeightLoss;

    @SerializedName("serviceProviderUIID")
    private String serviceProviderUIID;

    @SerializedName("serviceProviderGroup")
    private String serviceProviderGroup;

    @SerializedName("villageLeader")
    private String villageLeader;

    @SerializedName("referralDate")
    @TypeConverters(DateConverter.class)
    private Date referralDate;

    //This is the facility ID the referral is going
    @SerializedName("facilityId")
    private String facilityId;

    @SerializedName("fromFacilityId")
    private String fromFacilityId;

    /*
     * 1 = CHW
     * 2 = Health facility
     */
    @SerializedName("referralSource")
    private int referralSource;

    /*
     *  0 = new
     * -1 = rejected/discarded
     *  1 = complete referral
     */
    @SerializedName("referralStatus")
    private int referralStatus;

    @SerializedName("createdAt")
    @TypeConverters(DateConverter.class)
    private Date createdAt;

    @SerializedName("updatedAt")
    @TypeConverters(DateConverter.class)
    private Date updatedAt;

    /*
    The following two are the feedback portion of a referal
     */
    private String serviceGivenToPatient;

    private String otherNotesAndAdvices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getReferral_id() {
        return referral_id;
    }

    public void setReferral_id(String id) {
        this.referral_id = id;
    }

    public String getCommunityBasedHivService() {
        return communityBasedHivService;
    }

    public void setCommunityBasedHivService(String communityBasedHivService) {
        this.communityBasedHivService = communityBasedHivService;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public void setReferralReason(String referralReason) {
        this.referralReason = referralReason;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getCtcNumber() {
        return ctcNumber;
    }

    public void setCtcNumber(String ctcNumber) {
        this.ctcNumber = ctcNumber;
    }

    public Boolean getHas2WeeksCough() {
        return has2WeeksCough;
    }

    public void setHas2WeeksCough(Boolean has2WeeksCough) {
        this.has2WeeksCough = has2WeeksCough;
    }

    public Boolean getHasBloodCough() {
        return hasBloodCough;
    }

    public void setHasBloodCough(Boolean hasBloodCough) {
        this.hasBloodCough = hasBloodCough;
    }

    public Boolean getHasSevereSweating() {
        return hasSevereSweating;
    }

    public void setHasSevereSweating(Boolean hasSevereSweating) {
        this.hasSevereSweating = hasSevereSweating;
    }

    public int getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(int referralSource) {
        this.referralSource = referralSource;
    }

    public Boolean getHasFever() {
        return hasFever;
    }

    public void setHasFever(Boolean hasFever) {
        this.hasFever = hasFever;
    }

    public Boolean getHadWeightLoss() {
        return hadWeightLoss;
    }

    public void setHadWeightLoss(Boolean hadWeightLoss) {
        this.hadWeightLoss = hadWeightLoss;
    }

    public String getServiceProviderUIID() {
        return serviceProviderUIID;
    }

    public void setServiceProviderUIID(String serviceProviderUIID) {
        this.serviceProviderUIID = serviceProviderUIID;
    }

    public String getServiceProviderGroup() {
        return serviceProviderGroup;
    }

    public void setServiceProviderGroup(String serviceProviderGroup) {
        this.serviceProviderGroup = serviceProviderGroup;
    }

    public String getVillageLeader() {
        return villageLeader;
    }

    public void setVillageLeader(String villageLeader) {
        this.villageLeader = villageLeader;
    }

    public Date getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(Date referralDate) {
        this.referralDate = referralDate;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public int getReferralStatus() {
        return referralStatus;
    }

    public void setReferralStatus(int referralStatus) {
        this.referralStatus = referralStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFromFacilityId() {
        return fromFacilityId;
    }

    public void setFromFacilityId(String fromFacilityId) {
        this.fromFacilityId = fromFacilityId;
    }

    public String getServiceGivenToPatient() {
        return serviceGivenToPatient;
    }

    public void setServiceGivenToPatient(String serviceGivenToPatient) {
        this.serviceGivenToPatient = serviceGivenToPatient;
    }

    public String getOtherNotesAndAdvices() {
        return otherNotesAndAdvices;
    }

    public void setOtherNotesAndAdvices(String otherNotesAndAdvices) {
        this.otherNotesAndAdvices = otherNotesAndAdvices;
    }
}
