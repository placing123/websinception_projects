package com.websinception.megastar.UI.contestDetail;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.contestDetailLeaderBoard.ContestLeaderPresenterImpl;
import com.websinception.megastar.UI.contestDetailLeaderBoard.ContestLeaderView;
import com.websinception.megastar.UI.joinedContests.AllJoinedFragment;
import com.websinception.megastar.UI.matches.MatchesFragment;
import com.websinception.megastar.UI.playerPoints.PlayerPointsActivity;
import com.websinception.megastar.UI.previewTeam.BottomSheetFootballTeamPreviewFragment;
import com.websinception.megastar.UI.previewTeam.BottomSheetPreviewFragment;
import com.websinception.megastar.UI.previewTeam.PlayerPreviewCallback;
import com.websinception.megastar.UI.previewTeam.PlayerRecord;
import com.websinception.megastar.UI.winnings.WinnersCallback;
import com.websinception.megastar.UI.winnings.WinnersRankBean;
import com.websinception.megastar.UI.winnings.WinningsFragment;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.BasePagerAdapter;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.DreamTeamInput;
import com.websinception.megastar.beanInput.MatchDetailInput;
import com.websinception.megastar.beanOutput.ContestDetailOutput;
import com.websinception.megastar.beanOutput.Download;
import com.websinception.megastar.beanOutput.DreamTeamOutput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;
import com.websinception.megastar.beanOutput.ResponseDownloadTeam;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.AlertDialog;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.download.DownloadServiceHttpUrl;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.FileOpen;
import com.websinception.megastar.utility.TimeUtils;
import com.websinception.megastar.utility.ViewUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;


public class ContestDetailFragment extends BaseFragment implements ContestLeaderView {


    @BindView(R.id.linearLayout_score_root)
    LinearLayout linearLayout_score_root;


    @BindView(R.id.ctv_timmer_local)
    CustomTextView ctv_timmer_local;

    @BindView(R.id.ctv_timmer_visitor)
    CustomTextView ctv_timmer_visitor;

    @BindView(R.id.civ_timmer_local)
    CustomImageView civ_timmer_local;

    @BindView(R.id.civ_timmer_visitor)
    CustomImageView civ_timmer_visitor;


    private ProgressDialog mProgressDialog;
    public String matchGUID;
    public String statusId;

    ContestDetailOutput mContestDetailOutput;
    //MatchDetailOutPut details;

    @BindView(R.id.joinContestUpcoming)
    @Nullable
    CustomTextView joinContestUpcoming;

    @BindView(R.id.upcomingMatchesLin)
    @Nullable
    LinearLayout upcomingMatchesLin;

    public BasePagerAdapter mViewPagerAdapter;
    @BindString(R.string.fixtures)
    String fixtures;
    @BindString(R.string.live)
    String live;
    @BindString(R.string.results)
    String results;
    @BindView(R.id.viewPager)
    @Nullable
    ViewPager mViewPager;
    @BindView(R.id.fram)
    @Nullable
    FrameLayout frameLayout;
    @BindView(R.id.iv_team_1)
    @Nullable
    CustomImageView mCustomImageViewTeam1;
    @BindView(R.id.iv_team_2)
    @Nullable
    CustomImageView mCustomImageViewTeam2;

    @BindView(R.id.ctv_full_time)
    @Nullable
    CustomTextView customTextViewFullTime;
    @BindView(R.id.ctv_winning_amount)
    @Nullable
    CustomTextView customTextViewWinningAmount;
    @BindView(R.id.ctv_entry_fee)
    @Nullable
    CustomTextView customTextViewEntryFee;
    @BindView(R.id.confirmWinningTag)
    @Nullable
    CustomTextView confirmWinningTag;
    @BindView(R.id.joinMultipleTag)
    @Nullable
    CustomTextView joinMultipleTag;
    @BindView(R.id.rl_winnings)
    @Nullable
    CustomTextView rlWinnings;
    @BindView(R.id.winners)
    @Nullable
    CustomTextView winners;
    @BindView(R.id.teamName)
    @Nullable
    CustomTextView teamName;

    @BindView(R.id.ctv_joined)
    @Nullable
    CustomTextView ctvJoined;
    @BindView(R.id.progress_view)
    @Nullable
    ProgressBar progressBar;
    @BindView(R.id.ctv_join)
    @Nullable
    CustomTextView ctvJoin;
    @BindView(R.id.ctvSwitchTeam)
    @Nullable
    CustomTextView ctvSwitchTeam;

    @BindView(R.id.free_icon)
    ImageView free_icon;
    @BindView(R.id.spotLeftCount)
    @Nullable
    CustomTextView spotLeftCount;
    @BindView(R.id.teamsCount)
    @Nullable
    CustomTextView teamsCount;
    @BindView(R.id.teamsVS)
    @Nullable
    CustomTextView teamsVS;
    @BindView(R.id.switchTeam_lin)
    @Nullable
    LinearLayout switchTeam_lin;
    @BindView(R.id.scorecard)
    @Nullable
    RelativeLayout scorecard;
    @BindView(R.id.linout)
    @Nullable
    LinearLayout linout;
    @BindView(R.id.dreamteamLin)
    @Nullable
    LinearLayout dreamteamLin;

