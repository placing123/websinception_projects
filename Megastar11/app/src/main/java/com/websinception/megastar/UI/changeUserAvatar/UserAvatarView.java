package com.websinception.megastar.UI.changeUserAvatar;

import android.content.Context;

import com.websinception.megastar.beanOutput.AvatarListOutput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.ResponseUpdateProfile;


/**
 *
 */

public interface UserAvatarView {
    void showLoading();

    void hideLoading();

    void showUpdateLoading();

    void hideUpdateLoading();

    void onSuccess(AvatarListOutput responseLogin);

    void onAvatarUpdated(LoginResponseOut responseLogin);
    void onUpdateSuccess(ResponseUpdateProfile updateProfile);

    void onError(String message);

    void onFailed(String message);

    void onShowSnackBar(String message);

    Context getContext();

    void finishActivity();
}
