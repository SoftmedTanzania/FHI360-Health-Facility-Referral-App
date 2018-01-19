package apps.softmed.com.hfreferal.dom.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by issy on 19/01/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ReferralIndicator implements Serializable {

    @SerializedName("referralServiceIndicatorId")
    private long referralServiceIndicatorId;

    @SerializedName("referralIndicatorId")
    private long referralIndicatorId;

    @SerializedName("indicatorName")
    private String indicatorName;

    @SerializedName("isActive")
    private boolean isActive;

    public long getReferralServiceIndicatorId() {
        return referralServiceIndicatorId;
    }

    public void setReferralServiceIndicatorId(long referralServiceIndicatorId) {
        this.referralServiceIndicatorId = referralServiceIndicatorId;
    }

    public long getReferralIndicatorId() {
        return referralIndicatorId;
    }

    public void setReferralIndicatorId(long referralIndicatorId) {
        this.referralIndicatorId = referralIndicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
