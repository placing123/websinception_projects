package com.websinception.megastar.UI.matches;


import com.websinception.megastar.beanInput.MatchListInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface IMatchesPresenter {
    void actionListing(MatchListInput matchListInput);

    void myContestListing(String loginSessionKey, String type, int limit, int offset);

    void to_check_contest(String loginSessionKey, String user_invite_code);

    void actionMatchSeries(String loginSessionKey);


}
