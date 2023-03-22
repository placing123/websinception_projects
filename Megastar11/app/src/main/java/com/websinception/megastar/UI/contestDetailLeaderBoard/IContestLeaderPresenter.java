package com.websinception.megastar.UI.contestDetailLeaderBoard;


import com.websinception.megastar.beanInput.ContestDetailInput;
import com.websinception.megastar.beanInput.DownloadTeamInput;
import com.websinception.megastar.beanInput.DreamTeamInput;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.MatchDetailInput;

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
