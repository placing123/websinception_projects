package com.mw.fantasy.UI.auction.auctionLoby;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.auctionHome.AuctionDetailScreenActivity;
import com.mw.fantasy.UI.draft.draftHome.DraftDetailScreenActivity;
import com.mw.fantasy.UI.draft.draftHome.MySquadFragment;
import com.mw.fantasy.beanOutput.GetAuctionInfoOutput;
import com.mw.fantasy.beanOutput.GetAuctionJoinedUserOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuctionLobbyUserListAdapter extends RecyclerView.Adapter<AuctionLobbyUserListAdapter.OrderListViewHolder> {

    private GetAuctionJoinedUserOutput mGetAuctionJoinedUserOutput;
    private Context mContext;
    private String roundId, contestGUID, seriesID, draftStatus, contestStatus;
    private int flag;
    private boolean isSeriesStarted;
    private String seriesName, seriesDeadLine,auctionStartTime;
    private int seriesStatus;
    private GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria draftPlayerSelectionCriteria;

    public AuctionLobbyUserListAdapter(Context context,
                                       GetAuctionJoinedUserOutput getAuctionJoinedUserOutput,
                                       String roundId,
                                       int flag,
                                       String draftStatus,
                                       String contestStatus,
                                       String seriesID,
                                       String contestGUID,
                                       String seriesName,
                                       String seriesDeadLine,
                                       int seriesStatus,
                                       boolean isSeriesStarted,
                                       GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria draftPlayerSelectionCriteria,
                                       String auctionStartTime
    ) {
        this.mGetAuctionJoinedUserOutput = getAuctionJoinedUserOutput;
        this.mContext = context;
        this.roundId = roundId;
        this.flag = flag;
        this.draftStatus = draftStatus;
        this.seriesName = seriesName;
        this.seriesDeadLine = seriesDeadLine;
        this.seriesStatus = seriesStatus;
        this.contestGUID = contestGUID;
        this.seriesID = seriesID;
        this.contestStatus = contestStatus;
        this.isSeriesStarted = isSeriesStarted;
        this.auctionStartTime = auctionStartTime;
        this.draftPlayerSelectionCriteria = draftPlayerSelectionCriteria;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_auction_leaderboard_lobby_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        final GetAuctionJoinedUserOutput.DataBean.RecordsBean recordsBean = mGetAuctionJoinedUserOutput.getData().getRecords().get(i);
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getProfilePic());
        String points = (recordsBean.getTotalPoints() == null || recordsBean.getTotalPoints().trim().isEmpty()) ? "0" : recordsBean.getTotalPoints().trim();
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getUsername());
        orderListViewHolder.mCustomTextViewPoint.setText(points + " POINT");
        if (recordsBean.getUserRank() != null && !recordsBean.getUserRank().trim().equals("")) {
            orderListViewHolder.mCustomTextViewRank.setText("" + recordsBean.getUserRank());
        } else {
            orderListViewHolder.mCustomTextViewRank.setText("#");
        }

        String userWinningAmount = recordsBean.getUserWinningAmount();
        if (contestStatus.equals("Completed")) {
            if (userWinningAmount != null && !userWinningAmount.trim().equals("")) {
                orderListViewHolder.mCustomTextViewWon.setText("₹" + userWinningAmount);
            } else {
                orderListViewHolder.mCustomTextViewWon.setText("N/A");
            }
        } else {
            orderListViewHolder.mCustomTextViewWon.setText("");
        }

        if (recordsBean.getUserGUID().equals(AppSession.getInstance().getLoginSession().getData().getUserGUID())) {
            orderListViewHolder.mLinearLayoutRoot.setBackground(mContext.getResources().getDrawable(R.drawable.white_trans_gradient));

        } else {
            orderListViewHolder.mLinearLayoutRoot.setBackground(mContext.getResources().getDrawable(R.drawable.bg_auction_trans));
        }

        orderListViewHolder.mLinearLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recordsBean.getUserTeamPlayers().size() == 0) {
                    AppUtils.showToast(mContext, "No data found.");
                } else {

                    String name = (recordsBean.getFirstName() == null || recordsBean.getFirstName().trim().isEmpty()) ? recordsBean.getUserTeamName() : (recordsBean.getFirstName());
                    if (recordsBean.getUserGUID().equals(AppSession.getInstance().getLoginSession().getData().getUserGUID())) {
                        name = "My";
                    }

                    if (flag == 1) {
                        AuctionDetailScreenActivity.start2(mContext,
                                isSeriesStarted ? AuctionDetailScreenActivity.COMPLITED_SQUAD : AuctionDetailScreenActivity.SQUAD,
                                roundId,
                                seriesID,
                                contestGUID,
                                draftStatus,
                                recordsBean.getUserGUID(),
                                name,
                                seriesName,
                                seriesDeadLine,
                                seriesStatus, isSeriesStarted,
                                auctionStartTime);
                    }else {
                        if (draftPlayerSelectionCriteria!=null) {
                            if (isSeriesStarted) {
                                DraftDetailScreenActivity.start(
                                        mContext,
                                        DraftDetailScreenActivity.COMPLETED_SQUAD,
                                        name,
                                        seriesName,
                                        seriesDeadLine, seriesStatus,
                                        draftPlayerSelectionCriteria,
                                        MySquadFragment.MY_SQUAD, roundId,
                                        contestGUID,
                                        draftStatus,
                                        seriesID,
                                        recordsBean.getUserGUID(),"",
                                        auctionStartTime);
                            }else {
                                if (recordsBean.getUserGUID().equals(AppSession.getInstance().getLoginSession().getData().getUserGUID())) {
                                    DraftDetailScreenActivity.start(
                                            mContext,
                                            DraftDetailScreenActivity.SQUAD,
                                            name,
                                            seriesName,
                                            seriesDeadLine, seriesStatus,
                                            draftPlayerSelectionCriteria,
                                            MySquadFragment.MY_SQUAD, roundId,
                                            contestGUID,
                                            draftStatus,
                                            seriesID,
                                            recordsBean.getUserGUID(),"",auctionStartTime);
                                }else {
                                    DraftDetailScreenActivity.start(
                                            mContext,
                                            DraftDetailScreenActivity.SQUAD,
                                            name,
                                            seriesName,
                                            seriesDeadLine, seriesStatus,
                                            draftPlayerSelectionCriteria,
                                            MySquadFragment.OTHER_SQUAD, roundId,
                                            contestGUID,
                                            draftStatus,
                                            seriesID,
                                            recordsBean.getUserGUID(),"",auctionStartTime);
                                }
                            }
                        }else {
                            AppUtils.showToast(mContext, "draftPlayerSelectionCriteria == null");
                        }

                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGetAuctionJoinedUserOutput.getData().getRecords().size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_root)
        LinearLayout mLinearLayoutRoot;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.ctv_points)
        CustomTextView mCustomTextViewPoint;

        @BindView(R.id.ctv_rank)
        CustomTextView mCustomTextViewRank;


        @BindView(R.id.ctv_winning)
        CustomTextView mCustomTextViewWon;


        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
