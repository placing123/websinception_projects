package com.websinception.megastar.UI.myTeams;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.websinception.megastar.R;
import com.websinception.megastar.beanOutput.MyTeamOutput;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 *
 */

public class MyFootballTeamsAdapter extends RecyclerView.Adapter<MyFootballTeamsAdapter.MyViewHolder> {

    OnItemClickListener.OnItemClickCallback onItemClickCallback, onEditItemClickCallback, onViewItemClickCallback, onCloneItemClickCallback;
    int layoutId = 0;
    String teamId = "";
    private List<MyTeamOutput.DataBean.RecordsBean> responseBeen = new ArrayList<>();
    private Context mContext;

    public MyFootballTeamsAdapter(int layoutId, Context mContext, List<MyTeamOutput.DataBean.RecordsBean> responseBeen,
                                  OnItemClickListener.OnItemClickCallback onItemClickCallback,
                                  OnItemClickListener.OnItemClickCallback onEditItemClickCallback,
                                  OnItemClickListener.OnItemClickCallback onViewItemClickCallback,
                                  OnItemClickListener.OnItemClickCallback onCloneItemClickCallback) {
        this.responseBeen = responseBeen;
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.onItemClickCallback = onItemClickCallback;
        this.onViewItemClickCallback = onViewItemClickCallback;
        this.onEditItemClickCallback = onEditItemClickCallback;
        this.onCloneItemClickCallback = onCloneItemClickCallback;
    }

    @Override
    public MyFootballTeamsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyFootballTeamsAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.setTeam();

