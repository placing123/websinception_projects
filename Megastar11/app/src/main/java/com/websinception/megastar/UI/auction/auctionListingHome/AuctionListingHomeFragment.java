package com.websinception.megastar.UI.auction.auctionListingHome;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.SelectMode.SelectModeActivity;
import com.websinception.megastar.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment;
import com.websinception.megastar.UI.draft.matchListing.DraftMatchListingFragment;
import com.websinception.megastar.UI.notifications.NotificationsActivity;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.BasePagerAdapter;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuctionListingHomeFragment extends BaseFragment {

    @Nullable
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.ctv_title)
    CustomTextView mCtvTitle;

    @BindView(R.id.ctv_game_type)
    CustomTextView ctvImageType;

    @Nullable
    @BindView(R.id.notification_counter)
    CustomTextView notification_counter;

    @BindString(R.string.fixtures)
    String fixtures;

    @BindString(R.string.live)
    String live;

    @BindString(R.string.results)
    String results;
    private UserInteractor mUserInteractor;
    private Context mContext;
    private Call<LoginResponseOut> loginResponseOutCall;

    @Optional
    @OnClick(R.id.iv_change_mode)
    void launchSelectPlayMode() {
        SelectModeActivity.start(getActivity());
    }


    @Optional
    @OnClick(R.id.menu)
    void launchNotification() {
        NotificationsActivity.start(getContext());
    }


    public static final int ALL = 1, JOINED = 2;
    private int type;
    private int flag;
    private BasePagerAdapter mViewPagerAdapter;


    @Override
    public int getLayout() {
        type = getArguments().getInt("type");
        flag = getArguments().getInt("flag");
        if (type == ALL)
            return R.layout.fragment_auction_home;
        else
            return R.layout.fragment_auction_home_my;
    }

    @Override
    public void init() {
        mContext = getActivity();

        mUserInteractor = new UserInteractor();


        if (AppSession.getInstance().getPlayMode() == 1) {
            ctvImageType.setText("Auction");
        } else {
            ctvImageType.setText("Gully Fantasy");
        }

        mViewPagerAdapter = new BasePagerAdapter(getChildFragmentManager());
        if (type == ALL) {
            if (flag == 1) {
                mViewPagerAdapter.addFrag(AuctionSeriesListingFragment.newInstance(flag, type, AuctionSeriesListingFragment.FIXTURE), fixtures, 0);
            } else {
                mViewPagerAdapter.addFrag(DraftMatchListingFragment.newInstance(type, AuctionSeriesListingFragment.FIXTURE), fixtures, 0);

            }
            mViewPager.setAdapter(mViewPagerAdapter);
            mViewPager.setOffscreenPageLimit(1);
        } else {
            if (flag == 1) {
                mViewPagerAdapter.addFrag(AuctionSeriesListingFragment.newInstance(flag, type, AuctionSeriesListingFragment.FIXTURE), fixtures, 0);
                mViewPagerAdapter.addFrag(AuctionSeriesListingFragment.newInstance(flag, type, AuctionSeriesListingFragment.LIVE), live, 1);
                mViewPagerAdapter.addFrag(AuctionSeriesListingFragment.newInstance(flag, type, AuctionSeriesListingFragment.COMPLETED), results, 2);
            } else {
                mViewPagerAdapter.addFrag(DraftMatchListingFragment.newInstance(type, AuctionSeriesListingFragment.FIXTURE), fixtures, 0);
                mViewPagerAdapter.addFrag(DraftMatchListingFragment.newInstance(type, AuctionSeriesListingFragment.LIVE), live, 1);
                mViewPagerAdapter.addFrag(DraftMatchListingFragment.newInstance(type, AuctionSeriesListingFragment.COMPLETED), results, 2);
            }
            mViewPager.setAdapter(mViewPagerAdapter);
            mViewPager.setOffscreenPageLimit(3);
            mTabLayout.setupWithViewPager(mViewPager);
            AppUtils.applyFontTabLayoutAuction(getActivity(), mTabLayout);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        if (flag == 1) {
                            mCtvTitle.setText("SELECT A SERIES");
                        } else {
                            mCtvTitle.setText("SELECT A MATCH");
                        }
                        break;
                    case 1:
                        mCtvTitle.setText("SERIES IN PROGRESS");
                        break;
                    case 2:
                        mCtvTitle.setText("SERIES COMPLETED");
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        if (flag == 1) {
            mCtvTitle.setText("SELECT A SERIES");
        } else {
            mCtvTitle.setText("SELECT A MATCH");
        }
        mCtvTitle.setVisibility(type == ALL ? View.VISIBLE : View.INVISIBLE);
        getApiNotification();


    }

    private void getApiNotification() {
        LoginInput mLoginInput = new LoginInput();
        mLoginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        loginResponseOutCall = mUserInteractor.notificationCount(mLoginInput, new IUserInteractor.OnResponseListener() {
            @Override
            public void onSuccess(LoginResponseOut mLoginResponseOut) {
                if (loginResponseOutCall == null && loginResponseOutCall.isCanceled()) {
                    return;
                }
                if (mLoginResponseOut != null) {
                    if (notification_counter != null) {
                        if (Integer.parseInt(mLoginResponseOut.getData().getTotalUnread()) > 0) {
                            notification_counter.setVisibility(View.VISIBLE);
                            notification_counter.setText(mLoginResponseOut.getData().getTotalUnread());
                        } else {
                            notification_counter.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                if (loginResponseOutCall == null && loginResponseOutCall.isCanceled()) {
                    return;
                }
                AppUtils.showToast(mContext, errorMsg);
            }
        });
    }

}
