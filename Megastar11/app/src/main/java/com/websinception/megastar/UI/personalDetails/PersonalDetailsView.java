package com.websinception.megastar.UI.personalDetails;

import android.content.Context;

import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.ResponseCountries;
import com.websinception.megastar.beanOutput.ResponseUpdateProfile;



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
