package com.websinception.megastar.UI.payTm;


import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.PaytmInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IPayTmPresenter {
    void actionPayTmResponseBtn(PaytmInput paytmInput);
    void actionPayTmDetailsBtn(PaytmInput paytmInput);

    void actionViewProfile(LoginInput mLoginInput);

}
