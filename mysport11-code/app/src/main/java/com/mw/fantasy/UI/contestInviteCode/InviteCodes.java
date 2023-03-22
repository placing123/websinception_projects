package com.mw.fantasy.UI.contestInviteCode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import butterknife.BindView;
import butterknife.OnClick;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;

import com.mw.fantasy.UI.contestDetailLeaderBoard.ContestLeaderPresenterImpl;
import com.mw.fantasy.UI.contestDetailLeaderBoard.ContestLeaderView;
import com.mw.fantasy.UI.myTeams.MyTeamsActivity;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.base.Loader;

import com.mw.fantasy.beanInput.ContestDetailInput;
import com.mw.fantasy.beanInput.GetPrivateContestInput;
import com.mw.fantasy.beanOutput.ContestDetailOutput;
import com.mw.fantasy.beanOutput.DreamTeamOutput;
import com.mw.fantasy.beanOutput.GetPrivateContestOutput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.MatchContestOutPut;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.beanOutput.ResponseDownloadTeam;
import com.mw.fantasy.customView.CustomEditText;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;

import java.util.ArrayList;

public class InviteCodes extends BaseActivity implements ContestLeaderView {



    public static final int DFS = 0;
    public static final int AUCTION = 1;
    public static final int SNAKE_DRAFT = 2;


    private int flag;
    private String roundId;
    private ProgressDialog mProgressDialog;
    private UserInteractor mUserInteractor;

    private ContestLeaderPresenterImpl mContestLeaderPresenterImpl;

    Context mContext;
    private Loader loader;

    String teamCount="0";
    String inviteCodeString;

