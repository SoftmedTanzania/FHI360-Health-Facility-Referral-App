package apps.softmed.com.hfreferal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.softmed.com.hfreferal.NewReferalsActivity;
import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.ReferedClientsActivity;
import apps.softmed.com.hfreferal.TbReferalListActivity;
import apps.softmed.com.hfreferal.TbRegisterActivity;
import apps.softmed.com.hfreferal.utils.constants;

/**
 * Created by issy on 12/4/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbFragment extends Fragment {

    CardView referalListCard, newReferalsCard, referredListCard, tbRegister;

    public TbFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView    = inflater.inflate(R.layout.fragment_tb, container, false);
        setUpView(rootView);

        referalListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TbFragment.this.getActivity(), TbReferalListActivity.class));
            }
        });

        newReferalsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TbFragment.this.getActivity(), NewReferalsActivity.class));
            }
        });


        referredListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TbFragment.this.getActivity(), ReferedClientsActivity.class);
                intent.putExtra("service_id",  constants.TB_SERVICE_ID);
                startActivity(intent); //TODO: Implement Service Id in querying List of referred clients
            }
        });

        tbRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TbFragment.this.getActivity(), TbRegisterActivity.class));
            }
        });

        return rootView;
    }

    public void setUpView(View v){
        referalListCard = (CardView) v.findViewById(R.id.tb_referal_list_card);
        newReferalsCard = (CardView) v.findViewById(R.id.tb_new_referals_card);
        referredListCard = (CardView) v.findViewById(R.id.tb_refered_clients_card);
        tbRegister = (CardView) v.findViewById(R.id.tb_register);
    }

}
