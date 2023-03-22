package com.mw.fantasy.UI.contestDetailLeaderBoard.leaderBoard;

import android.content.Context;

import com.mw.fantasy.beanOutput.ContestUserOutput;
import com.mw.fantasy.beanOutput.ResponsePlayersScore;
import com.mw.fantasy.beanOutput.SingleTeamOutput;


/**
 *
 */

public interface LeaderBoardView {
    void showLoading();

    void hideLoading();

    void onShowLoading();

    void onHideLoading();

    void onLoadingSuccess(ContestUserOutput responseContest);

    void onLoadingError(String value);

    void onLoadingNotFound(String value);

    void onShowScrollLoading();

    void onHideScrollLoading();

    void onScrollLoadingSuccess(ContestUserOutput responseContest);

    void onScrollLoadingError(String value);

    void onScrollLoadingNotFound(String value);

    void onTeamSuccess(SingleTeamOutput responseContest);

    void onTeamError(String value);

    void onScoreSuccess(ResponsePlayersScore responseContest);

    void onScoreError(String value);

    void onShowSnackBar(String message);

    boolean isLayoutAdded();

    Context getContext();
}
