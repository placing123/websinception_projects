package com.mw.fantasy.UI.verifyEmail;

import android.content.Context;

import com.mw.fantasy.beanOutput.LoginResponseOut;


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
