package com.websinception.megastar.UI.createCaption;

import android.content.Context;

import com.websinception.megastar.beanOutput.LoginResponseOut;


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
