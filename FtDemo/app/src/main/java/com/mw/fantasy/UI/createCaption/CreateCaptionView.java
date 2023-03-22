package com.mw.fantasy.UI.createCaption;

import android.content.Context;

import com.mw.fantasy.beanOutput.LoginResponseOut;


/**
 *
 */

public interface CreateCaptionView {
    void showLoading();

    void hideLoading();

    void onSaveSuccess(LoginResponseOut responseLogin);

    void onSaveError(String value);

    void onShowSnackBar(String message);

    boolean isLayoutAdded();

    Context getContext();
}
