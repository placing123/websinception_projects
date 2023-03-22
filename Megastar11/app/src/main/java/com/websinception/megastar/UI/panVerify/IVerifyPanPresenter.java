package com.websinception.megastar.UI.panVerify;


import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.UploadImageInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IVerifyPanPresenter {

    void actionViewProfile(LoginInput loginInput);

    void uploadImage(UploadImageInput uploadImageInput);

    void actionCountriesBtn(String userLoginSessionKey);

    void actionStatesBtn(String userLoginSessionKey, String countryId);
}
