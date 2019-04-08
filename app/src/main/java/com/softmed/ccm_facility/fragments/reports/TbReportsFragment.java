package com.softmed.ccm_facility.fragments.reports;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.softmed.ccm_facility.R;
import com.softmed.ccm_facility.base.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.softmed.ccm_facility.utils.constants.TREATMENT_TYPE_1;
import static com.softmed.ccm_facility.utils.constants.TREATMENT_TYPE_2;
import static com.softmed.ccm_facility.utils.constants.TREATMENT_TYPE_3;
import static com.softmed.ccm_facility.utils.constants.TREATMENT_TYPE_4;
import static com.softmed.ccm_facility.utils.constants.TREATMENT_TYPE_5;

/**
 * Created by issy on 12/04/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbReportsFragment extends Fragment {

    ImageView dateFromIcon1;
    ImageView dateToIcon1;
    LinearLayout btnUnderMedicationFromRange, btnUnderMedicationToRange;
    TextView txtUnderMedicationFromRange, txtUnderMedicationToRange;
    TextView txt_um_m1, txt_um_m2, txt_um_m3, txt_um_m4, txt_um_m5;
    TextView txt_um_f1, txt_um_f2, txt_um_f3, txt_um_f4, txt_um_f5;
    TextView txt_um_t1, txt_um_t2, txt_um_t3, txt_um_t4, txt_um_t5;

    PieChart underMedicationPieChart;

    DatePickerDialog fromDatePickerDialog = new DatePickerDialog();
    DatePickerDialog toDatePickerDialog = new DatePickerDialog();
    Calendar calendar;

    long valUnderMedicationFromRange, valUnderMedicationToRange;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tb_reports ,container, false);
        setupviews(rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnUnderMedicationFromRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null){
                    fromDatePickerDialog.show(getActivity().getFragmentManager(),"fromDateRange");
                }
                fromDatePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        txtUnderMedicationFromRange.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        valUnderMedicationFromRange = calendar.getTimeInMillis();
                        /*if (!isFirstDateSelected) isFirstDateSelected = true;
                        else loadGraphData();*/
                    }

                });
            }
        });

        btnUnderMedicationToRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null){
                    toDatePickerDialog.show(getActivity().getFragmentManager(),"toDateRange");
                }
                toDatePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        txtUnderMedicationToRange.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        valUnderMedicationToRange = calendar.getTimeInMillis();
                        /*if (!isFirstDateSelected) isFirstDateSelected = true;
                        else loadGraphData();*/

                        loadUnderMedicationData();

                    }

                });
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    void loadUnderMedicationData(){

        new AsyncTask<Void, Void, Void>(){

            //VALUE_REPORTTYPE_GENDER[COUNT]
            int v_um_m1, v_um_m2, v_um_m3, v_um_m4, v_um_m5;
            int v_um_f1, v_um_f2, v_um_f3, v_um_f4, v_um_f5;
            int v_um_t1, v_um_t2, v_um_t3, v_um_t4, v_um_t5;

            List<RowView> totalPatientsUnderMedicationList = new ArrayList<>();


            @Override
            protected Void doInBackground(Void... voids) {

                v_um_m1 = BaseActivity.baseDatabase.tbPatientModelDao().getCountUnderMedication(TREATMENT_TYPE_1, "Male");
                v_um_m2 = BaseActivity.baseDatabase.tbPatientModelDao().getCountUnderMedication(TREATMENT_TYPE_2, "Male");
                v_um_m3 = BaseActivity.baseDatabase.tbPatientModelDao().getCountUnderMedication(TREATMENT_TYPE_3, "Male");
                v_um_m4 = BaseActivity.baseDatabase.tbPatientModelDao().getCountUnderMedication(TREATMENT_TYPE_4, "Male");
                v_um_m5 = BaseActivity.baseDatabase.tbPatientModelDao().getCountUnderMedication(TREATMENT_TYPE_5, "Male");

                v_um_f1 = BaseActivity.baseDatabase.tbPatientModelDao().getCountUnderMedication(TREATMENT_TYPE_1, "Female");
                v_um_f2 = BaseActivity.baseDatabase.tbPatientModelDao().getCountUnderMedication(TREATMENT_TYPE_2, "Female");
                v_um_f3 = BaseActivity.baseDatabase.tbPatientModelDao().getCountUnderMedication(TREATMENT_TYPE_3, "Female");
                v_um_f4 = BaseActivity.baseDatabase.tbPatientModelDao().getCountUnderMedication(TREATMENT_TYPE_4, "Female");
                v_um_f5 = BaseActivity.baseDatabase.tbPatientModelDao().getCountUnderMedication(TREATMENT_TYPE_5, "Female");

                v_um_t1 = v_um_m1 + v_um_f1;
                v_um_t2 = v_um_m2 + v_um_f2;
                v_um_t3 = v_um_m3 + v_um_f3;
                v_um_t4 = v_um_m4 + v_um_f4;
                v_um_t5 = v_um_m5 + v_um_f5;

                totalPatientsUnderMedicationList.add(new RowView(TREATMENT_TYPE_1, v_um_t1));
                totalPatientsUnderMedicationList.add(new RowView(TREATMENT_TYPE_2, v_um_t2));
                totalPatientsUnderMedicationList.add(new RowView(TREATMENT_TYPE_3, v_um_t3));
                totalPatientsUnderMedicationList.add(new RowView(TREATMENT_TYPE_4, v_um_t4));
                totalPatientsUnderMedicationList.add(new RowView(TREATMENT_TYPE_5, v_um_t5));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                txt_um_m1.setText(v_um_m1+"");
                txt_um_m2.setText(v_um_m2+"");
                txt_um_m3.setText(v_um_m3+"");
                txt_um_m4.setText(v_um_m4+"");
                txt_um_m5.setText(v_um_m5+"");

                txt_um_f1.setText(v_um_f1+"");
                txt_um_f2.setText(v_um_f2+"");
                txt_um_f3.setText(v_um_f3+"");
                txt_um_f4.setText(v_um_f4+"");
                txt_um_f5.setText(v_um_f5+"");

                txt_um_t1.setText(v_um_t1+"");
                txt_um_t2.setText(v_um_t2+"");
                txt_um_t3.setText(v_um_t3+"");
                txt_um_t4.setText(v_um_t4+"");
                txt_um_t5.setText(v_um_t5+"");

                ArrayList<PieEntry> yEntrys = new ArrayList<>();
                ArrayList<String> xEntrys = new ArrayList<>();

                for (int j=0; j<totalPatientsUnderMedicationList.size(); j++){
                    RowView rowView = totalPatientsUnderMedicationList.get(j);
                    if (rowView.getValue() > 0){
                        yEntrys.add(new PieEntry(rowView.getValue() , rowView.getName()));
                    }
                }

                PieDataSet pieDataSet = new PieDataSet(yEntrys, getResources().getString(R.string.treatment_type));
                pieDataSet.setSliceSpace(2);
                pieDataSet.setValueTextSize(12);

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                Legend legend = underMedicationPieChart.getLegend();
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

                //create pie data object
                PieData pieData = new PieData(pieDataSet);
                underMedicationPieChart.setData(pieData);
                underMedicationPieChart.invalidate();

            }
        }.execute();


    }

    void setupviews(View v){
        dateFromIcon1 = v.findViewById(R.id.date_from_icon_1);
        dateFromIcon1.setColorFilter(getResources().getColor(R.color.colorPrimary));
        dateToIcon1 = v.findViewById(R.id.date_to_icon_1);
        dateToIcon1.setColorFilter(getResources().getColor(R.color.colorPrimary));

        btnUnderMedicationFromRange = v.findViewById(R.id.btn_under_medication_from_range);
        btnUnderMedicationToRange = v.findViewById(R.id.btn_under_medication_to_range);

        txtUnderMedicationFromRange = v.findViewById(R.id.txt_under_medication_from_range);
        txtUnderMedicationToRange = v.findViewById(R.id.txt_under_medication_to_range);

        txt_um_m1 = v.findViewById(R.id.v_um_m1);
        txt_um_m2 = v.findViewById(R.id.v_um_m2);
        txt_um_m3 = v.findViewById(R.id.v_um_m3);
        txt_um_m4 = v.findViewById(R.id.v_um_m4);
        txt_um_m5 = v.findViewById(R.id.v_um_m5);

        txt_um_f1 = v.findViewById(R.id.v_um_f1);
        txt_um_f2 = v.findViewById(R.id.v_um_f2);
        txt_um_f3 = v.findViewById(R.id.v_um_f3);
        txt_um_f4 = v.findViewById(R.id.v_um_f4);
        txt_um_f5 = v.findViewById(R.id.v_um_f5);

        txt_um_t1 = v.findViewById(R.id.v_um_t1);
        txt_um_t2 = v.findViewById(R.id.v_um_t2);
        txt_um_t3 = v.findViewById(R.id.v_um_t3);
        txt_um_t4 = v.findViewById(R.id.v_um_t4);
        txt_um_t5 = v.findViewById(R.id.v_um_t5);

        underMedicationPieChart = v.findViewById(R.id.under_medication_pie_chart);
        underMedicationPieChart.setRotationEnabled(true);
        underMedicationPieChart.setHoleRadius(60f);
        underMedicationPieChart.setTransparentCircleAlpha(5);
        underMedicationPieChart.setCenterText("Patients Under Medication");
        underMedicationPieChart.setCenterTextSize(16);
        underMedicationPieChart.setCenterTextTypeface(BaseActivity.Avenir_Light);
        underMedicationPieChart.getDescription().setEnabled(false);
    }


    class RowView{
        String name;
        int value;
        public RowView(){}
        public RowView(String _name, int _value){
            this.name = _name;
            this.value = _value;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getValue() {
            return value;
        }
        public void setValue(int value) {
            this.value = value;
        }
    }

}
