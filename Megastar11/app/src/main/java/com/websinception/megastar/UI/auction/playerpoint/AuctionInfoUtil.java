package com.websinception.megastar.UI.auction.playerpoint;

import android.os.CountDownTimer;

import com.websinception.megastar.R;
import com.websinception.megastar.customView.CustomTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class AuctionInfoUtil {




    private CustomTextView mCustomTextViewName, mCustomTextViewStatus;
    private CountDownTimer countDownTimer;
    private String seriesName,auctionStartTime;
    private String auctionStatus;

    public AuctionInfoUtil(
            CustomTextView mCustomTextViewName,
            CustomTextView mCustomTextViewStatus,
            String seriesName,
            String auctionStartTime,
            String  auctionStatus) {
        this.mCustomTextViewName = mCustomTextViewName;
        this.mCustomTextViewStatus = mCustomTextViewStatus;
        this.seriesName = seriesName;
        this.auctionStartTime = auctionStartTime;
        this.auctionStatus = auctionStatus;

    }

    public void start(){
        setSeriesInfo();
    }

    private void setSeriesInfo() {
        mCustomTextViewName.setText(seriesName);
        switch (auctionStatus) {
            case "Pending":
                //mCustomTextViewName.setText("Auction start in");
                mCustomTextViewStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock, 0, 0, 0);
                mCustomTextViewStatus.setBackground(null);
                startTimmer();
                break;
            case "Running":
                //mCustomTextViewName.setText("Auction Status");
                mCustomTextViewStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                mCustomTextViewStatus.setText("● LIVE");
                mCustomTextViewStatus.setBackgroundResource(R.drawable.bg_live_btn);
                break;
            case "Completed":
                //mCustomTextViewName.setText("Auction Status");
                mCustomTextViewStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                mCustomTextViewStatus.setText("COMPLETED");
                mCustomTextViewStatus.setBackground(null);
                break;
        }
    }



    private void startTimmer() {
        try {
            if (countDownTimer != null) countDownTimer.cancel();
            countDownTimer = new CountDownTimer(getRemainingTime(auctionStartTime),
                    TimeUnit.SECONDS.toMillis(1)) {
                public void onTick(long millisUntilFinished) {
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        Date seriesStartDate = simpleDateFormat.parse(auctionStartTime);
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
                                    mCustomTextViewStatus.setText(finalDays+" days");
                                }else if (finalHours>0){
                                    mCustomTextViewStatus.setText(finalHours+" hrs");
                                }else if (finalMinute>0){
                                    mCustomTextViewStatus.setText(finalMinute+" min");
                                }else if (finalSeconds>0){
                                    mCustomTextViewStatus.setText(finalSeconds+" sec");
                                }else {
                                    mCustomTextViewStatus.setText("0 sec");
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
                            auctionStatus="Running";
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
