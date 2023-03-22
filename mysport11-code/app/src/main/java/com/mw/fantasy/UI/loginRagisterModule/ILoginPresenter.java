package com.mw.fantasy.UI.loginRagisterModule;

import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.RequestOtpForSigninInput;

/**
 *
 */

public interface ILoginPresenter {
    void actionLoginBtn(LoginInput mLoginInput);

    void actionSocialBtn(LoginInput mLoginInput);

    void verifyOTP(String OTP);

    void actionOtpLoginBtn(RequestOtpForSigninInput requestOtpForSigninInput);
}
