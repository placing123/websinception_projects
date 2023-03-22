package com.websinception.megastar.UI.auction.auctionHome;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.SelectMode.SelectModeActivity;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.BasePagerAdapter;
import com.websinception.megastar.beanOutput.LoginResponseOut;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuctionHomeFragment extends BaseFragment {


    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindString(R.string.fixtures)
    String fixtures;

    @BindString(R.string.live)
    String live;

    @BindString(R.string.results)
    String results;


    private UserInteractor mUserInteractor;
    private Call<LoginResponseOut> loginResponseOutCall;
    private Context mContext;

    @Optional
    @OnClick(R.id.iv_change_mode)
    void launchSelectPlayMode() {
        SelectModeActivity.start(getActivity());
    }


    public static final int ALL = 1, JOINED = 2;
    private int type;
    private BasePagerAdapter mViewPagerAdapter;

    @Override
    public int getLayout() {
        type = getArguments().getInt("type");
        if (type == ALL) {
            return R.layout.fragment_auction_home;
        } else {
            return R.layout.fragment_auction_home_my;
        }

    }

    @Override
    public void init() {
        mContext = getActivity();
        mViewPagerAdapter = new BasePagerAdapter(getChildFragmentManager());
      /*  mViewPagerAdapter.addFrag(AuctionSeriesListingFragment.newInstance(type, AuctionSeriesListingFragment.FIXTURE), fixtures, 0);
        mViewPagerAdapter.addFrag(AuctionSeriesListingFragment.newInstance(type, AuctionSeriesListingFragment.LIVE), live, 1);
        mViewPagerAdapter.addFrag(AuctionSeriesListingFragment.newInstance(type, AuctionSeriesListingFragment.COMPLETED), results, 2);
        */
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mUserInteractor = new UserInteractor();

    }



}
