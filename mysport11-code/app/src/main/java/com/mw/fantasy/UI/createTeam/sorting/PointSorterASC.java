package com.mw.fantasy.UI.createTeam.sorting;

import com.mw.fantasy.beanOutput.PlayersOutput;


import java.util.Comparator;

public class PointSorterASC implements Comparator<PlayersOutput.DataBean.RecordsBean> {

    public int compare(PlayersOutput.DataBean.RecordsBean one, PlayersOutput.DataBean.RecordsBean another){
        int returnVal = 0;

        if(one.getPointCredits() < another.getPointCredits()){
            returnVal =  -1;
        }else if(one.getPointCredits() > another.getPointCredits()){
            returnVal =  1;
        }else if(one.getPointCredits() == another.getPointCredits()){
            returnVal =  0;
        }
        return returnVal;
    }

}
