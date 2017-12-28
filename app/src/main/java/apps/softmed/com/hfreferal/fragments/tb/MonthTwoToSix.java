package apps.softmed.com.hfreferal.fragments.tb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import apps.softmed.com.hfreferal.R;
import fr.ganfra.materialspinner.MaterialSpinner;

import static apps.softmed.com.hfreferal.utils.constants.TB_1_PLUS;
import static apps.softmed.com.hfreferal.utils.constants.TB_2_PLUS;
import static apps.softmed.com.hfreferal.utils.constants.TB_3_PLUS;
import static apps.softmed.com.hfreferal.utils.constants.TB_NEGATIVE;
import static apps.softmed.com.hfreferal.utils.constants.TB_SCANTY;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_1;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_2;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_3;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_4;
import static apps.softmed.com.hfreferal.utils.constants.TREATMENT_TYPE_5;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class MonthTwoToSix extends Fragment {

    private MaterialSpinner makohoziSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView    = inflater.inflate(R.layout.fragment_month_two, container, false);
        setUpView(rootView);

        final String[] tbTypes = {TB_NEGATIVE, TB_SCANTY, TB_1_PLUS, TB_2_PLUS, TB_3_PLUS};
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(MonthTwoToSix.this.getActivity(), R.layout.simple_spinner_item_black, tbTypes);
        spinAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_black);
        makohoziSpinner.setAdapter(spinAdapter);

        return rootView;
    }

    public void setUpView(View v){
        makohoziSpinner = (MaterialSpinner) v.findViewById(R.id.spin_makohozi);
    }

}
