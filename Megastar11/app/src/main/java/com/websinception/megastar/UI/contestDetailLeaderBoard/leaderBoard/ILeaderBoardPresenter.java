package com.websinception.megastar.UI.contestDetailLeaderBoard.leaderBoard;


import com.websinception.megastar.beanInput.ContestUserInput;
import com.websinception.megastar.beanInput.MyTeamInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface ILeaderBoardPresenter {


    void joinedUsers(ContestUserInput mContestUserInput);
    void getUsersTeam(MyTeamInput mContestUserInput);




}
