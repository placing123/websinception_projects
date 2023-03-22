package com.websinception.megastar.UI.auction.auctionHome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.websinception.megastar.R;
import com.websinception.megastar.beanOutput.GetContestBidHistoryOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BidHistoryListAdapter extends RecyclerView.Adapter<BidHistoryListAdapter.OrderListViewHolder> {

    private ArrayList<GetContestBidHistoryOutput.DataBean.RecordsBean> mRecordsBeanArrayList;

    public BidHistoryListAdapter( ArrayList<GetContestBidHistoryOutput.DataBean.RecordsBean> mRecordsBeanArrayList) {
        this.mRecordsBeanArrayList = mRecordsBeanArrayList;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_auction_bid_history_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        GetContestBidHistoryOutput.DataBean.RecordsBean recordsBean = mRecordsBeanArrayList.get(i);
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getUsername());
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getProfilePic());
        if (recordsBean.getBidCredit().trim().equals("")) {
            orderListViewHolder.mCustomTextViewBudget.setText("");
        } else {
            int auctionBudget = Integer.parseInt(recordsBean.getBidCredit());
            if (auctionBudget != 0) {
                if (auctionBudget < 10000000) {
                    orderListViewHolder.mCustomTextViewBudget.setText(auctionBudget / 100000 + " Lacs");
                } else {
                    orderListViewHolder.mCustomTextViewBudget.setText(auctionBudget / 10000000 + " Crs");
                }
            } else {
                orderListViewHolder.mCustomTextViewBudget.setText("");
            }
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = simpleDateFormat.parse(recordsBean.getDateTime());
            orderListViewHolder.mCustomTextViewTime.setText(new SimpleDateFormat("MMM dd, yyyy\nhh:mm:ss a").format(d));
        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  GetAuctionJoinedUserOutput.DataBean.RecordsBean recordsBean = mGetAuctionJoinedUserOutput.getData().getRecords().get(i);
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getProfilePic());
        if (recordsBean.getAuctionBudget().trim().equals("")) {
            orderListViewHolder.mCustomTextViewBudget.setText("");
        } else {
            int auctionBudget = Integer.parseInt(recordsBean.getAuctionBudget());
            if (auctionBudget != 0) {
                if (auctionBudget < 10000000) {
                    orderListViewHolder.mCustomTextViewBudget.setText(auctionBudget / 100000.0 + " Lacs");
                } else {
                    orderListViewHolder.mCustomTextViewBudget.setText(auctionBudget / 10000000.0 + " Crs");
                }
            } else {
                orderListViewHolder.mCustomTextViewBudget.setText("");
            }
        }
        if (recordsBean.getAuctionTimeBank().trim().equals("")) {
            orderListViewHolder.mCustomTextViewTime.setText("");
        } else {
            int time = Integer.parseInt(recordsBean.getAuctionTimeBank());
            orderListViewHolder.mCustomTextViewTime.setText(String.format("%02d:%02d s", time / 60, time % 60));
        }
        if (recordsBean.getAuctionUserStatus().equals("Online")) {
            orderListViewHolder.mImageViewIndicator.setImageResource(R.drawable.circle_green);
        } else {
            orderListViewHolder.mImageViewIndicator.setImageResource(R.drawable.circle_red);
        }*/
    }

    @Override
    public int getItemCount() {
        return mRecordsBeanArrayList.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ctv_budget)
        CustomTextView mCustomTextViewBudget;

        @BindView(R.id.ctv_time)
        CustomTextView mCustomTextViewTime;


        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        @BindView(R.id.iv_indicator)
        ImageView mImageViewIndicator;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
