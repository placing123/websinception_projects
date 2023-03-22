package com.mw.fantasy.UI.versionUpdate;

import android.content.Context;

import com.mw.fantasy.beanOutput.LoginResponseOut;




/**
 * Created by hp on 06-07-2017.
 *
 */

public interface UpdateVersionView {
    void showLoading();
    void hideLoading();
    void updateSuccess(LoginResponseOut responseLogin);
    void updateFailure(String errMsg);
    void showSnackBar(String message);
    void setActivityBackground();
    Context getContext();
    void finishActivity();
}
