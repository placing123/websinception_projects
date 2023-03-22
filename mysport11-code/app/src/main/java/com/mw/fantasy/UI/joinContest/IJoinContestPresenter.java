package com.mw.fantasy.UI.joinContest;


import com.mw.fantasy.beanInput.JoinAuctionInput;
import com.mw.fantasy.beanInput.JoinContestInput;
import com.mw.fantasy.beanInput.WalletInput;


/**
 * Created by hp on 06-07-2017.
 */

public interface IJoinContestPresenter {
    //cricket
    void actionViewAccount(WalletInput mWalletInput);

    void actionJoinContest(JoinContestInput mJoinContestInput);

    void actionJoinAuction(JoinAuctionInput joinAuctionInput);

    void actionJoinDfraft(JoinAuctionInput joinAuctionInput);



}
