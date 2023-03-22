package com.mw.fantasy.UI.auction.auctionHome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.playerpoint.AuctionInfoUtil;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanOutput.GetAuctionJoinedUserOutput;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.AppUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AuctionDetailScreenActivity extends BaseActivity {

    private String contestGUID, roundId, playerGUID, auctionStatus, seriesId, userGUID, userName;
    private boolean isSeriesStarted;
    public static final int ORDER = 1, ASSISTANT = 2, BUDGET = 3, SQUAD = 4, BID_HISTORY = 5, COMPLITED_SQUAD = 7;
    private ArrayList<GetAuctionJoinedUserOutput.DataBean.RecordsBean.UserTeamPlayers> userTeamPlayers;
    private String seriesName, seriesDeadLine ,budgetLeft,auctionStartTime;
    private int seriesStatus;


    public static void start2(
            Context context,
            int flag,
            String roundId,
            String seriesID,
            String contestGUID,
            String auctionStatus,
            String userGUID,
            String userName,
            String seriesName,
            String seriesDeadLine,
            int seriesStatus,
            boolean isSeriesStarted,
            String auctionStartTime) {
        Intent starter = new Intent(context, AuctionDetailScreenActivity.class);
        starter.putExtra("userGUID", userGUID);
        starter.putExtra("userName", userName);
        starter.putExtra("flag", flag);
        starter.putExtra("roundId", roundId);
        starter.putExtra("seriesId", seriesID);
        starter.putExtra("contestGUID", contestGUID);
        starter.putExtra("auctionStatus", auctionStatus);

        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("isSeriesStarted", isSeriesStarted);
        starter.putExtra("auctionStartTime", auctionStartTime);

        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.bottom_activity_enter_1, R.anim.bottom_activity_enter_2);
    }


    public static void start1(Context context, int flag, String roundId, String seriesID, String contestGUID, String auctionStatus
            , String seriesName,
                              String seriesDeadLine,
                              int seriesStatus,
                              String auctionStartTime) {
        Intent starter = new Intent(context, AuctionDetailScreenActivity.class);
        starter.putExtra("flag", flag);
        starter.putExtra("roundId", roundId);
        starter.putExtra("seriesId", seriesID);
        starter.putExtra("contestGUID", contestGUID);
        starter.putExtra("auctionStatus", auctionStatus);
        starter.putExtra("auctionStartTime", auctionStartTime);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.bottom_activity_enter_1, R.anim.bottom_activity_enter_2);
    }


    public static void start(Context context, int flag, String roundId,
                             String seriesID, String contestGUID, String auctionStatus
            , String seriesName,
                              String seriesDeadLine,
                              int seriesStatus,
                              String budgetLeft,
                             String auctionStartTime) {
        Intent starter = new Intent(context, AuctionDetailScreenActivity.class);
        starter.putExtra("flag", flag);
        starter.putExtra("roundId", roundId);
        starter.putExtra("seriesId", seriesID);
        starter.putExtra("contestGUID", contestGUID);
        starter.putExtra("auctionStatus", auctionStatus);

        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("budgetLeft", budgetLeft);
        starter.putExtra("auctionStartTime", auctionStartTime);

        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.bottom_activity_enter_1, R.anim.bottom_activity_enter_2);
    }

    public static void start(Context context, int flag, String roundId, String contestGUID, String playerGUID
            , String seriesName,
                             String seriesDeadLine,
                             int seriesStatus,
                             String auctionStartTime,
                             String auctionStatus) {
        Intent starter = new Intent(context, AuctionDetailScreenActivity.class);
        starter.putExtra("flag", flag);
        starter.putExtra("roundId", roundId);
        starter.putExtra("contestGUID", contestGUID);
        starter.putExtra("playerGUID", playerGUID);
        starter.putExtra("auctionStatus", auctionStatus);
        starter.putExtra("auctionStartTime", auctionStartTime);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.bottom_activity_enter_1, R.anim.bottom_activity_enter_2);
    }


    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;

    @BindView(R.id.ctv_title)
    CustomTextView mCustomTextViewTitle;


    @OnClick(R.id.iv_info)
    void onInfoClick() {
        AppUtils.clickVibrate(this);
        switch (flag) {
            case ASSISTANT:
                AppUtils.showToast(this, "Put your assistant to work and let him know the max bids for your preferred players. In case you are distracted or disconnect for any reason the Assistant will pick for you.");
                break;
            case BUDGET:
                AppUtils.showToast(this, "This shows how much budget is left with each participant. Click on the names to view teams of your opponents!");
                break;
            case ORDER:
                AppUtils.showToast(this, "The order in which the players will go under the hammer. This is a system generated order.");
                break;
            case SQUAD:
            case COMPLITED_SQUAD:
                AppUtils.showToast(this, "This reflects the team you have built so far. View this to see how you are faring and what additions you need");
                break;
            case BID_HISTORY:
                AppUtils.showToast(this, "Log of all bids made on a particular player");
                break;
        }
    }



    @OnClick(R.id.iv_close)
    void onCloseBtnClick() {
        AppUtils.clickVibrate(this);
        onBackPressed();
    }

    private int flag, type;

    @Override
    public int getLayout() {
        return R.layout.activity_auction_detail_screen;
    }

    @Override
    public void init() {

        flag = getIntent().getExtras().getInt("flag");

        if (getIntent().hasExtra("type")) {
            type = getIntent().getExtras().getInt("type");
        }

        if (getIntent().hasExtra("roundId")) {
            roundId = getIntent().getExtras().getString("roundId");
        }

        if (getIntent().hasExtra("budgetLeft")) {
            budgetLeft = getIntent().getExtras().getString("budgetLeft");
        }

        if (getIntent().hasExtra("contestGUID")) {
            contestGUID = getIntent().getExtras().getString("contestGUID");
        }

        if (getIntent().hasExtra("playerGUID")) {
            playerGUID = getIntent().getExtras().getString("playerGUID");
        }

        if (getIntent().hasExtra("auctionStatus")) {
            auctionStatus = getIntent().getExtras().getString("auctionStatus");
        }

        if (getIntent().hasExtra("userTeamPlayers")) {
            userTeamPlayers = (ArrayList<GetAuctionJoinedUserOutput.DataBean.RecordsBean.UserTeamPlayers>) getIntent().getExtras().get("userTeamPlayers");
        }

        if (getIntent().hasExtra("seriesName")) {
            seriesName = getIntent().getExtras().getString("seriesName");
        }

        if (getIntent().hasExtra("seriesDeadLine")) {
            seriesDeadLine = getIntent().getExtras().getString("seriesDeadLine");
        }

        if (getIntent().hasExtra("seriesStatus")) {
            seriesStatus = getIntent().getExtras().getInt("seriesStatus");
        }

        if (getIntent().hasExtra("seriesId")) {
            seriesId = getIntent().getExtras().getString("seriesId");
        }


        if (getIntent().hasExtra("userGUID")) {
            userGUID = getIntent().getExtras().getString("userGUID");
        }

        if (getIntent().hasExtra("userName")) {
            userName = getIntent().getExtras().getString("userName");
        }

        if (getIntent().hasExtra("isSeriesStarted")) {
            isSeriesStarted = getIntent().getExtras().getBoolean("isSeriesStarted");
        }

        if (getIntent().hasExtra("auctionStartTime")) {
            auctionStartTime = getIntent().getExtras().getString("auctionStartTime");
        }

        switch (flag) {
            case ORDER:
                mCustomTextViewTitle.setText("Auction Order");
                setFragment(OrderFragment.newInstance(roundId, contestGUID));
                break;
            case ASSISTANT:
                mCustomTextViewTitle.setText("Auction Assistant");
                setFragment(AssistantFragment.newInstance(roundId, seriesId, contestGUID, auctionStatus,seriesName,seriesDeadLine,seriesStatus, budgetLeft,auctionStartTime));
                break;
            case BUDGET:
                mCustomTextViewTitle.setText("Budget BANK");
                setFragment(BudgetFragment.newInstance(roundId, contestGUID));
                break;
            case SQUAD:
                mCustomTextViewTitle.setText(userName + " Squad");
                setFragment(SquadFragment.newInstance(userGUID, roundId, userName, seriesId, contestGUID, auctionStatus, isSeriesStarted, seriesName, seriesDeadLine, seriesStatus,auctionStartTime));
                break;
            case BID_HISTORY:
                mCustomTextViewTitle.setText("Bid History");
                setFragment(PlayerBidHistoryFragment.newInstance(roundId, contestGUID, playerGUID));
                break;
            case COMPLITED_SQUAD:
                mCustomTextViewTitle.setText(userName + " Squad");
                setFragment(CompletedSquadFragment.newInstance(userGUID, roundId, userName, seriesId, contestGUID, auctionStatus, isSeriesStarted));
                break;

        }

        new AuctionInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName,
                auctionStartTime,
                auctionStatus).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.bottom_activity_exit_1, R.anim.bottom_activity_exit_2);
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

        if (getSupportFragmentManager().findFragmentById(R.id.fl_container) instanceof SquadFragment) {
            SquadFragment fragment = (SquadFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container);
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
