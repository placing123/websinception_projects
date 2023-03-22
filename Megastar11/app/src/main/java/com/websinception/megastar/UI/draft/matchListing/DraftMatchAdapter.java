package com.websinception.megastar.UI.draft.matchListing;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.auctionContestListing.myContest.MyContestPendingLiveListingActivity;
import com.websinception.megastar.UI.auction.auctionContestListing.upcoming.AuctionContestListingActivity;
import com.websinception.megastar.beanOutput.MatchResponseOut;
import com.websinception.megastar.beanOutput.MyContestMatchesOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.TimeUtils;
import com.websinception.megastar.utility.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.websinception.megastar.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.COMPLETED;
import static com.websinception.megastar.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.FIXTURE;
import static com.websinception.megastar.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.LIVE;

public class DraftMatchAdapter extends RecyclerView.Adapter<DraftMatchAdapter.DraftMatchViewHolder> {

    private int type, seriesStatus;
    private Context mContext;
    private ArrayList<MatchResponseOut.DataBean.RecordsBean> mRecordsBeanArrayList;
    private ArrayList<MyContestMatchesOutput.DataBean.RecordsBean> mRecordsBeanArrayListJoined;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public DraftMatchAdapter(Context mContext,
                             ArrayList<MatchResponseOut.DataBean.RecordsBean> mRecordsBeanArrayList,
                             ArrayList<MyContestMatchesOutput.DataBean.RecordsBean> mRecordsBeanArrayListJoined,
                             int type, int seriesStatus) {
        this.mContext = mContext;
        this.mRecordsBeanArrayList = mRecordsBeanArrayList;
        this.mRecordsBeanArrayListJoined = mRecordsBeanArrayListJoined;
        this.type = type;
        this.seriesStatus = seriesStatus;

    }

