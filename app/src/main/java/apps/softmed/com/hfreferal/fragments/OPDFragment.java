package apps.softmed.com.hfreferal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.softmed.com.hfreferal.R;

/**
 * Created by issy on 12/4/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class OPDFragment extends Fragment {

    CardView opdRegistrationCard, opdReferralListCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView    = inflater.inflate(R.layout.fragment_opd, container, false);
        setUpView(rootView);

        opdRegistrationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        opdReferralListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rootView;
    }

    public void setUpView(View v){
        opdRegistrationCard = (CardView) v.findViewById(R.id.opd_registration_card);
        opdReferralListCard = (CardView) v.findViewById(R.id.opd_referral_list_card);
    }

}
