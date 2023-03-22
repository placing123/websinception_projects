package com.websinception.megastar.UI.joinContest;

import android.content.Context;

import com.websinception.megastar.beanOutput.JoinAuctionOutput;
import com.websinception.megastar.beanOutput.JoinContestOutput;
import com.websinception.megastar.beanOutput.WalletOutputBean;



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
