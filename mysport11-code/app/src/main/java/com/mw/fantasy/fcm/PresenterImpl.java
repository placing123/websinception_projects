package com.mw.fantasy.fcm;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.MatchDetailInput;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;

import retrofit2.Call;

public class PresenterImpl implements Presenter {

    View mView;
    IUserInteractor mInteractor;

    public PresenterImpl(View mView, IUserInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    Call<MatchDetailOutPut> matchDetailCall;

    @Override
    public void actionMatchdetail(MatchDetailInput mMatchDetailInput) {

        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.onMatchFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            matchDetailCall = mInteractor.matchDetail(mMatchDetailInput, new IUserInteractor.OnResponseMatchDetailsListener() {
                @Override
                public void onSuccess(MatchDetailOutPut mMatchDetailOutPut) {
                        mView.onMatchSuccess(mMatchDetailOutPut);
                }

                @Override
                public void onError(String errorMsg) {
                        mView.onMatchFailure(errorMsg);
                }

            });
        }

    }
}
