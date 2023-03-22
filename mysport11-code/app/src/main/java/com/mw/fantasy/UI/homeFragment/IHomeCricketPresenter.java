package com.mw.fantasy.UI.homeFragment;


import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.VersionInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IHomeCricketPresenter {
    void actionBannersList(LoginInput mLoginInput);

    void appVerson(VersionInput versionInput);

    void actionNotificationCount(LoginInput mLoginInput);
}
