package com.mw.fantasy.UI.resetPassword;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanOutput.ForgetPasswordOut;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;


/**
 *
 */

public class ResetPasswordPresenterImpl implements IResetPasswordPresenter {

    ResetPasswordView mVerifyOTP;
    IUserInteractor mIUserInteractor;

    public ResetPasswordPresenterImpl(ResetPasswordView mVerifyOTP, IUserInteractor mIUserInteractor) {
        this.mVerifyOTP = mVerifyOTP;
        this.mIUserInteractor = mIUserInteractor;
    }

    @Override
    public void resetPasswordBtn(LoginInput mLoginInput) {
        if (!NetworkUtils.isNetworkConnected(mVerifyOTP.getContext())) {
            mVerifyOTP.hideLoading();
            mVerifyOTP.verifyAccountFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mVerifyOTP.showLoading();
            mIUserInteractor.resetPassword(mLoginInput, new IUserInteractor.OnForgetPasswordListener() {
                @Override
                public void onSuccess(ForgetPasswordOut responseLogin) {
                    if (responseLogin != null) {
                        mVerifyOTP.verifyAccountSuccess(responseLogin);
                    } else {
                        mVerifyOTP.hideLoading();
                        mVerifyOTP.verifyAccountFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mVerifyOTP.hideLoading();
                    mVerifyOTP.verifyAccountFailure(errorMsg);
                }

            });
        }
    }

    @Override
    public void resendAccountVerificationCodeBtn(String email) {
        if (!NetworkUtils.isNetworkConnected(mVerifyOTP.getContext())) {
            mVerifyOTP.hideLoading();
            mVerifyOTP.resendAccountVerificationCodeFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mVerifyOTP.showLoading();
           /* mIUserInteractor.resendAccountVerificationCode(email,new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(ResponseLogin responseLogin) {
                    if (responseLogin != null) {

                        mVerifyOTP.resendAccountVerificationCodeSuccess(responseLogin);
                    } else {
                        mVerifyOTP.hideLoading();
                        mVerifyOTP.resendAccountVerificationCodeFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }
                @Override
                public void onError(String errorMsg) {
                    mVerifyOTP.hideLoading();
                    mVerifyOTP.resendAccountVerificationCodeFailure(errorMsg);
                }

            });*/
        }
    }
}
