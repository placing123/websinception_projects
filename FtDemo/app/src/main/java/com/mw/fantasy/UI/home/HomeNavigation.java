package com.mw.fantasy.UI.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.SpinWheel.SpinWheelActivity;
import com.mw.fantasy.UI.auction.auctionListingHome.AuctionListingHomeFragment;
import com.mw.fantasy.UI.homeFragment.HomeFragment;
import com.mw.fantasy.UI.more.MoreFragment;
import com.mw.fantasy.UI.myContest.MyContestListing;
import com.mw.fantasy.UI.popup.WelcomeDialogActivity;
import com.mw.fantasy.UI.subscription.SubscriptionListActivity;
import com.mw.fantasy.UI.userProfile.UserProfileFragment;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanOutput.PopupData;
import com.mw.fantasy.beanOutput.SpinWheelOutput;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.BottomNavigationViewHelper;
import com.mw.fantasy.utility.NetworkUtils;

import butterknife.OnClick;

public class HomeNavigation extends BaseActivity {

    private TextView mTextMessage;
    private BottomNavigationView navigation;
    Boolean flag;
    String notificationStatus = "";


    String[] fragmentIndex = new String[4];
    private Context mContext;
    private UserInteractor mInteractor;
    private String notificationId;




    public static void start(Context context) {

        Intent starter = new Intent(context, HomeNavigation.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.alert_open, R.anim.dialog_close);
    }


    public static void startHome(Context context, Boolean flag) {

        Intent starter = new Intent(context, HomeNavigation.class);
        starter.putExtra("Ranking", flag);
        context.startActivity(starter);
    }

    public static void start(Context context, String notificationStatus) {
        Intent starter = new Intent(context, HomeNavigation.class);
        starter.putExtra("notificationStatus", notificationStatus);
        context.startActivity(starter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle b = new Bundle();

            if (getIntent().hasExtra("appLinkData")) {
                b.putString("appLinkData", getIntent().getStringExtra("appLinkData"));
                getIntent().removeExtra("appLinkData");
            }
            if (getIntent().hasExtra("contestId")) {
                b.putBundle("dataExtra", getIntent().getExtras());
            }
            if (getIntent().hasExtra("notificationStatus")) {
                b.putString("status", getIntent().getStringExtra("notificationStatus"));
            }

            fragmentIndex[0] = AppUtils.getStrFromRes(R.string.home);
            fragmentIndex[1] = AppUtils.getStrFromRes(R.string.myContest);
            fragmentIndex[2] = AppUtils.getStrFromRes(R.string.me);
            fragmentIndex[3] = AppUtils.getStrFromRes(R.string.more);

            int playMode = AppSession.getInstance().getPlayMode();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.home);
                    mTextMessage.setText(AppSession.getInstance().getLoginSession().getData().getEmail());
                    fragmentIndex[0] = AppUtils.getStrFromRes(R.string.home);
                    if (playMode == 0) {
                        setFragment(new HomeFragment(), AppUtils.getStrFromRes(R.string.home), b);
                    } else if (playMode == 1 || playMode == 2) {
                        b.putInt("flag", playMode);
                        b.putInt("type", AuctionListingHomeFragment.ALL);
                        setFragment(new AuctionListingHomeFragment(), AppUtils.getStrFromRes(R.string.home), b);
                    }


                    return true;
                case R.id.navigation_contest:
                    mTextMessage.setText(R.string.myContest);
                    if (playMode == 0) {
                        setFragment(new MyContestListing(), AppUtils.getStrFromRes(R.string.contest), b);
                    } else if (playMode == 1 || playMode == 2) {
                        b.putInt("flag", playMode);
                        b.putInt("type", AuctionListingHomeFragment.JOINED);
                        setFragment(new AuctionListingHomeFragment(), AppUtils.getStrFromRes(R.string.contest), b);
                    }
                    fragmentIndex[1] = AppUtils.getStrFromRes(R.string.myContest);
                    return true;
                case R.id.navigation_me:
                    mTextMessage.setText(R.string.me);

                    b = new Bundle();
                    setFragment(new UserProfileFragment(), AppUtils.getStrFromRes(R.string.fullProfile), b);
                    fragmentIndex[2] = AppUtils.getStrFromRes(R.string.me);

                    return true;
                case R.id.navigation_more:
                    mTextMessage.setText(R.string.more);
                    b = new Bundle();
                    setFragment(new MoreFragment(), AppUtils.getStrFromRes(R.string.more), b);
                    fragmentIndex[3] = AppUtils.getStrFromRes(R.string.more);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_navigation);
        mContext = this;


