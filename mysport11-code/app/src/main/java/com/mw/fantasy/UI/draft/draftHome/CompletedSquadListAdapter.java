package com.mw.fantasy.UI.draft.draftHome;

import android.content.Context;
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

public class CompletedSquadListAdapter extends RecyclerView.Adapter<CompletedSquadListAdapter.OrderListViewHolder> {

    private GetAuctionPlayerOutput mGetAuctionPlayerOutput;
    private Context mContext;
    private String seriesId, roundId;

    public CompletedSquadListAdapter(Context context,
                                     String roundId,
                                     String seriesId,
                                     GetAuctionPlayerOutput mGetAuctionPlayerOutput) {
        this.mGetAuctionPlayerOutput = mGetAuctionPlayerOutput;
        this.mContext = context;
        this.roundId = roundId;
        this.seriesId = seriesId;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_auction_completed_squad_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = mGetAuctionPlayerOutput.getData().getRecords().get(i);
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getPlayerName());
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getPlayerPic());
        orderListViewHolder.mCustomTextViewRound.setText((i+1)+"");
        orderListViewHolder.ctv_total_point.setText(recordsBean.getTotalPoints());
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

        orderListViewHolder.mLinearLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionPlayerStatsActivity.start(mContext,
                        recordsBean.getSeriesGUID(),
                        recordsBean.getPlayerGUID(),
                        roundId,
                        recordsBean.getSeriesID(),
                        true);
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
        CustomTextView mCustomTextViewRound;

        @BindView(R.id.ll_root)
        LinearLayout mLinearLayoutRoot;


        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        @BindView(R.id.iv_position_indicator)
        ImageView iv_positionIndicator;

        @BindView(R.id.ctv_total_point)
        CustomTextView ctv_total_point;

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
