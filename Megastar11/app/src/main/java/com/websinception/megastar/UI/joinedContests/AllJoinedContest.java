package com.websinception.megastar.UI.joinedContests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.allContest.AllContestPresenterImpl;
import com.websinception.megastar.UI.allContest.AllContestView;
import com.websinception.megastar.UI.contestDetailLeaderBoard.ContestLeaderBoard;
import com.websinception.megastar.UI.createTeam.CreateTeamActivity;
import com.websinception.megastar.UI.inviteContest.InviteContestActivity;
import com.websinception.megastar.UI.loginRagisterModule.LoginScreen;
import com.websinception.megastar.UI.matchContest.SingleContestAdapter;
import com.websinception.megastar.UI.myMatches.MyMatchesPresenterImpl;
import com.websinception.megastar.UI.myMatches.MyMatchesView;
import com.websinception.megastar.UI.myTeams.MyTeamsActivity;
import com.websinception.megastar.UI.winnings.WinnersCallback;
import com.websinception.megastar.UI.winnings.WinnersRankBean;
import com.websinception.megastar.UI.winnings.WinningsFragment;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.base.LoaderScroller;
import com.websinception.megastar.beanInput.JoinedContestInput;
import com.websinception.megastar.beanInput.MatchDetailInput;
import com.websinception.megastar.beanOutput.AllContestOutPut;
import com.websinception.megastar.beanOutput.JoinedContestOutput;
import com.websinception.megastar.beanOutput.MatchContestOutPut;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;
import com.websinception.megastar.beanOutput.MyContestMatchesOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.EndlessRecyclerViewScrollListenerFab;
import com.websinception.megastar.utility.ItemOffsetDecoration;
import com.websinception.megastar.utility.OnWinnerClickListener;
import com.websinception.megastar.utility.TimeUtils;
import com.websinception.megastar.utility.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

public class AllJoinedContest extends BaseActivity implements AllContestView, MyMatchesView {

    @BindView(R.id.ctv_timmer_local)
    CustomTextView ctv_timmer_local;

    @BindView(R.id.ctv_timmer_visitor)
    CustomTextView ctv_timmer_visitor;

    @BindView(R.id.civ_timmer_local)
    CustomImageView civ_timmer_local;

    @BindView(R.id.civ_timmer_visitor)
    CustomImageView civ_timmer_visitor;

