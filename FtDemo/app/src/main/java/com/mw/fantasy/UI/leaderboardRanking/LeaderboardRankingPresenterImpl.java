package com.mw.fantasy.UI.leaderboardRanking;

import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.RankingInput;
import com.mw.fantasy.beanInput.SeriesInput;
import com.mw.fantasy.beanOutput.RankingOutput;
import com.mw.fantasy.beanOutput.SeriesOutput;


import retrofit2.Call;

public class LeaderboardRankingPresenterImpl implements LeaderboardRankingPresenter {

    LeaderboardRankingView mLeaderboardRankingView;
    IUserInteractor mIUserInteractor;
    Call<SeriesInput> responseLoginCall;

    public LeaderboardRankingPresenterImpl(LeaderboardRankingView mLeaderboardRankingView, IUserInteractor mIUserInteractor) {
        this.mLeaderboardRankingView = mLeaderboardRankingView;
        this.mIUserInteractor = mIUserInteractor;
    }

    public void actionLoginCancel() {
        if (responseLoginCall != null && !responseLoginCall.isExecuted())
            responseLoginCall.cancel();
    }

    @Override
    public void actionMatchSeries(SeriesInput seriesInput) {
        mIUserInteractor.matchSeriesCall(seriesInput, new IUserInteractor.OnResponseMatchSeriesListener() {
            @Override
            public void onSuccess(SeriesOutput responseSeries) {
                if (responseSeries != null) {
                    mLeaderboardRankingView.hideLoading();
                    mLeaderboardRankingView.onMatchSeriesSuccess(responseSeries);
                } else {
                    mLeaderboardRankingView.hideLoading();
                    mLeaderboardRankingView.onMatchSeriesFailure(responseSeries.getMessage());
                }

            }

            @Override
            public void onError(String errorMsg) {

                mLeaderboardRankingView.hideLoading();
                mLeaderboardRankingView.onMatchSeriesFailure(errorMsg);
            }
        });
    }

    @Override
    public void actionRanking(RankingInput rankingInput) {

        mIUserInteractor.getRankings(rankingInput, new IUserInteractor.OnRankingListener() {



            @Override
            public void onSuccess(RankingOutput mRankingOutput) {
                if (mRankingOutput != null) {
                    mLeaderboardRankingView.hideLoading();
                    mLeaderboardRankingView.onOverAllLeaderboardSuccess(mRankingOutput);
                } else {
                    mLeaderboardRankingView.hideLoading();
                    mLeaderboardRankingView.onOverAllLeaderboardFailure(mRankingOutput.getMessage());
                }
            }

            @Override
            public void onError(String errorMsg) {
                mLeaderboardRankingView.hideLoading();
                mLeaderboardRankingView.onOverAllLeaderboardFailure(errorMsg);
            }
        });
    }
}
