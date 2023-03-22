package com.mw.fantasy.UI.createTeam.sorting;

import com.mw.fantasy.beanOutput.ResponsePlayerFantasyStats;

import java.util.Comparator;

public class SelectedByASC implements Comparator<ResponsePlayerFantasyStats.DataBean.RecordsBean> {
    @Override
    public int compare(ResponsePlayerFantasyStats.DataBean.RecordsBean one, ResponsePlayerFantasyStats.DataBean.RecordsBean another) {
        int returnVal = 0;

        if(Double.valueOf(one.getPlayerSelectedPercent()) < Double.valueOf(another.getPlayerSelectedPercent())){
            returnVal =  -1;
        }else if(Double.valueOf(one.getPlayerSelectedPercent()) > Double.valueOf(another.getPlayerSelectedPercent())){
            returnVal =  1;
        }else if(Double.valueOf(one.getPlayerSelectedPercent()) == Double.valueOf(another.getPlayerSelectedPercent())){
            returnVal =  0;
        }
        return returnVal;
    }
}
