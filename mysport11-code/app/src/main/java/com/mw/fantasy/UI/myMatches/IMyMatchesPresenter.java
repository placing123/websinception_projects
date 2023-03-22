package com.mw.fantasy.UI.myMatches;


import com.mw.fantasy.beanInput.JoinedContestInput;
import com.mw.fantasy.beanInput.MyContestMatchesInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IMyMatchesPresenter {

    void actionListing(JoinedContestInput mJoinedContestInput);

    void myContestactionListing(MyContestMatchesInput mJoinedContestInput);

    void matchContestList(JoinedContestInput mJoinedContestInput);



}
