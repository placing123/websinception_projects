package com.websinception.megastar.UI.verifyOtp;

import android.content.Context;

import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.RequestOtpForSigninOutput;


/**
 * Created by hp on 06-07-2017.
 */

public interface VerifyOTPView {
    void showLoading();

    void hideLoading();

    void verifyAccountSuccess(LoginResponseOut responseLogin);

    void verifyAccountFailure(String errMsg);

    void verifyEmailSuccess(LoginResponseOut responseLogin);

    void verifyEmailFailure(String errMsg);


    void resendAccountVerificationCodeSuccess(LoginResponseOut responseLogin);

    void resendAccountVerificationCodeFailure(String errMsg);

    void showSnackBar(String message);

    void setActivityBackground();

    Context getContext();

    void finishActivity();

    void loginSuccess(LoginResponseOut responseLogin);

    void otpRecevied(LoginResponseOut responseLogin);

    void loginFailure(String errMsg);

    void loginNotVerify(LoginResponseOut responseLogin);

    void onAccountAvailableForSignUp(String errorMsg);

    void loginOtpSuccess(RequestOtpForSigninOutput requestOtpForSigninOutput);

    void loginOtpFailure(String ermsg);
}
