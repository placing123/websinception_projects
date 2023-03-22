package com.websinception.megastar.UI.walkThrough;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.facebook.common.util.UriUtil;
import com.websinception.megastar.R;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalkThroughFragment extends BaseFragment {

    @BindView(R.id.civ_pic)
    CustomImageView mCivPic;

    @BindView(R.id.ctv_title)
    CustomTextView mCtvTitle;

    @BindView(R.id.ctv_desc)
    CustomTextView mCtvDesc;


    String[] title = {
            "DAILY FANTASY",
            "AUCTION",
            "GULLY FANTASY"
    };

    String[] desc = {
            "Select match, Choose your favourite players to create team to play, Join any league. Chase your opponent Play & Win real cash.",
            "Take up fantasy Series Challenge with a twist of fantasy auction. Grab the opportunity to create your favourite team by bidding highest player",
            "fantasy is giving you an opportunity to relive the old game of Gully Cricket with the twist of Fantasy Game."
    };

    int icon[] = {
            R.drawable.wt_1,
            R.drawable.wt_2,
            R.drawable.wt_3
    };
    private int position;

    public static WalkThroughFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        WalkThroughFragment fragment = new WalkThroughFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_walk_through;
    }

    @Override
    public void init() {
        position = getArguments().getInt("position");
        mCtvTitle.setText(title[position]);
        mCtvDesc.setText(desc[position]);
        mCivPic.setImageURI(new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(icon[position]))
                .build());
    }

}
