package com.mw.fantasy.UI.mlb;

import android.content.Context;

import com.mw.fantasy.beanOutput.DefaultRespose;
import com.mw.fantasy.beanOutput.ReferralUsersResponse;

public interface ReferralUsersView {

    void showLoading();

    void hideLoading();

    void onLoadingSuccess(ReferralUsersResponse mReferralUsersResponse);

    void onLoadingError(String value);

    Context getContext();

    void onReferEarnSuccess(DefaultRespose mDefaultRespose);

    void onReferEarnError(String value);


}
