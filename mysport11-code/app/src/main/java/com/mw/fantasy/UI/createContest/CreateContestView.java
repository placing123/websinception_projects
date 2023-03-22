package com.mw.fantasy.UI.createContest;

import android.content.Context;

import com.mw.fantasy.beanOutput.CreateContestOutput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.WinBreakupOutPut;


/**
 * Created by hp on 06-07-2017.
 */

public interface CreateContestView {
    void showLoading();

    void hideLoading();

    void createContestSuccess(CreateContestOutput mCreateContestOutput);

    void createContestFailure(String errMsg);

    void winBreakupSuccess(WinBreakupOutPut responseLogin);

    void winBreakupFailure(String errMsg);

    void onProfileSuccess(LoginResponseOut responseLogin);

    void onProfileFailure(String errMsg);


    void onShowLoading();

    void onHideLoading();

    void showSnackBar(String message);

    void setActivityBackground();


    Context getContext();

    void finishActivity();
}
