package com.websinception.megastar.UI.loginRagisterModule;

import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.RequestOtpForSigninInput;

/**
 *
 */

public interface ILoginPresenter {
    void actionLoginBtn(LoginInput mLoginInput);

    void actionSocialBtn(LoginInput mLoginInput);

    void verifyOTP(String OTP);

    void actionOtpLoginBtn(RequestOtpForSigninInput requestOtpForSigninInput);
}