    @BindView(R.id.saveRel)
    @Nullable
    RelativeLayout saveRel;


    @BindView(R.id.score1)
    @Nullable
    CustomTextView score1;

    @BindView(R.id.score2)
    @Nullable
    CustomTextView score2;



    @BindView(R.id._score1)
    @Nullable
    CustomTextView _score1;

    @BindView(R.id._score2)
    @Nullable
    CustomTextView _score2;


    @BindView(R.id.team1)
    @Nullable
    CustomTextView teamName1;

    @BindView(R.id._team1)
    @Nullable
    CustomTextView _teamName1;

    @BindView(R.id.team2)
    @Nullable
    CustomTextView teamName2;


    @BindView(R.id.ctvInnIndicator1)
    @Nullable
    CustomTextView ctvInnIndicator1;


    @BindView(R.id.ctvInnIndicator2)
    @Nullable
    CustomTextView ctvInnIndicator2;


    @BindView(R.id._team2)
    @Nullable
    CustomTextView _teamName2;

    @BindView(R.id.overs1)
    @Nullable
    CustomTextView overs1;

    @BindView(R.id.overs2)
    @Nullable
    CustomTextView overs2;


    @BindView(R.id._overs1)
    @Nullable
    CustomTextView _overs1;

    @BindView(R.id._overs2)
    @Nullable
    CustomTextView _overs2;


    @BindView(R.id.liveStatus)
    @Nullable
    CustomTextView liveStatus;

    @BindView(R.id.viewPlayerStates)
    @Nullable
    CustomTextView viewPlayerStates;

