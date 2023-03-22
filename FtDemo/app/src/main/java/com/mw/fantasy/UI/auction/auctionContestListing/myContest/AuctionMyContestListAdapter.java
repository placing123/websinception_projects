package com.mw.fantasy.UI.auction.auctionContestListing.myContest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.auctionLoby.AuctionLobbyActivity;
import com.mw.fantasy.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment;
import com.mw.fantasy.UI.draft.draftHome.DraftDetailScreenActivity;
import com.mw.fantasy.UI.draft.draftHome.DraftHomeActivity;
import com.mw.fantasy.UI.joinContest.JoinContestActivity;
import com.mw.fantasy.beanOutput.GetSeriesAuctionContestOutput;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.OnItemClickListener;
import com.mw.fantasy.utility.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mw.fantasy.UI.auction.auctionContestListing.myContest.MyContestPendingLiveListingActivity.REQUEST_CODE_JOIN;


public class AuctionMyContestListAdapter extends RecyclerView.Adapter<AuctionMyContestListAdapter.AuctionContestViewHolder> {

    private Context mContext;
    private ArrayList<GetSeriesAuctionContestOutput.DataBean.RecordsBean> mRecordsBeanArrayList = new ArrayList<>();
    private int type, flag, seriesStatus;
    private String roundId, seriesName, matchGUID;
    OnItemClickListener.OnItemClickCallback onWinnerClickCallBack;
    private String seriesDeadLine;

    public AuctionMyContestListAdapter(Context mContext,
                                       ArrayList<GetSeriesAuctionContestOutput.DataBean.RecordsBean> mRecordsBeanArrayList,
                                       int flag,
                                       int type,
                                       int seriesStatus,
                                       String seriesName,
                                       String roundId,
                                       String matchGUID,
                                       OnItemClickListener.OnItemClickCallback onWinnerClickCallBack,
                                       String seriesDeadLine) {
        this.mContext = mContext;
        this.mRecordsBeanArrayList = mRecordsBeanArrayList;
        this.flag = flag;
        this.seriesName = seriesName;
        this.type = type;
        this.roundId = roundId;
        this.matchGUID = matchGUID;
        this.seriesStatus = seriesStatus;
        this.onWinnerClickCallBack = onWinnerClickCallBack;
        this.seriesDeadLine = seriesDeadLine;
    }

