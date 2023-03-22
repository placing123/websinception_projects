package com.websinception.megastar.UI.createTeam.sorting;

import com.websinception.megastar.beanOutput.PlayersOutput;

import java.util.Comparator;

public class SelectedSorterDEC implements Comparator<PlayersOutput.DataBean.RecordsBean >{

    public int compare(PlayersOutput.DataBean.RecordsBean another, PlayersOutput.DataBean.RecordsBean one){
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
