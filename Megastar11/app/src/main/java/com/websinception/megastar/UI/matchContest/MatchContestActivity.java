package com.websinception.megastar.UI.matchContest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.allContest.AllContest;
import com.websinception.megastar.UI.contestDetailLeaderBoard.ContestLeaderBoard;
import com.websinception.megastar.UI.contestInviteCode.InviteCodes;
import com.websinception.megastar.UI.createContest.CreateContestActivity;
import com.websinception.megastar.UI.createTeam.CreateTeamActivity;
import com.websinception.megastar.UI.filter.BottomSheetFilterFragment;
import com.websinception.megastar.UI.filter.ContestSearchResultFilters;
import com.websinception.megastar.UI.home.HomeNavigation;
import com.websinception.megastar.UI.inviteContest.InviteContestActivity;
import com.websinception.megastar.UI.joinedContests.AllJoinedContest;
import com.websinception.megastar.UI.myAccount.MyAccountDialogActivity;
import com.websinception.megastar.UI.myTeams.MyTeamsActivity;
import com.websinception.megastar.UI.winnings.WinnersCallback;
import com.websinception.megastar.UI.winnings.WinnersRankBean;
import com.websinception.megastar.UI.winnings.WinningsFragment;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.MatchContestInput;
import com.websinception.megastar.beanInput.MatchDetailInput;
import com.websinception.megastar.beanOutput.CheckContestBean;
import com.websinception.megastar.beanOutput.MatchContestOutPut;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.ItemOffsetDecoration;
import com.websinception.megastar.utility.OnWinnerClickListener;
import com.websinception.megastar.utility.TimeUtils;
import com.websinception.megastar.utility.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

public class MatchContestActivity extends BaseActivity implements MatchDetailView {


    private String isMatchReal = "";

    public static void start(Context context, String matchGUID, String StatusID) {
        Intent starter = new Intent(context, MatchContestActivity.class);

        starter.putExtra("MatchGUID", matchGUID);
        starter.putExtra("StatusID", StatusID);


        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    public static void start(Context context, String matchGUID, String StatusID, Boolean flag) {
        Intent starter = new Intent(context, MatchContestActivity.class);

        starter.putExtra("MatchGUID", matchGUID);
        starter.putExtra("StatusID", StatusID);
        starter.putExtra("Flag", flag);

        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    Loader loader;
    Context mContext;

    String matchTeamVS = "";
    boolean flag = false;
    String teamCount = "0";
    // private ProgressDialog mProgressDialog;
    // gjgj

    CheckContestBean mCheckContestBean;


    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.ctv_timmer_local)
    CustomTextView ctv_timmer_local;

    @BindView(R.id.ctv_timmer_visitor)
    CustomTextView ctv_timmer_visitor;

    @BindView(R.id.civ_timmer_local)
    CustomImageView civ_timmer_local;

    @BindView(R.id.civ_timmer_visitor)
    CustomImageView civ_timmer_visitor;


    CountDownTimer countDownTimer;

    MatchDetailOutPut matchDetail;
    MatchContestPresenterImpl mMatchContestPresenter;

    String StatusID;
    String MatchGUID;

    MatchContestOutPut matchContestOutPut;


    @BindView(R.id.contestSection)
    LinearLayout contestSection;

    @BindView(R.id.private_button)
    LinearLayout private_button;

    @BindView(R.id.ctv_contest_count)
    CustomTextView ctv_contest_count;


    @BindView(R.id.contestContainer)
    LinearLayout contestContainer;

    @BindView(R.id.notFound)
    RelativeLayout notFound;

    @BindView(R.id.notFountMsg)
    CustomTextView notFountMsg;

    @BindView(R.id.joinButton)
    CustomTextView allContest;

    @BindView(R.id.contesRel)
    RelativeLayout contesRel;

    @BindView(R.id.saveLin)
    LinearLayout saveLin;

    @BindView(R.id.saveRel)
    RelativeLayout saveRel;


    @BindView(R.id.teamsVS)
    CustomTextView teamsVS;

    @BindView(R.id.ctv_full_time)
    CustomTextView ctv_timer;

    @BindView(R.id.ctv_my_team)
    CustomTextView ctv_my_team;

    @BindView(R.id.ctv_join_contest)
    CustomTextView ctv_join_contest;

    @BindView(R.id.gotContestCode)
    CardView gotContestCode;

    @BindView(R.id.create_contest)
    CardView create_contest;

    @OnClick(R.id.gotContestCode)
    void clickOnContestCode() {

        InviteCodes.start(this, teamCount);
    }

    @OnClick(R.id.wallet)
    void onwalletClick() {
        //JoinContestActivityStart(mContext);
        MyAccountDialogActivity.start(mContext, false);

    }

    @OnClick(R.id.back)
    void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.ll_join_contest)
    public void onClickJoinedContest() {
        if (matchDetail.getData().getMatchGUID() != null && !matchContestOutPut.getData().getStatics().getJoinedContest().equalsIgnoreCase("0")) {
            AllJoinedContest.start(mContext, matchDetail.getData().getMatchGUID(), matchDetail.getData().getStatus());
        } else {
            AppUtils.showToast(mContext, "You have not joined any contest.");
        }
    }

