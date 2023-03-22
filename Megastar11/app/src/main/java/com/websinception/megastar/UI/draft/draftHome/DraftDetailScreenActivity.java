package com.websinception.megastar.UI.draft.draftHome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.playerpoint.AuctionInfoUtil;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanOutput.GetAuctionInfoOutput;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;

public class DraftDetailScreenActivity extends BaseActivity {

    public static final int REQUEST_CODE = 158;
    private String contestGUID, seriesGUID, auctionStatus, seriesDeadLine, seriesName, matchGUID, seriesId, userName, userGUID, budgetLeft, auctionStartTime;
    private GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria mDraftPlayerSelectionCriteria;
    private int squadType, seriesStatus;
    private Fragment currentFragment;
    public static final int SQUAD = 1;
    public static final int ASSISTANT = 2;
    public static final int COMPLETED_SQUAD = 3;


    public static void start(
            Context context,
            int flag,
            String userName,
            String seriesName,
            String seriesDeadLine,
            int seriesStatus,
            GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria draftPlayerSelectionCriteria,
            int squadType,
            String matchGUID,
            String contestGUID,
            String auctionStatus,
            String seriesId,
            String userGUID,
            String budgetLeft,
            String auctionStartTime
    ) {
        Intent starter = new Intent(context, DraftDetailScreenActivity.class);
        starter.putExtra("flag", flag);
        starter.putExtra("matchGUID", matchGUID);
        starter.putExtra("userName", userName);
        starter.putExtra("contestGUID", contestGUID);
        starter.putExtra("userGUID", userGUID);
        starter.putExtra("auctionStatus", auctionStatus);
        starter.putExtra("squadType", squadType);
        starter.putExtra("draftPlayerSelectionCriteria", draftPlayerSelectionCriteria);
        starter.putExtra("seriesDeadLine", seriesDeadLine);

        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("seriesId", seriesId);
        starter.putExtra("budgetLeft", budgetLeft);
        starter.putExtra("auctionStartTime", auctionStartTime);
        ((Activity) context).startActivityForResult(starter, REQUEST_CODE);
        /*context.startActivity(starter);*/
        ((Activity) context).overridePendingTransition(R.anim.bottom_activity_enter_1, R.anim.bottom_activity_enter_2);
    }


    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;

  /*  @BindView(R.id.ctv_time)
    CustomTextView mCtvSeriesTime;*/


    @BindView(R.id.ctv_title)
    CustomTextView mCustomTextViewTitle;

    @OnClick(R.id.iv_close)
    void onCloseBtnClick() {
        AppUtils.clickVibrate(this);
        onBackPressed();
    }

    @OnClick(R.id.iv_info)
    void onInfoClick() {
        AppUtils.clickVibrate(this);
        switch (flag) {
            case SQUAD:
                if (squadType == 1) {
                    AppUtils.showToast(this, "This reflects the team you have built so far. Below, you can track how many of the minimum criteria you have met and how many still need to be picked");
                } else {
                    AppUtils.showToast(this, "Here you can track how your opponent's squad is shaping up. Use the filter to switch between squads of various opponents.");
                }
                break;
            case ASSISTANT:
                AppUtils.showToast(this, "Put your Assistant to work and let him know your priority picks. In case you are distracted or disconnect for any reason the Assistant will pick for you.Please do note that the minimum picks for criteria take precedence.");
                break;
        }
    }

    private int flag;

