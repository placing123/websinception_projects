package com.websinception.megastar.UI.mobileAndEmailVerify;

import android.text.TextUtils;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.VerifyMobileInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.DataValidationUtils;
import com.websinception.megastar.utility.NetworkUtils;


import retrofit2.Call;

/**
 *
 */

public class VerifyMobileEmailPresenterImpl implements IVerifyMobileEmailPresenter {

    VerifyMobileEmailView view;
    IUserInteractor iUserInteractor;
    Call<LoginResponseOut> responseLoginCall;

    public VerifyMobileEmailPresenterImpl(VerifyMobileEmailView view, IUserInteractor iUserInteractor) {
        this.view = view;
        this.iUserInteractor = iUserInteractor;
    }

    public void actionLoginCancel() {
        if (responseLoginCall != null && !responseLoginCall.isExecuted())
            responseLoginCall.cancel();
    }



    @Override
    public void actionViewProfile(LoginInput mLoginInput) {
        actionLoginCancel();
        if (!NetworkUtils.isNetworkConnected(view.getContext())) {
            view.onHideLoading();
            view.onProfileFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            view.onShowLoading();
            responseLoginCall = iUserInteractor.viewProfile(mLoginInput, new IUserInteractor.OnResponseListener() {


                @Override
                public void onSuccess(LoginResponseOut loginResponseOut) {
                    if (view.getContext() != null&&responseLoginCall!=null&&!responseLoginCall.isCanceled()) {
                        view.onHideLoading();
                        view.onProfileSuccess(loginResponseOut);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (view.getContext() != null&&responseLoginCall!=null&&!responseLoginCall.isCanceled()) {
                        view.onHideLoading();
                        view.onProfileFailure(errorMsg);
                    }
                }
            });
        }
    }

  /*  @Override
    public void actionSendEmailCodeBtn(VerifyMobileInput mobileInput) {
        if (!NetworkUtils.isNetworkConnected(view.getContext())) {
            view.hideLoading();
            view.verifyEmailFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            view.showLoading();
            iUserInteractor.sendMobileOtp(mobileInput, new IUserInteractor.OnResponseListener() {


                @Override
                public void onSuccess(LoginResponseOut loginResponseOut) {
                    if (loginResponseOut != null) {
                        view.hideLoading();
                        view.verifyEmailSuccess(loginResponseOut);
                    } else {
                        view.hideLoading();
                        view.verifyEmailFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    view.hideLoading();
                    view.verifyEmailFailure(errorMsg);
                }

            });
        }
    }

    @Override
    public void actionSendMobileOtpBtn(VerifyMobileInput mobileInput) {
        if (!NetworkUtils.isNetworkConnected(view.getContext())) {
            view.hideLoading();
            view.verifyMobileFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            view.showLoading();
            iUserInteractor.sendMobileOtp(mobileInput, new IUserInteractor.OnResponseListener() {


                @Override
                public void onSuccess(LoginResponseOut loginResponseOut) {
                    if (loginResponseOut != null) {
                        view.hideLoading();
                        view.verifyMobileSuccess(loginResponseOut);
                    } else {
                        view.hideLoading();
                        view.verifyMobileFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    view.hideLoading();
                    view.verifyMobileFailure(errorMsg);
                }

            });
        }
    }
*/


    @Override
    public void actionSendEmailCodeBtn(VerifyMobileInput mobileInput) {
        if (!NetworkUtils.isNetworkConnected(view.getContext())) {
            view.hideLoading();
            view.verifyEmailFailure(AppUtils.getStrFromRes(R.string.network_error));
        }else if (mobileInput.getEmail().equals("")){
            view.hideLoading();
            view.verifyEmailFailure(AppUtils.getStrFromRes(R.string.empty_email));
        }else if (!DataValidationUtils.isValidEmail(mobileInput.getEmail())){
            view.hideLoading();
            view.verifyEmailFailure(AppUtils.getStrFromRes(R.string.invalid_email));
        } else {
            view.showLoading();
            iUserInteractor.sendMobileOtp(mobileInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut loginResponseOut) {
                    if (loginResponseOut != null) {
                        view.hideLoading();
                        view.verifyEmailSuccess(loginResponseOut);
                    } else {
                        view.hideLoading();
                        view.verifyEmailFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    view.hideLoading();
                    view.verifyEmailFailure(errorMsg);
                }

            });
        }
    }

    @Override
    public void actionSendMobileOtpBtn(VerifyMobileInput mobileInput) {
        if (!NetworkUtils.isNetworkConnected(view.getContext())) {
            view.hideLoading();
            view.verifyMobileFailure(AppUtils.getStrFromRes(R.string.network_error));
        }else if (TextUtils.isEmpty(mobileInput.getPhoneNumber())) {
            view.hideLoading();
            view.verifyMobileFailure(AppUtils.getStrFromRes(R.string.empty_phone_number));
        } else if (!DataValidationUtils.checkMobile(mobileInput.getPhoneNumber())) {
            view.hideLoading();
            view.verifyMobileFailure(AppUtils.getStrFromRes(R.string.invalid_phone_number));
        } else {
            view.showLoading();
            iUserInteractor.sendMobileOtp(mobileInput, new IUserInteractor.OnResponseListener() {

                @Override
                public void onSuccess(LoginResponseOut loginResponseOut) {
                    if (loginResponseOut != null) {
                        view.hideLoading();
                        view.verifyMobileSuccess(loginResponseOut);
                    } else {
                        view.hideLoading();
                        view.verifyMobileFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    view.hideLoading();
                    view.verifyMobileFailure(errorMsg);
                }

            });
        }
    }



}
