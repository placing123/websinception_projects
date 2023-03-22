package com.websinception.megastar.UI.draft.draftHome.addPlayer;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.OrderListViewHolder> {

    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> mRecordsBeanList;
    private Context mContext;
    private BottomSheetAddPlayerFragment.OnPlayerSelec mOnPlayerSeleceListner;
    private String roundId;


    public PlayerListAdapter(Context context,
                             ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> recordsBeanList, BottomSheetAddPlayerFragment.OnPlayerSelec mOnPlayerSeleceListner,
                             String roundId) {
        this.mRecordsBeanList = recordsBeanList;
        this.mContext = context;
        this.mOnPlayerSeleceListner = mOnPlayerSeleceListner;
        this.roundId = roundId;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_draft_add_player_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListViewHolder orderListViewHolder, int i) {
        final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = mRecordsBeanList.get(i);
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getPlayerName());
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getPlayerPic());
        orderListViewHolder.mCustomTextViewPrice.setText(recordsBean.getTeamNameShort());
        switch (recordsBean.getPlayerRole()) {
            case "WicketKeeper":
                orderListViewHolder.mCustomTextViewStatus.setText("WK");
                break;
            case "Bowler":
                orderListViewHolder.mCustomTextViewStatus.setText("BOWL");
                break;
            case "Batsman":
                orderListViewHolder.mCustomTextViewStatus.setText("BAT");
                break;
            case "AllRounder":
                orderListViewHolder.mCustomTextViewStatus.setText("AR");
                break;
        }
        if (recordsBean.getPlayerStatus().equals("Sold")) {
            orderListViewHolder.mLinearLayoutRoot.setAlpha(0.2f);
        }else {
            orderListViewHolder.mLinearLayoutRoot.setAlpha(1f);
        }
        orderListViewHolder.mLinearLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.clickVibrate(mContext);
                if (recordsBean.getPlayerStatus().equals("Sold")) {
                    AppUtils.showToast(mContext,recordsBean.getPlayerName()+" already sold. Please select other one.");
                }else {
                    mOnPlayerSeleceListner.onSelect(recordsBean);
                }
            }
        });

        orderListViewHolder.mCustomImageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionPlayerStatsActivity.start(mContext,
                        recordsBean.getSeriesGUID(),
                        recordsBean.getPlayerGUID(),
                        roundId,
                        recordsBean.getSeriesID(),false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecordsBeanList.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ll_rrot)
        LinearLayout mLinearLayoutRoot;

        @BindView(R.id.ctv_price)
        CustomTextView mCustomTextViewPrice;

        @BindView(R.id.ctv_status)
        CustomTextView mCustomTextViewStatus;

        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
