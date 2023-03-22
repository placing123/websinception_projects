package com.websinception.megastar.UI.auction.auctionContestListing.myContest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.SeriesInfoUtil;
import com.websinception.megastar.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment;
import com.websinception.megastar.UI.auction.playerpoint.AuctionBestTeamActivity;
import com.websinception.megastar.UI.joinContest.JoinContestActivity;
import com.websinception.megastar.UI.myAccount.MyAccountDialogActivity;
import com.websinception.megastar.UI.previewTeam.BottomSheetPreviewFragment;
import com.websinception.megastar.UI.previewTeam.PlayerPreviewCallback;
import com.websinception.megastar.UI.previewTeam.PlayerRecord;
import com.websinception.megastar.UI.winnings.WinnersCallback;
import com.websinception.megastar.UI.winnings.WinnersRankBean;
import com.websinception.megastar.UI.winnings.WinningsFragment;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanInput.DreamTeamInput;
import com.websinception.megastar.beanInput.GetSeriesAuctionContestInput;
import com.websinception.megastar.beanOutput.DreamTeamOutput;
import com.websinception.megastar.beanOutput.GetSeriesAuctionContestOutput;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.AlertDialog;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.EndlessRecyclerViewScrollListenerFab;
import com.websinception.megastar.utility.NetworkUtils;
import com.websinception.megastar.utility.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

import static com.websinception.megastar.UI.auction.auctionHome.AuctionHomeFragment.JOINED;
import static com.websinception.megastar.UI.auction.auctionListingHome.AuctionListingHomeFragment.ALL;


public class MyContestPendingLiveListingActivity extends BaseActivity {


    private AlertDialog mAlertDialog;

    @Override
    public int getLayout() {
        return R.layout.activity_my_contest_pending_live_listing;
    }


    public static final int REQUEST_CODE_JOIN = 121;
    private Context mContext;
    private String roundID, seriesName, seriesDeadLine, contestType, matchGUID;
    private int type, seriesStatus, currentPageNo = 1, flag;
    private AuctionMyContestListAdapter mListAdapter;
    private IUserInteractor mInteractor;
    private ArrayList<GetSeriesAuctionContestOutput.DataBean.RecordsBean> mRecordsBeanArrayList = new ArrayList<>();
    private boolean allContestListed = false;
    private LinearLayoutManager layoutManager;
    private Call<GetSeriesAuctionContestOutput> seriesAuctionContestCall;
    private ProgressDialog mProgressDialog;
    private EndlessRecyclerViewScrollListenerFab listenerFab;
    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private boolean loading = true;
    private String selectedContestStatus = null;


    public static void start(Context context, int flag, String roundID, int type, int seriesStatus, String seriesName, String seriesDeadLine) {
        Intent starter = new Intent(context, MyContestPendingLiveListingActivity.class);
        starter.putExtra("flag", flag);
        starter.putExtra("type", type);
        starter.putExtra("roundID", roundID);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("contestType", "");
        context.startActivity(starter);
    }

    public static void start(Context context, int flag, String roundID, int type, int seriesStatus, String seriesName, String seriesDeadLine, String contestType) {
        Intent starter = new Intent(context, MyContestPendingLiveListingActivity.class);
        starter.putExtra("flag", flag);
        starter.putExtra("type", type);
        starter.putExtra("roundID", roundID);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("contestType", contestType);
        context.startActivity(starter);
    }

    @OnClick(R.id.tv_tryAgn)
    public void tryAgainBtnClick() {
        allContestListed = false;
        getData(1);
    }

    @OnClick(R.id.back)
    public void BackBtnClick() {
        onBackPressed();
    }

    @OnClick(R.id.wallet)
    void walletBtnClick() {
        MyAccountDialogActivity.start(this, true);
    }

