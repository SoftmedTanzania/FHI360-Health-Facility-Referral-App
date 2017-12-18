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
    private String referral_id;

    /**
     *  1 == POSTED
     *  0 == NOT_POSTED
     */
    private int syncStatus;

    public String getReferral_id() {
        return referral_id;
    }

    public void setReferral_id(String referral_id) {
        this.referral_id = referral_id;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }
}
