package com.softmed.ccm_facility.dom.responces;

import com.google.gson.annotations.SerializedName;
import com.softmed.ccm_facility.dom.objects.PatientAppointment;
import com.softmed.ccm_facility.dom.objects.TbEncounters;

import java.io.Serializable;
import java.util.List;

/**
 * Created by issy on 11/04/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class EncounterResponse implements Serializable {

    @SerializedName("tbEncounterDTO")
    TbEncounters encounter;

    @SerializedName("patientsAppointmentsDTOS")
    List<PatientAppointment> patientAppointments;

    public EncounterResponse(){}

    public TbEncounters getEncounter() {
        return encounter;
    }

    public void setEncounter(TbEncounters encounter) {
        this.encounter = encounter;
    }

    public List<PatientAppointment> getPatientAppointments() {
        return patientAppointments;
    }

    public void setPatientAppointments(List<PatientAppointment> patientAppointments) {
        this.patientAppointments = patientAppointments;
    }
}