    @NonNull
    @Override
    public AuctionContestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AuctionContestViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(flag == 1 ? R.layout.adapter_series_auction_item : R.layout.adapter_series_draft_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final AuctionContestViewHolder auctionContestViewHolder, final int i) {
        final GetSeriesAuctionContestOutput.DataBean.RecordsBean recordsBean = mRecordsBeanArrayList.get(i);
        int spotLeft = Integer.parseInt(recordsBean.getContestSize()) - Integer.parseInt(recordsBean.getTotalJoined());
        double cashBonus = Double.parseDouble(recordsBean.getCashBonusContribution());
        auctionContestViewHolder.mCtvName.setText(Html.fromHtml("<u>" + recordsBean.getContestName() + "</u>"));
        auctionContestViewHolder.mCtvPrizePool.setText("₹" + recordsBean.getWinningAmount());
        auctionContestViewHolder.mCtvWinnerCount.setText(recordsBean.getNoOfWinners());
        auctionContestViewHolder.mCtvEntryFee.setText("₹" + recordsBean.getEntryFee());
        if (spotLeft == 0) {
            auctionContestViewHolder.mCtvSpotLeft.setText("League full");
        } else {
            auctionContestViewHolder.mCtvSpotLeft.setText("Only " + spotLeft + " spots left");
        }
        auctionContestViewHolder.mCtvContestSize.setText(recordsBean.getContestSize() + " Teams");
        if (cashBonus == 0) {
            auctionContestViewHolder.mCtvBonusInfo.setVisibility(View.GONE);
        } else {
            auctionContestViewHolder.mCtvBonusInfo.setText("USE " + cashBonus + "% CASH BONUS");
            auctionContestViewHolder.mCtvBonusInfo.setVisibility(View.VISIBLE);
        }
        auctionContestViewHolder.mProgressBar.setMax(Integer.parseInt(recordsBean.getContestSize()));
        auctionContestViewHolder.mProgressBar.setProgress(Integer.parseInt(recordsBean.getTotalJoined()));
        auctionContestViewHolder.mProgressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        auctionContestViewHolder.mCtvWinnerCount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        auctionContestViewHolder.mCtvWinnerCount.setOnClickListener(null);
        if (!recordsBean.getNoOfWinners().equals("1")) {

            if (!recordsBean.getNoOfWinners().equals("0")) {
                auctionContestViewHolder.mCtvWinnerCount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                auctionContestViewHolder.mCtvWinnerCount.setCompoundDrawablePadding(10);

                auctionContestViewHolder.mCtvWinnerCount.setOnClickListener(new OnItemClickListener(i, onWinnerClickCallBack));
            }
        }

        auctionContestViewHolder.setTime();

        if (recordsBean.getAuctionStatus().equals("Pending")) {
            auctionContestViewHolder.mCtvDeadLineLabel.setText((flag == 1 ? "Auction" : "Draft") + " starts in");
           /* try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = simpleDateFormat.parse(recordsBean.getLeagueJoinDateTime());
                auctionContestViewHolder.mCtvDeadline.setText(new SimpleDateFormat("MMM dd, yyyy hh:mm a").format(d));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        } else if (recordsBean.getAuctionStatus().equals("Running")) {
            auctionContestViewHolder.mCtvDeadLineLabel.setText((flag == 1 ? "Auction" : "Draft") + " status");
        } else {
            //auctionContestViewHolder.mCtvDeadLineLabel.setText("Deadline for team submission/update");
            auctionContestViewHolder.mCtvDeadLineLabel.setText("Submit team before");
            /*try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = simpleDateFormat.parse(seriesDeadLine);
                auctionContestViewHolder.mCtvDeadline.setText(new SimpleDateFormat("MMM dd, yyyy hh:mm a").format(d));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }

        if (seriesStatus == AuctionSeriesListingFragment.FIXTURE) {
            auctionContestViewHolder.mLinearLayoutSeriesTimeInfoRoot.setVisibility(View.VISIBLE);
        } else {
            auctionContestViewHolder.mLinearLayoutSeriesTimeInfoRoot.setVisibility(View.GONE);
        }


        if (recordsBean.getStatus().equals("Cancelled")) {
            auctionContestViewHolder.mCtvJoinButton.setText("CANCELLED");
            auctionContestViewHolder.mCtvJoinButton.setBackgroundResource(R.drawable.bg_auction_btn_red);
            auctionContestViewHolder.mCtvJoinButton.setTextColor(Color.parseColor("#ffffff"));
            auctionContestViewHolder.mLinearLayoutSeriesTimeInfoRoot.setVisibility(View.GONE);
        } else {
            auctionContestViewHolder.mCtvJoinButton.setBackgroundResource(R.drawable.bg_auc_btn);
            auctionContestViewHolder.mCtvJoinButton.setTextColor(Color.parseColor("#000000"));
            if (recordsBean.getIsJoined().equals("No")) {
                auctionContestViewHolder.mCtvJoinButton.setText("JOIN");
            } else {
                if (recordsBean.getIsAuctionFinalTeamSubmitted() == null) {
                    auctionContestViewHolder.mCtvJoinButton.setText("ENTER");
                } else {
                    if (recordsBean.getIsAuctionFinalTeamSubmitted().equals("Yes")) {
                        if (recordsBean.getIsSeriesMatchStarted().equals("Yes")) {
                            //auctionContestViewHolder.mCtvJoinButton.setText("LEADERBOARD");
                            auctionContestViewHolder.mCtvJoinButton.setText("ENTER");
                        } else {
                            auctionContestViewHolder.mCtvJoinButton.setText("ENTER");
                        }
                    } else {
                        auctionContestViewHolder.mCtvJoinButton.setText("ENTER");
                    }
                }

            }
        }

        auctionContestViewHolder.mCtvJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        auctionContestViewHolder.mLinearLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auctionContestViewHolder.mCtvJoinButton.performClick();
            }
        });

        if (seriesStatus == AuctionSeriesListingFragment.COMPLETED) {
            auctionContestViewHolder.mLinearLayoutMyStatsRoot.setVisibility(View.VISIBLE);
            auctionContestViewHolder.mLinearLayoutSizeRoot.setVisibility(View.GONE);
            auctionContestViewHolder.mCtvPoints.setText(recordsBean.getTotalPoints());
            auctionContestViewHolder.mCtvRank.setText("#" + recordsBean.getUserRank());
            auctionContestViewHolder.mCtvWinings.setText("Your Wining is ₹" + recordsBean.getUserWinningAmount());
            auctionContestViewHolder.mCtvLeaderBoard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auctionContestViewHolder.mCtvJoinButton.performClick();
                }
            });
            String auctionBudget = recordsBean.getAuctionBudget();
            if (auctionBudget != null && !auctionBudget.trim().isEmpty()) {
                try {
                    double tem = Double.parseDouble(auctionBudget.trim());
                    tem = 1000000000 - tem;
                    auctionContestViewHolder.mCtvBudgetUsed.setText("₹" + (tem / 10000000) + " Crs");
                } catch (Exception e) {
                    e.printStackTrace();
                    auctionContestViewHolder.mCtvBudgetUsed.setText("₹ N/A");
                }
            } else {
                auctionContestViewHolder.mCtvBudgetUsed.setText("₹ N/A");

            }
        } else {
            auctionContestViewHolder.mLinearLayoutMyStatsRoot.setVisibility(View.GONE);
            auctionContestViewHolder.mLinearLayoutSizeRoot.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mRecordsBeanArrayList.size();
    }

    public GetSeriesAuctionContestOutput.DataBean.RecordsBean getItem(int position) {


        return mRecordsBeanArrayList.get(position);
    }


    public long getRemainingTime(int position) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date seriesStartDate = null;
            GetSeriesAuctionContestOutput.DataBean.RecordsBean recordsBean = (GetSeriesAuctionContestOutput.DataBean.RecordsBean) mRecordsBeanArrayList.get(position);
            if (recordsBean.getAuctionStatus().equals("Pending")) {
                seriesStartDate = simpleDateFormat.parse(recordsBean.getLeagueJoinDateTimeUTC());
            } else if (recordsBean.getAuctionStatus().equals("Running")) {
                return 0;
            } else {
                seriesStartDate = simpleDateFormat.parse(seriesDeadLine);
            }
            long time = seriesStartDate.getTime();
            long diff = time - System.currentTimeMillis();
            if (diff > 0) {
                return diff;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public class AuctionContestViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ctv_name)
        CustomTextView mCtvName;

        @BindView(R.id.ctv_prize_pool)
        CustomTextView mCtvPrizePool;

        @BindView(R.id.ctv_winners_count)
        CustomTextView mCtvWinnerCount;

        @BindView(R.id.ctv_entry_fee)
        CustomTextView mCtvEntryFee;

        @BindView(R.id.spotLeftCount)
        CustomTextView mCtvSpotLeft;

        @BindView(R.id.ctv_contest_size)
        CustomTextView mCtvContestSize;

        @BindView(R.id.bonus_info)
        CustomTextView mCtvBonusInfo;

        @BindView(R.id.ctv_deadline)
        CustomTextView mCtvDeadline;

        @BindView(R.id.joinButton)
        CustomTextView mCtvJoinButton;

        @BindView(R.id.ctv_deadline_label)
        CustomTextView mCtvDeadLineLabel;

        @BindView(R.id.seekBar)
        ProgressBar mProgressBar;

        @BindView(R.id.hi_main_card)
        LinearLayout mLinearLayoutRoot;


        @BindView(R.id.ll_size_info_root)
        LinearLayout mLinearLayoutSizeRoot;


        @BindView(R.id.ll_my_stats_info)
        LinearLayout mLinearLayoutMyStatsRoot;

        @BindView(R.id.ctv_budget_used)
        CustomTextView mCtvBudgetUsed;

        @BindView(R.id.ctv_points)
        CustomTextView mCtvPoints;

        @BindView(R.id.rank_value)
        CustomTextView mCtvRank;


        @BindView(R.id.ctv_winings)
        CustomTextView mCtvWinings;

        @BindView(R.id.ctv_leader_board)
        CustomTextView mCtvLeaderBoard;


        @BindView(R.id.ll_series_time_info)
        LinearLayout mLinearLayoutSeriesTimeInfoRoot;

        CountDownTimer countDownTimer;


        public AuctionContestViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setTime() {
            try {
                if (countDownTimer != null)
                    countDownTimer.cancel();
                countDownTimer = new CountDownTimer(getRemainingTime(getAdapterPosition()),
                        TimeUnit.SECONDS.toMillis(1)) {
                    public void onTick(long millisUntilFinished) {
                        try {
                            int index = getAdapterPosition();
                            if (index != -1 && mRecordsBeanArrayList != null && index < mRecordsBeanArrayList.size()) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date seriesStartDate = null;
                                GetSeriesAuctionContestOutput.DataBean.RecordsBean recordsBean = (GetSeriesAuctionContestOutput.DataBean.RecordsBean) mRecordsBeanArrayList.get(index);
                                if (recordsBean.getAuctionStatus().equals("Pending")) {
                                    seriesStartDate = simpleDateFormat.parse(recordsBean.getLeagueJoinDateTimeUTC());
                                } else {
                                    seriesStartDate = simpleDateFormat.parse(seriesDeadLine);
                                }

                                long time = seriesStartDate.getTime();
                                long diff = (time - System.currentTimeMillis()) / 1000;
                                long days = 0, hours = 0, minute = 0, seconds = 0;
                                if (diff > 0) {
                                    days = TimeUtils.getDaysBetween(new Date(), seriesStartDate);
                                    diff = diff % (24 * 3600);
                                    hours = diff / 3600;
                                    diff %= 3600;
                                    minute = diff / 60;
                                    diff %= 60;
                                    seconds = diff;
                                }
                                mCtvDeadline.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                                if (days > 0) {
                                    if (days == 1) {
                                        mCtvDeadline.setText("Tomorrow");
                                    } else {
                                        mCtvDeadline.setText(days + " days left");
                                    }
                                } else if (hours > 0) {
                                    mCtvDeadline.setText(hours + " hours left");
                                } else if (minute > 0) {
                                    mCtvDeadline.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                                    mCtvDeadline.setText(minute + " minute left");
                                } else if (seconds > 0) {
                                    mCtvDeadline.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                                    mCtvDeadline.setText(seconds + " seconds left");
                                } else {
                                    mCtvDeadline.setText("Live");
                                    mCtvDeadline.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            mCtvDeadline.setText("ERROR3");
                        }

                    }

                    public void onFinish() {
                        mCtvDeadline.setText("Live");
                        mCtvDeadline.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    }
                };
                countDownTimer.start();

            } catch (Exception e1) {
                e1.printStackTrace();
                mCtvDeadline.setText("ERROR1");
            }

        }

    }
}