    @Override
    public void onBackPressed() {

        if (flag) {
            finish();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 1000);
            HomeNavigation.start(this);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.ll_my_team)
    public void myTeam(View view) {
        if (matchDetail == null) return;
        MyTeamActivityStart(mContext,
                matchDetail.getData().getMatchGUID(),
                matchDetail.getData().getStatus(), "", "", teamsVS.getText().toString(), "", new ArrayList<String>());
    }

    @OnClick({R.id.allContest, R.id.joinButton})
    public void onClickAllContest() {
        AllContest.start(mContext, matchDetail.getData().getMatchGUID(), matchDetail.getData().getStatus());
    }

    @OnClick(R.id.menu)
    void onMenuFilterClick() {
        showFilter();
    }

    @OnClick(R.id.create_contest)
    void oncreate_contest() {

        if (matchDetail != null) {
            if (AppSession.getInstance().getGameType() == 1) {
                if (isMatchReal.equalsIgnoreCase("Virtual")) {
                    AppUtils.showToast(mContext, "You can not create private contest in Virtual Match.");
                } else {
                    CreateContestActivityStart(mContext, matchDetail.getData().getSeriesGUID(),
                            matchDetail.getData().getMatchGUID(),
                            matchDetail.getData().getTeamNameShortLocal(),
                            matchDetail.getData().getTeamNameShortVisitor());
                }
            } else {
                CreateContestActivityStart(mContext, matchDetail.getData().getSeriesGUID(),
                        matchDetail.getData().getMatchGUID(),
                        matchDetail.getData().getTeamNameShortLocal(),
                        matchDetail.getData().getTeamNameShortVisitor());
            }
        }

    }

    @OnClick(R.id.create_teaam1)
    void onCreateTeam() {
        CreateTeamActivityStart(mContext, MatchGUID);
        //CreateTeamActivityStart(mContext, "fa1063fb-5e79-bdc6-5f5c-8311ecae9994");
    }

    @BindView(R.id.title)
    CustomTextView title;