        mInteractor = new UserInteractor();

        findViewById(R.id.card_View_subscription).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubscriptionListActivity.start(mContext);
            }
        });
        //AppUtils.setStatusBarGradiant(this);
        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationViewHelper.disableShiftMode(navigation);

        // ATTENTION: This was auto-generated to handle app links.

        Bundle b = new Bundle();

        Intent appLinkIntent = getIntent();

        String appLinkAction = appLinkIntent.getAction();

        Uri appLinkData = appLinkIntent.getData();
        if (appLinkData != null) {

            String[] separated = appLinkData.toString().split("ccode=");
            //   Toast.makeText(this, separated[1], Toast.LENGTH_SHORT).show();

            b.putString("appLinkData", separated[1]);
        }
        int playMode = AppSession.getInstance().getPlayMode();
        if (playMode == 0) {
            setFragment(new HomeFragment(), AppUtils.getStrFromRes(R.string.app_name), b);
        } else if (playMode == 1) {
            b.putInt("flag", playMode);
            b.putInt("type", AuctionListingHomeFragment.ALL);
            setFragment(new AuctionListingHomeFragment(), AppUtils.getStrFromRes(R.string.app_name), b);
        } else if (playMode == 2) {
            b.putInt("flag", playMode);
            b.putInt("type", AuctionListingHomeFragment.ALL);
            setFragment(new AuctionListingHomeFragment(), AppUtils.getStrFromRes(R.string.app_name), b);
        }

        flag = getIntent().getBooleanExtra("Ranking", false);
        if (flag) {
            navigation.setSelectedItemId(R.id.navigation_me);
            // setFragment(new UserProfileFragment(), AppUtils.getStrFromRes(R.string.app_name), b);
            flag = false;
        }

        if (getIntent().hasExtra("notificationStatus")) {
            notificationStatus = getIntent().getStringExtra("notificationStatus");
        }

        //apiCallGetSpinData();
        apiCallPopData();
