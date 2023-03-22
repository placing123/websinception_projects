package com.mw.fantasy.UI.mobileAndEmailVerify;


import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.VerifyMobileInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IVerifyMobileEmailPresenter {

    void actionViewProfile(LoginInput mLoginInput);

    void actionSendEmailCodeBtn(VerifyMobileInput mobileInput);

    void actionSendMobileOtpBtn(VerifyMobileInput mobileInput);


}
