package com.websinception.megastar.UI.addMoney;

import android.content.Context;

import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.PromoCodeListOutput;
import com.websinception.megastar.beanOutput.PromoCodeResponse;


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
