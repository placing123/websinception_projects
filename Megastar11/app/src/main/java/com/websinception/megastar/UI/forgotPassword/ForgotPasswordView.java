package com.websinception.megastar.UI.forgotPassword;

import android.content.Context;

import com.websinception.megastar.beanOutput.ForgetPasswordOut;


/**
 * Created by hp on 06-07-2017.
 */

public interface ForgotPasswordView {
    void showLoading();

    void hideLoading();

    void forgotPasswordSuccess(ForgetPasswordOut responseLogin);

    void forgotPasswordFailure(String errMsg);

    void showSnackBar(String message);

    void setActivityBackground();

    Context getContext();

    void finishActivity();
}
