package com.mw.fantasy.UI.matches;

import android.content.Context;

import com.mw.fantasy.beanOutput.CheckContestBean;
import com.mw.fantasy.beanOutput.MatchResponseOut;
import com.mw.fantasy.beanOutput.SeriesOutput;


/**
 *
 */

public interface MatchesView {
    void showLoading();

    void hideLoading();

    void onShowLoading();

    void onHideLoading();

    void onLoadingSuccess(MatchResponseOut responseMatches);

    void onCheckContest(CheckContestBean mJoinedContestBean);

    void onLoadingError(String value);

    void onLoadingNotFound(String value);

    void onShowScrollLoading();

    void onHideScrollLoading();

    void onScrollLoadingSuccess(MatchResponseOut responseMatches);

    void onScrollLoadingError(String value);

    void onScrollLoadingNotFound(String value);

    void onShowSnackBar(String message);

    void onMatchSeriesSuccess(SeriesOutput responseMatchSeries);

    void onMatchSeriesFailure(String errMsg);


    boolean isLayoutAdded();

    Context getContext();

    void onClearLogout();
}
