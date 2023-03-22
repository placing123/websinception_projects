package com.websinception.megastar.fcm;

import android.content.Context;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;

public interface View {

    void showLoading();

    void hideLoading();

    void onMatchSuccess(MatchDetailOutPut responseLogin);

    void onMatchFailure(String errMsg);

    Context getContext();
}
