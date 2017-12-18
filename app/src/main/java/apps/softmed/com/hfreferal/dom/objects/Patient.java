package apps.softmed.com.hfreferal.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by issy on 11/28/17.
 */

@Entity
public class Patient implements Serializable{

    private Long id;

    @NonNull
    @PrimaryKey(autoGenerate = false)
    @SerializedName("patientId")
    private String patientId;

    @SerializedName("firstName")
    private String patientFirstName;

    @SerializedName("middleName")
    private String patientMiddleName;

    @SerializedName("surname")
    private String patientSurname;

    @SerializedName("phoneNumber")
    private String phone_number;

    @SerializedName("ward")
    private String ward;

    @SerializedName("village")
    private String village;

    @SerializedName("hamlet")
    private String hamlet;

    @SerializedName("dateOfBirth")
    //@TypeConverters(DateConverter.class)
    private String dateOfBirth;

    @SerializedName("gender")
    private String gender;

    @SerializedName("dateOfDeath")
    private long dateOfDeath;

    private boolean currentOnTbTreatment;

    private boolean currentlyPregnant;

    private String drugAlergies;

    private String otherRelevantClinicalNotes;

    /*
    @SerializedName("createdAt")
    @TypeConverters(DateConverter.class)
    private Date createdAt;

    @SerializedName("updatedAt")
    @TypeConverters(DateConverter.class)
    private Date updatedAt;
    */

    @SerializedName("createdAt")
    private long createdAt;

    @SerializedName("updatedAt")
    private long updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientSurname() {
        return patientSurname;
    }

    public void setPatientSurname(String patientSurname) {
        this.patientSurname = patientSurname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getHamlet() {
        return hamlet;
    }

    public void setHamlet(String hamlet) {
        this.hamlet = hamlet;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(long dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public String getPatientMiddleName() {
        return patientMiddleName;
    }

    public void setPatientMiddleName(String patientMiddleName) {
        this.patientMiddleName = patientMiddleName;
    }

    public boolean isCurrentOnTbTreatment() {
        return currentOnTbTreatment;
    }

    public void setCurrentOnTbTreatment(boolean currentOnTbTreatment) {
        this.currentOnTbTreatment = currentOnTbTreatment;
    }

    public boolean isCurrentlyPregnant() {
        return currentlyPregnant;
    }

    public void setCurrentlyPregnant(boolean currentlyPregnant) {
        this.currentlyPregnant = currentlyPregnant;
    }

    public String getDrugAlergies() {
        return drugAlergies;
    }

    public void setDrugAlergies(String drugAlergies) {
        this.drugAlergies = drugAlergies;
    }

    public String getOtherRelevantClinicalNotes() {
        return otherRelevantClinicalNotes;
    }

    public void setOtherRelevantClinicalNotes(String otherRelevantClinicalNotes) {
        this.otherRelevantClinicalNotes = otherRelevantClinicalNotes;
    }
}