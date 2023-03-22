package com.websinception.megastar.UI.userProfile;


import com.websinception.megastar.beanInput.LoginInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IMyProfileParentPresenter {
    void actionViewProfile(LoginInput mLoginInput);

    void actionUploadUserImage(String userLoginSessionKey, String filePath);

}
