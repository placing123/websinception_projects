package com.websinception.megastar.UI.homeFragment;

import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.websinception.megastar.BuildConfig;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.SelectMode.SelectModeActivity;
import com.websinception.megastar.UI.addMoney.AddMoneyActivity;
import com.websinception.megastar.UI.contestInviteCode.InviteCodes;
import com.websinception.megastar.UI.home.HomeNavigation;
import com.websinception.megastar.UI.matches.MatchesFragment;
import com.websinception.megastar.UI.notifications.NotificationsActivity;
import com.websinception.megastar.UI.versionUpdate.UpdateVersionDialogActivity;
import com.websinception.megastar.UI.versionUpdate.UpdateVersionDialogCompalsoryActivity;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.VersionInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.ResponseBanner;
import com.websinception.megastar.beanOutput.VersonBean;
import com.websinception.megastar.customView.CircleIndicator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import com.websinception.megastar.AppSession;


import com.websinception.megastar.appApi.interactors.UserInteractor;

import com.websinception.megastar.base.BasePagerAdapter;

import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.fcm.MyFirebaseMessagingService;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;

public class HomeFragment extends BaseFragment implements HomeFragmentView {

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

    @BindView(R.id.ctv_wallet_amount)
    CustomTextView ctv_wallet_amount;


    @BindView(R.id.tab_sportSelector)
    TabLayout tab_sportSelector;


    @OnClick(R.id.iv_change_mode)
    void launchSelectPlayMode() {
        SelectModeActivity.start(getActivity());
    }

    com.websinception.megastar.dialog.AlertDialog alertLogoutDialog;

    Timer timer;
    String status = "";

    @OnClick(R.id.menu)
    void onNotificationClick() {

        NotificationsActivity.start(mContext);
        notification_counter.setVisibility(View.GONE);
        AppSession.getInstance().setBadges("0");

    }

    @BindView(R.id.notification_counter)
    CustomTextView notification_counter;

    @BindView(R.id.ctv_announcement)
    CustomTextView ctv_announcement;


    @OnClick(R.id.card_view_wallet_root)
    void walletClic() {
        Intent starter = new Intent(getActivity(), AddMoneyActivity.class);
        startActivityForResult(starter, 112);
        ((Activity) getActivity()).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }


    private HomeCricketPresenterImpl mMyProfileParentPresenterImpl;
    public BasePagerAdapter mViewPagerAdapter;

    Loader loader;
    Context mContext;

    int gameType = 1;

    String appLinkData = "";

    String module_type;


    @BindView(R.id.recycler_view_tournament_list)

    RecyclerView recyclerViewSeriesList;


    @BindView(R.id.sportSelector)
    CustomTextView sportSelector;

    @OnClick(R.id.sportSelector)
    public void onSportSelect() {

        int[] location = new int[2];
        sportSelector.getLocationOnScreen(location);
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];
        // showPopup(getActivity(), p);
    }

    @Override
    public int getLayout() {
        return R.layout.home_fragment;
    }

    @Override
    public void init() {
        mHandler = new Handler();
        if (isAttached()) {
            mContext = getActivity();
            //initiate loader
            //   title.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Amarante-Regular.ttf"));

            if (tab_sportSelector.getTabCount() == 0) {
                tab_sportSelector.addTab(tab_sportSelector.newTab().setIcon(R.drawable.ic_type_crik).setText("Cricket"));
                tab_sportSelector.addTab(tab_sportSelector.newTab().setIcon(R.drawable.ic_type_foot).setText("Football"));


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
                    sportSelector.setText(AppUtils.getStrFromRes(R.string.cricket));
                    module_type = "CRICKET";
                    tab_sportSelector.getTabAt(0).select();

                    LinearLayout layout = (LinearLayout) tab_sportSelector.getTabAt(0).getCustomView();
                    ImageView tabIcon = layout.findViewById(R.id.tabIcon);
                    tabIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_yellow_criket));
                    LinearLayout layout1 = (LinearLayout) tab_sportSelector.getTabAt(1).getCustomView();
                    ImageView tabIcon1 = layout1.findViewById(R.id.tabIcon);
                    tabIcon1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_foot));


                } else if (gameType == 2) {
                    tab_sportSelector.getTabAt(1).select();

                    sportSelector.setText(AppUtils.getStrFromRes(R.string.football));
                    module_type = "FOOTBALL";
                    LinearLayout layout = (LinearLayout) tab_sportSelector.getTabAt(1).getCustomView();
                    ImageView tabIcon = layout.findViewById(R.id.tabIcon);
                    tabIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_yellow_foot));
                    LinearLayout layout1 = (LinearLayout) tab_sportSelector.getTabAt(0).getCustomView();
                    ImageView tabIcon1 = layout1.findViewById(R.id.tabIcon);
                    tabIcon1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_criket));

                } else if (gameType == 3) {
                    sportSelector.setText(AppUtils.getStrFromRes(R.string.kabaddi));
                    module_type = "KABBADI";
                }

            }


            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);

            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            viewPager.getLayoutParams().height = ((width) / 4);

            int _4sdp = (int) getResources().getDimension(R.dimen._4sdp);
            int _16sdp = (int) getResources().getDimension(R.dimen._16sdp);
            viewPager.setPadding(_16sdp, 0, _16sdp, 0);
            viewPager.setClipToPadding(false);
            viewPager.setPageMargin(_4sdp);

            viewPager.getLayoutParams().height = ((width) / 4);
            mMyProfileParentPresenterImpl = new HomeCricketPresenterImpl(this, new UserInteractor());


            if (getArguments().containsKey("appLinkData")) {
                appLinkData = getArguments().getString("appLinkData");
                Log.d("appLinkData", appLinkData);

                InviteCodes.start(mContext, "0", appLinkData);
            }

            if (getArguments().containsKey("status")) {
                status = getArguments().getString("status");
            }


            final LoginInput mLoginInput = new LoginInput();
            mLoginInput.setIsActive("Yes");
            mLoginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());

            loader = new Loader(getCurrentView());
            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mMyProfileParentPresenterImpl.actionBannersList(mLoginInput);
                }
            });
            //view profile calling


            mMyProfileParentPresenterImpl.actionBannersList(mLoginInput);

            mMyProfileParentPresenterImpl.actionNotificationCount(mLoginInput);


            // setPagerAdapter();


            /*  FIXTURE,LIVE,COMPLETED*/
            mViewPagerAdapter = new BasePagerAdapter(getChildFragmentManager());
            mViewPagerAdapter.addFrag(MatchesFragment.getInstance("", "FIXTURE", gameType, appLinkData, getArguments()), fixtures, 0);
