package com.websinception.megastar.UI.myTeams;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.createTeam.CreateTeamActivity;
import com.websinception.megastar.UI.joinContest.JoinContestActivity;
import com.websinception.megastar.UI.matchControlet.MatchDetailPresenterImpl;
import com.websinception.megastar.UI.matchControlet.MatchInfoView;
import com.websinception.megastar.UI.previewTeam.BottomSheetPreviewFragment;
import com.websinception.megastar.UI.previewTeam.PlayerPreviewCallback;
import com.websinception.megastar.UI.previewTeam.PlayerRecord;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.MatchDetailInput;
import com.websinception.megastar.beanInput.MyTeamInput;
import com.websinception.megastar.beanInput.SwitchTeamInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.MatchContestOutPut;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;
import com.websinception.megastar.beanOutput.MyTeamOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.ItemOffsetDecoration;
import com.websinception.megastar.utility.OnItemClickListener;
import com.websinception.megastar.utility.TimeUtils;
import com.websinception.megastar.utility.ViewUtils;
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTeamsFragment extends BaseFragment implements MyTeamsView, MatchInfoView {

    public static MyTeamsFragment getInstance(Bundle bundle) {
        MyTeamsFragment friendsFragment = new MyTeamsFragment();
        friendsFragment.setArguments(bundle);
        return friendsFragment;
    }

    private Context mContext;
    private MatchDetailPresenterImpl mMatchDetailPresenterImpl;
    private MyTeamsPresenterImpl presenterImpl;
    private boolean SWITCH = false;
    private Loader loader;
    public MyTeamsAdapter adapter;
    private List<MyTeamOutput.DataBean.RecordsBean> mResponse; // Team-list
    private String seriesId = "",
            matchId = "",
            visitorteamId = "",
            statusId,
            join,
            localteamId = "",
            contestId = "",
            joiningAmount = "",
            chip = "",
            userInviteCode = "",
            teamName = "",
            teamId = "",
            cashBonusContribution = "",
            SeriesGUID = "",
            matchTeamVS = "";
    private int maxTeamsAllowed = 11;
    private MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer1, offer2;
    private CountDownTimer countDownTimer;
    private ProgressDialog mProgressDialog;
    private ArrayList<String> joinedTeamGUIDS = new ArrayList<>();


    @BindView(R.id.ll_buttons_container1)
    RelativeLayout ll_buttons_container1;

    @BindView(R.id.ctv_create_team1)
    CustomTextView ctv_create_team1;

    @OnClick(R.id.ctv_create_team1)
    public void createTeam1(View view) {
        CreateTeamActivityStart(mContext, seriesId, matchId, localteamId, visitorteamId, "", teamName, "Create Team");
    }

    @BindView(R.id.ll_buttons_container2)
    LinearLayout ll_buttons_container2;

    @BindView(R.id.ctv_create_team2)
    CustomTextView ctv_create_team2;

    @OnClick(R.id.ctv_create_team2)
    public void createTeam2(View view) {
        CreateTeamActivityStart(mContext, matchId, teamName);
    }

    @BindView(R.id.ctv_join_contest)
    TextView ctv_join_contest;


    @OnClick(R.id.ctv_join_contest)
    public void createContests(View view) {
        if (adapter.getSelectedTeamsId().size() != 0) {
            if (adapter.getSelectedTeamsId().size() > maxTeamsAllowed) {
                AppUtils.showToast(mContext, "You can select max " + maxTeamsAllowed + (maxTeamsAllowed == 1 ? " Team" : " Teams"));
                return;
            }
            JoinContestActivityStart(mContext,
                    matchId,
                    adapter.getSelectedTeamsId(),
                    contestId,
                    joiningAmount,
                    chip,
                    userInviteCode,
                    matchTeamVS);
        }
    }

    @BindView(R.id.ctv_switch_team)
    TextView ctv_switch_team;

    @OnClick(R.id.ctv_switch_team)
    public void switchTeam(View view) {
        if (SWITCH) {
            if (adapter.getSelectedTeamsId().size() == 0) {
                onShowSnackBar("Please choose a new team");
                return;
            }
            ArrayList<String> listUserTeam = new ArrayList<>();
            listUserTeam.add(adapter.getSelectedTeamsId().get(0));
            ArrayList<String> listOldUserTeam = new ArrayList<>();
            listOldUserTeam.add(teamId);
            Gson gson = new Gson();
            String data = gson.toJson(listUserTeam);
            String old = gson.toJson(listOldUserTeam);
            SwitchTeamInput switchTeamInput = new SwitchTeamInput();
            switchTeamInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            switchTeamInput.setUserTeamGUID(data);
            switchTeamInput.setContestGUID(contestId);
            switchTeamInput.setOldUserTeamGUID(old);
            presenterImpl.actionSwitchBtn(switchTeamInput);
        }
    }

    @BindView(R.id.teamsVS)
    CustomTextView teamsVS;

    @BindView(R.id.ctv_timmer_local)
    CustomTextView ctv_timmer_local;

    @BindView(R.id.ctv_timmer_visitor)
    CustomTextView ctv_timmer_visitor;

    @BindView(R.id.civ_timmer_local)
    CustomImageView civ_timmer_local;

    @BindView(R.id.civ_timmer_visitor)
    CustomImageView civ_timmer_visitor;

    @BindView(R.id.ctv_full_time)
    CustomTextView ctv_timer;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.checkBoxSelectAllTeam)
    CheckBox checkBoxSelectAllTeam;

    @BindView(R.id.cardViewSelectAll)
    CardView cardViewSelectAll;


    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, final int position) {
            if (!adapter.isTeamAlreadyJoined(adapter.getItemData(position).getUserTeamGUID())) {
                adapter.setSelect(position);
            }
        }
    };

    private OnItemClickListener.OnItemClickCallback onCloneItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            if (adapter.getItemCount() < 11) {
                MyTeamOutput.DataBean.RecordsBean teamRecord = adapter.getCloneData(position);
                CreateTeamActivityStart(mContext, seriesId, matchId, localteamId, visitorteamId,
                        new Gson().toJson(teamRecord),
                        teamName, 1);
            } else {
                onShowSnackBar("You can not create more than 11 team in single match.");
            }

        }
    };

    private OnItemClickListener.OnItemClickCallback onEditItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            CreateTeamActivityStart(mContext, seriesId, matchId, localteamId, visitorteamId,
                    new Gson().toJson(adapter.getItemData(position)),
                    adapter.getTeamName(position), "Edit Team");
        }
    };


    private OnItemClickListener.OnItemClickCallback onViewItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            List<PlayerRecord> recordList = new ArrayList<>();
            for (int i = 0; i < adapter.getCloneData(position).getUserTeamPlayers().size(); i++) {
                MyTeamOutput.DataBean.RecordsBean.UserTeamPlayersBean teamPlayer = adapter.getCloneData(position).getUserTeamPlayers().get(i);

                PlayerRecord playerRecord = new PlayerRecord();
                playerRecord.setPlayerGUID(teamPlayer.getPlayerGUID());
                playerRecord.setPlayerName(teamPlayer.getPlayerName());
                playerRecord.setPlayerRole(teamPlayer.getPlayerRole());
                playerRecord.setPlayerPic(teamPlayer.getPlayerPic());
                playerRecord.setIsPlaying(teamPlayer.getIsPlaying());
                playerRecord.setTeamGUID(teamPlayer.getTeamGUID());
                playerRecord.setTeamNameShort(teamPlayer.getTeamNameShort());
                playerRecord.setPoints(teamPlayer.getPlayerSalary());
                playerRecord.setPointCredits(teamPlayer.getPlayerSalary());
                playerRecord.setTotalPoints(teamPlayer.getTotalPointCredits());
                playerRecord.setPosition(teamPlayer.getPlayerPosition());
                playerRecord.setPlayerCountry(teamPlayer.getPlayerCountry());
                playerRecord.setPlayerBowlingStyle(teamPlayer.getPlayerBowlingStyle());
                playerRecord.setPlayerBattingStyle(teamPlayer.getPlayerBattingStyle());
                playerRecord.setPlayerSelectedPercent(teamPlayer.getPlayerSelectedPercent());
                playerRecord.setSeriesGUID(SeriesGUID);
                playerRecord.setLocalTeamName(adapter.getCloneData(position).getUserTeamPlayers().get(0).getTeamGUID());
                recordList.add(playerRecord);
            }
            showPreview(recordList, "0", adapter.getTeamName(position), adapter.getCloneData(position).getPlaying11Announce());
        }
    };


    @Override
    public int getLayout() {
        return R.layout.my_team_fragment_new;
    }


    @Override
    public void init() {
        mContext = getActivity();
        mMatchDetailPresenterImpl = new MatchDetailPresenterImpl(this, new UserInteractor());
        presenterImpl = new MyTeamsPresenterImpl(this, new UserInteractor());
        loader = new Loader(getCurrentView());
        SWITCH = false;
        if (getArguments() != null) {

            if (getArguments().containsKey("maxTeamsAllowed")) {
                maxTeamsAllowed = getArguments().getInt("maxTeamsAllowed");
            }

            if (getArguments().containsKey("offer1")) {
                offer1 = (MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean) getArguments().get("offer1");
            }
            if (getArguments().containsKey("offer2")) {
                offer2 = (MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean) getArguments().get("offer2");
            }

            if (getArguments().containsKey("seriesId")) {
                seriesId = getArguments().getString("seriesId");
            }
            if (getArguments().containsKey("matchId")) {
                matchId = getArguments().getString("matchId");
            }
            if (getArguments().containsKey("visitorteamId")) {
                visitorteamId = getArguments().getString("visitorteamId");
            }
            if (getArguments().containsKey("localteamId")) {
                localteamId = getArguments().getString("localteamId");
            }
            if (getArguments().containsKey("contestId")) {
                contestId = getArguments().getString("contestId");
            }
            if (getArguments().containsKey("joiningAmount")) {
                joiningAmount = getArguments().getString("joiningAmount");
            }
            if (getArguments().containsKey("chip")) {
                chip = getArguments().getString("chip");
            }

            if (getArguments().containsKey("userInviteCode")) {
                userInviteCode = getArguments().getString("userInviteCode");
            }
            if (getArguments().containsKey("teamId")) {
                teamId = getArguments().getString("teamId");
            }

            if (maxTeamsAllowed == 1) {
                teamId = "singleEntry";
            }
            if (getArguments().containsKey("SWITCH")) {
                SWITCH = getArguments().getBoolean("SWITCH");
            }
            if (getArguments().containsKey("statusId")) {
                statusId = getArguments().getString("statusId");
            }

            if (getArguments().containsKey("cashBonusContribution")) {
                cashBonusContribution = getArguments().getString("cashBonusContribution");
            }

            if (getArguments().containsKey("join")) {
                join = getArguments().getString("join");
            }

            if (getArguments().containsKey("joinedTeamGUIDS")) {
                joinedTeamGUIDS = getArguments().getStringArrayList("joinedTeamGUIDS");
            }
        }

        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.item_offset));
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyTeamsAdapter(TextUtils.isEmpty(contestId)
                ? R.layout.list_item_teams : R.layout.list_item_select_teams,
                getActivity(),
                new ArrayList<MyTeamOutput.DataBean.RecordsBean>(), joinedTeamGUIDS,
                onItemClickCallback,
                onEditItemClickCallback, onViewItemClickCallback, onCloneItemClickCallback);
        mRecyclerView.setAdapter(adapter);


        teamName = String.format(AppUtils.getStrFromRes(R.string.team_1), "1");

        if (TextUtils.isEmpty(contestId)) {
            ll_buttons_container2.setVisibility(View.GONE);
            ll_buttons_container1.setVisibility(View.VISIBLE);
            ctv_create_team1.setText(String.format(AppUtils.getStrFromRes(R.string.create_team_1), "1"));
        } else {
            ll_buttons_container1.setVisibility(View.GONE);
            ll_buttons_container2.setVisibility(View.VISIBLE);
            if (SWITCH) {
                ctv_join_contest.setVisibility(View.GONE);
                ctv_switch_team.setVisibility(View.VISIBLE);
                ctv_switch_team.setBackgroundResource(R.color.black);
                ctv_switch_team.setFocusable(false);
            } else {
                ctv_switch_team.setVisibility(View.GONE);
                ctv_join_contest.setBackgroundResource(R.color.black);
                ctv_join_contest.setFocusable(false);
            }
        }
        if (loader.getTryAgainView() != null) {
            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apiCallGetMyTeams();
                }
            });
        }
        apiCallGetMyTeams();

        checkBoxSelectAllTeam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                adapter.setSelectAll(b);
            }
        });
    }


    public void apiCallGetMyTeams() {
        MyTeamInput myTeamInput = new MyTeamInput();
        myTeamInput.setMatchGUID(matchId);
        myTeamInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
        myTeamInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        myTeamInput.setPageNo(0);
        myTeamInput.setContestGUID(contestId);
        myTeamInput.setParams(contestId.isEmpty() ? Constant.USER_TEAM_PARAM : Constant.USER_TEAM_JOINED_PARAM);
        myTeamInput.setOrderBy("UserTeamID");
        myTeamInput.setSequence("ASC");
        presenterImpl.actionTeamList(myTeamInput);
    }

    @Override
    public void onLoadingError(String value) {
        loader.error(value);
    }

    @Override
    public void onShowLoading() {
        loader.start();
    }

    @Override
    public void onHideLoading() {
        loader.hide();
    }

    // received team list
    @Override
    public void onLoadingSuccess(MyTeamOutput response) {
        if (isLayoutAdded() && mRecyclerView != null && response.getData().getRecords() != null) {
            mResponse = response.getData().getRecords();
            adapter.clear();
            adapter.addAllItem(response.getData().getRecords());
            adapter.setSelect(teamId);
            ctv_create_team1.setText(String.format(AppUtils.getStrFromRes(R.string.create_team_1), response.getData().getRecords().size() + 1));
            teamName = String.format(AppUtils.getStrFromRes(R.string.team_1), response.getData().getRecords().size() + 1);

            if (!TextUtils.isEmpty(contestId)) {
                ll_buttons_container1.setVisibility(View.GONE);
                ll_buttons_container2.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(contestId)) {
                if (response.getData().getRecords().size() >= 11) {
                    ctv_create_team1.setVisibility(View.GONE);
                    ll_buttons_container1.setVisibility(View.GONE);
                }
            } else {
                if (response.getData().getRecords().size() >= 11) {
                    ctv_create_team2.setVisibility(View.GONE);
                }
                if (SWITCH) {
                    ctv_switch_team.setBackgroundResource(R.color.black);
                    ctv_switch_team.setFocusable(false);
                } else {
                    ctv_join_contest.setBackgroundResource(R.color.black);
                    ctv_join_contest.setFocusable(false);
                }
            }

        } else {
            onLoadingNotFound("Team not found");
            ll_buttons_container1.setVisibility(View.VISIBLE);
            ll_buttons_container2.setVisibility(View.GONE);
        }
        apiCallGetMatchDetails(matchId, statusId);
    }

    @Override
    public void onLoadingNotFound(String value) {
        loader.setNotFoundImage(getContext().getResources().getDrawable(R.drawable.ic_gallery));
        loader.dataNotFound(value);

        ll_buttons_container1.setVisibility(View.VISIBLE);
        ctv_create_team1.setVisibility(View.VISIBLE);
    }


    void apiCallGetMatchDetails(String matchId, String statusId) {
        MatchDetailInput mMatchDetailInput = new MatchDetailInput();
        mMatchDetailInput.setPrivacy("No");
        mMatchDetailInput.setMatchGUID(matchId);
        mMatchDetailInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mMatchDetailInput.setStatus(Constant.Pending);
        mMatchDetailInput.setParams(Constant.MATCH_PARAMS);
        mMatchDetailPresenterImpl.actionMatchdetail(mMatchDetailInput);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }

    @Override
    public void onMatchFailure(String errMsg) {
        hideLoading();
        AppUtils.showToast(mContext, errMsg);
    }

    // Received match Details
    @Override
    public void onMatchSuccess(MatchDetailOutPut responseLogin) {
        hideLoading();
        if (responseLogin.getData() != null) {
            matchTeamVS = responseLogin.getData().getTeamNameShortLocal()
                    + " " + AppUtils.getStrFromRes(R.string.vs)
                    + " " + responseLogin.getData().getTeamNameShortVisitor();

            teamsVS.setText(responseLogin.getData().getTeamNameShortLocal()
                    + " " + AppUtils.getStrFromRes(R.string.vs)
                    + " " + responseLogin.getData().getTeamNameShortVisitor());
            SeriesGUID = responseLogin.getData().getSeriesGUID();


            ctv_timmer_local.setText(responseLogin.getData().getTeamNameShortLocal());
            ctv_timmer_visitor.setText(responseLogin.getData().getTeamNameShortVisitor());
            ViewUtils.setImageUrl(civ_timmer_local, responseLogin.getData().getTeamFlagLocal());
            ViewUtils.setImageUrl(civ_timmer_visitor, responseLogin.getData().getTeamFlagVisitor());

            setMatchTimer(responseLogin);

            if (!TextUtils.isEmpty(contestId) && !SWITCH) {
                if (teamId != null && !teamId.equals("singleEntry") && maxTeamsAllowed != 1) {
                    cardViewSelectAll.setVisibility(View.VISIBLE);
                }
            }
        }

    }


    void setMatchTimer(MatchDetailOutPut details) {
        try {
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


    // clone Team
    public void CreateTeamActivityStart(Context context, String seriesId, String matchId, String localteamId, String visitorteamId, String teamData, String teamName, int actionTag) {
        Intent starter = new Intent(context, CreateTeamActivity.class);
        starter.putExtra("seriesId", seriesId);
        starter.putExtra("MatchGUID", matchId);
        starter.putExtra("localteamId", localteamId);
        starter.putExtra("visitorteamId", visitorteamId);
        starter.putExtra("teamData", teamData);
        starter.putExtra("join", join);
        starter.putExtra("teamName", teamName);
        starter.putExtra("actionTag", actionTag);
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_TEAM);
    }

    // create & Edit Team
    public void CreateTeamActivityStart(Context context, String seriesId, String matchId,
                                        String localteamId, String visitorteamId, String teamData, String teamName, String title) {
        Intent starter = new Intent(context, CreateTeamActivity.class);
        starter.putExtra("seriesId", seriesId);
        starter.putExtra("MatchGUID", matchId);
        starter.putExtra("localteamId", localteamId);
        starter.putExtra("visitorteamId", visitorteamId);
        starter.putExtra("teamData", teamData);
        starter.putExtra("teamName", teamName);
        starter.putExtra("title", title);
        starter.putExtra("join", join);
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_TEAM);
    }


    // create
    public void CreateTeamActivityStart(Context context, String matchId, String teamName) {
        Intent starter = new Intent(context, CreateTeamActivity.class);
        starter.putExtra("MatchGUID", matchId);
        starter.putExtra("join", join);
        starter.putExtra("teamName", teamName);
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_TEAM);
    }

    //Open Bottom sheet For Team View
    private void showPreview(final List<PlayerRecord> responseMatchPlayers, final String totalPoints, final String teamNameString, final String playing11Announce) {
        final BottomSheetPreviewFragment dialogFragment = new BottomSheetPreviewFragment();
        dialogFragment.setCanOpenDetailedScreen(true);
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
                if (TextUtils.isEmpty(teamName))
                    return "Team 1";
                else return teamNameString;
            }

            @Override
            public boolean isTeamPoints() {
                return false;
            }

            @Override
            public String totalPoints() {
                return totalPoints;
            }

            @Override
            public String getMatchID() {
                return matchId;
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
                return playing11Announce;
            }

            @Override
            public String getLocalTeamGUID() {
                for (PlayerRecord responseMatchPlayer : responseMatchPlayers) {
                    if (responseMatchPlayer.getTeamNameShort().equals(ctv_timmer_local.getText().toString())) {
                        return responseMatchPlayer.getTeamGUID();
                    }
                }
                return "";
            }
        });
        dialogFragment.show(getChildFragmentManager(), dialogFragment.getTag());
        dialogFragment.setPointLaval("Cr");
    }

    // Open JinContest Activity
    public void JoinContestActivityStart(Context context, String matchId, ArrayList<String> teamId, String contestId, String joiningAmount, String chip, String userInviteCode, String matchTeamVS) {
        Intent starter = JoinContestActivity.getIntent(context);
        starter.putExtra("contestId", contestId);
        starter.putExtra("teamId", teamId);
        starter.putExtra("matchId", matchId);
        starter.putExtra("joiningAmount", joiningAmount);
        starter.putExtra("chip", chip);
        starter.putExtra("userInviteCode", userInviteCode);
        starter.putExtra("cashBonusContribution", cashBonusContribution);
        starter.putExtra("matchTeamVS", matchTeamVS);
        starter.putExtra("join", join);
        starter.putExtra("joinedTeamGUIDS", joinedTeamGUIDS);
        if (offer1 != null) {
            starter.putExtra("offer1", offer1);
        }
        if (offer2 != null) {
            starter.putExtra("offer2", offer2);
        }
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_JOIN_CONTESTS);
    }


    @Override
    public void onSwitchError(String value) {
        onShowSnackBar(value);
    }

    @Override
    public void onSwitchSuccess(LoginResponseOut responseLogin) {
        onShowSnackBar(responseLogin.getMessage());
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }


    @Override
    public void showLoading() {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public boolean isLayoutAdded() {
        return (isAdded() && getActivity() != null);
    }

    @Override
    public void onShowSnackBar(@NonNull String message) {
        hideLoading();
        AppUtils.showToast(mContext, message);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode", "requestCode: " + BaseActivity.REQUEST_CODE_CREATE_TEAM);
        if (requestCode == BaseActivity.REQUEST_CODE_CREATE_TEAM && resultCode == getActivity().RESULT_OK) {
            apiCallGetMyTeams();
        } else if (requestCode == BaseActivity.REQUEST_CODE_JOIN_CONTESTS && resultCode == getActivity().RESULT_OK) {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }


    public void onDestroy() {
        super.onDestroy();
        if (presenterImpl != null) presenterImpl.actionListingCancel();
    }


}
