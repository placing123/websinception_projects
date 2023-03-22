package com.websinception.megastar.UI.personalDetails;

import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.UpdateProfileInput;



public interface PersonalDetailsPresenter {

    void actionStatesBtn(String userLoginSessionKey, String countryId);

    void actionViewProfile(LoginInput mLoginInput);

    void actionUpdateProfile(UpdateProfileInput mUpdateProfileInput);
}
