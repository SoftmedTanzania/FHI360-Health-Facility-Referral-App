package com.softmed.ccm_facility.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.softmed.ccm_facility.base.AppDatabase;
import com.softmed.ccm_facility.base.BaseActivity;

/**
 * Created by issy on 12/3/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferal
 */

public class LoaderActivity extends BaseActivity {

    //Activity to be used to load all the required data.

    private AppDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabase(this.getApplication());

        //callReferralList();

    }

}
