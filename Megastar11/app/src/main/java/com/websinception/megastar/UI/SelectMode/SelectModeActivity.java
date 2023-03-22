package com.websinception.megastar.UI.SelectMode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.home.HomeNavigation;
import com.websinception.megastar.base.BaseActivity;

import butterknife.OnClick;

public class SelectModeActivity extends BaseActivity {

    @OnClick(R.id.ll_daily_fantasy)
    void launchDailyFantasy() {
        AppSession.getInstance().setPlayMode(0);
//        SelectSportActivity.start(this);
        HomeNavigation.start(this);
    }

    @OnClick(R.id.ll_auction)
    void launchAuction() {
        AppSession.getInstance().setPlayMode(1);
        HomeNavigation.start(this);
    }

    @OnClick(R.id.ll_snack_draft)
    void launchSnackDraft() {
        //AppUtils.showToast(this,"Coming soon!");
        AppSession.getInstance().setPlayMode(2);
        HomeNavigation.start(this);
    }

    public static void start(Context context) {

      /*  if (!AppSession.getInstance().isWalkThroughShown()) {
            WalkThroughActivity.start(context);
            return;
        }*/
        AppSession.getInstance().setPlayMode(0);
        HomeNavigation.start(context);

       /* Intent starter = new Intent(context, HomeNavigation.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.alert_open, R.anim.dialog_close);*/
    }

    @Override
    public int getLayout() {
        return R.layout.activity_select_mode;
    }

    @Override
    public void init() {

    }
}
