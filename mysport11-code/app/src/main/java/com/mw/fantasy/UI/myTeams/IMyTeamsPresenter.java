package com.mw.fantasy.UI.myTeams;


import com.mw.fantasy.beanInput.MyTeamInput;
import com.mw.fantasy.beanInput.SwitchTeamInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IMyTeamsPresenter {
    //cricket
    void actionTeamList(MyTeamInput myTeamInput);

    void actionSwitchBtn(SwitchTeamInput switchTeamInput);


}
