package com.mw.fantasy.UI.createCaption;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mw.fantasy.R;
import com.mw.fantasy.beanOutput.PlayersOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.textdrawable.TextDrawable;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.OnItemClickListener;
import com.mw.fantasy.utility.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 *
 */

public class CreateCaptionAdapter extends RecyclerView.Adapter<CreateCaptionAdapter.MyViewHolder> {

    OnItemClickListener.OnItemClickCallback onCaptionItemClickCallback, onViceCaptionItemClickCallback, onItemClickCallBack;
    private List<PlayersOutput.DataBean.RecordsBean> responseBeen = new ArrayList<>();
    private Context mContext;
    private String localteamId;

    public CreateCaptionAdapter(Context mContext, List<PlayersOutput.DataBean.RecordsBean> responseBeen,
                                OnItemClickListener.OnItemClickCallback onCaptionItemClickCallback,
                                OnItemClickListener.OnItemClickCallback onViceCaptionItemClickCallback,
                                OnItemClickListener.OnItemClickCallback onItemClickCallBack) {
        this.responseBeen = responseBeen;
        this.mContext = mContext;
        this.onCaptionItemClickCallback = onCaptionItemClickCallback;
        this.onViceCaptionItemClickCallback = onViceCaptionItemClickCallback;
        this.onItemClickCallBack = onItemClickCallBack;
    }

    public void setLocalteamId(String localteamId) {
        this.localteamId = localteamId;
    }

    @Override
    public CreateCaptionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;

