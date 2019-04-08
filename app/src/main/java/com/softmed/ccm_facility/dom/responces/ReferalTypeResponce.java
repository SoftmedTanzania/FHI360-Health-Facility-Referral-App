package com.softmed.ccm_facility.dom.responces;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Coze on 19/03/19.
 *
 * @issyzac ilakozejumanne@gmail.com
 * On Project HFReferal
 */

public class ReferalTypeResponce implements Serializable {

    @SerializedName("referralTypeId")
    private Long referralTypeId;

    public Long getReferralTypeId() {
        return referralTypeId;
    }

    public void setReferralTypeId(Long referralTypeId) {
        this.referralTypeId = referralTypeId;
    }
}
