package com.mw.fantasy.UI.forgotPassword;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanOutput.ForgetPasswordOut;

import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;


import retrofit2.Call;

/**
 *
 */

public class ForgotPasswordPresenterImpl implements IForgotPasswordPresenter {

    ForgotPasswordView mForgotPasswordView;
    IUserInteractor mIForgotPasswordInteractor;
    Call<ForgetPasswordOut> forgotPasswordCallback = null;

    public ForgotPasswordPresenterImpl(ForgotPasswordView mForgotPasswordView, IUserInteractor mIForgotPasswordInteractor) {
        this.mForgotPasswordView = mForgotPasswordView;
        this.mIForgotPasswordInteractor = mIForgotPasswordInteractor;
    }

    public void forgotPasswordCancel() {
        if (forgotPasswordCallback != null && !forgotPasswordCallback.isExecuted()) {
            forgotPasswordCallback.cancel();
        }
    }

    @Override
    public void actionForgotPasswordBtn(LoginInput mLoginInput) {
        forgotPasswordCancel();
        if (!NetworkUtils.isNetworkConnected(mForgotPasswordView.getContext())) {
            mForgotPasswordView.hideLoading();
            mForgotPasswordView.forgotPasswordFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mForgotPasswordView.showLoading();
            forgotPasswordCallback = mIForgotPasswordInteractor.forgotPassword(mLoginInput, new IUserInteractor.OnForgetPasswordListener() {
                @Override
                public void onSuccess(ForgetPasswordOut responseLogin) {
                    if (responseLogin != null) {
                        mForgotPasswordView.hideLoading();
                        mForgotPasswordView.forgotPasswordSuccess(responseLogin);
                    } else {
                        mForgotPasswordView.hideLoading();
                        mForgotPasswordView.forgotPasswordFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mForgotPasswordView.hideLoading();
                    mForgotPasswordView.forgotPasswordFailure(errorMsg);
                }

            });
        }
    }
}
