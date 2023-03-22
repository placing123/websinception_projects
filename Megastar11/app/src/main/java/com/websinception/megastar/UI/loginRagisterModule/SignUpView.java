package com.websinception.megastar.UI.loginRagisterModule;

import android.content.Context;

import com.websinception.megastar.beanOutput.ResponceSignup;
import com.websinception.megastar.beanOutput.ResponseReferralDetails;


public interface SignUpView {
    void showLoading();

    void hideLoading();

    void signUpNotVerify(ResponceSignup mSignUpOutput);

    void signUpSuccess(ResponceSignup mSignUpOutput);

    void referralSuccess(ResponseReferralDetails mReferralDetails);

    void referralFailure(String errMsg);

    void signUpFailure(String errMsg);

    void showSnackBar(String message);

    void setActivityBackground();

    void showloader();

    void hideloader();

    Context getContext();

    void finishActivity();
}
