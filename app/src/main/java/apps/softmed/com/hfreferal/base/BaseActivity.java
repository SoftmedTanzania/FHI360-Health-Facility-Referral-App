package apps.softmed.com.hfreferal.base;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.api.Endpoints;
import apps.softmed.com.hfreferal.utils.SessionManager;
import retrofit2.Retrofit;

/**
 * Created by issy on 11/14/17.
 */

public class BaseActivity extends AppCompatActivity {

    public static Typeface Julius, Avenir, RobotoCondenced, RobotoCondencedItalic, RobotoThin, RobotoLight, RosarioRegular, RobotoMedium, athletic, FunRaiser;
    public static Typeface CabinSketchRegular, CabinSketchBold;

    public Retrofit retrofit;
    public Endpoints apiEndpoints;

    public AppDatabase baseDatabase;

    // Session Manager Class
    public static SessionManager session;

    final public static DatePickerDialog toDatePicker = new DatePickerDialog();
    final public static DatePickerDialog fromDatePicker = new DatePickerDialog();
    final public static DatePickerDialog datePickerDialog = new DatePickerDialog();

    final public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setuptypeface();

        // Session class instance
        session = new SessionManager(getApplicationContext());
        baseDatabase = AppDatabase.getDatabase(this);

        fromDatePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        toDatePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));

//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        apiEndpoints = retrofit.create(Endpoints.class);

    }

    public static String getThisFacilityId(){
        return "669c642f-20e9-452b-97d3-73d74ae4ba00";
    }


    public void setuptypeface(){
        Julius = Typeface.createFromAsset(this.getAssets(), "JuliusSansOne-Regular.ttf");
        Avenir = Typeface.createFromAsset(this.getAssets(), "avenir-light.ttf");
        RobotoCondenced = Typeface.createFromAsset(this.getAssets(), "Roboto-Condensed.ttf");
        RobotoCondencedItalic = Typeface.createFromAsset(this.getAssets(), "Roboto-CondensedItalic.ttf");
        RobotoThin = Typeface.createFromAsset(this.getAssets(), "Roboto-Thin.ttf");
        RobotoLight = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");
        RobotoMedium = Typeface.createFromAsset(this.getAssets(), "Roboto-Medium.ttf");
        RosarioRegular = Typeface.createFromAsset(this.getAssets(), "Rosario-Regular.ttf");
        athletic = Typeface.createFromAsset(this.getAssets(), "athletic.ttf");
        FunRaiser = Typeface.createFromAsset(this.getAssets(), "Fun-Raiser.ttf");
        CabinSketchRegular = Typeface.createFromAsset(this.getAssets(), "CabinSketch-Regular.ttf");
        CabinSketchBold = Typeface.createFromAsset(this.getAssets(), "CabinSketch-Bold.ttf");
    }

}
