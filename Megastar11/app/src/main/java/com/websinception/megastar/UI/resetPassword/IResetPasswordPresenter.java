package com.websinception.megastar.UI.resetPassword;


import com.websinception.megastar.beanInput.LoginInput;


/**
 * Created by hp on 06-07-2017.
 */

public interface IResetPasswordPresenter {
    void resetPasswordBtn(LoginInput mLoginInput);

    void resendAccountVerificationCodeBtn(String email);
}
