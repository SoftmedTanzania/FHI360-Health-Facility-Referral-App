package com.softmed.htmr_facility.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.softmed.htmr_facility.dom.objects.FacilityChws;
import com.softmed.htmr_facility.dom.objects.ReferralFeedback;
import com.softmed.htmr_facility.dom.objects.UserData;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by cozej4 on 19/03/19.
 *
 * @cozej4  ilakozejumanne@gmail.com
 * On Project HFReferralApp
 */

@Dao
public interface ReferralFeedbackModelDao {

    @Query("select * from ReferralFeedback WHERE referralTypeId=:referralTypeId")
    List<ReferralFeedback> getReferralFeedbackByRefeerralType(int referralTypeId);

    @Query("select * from ReferralFeedback WHERE id=:id")
    List<ReferralFeedback> getReferralFeedbackById(int id);

    @Insert(onConflict = REPLACE)
    void addReferralFeedback(ReferralFeedback feedback);

}
