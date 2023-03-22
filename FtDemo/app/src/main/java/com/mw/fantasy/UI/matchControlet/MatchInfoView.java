package com.mw.fantasy.UI.matchControlet;

import android.content.Context;

import com.mw.fantasy.beanOutput.MatchDetailOutPut;



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
