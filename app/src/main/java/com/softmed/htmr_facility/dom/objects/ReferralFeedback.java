package com.softmed.htmr_facility.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.softmed.htmr_facility.utils.DateConverter;
import com.softmed.htmr_facility.utils.ListStringConverter;

import java.io.Serializable;
import java.util.List;

/**
 *  Created by Coze on 03/19/19.
 */

@Entity
public class ReferralFeedback implements Serializable{

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private Long id;

    @SerializedName("desc")
    private String desc;


    @SerializedName("descSw")
    private String descSw;

    @SerializedName("referralTypeId")
    private Long referralTypeId;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
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

    public Long getReferralTypeId() {
        return referralTypeId;
    }

    public void setReferralTypeId(Long referralTypeId) {
        this.referralTypeId = referralTypeId;
    }
}
