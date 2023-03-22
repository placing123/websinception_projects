package com.websinception.megastar.UI.joinedContests;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.websinception.megastar.R;
import com.websinception.megastar.beanOutput.JoinedContestOutput;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JoinedContestAdapter extends RecyclerView.Adapter<JoinedContestAdapter.MyViewHolder> {

    List<JoinedContestOutput.DataBean.RecordsBean> responseBeen = new ArrayList<>();
    int layoutId = 0;
    OnItemClickListener.OnItemClickCallback onWinnerClickCallBack, onItemClickCallback, onJoinClickCallBack;
    private Context mContext;


    public JoinedContestAdapter(int layoutId, Context mContext, List<JoinedContestOutput.DataBean.RecordsBean> responseBeen,
                                OnItemClickListener.OnItemClickCallback onWinnerClickCallBack,
                                OnItemClickListener.OnItemClickCallback onItemClickCallback,
                                OnItemClickListener.OnItemClickCallback onJoinClickCallBack) {
        this.responseBeen = responseBeen;
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.onJoinClickCallBack = onJoinClickCallBack;
        this.onWinnerClickCallBack = onWinnerClickCallBack;
        this.onItemClickCallback = onItemClickCallback;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_contest_item, parent, false));

    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (responseBeen.get(position).getSmartPool().equals("Yes")) {
            holder.total_winnings_amount.setText(responseBeen.get(position).getSmartPoolWinning());
        } else {
            if (!responseBeen.get(position).getWinningAmount().equals("0")) {

                if (responseBeen.get(position).getWinningType() != null && responseBeen.get(position).getWinningType().equalsIgnoreCase("Free Join Contest")) {
                    holder.total_winnings_amount.setText("Bonus " + responseBeen.get(position).getWinningAmount());
                } else {
                    holder.total_winnings_amount.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(position).getWinningAmount());
                }
//            holder.total_winnings_amount.setText(AppUtils.getStrFromRes(R.string.price_unit)+""+responseBeen.get(position).getWinningAmount());
            } else {
                holder.total_winnings_amount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                holder.total_winnings_amount.setText(AppUtils.getStrFromRes(R.string.practice_contest));

            }
        }


        if (responseBeen.get(position).getStatus().equals(Constant.Cancelled)) {

            holder.total_winnings.setText(Html.fromHtml(responseBeen.get(position).getContestType() + " <b><u><font color='#DA473D'>Cancelled</font></u></b>"));
        } else {

            holder.total_winnings.setText(responseBeen.get(position).getContestType());

        }

        holder.entry_fee_amount.setText(responseBeen.get(position).getEntryFee());
        holder.teamName.setText(responseBeen.get(position).getUserTeamName());
        holder.points.setText(responseBeen.get(position).getTotalPoints());
        holder.rank.setText("#" + responseBeen.get(position).getUserRank());

        holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(position).getEntryFee());

        holder.winnersCount.setText(responseBeen.get(position).getNoOfWinners() + " " + AppUtils.getStrFromRes(R.string.winners));

      /*  if (responseBeen.get(position).getUserWinningAmount().equals("0.00")) {
            holder.mYourWinnngs.setVisibility(View.GONE);
            holder.mLine.setVisibility(View.GONE);
        }else {*/

        if (responseBeen.get(position).getSmartPool().equalsIgnoreCase("Yes")) {
            holder.mYourWinnngs.setText("You Won " + responseBeen.get(position).getSmartPoolWinning());
        } else {
            if (responseBeen.get(position).getWinningType() != null && responseBeen.get(position).getWinningType().equalsIgnoreCase("Free Join Contest")) {
                String amt = "Your Winnings is " + "<b>" + "Bonus " + responseBeen.get(position).getUserWinningAmount() + "</b>";
                holder.mYourWinnngs.setText(Html.fromHtml(amt));
            } else {
                String amt = "Your Winnings is " + "<b>" + AppUtils.getStrFromRes(R.string.price_unit) + responseBeen.get(position).getUserWinningAmount() + "</b>";
                holder.mYourWinnngs.setText(Html.fromHtml(amt));
            }

        }
        // }


        holder.hi_main_card.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));

    }


    @Override
    public int getItemCount() {
        return responseBeen.size();
    }

    public void clear() {
        if (responseBeen == null) return;
        responseBeen.clear();
        notifyDataSetChanged();
    }

    public void addAllItem(List<JoinedContestOutput.DataBean.RecordsBean> beanList) {
        if (beanList == null || responseBeen == null) return;
        for (int i = 0; i < beanList.size(); i++) {
            addItem(beanList.get(i));
        }
    }

    public void addItem(JoinedContestOutput.DataBean.RecordsBean bean) {
        if (bean == null || responseBeen == null) return;
        responseBeen.add(bean);
        notifyItemInserted(responseBeen.size() - 1);
    }

    public String getContestGUID(int position) {


        return responseBeen.get(position).getContestGUID();
    }

    public JoinedContestOutput.DataBean.RecordsBean getItem(int position) {


        return responseBeen.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.total_winnings_amount)
        CustomTextView total_winnings_amount;

        @BindView(R.id.total_winnings)
        CustomTextView total_winnings;


        @BindView(R.id.entry_fee_amount)
        CustomTextView entry_fee_amount;

        @BindView(R.id.hi_main_card)
        CardView hi_main_card;

        @BindView(R.id.teamName)
        CustomTextView teamName;

        @BindView(R.id.points)
        CustomTextView points;

        @BindView(R.id.rank)
        CustomTextView rank;

        @BindView(R.id.winnersCount)
        CustomTextView winnersCount;

        @BindView(R.id.your_winnings)
        CustomTextView mYourWinnngs;

        @BindView(R.id.viewLeaderBoard)
        CustomTextView viewLeaderBoard;

        @BindView(R.id.view_line)
        View mLine;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }

}
