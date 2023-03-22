package com.mw.fantasy.fcm;

import android.content.Context;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;

public interface View {

    void showLoading();

    void hideLoading();

    void onMatchSuccess(MatchDetailOutPut responseLogin);

    void onMatchFailure(String errMsg);

    Context getContext();
}
