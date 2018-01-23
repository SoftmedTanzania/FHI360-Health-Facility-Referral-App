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
import android.widget.TextView;

import apps.softmed.com.hfreferal.activities.NewReferalsActivity;
import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.activities.ReferedClientsActivity;
import apps.softmed.com.hfreferal.activities.ReferralListActivity;
import apps.softmed.com.hfreferal.base.AppDatabase;
import apps.softmed.com.hfreferal.base.BaseActivity;

import static apps.softmed.com.hfreferal.utils.constants.HIV_SERVICE_ID;
import static apps.softmed.com.hfreferal.utils.constants.SOURCE_CHW;
import static apps.softmed.com.hfreferal.utils.constants.SOURCE_HF;

/**
 * Created by issy on 12/4/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class HivFragment extends Fragment {

    private TextView referalCountText, referalFeedbackCount, chwReferralCounts, hfReferralCount;
    private CardView referalListCard, referedClientsCard, newReferalsCard;

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

        new ReferalCountsTask().execute();

        referalListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HivFragment.this.getActivity(), ReferralListActivity.class);
                intent.putExtra("service", HIV_SERVICE_ID);
                startActivity(intent);
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
        referedClientsCard = (CardView) view.findViewById(R.id.refered_clients_card);
        referalListCard = (CardView) view.findViewById(R.id.referal_list_card);
        newReferalsCard = (CardView) view.findViewById(R.id.new_referals_card);

    }

    private class ReferalCountsTask extends AsyncTask<Void, Void, Void> {

        String referralCounts = "";
        String feedbackCount = "";

        @Override
        protected Void doInBackground(Void... voids) {
            referralCounts = database.referalModel().geCounttUnattendedReferalsByService(HIV_SERVICE_ID)+" New referrals unattended";
            feedbackCount = "Pending Feedback : "+database.referalModel().geCountPendingReferalFeedback(HIV_SERVICE_ID, BaseActivity.session.getKeyHfid());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            referalCountText.setText(referralCounts);
            referalFeedbackCount.setText(feedbackCount);
            super.onPostExecute(aVoid);
        }

    }


}
