package com.websinception.megastar.UI.favoriteTeam;

import android.content.Context;

import com.websinception.megastar.beanOutput.DefaultRespose;
import com.websinception.megastar.beanOutput.ResponseFavoriteTeam;


/**
 *
 */

public interface FavoriteTeamView {
    void showLoading();

    void hideLoading();

    void onGetFavoriteTeamSuccess(ResponseFavoriteTeam responseBanner);

    void onFavoriteTeamFailure(String errMsg);

    void onMakeFavoriteTeamSuccess(DefaultRespose responseBanner);

    void onMakeFavoriteTeamFailure(String errMsg);

    void onFavoriteTeamNotFound(String errMsg);

    void onShowSnackBar(String message);

    boolean isAttached();

    Context getContext();
}
