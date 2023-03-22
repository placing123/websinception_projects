package com.mw.fantasy.UI.auction.auctionHome;

import android.content.Context;
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
import com.mw.fantasy.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.textdrawable.TextDrawable;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.OnItemClickListener;
import com.mw.fantasy.utility.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 *
 */

public class ChooseCaptainAdapter extends RecyclerView.Adapter<ChooseCaptainAdapter.MyViewHolder> {

    OnItemClickListener.OnItemClickCallback onCaptionItemClickCallback, onViceCaptionItemClickCallback, onItemClickCallBack;
    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> responseBeen = new ArrayList<>();
    private Context mContext;
    private String roundId;

    public ChooseCaptainAdapter(Context mContext, String roundId,
                                List<GetAuctionPlayerOutput.DataBean.RecordsBean> responseBeen,
                                OnItemClickListener.OnItemClickCallback onCaptionItemClickCallback,
                                OnItemClickListener.OnItemClickCallback onViceCaptionItemClickCallback,
                                OnItemClickListener.OnItemClickCallback onItemClickCallBack) {
        this.responseBeen = responseBeen;
        this.mContext = mContext;
        this.roundId = roundId;
        this.onCaptionItemClickCallback = onCaptionItemClickCallback;
        this.onViceCaptionItemClickCallback = onViceCaptionItemClickCallback;
        this.onItemClickCallBack = onItemClickCallBack;
    }


    Map<String, Object> map = new HashMap<String, Object>() {{
        put("WicketKeeper", "WK");
        put("Batsman", "Bat");
        put("AllRounder", "AR");
        put("Bowler", "Bowl");

    }};

    @Override
    public ChooseCaptainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;

        switch (viewType) {
            case 0:
                layout = R.layout.list_auction_item_captains;
                break;
            case 1:
                layout = R.layout.list_auction_item_captains_heading;
                break;
        }
        return new ChooseCaptainAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return responseBeen.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (holder.customTextViewName != null)
            holder.customTextViewName.setText(responseBeen.get(position).getPlayerName());
        if (holder.ctvCountry != null)
            holder.ctvCountry.setText(responseBeen.get(position).getTeamNameShort().trim().replace(" ", "") + " - " + map.get(responseBeen.get(position).getPlayerRole()));

        if (holder.ctvCredits != null)
            holder.ctvCredits.setText(responseBeen.get(position).getTotalPoints() + " " + AppUtils.getStrFromRes(R.string.points));

        if (holder.ivPlayer != null)

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

/*
        if (responseBeen.get(position).getPlayerPosition() != null && responseBeen.get(position).getPlayerPosition().equals(Constant.POSITION_CAPTAIN)) {
            responseBeen.get(position).setPosition(Constant.POSITION_CAPTAIN);

        } else if (responseBeen.get(position).getPlayerPosition() != null && responseBeen.get(position).getPlayerPosition().equals(Constant.POSITION_VICE_CAPTAIN)) {
            responseBeen.get(position).setPosition(Constant.POSITION_VICE_CAPTAIN);
        }
*/

        if (holder.ivPoints != null && holder.customTextViewCaption != null && holder.customTextViewViceCaption != null)
            holder.setCaptionButton(position);
        if (holder.customTextViewCaption != null)
            holder.customTextViewCaption.setOnClickListener(new OnItemClickListener(position, onCaptionItemClickCallback));
        if (holder.customTextViewViceCaption != null)
            holder.customTextViewViceCaption.setOnClickListener(new OnItemClickListener(position, onViceCaptionItemClickCallback));

