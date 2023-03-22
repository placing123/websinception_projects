package com.websinception.megastar.UI.auction.auctionLoby;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.SeriesInfoUtil;
import com.websinception.megastar.UI.auction.auctionHome.AuctionDetailScreenActivity;
import com.websinception.megastar.UI.auction.auctionHome.AuctionHomeActivity;
import com.websinception.megastar.UI.auction.playerpoint.AuctionPlayerPointsActivity;
import com.websinception.megastar.UI.draft.draftHome.DraftDetailScreenActivity;
import com.websinception.megastar.UI.draft.draftHome.DraftHomeActivity;
import com.websinception.megastar.UI.draft.draftHome.MySquadFragment;
import com.websinception.megastar.UI.winnings.WinnersCallback;
import com.websinception.megastar.UI.winnings.WinnersRankBean;
import com.websinception.megastar.UI.winnings.WinningsFragment;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanInput.DownloadTeamInput;
import com.websinception.megastar.beanInput.GetAuctionInfoInput;
import com.websinception.megastar.beanInput.GetAuctionJoinedUserInput;
import com.websinception.megastar.beanOutput.GetAuctionInfoOutput;
import com.websinception.megastar.beanOutput.GetAuctionJoinedUserOutput;
import com.websinception.megastar.beanOutput.ResponseDownloadTeam;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.AlertDialog;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;

public class AuctionLobbyActivity extends BaseActivity {


    private Context mContext;
    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    private String roundId, contestGUID, seriesID, matchGUID;
    private String seriesName, seriesDeadLine, myBudget = "--";
    private int seriesStatus, flag;
    private boolean isSeriesStarted;
    private int creditLeft = 0;
    private boolean isAuctionTeamSubmited = false;
    private Handler mHandler;

    private IUserInteractor mInteractor;
    private GetAuctionInfoOutput.DataBean mAuctionInfo;

    @BindView(R.id.ctv_game_type)
    CustomTextView ctvImageType;


    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;


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

    @BindView(R.id.seekBar)
    ProgressBar mProgressBar;

    @BindView(R.id.ctv_btn_invite)
    CustomTextView mCtvInviteBtn;


    @BindView(R.id.ctv_team_count)
    CustomTextView mCtvTeamCount;

    @BindView(R.id.score_lyt)
    CardView score_lyt;

    @BindView(R.id.ctv_auction_home_btn)
    CustomTextView mCtvContestHomeBtn;

    @BindView(R.id.ctv_assistant_btn)
    CustomTextView mCtvContestAssistantBtn;


    @BindView(R.id.tv_btn_detail)
    CustomTextView tv_btn_detail;

    @BindView(R.id.ctv_home_btn_desc)
    CustomTextView mCtvContestHomeBtnDesc;

    @BindView(R.id.scoreData)
    @Nullable
    CustomTextView scoreData;


    @BindView(R.id.ll_auction_home_root)
    LinearLayout mLinearLayoutAuctionHomeRoot;

    @BindView(R.id.ll_root_enter_auction)
    LinearLayout mLinearLayoutEnterAuctionRoot;

    @BindView(R.id.ll_remaining_time_root)
    LinearLayout mLinearLayoutRemainingTimeRoot;

    @BindView(R.id.ll_auction_team_pdf_root)
    LinearLayout mLinearLayoutAuctionPDFRoot;

    @BindView(R.id.ll_player_stats_root)
    LinearLayout mLinearLayoutPlayerStatsRoot;

    @BindView(R.id.ll_winning_root)
    LinearLayout mLinearLayoutWinningRoot;

    @BindView(R.id.ll_contest_size_root)
    LinearLayout mLinearLayoutContestSizeRoot;

    @BindView(R.id.ctv_winning_info)
    CustomTextView mCustomTextViewWinningInfo;


    @BindView(R.id.ctv_remaining_time)
    CustomTextView mCustomTextViewRemainingTime;

    @BindView(R.id.ctv_auc_start_in)
    CustomTextView ctv_auc_start_in;


    @BindView(R.id.rv_users)
    RecyclerView mRecyclerView;


    @BindView(R.id.score1)
    @Nullable
    CustomTextView score1;

    @BindView(R.id.score2)
    @Nullable
    CustomTextView score2;


    @BindView(R.id.team1)
    @Nullable
    CustomTextView teamName1;