//            mViewPagerAdapter.addFrag(MatchesFragment.getInstance("", "LIVE", gameType, appLinkData, getArguments()), live, 1);
//            mViewPagerAdapter.addFrag(MatchesFragment.getInstance("", "COMPLETED", gameType, appLinkData, getArguments()), results, 2);
            //FIXTURE,LIVE,COMPLETED
            mViewPager.setAdapter(mViewPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        AppUtils.applyFontedTab(

                getActivity(), mViewPager, mTabLayout);

        VersionInput versionInput = new VersionInput();
        versionInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        versionInput.setUserAppVersion(AppUtils.getVersionCode());
        versionInput.setDeviceType(Constant.ANDROID_PHONE);
        mMyProfileParentPresenterImpl.appVerson(versionInput);


        LocalBroadcastManager.getInstance(mContext).

                registerReceiver(badges_receiver, new IntentFilter(MyFirebaseMessagingService.BADGES_INTENT_FILTER));


    }

    private void tabset() {
        tab_sportSelector.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        if (AppSession.getInstance().getGameType() != 1) {
                            AppSession.getInstance().setGameType(1);
//                            for (int i = 0; i < tab_sportSelector.getTabCount(); i++) {
//
//                                LinearLayout layout  = (LinearLayout)tab_sportSelector.getTabAt(i).getCustomView();
//                                ImageView tabIcon = layout.findViewById(R.id.tabIcon);
//                                if (i == tab.getPosition()) {
//                                    tabIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_yellow_criket));
//                                }else {
//                                    tabIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_foot));
//                                }
//                            }


                        }
                        break;
                    case 1:
                        if (AppSession.getInstance().getGameType() != 2) {
                            AppSession.getInstance().setGameType(2);
//                            for (int i = 0; i < tab_sportSelector.getTabCount(); i++) {
//
//                                LinearLayout layout  = (LinearLayout)tab_sportSelector.getTabAt(i).getCustomView();
//                                ImageView tabIcon = layout.findViewById(R.id.tabIcon);
//                                if (i == tab.getPosition()) {
//                                    tabIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_yellow_foot));
//                                }else {
//                                    tabIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_type_criket));
//                                }
//                            }
                         /*   tab.getCustomView().setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.yellow));

                            TextView tabTwo1 = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_tab, null);
                            tabTwo1.setText("Football");
                            tabTwo1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_type_yellow_foot, 0, 0);
                            tab_sportSelector.getTabAt(1).setCustomView(tabTwo1);*/
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


    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(mContext.getResources().getColor(color), PorterDuff.Mode.SRC_IN));
            }
        }
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
    public void onVersonSuccess(final VersonBean versionBean) {

        if (versionBean.getData().getAndroidAppVersion() != null) {
            if (BuildConfig.VERSION_CODE < Integer.valueOf(versionBean.getData().getAndroidAppVersion())) {
                if (versionBean.getData().getIsAndroidAppUpdateMandatory().equals("No")) {
                    UpdateVersionDialogActivity.start(mContext, versionBean.getData().getAndroidAppFeatures(), versionBean.getData().getAndridAppUrl());


                } else {
                    UpdateVersionDialogCompalsoryActivity.start(mContext, versionBean.getData().getAndroidAppFeatures(), versionBean.getData().getAndridAppUrl());
                }
            }
        }


    }

    @Override
    public void onNotificationCountSuccess(LoginResponseOut mLoginResponseOut) {

        if (mLoginResponseOut != null) {
            if (notification_counter != null) {
                try{
                    if (Integer.parseInt(mLoginResponseOut.getData().getTotalUnread()) > 0) {
                        notification_counter.setVisibility(View.VISIBLE);
                        notification_counter.setText(mLoginResponseOut.getData().getTotalUnread());
                    } else {
                        notification_counter.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    notification_counter.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onNotificationCountFailure(String errMsg) {

        onShowSnackBar(errMsg);

    }

    @Override
    public void onVersonFailed(String message) {
        AppUtils.showSnackBar(getActivity(), mCoordinatorLayout, message);
    }

    @Override
    public void onVersonError(String message) {
        AppUtils.showSnackBar(getActivity(), mCoordinatorLayout, message);
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
        setAnnouncement(null);
        if (isAdded() && getActivity() != null) {
            loader.error(error);
        }
    }


    @Override
    public void onBannerSuccess(ResponseBanner responseBanner) {

        if (isAdded() && getActivity() != null) {
            loader.hide();
            setPagerAdapter(responseBanner.getData().getRecords());
            setAnnouncement(responseBanner.getData().getAnnouncement());

           /* if (notification_counter != null) {
                if (Integer.parseInt(AppSession.getInstance().getBadges()) > 0) {
                    notification_counter.setVisibility(View.VISIBLE);
                    notification_counter.setText(AppSession.getInstance().getBadges());
                } else {
                    notification_counter.setVisibility(View.GONE);
                }
            }*/
        }
    }

    private void setPagerAdapter(final List<ResponseBanner.DataBean.RecordsBean> beans) {

       /* Gson gson = new Gson();
        String jsonStates = null;
        try {
            jsonStates = AppUtils.AssetJSONFile("banners.json", mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("jsonStatesS",jsonStates);
        ResponseBanner mStatesBean = gson.fromJson(jsonStates, ResponseBanner.class);*/

        MultipleImageAdapter multipleImageAdapter = new MultipleImageAdapter(mContext, beans);
        viewPager.setAdapter(multipleImageAdapter);

        if (circleIndicator != null)
            circleIndicator.setViewPager(viewPager);


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                try {
                    int NUM_PAGES = beans.size()==0?0:beans.size()+100;
                    int currentPage = viewPager.getCurrentItem();
                    if (currentPage == NUM_PAGES - 1) {
                        currentPage = -1;
                    }
                    viewPager.setCurrentItem(currentPage + 1, true);
                } catch (Exception e) {

                }

            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

    }

  /*  // The method that displays the popup.
    private void showPopup(final Activity context, Point p) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.game_popup, viewGroup);

        int popupWidth = width;
        int popupHeight = height / 4;

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 60;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.
        final LinearLayout cricketLin = (LinearLayout) layout.findViewById(R.id.cricketLin);
        final LinearLayout footballLin = (LinearLayout) layout.findViewById(R.id.footballLin);
        final LinearLayout kabaddiLin = (LinearLayout) layout.findViewById(R.id.kabaddiLin);

        if (AppSession.getInstance().getGameType() == 1) {
            cricketLin.setBackground(getResources().getDrawable(R.drawable.border_green_bg_white));
            footballLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
            kabaddiLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
        }
        if (AppSession.getInstance().getGameType() == 2) {
            cricketLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
            footballLin.setBackground(getResources().getDrawable(R.drawable.border_green_bg_white));
            kabaddiLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
        }
        if (AppSession.getInstance().getGameType() == 3) {
            cricketLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
            footballLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
            kabaddiLin.setBackground(getResources().getDrawable(R.drawable.border_green_bg_white));
        }

        cricketLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cricketLin.setBackground(getResources().getDrawable(R.drawable.border_green_bg_white));
                footballLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
                kabaddiLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));

                AppSession.getInstance().setGameType(1);

                sportSelector.setText(AppUtils.getStrFromRes(R.string.cricket));
                module_type = "CRICKET";

                popup.dismiss();
                getArguments().remove("appLinkData");
                init();
            }
        });
        footballLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cricketLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
                footballLin.setBackground(getResources().getDrawable(R.drawable.border_green_bg_white));
                kabaddiLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
                AppSession.getInstance().setGameType(2);
                sportSelector.setText(AppUtils.getStrFromRes(R.string.football));
                module_type = "FOOTBALL";
                getArguments().remove("appLinkData");
                popup.dismiss();
                init();
            }
        });
        kabaddiLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cricketLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
                footballLin.setBackground(getResources().getDrawable(R.drawable.border_blue_bg_white_5));
                kabaddiLin.setBackground(getResources().getDrawable(R.drawable.border_green_bg_white));
                AppSession.getInstance().setGameType(3);
                sportSelector.setText(AppUtils.getStrFromRes(R.string.kabaddi));
                module_type = "KABADDI";
                getArguments().remove("appLinkData");
                popup.dismiss();
                init();
            }
        });


    }*/

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((HomeNavigation) getActivity()).changeNavigationSelction(0);
    }

    private BroadcastReceiver badges_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            try {
                if (notification_counter != null && i.getAction() != null && i.getAction().equals(MyFirebaseMessagingService.BADGES_INTENT_FILTER)) {
                    if (i.hasExtra("badges")) {
                        if (Integer.parseInt(i.getStringExtra("badges")) > 0) {
                            notification_counter.setVisibility(View.VISIBLE);
                            notification_counter.setText(AppSession.getInstance().getBadges());
                        } else {
                            notification_counter.setVisibility(View.GONE);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onDestroyView() {
        if (badges_receiver != null) {
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(badges_receiver);
        }
        super.onDestroyView();
    }

   /* public void showMaterialDialog(Context mContext,int icon,String title,String disCription,String postiveText,String nigativeText){

        final MaterialStyledDialog.Builder dialogHeader_1 = new MaterialStyledDialog.Builder(mContext)
                .setIcon(icon)
                .withDialogAnimation(true)
                .setTitle(title)
                .setDescription(disCription)
                .setHeaderColor(R.color.colorPrimary)
                .setPositiveText(postiveText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .setNegativeText(nigativeText);

        dialogHeader_1.show();
    }*/

   /* private void setPagerAdapter() {

        Gson gson = new Gson();
        String jsonStates = null;
        try {
            jsonStates = AppUtils.AssetJSONFile("banners.json", mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("jsonStatesS",jsonStates);
        ResponseBanner mStatesBean = gson.fromJson(jsonStates, ResponseBanner.class);

        MultipleImageAdapter multipleImageAdapter = new MultipleImageAdapter(mContext, mStatesBean.getData().getRecords());
        viewPager.setAdapter(multipleImageAdapter);

        if (circleIndicator != null)
            circleIndicator.setViewPager(viewPager);
    }*/


    private List<ResponseBanner.DataBean.AnnouncementBean> mAnnouncement;
    private int index = 0;
    private Handler mHandler;
    private Animation animFadein;


    private void setAnnouncement(List<ResponseBanner.DataBean.AnnouncementBean> announcement) {
        mAnnouncement = announcement;
        animFadein = AnimationUtils.loadAnimation(mContext,
                R.anim.announcment_anim);
        if (announcement != null && announcement.size() != 0) {
            index = 0;
            ctv_announcement.setVisibility(View.VISIBLE);
            mHandler.post(mRunnableAnnouncment);
        } else {
            ctv_announcement.setVisibility(View.GONE);
        }
    }

    private Runnable mRunnableAnnouncment = new Runnable() {
        @Override
        public void run() {
            if (mAnnouncement != null && mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (index == mAnnouncement.size() - 1) {
                                index = 0;
                            } else {
                                index++;
                            }
                            if (ctv_announcement != null) {
                                ctv_announcement.setText(mAnnouncement.get(index).getMessage());
                                ctv_announcement.startAnimation(animFadein);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                mHandler.postDelayed(mRunnableAnnouncment, 10 * 1000);
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        apiCallGetUserDetails();
    }

    private void apiCallGetUserDetails() {
        LoginInput mLoginInput = new LoginInput();
        mLoginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mLoginInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
        mLoginInput.setParams(Constant.GET_PROFILE_PARAMS);
        new UserInteractor().viewProfile(mLoginInput, new IUserInteractor.OnResponseListener() {
            @Override
            public void onSuccess(final LoginResponseOut user) {
                if (user.getData().getTotalCash()!=null) {
                    if (ctv_wallet_amount!=null) {
                        ctv_wallet_amount.post(new Runnable() {
                            @Override
                            public void run() {
                                ctv_wallet_amount.setText(user.getData().getTotalCash());
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(String errorMsg) { }
        });
    }

}
