package com.websinception.megastar.UI.playerPoints;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.websinception.megastar.R;
import com.websinception.megastar.customView.CustomTextView;


public class PlayerSheetViewHolder extends RecyclerView.ViewHolder {


    CustomTextView event_value;
    CustomTextView points_value;
    CustomTextView actual_value;


    public PlayerSheetViewHolder(View itemView) {
        super(itemView);

        event_value = (CustomTextView) itemView.findViewById(R.id.event_value);
        actual_value = (CustomTextView) itemView.findViewById(R.id.actual_value);
        points_value = (CustomTextView) itemView.findViewById(R.id.points_value);

    }
}