    @BindView(R.id.team2)
    @Nullable
    CustomTextView teamName2;

    @BindView(R.id.overs1)
    @Nullable
    CustomTextView overs1;

    @BindView(R.id.overs2)
    @Nullable
    CustomTextView overs2;


    @OnClick(R.id.iv_change_mode)
    public void onBack() {
        finish();
    }


    @OnClick(R.id.ctv_player_stats)
    void playerStatsBtnClick() {
        AuctionPlayerPointsActivity.start(mContext,
                flag,
                mAuctionInfo.getSeriesGUID(),
                contestGUID,
                flag == 1 ? roundId : matchGUID,
                seriesName,
                seriesDeadLine,
                seriesStatus,
                mAuctionInfo.getSeriesID());

    }

    @OnClick(R.id.ctv_view_team_btn)
    void viewTeamBtnClick() {
        apiCallDownloadteams();
    }


    @OnClick(R.id.ctv_winners_count)
    void winnerCountClick() {
        AppUtils.clickVibrate(this);
        List<GetAuctionInfoOutput.DataBean.CustomizeWinningBean> customizeWin = mAuctionInfo.getCustomizeWinning();
        List<WinnersRankBean> rankList = new ArrayList<>();
        for (int i = 0; i < customizeWin.size(); i++) {

            WinnersRankBean mWinnersRankBean = new WinnersRankBean();

            mWinnersRankBean.setFrom(customizeWin.get(i).getFrom());
            mWinnersRankBean.setTo(Integer.parseInt(customizeWin.get(i).getTo()));
            mWinnersRankBean.setPercent(customizeWin.get(i).getPercent() + "");
            mWinnersRankBean.setWinningAmount(customizeWin.get(i).getWinningAmount());

            rankList.add(i, mWinnersRankBean);
        }

        showWinningBreckUp(rankList, mAuctionInfo.getWinningAmount());

    }

    @OnClick(R.id.ctv_btn_invite)
    void inviteBtnBtn() {
        AppUtils.clickVibrate(this);
        /*if (true) {
            AuctionHomeActivity.start(mContext, roundId, seriesID, contestGUID, seriesName, seriesDeadLine, seriesStatus);
            return;
        }*/
        if (mAuctionInfo.getAuctionStatus().equals("Pending")) {
            AppUtils.shareTextUrl(mContext,
                    AppUtils.getStrFromRes(R.string.inviteFriendsMore),
                    "Join V/s Paid " + (flag == 1 ? "auction" : "draft") + " league on fantasy and win ₹" + mAuctionInfo.getWinningAmount() + ". Entry fee ₹" + mAuctionInfo.getEntryFee() + ". Use league code " + mAuctionInfo.getUserInvitationCode() + ".",
                    "Invite");
        } else {
            if (flag == 1) {
                AuctionDetailScreenActivity.start2(mContext,
                        AuctionDetailScreenActivity.SQUAD,
                        roundId,
                        seriesID,
                        contestGUID,
                        mAuctionInfo.getAuctionStatus(),
                        AppSession.getInstance().getLoginSession().getData().getUserGUID(),
                        AppSession.getInstance().getLoginSession().getData().getUsername(),
                        seriesName,
                        seriesDeadLine,
                        seriesStatus, isSeriesStarted,
                        mAuctionInfo.getLeagueJoinDateTimeUTC());
            } else if (flag == 2) {
                if (mAuctionInfo.getDraftPlayerSelectionCriteria() != null) {
                    DraftDetailScreenActivity.start(
                            mContext,
                            DraftDetailScreenActivity.SQUAD,
                            AppSession.getInstance().getLoginSession().getData().getUsername(),
                            seriesName,
                            seriesDeadLine, seriesStatus,
                            mAuctionInfo.getDraftPlayerSelectionCriteria(),
                            MySquadFragment.MY_SQUAD, matchGUID,
                            contestGUID,
                            mAuctionInfo.getAuctionStatus(),
                            seriesID,
                            AppSession.getInstance().getLoginSession().getData().getUserGUID(), "",
                            mAuctionInfo.getLeagueJoinDateTimeUTC());
                } else {
                    AppUtils.showToast(mContext, "draftPlayerSelectionCriteria == null");
                }
            }
        }

    }

