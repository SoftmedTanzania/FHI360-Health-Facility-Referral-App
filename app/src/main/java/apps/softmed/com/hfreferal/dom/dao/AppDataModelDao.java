package apps.softmed.com.hfreferal.dom.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import apps.softmed.com.hfreferal.dom.objects.AppData;
import apps.softmed.com.hfreferal.dom.objects.Referal;

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
    void insertDeviceID(AppData appData);

    @Query("update AppData set value = :token where name = :name")
    void updateDeviceID(String token, String name);

}
