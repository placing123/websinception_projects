package com.mw.fantasy.UI.contestDetailLeaderBoard.leaderBoard;


import com.mw.fantasy.beanInput.ContestUserInput;
import com.mw.fantasy.beanInput.MyTeamInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface ILeaderBoardPresenter {


    void joinedUsers(ContestUserInput mContestUserInput);
    void getUsersTeam(MyTeamInput mContestUserInput);




}
