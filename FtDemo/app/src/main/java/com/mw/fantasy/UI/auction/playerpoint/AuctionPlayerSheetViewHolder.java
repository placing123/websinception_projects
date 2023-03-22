package com.mw.fantasy.UI.auction.playerpoint;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mw.fantasy.R;
import com.mw.fantasy.customView.CustomTextView;


public class AuctionPlayerSheetViewHolder extends RecyclerView.ViewHolder {


    CustomTextView event_value;
    CustomTextView points_value;
    CustomTextView actual_value;


    public AuctionPlayerSheetViewHolder(View itemView) {
        super(itemView);

        event_value = (CustomTextView) itemView.findViewById(R.id.event_value);
        actual_value = (CustomTextView) itemView.findViewById(R.id.actual_value);
        points_value = (CustomTextView) itemView.findViewById(R.id.points_value);

    }
}
