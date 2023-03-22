package com.mw.fantasy.UI.auction.auctionHome;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mw.fantasy.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.FIXTURE;

public class SquadListAdapter extends RecyclerView.Adapter<SquadListAdapter.OrderListViewHolder> {

    private GetAuctionPlayerOutput mGetAuctionPlayerOutput;
    private SquadFragment mSquadFragment;
    private String auctionStatus ,seriesId, roundId;
    private int seriesStatus;

    public SquadListAdapter(SquadFragment squadFragment,
                            String roundId,
                            String seriesId,
                            String auctionStatus,
                            GetAuctionPlayerOutput mGetAuctionPlayerOutput, int seriesStatus) {
        this.mGetAuctionPlayerOutput = mGetAuctionPlayerOutput;
        this.auctionStatus = auctionStatus;
        this.mSquadFragment = squadFragment;
        this.roundId = roundId;
        this.seriesId = seriesId;
        this.seriesStatus = seriesStatus;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_auction_squad_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = mGetAuctionPlayerOutput.getData().getRecords().get(i);
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getPlayerName());
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getPlayerPic());
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
                orderListViewHolder.mCustomTextViewPrice.setText("0");
            }
        }



        /*if (recordsBean.getAuctionTopPlayerSubmitted().equals("Yes")) {
            switch (recordsBean.getPlayerPosition()) {
                case "Captain":
                    orderListViewHolder.mImageViewBtn.setImageResource(R.drawable.c);
                    break;
                case "ViceCaptain":
                    orderListViewHolder.mImageViewBtn.setImageResource(R.drawable.vc);
                    break;
                default:
                    orderListViewHolder.mImageViewBtn.setImageResource(R.drawable.p);
                    break;
            }
        } else {
            if (auctionStatus.equals("Completed")) {
                setCrossButton(orderListViewHolder.mImageViewBtn, i);
                orderListViewHolder.mImageViewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recordsBean.setPlayerSelectedForSumbmit(!recordsBean.isPlayerSelectedForSumbmit());
                        notifyDataSetChanged();
                    }
                });
            } else {
                orderListViewHolder.mImageViewBtn.setVisibility(View.GONE);
            }
        }*/

        if (auctionStatus.equals("Completed")) {
            if (recordsBean.getAuctionPlayingPlayer().equals("Yes")) {
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
            }else {
                orderListViewHolder.iv_positionIndicator.setVisibility(View.GONE);
            }
        }else {
            orderListViewHolder.iv_positionIndicator.setVisibility(View.GONE);
        }

        orderListViewHolder.mImageViewBtn.setOnClickListener(null);
        if (mSquadFragment.isMySquad()) {
            if (mSquadFragment.isSeriesStarted()) {
                orderListViewHolder.mImageViewBtn.setVisibility(View.GONE);
            }else {
                if (auctionStatus.equals("Completed")) {
                    orderListViewHolder.mImageViewBtn.setVisibility(View.VISIBLE);
                    setCrossButton(orderListViewHolder.mImageViewBtn, i);
                    orderListViewHolder.mImageViewBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (recordsBean.getAuctionPlayingPlayer().equals("Yes")) {
                                recordsBean.setAuctionPlayingPlayer("No");
                            } else {
                                recordsBean.setAuctionPlayingPlayer("Yes");
                            }
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    orderListViewHolder.mImageViewBtn.setVisibility(View.GONE);
                }
            }

        }else {
            orderListViewHolder.mImageViewBtn.setVisibility(View.GONE);
        }
        orderListViewHolder.mCustomImageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionPlayerStatsActivity.start(mSquadFragment.getContext(),
                        recordsBean.getSeriesGUID(),
                        recordsBean.getPlayerGUID(),
                        roundId,
                        recordsBean.getSeriesID(),
                        seriesStatus==FIXTURE?false:true);
            }
        });



    }


    public void setCrossButton(ImageView ivCross, int position) {
        ivCross.setImageResource(isSelected(position) ? R.drawable.ic_cancel : R.drawable.ic_add_button);
        ivCross.setColorFilter(Color.parseColor(!isSelected(position) ? "#26BA38" : "#ed0a0a"), PorterDuff.Mode.SRC_IN);
    }

    private boolean isSelected(int position) {
        return mGetAuctionPlayerOutput.getData().getRecords().get(position).getAuctionPlayingPlayer().equals("Yes");
    }

    @Override
    public int getItemCount() {
        return mGetAuctionPlayerOutput.getData().getRecords().size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ctv_price)
        CustomTextView mCustomTextViewPrice;

        @BindView(R.id.ll_root)
        LinearLayout mLinearLayoutRoot;


        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        @BindView(R.id.iv_position_indicator)
        ImageView iv_positionIndicator;

        @BindView(R.id.img_btn)
        ImageView mImageViewBtn;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> getSelectedPlayers() {
        ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> tem = new ArrayList<>();
        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
            if (record.getAuctionPlayingPlayer().equals("Yes")) {
                tem.add(record);
            }
        }
        return tem;
    }
}
