package com.websinception.megastar.UI.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.Fragments.VerifyAaadharFragment;
import com.websinception.megastar.UI.bankVerify.VerifyBankFragment;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.BasePagerAdapter;
import com.websinception.megastar.utility.AppUtils;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankNAadhaarVerifyParentFragment extends BaseFragment {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    BasePagerAdapter mViewPagerAdapter;


    public static BankNAadhaarVerifyParentFragment newInstance() {
        BankNAadhaarVerifyParentFragment fragment = new BankNAadhaarVerifyParentFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_bank_naadhaar_verify_parent;
    }

    @Override
    public void init() {
        mViewPagerAdapter = new BasePagerAdapter(getChildFragmentManager());

        mViewPagerAdapter.addFrag(VerifyBankFragment.getInstance(), AppUtils.getStrFromRes(R.string.Bank_Verification), 0);
        //mViewPagerAdapter.addFrag(VerifyAaadharFragment.newInstance(), AppUtils.getStrFromRes(R.string.Aadhaar_Verification), 1);
        //FIXTURE,LIVE,COMPLETED
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        AppUtils.applyFontedMyContestTab(getActivity(), mViewPager, mTabLayout);
    }

    public void picPath(String path) {
        if (mViewPagerAdapter != null) {
            if (mViewPagerAdapter.getItem(mViewPager.getCurrentItem()) instanceof VerifyBankFragment) {
                ((VerifyBankFragment) mViewPagerAdapter.getItem(mViewPager.getCurrentItem())).bankPicture(path);
            } else if (mViewPagerAdapter.getItem(mViewPager.getCurrentItem()) instanceof VerifyAaadharFragment) {
                ((VerifyAaadharFragment) mViewPagerAdapter.getItem(mViewPager.getCurrentItem())).aadharPic(path);
            }
        }
    }
}
