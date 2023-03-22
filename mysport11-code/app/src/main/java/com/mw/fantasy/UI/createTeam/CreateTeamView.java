package com.mw.fantasy.UI.createTeam;

import android.content.Context;

import com.mw.fantasy.beanOutput.PlayersOutput;



public interface CreateTeamView {
    void showLoading();

    void hideLoading();

    void onShowLoading();

    void onHideLoading();

    void onLoadingSuccess(PlayersOutput responseMatches);

    void onLoadingError(String value);

    void onLoadingNotFound(String value);

    void onShowSnackBar(String message);

    boolean isLayoutAdded();

    Context getContext();
}
