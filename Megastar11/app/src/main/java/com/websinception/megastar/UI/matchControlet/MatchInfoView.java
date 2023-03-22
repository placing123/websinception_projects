package com.websinception.megastar.UI.matchControlet;

import android.content.Context;

import com.websinception.megastar.beanOutput.MatchDetailOutPut;



/**
 *
 */

public interface MatchInfoView {

    void showLoading();

    void hideLoading();

    void onMatchSuccess(MatchDetailOutPut responseLogin);

    void onMatchFailure(String errMsg);

    Context getContext();



}
