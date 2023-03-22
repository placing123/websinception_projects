package com.mw.fantasy.UI.createTeam.sorting;

import com.mw.fantasy.beanOutput.PlayersOutput;

import java.util.Comparator;

public class SortByNameDES implements Comparator<PlayersOutput.DataBean.RecordsBean> {


    @Override
    public int compare(PlayersOutput.DataBean.RecordsBean o1, PlayersOutput.DataBean.RecordsBean o2) {
        return o1.getPlayerName() .compareTo(o2.getPlayerName());
    }
}
