package com.mw.fantasy.UI.draft.draftHome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mw.fantasy.beanOutput.DraftJoinedUserResponse;

public class HomeUserPagerAdapter extends FragmentStatePagerAdapter {

    private DraftJoinedUserResponse mDraftJoinedUserResponse;

    public HomeUserPagerAdapter(FragmentManager fm, DraftJoinedUserResponse mDraftJoinedUserResponse) {
        super(fm);
        this.mDraftJoinedUserResponse = mDraftJoinedUserResponse;

    }


    @Override
    public Fragment getItem(int i) {
        return UserFragment.newInstance(mDraftJoinedUserResponse.getDraftJoinedContestUser().getData().get(i),
                mDraftJoinedUserResponse.getDraftJoinedContestUser().getDraftLiveRound()+"");
    }

    @Override
    public int getCount() {
        return mDraftJoinedUserResponse.getDraftJoinedContestUser().getData().size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
