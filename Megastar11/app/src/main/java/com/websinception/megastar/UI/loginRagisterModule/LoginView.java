package com.websinception.megastar.UI.loginRagisterModule;

import android.content.Context;

import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.RequestOtpForSigninOutput;


public interface LoginView {
    void showLoading();

    void hideLoading();

    void onAccountAvailableForSignUp(String errorMsg);

    void loginSuccess(LoginResponseOut responseLogin);

    void otpRecevied(LoginResponseOut responseLogin);

    void loginFailure(String errMsg);

    void loginNotVerify(LoginResponseOut responseLogin);

    void socialLoginSuccess(LoginResponseOut responseLogin);

    void socialLoginFailure(String errMsg);

    void socialLoginNotVerify(LoginResponseOut responseLogin);

    void socialLoginNotAvailable(LoginInput mLoginInput, String errorMsg);

    void showSnackBar(String message);

    void setActivityBackground();

    Context getContext();

    void finishActivity();

    void loginOtpSuccess(RequestOtpForSigninOutput requestOtpForSigninOutput);
}
