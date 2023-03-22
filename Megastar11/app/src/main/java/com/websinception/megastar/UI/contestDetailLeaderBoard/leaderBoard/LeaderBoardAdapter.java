package com.websinception.megastar.UI.contestDetailLeaderBoard.leaderBoard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.beanOutput.ContestUserOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.textdrawable.TextDrawable;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.OnItemClickListener;
import com.websinception.megastar.utility.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 *
 */

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {

    private final String winningType;
    OnItemClickListener.OnItemClickCallback onItemClickCallback, onViewItemClickCallback, onLeaderBoardItemClickCallback;
    int layoutId = 0;
    private List<ContestUserOutput.DataBean.RecordsBean> responseBeen = new ArrayList<>();
    private Context mContext;
    String statusId;

    public LeaderBoardAdapter(int layoutId, Context mContext, List<ContestUserOutput.DataBean.RecordsBean> responseBeen, String statusId,
                              OnItemClickListener.OnItemClickCallback onItemClickCallback,
                              OnItemClickListener.OnItemClickCallback onViewItemClickCallback,
                              OnItemClickListener.OnItemClickCallback onLeaderBoardItemClickCallback, String winningType) {
        this.responseBeen = responseBeen;
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.statusId = statusId;
        this.onItemClickCallback = onItemClickCallback;
        this.onViewItemClickCallback = onViewItemClickCallback;
        this.onLeaderBoardItemClickCallback = onLeaderBoardItemClickCallback;
        this.winningType = winningType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (holder.customTextViewName != null) {
            if (responseBeen.get(position).getIsSubscribe() != null && responseBeen.get(position).getIsSubscribe().equalsIgnoreCase("Yes")) {
                holder.rl_star_member.setVisibility(View.VISIBLE);
            } else {
                holder.rl_star_member.setVisibility(View.GONE);
            }
            holder.customTextViewName.setText(responseBeen.get(position).getUsername() + " (" + responseBeen.get(position).getUserTeamName() + ")");
           /* if (responseBeen.get(position).getFirstName().length() == 0) {
                holder.customTextViewName.setText(responseBeen.get(position).getUserTeamName());
            } else {
                holder.customTextViewName.setText(responseBeen.get(position).getFirstName()+"("+responseBeen.get(position).getUserTeamName()+")");
            }*/
        }
        if (holder.ctvCredits != null)
            holder.ctvCredits.setText(responseBeen.get(position).getTotalPoints() + " " + AppUtils.getStrFromRes(R.string.points));
        if (holder.ivPlayer != null) {
            if (TextUtils.isEmpty(responseBeen.get(position).getProfilePic())) {
                TextDrawable drawable2 = TextDrawable.builder().beginConfig()
                        .fontSize(45) /* size in px */
                        .bold()
                        .toUpperCase()
                        .endConfig()
                        .buildRound(AppUtils.getNameCharacters(responseBeen.get(position).getFirstName()), AppUtils.getRandomColor());
                if (drawable2 != null) holder.ivPlayer.setImageDrawable(drawable2);
            } else {
                ViewUtils.setImageUrl(holder.ivPlayer, responseBeen.get(position).getProfilePic());
            }
        }

        if (holder.ctvRanks != null)
            if (responseBeen.get(position).getUserRank().equals("0") || responseBeen.get(position).getUserRank().equals("0.00")) {
                holder.ctvRanks.setText("--");

            } else {
                holder.ctvRanks.setText("#" + responseBeen.get(position).getUserRank());
            }

        if (statusId.equals(Constant.Pending)) {
            holder.ctv_country.setVisibility(View.GONE);
        } else {
            holder.ctv_country.setText(responseBeen.get(position).getTotalPoints() + " " +
                    AppUtils.getStrFromRes(R.string.points));
        }


        if (holder.mCardViewMainCard != null) {
            holder.mCardViewMainCard.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
        }

        if (holder.ivPlayer != null) {
            holder.ivPlayer.setOnClickListener(new OnItemClickListener(position, onViewItemClickCallback));
        }

        if (AppSession.getInstance().getLoginSession().getData().getUserGUID().equals(responseBeen.get(position).getUserGUID())) {
            holder.mCardViewMainCard.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lightSkyBlue));
        } else {
            holder.mCardViewMainCard.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }

        if (statusId.equals(Constant.Completed)) {
            if (holder.mWinnings != null) {
                if (responseBeen.get(position).getSmartPool().equalsIgnoreCase("Yes")) {
                    if (responseBeen.get(position).getSmartPoolWinning() != null && !responseBeen.get(position).getSmartPoolWinning().equalsIgnoreCase("")) {
                        holder.mWinnings.setText("You Won " + responseBeen.get(position).getSmartPoolWinning());
                    } else {
                        holder.mWinnings.setVisibility(View.GONE);

                    }
                } else {

                    if (winningType != null && winningType.equalsIgnoreCase("Free Join Contest")) {
                        holder.mWinnings.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        holder.mWinnings.setText("Bonus " + responseBeen.get(position).getUserWinningAmount());
                    } else {
                        holder.mWinnings.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rupee, 0, 0, 0);
                        holder.mWinnings.setText(responseBeen.get(position).getUserWinningAmount());
                    }
                }
            }
        } else {
            holder.mWinnings.setVisibility(View.GONE);
        }

    }


    public ContestUserOutput.DataBean.RecordsBean getItemData(int position) {
        if (responseBeen == null) return null;
        return responseBeen.get(position);
    }

    public String getTeamId(int position) {
        if (responseBeen == null) return "";
        return responseBeen.get(position).getUserTeamID();
    }

    public String getUserGUID(int position) {
        if (responseBeen == null) return "";
        return responseBeen.get(position).getUserGUID();
    }

    public void addItem(ContestUserOutput.DataBean.RecordsBean bean) {
        if (bean == null || responseBeen == null) return;
        responseBeen.add(bean);
        notifyItemInserted(responseBeen.size() - 1);
    }

    public void addAllItem(List<ContestUserOutput.DataBean.RecordsBean> beanList) {
        if (beanList == null || responseBeen == null) return;
        for (int i = 0; i < beanList.size(); i++) {
            addItem(beanList.get(i));
        }
    }

    public void clear() {
        if (responseBeen == null) return;
        responseBeen.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return responseBeen.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.hi_main_card)
        @Nullable
        CardView mCardViewMainCard;

        @BindView(R.id.iv_player)
        @Nullable
        CustomImageView ivPlayer;

        @BindView(R.id.ctv_name)
        @Nullable
        CustomTextView customTextViewName;

        @BindView(R.id.ctv_winnings)
        CustomTextView mWinnings;

        @BindView(R.id.ctv_country)
        @Nullable
        CustomTextView ctv_country;

        @Nullable
        CustomTextView ctvCredits;

        @BindView(R.id.ctv_ranks)
        @Nullable
        CustomTextView ctvRanks;

        @BindView(R.id.rl_star_member)
        @Nullable
        RelativeLayout rl_star_member;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
