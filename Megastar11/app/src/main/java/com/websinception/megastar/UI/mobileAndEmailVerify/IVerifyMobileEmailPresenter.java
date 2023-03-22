package com.websinception.megastar.UI.mobileAndEmailVerify;


import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.VerifyMobileInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IVerifyMobileEmailPresenter {

    void actionViewProfile(LoginInput mLoginInput);

    void actionSendEmailCodeBtn(VerifyMobileInput mobileInput);

    void actionSendMobileOtpBtn(VerifyMobileInput mobileInput);


}
