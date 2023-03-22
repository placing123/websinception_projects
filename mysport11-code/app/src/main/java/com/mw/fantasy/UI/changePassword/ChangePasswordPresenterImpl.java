package com.mw.fantasy.UI.changePassword;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.ChangePasswordInput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;


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
