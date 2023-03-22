package com.mw.fantasy.UI.addMoney;


import com.mw.fantasy.beanInput.PromoCodeInput;
import com.mw.fantasy.beanInput.PromoCodeListInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IAddMoneyPresenter {


    void actionPayUMoneyResponseBtn(String loginSessionKey, String response);

    void promoCodeBtn(PromoCodeInput promoCodeInput);

    void actionPromoCodeList(PromoCodeListInput mPromoCodeListInput);

}
