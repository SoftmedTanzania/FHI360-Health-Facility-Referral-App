package apps.softmed.com.hfreferal.customviews;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import apps.softmed.com.hfreferal.R;
import apps.softmed.com.hfreferal.fragments.IssueReferralDialogueFragment;

/**
 * Created by issy on 22/01/2018.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

public class IndicatorsViewHolder extends SwappingHolder
        implements View.OnClickListener{

    public TextView indicatorName;

    public IndicatorsViewHolder(View itemView) {
        super(itemView);
        indicatorName = (TextView) itemView.findViewById(R.id.indicator_name);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        if (!multiSelector.isSelectable()) { // (3)
//            IssueReferralDialogueFragment.multiSelector.setSelectable(true); // (4)
//            multiSelector.setSelected(IndicatorsViewHolder.this, true); // (5)
//        }
    }

}