    @NonNull
    @Override
    public DraftMatchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DraftMatchViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_draft_match_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final DraftMatchViewHolder holder, final int position) {
        holder.mCustomImageViewTeam1.getHierarchy().setFailureImage(R.drawable.match_defult150);
        holder.mCustomImageViewTeam1.getHierarchy().setPlaceholderImage(R.drawable.match_defult150);
        holder.mCustomImageViewTeam2.getHierarchy().setFailureImage(R.drawable.match_defult150);
        holder.mCustomImageViewTeam2.getHierarchy().setPlaceholderImage(R.drawable.match_defult150);
        if (type == 1) {
            onBindViewHolderAll(holder, position);
        } else {
            onBindViewHolderMy(holder, position);
        }
    }


    private void onBindViewHolderAll(DraftMatchViewHolder holder, final int position) {
        ViewUtils.setImageUrl(holder.mCustomImageViewTeam1, mRecordsBeanArrayList.get(position).getTeamFlagLocal());
        ViewUtils.setImageUrl(holder.mCustomImageViewTeam2, mRecordsBeanArrayList.get(position).getTeamFlagVisitor());
        holder.mCustomTextViewTeam1.setText(mRecordsBeanArrayList.get(position).getTeamNameShortLocal());
        holder.mCustomTextViewTeam2.setText(mRecordsBeanArrayList.get(position).getTeamNameShortVisitor());
        holder.ctv_series_name.setText(mRecordsBeanArrayList.get(position).getSeriesName());
        holder.ctv_match_type.setText(mRecordsBeanArrayList.get(position).getMatchType());
//        if (mRecordsBeanArrayList.get(position).getStatus().equals(Constant.Pending)) {
//            if (mRecordsBeanArrayList.get(position).getContestAvailable().equalsIgnoreCase("Yes") &&
//                    mRecordsBeanArrayList.get(position).getTeamPlayersAvailable().equalsIgnoreCase("Yes")) {
//                holder.mCardViewMainCard.setAlpha(1f);
//            } else {
//                holder.mCardViewMainCard.setAlpha(0.3f);
//            }
//        }
        if (mRecordsBeanArrayList.get(position).getStatus().equals(Constant.Pending)) {
            if ( mRecordsBeanArrayList.get(position).getMatchDisplay().equalsIgnoreCase("Enable")){
                holder.bluredRel.setVisibility(View.GONE);
            }else {
                holder.bluredRel.setVisibility(View.VISIBLE);
            }

        }else {
            holder.bluredRel.setVisibility(View.GONE);
        }

        holder.mCtv_status.setVisibility(View.GONE);
        holder.mLinearLayoutTimmerRoot.setVisibility(View.VISIBLE);
        holder.setTimeAll();


        holder.mCardViewMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matchName = mRecordsBeanArrayList.get(position).getTeamNameShortLocal() + " VS " + mRecordsBeanArrayList.get(position).getTeamNameShortVisitor() /*+ " | " + mRecordsBeanArrayList.get(position).getSeriesName()*/;

                if (type == 2) {
                    MyContestPendingLiveListingActivity.start(
                            mContext,
                            2,
                            mRecordsBeanArrayList.get(position).getMatchGUID(),
                            type,
                            seriesStatus,
                            matchName,
                            mRecordsBeanArrayList.get(position).getMatchStartDateTimeUTC());
                } else {
                    if (mRecordsBeanArrayList.get(position).getStatus().equals(Constant.Pending)) {
                        if (mRecordsBeanArrayList.get(position).getMatchDisplay().equalsIgnoreCase("Enable")) {
                            AuctionContestListingActivity.start(mContext,
                                    1, mRecordsBeanArrayList.get(position).getMatchGUID(),
                                    1,
                                    mRecordsBeanArrayList.get(position).getSeriesID(),
                                    matchName,
                                    mRecordsBeanArrayList.get(position).getMatchStartDateTimeUTC(),
                                    mRecordsBeanArrayList.get(position).getMatchStartDateTime());
                        } else {
                            AppUtils.showToast(mContext, "Contests for this match will open soon");
                        }

                    }
                }

            }
        });

    }

    private void onBindViewHolderMy(DraftMatchViewHolder holder, final int position) {
        ViewUtils.setImageUrl(holder.mCustomImageViewTeam1, mRecordsBeanArrayListJoined.get(position).getTeamFlagLocal());
        ViewUtils.setImageUrl(holder.mCustomImageViewTeam2, mRecordsBeanArrayListJoined.get(position).getTeamFlagVisitor());
        holder.mCustomTextViewTeam1.setText(mRecordsBeanArrayListJoined.get(position).getTeamNameShortLocal());
        holder.mCustomTextViewTeam2.setText(mRecordsBeanArrayListJoined.get(position).getTeamNameShortVisitor());
        holder.ctv_series_name.setText(mRecordsBeanArrayListJoined.get(position).getSeriesName());
        holder.ctv_match_type.setText(mRecordsBeanArrayListJoined.get(position).getMatchType());

        holder.mCtv_status.setVisibility(View.GONE);
        holder.mLinearLayoutTimmerRoot.setVisibility(View.GONE);

        switch (seriesStatus) {
            case FIXTURE:
                holder.mLinearLayoutTimmerRoot.setVisibility(View.VISIBLE);
                holder.setTimeMy();
                break;
            case LIVE:
                holder.mCtv_status.setVisibility(View.VISIBLE);
                holder.mCtv_status.setText("Live");
                break;
            case COMPLETED:
                holder.mCtv_status.setVisibility(View.VISIBLE);
                holder.mCtv_status.setText("Completed");
                break;
        }

        holder.mCardViewMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matchName = mRecordsBeanArrayListJoined.get(position).getTeamNameShortLocal() + " VS " + mRecordsBeanArrayListJoined.get(position).getTeamNameShortVisitor() /*+ " | " + mRecordsBeanArrayListJoined.get(position).getSeriesName()*/;
                if (mRecordsBeanArrayListJoined.get(position).getStatus().equals("Abandoned")) {
                    AppUtils.showToast(mContext, "Match Abandoned");
                } else {
                    MyContestPendingLiveListingActivity.start(
                            mContext,
                            2,
                            mRecordsBeanArrayListJoined.get(position).getMatchGUID(),
                            type,
                            seriesStatus,
                            matchName,
                            mRecordsBeanArrayListJoined.get(position).getMatchStartDateTimeUTC());
                }


            }
        });


    }


    @Override
    public int getItemCount() {
        if (type == 1) {
            return mRecordsBeanArrayList.size();
        } else {
            return mRecordsBeanArrayListJoined.size();
        }

    }


    public long getRemainingTime(int position) {
        return TimeUtils.getTimeDifference(mRecordsBeanArrayList.get(position).getMatchStartDateTime(),
                mRecordsBeanArrayList.get(position).getCurrentDateTime());
    }

    public long getRemainingTimeJoined(int position) {
        return TimeUtils.getTimeDifference(mRecordsBeanArrayListJoined.get(position).getMatchStartDateTime(),
                mRecordsBeanArrayListJoined.get(position).getCurrentDateTime());
    }

    public class DraftMatchViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ll_container)
        @Nullable
        View mCardViewMainCard;


        @BindView(R.id.ll_timmer_cont)
        LinearLayout mLinearLayoutTimmerRoot;

        @BindView(R.id.iv_team_1)
        @Nullable
        CustomImageView mCustomImageViewTeam1;

        @BindView(R.id.iv_team_2)
        @Nullable
        CustomImageView mCustomImageViewTeam2;


        @BindView(R.id.ctv_name_1)
        CustomTextView mCustomTextViewTeam1;

        @BindView(R.id.ctv_name_2)
        CustomTextView mCustomTextViewTeam2;

        @BindView(R.id.ctv_series_name)
        CustomTextView ctv_series_name;

        @BindView(R.id.ctv_match_type)
        CustomTextView ctv_match_type;


        @BindView(R.id.ctv_status)
        CustomTextView mCtv_status;

        @BindView(R.id.ctv_day)
        CustomTextView mCtv_day;

        @BindView(R.id.ctv_min)
        CustomTextView mCtv_min;

        @BindView(R.id.ctv_sec)
        CustomTextView mCtv_sec;

        @BindView(R.id.ctv_hrs)
        CustomTextView mCtv_hrs;


        @BindView(R.id.bluredRel)
        LinearLayout bluredRel;



       /* @BindView(R.id.contest_joined)
        CustomTextView contest_joined;*/



