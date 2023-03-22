package com.mw.fantasy.UI.auction.auctionContestListing.upcoming;

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
import com.mw.fantasy.UI.auction.auctionContestListing.myContest.MyContestPendingLiveListingActivity;
import com.mw.fantasy.UI.auction.auctionLoby.AuctionLobbyActivity;
import com.mw.fantasy.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment;
import com.mw.fantasy.UI.draft.draftHome.DraftDetailScreenActivity;
import com.mw.fantasy.UI.draft.draftHome.DraftHomeActivity;
import com.mw.fantasy.UI.joinContest.JoinContestActivity;
import com.mw.fantasy.UI.winnings.WinnersRankBean;
import com.mw.fantasy.beanOutput.GetSeriesAuctionContestByTypeOutput;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mw.fantasy.UI.auction.auctionContestListing.upcoming.AuctionContestListingActivity.REQUEST_CODE_JOIN;


public class AuctionContestListByTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "AuctionContestListByTyp";

    public static final int HEADER = 1, DATA = 2, FOOTER = 3;
    private Context mContext;
    private GetSeriesAuctionContestByTypeOutput mGetSeriesAuctionContestByTypeOutput;

    ArrayList<Object> data = new ArrayList<>();


    private int type, flag, seriesStatus;
    private String roundId, seriesName, matchGUID;
    private String seriesDeadLine;

    public AuctionContestListByTypeAdapter(Context mContext,
                                           GetSeriesAuctionContestByTypeOutput getSeriesAuctionContestByTypeOutput,
                                           int flag,
                                           int type,
                                           int seriesStatus,
                                           String seriesName,
                                           String roundId,
                                           String matchGUID,
                                           String seriesDeadLine) {
        this.mContext = mContext;
        this.mGetSeriesAuctionContestByTypeOutput = getSeriesAuctionContestByTypeOutput;
        this.flag = flag;
        this.seriesName = seriesName;
        this.type = type;
        this.roundId = roundId;
        this.seriesStatus = seriesStatus;
        this.matchGUID = matchGUID;

        this.seriesDeadLine = seriesDeadLine;
        for (int i = 0; i < mGetSeriesAuctionContestByTypeOutput.getData().getResults().size(); i++) {
            GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean resultsBean = mGetSeriesAuctionContestByTypeOutput.getData().getResults().get(i);
            if (resultsBean.getTotalRecords() != 0) {
                data.add(new HeaderDataBean(resultsBean.getKey(), resultsBean.getTagLine()));
                for (GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean.RecordsBean record : resultsBean.getRecords()) {
                    data.add(record);
                }
                if (flag == 1) {
                    if (resultsBean.getTotalRecords() > 3) {
                        data.add(new FooterDataBean(resultsBean.getTotalRecords()));
                    }
                }

            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case HEADER:
                return new AuctionHeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_series_auction_item_header, viewGroup, false));
            case FOOTER:
                return new AuctionFooterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_series_auction_item_footer, viewGroup, false));
            default:
                return new AuctionDataViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_series_auction_item, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case HEADER:
                onBindViewHolderHeader((AuctionHeaderViewHolder) viewHolder, position);
                break;
            case FOOTER:
                onBindViewHolderFooter((AuctionFooterViewHolder) viewHolder, position);
                break;
            case DATA:
                onBindViewHolderData((AuctionDataViewHolder) viewHolder, position);
                break;
        }
    }


    private void onBindViewHolderHeader(AuctionHeaderViewHolder auctionHeaderViewHolder, int position) {
        HeaderDataBean headerDataBean = (HeaderDataBean) data.get(position);
        auctionHeaderViewHolder.mCustomTextViewTitle.setText(headerDataBean.getTitle());
        auctionHeaderViewHolder.mCustomTextViewDesc.setText(headerDataBean.getDescription());
    }

    private void onBindViewHolderFooter(AuctionFooterViewHolder auctionFooterViewHolder, final int position) {
        FooterDataBean footerDataBean = (FooterDataBean) data.get(position);
        auctionFooterViewHolder.mCustomTextCount.setText("View " + (footerDataBean.getCount() - 3) + " more");
        auctionFooterViewHolder.mCustomTextCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean.RecordsBean recordsBean = (GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean.RecordsBean) data.get(position - 1);
                ;

                MyContestPendingLiveListingActivity.start(
                        mContext,
                        flag,
                        roundId,
                        type,
                        seriesStatus,
                        seriesName,
                        seriesDeadLine,
                        recordsBean.getContestType());
            }
        });
    }

    private void onBindViewHolderData(final AuctionDataViewHolder auctionDataViewHolder, int position) {

        final GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean.RecordsBean recordsBean = (GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean.RecordsBean) data.get(position);
        ;

        int spotLeft = Integer.parseInt(recordsBean.getContestSize()) - Integer.parseInt(recordsBean.getTotalJoined());
        double cashBonus = Double.parseDouble(recordsBean.getCashBonusContribution());
        auctionDataViewHolder.mCtvName.setText(Html.fromHtml("<u>" + recordsBean.getContestName() + "</u>"));
        auctionDataViewHolder.mCtvPrizePool.setText("₹" + recordsBean.getWinningAmount());
        auctionDataViewHolder.mCtvWinnerCount.setText(recordsBean.getNoOfWinners());
        auctionDataViewHolder.mCtvEntryFee.setText("₹" + recordsBean.getEntryFee());
        if (spotLeft == 0) {
            auctionDataViewHolder.mCtvSpotLeft.setText("League full");
        } else {
            auctionDataViewHolder.mCtvSpotLeft.setText("Only " + spotLeft + " spots left");
        }
        auctionDataViewHolder.mCtvContestSize.setText(recordsBean.getContestSize() + " Teams");
        if (cashBonus == 0) {
            auctionDataViewHolder.mCtvBonusInfo.setVisibility(View.GONE);
        } else {
            auctionDataViewHolder.mCtvBonusInfo.setText("USE " + cashBonus + "% CASH BONUS");
            auctionDataViewHolder.mCtvBonusInfo.setVisibility(View.VISIBLE);
        }
        auctionDataViewHolder.mProgressBar.setMax(Integer.parseInt(recordsBean.getContestSize()));
        auctionDataViewHolder.mProgressBar.setProgress(Integer.parseInt(recordsBean.getTotalJoined()));
        auctionDataViewHolder.mProgressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        auctionDataViewHolder.mCtvWinnerCount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        auctionDataViewHolder.mCtvWinnerCount.setOnClickListener(null);
        if (!recordsBean.getNoOfWinners().equals("1")) {

            if (!recordsBean.getNoOfWinners().equals("0")) {
                auctionDataViewHolder.mCtvWinnerCount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                auctionDataViewHolder.mCtvWinnerCount.setCompoundDrawablePadding(10);
                auctionDataViewHolder.mCtvWinnerCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean.RecordsBean.CustomizeWinningBean> customizeWinning = recordsBean.getCustomizeWinning();
                        List<WinnersRankBean> rankList = new ArrayList<>();
                        for (int i = 0; i < customizeWinning.size(); i++) {
                            WinnersRankBean mWinnersRankBean = new WinnersRankBean();
                            mWinnersRankBean.setFrom(Integer.parseInt(customizeWinning.get(i).getFrom()));
                            mWinnersRankBean.setTo(Integer.parseInt(customizeWinning.get(i).getTo()));
                            mWinnersRankBean.setPercent(customizeWinning.get(i).getPercent() + "");
                            mWinnersRankBean.setWinningAmount(customizeWinning.get(i).getWinningAmount());
                            rankList.add(i, mWinnersRankBean);
                        }
                        ((AuctionContestListingActivity) mContext).showPreview(rankList, recordsBean.getWinningAmount());

                    }
                });
            }
        }


        //uncomment
        if (recordsBean.getAuctionStatus().equals("Pending")) {
            auctionDataViewHolder.mCtvDeadLineLabel.setText((flag == 1 ? "Auction" : "Draft") + " start on");
            /*try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = simpleDateFormat.parse(recordsBean.getLeagueJoinDateTime());
                auctionDataViewHolder.mCtvDeadline.setText(new SimpleDateFormat("MMM dd, yyyy hh:mm a").format(d));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        } else {
            auctionDataViewHolder.mCtvDeadLineLabel.setText("Submit team before");
            //auctionDataViewHolder.mCtvDeadLineLabel.setText("Deadline for team submission/update");
            /*try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = simpleDateFormat.parse(seriesDeadLine);
                auctionDataViewHolder.mCtvDeadline.setText(new SimpleDateFormat("MMM dd, yyyy hh:mm a").format(d));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
        auctionDataViewHolder.setTime();

        if (seriesStatus == AuctionSeriesListingFragment.FIXTURE) {
            auctionDataViewHolder.mLinearLayoutSeriesTimeInfoRoot.setVisibility(View.VISIBLE);
        } else {
            auctionDataViewHolder.mLinearLayoutSeriesTimeInfoRoot.setVisibility(View.GONE);
        }


        //uncomment

        if (recordsBean.getStatus().equals("Cancelled")) {
            auctionDataViewHolder.mCtvJoinButton.setText("CANCELLED");
            auctionDataViewHolder.mCtvJoinButton.setBackgroundResource(R.drawable.bg_auction_btn_red);
            auctionDataViewHolder.mCtvJoinButton.setTextColor(Color.parseColor("#ffffff"));

        } else {
            auctionDataViewHolder.mCtvJoinButton.setBackgroundResource(R.drawable.bg_auc_btn);
            auctionDataViewHolder.mCtvJoinButton.setTextColor(Color.parseColor("#000000"));
            if (recordsBean.getIsJoined().equals("No")) {
                auctionDataViewHolder.mCtvJoinButton.setText("JOIN");
            } else {
                if (recordsBean.getIsAuctionFinalTeamSubmitted() == null) {
                    auctionDataViewHolder.mCtvJoinButton.setText("ENTER");
                } else {
                    if (recordsBean.getIsAuctionFinalTeamSubmitted().equals("Yes")) {
                        if (recordsBean.getIsSeriesMatchStarted().equals("Yes")) {
                            auctionDataViewHolder.mCtvJoinButton.setText("LEADERBOARD");
                        } else {
                            auctionDataViewHolder.mCtvJoinButton.setText("ENTER");
                        }
                    } else {
                        auctionDataViewHolder.mCtvJoinButton.setText("ENTER");
                    }
                }

            }
        }

        //uncomment
        auctionDataViewHolder.mCtvJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        auctionDataViewHolder.mLinearLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auctionDataViewHolder.mCtvJoinButton.performClick();
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof HeaderDataBean) {
            return HEADER;
        } else if (data.get(position) instanceof FooterDataBean) {
            return FOOTER;
        } else {
            return DATA;
        }
    }

    public void clearData() {
        data.clear();
    }


    public long getRemainingTime(int position) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date seriesStartDate = null;
            GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean.RecordsBean recordsBean = (GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean.RecordsBean) data.get(position);
            if (recordsBean.getAuctionStatus().equals("Pending")) {
                seriesStartDate = simpleDateFormat.parse(recordsBean.getLeagueJoinDateTimeUTC());
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


    public class AuctionDataViewHolder extends RecyclerView.ViewHolder {


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

        @BindView(R.id.ll_series_time_info)
        LinearLayout mLinearLayoutSeriesTimeInfoRoot;

        @BindView(R.id.seekBar)
        ProgressBar mProgressBar;

        @BindView(R.id.hi_main_card)
        LinearLayout mLinearLayoutRoot;

        CountDownTimer countDownTimer;


        public AuctionDataViewHolder(@NonNull View itemView) {
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
                            if (index != -1 && data != null && index < data.size()) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date seriesStartDate = null;
                                GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean.RecordsBean recordsBean = (GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean.RecordsBean) data.get(index);
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

    public class AuctionHeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ctv_title)
        CustomTextView mCustomTextViewTitle;


        @BindView(R.id.ctv_desc)
        CustomTextView mCustomTextViewDesc;


        public AuctionHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class AuctionFooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ctv_count)
        CustomTextView mCustomTextCount;


        public AuctionFooterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



























      /*@Override
    public void onBindViewHolder(@NonNull final AuctionDataViewHolder auctionDataViewHolder, final int i) {
        *//*final GetSeriesAuctionContestOutput.DataBean.RecordsBean recordsBean = mRecordsBeanArrayList.get(i);
        int spotLeft = Integer.parseInt(recordsBean.getContestSize()) - Integer.parseInt(recordsBean.getTotalJoined());
        double cashBonus = Double.parseDouble(recordsBean.getCashBonusContribution());
        auctionDataViewHolder.mCtvName.setText(Html.fromHtml("<u>" + recordsBean.getContestName() + "</u>"));
        auctionDataViewHolder.mCtvPrizePool.setText(recordsBean.getWinningAmount());
        auctionDataViewHolder.mCtvWinnerCount.setText(recordsBean.getNoOfWinners());
        auctionDataViewHolder.mCtvEntryFee.setText(recordsBean.getEntryFee());
        if (spotLeft == 0) {
            auctionDataViewHolder.mCtvSpotLeft.setText("League full");
        } else {
            auctionDataViewHolder.mCtvSpotLeft.setText("Only " + spotLeft + " spots left");
        }
        auctionDataViewHolder.mCtvContestSize.setText(recordsBean.getContestSize() + " Teams");
        if (cashBonus == 0) {
            auctionDataViewHolder.mCtvBonusInfo.setVisibility(View.GONE);
        } else {
//            auctionDataViewHolder.mCtvBonusInfo.setText("USE " + cashBonus + "% CASH BONUS");
            auctionDataViewHolder.mCtvBonusInfo.setText(cashBonus + "% BONUS");
            auctionDataViewHolder.mCtvBonusInfo.setVisibility(View.VISIBLE);
        }
        auctionDataViewHolder.mProgressBar.setMax(Integer.parseInt(recordsBean.getContestSize()));
        auctionDataViewHolder.mProgressBar.setProgress(Integer.parseInt(recordsBean.getTotalJoined()));
        auctionDataViewHolder.mProgressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        auctionDataViewHolder.mCtvWinnerCount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        auctionDataViewHolder.mCtvWinnerCount.setOnClickListener(null);
        if (!recordsBean.getNoOfWinners().equals("1")) {

            if (!recordsBean.getNoOfWinners().equals("0")) {
                auctionDataViewHolder.mCtvWinnerCount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                auctionDataViewHolder.mCtvWinnerCount.setCompoundDrawablePadding(10);

                auctionDataViewHolder.mCtvWinnerCount.setOnClickListener(new OnItemClickListener(i, onWinnerClickCallBack));
            }
        }


        if (recordsBean.getAuctionStatus().equals("Pending")) {
            auctionDataViewHolder.mCtvDeadLineLabel.setText((flag==1?"Auction":"Draft")+" start on");
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = simpleDateFormat.parse(recordsBean.getLeagueJoinDateTime());
                auctionDataViewHolder.mCtvDeadline.setText(new SimpleDateFormat("MMM dd, yyyy hh:mm a").format(d));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            auctionDataViewHolder.mCtvDeadLineLabel.setText("Deadline for team submission/update");
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = simpleDateFormat.parse(seriesDeadLine);
                auctionDataViewHolder.mCtvDeadline.setText(new SimpleDateFormat("MMM dd, yyyy hh:mm a").format(d));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        if (recordsBean.getStatus().equals("Cancelled")) {
            auctionDataViewHolder.mCtvJoinButton.setText("CANCELLED");
        } else {
            if (recordsBean.getIsJoined().equals("No")) {
                auctionDataViewHolder.mCtvJoinButton.setText("JOIN");
            } else {
                if (recordsBean.getIsAuctionFinalTeamSubmitted() == null) {
                    auctionDataViewHolder.mCtvJoinButton.setText("ENTER");
                } else {
                    if (recordsBean.getIsAuctionFinalTeamSubmitted().equals("Yes")) {
                        if (recordsBean.getIsSeriesMatchStarted().equals("Yes")) {
                            auctionDataViewHolder.mCtvJoinButton.setText("LEADERBOARD");
                        } else {
                            auctionDataViewHolder.mCtvJoinButton.setText("ENTER");
                        }
                    } else {
                        auctionDataViewHolder.mCtvJoinButton.setText("ENTER");
                    }
                }

            }
        }

        auctionDataViewHolder.mCtvJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recordsBean.getStatus().equals("Cancelled")) {
                    if (recordsBean.getIsJoined().equals("No")) {
                        // call join
                        if (flag == 1) {
                            Intent intent = new Intent(mContext, JoinContestActivity.class);
                            intent.putExtra("isAuction", "Yes");
                            intent.putExtra("series_id", recordsBean.getSeriesID());
                            intent.putExtra("round_id", roundId);
                            intent.putExtra("contestId", recordsBean.getContestGUID());
                            intent.putExtra("joiningAmount", recordsBean.getEntryFee());
                            intent.putExtra("cashBonusContribution", recordsBean.getCashBonusContribution());
                            intent.putExtra("userInviteCode", "");
                            ((AuctionContestListingActivity) mContext).startActivityForResult(intent, REQUEST_CODE_JOIN);
                        } else {
                            Intent intent = new Intent(mContext, JoinContestActivity.class);
                            intent.putExtra("isDraft", "Yes");
                            intent.putExtra("series_id", recordsBean.getSeriesID());
                            intent.putExtra("round_id", roundId);
                            intent.putExtra("contestId", recordsBean.getContestGUID());
                            intent.putExtra("joiningAmount", recordsBean.getEntryFee());
                            intent.putExtra("cashBonusContribution", recordsBean.getCashBonusContribution());
                            intent.putExtra("userInviteCode", "");
                            String message= "** This draft will have "+recordsBean.getDraftTeamPlayerLimit()+" rounds.\n" +
                                    "** Min Criteria for team: Wk: "+recordsBean.getDraftPlayerSelectionCriteria().getWk()+
                                    ", Bat: "+recordsBean.getDraftPlayerSelectionCriteria().getBat()+
                                    ", AR: "+recordsBean.getDraftPlayerSelectionCriteria().getAr()+
                                    ", Bowl: "+recordsBean.getDraftPlayerSelectionCriteria().getBowl()+" .";
                            intent.putExtra("message", message);
                            ((AuctionContestListingActivity) mContext).startActivityForResult(intent, REQUEST_CODE_JOIN);
                        }

                    } else {
                        if (recordsBean.getIsAuctionFinalTeamSubmitted() == null) {
                            //Enter Auction
                            if (flag == 1) {
                                AuctionLobbyActivity.start(mContext,
                                        roundId,
                                        recordsBean.getSeriesID(),
                                        recordsBean.getContestGUID(),
                                        seriesName,
                                        seriesDeadLine,
                                        seriesStatus);
                            } else {
                                DraftHomeActivity.start(mContext,
                                        roundId,
                                        recordsBean.getContestGUID(),
                                        seriesDeadLine);
                            }

                        } else {
                            if (recordsBean.getIsAuctionFinalTeamSubmitted().equals("Yes")) {
                                //Enter Leaderboard
                                if (flag == 1) {
                                    AuctionLobbyActivity.start(mContext,
                                            roundId,
                                            recordsBean.getSeriesID(),
                                            recordsBean.getContestGUID(),
                                            seriesName,
                                            seriesDeadLine,
                                            seriesStatus);
                                } else {
                                    if (recordsBean.getIsSeriesMatchStarted().equals("Yes")) {
                                        AuctionLeaderBoardActivity.start(mContext,
                                                roundId,
                                                recordsBean.getContestGUID(),
                                                flag,
                                                seriesName,seriesDeadLine,seriesStatus);
                                    } else {
                                        DraftHomeActivity.start(mContext, roundId, recordsBean.getContestGUID(), seriesDeadLine);
                                    }
                                }
                            } else {
                                //Enter Auction
                                if (flag == 1) {
                                    AuctionLobbyActivity.start(mContext,
                                            roundId,
                                            recordsBean.getSeriesID(),
                                            recordsBean.getContestGUID(),
                                            seriesName,
                                            seriesDeadLine,
                                            seriesStatus);
                                } else {
                                    DraftHomeActivity.start(mContext, roundId, recordsBean.getContestGUID(), seriesDeadLine);
                                }

                            }
                        }

                    }
                } else {
                    AppUtils.showToast(mContext,"Contest cancelled!");
                }
            }
        });
        if (recordsBean.getPrivacy().equals("Yes")) {
            if (recordsBean.getAuctionStatus().equals("Pending")) {
                if (spotLeft == 0) {
                    auctionDataViewHolder.invitButton.setVisibility(View.GONE);
                } else {
                    auctionDataViewHolder.invitButton.setVisibility(View.VISIBLE);
                }
            } else {
                auctionDataViewHolder.invitButton.setVisibility(View.GONE);
            }
        } else {
            auctionDataViewHolder.invitButton.setVisibility(View.GONE);
        }
        auctionDataViewHolder.invitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.shareTextUrl(mContext,
                        AppUtils.getStrFromRes(R.string.inviteFriendsMore),
                        "Join V/s Paid league on  and win ₹" + recordsBean.getWinningAmount() + ". Entry fee ₹" + recordsBean.getEntryFee() + ". Use league code " + recordsBean.getUserInvitationCode() + ".",
                        "Invite");
            }
        });
        auctionDataViewHolder.mLinearLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auctionDataViewHolder.mCtvJoinButton.performClick();
            }
        });*//*
    }*/


    public class HeaderDataBean {
        private String title, description;

        public HeaderDataBean(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


    public class FooterDataBean {

        private int count;

        public FooterDataBean(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }


}
