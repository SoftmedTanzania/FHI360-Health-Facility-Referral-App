package apps.softmed.com.hfreferal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.dom.objects.HealthFacilities;
import apps.softmed.com.hfreferal.dom.objects.HealthFacilityServices;
import apps.softmed.com.hfreferal.dom.objects.ReferralServiceIndicators;

/**
 * Created by issy on 1/7/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class ServicesAdapter extends ArrayAdapter<ReferralServiceIndicators> {

    List<ReferralServiceIndicators> items = new ArrayList<>();
    Context act;

    public ServicesAdapter(Context context, int resource, List<ReferralServiceIndicators> mItems) {
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
        tvTitle.setText(items.get(position).getServiceName());

        return rowView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        LayoutInflater vi = (LayoutInflater) act.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = vi.inflate(R.layout.single_text_spinner_view_item, null);

        TextView tvTitle = (TextView)rowView.findViewById(R.id.rowtext);
        tvTitle.setText(items.get(position).getServiceName());

        return rowView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void updateItems(List<ReferralServiceIndicators> newItems){
        this.items = null;
        this.items = newItems;
        this.notifyDataSetChanged();
    }

}