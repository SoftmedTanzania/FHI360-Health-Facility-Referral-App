package apps.softmed.com.hfreferal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import apps.softmed.com.hfreferal.base.BaseActivity;

/**
 * Created by issy on 12/14/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ReminderActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        setupviews();

        if (toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }



    private void setupviews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