    @Override
    public int getLayout() {
        return R.layout.activity_auction_detail_screen;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        if (intent.hasExtra("flag"))
            flag = getIntent().getExtras().getInt("flag");
        if (intent.hasExtra("matchGUID"))
            matchGUID = getIntent().getExtras().getString("matchGUID");
        if (intent.hasExtra("userGUID"))
            userGUID = getIntent().getExtras().getString("userGUID");
        if (intent.hasExtra("contestGUID"))
            contestGUID = getIntent().getExtras().getString("contestGUID");
        if (intent.hasExtra("auctionStatus"))
            auctionStatus = getIntent().getExtras().getString("auctionStatus");
        if (intent.hasExtra("squadType"))
            squadType = getIntent().getExtras().getInt("squadType");
        if (intent.hasExtra("draftPlayerSelectionCriteria"))
            mDraftPlayerSelectionCriteria = (GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria) getIntent().getExtras().get("draftPlayerSelectionCriteria");
        if (intent.hasExtra("seriesDeadLine"))
            seriesDeadLine = getIntent().getExtras().getString("seriesDeadLine");
        if (intent.hasExtra("seriesName"))
            seriesName = getIntent().getExtras().getString("seriesName");
        if (intent.hasExtra("budgetLeft"))
            budgetLeft = getIntent().getExtras().getString("budgetLeft");
        if (intent.hasExtra("seriesId"))
            seriesId = getIntent().getExtras().getString("seriesId");

        if (intent.hasExtra("seriesStatus"))
            seriesStatus = getIntent().getExtras().getInt("seriesStatus");

        if (getIntent().hasExtra("userName")) {
            userName = getIntent().getExtras().getString("userName");
        }
        if (getIntent().hasExtra("auctionStartTime")) {
            auctionStartTime = getIntent().getExtras().getString("auctionStartTime");
        }

        switch (flag) {
            case SQUAD:
                if (squadType == MySquadFragment.MY_SQUAD) {
                    //mCtvSeriesTime.setVisibility(View.VISIBLE);
                    //startTimmer();
                    mCustomTextViewTitle.setText("My Squad");
                } else {
                    mCustomTextViewTitle.setText("Other's Squad");
                }
                setFragment(MySquadFragment.newInstance(matchGUID, seriesId, contestGUID, auctionStatus, squadType, mDraftPlayerSelectionCriteria, seriesName, seriesDeadLine, seriesStatus, userGUID, auctionStartTime));
                break;
            case ASSISTANT:
                mCustomTextViewTitle.setText("My Assistant");
                setFragment(DraftAssistantFragment.newInstance(matchGUID, seriesId, contestGUID, mDraftPlayerSelectionCriteria, auctionStatus, seriesName, seriesDeadLine, seriesStatus, budgetLeft, auctionStartTime));
                break;
            case COMPLETED_SQUAD:
                mCustomTextViewTitle.setText(userName + " Squad");
                setFragment(CompletedSquadFragment.newInstance(userGUID,
                        matchGUID,
                        seriesId,
                        contestGUID));
                break;
        }

        new AuctionInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName,
                auctionStartTime,
                auctionStatus).start();

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK, getIntent());
        finish();

     /*   super.onBackPressed();
        overridePendingTransition(R.anim.bottom_activity_exit_1, R.anim.bottom_activity_exit_2);*/
    }


    public void setFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fl_container, fragment);
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (getSupportFragmentManager().findFragmentById(R.id.fl_container) instanceof MySquadFragment) {
            MySquadFragment fragment = (MySquadFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container);
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }*/
    }


    CountDownTimer countDownTimer;

/*
    public void startTimmer() {
        try {
            if (countDownTimer != null) countDownTimer.cancel();
            countDownTimer = new CountDownTimer(getRemainingTime(),
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
                        mCtvSeriesTime.post(new Runnable() {
                            @Override
                            public void run() {
                                mCtvSeriesTime.setText(String.format("%02d", finalDays) + "d : " +
                                        String.format("%02d", finalHours) + "h : " +
                                        String.format("%02d", finalMinute) + "m : " +
                                        String.format("%02d", finalSeconds) + "s");
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        mCtvSeriesTime.post(new Runnable() {
                            @Override
                            public void run() {
                                mCtvSeriesTime.setText("Error");
                            }
                        });

                    }
                }

                public void onFinish() {
                    mCtvSeriesTime.post(new Runnable() {
                        @Override
                        public void run() {
                            mCtvSeriesTime.setText("00d : 00h : 00m : 00s");
                        }
                    });

                }
            };
            countDownTimer.start();

        } catch (Exception e1) {
            e1.printStackTrace();
            mCtvSeriesTime.setText("Error");
        }

    }
*/

    public long getRemainingTime() {
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
