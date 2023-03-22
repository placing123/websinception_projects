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

public class MyTeamsAdapter extends RecyclerView.Adapter<MyTeamsAdapter.MyViewHolder> {

    OnItemClickListener.OnItemClickCallback
            onItemClickCallback,
            onEditItemClickCallback,
            onViewItemClickCallback,
            onCloneItemClickCallback;

    private String teamId = "";
    private boolean isSingleEntry = true;
    private int layoutId;
    private Context mContext;
    private List<MyTeamOutput.DataBean.RecordsBean> responseBeen;
    private ArrayList<String> joinedTeamGUIDS;

    public MyTeamsAdapter(int layoutId,
                          Context mContext,
                          List<MyTeamOutput.DataBean.RecordsBean> responseBeen,
                          ArrayList<String> joinedTeamGUIDS,
                          OnItemClickListener.OnItemClickCallback onItemClickCallback,
                          OnItemClickListener.OnItemClickCallback onEditItemClickCallback,
                          OnItemClickListener.OnItemClickCallback onViewItemClickCallback,
                          OnItemClickListener.OnItemClickCallback onCloneItemClickCallback) {
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.responseBeen = responseBeen;
        this.joinedTeamGUIDS = joinedTeamGUIDS;
        this.onItemClickCallback = onItemClickCallback;
        this.onViewItemClickCallback = onViewItemClickCallback;
        this.onEditItemClickCallback = onEditItemClickCallback;
        this.onCloneItemClickCallback = onCloneItemClickCallback;
    }

