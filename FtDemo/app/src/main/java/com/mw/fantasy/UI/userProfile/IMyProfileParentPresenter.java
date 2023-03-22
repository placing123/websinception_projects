package com.mw.fantasy.UI.userProfile;


import com.mw.fantasy.beanInput.LoginInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IMyProfileParentPresenter {
    void actionViewProfile(LoginInput mLoginInput);

    void actionUploadUserImage(String userLoginSessionKey, String filePath);

}
