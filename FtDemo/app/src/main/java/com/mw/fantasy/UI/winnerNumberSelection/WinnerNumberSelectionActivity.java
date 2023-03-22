package com.mw.fantasy.UI.winnerNumberSelection;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.SeriesInfoUtil;
import com.mw.fantasy.UI.createContest.CreateContestPresenterImpl;
import com.mw.fantasy.UI.createContest.CreateContestView;
import com.mw.fantasy.UI.createTeam.CreateTeamActivity;
import com.mw.fantasy.UI.matchControlet.MatchDetailPresenterImpl;
import com.mw.fantasy.UI.matchControlet.MatchInfoView;
import com.mw.fantasy.UI.myTeams.MyTeamsActivity;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.base.Loader;
import com.mw.fantasy.beanInput.CreateContestInput;
import com.mw.fantasy.beanInput.MatchDetailInput;
import com.mw.fantasy.beanInput.WinnerBreakupInput;
import com.mw.fantasy.beanOutput.AuctionContestCreateOutput;
import com.mw.fantasy.beanOutput.CreateContestOutput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.beanOutput.WinBreakupOutPut;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.ItemOffsetDecoration;
import com.mw.fantasy.utility.NetworkUtils;
import com.mw.fantasy.utility.OnItemClickListener;
import com.mw.fantasy.utility.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;


public class WinnerNumberSelectionActivity extends BaseActivity implements CreateContestView, MatchInfoView {


    WinBreakupNumberAdapter adapter;
    Loader loader;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Context mContext;
    int position = 0;
    @BindView(R.id.teamsVS)
    CustomTextView teamsVS;
    @BindView(R.id.ctv_timer)
    CustomTextView ctv_timer;
    @BindView(R.id.contest_size)
    CustomTextView contest_size;
    @BindView(R.id.price_pool)
    CustomTextView price_pool;
    @BindView(R.id.entry_fee)
    CustomTextView entry_fee;
    @BindView(R.id.winners)
    CustomTextView winners;
    @BindView(R.id.choose_total_winner)
    CustomTextView choose_total_winner;
    @BindView(R.id.winnersRl)
    RelativeLayout winnersRl;
    @BindView(R.id.title)
    CustomTextView mCustomTextViewTitle;
    @BindView(R.id.matchDetail)
    LinearLayout matchDetail;


    @BindView(R.id.fl_auction_series_info_root)
    FrameLayout mFrameLayoutSeriesInfoRoot;

    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;

    Context context;
    String match_id = "", total_winning_amount = "";
    String contest_sizes = "";
    String team_entry_fee = "";
    String contest_name = "";
    String is_multientry = "";
    String seriesId = "";
    String localteamId = "";
    String visitorteamId = "";
    String teamId = "";
    String isPrivacyNameDisplay = "";
    String number_of_winners;
    JSONArray winning_ranks = new JSONArray();
    CountDownTimer countDownTimer;
    private String seriesDeadLineLocal;