/*
        @BindView(R.id.linout)
        LinearLayout linout;

        @BindView(R.id.bluredRel)
        RelativeLayout bluredRel;*/


        public DraftMatchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        CountDownTimer countDownTimer;


        public void setTimeAll() {
            try {
                if (countDownTimer != null) countDownTimer.cancel();
                countDownTimer = new CountDownTimer(getRemainingTime(getAdapterPosition()),
                        TimeUnit.SECONDS.toMillis(1)) {
                    public void onTick(long millisUntilFinished) {
                        try {
                            int index = getAdapterPosition();
                            if (index != -1 && mRecordsBeanArrayList != null && index < mRecordsBeanArrayList.size()) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date seriesStartDate = simpleDateFormat.parse(mRecordsBeanArrayList.get(index).getMatchStartDateTimeUTC());
                                long time = seriesStartDate.getTime();
                                long diff = (time - System.currentTimeMillis()) / 1000;
                                long days = 0, hours = 0, minute = 0, seconds = 0;
                                if (diff > 0) {
                                    days = diff / (24 * 3600);
                                    diff = diff % (24 * 3600);
                                    hours = diff / 3600;
                                    diff %= 3600;
                                    minute = diff / 60;
                                    diff %= 60;
                                    seconds = diff;
                                }
                                mCtv_day.setText(String.format("%02d", days));
                                mCtv_hrs.setText(String.format("%02d", hours));
                                mCtv_min.setText(String.format("%02d", minute));
                                mCtv_sec.setText(String.format("%02d", seconds));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            mCtv_day.setText("ER");
                            mCtv_hrs.setText("ER");
                            mCtv_min.setText("ER");
                            mCtv_sec.setText("ER");
                        }

                    }

                    public void onFinish() {
                        mCtv_day.setText("00");
                        mCtv_hrs.setText("00");
                        mCtv_min.setText("00");
                        mCtv_sec.setText("00");
                    }
                };
                countDownTimer.start();

            } catch (Exception e1) {
                e1.printStackTrace();
                mCtv_day.setText("ER");
                mCtv_hrs.setText("ER");
                mCtv_min.setText("ER");
                mCtv_sec.setText("ER");
            }
        }


        public void setTimeMy() {


            try {
                if (countDownTimer != null) countDownTimer.cancel();
                countDownTimer = new CountDownTimer(getRemainingTimeJoined(getAdapterPosition()),
                        TimeUnit.SECONDS.toMillis(1)) {
                    public void onTick(long millisUntilFinished) {
                        try {
                            int index = getAdapterPosition();
                            if (index != -1 && mRecordsBeanArrayListJoined != null && index < mRecordsBeanArrayListJoined.size()) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date seriesStartDate = simpleDateFormat.parse(mRecordsBeanArrayListJoined.get(index).getMatchStartDateTimeUTC());
                                long time = seriesStartDate.getTime();
                                long diff = (time - System.currentTimeMillis()) / 1000;
                                long days = 0, hours = 0, minute = 0, seconds = 0;
                                if (diff > 0) {
                                    days = diff / (24 * 3600);
                                    diff = diff % (24 * 3600);
                                    hours = diff / 3600;
                                    diff %= 3600;
                                    minute = diff / 60;
                                    diff %= 60;
                                    seconds = diff;
                                }
                                mCtv_day.setText(String.format("%02d", days));
                                mCtv_hrs.setText(String.format("%02d", hours));
                                mCtv_min.setText(String.format("%02d", minute));
                                mCtv_sec.setText(String.format("%02d", seconds));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            mCtv_day.setText("ER");
                            mCtv_hrs.setText("ER");
                            mCtv_min.setText("ER");
                            mCtv_sec.setText("ER");
                        }

                    }

                    public void onFinish() {
                        mCtv_day.setText("00");
                        mCtv_hrs.setText("00");
                        mCtv_min.setText("00");
                        mCtv_sec.setText("00");
                    }
                };
                countDownTimer.start();

            } catch (Exception e1) {
                e1.printStackTrace();
                mCtv_day.setText("ER");
                mCtv_hrs.setText("ER");
                mCtv_min.setText("ER");
                mCtv_sec.setText("ER");
            }

        }


    }

}