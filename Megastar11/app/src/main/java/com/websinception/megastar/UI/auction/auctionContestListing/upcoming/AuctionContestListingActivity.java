package com.websinception.megastar.UI.auction.auctionContestListing.upcoming;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.SeriesInfoUtil;
import com.websinception.megastar.UI.auction.auctionContestListing.myContest.MyContestPendingLiveListingActivity;
import com.websinception.megastar.UI.contestInviteCode.InviteCodes;
import com.websinception.megastar.UI.draft.createPrivateContest.CreatePrivateDraftActivity;
import com.websinception.megastar.UI.draft.draftHome.DraftDetailScreenActivity;
import com.websinception.megastar.UI.joinContest.JoinContestActivity;
import com.websinception.megastar.UI.myAccount.MyAccountDialogActivity;
import com.websinception.megastar.UI.winnings.WinnersCallback;
import com.websinception.megastar.UI.winnings.WinnersRankBean;
import com.websinception.megastar.UI.winnings.WinningsFragment;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanInput.GetSeriesAuctionContestByTypeInput;
import com.websinception.megastar.beanOutput.AuctionContestCreateOutput;
import com.websinception.megastar.beanOutput.GetPrivateContestOutput;
import com.websinception.megastar.beanOutput.GetSeriesAuctionContestByTypeOutput;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.websinception.megastar.UI.auction.auctionHome.AuctionHomeFragment.JOINED;

public class AuctionContestListingActivity extends BaseActivity {


    public void showPreview(final List<WinnersRankBean> bean,
                            final String totalWinngingAmmount) {
        final WinningsFragment dialogFragment = new WinningsFragment();
        dialogFragment.setUpdateable(new WinnersCallback() {
            @Override
            public void close() {

            }

            @Override
            public Context getContext() {
                return AuctionContestListingActivity.this;
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


    @OnClick(R.id.ll_joined_contest_btn)
    void joinedContestBtnClick() {
        MyContestPendingLiveListingActivity.start(
                mContext,
                flag,
                roundID,
                JOINED,
                seriesStatus,
                seriesName,
                seriesDeadLine);
        finish();
    }


    public static final int REQUEST_CODE_JOIN = 121;
    private Context mContext;
    private String roundID, seriesName, seriesDeadLine, seriesDeadLineLocal, seriesId, matchGUID;
    private int type, seriesStatus, flag;
    private ProgressDialog mProgressDialog;
    private IUserInteractor mInteractor;

    private AuctionContestListByTypeAdapter mAuctionContestListByTypeAdapter;


    public static void start(Context context, int flag, String roundID, String seriesId, int type, int seriesStatus, String seriesName, String seriesDeadLine) {
        Intent starter = new Intent(context, AuctionContestListingActivity.class);
        starter.putExtra("flag", flag);
        starter.putExtra("type", type);
        starter.putExtra("roundID", roundID);
        starter.putExtra("seriesID", seriesId);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesDeadLineLocal", "");
        context.startActivity(starter);
    }


    public static void start(Context context,
                             int type, String matchGUID, int seriesStatus, String seriesID, String seriesName, String seriesDeadLine, String seriesDeadLineLocal) {
        Intent starter = new Intent(context, AuctionContestListingActivity.class);
        starter.putExtra("flag", 2);
        starter.putExtra("type", type);
        starter.putExtra("matchGUID", matchGUID);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("seriesID", seriesID);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesDeadLineLocal", seriesDeadLineLocal);
        context.startActivity(starter);
    }


    @OnClick(R.id.tv_tryAgn)
    public void tryAgainBtnClick() {
        getData();
    }

    @OnClick(R.id.back)
    public void BackBtnClick() {
        onBackPressed();
    }

    @OnClick(R.id.wallet)
    void walletBtnClick() {
        MyAccountDialogActivity.start(this, true);
    }


    @OnClick(R.id.gotContestCode)
    void inviteCodeClick() {
        InviteCodes.start(this, flag == 1 ? roundID : matchGUID, flag == 1 ? InviteCodes.AUCTION : InviteCodes.SNAKE_DRAFT);
    }

    @OnClick(R.id.create_contest)
    void createPrivateContestClick() {
        CreatePrivateDraftActivity.start(this,

                seriesName,
                flag == 1 ? roundID : matchGUID,
                seriesId,
                seriesStatus,
                seriesDeadLine,
                seriesDeadLineLocal, flag);

    }

    @OnClick(R.id.rl_all_contest_root)
    void allContestBtnClick() {
        MyContestPendingLiveListingActivity.start(
                mContext,
                flag,
                flag == 1 ? roundID : matchGUID,
                type,
                seriesStatus,
                seriesName,
                seriesDeadLine);
    }

    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;

    @BindView(R.id.rv_contest)
    RecyclerView mRecyclerView;

    @BindView(R.id.ctv_error_msg)
    CustomTextView mCtvErrorMsg;

    @BindView(R.id.ll_error_root)
    LinearLayout mLinearLayoutErrorRoot;

    @BindView(R.id.rl_joined_contest_btn_root)
    View mViewJoinBtnRoot;

    @BindView(R.id.total_joined)
    CustomTextView mCustomTextViewTotalJoinedContestCount;

    @BindView(R.id.rl_all_contest_root)
    View mViewAllcontestRoot;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public int getLayout() {
        return R.layout.activity_auction_listing_contest;
    }

    @Override
    public void init() {
        mContext = this;
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(mContext);
        flag = getIntent().getExtras().getInt("flag");
        type = getIntent().getExtras().getInt("type");
        seriesStatus = getIntent().getExtras().getInt("seriesStatus");
        seriesName = getIntent().getExtras().getString("seriesName");
        seriesDeadLine = getIntent().getExtras().getString("seriesDeadLine");
        seriesDeadLineLocal = getIntent().getExtras().getString("seriesDeadLineLocal");
        if (flag == 1) {
            //auction
            roundID = getIntent().getExtras().getString("roundID");
            seriesId = getIntent().getExtras().getString("seriesID");
        } else {
            //Draft
            matchGUID = getIntent().getExtras().getString("matchGUID");
            seriesId = getIntent().getExtras().getString("seriesID");
        }
        new SeriesInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName, seriesDeadLine, seriesStatus).start();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                getData();
            }
        });

        getData();
    }


