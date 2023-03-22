package com.websinception.megastar.UI.changeUserAvatar;


import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.UpdateProfileInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IUserAvatarPresenter {
    //cricket
    void users_avatar_list(LoginInput loginInput);

    void actionUpdateProfile(UpdateProfileInput mUpdateProfileInput);


}
