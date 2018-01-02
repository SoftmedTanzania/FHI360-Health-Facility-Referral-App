package apps.softmed.com.hfreferal.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Entity(primaryKeys = { "tbPatientID", "encounterMonth" })
public class TbEncounters implements Serializable {

    @NonNull
    @SerializedName("tb_patient_id")
    private String tbPatientID;

    @NonNull
    @SerializedName("encounter_month")
    private String encounterMonth;

    @SerializedName("makohozi")
    private String makohozi;

    @SerializedName("appointment_id")
    private String appointmentId;

    @SerializedName("has_finished_previous_month_medication")
    private int hasFinishedPreviousMonthMedication;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("medication_status")
    private boolean medicationStatus;

    @SerializedName("medication_date")
    private String medicationDate;

    @SerializedName("appointment_date")
    private String appointmentDate;


    public String getTbPatientID() {
        return tbPatientID;
    }

    public void setTbPatientID(String tbPatientID) {
        this.tbPatientID = tbPatientID;
    }

    public String getEncounterMonth() {
        return encounterMonth;
    }

    public void setEncounterMonth(String encounterMonth) {
        this.encounterMonth = encounterMonth;
    }

    public String getMakohozi() {
        return makohozi;
    }

    public void setMakohozi(String makohozi) {
        this.makohozi = makohozi;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getHasFinishedPreviousMonthMedication() {
        return hasFinishedPreviousMonthMedication;
    }

    public void setHasFinishedPreviousMonthMedication(int hasFinishedPreviousMonthMedication) {
        this.hasFinishedPreviousMonthMedication = hasFinishedPreviousMonthMedication;
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

    public boolean isMedicationStatus() {
        return medicationStatus;
    }

    public void setMedicationStatus(boolean medicationStatus) {
        this.medicationStatus = medicationStatus;
    }

    public String getMedicationDate() {
        return medicationDate;
    }

    public void setMedicationDate(String medicationDate) {
        this.medicationDate = medicationDate;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