    @OnClick(R.id.ctv_auction_home_btn)
    void auctionHomeBtn() {
        AppUtils.clickVibrate(this);
        if (flag == 1) {
            AuctionHomeActivity.start(mContext, roundId, seriesID, contestGUID, seriesName, seriesDeadLine, seriesStatus);
        } else {
            DraftHomeActivity.start(mContext,
                    matchGUID,
                    seriesID,
                    contestGUID,
                    seriesName,
                    seriesDeadLine,
                    seriesStatus,
                    mAuctionInfo.getAuctionStatus(),
                    mAuctionInfo.getLeagueJoinDateTimeUTC());
        }

    }

    @OnClick(R.id.ctv_assistant_btn)
    void auctionAssistantBtn() {
        AppUtils.clickVibrate(this);
        if (flag == 1) {
            AuctionDetailScreenActivity.start(this,
                    AuctionDetailScreenActivity.ASSISTANT,
                    roundId,
                    seriesID,
                    contestGUID,
                    mAuctionInfo.getAuctionStatus(),
                    seriesName,
                    seriesDeadLine,
                    seriesStatus,
                    myBudget,
                    mAuctionInfo.getLeagueJoinDateTimeUTC());
        } else {

            DraftDetailScreenActivity.start(
                    this,
                    DraftDetailScreenActivity.ASSISTANT,
                    "",
                    seriesName,
                    seriesDeadLine, seriesStatus,
                    mAuctionInfo.getDraftPlayerSelectionCriteria(),
                    1, matchGUID,
                    contestGUID,
                    mAuctionInfo.getAuctionStatus(),
                    seriesID,
                    "",
                    (mAuctionInfo.getAuctionStatus().equals("Pending") ? "0" : mAuctionInfo.getDraftLiveRound()) + "/" + mAuctionInfo.getDraftTeamPlayerLimit(),
                    mAuctionInfo.getLeagueJoinDateTimeUTC());
        }
    }


