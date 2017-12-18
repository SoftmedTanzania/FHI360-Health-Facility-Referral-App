package apps.softmed.com.hfreferal.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import apps.softmed.com.hfreferal.NewReferalsActivity;
import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.ReferalListActivityOld;
import apps.softmed.com.hfreferal.ReferedClientsActivity;
import apps.softmed.com.hfreferal.ReferralListActivity;
import apps.softmed.com.hfreferal.base.AppDatabase;

import static apps.softmed.com.hfreferal.base.BaseActivity.Avenir;
import static apps.softmed.com.hfreferal.base.BaseActivity.Julius;
import static apps.softmed.com.hfreferal.utils.constants.HIV_SERVICE_ID;

/**
 * Created by issy on 12/4/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class HivFragment extends Fragment {

    private TextView referalListText, referedClientsText, newReferalText, hivText, tbText, tbReferalListText, tbReferedClientsText, tbReferNewClientsText;
    private TextView referalCountText, referalFeedbackCount, toolbarTitle, chwReferralCounts, hfReferralCount;
    private CardView referalListCard, referedClientsCard, newReferalsCard;
    private ImageView tbReferalListIcon, tbReferedClientsIcon, tbNewReferalsIcon;

    private Context context;

    AppDatabase database;

    public HivFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabase(HivFragment.this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView    = inflater.inflate(R.layout.fragment_hiv, container, false);

        setupviews(rootView);

        ReferalCountsTask referalCountsTask = new ReferalCountsTask();
        referalCountsTask.execute();

        referalListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HivFragment.this.getActivity(), ReferralListActivity.class));
            }
        });

        referedClientsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HivFragment.this.getActivity(), ReferedClientsActivity.class);
                intent.putExtra("service_id", HIV_SERVICE_ID);
                startActivity(intent);
            }
        });

        newReferalsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HivFragment.this.getActivity(), NewReferalsActivity.class));
            }
        });

        return rootView;
    }

    private void setupviews(View view){

        chwReferralCounts = (TextView) view.findViewById(R.id.chw_referal_count_text);

        hfReferralCount = (TextView) view.findViewById(R.id.hf_referal_count_text);

        referalCountText = (TextView) view.findViewById(R.id.referal_count_text);

        referalFeedbackCount = (TextView) view.findViewById(R.id.referal_feedback_count);

        referalListText = (TextView) view.findViewById(R.id.referal_list_text);
        referedClientsText = (TextView) view.findViewById(R.id.refered_clients_text);
        newReferalText = (TextView) view.findViewById(R.id.new_referals_text);

        referedClientsCard = (CardView) view.findViewById(R.id.refered_clients_card);
        referalListCard = (CardView) view.findViewById(R.id.referal_list_card);
        newReferalsCard = (CardView) view.findViewById(R.id.new_referals_card);

    }

    private class ReferalCountsTask extends AsyncTask<Void, Void, Void> {

        String referralCounts = "";

        @Override
        protected Void doInBackground(Void... voids) {
            referralCounts = database.referalModel().geCounttUnattendedReferals(HIV_SERVICE_ID)+" New referrals unattended";
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            referalCountText.setText(referralCounts);
            super.onPostExecute(aVoid);
        }

    }


}
