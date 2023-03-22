package com.mw.fantasy.UI.myContest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mw.fantasy.UI.myMatches.MyMatchesFragment;
import com.mw.fantasy.UI.notifications.NotificationsActivity;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.base.BaseFragment;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.home.HomeNavigation;
import com.mw.fantasy.UI.homeFragment.HomeCricketPresenterImpl;
import com.mw.fantasy.UI.homeFragment.HomeFragmentView;
import com.mw.fantasy.UI.homeFragment.MultipleImageAdapter;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BasePagerAdapter;

import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.ResponseBanner;
import com.mw.fantasy.beanOutput.VersonBean;
import com.mw.fantasy.customView.CircleIndicator;
import com.mw.fantasy.customView.CustomTextView;

import com.mw.fantasy.utility.AppUtils;

public class MyContestListing extends BaseFragment implements HomeFragmentView {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindString(R.string.fixtures)
    String fixtures;
    @BindString(R.string.live)
    String live;
    @BindString(R.string.results)
    String results;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CircleIndicator circleIndicator;

    @BindView(R.id.tab_sportSelector)
    TabLayout tab_sportSelector;


    int gameType = 1;
    private int mPosition = 0;

    private HomeCricketPresenterImpl mMyProfileParentPresenterImpl;
    public BasePagerAdapter mViewPagerAdapter;

    com.mw.fantasy.base.Loader loader;
    Context mContext;

    @BindView(R.id.notification_counter)
    CustomTextView notification_counter;

    @BindView(R.id.recycler_view_tournament_list)
    RecyclerView recyclerViewSeriesList;


    @BindView(R.id.sportSelector)
    CustomTextView sportSelector;

    @OnClick(R.id.menu)
    void onNotificationClick() {

        NotificationsActivity.start(mContext);
        notification_counter.setVisibility(View.GONE);
        AppSession.getInstance().setBadges("0");

    }

