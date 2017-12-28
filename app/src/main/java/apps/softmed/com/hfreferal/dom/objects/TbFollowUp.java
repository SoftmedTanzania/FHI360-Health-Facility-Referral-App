package apps.softmed.com.hfreferal.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Entity
public class TbFollowUp implements Serializable{

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private Long id;

    private String patientId;

    private String patientType;

    private String monthOneCough;

    private String monthTwoCough;

    private String monthThreeCough;

    private String monthFiveCough;

    private String monthSevenEightCough;

    private String xRay;

    private String otherTests;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getMonthOneCough() {
        return monthOneCough;
    }

    public void setMonthOneCough(String monthOneCough) {
        this.monthOneCough = monthOneCough;
    }

    public String getMonthTwoCough() {
        return monthTwoCough;
    }

    public void setMonthTwoCough(String monthTwoCough) {
        this.monthTwoCough = monthTwoCough;
    }

    public String getMonthThreeCough() {
        return monthThreeCough;
    }

    public void setMonthThreeCough(String monthThreeCough) {
        this.monthThreeCough = monthThreeCough;
    }

    public String getMonthFiveCough() {
        return monthFiveCough;
    }

    public void setMonthFiveCough(String monthFiveCough) {
        this.monthFiveCough = monthFiveCough;
    }

    public String getMonthSevenEightCough() {
        return monthSevenEightCough;
    }

    public void setMonthSevenEightCough(String monthSevenEightCough) {
        this.monthSevenEightCough = monthSevenEightCough;
    }

    public String getxRay() {
        return xRay;
    }

    public void setxRay(String xRay) {
        this.xRay = xRay;
    }

    public String getOtherTests() {
        return otherTests;
    }

    public void setOtherTests(String otherTests) {
        this.otherTests = otherTests;
    }
}
