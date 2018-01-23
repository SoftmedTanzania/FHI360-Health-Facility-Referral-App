package apps.softmed.com.hfreferal.api;

import java.util.List;

import apps.softmed.com.hfreferal.dom.objects.HealthFacilities;
import apps.softmed.com.hfreferal.dom.objects.HealthFacilityServices;
import apps.softmed.com.hfreferal.dom.objects.Referral;
import apps.softmed.com.hfreferal.dom.objects.ReferralServiceIndicatorsResponse;
import apps.softmed.com.hfreferal.dom.objects.TbEncounters;
import apps.softmed.com.hfreferal.dom.responces.InitialSyncResponce;
import apps.softmed.com.hfreferal.dom.responces.LoginResponse;
import apps.softmed.com.hfreferal.dom.responces.PatientResponce;
import apps.softmed.com.hfreferal.dom.responces.ReferalResponce;
import apps.softmed.com.hfreferal.dom.responces.SingleReferralResonse;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by issy on 11/23/17.
 */

public class Endpoints {

    private String HFUUID = "";

    public interface LoginService {

        @POST("security/authenticate/")
        Call<LoginResponse> basicLogin();

    }

    public interface ReferalService {

        @GET("all-patients-referrals")
        Call<List<ReferalResponce>> getHfReferrals();

        @GET("get-facility-referrals/{facilityUUID}")
        Call<List<ReferalResponce>> getHealthFacilityReferrals(@Path("facilityUUID") String facilityUUID );

        @POST("save-facility-referral")
        Call<Referral> postReferral(@Body RequestBody r);

        @POST("receive-feedback")
        Call<String> sendReferralFeedback(@Body RequestBody f);

        @GET("boresha-afya-services")
        Call<List<ReferralServiceIndicatorsResponse>> getAllServices();

        @GET("get-health-facilities")
        Call<List<HealthFacilities>> getHealthFacilities();

    }

    public interface PatientServices{

        @POST("save-tb-patient")
        Call<PatientResponce> postPatient(@Body RequestBody p);

        @POST("save-tb-encounters")
        Call<TbEncounters> postEncounter(@Body RequestBody e);

    }

    public interface NotificationServices{

        @POST("save-push-notification-token")
        Call<String> registerDevice(@Body RequestBody u);

    }

}
