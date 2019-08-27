package com.softmed.htmr_facility.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.softmed.htmr_facility.dom.objects.AppData;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 12/19/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Dao
public interface AppDataModelDao {

    @Query("select value from AppData where name = :name")
    String getDeviceID(String name);

    @Insert(onConflict = REPLACE)
    void insertAppData(AppData appData);

    @Query("update AppData set value = :token where name = :name")
    void updateDeviceID(String token, String name);

    @Query("select * from AppData where name = :dataName")
    LiveData<AppData> observeAppDataByName(String dataName);

    @Update
    void setSyncStatusAppData(AppData appData);

}
