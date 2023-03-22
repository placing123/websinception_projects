package com.websinception.megastar.UI.player;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.websinception.megastar.R;
import com.websinception.megastar.customView.CustomTextView;


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
