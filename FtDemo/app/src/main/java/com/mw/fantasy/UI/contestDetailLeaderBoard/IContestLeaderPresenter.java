package com.mw.fantasy.UI.contestDetailLeaderBoard;


import com.mw.fantasy.beanInput.ContestDetailInput;
import com.mw.fantasy.beanInput.DownloadTeamInput;
import com.mw.fantasy.beanInput.DreamTeamInput;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.MatchDetailInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IContestLeaderPresenter {

    void actionMatchdetail(MatchDetailInput mMatchDetailInput);

    void getContest(ContestDetailInput mContestDetailInput);

    void actionDownloadTeam(DownloadTeamInput mDownloadTeamInput);

    void getDreamTeam(DreamTeamInput mDreamTeamInput);

    void actionViewProfile(LoginInput mLoginInput);

}
