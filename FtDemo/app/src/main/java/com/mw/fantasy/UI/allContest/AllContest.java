package com.mw.fantasy.UI.allContest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.contestDetailLeaderBoard.ContestLeaderBoard;
import com.mw.fantasy.UI.createTeam.CreateTeamActivity;
import com.mw.fantasy.UI.filter.BottomSheetFilterFragment;
import com.mw.fantasy.UI.filter.ContestSearchResultFilters;
import com.mw.fantasy.UI.inviteContest.InviteContestActivity;
import com.mw.fantasy.UI.loginRagisterModule.LoginScreen;
import com.mw.fantasy.UI.matchContest.MatchContestActivity;
import com.mw.fantasy.UI.myTeams.MyTeamsActivity;
import com.mw.fantasy.UI.winnings.WinnersCallback;
import com.mw.fantasy.UI.winnings.WinnersRankBean;
import com.mw.fantasy.UI.winnings.WinningsFragment;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.base.Loader;
import com.mw.fantasy.base.LoaderScroller;
import com.mw.fantasy.beanInput.MatchContestInput;
import com.mw.fantasy.beanInput.MatchDetailInput;
import com.mw.fantasy.beanOutput.AllContestOutPut;
import com.mw.fantasy.beanOutput.MatchContestOutPut;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.beanOutput.ResponseFilter;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.EndlessRecyclerViewScrollListenerFab;
import com.mw.fantasy.utility.ItemOffsetDecoration;
import com.mw.fantasy.utility.OnItemClickListener;
import com.mw.fantasy.utility.TimeUtils;
import com.mw.fantasy.utility.ViewUtils;
import com.rey.material.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

public class AllContest extends BaseActivity implements AllContestView {


    @BindView(R.id.ctv_timmer_local)
    CustomTextView ctv_timmer_local;

    @BindView(R.id.ctv_timmer_visitor)
    CustomTextView ctv_timmer_visitor;

    @BindView(R.id.civ_timmer_local)
    CustomImageView civ_timmer_local;

