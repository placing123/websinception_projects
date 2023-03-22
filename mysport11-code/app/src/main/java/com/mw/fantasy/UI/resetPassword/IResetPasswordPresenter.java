package com.mw.fantasy.UI.resetPassword;


import com.mw.fantasy.beanInput.LoginInput;


/**
 * Created by hp on 06-07-2017.
 */

public interface IResetPasswordPresenter {
    void resetPasswordBtn(LoginInput mLoginInput);

    void resendAccountVerificationCodeBtn(String email);
}
