package com.websinception.megastar.UI.addMoney;


import com.websinception.megastar.beanInput.PromoCodeInput;
import com.websinception.megastar.beanInput.PromoCodeListInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IAddMoneyPresenter {


    void actionPayUMoneyResponseBtn(String loginSessionKey, String response);

    void promoCodeBtn(PromoCodeInput promoCodeInput);

    void actionPromoCodeList(PromoCodeListInput mPromoCodeListInput);

}
