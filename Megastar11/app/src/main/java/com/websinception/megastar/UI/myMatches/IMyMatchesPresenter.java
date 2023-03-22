package com.websinception.megastar.UI.myMatches;


import com.websinception.megastar.beanInput.JoinedContestInput;
import com.websinception.megastar.beanInput.MyContestMatchesInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IMyMatchesPresenter {

    void actionListing(JoinedContestInput mJoinedContestInput);

    void myContestactionListing(MyContestMatchesInput mJoinedContestInput);

    void matchContestList(JoinedContestInput mJoinedContestInput);



}
