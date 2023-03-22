package com.mw.fantasy.UI.changeUserAvatar;

import android.content.Context;

import com.mw.fantasy.beanOutput.AvatarListOutput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.ResponseUpdateProfile;


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
