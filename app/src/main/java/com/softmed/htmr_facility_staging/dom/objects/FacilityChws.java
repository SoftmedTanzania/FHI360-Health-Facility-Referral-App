package com.softmed.htmr_facility_staging.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Coze on 02/26/19.
 */
@Entity
public class FacilityChws implements Serializable {

    @SerializedName("display")
    private String display;

    @SerializedName("uuid")
    @PrimaryKey
    @NonNull
    private String uuid;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
