package com.websinception.megastar.UI.selectSport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.home.HomeNavigation;
import com.websinception.megastar.base.BaseActivity;

import butterknife.OnClick;

public class SelectSportActivity extends BaseActivity {



    @OnClick(R.id.iv_football)
    void selectFootballOnClick(){
        AppSession.getInstance().setGameType(2);
        HomeNavigation.start(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_select_sport;
    }

    public static void start(Context context) {

        Intent starter = new Intent(context, SelectSportActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.alert_open, R.anim.dialog_close);
    }

    @Override
    public void init() {

    }
}
