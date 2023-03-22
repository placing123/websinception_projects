package com.websinception.megastar.UI.myAccount;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.WalletInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.WalletOutputBean;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;


import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class MyAccountParentPresenterImpl implements IMyAccountParentPresenter {

    MyAccountParentView mMyAccountParentView;
    IUserInteractor mImyAccountParentInteractor;
    Call<WalletOutputBean> responseLoginCall;
    Call<LoginResponseOut> responseProfileCall;

    public MyAccountParentPresenterImpl(MyAccountParentView mMyAccountParentView, IUserInteractor mImyAccountParentInteractor) {
        this.mMyAccountParentView = mMyAccountParentView;
        this.mImyAccountParentInteractor = mImyAccountParentInteractor;
    }

    public void actionLoginCancel() {
        if (responseLoginCall != null && !responseLoginCall.isExecuted())
            responseLoginCall.cancel();
    }

    @Override
    public void actionViewAccount(WalletInput mWalletInput) {
        actionLoginCancel();
        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.hideLoading();
            mMyAccountParentView.onAccountFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.showLoading();
            responseLoginCall = mImyAccountParentInteractor.viewAccount(mWalletInput, new IUserInteractor.OnResponseAccountListener() {
                @Override
                public void onSuccess(WalletOutputBean mResponseAccount) {
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.hideLoading();
                        mMyAccountParentView.onAccountSuccess(mResponseAccount);
                    }

                }

                @Override
                public void onError(String errorMsg) {
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.hideLoading();
                        mMyAccountParentView.onAccountFailure(errorMsg);
                    }
                }

            });
        }
    }


}
