package com.websinception.megastar.UI.allContest;

import com.websinception.megastar.beanInput.MatchContestInput;
import com.websinception.megastar.beanInput.MatchDetailInput;

public interface IAllContestPresenter {

    void matchContestList(MatchContestInput mMatchContestInput);

    void actionMatchdetail(MatchDetailInput mMatchDetailInput);
}
