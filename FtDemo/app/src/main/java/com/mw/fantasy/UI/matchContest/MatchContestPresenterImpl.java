package com.mw.fantasy.UI.matchContest;


import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.MatchContestInput;
import com.mw.fantasy.beanInput.MatchDetailInput;
import com.mw.fantasy.beanOutput.MatchContestOutPut;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;


import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class MatchContestPresenterImpl implements IMatchContestPresenter {

    MatchDetailView mMyAccountParentView;
    IUserInteractor mImyAccountParentInteractor;

    public MatchContestPresenterImpl(MatchDetailView mView, IUserInteractor mInteractor) {
        this.mMyAccountParentView = mView;
        this.mImyAccountParentInteractor = mInteractor;
    }

    Call<MatchDetailOutPut> matchDetailCall;

    Call<MatchContestOutPut> matchContest;


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
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.onMatchSuccess(mMatchDetailOutPut);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.onMatchFailure(errorMsg);
                    }
                }

            });
        }
    }

    @Override
    public void matchContestList(MatchContestInput mMatchContestInput) {

        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.hideLoading();
            mMyAccountParentView.onMatchFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.showLoading();
            matchContest = mImyAccountParentInteractor.getContestsByType(mMatchContestInput, new IUserInteractor.OnResponseContestListener() {
                @Override
                public void onSuccess(MatchContestOutPut mResponseContest) {

                    mMyAccountParentView.hideLoading();
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.onMatchContestSuccess(mResponseContest);
                    }
                }

                @Override
                public void onNotFound(String error) {
                    mMyAccountParentView.hideLoading();
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.onMatchContestFailure(error);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mMyAccountParentView.hideLoading();
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.onMatchContestFailure(errorMsg);
                    }
                }

                @Override
                public void OnSessionExpire() {

                }
            });
        }
    }

    public void actionLoginCancel() {
        if (matchDetailCall != null && !matchDetailCall.isExecuted())
            matchDetailCall.cancel();
    }

}