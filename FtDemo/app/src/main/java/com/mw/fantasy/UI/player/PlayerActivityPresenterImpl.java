package com.mw.fantasy.UI.player;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.PlayerFantasyStatsInput;
import com.mw.fantasy.beanOutput.ResponsePlayerFantasyStats;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;

import retrofit2.Call;

public class PlayerActivityPresenterImpl implements PlayerActivityPresenter {

    PlayerActivityView mPlayerActivityView;
    IUserInteractor mIUserInteractor;
    Call<ResponsePlayerFantasyStats> responsePlayerFantasyStats;

    public PlayerActivityPresenterImpl(PlayerActivityView mPlayerActivityView, IUserInteractor mIUserInteractor) {
        this.mPlayerActivityView = mPlayerActivityView;
        this.mIUserInteractor = mIUserInteractor;
    }

    public void actionLoginCancel() {
        if (responsePlayerFantasyStats != null && !responsePlayerFantasyStats.isExecuted())
            responsePlayerFantasyStats.cancel();
    }


    @Override
    public void actionPlayerStats(PlayerFantasyStatsInput mPlayerFantasyStatsInput) {

        if (!NetworkUtils.isNetworkConnected(mPlayerActivityView.getContext())) {
            mPlayerActivityView.hideLoading();
            mPlayerActivityView.onPlayerStatsFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mPlayerActivityView.showLoading();
            responsePlayerFantasyStats = mIUserInteractor.playerFantasyStats(mPlayerFantasyStatsInput, new IUserInteractor.OnPlayerFantasyStatsListener() {
                @Override
                public void onSuccess(ResponsePlayerFantasyStats responseLogin) {
                    mPlayerActivityView.hideLoading();
                    mPlayerActivityView.onPlayerStatsSuccess(responseLogin);
                }
                @Override
                public void onError(String errorMsg) {
                    mPlayerActivityView.hideLoading();
                    mPlayerActivityView.onPlayerStatsFailure(errorMsg);

                }

            });
        }


    }
}
