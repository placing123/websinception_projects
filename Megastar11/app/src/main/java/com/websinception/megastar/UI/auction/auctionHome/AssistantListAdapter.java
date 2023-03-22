package com.websinception.megastar.UI.auction.auctionHome;

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
import com.websinception.megastar.utility.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssistantListAdapter extends RecyclerView.Adapter<AssistantListAdapter.OrderListViewHolder> {

    private GetAuctionPlayerOutput mGetAuctionPlayerOutput;
    private Context mContext;
    private String roundId;

    public AssistantListAdapter(Context context, String roundId,GetAuctionPlayerOutput mGetAuctionPlayerOutput) {
        this.mContext = context;
        this.roundId = roundId;
        this.mGetAuctionPlayerOutput = mGetAuctionPlayerOutput;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_auction_assistant_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = mGetAuctionPlayerOutput.getData().getRecords().get(i);
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getPlayerName());
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getPlayerPic());
        orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getPlayerRole());
        if (recordsBean.getBidCredit().trim().equals("")) {
            orderListViewHolder.mCustomTextViewPrice.setText("");
        } else {
            int soldAt = Integer.parseInt(recordsBean.getBidCredit());
            if (soldAt != 0) {
                if (soldAt < 10000000) {
                    orderListViewHolder.mCustomTextViewPrice.setText(soldAt / 100000 + " Lacs");
                } else {
                    orderListViewHolder.mCustomTextViewPrice.setText(soldAt / 10000000 + " Crs");
                }
            } else {
                orderListViewHolder.mCustomTextViewPrice.setText("");
            }
        }

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

        orderListViewHolder.mCustomImageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionPlayerStatsActivity.start(mContext,
                        recordsBean.getSeriesGUID(),
                        recordsBean.getPlayerGUID(),
                        roundId,
                        recordsBean.getSeriesID(),
                        false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGetAuctionPlayerOutput.getData().getRecords().size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ctv_price)
        CustomTextView mCustomTextViewPrice;


        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        @BindView(R.id.ctv_info)
        CustomTextView mCustomTextViewPlayerInfo;

        @BindView(R.id.ll_root)
        LinearLayout mLinearLayoutRoot;


        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
