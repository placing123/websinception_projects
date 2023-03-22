package com.websinception.megastar.UI.userProfile;

import android.content.Context;

import com.websinception.megastar.beanOutput.LoginResponseOut;


/**
 *
 */

public interface MyProfileParentView {
    void showLoading();

    void hideLoading();

    void onProfileSuccess(LoginResponseOut responseLogin);

    void onProfileFailure(String errMsg);

    void onSetProfilePicture(String value);

    void onShowLoading();

    void onHideLoading();

    void onUploadPictureSuccess(LoginResponseOut responseLogin, String filePath);

    void onUploadPictureFailure(String errMsg);

    void onShowSnackBar(String message);

    boolean isAttached();

    Context getContext();


}
