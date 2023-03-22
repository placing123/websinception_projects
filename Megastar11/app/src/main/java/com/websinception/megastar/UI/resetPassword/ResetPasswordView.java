package com.websinception.megastar.UI.resetPassword;

import android.content.Context;

import com.websinception.megastar.beanOutput.ForgetPasswordOut;
import com.websinception.megastar.beanOutput.ResponseLogin;


/**
 * Created by hp on 06-07-2017.
 */

public interface ResetPasswordView {
    void showLoading();

    void hideLoading();

    void verifyAccountSuccess(ForgetPasswordOut responseLogin);

    void verifyAccountFailure(String errMsg);

    void resendAccountVerificationCodeSuccess(ResponseLogin responseLogin);

    void resendAccountVerificationCodeFailure(String errMsg);

    void showSnackBar(String message);

    void setActivityBackground();

    Context getContext();

    void finishActivity();
}