    private void getData() {
        if (flag == 1) {
            apiCallGetAuctionContestByType();
        } else {
            apiCallGetDraftContestByType();
        }
    }


    private void setError(String msg) {
        mCtvErrorMsg.setText(msg);
        mLinearLayoutErrorRoot.setVisibility(View.VISIBLE);
        mViewAllcontestRoot.setVisibility(View.GONE);
    }


    private void apiCallGetAuctionContestByType() {
        mViewAllcontestRoot.setVisibility(View.GONE);
        mLinearLayoutErrorRoot.setVisibility(View.GONE);
        mViewJoinBtnRoot.setVisibility(View.GONE);
        if (mAuctionContestListByTypeAdapter != null) {
            mAuctionContestListByTypeAdapter.clearData();
            mAuctionContestListByTypeAdapter.notifyDataSetChanged();
        }
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            GetSeriesAuctionContestByTypeInput contestByTypeInput = new GetSeriesAuctionContestByTypeInput();
            contestByTypeInput.setContestList("Yes");
            contestByTypeInput.setFilter("Normal");
            contestByTypeInput.setOrderBy("TotalJoined");
            contestByTypeInput.setPageNo(1);
            contestByTypeInput.setPageSize(3);
            contestByTypeInput.setParams("Privacy,TotalJoined,IsPaid,StatusID,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,IsJoined,Status,ContestFormat,ContestType,CustomizeWinning,TotalJoined,UserInvitationCode,TeamNameLocal,TeamNameVisitor,IsConfirm,CashBonusContribution,TeamNameShortLocal,TeamNameShortVisitor,MatchGUID,CashBonusContribution,IsPrivacyNameDisplay,AuctionStatus,LeagueJoinDateTime,LeagueJoinDateTimeUTC");
            contestByTypeInput.setPrivacy("No");
            contestByTypeInput.setSequence("DESC");
            contestByTypeInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            contestByTypeInput.setStatus("Pending");
            contestByTypeInput.setAuctionStatus("Pending");
            contestByTypeInput.setContestFull("No");
            contestByTypeInput.setRoundID(roundID);
            contestByTypeInput.setTotalJoinedByRound("Yes");

            mInteractor.getSeriesAuctionContestByType(contestByTypeInput, new IUserInteractor.OnGetSeriesAuctionContestByTypeListener() {
                @Override
                public void onSuccess(GetSeriesAuctionContestByTypeOutput getSeriesAuctionContestByTypeOutput) {
                    mProgressDialog.dismiss();
                    boolean hasContest = false;
                    for (GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean result : getSeriesAuctionContestByTypeOutput.getData().getResults()) {
                        if (result.getRecords().size() != 0) {
                            hasContest = true;
                            break;
                        }
                    }
                    if (hasContest) {
                        mViewAllcontestRoot.setVisibility(View.VISIBLE);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        mAuctionContestListByTypeAdapter = new AuctionContestListByTypeAdapter(mContext, getSeriesAuctionContestByTypeOutput, flag, type, seriesStatus, seriesName, roundID, matchGUID, seriesDeadLine);
                        mRecyclerView.setAdapter(mAuctionContestListByTypeAdapter);
                    } else {
                        setError("No Auction Contest Available to Play for this series as of Now. Please Try again after sometime!");
                    }
                    if (getSeriesAuctionContestByTypeOutput.getData().getResults().get(0).getTotalJoinedByRound() != 0) {
                        mCustomTextViewTotalJoinedContestCount.setText(getSeriesAuctionContestByTypeOutput.getData().getResults().get(0).getTotalJoinedByRound() + "");
                        mViewJoinBtnRoot.setVisibility(View.VISIBLE);
                    } else {
                        mViewJoinBtnRoot.setVisibility(View.GONE);
                    }


                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    setError(errorMsg);
                }
            });
        } else {
            mProgressDialog.dismiss();
            setError(AppUtils.getStrFromRes(R.string.network_error));
        }
    }


    private void apiCallGetDraftContestByType() {
        mViewAllcontestRoot.setVisibility(View.GONE);
        mLinearLayoutErrorRoot.setVisibility(View.GONE);
        mViewJoinBtnRoot.setVisibility(View.GONE);
        if (mAuctionContestListByTypeAdapter != null) {
            mAuctionContestListByTypeAdapter.clearData();
            mAuctionContestListByTypeAdapter.notifyDataSetChanged();
        }
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            GetSeriesAuctionContestByTypeInput contestByTypeInput = new GetSeriesAuctionContestByTypeInput();
            contestByTypeInput.setContestList("Yes");
            contestByTypeInput.setFilter("Normal");
            contestByTypeInput.setOrderBy("TotalJoined");
         /*   contestByTypeInput.setPageNo(1);
            contestByTypeInput.setPageSize(3);*/
            contestByTypeInput.setParams("Privacy,TotalJoined,IsPaid,StatusID,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,IsJoined,Status,ContestFormat,ContestType,CustomizeWinning,TotalJoined,UserInvitationCode,TeamNameLocal,TeamNameVisitor,IsConfirm,CashBonusContribution,TeamNameShortLocal,TeamNameShortVisitor,MatchGUID,CashBonusContribution,IsPrivacyNameDisplay,AuctionStatus,LeagueJoinDateTime,DraftTeamPlayerLimit,DraftPlayerSelectionCriteria,LeagueJoinDateTimeUTC,ContestID");
            contestByTypeInput.setPrivacy("No");
            contestByTypeInput.setSequence("DESC");
            contestByTypeInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            contestByTypeInput.setStatus("Pending");
            contestByTypeInput.setAuctionStatus("Pending");
            contestByTypeInput.setContestFull("No");
            contestByTypeInput.setMatchGUID(matchGUID);
            contestByTypeInput.setTotalJoinedByRound("Yes");
            contestByTypeInput.setIsAssistanceCreated("Yes");

            mInteractor.getSeriesDraftContestByType(contestByTypeInput, new IUserInteractor.OnGetSeriesAuctionContestByTypeListener() {
                @Override
                public void onSuccess(GetSeriesAuctionContestByTypeOutput getSeriesAuctionContestByTypeOutput) {
                    mProgressDialog.dismiss();
                    boolean hasContest = false;
                    for (GetSeriesAuctionContestByTypeOutput.DataBean.ResultsBean result : getSeriesAuctionContestByTypeOutput.getData().getResults()) {
                        if (result.getRecords().size() != 0) {
                            hasContest = true;
                            break;
                        }
                    }
                    if (hasContest) {
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        mAuctionContestListByTypeAdapter = new AuctionContestListByTypeAdapter(mContext, getSeriesAuctionContestByTypeOutput, flag, type, seriesStatus, seriesName, roundID, matchGUID, seriesDeadLine);
                        mRecyclerView.setAdapter(mAuctionContestListByTypeAdapter);
                    } else {
                        setError("Sorry, currently no draft available! Please try after sometime.");
                    }
                    if (getSeriesAuctionContestByTypeOutput.getData().getResults().get(0).getTotalJoinedByRound() != 0) {
                        mCustomTextViewTotalJoinedContestCount.setText(getSeriesAuctionContestByTypeOutput.getData().getResults().get(0).getTotalJoinedByRound() + "");
                        mViewJoinBtnRoot.setVisibility(View.VISIBLE);
                    } else {
                        mViewJoinBtnRoot.setVisibility(View.GONE);
                    }


                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    setError(errorMsg);
                }
            });
        } else {
            mProgressDialog.dismiss();
            setError(AppUtils.getStrFromRes(R.string.network_error));
        }
    }



    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_JOIN) {
            if (resultCode == RESULT_OK) {
                if (flag == 1) {
                    allContestListed = false;
                    getData(1);
                } else {
                    allContestListed = false;
                    getData(1);
                    AppUtils.showToast(this, "Contest join successfully.");
                }
            }
        } else if (requestCode == 120) {
            if (resultCode == RESULT_OK) {
                if (flag == 2) {
                    Intent intent = new Intent(this, JoinContestActivity.class);
                    intent.putExtra("isDraft", "Yes");
                    intent.putExtra("series_id", data.getExtras().getString("seriesGUID"));
                    intent.putExtra("contestId", data.getExtras().getString("contestGUID"));
                    intent.putExtra("joiningAmount", data.getExtras().getString("joiningAmount"));
                    intent.putExtra("cashBonusContribution", data.getExtras().getString("cashBonusContribution"));
                    intent.putExtra("userInviteCode", data.getExtras().getString("userInviteCode"));
                    startActivityForResult(intent, REQUEST_CODE_JOIN);
                }else {

                    Intent intent = new Intent(mContext, JoinContestActivity.class);
                    intent.putExtra("isAuction", "Yes");
                    intent.putExtra("series_id", data.getExtras().getString("series_id"));
                    intent.putExtra("round_id", data.getExtras().getString("round_id"));
                    intent.putExtra("contestId", data.getExtras().getString("contestId"));
                    intent.putExtra("joiningAmount", data.getExtras().getString("joiningAmount"));
                    intent.putExtra("cashBonusContribution", data.getExtras().getString("cashBonusContribution"));
                    intent.putExtra("userInviteCode", data.getExtras().getString("userInviteCode"));
                    startActivityForResult(intent, REQUEST_CODE_JOIN);
                }
            }
        } else if (requestCode == 150) {
            if (resultCode == RESULT_OK) {
                if (flag == 2) {
                    Intent intent = new Intent(this, JoinContestActivity.class);
                    intent.putExtra("isDraft", "Yes");
                    intent.putExtra("series_id", data.getExtras().getString("seriesGUID"));
                    intent.putExtra("contestId", data.getExtras().getString("contestGUID"));
                    intent.putExtra("joiningAmount", data.getExtras().getString("joiningAmount"));
                    intent.putExtra("cashBonusContribution", data.getExtras().getString("cashBonusContribution"));
                    intent.putExtra("userInviteCode", data.getExtras().getString("userInviteCode"));
                    startActivityForResult(intent, REQUEST_CODE_JOIN);
                }
            }
        }
    }*/


}