    @BindView(R.id.swipeContainer)
    @Nullable
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.view1)
    @Nullable
    View view1;

    @BindView(R.id.create_teaam1)
    @Nullable
    CustomTextView create_teaam1;
    private MatchDetailOutPut.DataBean mMatchDetailOut;

    @OnClick(R.id.create_teaam1)
    void onClick() {
        callAllPlayesScore();

    }

    @OnClick(R.id.viewPlayerStates)
    void onPlayerStateClick() {

        PlayerPointsActivity.start(mContext, "", matchGUID, "",statusId);
    }

    //BottomSheetPreviewFragment dialogFragment;
    String type = "";
    //ResponseContestDetails mResponseContestDetails;
    String loginSessionKey = "";
    String seriesGUID = "";

    Loader loader;
    Context mContext;


    CountDownTimer countDownTimer;

    AlertDialog alertRefreshDialog;
    //LeaderBoardFragment leaderBoardFragment;
    android.app.ProgressDialog bar;

    private BroadcastReceiver updates_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            try {
                if (i.getAction().equals(ContestDetailFragment.class.getSimpleName())) {
                    if (i.hasExtra("KEY")) {
                        if (i.getStringExtra("KEY").equals("REFRESH")) {
                            //callTask();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(DownloadServiceHttpUrl.MESSAGE_PROGRESS)) {

                Download download = intent.getParcelableExtra("download");

                if (download.getStatus() == Download.DownloadStatus.START) {

                    if (bar != null && bar.isShowing())
                        bar.dismiss();
                    bar = new android.app.ProgressDialog(getActivity());
                    bar.setCancelable(false);
                    bar.setMessage("Downloading...");
                    bar.setIndeterminate(true);
                    bar.setCanceledOnTouchOutside(false);
                    bar.show();

                } else if (download.getStatus() == Download.DownloadStatus.DOWNLOADING) {
                    bar.setIndeterminate(false);
                    bar.setMax(100);
                    bar.setProgress(download.getProgress());
                    String msg = "";
                    if (download.getProgress() > 99) {
                        msg = "Finishing... ";
                    } else {
                        msg = "Downloading... " + download.getProgress() + "%";
                    }
                    bar.setMessage(msg);

                } else if (download.getStatus() == Download.DownloadStatus.COMPLETED) {
                    if (bar != null && bar.isShowing())
                        bar.dismiss();


                    try {
                        //onShowSnackBar("File Download Complete");
                        FileOpen.openFile(mContext, new File(download.getFilePath()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        //onShowSnackBar(e.getMessage());
                    }

                } else if (download.getStatus() == Download.DownloadStatus.ERROR) {
                    if (bar != null && bar.isShowing())
                        bar.dismiss();
                }
            }
        }
    };
    //private ProgressDialog mProgressDialog;


    public void setSwipeRefreshLayout(boolean refreshLayout) {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setEnabled(refreshLayout);
    }

    @OnClick(R.id.rl_winnings)
    @Optional
    public void winnings(View view) {
        if (mContestDetailOutput == null) return;

        List<WinnersRankBean> rankList = new ArrayList<>();

        for (int i = 0; i < mContestDetailOutput.getData().getCustomizeWinning().size(); i++) {


            ContestDetailOutput.DataBean.CustomizeWinningBean customizeWinningBean = mContestDetailOutput.getData().getCustomizeWinning().get(i);

            WinnersRankBean mWinnersRankBean = new WinnersRankBean();

            mWinnersRankBean.setFrom(customizeWinningBean.getFrom());
            mWinnersRankBean.setTo(customizeWinningBean.getTo());
            mWinnersRankBean.setPercent(customizeWinningBean.getPercent());
            mWinnersRankBean.setWinningAmount(customizeWinningBean.getWinningAmount());

            rankList.add(i, mWinnersRankBean);


        }
        showPreview(rankList, mContestDetailOutput.getData().getTotalWinningAmount(),mContestDetailOutput.getData().getWinningType());
    }

    @OnClick(R.id.ctvSwitchTeam)
    @Optional
    public void switchTeam(View view) {


    }

    @OnClick(R.id.ctv_download)
    @Optional
    public void download(View view) {

    }

    @OnClick(R.id.ctv_join)
    @Optional
    public void join(View view) {


    }


    @OnClick(R.id.dreamTeam)
    void onDreamTeamClick() {

       /* mMyProfileParentPresenterImpl.allPlayersScore(AppSession.getInstance().getLoginSession().
                        getResponse().getLogin_session_key(), mResponseContestDetails.getMatchDetail().getSeriesId(),
                mResponseContestDetails.getMatchDetail().getLocalteamId(),
                mResponseContestDetails.getMatchDetail().getVisitorteamId(), matchId);*/



      /*  mMyProfileParentPresenterImpl.allPlayersScore(AppSession.getInstance().getLoginSession().
                getResponse().getLogin_session_key(), "111251", "5", "13", "38368");*/


        callAllPlayesScore();
    }

    void callAllPlayesScore() {

        DreamTeamInput dreamTeamInput = new DreamTeamInput();
        dreamTeamInput.setMatchGUID(matchGUID);
        dreamTeamInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        dreamTeamInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
        mContestLeaderPresenterImpl.getDreamTeam(dreamTeamInput);
    }

    @OnClick(R.id.tellYourFriends)
    void onTellYourFriend() {
        /*AppUtils.shareTextUrl(getActivity(), AppUtils.getStrFromRes(R.string.inviteFriendsMore),
                AppSession.getInstance().getLoginSession().getData().getReferralCode(),
                AppUtils.getStrFromRes(R.string.app_name));*/

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode", "requestCode: " + BaseActivity.REQUEST_CODE_CREATE_TEAM);

        if (requestCode == BaseActivity.REQUEST_CODE_CREATE_TEAM && resultCode == getActivity().RESULT_OK) {
            // callTask();
        } else if (requestCode == BaseActivity.REQUEST_CODE_JOIN_CONTESTS && resultCode == getActivity().RESULT_OK) {
            //callTask();
        } else if (requestCode == BaseActivity.REQUEST_CODE_SWITCH_TEAM && resultCode == getActivity().RESULT_OK) {
            //callTask();
        }
    }


    private ContestLeaderPresenterImpl mContestLeaderPresenterImpl;


    public static ContestDetailFragment getInstance(Bundle bundle) {
        ContestDetailFragment profileFragment = new ContestDetailFragment();
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    @Override
    public void init() {

        mProgressDialog = new ProgressDialog(getActivity());
        if (isAttached()) {


            if (AppSession.getInstance().getLoginSession() != null) {
                loginSessionKey = AppSession.getInstance().getLoginSession().getData().getSessionKey();

            }

            mContext = getActivity();
            registerReceiver();
            if (getArguments() != null) {
                if (getArguments().containsKey("matchGUID")) {
                    matchGUID = getArguments().getString("matchGUID");
                    statusId = getArguments().getString("statusId");
                }

            }
            //initiate loader
            loader = new Loader(getCurrentView());
            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            frameLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    setSwipeRefreshLayout(true);
                    return false;
                }
            });

            if (swipeRefreshLayout != null) {
                // Setup refresh listener which triggers new data loading
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // callTask();
                    }
                });
                // Configure the refreshing colors
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,
                        R.color.colorAccent,
                        R.color.secondary_tab_color);


            }


            mContestLeaderPresenterImpl = new ContestLeaderPresenterImpl(this, new UserInteractor());
            loader.start();

            callMatchDetail(matchGUID, statusId);


            LocalBroadcastManager.getInstance(mContext).registerReceiver(updates_receiver, new IntentFilter(ContestDetailFragment.class.getSimpleName()));


        }

    }
    public void callMatchDetail() {
        callMatchDetail(matchGUID,statusId);
    }
    public void callMatchDetail(String matchGuid, String statusId) {

        MatchDetailInput mMatchDetailInput = new MatchDetailInput();
        mMatchDetailInput.setPrivacy("No");
        mMatchDetailInput.setMatchGUID(matchGuid);
        mMatchDetailInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mMatchDetailInput.setStatus(statusId);
        mMatchDetailInput.setParams(Constant.MATCH_PARAMS);

        Log.d("mMatchDetailInput", AppUtils.gsonToJSON(mMatchDetailInput));

        mContestLeaderPresenterImpl.actionMatchdetail(mMatchDetailInput);


    }


    @Override
    public void onDestroy() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(updates_receiver);
        if (broadcastReceiver != null)
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(broadcastReceiver);
        if (mContestLeaderPresenterImpl != null)
            mContestLeaderPresenterImpl.actionLoginCancel();
        super.onDestroy();
    }

    @Override
    public int getLayout() {
        return R.layout.contest_detail_main;
    }

    @Override
    public void showLoading() {
       /* if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();*/
        loader.start();
    }

    @Override
    public void hideLoading() {


        /*if (mProgressDialog != null) mProgressDialog.dismiss();*/
        loader.hide();
    }

    @Override
    public void onMatchSuccess(MatchDetailOutPut mMatchDetailOutPut) {
        if (isAdded() && getActivity() != null && mMatchDetailOutPut.getData().getTeamNameShortVisitor() != null) {
            loader.hide();
            mMatchDetailOut = mMatchDetailOutPut.getData();
            teamsVS.setText(mMatchDetailOutPut.getData().getTeamNameShortLocal()
                    + " " + AppUtils.getStrFromRes(R.string.vs)
                    + " " + mMatchDetailOutPut.getData().getTeamNameShortVisitor());

            ctv_timmer_local.setText(mMatchDetailOutPut.getData().getTeamNameShortLocal());
            ctv_timmer_visitor.setText(mMatchDetailOutPut.getData().getTeamNameShortVisitor());
            ViewUtils.setImageUrl(civ_timmer_local, mMatchDetailOutPut.getData().getTeamFlagLocal());
            ViewUtils.setImageUrl(civ_timmer_visitor, mMatchDetailOutPut.getData().getTeamFlagVisitor());

            seriesGUID = mMatchDetailOutPut.getData().getSeriesGUID();

            if (customTextViewFullTime != null) {
                switch (mMatchDetailOutPut.getData().getStatus()) {
                    case Constant.Pending:
                        setTime(mMatchDetailOutPut.getData().getMatchStartDateTime(), mMatchDetailOutPut.getData().getMatchDate(),
                                mMatchDetailOutPut.getData().getMatchTime(), mMatchDetailOutPut.getData().getCurrentDateTime());
                        break;

                    case Constant.Running:
                        customTextViewFullTime.setAllCaps(true);
                        customTextViewFullTime.setText(mMatchDetailOutPut.getData().getStatus());
                        //customTextViewFullTime.setTextColor(getResources().getColor(R.color.green));
                        break;
                    case Constant.Completed:
                        customTextViewFullTime.setAllCaps(true);
                        customTextViewFullTime.setText(mMatchDetailOutPut.getData().getStatus());
                       // customTextViewFullTime.setTextColor(getResources().getColor(R.color.green));
                        break;


                    default:
                        customTextViewFullTime.setText(mMatchDetailOutPut.getData().getStatus());

                        break;
                }


            }

            if (mMatchDetailOutPut.getData().getMatchScoreDetails() != null) {

                if (mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreLocal() != null) {
                    setScoreBoard(mMatchDetailOutPut);
                }

            }

            Bundle bundle = getArguments();
            if (bundle == null) bundle = new Bundle();


            bundle.putString("MatchGUID", matchGUID);
            bundle.putString("Status", mMatchDetailOutPut.getData().getStatus());
            bundle.putString("statusID", mMatchDetailOutPut.getData().getStatus());
            bundle.putInt("tag", 0);
            bundle.putString("type", "FIXTURE");


            if (mMatchDetailOutPut.getData().getJoinedContests().equals("0")) {
                setRecentMatches(bundle);
            } else {
                upcomingMatchesLin.setVisibility(View.GONE);
                setJoinedContest(bundle);
            }

        }
    }

    void setScoreBoard(MatchDetailOutPut mMatchDetailOutPut) {
        liveStatus.setText(mMatchDetailOutPut.getData().getMatchScoreDetails().getStatusNote());


        String team1 = mMatchDetailOut.getTeamNameShortLocal();
       // String team1 = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreLocal().getShortName();

        //String team2 = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreVisitor().getShortName();
        String team2 = mMatchDetailOut.getTeamNameShortVisitor();

        String team1Score = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreLocal().getScores().replace('/', '-');
        String _team1Score = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreLocalSecondInn().getScores().replace('/', '-');

        String team2Score = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreVisitor().getScores().replace('/', '-');
        String _team2Score = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreVisitorSecondInn().getScores().replace('/', '-');

        String teamOver1 = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreLocal().getOvers();
        String _teamOver1 = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreLocalSecondInn().getOvers();
        String teamOver2 = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreVisitor().getOvers();
        String _teamOver2 = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreVisitorSecondInn().getOvers();


        // String team2Over = mMatchDetailOutPut.getData().getMatchScoreDetails().getTeamScoreVisitor().getOvers();

        teamName1.setText(team1);
        if (team1Score.equalsIgnoreCase("")) {
            score1.setText("NA");
        } else {
            score1.setText(team1Score);
        }

        teamName2.setText(team2);
        if (team1Score.equalsIgnoreCase("")) {
            score2.setText("NA");
        } else {
            score2.setText(team2Score);
        }

        if (AppSession.getInstance().getGameType() == 1) {
            if (teamOver2.equalsIgnoreCase("")) {
                overs2.setText("NA");
            } else {
                overs2.setText("(" + teamOver2 + ")");
            }
            if (teamOver1.equalsIgnoreCase("")) {
                overs1.setText("NA");
            } else {
                overs1.setText("(" + teamOver1 + ")");
            }

        } else {
            overs1.setVisibility(View.GONE);
            overs2.setVisibility(View.GONE);
        }

        if (mMatchDetailOut.getMatchType().equalsIgnoreCase("Test")){
            if (_teamOver1.equalsIgnoreCase("") && _teamOver2.equalsIgnoreCase("")) {
                linearLayout_score_root.setVisibility(View.GONE);
            }else {
                linearLayout_score_root.setVisibility(View.VISIBLE);

                ctvInnIndicator1.setText("- 1'st Inn -");
                ctvInnIndicator2.setText("- 2'nd Inn -");

                _teamName1.setText(team1);
                if (_team1Score.equalsIgnoreCase("")) {
                    _score1.setText("NA");
                } else {
                    _score1.setText(_team1Score);
                }

                _teamName2.setText(team2);
                if (_team2Score.equalsIgnoreCase("")) {
                    _score2.setText("NA");
                } else {
                    _score2.setText(_team2Score);
                }

                if (AppSession.getInstance().getGameType() == 1) {
                    if (_teamOver2.equalsIgnoreCase("")) {
                        _overs2.setText("NA");
                    } else {
                        _overs2.setText("(" + _teamOver2 + ")");
                    }
                    if (_teamOver1.equalsIgnoreCase("")) {
                        _overs1.setText("NA");
                    } else {
                        _overs1.setText("(" + _teamOver1 + ")");
                    }

                } else {
                    _overs1.setVisibility(View.GONE);
                    _overs2.setVisibility(View.GONE);
                }
            }
        }



        if (teamName1.getText().toString().equalsIgnoreCase("")||teamName2.getText().toString().equalsIgnoreCase("")){
            teamName1.setText(mMatchDetailOut.getTeamNameShortLocal()+"()");
            teamName2.setText(mMatchDetailOut.getTeamNameShortVisitor()+"()");
        }


        joinContestUpcoming.setText(" FOR " + team1 + " vs " + team2);


    }

    private void setRecentMatches(Bundle bundle) {
        mViewPagerAdapter = new BasePagerAdapter(getChildFragmentManager());

        MatchesFragment leaderBoardFragment = MatchesFragment.getInstance(bundle);
        mViewPagerAdapter.addFrag(leaderBoardFragment, "", 0);
        if (mViewPager != null) {
            mViewPager.setVisibility(View.VISIBLE);
            mViewPager.setAdapter(mViewPagerAdapter);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }

    private void setJoinedContest(Bundle bundle) {
        mViewPagerAdapter = new BasePagerAdapter(getChildFragmentManager());

        AllJoinedFragment leaderBoardFragment = AllJoinedFragment.getInstance(bundle);
        mViewPagerAdapter.addFrag(leaderBoardFragment, "", 0);
        if (mViewPager != null) {
            mViewPager.setVisibility(View.VISIBLE);
            mViewPager.setAdapter(mViewPagerAdapter);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }


    @Override
    public void onMatchFailure(String errMsg) {

        if (isAdded() && getActivity() != null) {
            loader.error(errMsg);
        }
    }

    @Override
    public void onContestDetailSuccess(ContestDetailOutput mContestDetail) {
        mContestDetailOutput = mContestDetail;

        if (mContestDetail.getData().getIsPaid().equalsIgnoreCase("Yes")) {
            free_icon.setVisibility(View.GONE);
        } else {
            free_icon.setVisibility(View.VISIBLE);
        }
        if (mContestDetail.getData().getIsJoined().equalsIgnoreCase("Yes")) {
            ctvJoin.setVisibility(View.GONE);
            switchTeam_lin.setVisibility(View.VISIBLE);
        } else {
            ctvJoin.setVisibility(View.VISIBLE);
            switchTeam_lin.setVisibility(View.GONE);
        }

        if (!mContestDetail.getData().getNoOfWinners().equals("1")) {
            if (!mContestDetail.getData().getNoOfWinners().equals("0")) {
                rlWinnings.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                rlWinnings.setCompoundDrawablePadding(10);


            }

        }

        if (customTextViewFullTime != null) {
            switch (mContestDetail.getData().getStatusID()) {
                case "1":

                    scorecard.setVisibility(View.GONE);
                    dreamteamLin.setVisibility(View.GONE);
                    saveRel.setVisibility(View.GONE);

                    if (mContestDetail.getData().getIsConfirm().equals("Yes")) {
                        confirmWinningTag.setVisibility(View.VISIBLE);
                    } else {
                        confirmWinningTag.setVisibility(View.GONE);
                    }
                    if (mContestDetail.getData().getEntryType().equals("Single")) {
                        //ctvReJoin.setVisibility(View.VISIBLE);
                        ctvSwitchTeam.setVisibility(View.GONE);

                        joinMultipleTag.setVisibility(View.VISIBLE);
                    } else {
                        joinMultipleTag.setVisibility(View.VISIBLE);
                    }

                    if (mContestDetail.getData().getCustomizeWinning() != null && mContestDetail.getData().getCustomizeWinning().size() > 0) {
                        rlWinnings.setVisibility(View.VISIBLE);
                        winners.setVisibility(View.VISIBLE);
                    } else {
                        rlWinnings.setVisibility(View.GONE);
                        winners.setVisibility(View.GONE);
                    }

                    if (mContestDetail.getData() != null) {
                        if (progressBar != null) {
                            if (mContestDetail.getData().getTotalJoined().equals("0")) {
                                progressBar.setProgress(0);
                            } else {
                                progressBar.setProgress(ViewUtils.getPercentage(Float.parseFloat(mContestDetail.getData().getTotalJoined()),
                                        Integer.parseInt(mContestDetail.getData().getContestSize())));
                            }
                        }
                        if (ctvJoin != null && ctvJoined != null && ctvSwitchTeam != null) {
                            ctvSwitchTeam.setVisibility(View.GONE);

                            if (isFull(mContestDetail)) {
                                ctvJoin.setVisibility(View.GONE);
                                //ctvReJoin.setVisibility(View.GONE);
                                ctvJoined.setText(AppUtils.getStrFromRes(R.string.contest_full) + " (" + mContestDetail.getData().getContestSize() + " " + AppUtils.getStrFromRes(R.string.teams) + ")");
                            } else {
                                ctvJoin.setVisibility(View.VISIBLE);
                                //ctvReJoin.setVisibility(View.GONE);
                                ctvJoined.setText(mContestDetail.getData().getTotalJoined() + "/" + mContestDetail.getData().getContestSize() + " " + AppUtils.getStrFromRes(R.string.joined));
                                if (!mContestDetail.getData().getTotalJoined().equals("0")) {

                                    if (!mContestDetail.getData().getEntryType().equals("Single")) {
                                        ctvJoin.setText(AppUtils.getStrFromRes(R.string.joinplus));
                                    } else {
                                        ctvJoin.setText(AppUtils.getStrFromRes(R.string.invite));
                                    }
                                    ctvSwitchTeam.setVisibility(View.VISIBLE);


                                } else {
                                    joinMultipleTag.setVisibility(View.GONE);
                                    ctvJoin.setText(AppUtils.getStrFromRes(R.string.joinThisContest));
                                }
                            }
                        }
                    }


                    //customTextViewFullTime.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));

                    break;
                case "2":

                    dreamteamLin.setVisibility(View.GONE);
                    saveRel.setVisibility(View.VISIBLE);
                    if (mContestDetail.getData().getCustomizeWinning() != null
                            && mContestDetail.getData().getCustomizeWinning().size() > 0) {
                        rlWinnings.setVisibility(View.VISIBLE);
                    } else {
                        rlWinnings.setVisibility(View.GONE);
                    }


                    if (ctvSwitchTeam != null)
                        ctvSwitchTeam.setVisibility(View.GONE);
                    ctvJoin.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                   // customTextViewFullTime.setTextColor(mContext.getResources().getColor(R.color.doneIconColor));
                    customTextViewFullTime.setText(AppUtils.getStrFromRes(R.string.in_progress));


                    customTextViewFullTime.setText(mContestDetail.getData().getStatusID().equals("5") ? "Live  " :
                            mContestDetail.getData().getStatusID());
                    teamsVS.setText(mContestDetail.getData().getTeamNameShortLocal() + " " + AppUtils.getStrFromRes(R.string.vs)
                            + " " + mContestDetail.getData().getTeamNameShortVisitor());

                    joinedContest(mContestDetail);

                    break;
                case "COMPLETED":
                    dreamteamLin.setVisibility(View.GONE);
                    saveRel.setVisibility(View.VISIBLE);
                    if (mContestDetail.getData().getCustomizeWinning() != null && mContestDetail.getData().getCustomizeWinning().size() > 0) {
                        rlWinnings.setVisibility(View.VISIBLE);
                    } else {
                        rlWinnings.setVisibility(View.GONE);
                    }


                    if (ctvSwitchTeam != null)
                        ctvSwitchTeam.setVisibility(View.GONE);
                    ctvJoin.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    //customTextViewFullTime.setTextColor(mContext.getResources().getColor(R.color.google));


                    customTextViewFullTime.setText(AppUtils.getStrFromRes(R.string.completed));

                    customTextViewFullTime.setText(mContestDetail.getData().getMatchScoreDetails().getStatusLive());
                    teamsVS.setText(mContestDetail.getData().getTeamNameShortLocal()
                            + " " + AppUtils.getStrFromRes(R.string.vs)
                            + " " + mContestDetail.getData().getTeamNameShortVisitor());

                    joinedContest(mContestDetail);
                    break;
            }
        }
        if (mContestDetail.getData() != null) {
            if (customTextViewWinningAmount != null)
                customTextViewWinningAmount.setText(" " + mContestDetail.getData().getWinningAmount());

            int spotLeft = Integer.valueOf(mContestDetail.getData().getContestSize()) - Integer.valueOf(mContestDetail.getData().getTotalJoined());

            rlWinnings.setText(mContestDetail.getData().getNoOfWinners());
            spotLeftCount.setText(AppUtils.getStrFromRes(R.string.only) + " " + spotLeft
                    + " " + AppUtils.getStrFromRes(R.string.spotLeft));
            teamsCount.setText(mContestDetail.getData().getTotalJoined()
                    + " " + AppUtils.getStrFromRes(R.string.teams));

            if (customTextViewEntryFee != null) {
                customTextViewEntryFee.setText(" " + mContestDetail.getData().getEntryFee());
            }
            if (ctvJoined != null) {
                ctvJoined.setText(mContestDetail.getData().getTotalJoined() + "/" + mContestDetail.getData().getContestSize() + " " + AppUtils.getStrFromRes(R.string.teams));
            }
        }
    }


    @Override
    public void onContestDetailFailure(String errMsg) {

        onShowSnackBar(errMsg);
    }

    @Override
    public void onDownloadTeamSuccess(ResponseDownloadTeam mResponseDownloadTeam) {

    }

    @Override
    public void onDownloadTeamFailure(String errMsg) {

    }

    @Override
    public void onDreamTeamSucess(DreamTeamOutput dreamTeamOutput) {


        if (dreamTeamOutput.getData().getRecords() != null) {
            List<PlayerRecord> recordList = new ArrayList<>();
            for (int i = 0; i < dreamTeamOutput.getData().getRecords().size(); i++) {

                DreamTeamOutput.DataBean.RecordsBean recordsBean = dreamTeamOutput.getData().getRecords().get(i);
                PlayerRecord player = new PlayerRecord();
                player.setPlayerGUID(recordsBean.getPlayerGUID());
                player.setPlayerName(recordsBean.getPlayerName());
                player.setPlayerRole(recordsBean.getPlayerRole());
                player.setPlayerPic(recordsBean.getPlayerPic());
                player.setTeamNameShort(recordsBean.getTeamNameShort());


                player.setPoints(recordsBean.getTotalPoints());
                player.setPointCredits(recordsBean.getPlayerSalary());
                player.setTotalPoints(recordsBean.getTotalPointCredits());
                player.setPlayerBattingStyle(recordsBean.getPlayerBattingStyle());
                player.setPlayerBowlingStyle(recordsBean.getPlayerBowlingStyle());
                player.setSeriesGUID(seriesGUID);
                player.setPlayerCountry(recordsBean.getPlayerCountry());
                player.setTeamGUID(recordsBean.getTeamGUID());
                player.setPosition(recordsBean.getPlayerPosition());
                player.setMyPlayer(recordsBean.getMyPlayer());
                player.setPlayerSelectedPercent(recordsBean.getPlayerSelectedPercent());
                player.setTopPlayer(recordsBean.getTopPlayer());
                player.setLocalTeamName(dreamTeamOutput.getData().getRecords().get(i).getTeamGUID());
                player.setPointsData(dreamTeamOutput.getData().getRecords().get(i).getPointsData());
                player.setPlayerSalary(dreamTeamOutput.getData().getRecords().get(i).getPlayerSalary());

                recordList.add(player);

            }
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    showPreviewcrik(recordList, String.valueOf(dreamTeamOutput.getData().getTotalPoints()));
                    break;
                case 2:
                    showPreviewfoot(recordList,
                            String.valueOf(dreamTeamOutput.getData().getTotalPoints()) );
                    break;
            }
//            showTeamPreview(recordList, String.valueOf(dreamTeamOutput.getData().getTotalPoints()));
        } else {
            AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.pageNotFound));
        }

    }

    private void showPreviewcrik(final List<PlayerRecord> bean, final String totalPoint) {
        final BottomSheetPreviewFragment dialogFragment = new BottomSheetPreviewFragment();
        dialogFragment.setUpdateable(new PlayerPreviewCallback() {
            @Override
            public void close() {

            }

            @Override
            public void edit() {
                /*CreateTeamActivityStart(mContext, bean.getSeriesId(), matchId, localteamId,
                        visitorteamId, new Gson().toJson(bean), bean.getName());*/
            }

            @Override
            public void refresh() {

            }

            @Override
            public String getTeamName() {
                return AppUtils.getStrFromRes(R.string.dreamTeam);
            }

            @Override
            public boolean isTeamPoints() {
                return true;
            }

            @Override
            public String totalPoints() {
                return totalPoint;
            }

            @Override
            public String getMatchID() {
                return matchGUID;
            }

            @Override
            public String getStatus() {
                return statusId;
            }

            @Override
            public List<PlayerRecord> getPlayers() {
                return bean;
            }

            @Override
            public Context getContext() {
                return mContext;
            }

            @Override
            public String isPlaying11Avaible() {
                return "No";
            }

            @Override
            public String getLocalTeamGUID() {
                for (PlayerRecord playerRecord : bean) {
                    if (playerRecord.getTeamNameShort().equals(ctv_timmer_local.getText().toString())) {
                        return playerRecord.getTeamGUID();
                    }
                }
                return "" ;
            }
        });
        dialogFragment.show(getChildFragmentManager(), dialogFragment.getTag());
        dialogFragment.setPointLaval(AppUtils.getStrFromRes(R.string.pts));

    }


    private void showPreviewfoot(final List<PlayerRecord> responseMatchPlayers, final String totalPoint) {
        final BottomSheetFootballTeamPreviewFragment dialogFragment = new BottomSheetFootballTeamPreviewFragment();
        dialogFragment.setUpdateable(new PlayerPreviewCallback() {
            @Override
            public void close() {

            }

            @Override
            public void edit() {

            }

            @Override
            public void refresh() {

            }


            @Override
            public String getTeamName() {

                return AppUtils.getStrFromRes(R.string.dreamTeam);

            }

            @Override
            public boolean isTeamPoints() {
                return true;
            }

            @Override
            public String totalPoints() {
                return totalPoint;
            }

            @Override
            public String getMatchID() {
                return matchGUID;
            }

            @Override
            public String getStatus() {
                return statusId;
            }

            @Override
            public List<PlayerRecord> getPlayers() {
                return responseMatchPlayers;
            }

            @Override
            public Context getContext() {
                return mContext;
            }

            @Override
            public String isPlaying11Avaible() {
                return "No";
            }

            @Override
            public String getLocalTeamGUID() {
                return "9993399409";
            }
        });
        dialogFragment.show(getChildFragmentManager(), dialogFragment.getTag());

        dialogFragment.setPointLaval(AppUtils.getStrFromRes(R.string.pts));

    }


    @Override
    public void onDreamTeamFailure(String errMsg) {

        AppUtils.showToast(mContext, errMsg);
    }

    @Override
    public void dreamshowLoading() {
        /*if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();*/

        mProgressDialog.show();
    }

    @Override
    public void dreamhideLoading() {
        /*if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.dismiss();*/
        mProgressDialog.dismiss();
    }

    @Override
    public void onProfileSuccess(LoginResponseOut responseLogin) {

    }

    @Override
    public void onProfileFailure(String errMsg) {

    }

    @Override
    public void onShowSnackBar(String message) {
        AppUtils.showToast(mContext, message);
    }

    @Override
    public boolean isAttached() {
        return isAdded() && getActivity() != null;
    }

    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadServiceHttpUrl.MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    public void setTime(String matchDateTime, final String matchDate, final String matchTime, String currentTime) {
        //customTextViewFullTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_time, 0, 0, 0);
        try {
            if (countDownTimer != null) countDownTimer.cancel();
            if (customTextViewFullTime != null) {

                long remainingTime = TimeUtils.getTimeDifference(matchDateTime,
                        currentTime);

                //ctv_timer.setPaintFlags(ctv_timer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                if (TimeUtils.isThisDateValid(matchDateTime, "yyyy-MM-dd HH:mm:ss")) {
                    if (TimeUnit.MILLISECONDS.toHours(remainingTime) > Constant.SHOW_TIME_LIMIT_HRS) {
                        customTextViewFullTime.setText(TimeUtils.getMatchDateOnly(matchDate));
                    } else {
                        //need to implement counter
                        countDownTimer = new CountDownTimer(remainingTime, TimeUnit.SECONDS.toMillis(1)) {
                            public void onTick(long millisUntilFinished) {
                                if (customTextViewFullTime != null)
                                    customTextViewFullTime.setText(TimeUtils.getRemainsTime(millisUntilFinished));
                            }

                            public void onFinish() {
                                if (customTextViewFullTime != null)
                                    customTextViewFullTime.setText(TimeUtils.getDisplayFullDate1(matchDate, matchTime));
                            }
                        };
                        if (countDownTimer != null) {
                            countDownTimer.start();
                        }
                    }
                } else {
                    customTextViewFullTime.setText(TimeUtils.getMatchDateOnly(matchDate));
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            customTextViewFullTime.setText("N/A");
        }

    }

    public boolean isFull(ContestDetailOutput mContestDetailOutput) {
        if (Integer.valueOf(mContestDetailOutput.getData().getTotalJoined())
                == Integer.parseInt(mContestDetailOutput.getData().getContestSize())) {
            return true;
        } else {
            return false;
        }
    }

    void joinedContest(ContestDetailOutput mMatchDetails) {
        scorecard.setVisibility(View.VISIBLE);
        switchTeam_lin.setVisibility(View.GONE);
        switchTeam_lin.setVisibility(View.GONE);
        linout.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);


        if (mMatchDetails.getData().getMatchScoreDetails().getInnings() != null) {
            if (AppSession.getInstance().getGameType() == 1) {

                String team1 = "", team2 = "", team1Score = "", team2Score = "";

                for (int i = 0; i < mMatchDetails.getData().getMatchScoreDetails().getInnings().size(); i++) {

                    ContestDetailOutput.DataBean.MatchScoreDetailsBean.InningsBean inningsBean = new ContestDetailOutput.DataBean.MatchScoreDetailsBean.InningsBean();

                    inningsBean = mMatchDetails.getData().getMatchScoreDetails().getInnings().get(i);

                    if (i == 0) {
                        team1 = inningsBean.getShortName();
                        team1Score = inningsBean.getScores().replace('/', '-');
                        ;
                    }
                    if (i == 2) {
                        team2 = inningsBean.getShortName();
                        team2Score = inningsBean.getScores().replace('/', '-');
                        ;
                    }


                }
                //scoreData.setText(team1+"   "+team1Score+ "     |    " +team1+"   "+ team2Score);


                teamName1.setText(team1);
                score1.setText(team1Score);
                overs1.setText("");


                teamName2.setText(team2);
                score2.setText(team2Score);
                overs2.setText("");


            }


        }


    }

    void showPreview(final List<WinnersRankBean> bean, final String totalWinngingAmmount, final String winningType) {
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
                return winningType;
            }
        });
        dialogFragment.show(getFragmentManager(), dialogFragment.getTag());

    }
}
