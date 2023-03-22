package com.mw.fantasy.UI.payTm;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.PaytmInput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.ResponsePayTmDetails;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;

import retrofit2.Call;


/**
 * Created by pintu kumar patil on 13-09-2017.
 */

public class PayTmPresenterImpl implements IPayTmPresenter {

    PayTmView mView;
    IUserInteractor mIUserInteractor;
    Call<LoginResponseOut> responseLoginCall;

    public PayTmPresenterImpl(PayTmView mView, IUserInteractor mIUserInteractor) {
        this.mView = mView;
        this.mIUserInteractor = mIUserInteractor;
    }

    @Override
    public void actionPayTmDetailsBtn(PaytmInput paytmInput) {
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.payTmDetailsFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            mIUserInteractor.payTm(paytmInput, new IUserInteractor.OnPayTmResponseListener() {
                @Override
                public void onSuccess(ResponsePayTmDetails responseLogin) {
                    if (responseLogin != null) {
                        mView.hideLoading();
                        mView.payTmDetailsSuccess(responseLogin);
                    } else {
                        mView.hideLoading();
                        mView.payTmDetailsFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                    mView.payTmDetailsFailure(errorMsg);
                }

            });
        }
    }

    @Override
    public void actionViewProfile(LoginInput mLoginInput) {


        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.onProfileFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            responseLoginCall = mIUserInteractor.viewProfile(mLoginInput, new IUserInteractor.OnResponseListener() {


                @Override
                public void onSuccess(LoginResponseOut loginResponseOut) {
                    if (mView.getContext() != null) {
                        mView.hideLoading();
                        mView.onProfileSuccess(loginResponseOut);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (mView.getContext() != null) {
                        mView.hideLoading();
                        mView.onProfileFailure(errorMsg);
                    }
                }
            });
        }
    }

    @Override
    public void actionPayTmResponseBtn(PaytmInput paytmInput) {
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.payTmResponseFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            mIUserInteractor.payTmResponse(paytmInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    if (responseLogin != null) {
                        mView.hideLoading();
                        mView.payTmResponseSuccess(responseLogin);
                    } else {
                        mView.hideLoading();
                        mView.payTmResponseFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                    mView.payTmResponseFailure(errorMsg);
                }
            });
        }
    }

}
