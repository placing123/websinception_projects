package com.websinception.megastar.UI.myMatches;

import android.content.Context;

import com.websinception.megastar.beanOutput.JoinedContestOutput;
import com.websinception.megastar.beanOutput.MatchContestOutPut;
import com.websinception.megastar.beanOutput.MyContestMatchesOutput;


/**
 *
 */

public interface MyMatchesView {

    void onShowLoading();

    void onHideLoading();

    void onLoadingSuccess(JoinedContestOutput mJoinedContestOutput);

    void onLoadingError(String value);

    void onLoadingNotFound(String value);

    void onShowScrollLoading();

    void onHideScrollLoading();

    void onScrollLoadingSuccess(JoinedContestOutput mJoinedContestOutput);

    void onScrollLoadingError(String value);

    void onScrollLoadingNotFound(String value);

    void onShowSnackBar(String message);


    boolean isLayoutAdded();

    Context getContext();

    void onClearLogout();

    void onMyContestLoadingSuccess(MyContestMatchesOutput mJoinedContestOutput);

    void onMyContestLoadingError(String value);

    void onMyContestLoadingNotFound(String value);

    void onMyContestScrollLoadingSuccess(MyContestMatchesOutput mJoinedContestOutput);

    void onMyContestScrollLoadingError(String value);

    void onMyContestScrollLoadingNotFound(String value);


    void onMatchContestSuccess(MatchContestOutPut responseLogin);

    void onMatchContestFailure(String errMsg);
}
