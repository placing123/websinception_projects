package com.websinception.megastar.UI.mlb;

import android.content.Context;

import com.websinception.megastar.beanOutput.DefaultRespose;
import com.websinception.megastar.beanOutput.ReferralUsersResponse;

public interface ReferralUsersView {

    void showLoading();

    void hideLoading();

    void onLoadingSuccess(ReferralUsersResponse mReferralUsersResponse);

    void onLoadingError(String value);

    Context getContext();

    void onReferEarnSuccess(DefaultRespose mDefaultRespose);

    void onReferEarnError(String value);


}
