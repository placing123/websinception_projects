package com.mw.fantasy.UI.createContest;



import com.mw.fantasy.beanInput.CreateContestInput;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.WinnerBreakupInput;


/**
 * Created by hp on 06-07-2017.
 */

public interface ICreateContestPresenter {
    void actionCreateContestBtn(CreateContestInput createContestOutput);

    void winning_breakup(WinnerBreakupInput mWinnerBreakupInput);

    void actionViewProfile(LoginInput mLoginInput);


}