        if (holder.mCardViewMainCard != null) {
            holder.mCardViewMainCard.setOnClickListener(new OnItemClickListener(position, onItemClickCallBack));
        }
        if (holder.ivPlayer != null) {
            holder.ivPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AuctionPlayerStatsActivity.start(mContext,
                            responseBeen.get(position).getSeriesGUID(),
                            responseBeen.get(position).getPlayerGUID(),
                            roundId,
                            responseBeen.get(position).getSeriesID(),false);
                }
            });
        }


    }


    public String getPlayerId(int position) {
        return responseBeen.get(position).getPlayerGUID();
    }


    public String getPlayerPosition(int position) {
        return responseBeen.get(position).getPlayerPosition();
    }

    public List<GetAuctionPlayerOutput.DataBean.RecordsBean> getPlayers() {
        List<GetAuctionPlayerOutput.DataBean.RecordsBean> players = new ArrayList<>();
        if (responseBeen == null) return null;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getViewType() == 0) {
                players.add(responseBeen.get(i));
            }
        }
        return players;
    }

    public GetAuctionPlayerOutput.DataBean.RecordsBean getPlayer(int position) {
        return responseBeen.get(position);
    }

    public void setCaption(int position) {
        if (responseBeen == null) return;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getPlayerPosition() != null && responseBeen.get(i).getPlayerPosition().equals(Constant.POSITION_CAPTAIN)) {
                responseBeen.get(i).setPlayerPosition(Constant.POSITION_PLAYER);
            }
        }
        responseBeen.get(position).setPlayerPosition(Constant.POSITION_CAPTAIN);
        notifyDataSetChanged();
    }

    public void setViceCaption(int position) {
        if (responseBeen == null) return;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getPlayerPosition() != null && responseBeen.get(i).getPlayerPosition().equals(Constant.POSITION_VICE_CAPTAIN)) {
                responseBeen.get(i).setPlayerPosition(Constant.POSITION_PLAYER);
            }
        }
        responseBeen.get(position).setPlayerPosition(Constant.POSITION_VICE_CAPTAIN);
        notifyDataSetChanged();
    }

    public boolean isCaptionAndVoiceCaptionExist() {
        boolean isCaption = false, isViceCaption = false;
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getPlayerPosition() != null && responseBeen.get(i).getPlayerPosition().equals(Constant.POSITION_CAPTAIN)) {
                isCaption = true;
            } else if (responseBeen.get(i).getPlayerPosition() != null && responseBeen.get(i).getPlayerPosition().equals(Constant.POSITION_VICE_CAPTAIN)) {
                isViceCaption = true;
            }
        }
        if (responseBeen.size() > 2) {
            return isCaption && isViceCaption;
        } else if (responseBeen.size() == 2) {
            return isCaption;
        } else {
            return false;
        }

    }

    public String getCaptain() {
        String s = "";
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getPlayerPosition() != null && responseBeen.get(i).getPlayerPosition().equals(Constant.POSITION_CAPTAIN)) {
                s = responseBeen.get(i).getPlayerGUID();
            }
        }
        return s;
    }


    public String getViceCaptain() {
        String s = "";
        for (int i = 0; i < responseBeen.size(); i++) {
            if (responseBeen.get(i).getPlayerPosition() != null && responseBeen.get(i).getPlayerPosition().equals(Constant.POSITION_VICE_CAPTAIN)) {
                s = responseBeen.get(i).getPlayerGUID();
            }
        }
        return s;
    }


    public GetAuctionPlayerOutput.DataBean.RecordsBean getItemData(int position) {
        if (responseBeen == null) return null;
        return responseBeen.get(position);
    }

    public void addItem(GetAuctionPlayerOutput.DataBean.RecordsBean bean) {
        if (bean == null || responseBeen == null) return;
        responseBeen.add(bean);
        notifyItemInserted(responseBeen.size() - 1);
    }

    public void addAllItem(List<GetAuctionPlayerOutput.DataBean.RecordsBean> beanList) {
        if (beanList == null || responseBeen == null) return;
        for (int i = 0; i < beanList.size(); i++) {
            addItem(beanList.get(i));
        }
    }

    public void updateTeamData(List<GetAuctionPlayerOutput.DataBean.RecordsBean> beanList) {
        clear();
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

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setCaptionButton(int position) {
            if (getPlayerPosition(position).equals(Constant.POSITION_CAPTAIN) || responseBeen.get(position).getPlayerPosition().equals(Constant.POSITION_CAPTAIN)) {
                ivPoints.setImageResource(R.drawable.captainselection);
                ivPoints.setVisibility(View.VISIBLE);
                customTextViewCaption.setTextColor(mContext.getResources().getColor(R.color.white));
                customTextViewViceCaption.setTextColor(mContext.getResources().getColor(R.color.warm_grey));
                customTextViewCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_blue_bg_white));
                customTextViewViceCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_white_border_gray));
            } else if (getPlayerPosition(position).equals(Constant.POSITION_VICE_CAPTAIN) || responseBeen.get(position).getPlayerPosition().equals(Constant.POSITION_VICE_CAPTAIN)) {
                ivPoints.setImageResource(R.drawable.vicecaptainlabel);
                ivPoints.setVisibility(View.VISIBLE);
                customTextViewCaption.setTextColor(mContext.getResources().getColor(R.color.warm_grey));
                customTextViewViceCaption.setTextColor(mContext.getResources().getColor(R.color.white));
                customTextViewCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_white_border_gray));
                customTextViewViceCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_yellow_bg_white));
            } else {
                ivPoints.setVisibility(View.GONE);
                customTextViewCaption.setTextColor(mContext.getResources().getColor(R.color.warm_grey));
                customTextViewViceCaption.setTextColor(mContext.getResources().getColor(R.color.warm_grey));
                customTextViewCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_white_border_gray));
                customTextViewViceCaption.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_white_border_gray));
            }
        }
    }


    public List<GetAuctionPlayerOutput.DataBean.RecordsBean> getResponseBeen() {
        return responseBeen;
    }
}
