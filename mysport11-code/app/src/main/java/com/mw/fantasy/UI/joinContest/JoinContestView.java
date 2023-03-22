package com.mw.fantasy.UI.joinContest;

import android.content.Context;

import com.mw.fantasy.beanOutput.JoinAuctionOutput;
import com.mw.fantasy.beanOutput.JoinContestOutput;
import com.mw.fantasy.beanOutput.WalletOutputBean;



/**
 *
 */

public interface JoinContestView {
    void showLoading();

    void hideLoading();

    void onAccountSuccess(WalletOutputBean mWalletOutputBean);

    void onAccountFailure(String errMsg);

    void onJoinSuccess(JoinContestOutput responseLogin);

    void onJoinFailure(String errMsg);

    void onJoinNotFound(String errMsg);

    void onShowLoading();

    void onHideLoading();

    void onShowSnackBar(String message);


    void onAuctionJoinSuccess(JoinAuctionOutput joinAuctionOutput);

    void onAuctionJoinError(String error);

    void onDraftJoinSuccess(JoinAuctionOutput joinAuctionOutput);

    void onDraftJoinError(String error);


    boolean isAttached();

    Context getContext();
}
