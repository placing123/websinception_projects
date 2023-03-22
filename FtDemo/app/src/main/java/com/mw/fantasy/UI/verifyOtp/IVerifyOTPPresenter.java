package com.mw.fantasy.UI.verifyOtp;


import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.RequestOtpForSigninInput;
import com.mw.fantasy.beanInput.VerifyMobileInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IVerifyOTPPresenter {

    void reSendMobileOtp(VerifyMobileInput mobileInput);

    void verifyMobileOtp(VerifyMobileInput mobileInput);

    void actionSendMobileOtpBtn(VerifyMobileInput mobileInput);

    void verifyEmailOtp(VerifyMobileInput verifyMobileInput);

    void actionSendEmailCodeBtn(VerifyMobileInput mobileInput);

    void actionLoginBtn(LoginInput mLoginInput);

    void actionOtpLoginBtn(RequestOtpForSigninInput requestOtpForSigninInput);

}
