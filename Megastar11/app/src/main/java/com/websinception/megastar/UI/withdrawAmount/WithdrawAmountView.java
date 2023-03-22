package com.websinception.megastar.UI.withdrawAmount;

import android.content.Context;

import com.websinception.megastar.beanOutput.WithDrawoutput;



/**
 * Created by hp on 06-07-2017.
 */

public interface WithdrawAmountView {
    void showLoading();

    void hideLoading();

    void withDrawSuccess(WithDrawoutput mWithDrawoutput);

    void withDrawFailure(String errMsg);

    void showSnackBar(String message);

    void setActivityBackground();

    Context getContext();

    void finishActivity();
}
