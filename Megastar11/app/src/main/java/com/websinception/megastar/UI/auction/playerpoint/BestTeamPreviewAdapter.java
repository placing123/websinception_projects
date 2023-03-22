package com.websinception.megastar.UI.auction.playerpoint;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.websinception.megastar.R;
import com.websinception.megastar.beanOutput.DreamTeamOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
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

public class BestTeamPreviewAdapter extends RecyclerView.Adapter<BestTeamPreviewAdapter.MyViewHolder> {

    OnItemClickListener.OnItemClickCallback onItemClickCallback;
    int layoutId = 0;
    String pointLaval = "";
    String team1 = "";

    String teamGUID="";

    private List<DreamTeamOutput.DataBean.RecordsBean> responseBeen = new ArrayList<>();

    private Context mContext;

    public BestTeamPreviewAdapter(int layoutId, Context mContext,
                                  List<DreamTeamOutput.DataBean.RecordsBean> responseBeen,
                                  OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.responseBeen = responseBeen;
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setTeam(String team1) {
        this.team1 = team1;
    }

    @Override
    public BestTeamPreviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BestTeamPreviewAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public void setPointLaval(String value) {
        pointLaval = value;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (position % 2 == 0){
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.ll_main.getLayoutParams();
            layoutParams.setMargins(0, 20, 0, 0);
            holder.ll_main.setLayoutParams(layoutParams);
        }else {
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.ll_main.getLayoutParams();
            layoutParams.setMargins(0, 80, 0, 0);
            holder.ll_main.setLayoutParams(layoutParams);
        }

        if (holder.customTextViewName != null) {
            holder.customTextViewName.setText(ViewUtils.getPlayerName(responseBeen.get(position).getPlayerName()));


            if (team1.equals(responseBeen.get(position).getTeamGUID())) {

                holder.customTextViewName.setTextColor( mContext.getResources().getColor(R.color.white));
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    holder.customTextViewName.setBackgroundDrawable( mContext.getResources().getDrawable(R.drawable.black_solid_two_cornors));
                } else {
                    holder.customTextViewName.setBackground( mContext.getResources().getDrawable(R.drawable.black_solid_two_cornors));
                }
            } else {
                holder.customTextViewName.setTextColor( mContext.getResources().getColor(R.color.black));
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    holder.customTextViewName.setBackgroundDrawable( mContext.getResources().getDrawable(R.drawable.white_solid_with_two_cornor));
                } else {
                    holder.customTextViewName.setBackground( mContext.getResources().getDrawable(R.drawable.white_solid_with_two_cornor));
                }
            }
        }

        if (holder.ctvPosition != null) {
            if (responseBeen.get(position).getPlayerPosition().equals(Constant.POSITION_CAPTAIN)) {
                holder.ctvPosition.setVisibility(View.VISIBLE);
                holder.ctvPosition.setBackgroundResource(R.drawable.circle_blue);
                holder.ctvPosition.setText("C");
            } else if (responseBeen.get(position).getPlayerPosition().equals(Constant.POSITION_VICE_CAPTAIN)) {
                holder.ctvPosition.setVisibility(View.VISIBLE);
                holder.ctvPosition.setText("VC");
                holder.ctvPosition.setBackgroundResource(R.drawable.circle_yellow);
            } else {
                holder.ctvPosition.setVisibility(View.INVISIBLE);
            }
        }

        if (holder.ctvCredits != null)

            holder.ctvCredits.setText(responseBeen.get(position).getTotalPoints()+" Pts");
           // holder.ctvCredits.setText(responseBeen.get(position).getPointCredits() + " " + pointLaval);

      /*  {
            try {
                JSONObject jsonObject= new JSONObject(AppUtils.gsonToJSON(responseBeen.get(position)));

                //holder.ctvCredits.setText(jsonObject.get("PointCredits").toString() + " " + pointLaval);
               // holder.ctvCredits.setText(responseBeen.get(position).getPointCredits()+ " " + pointLaval);
                if (responseBeen.get(position).getPoints()!= null) {
                    Double price = Double.valueOf(responseBeen.get(position).getPoints());
                    DecimalFormat format = new DecimalFormat("0.#");
                    holder.ctvCredits.setText();
//
                }
                Log.d("PointsJson",jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/

        if (holder.ivPlayer != null)
            ViewUtils.setImageUrl(holder.ivPlayer, responseBeen.get(position).getPlayerPic());

            //Picasso.get().load(responseBeen.get(position).getPlayerPic()).placeholder(R.drawable.player).into(holder.ivPlayer);


        if (holder.mCardViewMainCard != null) {
            holder.mCardViewMainCard.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
        }

        if (holder.ivPlayer != null) {
            holder.ivPlayer.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
        }
    }

    public String getPlayerId(int position) {
        return responseBeen.get(position).getPlayerGUID();
    }

    public DreamTeamOutput.DataBean.RecordsBean getPlayer(int position) {
        return responseBeen.get(position);
    }


   /* public String getPlayerPosition(int position) {
        return responseBeen.get(position).getPosition();
    }*/

    public DreamTeamOutput.DataBean.RecordsBean getItemData(int position) {
        if (responseBeen == null) return null;
        return responseBeen.get(position);
    }

    public void addItem(DreamTeamOutput.DataBean.RecordsBean bean) {
        if (bean == null || responseBeen == null) return;
        responseBeen.add(bean);
        notifyItemInserted(responseBeen.size() - 1);
    }

    public void addAllItem(List<DreamTeamOutput.DataBean.RecordsBean> beanList) {
        if (beanList == null || responseBeen == null) return;
        for (int i = 0; i < beanList.size(); i++) {
            addItem(beanList.get(i));
        }
    }

    public void updateTeamData(List<DreamTeamOutput.DataBean.RecordsBean> beanList) {
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
        @BindView(R.id.ctv_position)
        @Nullable
        CustomTextView ctvPosition;
        @BindView(R.id.ctv_credits)
        @Nullable
        CustomTextView ctvCredits;

        @BindView(R.id.ll_main)
        @Nullable
        LinearLayout ll_main;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
