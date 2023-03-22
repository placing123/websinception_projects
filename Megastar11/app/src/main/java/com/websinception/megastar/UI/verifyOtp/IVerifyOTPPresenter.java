package com.websinception.megastar.UI.verifyOtp;


import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.RequestOtpForSigninInput;
import com.websinception.megastar.beanInput.VerifyMobileInput;

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
