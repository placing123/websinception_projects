package com.mw.fantasy.UI.playerPoints;

import android.content.Context;

import com.mw.fantasy.beanOutput.PlayersOutput;


/**
 * Created by mobiweb on 6/12/16.
 */
public interface PlayerPointCallback {
    public void close();



    public PlayersOutput.DataBean.RecordsBean  getPlayer();

    public Context getContext();
}
