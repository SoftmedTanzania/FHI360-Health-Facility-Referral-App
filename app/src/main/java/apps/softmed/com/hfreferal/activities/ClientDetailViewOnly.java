package apps.softmed.com.hfreferal.activities;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.base.BaseActivity;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by issy on 12/8/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ClientDetailViewOnly extends BaseActivity {

    private Toolbar toolbar;
    private Button saveButton;
    private Button referButton;
    private MaterialSpinner servicesSpinner, healthFacilitySpinner;
    private TextView referrerName;

    public Dialog referalDialogue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        setupviews();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        referalDialogue = new Dialog(this);
        referalDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        referButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void setupviews(){
        referrerName = (TextView) findViewById(R.id.referer_name_value);
    }

}
