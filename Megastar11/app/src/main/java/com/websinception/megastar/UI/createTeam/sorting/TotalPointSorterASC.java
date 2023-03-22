package com.websinception.megastar.UI.createTeam.sorting;

import com.websinception.megastar.beanOutput.PlayersOutput;

import java.util.Comparator;

public class TotalPointSorterASC implements Comparator<PlayersOutput.DataBean.RecordsBean> {

    public int compare(PlayersOutput.DataBean.RecordsBean one, PlayersOutput.DataBean.RecordsBean another){
        int returnVal = 0;

        if(Integer.parseInt(one.getTotalPoints()) < Integer.parseInt(another.getTotalPoints())){
            returnVal =  -1;
        }else if(Integer.parseInt(one.getTotalPoints()) > Integer.parseInt(another.getTotalPoints())){
            returnVal =  1;
        }else if(Integer.parseInt(one.getTotalPoints()) ==Integer.parseInt(another.getTotalPoints())){
            returnVal =  0;
        }
        return returnVal;
    }

}