    WinBreakupOutPut mWinBreakupBean;
    private MatchDetailPresenterImpl mMatchDetailPresenterImpl;
    List<WinBreakupOutPut.DataBean.WinnersBean> mWinnersBean = new ArrayList<>();
    int teamCount = 0;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    private CreateContestPresenterImpl mUpdateProfilePresenterImpl;
    private ProgressDialog mProgressDialog;
    private OnItemClickListener.OnItemClickCallback onPayItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

        }
    };
    private String roundID;
    private boolean isFromSnackDraft = false;
    private boolean isFromAuction = false;
    private String leagueStartTime;


    private IUserInteractor mInteractor;
    private Call<AuctionContestCreateOutput> getAuctioncreateCall;
    private int flag;
    /*
        private String total_round, wk, ar, bowl, bat;
    */
    private Call<AuctionContestCreateOutput> getDraftcreateCall;
    private String statusId;


    @OnClick(R.id.back)
    void onBack() {
        finish();
    }

    @OnClick(R.id.ctv_save)
    public void save(View view) {

        if (flag == 1) {

            CreateContestInput createContestInput = new CreateContestInput();
            createContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            createContestInput.setContestName(contest_name);
            createContestInput.setWinningAmount(total_winning_amount);
            createContestInput.setContestSize(contest_sizes);
            createContestInput.setEntryFee(team_entry_fee);
            createContestInput.setContestFormat(Constant.ContestFormat);
            createContestInput.setContestType(Constant.ContestType);
            createContestInput.setPrivacy("Yes");
            createContestInput.setIsPaid("Yes");
            createContestInput.setIsConfirm("No");
            createContestInput.setShowJoinedContest("Yes");
            createContestInput.setLeagueJoinDateTime(leagueStartTime);
            createContestInput.setEntryType("Single");
            createContestInput.setMinimumUserJoined("1");
            createContestInput.setSeriesID(seriesId);
            createContestInput.setCustomizeWinning(winning_ranks.toString());
            createContestInput.setCashBonusContribution(0);
            createContestInput.setNoOfWinners(number_of_winners);
            createContestInput.setAdminPercent("15");
            createContestInput.setLeagueType("Auction");
            createContestInput.setRoundID(roundID);
            apiAuctionCreateContest(createContestInput);


        } else if (flag == 2) {

            CreateContestInput createContestInput = new CreateContestInput();
            createContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            createContestInput.setContestName(contest_name);
            createContestInput.setWinningAmount(total_winning_amount);
            createContestInput.setContestSize(contest_sizes);
            createContestInput.setEntryFee(team_entry_fee);
            createContestInput.setContestFormat(Constant.ContestFormat);
            createContestInput.setContestType(Constant.ContestType);
            createContestInput.setPrivacy("Yes");
            createContestInput.setIsPaid("Yes");
            createContestInput.setIsConfirm("No");
            createContestInput.setShowJoinedContest("Yes");
            createContestInput.setLeagueJoinDateTime(leagueStartTime);
            createContestInput.setEntryType("Single");
            createContestInput.setMinimumUserJoined("1");
            createContestInput.setSeriesID(seriesId);
            createContestInput.setCustomizeWinning(winning_ranks.toString());
            createContestInput.setCashBonusContribution(0);
            createContestInput.setNoOfWinners(number_of_winners);
            createContestInput.setAdminPercent("15");
            createContestInput.setLeagueType("Draft");
            createContestInput.setMatchGUID(roundID);
            createContestInput.setMatchStartDate(seriesDeadLineLocal);
            /*createContestInput.setDraftTeamPlayerLimit(total_round);*/
            CreateContestInput.DraftPlayerSelectionCriteria draftPlayerSelectionCriteria = new CreateContestInput.DraftPlayerSelectionCriteria();
            /*draftPlayerSelectionCriteria.setAllRounder(ar);
            draftPlayerSelectionCriteria.setBatsman(bat);
            draftPlayerSelectionCriteria.setBowler(bowl);
            draftPlayerSelectionCriteria.setWicketKeeper(wk);*/
            Gson gson = new Gson();
            createContestInput.setDraftPlayerSelectionCriteria(gson.toJson(draftPlayerSelectionCriteria));

            apiDraftCreateContest(createContestInput);

        } else {


            CreateContestInput createContestInput = new CreateContestInput();
            createContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            createContestInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            createContestInput.setContestName(contest_name);
            createContestInput.setWinningAmount(total_winning_amount);
            createContestInput.setContestSize(contest_sizes);
            createContestInput.setEntryFee(team_entry_fee);
            createContestInput.setContestFormat(Constant.ContestFormat);
            createContestInput.setContestType(Constant.ContestType);
            createContestInput.setPrivacy("Yes");
            createContestInput.setIsPaid("Yes");
            createContestInput.setShowJoinedContest("No");
            createContestInput.setEntryType(is_multientry);
            if (is_multientry.equalsIgnoreCase("Multiple")) {
                createContestInput.setUserJoinLimit("6");
            } else {
                createContestInput.setUserJoinLimit("1");
            }
            createContestInput.setMatchGUID(match_id);
            createContestInput.setSeriesGUID(seriesId);
            createContestInput.setCustomizeWinning(winning_ranks.toString());
            createContestInput.setCashBonusContribution(0);
            createContestInput.setNoOfWinners(number_of_winners);
            createContestInput.setApp("Yes");
            createContestInput.setAdminPercent("10");
            createContestInput.setIsPrivacyNameDisplay(isPrivacyNameDisplay);

            mUpdateProfilePresenterImpl.actionCreateContestBtn(createContestInput);


        }
    }

    @OnClick(R.id.winnersRl)
    public void onWinnerList() {

        showPreview(mWinBreakupBean.getData(), "0", position);
    }

    public void MyTeamActivityStartForJoin(Context context, String seriesId, String matchId,
                                           String localteamId, String visitorteamId,
                                           String contestId, String joiningAmount, String chip, String userInviteCode,
                                           boolean isSingleEntry) {
        Intent starter = MyTeamsActivity.getIntent(context);
        starter.putExtra("seriesId", seriesId);
        starter.putExtra("matchId", matchId);
        starter.putExtra("localteamId", localteamId);
        starter.putExtra("visitorteamId", visitorteamId);
        starter.putExtra("contestId", contestId);
        starter.putExtra("statusId", statusId);
        starter.putExtra("join", "0");
        starter.putExtra("joiningAmount", joiningAmount);
        starter.putExtra("chip", chip);
        starter.putExtra("userInviteCode", userInviteCode);
        starter.putExtra("cashBonusContribution", "0");
        if (isSingleEntry) {
            starter.putExtra("teamId", "singleEntry");
        }
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_JOIN_CONTESTS);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_winner_number_selection;
    }

    @Override
    public void init() {

        mContext = this;
        mInteractor = new UserInteractor();

        mCustomTextViewTitle.setText(AppUtils.getStrFromRes(R.string.newContest));

        mWinBreakupBean = new WinBreakupOutPut();
        setView();

        loader = new Loader(this);

        if (getIntent().hasExtra("flag")) {
            flag = getIntent().getIntExtra("flag", 0);
        }


        if (getIntent().hasExtra("match_id")) {
            //matchDetail.setVisibility(View.VISIBLE);
            match_id = getIntent().getStringExtra("match_id");
            total_winning_amount = getIntent().getStringExtra("total_winning_amount");
            contest_sizes = getIntent().getStringExtra("contest_sizes");
            contest_name = getIntent().getStringExtra("contest_name");
            is_multientry = getIntent().getStringExtra("is_multientry");
            team_entry_fee = getIntent().getStringExtra("team_entry_fee");

            seriesId = getIntent().getStringExtra("seriesId");
            localteamId = getIntent().getStringExtra("localteamId");
            visitorteamId = getIntent().getStringExtra("visitorteamId");
            isPrivacyNameDisplay = getIntent().getStringExtra("isPrivacyNameDisplay");

            teamCount = getIntent().getIntExtra("teamCount", 0);

            contest_size.setText(contest_sizes);
            price_pool.setText(AppUtils.getStrFromRes(R.string.price_unit) + " " + total_winning_amount);
            entry_fee.setText(AppUtils.getStrFromRes(R.string.price_unit) + " " + team_entry_fee);

            if (getIntent().hasExtra("statusId")) {
                statusId = getIntent().getStringExtra("statusId");
            }

            if (getIntent().hasExtra("teamId")) {
                teamId = getIntent().getStringExtra("teamId");
            }
        } else {
            if (flag == 1) {
//                if (getIntent().hasExtra("isFromAuction") && getIntent().getBooleanExtra("isFromAuction", false)) {
//                    isFromAuction = true;
                matchDetail.setVisibility(View.GONE);
                contest_name = getIntent().getStringExtra("contest_name");
                contest_sizes = getIntent().getStringExtra("contest_sizes");
                total_winning_amount = getIntent().getStringExtra("total_winning_amount");
                team_entry_fee = getIntent().getStringExtra("team_entry_fee");
                seriesId = getIntent().getStringExtra("SeriesID");
                roundID = getIntent().getStringExtra("RoundID");
                leagueStartTime = getIntent().getStringExtra("leagueStartTime");

                contest_size.setText(contest_sizes);
                price_pool.setText(AppUtils.getStrFromRes(R.string.price_unit) + " " + total_winning_amount);
                entry_fee.setText(AppUtils.getStrFromRes(R.string.price_unit) + " " + team_entry_fee);

                String seriesName = getIntent().getStringExtra("seriesName");
                String seriesDeadLine = getIntent().getStringExtra("seriesDeadLine");
                int seriesStatus = getIntent().getExtras().getInt("seriesStatus");
                //mFrameLayoutSeriesInfoRoot.setVisibility(View.VISIBLE);
                new SeriesInfoUtil(mCustomTextViewASI_SeriesName,
                        mCustomTextViewASI_SeriesStatus, seriesName, seriesDeadLine, seriesStatus).start();

//                }
            } else if (flag == 2) {
                matchDetail.setVisibility(View.GONE);
                contest_name = getIntent().getStringExtra("contest_name");
                contest_sizes = getIntent().getStringExtra("contest_sizes");
                total_winning_amount = getIntent().getStringExtra("total_winning_amount");
                team_entry_fee = getIntent().getStringExtra("team_entry_fee");
                seriesId = getIntent().getStringExtra("SeriesID");
                roundID = getIntent().getStringExtra("RoundID");
                seriesDeadLineLocal = getIntent().getStringExtra("seriesDeadLineLocal");
               /* wk = getIntent().getStringExtra("wk");
                ar = getIntent().getStringExtra("ar");
                bat = getIntent().getStringExtra("bat");
                bowl = getIntent().getStringExtra("bowl");
                total_round = getIntent().getStringExtra("round");*/
                leagueStartTime = getIntent().getStringExtra("leagueStartTime");

                contest_size.setText(contest_sizes);
                price_pool.setText(AppUtils.getStrFromRes(R.string.price_unit) + " " + total_winning_amount);
                entry_fee.setText(AppUtils.getStrFromRes(R.string.price_unit) + " " + team_entry_fee);
            }

        }

        mUpdateProfilePresenterImpl = new CreateContestPresenterImpl(this, new UserInteractor());
        mMatchDetailPresenterImpl = new MatchDetailPresenterImpl(this, new UserInteractor());

        adapter = new WinBreakupNumberAdapter(R.layout.item_winning_number, mContext, mWinnersBean, onPayItemClickCallback);
        recyclerView.setAdapter(adapter);


        WinnerBreakupInput mWinnerBreakupInput = new WinnerBreakupInput();
        mWinnerBreakupInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        if (flag == 0) {
            mWinnerBreakupInput.setMatchGUID(match_id);
        }
        mWinnerBreakupInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
        mWinnerBreakupInput.setWinningAmount(total_winning_amount);
        mWinnerBreakupInput.setContestSize(contest_sizes);
        mWinnerBreakupInput.setEntryFee(team_entry_fee);
        mWinnerBreakupInput.setIsPaid("Yes");


        mUpdateProfilePresenterImpl.winning_breakup(mWinnerBreakupInput);
        if (getIntent().hasExtra("match_id")) {
            callMatchDetail(getIntent().getStringExtra("match_id"), Constant.Pending);
        }


    }


    void callMatchDetail(String matchId, String statusId) {

        MatchDetailInput mMatchDetailInput = new MatchDetailInput();
        mMatchDetailInput.setPrivacy("No");
        mMatchDetailInput.setMatchGUID(matchId);
        mMatchDetailInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mMatchDetailInput.setStatus(statusId);
        mMatchDetailInput.setParams(Constant.MATCH_PARAMS);

        mMatchDetailPresenterImpl.actionMatchdetail(mMatchDetailInput);

    }

    private void setView() {
        recyclerView.addItemDecoration(new ItemOffsetDecoration(mContext, R.dimen.item_offset));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null) mProgressDialog.dismiss();

    }


    @Override
    public void createContestSuccess(CreateContestOutput responseLogin) {
        hideLoading();

        AppUtils.showSnackBar(mContext, mCoordinatorLayout, responseLogin.getMessage());
        if (responseLogin.getTotalTeams() != null && responseLogin.getTotalTeams().equalsIgnoreCase("0")) {
            CreateTeamActivityStart(mContext, match_id, responseLogin.getData().getContestGUID().getContestGUID(), String.valueOf(responseLogin.getData().getContestGUID().getEntryFee()));
        } else {
            MyTeamActivityStartForJoin(mContext, seriesId,
                    match_id, localteamId,
                    visitorteamId,
                    responseLogin.getData().getContestGUID().getContestGUID(),
                    String.valueOf(responseLogin.getData().getContestGUID().getEntryFee()),
                    "", responseLogin.getData().getContestGUID().getUserInvitationCode()
                    , responseLogin.getData().getContestGUID().getEntryType().equals("Single")
            );
        }


        /*if (teamCount > 0) {
            //  JoinContestActivityStart(mContext, matchId, teamId, responseLogin.getResponse().getId(), responseLogin.getResponse().getTeamEntryFee(), responseLogin.getResponse().getUserInviteCode());


        } else {
            // MyTeamActivityStart(mContext, seriesId, matchId, localteamId, visitorteamId, responseLogin.getResponse().getId(), responseLogin.getResponse().getTeamEntryFee(), responseLogin.getResponse().getUserInviteCode(),responseLogin.getResponse().getChip());
            CreateTeamActivityStart(mContext, seriesId, match_id, localteamId, visitorteamId,
                    responseLogin.getData().getContestGUID().getContestGUID(),
                    String.valueOf(responseLogin.getData().getContestGUID().getEntryFee()),
                    responseLogin.getData().getContestGUID().getUserInvitationCode());
        }*/
        setResult(RESULT_OK);
        finish();
    }


    public void CreateTeamActivityStart(Context context, String MatchGUID, String contestId, String joiningAmount) {

        Intent starter = new Intent(context, CreateTeamActivity.class);
        starter.putExtra("MatchGUID", MatchGUID);
        starter.putExtra("contestId", contestId);
        starter.putExtra("joiningAmount", joiningAmount);
        starter.putExtra("cashBonusContribution", "0");

        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_TEAM);
    }

    @Override
    public void createContestFailure(String errMsg) {
        hideLoading();
        showSnackBar(errMsg);
    }

    @Override
    public void winBreakupSuccess(WinBreakupOutPut responseLogin) {

        mWinBreakupBean = responseLogin;

        number_of_winners = String.valueOf(responseLogin.getData().get(position).getNoOfWinners());

        try {
            JSONArray jsonArray = new JSONArray(AppUtils.gsonToJSON(responseLogin.getData().get(position).getWinners()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            winning_ranks = new JSONArray(AppUtils.gsonToJSON(responseLogin.getData().get(position).getWinners()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("winning_ranks", winning_ranks.toString());

        mWinnersBean.addAll(responseLogin.getData().get(position).getWinners());

        winners.setText(responseLogin.getData().get(position).getNoOfWinners() + " " + AppUtils.getStrFromRes(R.string.winners));

        adapter.notifyDataSetChanged();
    }

    @Override
    public void winBreakupFailure(String errMsg) {

        showSnackBar(errMsg);
    }

    @Override
    public void onProfileSuccess(LoginResponseOut responseLogin) {

    }

    @Override
    public void onProfileFailure(String errMsg) {

    }

    @Override
    public void onHideLoading() {
        loader.hide();
    }

    @Override
    public void onShowLoading() {
        loader.start();
    }

    @Override
    public void showSnackBar(String message) {
        hideLoading();
        AppUtils.showSnackBar(mContext, mCoordinatorLayout, message);
    }

    @Override
    public void setActivityBackground() {

    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void showPreview(final List<WinBreakupOutPut.DataBean> bean, final String totalWinngingAmmount, final int index) {
        final WinBreakupFragment dialogFragment = new WinBreakupFragment();
        dialogFragment.setUpdateable(new WinBreakupCallback() {
            @Override
            public void close(int pos) {
                adapter.clear();
                position = pos;
                number_of_winners = String.valueOf(mWinBreakupBean.getData().get(pos).getNoOfWinners());

                try {
                    winning_ranks = new JSONArray(AppUtils.gsonToJSON(mWinBreakupBean.getData().get(position).getWinners()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("winning_ranks", winning_ranks.toString());

                mWinnersBean.addAll(mWinBreakupBean.getData().get(pos).getWinners());

                winners.setText(mWinBreakupBean.getData().get(pos).getNoOfWinners() + " " + AppUtils.getStrFromRes(R.string.winners));

                adapter.notifyDataSetChanged();

            }

            @Override
            public Context getContext() {
                return mContext;
            }

            @Override
            public List<WinBreakupOutPut.DataBean> getBean() {
                return bean;
            }

            @Override
            public int getIndex() {
                return index;
            }
        });
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());

    }

    private void apiAuctionCreateContest(CreateContestInput createContestInput) {

        if (!NetworkUtils.isNetworkConnected(mContext)) {
            showSnackBar(AppUtils.getStrFromRes(R.string.network_error));

        } else {
            //loader.start();
            showLoading();
            getAuctioncreateCall = mInteractor.auctioncreateContest(createContestInput, new IUserInteractor.OnResponseAuctionCreateContestListener() {

                @Override
                public void onSuccess(AuctionContestCreateOutput mCreateContestOutput) {
                    if (getAuctioncreateCall == null || getAuctioncreateCall.isCanceled()) {
                        return;
                    }
                    hideLoading();
                    Intent intent = new Intent();
                    intent.putExtra("series_id", mCreateContestOutput.getData().getSeriesID());
                    intent.putExtra("round_id", mCreateContestOutput.getData().getRoundID());
                    intent.putExtra("contestId", mCreateContestOutput.getData().getContestGUID());
                    intent.putExtra("joiningAmount", mCreateContestOutput.getData().getEntryFee());
                    intent.putExtra("userInviteCode", mCreateContestOutput.getData().getUserInvitationCode());
                    intent.putExtra("cashBonusContribution", "0");
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onError(String errorMsg) {
                    if (getAuctioncreateCall == null || getAuctioncreateCall.isCanceled()) {
                        return;
                    }
                    hideLoading();
                    showSnackBar(errorMsg);


                }
            });
        }
    }


    private void apiDraftCreateContest(CreateContestInput createContestInput) {

        if (!NetworkUtils.isNetworkConnected(mContext)) {
            showSnackBar(AppUtils.getStrFromRes(R.string.network_error));

        } else {
            showLoading();
            getDraftcreateCall = mInteractor.createPrivateSnakeContest(createContestInput, new IUserInteractor.OnResponseAuctionCreateContestListener() {

                @Override
                public void onSuccess(AuctionContestCreateOutput mCreateContestOutput) {
                    if (getDraftcreateCall == null || getDraftcreateCall.isCanceled()) {
                        return;
                    }
                    hideLoading();
                    Intent intent = new Intent();
                    intent.putExtra("series_id", mCreateContestOutput.getData().getSeriesID());
                    intent.putExtra("round_id", roundID);
                    intent.putExtra("contestId", mCreateContestOutput.getData().getContestGUID());
                    intent.putExtra("joiningAmount", mCreateContestOutput.getData().getEntryFee());
                    intent.putExtra("userInviteCode", mCreateContestOutput.getData().getUserInvitationCode());
                    intent.putExtra("cashBonusContribution", "0");
                    intent.putExtra("draftPlayerSelectionCriteria", mCreateContestOutput.getData().getDraftPlayerSelectionCriteria());
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onError(String errorMsg) {
                    if (getDraftcreateCall == null || getDraftcreateCall.isCanceled()) {
                        return;
                    }
                    hideLoading();
                    showSnackBar(errorMsg);


                }
            });
        }
    }


    @Override
    public void onMatchSuccess(MatchDetailOutPut responseLogin) {
        hideLoading();

        teamsVS.setText(responseLogin.getData().getTeamNameShortLocal()
                + " " + AppUtils.getStrFromRes(R.string.vs)
                + " " + responseLogin.getData().getTeamNameShortVisitor());

        setMatchTimer(responseLogin);
    }

    @Override
    public void onMatchFailure(String errMsg) {
        hideLoading();
        showSnackBar(errMsg);

    }

    void setMatchTimer(MatchDetailOutPut details) {


        ctv_timer.setTextColor(mContext.getResources().getColor(R.color.green));
        try {

            ctv_timer.setTextColor(mContext.getResources().getColor(R.color.green));
            ctv_timer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_time, 0, 0, 0);

            if (TimeUtils.isThisDateValid(details.getData().getMatchStartDateTime(), "yyyy-MM-dd HH:mm:ss")) {
                final long diff = TimeUtils.getTimeDifference(details.getData().getMatchStartDateTime(),
                        details.getData().getCurrentDateTime());
                if (TimeUnit.MILLISECONDS.toHours(diff) > Constant.SHOW_TIME_LIMIT_HRS) {
                    ctv_timer.setText(TimeUtils.getMatchDateOnly(details.getData().getMatchDate()));
                } else {
                    try {
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                        //need to implement counter
                        countDownTimer = new CountDownTimer(diff, TimeUnit.SECONDS.toMillis(1)) {
                            public void onTick(long millisUntilFinished) {
                                if (ctv_timer != null) {
                                    ctv_timer.setText(TimeUtils.getRemainsTime(millisUntilFinished));
                                } else {
                                    if (countDownTimer != null) {
                                        countDownTimer.cancel();
                                    }
                                }
                            }

                            public void onFinish() {
                                if (ctv_timer != null) {
                                    ctv_timer.setText(AppUtils.getStrFromRes(R.string.in_progress));

                                }
                            }
                        };
                        if (countDownTimer != null) {
                            countDownTimer.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                ctv_timer.setText(TimeUtils.getMatchDateOnly(details.getData().getMatchDate()));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            ctv_timer.setText("N/A");
        }
    }


}
