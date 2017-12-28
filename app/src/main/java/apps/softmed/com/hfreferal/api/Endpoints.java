package apps.softmed.com.hfreferal.api;

import java.util.List;

import apps.softmed.com.hfreferal.dom.responces.InitialSyncResponce;
import apps.softmed.com.hfreferal.dom.responces.LoginResponse;
import apps.softmed.com.hfreferal.dom.responces.ReferalResponce;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by issy on 11/23/17.
 */

public class Endpoints {

    public interface LoginService {

        @POST("security/authenticate/")
        Call<LoginResponse> basicLogin();

    }

    public interface ReferalService {

        @GET("all-patients-referrals/")
        Call<List<ReferalResponce>> getHfReferrals();

        @POST("facility_facility_referral")
        Call referreClient();

        @POST("receive_feedback")
        Call sendReferralFeedback(@Body RequestBody feedback);

    }

    public interface NotificationServices{

        @POST("push_notification_register")
        Call registerDevice(@Body RequestBody user);

    }

}