    public void CreateTeamActivityStart(Context context, String MatchGUID) {
        Intent starter = new Intent(context, CreateTeamActivity.class);
        starter.putExtra("MatchGUID", MatchGUID);
        starter.putExtra("join", "0");
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_TEAM);
    }

    public void CreateTeamActivityStart(Context context, String MatchGUID, String contestId, String joiningAmount, String cashBonusContribution,
                                        ArrayList<String> joinedTeamGUIDS,
                                        MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer1,
                                        MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer2) {

        Intent starter = new Intent(context, CreateTeamActivity.class);
        starter.putExtra("MatchGUID", MatchGUID);
        starter.putExtra("contestId", contestId);

        starter.putExtra("join", "0");
        starter.putExtra("joiningAmount", joiningAmount);
        starter.putExtra("cashBonusContribution", cashBonusContribution);

        starter.putExtra("joinedTeamGUIDS", joinedTeamGUIDS);
        starter.putExtra("offer1", offer1);
        starter.putExtra("offer2", offer2);
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_TEAM);
    }


    private OnWinnerClickListener.OnWinnerClickCallback onWinnerClickCallBack = new OnWinnerClickListener.OnWinnerClickCallback() {
        @Override
        public void onWinnerClicked(View view, int position,
                                    List<MatchContestOutPut.DataBean.ResultsBean.RecordsBean> responseBeen) {

            List<MatchContestOutPut.DataBean.ResultsBean.RecordsBean.CustomizeWinningBean> customizeWin = responseBeen.get(position).getCustomizeWinning();
            List<WinnersRankBean> rankList = new ArrayList<>();
            for (int i = 0; i < customizeWin.size(); i++) {

                WinnersRankBean mWinnersRankBean = new WinnersRankBean();

                mWinnersRankBean.setFrom(customizeWin.get(i).getFrom());
                mWinnersRankBean.setTo(customizeWin.get(i).getTo());

                if (responseBeen.get(position).getSmartPool().equalsIgnoreCase("Yes")) {
                    mWinnersRankBean.setProductName(customizeWin.get(i).getProductName());
                    mWinnersRankBean.setProductUrl(customizeWin.get(i).getProductUrl());
                } else {
                    mWinnersRankBean.setPercent(customizeWin.get(i).getPercent());
                    mWinnersRankBean.setWinningAmount(customizeWin.get(i).getWinningAmount());

                }

                rankList.add(i, mWinnersRankBean);


            }
            if (responseBeen.get(position).getSmartPool().equalsIgnoreCase("Yes")) {
                showPreview(rankList, null, responseBeen.get(position).getWinningType());
            } else {
                showPreview(rankList, responseBeen.get(position).getWinningAmount(), responseBeen.get(position).getWinningType());
            }


        }
    };
    private OnWinnerClickListener.OnWinnerClickCallback onContestClickCallBack =
            new OnWinnerClickListener.OnWinnerClickCallback() {
                @Override
                public void onWinnerClicked(View view, int position,
                                            List<MatchContestOutPut.DataBean.ResultsBean.RecordsBean> responseBeen) {

                    //CashContest.start(mContext,matchID,responseBeen.get(position).getId());
                    ContestLeaderBoard.start(mContext, MatchGUID, responseBeen.get(position).getContestGUID(), StatusID);


                }
            };

    private OnWinnerClickListener.OnWinnerClickCallback onJoinClickCallBack =
            new OnWinnerClickListener.OnWinnerClickCallback() {
                @Override
                public void onWinnerClicked(View view, int position,
                                            List<MatchContestOutPut.DataBean.ResultsBean.RecordsBean> responseBeen) {

                    finish();
                    finishActivity(RESULT_OK);

                    if (responseBeen != null) {
                        AppSession.getInstance().UserInviteCode = responseBeen.get(position).getUserInvitationCode();
                        if (matchContestOutPut.getData().getStatics().getTotalTeams().equals("0")) {
                            CreateTeamActivityStart(mContext,
                                    matchDetail.getData().getMatchGUID(),
                                    responseBeen.get(position).getContestGUID(), responseBeen.get(position).getEntryFee(), responseBeen.get(position).getCashBonusContribution(),
                                    responseBeen.get(position).getJoinedTeamsGUID(),
                                    responseBeen.get(position).getOffer1(),
                                    responseBeen.get(position).getOffer2());
                        } else {
                            if (responseBeen.get(position).getIsJoined().equals("No")) {
                                MyTeamActivityStart1(mContext,
                                        matchDetail.getData().getMatchGUID(),
                                        matchDetail.getData().getStatus(),
                                        responseBeen.get(position).getContestGUID(),
                                        responseBeen.get(position).getEntryFee(),
                                        teamsVS.getText().toString(),
                                        responseBeen.get(position).getCashBonusContribution(),
                                        responseBeen.get(position).getJoinedTeamsGUID(),
                                        responseBeen.get(position).getOffer1(),
                                        responseBeen.get(position).getOffer2(),
                                        responseBeen.get(position).getEntryType().equals("Single"),
                                        responseBeen.get(position).getUserJoinLimit()- responseBeen.get(position).getUserTeamDetails().size()
                                        );
                            } else {
                                if (responseBeen.get(position).getEntryType().equals("Multiple")) {
                                    MyTeamActivityStart1(mContext,
                                            matchDetail.getData().getMatchGUID(),
                                            matchDetail.getData().getStatus(),
                                            responseBeen.get(position).getContestGUID(),
                                            responseBeen.get(position).getEntryFee(),
                                            teamsVS.getText().toString(),
                                            responseBeen.get(position).getCashBonusContribution(),
                                            responseBeen.get(position).getJoinedTeamsGUID(),
                                            responseBeen.get(position).getOffer1(),
                                            responseBeen.get(position).getOffer2(),
                                            responseBeen.get(position).getEntryType().equals("Single"),
                                            responseBeen.get(position).getUserJoinLimit()- responseBeen.get(position).getUserTeamDetails().size()
                                    );
                                } else {
                                    InviteContestActivity.start(mContext, responseBeen.get(position).getUserInvitationCode(), matchTeamVS);
                                }
                                //InviteContestActivity.start(mContext, responseBeen.get(position).getUserInvitationCode(), matchTeamVS);
                            }
                        }
                    }


                    /*
                     */
            /*CreateTeamActivityStart(mContext,"111127","37879","490",
                    "25","","","");*/

           /* MyTeamActivityStartForJoin(mContext, mResponseMatchDetails.getResponse().getSeries_id(),
                    matchID, mResponseMatchDetails.getResponse().getLocalteam_id(),
                    mResponseMatchDetails.getResponse().getVisitorteam_id(),
                    responseBeen.get(position).getId(),
                    String.valueOf(responseBeen.get(position).getDisplay_join_amount()),
                    responseBeen.get(position).getChip(), responseBeen.get(position).getUser_invite_code()
            );*/

            /*if (mResponseMatchDetails == null) return;
            if (responseBeen.get(position).getIs_user_joined() == 0) {
                if (mResponseMatchDetails.getMyTeam().equals("0")) {
                    CreateTeamActivityStart(mContext, matchDetails.getResponse().getSeriesId(), matchId, matchDetails.getResponse().getLocalteamId(), matchDetails.getResponse().getVisitorteamId(), adapter.getId(position), adapter.getTeamEntryFee(position),adapter.getChip(position), adapter.getUserInviteCode(position));
                } else {
                    MyTeamActivityStart(mContext, matchDetails.getResponse().getSeriesId(), matchId, matchDetails.getResponse().getLocalteamId(), matchDetails.getResponse().getVisitorteamId(), adapter.getId(position), adapter.getTeamEntryFee(position),adapter.getChip(position), adapter.getUserInviteCode(position));
                }
            } else {
                InviteContestActivity.start(mContext, adapter.getUserInviteCode(position));
            }*/

                }
            };

    public void CreateContestActivityStart(Context context, String seriesId,
                                           String matchId, String localteamId, String visitorteamId) {

        Intent starter = new Intent(context, CreateContestActivity.class);
        starter.putExtra("seriesId", seriesId);
        starter.putExtra("matchId", matchId);
        starter.putExtra("statusId", matchDetail.getData().getStatus());

      /*  starter.putExtra("teamId", teamId);
        starter.putExtra("teamCount", teamCount);*/
        starter.putExtra("localteamId", localteamId);
        starter.putExtra("visitorteamId", visitorteamId);
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_CONTESTS);
    }

    /*public void MyTeamActivityStart(Context context, String seriesId, String matchId, String localteamId,
                                    String visitorteamId,String joiningAmount, String teamsVSStr) {
        Intent starter = new Intent(context, MyTeamsActivity.class);
        starter.putExtra("seriesId", seriesId);
        starter.putExtra("matchId", matchId);
        starter.putExtra("localteamId", localteamId);
        starter.putExtra("visitorteamId", visitorteamId);
        starter.putExtra("teamsVSStr", teamsVSStr);
        starter.putExtra("joiningAmount", joiningAmount);
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_MY_TEAM);
    }*/

    public void MyTeamActivityStart(Context context, String matchId, String statusId, String contestId, String joiningAmount, String teamsVSStr, String cashBonusContribution, ArrayList<String> joinedTeamGUIDS) {
        Intent starter = MyTeamsActivity.getIntent(context);
        starter.putExtra("contestId", contestId);
        starter.putExtra("matchId", matchId);
        starter.putExtra("statusId", statusId);
        starter.putExtra("joiningAmount", joiningAmount);
        starter.putExtra("teamsVSStr", teamsVSStr);
        starter.putExtra("join", "0");
        starter.putExtra("cashBonusContribution", cashBonusContribution);
        starter.putExtra("joinedTeamGUIDS", joinedTeamGUIDS);
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_MY_TEAM);
    }

    public void MyTeamActivityStart1(Context context, String matchId, String statusId, String contestId, String joiningAmount, String teamsVSStr, String cashBonusContribution,
                                     ArrayList<String> joinedTeamGUIDS,
                                     MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer1,
                                     MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer2,
                                     boolean isSingleEntry,
                                     int maxTeamsAllowed
                                     ) {
        Intent starter = MyTeamsActivity.getIntent(context);
        starter.putExtra("contestId", contestId);
        starter.putExtra("matchId", matchId);
        starter.putExtra("statusId", statusId);
        starter.putExtra("joiningAmount", joiningAmount);
        starter.putExtra("teamsVSStr", teamsVSStr);
        starter.putExtra("join", "0");
        starter.putExtra("cashBonusContribution", cashBonusContribution);
        starter.putExtra("offer1", offer1);
        starter.putExtra("offer2", offer2);
        starter.putExtra("joinedTeamGUIDS", joinedTeamGUIDS);
        starter.putExtra("maxTeamsAllowed", maxTeamsAllowed);
        if (isSingleEntry) {
            starter.putExtra("teamId", "singleEntry");
        }
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_MY_TEAM);
    }


    @Override
    public int getLayout() {


        return R.layout.activity_match_contest;
    }

    @Override
    public void init() {

        mContext = this;
        matchDetail = new MatchDetailOutPut();
        mMatchContestPresenter = new MatchContestPresenterImpl(this, new UserInteractor());

        if (getIntent().hasExtra("MatchGUID")) {

            MatchGUID = getIntent().getStringExtra("MatchGUID");
            StatusID = getIntent().getStringExtra("StatusID");
            flag = getIntent().getBooleanExtra("Flag", false);
        }
        loader = new Loader(this);

        loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callMatchDetail(MatchGUID, StatusID);

            }
        });

        callMatchDetail(MatchGUID, StatusID);

        LocalBroadcastManager.getInstance(mContext).registerReceiver(updates_receiver,
                new IntentFilter(MatchContestActivity.class.getSimpleName()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                callMatchDetail(MatchGUID, StatusID);
            }
        });

    }


    public void callMatchDetail(String matchGuid, String statusId) {

        MatchDetailInput mMatchDetailInput = new MatchDetailInput();
        mMatchDetailInput.setPrivacy("No");
        mMatchDetailInput.setMatchGUID(MatchGUID);
        mMatchDetailInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mMatchDetailInput.setStatus(statusId);
        mMatchDetailInput.setParams(Constant.MATCH_PARAMS);
        mMatchContestPresenter.actionMatchdetail(mMatchDetailInput);
    }

    public void callMatchContest(String matchGuID, String statusId) {

        MatchContestInput mMatchContestInput = new MatchContestInput();
        mMatchContestInput.setPrivacy("No");
        mMatchContestInput.setContestList("Yes");
        mMatchContestInput.setMatchGUID(matchGuID);
        mMatchContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mMatchContestInput.setStatus(statusId);
        mMatchContestInput.setPageNo(1);
        mMatchContestInput.setPageSize(3);
        mMatchContestInput.setOrderBy("TotalJoined");
        mMatchContestInput.setSequence("DESC");
        mMatchContestInput.setParams(Constant.CONTEST_PARAM);
        mMatchContestInput.setFilter("Normal");
        mMatchContestInput.setContestFull("No");
        mMatchContestPresenter.matchContestList(mMatchContestInput);
    }


    @Override
    public void showLoading() {
        loader.start();
    }

    @Override
    public void hideLoading() {
        loader.hide();
    }

    @Override
    public void onMatchSuccess(MatchDetailOutPut mMatchDetailOutPut) {

        hideLoading();

        matchDetail = mMatchDetailOutPut;

        matchTeamVS = mMatchDetailOutPut.getData().getTeamNameShortLocal() + " " + AppUtils.getStrFromRes(R.string.vs) + " " + mMatchDetailOutPut.getData().getTeamNameShortVisitor();

        AppSession.getInstance().MatchTeamVS = matchTeamVS;

        ctv_timmer_local.setText(mMatchDetailOutPut.getData().getTeamNameShortLocal());
        ctv_timmer_visitor.setText(mMatchDetailOutPut.getData().getTeamNameShortVisitor());
        ViewUtils.setImageUrl(civ_timmer_local, mMatchDetailOutPut.getData().getTeamFlagLocal());
        ViewUtils.setImageUrl(civ_timmer_visitor, mMatchDetailOutPut.getData().getTeamFlagVisitor());


        //teamsVS.setText(mMatchDetailOutPut.getData().getTeamNameShortLocal() + " " + AppUtils.getStrFromRes(R.string.vs) + " " + mMatchDetailOutPut.getData().getTeamNameShortVisitor());
        setTime(mMatchDetailOutPut.getData().getMatchStartDateTime(), mMatchDetailOutPut.getData().getMatchDate(),
                mMatchDetailOutPut.getData().getMatchTime(), mMatchDetailOutPut.getData().getCurrentDateTime());
//        AppSession.getInstance().UserInviteCode = mMatchDetailOutPut.getData().;

        callMatchContest(mMatchDetailOutPut.getData().getMatchGUID(), mMatchDetailOutPut.getData().getStatus());

    }

    @Override
    public void onMatchFailure(String errMsg) {

        loader.getTryAgainView();
        loader.dataNotFound(errMsg);
    }

    @Override
    public void onMatchContestSuccess(final MatchContestOutPut responseLogin) {
        hideLoading();

        contestSection.removeAllViews();
        int totalContest = 0;

        matchContestOutPut = new MatchContestOutPut();

        matchContestOutPut = responseLogin;

        teamCount = responseLogin.getData().getStatics().getTotalTeams();

        ctv_contest_count.setText(responseLogin.getData().getContest().getTotalContest()+"");


        for (int i = 0; i < responseLogin.getData().getResults().size(); i++) {

            if (responseLogin.getData().getResults().get(i).getTotalRecords() != 0) {

                for (int j = 0; j < responseLogin.getData().getResults().get(i).getRecords().size(); j++) {
                    isMatchReal = responseLogin.getData().getResults().get(i).getRecords().get(j).getMatchTypeByApi();

                }

                LayoutInflater inflater = LayoutInflater.from(getContext());
                ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                        .findViewById(android.R.id.content)).getChildAt(0);

                View mView = inflater.inflate(R.layout.contest_section_item, viewGroup, false);

                RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycleViewContest);
                CustomTextView contestTypeName = (CustomTextView) mView.findViewById(R.id.contestTypeName);
                CustomTextView contestTypeTitle = (CustomTextView) mView.findViewById(R.id.contestTypeTitle);

                SimpleDraweeView contest_Icon = (SimpleDraweeView) mView.findViewById(R.id.contest_Icon);

                CustomTextView contest_count = (CustomTextView) mView.findViewById(R.id.contest_count);

                contestTypeName.setText(responseLogin.getData().getResults().get(i).getKey());
                contestTypeTitle.setText(responseLogin.getData().getResults().get(i).getTagLine());

                int remainContest =
                        responseLogin.getData().getResults().get(i).getTotalRecords() - responseLogin.getData().getResults().get(i).getRecords().size();
                if (remainContest != 0) {
                    // contest_count.setText(remainContest+" "+AppUtils.getStrFromRes(R.string.more_contest));
                    contest_count.setText("View " + remainContest + " more");
                } else {
                    contest_count.setVisibility(View.GONE);
                }

               // ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen.item_offset);
                //mRecyclerView.addItemDecoration(itemDecoration);
                mRecyclerView.setNestedScrollingEnabled(true);

                LinearLayoutManager layoutManager;

                layoutManager = new LinearLayoutManager(getContext()) {
                    @Override
                    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                        super.onLayoutChildren(recycler, state);
                        // initSpruce();
                    }
                };

                mRecyclerView.setLayoutManager(layoutManager);

                SingleContestAdapter adapter;

                adapter = new SingleContestAdapter(R.layout.single_contest_item, mContext, responseLogin.getData().getResults().get(i).getRecords(),
                        onWinnerClickCallBack, onContestClickCallBack, onJoinClickCallBack, matchTeamVS);
                mRecyclerView.setAdapter(adapter);

                contestSection.addView(mView);
                totalContest++;


                final int finalI = i;
                contest_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AllContest.start(mContext, matchDetail.getData().getMatchGUID(), responseLogin.getData().getResults().get(finalI).getKey(), matchDetail.getData().getStatus());

                    }
                });
            }

        }
        if (AppSession.getInstance().getGameType() == 1) {
            if (isMatchReal.equalsIgnoreCase("Virtual")) {
                private_button.setVisibility(View.GONE);
            } else {
                //private_button.setVisibility(View.VISIBLE);
            }
        }


        if (totalContest == 0) {

            /*loader.getTryAgainView();
            loader.dataNotFound(AppUtils.getStrFromRes(R.string.contestNotFound));


            loader.setNotFound(getContext().getResources().getDrawable(R.drawable.ic_trophy));
            loader.getTryAgainView().setText(getResources().getText(R.string.try_again));
            loader.getTryAgainView().setVisibility(View.VISIBLE);*/

            notFound.setVisibility(View.VISIBLE);
            contesRel.setVisibility(View.GONE);
        }

        if (responseLogin.getData().getStatics().getTotalTeams().equals("0")) {

            saveRel.setVisibility(View.VISIBLE);
            saveLin.setVisibility(View.GONE);

        } else {

            saveRel.setVisibility(View.GONE);
            saveLin.setVisibility(View.VISIBLE);

            ctv_my_team.setText(responseLogin.getData().getStatics().getTotalTeams());
            ctv_join_contest.setText(responseLogin.getData().getStatics().getJoinedContest());


        }

    }

    @Override
    public void onMatchContestFailure(String errMsg) {
        loader.getTryAgainView();
        loader.dataNotFound(errMsg);
        loader.setNotFoundImage(getResources().getDrawable(R.drawable.not_found_img));
    }

    @Override
    public boolean isAttached() {
        return true;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public void setTime(String matchDateTime, final String matchDate, final String matchTime, String currentTime) {
        //ctv_timer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_time, 0, 0, 0);
        try {
            if (countDownTimer != null) countDownTimer.cancel();
            if (ctv_timer != null) {

                long remainingTime = TimeUtils.getTimeDifference(matchDateTime,
                        currentTime);

                //ctv_timer.setPaintFlags(ctv_timer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                if (TimeUtils.isThisDateValid(matchDateTime, "yyyy-MM-dd HH:mm:ss")) {
                    if (TimeUnit.MILLISECONDS.toHours(remainingTime) > Constant.SHOW_TIME_LIMIT_HRS) {
                        ctv_timer.setText(TimeUtils.getMatchDateOnly(matchDate));
                    } else {
                        //need to implement counter
                        countDownTimer = new CountDownTimer(remainingTime, TimeUnit.SECONDS.toMillis(1)) {
                            public void onTick(long millisUntilFinished) {
                                if (ctv_timer != null)
                                    ctv_timer.setText(TimeUtils.getRemainsTime(millisUntilFinished));
                            }

                            public void onFinish() {
                                if (ctv_timer != null)
                                    ctv_timer.setText(TimeUtils.getDisplayFullDate1(matchDate, matchTime));
                            }
                        };
                        if (countDownTimer != null) {
                            countDownTimer.start();
                        }
                    }
                } else {
                    ctv_timer.setText(TimeUtils.getMatchDateOnly(matchDate));
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            ctv_timer.setText("N/A");
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
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());

    }

    private void showFilter() {
        final BottomSheetFilterFragment dialogFragment = new BottomSheetFilterFragment();
        dialogFragment.setUpdateable(new ContestSearchResultFilters() {
            @Override
            public void search() {
                /*Intent intent = new Intent(MatchContestActivity.class.getSimpleName());
                intent.putExtra("KEY", "REFRESH");
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);*/
                AllContest.start(mContext, matchDetail.getData().getMatchGUID(), matchDetail.getData().getStatus());
            }

            @Override
            public void reSetFilter() {

            }

            @Override
            public Context getContext() {
                return mContext;
            }
        });
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());

    }


    private BroadcastReceiver updates_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            try {
                if (i.getAction().equals(MatchContestActivity.class.getSimpleName())) {
                    if (i.hasExtra("KEY")) {
                        if (i.getStringExtra("KEY").equals("REFRESH")) {
                            callMatchDetail(MatchGUID, StatusID);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
