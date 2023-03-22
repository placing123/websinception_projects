package com.mw.fantasy.UI.auction.auctionHome;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> recordsBeanList;
    private Context mContext;
    private String roundID;


    public OrderListAdapter(Context context,
                            String roundID,
                            List<GetAuctionPlayerOutput.DataBean.RecordsBean> records) {
        this.recordsBeanList=records;
        this.mContext=context;
        this.roundID=roundID;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_auction_order_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = recordsBeanList.get(i);
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getPlayerPic());
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getPlayerName());
        if (recordsBean.getPlayerRole()!=null&&!recordsBean.getPlayerRole().trim().equals("")) {
            switch (recordsBean.getPlayerRole()) {
                case "Batsman":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort()+" - BAT");
                    break;
                case "WicketKeeper":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort()+" - WK");
                    break;
                case "AllRounder":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort()+" - AR");
                    break;
                case "Bowler":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort()+" - BOWL");
                    break;
            }
        }else {
            orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort());
        }

        if (recordsBean.getBidSoldCredit().trim().equals("")) {
            orderListViewHolder.mCustomTextViewPrice.setText("");
        }else {
            int soldAt=Integer.parseInt(recordsBean.getBidSoldCredit());
            if (soldAt!=0) {
                if (soldAt<10000000){
                    orderListViewHolder.mCustomTextViewPrice.setText(soldAt/100000+" Lacs");
                }else {
                    orderListViewHolder.mCustomTextViewPrice.setText(soldAt/10000000+" Crs");
                }
            }else {
                orderListViewHolder.mCustomTextViewPrice.setText("");
            }

        }

        orderListViewHolder.mCustomTextViewStatus.setText(recordsBean.getPlayerStatus());
        orderListViewHolder.mCustomTextViewBuyerName.setText("");
        switch (recordsBean.getPlayerStatus()) {
            case "Sold":
                orderListViewHolder.mCustomTextViewStatus.setTextColor(Color.parseColor("#FF0000"));
                orderListViewHolder.mCustomTextViewBuyerName.setText((recordsBean.getUserPlayerSoldName()==null||recordsBean.getUserPlayerSoldName().trim().isEmpty())?"N/A":recordsBean.getUserPlayerSoldName().trim());
                break;
            case "Unsold":
                orderListViewHolder.mCustomTextViewStatus.setTextColor(Color.parseColor("#00ff00"));
                break;
            case "Upcoming":
                orderListViewHolder.mCustomTextViewStatus.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case "Live":
                orderListViewHolder.mCustomTextViewStatus.setTextColor(Color.parseColor("#ffe124"));
                break;
        }


        orderListViewHolder.mCustomImageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionPlayerStatsActivity.start(mContext,
                        recordsBean.getSeriesGUID(),
                        recordsBean.getPlayerGUID(),
                        roundID,
                        recordsBean.getSeriesID(), false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recordsBeanList.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.ll_root)
        View mViewRoot;

        @BindView(R.id.ctv_price)
        CustomTextView mCustomTextViewPrice;

        @BindView(R.id.ctv_info)
        CustomTextView mCustomTextViewPlayerInfo;

        @BindView(R.id.ctv_buyer_name)
        CustomTextView mCustomTextViewBuyerName;

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
