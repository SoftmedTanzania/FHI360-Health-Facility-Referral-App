package com.softmed.htmr_facility_staging.fragments.reports;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.softmed.htmr_facility_staging.R;
import com.softmed.htmr_facility_staging.base.BaseActivity;
import com.softmed.htmr_facility_staging.dom.objects.ReferralServiceIndicators;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.softmed.htmr_facility_staging.utils.constants.FEMALE;
import static com.softmed.htmr_facility_staging.utils.constants.INTRAFACILITY;
import static com.softmed.htmr_facility_staging.utils.constants.MALE;
import static com.softmed.htmr_facility_staging.utils.constants.REFERRAL_STATUS_COMPLETED;
import static com.softmed.htmr_facility_staging.utils.constants.REFERRAL_STATUS_NEW;

/**
 * Created by issy on 13/03/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class IntraFacilityReferralsReportFragment extends Fragment {

    TableLayout servicesTable;
    LinearLayout dateFromLayout, dateToLayout;
    Button applyDateRangeButton;
    TextView dateRangeFromText, dateRangeToText;
    DatePickerDialog fromDatePickerDialog = new DatePickerDialog();
    DatePickerDialog toDatePickerDialog = new DatePickerDialog();

    LayoutInflater layoutInflater;

    Calendar cal;
    long dateFromInMillis, dateToInMillis;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_referrals_report, container, false);
        layoutInflater = inflater;
        setUpViews(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateFromLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null){
                    fromDatePickerDialog.show(getActivity().getFragmentManager(),"fromDateRange");
                }
                fromDatePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        dateRangeFromText.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        dateFromInMillis = cal.getTimeInMillis();
                    }

                });
            }
        });


        dateToLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null){
                    toDatePickerDialog.show(getActivity().getFragmentManager(),"toDateRange");
                }
                toDatePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        dateRangeToText.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        dateToInMillis = cal.getTimeInMillis();
                    }

                });
            }
        });

        applyDateRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReportData();
            }
        });

    }

    private void loadReportData(){

        new AsyncTask<Void, Void, Void>(){

            List<ReferralServiceIndicators> listOfIndicators = new ArrayList<>();
            List<RowView> rowViewList = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... voids) {
                listOfIndicators = BaseActivity.baseDatabase.referralServiceIndicatorsDao().getAllServices();
                for (ReferralServiceIndicators indicator : listOfIndicators){

                    RowView rowView = new RowView();

                    int totalReceivedReferralsMaleCount = 0;
                    int totalReceivedReferralsFemaleCount = 0;
                    int totalReceivedReferralsCount = 0;
                    int totalAttendedReferralsMale = 0;
                    int totalAttendedReferralsFemale = 0;
                    int totalAttendedReferrals = 0;
                    int totalUnattendedReferralsMale = 0;
                    int totalUnattendedReferralsFemale = 0;
                    int totalUnattendedReferrals = 0;

                    totalReceivedReferralsMaleCount = BaseActivity.baseDatabase.referalModel().
                            getAllReferralsByServcieAndDateRange(
                                    indicator.getServiceId(),
                                    dateFromInMillis,
                                    dateToInMillis,
                                    INTRAFACILITY,
                                    MALE);

                    totalReceivedReferralsFemaleCount = BaseActivity.baseDatabase.referalModel().
                            getAllReferralsByServcieAndDateRange(
                                    indicator.getServiceId(),
                                    dateFromInMillis,
                                    dateToInMillis,
                                    INTRAFACILITY,
                                    FEMALE);

                    totalReceivedReferralsCount = totalReceivedReferralsMaleCount + totalReceivedReferralsFemaleCount;


                    totalAttendedReferralsMale = BaseActivity.baseDatabase.referalModel().
                            getAllReferralsByServcieDateRangeAndReferralStatus(
                                    indicator.getServiceId(),
                                    dateFromInMillis,
                                    dateToInMillis,
                                    INTRAFACILITY,
                                    MALE,
                                    REFERRAL_STATUS_COMPLETED);

                    totalAttendedReferralsFemale = BaseActivity.baseDatabase.referalModel().
                            getAllReferralsByServcieDateRangeAndReferralStatus(
                                    indicator.getServiceId(),
                                    dateFromInMillis,
                                    dateToInMillis,
                                    INTRAFACILITY,
                                    FEMALE,
                                    REFERRAL_STATUS_COMPLETED);

                    totalAttendedReferrals = totalAttendedReferralsMale + totalAttendedReferralsFemale;

                    totalUnattendedReferralsMale = BaseActivity.baseDatabase.referalModel().
                            getAllReferralsByServcieDateRangeAndReferralStatus(
                                    indicator.getServiceId(),
                                    dateFromInMillis,
                                    dateToInMillis,
                                    INTRAFACILITY,
                                    MALE,
                                    REFERRAL_STATUS_NEW);

                    totalUnattendedReferralsFemale = BaseActivity.baseDatabase.referalModel().
                            getAllReferralsByServcieDateRangeAndReferralStatus(
                                    indicator.getServiceId(),
                                    dateFromInMillis,
                                    dateToInMillis,
                                    INTRAFACILITY,
                                    FEMALE,
                                    REFERRAL_STATUS_NEW);

                    totalUnattendedReferrals = totalUnattendedReferralsMale + totalUnattendedReferralsFemale;

                    rowView.setServiceName(indicator.getServiceName());
                    rowView.setTotalReceivedReferrals(totalReceivedReferralsCount);
                    rowView.setTotalReceivedReferralsFemale(totalReceivedReferralsFemaleCount);
                    rowView.setTotalReveivedReferralsMale(totalReceivedReferralsMaleCount);
                    rowView.setAttendedReferrals(totalAttendedReferrals);
                    rowView.setAttendedReferralsMale(totalAttendedReferralsMale);
                    rowView.setAttendedReferralsFemale(totalAttendedReferralsFemale);
                    rowView.setUnattendedReferrals(totalUnattendedReferrals);
                    rowView.setUnattendedReferralsFemale(totalUnattendedReferralsFemale);
                    rowView.setUnattendedReferralsMale(totalUnattendedReferralsMale);

                    rowViewList.add(rowView);

                    Log.d("referralReport", "Total Received | "+indicator.getServiceName()+" = Male :: "+totalReceivedReferralsMaleCount+" Female :: "+totalReceivedReferralsFemaleCount);

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                for (RowView row : rowViewList){

                    View tableview = View.inflate(IntraFacilityReferralsReportFragment.this.getActivity(), R.layout.single_service_report_table_item, null);

                    TextView serviceName = (TextView) tableview.findViewById(R.id.service_name);

                    TextView maleTotal  = (TextView) tableview.findViewById(R.id.male_total);
                    TextView femaleTotal = (TextView) tableview.findViewById(R.id.female_total);
                    TextView total = (TextView) tableview.findViewById(R.id.total);

                    TextView maleAttendedCount = (TextView) tableview.findViewById(R.id.male_attended);
                    TextView femaleAttendedCount = (TextView) tableview.findViewById(R.id.female_attended);
                    TextView totalAttended = (TextView) tableview.findViewById(R.id.total_attended);

                    TextView maleUnattended = (TextView) tableview.findViewById(R.id.male_pending);
                    TextView femaleUnattended = (TextView) tableview.findViewById(R.id.female_pending);
                    TextView totalUnattended = (TextView) tableview.findViewById(R.id.total_pending);

                    serviceName.setText(row.getServiceName());

                    maleTotal.setText(row.getTotalReveivedReferralsMale()+"");
                    femaleTotal.setText(row.getTotalReceivedReferralsFemale()+"");
                    total.setText(row.getTotalReceivedReferrals()+"");

                    maleAttendedCount.setText(row.getAttendedReferralsMale()+"");
                    femaleAttendedCount.setText(row.getAttendedReferralsFemale()+"");
                    totalAttended.setText(row.getAttendedReferrals()+"");

                    maleUnattended.setText(row.getUnattendedReferralsMale()+"");
                    femaleUnattended.setText(row.getUnattendedReferralsFemale()+"");
                    totalUnattended.setText(row.getUnattendedReferrals()+"");

                    servicesTable.addView(tableview);

                }

            }
        }.execute();

    }

    private void setUpViews(View view){
        servicesTable = (TableLayout) view.findViewById(R.id.services_table);
        dateFromLayout = (LinearLayout) view.findViewById(R.id.range_from);
        dateToLayout = (LinearLayout) view.findViewById(R.id.range_to);
        applyDateRangeButton = (Button) view.findViewById(R.id.apply_date_range_button);
        dateRangeFromText = (TextView) view.findViewById(R.id.date_range_from);
        dateRangeToText = (TextView) view.findViewById(R.id.date_range_to);
    }

    class RowView{

        String serviceName;

        int totalReveivedReferralsMale, totalReceivedReferralsFemale, totalReceivedReferrals;
        int attendedReferralsMale, attendedReferralsFemale, attendedReferrals;
        int unattendedReferralsMale, unattendedReferralsFemale, unattendedReferrals;

        RowView(){}

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public int getTotalReveivedReferralsMale() {
            return totalReveivedReferralsMale;
        }

        public void setTotalReveivedReferralsMale(int totalReveivedReferralsMale) {
            this.totalReveivedReferralsMale = totalReveivedReferralsMale;
        }

        public int getTotalReceivedReferralsFemale() {
            return totalReceivedReferralsFemale;
        }

        public void setTotalReceivedReferralsFemale(int totalReceivedReferralsFemale) {
            this.totalReceivedReferralsFemale = totalReceivedReferralsFemale;
        }

        public int getTotalReceivedReferrals() {
            return totalReceivedReferrals;
        }

        public void setTotalReceivedReferrals(int totalReceivedReferrals) {
            this.totalReceivedReferrals = totalReceivedReferrals;
        }

        public int getAttendedReferralsMale() {
            return attendedReferralsMale;
        }

        public void setAttendedReferralsMale(int attendedReferralsMale) {
            this.attendedReferralsMale = attendedReferralsMale;
        }

        public int getAttendedReferralsFemale() {
            return attendedReferralsFemale;
        }

        public void setAttendedReferralsFemale(int attendedReferralsFemale) {
            this.attendedReferralsFemale = attendedReferralsFemale;
        }

        public int getAttendedReferrals() {
            return attendedReferrals;
        }

        public void setAttendedReferrals(int attendedReferrals) {
            this.attendedReferrals = attendedReferrals;
        }

        public int getUnattendedReferralsMale() {
            return unattendedReferralsMale;
        }

        public void setUnattendedReferralsMale(int unattendedReferralsMale) {
            this.unattendedReferralsMale = unattendedReferralsMale;
        }

        public int getUnattendedReferralsFemale() {
            return unattendedReferralsFemale;
        }

        public void setUnattendedReferralsFemale(int unattendedReferralsFemale) {
            this.unattendedReferralsFemale = unattendedReferralsFemale;
        }

        public int getUnattendedReferrals() {
            return unattendedReferrals;
        }

        public void setUnattendedReferrals(int unattendedReferrals) {
            this.unattendedReferrals = unattendedReferrals;
        }
    }

}
