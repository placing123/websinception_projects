package com.websinception.megastar.UI.leaderboardRanking;

import com.websinception.megastar.beanOutput.RankingOutput;
import com.websinception.megastar.beanOutput.SeriesOutput;



public interface LeaderboardRankingView {

    void showLoading();

    void hideLoading();

    void showSnackBar(String message);

    void onMatchSeriesSuccess(SeriesOutput responseMatchSeries);

    void onMatchSeriesFailure(String errMsg);

    void onOverAllLeaderboardSuccess(RankingOutput responseOverallLeaderboard);

    void onOverAllLeaderboardFailure(String errMsg);
}
