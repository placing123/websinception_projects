package com.mw.fantasy.UI.homeFragment;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.VersionInput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.ResponseBanner;
import com.mw.fantasy.beanOutput.VersonBean;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;


import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class HomeCricketPresenterImpl implements IHomeCricketPresenter {

    HomeFragmentView mView;
    IUserInteractor mImyProfileParentInteractor;

    public HomeCricketPresenterImpl(HomeFragmentView mView, IUserInteractor mImyProfileParentInteractor) {
        this.mView = mView;
        this.mImyProfileParentInteractor = mImyProfileParentInteractor;
    }

    Call<ResponseBanner> responseLoginCall;
    Call<VersonBean> responceVersion;
    Call<LoginResponseOut> loginResponseOutCall;


    public void actionLoginCancel() {
        if (responseLoginCall != null && !responseLoginCall.isExecuted())
            responseLoginCall.cancel();
    }

    public void appVersionCancel() {
        if (responceVersion != null && !responceVersion.isExecuted())
            responceVersion.cancel();
    }

    @Override
    public void actionBannersList(LoginInput mLoginInput) {
        actionLoginCancel();
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.onBannerFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            responseLoginCall = mImyProfileParentInteractor.bannersList(mLoginInput, new IUserInteractor.OnResponseBannerListener() {
                @Override
                public void onSuccess(ResponseBanner responseLogin) {
                    if (mView.isAttached()) {
                        mView.onBannerSuccess(responseLogin);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                    if (mView.isAttached()) {
                        mView.onBannerFailure(errorMsg);
                    }
                }

                @Override
                public void onNotFound(String error) {

                    if (mView.isAttached()) {
                        mView.onBannerNotFound(error);
                    }
                }
            });
        }
    }

    @Override
    public void appVerson(VersionInput versionInput) {
        appVersionCancel();
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.onBannerFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            responceVersion = mImyProfileParentInteractor.appVersion(versionInput, new IUserInteractor.OnVersonResponseListener() {
                @Override
                public void onSuccess(VersonBean user) {
                    if (mView.isAttached()) {
                        mView.onVersonSuccess(user);
                    }
                }

                @Override
                public void onFailed(String errorMsg) {
                    if (mView.isAttached()) {
                        mView.onVersonFailed(errorMsg);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (mView.isAttached()) {
                        mView.onVersonError(errorMsg);
                    }
                }
            });
        }
    }

    @Override
    public void actionNotificationCount(LoginInput mLoginInput) {
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.onNotificationCountFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            loginResponseOutCall = mImyProfileParentInteractor.notificationCount(mLoginInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut user) {
                    if (mView.isAttached()) {
                        mView.onNotificationCountSuccess(user);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (mView.isAttached()) {
                        mView.onNotificationCountFailure(errorMsg);
                    }
                }
            });
        }
    }
}
