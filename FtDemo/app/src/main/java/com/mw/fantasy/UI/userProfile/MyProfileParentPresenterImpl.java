package com.mw.fantasy.UI.userProfile;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;


import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class MyProfileParentPresenterImpl implements IMyProfileParentPresenter {

    MyProfileParentView mMyProfileParentView;
    IUserInteractor mImyProfileParentInteractor;

    public MyProfileParentPresenterImpl(MyProfileParentView mMyProfileParentView, IUserInteractor mImyProfileParentInteractor) {
        this.mMyProfileParentView = mMyProfileParentView;
        this.mImyProfileParentInteractor = mImyProfileParentInteractor;
    }

    Call<LoginResponseOut> responseLoginCall;

    public void actionLoginCancel() {
        if (responseLoginCall != null && !responseLoginCall.isExecuted())
            responseLoginCall.cancel();
    }

    @Override
    public void actionViewProfile(LoginInput mLoginInput) {
        actionLoginCancel();
        if (!NetworkUtils.isNetworkConnected(mMyProfileParentView.getContext())) {
            mMyProfileParentView.hideLoading();
            mMyProfileParentView.onProfileFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyProfileParentView.showLoading();
            responseLoginCall = mImyProfileParentInteractor.viewProfile(mLoginInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    if (mMyProfileParentView.isAttached()) {
                        mMyProfileParentView.onProfileSuccess(responseLogin);
                    }

                }

                @Override
                public void onError(String errorMsg) {
                    if (mMyProfileParentView.isAttached()) {
                        mMyProfileParentView.onProfileFailure(errorMsg);
                    }
                }

            });
        }
    }

    @Override
    public void actionUploadUserImage(String userLoginSessionKey, final String filePath) {
        if (!NetworkUtils.isNetworkConnected(mMyProfileParentView.getContext())) {
            mMyProfileParentView.onHideLoading();
            mMyProfileParentView.onUploadPictureFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyProfileParentView.onShowLoading();
           /* mImyProfileParentInteractor.uploadUserImage(userLoginSessionKey, filePath, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    if (mMyProfileParentView.isAttached()) {
                        mMyProfileParentView.onHideLoading();
                        mMyProfileParentView.onUploadPictureSuccess(responseLogin, filePath);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (mMyProfileParentView.isAttached()) {
                        mMyProfileParentView.onHideLoading();
                        mMyProfileParentView.onShowSnackBar(errorMsg);
                    }
                }
            });*/
        }
    }
}
