package com.mw.fantasy.UI.walkThrough;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.SelectMode.SelectModeActivity;
import com.mw.fantasy.UI.startup.StartupActivity;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.customView.CircleIndicator;

import butterknife.BindView;
import butterknife.OnClick;

public class WalkThroughActivity extends BaseActivity {

    @BindView(R.id.indicator)
    CircleIndicator mCi_Indicator;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @OnClick(R.id.ctv_skip)
    void skipBtnClick() {
        AppSession.getInstance().setWalkThroughShown(true);
        openNext();
    }

    @OnClick(R.id.ctv_next)
    void nextBtnClick() {
        if (mViewPager.getCurrentItem() + 1 == mViewPager.getAdapter().getCount()) {
            AppSession.getInstance().setWalkThroughShown(true);
            openNext();
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, WalkThroughActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_walk_through;
    }

    @Override
    public void init() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return WalkThroughFragment.newInstance(i);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        mCi_Indicator.setViewPager(mViewPager);
    }

    private void openNext(){
        AppSession mAppSession = AppSession.getInstance();
        if (mAppSession.getLoginSession() == null) {
            StartupActivity.start(this);
            finish();
        } else {
            if (AppSession.getInstance().getLoginSession().getResponseCode() == 200 /*&& !AppSession.getInstance().getLoginSession().getData().getPhoneNumber().equals("")*/) {
                SelectModeActivity.start(this);
            } else {
                StartupActivity.start(this);

            }
        }
        finish();
    }
}
