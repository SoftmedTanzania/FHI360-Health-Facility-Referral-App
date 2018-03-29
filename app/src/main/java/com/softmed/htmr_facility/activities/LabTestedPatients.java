package com.softmed.htmr_facility.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.AppDatabase;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.viewmodels.ReferalListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by issy on 27/03/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class LabTestedPatients extends BaseActivity {

    RecyclerView testedPatientsRecyclerView;
    Toolbar toolbar;

    TestedClientsAdapter adapter;
    ReferalListViewModel listViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tested_patients);
        setupview();

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        adapter = new TestedClientsAdapter(new ArrayList<Referral>(), this);
        listViewModel = ViewModelProviders.of(this).get(ReferalListViewModel.class);
        testedPatientsRecyclerView.setAdapter(adapter);

        //Get all tested referrals
        listViewModel.getAllTestedClients().observe(LabTestedPatients.this, new Observer<List<Referral>>() {
            @Override
            public void onChanged(@Nullable List<Referral> referrals) {
                Log.d("enthroned", "Size of tested : "+referrals.size());
                adapter.addItems(referrals);
            }
        });

    }

    void setupview(){
        testedPatientsRecyclerView = findViewById(R.id.tested_clients_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        testedPatientsRecyclerView.setLayoutManager(layoutManager);
        testedPatientsRecyclerView.setHasFixedSize(true);

        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class TestedClientsAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

        private List<Referral> items;
        private Context context;
        private AppDatabase database;
        private ListViewItemViewHolder mViewHolder;


        public TestedClientsAdapter(List<Referral> mItems, Context context){
            this.items = mItems;
            this.database = AppDatabase.getDatabase(context);
        }

        public TestedClientsAdapter(){}

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            context         = viewGroup.getContext();
            View itemView   = null;
            itemView = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.tested_clients_list_item, viewGroup, false);

            return new ListViewItemViewHolder(itemView);

        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int itemPosition){

            final Referral referral = getItem(itemPosition);
            ListViewItemViewHolder holder = (ListViewItemViewHolder) viewHolder;
            mViewHolder = holder;

            new patientDetailsTask(database, referral, holder.clientsNames, holder.testConducted).execute();

            if (!referral.isTestResults()){
                holder.testResult.setText(getResources().getString(R.string.negative));
                holder.testResult.setTextColor(context.getResources().getColor(R.color.green_a700));
            }else {
                holder.testResult.setText(getResources().getString(R.string.positive));
                holder.testResult.setTextColor(context.getResources().getColor(R.color.red_600));
            }

            holder.testDate.setText(BaseActivity.simpleDateFormat.format(referral.getReferralDate()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LabTestDetailsActivity.class);
                    intent.putExtra("referal", referral);
                    context.startActivity(intent);
                }
            });

        }

        public void addItems (List<Referral> referralList){
            this.items = referralList;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount(){
            return items.size();
//        return 10;
        }

        private Referral getItem(int position){
            return items.get(position);
        }

        private class ListViewItemViewHolder extends RecyclerView.ViewHolder {

            TextView clientsNames, testResult, testConducted, testDate;
            View viewItem;

            public ListViewItemViewHolder(View itemView){
                super(itemView);
                this.viewItem   = itemView;

                clientsNames = (TextView) itemView.findViewById(R.id.client_name);
                testResult = (TextView) itemView.findViewById(R.id.test_results);
                testConducted = (TextView) itemView.findViewById(R.id.test_conducted);
                testDate = (TextView) itemView.findViewById(R.id.test_date);

            }

        }

        class patientDetailsTask extends AsyncTask<Void, Void, Void> {

            String patientNames, serviceNameString;
            AppDatabase db;
            TextView mText, _testConducted;
            Referral ref;

            patientDetailsTask(AppDatabase database, Referral referral, TextView namesInstance, TextView testConducted){
                this.db = database;
                this.ref = referral;
                mText = namesInstance;
                _testConducted = testConducted;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                Log.d("reckless", "doing name search backgroundically!");
                patientNames = db.patientModel().getPatientName(ref.getPatient_id());
                serviceNameString = db.referralServiceIndicatorsDao().getServiceNameById(ref.getLabTest());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d("reckless", "Done background !"+patientNames);
                mText.setText(patientNames);
                _testConducted.setText(serviceNameString);
                //adapter.notifyDataSetChanged();
            }

        }


    }


}
