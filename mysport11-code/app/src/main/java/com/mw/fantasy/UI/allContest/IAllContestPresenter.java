package com.mw.fantasy.UI.allContest;

import com.mw.fantasy.beanInput.MatchContestInput;
import com.mw.fantasy.beanInput.MatchDetailInput;

public interface IAllContestPresenter {

    void matchContestList(MatchContestInput mMatchContestInput);

    void actionMatchdetail(MatchDetailInput mMatchDetailInput);
}
