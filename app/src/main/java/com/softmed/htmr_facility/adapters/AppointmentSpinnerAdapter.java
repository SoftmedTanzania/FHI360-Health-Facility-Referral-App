package com.softmed.htmr_facility.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.softmed.htmr_facility.R;
import com.softmed.htmr_facility.base.BaseActivity;
import com.softmed.htmr_facility.dom.objects.PatientAppointment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * Created by issy on 11/04/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class AppointmentSpinnerAdapter extends ArrayAdapter<PatientAppointment> {

    static List<PatientAppointment> items = new ArrayList<>();
    Context act;

    public AppointmentSpinnerAdapter(Context context, int resource, List<PatientAppointment> mItems) {
        super(context, resource, mItems);
        this.items = mItems;
        act = context;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        LayoutInflater vi = (LayoutInflater) act.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = vi.inflate(R.layout.subscription_plan_items_drop_down, null);

        TextView tvTitle =(TextView)rowView.findViewById(R.id.rowtext);

        long appointmentDate = items.get(position).getAppointmentDate();
        Calendar cal  = Calendar.getInstance();
        cal.setTimeInMillis(appointmentDate);

        int month = cal.get(Calendar.MONTH)+1;
        String monthName = BaseActivity.getMonthName(month);
        tvTitle.setText(cal.get(Calendar.DAY_OF_MONTH)+" - "+monthName);

        return rowView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        LayoutInflater vi = (LayoutInflater) act.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = vi.inflate(R.layout.single_text_spinner_view_item_black, null);

        TextView tvTitle = rowView.findViewById(R.id.rowtext);

        long appointmentDate = items.get(position).getAppointmentDate();
        Calendar cal  = Calendar.getInstance();
        cal.setTimeInMillis(appointmentDate);

        int month = cal.get(Calendar.MONTH)+1;
        String monthName = BaseActivity.getMonthName(month);
        tvTitle.setText(cal.get(Calendar.DAY_OF_MONTH)+" - "+monthName);

        return rowView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void updateItems(List<PatientAppointment> newItems){
        this.items = null;
        this.items = newItems;
        this.notifyDataSetChanged();
    }

    public static PatientAppointment getServiceByPosition(int position){
        return items.get(position);
    }

}
