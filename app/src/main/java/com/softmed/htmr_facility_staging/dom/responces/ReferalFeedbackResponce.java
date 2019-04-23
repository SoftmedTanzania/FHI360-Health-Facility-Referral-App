package com.softmed.htmr_facility_staging.dom.responces;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Coze on 19/03/19.
 *
 * @cozej4 ilakozejumanne@gmail.com
 * On Project HFReferal
 */

public class ReferalFeedbackResponce implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("desc")
    private String desc;

    @SerializedName("descSw")
    private String descSw;

    @SerializedName("referralType")
    private ReferalTypeResponce referalTypeResponce;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescSw() {
        return descSw;
    }

    public void setDescSw(String descSw) {
        this.descSw = descSw;
    }

    public ReferalTypeResponce getReferalTypeResponce() {
        return referalTypeResponce;
    }

    public void setReferalTypeResponce(ReferalTypeResponce referalTypeResponce) {
        this.referalTypeResponce = referalTypeResponce;
    }
}
