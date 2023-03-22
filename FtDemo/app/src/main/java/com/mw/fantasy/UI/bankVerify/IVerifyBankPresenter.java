package com.mw.fantasy.UI.bankVerify;


import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.UploadImageInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IVerifyBankPresenter {

    void actionViewProfile(LoginInput loginInput);

    void uploadImage(UploadImageInput uploadImageInput);

    void actionCountriesBtn(String userLoginSessionKey);

    void actionStatesBtn(String userLoginSessionKey, String countryId);
}
