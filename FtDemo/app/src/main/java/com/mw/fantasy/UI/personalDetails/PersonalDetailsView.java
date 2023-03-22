package com.mw.fantasy.UI.personalDetails;

import android.content.Context;

import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.ResponseCountries;
import com.mw.fantasy.beanOutput.ResponseUpdateProfile;



public interface PersonalDetailsView {

    void showLoading();

    void hideLoading();

    void onProfileSuccess(LoginResponseOut responseLogin);

    void onProfileFailure(String errMsg);

    void onStatesSuccess(ResponseCountries responseCountries);

    void onStatesFailure(String errMsg);

    void showSnackBar(String message);

    void onUpdateSuccess(ResponseUpdateProfile updateProfile);

    void onUpdateFailure(String errMsg);

    Context getContext();
}
