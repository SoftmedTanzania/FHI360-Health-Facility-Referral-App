package apps.softmed.com.hfreferal.api;

import java.util.List;

import apps.softmed.com.hfreferal.dom.responces.InitialSyncResponce;
import apps.softmed.com.hfreferal.dom.responces.LoginResponse;
import apps.softmed.com.hfreferal.dom.responces.ReferalResponce;
import retrofit2.Call;
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

    }

}
