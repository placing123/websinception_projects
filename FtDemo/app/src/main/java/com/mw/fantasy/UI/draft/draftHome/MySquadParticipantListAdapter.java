package com.mw.fantasy.UI.draft.draftHome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mw.fantasy.R;
import com.mw.fantasy.beanOutput.GetAuctionJoinedUserOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySquadParticipantListAdapter extends RecyclerView.Adapter<MySquadParticipantListAdapter.MySquadParticipantViewHolder> {


    private int selectedPosition = 0;
    private MySquadFragment mySquadFragment;
    private List<GetAuctionJoinedUserOutput.DataBean.RecordsBean> joinedUserList;

    public MySquadParticipantListAdapter(MySquadFragment mySquadFragment, List<GetAuctionJoinedUserOutput.DataBean.RecordsBean> joinedUserList, int selectedPosition) {
        this.mySquadFragment = mySquadFragment;
        this.joinedUserList = joinedUserList;
        this.selectedPosition = selectedPosition;
    }

    @NonNull
    @Override
    public MySquadParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MySquadParticipantViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_draft_participant, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MySquadParticipantViewHolder mySquadParticipantViewHolder, final int i) {
        final GetAuctionJoinedUserOutput.DataBean.RecordsBean recordsBean = joinedUserList.get(i);
        mySquadParticipantViewHolder.mCustomTextViewName.setText(recordsBean.getUsername());
        ViewUtils.setImageUrl(mySquadParticipantViewHolder.mCustomImageViewPic, recordsBean.getProfilePic());
        if (selectedPosition == i) {
            mySquadParticipantViewHolder.mLinearLayoutRoot.setBackgroundResource(R.color.yellow);
        } else {
            mySquadParticipantViewHolder.mLinearLayoutRoot.setBackgroundResource(R.color.white);
        }
        mySquadParticipantViewHolder.mLinearLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = i;
                notifyDataSetChanged();
                mySquadFragment.apiCallGetUserSquad(recordsBean.getUserGUID());

            }
        });
    }

    @Override
    public int getItemCount() {
        return joinedUserList.size();
    }

    public class MySquadParticipantViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_root)
        LinearLayout mLinearLayoutRoot;


        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        public MySquadParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