    @Override
    public MyTeamsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyTeamsAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setTeam();
        if (holder.mCardViewMainCard != null)
            holder.mCardViewMainCard.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
        if (holder.ivCheck != null)
            holder.setMark();
        if (holder.ctvTitle != null)
            holder.ctvTitle.setText(responseBeen.get(position).getUserTeamName());
        if (holder.llEdit != null)
            holder.llEdit.setOnClickListener(new OnItemClickListener(position, onEditItemClickCallback));
        if (holder.llPreview != null)
            holder.llPreview.setOnClickListener(new OnItemClickListener(position, onViewItemClickCallback));
        if (holder.llCopy != null)
            holder.llCopy.setOnClickListener(new OnItemClickListener(position, onCloneItemClickCallback));
    }

    @Override
    public int getItemCount() {
        return responseBeen.size();
    }

    public void setSingleEntry(boolean singleEntry) {
        isSingleEntry = singleEntry;
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

        public void setTeam() {
            int wk = 0, bat = 0, ar = 0, bowl = 0;
            String caption = "", viceCaption = "";
            for (int i = 0; i < responseBeen.get(getAdapterPosition()).getUserTeamPlayers().size(); i++) {
                MyTeamOutput.DataBean.RecordsBean.UserTeamPlayersBean userTeam = new MyTeamOutput.DataBean.RecordsBean.UserTeamPlayersBean();

                userTeam = responseBeen.get(getAdapterPosition()).getUserTeamPlayers().get(i);

                if (userTeam.getPlayerPosition().equals(Constant.POSITION_CAPTAIN)) {
                    caption = userTeam.getPlayerName();
                } else if (userTeam.getPlayerPosition().equals(Constant.POSITION_VICE_CAPTAIN)) {
                    viceCaption = userTeam.getPlayerName();
                }
                if (userTeam.getPlayerRole().equals(Constant.ROLE_WICKETKEEPER)) {
                    wk++;
                } else if (userTeam.getPlayerRole().equals(Constant.ROLE_BATSMAN)) {
                    bat++;
                } else if (userTeam.getPlayerRole().equals(Constant.ROLE_ALLROUNDER)) {
                    ar++;
                } else if (userTeam.getPlayerRole().equals(Constant.ROLE_BOWLER)) {
                    bowl++;
                }
            }
            setCaption(caption);
            setViceCaption(viceCaption);
            setAR(ar);
            setWK(wk);
            setBAT(bat);
            setBOWL(bowl);
            if (ivCheck != null) {
                ivCheck.setVisibility(View.VISIBLE);
            }
            if (ctvAlreadyAdded != null) {
                if (isTeamAlreadyJoined(responseBeen.get(getAdapterPosition()).getUserTeamGUID())) {
                    ctvAlreadyAdded.setVisibility(View.VISIBLE);
                    ivCheck.setVisibility(View.GONE);
                } else {
                    ctvAlreadyAdded.setVisibility(View.GONE);
                }
            }

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

        public void setMark() {
            if (ivCheck == null) return;
            if (responseBeen.get(getAdapterPosition()).isSelected()) {
                ivCheck.setImageResource(R.drawable.ic_mark);
            } else {
                ivCheck.setImageResource(R.drawable.circle_trans_border_white);
            }
          /*  if (responseBeen.get(getAdapterPosition()).getIsTeamJoined().equals("Yes")) {
                ivCheck.setImageResource(R.drawable.ic_mark);
            } else {
                ivCheck.setImageResource(R.drawable.circle_trans_border_white);
            }
            if (responseBeen.get(getAdapterPosition()).getIs_user_joined_team() == 1) {
                ivCheck.setImageResource(R.drawable.ic_mark);
            } else {
                ivCheck.setImageResource(R.drawable.circle_trans_border_white);
            }*/
           /* if (ctvAlreadyAdded != null && !TextUtils.isEmpty(teamId)) {
                if (teamId.equals(responseBeen.get(getAdapterPosition()).getUserTeamID())) {
                    ctvAlreadyAdded.setVisibility(View.VISIBLE);
                } else {
                    ctvAlreadyAdded.setVisibility(View.GONE);
                }
            } else {
                ctvAlreadyAdded.setVisibility(View.GONE);
            }*/
        }
    }


    public MyTeamOutput.DataBean.RecordsBean getCloneData(int position) {
        if (responseBeen == null) return null;
        try {
            MyTeamOutput.DataBean.RecordsBean responseClone = responseBeen.get(position);
            responseClone.setUserTeamID("");
            return responseClone;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTeamName(int position) {
        if (responseBeen == null) return "";
        return responseBeen.get(position).getUserTeamName();
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

    // just clearing the data
    public void clear() {
        if (responseBeen == null) return;
        responseBeen.clear();
        notifyDataSetChanged();
    }


    public void setSelect(int position) {
        if (responseBeen == null) return;
        if (!teamId.trim().isEmpty()) {
            for (int i = 0; i < responseBeen.size(); i++) {
                responseBeen.get(i).setSelected(false);
            }
        }
        responseBeen.get(position).setSelected(!responseBeen.get(position).isSelected());
        notifyDataSetChanged();
    }


    public void setSelectAll(boolean isSelect) {
        if (responseBeen == null) return;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (!isTeamAlreadyJoined(getItemData(i).getUserTeamGUID())) {
                responseBeen.get(i).setSelected(isSelect);
            }
        }
        notifyDataSetChanged();
    }

    public void setSelect(String teamId) {
        this.teamId = teamId;
        /*if (responseBeen == null || TextUtils.isEmpty(teamId)) return;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getUserTeamGUID().equals(teamId))
                responseBeen.get(i).setIs_user_joined_team(1);
            else
                responseBeen.get(i).setIs_user_joined_team(0);
        }*/
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectedTeamsId() {
        ArrayList<String> result = new ArrayList<>();
        if (responseBeen == null) return result;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).isSelected()) {
                result.add(responseBeen.get(i).getUserTeamGUID());
            }
        }
        return result;
    }


    public boolean isTeamAlreadyJoined(String teamGUID) {
        for (String joinedTeamGUID : joinedTeamGUIDS) {
            if (teamGUID.equals(joinedTeamGUID)) {
                return true;
            }
        }
        return false;
    }

}
