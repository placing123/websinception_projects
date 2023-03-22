package com.websinception.megastar.UI.player;

import android.content.Context;

import com.websinception.megastar.beanOutput.ResponsePlayerFantasyStats;

public interface PlayerActivityView {

    void showLoading();

    void hideLoading();

    void onPlayerStatsSuccess(ResponsePlayerFantasyStats responseLogin);

    void onPlayerStatsFailure(String errMsg);

    void showSnackBar(String message);

    Context getContext();
}
