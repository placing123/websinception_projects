package com.websinception.megastar.UI.auction.auctionSeriesListing;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.auctionContestListing.myContest.MyContestPendingLiveListingActivity;
import com.websinception.megastar.UI.auction.auctionContestListing.upcoming.AuctionContestListingActivity;
import com.websinception.megastar.beanInput.GetAuctionSeriesOutput;
import com.websinception.megastar.customView.CustomTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.websinception.megastar.UI.auction.auctionHome.AuctionHomeFragment.ALL;
import static com.websinception.megastar.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.COMPLETED;
import static com.websinception.megastar.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.FIXTURE;
import static com.websinception.megastar.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.LIVE;

public class AuctionSeriesListAdapter extends RecyclerView.Adapter<AuctionSeriesListAdapter.ActionListViewHolder> {

    private AuctionSeriesListingFragment mAuctionSeriesListingFragment;
    private ArrayList<GetAuctionSeriesOutput.DataBean.RecordsBean> mDataBeansArrayList;
    private int seriesStatus, type,flag;

    public AuctionSeriesListAdapter(AuctionSeriesListingFragment auctionSeriesListingFragment,
                                    ArrayList<GetAuctionSeriesOutput.DataBean.RecordsBean> mDataBeansArrayList,
                                    int type,
                                    int flag,
                                    int seriesStatus) {
        this.mDataBeansArrayList = mDataBeansArrayList;
        this.mAuctionSeriesListingFragment = auctionSeriesListingFragment;
        this.seriesStatus = seriesStatus;
        this.type = type;
        this.flag = flag;
    }

    @NonNull
    @Override
    public ActionListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ActionListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_auction_series_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActionListViewHolder actionListViewHolder, int i) {
        final GetAuctionSeriesOutput.DataBean.RecordsBean recordsBean = mDataBeansArrayList.get(i);
        actionListViewHolder.mCtv_seriesName.setText(recordsBean.getSeriesName());
        actionListViewHolder.mLinearLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type==ALL) {
                    AuctionContestListingActivity.start(mAuctionSeriesListingFragment.getContext(),
                            flag,
                            recordsBean.getRoundID(),
                            recordsBean.getSeriesID(),
                            type,
                            seriesStatus,
                            recordsBean.getSeriesName(),
                            recordsBean.getSeriesMatchStartDate());
                }else {
                    if (seriesStatus==COMPLETED) {
                        MyContestPendingLiveListingActivity.start(
                                mAuctionSeriesListingFragment.getContext(),
                                flag,
                                recordsBean.getRoundID(),
                                type,
                                seriesStatus,
                                recordsBean.getSeriesName(),
                                recordsBean.getSeriesMatchStartDate());
                    }else {
                        MyContestPendingLiveListingActivity.start(
                                mAuctionSeriesListingFragment.getContext(),
                                flag,
                                recordsBean.getRoundID(),
                                type,
                                seriesStatus,
                                recordsBean.getSeriesName(),
                                recordsBean.getSeriesMatchStartDate());
                    }
                }

            }
        });
        actionListViewHolder.mCtv_status.setVisibility(View.GONE);
        actionListViewHolder.mLinearLayoutTimmerRoot.setVisibility(View.GONE);
        switch (seriesStatus) {
            case FIXTURE:
                actionListViewHolder.mLinearLayoutTimmerRoot.setVisibility(View.VISIBLE);
                actionListViewHolder.setTime();
                break;
            case LIVE:
                actionListViewHolder.mCtv_status.setVisibility(View.VISIBLE);
                actionListViewHolder.mCtv_status.setText("Live");
                break;
            case COMPLETED:
                actionListViewHolder.mCtv_status.setVisibility(View.VISIBLE);
                actionListViewHolder.mCtv_status.setText("Completed");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mDataBeansArrayList.size();
    }


    public long getRemainingTime(int position) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date seriesStartDate = simpleDateFormat.parse(mDataBeansArrayList.get(position).getSeriesMatchStartDate());
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

    public class ActionListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_container)
        LinearLayout mLinearLayoutRoot;

        @BindView(R.id.ll_timmer_cont)
        LinearLayout mLinearLayoutTimmerRoot;

        @BindView(R.id.ctv_series_name)
        CustomTextView mCtv_seriesName;

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

        CountDownTimer countDownTimer;

        public ActionListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setTime() {
            try {
                if (countDownTimer != null) countDownTimer.cancel();
                countDownTimer = new CountDownTimer(getRemainingTime(getAdapterPosition()),
                        TimeUnit.SECONDS.toMillis(1)) {
                    public void onTick(long millisUntilFinished) {
                        try {
                            int index = getAdapterPosition();
                            if (index!=-1&&mDataBeansArrayList!=null&&index<mDataBeansArrayList.size()) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date seriesStartDate = simpleDateFormat.parse(mDataBeansArrayList.get(index).getSeriesMatchStartDate());
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
                            mCtv_day.setText("EE");
                            mCtv_hrs.setText("EE");
                            mCtv_min.setText("EE");
                            mCtv_sec.setText("EE");
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
                mCtv_day.setText("EE");
                mCtv_hrs.setText("EE");
                mCtv_min.setText("EE");
                mCtv_sec.setText("EE");
            }

        }
    }


}
