package com.websinception.megastar.UI.favoriteTeam;


import com.websinception.megastar.beanInput.FavoriteTeamInput;
import com.websinception.megastar.beanInput.MakeFavoriteTeamInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface FavoriteTeamPresenter {

    void actionfavoriteTeamList(FavoriteTeamInput mFavoriteTeamInput);

    void actionMakefavoriteTeamList(MakeFavoriteTeamInput mFavoriteTeamInput);
}
