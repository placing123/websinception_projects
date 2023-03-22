package com.mw.fantasy.UI.matchControlet;


import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.MatchDetailInput;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;


import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class MatchDetailPresenterImpl implements IMatchDetailPresenter {

    MatchInfoView mMyAccountParentView;
    IUserInteractor mImyAccountParentInteractor;
    Call<MatchDetailOutPut> matchDetailCall;


    public MatchDetailPresenterImpl(MatchInfoView mView, IUserInteractor mInteractor) {
        this.mMyAccountParentView = mView;
        this.mImyAccountParentInteractor = mInteractor;
    }

    @Override
    public void actionMatchdetail(MatchDetailInput matchDetailInput) {

        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.hideLoading();
            mMyAccountParentView.onMatchFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.showLoading();
            matchDetailCall = mImyAccountParentInteractor.matchDetail(matchDetailInput, new IUserInteractor.OnResponseMatchDetailsListener() {
                @Override
                public void onSuccess(MatchDetailOutPut responseLogin) {

                        mMyAccountParentView.onMatchSuccess(responseLogin);
                        mMyAccountParentView.hideLoading();

                }

                @Override
                public void onError(String errorMsg) {

                        mMyAccountParentView.onMatchFailure(errorMsg);
                        mMyAccountParentView.hideLoading();

                }

            });
        }
    }




}