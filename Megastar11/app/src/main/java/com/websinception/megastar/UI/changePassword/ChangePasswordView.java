package com.websinception.megastar.UI.changePassword;

import android.content.Context;

import com.websinception.megastar.beanOutput.LoginResponseOut;


public interface ChangePasswordView {

    void showLoading();

    void hideLoading();

    void loginSuccess(LoginResponseOut responseChangePassword);

    void loginFailure(String errMsg);

    void showSnackBar(String message);

    Context getContext();

}
