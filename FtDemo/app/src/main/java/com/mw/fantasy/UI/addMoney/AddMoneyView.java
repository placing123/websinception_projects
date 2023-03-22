package com.mw.fantasy.UI.addMoney;

import android.content.Context;

import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.PromoCodeListOutput;
import com.mw.fantasy.beanOutput.PromoCodeResponse;


/**
 * Created by hp on 06-07-2017.
 */

public interface AddMoneyView {
    void showLoading();

    void hideLoading();

    void payUMoneyResponseSuccess(LoginResponseOut responseLogin);

    void payUMoneyResponseFailure(String errMsg);

    void changePasswordSuccess(LoginResponseOut responseLogin);

    void changePasswordFailure(String errMsg);

    void showSnackBar(String message);

    void promoCodeSuccess(PromoCodeResponse mPromoCodeResponse);

    void promoCodeFaliure(String message);

    void promocodeListSuccess(PromoCodeListOutput mPromoCodeListOutput);

    void promocodeListFailure(String msg);

    void setActivityBackground();

    Context getContext();

    void finishActivity();
}
