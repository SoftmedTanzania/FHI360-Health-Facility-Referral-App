package com.softmed.htmr_facility_staging.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import com.softmed.htmr_facility_staging.utils.DateConverter;

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
    private String tempID;

    @PrimaryKey(autoGenerate = false)
    @SerializedName("tbPatientId")
    private long tbPatientId;

    @SerializedName("healthFacilityPatientId")
    private Long healthFacilityPatientId;

    @SerializedName("patient_type")
    private int patientType;

    @SerializedName("transfer_type")
    private int transferType;

    @SerializedName("referral_type")
    private int referralType;


    /*
    1 = Makohozi
    2 = X-Ray
    3 = Other
    */
    @SerializedName("testType")
    private int testType;

    private String otherTestDetails;

    @SerializedName("veo")
    private String veo;

    @SerializedName("weight")
    private double weight;

    @SerializedName("xray")
    private String xray;

    @SerializedName("makohozi")
    private String makohozi;

    @SerializedName("otherTestsDetails")
    private String otherTests;

    @SerializedName("treatment_type")
    private String treatment_type;

    @SerializedName("outcome")
    private String outcome;

    @SerializedName("outcomeDate")
    @TypeConverters(DateConverter.class)
    private long outcomeDate;

    @SerializedName("outcomeDetails")
    private String outcomeDetails;

    @SerializedName("isPregnant")
    private boolean isPregnant;

    /***
     *  1 -> Ongoing
     *  0 -> Cancelled
     *  3 -> Finished?
     */
    @SerializedName("treatmentStatus")
    private int treatmentStatus;

    public String getTempID() {
        return tempID;
    }

    public void setTempID(String tempID) {
        this.tempID = tempID;
    }

    public long getTbPatientId() {
        return tbPatientId;
    }

    public void setTbPatientId(long tbPatientId) {
        this.tbPatientId = tbPatientId;
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

    public int getTestType() {
        return testType;
    }

    public void setTestType(int testType) {
        this.testType = testType;
    }

    public String getOtherTestDetails() {
        return otherTestDetails;
    }

    public void setOtherTestDetails(String otherTestDetails) {
        this.otherTestDetails = otherTestDetails;
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

    public String getOtherTests() {
        return otherTests;
    }

    public void setOtherTests(String otherTests) {
        this.otherTests = otherTests;
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

    public long getOutcomeDate() {
        return outcomeDate;
    }

    public void setOutcomeDate(long outcomeDate) {
        this.outcomeDate = outcomeDate;
    }

    public String getOutcomeDetails() {
        return outcomeDetails;
    }

    public void setOutcomeDetails(String outcomeDetails) {
        this.outcomeDetails = outcomeDetails;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public int getTreatmentStatus() {
        return treatmentStatus;
    }

    public void setTreatmentStatus(int treatmentStatus) {
        this.treatmentStatus = treatmentStatus;
    }
}
