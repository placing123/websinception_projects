package com.websinception.megastar.UI.matchContest;


import com.websinception.megastar.beanInput.MatchContestInput;
import com.websinception.megastar.beanInput.MatchDetailInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IMatchContestPresenter {

    void actionMatchdetail(MatchDetailInput mMatchDetailInput);

    void matchContestList(MatchContestInput mMatchContestInput);


}