    public static void start(Context context,
                             int flag,
                             String roundId,
                             String seriesID,
                             String contestGUID,
                             String seriesName,
                             String seriesDeadLine,
                             int seriesStatus) {
        Intent starter = new Intent(context, AuctionLobbyActivity.class);
        starter.putExtra("flag", flag);
        starter.putExtra("roundId", roundId);
        starter.putExtra("seriesID", seriesID);
        starter.putExtra("contestGUID", contestGUID);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);
        context.startActivity(starter);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_auction_lobby;
    }

    @Override
    public void init() {
        mHandler = new android.os.Handler();
        mContext = this;
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(mContext);
        flag = getIntent().getExtras().getInt("flag");
        if (flag == 1) {
            roundId = getIntent().getExtras().getString("roundId");
        } else {
            matchGUID = getIntent().getExtras().getString("roundId");
        }

        seriesID = getIntent().getExtras().getString("seriesID");
        contestGUID = getIntent().getExtras().getString("contestGUID");
        seriesName = getIntent().getExtras().getString("seriesName");
        seriesDeadLine = getIntent().getExtras().getString("seriesDeadLine");
        seriesStatus = getIntent().getExtras().getInt("seriesStatus");
        if (flag == 1) {
            ctv_auc_start_in.setText("Auction start in");
            ctvImageType.setText("Auction");
            mCtvContestHomeBtn.setText("Go to Auction");
            mCtvContestAssistantBtn.setText("Auction Assistant");
            mCtvContestHomeBtnDesc.setText("Go to live auction page.");
            tv_btn_detail.setText(getResources().getString(R.string.manage_your_team_amp_auction));
        } else {
            ctv_auc_start_in.setText("Draft start in");
            ctvImageType.setText("Gully Fantasy");
            mCtvContestAssistantBtn.setText("Draft Assistant");
            tv_btn_detail.setText(getResources().getString(R.string.mange_team_draft));

            mCtvContestHomeBtn.setText("Go to Draft");
            mCtvContestHomeBtnDesc.setText("Go to live draft page.");
        }


        new SeriesInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName, seriesDeadLine, seriesStatus).start();

    }


    @Override
    protected void onResume() {
        super.onResume();
        getContestInfo();
    }

    private void getContestInfo() {
//        score_lyt.setVisibility(View.GONE);
        if (flag == 1) {
            apiCallGetAuctionInfo();
            getAuctionMatchInfo();
        } else {
            apiCallGetDraftInfo();
        }
    }


    private void getContestJoinedUsers() {
        if (flag == 1) {
            apiCallGetAuctionJoinedUser();
        } else {
            apiCallGetDraftJoinedUser();
        }
    }


    private void setAuctionInfo() {
        if (mAuctionInfo != null) {
            int spotLeft = Integer.parseInt(mAuctionInfo.getContestSize()) - Integer.parseInt(mAuctionInfo.getTotalJoined());
            mCtvPrizePool.setText("₹" + mAuctionInfo.getWinningAmount());
            mCtvWinnerCount.setText(mAuctionInfo.getNoOfWinners());
            mCtvEntryFee.setText("₹" + mAuctionInfo.getEntryFee());
            if (spotLeft == 0) {
                mCtvSpotLeft.setText("League full");
            } else {
                mCtvSpotLeft.setText("Only " + spotLeft + " spots left");
            }
            mCtvContestSize.setText(mAuctionInfo.getContestSize() + " Teams");
            mProgressBar.setMax(Integer.parseInt(mAuctionInfo.getContestSize()));
            mProgressBar.setProgress(Integer.parseInt(mAuctionInfo.getTotalJoined()));
            mProgressBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            if (mAuctionInfo.getNoOfWinners().equals("1") || mAuctionInfo.getNoOfWinners().equals("0")) {
                mCtvWinnerCount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                mCtvWinnerCount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
            }

            if (mAuctionInfo.getAuctionStatus().equals("Pending")) {
                if (spotLeft == 0) {
                    mCtvInviteBtn.setVisibility(View.GONE);
                } else {
                    mCtvInviteBtn.setVisibility(View.VISIBLE);
                }

            } else {
                mCtvInviteBtn.setVisibility(View.GONE);
            }

            if (isSeriesStarted()) {
                mLinearLayoutAuctionHomeRoot.setVisibility(View.GONE);
                if (flag == 1) {
                    mLinearLayoutAuctionPDFRoot.setVisibility(View.VISIBLE);
                }
                mLinearLayoutPlayerStatsRoot.setVisibility(View.VISIBLE);
                mLinearLayoutContestSizeRoot.setVisibility(View.GONE);
                //write case for winning
            } else {
                switch (mAuctionInfo.getAuctionStatus()) {
                    case "Pending":
                        mLinearLayoutAuctionHomeRoot.setVisibility(View.VISIBLE);
                        mLinearLayoutEnterAuctionRoot.setVisibility(View.GONE);
                        mLinearLayoutRemainingTimeRoot.setVisibility(View.VISIBLE);
                        mHandler.postDelayed(mRunnableForTimer, 1000);
                        break;
                    case "Running":
                        mLinearLayoutAuctionHomeRoot.setVisibility(View.VISIBLE);
                        mLinearLayoutEnterAuctionRoot.setVisibility(View.VISIBLE);
                        mLinearLayoutRemainingTimeRoot.setVisibility(View.GONE);
//                        AuctionHomeActivity.start(mContext, roundId, seriesID, contestGUID, seriesName, seriesDeadLine, seriesStatus);
                        break;
                    case "Completed":
                        mLinearLayoutAuctionHomeRoot.setVisibility(View.GONE);
                        mCtvInviteBtn.setVisibility(View.VISIBLE);
                        if (!isAuctionTeamSubmited) {
                            mCtvInviteBtn.setText("Make Your Team");
                        } else {
                            mCtvInviteBtn.setText("Edit Your Team");
                        }
                        break;
                }
                /*if (mAuctionInfo.getAuctionStatus().equals("Completed")) {
                    mLinearLayoutAuctionHomeRoot.setVisibility(View.GONE);
                    mCtvInviteBtn.setVisibility(View.VISIBLE);
                    if (!isAuctionTeamSubmited) {
                        mCtvInviteBtn.setText("Make Your Team");
                    } else {
                        mCtvInviteBtn.setText("Edit Your Team");
                    }
                } else {
                    mLinearLayoutAuctionHomeRoot.setVisibility(View.VISIBLE);
                }*/

                mLinearLayoutContestSizeRoot.setVisibility(View.VISIBLE);

                mLinearLayoutAuctionPDFRoot.setVisibility(View.GONE);
                mLinearLayoutPlayerStatsRoot.setVisibility(View.GONE);
                mLinearLayoutWinningRoot.setVisibility(View.GONE);


                AppUtils.auctionBtnEnabledDisable(mCtvContestAssistantBtn, !mAuctionInfo.getAuctionStatus().equals("Completed"));
            }

        } else {
            mAlertDialog = new AlertDialog(mContext, "setAuctionInfo->mAuctionInfo=null", "Okay", null, new AlertDialog.OnBtnClickListener() {
                @Override
                public void onYesClick() {
                    mAlertDialog.hide();
                    finish();
                }

                @Override
                public void onNoClick() {
                    mAlertDialog.hide();
                    finish();
                }
            });
            mAlertDialog.show();
        }
    }


    void joinedContest(GetAuctionInfoOutput mMatchDetails) {


        if (mMatchDetails.getData().getMatchScoreDetails()!=null&&mMatchDetails.getData().getMatchScoreDetails().getInnings() != null) {
            if (AppSession.getInstance().getGameType() == 1) {

                String team1 = "", team2 = "", team1Score = "", team2Score = "";

                for (int i = 0; i < mMatchDetails.getData().getMatchScoreDetails().getInnings().size(); i++) {

                    GetAuctionInfoOutput.DataBean.MatchScoreDetailsBean.InningsBean inningsBean = new GetAuctionInfoOutput.DataBean.MatchScoreDetailsBean.InningsBean();

                    inningsBean = mMatchDetails.getData().getMatchScoreDetails().getInnings().get(i);

                    if (i == 0) {
                        team1 = inningsBean.getShortName();
                        team1Score = inningsBean.getScoresFull();
                    }
                    if (i == 1) {
                        team2 = inningsBean.getShortName();
                        team2Score = inningsBean.getScoresFull();
                    }
                }
                teamName1.setText(team1.replace("inn.", ""));
                teamName2.setText(team2.replace("inn.", ""));
                score1.setText(team1Score);
                score2.setText(team2Score);
//                scoreData.setText(team1 + "   " + team1Score + "     |    " + team2 + "   " + team2Score);
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnableForTimer);
    }

    private void apiCallGetDraftInfo() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            final GetAuctionInfoInput getAuctionInfoInput = new GetAuctionInfoInput();
            getAuctionInfoInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionInfoInput.setMatchGUID(matchGUID);
            getAuctionInfoInput.setContestGUID(contestGUID);
            getAuctionInfoInput.setIsSeriesStarted("Yes");
            getAuctionInfoInput.setParams("MatchScoreDetails,SeriesName,SeriesGUID,SeriesID,LeagueJoinDateTime,Status,AuctionStatus,LeagueJoinDateTimeUTC,DraftTeamPlayerLimit,DraftPlayerSelectionCriteria,CustomizeWinning,TotalJoined,ContestSize,NoOfWinners,WinningAmount,EntryFee,UserInvitationCode,LeagueJoinDateTimeUTC,DraftLiveRound,MatchID");
            getAuctionInfoInput.setDraftSeriesType("Yes");
            mInteractor.getDraftInfo(getAuctionInfoInput, new IUserInteractor.OnGetAuctionInfoResponseListener() {
                @Override
                public void onSuccess(GetAuctionInfoOutput getAuctionInfoOutput) {
                    mAuctionInfo = getAuctionInfoOutput.getData();
                    GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria draftPlayerSelectionCriteria = mAuctionInfo.getDraftPlayerSelectionCriteria();
                    isSeriesStarted = mAuctionInfo.getIsSeriesMatchStarted().equals("Yes");
                    getContestJoinedUsers();
                    if (seriesStatus != 1) {
                        score_lyt.setVisibility(View.VISIBLE);
                        joinedContest(getAuctionInfoOutput);
                    } else {
                        score_lyt.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new com.websinception.megastar.dialog.AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    getContestInfo();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new com.websinception.megastar.dialog.AlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new com.websinception.megastar.dialog.AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            getContestInfo();

                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }

    private void apiCallGetAuctionInfo() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            final GetAuctionInfoInput getAuctionInfoInput = new GetAuctionInfoInput();
            getAuctionInfoInput.setRoundID(roundId);
            getAuctionInfoInput.setContestGUID(contestGUID);
            getAuctionInfoInput.setIsSeriesStarted("Yes");
            getAuctionInfoInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionInfoInput.setParams("AuctionStatus,SeriesGUID,SeriesName,LeagueJoinDateTime,GameType,Privacy,IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,IsJoined,Status,ContestFormat,ContestType,CustomizeWinning,TotalJoined,UserInvitationCode,TeamNameLocal,TeamNameVisitor,IsConfirm,CashBonusContribution,GameTimeLive,LeagueJoinDateTimeUTC");
            mInteractor.getAuctionInfo(getAuctionInfoInput, new IUserInteractor.OnGetAuctionInfoResponseListener() {
                @Override
                public void onSuccess(GetAuctionInfoOutput getAuctionInfoOutput) {
                    mAuctionInfo = getAuctionInfoOutput.getData();
                    isSeriesStarted = mAuctionInfo.getIsSeriesMatchStarted().equals("Yes");
                    getContestJoinedUsers();

                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new com.websinception.megastar.dialog.AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    getContestInfo();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new com.websinception.megastar.dialog.AlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new com.websinception.megastar.dialog.AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            getContestInfo();

                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }


    private void getAuctionMatchInfo() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            final GetAuctionInfoInput getAuctionInfoInput = new GetAuctionInfoInput();
            getAuctionInfoInput.setRoundID(roundId);
            getAuctionInfoInput.setContestGUID(contestGUID);
            getAuctionInfoInput.setIsSeriesStarted("Yes");
            getAuctionInfoInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionInfoInput.setParams("MatchScoreDetails,AuctionStatus,SeriesGUID,SeriesName,LeagueJoinDateTime,GameType,Privacy,IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,IsJoined,Status,ContestFormat,ContestType,CustomizeWinning,TotalJoined,UserInvitationCode,TeamNameLocal,TeamNameVisitor,IsConfirm,CashBonusContribution,GameTimeLive,LeagueJoinDateTimeUTC");
            mInteractor.getAuctionMatchInfo(getAuctionInfoInput, new IUserInteractor.OnGetAuctionInfoResponseListener() {
                @Override
                public void onSuccess(GetAuctionInfoOutput getAuctionInfoOutput) {
                    if (seriesStatus != 1) {
                        score_lyt.setVisibility(View.VISIBLE);
                        joinedContest(getAuctionInfoOutput);
                    } else {
                        score_lyt.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new com.websinception.megastar.dialog.AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    getContestInfo();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new com.websinception.megastar.dialog.AlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new com.websinception.megastar.dialog.AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            getContestInfo();

                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }


    private void apiCallDownloadteams() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            DownloadTeamInput downloadTeamInput = new DownloadTeamInput();
            downloadTeamInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            downloadTeamInput.setContestGUID(contestGUID);
            downloadTeamInput.setRoundID(roundId);
            downloadTeamInput.setSeriesID(seriesID);
            mInteractor.auctiondownloadTeam(downloadTeamInput, new IUserInteractor.OnDownloadTeamListener() {
                @Override
                public void onSuccess(ResponseDownloadTeam mResponseDownloadTeam) {
                    mProgressDialog.dismiss();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mResponseDownloadTeam.getData().getTeamsPdfFileURL()));
                    startActivity(browserIntent);
                }


                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new com.websinception.megastar.dialog.AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    apiCallDownloadteams();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new com.websinception.megastar.dialog.AlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new com.websinception.megastar.dialog.AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            apiCallDownloadteams();

                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }


    private void apiCallGetAuctionJoinedUser() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();

            GetAuctionJoinedUserInput getAuctionJoinedUserInput = new GetAuctionJoinedUserInput();
            getAuctionJoinedUserInput.setContestGUID(contestGUID);
            getAuctionJoinedUserInput.setRoundID(roundId);
            getAuctionJoinedUserInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionJoinedUserInput.setParams("UserTeamName,TotalPoints,UserWinningAmount,FirstName,Username,UserGUID,UserTeamPlayers,UserTeamID,UserRank,ProfilePic,AuctionBudget");
            mInteractor.getAuctionJoinedUsers(getAuctionJoinedUserInput, new IUserInteractor.OnGetAuctionJoinedUserResponseListener() {
                @Override
                public void onSuccess(GetAuctionJoinedUserOutput getAuctionJoinedUserOutput) {
                    mProgressDialog.dismiss();
                    if (getAuctionJoinedUserOutput.getData().getTotalRecords() != 0) {
                        for (GetAuctionJoinedUserOutput.DataBean.RecordsBean record : getAuctionJoinedUserOutput.getData().getRecords()) {
                            if (record.getUserGUID().equals(AppSession.getInstance().getLoginSession().getData().getUserGUID())) {
                                if (record.getAuctionBudget() != null && !record.getAuctionBudget().trim().isEmpty()) {

                                    isAuctionTeamSubmited = record.getUserTeamPlayers().size() != 0;
                                    try {
                                        myBudget = (Integer.parseInt(record.getAuctionBudget()) / 10000000) + "";
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                                String userWinningAmount = record.getUserWinningAmount();
                                if (mAuctionInfo.getStatus().equals("Completed")) {
                                    mLinearLayoutWinningRoot.setVisibility(View.VISIBLE);
                                    if (userWinningAmount != null && !userWinningAmount.trim().equals("")) {
                                        mCustomTextViewWinningInfo.setText("Your winning is ₹" + userWinningAmount);
                                    } else {
                                        mCustomTextViewWinningInfo.setText("Your winning is N/A");
                                    }
                                } else {
                                    mLinearLayoutWinningRoot.setVisibility(View.GONE);
                                }
                                break;
                            }
                        }
                        setAuctionInfo();
                        mCtvTeamCount.setText(getAuctionJoinedUserOutput.getData().getTotalRecords() + "  Team");
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        mRecyclerView.setAdapter(
                                new AuctionLobbyUserListAdapter(mContext,
                                        getAuctionJoinedUserOutput,
                                        roundId,
                                        flag,
                                        mAuctionInfo.getAuctionStatus(),
                                        mAuctionInfo.getStatus(),
                                        seriesID,
                                        contestGUID,
                                        seriesName,
                                        seriesDeadLine,
                                        seriesStatus,
                                        isSeriesStarted(),
                                        null,
                                        mAuctionInfo.getLeagueJoinDateTimeUTC()));

                    } else {

                        mProgressDialog.dismiss();
                        mAlertDialog = new AlertDialog(mContext,
                                "No User Joined yet.\"",
                                AppUtils.getStrFromRes(R.string.try_again),
                                AppUtils.getStrFromRes(R.string.cancel),
                                new AlertDialog.OnBtnClickListener() {
                                    @Override
                                    public void onYesClick() {
                                        mAlertDialog.hide();
                                        getContestJoinedUsers();
                                    }

                                    @Override
                                    public void onNoClick() {
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
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    getContestJoinedUsers();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            getContestJoinedUsers();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }


    private void apiCallGetDraftJoinedUser() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();

            GetAuctionJoinedUserInput getAuctionJoinedUserInput = new GetAuctionJoinedUserInput();
            getAuctionJoinedUserInput.setContestGUID(contestGUID);
            getAuctionJoinedUserInput.setMatchGUID(matchGUID);
            getAuctionJoinedUserInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionJoinedUserInput.setParams("UserTeamName,TotalPoints,UserWinningAmount,FirstName,Username,UserGUID,UserTeamPlayers,UserTeamID,UserRank,ProfilePic,AuctionBudget");
            mInteractor.getDraftJoinedUsers(getAuctionJoinedUserInput, new IUserInteractor.OnGetAuctionJoinedUserResponseListener() {
                @Override
                public void onSuccess(GetAuctionJoinedUserOutput getAuctionJoinedUserOutput) {
                    mProgressDialog.dismiss();
                    if (getAuctionJoinedUserOutput.getData().getTotalRecords() != 0) {
                        for (GetAuctionJoinedUserOutput.DataBean.RecordsBean record : getAuctionJoinedUserOutput.getData().getRecords()) {
                            if (record.getUserGUID().equals(AppSession.getInstance().getLoginSession().getData().getUserGUID())) {
                                if (record.getAuctionBudget() != null && !record.getAuctionBudget().trim().isEmpty()) {
                                    isAuctionTeamSubmited = record.getUserTeamPlayers().size() != 0;
                                    try {
                                        myBudget = (Integer.parseInt(record.getAuctionBudget()) / 10000000) + "";
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                                String userWinningAmount = record.getUserWinningAmount();
                                if (mAuctionInfo.getStatus().equals("Completed")) {
                                    mLinearLayoutWinningRoot.setVisibility(View.VISIBLE);
                                    if (userWinningAmount != null && !userWinningAmount.trim().equals("")) {
                                        mCustomTextViewWinningInfo.setText("Your winning is ₹" + userWinningAmount);
                                    } else {
                                        mCustomTextViewWinningInfo.setText("Your winning is N/A");
                                    }
                                } else {
                                    mLinearLayoutWinningRoot.setVisibility(View.GONE);
                                }
                                break;
                            }
                        }
                        setAuctionInfo();
                        mCtvTeamCount.setText(getAuctionJoinedUserOutput.getData().getTotalRecords() + "  Team");
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        mRecyclerView.setAdapter(
                                new AuctionLobbyUserListAdapter(mContext,
                                        getAuctionJoinedUserOutput,
                                        matchGUID,
                                        flag,
                                        mAuctionInfo.getAuctionStatus(),
                                        mAuctionInfo.getStatus(),
                                        seriesID,
                                        contestGUID,
                                        seriesName,
                                        seriesDeadLine,
                                        seriesStatus,
                                        isSeriesStarted(),
                                        mAuctionInfo.getDraftPlayerSelectionCriteria(),
                                        mAuctionInfo.getLeagueJoinDateTimeUTC()));
                    } else {

                        mProgressDialog.dismiss();
                        mAlertDialog = new AlertDialog(mContext,
                                "No User Joined yet.\"",
                                AppUtils.getStrFromRes(R.string.try_again),
                                AppUtils.getStrFromRes(R.string.cancel),
                                new AlertDialog.OnBtnClickListener() {
                                    @Override
                                    public void onYesClick() {
                                        mAlertDialog.hide();
                                        getContestJoinedUsers();
                                    }

                                    @Override
                                    public void onNoClick() {
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
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    getContestJoinedUsers();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            getContestJoinedUsers();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }


    private void showWinningBreckUp(final List<WinnersRankBean> bean, final String totalWinngingAmmount) {
        final WinningsFragment dialogFragment = new WinningsFragment();
        dialogFragment.setUpdateable(new WinnersCallback() {
            @Override
            public void close() {

            }

            @Override
            public Context getContext() {
                return mContext;
            }


            @Override
            public List<WinnersRankBean> getBean() {
                return bean;
            }

            @Override
            public String getTotalWiningAmount() {
                return totalWinngingAmmount;
            }

            @Override
            public String getWinningType() {
                return null;
            }
        });
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());

    }

    public boolean isSeriesStarted() {
        return isSeriesStarted;
    }


    private Runnable mRunnableForTimer = new Runnable() {
        @Override
        public void run() {
            if (mAuctionInfo != null && mAuctionInfo.getAuctionStatus().equals("Pending")) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    long auctionTime = simpleDateFormat.parse(mAuctionInfo.getLeagueJoinDateTimeUTC()).getTime();
                    long currentTime = AppUtils.getSystemTime();
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
                    String txt = "";
                    if (days > 0) {
                        txt += String.format("%02d", days) + "d";
                    }
                    if (hours > 0) {
                        txt += (txt.isEmpty() ? "" : " ") + String.format("%02d", hours) + "h";
                    }
                    if (minute > 0) {
                        txt += (txt.isEmpty() ? "" : " ") + String.format("%02d", minute) + "m";
                    }
                    if (seconds > 0) {
                        txt += (txt.isEmpty() ? "" : " ") + String.format("%02d", seconds) + "s";
                    }
                    final String finalTxt = txt;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCustomTextViewRemainingTime.setText(finalTxt);
                        }
                    });
                    if (!(days + hours + minute + seconds <= 0)) {
                        mHandler.postDelayed(mRunnableForTimer, 1000);
                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mLinearLayoutEnterAuctionRoot.setVisibility(View.VISIBLE);
                                mLinearLayoutRemainingTimeRoot.setVisibility(View.GONE);
//                                AuctionHomeActivity.start(mContext, roundId, seriesID, contestGUID, seriesName, seriesDeadLine, seriesStatus);
                                auctionHomeBtn();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
