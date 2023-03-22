package com.mw.fantasy.UI.favoriteTeam;


import com.mw.fantasy.beanInput.FavoriteTeamInput;
import com.mw.fantasy.beanInput.MakeFavoriteTeamInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface FavoriteTeamPresenter {

    void actionfavoriteTeamList(FavoriteTeamInput mFavoriteTeamInput);

    void actionMakefavoriteTeamList(MakeFavoriteTeamInput mFavoriteTeamInput);
}
