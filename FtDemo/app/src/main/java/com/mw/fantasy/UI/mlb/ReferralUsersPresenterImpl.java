package com.mw.fantasy.UI.mlb;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.ReferEarnInput;
import com.mw.fantasy.beanOutput.DefaultRespose;
import com.mw.fantasy.beanOutput.ReferralUsersResponse;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;

import retrofit2.Call;

public class ReferralUsersPresenterImpl implements ReferralUsersPresenter {

    private ReferralUsersView mView;
    private IUserInteractor mInteractor;
    Call<ReferralUsersResponse> mReferralUsers;
    Call<DefaultRespose> mReferEarn;

    public ReferralUsersPresenterImpl(ReferralUsersView mView, IUserInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }


    @Override
    public void actionListing(LoginInput mLoginInput) {
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.onLoadingError(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            mReferralUsers = mInteractor.getReferralUsers(mLoginInput, new IUserInteractor.OnReferralUsersListener() {
                @Override
                public void onSuccess(ReferralUsersResponse mReferralUsersResponse) {

                    if ((mView.getContext() != null && mReferralUsers!=null && !mReferralUsers.isCanceled())) {
                        mView.hideLoading();
                        mView.onLoadingSuccess(mReferralUsersResponse);
                    }

                }

                @Override
                public void onError(String errorMsg) {

                    if ((mView.getContext() != null && mReferralUsers!=null && !mReferralUsers.isCanceled())) {
                        mView.hideLoading();
                        mView.onLoadingError(errorMsg);
                    }
                }
            });
        }


    }

    @Override
    public void getReferEarn(ReferEarnInput mReferEarnInput) {
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.onLoadingError(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            mReferEarn = mInteractor.getReferEarn(mReferEarnInput, new IUserInteractor.OnMakeFavoriteTeamListener() {
                @Override
                public void onSuccess(DefaultRespose user) {
                    if (user != null) {
                        mView.hideLoading();
                        mView.onReferEarnSuccess(user);
                    }

                }

                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                    mView.onReferEarnError(errorMsg);
                }
            });


        }
    }
}
