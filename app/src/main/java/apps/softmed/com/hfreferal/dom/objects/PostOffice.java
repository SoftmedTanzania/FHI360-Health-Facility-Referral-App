package apps.softmed.com.hfreferal.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by issy on 12/12/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Entity
public class PostOffice implements Serializable{

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String patient_id;

    private String type;

    /**
     *  1 == POSTED
     *  0 == NOT_POSTED
     */
    private int syncStatus;

    @NonNull
    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(@NonNull String patient_id) {
        this.patient_id = patient_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }
}
