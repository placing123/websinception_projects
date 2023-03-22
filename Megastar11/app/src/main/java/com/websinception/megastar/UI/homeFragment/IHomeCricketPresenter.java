package com.websinception.megastar.UI.homeFragment;


import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.VersionInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IHomeCricketPresenter {
    void actionBannersList(LoginInput mLoginInput);

    void appVerson(VersionInput versionInput);

    void actionNotificationCount(LoginInput mLoginInput);
}