    /* @BindView(R.id.recycler_view)
     RecyclerView mRecyclerView;*/
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.title)
    CustomTextView title;
    @BindView(R.id.teamsVS)
    CustomTextView teamsVS;
    @BindView(R.id.ctv_full_time)
    CustomTextView ctv_timer;
   /* @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;*/

    @BindView(R.id.ll_main)
    LinearLayout mLinearLayout;

    @BindView(R.id.winnings)
    CustomTextView winnings;

    @BindView(R.id.teams)
    CustomTextView teams;

    @BindView(R.id.winners)
    CustomTextView winners;

    @BindView(R.id.entry)
    CustomTextView entry;

    @BindView(R.id.contestSection)
    LinearLayout contestSection;

    @OnClick(R.id.back)
    void onBackClick() {

        onBackPressed();
    }


    private EndlessRecyclerViewScrollListenerFab scrollListener;
    private LinearLayoutManager layoutManager;

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private Loader loader;
    private LoaderScroller loaderScroller;

    String statusID = Constant.Pending;
    String matchGUID;

    //public AllJoinedContestAdapter adapter;

    JoinedContestOutput allContest;
    String matchTeamVS = "";

    private AllContestPresenterImpl mAllContestPresenterImpl;
    private MyMatchesPresenterImpl presenterImpl;

    List<JoinedContestOutput.DataBean.RecordsBean> all_contest;


    public static void start(Context context, String match_id, String statusID) {
        Intent starter = new Intent(context, AllJoinedContest.class);

        starter.putExtra("matchGUID", match_id);
        starter.putExtra("statusID", statusID);

        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    private OnWinnerClickListener.OnWinnerClickCallback onWinnerClickCallBack = new OnWinnerClickListener.OnWinnerClickCallback() {
        @Override
        public void onWinnerClicked(View view, int position,
                                    List<MatchContestOutPut.DataBean.ResultsBean.RecordsBean> responseBeen) {

            /*List<JoinedContestOutput.DataBean.RecordsBean.CustomizeWinningBean> customizeWin= adapter.getItem(position).getCustomizeWinning();
            List<WinnersRankBean> rankList = new ArrayList<>();
            for (int i = 0; i < customizeWin.size(); i++) {

                WinnersRankBean mWinnersRankBean = new WinnersRankBean();

                mWinnersRankBean.setFrom(customizeWin.get(i).getFrom());
                mWinnersRankBean.setTo(customizeWin.get(i).getTo());
                mWinnersRankBean.setPercent(customizeWin.get(i).getPercent());
                mWinnersRankBean.setWinningAmount(customizeWin.get(i).getWinningAmount());

                rankList.add(i, mWinnersRankBean);

            }

            showPreview(rankList, adapter.getItem(position).getWinningAmount());*/

            List<MatchContestOutPut.DataBean.ResultsBean.RecordsBean.CustomizeWinningBean> customizeWin = responseBeen.get(position).getCustomizeWinning();
            List<WinnersRankBean> rankList = new ArrayList<>();
            for (int i = 0; i < customizeWin.size(); i++) {

                WinnersRankBean mWinnersRankBean = new WinnersRankBean();

                mWinnersRankBean.setFrom(customizeWin.get(i).getFrom());
                mWinnersRankBean.setTo(customizeWin.get(i).getTo());
//                mWinnersRankBean.setPercent(customizeWin.get(i).getPercent());
//                mWinnersRankBean.setWinningAmount(customizeWin.get(i).getWinningAmount());

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
//            showPreview(rankList,responseBeen.get(position).getWinningAmount());
        }
    };

    private OnWinnerClickListener.OnWinnerClickCallback onContestClickCallBack =
            new OnWinnerClickListener.OnWinnerClickCallback() {
                @Override
                public void onWinnerClicked(View view, int position,
                                            List<MatchContestOutPut.DataBean.ResultsBean.RecordsBean> responseBeen) {

//            ContestLeaderBoard.start(mContext, matchGUID, adapter.getItem(position).getContestGUID(),statusID);
                    ContestLeaderBoard.start(mContext, matchGUID, responseBeen.get(position).getContestGUID(), statusID);
                }
            };

    private OnWinnerClickListener.OnWinnerClickCallback onJoinClickCallBack =
            new OnWinnerClickListener.OnWinnerClickCallback() {
                @Override
                public void onWinnerClicked(View view, int position,
                                            List<MatchContestOutPut.DataBean.ResultsBean.RecordsBean> responseBeen) {


                    // InviteContestActivity.start(mContext, responseBeen.get(position).getUserInvitationCode(), matchTeamVS);

                    if (responseBeen != null) {
                        if (responseBeen.get(position).getIsJoined().equals("No")) {
                            MyTeamActivityStart1(mContext,
                                    matchGUID,
                                    responseBeen.get(position).getStatus(),
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
                                        matchGUID,
                                        responseBeen.get(position).getStatus(),
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

                    if (allContest != null) {
                        if (allContest.getData().getStatics().getTotalTeams().equals("0")) {
                            CreateTeamActivityStart(mContext,
                                    matchGUID,
                                    responseBeen.get(position).getContestGUID(),
                                    responseBeen.get(position).getEntryFee(),
                                    responseBeen.get(position).getJoinedTeamsGUID(),
                                    responseBeen.get(position).getOffer1(),
                                    responseBeen.get(position).getOffer2());
                        } else {
                            if (responseBeen.get(position).getIsJoined().equals("No")) {
                                MyTeamActivityStart1(mContext,
                                        matchGUID,
                                        statusID,
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
                                            matchGUID,
                                            statusID,
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
                            }

                        }
                    }
                }
            };

    public void CreateTeamActivityStart(Context context, String MatchGUID) {
        Intent starter = new Intent(context, CreateTeamActivity.class);

        starter.putExtra("MatchGUID", MatchGUID);


        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_TEAM);
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
        starter.putExtra("maxTeamsAllowed", maxTeamsAllowed);
        starter.putExtra("joinedTeamGUIDS", joinedTeamGUIDS);
        if (isSingleEntry) {
            starter.putExtra("teamId", "singleEntry");
        }
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_MY_TEAM);
    }

    public void CreateTeamActivityStart(Context context, String MatchGUID, String contestId, String joiningAmount,
                                        ArrayList<String> joinedTeamGUIDS,
                                        MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer1,
                                        MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer2
    ) {
        Intent starter = new Intent(context, CreateTeamActivity.class);

        starter.putExtra("MatchGUID", MatchGUID);
        starter.putExtra("contestId", contestId);
        starter.putExtra("joiningAmount", joiningAmount);
        starter.putExtra("joinedTeamGUIDS", joinedTeamGUIDS);
        starter.putExtra("offer1", offer1);
        starter.putExtra("offer2", offer2);

        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_TEAM);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_all_joined_contest;
    }

    @Override
    public void init() {

        mContext = this;
        loaderScroller = new LoaderScroller(this);
        mAllContestPresenterImpl = new AllContestPresenterImpl(this, new UserInteractor());
        presenterImpl = new MyMatchesPresenterImpl(this, new UserInteractor());

        all_contest = new ArrayList<>();

        mLinearLayout.setVisibility(View.GONE);

        loader = new Loader(this);

        loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callMatchDetail(matchGUID, statusID);

            }
        });

      /*  ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setNestedScrollingEnabled(true);*/

        layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                // initSpruce();
            }
        };
        //  mRecyclerView.setLayoutManager(layoutManager);

        // Setup refresh listener which triggers new data loading
      /*  swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
//                if (adapter != null) adapter.clear();
                if (scrollListener != null)
                    scrollListener.resetState();
                callTask(statusID, 1);
            }
        });
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,
                R.color.colorPrimary,
                R.color.secondary_tab_color);
*/
        if (loader.getTryAgainView() != null)
            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (scrollListener != null)
                        scrollListener.resetState();
                    callTask(statusID, 1);
                }
            });

        scrollListener = new EndlessRecyclerViewScrollListenerFab(layoutManager) {
            @Override
            public void onLoadMore(int rPage, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.i("loadNextDataFromApi", "loadNextDataFromApi " + rPage);
                //callMatchDetail(matchGUID, statusID);
                callTask(statusID, rPage);
            }

            @Override
            public void onShowFab(boolean show) {

            }
        };

      /*  // Adds the scroll listener to RecyclerView
        mRecyclerView.addOnScrollListener(scrollListener);
        scrollListener.resetState();*/

        if (getIntent().hasExtra("matchGUID")) {

            matchGUID = getIntent().getStringExtra("matchGUID");
            statusID = getIntent().getStringExtra("statusID");

            callMatchDetail(matchGUID, statusID);
        }

       /* adapter = new AllJoinedContestAdapter(R.layout.single_contest_item, mContext, all_contest,
                onWinnerClickCallBack, onContestClickCallBack, onJoinClickCallBack , matchTeamVS);
        mRecyclerView.setAdapter(adapter);*/


    }

    public void callTask(String statusId, int PAGE_NO) {

        if (PAGE_NO == 1) {
//            adapter.clear();
        }

        JoinedContestInput mMatchListInput = new JoinedContestInput();
        mMatchListInput.setPageNo(PAGE_NO);
        mMatchListInput.setPageSize(Constant.PAGE_LIMIT);
        mMatchListInput.setParams(Constant.JOINEDCONTESTS_PARAM);
        mMatchListInput.setGetJoinedMatches("NO");
        mMatchListInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mMatchListInput.setStatus(statusId);
        mMatchListInput.setMatchGUID(matchGUID);
        mMatchListInput.setPrivacy("All");
        mMatchListInput.setMyJoinedContest("Yes");

        //  presenterImpl.actionListing(mMatchListInput);

        presenterImpl.matchContestList(mMatchListInput);


    }


    public void callMatchDetail(String matchGuid, String statusId) {

        MatchDetailInput mMatchDetailInput = new MatchDetailInput();
        mMatchDetailInput.setPrivacy("No");
        mMatchDetailInput.setMatchGUID(matchGuid);
        mMatchDetailInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mMatchDetailInput.setStatus(statusId);
        mMatchDetailInput.setParams(Constant.MATCH_PARAMS);

        mAllContestPresenterImpl.actionMatchdetail(mMatchDetailInput);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        loader.hide();
    }

    @Override
    public void onShowLoading() {
        loader.start();
    }

    @Override
    public void onHideLoading() {
        loader.hide();

     /*   if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);*/
    }

    @Override
    public void onLoadingSuccess(JoinedContestOutput mJoinedContestOutput) {
      /*  if (isLayoutAdded() && mRecyclerView != null) {

            allContest= mJoinedContestOutput;
            adapter.addAllItem(mJoinedContestOutput.getData().getRecords());


            adapter.notifyDataSetChanged();
        }*/
    }

    @Override
    public void onLoadingSuccess(AllContestOutPut mAllContestOutPut) {

    }

    @Override
    public void onLoadingError(String value) {
        loader.error(value);
    }

    @Override
    public void onLoadingNotFound(String value) {
        loader.setNotFoundImage(getContext().getResources().getDrawable(R.drawable.not_found_img));
        loader.dataNotFound(value);
    }

    @Override
    public void onShowScrollLoading() {
        loaderScroller.show();
    }

    @Override
    public void onHideScrollLoading() {
        loaderScroller.hide();
    }

    @Override
    public void onScrollLoadingSuccess(JoinedContestOutput mJoinedContestOutput) {
        loaderScroller.hide();
        //adapter.addAllItem(mJoinedContestOutput.getData().getRecords());
    }

    @Override
    public void onScrollLoadingSuccess(AllContestOutPut mAllContestOutPut) {


    }

    @Override
    public void onScrollLoadingError(String value) {

        loaderScroller.error(value);
    }

    @Override
    public void onScrollLoadingNotFound(String value) {
        loaderScroller.hide();
      /*  if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);*/
    }

    @Override
    public void onShowSnackBar(String message) {
        AppUtils.showToast(mContext, message);
    }

    @Override
    public boolean isLayoutAdded() {
        return true;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onClearLogout() {
        AppSession.getInstance().clearSession();
        LoginScreen.start(mContext);
    }

    @Override
    public void onMyContestLoadingSuccess(MyContestMatchesOutput mJoinedContestOutput) {

    }

    @Override
    public void onMyContestLoadingError(String value) {

    }

    @Override
    public void onMyContestLoadingNotFound(String value) {

    }

    @Override
    public void onMyContestScrollLoadingSuccess(MyContestMatchesOutput mJoinedContestOutput) {

    }

    @Override
    public void onMyContestScrollLoadingError(String value) {

    }

    @Override
    public void onMyContestScrollLoadingNotFound(String value) {

    }

    SingleContestAdapter adapter;

    @Override
    public void onMatchContestSuccess(MatchContestOutPut responseLogin) {
        contestSection.removeAllViews();

        int totalContest = 0;

        for (int i = 0; i < responseLogin.getData().getResults().size(); i++) {

            if (responseLogin.getData().getResults().get(i).getTotalRecords() != 0) {

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

                contest_count.setVisibility(View.GONE);

               /* int remainContest=
                        responseLogin.getData().getResults().get(i).getTotalRecords()-responseLogin.getData().getResults().get(i).getRecords().size();
                if(remainContest!=0){
                    contest_count.setText(remainContest+" "+AppUtils.getStrFromRes(R.string.more_contest));
                }else {
                    contest_count.setVisibility(View.GONE);
                }*/

                ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen.item_offset);
                mRecyclerView.addItemDecoration(itemDecoration);
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


                adapter = new SingleContestAdapter(R.layout.single_contest_item, mContext, responseLogin.getData().getResults().get(i).getRecords(),
                        onWinnerClickCallBack, onContestClickCallBack, onJoinClickCallBack, matchTeamVS);
                mRecyclerView.setAdapter(adapter);
                contestSection.addView(mView);
                totalContest++;
            }
        }

        if (totalContest == 0) {
            loader.dataNotFound(AppUtils.getStrFromRes(R.string.contestNotFound));
        }
    }

    @Override
    public void onMatchContestFailure(String errMsg) {

    }

    @Override
    public void onMatchSuccess(MatchDetailOutPut mMatchDetailOutPut) {

        hideLoading();
        mLinearLayout.setVisibility(View.VISIBLE);


        matchTeamVS = mMatchDetailOutPut.getData().getTeamNameShortLocal()
                + " " + AppUtils.getStrFromRes(R.string.vs) + " " + mMatchDetailOutPut.getData().getTeamNameShortVisitor();
        ctv_timmer_local.setText(mMatchDetailOutPut.getData().getTeamNameShortLocal());
        ctv_timmer_visitor.setText(mMatchDetailOutPut.getData().getTeamNameShortVisitor());
        ViewUtils.setImageUrl(civ_timmer_local, mMatchDetailOutPut.getData().getTeamFlagLocal());
        ViewUtils.setImageUrl(civ_timmer_visitor, mMatchDetailOutPut.getData().getTeamFlagVisitor());

       /* if (adapter != null) {
            adapter.matchTeamVS = matchTeamVS;
        }*/

        teamsVS.setText(mMatchDetailOutPut.getData().getTeamNameShortLocal()
                + " " + AppUtils.getStrFromRes(R.string.vs) + " " + mMatchDetailOutPut.getData().getTeamNameShortVisitor());


        if (mMatchDetailOutPut.getData().getStatus() != null) {
            if (mMatchDetailOutPut.getData().getStatus().equals(Constant.Pending)) {
                setTime(mMatchDetailOutPut.getData().getMatchStartDateTime(), mMatchDetailOutPut.getData().getMatchDate(),
                        mMatchDetailOutPut.getData().getMatchTime(), mMatchDetailOutPut.getData().getCurrentDateTime());
            } else if (mMatchDetailOutPut.getData().getStatus().equals(Constant.Running)) {
                ctv_timer.setText(mMatchDetailOutPut.getData().getStatus());
                //ctv_timer.setTextColor(getResources().getColor(R.color.yellow));
            } else if (mMatchDetailOutPut.getData().getStatus().equals(Constant.Completed)) {
                ctv_timer.setText(mMatchDetailOutPut.getData().getStatus());
                //  ctv_timer.setTextColor(getResources().getColor(R.color.green));

            }
        }
        callTask(statusID, 1);
    }

    @Override
    public void onMatchFailure(String errMsg) {
        hideLoading();
        loader.error(errMsg);
    }


    CountDownTimer countDownTimer;

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

    private void showPreview(final List<WinnersRankBean> bean,
                             final String totalWinngingAmmount, final String winningType) {
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

}
