package com.websinception.megastar.UI.auction.playerpoint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.createTeam.sorting.SelectedSorterASC;
import com.websinception.megastar.UI.createTeam.sorting.SelectedSorterDEC;
import com.websinception.megastar.UI.createTeam.sorting.SortByNameASC;
import com.websinception.megastar.UI.createTeam.sorting.SortByNameDES;
import com.websinception.megastar.UI.createTeam.sorting.TotalPointSorterASC;
import com.websinception.megastar.UI.createTeam.sorting.TotalPointSorterDES;
import com.websinception.megastar.beanOutput.PlayersOutput;
import com.websinception.megastar.utility.OnItemClickListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AcutionPlayerPointsAdapter extends RecyclerView.Adapter<AcutionPlayerPointsViewHolder> {

    Context mContext;
    List<PlayersOutput.DataBean.RecordsBean> responseBeanList;
    OnItemClickListener.OnItemClickCallback onItemClickCallback;

    Map<String, Object> map = new HashMap<String, Object>() {{
        put("WicketKeeper", "WK");
        put("Batsman", "Bat");
        put("AllRounder", "AR");
        put("Bowler", "Bowl");

    }};

    public AcutionPlayerPointsAdapter(Context mContext, List<PlayersOutput.DataBean.RecordsBean> responseBeanList,
                                      OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.mContext = mContext;
        this.responseBeanList = responseBeanList;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public AcutionPlayerPointsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.acution_player_state_item, parent, false);

        return new AcutionPlayerPointsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AcutionPlayerPointsViewHolder holder, int position) {
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

        holder.teamName.setText(responseBeanList.get(position).getTeamNameShort().trim().replace(" ","")+" - "+map.get(responseBeanList.get(position).getPlayerRole()));
        holder.mCustomTextViewPoints.setText(""+responseBeanList.get(position).getTotalPoints());
        holder.mCustomTextViewSelectedBy.setText(responseBeanList.get(position).getPlayerSelectedPercent()+"%");

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
        ((AuctionPlayerPointsActivity)mContext).responseBeanList=responseBeanList;
        notifyDataSetChanged();
    }
    void shotByPoint(boolean bool){

        if(bool){
            Collections.sort(responseBeanList, new TotalPointSorterDES());
        }else {
            Collections.sort(responseBeanList, new TotalPointSorterASC());
        }
        ((AuctionPlayerPointsActivity)mContext).responseBeanList=responseBeanList;
        notifyDataSetChanged();
    }

    void shotBySelectedpercentage(boolean bool){

        if(bool){
            Collections.sort(responseBeanList, new SelectedSorterDEC());
        }else {
            Collections.sort(responseBeanList, new SelectedSorterASC());
        }
        ((AuctionPlayerPointsActivity)mContext).responseBeanList=responseBeanList;
        notifyDataSetChanged();
    }


}
