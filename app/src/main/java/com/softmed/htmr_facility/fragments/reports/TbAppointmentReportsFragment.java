package com.softmed.htmr_facility.fragments.reports;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

import static com.softmed.htmr_facility.utils.constants.FEMALE;
import static com.softmed.htmr_facility.utils.constants.MALE;
import static com.softmed.htmr_facility.utils.constants.STATUS_COMPLETED;
import static com.softmed.htmr_facility.utils.constants.STATUS_PENDING;

/**
 * Created by issy on 08/03/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class TbAppointmentReportsFragment extends Fragment{

    LinearLayout rangeFrom, rangeTo;
    Button applyDateRange;
    private TextView dateRangeFromText, dateRangeToText, totalAppointment, attendedMale, attendedFemale, missedMale, missedFemale;

    final public static DatePickerDialog datePickerDialog = new DatePickerDialog();

    Calendar cal;
    Date rangeFromDate, rangeToDate;
    long dateFromInMillis, dateToInMillis;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_report_tb_appointment, container, false);
        setUpView(rootView);

        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));

        rangeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null){
                    datePickerDialog.show(getActivity().getFragmentManager(),"dateOfBirth");
                }
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        dateRangeFromText.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        dateFromInMillis = cal.getTimeInMillis();
                        rangeFromDate = cal.getTime();
                    }

                });
            }
        });

        rangeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null){
                    datePickerDialog.show(getActivity().getFragmentManager(),"dateOfBirth");
                }
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        dateRangeToText.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1) + "-" + year);
                        cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        dateToInMillis = cal.getTimeInMillis();
                        rangeToDate = cal.getTime();
                    }

                });
            }
        });

        applyDateRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Filter data based on range
                loadData(rangeFromDate, rangeToDate);
            }
        });

        return rootView;
    }

    private void loadData(Date from, Date to){

        //List<PatientAppointment> allAppointments = BaseActivity.baseDatabase.appointmentModelDao().getAllAppointments();

        new AsyncTask<Void, Void, Void>(){

            int totalAppointmentsCount = 0;
            int totalPendingAppointmentsMale = 0;
            int totalPendingAppointmentsFemale = 0;
            int totalAttendedAppointmentsMale =  0;
            int totalAttendedAppointmentsFemale =  0;

            @Override
            protected Void doInBackground(Void... voids) {
                totalAppointmentsCount = BaseActivity.baseDatabase.appointmentModelDao().getTotalAppointmentsByAppointmentDate(
                        dateFromInMillis,
                        dateToInMillis);

                totalPendingAppointmentsMale = BaseActivity.baseDatabase.appointmentModelDao().getTotalAppointmentsByAppointmentDateStatusAndGender(
                        dateFromInMillis,
                        dateToInMillis,
                        STATUS_PENDING,
                        MALE);

                totalPendingAppointmentsFemale = BaseActivity.baseDatabase.appointmentModelDao().getTotalAppointmentsByAppointmentDateStatusAndGender(
                        dateFromInMillis,
                        dateToInMillis,
                        STATUS_PENDING,
                        FEMALE);

                totalAttendedAppointmentsMale = BaseActivity.baseDatabase.appointmentModelDao().getTotalAppointmentsByAppointmentDateStatusAndGender(
                        dateFromInMillis,
                        dateToInMillis,
                        STATUS_COMPLETED,
                        MALE);

                totalAttendedAppointmentsFemale = BaseActivity.baseDatabase.appointmentModelDao().getTotalAppointmentsByAppointmentDateStatusAndGender(
                        dateFromInMillis,
                        dateToInMillis,
                        STATUS_COMPLETED,
                        FEMALE);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                totalAppointment.setText(totalAppointmentsCount+"");
                attendedMale.setText(totalAttendedAppointmentsMale+"");
                attendedFemale.setText(totalAttendedAppointmentsFemale+"");
                missedMale.setText(totalPendingAppointmentsMale+"");
                missedFemale.setText(totalPendingAppointmentsFemale+"");
            }
        }.execute();

    }

    private void setUpView(View v){
        rangeFrom = (LinearLayout) v.findViewById(R.id.range_from);
        rangeTo = (LinearLayout) v.findViewById(R.id.range_to);
        applyDateRange = (Button) v.findViewById(R.id.apply_date_range_button);
        dateRangeToText = (TextView) v.findViewById(R.id.date_range_to);
        dateRangeFromText = (TextView) v.findViewById(R.id.date_range_from);

        totalAppointment = (TextView) v.findViewById(R.id.total_appointments);
        missedMale = (TextView) v.findViewById(R.id.missed_male);
        missedFemale = (TextView) v.findViewById(R.id.missed_female);
        attendedMale = (TextView) v.findViewById(R.id.attended_male);
        attendedFemale = (TextView) v.findViewById(R.id.attended_female);

    }

}