    public static void start(Context context, String roundId, int flag) {
        Intent starter = new Intent(context, InviteCodes.class);
        starter.putExtra("roundId", roundId);
        starter.putExtra("flag", flag);
        ((Activity) context).startActivityForResult(starter, 120);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    public static void start(Context context, String teamCount) {
        Intent starter = new Intent(context, InviteCodes.class);
        starter.putExtra("teamCount", teamCount);
        starter.putExtra("flag", DFS);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    public static void start(Context context, String teamCount, String inviteCode) {
        Intent starter = new Intent(context, InviteCodes.class);
        starter.putExtra("teamCount", teamCount);
        starter.putExtra("inviteCode", inviteCode);
        starter.putExtra("flag", DFS);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    @OnClick(R.id.back)
    void onBackClick() {

        onBackPressed();
    }

    @BindView(R.id.inviteCode)
    CustomEditText inviteCode;

    @OnClick(R.id.joinContest)
    void onJoinClick() {

        // Toast.makeText(this, AppUtils.getStrFromRes(R.string.workInProgress), Toast.LENGTH_SHORT).show();

        if (inviteCode.getText().toString().trim().length() == 0) {
            // Toast.makeText(this, getString(R.string.enter_your_code8), Toast.LENGTH_SHORT).show();

            //AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.enter_your_code8));
            AppUtils.showToast(mContext, "Please enter Invite Code!");

            return;
        }

       /* presenterImpl.to_check_contest(AppSession.getInstance().getLoginSession().getResponse().
                        getLoginSessionKey(),
                inviteCode.getText().toString().trim());*/

        callContestDetail(inviteCode.getText().toString().trim());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {

        return R.layout.activity_invite_codes;
    }

    @Override
    public void init() {
        mContext = this;
        mUserInteractor= new UserInteractor();
        loader = new Loader(this);
        mContestLeaderPresenterImpl = new ContestLeaderPresenterImpl(this, new UserInteractor());

        flag = getIntent().getExtras().getInt("flag");


        if (getIntent().hasExtra("roundId")) {
            roundId = getIntent().getStringExtra("roundId");
        }

        if(getIntent().hasExtra("teamCount")){
            teamCount= getIntent().getStringExtra("teamCount");
        }

        if(getIntent().hasExtra("inviteCode")){
            inviteCodeString= getIntent().getStringExtra("inviteCode");

            inviteCode.setText(inviteCodeString);
            callContestDetail(inviteCodeString);
        }

    }

    private void callContestDetail(String inviteCode){

        if (flag == 0) {
            ContestDetailInput mContestDetailInput= new ContestDetailInput();
            mContestDetailInput.setUserInvitationCode(inviteCode);
            mContestDetailInput.setParams(Constant.CONTEST_PARAM);
            mContestDetailInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            mContestLeaderPresenterImpl.getContest(mContestDetailInput);
        }else if (flag == AUCTION) {
            apiCallGetAuctionPrivateContest();
        }else if (flag==SNAKE_DRAFT){
            apiCallGetSnakePrivateContest();
        }



    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.dismiss();

        loader.hide();
    }

    @Override
    public void onMatchSuccess(MatchDetailOutPut responseLogin) {

    }

    @Override
    public void onMatchFailure(String errMsg) {

    }

    @Override
    public void onContestDetailSuccess(ContestDetailOutput mContestDetailOutput) {


       /* if(teamCount.equals("0")){

            CreateTeamActivityStart(mContext,
                    mContestDetailOutput.getData().getMatchGUID(),
                    mContestDetailOutput.getData().getContestGUID(),mContestDetailOutput.getData().getEntryFee());
        }else {
            MyTeamActivityStart(mContext, mContestDetailOutput.getData().getMatchGUID(),
                    mContestDetailOutput.getData().getContestGUID(), mContestDetailOutput.getData().getEntryFee(),
                    mContestDetailOutput.getData().getStatusID());
        }*/
        if (mContestDetailOutput.getData().getContestGUID()==null) {
            onShowSnackBar("Invalid code");
        }else {
            MyTeamActivityStart(mContext,
                    mContestDetailOutput.getData().getMatchGUID(),
                    mContestDetailOutput.getData().getContestGUID(),
                    mContestDetailOutput.getData().getEntryFee(),
                    mContestDetailOutput.getData().getStatusID(),
                    mContestDetailOutput.getData().getUserInvitationCode(),
                    mContestDetailOutput.getData().getJoinedTeamsGUID(),
                    mContestDetailOutput.getData().getOffer1(),
                    mContestDetailOutput.getData().getOffer2(),
                    mContestDetailOutput.getData().getEntryType().equals("Single"),
                    mContestDetailOutput.getData().getUserJoinLimit()- mContestDetailOutput.getData().getUserTeamDetails().size()
            );

            Log.d("mJoinedContestBean",mContestDetailOutput.getMessage());
            onShowSnackBar(mContestDetailOutput.getMessage());

        }





    }



    public void MyTeamActivityStart(Context context,  String matchId,  String contestId, String joiningAmount,String statusId,String userInviteCode,
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
        starter.putExtra("userInviteCode", userInviteCode);
        starter.putExtra("maxTeamsAllowed", maxTeamsAllowed);
        starter.putExtra("cashBonusContribution", "0");
        starter.putExtra("join", "1");
        starter.putExtra("joinedTeamGUIDS", joinedTeamGUIDS);
        starter.putExtra("offer1", offer1);
        starter.putExtra("offer2", offer2);
        if (isSingleEntry) {
            starter.putExtra("teamId", "singleEntry");
        }
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_JOIN_CONTESTS);
    }

    @Override
    public void onContestDetailFailure(String errMsg) {

    }

    @Override
    public void onDownloadTeamSuccess(ResponseDownloadTeam mResponseDownloadTeam) {

    }

    @Override
    public void onDownloadTeamFailure(String errMsg) {

    }

    @Override
    public void onDreamTeamSucess(DreamTeamOutput dreamTeamOutput) {

    }

    @Override
    public void onDreamTeamFailure(String errMsg) {

    }

    @Override
    public void dreamshowLoading() {

    }

    @Override
    public void dreamhideLoading() {

    }

    @Override
    public void onProfileSuccess(LoginResponseOut responseLogin) {

    }

    @Override
    public void onProfileFailure(String errMsg) {

    }

    @Override
    public void onShowSnackBar(String message) {

        AppUtils.showToast(mContext,message);
    }

    @Override
    public boolean isAttached() {
        return false;
    }

    @Override
    public Context getContext() {
        return mContext;
    }



    private void apiCallGetAuctionPrivateContest() {
        hideLoading();
        if (inviteCode.getText().toString().trim().length() != 6) {
            AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.enter_your_code8));
        } else if (!NetworkUtils.isConnected(mContext)) {
            AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.network_error));
        } else {
            showLoading();
            final GetPrivateContestInput getPrivateContestInput = new GetPrivateContestInput();
            getPrivateContestInput.setParams("IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,TotalJoined,CustomizeWinning,CashBonusContribution,SeriesGUID");
            getPrivateContestInput.setRoundID(roundId);
            getPrivateContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getPrivateContestInput.setUserInvitationCode(inviteCode.getText().toString().trim());

            mUserInteractor.getAuctionPrivateContest(getPrivateContestInput, new IUserInteractor.OnGetAuctionPrivateContestListener() {
                @Override
                public void onSuccess(GetPrivateContestOutput getPrivateContestOutput) {
                    hideLoading();
                    if (getPrivateContestOutput.getData().getContestGUID() == null) {
                        AppUtils.showToast(mContext, "Invalid league code");
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("series_id", getPrivateContestOutput.getData().getSeriesID());
                        intent.putExtra("round_id", roundId);
                        intent.putExtra("join", "1");
                        intent.putExtra("contestId", getPrivateContestOutput.getData().getContestGUID());
                        intent.putExtra("joiningAmount", getPrivateContestOutput.getData().getEntryFee());
                        intent.putExtra("cashBonusContribution", getPrivateContestOutput.getData().getCashBonusContribution());
                        intent.putExtra("userInviteCode", getPrivateContestInput.getUserInvitationCode());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    hideLoading();
                    AppUtils.showToast(mContext, errorMsg);
                }
            });
        }
    }



