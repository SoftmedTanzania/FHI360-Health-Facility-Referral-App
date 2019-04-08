package com.softmed.ccm_facility.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.softmed.ccm_facility.dom.objects.LoggedInSessions;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 01/05/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Dao
public interface LoggedInSessionsModelDao {

    @Query("select * from LoggedInSessions")
    LiveData<List<LoggedInSessions>> getAllLoggedInSessions();

    @Insert(onConflict = REPLACE)
    void addLoggedInSession(LoggedInSessions loggedInSessions);

    @Query("select * from LoggedInSessions where userName = :username and userPassword = :password")
    List<LoggedInSessions> loggeInUser(String username, String password);

    @Update
    void updateLoggedInSession(LoggedInSessions loggedInSessions);

    @Delete
    void deleteLoggedInSession(LoggedInSessions loggedInSessions);

}
