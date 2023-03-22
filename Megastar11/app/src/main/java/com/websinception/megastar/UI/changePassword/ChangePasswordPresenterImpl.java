package com.websinception.megastar.UI.changePassword;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.ChangePasswordInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.NetworkUtils;


public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {

    ChangePasswordView mChangePasswordView;
    IUserInteractor mIUserInteractor;

    public ChangePasswordPresenterImpl(ChangePasswordView mChangePasswordView, IUserInteractor mIUserInteractor) {
        this.mChangePasswordView = mChangePasswordView;
        this.mIUserInteractor = mIUserInteractor;
    }

    @Override
    public void submitAction(final ChangePasswordInput mChangePasswordInput) {
        if (!NetworkUtils.isNetworkConnected(mChangePasswordView.getContext())) {
            mChangePasswordView.hideLoading();
            mChangePasswordView.loginFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mChangePasswordView.showLoading();
            mIUserInteractor.changePassword(mChangePasswordInput, new IUserInteractor.OnResponseListener() {
                        @Override
                        public void onSuccess(LoginResponseOut responseLogin) {
                            if (responseLogin != null) {
                                mChangePasswordView.loginSuccess(responseLogin);
                            } else {
                                mChangePasswordView.hideLoading();
                                mChangePasswordView.loginFailure(Constant.NULL_DATA_MESSAGE);
                            }
                        }

                        @Override
                        public void onError(String errorMsg) {
                            mChangePasswordView.hideLoading();
                            mChangePasswordView.loginFailure(errorMsg);
                        }

                    });
        }

    }
}
