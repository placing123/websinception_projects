package com.mw.fantasy.UI.matchContest;


import com.mw.fantasy.beanInput.MatchContestInput;
import com.mw.fantasy.beanInput.MatchDetailInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IMatchContestPresenter {

    void actionMatchdetail(MatchDetailInput mMatchDetailInput);

    void matchContestList(MatchContestInput mMatchContestInput);


}
