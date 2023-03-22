package com.websinception.megastar.UI.withdrawAmount;

import android.support.annotation.NonNull;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.WithdrawInput;
import com.websinception.megastar.beanOutput.WithDrawoutput;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.NetworkUtils;


/**
 * Created by pintu kumar patil on 13-09-2017.
 */

public class WithdrawAmountPresenterImpl implements IWithdrawAmountPresenter {

    WithdrawAmountView mView;
    IUserInteractor mInteractor;

    public WithdrawAmountPresenterImpl(WithdrawAmountView mView, IUserInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    @Override
    public void actionWithdrawAmountBtn(@NonNull WithdrawInput withdrawInput) {
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.withDrawFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            mInteractor.withdrawal_add(withdrawInput, new IUserInteractor.OnwithdrawalResponseListener() {
                @Override
                public void onSuccess(WithDrawoutput mWithDrawoutput) {
                    if (mWithDrawoutput != null) {
                        mView.withDrawSuccess(mWithDrawoutput);
                    } else {
                        mView.hideLoading();
                        mView.withDrawFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }



                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                    mView.withDrawFailure(errorMsg);
                }

                @Override
                public void onNotFound(String error) {

                }

            });
        }
    }
}
