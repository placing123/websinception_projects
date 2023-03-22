package com.websinception.megastar.UI.playerPoints;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.createTeam.sorting.PointSorterASC;
import com.websinception.megastar.UI.createTeam.sorting.PointSorterDES;
import com.websinception.megastar.UI.createTeam.sorting.SelectedSorterASC;
import com.websinception.megastar.UI.createTeam.sorting.SelectedSorterDEC;
import com.websinception.megastar.UI.createTeam.sorting.SortByNameASC;
import com.websinception.megastar.UI.createTeam.sorting.SortByNameDES;
import com.websinception.megastar.beanOutput.PlayersOutput;
import com.websinception.megastar.utility.OnItemClickListener;

import java.util.Collections;
import java.util.List;



public class PlayerPointsAdapter extends RecyclerView.Adapter<PlayerPointsViewHolder> {

    Context mContext;
    List<PlayersOutput.DataBean.RecordsBean> responseBeanList;
    OnItemClickListener.OnItemClickCallback onItemClickCallback;

    public PlayerPointsAdapter(Context mContext, List<PlayersOutput.DataBean.RecordsBean> responseBeanList,
                               OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.mContext = mContext;
        this.responseBeanList = responseBeanList;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public PlayerPointsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.player_state_item, parent, false);

        return new PlayerPointsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerPointsViewHolder holder, int position) {
        if (responseBeanList.get(position).getMyPlayer().equals("No")) {
            holder.mImageView.setImageResource(R.drawable.your_player_blur);
           // holder.mImageView.setVisibility(View.GONE);
        } else {
            holder.mImageView.setImageResource(R.drawable.your_player);
        }

        if (responseBeanList.get(position).getTopPlayer().equals("No")) {
            holder.mImageViewTopPlayer.setImageResource(R.drawable.top_player_blur);
            //holder.mImageViewTopPlayer.setVisibility(View.GONE);
        } else {
            holder.mImageViewTopPlayer.setImageResource(R.drawable.top_player);
        }

        holder.mSimpleDraweeView.setImageURI(responseBeanList.get(position).getPlayerPic());
        holder.mCustomTextViewName.setText(responseBeanList.get(position).getPlayerName());
        holder.mCustomTextViewPoints.setText(""+responseBeanList.get(position).getPointCredits());
        holder.mCustomTextViewSelectedBy.setText(responseBeanList.get(position).getPlayerSelectedPercent() + "%");

        holder.mRelativeLayout.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));

    }

    public void addAllItem(List<PlayersOutput.DataBean.RecordsBean> beanList) {
        if (beanList == null || responseBeanList == null) return;
        for (int i = 0; i < beanList.size(); i++) {
            addItem(beanList.get(i));
        }
    }
    public void addItem(PlayersOutput.DataBean.RecordsBean bean) {
        if (bean == null || responseBeanList == null) return;
        responseBeanList.add(bean);
        notifyItemInserted(responseBeanList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return responseBeanList.size();
    }


    void shotByName(boolean bool){

        if(bool){
            Collections.sort(responseBeanList, new SortByNameDES());
        }else {
            Collections.sort(responseBeanList, new SortByNameASC());
        }
        ((PlayerPointsActivity)mContext).responseBeanList=responseBeanList;
        notifyDataSetChanged();
    }
    void shotByPoint(boolean bool){

        if(bool){
            Collections.sort(responseBeanList, new PointSorterDES());
        }else {
            Collections.sort(responseBeanList, new PointSorterASC());
        }
        ((PlayerPointsActivity)mContext).responseBeanList=responseBeanList;
        notifyDataSetChanged();
    }

    void shotBySelectedpercentage(boolean bool){

        if(bool){
            Collections.sort(responseBeanList, new SelectedSorterDEC());
        }else {
            Collections.sort(responseBeanList, new SelectedSorterASC());
        }
        ((PlayerPointsActivity)mContext).responseBeanList=responseBeanList;
        notifyDataSetChanged();
    }


}
