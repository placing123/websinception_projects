package com.websinception.megastar.UI.verifyEmail;

import android.content.Context;

import com.websinception.megastar.beanOutput.LoginResponseOut;


public interface VerifyOtpView {
    void showLoading();

    void hideLoading();

    void showSnackBar(String message);

    void onSuccess(LoginResponseOut message);

    void onError(String message);

    void setActivityBackground();

    Context getContext();

    void finishActivity();
}
