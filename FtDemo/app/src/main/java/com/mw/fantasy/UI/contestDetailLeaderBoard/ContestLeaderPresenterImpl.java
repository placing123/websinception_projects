package com.mw.fantasy.UI.contestDetailLeaderBoard;


import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.ContestDetailInput;
import com.mw.fantasy.beanInput.DownloadTeamInput;
import com.mw.fantasy.beanInput.DreamTeamInput;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.MatchDetailInput;
import com.mw.fantasy.beanOutput.ContestDetailOutput;
import com.mw.fantasy.beanOutput.DreamTeamOutput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.beanOutput.ResponseDownloadTeam;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;

import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class ContestLeaderPresenterImpl implements IContestLeaderPresenter {

    ContestLeaderView mMyAccountParentView;
    IUserInteractor mImyAccountParentInteractor;

    public ContestLeaderPresenterImpl(ContestLeaderView mView, IUserInteractor mInteractor) {
        this.mMyAccountParentView = mView;
        this.mImyAccountParentInteractor = mInteractor;
    }

    Call<MatchDetailOutPut> matchDetailCall;

    Call<ContestDetailOutput> matchContest;

    Call<DreamTeamOutput> dreamTeamOutputCall;

    Call<ResponseDownloadTeam> downloadTeamCall;

    Call<LoginResponseOut> responseLoginCall;


    @Override
    public void actionMatchdetail(MatchDetailInput mMatchDetailInput) {
        actionLoginCancel();
        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.hideLoading();
            mMyAccountParentView.onMatchFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.showLoading();
            matchDetailCall = mImyAccountParentInteractor.matchDetail(mMatchDetailInput, new IUserInteractor.OnResponseMatchDetailsListener() {
                @Override
                public void onSuccess(MatchDetailOutPut mMatchDetailOutPut) {
                    mMyAccountParentView.hideLoading();
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.onMatchSuccess(mMatchDetailOutPut);
                    }
                }

                @Override
                public void onError(String errorMsg) {

                    mMyAccountParentView.hideLoading();
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.onMatchFailure(errorMsg);
                    }
                }

            });
        }
    }

    @Override
    public void getContest(final ContestDetailInput mContestDetailOutput) {
        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.hideLoading();
            mMyAccountParentView.onMatchFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.showLoading();
            matchContest = mImyAccountParentInteractor.getContest(mContestDetailOutput, new IUserInteractor.OnResponseContestDetailsListener() {
                @Override
                public void onSuccess(ContestDetailOutput mContestDetailOutput) {

                    if ((mMyAccountParentView.getContext() != null && matchContest!=null && !matchContest.isCanceled())) {
                        mMyAccountParentView.hideLoading();
                        mMyAccountParentView.onContestDetailSuccess(mContestDetailOutput);
                    }

                }

                @Override
                public void onError(String errorMsg) {

                    if ((mMyAccountParentView.getContext() != null && matchContest!=null && !matchContest.isCanceled())) {
                        mMyAccountParentView.hideLoading();
                        mMyAccountParentView.onContestDetailFailure(errorMsg);
                    }
                }
            });
        }
    }

    @Override
    public void actionDownloadTeam(DownloadTeamInput mDownloadTeamInput) {

        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.dreamhideLoading();
            mMyAccountParentView.onDownloadTeamFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.dreamshowLoading();
            downloadTeamCall = mImyAccountParentInteractor.downloadTeam(mDownloadTeamInput, new IUserInteractor.OnDownloadTeamListener() {
                @Override
                public void onSuccess(ResponseDownloadTeam responseDownloadTeam) {

                    mMyAccountParentView.hideLoading();
                    mMyAccountParentView.onDownloadTeamSuccess(responseDownloadTeam);
                }

                @Override
                public void onError(String errorMsg) {

                    mMyAccountParentView.hideLoading();
                    mMyAccountParentView.onDownloadTeamFailure(errorMsg);
                }
            });
        }
    }

    @Override
    public void getDreamTeam(DreamTeamInput mDreamTeamInput) {
        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.dreamhideLoading();
            mMyAccountParentView.onMatchFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.dreamshowLoading();
            dreamTeamOutputCall = mImyAccountParentInteractor.allPlayersScore(mDreamTeamInput, new IUserInteractor.OnResponseDreamTeamsListener() {
                @Override
                public void onSuccess(DreamTeamOutput mDreamTeamOutput) {

                    mMyAccountParentView.dreamhideLoading();
                    mMyAccountParentView.onDreamTeamSucess(mDreamTeamOutput);
                }

                @Override
                public void onNotFound(String error) {
                    mMyAccountParentView.dreamhideLoading();
                    mMyAccountParentView.onContestDetailFailure(error);
                }

                @Override
                public void onError(String errorMsg) {

                    mMyAccountParentView.dreamhideLoading();
                    mMyAccountParentView.onContestDetailFailure(errorMsg);
                }
            });
        }
    }

    @Override
    public void actionViewProfile(LoginInput mLoginInput) {

        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.hideLoading();
            mMyAccountParentView.onProfileFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.showLoading();
            responseLoginCall = mImyAccountParentInteractor.viewProfile(mLoginInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {

                    mMyAccountParentView.onProfileSuccess(responseLogin);


                }

                @Override
                public void onError(String errorMsg) {

                    mMyAccountParentView.onProfileFailure(errorMsg);

                }

            });
        }
    }


    public void actionLoginCancel() {
        if (matchDetailCall != null && !matchDetailCall.isExecuted())
            matchDetailCall.cancel();
    }

}