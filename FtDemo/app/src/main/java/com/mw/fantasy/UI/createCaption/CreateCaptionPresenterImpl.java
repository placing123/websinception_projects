package com.mw.fantasy.UI.createCaption;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.CreateTeamInput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;


import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class CreateCaptionPresenterImpl implements ICreateCaptionPresenter {

    CreateCaptionView mView;
    IUserInteractor mInteractor;
    Call<LoginResponseOut> responseLoginCall;

    public CreateCaptionPresenterImpl(CreateCaptionView mView, IUserInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    public void actionListingCancel() {
        if (responseLoginCall != null && !responseLoginCall.isExecuted())
            responseLoginCall.cancel();
    }

    @Override
    public void actionCreateTeam(CreateTeamInput mCreateTeamInput) {
        actionListingCancel();
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.onSaveError(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            responseLoginCall = mInteractor.addUserTeam(mCreateTeamInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    if (responseLogin != null) {
                        mView.onSaveSuccess(responseLogin);
                    } else {
                        mView.hideLoading();
                        mView.onSaveError(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                    mView.onSaveError(errorMsg);
                }

            });
        }
    }

    @Override
    public void actionEditTeam(CreateTeamInput mCreateTeamInput) {
        actionListingCancel();
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.onSaveError(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            responseLoginCall = mInteractor.editUserTeam(mCreateTeamInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    if (responseLogin != null) {
                        mView.onSaveSuccess(responseLogin);
                    } else {
                        mView.hideLoading();
                        mView.onSaveError(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                    mView.onSaveError(errorMsg);
                }

            });
        }
    }


}
