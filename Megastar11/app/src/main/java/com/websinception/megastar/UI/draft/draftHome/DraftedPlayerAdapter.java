package com.websinception.megastar.UI.draft.draftHome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftedPlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int min;
    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> myDraftedPlayers;
    private Context mContext;
    private String matchGUID;


    public DraftedPlayerAdapter(Context mContext, String matchGUID, List<GetAuctionPlayerOutput.DataBean.RecordsBean> myDraftedPlayers, String role, int min) {
        this.min = min;
        this.mContext = mContext;
        this.matchGUID = matchGUID;
        this.myDraftedPlayers = filterData(myDraftedPlayers, role);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DraftedPlayerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_drafted_player, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        DraftedPlayerViewHolder draftedPlayerViewHolder = (DraftedPlayerViewHolder) viewHolder;
        if (position >= myDraftedPlayers.size()) {
            draftedPlayerViewHolder.mViewEmpty.setVisibility(View.VISIBLE);
            draftedPlayerViewHolder.mLinearLayoutDataRoot.setVisibility(View.GONE);
        } else {
            draftedPlayerViewHolder.mViewEmpty.setVisibility(View.GONE);
            draftedPlayerViewHolder.mLinearLayoutDataRoot.setVisibility(View.VISIBLE);
           // draftedPlayerViewHolder.mCtvName.setText(myDraftedPlayers.get(position).getPlayerName());

            draftedPlayerViewHolder.mCtvName.setText(AppUtils.shortPlayerName(myDraftedPlayers.get(position).getPlayerName()));

            ViewUtils.setImageUrl(draftedPlayerViewHolder.mCustomImageViewPic, myDraftedPlayers.get(position).getPlayerPic());
            draftedPlayerViewHolder.mCustomImageViewPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AuctionPlayerStatsActivity.start(mContext,
                            myDraftedPlayers.get(position).getSeriesGUID(),
                            myDraftedPlayers.get(position).getPlayerGUID(),
                            matchGUID,
                            myDraftedPlayers.get(position).getSeriesID(),false);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (myDraftedPlayers.size() >= min) {
            return myDraftedPlayers.size();
        } else {
            return min;
        }
    }

    public class DraftedPlayerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_data_root)
        LinearLayout mLinearLayoutDataRoot;

        @BindView(R.id.v_empty)
        View mViewEmpty;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        @BindView(R.id.ctv_name)
        CustomTextView mCtvName;


        public DraftedPlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> filterData(List<GetAuctionPlayerOutput.DataBean.RecordsBean> myDraftedPlayers, String role) {
        List<GetAuctionPlayerOutput.DataBean.RecordsBean> tem = new ArrayList<>();
        String temRole = null;
        switch (role) {
            case "WK":
                temRole = "WicketKeeper";
                break;
            case "AR":
                temRole = "AllRounder";
                break;
            case "BAT":
                temRole = "Batsman";
                break;
            case "BOWL":
                temRole = "Bowler";
                break;
        }
        for (GetAuctionPlayerOutput.DataBean.RecordsBean myDraftedPlayer : myDraftedPlayers) {
            if (myDraftedPlayer.getPlayerRole().equals(temRole)) {
                tem.add(myDraftedPlayer);
            }
        }
        return tem;
    }
}
