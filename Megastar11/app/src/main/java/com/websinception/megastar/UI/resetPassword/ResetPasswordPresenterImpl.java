package com.websinception.megastar.UI.resetPassword;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanOutput.ForgetPasswordOut;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.NetworkUtils;


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
