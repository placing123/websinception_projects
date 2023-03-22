package com.mw.fantasy.UI.player;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mw.fantasy.R;
import com.mw.fantasy.customView.CustomTextView;


public class PlayerViewHolder extends RecyclerView.ViewHolder {

    CustomTextView mCustomTextViewPoints;
    CustomTextView mCustomTextViewMatch;
    CustomTextView mCustomTextViewDate;
    CustomTextView mCustomTextViewSelectedBy;

    public PlayerViewHolder(View itemView) {
        super(itemView);
        mCustomTextViewMatch = (CustomTextView) itemView.findViewById(R.id.ctv_match);
        mCustomTextViewPoints = (CustomTextView) itemView.findViewById(R.id.ctv_points);
        mCustomTextViewDate = (CustomTextView) itemView.findViewById(R.id.ctv_date);
        mCustomTextViewSelectedBy = (CustomTextView) itemView.findViewById(R.id.ctv_selected_by);
    }
}
