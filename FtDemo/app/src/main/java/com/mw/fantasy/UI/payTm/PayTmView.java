package com.mw.fantasy.UI.payTm;

import android.content.Context;

import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.ResponsePayTmDetails;



/**
 * Created by hp on 06-07-2017.
 */

public interface PayTmView {
    void showLoading();
    void hideLoading();
    void payTmDetailsSuccess(ResponsePayTmDetails responseLogin);
    void payTmDetailsFailure(String errMsg);
    void payTmResponseSuccess(LoginResponseOut responseLogin);
    void payTmResponseFailure(String errMsg);
    void showSnackBar(String message);
    void onProfileSuccess(LoginResponseOut responseLogin);

    void onProfileFailure(String errMsg);

    Context getContext();
}
