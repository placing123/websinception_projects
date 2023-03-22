package com.websinception.megastar.UI.createContest;


import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.CreateContestInput;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.WinnerBreakupInput;
import com.websinception.megastar.beanOutput.CreateContestOutput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.WinBreakupOutPut;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.NetworkUtils;

import retrofit2.Call;


/**
 *
 */

public class CreateContestPresenterImpl implements ICreateContestPresenter {

    CreateContestView view;
    IUserInteractor iUserInteractor;

    Call<LoginResponseOut> responseLoginCall;

    public CreateContestPresenterImpl(CreateContestView view, IUserInteractor iUserInteractor) {
        this.view = view;
        this.iUserInteractor = iUserInteractor;
    }

    @Override
    public void actionCreateContestBtn(CreateContestInput createContestInput) {
        if (!NetworkUtils.isNetworkConnected(view.getContext())) {
            view.hideLoading();
            view.createContestFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            view.showLoading();
            iUserInteractor.createContest(createContestInput, new IUserInteractor.OnResponseCreateContestListener() {
                        @Override
                        public void onSuccess(CreateContestOutput responseLogin) {
                            if (responseLogin != null) {
                                view.createContestSuccess(responseLogin);
                            } else {
                                view.hideLoading();
                                view.createContestFailure(Constant.NULL_DATA_MESSAGE);
                            }
                        }

                        @Override
                        public void onError(String errorMsg) {
                            view.hideLoading();
                            view.createContestFailure(errorMsg);
                        }

                    });
        }
    }

    @Override
    public void winning_breakup(WinnerBreakupInput mWinnerBreakupInput) {
        if (!NetworkUtils.isNetworkConnected(view.getContext())) {
            view.hideLoading();
            view.createContestFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            view.showLoading();
            iUserInteractor.winning_breakup(mWinnerBreakupInput, new IUserInteractor.OnResponseWinBreakUpListener() {
                @Override
                public void onSuccess(WinBreakupOutPut responseLogin) {
                    if (responseLogin != null) {
                        view.hideLoading();
                        view.winBreakupSuccess(responseLogin);
                    } else {
                        view.hideLoading();
                        view.winBreakupFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    view.hideLoading();
                    view.createContestFailure(errorMsg);
                }

            });
        }
    }

    @Override
    public void actionViewProfile(LoginInput mLoginInput) {
        if (!NetworkUtils.isNetworkConnected(view.getContext())) {
            view.hideLoading();
            view.onProfileFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            view.showLoading();
            responseLoginCall = iUserInteractor.viewProfile(mLoginInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {

                    view.onProfileSuccess(responseLogin);


                }

                @Override
                public void onError(String errorMsg) {

                    view.onProfileFailure(errorMsg);

                }

            });
        }
    }


}
