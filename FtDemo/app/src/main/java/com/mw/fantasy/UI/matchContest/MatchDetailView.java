package com.mw.fantasy.UI.matchContest;

import android.content.Context;

import com.mw.fantasy.beanOutput.MatchContestOutPut;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;


/**
 *
 */

public interface MatchDetailView {

    void showLoading();

    void hideLoading();

    void onMatchSuccess(MatchDetailOutPut responseLogin);

    void onMatchFailure(String errMsg);

    void onMatchContestSuccess(MatchContestOutPut responseLogin);

    void onMatchContestFailure(String errMsg);

    boolean isAttached();

    Context getContext();


}