    @OnClick(R.id.ctv_best_team)
    void bestTeamBtnClick() {
        if (flag == 1) {
            if (mRecordsBeanArrayList.size() != 0) {
                AuctionBestTeamActivity.start(flag, mContext, mRecordsBeanArrayList.get(0).getSeriesID(), flag == 1 ? roundID : matchGUID);
            }

        } else {
            apiCallDownloadteams(mRecordsBeanArrayList.get(0).getSeriesGUID(), matchGUID);
        }

    }


    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.tv_tryAgn)
    CustomTextView mCustomTextViewTryAgainBtn;


    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;


    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_contest)
    RecyclerView mRecyclerView;


    @BindView(R.id.ctv_best_team_root)
    FrameLayout mFrameLayoutBeatTeamBtnRoot;

    @BindView(R.id.ctv_error_msg)
    CustomTextView mCtvErrorMsg;

    @BindView(R.id.ll_error_root)
    LinearLayout mLinearLayoutErrorRoot;


    @BindView(R.id.ll_auction_type_root)
    LinearLayout mLinearLayoutContestTypeRoot;

    @BindView(R.id.ctv_contest_pending)
    CustomTextView mCtvContestTypePending;

    @BindView(R.id.ctv_contest_live)
    CustomTextView mCtvContestTypeLive;

    @BindView(R.id.ctv_contest_completed)
    CustomTextView mCtvContestTypeCompleted;


    @OnClick({R.id.ctv_contest_pending, R.id.ctv_contest_live, R.id.ctv_contest_completed})
    void contestTypeBtnClick(View view) {
        AppUtils.clickVibrate(mContext);
        mCtvContestTypePending.setBackgroundResource(R.drawable.contest_type_btn_left_inactive);
        mCtvContestTypeLive.setBackgroundResource(R.drawable.contest_type_btn_inactive);
        mCtvContestTypeCompleted.setBackgroundResource(R.drawable.contest_type_btn_right_inactive);


        switch (view.getId()) {
            case R.id.ctv_contest_pending:
                mCtvContestTypePending.setBackgroundResource(R.drawable.contest_type_btn_left_active);
                selectedContestStatus = "Pending";

                break;
            case R.id.ctv_contest_live:
                mCtvContestTypeLive.setBackgroundResource(R.drawable.contest_type_btn_active);
                selectedContestStatus = "Running";

                break;
            case R.id.ctv_contest_completed:
                mCtvContestTypeCompleted.setBackgroundResource(R.drawable.contest_type_btn_right_active);
                selectedContestStatus = "Completed";
                break;
        }

        allContestListed = false;
        loading = true;
        getData(1);
    }






/*    @BindView(R.id.stepper)
    StepperView stepper;*/