        if (holder.mCardViewMainCard != null)
            holder.mCardViewMainCard.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
        if (holder.ivCheck != null)
            holder.setMark();
        if (holder.llEdit != null)
            holder.llEdit.setOnClickListener(new OnItemClickListener(position, onEditItemClickCallback));
        if (holder.llPreview != null)
            holder.llPreview.setOnClickListener(new OnItemClickListener(position, onViewItemClickCallback));
        if (holder.llCopy != null)
            holder.llCopy.setOnClickListener(new OnItemClickListener(position, onCloneItemClickCallback));

    }

    public void setSelect(int position) {
        if (responseBeen == null) return;
        for (int i = 0; i < responseBeen.size(); i++) {
            responseBeen.get(i).setSelect(false);
        }
        responseBeen.get(position).setSelect(true);
        notifyDataSetChanged();
    }

    public void setSelect(String teamId) {
        this.teamId = teamId;
        if (responseBeen == null || TextUtils.isEmpty(teamId)) return;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getUserTeamGUID().equals(teamId))
                responseBeen.get(i).setSelect(true);
            else
                responseBeen.get(i).setSelect(false);
        }
        notifyDataSetChanged();
    }

    public String getSelectTeamId() {
        if (responseBeen == null) return "";
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).isSelect())
                return responseBeen.get(i).getUserTeamGUID();
        }
        return "";
    }

    public String getTeamName(int position) {
        if (responseBeen == null) return "";
        return responseBeen.get(position).getUserTeamName();
    }

    public MyTeamOutput.DataBean.RecordsBean getCloneData(int position) {
        if (responseBeen == null) return null;
        try {
            MyTeamOutput.DataBean.RecordsBean responseClone = (MyTeamOutput.DataBean.RecordsBean) responseBeen.get(position);
            responseClone.setUserTeamID("");
            return responseClone;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MyTeamOutput.DataBean.RecordsBean getItemData(int position) {
        if (responseBeen == null) return null;
        return responseBeen.get(position);
    }

    public void addItem(MyTeamOutput.DataBean.RecordsBean bean) {
        if (bean == null || responseBeen == null) return;
        responseBeen.add(bean);
        notifyItemInserted(responseBeen.size() - 1);
    }

    public void addAllItem(List<MyTeamOutput.DataBean.RecordsBean> beanList) {
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
        @BindView(R.id.iv_check)
        @Nullable
        ImageView ivCheck;

        @BindView(R.id.ctv_vice_captain)
        @Nullable
        CustomTextView ctvViceCaptain;
        @BindView(R.id.ctv_captain)
        @Nullable
        CustomTextView ctvCaptain;
        @BindView(R.id.ctv_title)
        @Nullable
        CustomTextView ctvTitle;
        @BindView(R.id.ctv_already_added)
        @Nullable
        CustomTextView ctvAlreadyAdded;

        @BindView(R.id.ctv_wk)
        CustomTextView customTextViewWK;
        @BindView(R.id.ctv_bat)
        CustomTextView customTextViewBAT;
        @BindView(R.id.ctv_ar)
        CustomTextView customTextViewAR;
        @BindView(R.id.ctv_bowl)
        CustomTextView customTextViewBOWL;

        @BindView(R.id.ll_edit)
        @Nullable
        LinearLayout llEdit;
        @BindView(R.id.ll_preview)
        @Nullable
        LinearLayout llPreview;
        @BindView(R.id.ll_copy)
        @Nullable
        LinearLayout llCopy;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setMark() {
            if (ivCheck == null) return;
            if (responseBeen.get(getAdapterPosition()).isSelect()) {
                ivCheck.setImageResource(R.drawable.ic_mark);
            } else {
                ivCheck.setImageResource(R.drawable.circle_trans_border_white);
            }
            if (ctvAlreadyAdded != null && !TextUtils.isEmpty(teamId)) {
                if (teamId.equals(responseBeen.get(getAdapterPosition()).getTeamId())) {
                    ctvAlreadyAdded.setVisibility(View.VISIBLE);
                } else {
                    ctvAlreadyAdded.setVisibility(View.GONE);
                }
            } else {
                ctvAlreadyAdded.setVisibility(View.GONE);
            }
        }

        public void setTeam() {
            int Gk = 0, def = 0, st = 0, mid = 0;
            String caption = "", viceCaption = "";
            for (int i = 0; i < responseBeen.get(getAdapterPosition()).getUserTeamPlayers().size(); i++) {
                if (responseBeen.get(getAdapterPosition()).getUserTeamPlayers().get(i).getPlayerPosition().equals(Constant.POSITION_CAPTAIN)) {
                    caption = responseBeen.get(getAdapterPosition()).getUserTeamPlayers().get(i).getPlayerName();
                } else if (responseBeen.get(getAdapterPosition()).getUserTeamPlayers().get(i).getPlayerPosition().equals(Constant.POSITION_VICE_CAPTAIN)) {
                    viceCaption = responseBeen.get(getAdapterPosition()).getUserTeamPlayers().get(i).getPlayerName();
                }
                if (responseBeen.get(getAdapterPosition()).getUserTeamPlayers().get(i).getPlayerRole().equals(Constant.ROLE_GOALKEEPER)) {
                    Gk++;
                } else if (responseBeen.get(getAdapterPosition()).getUserTeamPlayers().get(i).getPlayerRole().equals(Constant.ROLE_DEFENDER)) {
                    def++;
                } else if (responseBeen.get(getAdapterPosition()).getUserTeamPlayers().get(i).getPlayerRole().equals(Constant.ROLE_FORWARD)) {
                    st++;
                } else if (responseBeen.get(getAdapterPosition()).getUserTeamPlayers().get(i).getPlayerRole().equals(Constant.ROLE_MIDFIELDER)) {
                    mid++;
                }
            }
            setCaption(caption);
            setViceCaption(viceCaption);
            setAR(st);
            setWK(Gk);
            setBAT(def);
            setBOWL(mid);
            setTitle();

        }

        private void setTitle() {
            if (ctvTitle == null) return;
            ctvTitle.setText(String.format(AppUtils.getStrFromRes(R.string.team_1), getAdapterPosition() + 1));
        }

        private void setCaption(String name) {
            if (ctvCaptain == null) return;
            ctvCaptain.setText(name);
        }

        private void setViceCaption(String name) {
            if (ctvViceCaptain == null) return;
            ctvViceCaptain.setText(name);
        }

        private void setWK(int count) {
            if (customTextViewWK == null) return;
            customTextViewWK.setText(count + "");
        }

        private void setBAT(int count) {
            if (customTextViewBAT == null) return;
            customTextViewBAT.setText(count + "");
        }

        private void setAR(int count) {
            if (customTextViewAR == null) return;
            customTextViewAR.setText(count + "");
        }

        private void setBOWL(int count) {
            if (customTextViewBOWL == null) return;
            customTextViewBOWL.setText(count + "");
        }
    }

}
