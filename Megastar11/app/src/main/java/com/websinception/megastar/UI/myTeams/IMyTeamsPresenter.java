package com.websinception.megastar.UI.myTeams;


import com.websinception.megastar.beanInput.MyTeamInput;
import com.websinception.megastar.beanInput.SwitchTeamInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IMyTeamsPresenter {
    //cricket
    void actionTeamList(MyTeamInput myTeamInput);

    void actionSwitchBtn(SwitchTeamInput switchTeamInput);


}
