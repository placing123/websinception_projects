package com.websinception.megastar.UI.myAccount;

import android.content.Context;

import com.websinception.megastar.beanOutput.WalletOutputBean;



/**
 *
 */

public interface MyAccountParentView {
    void showLoading();

    void hideLoading();

    void onAccountSuccess(WalletOutputBean mWalletOutputBean);

    void onAccountFailure(String errMsg);

    void onShowLoading();

    void onHideLoading();

    void onShowSnackBar(String message);

    boolean isAttached();

    Context getContext();
}
