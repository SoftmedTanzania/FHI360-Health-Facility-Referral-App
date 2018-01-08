package apps.softmed.com.hfreferal.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import apps.softmed.com.hfreferal.dom.objects.HealthFacilities;
import apps.softmed.com.hfreferal.dom.objects.Patient;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 1/7/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Dao
public interface HealthFacilitiesModelDao {

    @Query("select * from HealthFacilities")
    List<HealthFacilities> getAllHealthFacilities();

    @Insert(onConflict = REPLACE)
    void addHealthFacility(HealthFacilities hf);

    @Delete
    void deleteHealthFacility(HealthFacilities hf);

}
