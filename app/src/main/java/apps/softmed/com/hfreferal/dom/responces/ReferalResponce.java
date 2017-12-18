package apps.softmed.com.hfreferal.dom.responces;

import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Ref;
import java.util.Date;
import java.util.List;

import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.Referal;
import apps.softmed.com.hfreferal.utils.DateConverter;

/**
 * Created by issy on 12/3/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferal
 */

public class ReferalResponce implements Serializable {

    @SerializedName("patientsDTO")
    private Patient patient;

    @SerializedName("referralsDTOS")
    private List<Referal> referralDTOS;

    @SerializedName("patientReferralsList")
    private List<Referal> patientReferalList;

    public ReferalResponce(){}

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Referal> getPatientReferalList() {
        return patientReferalList;
    }

    public void setPatientReferalList(List<Referal> patientReferalList) {
        this.patientReferalList = patientReferalList;
    }

    public List<Referal> getReferralDTOS() {
        return referralDTOS;
    }

    public void setReferralDTOS(List<Referal> referralDTOS) {
        this.referralDTOS = referralDTOS;
    }
}
