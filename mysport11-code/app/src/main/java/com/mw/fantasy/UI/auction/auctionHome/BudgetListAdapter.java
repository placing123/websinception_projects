package com.mw.fantasy.UI.auction.auctionHome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.beanOutput.GetAuctionJoinedUserOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BudgetListAdapter extends RecyclerView.Adapter<BudgetListAdapter.OrderListViewHolder> {

    private GetAuctionJoinedUserOutput mGetAuctionJoinedUserOutput;

    public BudgetListAdapter(GetAuctionJoinedUserOutput getAuctionJoinedUserOutput) {
        this.mGetAuctionJoinedUserOutput = getAuctionJoinedUserOutput;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_auction_budget_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        GetAuctionJoinedUserOutput.DataBean.RecordsBean recordsBean = mGetAuctionJoinedUserOutput.getData().getRecords().get(i);
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getUsername());
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getProfilePic());
        if (recordsBean.getAuctionBudget().trim().equals("")) {
            orderListViewHolder.mCustomTextViewBudget.setText("");
        } else {
            int auctionBudget = Integer.parseInt(recordsBean.getAuctionBudget());
            if (auctionBudget != 0) {
                if (auctionBudget < 10000000) {
                    orderListViewHolder.mCustomTextViewBudget.setText(auctionBudget / 100000 + " Lacs");
                } else {
                    orderListViewHolder.mCustomTextViewBudget.setText(auctionBudget / 10000000 + " Crs");
                }
            } else {
                orderListViewHolder.mCustomTextViewBudget.setText("0");
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
            if (recordsBean.getUserGUID().equals(AppSession.getInstance().getLoginSession().getData().getUserGUID())) {
                orderListViewHolder.mImageViewIndicator.setImageResource(R.drawable.circle_green);
            } else {
                orderListViewHolder.mImageViewIndicator.setImageResource(R.drawable.circle_red);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mGetAuctionJoinedUserOutput.getData().getRecords().size();
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
