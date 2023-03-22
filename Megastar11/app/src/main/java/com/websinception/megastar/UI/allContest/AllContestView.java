package com.websinception.megastar.UI.allContest;

import android.content.Context;

import com.websinception.megastar.beanOutput.AllContestOutPut;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;



public interface AllContestView {
    void showLoading();

    void hideLoading();

    void onShowLoading();

    void onHideLoading();

    void onLoadingSuccess(AllContestOutPut mAllContestOutPut);

    void onLoadingError(String value);

    void onLoadingNotFound(String value);

    void onShowScrollLoading();

    void onHideScrollLoading();

    void onScrollLoadingSuccess(AllContestOutPut mAllContestOutPut);

    void onScrollLoadingError(String value);

    void onScrollLoadingNotFound(String value);

    void onShowSnackBar(String message);

    boolean isLayoutAdded();

    Context getContext();

    void onClearLogout();

    void onMatchSuccess(MatchDetailOutPut responseLogin);

    void onMatchFailure(String errMsg);

}
