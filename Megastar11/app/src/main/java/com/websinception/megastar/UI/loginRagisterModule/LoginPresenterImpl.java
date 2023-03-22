package com.websinception.megastar.UI.loginRagisterModule;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.RequestOtpForSigninInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.RequestOtpForSigninOutput;
import com.websinception.megastar.beanOutput.ResponseLogin;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;

import retrofit2.Call;


public class LoginPresenterImpl implements ILoginPresenter {

    LoginView mLoginView;
    IUserInteractor iUserInteractor;

    Call<LoginResponseOut> loginCallback = null;
    Call<RequestOtpForSigninOutput> requestOtpForSigninOutputCall = null;
    Call<ResponseLogin> checkSocialLoginCallback = null;


    public LoginPresenterImpl(LoginView mLoginView, IUserInteractor iUserInteractor) {
        this.mLoginView = mLoginView;
        this.iUserInteractor = iUserInteractor;
    }

    public void loginCancel() {
        if (loginCallback != null && !loginCallback.isExecuted()) {
            loginCallback.cancel();
        }
    }


    public void otpLoginCancel() {
        if (requestOtpForSigninOutputCall != null && !requestOtpForSigninOutputCall.isExecuted()) {
            loginCallback.cancel();
        }
    }

    public void checkSocialLoginCancel() {
        if (checkSocialLoginCallback != null && !checkSocialLoginCallback.isExecuted()) {
            checkSocialLoginCallback.cancel();
        }
    }

    @Override
    public void actionLoginBtn(LoginInput mLoginInput) {
        loginCancel();
        if (!NetworkUtils.isNetworkConnected(mLoginView.getContext())) {
            mLoginView.hideLoading();
            mLoginView.loginFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mLoginView.showLoading();
            loginCallback = iUserInteractor.login(mLoginInput, new IUserInteractor.OnLoginResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    mLoginView.loginSuccess(responseLogin);
                }

                @Override
                public void onAccountNotVerify(LoginResponseOut user) {
                    mLoginView.loginNotVerify(user);
                }

                @Override
                public void onOTPRecevied(LoginResponseOut user) {
                    mLoginView.otpRecevied(user);
                }

                @Override
                public void onAccountAvailableForSignUp(String errorMsg) {

                    mLoginView.onAccountAvailableForSignUp(errorMsg);

                }

                @Override
                public void onError(String errorMsg) {
                    mLoginView.loginFailure(errorMsg);
                }
            });
        }
    }

    @Override
    public void actionSocialBtn(final LoginInput mLoginInput) {
        checkSocialLoginCancel();
        if (!NetworkUtils.isNetworkConnected(mLoginView.getContext())) {
            mLoginView.hideLoading();
            mLoginView.loginFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mLoginView.showLoading();
            /*checkSocialLoginCallback= iUserInteractor.checkSocialLogin(
                    mLoginInput,new IUserInteractor.OnLoginResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    mLoginView.socialLoginSuccess(responseLogin);
                }

                @Override
                public void onAccountNotVerify(LoginResponseOut user) {
                    mLoginView.socialLoginNotVerify(user);
                }

                        @Override
                        public void onOTPRecevied(LoginResponseOut user) {

                        }

                        @Override
                public void onError(String errorMsg) {
                    mLoginView.socialLoginFailure( errorMsg);
                }

                @Override
                public void onAccountAvailableForSignUp(String errorMsg) {
                    mLoginView.socialLoginNotAvailable(  mLoginInput,  errorMsg);

                }
            });*/
        }
    }

    @Override
    public void verifyOTP(String OTP) {
        if (!NetworkUtils.isNetworkConnected(mLoginView.getContext())) {
            mLoginView.hideLoading();
            mLoginView.loginFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mLoginView.showLoading();
           /*  iUserInteractor.mobile_verify_otp_new(OTP, new IUserInteractor.OnLoginResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    mLoginView.loginSuccess(responseLogin);
                }

                @Override
                public void onAccountNotVerify(LoginResponseOut user) {
                    mLoginView.loginNotVerify(user);
                }

                @Override
                public void onOTPRecevied(LoginResponseOut user) {

                }

                @Override
                public void onAccountAvailableForSignUp(String errorMsg) {

                }

                @Override
                public void onError(String errorMsg) {
                    mLoginView.loginFailure(errorMsg);
                }
            });*/
        }
    }

    @Override
    public void actionOtpLoginBtn(RequestOtpForSigninInput requestOtpForSigninInput) {
        otpLoginCancel();
        if (!NetworkUtils.isNetworkConnected(mLoginView.getContext())) {
            mLoginView.hideLoading();
            mLoginView.loginFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mLoginView.showLoading();
            requestOtpForSigninOutputCall = iUserInteractor.requestOtpForSigninCall(requestOtpForSigninInput, new IUserInteractor.OnRequestOtpForSigninListener() {
                @Override
                public void onSuccess(RequestOtpForSigninOutput requestOtpForSigninOutput) {
                    mLoginView.loginOtpSuccess(requestOtpForSigninOutput);
                }

                @Override
                public void onError(String errorMsg) {
                    mLoginView.loginFailure(errorMsg);
                }
            });
        }
    }
}