    private void apiCallGetSnakePrivateContest() {
        hideLoading();
        if (inviteCode.getText().toString().trim().length() != 6) {
            AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.enter_your_code8));
        } else if (!NetworkUtils.isConnected(mContext)) {
            AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.network_error));
        } else {
            showLoading();
            final GetPrivateContestInput getPrivateContestInput = new GetPrivateContestInput();
            getPrivateContestInput.setParams("IsPaid,WinningAmount,ContestSize,EntryFee,NoOfWinners,EntryType,TotalJoined,CustomizeWinning,CashBonusContribution,SeriesGUID,DraftPlayerSelectionCriteria,MatchGUID");
            getPrivateContestInput.setMatchGUID(roundId);
            getPrivateContestInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getPrivateContestInput.setUserInvitationCode(inviteCode.getText().toString().trim());
            mUserInteractor.getPrivateContest(getPrivateContestInput, new IUserInteractor.OnGetPrivateContestListener() {
                @Override
                public void onSuccess(GetPrivateContestOutput getPrivateContestOutput) {
                    hideLoading();
                    if (getPrivateContestOutput.getData().getContestGUID() == null) {
                        AppUtils.showToast(mContext, "Invalid league code");
                    } else {


                        Intent intent = new Intent();

                        intent.putExtra("series_id", getPrivateContestOutput.getData().getSeriesID());
                        intent.putExtra("round_id", getPrivateContestOutput.getData().getMatchGUID());
                        intent.putExtra("contestId", getPrivateContestOutput.getData().getContestGUID());
                        intent.putExtra("joiningAmount", getPrivateContestOutput.getData().getEntryFee());
                        intent.putExtra("join", "1");
                        intent.putExtra("userInviteCode", getPrivateContestInput.getUserInvitationCode());
                        intent.putExtra("cashBonusContribution", getPrivateContestOutput.getData().getCashBonusContribution());
                        intent.putExtra("draftPlayerSelectionCriteria", getPrivateContestOutput.getData().getDraftPlayerSelectionCriteria());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    hideLoading();
                    AppUtils.showToast(mContext, errorMsg);
                }
            });
        }
    }



}
