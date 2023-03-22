package com.mw.fantasy.UI.verifyEmail;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;


public class VerifyOtpPresenterImpl implements IVerifyOtpPresenter {

    VerifyOtpView mLoginView;
    IUserInteractor iUserInteractor;


    public VerifyOtpPresenterImpl(VerifyOtpView mLoginView, IUserInteractor iUserInteractor) {
        this.mLoginView = mLoginView;
        this.iUserInteractor = iUserInteractor;
    }


    @Override
    public void verifyOTP(LoginInput mLoginInput) {
        if (!NetworkUtils.isNetworkConnected(mLoginView.getContext())) {
            mLoginView.hideLoading();
            mLoginView.onError(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mLoginView.showLoading();
            iUserInteractor.verifyEmail(mLoginInput, new IUserInteractor.OnLoginResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    mLoginView.onSuccess(responseLogin);
                }

                @Override
                public void onAccountNotVerify(LoginResponseOut user) {
                    mLoginView.onError(user.getMessage());
                }

                @Override
                public void onOTPRecevied(LoginResponseOut user) {

                }

                @Override
                public void onAccountAvailableForSignUp(String errorMsg) {
                    mLoginView.onError(errorMsg);
                }

                @Override
                public void onError(String errorMsg) {
                    mLoginView.onError(errorMsg);
                }
            });
        }
    }
}