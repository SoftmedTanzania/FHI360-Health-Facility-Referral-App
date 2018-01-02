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
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Entity
public class TbPatient implements Serializable{

    /*
    This is the ID of tbPatient table
     */
    @NonNull
    private Long tempID;

    @PrimaryKey(autoGenerate = false)
    private Long patientId;

    @SerializedName("health_facility_patient_id")
    private Long healthFacilityPatientId;

    @SerializedName("patient_type")
    private int patientType;

    @SerializedName("transfer_type")
    private int transferType;

    @SerializedName("referral_type")
    private int referralType;

    @SerializedName("veo")
    private String veo;

    @SerializedName("weight")
    private double weight;

    @SerializedName("xray")
    private String xray;

    @SerializedName("makohozi")
    private String makohozi;

    @SerializedName("other_tests")
    private String otherTests;

    @SerializedName("treatment_type")
    private String treatment_type;

    @SerializedName("outcome")
    private String outcome;

    @SerializedName("outcome_date")
    @TypeConverters(DateConverter.class)
    private Date outcomeDate;

    @SerializedName("outcome_details")
    private String outcomeDetails;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public void setTempID(@NonNull Long id) {
        this.tempID = id;
    }

    public String getOtherTests() {
        return otherTests;
    }

    public void setOtherTests(String otherTests) {
        this.otherTests = otherTests;
    }

    @NonNull
    public Long getTempID() {
        return tempID;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getHealthFacilityPatientId() {
        return healthFacilityPatientId;
    }

    public void setHealthFacilityPatientId(Long healthFacilityPatientId) {
        this.healthFacilityPatientId = healthFacilityPatientId;
    }

    public int getPatientType() {
        return patientType;
    }

    public void setPatientType(int patientType) {
        this.patientType = patientType;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public int getReferralType() {
        return referralType;
    }

    public void setReferralType(int referralType) {
        this.referralType = referralType;
    }

    public String getVeo() {
        return veo;
    }

    public void setVeo(String veo) {
        this.veo = veo;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getXray() {
        return xray;
    }

    public void setXray(String xray) {
        this.xray = xray;
    }

    public String getMakohozi() {
        return makohozi;
    }

    public void setMakohozi(String makohozi) {
        this.makohozi = makohozi;
    }

    public String getTreatment_type() {
        return treatment_type;
    }

    public void setTreatment_type(String treatment_type) {
        this.treatment_type = treatment_type;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Date getOutcomeDate() {
        return outcomeDate;
    }

    public void setOutcomeDate(Date outcomeDate) {
        this.outcomeDate = outcomeDate;
    }

    public String getOutcomeDetails() {
        return outcomeDetails;
    }

    public void setOutcomeDetails(String outcomeDetails) {
        this.outcomeDetails = outcomeDetails;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
