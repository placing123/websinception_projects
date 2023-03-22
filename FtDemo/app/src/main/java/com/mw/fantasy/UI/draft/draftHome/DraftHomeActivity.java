package com.mw.fantasy.UI.draft.draftHome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.mw.fantasy.AppController;
import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.playerpoint.AuctionInfoUtil;
import com.mw.fantasy.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.mw.fantasy.UI.draft.draftHome.addPlayer.BottomSheetAddPlayerFragment;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanInput.CheckUserDraftInLiveInput;
import com.mw.fantasy.beanInput.GetAuctionInfoInput;
import com.mw.fantasy.beanInput.GetAuctionPlayerInput;
import com.mw.fantasy.beanInput.GetDraftTeamsInput;
import com.mw.fantasy.beanOutput.BidErrorResponse;
import com.mw.fantasy.beanOutput.CheckUserDraftInLiveOutput;
import com.mw.fantasy.beanOutput.DraftBidSuccessOutput;
import com.mw.fantasy.beanOutput.DraftJoinedUserResponse;
import com.mw.fantasy.beanOutput.DraftUserChangeResponse;
import com.mw.fantasy.beanOutput.GetAuctionInfoOutput;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.beanOutput.GetDraftTeamsOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AuctionAlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;
import com.mw.fantasy.utility.ViewUtils;
import com.mw.fantasy.utility.socketUtil.SocketUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;
import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class DraftHomeActivity extends BaseActivity {


    public static void start(Context context,
                             String matchGUID,
                             String seriesID,
                             String contestGUID,
                             String seriesName,
                             String seriesDeadLine,
                             int seriesStatus,
                             String auctionStatus,
                             String auctionStartTime) {
        Intent starter = new Intent(context, DraftHomeActivity.class);
        starter.putExtra("matchGUID", matchGUID);
        starter.putExtra("seriesID", seriesID);
        starter.putExtra("seriesID", seriesID);
        starter.putExtra("contestGUID", contestGUID);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);



        starter.putExtra("auctionStatus", auctionStatus);

        starter.putExtra("auctionStartTime", auctionStartTime);


        context.startActivity(starter);
    }


    private static final String SOCKET_LOGS = "SOCKET_LOGS";
    private static final String loginSessionKey = AppSession.getInstance().getLoginSession().getData().getSessionKey();
    private static final String userGUID = AppSession.getInstance().getLoginSession().getData().getUserGUID();
    private Context mContext;
    private String matchGUID, seriesID, contestGUID, seriesName, seriesDeadLine,auctionStartTime;
    private int seriesStatus;
    private ProgressDialog mProgressDialog;
    private IUserInteractor mInteractor;
    private Handler mHandler = new Handler();
    private int wk, bat, ball, ar; // set from apiCallGetMySquad()
    private GetAuctionInfoOutput mGetAuctionInfoOutput; // from apiCallGetDraftInfo()
    private String auctionStatus; // set from apiCallGetDraftInfo()
    private int mwk = 1, mbat = 3, mball = 4, mar = 2; // set from apiCallGetDraftInfo()
    private List<GetDraftTeamsOutput.DataBean.RecordsBean> mListArrayListTeams; // from apiCallGetDraftTeams()
    private GetAuctionPlayerOutput mGetAuctionPlayerOutput; //from apiCallGetAvailPlayer()
    private DraftUserChangeResponse mDraftUserChangeResponse; // updating from apiCallCheckUserDraftInlive()
    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> myDraftedPlayers = new ArrayList<>();
    private DraftJoinedUserResponse mDraftJoinedUserResponse; // from event DraftJoinedContestUser
    private AuctionAlertDialog mAuctionAlertDialog;
    private AuctionAlertDialog mAuctionAlertDialogForCompletion;
    private GetAuctionPlayerOutput.DataBean.RecordsBean currentPlayer;
    private SocketUtility mSocketUtility;
    private Socket mSocket;
    private Emitter.Listener mEmitterListenerEventConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "->EVENT_CONNECT");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    emitEventDraftName();
                }
            });
        }
    };

    private Emitter.Listener mEmitterListenerEventDraftPlayerStatus = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "->DraftPlayerStatus");
            if (args.length != 0) {
                Log.d(SOCKET_LOGS, "DraftPlayerStatus->Data->" + args[0].toString());
                JSONObject data = (JSONObject) args[0];
                try {
                    mGetAuctionPlayerOutput = new Gson().fromJson(data.getJSONObject("auctionGetPlayer").toString(), GetAuctionPlayerOutput.class);

                    mCustomTextViewDraftedPlayerCount.post(new Runnable() {
                        @Override
                        public void run() {
                            int availPlayer = 0;
                            for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
                                if (record.getPlayerStatus().equals("Upcoming")) {
                                    availPlayer++;
                                }
                            }
                            mCustomTextViewAvailPlayerCount.setText(availPlayer + "");
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(SOCKET_LOGS, "Exception_>" + e.getMessage());
                }

            } else {
                Log.d(SOCKET_LOGS, "DraftPlayerStatus->Data->Not data");
            }
        }
    };


    private Emitter.Listener mEmitterListenerEventDraftUserChange = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "->DraftUserChange");
            if (args.length != 0) {
                Log.d(SOCKET_LOGS, "DraftUserChange->Data->" + args[0].toString());
                JSONObject data = (JSONObject) args[0];
                mDraftUserChangeResponse = new Gson().fromJson(data.toString(), DraftUserChangeResponse.class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setCurrentRoundSetting();

                    }
                });
            } else {
                Log.d(SOCKET_LOGS, "DraftUserChange->Data->Not data");
            }
        }
    };


    private Emitter.Listener mEmitterListenerEventDraftJoinedContestUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "->draftJoinedContestUser");
            if (args.length != 0) {
                Log.d(SOCKET_LOGS, "draftJoinedContestUser->Data->" + args[0].toString());
                JSONObject data = (JSONObject) args[0];
                mDraftJoinedUserResponse = new Gson().fromJson(data.toString(), DraftJoinedUserResponse.class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showUser();
                    }
                });
            } else {
                Log.d(SOCKET_LOGS, "draftJoinedContestUser->Data->Not data");
            }
        }
    };

    private Emitter.Listener mEmitterListenerEventDraftBidSuccess = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "->DraftBidSuccess");
            if (args.length != 0) {
                Log.d(SOCKET_LOGS, "DraftBidSuccess->Data->" + args[0].toString());
                JSONObject data = (JSONObject) args[0];
                final DraftBidSuccessOutput draftBidSuccessOutput = new Gson().fromJson(data.toString(), DraftBidSuccessOutput.class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCtvDraftedMessage.setVisibility(View.VISIBLE);
                        mCtvDraftedMessage.setText(draftBidSuccessOutput.getResponseData().getData().getPlayer().getPlayerName() + " SELECTED BY " + draftBidSuccessOutput.getResponseData().getData().getUser().getFirstName());
                        if (draftBidSuccessOutput.getResponseData().getData().getUser().getUserGUID().equals(userGUID)) {
                            apiCallGetMySquadAsync();
                        }
                        if (draftBidSuccessOutput.getResponseData().getData().getDraftStatus().equals("Completed")) {
                            auctionStatus = "Completed";
                            GetAuctionInfoOutput.DataBean tem = mGetAuctionInfoOutput.getData();
                            tem.setAuctionStatus(auctionStatus);
                            mGetAuctionInfoOutput.setData(tem);
                            setDraftStatus();
                        }
                        if (currentPlayer != null) {
                            if (currentPlayer.getPlayerID().equals(draftBidSuccessOutput.getResponseData().getData().getPlayer().getPlayerID())) {
                                currentPlayer = null;
                                showAuctionCurrentPlayer();
                            }
                        }
                        if (mGetAuctionPlayerOutput != null) {
                            for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
                                if (record.getPlayerID().equals(draftBidSuccessOutput.getResponseData().getData().getPlayer().getPlayerID())) {
                                    record.setPlayerStatus("Sold");
                                }
                            }
                        }
                        if (draftBidSuccessOutput.getResponseData().getData().getUser().getUserGUID().equals(userGUID)) {
                            switch (draftBidSuccessOutput.getResponseData().getData().getPlayer().getPlayerRole()) {
                                case "WicketKeeper":
                                    wk++;
                                    break;
                                case "Bowler":
                                    ball++;
                                    break;
                                case "Batsman":
                                    bat++;
                                    break;
                                case "AllRounder":
                                    ar++;
                                    break;
                            }
                        }

                    }
                });
            } else {
                Log.d(SOCKET_LOGS, "DraftBidSuccess->Data->Not data");
            }
        }
    };

    private Emitter.Listener mEmitterListenerEventDraftBidError = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(SOCKET_LOGS, "->DraftBidError");
            if (args.length != 0) {
                Log.d(SOCKET_LOGS, "DraftBidError->Data->" + args[0].toString());
                JSONObject data = (JSONObject) args[0];
                final BidErrorResponse bidErrorResponse = new Gson().fromJson(data.toString(), BidErrorResponse.class);
                if (bidErrorResponse.getUserGUID().equals(AppSession.getInstance().getLoginSession().getData().getUserGUID())) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            AppUtils.showToast(mContext, bidErrorResponse.getMessage());
                        }
                    });
                }

            } else {
                Log.d(SOCKET_LOGS, "DraftBidError->Data->Not data");
            }
        }
    };

    @OnClick(R.id.civ_player_img)
    void onSelectdPlayerImgClick() {
        AppUtils.clickVibrate(this);
        if (currentPlayer!=null) {
            AuctionPlayerStatsActivity.start(mContext,
                    currentPlayer.getSeriesGUID(),
                    currentPlayer.getPlayerGUID(),
                    matchGUID,
                    seriesID,false);
        }
    }


    @OnClick(R.id.ctv_action_btn)
    void onActionBtnClick() {
        AppUtils.clickVibrate(this);
        if (auctionStatus.equals("Pending")) {
            if (mDraftJoinedUserResponse!=null) {
                DraftDetailScreenActivity.start(
                        this,
                        DraftDetailScreenActivity.ASSISTANT,
                        "",
                        seriesName,
                        seriesDeadLine, seriesStatus,
                        mGetAuctionInfoOutput.getData().getDraftPlayerSelectionCriteria(),
                        1, matchGUID,
                        contestGUID,
                        auctionStatus,
                        seriesID,
                        "",
                        "0/"+mDraftJoinedUserResponse.getDraftJoinedContestUser().getData().size(),
                        mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());
            }

        } else {
            DraftDetailScreenActivity.start(
                    this,
                    DraftDetailScreenActivity.SQUAD,
                    "",
                    seriesName,
                    seriesDeadLine, seriesStatus,
                    mGetAuctionInfoOutput.getData().getDraftPlayerSelectionCriteria(),
                    MySquadFragment.MY_SQUAD, matchGUID,
                    contestGUID,
                    auctionStatus,
                    seriesID,
                    "", "",
                    mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());
        }
    }

    @OnClick(R.id.iv_home)
    void onHomeBtnClick() {
        AppUtils.clickVibrate(this);
        mAuctionAlertDialog = new AuctionAlertDialog(this,
                AppUtils.getStrFromRes(R.string.auction_back_alert),
                AppUtils.getStrFromRes(R.string.yes),
                AppUtils.getStrFromRes(R.string.No),
                new AuctionAlertDialog.OnOkayBtnClickListener() {
                    @Override
                    public void onClick() {
                        mAuctionAlertDialog.hide();
                        onBackPressed();
                    }

                    @Override
                    public void onCancel() {
                        mAuctionAlertDialog.hide();
                    }
                }
        );
        mAuctionAlertDialog.show();
    }

    @OnClick(R.id.ctv_btn_assistant)
    void assistantBtnClick() {
        AppUtils.clickVibrate(this);
        DraftDetailScreenActivity.start(
                this,
                DraftDetailScreenActivity.ASSISTANT,
                "",
                seriesName,
                seriesDeadLine, seriesStatus,
                mGetAuctionInfoOutput.getData().getDraftPlayerSelectionCriteria(),
                1, matchGUID,
                contestGUID,
                auctionStatus,
                seriesID,
                "", mDraftJoinedUserResponse.getDraftJoinedContestUser().getDraftLiveRound()+"/"+mDraftJoinedUserResponse.getDraftJoinedContestUser().getData().size(),
                mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());
    }

    @OnClick(R.id.ctv_btn_my_squad)
    void mySquadBtnClick() {
        AppUtils.clickVibrate(this);
        DraftDetailScreenActivity.start(
                this,
                DraftDetailScreenActivity.SQUAD,
                "",
                seriesName,
                seriesDeadLine, seriesStatus,
                mGetAuctionInfoOutput.getData().getDraftPlayerSelectionCriteria(),
                MySquadFragment.MY_SQUAD, matchGUID,
                contestGUID,
                auctionStatus,
                seriesID,
                "", "",
                mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());
    }

    @OnClick(R.id.ctv_btn_other_squad)
    void squadBtnClick() {
        AppUtils.clickVibrate(this);
        DraftDetailScreenActivity.start(
                this,
                DraftDetailScreenActivity.SQUAD,
                "",
                seriesName,
                seriesDeadLine, seriesStatus,
                mGetAuctionInfoOutput.getData().getDraftPlayerSelectionCriteria(),
                MySquadFragment.OTHER_SQUAD, matchGUID,
                contestGUID,
                auctionStatus,
                seriesID,
                "", "",
                mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());
    }

    @OnClick(R.id.iv_down_player_info)
    void onDownPlayerInfoBtnClick() {
        AppUtils.clickVibrate(this);
        mScrollView.scrollTo(0, mScrollView.getScrollY() + mScrollView.getMaxScrollAmount());
    }

    @OnClick(R.id.ctv_draft_player)
    void draftPlayerBtnClick() {
        AppUtils.clickVibrate(this);
        emitEventDraftPlayer();
    }

    @OnClick(R.id.ctv_avial_players)
    void availPlayerBtnClick() {
        AppUtils.clickVibrate(this);
        final BottomSheetAddPlayerFragment bottomSheetAddPlayerFragment = new BottomSheetAddPlayerFragment();
        bottomSheetAddPlayerFragment.setmOnPlayerSeleceListner(new BottomSheetAddPlayerFragment.OnPlayerSelec() {
            @Override
            public void onSelect(GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean) {
                if (isConditionFullfill(recordsBean.getPlayerRole())) {
                    bottomSheetAddPlayerFragment.dismiss();
                    currentPlayer = recordsBean;
                    showAuctionCurrentPlayer();
                } else {
                    if (mAuctionAlertDialog != null) {
                        mAuctionAlertDialog.hide();
                    }
                    String txt = "Minimum Criteria for " + recordsBean.getPlayerRole() + " is fulfilled. Please select player for another position until you complete the minimum criteria of " + (mwk + mbat + mball + mar) + " Players. (You can select extra " + ((mDraftJoinedUserResponse.getDraftJoinedContestUser().getData().size()) - (mwk + mbat + mball + mar)) + " players after it)." +
                            "\n" +
                            "\n" +
                            "MINIMUM CRITERIA : WK - " + mwk + ", BAT - " + mbat + ", BOWL - " + mball + ", AR - " + mar;
                    mAuctionAlertDialog = new AuctionAlertDialog(mContext, txt, "Okay", "", new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAuctionAlertDialog.hide();
                        }

                        @Override
                        public void onCancel() {
                            mAuctionAlertDialog.hide();
                        }
                    });
                    mAuctionAlertDialog.show();
                }
            }
        });
        bottomSheetAddPlayerFragment.show(getSupportFragmentManager(), "Dialog");
    }


    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;

    @BindView(R.id.ll_draft_statics_root)
    LinearLayout mLinearLayoutDraftStaticsRoot;

    @BindView(R.id.v_dummy)
    View mViewDummy;

    @BindView(R.id.ctv_available_player_count)
    CustomTextView mCustomTextViewAvailPlayerCount;

    @BindView(R.id.ctv_drafted_player_count)
    CustomTextView mCustomTextViewDraftedPlayerCount;

    @BindView(R.id.ctv_round_no)
    CustomTextView mCtvRoundNo;

    @BindView(R.id.ctv_user_turn_message)
    CustomTextView mCtvUserTurnMessage;




    @BindView(R.id.vp_user)
    ViewPager mViewPagerUser;


    @BindView(R.id.ll_player_info_root)
    LinearLayout mLinearLayoutPlayerInfoRoot;

    @BindView(R.id.ctv_round_time)
    CustomTextView mCustomTextViewRoundTime;

    @BindView(R.id.civ_player_img)
    CustomImageView mCustomImageViewPlayer;

    @BindView(R.id.civ_pic_two)
    CustomImageView civ_pic_two;

    @BindView(R.id.civ_pic_one)
    CustomImageView civ_pic_one;

    @BindView(R.id.user_name_one)
    CustomTextView user_name_one;

    @BindView(R.id.user_name_two)
    CustomTextView user_name_two;


    @BindView(R.id.first_check)
    ImageView first_check;

    @BindView(R.id.second_check)
    ImageView second_check;

    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    @BindView(R.id.ctv_player_name)
    CustomTextView mCtvPlayerName;

    @BindView(R.id.ctv_batting_style)
    CustomTextView mCtvBattingStyle;

    @BindView(R.id.ctv_bowling_style)
    CustomTextView mCtvBowlingStyle;

    @BindView(R.id.ctv_run_count)
    CustomTextView mCtvRunCount;

    @BindView(R.id.ctv_fifty_count)
    CustomTextView mCtvFiftyCount;

    @BindView(R.id.ctv_bat_avg)
    CustomTextView mCtvBattingAvg;

    @BindView(R.id.ctv_sr)
    CustomTextView mCtvSR;

    @BindView(R.id.ctv_wicket_count)
    CustomTextView mCtvWicketCount;

    @BindView(R.id.ctv_bowl_avg)
    CustomTextView mCtvBowlAvg;

    @BindView(R.id.ctv_economy)
    CustomTextView mCtvEconomy;

    @BindView(R.id.ctv_matches_count)
    CustomTextView mCtvMatchCount;

    @BindView(R.id.fl_draft_info)
    FrameLayout mFrameLayoutDraftInfo;

    @BindView(R.id.ll_auction_main_timmer)
    LinearLayout mLinearLayoutMainTimer;

    @BindView(R.id.ctv_day)
    CustomTextView mCtvDay;

    @BindView(R.id.ctv_hrs)
    CustomTextView mCtvHrs;

    @BindView(R.id.ctv_min)
    CustomTextView mCtvMin;

    @BindView(R.id.ctv_sec)
    CustomTextView mCtvSec;

    @BindView(R.id.ll_auction_message_root)
    LinearLayout mLinearLayoutAuctionMainMessageRoot;

    @BindView(R.id.ctv_main_msg_title)
    CustomTextView mCtvAuctionMainMsgTitle;

    @BindView(R.id.ctv_main_msg_description)
    CustomTextView mCtvAuctionMainMsgDescription;

    @BindView(R.id.ctv_draft_message)
    CustomTextView mCtvDraftedMessage;

    @BindView(R.id.ctv_action_btn)
    CustomTextView mCtvActionBtn;


    @BindView(R.id.rv_wk)
    RecyclerView mRecyclerView_WK;

    @BindView(R.id.rv_ar)
    RecyclerView mRecyclerView_AR;

    @BindView(R.id.rv_bat)
    RecyclerView mRecyclerView_BAT;

    @BindView(R.id.rv_bowl)
    RecyclerView mRecyclerView_BOWL;


    @BindView(R.id.ctv_wk)
    CustomTextView mCtv_mwk;

    @BindView(R.id.ctv_ar)
    CustomTextView mCtv_mar;


    @BindView(R.id.ctv_bat)
    CustomTextView mCtv_mbat;

    @BindView(R.id.ctv_bowl)
    CustomTextView mCtv_mbowl;

    @BindView(R.id.ctv_draft_player)
    CustomTextView mCtvDraftPlayerBtn;

    @BindView(R.id.ctv_btn_assistant)
    CustomTextView mCtvAssistantBtn;

    @BindView(R.id.ctv_btn_other_squad)
    CustomTextView mCtvOtherSquadBtn;

    @BindView(R.id.ctv_btn_my_squad)
    CustomTextView mCtvMySquadBtn;

    @BindView(R.id.ctv_select_player_label)
    CustomTextView mCtvSelectPlayerLabel;

    @BindView(R.id.ll_player_data_root)
    LinearLayout mLinearLayoutPlayerDataRoot;

    @BindView(R.id.ll_draftting_timer_root)
    LinearLayout mLinearLayoutDraftingTimerRootDataRoot;


    @Override
    public int getLayout() {
        return R.layout.activity_draft_home;
    }

    @Override
    public void init() {
        mContext = this;
        matchGUID = getIntent().getExtras().getString("matchGUID");
        seriesID = getIntent().getExtras().getString("seriesID");
        contestGUID = getIntent().getExtras().getString("contestGUID");
        seriesName = getIntent().getExtras().getString("seriesName");
        seriesDeadLine = getIntent().getExtras().getString("seriesDeadLine");
        seriesStatus = getIntent().getExtras().getInt("seriesStatus");
        auctionStartTime = getIntent().getExtras().getString("auctionStartTime");
        auctionStatus = getIntent().getExtras().getString("auctionStatus");
        mProgressDialog = new ProgressDialog(mContext);
        mInteractor = new UserInteractor();


        new AuctionInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName,
                auctionStartTime,
                auctionStatus).start();


        mRecyclerView_WK.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView_AR.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView_BAT.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView_BOWL.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        apiCallGetDraftInfo();
        mHandler.postDelayed(mRunnableForTimer, 1000);
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mRunnableForTimer);
        super.onPause();
        if (mSocket != null) {
            mSocketUtility.offDefaultEvent();
            mSocket.off(Socket.EVENT_CONNECT, mEmitterListenerEventConnect);
            mSocket.off("DraftPlayerStatus", mEmitterListenerEventDraftPlayerStatus);
            mSocket.off("DraftUserChange", mEmitterListenerEventDraftUserChange);
            mSocket.off("draftJoinedContestUser", mEmitterListenerEventDraftJoinedContestUser);
            mSocket.off("DraftBidSuccess", mEmitterListenerEventDraftBidSuccess);
            mSocket.off("DraftBidError", mEmitterListenerEventDraftBidError);
            ((AppController) AppController.getContext()).closeSocket();
        }
    }

    //1
    private void apiCallGetDraftInfo() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            final GetAuctionInfoInput getAuctionInfoInput = new GetAuctionInfoInput();
            getAuctionInfoInput.setMatchGUID(matchGUID);
            getAuctionInfoInput.setContestGUID(contestGUID);
            getAuctionInfoInput.setSessionKey(loginSessionKey);
            getAuctionInfoInput.setDraftSeriesType("Yes");
            getAuctionInfoInput.setParams("LeagueJoinDateTime,Status,AuctionStatus,LeagueJoinDateTimeUTC,DraftTeamPlayerLimit,DraftPlayerSelectionCriteria,CustomizeWinning,MatchType,MatchID");
            mInteractor.getDraftInfo(getAuctionInfoInput, new IUserInteractor.OnGetAuctionInfoResponseListener() {
                @Override
                public void onSuccess(GetAuctionInfoOutput getAuctionInfoOutput) {
                    mProgressDialog.dismiss();
                    mGetAuctionInfoOutput = getAuctionInfoOutput;
                    auctionStatus = mGetAuctionInfoOutput.getData().getAuctionStatus();

                    GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria draftPlayerSelectionCriteria = mGetAuctionInfoOutput.getData().getDraftPlayerSelectionCriteria();
                    if (draftPlayerSelectionCriteria != null) {
                        try {
                            mwk = Integer.parseInt(draftPlayerSelectionCriteria.getWk());
                            mbat = Integer.parseInt(draftPlayerSelectionCriteria.getBat());
                            mball = Integer.parseInt(draftPlayerSelectionCriteria.getBowl());
                            mar = Integer.parseInt(draftPlayerSelectionCriteria.getAr());
                            apiCallGetDraftTeams();
                        } catch (Exception e) {
                            mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                                    e.getMessage(),
                                    AppUtils.getStrFromRes(R.string.try_again),
                                    AppUtils.getStrFromRes(R.string.cancel),
                                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                                        @Override
                                        public void onClick() {
                                            mAuctionAlertDialog.hide();
                                            apiCallGetDraftInfo();
                                        }


                                        @Override
                                        public void onCancel() {
                                            mAuctionAlertDialog.hide();
                                            finish();
                                        }
                                    });
                            mAuctionAlertDialog.show();
                        }
                    } else {
                        mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                                "apiCallGetDraftInfo()->return DraftPlayerSelectionCriteria null",
                                AppUtils.getStrFromRes(R.string.try_again),
                                AppUtils.getStrFromRes(R.string.cancel),
                                new AuctionAlertDialog.OnOkayBtnClickListener() {
                                    @Override
                                    public void onClick() {
                                        mAuctionAlertDialog.hide();
                                        apiCallGetDraftInfo();
                                    }


                                    @Override
                                    public void onCancel() {
                                        mAuctionAlertDialog.hide();
                                        finish();
                                    }
                                });
                        mAuctionAlertDialog.show();
                    }

                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAuctionAlertDialog.hide();
                                    apiCallGetDraftInfo();
                                }


                                @Override
                                public void onCancel() {
                                    mAuctionAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAuctionAlertDialog.show();
                }
            });
        } else {
            mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {

                        @Override
                        public void onClick() {
                            mAuctionAlertDialog.hide();
                            apiCallGetDraftInfo();
                        }

                        @Override
                        public void onCancel() {
                            mAuctionAlertDialog.hide();
                            finish();
                        }
                    });
            mAuctionAlertDialog.show();
        }
    }

    //2
    private void apiCallGetDraftTeams() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            GetDraftTeamsInput getDraftTeamsInput = new GetDraftTeamsInput();
            getDraftTeamsInput.setMatchGUID(matchGUID);
            getDraftTeamsInput.setSessionKey(loginSessionKey);
            mInteractor.getDraftTeams(getDraftTeamsInput, new IUserInteractor.OnGetDraftTeamsListener() {
                @Override
                public void onSuccess(GetDraftTeamsOutput getDraftTeamsOutput) {
                    mProgressDialog.dismiss();
                    if (getDraftTeamsOutput != null && getDraftTeamsOutput.getData() != null && getDraftTeamsOutput.getData().getRecords() != null) {
                        mListArrayListTeams = getDraftTeamsOutput.getData().getRecords();
                        GetDraftTeamsOutput.DataBean.RecordsBean tem = new GetDraftTeamsOutput.DataBean.RecordsBean();
                        tem.setTeamNameShort("Select");
                        mListArrayListTeams.add(0, tem);
                        apiCallGetAvailPlayer();
                    } else {
                        mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                                "apiCallGetDraftTeams() return 0 teams.",
                                AppUtils.getStrFromRes(R.string.try_again),
                                AppUtils.getStrFromRes(R.string.cancel),
                                new AuctionAlertDialog.OnOkayBtnClickListener() {
                                    @Override
                                    public void onClick() {
                                        mAuctionAlertDialog.hide();
                                        apiCallGetDraftTeams();
                                    }

                                    @Override
                                    public void onCancel() {
                                        mAuctionAlertDialog.hide();
                                        finish();

                                    }
                                });
                        mAuctionAlertDialog.show();
                    }

                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                            AppUtils.getStrFromRes(R.string.network_error),
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAuctionAlertDialog.hide();
                                    apiCallGetDraftTeams();
                                }

                                @Override
                                public void onCancel() {
                                    mAuctionAlertDialog.hide();
                                    finish();

                                }
                            });
                    mAuctionAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAuctionAlertDialog.hide();
                            apiCallGetDraftTeams();
                        }

                        @Override
                        public void onCancel() {
                            mAuctionAlertDialog.hide();
                            finish();

                        }
                    });
            mAuctionAlertDialog.show();
        }
    }

    //3
    private void apiCallGetAvailPlayer() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            final GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            getAuctionPlayerInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setMatchGUID(matchGUID);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionPlayerInput.setParams("PlayerStatus,PlayerID,PlayerRole,PlayerPic,PlayerCountry,PlayerBornPlace,PlayerBattingStyle,PlayerBowlingStyle,MatchType,MatchNo,MatchDateTime,SeriesName,TeamGUID,PlayerBattingStats,PlayerBowlingStats,IsPlaying,PointsData,PlayerSalary,TeamNameShort,PlayerSalaryCredit,TeamName,SeriesGUID,SeriesID");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            mInteractor.getDraftPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    mProgressDialog.dismiss();
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        mGetAuctionPlayerOutput = getAuctionPlayerOutput;
                        mCustomTextViewDraftedPlayerCount.post(new Runnable() {
                            @Override
                            public void run() {
                                int availPlayer = 0;
                                for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
                                    if (record.getPlayerStatus().equals("Upcoming")) {
                                        availPlayer++;
                                    }
                                }
                                mCustomTextViewAvailPlayerCount.setText(availPlayer + "");
                            }
                        });
                    } else {
                        mGetAuctionPlayerOutput = null;
                        mCustomTextViewDraftedPlayerCount.setText("0");
                    }
                    apiCallGetMySquad();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                            AppUtils.getStrFromRes(R.string.network_error),
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAuctionAlertDialog.hide();
                                    apiCallGetAvailPlayer();
                                }

                                @Override
                                public void onCancel() {
                                    mAuctionAlertDialog.hide();
                                    finish();

                                }
                            });
                    mAuctionAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAuctionAlertDialog.hide();
                            apiCallGetAvailPlayer();
                        }

                        @Override
                        public void onCancel() {
                            mAuctionAlertDialog.hide();
                            finish();

                        }
                    });
            mAuctionAlertDialog.show();
        }
    }

    //4
    private void apiCallGetMySquad() {

        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            final GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            getAuctionPlayerInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setMatchGUID(matchGUID);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionPlayerInput.setParams("PlayerPosition,AuctionTopPlayerSubmitted,PlayerID,PlayerRole,PlayerPic,BidCredit,UserTeamGUID,UserTeamName,IsAssistant,SeriesGUID,SeriesID");
            getAuctionPlayerInput.setIsAssistant("No");
            getAuctionPlayerInput.setIsPreTeam("No");
            getAuctionPlayerInput.setMySquadPlayer("Yes");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            getAuctionPlayerInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            mInteractor.getDraftPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    mProgressDialog.dismiss();
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        myDraftedPlayers = getAuctionPlayerOutput.getData().getRecords();
                        wk = ball = bat = ar = 0;
                        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : getAuctionPlayerOutput.getData().getRecords()) {
                            switch (record.getPlayerRole()) {
                                case "WicketKeeper":
                                    wk++;
                                    break;
                                case "Bowler":
                                    ball++;
                                    break;
                                case "Batsman":
                                    bat++;
                                    break;
                                case "AllRounder":
                                    ar++;
                                    break;
                            }
                        }
                    } else {
                        myDraftedPlayers.clear();
                        wk = ball = bat = ar = 0;
                    }
                    apiCallCheckUserDraftInlive();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAuctionAlertDialog.hide();
                                    apiCallGetMySquad();
                                }

                                @Override
                                public void onCancel() {
                                    mAuctionAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAuctionAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAuctionAlertDialog.hide();
                            apiCallGetMySquad();
                        }

                        @Override
                        public void onCancel() {
                            mAuctionAlertDialog.hide();
                            finish();
                        }
                    });
            mAuctionAlertDialog.show();
        }
    }

    //5
    private void apiCallCheckUserDraftInlive() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            final CheckUserDraftInLiveInput checkUserDraftInLiveInput = new CheckUserDraftInLiveInput();
            checkUserDraftInLiveInput.setContestGUID(contestGUID);
            checkUserDraftInLiveInput.setMatchGUID(matchGUID);
            mInteractor.checkUserDraftInlive(checkUserDraftInLiveInput,
                    new IUserInteractor.OnCheckUserDraftListener() {
                        @Override
                        public void onSuccess(CheckUserDraftInLiveOutput checkUserDraftInLiveOutput) {
                            mProgressDialog.dismiss();
                            if (auctionStatus.equals("Running")) {
                                mDraftUserChangeResponse = new DraftUserChangeResponse();
                                mDraftUserChangeResponse.setContestGUID(contestGUID);
                                mDraftUserChangeResponse.setUserGUID(checkUserDraftInLiveOutput.getData().getUserGUID());
                                mDraftUserChangeResponse.setDatetime(checkUserDraftInLiveOutput.getData().getDraftUserLiveTime());
                                DraftUserChangeResponse.GetBidPlayerDataBean getBidPlayerDataBean = new DraftUserChangeResponse.GetBidPlayerDataBean();
                                getBidPlayerDataBean.setResponseCode(200);
                                getBidPlayerDataBean.setMessage(checkUserDraftInLiveOutput.getMessage());
                                DraftUserChangeResponse.GetBidPlayerDataBean.DataBean dataBean = new DraftUserChangeResponse.GetBidPlayerDataBean.DataBean();
                                dataBean.setContestGUID(contestGUID);
                                dataBean.setContestID(checkUserDraftInLiveOutput.getData().getContestID());
                                dataBean.setDraftLiveRound(checkUserDraftInLiveOutput.getData().getDraftLiveRound());
                                dataBean.setDraftNextRound(checkUserDraftInLiveOutput.getData().getDraftLiveRound());
                                //dataBean.setSeriesGUID(seriesGUID);
                                dataBean.setSeriesID(checkUserDraftInLiveOutput.getData().getSeriesID());
                                dataBean.setUserGUID(checkUserDraftInLiveOutput.getData().getUserGUID());
                                dataBean.setUserID(checkUserDraftInLiveOutput.getData().getUserID());
                                getBidPlayerDataBean.setData(dataBean);
                                mDraftUserChangeResponse.setGetBidPlayerData(getBidPlayerDataBean);
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setCurrentRoundSetting();
                                    }
                                });
                            }
                            initSocket();
                        }

                        @Override
                        public void onError(String errorMsg) {
                            mProgressDialog.dismiss();
                            mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                                    errorMsg,
                                    AppUtils.getStrFromRes(R.string.try_again),
                                    AppUtils.getStrFromRes(R.string.cancel),
                                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                                        @Override
                                        public void onClick() {
                                            mAuctionAlertDialog.hide();
                                            apiCallCheckUserDraftInlive();
                                        }

                                        @Override
                                        public void onCancel() {
                                            mAuctionAlertDialog.hide();
                                            finish();
                                        }
                                    });
                            mAuctionAlertDialog.show();
                        }
                    });
        } else {
            mAuctionAlertDialog = new AuctionAlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAuctionAlertDialog.hide();
                            apiCallCheckUserDraftInlive();
                        }

                        @Override
                        public void onCancel() {
                            mAuctionAlertDialog.hide();
                            finish();
                        }
                    });
            mAuctionAlertDialog.show();
        }
    }

    private void apiCallGetMySquadAsync() {
        final GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
        getAuctionPlayerInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
        getAuctionPlayerInput.setContestGUID(contestGUID);
        getAuctionPlayerInput.setMatchGUID(matchGUID);
        getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        getAuctionPlayerInput.setParams("PlayerPosition,AuctionTopPlayerSubmitted,PlayerID,PlayerRole,PlayerPic,BidCredit,UserTeamGUID,UserTeamName,IsAssistant,SeriesGUID,SeriesID");
        getAuctionPlayerInput.setIsAssistant("No");
        getAuctionPlayerInput.setIsPreTeam("No");
        getAuctionPlayerInput.setMySquadPlayer("Yes");
        getAuctionPlayerInput.setPlayerBidStatus("Yes");
        getAuctionPlayerInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
        mInteractor.getDraftPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
            @Override
            public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                    myDraftedPlayers = getAuctionPlayerOutput.getData().getRecords();
                    wk = ball = bat = ar = 0;
                    for (GetAuctionPlayerOutput.DataBean.RecordsBean record : getAuctionPlayerOutput.getData().getRecords()) {
                        switch (record.getPlayerRole()) {
                            case "WicketKeeper":
                                wk++;
                                break;
                            case "Bowler":
                                ball++;
                                break;
                            case "Batsman":
                                bat++;
                                break;
                            case "AllRounder":
                                ar++;
                                break;
                        }
                    }
                } else {
                    myDraftedPlayers.clear();
                    wk = ball = bat = ar = 0;
                }

                showMySquad();
            }

            @Override
            public void onError(String errorMsg) {
            }
        });

    }

    private void initSocket() {
        startDraft();
        mSocket = ((AppController) AppController.getContext()).getSocket();
        if (mSocket != null) {
            mSocketUtility = new SocketUtility(mSocket);
            mSocketUtility.onDefaultEvent();
            mSocket.on(Socket.EVENT_CONNECT, mEmitterListenerEventConnect);
            mSocket.on("DraftPlayerStatus", mEmitterListenerEventDraftPlayerStatus);
            mSocket.on("DraftUserChange", mEmitterListenerEventDraftUserChange);
            mSocket.on("draftJoinedContestUser", mEmitterListenerEventDraftJoinedContestUser);
            mSocket.on("DraftBidSuccess", mEmitterListenerEventDraftBidSuccess);
            mSocket.on("DraftBidError", mEmitterListenerEventDraftBidError);
            mSocket.connect();
        }
    }

    private void startDraft() {
        setDraftStatus();
        showMySquad();
    }

    private void showMySquad() {
        try {
            mRecyclerView_WK.setAdapter(new DraftedPlayerAdapter(mContext, matchGUID, myDraftedPlayers, "WK", mwk));
            mRecyclerView_AR.setAdapter(new DraftedPlayerAdapter(mContext, matchGUID, myDraftedPlayers, "AR", mar));
            mRecyclerView_BAT.setAdapter(new DraftedPlayerAdapter(mContext, matchGUID, myDraftedPlayers, "BAT", mbat));
            mRecyclerView_BOWL.setAdapter(new DraftedPlayerAdapter(mContext, matchGUID, myDraftedPlayers, "BOWL", mball));
            mCtv_mwk.setText("WK\n" + mwk);
            mCtv_mar.setText("AR\n" + mar);
            mCtv_mbat.setText("BAT\n" + mbat);
            mCtv_mbowl.setText("BOWL\n" + mball);
            mCustomTextViewDraftedPlayerCount.setText((wk + ar + ball + bat) + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setDraftStatus() {
        new AuctionInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName,
                auctionStartTime,
                auctionStatus).start();
        switch (auctionStatus) {
            case "Pending":
                mLinearLayoutDraftStaticsRoot.setVisibility(View.GONE);
                mViewDummy.setVisibility(View.VISIBLE);
                mLinearLayoutPlayerInfoRoot.setVisibility(View.INVISIBLE);
                mFrameLayoutDraftInfo.setVisibility(View.VISIBLE);
                mLinearLayoutMainTimer.setVisibility(View.VISIBLE);
                mLinearLayoutAuctionMainMessageRoot.setVisibility(View.GONE);
                mCtvDraftedMessage.setVisibility(View.GONE);
                mCtvActionBtn.setVisibility(View.VISIBLE);
                mCtvActionBtn.setText("My Assistant");
                enabledDisableBtn(mCtvAssistantBtn, false);
                enabledDisableBtn(mCtvOtherSquadBtn, false);
                enabledDisableBtn(mCtvMySquadBtn, false);
                enabledDisableBtn(mCtvDraftPlayerBtn, false);
                break;
            case "Running":
                mLinearLayoutDraftStaticsRoot.setVisibility(View.VISIBLE);
                mViewDummy.setVisibility(View.GONE);
                mLinearLayoutPlayerInfoRoot.setVisibility(View.VISIBLE);
                mFrameLayoutDraftInfo.setVisibility(View.GONE);
                mLinearLayoutMainTimer.setVisibility(View.GONE);
                mLinearLayoutAuctionMainMessageRoot.setVisibility(View.GONE);
                mCtvActionBtn.setVisibility(View.GONE);
                enabledDisableBtn(mCtvAssistantBtn, true);
                enabledDisableBtn(mCtvOtherSquadBtn, true);
                enabledDisableBtn(mCtvMySquadBtn, true);

                enabledDisableBtn(mCtvDraftPlayerBtn, false);
                break;
            case "Completed":
                mLinearLayoutDraftStaticsRoot.setVisibility(View.GONE);
                mViewDummy.setVisibility(View.VISIBLE);
                mLinearLayoutPlayerInfoRoot.setVisibility(View.INVISIBLE);
                mFrameLayoutDraftInfo.setVisibility(View.VISIBLE);
                mLinearLayoutMainTimer.setVisibility(View.GONE);
                mLinearLayoutAuctionMainMessageRoot.setVisibility(View.VISIBLE);
                mCtvAuctionMainMsgTitle.setText("Draft completed!");
                mCtvAuctionMainMsgDescription.setText("Create a team with your best captain & vice-captain to win the contest.");
                mCtvDraftedMessage.setVisibility(View.GONE);
                mCtvActionBtn.setVisibility(View.VISIBLE);
                mCtvActionBtn.setText("Submit Squad");

                enabledDisableBtn(mCtvAssistantBtn, false);
                enabledDisableBtn(mCtvOtherSquadBtn, true);
                enabledDisableBtn(mCtvMySquadBtn, true);

                enabledDisableBtn(mCtvDraftPlayerBtn, false);


                if (mAuctionAlertDialogForCompletion == null) {
                    mAuctionAlertDialogForCompletion = new AuctionAlertDialog(mContext,
                            "Draft Completed. Please submit your team.",
                            "Okay", "",
                            new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAuctionAlertDialogForCompletion.hide();
                                    DraftDetailScreenActivity.start(
                                            mContext,
                                            DraftDetailScreenActivity.SQUAD,
                                            "",
                                            seriesName,
                                            seriesDeadLine, seriesStatus,
                                            mGetAuctionInfoOutput.getData().getDraftPlayerSelectionCriteria(),
                                            MySquadFragment.MY_SQUAD, matchGUID,
                                            contestGUID,
                                            auctionStatus,
                                            seriesID,
                                            "", "",
                                            mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC());
                                }

                                @Override
                                public void onCancel() {
                                    mAuctionAlertDialogForCompletion.hide();
                                }
                            });
                    mAuctionAlertDialogForCompletion.show();
                }

                break;
        }
    }

    private void enabledDisableBtn(CustomTextView mCustomTextView, boolean enable) {
        mCustomTextView.setEnabled(enable);
        if (enable) {
            mCustomTextView.setBackgroundResource(R.drawable.bg_auc_btn);
            mCustomTextView.setTextColor(Color.parseColor("#000000"));
        } else {
            mCustomTextView.setBackgroundResource(R.drawable.bg_auction_btn_yellow_disabled);
            mCustomTextView.setTextColor(Color.parseColor("#BDBDBF"));
        }
    }

    private void showAuctionCurrentPlayer() {
        if (currentPlayer != null) {
            mLinearLayoutPlayerDataRoot.setVisibility(View.VISIBLE);
            mCtvSelectPlayerLabel.setVisibility(View.INVISIBLE);
            //.setText(currentPlayer.getTeamName());
            //mCtvRole.setText(currentPlayer.getPlayerRole());
            mCtvPlayerName.setText(currentPlayer.getPlayerName());
            ViewUtils.setImageUrl(mCustomImageViewPlayer, currentPlayer.getPlayerPic());
            mCtvBattingStyle.setText(currentPlayer.getPlayerBattingStyle());
            mCtvBowlingStyle.setText(currentPlayer.getPlayerBowlingStyle());
            String runs = "", strikeRate = "", batAvg = "", fiftyCount = "", matchesCount = "", wickets = "", bowlAvg = "", economy = "";
            switch (mGetAuctionInfoOutput.getData().getMatchType()) {
                case "t20i":
                    if (currentPlayer.getPlayerBattingStats().getT20i() != null) {
                        runs = currentPlayer.getPlayerBattingStats().getT20i().getRuns() + "";
                        strikeRate = currentPlayer.getPlayerBattingStats().getT20i().getStrikeRate() + "";
                        batAvg = currentPlayer.getPlayerBattingStats().getT20i().getAverage() + "";
                        fiftyCount = currentPlayer.getPlayerBattingStats().getT20i().getHundreds() + "/" + currentPlayer.getPlayerBattingStats().getT20i().getFifties();
                        matchesCount = currentPlayer.getPlayerBattingStats().getT20i().getMatches() + "";
                        wickets = currentPlayer.getPlayerBowlingStats().getT20i().getWickets() + "";
                        bowlAvg = currentPlayer.getPlayerBowlingStats().getT20i().getAverage() + "";
                        economy = currentPlayer.getPlayerBowlingStats().getT20i().getEconomy() + "";
                    }
                    break;
                case "t20":
                    if (currentPlayer.getPlayerBattingStats().getT20() != null) {
                        runs = currentPlayer.getPlayerBattingStats().getT20().getRuns() + "";
                        strikeRate = currentPlayer.getPlayerBattingStats().getT20().getStrikeRate() + "";
                        batAvg = currentPlayer.getPlayerBattingStats().getT20().getAverage() + "";
                        fiftyCount = currentPlayer.getPlayerBattingStats().getT20().getHundreds() + "/" + currentPlayer.getPlayerBattingStats().getT20().getFifties();
                        matchesCount = currentPlayer.getPlayerBattingStats().getT20().getMatches() + "";
                        wickets = currentPlayer.getPlayerBowlingStats().getT20().getWickets() + "";
                        bowlAvg = currentPlayer.getPlayerBowlingStats().getT20().getAverage() + "";
                        economy = currentPlayer.getPlayerBowlingStats().getT20().getEconomy() + "";
                    }
                    break;
                case "odi":
                    if (currentPlayer.getPlayerBattingStats().getODI() != null) {
                        runs = currentPlayer.getPlayerBattingStats().getODI().getRuns() + "";
                        strikeRate = currentPlayer.getPlayerBattingStats().getODI().getStrikeRate() + "";
                        batAvg = currentPlayer.getPlayerBattingStats().getODI().getAverage() + "";
                        fiftyCount = currentPlayer.getPlayerBattingStats().getODI().getHundreds() + "/" + currentPlayer.getPlayerBattingStats().getODI().getFifties();
                        matchesCount = currentPlayer.getPlayerBattingStats().getODI().getMatches() + "";
                        wickets = currentPlayer.getPlayerBowlingStats().getODI().getWickets() + "";
                        bowlAvg = currentPlayer.getPlayerBowlingStats().getODI().getAverage() + "";
                        economy = currentPlayer.getPlayerBowlingStats().getODI().getEconomy() + "";
                    }


                    break;
                case "list a":
                    if (currentPlayer.getPlayerBattingStats().getListA() != null) {
                        runs = currentPlayer.getPlayerBattingStats().getListA().getRuns() + "";
                        strikeRate = currentPlayer.getPlayerBattingStats().getListA().getStrikeRate() + "";
                        batAvg = currentPlayer.getPlayerBattingStats().getListA().getAverage() + "";
                        fiftyCount = currentPlayer.getPlayerBattingStats().getListA().getHundreds() + "/" + currentPlayer.getPlayerBattingStats().getListA().getFifties();
                        matchesCount = currentPlayer.getPlayerBattingStats().getListA().getMatches() + "";
                        wickets = currentPlayer.getPlayerBowlingStats().getListA().getWickets() + "";
                        bowlAvg = currentPlayer.getPlayerBowlingStats().getListA().getAverage() + "";
                        economy = currentPlayer.getPlayerBowlingStats().getListA().getEconomy() + "";
                    }
                    break;
                case "first class":
                    if (currentPlayer.getPlayerBattingStats().getFirstClass() != null) {
                        runs = currentPlayer.getPlayerBattingStats().getFirstClass().getRuns() + "";
                        strikeRate = currentPlayer.getPlayerBattingStats().getFirstClass().getStrikeRate() + "";
                        batAvg = currentPlayer.getPlayerBattingStats().getFirstClass().getAverage() + "";
                        fiftyCount = currentPlayer.getPlayerBattingStats().getFirstClass().getHundreds() + "/" + currentPlayer.getPlayerBattingStats().getFirstClass().getFifties();
                        matchesCount = currentPlayer.getPlayerBattingStats().getFirstClass().getMatches() + "";
                        wickets = currentPlayer.getPlayerBowlingStats().getFirstClass().getWickets() + "";
                        bowlAvg = currentPlayer.getPlayerBowlingStats().getFirstClass().getAverage() + "";
                        economy = currentPlayer.getPlayerBowlingStats().getFirstClass().getEconomy() + "";
                    }

                    break;
            }

            mCtvRunCount.setText((runs == null || runs.trim().equals("") ? "0" : runs));
            mCtvSR.setText((strikeRate == null || strikeRate.trim().equals("") ? "0" : strikeRate));
            mCtvBattingAvg.setText((batAvg == null || batAvg.trim().equals("") ? "0" : batAvg));
            mCtvFiftyCount.setText((fiftyCount == null || fiftyCount.trim().equals("") ? "0/0" : fiftyCount));
            mCtvMatchCount.setText((matchesCount == null || matchesCount.trim().equals("") ? "0" : matchesCount));
            mCtvWicketCount.setText((wickets == null || wickets.trim().equals("") ? "0" : wickets));
            mCtvBowlAvg.setText((bowlAvg == null || bowlAvg.trim().equals("") ? "0" : bowlAvg));
            mCtvEconomy.setText((economy == null || economy.trim().equals("") ? "0" : economy));
        } else {

            mLinearLayoutPlayerDataRoot.setVisibility(View.INVISIBLE);
            mCtvSelectPlayerLabel.setVisibility(View.VISIBLE);

            //mCtvTeam.setText("");
            // mCtvRole.setText("");
            mCtvPlayerName.setText("");
            ViewUtils.setImageUrl(mCustomImageViewPlayer, "");
            mCtvBattingStyle.setText("");
            mCtvBowlingStyle.setText("");
            mCtvRunCount.setText("");
            mCtvSR.setText("");
            mCtvBattingAvg.setText("");
            mCtvFiftyCount.setText("");
            mCtvMatchCount.setText("");
            mCtvWicketCount.setText("");
            mCtvBowlAvg.setText("");
            mCtvEconomy.setText("");
        }

    }

    public String getRoundId() {
        return matchGUID;
    }

    public GetAuctionPlayerOutput getmGetAuctionPlayerOutput() {
        return mGetAuctionPlayerOutput;
    }

    public List<GetDraftTeamsOutput.DataBean.RecordsBean> getmListArrayListTeams() {
        return mListArrayListTeams;
    }

    private void showUser() {
        if (mDraftJoinedUserResponse != null) {

//            if (mDraftJoinedUserResponse.getDraftJoinedContestUser().getData().get())
//
//            user_name_one.setText(usersBean.getFirstName());
//            ViewUtils.setImageUrl(myViewHolder.mCustomImageViewPic, usersBean.getProfilePic());
//            if (isCurrentRound) {
//                if (usersBean.getDraftUserLive().equals("Yes")) {
//                    myViewHolder.mLinearLayoutRoot.setBackgroundResource(R.drawable.draft_user_active);
//                } else {
//                    myViewHolder.mLinearLayoutRoot.setBackgroundResource(R.drawable.draft_user_inactive);
//                }
//            }else {
//                myViewHolder.mLinearLayoutRoot.setBackgroundResource(R.color.divider_color);
//            }
//            if (usersBean.getAuctionUserStatus().equals("Online")) {
//                myViewHolder.mImageViewIndicator.setImageResource(R.drawable.circle_green);
//            } else {
//                myViewHolder.mImageViewIndicator.setImageResource(R.drawable.circle_red);
//            }
            mViewPagerUser.removeAllViews();
            mViewPagerUser.invalidate();
            mViewPagerUser.setAdapter(null);
            HomeUserPagerAdapter homeUserPagerAdapter = new HomeUserPagerAdapter(getSupportFragmentManager(), mDraftJoinedUserResponse);
            mViewPagerUser.setAdapter(homeUserPagerAdapter);
            int draftLiveRound = mDraftJoinedUserResponse.getDraftJoinedContestUser().getDraftLiveRound();
            homeUserPagerAdapter.notifyDataSetChanged();
            mViewPagerUser.setCurrentItem(draftLiveRound - 1);
        }

    }

    private void emitEventDraftName() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserGUID", userGUID);
            jsonObject.put("ContestGUID", contestGUID);
            jsonObject.put("MatchGUID", matchGUID);
            jsonObject.put("SeriesID", seriesID);
            mSocket.emit("DraftName", jsonObject, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d(SOCKET_LOGS, "emitEventAuctionName: " + args);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void emitEventDraftPlayer() {
        if (currentPlayer != null) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserGUID", userGUID);
                jsonObject.put("ContestGUID", contestGUID);
                jsonObject.put("MatchGUID", matchGUID);
                jsonObject.put("SeriesID", seriesID);
                jsonObject.put("PlayerGUID", currentPlayer.getPlayerGUID());
                jsonObject.put("PlayerStatus", "Sold");
                mSocket.emit("DraftBid", jsonObject, new Ack() {
                    @Override
                    public void call(Object... args) {
                        Log.d(SOCKET_LOGS, "emitEventAuctionName: " + args);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    private void setCurrentRoundSetting() {
        if (mDraftUserChangeResponse != null) {
            DraftUserChangeResponse.GetBidPlayerDataBean.DataBean data = mDraftUserChangeResponse.getGetBidPlayerData().getData();
            mCtvRoundNo.setText(data.getDraftNextRound());
            if (data.getUserGUID().equals(userGUID)) {
                mLinearLayoutDraftingTimerRootDataRoot.setVisibility(View.VISIBLE);
                mCtvUserTurnMessage.setVisibility(View.GONE);
                mCtvUserTurnMessage.setText("ITS YOUR TURN");
            } else {
                mLinearLayoutDraftingTimerRootDataRoot.setVisibility(View.GONE);
                mCtvUserTurnMessage.setVisibility(View.VISIBLE);
                mCtvUserTurnMessage.setText("WAIT FOR YOUR TURN");
            }
            if (auctionStatus.equals("Pending")) {
                auctionStatus = "Running";
                GetAuctionInfoOutput.DataBean tem = mGetAuctionInfoOutput.getData();
                tem.setAuctionStatus(auctionStatus);
                mGetAuctionInfoOutput.setData(tem);
                setDraftStatus();
            }
            enabledDisableBtn(mCtvDraftPlayerBtn, data.getUserGUID().equals(userGUID));
        }
    }


    private boolean isConditionFullfill(String role) {
        boolean isWkFullFill = wk >= mwk;
        boolean isBatFullFill = bat >= mbat;
        boolean isBallFullFill = ball >= mball;
        boolean isArFullFill = ar >= mar;
        if (isWkFullFill && isBatFullFill && isBallFullFill && isArFullFill)
            return true;
        else if (!(isWkFullFill || isBatFullFill || isBallFullFill || isArFullFill))
            return true;
        else {
            switch (role) {
                case "WicketKeeper":
                    return !isWkFullFill;
                case "Bowler":
                    return !isBallFullFill;
                case "Batsman":
                    return !isBatFullFill;
                case "AllRounder":
                    return !isArFullFill;
                default:
                    return false;
            }
        }

    }


    private Runnable mRunnableForTimer = new Runnable() {
        @Override
        public void run() {
            if (mGetAuctionInfoOutput != null && mGetAuctionInfoOutput.getData().getAuctionStatus().equals("Pending")) {
                //======================================================//
                //================handling Auction timer================//
                //======================================================//
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    long auctionTime = simpleDateFormat.parse(mGetAuctionInfoOutput.getData().getLeagueJoinDateTimeUTC()).getTime();
                    long currentTime = AppUtils.getSystemTime();
                    //long currentTime = System.currentTimeMillis();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (mGetAuctionInfoOutput != null && mGetAuctionInfoOutput.getData().getAuctionStatus().equals("Running")) {
                if (mDraftUserChangeResponse != null) {
                    try {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        long auctionTime = simpleDateFormat.parse(mDraftUserChangeResponse.getDatetime()).getTime();
                        //long currentTime = System.currentTimeMillis();
                        long currentTime = AppUtils.getSystemTime();

                        long diff = (currentTime - auctionTime) / 1000;
                        long days = 0, hours = 0, minute = 0, seconds = 0;
                        if (diff <= (2 * 60)) {
                            diff = 120 - diff;
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
                                long s=finalSeconds+(finalMinute*60);
                                mCustomTextViewRoundTime.setText(s+"s");
                              /*  mCustomTextViewRoundTime.setText
                                        (String.format("%02d", finalMinute)
                                                + ":" + (String.format("%02d", finalSeconds) + "sec"));*/
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else if (mGetAuctionInfoOutput != null && mGetAuctionInfoOutput.getData().getAuctionStatus().equals("Completed")) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCustomTextViewRoundTime.setText("00:00 sec");
                    }
                });
            }
            mHandler.postDelayed(mRunnableForTimer, 1000);
        }
    };
}
