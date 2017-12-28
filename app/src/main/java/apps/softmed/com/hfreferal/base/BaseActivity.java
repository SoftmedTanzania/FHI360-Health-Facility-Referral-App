package apps.softmed.com.hfreferal.base;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.List;

import apps.softmed.com.hfreferal.HomeActivity;
import apps.softmed.com.hfreferal.LoaderActivity;
import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.api.Endpoints;
import apps.softmed.com.hfreferal.dom.objects.Referal;
import apps.softmed.com.hfreferal.dom.responces.ReferalResponce;
import apps.softmed.com.hfreferal.utils.ServiceGenerator;
import apps.softmed.com.hfreferal.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static apps.softmed.com.hfreferal.utils.constants.BASE_URL;

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
        return "001";
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