        switch (viewType) {
            case 0:
                layout = R.layout.list_item_captains;
                break;
            case 1:
                layout = R.layout.list_item_captains_heading;
                break;
        }
        return new CreateCaptionAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return responseBeen.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (holder.customTextViewName != null)
            holder.customTextViewName.setText(responseBeen.get(position).getPlayerName());
        if (holder.ctvCountry != null) {
            holder.ctvCountry.setText(responseBeen.get(position).getTeamNameShort());
            if (localteamId != null && !localteamId.trim().isEmpty()) {
                if (responseBeen.get(position).getTeamGUID().equals(localteamId)) {
                    holder.ctvCountry.setTextColor(Color.WHITE);
                    holder.ctvCountry.setBackgroundResource(R.drawable.bg_local);
                } else {
                    holder.ctvCountry.setBackgroundResource(R.drawable.bg_visitor);
                    holder.ctvCountry.setTextColor(Color.parseColor("#e82b2b"));
                }
            } else {
                holder.ctvCountry.setTextColor(Color.BLACK);
                holder.ctvCountry.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        if (holder.ctv_per_cap!=null&&holder.ctv_per_vcap!=null) {
            holder.ctv_per_cap.setText(responseBeen.get(position).getPlayerSelectedCaptain() + "%");
            holder.ctv_per_vcap.setText(responseBeen.get(position).getPlayerSelectedViceCaptain() + "%");
        }




        if (holder.ctvCredits != null)
            holder.ctvCredits.setText(responseBeen.get(position).getTotalPointCredits() + " " + AppUtils.getStrFromRes(R.string.points));

        // holder.ctvCredits.setText("0" + " " + AppUtils.getStrFromRes(R.string.points));
        if (holder.ivPlayer != null)
            // ViewUtils.setImageUrl(holder.ivPlayer, responseBeen.get(position).getPlayerPic());


            /*if (responseBeen.get(position).getTeamGUID().equals(responseBeen.get(1).getTeamGUID())) {
                holder.ivPlayer.getBackground().setColorFilter(Color.parseColor("#FFA400"),
                        PorterDuff.Mode.SRC_ATOP);
                holder.ivPlayer.setText(responseBeen.get(position).getTeamNameShort());
            } else {
                holder.ivPlayer.getBackground().setColorFilter(Color.parseColor("#FF5C00"),
                        PorterDuff.Mode.SRC_ATOP);
                holder.ivPlayer.setText(responseBeen.get(position).getTeamNameShort());

            }*/

            if (TextUtils.isEmpty(responseBeen.get(position).getPlayerPic())) {
                TextDrawable drawable2 = TextDrawable.builder().beginConfig()
                        .fontSize(45)  //size in px
                        .bold()
                        .toUpperCase()
                        .endConfig()
                        .buildRound(AppUtils.getNameCharacters(responseBeen.get(position).getPlayerName()), AppUtils.getRandomColor());
                if (drawable2 != null)
                    holder.ivPlayer.setImageDrawable(drawable2);

            } else {
                ViewUtils.setImageUrl(holder.ivPlayer, responseBeen.get(position).getPlayerPic());

                Log.d("getPlayerPic", responseBeen.get(position).getPlayerPic());
            }

        if (holder.ivPoints != null && holder.customTextViewCaption != null && holder.customTextViewViceCaption != null)
            holder.setCaptionButton(position);
        if (holder.customTextViewCaption != null)
            holder.customTextViewCaption.setOnClickListener(new OnItemClickListener(position, onCaptionItemClickCallback));
        if (holder.customTextViewViceCaption != null)
            holder.customTextViewViceCaption.setOnClickListener(new OnItemClickListener(position, onViceCaptionItemClickCallback));

        if (holder.mCardViewMainCard != null) {
            holder.mCardViewMainCard.setOnClickListener(new OnItemClickListener(position, onItemClickCallBack));
        }
    }


    public String getPlayerId(int position) {
        return responseBeen.get(position).getPlayerGUID();
    }

    public boolean isSelected(int position) {
        return responseBeen.get(position).isSelected();
    }

    public String getPlayerPosition(int position) {
        return responseBeen.get(position).getPosition();
    }

    public List<PlayersOutput.DataBean.RecordsBean> getPlayers() {
        List<PlayersOutput.DataBean.RecordsBean> players = new ArrayList<>();
        if (responseBeen == null) return null;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getViewType() == 0) {
                players.add(responseBeen.get(i));
            }
        }
        return players;
    }

    public PlayersOutput.DataBean.RecordsBean getPlayer(int position) {
        return responseBeen.get(position);
    }

    public void setCaption(int position) {
        if (responseBeen == null) return;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getPosition().equals(Constant.POSITION_CAPTAIN)) {
                responseBeen.get(i).setPosition(Constant.POSITION_PLAYER);
            }
        }
        responseBeen.get(position).setPosition(Constant.POSITION_CAPTAIN);
        notifyDataSetChanged();
    }

    public void setViceCaption(int position) {
        if (responseBeen == null) return;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getPosition().equals(Constant.POSITION_VICE_CAPTAIN)) {
                responseBeen.get(i).setPosition(Constant.POSITION_PLAYER);
            }
        }
        responseBeen.get(position).setPosition(Constant.POSITION_VICE_CAPTAIN);
        notifyDataSetChanged();
    }

    public boolean isCaptionAndVoiceCaptionExist() {
        boolean isCaption = false, isViceCaption = false;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getPosition().equals(Constant.POSITION_CAPTAIN)) {
                isCaption = true;
            } else if (responseBeen.get(i).getPosition().equals(Constant.POSITION_VICE_CAPTAIN)) {
                isViceCaption = true;
            }
        }
        return isCaption && isViceCaption ? true : false;
    }

    public void toggleSelected(int position) {
        responseBeen.get(position).setSelected(!responseBeen.get(position).isSelected());
    }

    public PlayersOutput.DataBean.RecordsBean getItemData(int position) {
        if (responseBeen == null) return null;
        return responseBeen.get(position);
    }

    public void addItem(PlayersOutput.DataBean.RecordsBean bean) {
        if (bean == null || responseBeen == null) return;
        responseBeen.add(bean);
        notifyItemInserted(responseBeen.size() - 1);
    }

    public void addAllItem(List<PlayersOutput.DataBean.RecordsBean> beanList) {
        if (beanList == null || responseBeen == null) return;
        for (int i = 0; i < beanList.size(); i++) {
            addItem(beanList.get(i));
        }
    }

    public void updateTeamData(List<PlayersOutput.DataBean.RecordsBean> beanList) {
        clear();
        if (beanList == null || responseBeen == null) return;
        for (int i = 0; i < beanList.size(); i++) {
            addItem(beanList.get(i));
        }
    }

    public int getSelectedCount() {
        if (responseBeen == null) return 0;
        int count = 0;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (isSelected(i)) count++;
        }
        return count;
    }

    public void clear() {
        if (responseBeen == null) return;
        responseBeen.clear();
        notifyDataSetChanged();
    }

    public void setCrossButton(ImageView ivCross, int position) {
        ivCross.setImageResource(isSelected(position) ? R.drawable.ic_cancel : R.drawable.ic_add_button);
        ivCross.setColorFilter(Color.parseColor(isSelected(position) ? "#DA473D" : "#3CB371"), PorterDuff.Mode.SRC_IN);
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
        @BindView(R.id.ctv_country)
        @Nullable
        CustomTextView ctvCountry;
        @BindView(R.id.ctv_credits)
        @Nullable
        CustomTextView ctvCredits;
        @BindView(R.id.iv_points)
        @Nullable
        ImageView ivPoints;
        @BindView(R.id.ctv_caption)
        @Nullable
        CustomTextView customTextViewCaption;
        @BindView(R.id.ctv_vice_caption)
        @Nullable
        CustomTextView customTextViewViceCaption;



        @BindView(R.id.ctv_per_cap)
        @Nullable
        CustomTextView ctv_per_cap;

        @BindView(R.id.ctv_per_vcap)
        @Nullable
        CustomTextView ctv_per_vcap;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setCaptionButton(int position) {
            if (getPlayerPosition(position).equals(Constant.POSITION_CAPTAIN)) {
                ivPoints.setImageResource(R.drawable.captainselection);
                ivPoints.setVisibility(View.VISIBLE);
                customTextViewCaption.setTextColor(mContext.getResources().getColor(R.color.white));
                customTextViewViceCaption.setTextColor(mContext.getResources().getColor(R.color.warm_grey));
                customTextViewCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_red));
                customTextViewViceCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_white_border_gray));
            } else if (getPlayerPosition(position).equals(Constant.POSITION_VICE_CAPTAIN)) {
                ivPoints.setImageResource(R.drawable.vicecaptainlabel);
                ivPoints.setVisibility(View.VISIBLE);
                customTextViewCaption.setTextColor(mContext.getResources().getColor(R.color.warm_grey));
                customTextViewViceCaption.setTextColor(mContext.getResources().getColor(R.color.white));
                customTextViewCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_white_border_gray));
                customTextViewViceCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_red));
            } else {
                ivPoints.setVisibility(View.GONE);
                customTextViewCaption.setTextColor(mContext.getResources().getColor(R.color.warm_grey));
                customTextViewViceCaption.setTextColor(mContext.getResources().getColor(R.color.warm_grey));
                customTextViewCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_white_border_gray));
                customTextViewViceCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_white_border_gray));
            }
        }
    }

}