//    ArrayList<String> list = new ArrayList<>();


    @Override
    public void init() {
        mContext = this;
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(mContext);

        flag = getIntent().getExtras().getInt("flag");
        type = getIntent().getExtras().getInt("type");
        if (flag == 1) {
            //auction
            roundID = getIntent().getExtras().getString("roundID");
        } else {
            matchGUID = getIntent().getExtras().getString("roundID");
        }
        seriesStatus = getIntent().getExtras().getInt("seriesStatus");
        seriesName = getIntent().getExtras().getString("seriesName");
        seriesDeadLine = getIntent().getExtras().getString("seriesDeadLine");
        contestType = getIntent().getExtras().getString("contestType");
        if (type == JOINED) {
            if (seriesStatus == AuctionSeriesListingFragment.FIXTURE) {
                if (flag == 1) {
                    mLinearLayoutContestTypeRoot.setVisibility(View.VISIBLE);
                    selectedContestStatus = "Pending";
                } else {
                    mLinearLayoutContestTypeRoot.setVisibility(View.GONE);
                }

            } else {
                mLinearLayoutContestTypeRoot.setVisibility(View.GONE);
            }

        } else {
            mLinearLayoutContestTypeRoot.setVisibility(View.GONE);
        }

        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
//        list.add("200");
//        list.add("300");
//        list.add("400");
//        list.add("500");
//        list.add("600");
//        list.add("700");
//        list.add("800");

//        stepper.addList(list);


//        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListenerFab(layoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                getData(currentPageNo);
//            }
//
//            @Override
//            public void onShowFab(boolean show) {
//            }
//        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                            getData(currentPageNo);
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });
        mListAdapter = new AuctionMyContestListAdapter(this,
                mRecordsBeanArrayList,
                flag,
                type,
                seriesStatus,
                seriesName,
                roundID,
                matchGUID,
                onWinnerClickCallBack, seriesDeadLine);
        mRecyclerView.setAdapter(mListAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                allContestListed = false;
                loading = true;
                getData(1);
            }
        });


        getData(1);
        new SeriesInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName, seriesDeadLine, seriesStatus).start();
    }


    private void getData(int pageNo) {
        if (flag == 1) {
            getDataForAuction(pageNo);
        } else {
            getDataForDraft(pageNo);
        }
    }

    private void getDataForAuction(final int pageNo) {
        if (seriesAuctionContestCall != null || allContestListed) {
            return;
        }
        mLinearLayoutErrorRoot.setVisibility(View.GONE);
        if (pageNo == 1) {
            mFrameLayoutBeatTeamBtnRoot.setVisibility(View.GONE);
            currentPageNo = 1;
            mRecordsBeanArrayList.clear();
            mListAdapter.notifyDataSetChanged();
        }
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            final GetSeriesAuctionContestInput getSeriesAuctionContestInput = new GetSeriesAuctionContestInput();
            if (type == ALL) {
                getSeriesAuctionContestInput.setRoundID(roundID);
                getSeriesAuctionContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
                getSeriesAuctionContestInput.setPageNo(pageNo);
                getSeriesAuctionContestInput.setPageSize(15);
                getSeriesAuctionContestInput.setParams("LeagueJoinDateTime,GameType,Privacy,IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,IsJoined,Status,ContestFormat,ContestType,CustomizeWinning,TotalJoined,UserInvitationCode,TeamNameLocal,TeamNameVisitor,IsConfirm,CashBonusContribution,GameTimeLive,IsAuctionFinalTeamSubmitted,AuctionStatus,LeagueJoinDateTimeUTC");
                getSeriesAuctionContestInput.setContestFull("No");
                getSeriesAuctionContestInput.setStatus("1,2,5");
                getSeriesAuctionContestInput.setStatusID("1");
                getSeriesAuctionContestInput.setPrivacy("No");
                getSeriesAuctionContestInput.setAuctionStatus("Pending");
                getSeriesAuctionContestInput.setLeagueType("Auction");
                getSeriesAuctionContestInput.setIsSeriesStarted("Yes");
                getSeriesAuctionContestInput.setTotalJoinedByRound("Yes");
                if (!contestType.isEmpty()) {
                    getSeriesAuctionContestInput.setContestType(contestType);
                }
            } else {
                getSeriesAuctionContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
                getSeriesAuctionContestInput.setRoundID(roundID);
                getSeriesAuctionContestInput.setParams("ContestID,LeagueJoinDateTime,GameType,Privacy,IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,Status,TotalJoined,CustomizeWinning,CashBonusContribution,IsJoined,AuctionStatus,LeagueJoinDateTimeUTC");
                getSeriesAuctionContestInput.setPageNo(pageNo);
                getSeriesAuctionContestInput.setPageSize(15);
                getSeriesAuctionContestInput.setJoinedContestStatusID("Yes");
                getSeriesAuctionContestInput.setStatus("Pending");
                getSeriesAuctionContestInput.setMyJoinedContest("Yes");
                getSeriesAuctionContestInput.setPrivacy("All");
                getSeriesAuctionContestInput.setLeagueType("Auction");
                getSeriesAuctionContestInput.setIsSeriesStarted("Yes");
                getSeriesAuctionContestInput.setTotalJoinedByRound("Yes");
                if (seriesStatus == AuctionSeriesListingFragment.COMPLETED) {
                    getSeriesAuctionContestInput.setMyStats("Yes");
                } else if (seriesStatus == AuctionSeriesListingFragment.FIXTURE) {
                    if (selectedContestStatus != null) {
                        getSeriesAuctionContestInput.setAuctionStatus(selectedContestStatus);
                    }
                }
                if (!contestType.isEmpty()) {
                    getSeriesAuctionContestInput.setContestType(contestType);
                }



            }
            seriesAuctionContestCall = mInteractor.getSeriesAuctionContest(getSeriesAuctionContestInput, new IUserInteractor.OnGetSeriesAuctionContestListener() {
                @Override
                public void onSuccess(GetSeriesAuctionContestOutput getSeriesAuctionContestOutput) {
                    mProgressDialog.dismiss();
                    if (getSeriesAuctionContestOutput.getData().getTotalRecords() == 0) {
                        allContestListed = true;
                        switch (selectedContestStatus) {
                            case "Pending":
                                setError("There is no pending auction for this series on your list. Please check live or completed tabs.", false);
                                break;
                            case "Running":
                                setError("There is no live auction for this series on your list. Please check pending or completed tabs.", false);
                                break;
                            case "Completed":
                                setError("There is no completed auction for this series on your list. Please check pending or live tabs.", false);
                                break;
                        }

                    } else {
                        currentPageNo++;

                        if (getSeriesAuctionContestOutput.getData().getRecords().size() < 15) {
                            loading = false;
                        }

                        for (GetSeriesAuctionContestOutput.DataBean.RecordsBean record : getSeriesAuctionContestOutput.getData().getRecords()) {
                            mRecordsBeanArrayList.add(record);
                        }
                    }
                    mListAdapter.notifyDataSetChanged();
                    seriesAuctionContestCall.cancel();
                    seriesAuctionContestCall = null;
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (type != ALL && seriesStatus == AuctionSeriesListingFragment.COMPLETED) {
                        mFrameLayoutBeatTeamBtnRoot.setVisibility(View.VISIBLE);
                    } else {
                        mFrameLayoutBeatTeamBtnRoot.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    setError(errorMsg, true);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            mProgressDialog.dismiss();
            mSwipeRefreshLayout.setRefreshing(false);

            if (mRecordsBeanArrayList.size() == 0) {
                setError(AppUtils.getStrFromRes(R.string.network_error), true);
            } else {
                //  AppUtils.showToast(mContext,AppUtils.getStrFromRes(R.string.network_error));
            }
        }
    }


    private void getDataForDraft(final int pageNo) {
        if (seriesAuctionContestCall != null || allContestListed) {
            return;
        }
        mLinearLayoutErrorRoot.setVisibility(View.GONE);
        if (pageNo == 1) {
            mFrameLayoutBeatTeamBtnRoot.setVisibility(View.GONE);
            currentPageNo = 1;
            mRecordsBeanArrayList.clear();
            mListAdapter.notifyDataSetChanged();
        }
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            final GetSeriesAuctionContestInput getSeriesAuctionContestInput = new GetSeriesAuctionContestInput();
            if (type == ALL) {
                getSeriesAuctionContestInput.setMatchGUID(matchGUID);
                getSeriesAuctionContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
                getSeriesAuctionContestInput.setPageNo(pageNo);
                getSeriesAuctionContestInput.setPageSize(15);
                getSeriesAuctionContestInput.setParams("LeagueJoinDateTime,GameType,Privacy,IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,IsJoined,Status,ContestFormat,ContestType,CustomizeWinning,TotalJoined,UserInvitationCode,TeamNameLocal,TeamNameVisitor,IsConfirm,CashBonusContribution,GameTimeLive,IsAuctionFinalTeamSubmitted,AuctionStatus,DraftTeamPlayerLimit,DraftPlayerSelectionCriteria,LeagueJoinDateTimeUTC,ContestGUID");
                getSeriesAuctionContestInput.setContestFull("No");
                getSeriesAuctionContestInput.setStatus("1,2,5");
                getSeriesAuctionContestInput.setStatusID("1");
                getSeriesAuctionContestInput.setPrivacy("No");
                getSeriesAuctionContestInput.setAuctionStatus("Pending");
                getSeriesAuctionContestInput.setLeagueType("Draft");
                getSeriesAuctionContestInput.setIsSeriesStarted("Yes");
                getSeriesAuctionContestInput.setTotalJoinedByRound("Yes");
                getSeriesAuctionContestInput.setIsAssistanceCreated("Yes");
                if (!contestType.isEmpty()) {
                    getSeriesAuctionContestInput.setContestType(contestType);
                }
            } else {
                getSeriesAuctionContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
                getSeriesAuctionContestInput.setMatchGUID(matchGUID);
                getSeriesAuctionContestInput.setParams("ContestID,LeagueJoinDateTime,GameType,Privacy,IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,Status,TotalJoined,CustomizeWinning,CashBonusContribution,IsJoined,AuctionStatus,DraftTeamPlayerLimit,DraftPlayerSelectionCriteria,LeagueJoinDateTimeUTC,ContestGUID,SeriesGUID");
                getSeriesAuctionContestInput.setPageNo(pageNo);
                getSeriesAuctionContestInput.setPageSize(15);
                getSeriesAuctionContestInput.setJoinedContestStatusID("Yes");
                getSeriesAuctionContestInput.setStatus("Pending");
                getSeriesAuctionContestInput.setMyJoinedContest("Yes");
                getSeriesAuctionContestInput.setPrivacy("All");
                getSeriesAuctionContestInput.setLeagueType("Draft");
                getSeriesAuctionContestInput.setIsSeriesStarted("Yes");
                getSeriesAuctionContestInput.setTotalJoinedByRound("Yes");
                getSeriesAuctionContestInput.setIsAssistanceCreated("Yes");

                if (seriesStatus == AuctionSeriesListingFragment.COMPLETED) {
                    getSeriesAuctionContestInput.setMyStats("Yes");
                } else if (seriesStatus == AuctionSeriesListingFragment.FIXTURE) {
                    if (selectedContestStatus != null) {
                        getSeriesAuctionContestInput.setAuctionStatus(selectedContestStatus);
                    }
                }
                if (!contestType.isEmpty()) {
                    getSeriesAuctionContestInput.setContestType(contestType);
                }
                getSeriesAuctionContestInput.setAuctionStatus(seriesStatus == 1 ? "Pending" : (seriesStatus == 2 ? "Running" : "Completed"));

            }
            seriesAuctionContestCall = mInteractor.getSeriesDraftContest(getSeriesAuctionContestInput, new IUserInteractor.OnGetSeriesAuctionContestListener() {
                @Override
                public void onSuccess(GetSeriesAuctionContestOutput getSeriesAuctionContestOutput) {
                    mProgressDialog.dismiss();
                    if (getSeriesAuctionContestOutput.getData().getTotalRecords() == 0) {
                        allContestListed = true;
                        setError("Currently no contest available. Please check later", false);
                    } else {
                        currentPageNo++;

                        if (getSeriesAuctionContestOutput.getData().getRecords().size() < 15) {
                            loading = false;
                        }

                        for (GetSeriesAuctionContestOutput.DataBean.RecordsBean record : getSeriesAuctionContestOutput.getData().getRecords()) {
                            mRecordsBeanArrayList.add(record);
                        }
                    }
                    mListAdapter.notifyDataSetChanged();
                    seriesAuctionContestCall.cancel();
                    seriesAuctionContestCall = null;
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (type != ALL && seriesStatus == AuctionSeriesListingFragment.COMPLETED) {
                        mFrameLayoutBeatTeamBtnRoot.setVisibility(View.VISIBLE);
                    } else {
                        mFrameLayoutBeatTeamBtnRoot.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    setError(errorMsg, true);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            mProgressDialog.dismiss();
            mSwipeRefreshLayout.setRefreshing(false);

            if (mRecordsBeanArrayList.size() == 0) {
                setError(AppUtils.getStrFromRes(R.string.network_error), true);
            } else {
                //  AppUtils.showToast(mContext,AppUtils.getStrFromRes(R.string.network_error));
            }
        }
    }


    private OnItemClickListener.OnItemClickCallback onWinnerClickCallBack = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

            List<GetSeriesAuctionContestOutput.DataBean.RecordsBean.CustomizeWinningBean> customizeWin = mListAdapter.getItem(position).getCustomizeWinning();
            List<WinnersRankBean> rankList = new ArrayList<>();
            for (int i = 0; i < customizeWin.size(); i++) {

                WinnersRankBean mWinnersRankBean = new WinnersRankBean();

                mWinnersRankBean.setFrom(customizeWin.get(i).getFrom());
                mWinnersRankBean.setTo(Integer.parseInt(customizeWin.get(i).getTo()));
                mWinnersRankBean.setPercent(customizeWin.get(i).getPercent() + "");
                mWinnersRankBean.setWinningAmount(customizeWin.get(i).getWinningAmount());

                rankList.add(i, mWinnersRankBean);

            }

            showPreview(rankList, mListAdapter.getItem(position).getWinningAmount());
        }
    };

    private void showPreview(final List<WinnersRankBean> bean,
                             final String totalWinngingAmmount) {
        final WinningsFragment dialogFragment = new WinningsFragment();
        dialogFragment.setUpdateable(new WinnersCallback() {
            @Override
            public void close() {

            }

            @Override
            public Context getContext() {
                return MyContestPendingLiveListingActivity.this;
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


    private void setError(String msg, boolean showTryAgainBtn) {
        if (mRecordsBeanArrayList.size() == 0) {
            mCustomTextViewTryAgainBtn.setVisibility(showTryAgainBtn ? View.GONE : View.GONE);
            mCtvErrorMsg.setText(msg);
            mLinearLayoutErrorRoot.setVisibility(View.VISIBLE);
        }
    }




    private void apiCallDownloadteams(final String seriesID, final String matchGUID) {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            DreamTeamInput downloadTeamInput = new DreamTeamInput();
            downloadTeamInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            downloadTeamInput.setMatchGUID(matchGUID);


            downloadTeamInput.setSeriesID(seriesID);
            mInteractor.auctionallPlayersScore(downloadTeamInput, new IUserInteractor.OnResponseDreamTeamsListener() {


                @Override
                public void onSuccess(DreamTeamOutput responseTeams) {
                    if (responseTeams.getData() != null) {

                        mProgressDialog.dismiss();


                        if (responseTeams.getData().getRecords() != null) {


                            List<PlayerRecord> recordList = new ArrayList<>();

                            for (int i = 0; i < responseTeams.getData().getRecords().size(); i++) {

                                DreamTeamOutput.DataBean.RecordsBean recordsBean = responseTeams.getData().getRecords().get(i);
                                PlayerRecord player = new PlayerRecord();
                                player.setPlayerGUID(recordsBean.getPlayerGUID());
                                player.setPlayerName(recordsBean.getPlayerName());
                                player.setPlayerRole(recordsBean.getPlayerRole());
                                player.setPlayerPic(recordsBean.getPlayerPic());
                                player.setTeamNameShort(recordsBean.getTeamNameShort());


                                player.setPoints(recordsBean.getTotalPoints());
                                player.setPointCredits(recordsBean.getPlayerSalary());
                                player.setTotalPoints(recordsBean.getTotalPointCredits());
                                player.setPointsData(recordsBean.getPointsData());


                                player.setPlayerBattingStyle(recordsBean.getPlayerBattingStyle());
                                player.setPlayerBowlingStyle(recordsBean.getPlayerBattingStyle());
                                player.setSeriesGUID(seriesID);
                                player.setPlayerCountry(recordsBean.getPlayerCountry());
                                player.setTeamGUID(recordsBean.getTeamGUID());
                                player.setPosition(recordsBean.getPlayerPosition());
                                player.setLocalTeamName(responseTeams.getData().getRecords().get(0).getTeamGUID());

                                player.setPlayerSelectedPercent(recordsBean.getPlayerSelectedPercent());

                                recordList.add(player);

                            }

                            showPreviewcrik(recordList, String.valueOf(responseTeams.getData().getTotalPoints()));


                        } else {
                            AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.pageNotFound));
                        }
                    }
                }

                @Override
                public void onNotFound(String error) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            error,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new com.websinception.megastar.dialog.AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    apiCallDownloadteams(mRecordsBeanArrayList.get(0).getSeriesGUID(), matchGUID);
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
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
                                    apiCallDownloadteams(mRecordsBeanArrayList.get(0).getSeriesGUID(), matchGUID);
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
                            apiCallDownloadteams(mRecordsBeanArrayList.get(0).getSeriesGUID(), matchGUID);

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
                return  flag == 1 ? roundID : matchGUID;
            }

            @Override
            public String getStatus() {
                return "completed";
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
                return "";
            }
        });
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
        dialogFragment.setPointLaval(AppUtils.getStrFromRes(R.string.pts));

    }
}
