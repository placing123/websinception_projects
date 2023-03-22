package com.mw.fantasy.UI.addMoney;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.PromoCodeInput;
import com.mw.fantasy.beanInput.PromoCodeListInput;
import com.mw.fantasy.beanOutput.PromoCodeListOutput;
import com.mw.fantasy.beanOutput.PromoCodeResponse;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;



/**
 *
 */

public class AddMoneyPresenterImpl implements IAddMoneyPresenter {

    AddMoneyView mView;
    IUserInteractor mIUserInteractor;

    public AddMoneyPresenterImpl(AddMoneyView mView, IUserInteractor mIUserInteractor) {
        this.mView = mView;
        this.mIUserInteractor = mIUserInteractor;
    }


    @Override
    public void actionPayUMoneyResponseBtn(String loginSessionKey, String response) {
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.payUMoneyResponseFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
          /*  mIUserInteractor.payUMoneyResponse(loginSessionKey, response, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    if (responseLogin != null) {
                        mView.hideLoading();
                        mView.payUMoneyResponseSuccess(responseLogin);
                    } else {
                        mView.hideLoading();
                        mView.payUMoneyResponseFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                    mView.payUMoneyResponseFailure(errorMsg);
                }

            });*/
        }
    }

    @Override
    public void promoCodeBtn(PromoCodeInput promoCodeInput) {

        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.promoCodeFaliure(AppUtils.getStrFromRes(R.string.network_error));
        }else {
            mView.showLoading();
            mIUserInteractor.promoCode(promoCodeInput, new IUserInteractor.OnPromoCodeResponseListener() {
                @Override
                public void onSuccess(PromoCodeResponse mPromoCodeResponse) {
                    if (mPromoCodeResponse != null) {
                        mView.hideLoading();
                        mView.promoCodeSuccess(mPromoCodeResponse);
                    } else {
                        mView.hideLoading();
                        mView.promoCodeFaliure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                    mView.promoCodeFaliure(errorMsg);
                }
            });
        }
    }

    @Override
    public void actionPromoCodeList(PromoCodeListInput mPromoCodeListInput) {

        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.promoCodeFaliure(AppUtils.getStrFromRes(R.string.network_error));
        }else {
            mView.showLoading();
            mIUserInteractor.promocodeList(mPromoCodeListInput, new IUserInteractor.OnRequestPromoCodeListListener() {
                @Override
                public void onSuccess(PromoCodeListOutput mPromoCodeListOutput) {

                    if (mPromoCodeListOutput != null) {
                        mView.hideLoading();
                        mView.promocodeListSuccess(mPromoCodeListOutput);
                    }else {
                        mView.hideLoading();
                        mView.promocodeListFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                    mView.promocodeListFailure(errorMsg);
                }
            });
        }
    }
}