    @OnClick(R.id.sportSelector)
    public void onSportSelect() {

        int[] location = new int[2];
        sportSelector.getLocationOnScreen(location);
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];
        // showPopup(getActivity(),p);
    }

    @Override
    public int getLayout() {
        return R.layout.my_contest_fragment;
    }

    @Override
    public void init() {

        if (isAttached()) {
            mContext = getActivity();

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);

            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            if (tab_sportSelector.getTabCount() == 0) {
                tab_sportSelector.addTab(tab_sportSelector.newTab().setIcon(R.drawable.ic_type_crik));
                tab_sportSelector.addTab(tab_sportSelector.newTab().setIcon(R.drawable.ic_type_foot));

                LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.selector_item_tab, null);
                ImageView tabIcon = view.findViewById(R.id.tabIcon);
                CustomTextView snackbar_tv = view.findViewById(R.id.snackbar_tv);
                snackbar_tv.setText("Cricket");
                tabIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_criket));
                tab_sportSelector.getTabAt(0).setCustomView(view);

                LinearLayout view1 = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.selector_item_tab, null);
                ImageView tabIcon1 = view1.findViewById(R.id.tabIcon);
                CustomTextView snackbar_tv1 = view1.findViewById(R.id.snackbar_tv);
                snackbar_tv1.setText("Football");
                tabIcon1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_foot));
                tab_sportSelector.getTabAt(1).setCustomView(view1);


                AppUtils.wrapTabIndicatorToTitle(tab_sportSelector, 100, 100);
                tabset();




            }


            if (AppSession.getInstance() != null) {
                gameType = AppSession.getInstance().getGameType();

                if (gameType == 1) {
                            tab_sportSelector.getTabAt(0).select();
//                            tab_sportSelector.getTabAt(0).setIcon(R.drawable.ic_type_yellow_crik);

                    LinearLayout layout = (LinearLayout) tab_sportSelector.getTabAt(0).getCustomView();
                    ImageView tabIcon = layout.findViewById(R.id.tabIcon);
                    tabIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_yellow_criket));
                    LinearLayout layout1 = (LinearLayout) tab_sportSelector.getTabAt(1).getCustomView();
                    ImageView tabIcon1 = layout1.findViewById(R.id.tabIcon);
                    tabIcon1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_foot));

                } else if (gameType == 2) {
                            tab_sportSelector.getTabAt(1).select();
//                            tab_sportSelector.getTabAt(1).setIcon(R.drawable.ic_type_yellow_foot);

                    LinearLayout layout = (LinearLayout) tab_sportSelector.getTabAt(1).getCustomView();
                    ImageView tabIcon = layout.findViewById(R.id.tabIcon);
                    tabIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_yellow_foot));
                    LinearLayout layout1 = (LinearLayout) tab_sportSelector.getTabAt(0).getCustomView();
                    ImageView tabIcon1 = layout1.findViewById(R.id.tabIcon);
                    tabIcon1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_criket));


                } else if (gameType == 3) {
                    sportSelector.setText(AppUtils.getStrFromRes(R.string.kabaddi));
                }

            }

            //initiate loader
            loader = new com.mw.fantasy.base.Loader(getCurrentView());
            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // mMyProfileParentPresenterImpl.actionBannersList(AppSession.getInstance().getLoginSession().getResponse().getLogin_session_key());
                }
            });

            //view profile calling
            mMyProfileParentPresenterImpl = new HomeCricketPresenterImpl(this, new UserInteractor());
            // mMyProfileParentPresenterImpl.actionBannersList(AppSession.getInstance().getLoginSession().getResponse().getLogin_session_key());
            /*  FIXTURE,LIVE,COMPLETED*/
            mViewPagerAdapter = new BasePagerAdapter(getChildFragmentManager());

            mViewPagerAdapter.addFrag(MyMatchesFragment.getInstance("", "FIXTURE", gameType, 1), fixtures, 0);
            mViewPagerAdapter.addFrag(MyMatchesFragment.getInstance("", "LIVE", gameType, 1), live, 1);
            mViewPagerAdapter.addFrag(MyMatchesFragment.getInstance("", "COMPLETED", gameType, 1), results, 2);
            //FIXTURE,LIVE,COMPLETED
            mViewPager.setAdapter(mViewPagerAdapter);

            mTabLayout.setupWithViewPager(mViewPager);


            mViewPager.setCurrentItem(mPosition);


            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });



        }

        AppUtils.applyFontedMyContestsTab(getActivity(), mViewPager, mTabLayout);
    }

    private void tabset() {
        tab_sportSelector.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        if (AppSession.getInstance().getGameType() != 1) {
                            AppSession.getInstance().setGameType(1);
//                            tab.setIcon(R.drawable.ic_type_yellow_criket);
//                            for (int i = 0; i <tab_sportSelector.getTabCount() ; i++) {
//                                if (i == tab.getPosition()){
//                                    tab_sportSelector.getTabAt(i).setIcon(R.drawable.ic_type_yellow_crik);
//                                }else {
//                                    tab_sportSelector.getTabAt(i).setIcon(R.drawable.ic_type_foot);
//
//                                }
//
//                            }
                        }
                        break;
                    case 1:
                        if (AppSession.getInstance().getGameType() != 2) {
                            AppSession.getInstance().setGameType(2);
//                            tab.setIcon(R.drawable.ic_type_yellow_foot);
//                            for (int i = 0; i <tab_sportSelector.getTabCount() ; i++) {
//                                if (i == tab.getPosition()){
//                                    tab_sportSelector.getTabAt(i).setIcon(R.drawable.ic_type_yellow_foot);
//                                }else {
//                                    tab_sportSelector.getTabAt(i).setIcon(R.drawable.ic_type_crik);
//
//                                }
//
//                            }
                        }
                        break;

                }

                init();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean isAttached() {
        return isAdded() && getActivity() != null;
    }


    @Override
    public void hideLoading() {

        if (isAdded() && getActivity() != null) {
            loader.hide();
        }
    }

    @Override
    public void showLoading() {
        if (isAdded() && getActivity() != null) {
            loader.start();
        }
    }


    @Override
    public void onShowSnackBar(String message) {
        AppUtils.showSnackBar(getActivity(), mCoordinatorLayout, message);
    }

    @Override
    public void onVersonSuccess(VersonBean versionBean) {

    }

    @Override
    public void onNotificationCountSuccess(LoginResponseOut mLoginResponseOut) {

    }

    @Override
    public void onNotificationCountFailure(String errMsg) {

    }

    @Override
    public void onVersonFailed(String message) {

    }

    @Override
    public void onVersonError(String message) {

    }


    @Override
    public void onBannerNotFound(String error) {

        if (isAdded() && getActivity() != null) {
            loader.setNotFoundImage(getContext().getResources().getDrawable(R.drawable.not_found_img));
            loader.dataNotFound(error);
        }
    }

    @Override
    public void onBannerFailure(String error) {

        if (isAdded() && getActivity() != null) {
            loader.error(error);
        }
    }


    @Override
    public void onBannerSuccess(ResponseBanner responseBanner) {

        if (isAdded() && getActivity() != null) {
            loader.hide();
            setPagerAdapter(responseBanner.getData().getRecords());
        }
    }

    private void setPagerAdapter(List<ResponseBanner.DataBean.RecordsBean> beans) {
        MultipleImageAdapter multipleImageAdapter = new MultipleImageAdapter(mContext, beans);
        viewPager.setAdapter(multipleImageAdapter);
        circleIndicator.setViewPager(viewPager);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        ((HomeNavigation) getActivity()).changeNavigationSelction(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        apiCallGetNotificationCount();
    }

    private void apiCallGetNotificationCount(){
        LoginInput mLoginInput = new LoginInput();
        mLoginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        new UserInteractor().notificationCount(mLoginInput, new IUserInteractor.OnResponseListener() {
            @Override
            public void onSuccess(LoginResponseOut user) {
                if (user != null) {
                    if (notification_counter != null) {
                        if (Integer.parseInt(user.getData().getTotalUnread()) > 0) {
                            notification_counter.setVisibility(View.VISIBLE);
                            notification_counter.setText(user.getData().getTotalUnread());
                        } else {
                            notification_counter.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                notification_counter.setVisibility(View.GONE);
            }
        });
    }


}
