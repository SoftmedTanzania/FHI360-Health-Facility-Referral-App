package apps.softmed.com.hfreferal.dom.objects;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Entity(primaryKeys = { "tbPatientID", "encounterMonth" })
public class TbEncounters implements Serializable {

    private String id;

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

    @SerializedName("medicationDate")
    private long medicationDate;

    @SerializedName("scheduledDate")
    private long scheduledDate;


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

    public long getMedicationDate() {
        return medicationDate;
    }

    public void setMedicationDate(long medicationDate) {
        this.medicationDate = medicationDate;
    }

    public long getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(long scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
