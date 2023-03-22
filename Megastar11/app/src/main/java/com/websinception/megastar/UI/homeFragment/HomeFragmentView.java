package com.websinception.megastar.UI.homeFragment;

import android.content.Context;

import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.ResponseBanner;
import com.websinception.megastar.beanOutput.VersonBean;


/**
 *
 */

public interface HomeFragmentView {
    void showLoading();

    void hideLoading();

    void onBannerSuccess(ResponseBanner responseBanner);

    void onBannerFailure(String errMsg);

    void onBannerNotFound(String errMsg);

    void onShowSnackBar(String message);

    void onVersonSuccess(VersonBean versionBean);

    void onNotificationCountSuccess(LoginResponseOut mLoginResponseOut);

    void onNotificationCountFailure(String errMsg);

    void onVersonFailed(String message);

    void onVersonError(String message);

    boolean isAttached();

    Context getContext();
}
