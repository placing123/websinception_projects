package com.mw.fantasy.UI.joinContest;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.JoinAuctionInput;
import com.mw.fantasy.beanInput.JoinContestInput;
import com.mw.fantasy.beanInput.WalletInput;
import com.mw.fantasy.beanOutput.JoinAuctionOutput;
import com.mw.fantasy.beanOutput.JoinContestOutput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.WalletOutputBean;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;


import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class JoinContestPresenterImpl implements IJoinContestPresenter {

    JoinContestView mMyAccountParentView;
    IUserInteractor mImyAccountParentInteractor;
    Call<WalletOutputBean> responseLoginCall;
    Call<LoginResponseOut> responseLoginJoin;

    public JoinContestPresenterImpl(JoinContestView mMyAccountParentView, IUserInteractor mImyAccountParentInteractor) {
        this.mMyAccountParentView = mMyAccountParentView;
        this.mImyAccountParentInteractor = mImyAccountParentInteractor;
    }

    public void actionLoginCancel() {
        if (responseLoginCall != null && !responseLoginCall.isExecuted())
            responseLoginCall.cancel();
    }

    @Override
    public void actionViewAccount(WalletInput mWalletInput) {
        actionLoginCancel();
        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.hideLoading();
            mMyAccountParentView.onAccountFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.showLoading();
            responseLoginCall = mImyAccountParentInteractor.viewAccount(mWalletInput, new IUserInteractor.OnResponseAccountListener() {
                @Override
                public void onSuccess(WalletOutputBean mResponseAccount) {
                    mMyAccountParentView.hideLoading();
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.hideLoading();
                        mMyAccountParentView.onAccountSuccess(mResponseAccount);
                    }

                }

                @Override
                public void onError(String errorMsg) {
                    mMyAccountParentView.hideLoading();
                    if (mMyAccountParentView.isAttached()) {
                        mMyAccountParentView.hideLoading();
                        mMyAccountParentView.onAccountFailure(errorMsg);
                    }
                }

            });
        }}

    @Override
    public void actionJoinContest(JoinContestInput mJoinContestInput) {
          if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.onHideLoading();
            mMyAccountParentView.onJoinFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.onShowLoading();
            mImyAccountParentInteractor.joinContest(mJoinContestInput, new IUserInteractor.OnResponseJoinListener() {
                @Override
                public void onSuccess(JoinContestOutput responseLogin) {
                    mMyAccountParentView.onHideLoading();
                    if (responseLogin != null) {
                        mMyAccountParentView.onJoinSuccess(responseLogin);
                    } else {

                        mMyAccountParentView.onJoinFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onNotFound(String error) {
                    mMyAccountParentView.onHideLoading();
                    mMyAccountParentView.onJoinNotFound(error);
                }

                @Override
                public void onError(String errorMsg) {
                    mMyAccountParentView.onHideLoading();
                    mMyAccountParentView.onJoinFailure(errorMsg);
                }

            });
        }
    }


    @Override
    public void actionJoinAuction(JoinAuctionInput joinAuctionInput) {
        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.onHideLoading();
            mMyAccountParentView.onAuctionJoinError(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.onShowLoading();
            mImyAccountParentInteractor.joinAuction(joinAuctionInput, new IUserInteractor.OnJoinAuctionListener() {
                @Override
                public void onSuccess(JoinAuctionOutput joinAuctionOutput) {
                    mMyAccountParentView.onHideLoading();
                    if (joinAuctionOutput != null) {
                        if (joinAuctionOutput.getResponseCode() == 200) {
                            mMyAccountParentView.onAuctionJoinSuccess(joinAuctionOutput);
                        } else {
                            mMyAccountParentView.onAuctionJoinError(joinAuctionOutput.getMessage());
                        }

                    } else {

                        mMyAccountParentView.onAuctionJoinError(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mMyAccountParentView.onHideLoading();
                    mMyAccountParentView.onAuctionJoinError(errorMsg);
                }
            });
        }
    }

    @Override
    public void actionJoinDfraft(JoinAuctionInput joinAuctionInput) {
        if (!NetworkUtils.isNetworkConnected(mMyAccountParentView.getContext())) {
            mMyAccountParentView.onHideLoading();
            mMyAccountParentView.onDraftJoinError(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mMyAccountParentView.onShowLoading();
            mImyAccountParentInteractor.joinDraft(joinAuctionInput, new IUserInteractor.OnJoinAuctionListener() {
                @Override
                public void onSuccess(JoinAuctionOutput joinAuctionOutput) {
                    mMyAccountParentView.onHideLoading();
                    if (joinAuctionOutput != null) {
                        if (joinAuctionOutput.getResponseCode() == 200) {
                            mMyAccountParentView.onDraftJoinSuccess(joinAuctionOutput);
                        } else {
                            mMyAccountParentView.onDraftJoinError(joinAuctionOutput.getMessage());
                        }

                    } else {

                        mMyAccountParentView.onDraftJoinError(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mMyAccountParentView.onHideLoading();
                    mMyAccountParentView.onDraftJoinError(errorMsg);
                }
            });
        }
    }






    public void actionListingCancel() {
        if (responseLoginJoin != null && !responseLoginJoin.isExecuted())
            responseLoginJoin.cancel();
    }

}
