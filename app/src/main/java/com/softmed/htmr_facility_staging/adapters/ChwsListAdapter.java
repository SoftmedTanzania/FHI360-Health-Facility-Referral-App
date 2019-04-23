package com.softmed.htmr_facility_staging.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.softmed.htmr_facility_staging.R;
import com.softmed.htmr_facility_staging.dom.objects.FacilityChws;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Coze on 02/25/19.
 */

public class ChwsListAdapter extends ArrayAdapter<FacilityChws> {

    List<String> planPrices;
    List<FacilityChws> items = new ArrayList<>();
    Context act;

    public ChwsListAdapter(Context context, int resource, List<FacilityChws> mItems) {
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
        tvTitle.setText(items.get(position).getDisplay());

        return rowView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        LayoutInflater vi = (LayoutInflater) act.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = vi.inflate(R.layout.single_text_spinner_view_item, null);

        TextView tvTitle = (TextView)rowView.findViewById(R.id.rowtext);
        tvTitle.setText(items.get(position).getDisplay());

        return rowView;
     }

    @Override
    public int getCount() {
        return items.size();
    }

    public void updateItems(List<FacilityChws> newItems){
        this.items = null;
        this.items = newItems;
        this.notifyDataSetChanged();
    }

}