package com.mw.fantasy.UI.contestDetailLeaderBoard;

import android.content.Context;

import com.mw.fantasy.beanOutput.ContestDetailOutput;
import com.mw.fantasy.beanOutput.DreamTeamOutput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.beanOutput.ResponseDownloadTeam;


/**
 *
 */

public interface ContestLeaderView {

    void showLoading();

    void hideLoading();



    void onMatchSuccess(MatchDetailOutPut responseLogin);

    void onMatchFailure(String errMsg);

    void onContestDetailSuccess(ContestDetailOutput mContestDetailOutput);

    void onContestDetailFailure(String errMsg);

    void onDownloadTeamSuccess(ResponseDownloadTeam mResponseDownloadTeam);

    void onDownloadTeamFailure(String errMsg);


    void onDreamTeamSucess(DreamTeamOutput dreamTeamOutput);
    void onDreamTeamFailure(String errMsg);

    void dreamshowLoading();

    void dreamhideLoading();

    void onProfileSuccess(LoginResponseOut responseLogin);

    void onProfileFailure(String errMsg);

    void onShowSnackBar(String message);

    boolean isAttached();

    Context getContext();


}
