package apps.softmed.com.hfreferal.utils;

/**
 * Created by issy on 11/23/17.
 */

public class constants {

    public static String BASE_URL = "http://45.56.90.103:8080/opensrp/";
    //public static String BASE_URL = "http://192.168.43.251:8080/opensrp/";

    public static String STATUS_COMPLETED = "Tayari";
    public static String STATUS_NEW = "Mpya";

    public static int REFERRAL_STATUS_NEW = 0;
    public static int REFERRAL_STATUS_COMPLETED = 1;
    public static int REFERRAL_STATUS_REJECTED = -1;

    public static int ENTRY_NOT_SYNCED = 0;
    public static int ENTRY_SYNCED = 1;

    public static final int TB_SERVICE_ID = 2;
    public static final int HIV_SERVICE_ID = 1;
    public static final int MALARIA_SERVICE_ID = 3;



    public static int getReferralStatusValue(String statusString){
        if (statusString.equals(STATUS_NEW)){
            return REFERRAL_STATUS_NEW;
        }else if (statusString.equals(STATUS_COMPLETED)){
            return REFERRAL_STATUS_COMPLETED;
        }else {
            return REFERRAL_STATUS_REJECTED;
        }
    }

}