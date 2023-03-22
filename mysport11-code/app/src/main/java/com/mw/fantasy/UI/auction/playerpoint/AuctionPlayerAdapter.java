package com.mw.fantasy.UI.auction.playerpoint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mw.fantasy.R;
import com.mw.fantasy.UI.createTeam.sorting.PointsASC;
import com.mw.fantasy.UI.createTeam.sorting.PointsDEC;
import com.mw.fantasy.UI.createTeam.sorting.SelectedByASC;
import com.mw.fantasy.UI.createTeam.sorting.SelectedByDEC;
import com.mw.fantasy.beanOutput.ResponsePlayerFantasyStats;
import com.mw.fantasy.utility.TimeUtils;

import java.util.Collections;
import java.util.List;


public class AuctionPlayerAdapter extends RecyclerView.Adapter<AuctionPlayerViewHolder> {

    Context mContext;
    List<ResponsePlayerFantasyStats.DataBean.RecordsBean> responseBeanList;

    public AuctionPlayerAdapter(Context mContext, List<ResponsePlayerFantasyStats.DataBean.RecordsBean> responseBeanList) {
        this.mContext = mContext;
        this.responseBeanList = responseBeanList;
    }

     @Override
        public AuctionPlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.auction_player_stats_adapter, parent, false);

            return new AuctionPlayerViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AuctionPlayerViewHolder holder, int position) {

        String team = responseBeanList.get(position).getTeamNameShortLocal() + " Vs " +
                responseBeanList.get(position).getTeamNameShortVisitor();

            holder.mCustomTextViewMatch.setText(team);
            holder.mCustomTextViewDate.setText(TimeUtils.getDateByFormatInput(
                    responseBeanList.get(position).getMatchStartDateTime(), "yyyy-MM-dd",
                    "dd MMM, yyyy"));
            holder.mCustomTextViewPoints.setText(responseBeanList.get(position).getTotalPoints());
            holder.mCustomTextViewSelectedBy.setText(responseBeanList.get(position).getPlayerSelectedPercent() + "%");

        }
    @Override
    public int getItemCount() {
        return responseBeanList.size();
    }

    public void shotByPoint(boolean bool){

        if(bool){
            Collections.sort(responseBeanList, new PointsDEC());
        }else {
            Collections.sort(responseBeanList, new PointsASC());
        }

        notifyDataSetChanged();
    }

    public void shotBySelectedpercentage(boolean bool){

        if(bool){
            Collections.sort(responseBeanList, new SelectedByDEC());
        }else {
            Collections.sort(responseBeanList, new SelectedByASC());
        }
        notifyDataSetChanged();
    }

}
