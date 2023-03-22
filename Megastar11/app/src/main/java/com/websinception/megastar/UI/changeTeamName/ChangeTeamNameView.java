package com.websinception.megastar.UI.changeTeamName;

import android.content.Context;

import com.websinception.megastar.beanOutput.ResponseUpdateProfile;



/**
 * Created by hp on 06-07-2017.
 */

public interface ChangeTeamNameView {
    void showLoading();

    void hideLoading();



    void showSnackBar(String message);

    void onUpdateSuccess(ResponseUpdateProfile updateProfile);

    void onUpdateFailure(String errMsg);

    Context getContext();
}
