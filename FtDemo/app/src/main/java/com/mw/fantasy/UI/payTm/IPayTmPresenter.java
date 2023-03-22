package com.mw.fantasy.UI.payTm;


import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.PaytmInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IPayTmPresenter {
    void actionPayTmResponseBtn(PaytmInput paytmInput);
    void actionPayTmDetailsBtn(PaytmInput paytmInput);

    void actionViewProfile(LoginInput mLoginInput);

}
