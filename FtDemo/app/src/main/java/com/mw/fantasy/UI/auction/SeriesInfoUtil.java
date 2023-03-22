package com.mw.fantasy.UI.auction;

import android.os.CountDownTimer;

import com.mw.fantasy.R;
import com.mw.fantasy.customView.CustomTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static com.mw.fantasy.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.COMPLETED;
import static com.mw.fantasy.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.FIXTURE;
import static com.mw.fantasy.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.LIVE;

public class SeriesInfoUtil {




    private CustomTextView mCustomTextViewName, mCustomTextViewStatus;
    private CountDownTimer countDownTimer;
    private String seriesName,seriesDeadLine;
    private int seriesStatus;

    public SeriesInfoUtil(
            CustomTextView mCustomTextViewName,
            CustomTextView mCustomTextViewStatus,
            String seriesName,
            String seriesDeadLine,
            int seriesStatus) {
        this.mCustomTextViewName = mCustomTextViewName;
        this.mCustomTextViewStatus = mCustomTextViewStatus;
        this.seriesName = seriesName;
        this.seriesDeadLine = seriesDeadLine;
        this.seriesStatus = seriesStatus;

    }

    public void start(){
        setSeriesInfo();
    }

    private void setSeriesInfo() {
        mCustomTextViewName.setText(seriesName);
        switch (seriesStatus) {
            case FIXTURE:
                mCustomTextViewStatus.setText("00d : 00h : 00m : 00s");
                mCustomTextViewStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock, 0, 0, 0);
                mCustomTextViewStatus.setBackground(null);
                startTimmer();
                break;
            case LIVE:
                mCustomTextViewStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                mCustomTextViewStatus.setText("● LIVE");
                mCustomTextViewStatus.setBackgroundResource(R.drawable.bg_live_btn);
                break;
            case COMPLETED:
                mCustomTextViewStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                mCustomTextViewStatus.setText("COMPLETED");
                mCustomTextViewStatus.setBackground(null);
                break;
        }
    }



    private void startTimmer() {
        try {
            if (countDownTimer != null) countDownTimer.cancel();
            countDownTimer = new CountDownTimer(getRemainingTime(seriesDeadLine),
                    TimeUnit.SECONDS.toMillis(1)) {
                public void onTick(long millisUntilFinished) {
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        Date seriesStartDate = simpleDateFormat.parse(seriesDeadLine);
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
                        final long finalDays = days;
                        final long finalHours = hours;
                        final long finalMinute = minute;
                        final long finalSeconds = seconds;
                        mCustomTextViewStatus.post(new Runnable() {
                            @Override
                            public void run() {
                                if (finalDays>0) {
                                    mCustomTextViewStatus.setText(finalDays+" days left");
                                }else if (finalHours>0){
                                    mCustomTextViewStatus.setText(finalHours+" hrs left");
                                }else if (finalMinute>0){
                                    mCustomTextViewStatus.setText(finalMinute+" min left");
                                }else if (finalSeconds>0){
                                    mCustomTextViewStatus.setText(finalSeconds+" sec left");
                                }else {
                                    mCustomTextViewStatus.setText("0 sec left");
                                }

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        mCustomTextViewStatus.post(new Runnable() {
                            @Override
                            public void run() {
                                mCustomTextViewStatus.setText("Error");
                            }
                        });

                    }
                }

                public void onFinish() {
                    mCustomTextViewStatus.post(new Runnable() {
                        @Override
                        public void run() {
                            mCustomTextViewStatus.setText("00d : 00h : 00m : 00s");
                            setSeriesInfo();
                        }
                    });

                }
            };
            countDownTimer.start();

        } catch (Exception e1) {
            e1.printStackTrace();
            mCustomTextViewStatus.setText("Error");
        }

    }


    private long getRemainingTime(String seriesDeadLine) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date seriesStartDate = simpleDateFormat.parse(seriesDeadLine);
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

}
