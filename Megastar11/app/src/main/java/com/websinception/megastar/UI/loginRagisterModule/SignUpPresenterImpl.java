package com.websinception.megastar.UI.loginRagisterModule;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.SingupInput;
import com.websinception.megastar.beanOutput.ResponceSignup;
import com.websinception.megastar.beanOutput.ResponseReferralDetails;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.NetworkUtils;

/**
 * Created by hp on 06-07-2017.
 */

public class SignUpPresenterImpl implements ISignUpPresenter {

    SignUpView mSignUpView;
    IUserInteractor iUserInteractor;

    public SignUpPresenterImpl(SignUpView mSignUpView, IUserInteractor iUserInteractor) {
        this.mSignUpView = mSignUpView;
        this.iUserInteractor = iUserInteractor;
    }

    @Override
    public void actionSignUpBtn(final SingupInput mSingupInput) {
        if (!NetworkUtils.isNetworkConnected(mSignUpView.getContext())) {
            mSignUpView.hideLoading();
            mSignUpView.signUpFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mSignUpView.showLoading();

            iUserInteractor.signUp(mSingupInput, new IUserInteractor.OnSignUpResponseListener() {


                @Override
                public void onNotVerify(ResponceSignup mSignUpOutput) {
                    mSignUpView.hideLoading();
                    mSignUpView.signUpNotVerify(mSignUpOutput);
                }

                @Override
                public void onSuccess(ResponceSignup mSignUpOutput) {

                    if (mSingupInput.getSource().equals(Constant.Direct)) {
                        onNotVerify(mSignUpOutput);
                    } else {
                        mSignUpView.hideLoading();
                        mSignUpView.signUpSuccess(mSignUpOutput);
                    }

                }

                @Override
                public void onError(String errorMsg) {
                    mSignUpView.hideLoading();
                    mSignUpView.signUpFailure(errorMsg);
                }
            });


        }
    }

    @Override
    public void actionReferralBtn() {
        if (!NetworkUtils.isNetworkConnected(mSignUpView.getContext())) {
            mSignUpView.hideloader();
            mSignUpView.signUpFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mSignUpView.showloader();
            iUserInteractor.getReferralDetail(new IUserInteractor.OnReferralDetailListener() {
                @Override
                public void onSuccess(ResponseReferralDetails user) {
                    mSignUpView.hideloader();
                    mSignUpView.referralSuccess(user);
                }

                @Override
                public void onError(String errorMsg) {
                    mSignUpView.hideloader();
                    mSignUpView.referralFailure(errorMsg);
                }
            });

        }


    }
}
