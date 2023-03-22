package com.websinception.megastar.fcm;

import android.content.Context;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.home.HomeNavigation;
import com.websinception.megastar.UI.matchContest.MatchContestActivity;
import com.websinception.megastar.UI.notifications.NotificationsActivity;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanInput.MatchDetailInput;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;
import com.websinception.megastar.dialog.ProgressDialog;

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
