package com.websinception.megastar.UI.createContest;



import com.websinception.megastar.beanInput.CreateContestInput;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.WinnerBreakupInput;


/**
 * Created by hp on 06-07-2017.
 */

public interface ICreateContestPresenter {
    void actionCreateContestBtn(CreateContestInput createContestOutput);

    void winning_breakup(WinnerBreakupInput mWinnerBreakupInput);

    void actionViewProfile(LoginInput mLoginInput);


}
