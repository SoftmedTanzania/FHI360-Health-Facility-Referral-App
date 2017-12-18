package apps.softmed.com.hfreferal.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.utils.DateConverter;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 11/28/17.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface PatientModelDao {

    @Query("select * from Patient")
    LiveData<List<Patient>> getAllPatients();

    @Query("select * from Patient where patientId = :id")
    Patient getPatientById(String id);

    @Insert(onConflict = REPLACE)
    void addPatient(Patient patients);

    @Query("select patientFirstName || ' ' || patientSurname from Patient where patientId = :patientId")
    String getPatientName(String patientId);

    @Delete
    void deleteAPatient(Patient patients);

}
