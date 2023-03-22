package com.mw.fantasy.UI.changeUserAvatar;


import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.UpdateProfileInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IUserAvatarPresenter {
    //cricket
    void users_avatar_list(LoginInput loginInput);

    void actionUpdateProfile(UpdateProfileInput mUpdateProfileInput);


}
