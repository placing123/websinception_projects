package com.websinception.megastar.fcm;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.MatchDetailInput;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;

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
