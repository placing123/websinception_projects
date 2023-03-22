package com.websinception.megastar.UI.auction.auctionHome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.websinception.megastar.AppController;
import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.playerpoint.AuctionInfoUtil;
import com.websinception.megastar.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanInput.GetAuctionInfoInput;
import com.websinception.megastar.beanInput.GetAuctionJoinedUserInput;
import com.websinception.megastar.beanInput.GetAuctionPlayerInput;
import com.websinception.megastar.beanOutput.AuctionBidSuccessBean;
import com.websinception.megastar.beanOutput.AuctionPlayerStausDataBean;
import com.websinception.megastar.beanOutput.GetAuctionInfoOutput;
import com.websinception.megastar.beanOutput.GetAuctionJoinedUserOutput;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.beanOutput.GetCurrentLiveAuctionPlayerOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.customView.StepperView;

import com.websinception.megastar.dialog.AuctionAlertDialog;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;
import com.websinception.megastar.utility.ViewUtils;
import com.websinception.megastar.utility.socketUtil.SocketUtility;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;
import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class AuctionHomeActivity extends BaseActivity {


    public static void start(Context context, String roundId, String seriesID, String contestGUID, String seriesName, String seriesDeadLine, int seriesStatus) {
        Intent starter = new Intent(context, AuctionHomeActivity.class);
        starter.putExtra("roundId", roundId);
        starter.putExtra("seriesID", seriesID);
        starter.putExtra("contestGUID", contestGUID);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);
        context.startActivity(starter);
    }

    //===================================Click function=============================================
    //===================================Click function=============================================
    //===================================Click function=============================================

    /*
    Home btn on toolbar left click
     */
    @OnClick(R.id.iv_home)
    void onHomeBtnClick() {
        AppUtils.clickVibrate(this);
        new AuctionAlertDialog(this, "Are you sure you want to leave?", "Yes", "No", new AuctionAlertDialog.OnOkayBtnClickListener() {
            @Override
            public void onClick() {
                onBackPressed();
            }

            @Override
            public void onCancel() {
            }
        }).show();
    }

    /*
    Player order screen redirection click
     */
    @OnClick(R.id.ctv_btn_order)
    void onOrderBtnClick() {
        AppUtils.clickVibrate(this);
        AuctionDetailScreenActivity.start1(
                this,
                AuctionDetailScreenActivity.ORDER,
                roundId,
                seriesID,
                contestGUID,
                mGetAuctionInfoOutput.getData().getAuctionStatus(),
                seriesName,
                seriesDeadLine,
                seriesStatus,
                mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());
    }

    /*
    User Budget screen redirection click
     */
    @OnClick(R.id.ctv_btn_budget)
    void onBudgetBtnClick() {
        AppUtils.clickVibrate(this);
        AuctionDetailScreenActivity.start1(
                this,
                AuctionDetailScreenActivity.BUDGET,
                roundId,
                seriesID,
                contestGUID,
                mGetAuctionInfoOutput.getData().getAuctionStatus(),
                seriesName,
                seriesDeadLine,
                seriesStatus,
                mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());
    }

    /*
    My Assistant screen redirection click
     */
    @OnClick(R.id.ctv_btn_assistant)
    void onAssistantBtnClick() {
        AppUtils.clickVibrate(this);
        String budgetStr = "--";
        if (myLiveInfo != null && myLiveInfo.getAuctionBudget() != null && !myLiveInfo.getAuctionBudget().trim().isEmpty()) {
            try {
                final int budget = Integer.parseInt(myLiveInfo.getAuctionBudget().trim());
                budgetStr = (budget / 10000000) + "";
            } catch (Exception e) {
                e.printStackTrace();
                AppUtils.showToast(mContext, "Error->" + e.getMessage());
            }
        }

        AuctionDetailScreenActivity.start(
                this,
                AuctionDetailScreenActivity.ASSISTANT,
                roundId,
                seriesID,
                contestGUID,
                mGetAuctionInfoOutput.getData().getAuctionStatus(),
                seriesName,
                seriesDeadLine,
                seriesStatus, budgetStr,
                mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());
    }

    /*
    My Squad screen redirection click
     */
    @OnClick(R.id.ctv_btn_squad)
    void onSquadBtnClick() {
        AppUtils.clickVibrate(this);
        AuctionDetailScreenActivity.start2(
                this,
                AuctionDetailScreenActivity.SQUAD,
                roundId,
                seriesID,
                contestGUID,
                mGetAuctionInfoOutput.getData().getAuctionStatus(),
                userGUID,
                "My",
                seriesName,
                seriesDeadLine,
                seriesStatus,
                false,
                mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());

    }

    /*
    Player Bid history screen redirection click
     */
    @OnClick(R.id.ctv_btn_bid_history)
    void onBidHistoryBtnClick() {
        if (mGetCurrentLiveAuctionPlayerOutput != null) {
            AppUtils.clickVibrate(this);
            AuctionDetailScreenActivity.start(
                    this,
                    AuctionDetailScreenActivity.BID_HISTORY,
                    roundId,
                    contestGUID,
                    mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerGUID(),
                    seriesName,
                    seriesDeadLine,
                    seriesStatus,
                    mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC(),
                    mGetAuctionInfoOutput.getData().getAuctionStatus());
        }
    }

    @OnClick(R.id.civ_player_img)
    void onPlayerClick() {
        AppUtils.clickVibrate(this);
        if (mGetCurrentLiveAuctionPlayerOutput != null) {
            AuctionPlayerStatsActivity.start(mContext,
                    mGetAuctionInfoOutput.getData().getSeriesGUID(),
                    mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerGUID(),
                    roundId,
                    seriesID,
                    false);
        }

    }

    //===================================Views objects =============================================
    //===================================Views objects =============================================
    //===================================Views objects =============================================

    // shows series name
    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    // shows series remaining time
    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;

    // shows bought player count
    @BindView(R.id.ctv_player_bought)
    CustomTextView mCtvPlayerBoughtCount;

    // shows series total player count
    @BindView(R.id.ctv_total_player_count)
    CustomTextView mCtvTotalPlayerCount;

    // shows user remaing budget
    @BindView(R.id.ctv_remaing_budget)
    CustomTextView mCtvRemaingBudget;


    // show auction info start timer & message (contains- ll_auction_main_timmer & ll_auction_message_root overlapping each other )
    @BindView(R.id.fl_auction_info)
    FrameLayout mFrameLayoutAuctionInfoRoot;


    // show player info
    @BindView(R.id.ll_player_info_root)
    LinearLayout mLinearLayoutPlayerInfoRoot;

    //==============================================================
    // contains in ll_player_info_root
    // either rl_auction_bar or ll_auction_onhold show
    // show hold & resume btn                                       //
    @BindView(R.id.rl_auction_bar)                                  //
            RelativeLayout mRelativeLayoutAuctionInputContainer;            //
    //
    // show hold time of other user                                 //
    @BindView(R.id.ll_auction_onhold)                               //
            LinearLayout mLinearLayoutOnHoldRoot;                           //
    //================================================================

    @BindView(R.id.ll_auction_main_timmer)
    LinearLayout mLinearLayoutStartTimmerRoot;

    @BindView(R.id.ll_auction_message_root)
    LinearLayout mLinearLayoutAuctionMainMessageRoot;

    @BindView(R.id.ctv_main_msg_title)
    CustomTextView mCtvAuctionMainMsgTitle;

    @BindView(R.id.ctv_main_msg_description)
    CustomTextView mCtvAuctionMainMsgDescription;


    // ============================current player info==============================================

    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    @BindView(R.id.ctv_player_name)
    CustomTextView mCtvPlayerName;

    @BindView(R.id.civ_player_img)
    CustomImageView mCustomImageViewPlayer;

    @BindView(R.id.ctv_batting_style)
    CustomTextView mCtvBattingStyle;

    @BindView(R.id.ctv_bowling_style)
    CustomTextView mCtvBowlingStyle;

    @BindView(R.id.ctv_run_count)
    CustomTextView mCtvRunCount;

    @BindView(R.id.ctv_sr)
    CustomTextView mCtvSR;

    @BindView(R.id.ctv_bat_avg)
    CustomTextView mCtvBattingAvg;

    @BindView(R.id.ctv_fifty_count)
    CustomTextView mCtvFiftyCount;

    @BindView(R.id.ctv_matches_count)
    CustomTextView mCtvMatchCount;

    @BindView(R.id.ctv_wicket_count)
    CustomTextView mCtvWicketCount;

    @BindView(R.id.ctv_bowl_avg)
    CustomTextView mCtvBowlAvg;

    @BindView(R.id.ctv_economy)
    CustomTextView mCtvEconomy;
    // current player info=================================current player info======================


    @BindView(R.id.ctv_bid_info)
    CustomTextView mCtvBidInfoMessage;

    @BindView(R.id.sv_bid)
    StepperView mStepperViewBid;


    @BindView(R.id.ctv_day)
    CustomTextView mCtvDay;

    @BindView(R.id.ctv_auc_hold_status)
    CustomTextView mCtvOnHoldStatus;


    @BindView(R.id.rv_players)
    RecyclerView mRecyclerViewPlayers;


    @BindView(R.id.ctv_hrs)
    CustomTextView mCtvHrs;

    @BindView(R.id.ctv_min)
    CustomTextView mCtvMin;


    @BindView(R.id.ctv_sec)
    CustomTextView mCtvSec;

    @BindView(R.id.iv_player_left)
    ImageView mImgViewPlayerLeft;

    @BindView(R.id.iv_player_right)
    ImageView mImgViewPlayerRight;


    @BindView(R.id.ctv_btn_raise)
    CustomTextView mCtvBtnRise;

    @BindView(R.id.ctv_player_sold_info)
    CustomTextView mCtvPlayerSoldInfo;

    @BindView(R.id.ctv_remaining_timebank)
    CustomTextView mCtvTimeBank;

    @BindView(R.id.ctv_time_left)
    CustomTextView mCtvBidTimeLeft;

    @BindView(R.id.ctv_btn_hold)
    CustomTextView mCtvBtnHold;

    @BindView(R.id.ll_rise_btn_root)
    View mViewRiseBtnRoot;


    @OnClick(R.id.ctv_btn_hold)
    void onHoldBtnClick() {
        AppUtils.clickVibrate(this);
        if (onHoldUserInfo != null) {
            if (onHoldUserInfo.getUserGUID().equals(userGUID)) {
                emitEventTimerHold(false);
            }
        } else {
            emitEventTimerHold(true);
        }

    }

    @OnClick(R.id.ctv_btn_raise)
    void onRaiseBtnClick() {
        AppUtils.clickVibrate(this);
        int value;
        String selectedValue = mStepperViewBid.getCurrent();
        if (selectedValue.contains("Lacs")) {
            selectedValue = selectedValue.substring(0, selectedValue.length() - 5);
            value = Integer.parseInt(selectedValue);
            value = value * 100000;
        } else {
            selectedValue = selectedValue.substring(0, selectedValue.length() - 4);
            value = Integer.parseInt(selectedValue);
            value = value * 10000000;
        }

        if (mAuctionBidSuccessBean != null) {
            if (!mAuctionBidSuccessBean.getResponseData().getData().getUserGUID().equals(userGUID)) {

                if (value > mAuctionBidSuccessBean.getResponseData().getData().getBidCredit()) {
                    if (value >= mAuctionBidSuccessBean.getResponseData().getData().getBidCredit() * 5) {
                        final int finalValue = value;
                        mAlertDialog = new AuctionAlertDialog(mContext, "Are you sure, you want to add " + mStepperViewBid.getCurrent() + " bid?", "Yes", "No",
                                new AuctionAlertDialog.OnOkayBtnClickListener() {
                                    @Override
                                    public void onClick() {
                                        mAlertDialog.hide();
                                        emitEventAuctionBid(finalValue, mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerGUID());
                                    }

                                    @Override
                                    public void onCancel() {
                                        mAlertDialog.hide();
                                    }
                                });
                        mAlertDialog.show();
                    } else {
                        emitEventAuctionBid(value, mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerGUID());
                    }
                } else {
                    Toast.makeText(mContext, "Sorry, You can't add bid less than current bid.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (mGetCurrentLiveAuctionPlayerOutput != null && mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser().getPlayerID() != null) {
                if (!mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser().getUserGUID().equals(userGUID)) {

                    if (value > Integer.parseInt(mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser().getBidCredit())) {
                        if (value >= Integer.parseInt(mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser().getBidCredit()) * 5) {
                            final int finalValue = value;
                            mAlertDialog = new AuctionAlertDialog(mContext, "Are you sure, you want to add "
                                    + mStepperViewBid.getCurrent() + " bid?", "Yes", "No", new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAlertDialog.hide();
                                    emitEventAuctionBid(finalValue, mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerGUID());
                                }

                                @Override
                                public void onCancel() {
                                    mAlertDialog.hide();
                                }
                            });
                            mAlertDialog.show();
                        } else {
                            emitEventAuctionBid(value, mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerGUID());
                        }
                    } else {
                        Toast.makeText(mContext, "Sorry, You can't add bid less than current bid.", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                emitEventAuctionBid(value, mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerGUID());
            }
        }

        // now emmit event


        if (myLiveInfo != null) {
            String auctionBudget = myLiveInfo.getAuctionBudget();
            if (!auctionBudget.trim().equals("")) {
                int currentBudget = Integer.parseInt(auctionBudget.trim());
                if (value > currentBudget) {
                    Toast.makeText(mContext, "Sorry, You can't add bid because your budget is less than current bid.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

    }


    @OnClick(R.id.iv_down_player_info)
    void onDownPlayerInfoBtnClick() {
        AppUtils.clickVibrate(this);
        mScrollView.scrollTo(0, mScrollView.getScrollY() + mScrollView.getMaxScrollAmount());
    }


    //==============================================================================================
    //==============================================================================================
    //==============================================================================================
    //==============================================================================================


    private final String SOCKET_LOGS = "SOCKET_LOGS";
    private final String NO_BID_MESSAGE = "Current Bid is 0";
    private final String loginSessionKey = AppSession.getInstance().getLoginSession().getData().getSessionKey();
    private final String fullName = AppSession.getInstance().getLoginSession().getData().getFullName();
    private final String userGUID = AppSession.getInstance().getLoginSession().getData().getUserGUID();
    private String roundId, seriesID, contestGUID, seriesName, seriesDeadLine;
    private int seriesStatus;
    private Context mContext;
    private AuctionAlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    private IUserInteractor mInteractor;
    private SocketUtility mSocketUtility;
    private Socket mSocket;
    private Handler mHandler = new Handler();
    private GetAuctionInfoOutput mGetAuctionInfoOutput = null; // from API-Call -> apiCallGetAuctionInfo()
    private GetAuctionPlayerOutput mGetAuctionPlayerOutput = null; // from API-Call -> apiCallGetAllPlayers()
    private GetCurrentLiveAuctionPlayerOutput mGetCurrentLiveAuctionPlayerOutput = null; // from API-Call -> apiCallGetCurrentAuctionPlayer()
    private GetAuctionJoinedUserOutput.DataBean.RecordsBean onHoldUserInfo = null; // from API-Call -> apiCallGetJoinedUser()
    private GetAuctionJoinedUserOutput.DataBean.RecordsBean myLiveInfo = null; // from API-Call -> apiCallGetJoinedUser()
    private AuctionAlertDialog mAlertDialogCompleteAuction;


    private AuctionBidSuccessBean mAuctionBidSuccessBean = null;


    @Override
    public int getLayout() {
        return R.layout.activity_auction_home;
    }

    @Override
    public void init() {
        mContext = this;
        roundId = getIntent().getExtras().getString("roundId");
        seriesID = getIntent().getExtras().getString("seriesID");
        contestGUID = getIntent().getExtras().getString("contestGUID");
        seriesName = getIntent().getExtras().getString("seriesName");
        seriesDeadLine = getIntent().getExtras().getString("seriesDeadLine");
        seriesStatus = getIntent().getExtras().getInt("seriesStatus");
        mProgressDialog = new ProgressDialog(mContext);
        mInteractor = new UserInteractor();
        mStepperViewBid.addList(AppUtils.getBidArray());


        /*if (AppSession.getInstance().getPlayMode() == 1){
            icon_gameType.setImageDrawable(getResources().getDrawable(R.drawable.ic_auction_text));
        }else {
            icon_gameType.setImageDrawable(getResources().getDrawable(R.drawable.ic_draft_text));
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGetAuctionInfoOutput = null;
        mGetAuctionPlayerOutput = null;
        mGetCurrentLiveAuctionPlayerOutput = null;
        onHoldUserInfo = null;
        myLiveInfo = null;
        mAuctionBidSuccessBean = null;
        mHandler.postDelayed(mRunnableForTimer, 1000);

        changeRiseBidBtnStatus(false);
        changeHoldBtnStatus(false);
        apiCallGetAuctionInfo();
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mRunnableForTimer);
        super.onPause();
        if (mSocket != null) {
            mSocketUtility.offDefaultEvent();
            mSocket.off("AuctionPlayerStatus", mEmitterListenerAuctionPlayerStatus);
            mSocket.off("AuctionPlayerChange", mEmitterListenerAuctionPlayerChange);
            mSocket.off("AuctionBidSuccess", mEmitterListenerAuctionBidSuccess);
            mSocket.off("auctionJoinedContestUser", mEmitterListenerAuctionJoinedContestUser);
            ((AppController) AppController.getContext()).closeSocket();
        }
    }


    //===============================================================================================//
    //===============================================================================================//
    //===================================Update view Methods=========================================//
    //===============================================================================================//
    //===============================================================================================//

    private void changeAuctionStatus(String status) {
        new AuctionInfoUtil(
                mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus,
                seriesName,
                mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC(),
                status).start();
        mCtvBidInfoMessage.setBackgroundColor(getResources().getColor(R.color.off));
        mCtvBidInfoMessage.setTextColor(Color.parseColor("#BDBDBF"));
        mStepperViewBid.setEnabled(false);
        changeRiseBidBtnStatus(false);
        mCtvPlayerSoldInfo.setBackgroundColor(Color.parseColor("#E7E7E9"));

        mImgViewPlayerLeft.setColorFilter(ContextCompat.getColor(mContext, R.color.arrow_dis), android.graphics.PorterDuff.Mode.SRC_IN);
        mImgViewPlayerRight.setColorFilter(ContextCompat.getColor(mContext, R.color.arrow_dis), android.graphics.PorterDuff.Mode.SRC_IN);

        switch (status) {
            case "Pending":
                mFrameLayoutAuctionInfoRoot.setVisibility(View.VISIBLE);
                mLinearLayoutPlayerInfoRoot.setVisibility(View.GONE);
                mLinearLayoutAuctionMainMessageRoot.setVisibility(View.GONE);
                mLinearLayoutStartTimmerRoot.setVisibility(View.VISIBLE);
                break;
            case "Running":
                mFrameLayoutAuctionInfoRoot.setVisibility(View.GONE);
                mLinearLayoutPlayerInfoRoot.setVisibility(View.VISIBLE);
                mCtvBidInfoMessage.setBackgroundColor(getResources().getColor(R.color.red));
                mCtvBidInfoMessage.setTextColor(Color.parseColor("#FFFFFF"));
                mStepperViewBid.setEnabled(true);
                mCtvPlayerSoldInfo.setBackgroundColor(Color.parseColor("#59595B"));
                mImgViewPlayerLeft.setColorFilter(ContextCompat.getColor(mContext, R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
                mImgViewPlayerRight.setColorFilter(ContextCompat.getColor(mContext, R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
                changeRiseBidBtnStatus(true);
                break;
            case "Cancelled":
                mFrameLayoutAuctionInfoRoot.setVisibility(View.VISIBLE);
                mLinearLayoutPlayerInfoRoot.setVisibility(View.GONE);
                mLinearLayoutStartTimmerRoot.setVisibility(View.GONE);
                mLinearLayoutAuctionMainMessageRoot.setVisibility(View.VISIBLE);
                mCtvAuctionMainMsgTitle.setText("Auction status - Cancelled");
                mCtvAuctionMainMsgDescription.setText("This auction is canceled, Please join other auction.");
                break;
            case "Completed":
                mFrameLayoutAuctionInfoRoot.setVisibility(View.VISIBLE);
                mLinearLayoutPlayerInfoRoot.setVisibility(View.GONE);
                mLinearLayoutStartTimmerRoot.setVisibility(View.GONE);
                mLinearLayoutAuctionMainMessageRoot.setVisibility(View.VISIBLE);
                mCtvAuctionMainMsgTitle.setText("Auction completed!");
                mCtvAuctionMainMsgDescription.setText("Create a team with your best players to win the contest.");
                if (mAlertDialogCompleteAuction == null) {
                    mAlertDialogCompleteAuction = new AuctionAlertDialog(this, "Auction is completed. Please submit your team.",
                            "Submit",
                            "Later", new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAlertDialogCompleteAuction.hide();
                            AuctionDetailScreenActivity.start2(mContext,
                                    AuctionDetailScreenActivity.SQUAD, roundId, seriesID, contestGUID,
                                    mGetAuctionInfoOutput.getData().getAuctionStatus(),
                                    userGUID,
                                    "My", seriesName, seriesDeadLine, seriesStatus, false,
                                    mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());
                        }

                        @Override
                        public void onCancel() {
                            mAlertDialogCompleteAuction.hide();
                        }
                    });
                    mAlertDialogCompleteAuction.show();
                }
                mCtvPlayerSoldInfo.setTextColor(Color.parseColor("#BDBDBF"));
                mCtvPlayerSoldInfo.setText(mCtvPlayerSoldInfo.getText().toString());
                break;
        }
    }

    private void showAuctionCurrentPlayer() {
        mCtvPlayerName.setText(mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerName());
        ViewUtils.setImageUrl(mCustomImageViewPlayer, mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerPic());
        mCtvBattingStyle.setText(mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStyle());
        mCtvBowlingStyle.setText(mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStyle());
        String runs = "", strikeRate = "", batAvg = "", fiftyCount = "", matchesCount = "", wickets = "", bowlAvg = "", economy = "";
        switch (mGetAuctionInfoOutput.getData().getDraftSeriesType()) {
            case "t20i":
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20i() != null) {
                    runs = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20i().getRuns() + "";
                    strikeRate = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20i().getStrikeRate() + "";
                    batAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20i().getAverage() + "";
                    fiftyCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20i().getHundreds() + "/" + mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20i().getFifties();
                    matchesCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20i().getMatches() + "";
                }
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getT20i() != null) {
                    wickets = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getT20i().getWickets() + "";
                    bowlAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getT20i().getAverage() + "";
                    economy = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getT20i().getEconomy() + "";
                }
                break;
            case "t20":
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20() != null) {
                    runs = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20().getRuns() + "";
                    strikeRate = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20().getStrikeRate() + "";
                    batAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20().getAverage() + "";
                    fiftyCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20().getHundreds()
                            + "/" + mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20().getFifties();
                    matchesCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getT20().getMatches() + "";

                }
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getT20() != null) {
                    wickets = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getT20().getWickets() + "";
                    bowlAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getT20().getAverage() + "";
                    economy = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getT20().getEconomy() + "";
                }
                break;
            case "odi":
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getODI() != null) {
                    runs = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getODI().getRuns() + "";
                    strikeRate = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getODI().getStrikeRate() + "";
                    batAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getODI().getAverage() + "";
                    fiftyCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getODI().getHundreds()
                            + "/" + mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getODI().getFifties();
                    matchesCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getODI().getMatches() + "";

                }
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getODI() != null) {
                    wickets = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getODI().getWickets() + "";
                    bowlAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getODI().getAverage() + "";
                    economy = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getODI().getEconomy() + "";
                }
                break;
            case "list a":
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getListA() != null) {
                    runs = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getListA().getRuns() + "";
                    strikeRate = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getListA().getStrikeRate() + "";
                    batAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getListA().getAverage() + "";
                    fiftyCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getListA().getHundreds()
                            + "/" + mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getListA().getFifties();
                    matchesCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getListA().getMatches() + "";

                }
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getListA() != null) {
                    wickets = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getListA().getWickets() + "";
                    bowlAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getListA().getAverage() + "";
                    economy = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getListA().getEconomy() + "";
                }
                break;
            case "first class":
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getFirstClass() != null) {
                    runs = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getFirstClass().getRuns() + "";
                    strikeRate = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getFirstClass().getStrikeRate() + "";
                    batAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getFirstClass().getAverage() + "";
                    fiftyCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getFirstClass().getHundreds()
                            + "/" + mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getFirstClass().getFifties();
                    matchesCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getFirstClass().getMatches() + "";

                }
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getFirstClass() != null) {
                    wickets = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getFirstClass().getWickets() + "";
                    bowlAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getFirstClass().getAverage() + "";
                    economy = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getFirstClass().getEconomy() + "";
                }
                break;
            case "test":
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getTest() != null) {
                    runs = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getTest().getRuns() + "";
                    strikeRate = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getTest().getStrikeRate() + "";
                    batAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getTest().getAverage() + "";
                    fiftyCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getTest().getHundreds()
                            + "/" + mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getTest().getFifties();
                    matchesCount = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBattingStats().getTest().getMatches() + "";

                }
                if (mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getTest() != null) {
                    wickets = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getTest().getWickets() + "";
                    bowlAvg = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getTest().getAverage() + "";
                    economy = mGetCurrentLiveAuctionPlayerOutput.getData().getPlayerBowlingStats().getTest().getEconomy() + "";
                }
                break;
        }
        mCtvRunCount.setText((runs == null || runs.trim().equals("") ? "0" : runs));
        mCtvSR.setText((strikeRate == null || strikeRate.trim().equals("") ? "0" : strikeRate));
        mCtvBattingAvg.setText((batAvg == null || batAvg.trim().equals("") ? "0" : batAvg));
        mCtvFiftyCount.setText((fiftyCount == null || fiftyCount.trim().equals("") ? "0" : fiftyCount));
        mCtvMatchCount.setText((matchesCount == null || matchesCount.trim().equals("") ? "0" : matchesCount));
        mCtvWicketCount.setText((wickets == null || wickets.trim().equals("") ? "0" : wickets));
        mCtvBowlAvg.setText((bowlAvg == null || bowlAvg.trim().equals("") ? "0" : bowlAvg));
        mCtvEconomy.setText((economy == null || economy.trim().equals("") ? "0" : economy));
        mCtvBidInfoMessage.setText(NO_BID_MESSAGE);
        mAuctionBidSuccessBean = null;
        playerAliveTime = getPlayerAliveTime();
        //resetBidtimmer();
        updateBidView();
        if (mAlertDialog != null) {
            mAlertDialog.hide();
        }
    }


    private void setPlayerBought() {
        if (myLiveInfo != null) {
            if (myLiveInfo.getPlayerSelectedCount() != null && !myLiveInfo.getPlayerSelectedCount().trim().isEmpty()) {
                try {
                    final int playerCount = Integer.parseInt(myLiveInfo.getPlayerSelectedCount().trim());
                    mCtvPlayerBoughtCount.post(new Runnable() {
                        @Override
                        public void run() {
                            mCtvPlayerBoughtCount.setText(playerCount + "");
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    AppUtils.showToast(mContext, "Error->" + e.getMessage());
                    mCtvPlayerBoughtCount.post(new Runnable() {
                        @Override
                        public void run() {
                            mCtvPlayerBoughtCount.setText("Err");
                        }
                    });

                }

            } else {
                mCtvPlayerBoughtCount.post(new Runnable() {
                    @Override
                    public void run() {
                        mCtvPlayerBoughtCount.setText("N/A");
                    }
                });

            }
        } else {
            mCtvPlayerBoughtCount.post(new Runnable() {
                @Override
                public void run() {
                    mCtvPlayerBoughtCount.setText("N/A");
                }
            });

        }
    }

    private void setBudgetLeft() {
        if (myLiveInfo != null) {
            if (myLiveInfo.getAuctionBudget() != null && !myLiveInfo.getAuctionBudget().trim().isEmpty()) {
                try {
                    final int budget = Integer.parseInt(myLiveInfo.getAuctionBudget().trim());
                    mCtvRemaingBudget.post(new Runnable() {
                        @Override
                        public void run() {
                            mCtvRemaingBudget.setText((budget / 10000000) + "");
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    mCtvRemaingBudget.post(new Runnable() {
                        @Override
                        public void run() {
                            mCtvRemaingBudget.setText("Err");
                        }
                    });

                }

            } else {
                mCtvRemaingBudget.post(new Runnable() {
                    @Override
                    public void run() {
                        mCtvRemaingBudget.setText("N/A");
                    }
                });

            }
        } else {
            mCtvRemaingBudget.post(new Runnable() {
                @Override
                public void run() {
                    mCtvRemaingBudget.setText("N/A");
                }
            });

        }
    }


    private void changeRiseBidBtnStatus(boolean status) {
        mCtvBtnRise.setEnabled(status);
        if (status) {
            mCtvBtnRise.setBackgroundResource(R.drawable.bg_auc_btn);
            mCtvBtnRise.setTextColor(Color.parseColor("#000000"));
        } else {
            mCtvBtnRise.setBackgroundResource(R.drawable.bg_auction_btn_yellow_disabled);
            mCtvBtnRise.setTextColor(Color.parseColor("#BDBDBF"));
        }
    }

    private void changeHoldBtnStatus(boolean status) {
        mCtvBtnHold.setEnabled(status);
        if (status) {
            mCtvBtnHold.setBackgroundResource(R.drawable.bg_auc_btn);
            mCtvBtnHold.setTextColor(Color.parseColor("#000000"));
        } else {
            mCtvBtnHold.setBackgroundResource(R.drawable.bg_auction_btn_yellow_disabled);
            mCtvBtnHold.setTextColor(Color.parseColor("#BDBDBF"));
        }
    }

    private void changeAuctionHoldUnholdViewStatus() {
        if (onHoldUserInfo == null) {
            String txt;
            try {
                int timeBank = Integer.parseInt(myLiveInfo.getAuctionTimeBank());
                if (timeBank > 0) {
                    int m = timeBank / 60;
                    int s = timeBank % 60;
                    txt = "Remaining Time Bank : " + String.format("%02d", m) + ":" + String.format("%02d", s) + " s";
                } else {
                    txt = "Remaining Time Bank : 00:00 s";
                }
            } catch (Exception e) {
                e.printStackTrace();
                AppUtils.showToast(mContext, "Error->" + e.getMessage());
                txt = "";
            }
            if (holdStartTime != 0) {
                playerAliveTime += (System.currentTimeMillis() - holdStartTime);
                holdStartTime = 0;
            }
            final String finalTxt = txt;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    int timeBank = Integer.parseInt(myLiveInfo.getAuctionTimeBank());
                    if (timeBank > 0) {
                        GetCurrentLiveAuctionPlayerOutput.DataBean.CurrentBidUserBean currentBidUser = mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser();
                        if (currentBidUser.getUserGUID() != null) {
                            changeHoldBtnStatus(!mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser().getUserGUID().equals(userGUID));
                        } else {
                            changeHoldBtnStatus(true);
                        }
                    } else {
                        changeHoldBtnStatus(false);
                    }
                    updateBidView();
                    mCtvTimeBank.setText(finalTxt);
                    mCtvBtnHold.setText("HOLD");
                    mRelativeLayoutAuctionInputContainer.setVisibility(View.VISIBLE);
                    mLinearLayoutOnHoldRoot.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            if (onHoldUserInfo.getUserGUID().equals(userGUID)) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        changeRiseBidBtnStatus(false);
                        mCtvBtnHold.setText("RESUME");
                        mRelativeLayoutAuctionInputContainer.setVisibility(View.VISIBLE);
                        mLinearLayoutOnHoldRoot.setVisibility(View.INVISIBLE);
                    }
                });

            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        changeRiseBidBtnStatus(false);
                        mRelativeLayoutAuctionInputContainer.setVisibility(View.INVISIBLE);
                        mLinearLayoutOnHoldRoot.setVisibility(View.VISIBLE);
                    }
                });
            }
            holdStartTime = System.currentTimeMillis();
        }
    }


    private void resetBidtimmer() {
        playerAliveTime = System.currentTimeMillis();
    }

    private void updateBidView() {
        if (mAuctionBidSuccessBean == null) {
            GetCurrentLiveAuctionPlayerOutput.DataBean.CurrentBidUserBean currentBidUser = mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser();

            if (currentBidUser.getUserGUID() != null) {
                int bidCredit = Integer.parseInt(currentBidUser.getBidCredit());
                String bid = "";
                if (bidCredit < 10000000) {
                    bid = bidCredit / 100000 + " Lacs";
                } else {
                    bid = bidCredit / 10000000 + " Crs";
                }
                mCtvBidInfoMessage.setText("Current Bid " + bid + " with " + currentBidUser.getFirstName());
                ArrayList<String> bidArray = AppUtils.getBidArray();
                for (int i = 0; i < bidArray.size(); i++) {
                    if (bidArray.get(i).equals(bid)) {
                        mStepperViewBid.setCurrent(i < bidArray.size() - 1 ? i + 1 : bidArray.size());
                    }
                }
                //2019-05-18 11:13:49

                changeRiseBidBtnStatus(!mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser().getUserGUID().equals(userGUID));

                int timeBank = Integer.parseInt(myLiveInfo.getAuctionTimeBank());
                if (timeBank > 0) {
                    changeHoldBtnStatus(!mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser().getUserGUID().equals(userGUID));

                } else {
                    changeHoldBtnStatus(false);

                }
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));


                    playerAliveTime = simpleDateFormat.parse(currentBidUser.getDateTime()).getTime();

                } catch (ParseException e) {
                    e.printStackTrace();
                    AppUtils.showToast(mContext, e.getMessage());
                }
            } else {
                mCtvBidInfoMessage.setText(NO_BID_MESSAGE);
                mStepperViewBid.setCurrent(0);
                changeRiseBidBtnStatus(true);
                if (myLiveInfo != null) {
                    int timeBank = Integer.parseInt(myLiveInfo.getAuctionTimeBank());
                    changeHoldBtnStatus(timeBank > 0);
                } else {
                    changeHoldBtnStatus(true);
                }

            }
        } else {
            int bidCredit = mAuctionBidSuccessBean.getResponseData().getData().getBidCredit();
            String bid = "";
            if (bidCredit < 10000000) {
                bid = bidCredit / 100000 + " Lacs";
            } else {
                bid = bidCredit / 10000000 + " Crs";
            }

            ArrayList<String> bid_arrays = AppUtils.getBidArray();
            for (int i = 0; i < bid_arrays.size(); i++) {
                if (bid_arrays.get(i).equals(bid)) {
                    mStepperViewBid.setCurrent(i < bid_arrays.size() - 1 ? i + 1 : bid_arrays.size());
                }
            }
            mCtvBidInfoMessage.setText("Current Bid " + bid + " with " + mAuctionBidSuccessBean.getResponseData().getData().getFullName());
            changeRiseBidBtnStatus(!mAuctionBidSuccessBean.getResponseData().getData().getUserGUID().equals(userGUID));
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));


                playerAliveTime = simpleDateFormat.parse(mAuctionBidSuccessBean.getResponseData().getData().getDateTime()).getTime();

            } catch (ParseException e) {
                e.printStackTrace();
                AppUtils.showToast(mContext, e.getMessage());
            }

            if (myLiveInfo != null) {
                int timeBank = Integer.parseInt(myLiveInfo.getAuctionTimeBank());
                if (timeBank > 0) {
                    changeHoldBtnStatus(!mAuctionBidSuccessBean.getResponseData().getData().getUserGUID().equals(userGUID));
                } else {
                    changeHoldBtnStatus(false);
                }
            } else {
                changeHoldBtnStatus(!mAuctionBidSuccessBean.getResponseData().getData().getUserGUID().equals(userGUID));
            }
        }
    }

    private long playerAliveTime = 0;
    private long holdStartTime = 0;


    private void setOrder() {
        List<GetAuctionPlayerOutput.DataBean.RecordsBean> records = new ArrayList<>();
        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
            if (record.getPlayerStatus().equals("Live") || record.getPlayerStatus().equals("Upcoming")) {
                records.add(record);
            }
        }
        AuctionHomeOrderListAdapter auctionHomeOrderListAdapter = new AuctionHomeOrderListAdapter(mContext, roundId, seriesID, mGetAuctionInfoOutput.getData().getSeriesGUID(), records);
        mRecyclerViewPlayers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewPlayers.setAdapter(auctionHomeOrderListAdapter);
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getPlayerStatus().equals("Live")) {
                mRecyclerViewPlayers.scrollToPosition(i);
            }
        }

    }

    //===================================API Calling ===============================================
    //===================================API Calling ===============================================
    //===================================API Calling ===============================================
    //===================================API Calling ===============================================


    // 1. we get auction info
    private void apiCallGetAuctionInfo() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            final GetAuctionInfoInput getAuctionInfoInput = new GetAuctionInfoInput();
            getAuctionInfoInput.setRoundID(roundId);
            getAuctionInfoInput.setContestGUID(contestGUID);
            getAuctionInfoInput.setSessionKey(loginSessionKey);
            getAuctionInfoInput.setDraftSeriesType("Yes");
            getAuctionInfoInput.setParams("LeagueJoinDateTime,Status,AuctionStatus,LeagueJoinDateTimeUTC");
            mInteractor.getAuctionInfo(getAuctionInfoInput, new IUserInteractor.OnGetAuctionInfoResponseListener() {
                @Override
                public void onSuccess(GetAuctionInfoOutput getAuctionInfoOutput) {
                    mGetAuctionInfoOutput = getAuctionInfoOutput;
                    apiCallGetAllPlayers();
                    //apiCallGetCurrentAuctionPlayer();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AuctionAlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAlertDialog.hide();
                                    apiCallGetAuctionInfo();
                                }

                                @Override
                                public void onCancel() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AuctionAlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAlertDialog.hide();
                            apiCallGetAuctionInfo();
                        }

                        @Override
                        public void onCancel() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }

    // 2. we get auction all players in order
    private void apiCallGetAllPlayers() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setRoundID(roundId);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionPlayerInput.setParams("BidSoldCredit,PlayerStatus,PlayerID,PlayerRole,PlayerPic,PlayerCountry,PlayerBornPlace,PlayerBattingStyle,PlayerBowlingStyle,MatchType,MatchNo,MatchDateTime,SeriesName,TeamGUID,PlayerBattingStats,PlayerBowlingStats,IsPlaying,PointsData,PlayerSalary,TeamNameShort,PlayerSalaryCredit,UserPlayerSoldName");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            mInteractor.getAuctionPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        mGetAuctionPlayerOutput = getAuctionPlayerOutput;
                        apiCallGetCurrentAuctionPlayer();
                        mCtvTotalPlayerCount.setText("/" + getAuctionPlayerOutput.getData().getTotalRecords());
                    } else {
                        mProgressDialog.dismiss();


                        mAlertDialog = new AuctionAlertDialog(mContext,
                                "No Player Available.",
                                AppUtils.getStrFromRes(R.string.try_again),
                                AppUtils.getStrFromRes(R.string.cancel),
                                new AuctionAlertDialog.OnOkayBtnClickListener() {
                                    @Override
                                    public void onClick() {
                                        mAlertDialog.hide();
                                        apiCallGetAllPlayers();
                                    }

                                    @Override
                                    public void onCancel() {
                                        mAlertDialog.hide();
                                        finish();
                                    }
                                });
                        mAlertDialog.show();
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();


                    mAlertDialog = new AuctionAlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAlertDialog.hide();
                                    apiCallGetAllPlayers();
                                }

                                @Override
                                public void onCancel() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AuctionAlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAlertDialog.hide();
                            apiCallGetAllPlayers();
                        }

                        @Override
                        public void onCancel() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }


    //3. We get Current auction player
    private void apiCallGetCurrentAuctionPlayer() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            GetAuctionInfoInput getAuctionInfoInput = new GetAuctionInfoInput();
            getAuctionInfoInput.setRoundID(roundId);
            getAuctionInfoInput.setContestGUID(contestGUID);
            getAuctionInfoInput.setSessionKey(loginSessionKey);
            getAuctionInfoInput.setParams("PlayerID,PlayerRole,PlayerPic,PlayerCountry,PlayerBornPlace,PlayerBattingStyle,PlayerBowlingStyle,MatchType,MatchNo,MatchDateTime,SeriesName,TeamGUID,PlayerBattingStats,PlayerBowlingStats,IsPlaying,PointsData,PlayerSalary,TeamNameShort,PlayerSalaryCredit");
            mInteractor.getCurrentLiveAuctionPlayer(getAuctionInfoInput, new IUserInteractor.OnGetCurrentLiveAuctionPlayerResponseListener() {
                @Override
                public void onSuccess(GetCurrentLiveAuctionPlayerOutput getCurrentLiveAuctionPlayerOutput) {
                    AuctionHomeActivity.this.mGetCurrentLiveAuctionPlayerOutput = getCurrentLiveAuctionPlayerOutput;

                    apiCallGetJoinedUser();

                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AuctionAlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAlertDialog.hide();
                                    apiCallGetCurrentAuctionPlayer();
                                }

                                @Override
                                public void onCancel() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AuctionAlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAlertDialog.hide();
                            apiCallGetCurrentAuctionPlayer();
                        }

                        @Override
                        public void onCancel() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }

    //4. We get all the joined users list
    private void apiCallGetJoinedUser() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            GetAuctionJoinedUserInput getAuctionJoinedUserInput = new GetAuctionJoinedUserInput();
            getAuctionJoinedUserInput.setContestGUID(contestGUID);
            getAuctionJoinedUserInput.setRoundID(roundId);
            getAuctionJoinedUserInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionJoinedUserInput.setParams("FirstName,Username,UserGUID,ProfilePic,AuctionTimeBank,AuctionBudget,AuctionUserStatus,IsHold,AuctionHoldDateTime");
            mInteractor.getAuctionJoinedUsers(getAuctionJoinedUserInput, new IUserInteractor.OnGetAuctionJoinedUserResponseListener() {
                @Override
                public void onSuccess(GetAuctionJoinedUserOutput getAuctionJoinedUserOutput) {
                    mProgressDialog.dismiss();
                    if (getAuctionJoinedUserOutput.getData().getTotalRecords() != 0) {
                        try {
                            onHoldUserInfo = null;
                            myLiveInfo = null;
                            for (GetAuctionJoinedUserOutput.DataBean.RecordsBean record : getAuctionJoinedUserOutput.getData().getRecords()) {
                                if (record.getIsHold().equals("Yes")) {
                                    onHoldUserInfo = record;
                                }
                                if (record.getUserGUID().equals(userGUID)) {
                                    myLiveInfo = record;
                                    setBudgetLeft();
                                }
                            }
                            changeAuctionStatus(mGetAuctionInfoOutput.getData().getAuctionStatus());
                            showAuctionCurrentPlayer();
                            changeAuctionHoldUnholdViewStatus();
                            setOrder();
                            mCtvPlayerSoldInfo.setText("");
                            mCtvPlayerSoldInfo.setTextColor(Color.parseColor("#FFFFFF"));
                            if (mGetAuctionInfoOutput.getData().getAuctionStatus().equals("Pending")) {
                                mCtvPlayerSoldInfo.setText("");
                            } else if (mGetAuctionInfoOutput.getData().getAuctionStatus().equals("Completed")) {
                                mCtvPlayerSoldInfo.setTextColor(Color.parseColor("#BDBDBF"));
                                GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = mGetAuctionPlayerOutput.getData().getRecords().get(mGetAuctionPlayerOutput.getData().getRecords().size() - 1);
                                if (recordsBean.getPlayerStatus().equals("Sold")) {
                                    mCtvPlayerSoldInfo.setText(recordsBean.getPlayerName() + " SOLD TO " + recordsBean.getUserPlayerSoldName());
                                    //mCtvPlayerSoldInfo.setText(Html.fromHtml(recordsBean.getPlayerName() + "<font color='#DA473D'> SOLD TO </font> " + recordsBean.getUserPlayerSoldName()));
                                } else {
                                    mCtvPlayerSoldInfo.setText(recordsBean.getPlayerName() + " STATUS " + recordsBean.getPlayerStatus());
                                    //mCtvPlayerSoldInfo.setText(Html.fromHtml(recordsBean.getPlayerName() + " STATUS <font color='#DA473D'>" + recordsBean.getPlayerStatus() + "</font> "));
                                }
                            } else if (mGetAuctionInfoOutput.getData().getAuctionStatus().equals("Running")) {
                                for (int i = 0; i < mGetAuctionPlayerOutput.getData().getRecords().size(); i++) {
                                    if (mGetAuctionPlayerOutput.getData().getRecords().get(i).getPlayerStatus().equals("Live")) {
                                        if (i != 0) {
                                            GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = mGetAuctionPlayerOutput.getData().getRecords().get(i - 1);
                                            if (recordsBean.getPlayerStatus().equals("Sold")) {
                                                mCtvPlayerSoldInfo.setText(Html.fromHtml(recordsBean.getPlayerName() + "<font color='#DA473D'> SOLD TO </font> " + recordsBean.getUserPlayerSoldName()));
                                            } else {
                                                mCtvPlayerSoldInfo.setText(Html.fromHtml(recordsBean.getPlayerName() + " STATUS <font color='#DA473D'>" + recordsBean.getPlayerStatus() + "</font> "));
                                            }
                                        }
                                    }
                                }
                            }
                            initSocket();
                        } catch (Exception e) {

                            mAlertDialog = new AuctionAlertDialog(mContext,
                                    e.getMessage(),
                                    AppUtils.getStrFromRes(R.string.try_again),
                                    AppUtils.getStrFromRes(R.string.cancel),
                                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                                        @Override
                                        public void onClick() {
                                            mAlertDialog.hide();
                                            apiCallGetJoinedUser();
                                        }

                                        @Override
                                        public void onCancel() {
                                            mAlertDialog.hide();
                                            finish();
                                        }
                                    });
                            mAlertDialog.show();
                        }
                    } else {
                        mAlertDialog = new AuctionAlertDialog(mContext,
                                "No Joined user found.",
                                AppUtils.getStrFromRes(R.string.try_again),
                                AppUtils.getStrFromRes(R.string.cancel),
                                new AuctionAlertDialog.OnOkayBtnClickListener() {
                                    @Override
                                    public void onClick() {
                                        mAlertDialog.hide();
                                        apiCallGetJoinedUser();
                                    }

                                    @Override
                                    public void onCancel() {
                                        mAlertDialog.hide();
                                        finish();
                                    }
                                });
                        mAlertDialog.show();
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AuctionAlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAlertDialog.hide();
                                    apiCallGetJoinedUser();
                                }

                                @Override
                                public void onCancel() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AuctionAlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAlertDialog.hide();
                            apiCallGetJoinedUser();
                        }

                        @Override
                        public void onCancel() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }


    //===================================Socket function============================================
    //===================================Socket function============================================
    //===================================Socket function============================================
    //===================================Socket function============================================

    private void initSocket() {
        mSocket = ((AppController) AppController.getContext()).getSocket();
        if (mSocket != null) {
            mSocketUtility = new SocketUtility(mSocket);
            mSocketUtility.onDefaultEvent();
            mSocket.on(Socket.EVENT_CONNECT, mEmitterListenerEventConnect);
            // We are getting Auction status detail :- by this event
            mSocket.on("AuctionPlayerStatus", mEmitterListenerAuctionPlayerStatus);
            // Whenever player arrive for auction :- this event fire
            mSocket.on("AuctionPlayerChange", mEmitterListenerAuctionPlayerChange);
            // whenever someone bid on player :- this event fire.
            mSocket.on("AuctionBidSuccess", mEmitterListenerAuctionBidSuccess);
            // Fire joined users info.
            mSocket.on("auctionJoinedContestUser", mEmitterListenerAuctionJoinedContestUser);

            mSocket.connect();
        } else {
            mAlertDialog = new AuctionAlertDialog(mContext,
                    "Error occurred(mSocket = null)",
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAlertDialog.hide();
                            initSocket();
                        }


                        @Override
                        public void onCancel() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }

    private void emitEventAuctionName() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserGUID", userGUID);
            jsonObject.put("ContestGUID", contestGUID);
            jsonObject.put("RoundID", roundId);
            mSocket.emit("AuctionName", jsonObject, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d(SOCKET_LOGS, "emitEventAuctionName: " + args);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            AppUtils.showToast(mContext, "Error->" + e.getMessage());
        }

    }

    private void emitEventAuctionBid(int bidCredit, String playerGUID) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserGUID", userGUID);
            jsonObject.put("ContestGUID", contestGUID);
            jsonObject.put("RoundID", roundId);
            jsonObject.put("SessionKey", loginSessionKey);
            jsonObject.put("BidCredit", bidCredit);
            jsonObject.put("PlayerGUID", playerGUID);
            jsonObject.put("FullName", fullName);
            mSocket.emit("AuctionBid", jsonObject, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d(SOCKET_LOGS, "emitEventAuctionBid: " + args);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            AppUtils.showToast(mContext, "Error->" + e.getMessage());
        }

    }

    private void emitEventTimerHold(boolean isHold) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserGUID", userGUID);
            jsonObject.put("ContestGUID", contestGUID);
            jsonObject.put("RoundID", roundId);
            jsonObject.put("SessionKey", loginSessionKey);
            if (myLiveInfo != null) {
                if (isHold) {
                    jsonObject.put("Time", myLiveInfo.getAuctionTimeBank());
                } else {
                    long diff = (System.currentTimeMillis() - holdStartTime) / 1000;
                    jsonObject.put("Time", (Integer.parseInt(myLiveInfo.getAuctionTimeBank()) - diff));
                }
            }
            if (isHold) {
                jsonObject.put("IsHold", "Yes");
            } else {
                jsonObject.put("IsHold", "No");
            }

            mSocket.emit("TimerHold", jsonObject, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d(SOCKET_LOGS, "emitEventTimerHold: " + args);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            AppUtils.showToast(mContext, "Error->" + e.getMessage());
        }

    }


    private Emitter.Listener mEmitterListenerEventConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "On->EVENT_CONNECT");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    emitEventAuctionName();
                }
            });
        }
    };


    private Emitter.Listener mEmitterListenerAuctionBidSuccess = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "On->AuctionBidSuccess");
            if (args.length != 0) {
                Log.d(SOCKET_LOGS, "AuctionBidSuccess->Data->" + args[0].toString());
                try {
                    JSONObject data = (JSONObject) args[0];
                    final AuctionBidSuccessBean auctionBidSuccessBean
                            = new Gson().fromJson(data.toString(), AuctionBidSuccessBean.class);
                    mAuctionBidSuccessBean = auctionBidSuccessBean;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            updateBidView();
                            //resetBidtimmer();
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            AppUtils.showToast(mContext, "Error->" + e.getMessage());
                        }
                    });

                }
            } else {
                Log.d(SOCKET_LOGS, "AuctionBidSuccess->Data->Not data");
            }
        }
    };


    private Emitter.Listener mEmitterListenerAuctionJoinedContestUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "On->AuctionJoinedContestUser");
            if (args.length != 0) {
                Log.d(SOCKET_LOGS, "AuctionJoinedContestUser->Data->" + args[0].toString());
                JSONObject data = (JSONObject) args[0];
                try {
                    /*if (data.has("IsHold") && data.getString("IsHold").equals("Yes")) {
                        onHoldUserInfo = new GetAuctionJoinedUserOutput.DataBean.RecordsBean();
                        onHoldUserInfo.setUserGUID(data.getString("UserGUID"));
                    } else {
                        onHoldUserInfo = null;
                    }*/

                    onHoldUserInfo = null;
                    JSONObject auctionJoinedContestUser = data.getJSONObject("auctionJoinedContestUser");
                    final GetAuctionJoinedUserOutput getAuctionJoinedUserOutput
                            = new Gson().fromJson(auctionJoinedContestUser.toString(), GetAuctionJoinedUserOutput.class);
                    if (getAuctionJoinedUserOutput.getResponseCode() == 200
                            && getAuctionJoinedUserOutput.getData().getTotalRecords() != 0) {
                        for (GetAuctionJoinedUserOutput.DataBean.RecordsBean record : getAuctionJoinedUserOutput.getData().getRecords()) {
                            if (record.getUserGUID().equals(userGUID)) {
                                myLiveInfo = record;
                                if (myLiveInfo.getUserTeamPlayers().size() != 0) {
                                    myLiveInfo.setPlayerSelectedCount(myLiveInfo.getUserTeamPlayers().size() + "");
                                }
                                setBudgetLeft();
                                setPlayerBought();
                            }
                            if (record.getIsHold().equals("Yes")) {
                                onHoldUserInfo = record;
                                long timeBank = 0, diff = 0;
                                try {
                                    timeBank = Long.parseLong(onHoldUserInfo.getAuctionTimeBank());

                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                                    df.setTimeZone(TimeZone.getTimeZone("UTC"));
                                    Date holdStartTime = df.parse(onHoldUserInfo.getAuctionHoldDateTime());
                                    diff = System.currentTimeMillis() - holdStartTime.getTime();
                                } catch (final Exception e) {
                                    e.printStackTrace();
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            AppUtils.showToast(mContext, "Error->" + e.getMessage());
                                        }
                                    });
                                }
                                onHoldUserInfo.setAuctionTimeBank((timeBank - (diff / 1000)) + "");
                            }

                        }
                    }
                    changeAuctionHoldUnholdViewStatus();
                } catch (final Exception e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            AppUtils.showToast(mContext, "Error->" + e.getMessage());
                        }
                    });
                }
            } else {
                Log.d(SOCKET_LOGS, "AuctionJoinedContestUser->Data->No data");
            }
        }
    };


    private Emitter.Listener mEmitterListenerAuctionPlayerStatus = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "On->AuctionPlayerStatus");
            if (args.length != 0) {
                Log.d(SOCKET_LOGS, "AuctionPlayerStatus->Data->" + args[0].toString());
                try {
                    JSONObject data = (JSONObject) args[0];

                    JSONObject auctionGetPlayer = data.getJSONObject("auctionGetPlayer");
                    GetAuctionPlayerOutput getAuctionPlayerOutput
                            = new Gson().fromJson(auctionGetPlayer.toString(), GetAuctionPlayerOutput.class);

                    JSONObject getBidPlayerData = data.getJSONObject("getBidPlayerData");
                    GetCurrentLiveAuctionPlayerOutput getCurrentLiveAuctionPlayerOutput
                            = new Gson().fromJson(getBidPlayerData.toString(), GetCurrentLiveAuctionPlayerOutput.class);

                    mGetAuctionPlayerOutput = getAuctionPlayerOutput;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setOrder();
                        }
                    });

                    int recentDownPlayerIndex = -1;

                    if (getAuctionPlayerOutput != null) {
                        if (getAuctionPlayerOutput.getResponseCode() == 200 && getAuctionPlayerOutput.getData() != null) {
                            if (getAuctionPlayerOutput.getData().getRecords() != null && getAuctionPlayerOutput.getData().getRecords().size() != 0) {
                                List<GetAuctionPlayerOutput.DataBean.RecordsBean> records = getAuctionPlayerOutput.getData().getRecords();
                                for (int i = 0; i < records.size(); i++) {
                                    if (records.get(i).getPlayerGUID().equals(getCurrentLiveAuctionPlayerOutput.getData().getPlayerGUID())) {
                                        if (i == 0) {
                                            if (records.get(1).getPlayerStatus().equals("Sold") && records.get(1).getPlayerStatus().equals("Unsold")) {
                                                // completed
                                                recentDownPlayerIndex = records.size() - 1;
                                            }
                                        } else {
                                            recentDownPlayerIndex = i - 1;
                                        }

                                    }
                                }
                            }
                        }
                        if (recentDownPlayerIndex >= 0 && recentDownPlayerIndex < getAuctionPlayerOutput.getData().getRecords().size()) {
                            final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = getAuctionPlayerOutput.getData().getRecords().get(recentDownPlayerIndex);
                            mCtvPlayerSoldInfo.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (recordsBean.getPlayerStatus().equals("Sold")) {
                                        mCtvPlayerSoldInfo.setText(Html.fromHtml(recordsBean.getPlayerName() + "<font color='#ed0a0a'> SOLD TO </font> " + recordsBean.getUserPlayerSoldName()));
                                    } else {
                                        mCtvPlayerSoldInfo.setText(Html.fromHtml(recordsBean.getPlayerName() + " STATUS <font color='#ed0a0a'>" + recordsBean.getPlayerStatus() + "</font> "));
                                    }
                                }
                            });
                        } else {
                            mCtvPlayerSoldInfo.post(new Runnable() {
                                @Override
                                public void run() {
                                    mCtvPlayerSoldInfo.setText("");
                                }
                            });
                        }

                    }


                    JSONObject auctionPlayerStausData = data.getJSONObject("auctionPlayerStausData");
                    final AuctionPlayerStausDataBean auctionPlayerStausDataBean
                            = new Gson().fromJson(auctionPlayerStausData.toString(), AuctionPlayerStausDataBean.class);

                    if (!auctionPlayerStausDataBean.getData().getAuctionStatus().equals(mGetAuctionInfoOutput.getData().getAuctionStatus())) {
                        GetAuctionInfoOutput.DataBean data1 = mGetAuctionInfoOutput.getData();
                        data1.setAuctionStatus(auctionPlayerStausDataBean.getData().getAuctionStatus());
                        mGetAuctionInfoOutput.setData(data1);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                changeAuctionStatus(auctionPlayerStausDataBean.getData().getAuctionStatus());
                            }
                        });
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            AppUtils.showToast(mContext, "Error->" + e.getMessage());
                        }
                    });
                }
            } else {
                Log.d(SOCKET_LOGS, "AuctionPlayerStatus->Data->Not data");
            }
        }
    };


    private Emitter.Listener mEmitterListenerAuctionPlayerChange = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "On->AuctionPlayerChange");
            if (args.length != 0) {
                Log.d(SOCKET_LOGS, "AuctionPlayerChange->Data->" + args[0].toString());
                try {
                    JSONObject data = (JSONObject) args[0];
                    JSONObject getBidPlayerData = data.getJSONObject("getBidPlayerData");
                    final GetCurrentLiveAuctionPlayerOutput getCurrentLiveAuctionPlayerOutput
                            = new Gson().fromJson(getBidPlayerData.toString(), GetCurrentLiveAuctionPlayerOutput.class);
                    if (getCurrentLiveAuctionPlayerOutput.getResponseCode() == 200) {
                        AuctionHomeActivity.this.mGetCurrentLiveAuctionPlayerOutput = getCurrentLiveAuctionPlayerOutput;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                showAuctionCurrentPlayer();
                            }
                        });
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            AppUtils.showToast(mContext, "Error->" + e.getMessage());
                        }
                    });
                }

            } else {
                Log.d(SOCKET_LOGS, "AuctionPlayerChange->Data->Not data");
            }
        }
    };


    //===================================Timmer function============================================
    //===================================Timmer function============================================
    //===================================Timmer function============================================
    //===================================Timmer function============================================


    private Runnable mRunnableForTimer = new Runnable() {
        @Override
        public void run() {
            if (mGetAuctionInfoOutput != null) {
                if (mGetAuctionInfoOutput.getData() != null && mGetAuctionInfoOutput.getData().getAuctionStatus() != null && mGetAuctionInfoOutput.getData().getAuctionStatus().equals("Pending")) {
                    //======================================================//
                    //================handling Auction timer================//
                    //======================================================//
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        long auctionTime = simpleDateFormat.parse(mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC()).getTime();
                        long currentTime = System.currentTimeMillis();
                        long diff = (auctionTime - currentTime) / 1000;
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
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCtvDay.setText(String.format("%02d", finalDays));
                                mCtvHrs.setText(String.format("%02d", finalHours));
                                mCtvMin.setText(String.format("%02d", finalMinute));
                                mCtvSec.setText(String.format("%02d", finalSeconds));
                            }
                        });
                    } catch (final Exception e) {
                        e.printStackTrace();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(mContext, "Error->" + e.getMessage());
                            }
                        });
                    }
                } else if (holdStartTime != 0 && onHoldUserInfo != null) {
                    if (userGUID.equals(onHoldUserInfo.getUserGUID())) {
                        String txt = "";
                        try {
                            long currentTime = System.currentTimeMillis();
                            int dif = (int) ((currentTime - holdStartTime) / 1000);
                            int timeBank = Integer.parseInt(onHoldUserInfo.getAuctionTimeBank());
                            if (timeBank - dif > 0) {
                                int m = (timeBank - dif) / 60;
                                int s = (timeBank - dif) % 60;
                                txt = "Remaining Time Bank : " + String.format("%02d", m) + ":" + String.format("%02d", s) + " s";
                            } else {
                                txt = "Remaining Time Bank : 00:00 s";
                            }
                        } catch (final Exception e) {
                            e.printStackTrace();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(mContext, "Error->" + e.getMessage());
                                }
                            });
                            txt = "error";
                        }
                        final String finalTxt = txt;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCtvTimeBank.setText(finalTxt);
                            }
                        });
                    } else {
                        String txt = "";
                        try {
                            long currentTime = System.currentTimeMillis();
                            int dif = (int) ((currentTime - holdStartTime) / 1000);
                            int timeBank = Integer.parseInt(onHoldUserInfo.getAuctionTimeBank());
                            if (timeBank - dif > 0) {
                                int m = (timeBank - dif) / 60;
                                int s = (timeBank - dif) % 60;
                                txt = "By " + onHoldUserInfo.getUsername() + " ( " + String.format("%02d", m) + ":" + String.format("%02d", s) + " s )";
                            } else {
                                txt = "By " + onHoldUserInfo.getUsername() + " ( 00:00 s )";
                            }
                        } catch (final Exception e) {
                            e.printStackTrace();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(mContext, "Error->" + e.getMessage());
                                }
                            });
                            txt = "error";
                        }
                        final String finalTxt = txt;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCtvOnHoldStatus.setText(finalTxt);
                            }
                        });
                    }
                } else {
                    //======================================================//
                    //================handling bid timer====================//
                    //======================================================//
                    String sec = "--";
                    try {
                        long currentTime = System.currentTimeMillis();
                        if (playerAliveTime != 0) {
                            int dif = (int) ((currentTime - playerAliveTime) / 1000);
                            if (dif > 30) {
                                sec = "00";
                            } else {
                                sec = String.format("%02d", 30 - dif);
                            }
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(mContext, "Error->" + e.getMessage());
                            }
                        });
                    }

                    final SpannableStringBuilder builder = new SpannableStringBuilder();
                    SpannableString redSpannable = new SpannableString(sec);
                    redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, sec.length(), 0);
                    builder.append(redSpannable);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCtvBidTimeLeft.setText(builder, TextView.BufferType.SPANNABLE);
                        }
                    });
                }
            }
            mHandler.postDelayed(mRunnableForTimer, 1000);
        }
    };


    private long getPlayerAliveTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timeAsString = null;
        if (mGetCurrentLiveAuctionPlayerOutput != null) {
            if (mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser().getDateTime() != null) {
                timeAsString = mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidUser().getDateTime().trim();
            } else if (mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidPlayerTime().getDateTime() != null) {
                timeAsString = mGetCurrentLiveAuctionPlayerOutput.getData().getCurrentBidPlayerTime().getDateTime().trim();
            }
        }
        if (timeAsString != null && !timeAsString.isEmpty()) {
            try {
                Date tem = df.parse(timeAsString);
                return tem.getTime();
            } catch (Exception e) {
                e.printStackTrace();
                AppUtils.showToast(mContext, "Error->" + e.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }
}
