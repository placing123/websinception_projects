package com.mw.fantasy.UI.leaderboardRanking;

import com.mw.fantasy.beanOutput.RankingOutput;
import com.mw.fantasy.beanOutput.SeriesOutput;



public interface LeaderboardRankingView {

    void showLoading();

    void hideLoading();

    void showSnackBar(String message);

    void onMatchSeriesSuccess(SeriesOutput responseMatchSeries);

    void onMatchSeriesFailure(String errMsg);

    void onOverAllLeaderboardSuccess(RankingOutput responseOverallLeaderboard);

    void onOverAllLeaderboardFailure(String errMsg);
}
