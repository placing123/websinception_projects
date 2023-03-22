package com.websinception.megastar.UI.createTeam.sorting;

import com.websinception.megastar.beanOutput.PlayersOutput;

import java.util.Comparator;

public class SortByNameASC implements Comparator<PlayersOutput.DataBean.RecordsBean> {


    @Override
    public int compare(PlayersOutput.DataBean.RecordsBean o1, PlayersOutput.DataBean.RecordsBean o2) {
        return o2.getPlayerName() .compareTo(o1.getPlayerName());
    }
}
