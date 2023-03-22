package com.websinception.megastar.UI.joinContest;


import com.websinception.megastar.beanInput.JoinAuctionInput;
import com.websinception.megastar.beanInput.JoinContestInput;
import com.websinception.megastar.beanInput.WalletInput;


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
