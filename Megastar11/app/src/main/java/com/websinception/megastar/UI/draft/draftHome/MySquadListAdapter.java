package com.websinception.megastar.UI.draft.draftHome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.websinception.megastar.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.FIXTURE;
import static com.websinception.megastar.UI.draft.draftHome.MySquadFragment.MY_SQUAD;

public class MySquadListAdapter extends RecyclerView.Adapter<MySquadListAdapter.OrderListViewHolder> {

    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> mRecordsBeanList;
    private String auctionStatus;
    private Context mContext;
    private String roundId;
    private int seriesStatus,type;


    public MySquadListAdapter(Context context,int type, String roundId, String auctionStatus, List<GetAuctionPlayerOutput.DataBean.RecordsBean> recordsBeanList, int seriesStatus) {
        this.mRecordsBeanList = recordsBeanList;
        this.auctionStatus = auctionStatus;
        this.mContext = context;
        this.roundId = roundId;
        this.seriesStatus = seriesStatus;
        this.type = type;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_draft_squad_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = mRecordsBeanList.get(i);
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getPlayerName());
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getPlayerPic());
        if (recordsBean.getPlayerRole() != null && !recordsBean.getPlayerRole().trim().equals("")) {
            switch (recordsBean.getPlayerRole()) {
                case "Batsman":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort() + " - BAT");
                    break;
                case "WicketKeeper":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort() + " - WK");
                    break;
                case "AllRounder":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort() + " - AR");
                    break;
                case "Bowler":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort() + " - BOWL");
                    break;
            }
        } else {
            orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort());
        }
        orderListViewHolder.mCustomTextViewRound.setText((i + 1) + "");

        orderListViewHolder.mCustomImageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionPlayerStatsActivity.start(mContext,
                        recordsBean.getSeriesGUID(),
                        recordsBean.getPlayerGUID(),
                        roundId,
                        recordsBean.getSeriesID(),
                        seriesStatus == FIXTURE ? false : true);
            }
        });

        if (type == MY_SQUAD) {
            orderListViewHolder.iv_positionIndicator.setVisibility(View.VISIBLE);
            switch (recordsBean.getPlayerPosition()) {
                case "Captain":
                    orderListViewHolder.iv_positionIndicator.setImageResource(R.drawable.c);
                    break;
                case "ViceCaptain":
                    orderListViewHolder.iv_positionIndicator.setImageResource(R.drawable.vc);
                    break;
                default:
                    orderListViewHolder.iv_positionIndicator.setImageResource(R.drawable.p);
                    break;
            }
        } else {
            orderListViewHolder.iv_positionIndicator.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return mRecordsBeanList.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_position_indicator)
        ImageView iv_positionIndicator;


        @BindView(R.id.ctv_round)
        CustomTextView mCustomTextViewRound;


        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        @BindView(R.id.ctv_info)
        CustomTextView mCustomTextViewPlayerInfo;


        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
