package com.websinception.megastar.UI.player;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.PlayerFantasyStatsInput;
import com.websinception.megastar.beanOutput.ResponsePlayerFantasyStats;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;

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