    @BindView(R.id.civ_timmer_visitor)
    CustomImageView civ_timmer_visitor;


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.title)
    CustomTextView title;
    @BindView(R.id.teamsVS)
    CustomTextView teamsVS;
    @BindView(R.id.ctv_full_time)
    CustomTextView ctv_timer;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.winnings)
    CustomTextView winnings;

    @BindView(R.id.teams)
    CustomTextView teams;

    @BindView(R.id.ll_main)
    LinearLayout mLinearLayout;

    @BindView(R.id.winners)
    CustomTextView winners;

    @BindView(R.id.entry)
    CustomTextView entry;
    private String fixture_contest_type;
    private String contest_type;

    @OnClick(R.id.back)
    void onBackClick() {
        clearfilter();
        onBackPressed();
    }

    @OnClick(R.id.menu)
    void onMenuFilterClick() {
        showFilter();
    }


    String EntryStartFrom;
    String EntryEndTo;

    String WinningStartFrom;
    String WinningEndTo;

    String ContestType;

    String ContestSizeStartFrom;
    String ContestSizeEndTo;

    int flagFilter = 0;
    String matchTeamVS = "";

    private EndlessRecyclerViewScrollListenerFab scrollListener;
    private LinearLayoutManager layoutManager;

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private Loader loader;
    private LoaderScroller loaderScroller;

    String statusID = Constant.Pending;
    String matchGUID;

    public AllContestAdapter adapter;

    AllContestOutPut allContest;

    AllContestPresenterImpl mAllContestPresenterImpl;

    List<AllContestOutPut.DataBean.RecordsBean> all_contest;

    public static void start(Context context, String match_id, String statusID) {
        Intent starter = new Intent(context, AllContest.class);
        starter.putExtra("matchGUID", match_id);
        starter.putExtra("statusID", statusID);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);

    }

    public static void start(Context context, String match_id, String fixture_contest_type, String statusID) {
        Intent starter = new Intent(context, AllContest.class);

        starter.putExtra("matchGUID", match_id);
        starter.putExtra("fixture_contest_type", fixture_contest_type);
        starter.putExtra("statusID", statusID);

        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    private OnItemClickListener.OnItemClickCallback onWinnerClickCallBack = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

            List<AllContestOutPut.DataBean.RecordsBean.CustomizeWinningBean> customizeWin = adapter.getItem(position).getCustomizeWinning();
            List<WinnersRankBean> rankList = new ArrayList<>();
            for (int i = 0; i < customizeWin.size(); i++) {

                WinnersRankBean mWinnersRankBean = new WinnersRankBean();
                mWinnersRankBean.setFrom(customizeWin.get(i).getFrom());
                mWinnersRankBean.setTo(customizeWin.get(i).getTo());
                mWinnersRankBean.setPercent(customizeWin.get(i).getPercent());
                mWinnersRankBean.setWinningAmount(customizeWin.get(i).getWinningAmount());

                if (adapter.getItem(position).getSmartPool().equalsIgnoreCase("Yes")) {
                    mWinnersRankBean.setProductName(customizeWin.get(i).getProductName());
                    mWinnersRankBean.setProductUrl(customizeWin.get(i).getProductUrl());
                } else {
                    mWinnersRankBean.setPercent(customizeWin.get(i).getPercent());
                    mWinnersRankBean.setWinningAmount(customizeWin.get(i).getWinningAmount());

                }


                rankList.add(i, mWinnersRankBean);

            }
            if (adapter.getItem(position).getSmartPool().equalsIgnoreCase("Yes")) {
                showPreview(rankList, null, adapter.getItem(position).getWinningType());
            } else {
                showPreview(rankList, adapter.getItem(position).getWinningAmount(), adapter.getItem(position).getWinningType());
            }

        }
    };

    private OnItemClickListener.OnItemClickCallback onContestClickCallBack = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            ContestLeaderBoard.start(mContext, matchGUID, adapter.getItem(position).getContestGUID(), statusID);
        }
    };

    private OnItemClickListener.OnItemClickCallback onJoinClickCallBack = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

            if (allContest != null) {

                if (allContest.getData().getStatics().getTotalTeams().equals("0")) {
                    CreateTeamActivityStart(mContext,
                            matchGUID,
                            adapter.getItem(position).getContestGUID(), adapter.getItem(position).getEntryFee()
                            , adapter.getItem(position).getCashBonusContribution(),
                            adapter.getItem(position).getJoinedTeamsGUID(),
                            adapter.getItem(position).getOffer1(),
                            adapter.getItem(position).getOffer2());
                } else {
                    if (adapter.getItem(position).getIsJoined().equals("No")) {
                        MyTeamActivityStart(mContext,
                                matchGUID,
                                statusID, adapter.getItem(position).getContestGUID(),
                                adapter.getItem(position).getEntryFee(),
                                teamsVS.getText().toString(),
                                adapter.getItem(position).getCashBonusContribution(),
                                adapter.getItem(position).getJoinedTeamsGUID(),
                                adapter.getItem(position).getOffer1(),
                                adapter.getItem(position).getOffer2(),
                                adapter.getItem(position).getEntryType().equals("Single"),
                                adapter.getItem(position).getUserJoinLimit() - adapter.getItem(position).getUserTeamDetails().size()
                        );
                    } else {
                        if (adapter.getItem(position).getEntryType().equals("Multiple")) {
                            MyTeamActivityStart(mContext,
                                    matchGUID,
                                    statusID, adapter.getItem(position).getContestGUID(),
                                    adapter.getItem(position).getEntryFee(),
                                    teamsVS.getText().toString(),
                                    adapter.getItem(position).getCashBonusContribution(),
                                    adapter.getItem(position).getJoinedTeamsGUID(),
                                    adapter.getItem(position).getOffer1(),
                                    adapter.getItem(position).getOffer2(),
                                    adapter.getItem(position).getEntryType().equals("Single"),
                                    adapter.getItem(position).getUserJoinLimit() - adapter.getItem(position).getUserTeamDetails().size()
                            );
                        } else {
                            InviteContestActivity.start(mContext, adapter.getItem(position).getUserInvitationCode(), matchTeamVS);
                        }
                    }

                }
            }
        }
    };


    public void CreateTeamActivityStart(Context context, String MatchGUID, String contestId, String joiningAmount, String cashBonusContribution,
                                        ArrayList<String> joinedTeamGUIDS,
                                        MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer1,
                                        MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer2) {
        Intent starter = new Intent(context, CreateTeamActivity.class);
        starter.putExtra("MatchGUID", MatchGUID);
        starter.putExtra("contestId", contestId);
        starter.putExtra("joiningAmount", joiningAmount);
        starter.putExtra("cashBonusContribution", cashBonusContribution);
        starter.putExtra("joinedTeamGUIDS", joinedTeamGUIDS);
        starter.putExtra("offer1", offer1);
        starter.putExtra("offer2", offer2);
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_TEAM);
    }

    public void MyTeamActivityStart(Context context, String matchId, String statusId, String contestId, String joiningAmount, String teamsVSStr, String cashBonusContribution,
                                    ArrayList<String> joinedTeamGUIDS,
                                    MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer1,
                                    MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer2,
                                    boolean isSingleEntry,
                                    int maxTeamsAllowed) {
        Intent starter = MyTeamsActivity.getIntent(context);
        starter.putExtra("contestId", contestId);
        starter.putExtra("matchId", matchId);
        starter.putExtra("statusId", statusId);
        starter.putExtra("joiningAmount", joiningAmount);
        starter.putExtra("teamsVSStr", teamsVSStr);
        starter.putExtra("cashBonusContribution", cashBonusContribution);
        starter.putExtra("join", "0");

        starter.putExtra("joinedTeamGUIDS", joinedTeamGUIDS);
        starter.putExtra("offer1", offer1);
        starter.putExtra("offer2", offer2);
        starter.putExtra("maxTeamsAllowed", maxTeamsAllowed);

        if (isSingleEntry) {
            starter.putExtra("teamId", "singleEntry");
        }
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_MY_TEAM);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_all_contest;
    }

    @Override
    public void init() {

        mContext = this;
        loaderScroller = new LoaderScroller(this);
        mAllContestPresenterImpl = new AllContestPresenterImpl(this, new UserInteractor());

        all_contest = new ArrayList<>();

        mLinearLayout.setVisibility(View.GONE);

        loader = new Loader(this);
        loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callMatchDetail(matchGUID, statusID);

            }
        });

        getFilterValue();

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setNestedScrollingEnabled(true);

        layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                // initSpruce();
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);

        // Setup refresh listener which triggers new data loading
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                if (adapter != null) adapter.clear();
                if (scrollListener != null)
                    scrollListener.resetState();
                callTask(Constant.Pending, 1);
            }
        });
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,
                R.color.colorPrimary,
                R.color.secondary_tab_color);

        if (loader.getTryAgainView() != null)
            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (scrollListener != null)
                        scrollListener.resetState();
                    callTask(Constant.Pending, 1);
                }
            });

        scrollListener = new EndlessRecyclerViewScrollListenerFab(layoutManager) {
            @Override
            public void onLoadMore(int rPage, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.i("loadNextDataFromApi", "loadNextDataFromApi " + rPage);
                // callMatchDetail(matchGUID, statusID);

                callTask(Constant.Pending, rPage);
            }

            @Override
            public void onShowFab(boolean show) {

            }
        };

        // Adds the scroll listener to RecyclerView
        mRecyclerView.addOnScrollListener(scrollListener);
        scrollListener.resetState();


        if (getIntent().hasExtra("matchGUID")) {

            matchGUID = getIntent().getStringExtra("matchGUID");
            statusID = getIntent().getStringExtra("statusID");

            callMatchDetail(matchGUID, statusID);
        }


        fixture_contest_type = getIntent().getStringExtra("fixture_contest_type");

        if (fixture_contest_type != null) {
            if (!fixture_contest_type.equals("")) {
                if (fixture_contest_type.equals("Hot Contest")) {
                    contest_type = "Hot";
                } else if (fixture_contest_type.equals("Mega Contest")) {
                    contest_type = "Mega";
                } else if (fixture_contest_type.equals("Practice Contest")) {
                    contest_type = "Practice";
                } else if (fixture_contest_type.equals("Contests for Champions")) {
                    contest_type = "Champion";
                } else if (fixture_contest_type.equals("Winner Takes All")) {
                    contest_type = "Winner Takes All";
                } else if (fixture_contest_type.equals("Only For Beginners")) {
                    contest_type = "Only For Beginners";
                } else if (fixture_contest_type.equals("Head To Head Contest")) {
                    contest_type = "Head to Head";
                } else if (fixture_contest_type.equals("More Contest")) {
                    contest_type = "More";
                } else if (fixture_contest_type.equals("Infinity Pool")) {
                    contest_type = "Infinity Pool";
                }
            } else
                contest_type = null;
        } else
            contest_type = null;

        adapter = new AllContestAdapter(R.layout.single_contest_item, mContext, all_contest,
                onWinnerClickCallBack, onContestClickCallBack, onJoinClickCallBack, matchTeamVS);
        mRecyclerView.setAdapter(adapter);

    }

    public void callTask(String statusId, int PAGE_NO) {

        if (PAGE_NO == 1) {
            adapter.clear();
        }

        MatchContestInput mMatchContestInput = new MatchContestInput();
        mMatchContestInput.setContainTypeFilter(AppSession.getInstance().getSelectedContainTypeFilter());
        mMatchContestInput.setPrivacy("No");
        mMatchContestInput.setContestList("Yes");
        mMatchContestInput.setMatchGUID(matchGUID);
        mMatchContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mMatchContestInput.setStatus(statusId);
        mMatchContestInput.setParams(Constant.CONTEST_PARAM);
        mMatchContestInput.setPageNo(PAGE_NO);
        mMatchContestInput.setPageSize(Constant.PAGE_LIMIT);
        mMatchContestInput.setEntryStartFrom(EntryStartFrom);
        mMatchContestInput.setEntryEndTo(EntryEndTo);
        mMatchContestInput.setWinningStartFrom(WinningStartFrom);
        mMatchContestInput.setWinningEndTo(WinningEndTo);
        if (contest_type != null && !contest_type.trim().isEmpty()) {
            mMatchContestInput.setContestType(contest_type);
        }
        if (ContestType != null && !ContestType.trim().isEmpty()) {
            mMatchContestInput.setEntryType(ContestType);
        }

        mMatchContestInput.setContestSizeStartFrom(ContestSizeStartFrom);
        mMatchContestInput.setContestSizeEndTo(ContestSizeEndTo);
//        mMatchContestInput.setContestFull("No");
        mMatchContestInput.setOrderBy("WinningAmount");
        mMatchContestInput.setSequence("DESC");

        mAllContestPresenterImpl.matchContestList(mMatchContestInput);


        // Log.d("mMatchContestInput",AppUtils.gsonToJSON(responseFilter));


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

        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadingSuccess(AllContestOutPut mAllContestOutPut) {
        onHideLoading();
        if (isLayoutAdded() && mRecyclerView != null) {
            allContest = mAllContestOutPut;
            adapter.addAllItem(mAllContestOutPut.getData().getRecords());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadingError(String value) {
        loader.error(value);
    }

    @Override
    public void onLoadingNotFound(String value) {
        loader.setNotFoundImage(getContext().getResources().getDrawable(R.drawable.not_found_img));
        if (flagFilter == 1) {
            loader.dataNotFound("Please try another filter criteria.");
        } else {
            loader.dataNotFound(value);
        }
    }

    @Override
    public void onShowScrollLoading() {
        loaderScroller.show();
    }

    @Override
    public void onHideScrollLoading() {
        loader.hide();
        loaderScroller.hide();
    }

    @Override
    public void onScrollLoadingSuccess(AllContestOutPut mAllContestOutPut) {
        loader.hide();
        loaderScroller.hide();
        adapter.addAllItem(mAllContestOutPut.getData().getRecords());
    }

    @Override
    public void onScrollLoadingError(String value) {
        loader.hide();
        loaderScroller.hide();
        loaderScroller.error(value);
    }

    @Override
    public void onScrollLoadingNotFound(String value) {
        loaderScroller.hide();
        loader.hide();
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
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
    public void onMatchSuccess(MatchDetailOutPut mMatchDetailOutPut) {

        hideLoading();
        mLinearLayout.setVisibility(View.VISIBLE);
        matchTeamVS = mMatchDetailOutPut.getData().getTeamNameShortLocal() + " " + AppUtils.getStrFromRes(R.string.vs) + " " + mMatchDetailOutPut.getData().getTeamNameShortVisitor();

        if (adapter != null) {
            adapter.matchTeamVS = matchTeamVS;
        }


        ctv_timmer_local.setText(mMatchDetailOutPut.getData().getTeamNameShortLocal());
        ctv_timmer_visitor.setText(mMatchDetailOutPut.getData().getTeamNameShortVisitor());
        ViewUtils.setImageUrl(civ_timmer_local, mMatchDetailOutPut.getData().getTeamFlagLocal());
        ViewUtils.setImageUrl(civ_timmer_visitor, mMatchDetailOutPut.getData().getTeamFlagVisitor());

        //teamsVS.setText(mMatchDetailOutPut.getData().getTeamNameShortLocal() + " " + AppUtils.getStrFromRes(R.string.vs) + " " + mMatchDetailOutPut.getData().getTeamNameShortVisitor());
        setTime(mMatchDetailOutPut.getData().getMatchStartDateTime(), mMatchDetailOutPut.getData().getMatchDate(),
                mMatchDetailOutPut.getData().getMatchTime(), mMatchDetailOutPut.getData().getCurrentDateTime());

        callTask(Constant.Pending, 1);


    }

    @Override
    public void onMatchFailure(String errMsg) {
        hideLoading();
        loader.error(errMsg);
    }

    CountDownTimer countDownTimer;

    public void setTime(String matchDateTime, final String matchDate, final String matchTime, String currentTime) {
        // ctv_timer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_time, 0, 0, 0);
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

    private void clearfilter() {
        EntryStartFrom = "";
        EntryEndTo = "";

        WinningStartFrom = "";
        WinningEndTo = "";

        ContestType = "";

        ContestSizeStartFrom = "";
        ContestSizeEndTo = "";

        AppSession.getInstance().clearFilterSession();

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

    private void showFilter() {
        final BottomSheetFilterFragment dialogFragment = new BottomSheetFilterFragment();
        dialogFragment.setUpdateable(new ContestSearchResultFilters() {
            @Override
            public void search() {

                Intent intent = new Intent(MatchContestActivity.class.getSimpleName());
                intent.putExtra("KEY", "REFRESH");
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                getFilterValue();
                callTask(Constant.Pending, 1);
            }

            @Override
            public void reSetFilter() {
                EntryStartFrom = "";
                EntryEndTo = "";

                WinningStartFrom = "";
                WinningEndTo = "";

                ContestType = "";

                ContestSizeStartFrom = "";
                ContestSizeEndTo = "";
            }

            @Override
            public Context getContext() {
                return mContext;
            }
        });
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());

    }

    void getFilterValue() {
        ResponseFilter responseFilter = AppSession.getInstance().getFilterSession();

        for (int i = 0; i < responseFilter.getContets_type().size(); i++) {
            if (responseFilter.getContets_type().get(i).isSelected()) {
                flagFilter = 1;
                ContestType = responseFilter.getContets_type().get(i).getValue();
            }
        }
        for (int i = 0; i < responseFilter.getWin().size(); i++) {
            if (responseFilter.getWin().get(i).isSelected()) {
                flagFilter = 1;
                WinningStartFrom = responseFilter.getWin().get(i).getStartFrom();
                WinningEndTo = responseFilter.getWin().get(i).getEndTo();
            }
        }
        for (int i = 0; i < responseFilter.getPay().size(); i++) {
            if (responseFilter.getPay().get(i).isSelected()) {
                flagFilter = 1;
                EntryStartFrom = responseFilter.getPay().get(i).getStartFrom();
                EntryEndTo = responseFilter.getPay().get(i).getEndTo();
            }
        }
        for (int i = 0; i < responseFilter.getSize().size(); i++) {
            if (responseFilter.getSize().get(i).isSelected()) {
                flagFilter = 1;
                ContestSizeStartFrom = responseFilter.getSize().get(i).getStartFrom();
                if (ContestSizeStartFrom.equals("2")) {
                    ContestSizeEndTo = responseFilter.getSize().get(i).getStartFrom();
                } else {
                    ContestSizeEndTo = responseFilter.getSize().get(i).getEndTo();
                }

            }
        }
    }
}
