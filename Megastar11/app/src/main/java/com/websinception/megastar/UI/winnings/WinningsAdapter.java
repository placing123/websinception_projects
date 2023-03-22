package com.websinception.megastar.UI.winnings;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.websinception.megastar.R;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 *
 */

public class WinningsAdapter extends RecyclerView.Adapter<WinningsAdapter.MyViewHolder> {

    private final String winningType;
    int layoutId = 0;
    private List<WinnersRankBean> responseBeen = new ArrayList<>();
    private Context mContext;

    public WinningsAdapter(int layoutId, Context mContext, List<WinnersRankBean> responseBeen, String winningType) {
        this.responseBeen = responseBeen;
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.winningType = winningType;
    }

    @Override
    public WinningsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WinningsAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId,
                parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.rootNormal.setVisibility(View.GONE);
        holder.rootSmartPool.setVisibility(View.GONE);
        if (responseBeen.get(position).getWinningAmount() != null
                && !responseBeen.get(position).getWinningAmount().equalsIgnoreCase("")) {
            holder.rootNormal.setVisibility(View.VISIBLE);
            holder.setPay();
            holder.setTitle();
        } else {
            holder.rootSmartPool.setVisibility(View.VISIBLE);
            holder.customImageViewGiftPic.setImageURI(responseBeen.get(position).getProductUrl());
            holder.customTextViewRank.setText(holder.getTitle());
            holder.customTextViewProductName.setText(responseBeen.get(position).getProductName());
        }
    }

    public WinnersRankBean getItemData(int position) {
        if (responseBeen == null) return null;
        return responseBeen.get(position);
    }

    public void addItem(WinnersRankBean bean) {
        if (bean == null || responseBeen == null) return;
        responseBeen.add(bean);
        notifyItemInserted(responseBeen.size() - 1);
    }

    public void addAllItem(List<WinnersRankBean> beanList) {
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

        @BindView(R.id.ctv_rank)
        @Nullable
        TextView ctvRank;

        @BindView(R.id.ctv_pay)
        @Nullable
        TextView ctvPay;


        @BindView(R.id.rootNormal)
        View rootNormal;

        @BindView(R.id.rootSmartPool)
        View rootSmartPool;

        @BindView(R.id.customTextViewRank)
        CustomTextView customTextViewRank;

        @BindView(R.id.customTextViewProductName)
        CustomTextView customTextViewProductName;

        @BindView(R.id.customImageViewGiftPic)
        CustomImageView customImageViewGiftPic;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setTitle() {
            if (ctvRank == null) return;
            if (responseBeen.get(getAdapterPosition()).getFrom() == responseBeen.get(getAdapterPosition()).getTo()) {
                ctvRank.setText(AppUtils.getStrFromRes(R.string.rank) + ": " + responseBeen.get(getAdapterPosition()).getFrom());
            } else {
                ctvRank.setText(AppUtils.getStrFromRes(R.string.rank) + ": " + responseBeen.get(getAdapterPosition()).getFrom() + " - " + responseBeen.get(getAdapterPosition()).getTo());

            }
        }

        private String getTitle() {
            if (responseBeen.get(getAdapterPosition()).getFrom() == responseBeen.get(getAdapterPosition()).getTo()) {
                return AppUtils.getStrFromRes(R.string.rank) + ": " + responseBeen.get(getAdapterPosition()).getFrom();
            } else {
                return AppUtils.getStrFromRes(R.string.rank) + ": " + responseBeen.get(getAdapterPosition()).getFrom() + " - " + responseBeen.get(getAdapterPosition()).getTo();
            }
        }

        private void setPay() {
            if (ctvPay == null) return;
            if (winningType != null && winningType.equalsIgnoreCase("Free Join Contest")) {
                ctvPay.setText("Bonus " + responseBeen.get(getAdapterPosition()).getWinningAmount());
            } else {
                ctvPay.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(getAdapterPosition()).getWinningAmount());
            }
        }
    }
}