//        if (notificationStatus.equals("Live") || notificationStatus.equals("Completed")) {
//            navigation.setSelectedItemId(R.id.navigation_home);
//        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_home_navigation;
    }

    @Override
    public void init() {


        /*ResponseLogin.ResponseBean responce = new ResponseLogin.ResponseBean();
        responce=   AppSession.getInstance().getLoginSession().getResponse();
        AppSession.getInstance().getLoginSession();
        AppSession.getInstance().getLoginSession().getResponse();
        AppSession.getInstance().getLoginSession().getResponse().getLoginSessionKey();

        SharedPreferences sharedPreferences = getSharedPreferences("Preference" + this.getPackageName(), Context.MODE_PRIVATE);

       ResponseLogin responseLogin = new Gson().fromJson(sharedPreferences.
                getString(Constant.RESPONSE_LOGIN,
                        ""), ResponseLogin.class);

        AppSession.getInstance().setLoginSession(responseLogin);
        Log.d("setLoginSession",AppSession.getInstance().getLoginSession().getResponse().getLoginSessionKey());*/

        // jkhihkj

    }

    private void setFragment(final android.support.v4.app.Fragment fragment, final String fragmentName, final Bundle b) {
        try {
            if (fragment != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Getting reference to the FragmentManager
                            FragmentManager fragmentManager = getSupportFragmentManager();

                            // Creating a fragment transaction
                            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();

                            // Adding a fragment to the fragment transaction
                            fragment.setArguments(b);
                            ft.replace(R.id.frame_container, fragment, fragmentName);

                            // clear back fragments
                            ft.addToBackStack(fragmentName);
                            // Committing the transaction
                            ft.commitAllowingStateLoss();
                            ft.commit();
                        } catch (IllegalStateException e) {
                            //e.printStackTrace();
                        }
                    }
                }, 200);
            } else {
                // error in creating fragment
                Log.e("UserProfileFragment", "Error in creating fragment");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            fragmentManager.popBackStack();


             /* Fragment homeFrgment= fragmentManager.findFragmentByTag(AppUtils.getStrFromRes(R.string.home));
            Fragment myContestFragment= fragmentManager.findFragmentByTag(AppUtils.getStrFromRes(R.string.contest));
            Fragment userProfile= fragmentManager.findFragmentByTag(AppUtils.getStrFromRes(R.string.fullProfile));
            Fragment moreFragment= fragmentManager.findFragmentByTag(AppUtils.getStrFromRes(R.string.more));

            Menu menuItem = navigation.getMenu();

            if (homeFrgment != null && homeFrgment.isVisible()) {
                // add your code here


                menuItem.getItem(0).setChecked(true);

            }
            if (myContestFragment != null && myContestFragment.isVisible()) {
                // add your code here

                menuItem.getItem(1).setChecked(true);
            }
            if (userProfile != null && userProfile.isVisible()) {
                // add your code here
                menuItem.getItem(2).setChecked(true);

            }
            if (moreFragment != null && moreFragment.isVisible()) {
                // add your code here
                menuItem.getItem(3).setChecked(true);

            }*/


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1001) {
            notificationId = data.getStringExtra("notificationID");
            apiCallsubmitData(notificationId);
        }
        /*UserProfileFragment fragment = new UserProfileFragment();
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }*/

    }


    public void changeNavigationSelction(int index) {


/*
            if((fragmentIndex!=null && index < fragmentIndex.length) ){

                Log.d("fragmentIndex", "--"+fragmentIndex.length+"--"+index+"--"+fragmentIndex[index]);

                if(fragmentIndex[index].equals(AppUtils.getStrFromRes(R.string.home))){
                    navigation.getMenu().getItem(0).setChecked(true);
                }
                if(fragmentIndex[index].equals(AppUtils.getStrFromRes(R.string.myContest))){
                    navigation.getMenu().getItem(1).setChecked(true);
                }
                if(fragmentIndex[index].equals(AppUtils.getStrFromRes(R.string.me))){
                    navigation.getMenu().getItem(2).setChecked(true);
                }
                if(fragmentIndex[index].equals(AppUtils.getStrFromRes(R.string.more))){
                    navigation.getMenu().getItem(3).setChecked(true);
                }


            }*/


        /*if(navigation.getMenu().size()>index & index!=0) {
            navigation.getMenu().getItem(index-1).setChecked(true);
        }*/
    }



    private void apiCallGetSpinData() {
        if (NetworkUtils.isConnected(mContext)) {

            LoginInput loginInput = new LoginInput();
            loginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            mInteractor.getSpinWheelData(loginInput, new IUserInteractor.OnResponseSpinWheeListener() {

                @Override
                public void onSuccess(SpinWheelOutput spinWheelOutput) {

                    if (spinWheelOutput.getData().getIsPlay().equalsIgnoreCase("Yes")
                            && spinWheelOutput.getData().getLuckyWheelActive().equalsIgnoreCase("Yes")) {
                        SpinWheelActivity.start(mContext, true);
                    }
                }

                @Override
                public void onNotFound(String error) {

                }

                @Override
                public void onError(String errorMsg) {

                }

                @Override
                public void OnSessionExpire() {

                }
            });
        }
    }

    private void apiCallPopData() {
        if (NetworkUtils.isConnected(mContext)) {

            LoginInput loginInput = new LoginInput();
            loginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            mInteractor.getPopupData(loginInput, new IUserInteractor.OnResponsePopupListener() {

                @Override
                public void onSuccess(PopupData spinWheelOutput) {

                  /*  WelcomeDialogActivity.start(mContext, spinWheelOutput.getData().getRecords().getNotificationMessage(),
                            spinWheelOutput.getData().getRecords().getNotificationText(), spinWheelOutput.getData().getRecords().getNotificationID());*/

                }

                @Override
                public void onNotFound(String error) {

                }

                @Override
                public void onError(String errorMsg) {

                }

                @Override
                public void OnSessionExpire() {

                }
            });
        }
    }


    private void apiCallsubmitData(String notificationId) {
        if (NetworkUtils.isConnected(mContext)) {
            LoginInput loginInput = new LoginInput();
            loginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            loginInput.setNotificationID(notificationId);
            mInteractor.updateData(loginInput, new IUserInteractor.OnResponsePopupListener() {

                @Override
                public void onSuccess(PopupData spinWheelOutput) {

                  /*  WelcomeDialogActivity.start(mContext, spinWheelOutput.getData().getRecords().getNotificationMessage(),
                            spinWheelOutput.getData().getRecords().getNotificationText(), spinWheelOutput.getData().getRecords().getNotificationID());*/

                }

                @Override
                public void onNotFound(String error) {

                }

                @Override
                public void onError(String errorMsg) {

                }

                @Override
                public void OnSessionExpire() {

                }
            });
        }
    }

}
