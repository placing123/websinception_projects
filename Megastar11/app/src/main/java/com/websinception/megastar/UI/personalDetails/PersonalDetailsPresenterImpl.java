package com.websinception.megastar.UI.personalDetails;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.UpdateProfileInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.ResponseUpdateProfile;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.NetworkUtils;


import retrofit2.Call;

public class PersonalDetailsPresenterImpl implements PersonalDetailsPresenter {

    PersonalDetailsView mPersonalDetailsView;
    IUserInteractor mIUserInteractor;
    Call<LoginResponseOut> responseLoginCall;

    public PersonalDetailsPresenterImpl(PersonalDetailsView mPersonalDetailsView, IUserInteractor mIUserInteractor) {
        this.mPersonalDetailsView = mPersonalDetailsView;
        this.mIUserInteractor = mIUserInteractor;
    }

    public void actionLoginCancel() {
        if (responseLoginCall != null && !responseLoginCall.isExecuted())
            responseLoginCall.cancel();
    }


    @Override
    public void actionStatesBtn(String userLoginSessionKey, String countryId) {

       /* mIUserInteractor.states(userLoginSessionKey, countryId, new IUserInteractor.OnCountryResponseListener() {
            @Override
            public void onSuccess(ResponseCountries responseLogin) {
                if (responseLogin != null) {
                    mPersonalDetailsView.hideLoading();
                    mPersonalDetailsView.onStatesSuccess(responseLogin);
                } else {
                    mPersonalDetailsView.hideLoading();
                    mPersonalDetailsView.onStatesFailure(Constant.NULL_DATA_MESSAGE);
                }
            }

            @Override
            public void onError(String errorMsg) {
                mPersonalDetailsView.hideLoading();
                mPersonalDetailsView.onStatesFailure(errorMsg);
            }

        });*/

    }

    @Override
    public void actionViewProfile(LoginInput mLoginInput) {


        if (!NetworkUtils.isNetworkConnected(mPersonalDetailsView.getContext())) {
            mPersonalDetailsView.hideLoading();
            mPersonalDetailsView.onProfileFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mPersonalDetailsView.showLoading();
            responseLoginCall = mIUserInteractor.viewProfile(mLoginInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {

                        mPersonalDetailsView.onProfileSuccess(responseLogin);


                }

                @Override
                public void onError(String errorMsg) {

                        mPersonalDetailsView.onProfileFailure(errorMsg);

                }

            });
        }

    }

    @Override
    public void actionUpdateProfile(UpdateProfileInput mUpdateProfileInput) {

        if (!NetworkUtils.isNetworkConnected(mPersonalDetailsView.getContext())) {
            mPersonalDetailsView.hideLoading();
            mPersonalDetailsView.onUpdateFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mPersonalDetailsView.showLoading();
            mIUserInteractor.updateProfile(mUpdateProfileInput, new IUserInteractor.OnResponseUpdateProfileListener() {
                @Override
                public void onSuccess(ResponseUpdateProfile responseUpdate) {
                    if (responseUpdate != null) {
                        mPersonalDetailsView.showLoading();
                        mPersonalDetailsView.onUpdateSuccess(responseUpdate);
                    } else {
                        mPersonalDetailsView.hideLoading();
                        mPersonalDetailsView.onUpdateFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mPersonalDetailsView.hideLoading();
                    mPersonalDetailsView.onUpdateFailure(errorMsg);
                }
            });
        }
    }
}
