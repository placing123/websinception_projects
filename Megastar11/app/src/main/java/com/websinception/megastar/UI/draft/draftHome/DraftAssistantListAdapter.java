package com.websinception.megastar.UI.draft.draftHome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftAssistantListAdapter extends RecyclerView.Adapter<DraftAssistantListAdapter.OrderListViewHolder> {

    private GetAuctionPlayerOutput mGetAuctionPlayerOutput;
    private Context mContext;
    private String roundId;

    public DraftAssistantListAdapter(Context context, String roundId, GetAuctionPlayerOutput mGetAuctionPlayerOutput) {
        this.mGetAuctionPlayerOutput = mGetAuctionPlayerOutput;
        this.mContext = context;
        this.roundId = roundId;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_draft_assistant_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = mGetAuctionPlayerOutput.getData().getRecords().get(i);
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getPlayerName());
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getPlayerPic());
        orderListViewHolder.mCustomTextViewPriority.setText(recordsBean.getAuctionDraftAssistantPriority());
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
                        recordsBean.getSeriesID(),false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGetAuctionPlayerOutput.getData().getRecords().size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ctv_priority)
        CustomTextView mCustomTextViewPriority;


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
