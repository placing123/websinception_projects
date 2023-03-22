package com.websinception.megastar.UI.draft.draftHome;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.websinception.megastar.R;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.beanOutput.DraftJoinedUserResponse;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment {

//    @BindView(R.id.rv_user)
//    RecyclerView mRecyclerView;


    @BindView(R.id.civ_pic_two)
    CustomImageView civ_pic_two;

    @BindView(R.id.civ_pic_one)
    CustomImageView civ_pic_one;

    @BindView(R.id.user_name_one)
    CustomTextView user_name_one;

    @BindView(R.id.user_name_two)
    CustomTextView user_name_two;


    @BindView(R.id.first_check)
    ImageView first_check;

    @BindView(R.id.second_check)
    ImageView second_check;

    @BindView(R.id.user_lyt_one)
    RelativeLayout user_lyt_one;

    @BindView(R.id.user_lyt_two)
    RelativeLayout user_lyt_two;




    private ListAdapter mListAdapter;

    private DraftJoinedUserResponse.DraftJoinedContestUserBean.DataBean mDataBean;
    private String draftLiveRound;

    public static UserFragment newInstance(DraftJoinedUserResponse.DraftJoinedContestUserBean.DataBean dataBean, String draftLiveRound) {
        Bundle args = new Bundle();
        args.putSerializable("dataBean", dataBean);
        args.putString("draftLiveRound", draftLiveRound);
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_user;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init() {
        mDataBean = (DraftJoinedUserResponse.DraftJoinedContestUserBean.DataBean) getArguments().getSerializable("dataBean");
        draftLiveRound = getArguments().getString("draftLiveRound");

        if (mDataBean.getUsers().size()>0){

            if (mDataBean.getUsers().size() == 1){
                user_name_one.setText(mDataBean.getUsers().get(0).getFirstName());

                ViewUtils.setImageUrl(civ_pic_one, mDataBean.getUsers().get(0).getProfilePic());
                if (Integer.parseInt(draftLiveRound) == mDataBean.getRound()) {
                    if (mDataBean.getUsers().get(0).getDraftUserLive().equals("Yes")) {
                        user_lyt_one.setBackgroundResource(R.drawable.selectedplayerdraft);
                        user_name_one.setTextColor(getActivity().getColor(R.color.black));
                    } else {
                        user_lyt_one.setBackgroundResource(R.drawable.unselectedplayerdraft);
                        user_name_one.setTextColor(getActivity().getColor(R.color.white));
                    }
                }else {
                    user_lyt_two.setBackgroundResource(R.drawable.unselectedplayerdraft);
                    user_lyt_one.setBackgroundResource(R.drawable.unselectedplayerdraft);
                }

                if (mDataBean.getUsers().get(0).getAuctionUserStatus().equals("Online")) {
                    first_check.setImageResource(R.drawable.circle_green);
                } else {
                    first_check.setImageResource(R.drawable.circle_red);
                }
                user_lyt_two.setVisibility(View.GONE);
                second_check.setVisibility(View.GONE);
            }else {
                user_lyt_two.setVisibility(View.VISIBLE);
                second_check.setVisibility(View.VISIBLE);

                user_name_one.setText(mDataBean.getUsers().get(0).getFirstName());
                user_name_two.setText(mDataBean.getUsers().get(1).getFirstName());
                ViewUtils.setImageUrl(civ_pic_one, mDataBean.getUsers().get(0).getProfilePic());
                ViewUtils.setImageUrl(civ_pic_two, mDataBean.getUsers().get(1).getProfilePic());
                if (Integer.parseInt(draftLiveRound) == mDataBean.getRound()) {
                    if (mDataBean.getUsers().get(0).getDraftUserLive().equals("Yes")) {
                        user_lyt_one.setBackgroundResource(R.drawable.selectedplayerdraft);
                        user_name_one.setTextColor(getActivity().getColor(R.color.black));
                    } else {
                        user_lyt_one.setBackgroundResource(R.drawable.unselectedplayerdraft);
                        user_name_one.setTextColor(getActivity().getColor(R.color.white));
                    }


                    if (mDataBean.getUsers().get(1).getDraftUserLive().equals("Yes")) {
                        user_lyt_two.setBackgroundResource(R.drawable.selectedplayerdraft);
                        user_name_two.setTextColor(getActivity().getColor(R.color.black));
                    } else {
                        user_lyt_two.setBackgroundResource(R.drawable.unselectedplayerdraft);
                        user_name_two.setTextColor(getActivity().getColor(R.color.white));
                    }
                } else {
                    user_lyt_two.setBackgroundResource(R.drawable.unselectedplayerdraft);
                    user_lyt_one.setBackgroundResource(R.drawable.unselectedplayerdraft);

                }
                if (mDataBean.getUsers().get(0).getAuctionUserStatus().equals("Online")) {
                    first_check.setImageResource(R.drawable.circle_green);
                } else {
                    first_check.setImageResource(R.drawable.circle_red);
                }
                if (mDataBean.getUsers().get(1).getAuctionUserStatus().equals("Online")) {
                    second_check.setImageResource(R.drawable.circle_green);
                } else {
                    second_check.setImageResource(R.drawable.circle_red);
                }
            }

        }
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        mListAdapter = new ListAdapter(Integer.parseInt(draftLiveRound) == mDataBean.getRound(), mDataBean.getUsers());
//        mRecyclerView.setAdapter(mListAdapter);
//        for (int i = 0; i < mDataBean.getUsers().size(); i++) {
//            if (mDataBean.getUsers().get(i).getDraftUserLive().equals("Yes")) {
//                mRecyclerView.scrollToPosition(i);
//                break;
//            }
//        }
    }


    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

        private List<DraftJoinedUserResponse.DraftJoinedContestUserBean.DataBean.UsersBean> mUsersBeanList;
        private boolean isCurrentRound;

        public ListAdapter(boolean isCurrentround, List<DraftJoinedUserResponse.DraftJoinedContestUserBean.DataBean.UsersBean> users) {
            mUsersBeanList = users;
            isCurrentRound = isCurrentround;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_draft_round_user, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            DraftJoinedUserResponse.DraftJoinedContestUserBean.DataBean.UsersBean usersBean = mUsersBeanList.get(i);
            myViewHolder.mCustomTextViewName.setText(usersBean.getFirstName());
            ViewUtils.setImageUrl(myViewHolder.mCustomImageViewPic, usersBean.getProfilePic());
            if (isCurrentRound) {
                if (usersBean.getDraftUserLive().equals("Yes")) {
                    myViewHolder.mLinearLayoutRoot.setBackgroundResource(R.drawable.draft_user_active);
                } else {
                    myViewHolder.mLinearLayoutRoot.setBackgroundResource(R.drawable.draft_user_inactive);
                }
            }else {
                myViewHolder.mLinearLayoutRoot.setBackgroundResource(R.color.divider_color);
            }
            if (usersBean.getAuctionUserStatus().equals("Online")) {
                myViewHolder.mImageViewIndicator.setImageResource(R.drawable.circle_green);
            } else {
                myViewHolder.mImageViewIndicator.setImageResource(R.drawable.circle_red);
            }
        }

        @Override
        public int getItemCount() {
            return mUsersBeanList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.ll_root)
            LinearLayout mLinearLayoutRoot;

            @BindView(R.id.iv_indicator)
            ImageView mImageViewIndicator;

            @BindView(R.id.ctv_name)
            CustomTextView mCustomTextViewName;

            @BindView(R.id.civ_pic)
            CustomImageView mCustomImageViewPic;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
