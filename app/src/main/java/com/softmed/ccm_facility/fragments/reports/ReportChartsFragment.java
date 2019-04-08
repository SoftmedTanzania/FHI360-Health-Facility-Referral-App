package com.softmed.ccm_facility.fragments.reports;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.softmed.ccm_facility.R;
import com.softmed.ccm_facility.base.BaseActivity;
import com.softmed.ccm_facility.dom.objects.ReferralServiceIndicators;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.softmed.ccm_facility.utils.constants.CHW_TO_FACILITY;
import static com.softmed.ccm_facility.utils.constants.INTERFACILITY;
import static com.softmed.ccm_facility.utils.constants.INTRAFACILITY;

/**
 * Created by issy on 14/03/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ReportChartsFragment extends Fragment {

    BarChart chwBarChart, interFacilityBarChart;
    PieChart intrafacilityPieChart;
    ImageView dateFromIcon, dateToIcon;
    LinearLayout rangeFrom, rangeTo;
    DatePickerDialog fromDatePickerDialog = new DatePickerDialog();
    DatePickerDialog toDatePickerDialog = new DatePickerDialog();
    TextView dateRangeFromText, dateRangeToText;

    Calendar cal;
    long dateFromInMillis, dateToInMillis;
    boolean isFirstDateSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_charts, container, false);
        setupviews(rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateFromIcon.setColorFilter(getResources().getColor(R.color.card_title_text));
        dateToIcon.setColorFilter(getResources().getColor(R.color.card_title_text));

        rangeFrom.setOnClickListener(new View.OnClickListener() {
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
                        if (!isFirstDateSelected) isFirstDateSelected = true;
                        else loadGraphData();
                    }

                });
            }
        });

        rangeTo.setOnClickListener(new View.OnClickListener() {
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
                        if (!isFirstDateSelected) isFirstDateSelected = true;
                        else loadGraphData();
                    }

                });
            }
        });

    }

    private void setupviews(View v){
        chwBarChart = (BarChart) v.findViewById(R.id.chw_referrals_bar_chart);
        interFacilityBarChart = (BarChart) v.findViewById(R.id.interfacility_bar_chart);
        intrafacilityPieChart = (PieChart) v.findViewById(R.id.intrafacility_pie_chart);

        intrafacilityPieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        intrafacilityPieChart.setHoleRadius(60f);
        intrafacilityPieChart.setTransparentCircleAlpha(5);
        intrafacilityPieChart.setCenterText("Intra-facility Summary");
        intrafacilityPieChart.setCenterTextSize(16);
        intrafacilityPieChart.setCenterTextTypeface(BaseActivity.Avenir_Light);
        intrafacilityPieChart.getDescription().setEnabled(false);

        //chwBarChart.getAxisLeft().setDrawGridLines(false);
        chwBarChart.getAxisRight().setDrawGridLines(false);
        chwBarChart.getXAxis().setDrawGridLines(false);
        chwBarChart.setDrawValueAboveBar(false);
        chwBarChart.getDescription().setEnabled(false);
        chwBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        interFacilityBarChart.getAxisRight().setDrawGridLines(false);
        interFacilityBarChart.getXAxis().setDrawGridLines(false);
        interFacilityBarChart.setDrawValueAboveBar(false);
        interFacilityBarChart.getDescription().setEnabled(false);
        interFacilityBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        //chwBarChart.setDrawBarShadow(false);
        //chwBarChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        //chwBarChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        //chwBarChart.setPinchZoom(false);
        //chwBarChart.setDrawGridBackground(false);
        chwBarChart.animateY(5000);

        dateFromIcon = (ImageView) v.findViewById(R.id.date_from_icon);
        dateToIcon = (ImageView) v.findViewById(R.id.date_to_icon);
        rangeFrom = (LinearLayout) v.findViewById(R.id.range_from);
        rangeTo = (LinearLayout) v.findViewById(R.id.range_to);
        dateRangeFromText = (TextView) v.findViewById(R.id.date_range_from);
        dateRangeToText = (TextView) v.findViewById(R.id.date_range_to);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadGraphData(){
        new AsyncTask<Void, Void, Void>(){

            List<RowView> rowViewList = new ArrayList<>();
            List<RowView> interfacilityRowViewList = new ArrayList<>();
            List<RowView> intrafacilityRowViewList = new ArrayList<>();
            List<ReferralServiceIndicators> listOfIndicators = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... voids) {

                Log.d("graph_data", "Loading graph data on the background");

                listOfIndicators = BaseActivity.baseDatabase.referralServiceIndicatorsDao().getAllServices();

                for (ReferralServiceIndicators indicator : listOfIndicators){
                    RowView rowView = new RowView();
                    RowView interfacilityRowView = new RowView();
                    RowView intrafacilityRowView = new RowView();

                    int totalReceivedReferralsCount = 0;
                    int totalReceivedInterfacilityReferralCount = 0;
                    int totalIntrafacilityReferralCount = 0;

                    totalReceivedReferralsCount = BaseActivity.baseDatabase.referalModel().
                            getTotalReceivedReferralsByServiceID(
                                    indicator.getServiceId(),
                                    dateFromInMillis,
                                    dateToInMillis,
                                    CHW_TO_FACILITY);

                    totalReceivedInterfacilityReferralCount = BaseActivity.baseDatabase.referalModel().
                            getTotalReceivedReferralsByServiceID(
                                    indicator.getServiceId(),
                                    dateFromInMillis,
                                    dateToInMillis,
                                    INTERFACILITY);

                    totalIntrafacilityReferralCount = BaseActivity.baseDatabase.referalModel().
                            getTotalReceivedReferralsByServiceID(
                                    indicator.getServiceId(),
                                    dateFromInMillis,
                                    dateToInMillis,
                                    INTRAFACILITY);

                    rowView.setRowName(indicator.getServiceName());
                    rowView.setReferralCount(totalReceivedReferralsCount);
                    rowViewList.add(rowView);

                    interfacilityRowView.setRowName(indicator.getServiceName());
                    interfacilityRowView.setReferralCount(totalReceivedInterfacilityReferralCount);
                    interfacilityRowViewList.add(interfacilityRowView);

                    intrafacilityRowView.setRowName(indicator.getServiceName());
                    intrafacilityRowView.setReferralCount(totalIntrafacilityReferralCount);
                    intrafacilityRowViewList.add(intrafacilityRowView);

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                List<RowView> newColums = new ArrayList<>();
                List<RowView> newInterfacilityColumns = new ArrayList<>();

                ArrayList<BarEntry> entries = new ArrayList<>();
                ArrayList<BarEntry> interfacilityEntries = new ArrayList<>();
                ArrayList<BarEntry> intrafacilityEntries = new ArrayList<>();

                ArrayList<String> labels = new ArrayList<String>();
                ArrayList<String> interfacilityLabels = new ArrayList<String>();
                ArrayList<String> intrafacilityLabels = new ArrayList<String>();

                for (int i = 0; i<rowViewList.size();i++){
                    RowView rowView = rowViewList.get(i);
                    if (rowView.getReferralCount() > 0){
                        newColums.add(rowView);
                    }
                }

                for (int i=0; i<newColums.size(); i++){
                    entries.add(new BarEntry(i, newColums.get(i).getReferralCount()));
                    labels.add(newColums.get(i).getRowName());
                    Log.d("charts", "Added to new is  : "+labels.get(i));
                }

                for (int i=0; i<interfacilityRowViewList.size(); i++){
                    RowView rowView = interfacilityRowViewList.get(i);
                    if (rowView.getReferralCount() > 0){
                        newInterfacilityColumns.add(rowView);
                    }
                }

                for (int i=0; i<newInterfacilityColumns.size(); i++){
                    interfacilityEntries.add(new BarEntry(i, newInterfacilityColumns.get(i).getReferralCount()));
                    interfacilityLabels.add(newInterfacilityColumns.get(i).getRowName());
                    Log.d("charts", "Added to newInterfacilityColumns is  : "+interfacilityLabels.get(i));
                }

                ArrayList<PieEntry> yEntrys = new ArrayList<>();
                ArrayList<String> xEntrys = new ArrayList<>();

                for (int j=0; j<intrafacilityRowViewList.size(); j++){
                    RowView rowView = intrafacilityRowViewList.get(j);
                    if (rowView.getReferralCount() > 0){
                        yEntrys.add(new PieEntry(rowView.getReferralCount() , rowView.getRowName()));
                        //intrafacilityEntries.add(new BarEntry(j, rowView.getReferralCount()));
                        //intrafacilityLabels.add(rowView.getRowName());
                    }
                }

                BarDataSet set1 = new BarDataSet(entries, getString(R.string.services));
                BarDataSet set2 = new BarDataSet(interfacilityEntries, getString(R.string.services));
                //BarDataSet set3 = new BarDataSet(intrafacilityEntries, "Services");

                PieDataSet pieDataSet = new PieDataSet(yEntrys, getResources().getString(R.string.service));
                pieDataSet.setSliceSpace(2);
                pieDataSet.setValueTextSize(12);

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                Legend legend = intrafacilityPieChart.getLegend();
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

                //create pie data object
                PieData pieData = new PieData(pieDataSet);
                intrafacilityPieChart.setData(pieData);
                intrafacilityPieChart.invalidate();

                set1.setColors(ColorTemplate.MATERIAL_COLORS);
                set2.setColors(ColorTemplate.MATERIAL_COLORS);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                ArrayList<IBarDataSet> interfacilityChartDataSets = new ArrayList<IBarDataSet>();

                dataSets.add(set1);
                interfacilityChartDataSets.add(set2);

                BarData data = new BarData(dataSets);
                BarData interfacilityData = new BarData(interfacilityChartDataSets);

                //data.setValueTextSize(10f);
                //data.setBarWidth(0.9f);

                //interfacilityData.setValueTextSize(10f);
                //interfacilityData.setBarWidth(0.9f);

                //intrafacilityData.setValueTextSize(10f);
                //intrafacilityData.setBarWidth(0.9f);

                interfacilityData.setBarWidth(0.6f);
                interfacilityData.setValueTextSize(10f);

                data.setBarWidth(0.6f);
                data.setValueTextSize(10f);

                chwBarChart.setTouchEnabled(false);
                chwBarChart.setData(data);
                chwBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                chwBarChart.invalidate();

                interFacilityBarChart.setTouchEnabled(false);
                interFacilityBarChart.setData(interfacilityData);
                interFacilityBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(interfacilityLabels));
                interFacilityBarChart.invalidate();

            }
        }.execute();
    }

    class RowView {

        String rowName;
        int referralCount;

        public RowView(){}

        public String getRowName() {
            return rowName;
        }

        public void setRowName(String rowName) {
            this.rowName = rowName;
        }

        public int getReferralCount() {
            return referralCount;
        }

        public void setReferralCount(int referralCount) {
            this.referralCount = referralCount;
        }
    }

}
