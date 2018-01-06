package apps.softmed.com.hfreferal;

import android.os.Bundle;
import android.support.annotation.Nullable;

import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;
import apps.softmed.com.hfreferal.dom.objects.Patient;
import apps.softmed.com.hfreferal.dom.objects.Referal;

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

    private void createDummyPatientData(){

        Patient dummyPatient = new Patient();
        dummyPatient.setId(Long.valueOf("1"));
        dummyPatient.setPatientId("0001");
        dummyPatient.setPatientFirstName("John");
        dummyPatient.setPatientSurname("Doe");
        dummyPatient.setPhone_number("+255755688678");
        dummyPatient.setWard("Msolwa");
        dummyPatient.setVillage("Mlalakuwa");
        dummyPatient.setHamlet("Kwa Mwinyijuma");
        //dummyPatient.setDateOfBirth(new Date());
        dummyPatient.setGender("male");
        //dummyPatient.setCreatedAt(new Date());
        //dummyPatient.setUpdatedAt(new Date());

        Patient dummyPatient2 = new Patient();
        dummyPatient2.setId(Long.valueOf("2"));
        dummyPatient2.setPatientId("0002");
        dummyPatient2.setPatientFirstName("Larry");
        dummyPatient2.setPatientSurname("Smith");
        dummyPatient2.setPhone_number("+255683321768");
        dummyPatient2.setWard("Mwika");
        dummyPatient2.setVillage("Mkeka");
        dummyPatient2.setHamlet("Julio");
        //dummyPatient2.setDateOfBirth(new Date());
        dummyPatient2.setGender("female");
        //dummyPatient2.setCreatedAt(new Date());
        //dummyPatient2.setUpdatedAt(new Date());

        database.patientModel().addPatient(dummyPatient);
        database.patientModel().addPatient(dummyPatient2);

    }

    private  void createDummyReferralData(){

        Referal dummyReferral = new Referal();
        dummyReferral.setId(Long.valueOf("1"));
        dummyReferral.setReferral_id("0001");
        dummyReferral.setPatient_id("0001");
        dummyReferral.setCommunityBasedHivService("Madale");
        dummyReferral.setReferralReason("Coughing alot");
        dummyReferral.setServiceId(1);
        dummyReferral.setCtcNumber("00001/0001/00001");
        dummyReferral.setHas2WeeksCough(true);
        dummyReferral.setHasBloodCough(false);
        dummyReferral.setHasSevereSweating(true);
        dummyReferral.setHasFever(true);
        dummyReferral.setHadWeightLoss(true);
        dummyReferral.setServiceProviderUIID("123456");
        dummyReferral.setServiceProviderGroup("none");
        dummyReferral.setVillageLeader("Baraka");
//        dummyReferral.setReferralDate(new Date());
        dummyReferral.setFacilityId("001");
        dummyReferral.setFromFacilityId("002");
        dummyReferral.setReferralStatus(0);
//        dummyReferral.setCreatedAt(new Date());
//        dummyReferral.setUpdatedAt(new Date());

        Referal dummyReferral2 = new Referal();
        dummyReferral2.setId(Long.valueOf("2"));
        dummyReferral2.setReferral_id("0002");
        dummyReferral2.setPatient_id("0002");
        dummyReferral2.setCommunityBasedHivService("Kigoma");
        dummyReferral2.setReferralReason("Fever");
        dummyReferral2.setServiceId(2);
        dummyReferral2.setCtcNumber("00003/0003/00012");
        dummyReferral2.setHas2WeeksCough(true);
        dummyReferral2.setHasBloodCough(false);
        dummyReferral2.setHasSevereSweating(true);
        dummyReferral2.setHasFever(true);
        dummyReferral2.setHadWeightLoss(true);
        dummyReferral2.setServiceProviderUIID("33556");
        dummyReferral2.setServiceProviderGroup("none");
        dummyReferral2.setVillageLeader("Mugezwa");
//        dummyReferral2.setReferralDate(new Date());
        dummyReferral2.setFacilityId("001");
        dummyReferral2.setFromFacilityId("002");
        dummyReferral2.setReferralStatus(0);
//        dummyReferral2.setCreatedAt(new Date());
//        dummyReferral2.setUpdatedAt(new Date());

        Referal dummyReferral3 = new Referal();
        dummyReferral3.setReferral_id("0003");
        dummyReferral3.setId(Long.valueOf("3"));
        dummyReferral3.setPatient_id("0002");
        dummyReferral3.setCommunityBasedHivService("Muheza");
        dummyReferral3.setReferralReason("Coughing alot");
        dummyReferral3.setServiceId(3);
        dummyReferral3.setCtcNumber("00012/0003/00242");
        dummyReferral3.setHas2WeeksCough(false);
        dummyReferral3.setHasBloodCough(true);
        dummyReferral3.setHasSevereSweating(false);
        dummyReferral3.setHasFever(false);
        dummyReferral3.setHadWeightLoss(true);
        dummyReferral3.setServiceProviderUIID("4556");
        dummyReferral3.setServiceProviderGroup("group 8");
        dummyReferral3.setVillageLeader("Maheba");
//        dummyReferral3.setReferralDate(new Date());
        dummyReferral3.setFacilityId("002");
        dummyReferral3.setFromFacilityId("001");
        dummyReferral3.setReferralStatus(1);
//        dummyReferral3.setCreatedAt(new Date());
//        dummyReferral3.setUpdatedAt(new Date());

        Referal dummyReferral4 = new Referal();
        dummyReferral4.setReferral_id("0004");
        dummyReferral4.setId(Long.valueOf("4"));
        dummyReferral4.setPatient_id("0001");
        dummyReferral4.setCommunityBasedHivService("Muheza");
        dummyReferral4.setReferralReason("Night fevers, coughing a lot and loss of weight rapidly in the last two months");
        dummyReferral4.setServiceId(1);
        dummyReferral4.setCtcNumber("00017/0003/00243");
        dummyReferral4.setHas2WeeksCough(false);
        dummyReferral4.setHasBloodCough(true);
        dummyReferral4.setHasSevereSweating(false);
        dummyReferral4.setHasFever(false);
        dummyReferral4.setHadWeightLoss(true);
        dummyReferral4.setServiceProviderUIID("4556");
        dummyReferral4.setServiceProviderGroup("group 8");
        dummyReferral4.setVillageLeader("Maheba");
//        dummyReferral4.setReferralDate(new Date());
        dummyReferral4.setFacilityId("002");
        dummyReferral4.setFromFacilityId("001");
        dummyReferral4.setReferralStatus(1);
//        dummyReferral4.setCreatedAt(new Date());
//        dummyReferral4.setUpdatedAt(new Date());

        database.referalModel().addReferal(dummyReferral);
        database.referalModel().addReferal(dummyReferral2);
        database.referalModel().addReferal(dummyReferral3);
        database.referalModel().addReferal(dummyReferral4);

    }

}
