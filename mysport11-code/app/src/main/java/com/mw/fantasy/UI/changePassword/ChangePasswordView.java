package com.mw.fantasy.UI.changePassword;

import android.content.Context;

import com.mw.fantasy.beanOutput.LoginResponseOut;


public interface ChangePasswordView {

    void showLoading();

    void hideLoading();

    void loginSuccess(LoginResponseOut responseChangePassword);

    void loginFailure(String errMsg);

    void showSnackBar(String message);

    Context getContext();

}
