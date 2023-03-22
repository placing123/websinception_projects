package com.mw.fantasy.UI.createTeam.sorting;

import com.mw.fantasy.beanOutput.ResponsePlayerFantasyStats;

import java.util.Comparator;

public class PointsASC implements Comparator<ResponsePlayerFantasyStats.DataBean.RecordsBean> {
    @Override
    public int compare(ResponsePlayerFantasyStats.DataBean.RecordsBean one, ResponsePlayerFantasyStats.DataBean.RecordsBean another) {
        int returnVal = 0;

        if(Float.valueOf(one.getTotalPoints()) < Float.valueOf(another.getTotalPoints())){
            returnVal =  -1;
        }else if(Float.valueOf(one.getTotalPoints()) > Float.valueOf(another.getTotalPoints())){
            returnVal =  1;
        }else if(Float.valueOf(one.getTotalPoints()) == Float.valueOf(another.getTotalPoints())){
            returnVal =  0;
        }
        return returnVal;
    }
}
