package com.mw.fantasy.UI.personalDetails;

import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.UpdateProfileInput;



public interface PersonalDetailsPresenter {

    void actionStatesBtn(String userLoginSessionKey, String countryId);

    void actionViewProfile(LoginInput mLoginInput);

    void actionUpdateProfile(UpdateProfileInput mUpdateProfileInput);
}
