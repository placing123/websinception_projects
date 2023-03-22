package com.mw.fantasy.fcm;

import android.content.Context;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.home.HomeNavigation;
import com.mw.fantasy.UI.matchContest.MatchContestActivity;
import com.mw.fantasy.UI.notifications.NotificationsActivity;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanInput.MatchDetailInput;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.dialog.ProgressDialog;

public class NotificationClickActivity extends BaseActivity implements View {


    private String MatchGUID = "";
    private Context mContext;
    PresenterImpl mMatchPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    public int getLayout() {
        return R.layout.activity_notification_click;
    }

    @Override
    public void init() {

        mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        mMatchPresenter = new PresenterImpl(this, new UserInteractor());

        if (getIntent().hasExtra("MatchGUID")) {
            MatchGUID = getIntent().getStringExtra("MatchGUID");
        }

        if (MatchGUID.equals("")) {
            NotificationsActivity.start(mContext);
            finish();
        } else {
            calltask(MatchGUID);
        }

    }

    private void calltask(String matchGUID) {

        MatchDetailInput mMatchDetailInput = new MatchDetailInput();
        mMatchDetailInput.setMatchGUID(MatchGUID);
        mMatchDetailInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mMatchDetailInput.setParams("Status");
        mMatchPresenter.actionMatchdetail(mMatchDetailInput);
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onMatchSuccess(MatchDetailOutPut responseLogin) {

        hideLoading();
        if (responseLogin != null && responseLogin.getData() != null) {
            if (responseLogin.getData().getStatus() != null && responseLogin.getData().getStatus().equals("Pending")) {
                MatchContestActivity.start(mContext, MatchGUID, responseLogin.getData().getStatus(), true);
            } else if (responseLogin.getData().getStatus() != null && responseLogin.getData().getStatus().equals("Running")) {
                HomeNavigation.start(mContext, responseLogin.getData().getStatus());
            } else if (responseLogin.getData().getStatus() != null && responseLogin.getData().getStatus().equals("Completed")) {
                HomeNavigation.start(mContext, responseLogin.getData().getStatus());
            }
            finish();
        }
    }

    @Override
    public void onMatchFailure(String errMsg) {
        hideLoading();
        NotificationsActivity.start(mContext);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
