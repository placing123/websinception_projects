package com.websinception.megastar.UI.matches;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.websinception.megastar.R;
import com.websinception.megastar.beanOutput.MatchResponseOut;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.OnItemClickListener;
import com.websinception.megastar.utility.TimeUtils;
import com.websinception.megastar.utility.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 *
 */

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MyViewHolder> {

    private List<MatchResponseOut.DataBean.RecordsBean> responseBeen = new ArrayList<>();
    private Context mContext;
    OnItemClickListener.OnItemClickCallback onItemClickCallback, onEditItemClickCallback, onDeleteItemClickCallback;
    int layoutId = 0;
    String type;
    int gametype;

    int tag = 0;

    int upcomingMatchesTime = 0;
    String[] color_code;


    public MatchesAdapter(String type, int layoutId, Context mContext, int gametype, List<MatchResponseOut.DataBean.RecordsBean> responseBeen,
                          OnItemClickListener.OnItemClickCallback onItemClickCallback,
                          OnItemClickListener.OnItemClickCallback onEditItemClickCallback,
                          OnItemClickListener.OnItemClickCallback onDeleteItemClickCallback,
                          int upcomingMatchesTime) {
        this.responseBeen = responseBeen;
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.type = type;
        this.gametype = gametype;
        this.onItemClickCallback = onItemClickCallback;
        this.onEditItemClickCallback = onEditItemClickCallback;
        this.onDeleteItemClickCallback = onDeleteItemClickCallback;
        this.upcomingMatchesTime = upcomingMatchesTime;
        color_code = mContext.getResources().getStringArray(R.array.colors);
    }

    public MatchesAdapter(int tag, String type, int layoutId, Context mContext, int gametype, List<MatchResponseOut.DataBean.RecordsBean> responseBeen,
                          OnItemClickListener.OnItemClickCallback onItemClickCallback,
                          OnItemClickListener.OnItemClickCallback onEditItemClickCallback,
                          OnItemClickListener.OnItemClickCallback onDeleteItemClickCallback) {
        this.responseBeen = responseBeen;
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.type = type;
        this.gametype = gametype;
        this.onItemClickCallback = onItemClickCallback;
        this.onEditItemClickCallback = onEditItemClickCallback;
        this.onDeleteItemClickCallback = onDeleteItemClickCallback;
        this.tag = tag;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_matches, parent, false));

    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(responseBeen.get(position).getTeamFlagLocal()) || TextUtils.isEmpty(responseBeen.get(position).getTeamFlagVisitor())) {
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.view_left_wing.setBackgroundColor(Color.parseColor(color_code[position%8]));
        holder.view_right_wing.setBackgroundColor(Color.parseColor(color_code[7-(position%8)]));
       // holder.view_right_wing.setBackgroundColor(Color.parseColor(color_code[7-(getAdapterPosition()%7)]));

        holder.mCustomImageViewTeam1.getHierarchy().setFailureImage(R.drawable.match_defult150);
        holder.mCustomImageViewTeam1.getHierarchy().setPlaceholderImage(R.drawable.match_defult150);

        holder.mCustomImageViewTeam2.getHierarchy().setFailureImage(R.drawable.match_defult150);
        holder.mCustomImageViewTeam2.getHierarchy().setPlaceholderImage(R.drawable.match_defult150);


        ViewUtils.setImageUrl(holder.mCustomImageViewTeam1, responseBeen.get(position).getTeamFlagLocal());
        ViewUtils.setImageUrl(holder.mCustomImageViewTeam2, responseBeen.get(position).getTeamFlagVisitor());

        holder.mCustomTextViewTeam1.setText(responseBeen.get(position).getTeamNameShortLocal());
        holder.mCustomTextViewTeam2.setText(responseBeen.get(position).getTeamNameShortVisitor());

        /*String seriesName="";
        if(responseBeen.get(position).getSeriesName().split("\\s+").length>3){
            seriesName= responseBeen.get(position).getSeriesName().substring(0,3);
        }else {
            seriesName= responseBeen.get(position).getSeriesName();
        }*/


        holder.ctv_series_name.setText(responseBeen.get(position).getSeriesName());
        holder.ctv_match_type.setText(responseBeen.get(position).getMatchType());

        if (responseBeen.get(position).getStatus().equals(Constant.Pending)) {
            if (responseBeen.get(position).getContestAvailable().equalsIgnoreCase("Yes") &&
                    responseBeen.get(position).getTeamPlayersAvailable().equalsIgnoreCase("Yes")) {
                holder.bluredRel.setVisibility(View.GONE);
            } else {
                holder.bluredRel.setVisibility(View.VISIBLE);
            }

        } else {
            holder.bluredRel.setVisibility(View.GONE);
        }


        if (type.equals("FIXTURE")) {
//            holder.ctv_timer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_time, 0, 0, 0);
            holder.setTime();
            holder.ctv_timer.setTextColor(mContext.getResources().getColor(R.color.black));

            // holder.setTimeStatic();

        } else if (type.equals("LIVE")) {
            holder.bluredRel.setVisibility(View.GONE);
            holder.ctv_timer.setText(AppUtils.getStrFromRes(R.string.in_progress));
            holder.ctv_timer.setTextColor(mContext.getResources().getColor(R.color.black));
        } else if (type.equals("COMPLETED")) {
            holder.bluredRel.setVisibility(View.GONE);
            // holder.ctv_timer.setText(AppUtils.getStrFromRes(R.string.completed));
            holder.ctv_timer.setText(responseBeen.get(position).getStatus());
            if (responseBeen.get(position).getStatus().equals("Completed")) {
                holder.ctv_timer.setTextColor(mContext.getResources().getColor(R.color.black));
            } else
                holder.ctv_timer.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        holder.mCardViewMainCard.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));

        if (tag == 0) {
            holder.linout.setVisibility(View.GONE);
            holder.contest_joined.setVisibility(View.GONE);
            holder.ctv_VS.setVisibility(View.GONE);
            if (responseBeen.get(position).getIsPlayingXINotificationSent().equals("Yes")) {
                //holder.linout.setVisibility(View.VISIBLE);
                holder.ctv_VS.setText("Playing 11 announced");
                holder.ctv_VS.setVisibility(View.VISIBLE);
            } else {
                holder.ctv_VS.setVisibility(View.GONE);
            }
        } else {
            holder.linout.setVisibility(View.VISIBLE);
            holder.ctv_VS.setVisibility(View.GONE);
            holder.contest_joined.setVisibility(View.VISIBLE);
           /* holder.contest_joined.setText(responseBeen.get(position).getTotalJoinedContest()
                    +" "+AppUtils.getStrFromRes(R.string.contest_joined));*/
        }

        if (responseBeen.get(position).getMatchTypeByApi() != null) {
            holder.ctv_game_type.setVisibility(View.VISIBLE);
            if (responseBeen.get(position).getMatchTypeByApi().equalsIgnoreCase("Real")) {
                holder.ctv_game_type.setVisibility(View.GONE);
            } else {
                holder.ctv_game_type.setVisibility(View.VISIBLE);
                holder.ctv_game_type.setText("Virtual Tournament");
            }
        } else {
            holder.ctv_game_type.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
//        if (type.equals("COMPLETED")) {
//            return responseBeen.size()>15?15:responseBeen.size();
//        }else {
        return responseBeen.size();
//        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.hi_main_card)
        @Nullable

        CardView mCardViewMainCard;

        @BindView(R.id.iv_team_1)
        @Nullable
        CustomImageView mCustomImageViewTeam1;

        @BindView(R.id.iv_team_2)
        @Nullable
        CustomImageView mCustomImageViewTeam2;


        @BindView(R.id.ctv_name_1)
        CustomTextView mCustomTextViewTeam1;

        @BindView(R.id.ctv_name_2)
        CustomTextView mCustomTextViewTeam2;

        @BindView(R.id.ctv_timer)
        CustomTextView ctv_timer;

        @BindView(R.id.ctv_VS)
        CustomTextView ctv_VS;

        @BindView(R.id.contest_joined)
        CustomTextView contest_joined;

        @BindView(R.id.ctv_series_name)
        CustomTextView ctv_series_name;

        @BindView(R.id.ctv_match_type)
        CustomTextView ctv_match_type;

        @BindView(R.id.ctv_game_type)
        CustomTextView ctv_game_type;


        @BindView(R.id.linout)
        LinearLayout linout;

        @BindView(R.id.bluredRel)
        RelativeLayout bluredRel;


        @BindView(R.id.view_right_wing)
        View view_right_wing;

        @BindView(R.id.view_left_wing)
        View view_left_wing;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);



        }


        CountDownTimer countDownTimer;

        public void setTime() {

            try {
                if (countDownTimer != null) countDownTimer.cancel();
                if (ctv_timer != null) {

                    // ctv_timer.setPaintFlags(ctv_timer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    if (responseBeen.get(getAdapterPosition()).getStatusID().equals("1") &&
                            TimeUtils.isThisDateValid(responseBeen.get(getAdapterPosition()).getMatchStartDateTime(),
                                    "yyyy-MM-dd HH:mm:ss")) {
                        if (TimeUnit.MILLISECONDS.toHours(getRemainingTime(getAdapterPosition())) >
                                Constant.SHOW_TIME_LIMIT_HRS) {
                            ctv_timer.setText(TimeUtils.getRemainingTime(getRemainingTime(getAdapterPosition())));
                        } else {
                            //need to implement counter
                            countDownTimer = new CountDownTimer(getRemainingTime(getAdapterPosition()),
                                    TimeUnit.SECONDS.toMillis(1)) {
                                public void onTick(long millisUntilFinished) {
                                    if (ctv_timer != null)
                                        ctv_timer.setText(TimeUtils.getRemainsTime(millisUntilFinished));
                                }

                                public void onFinish() {
                                    if (ctv_timer != null) {
                                        final int adapterPosition = getAdapterPosition();
                                        if (adapterPosition != -1) {
                                            if (responseBeen.get(adapterPosition).getMatchDate() != null) {
                                                ctv_timer.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ctv_timer.setText(AppUtils.getStrFromRes(R.string.Zero_s));

                                                    }
                                                });

                                            }
                                        }
                                    }

                                }
                            };
                            if (countDownTimer != null) {
                                countDownTimer.start();
                            }
                        }
                    } else {
                        ctv_timer.setText(TimeUtils.getMatchDateOnly(responseBeen.get(getAdapterPosition()).getMatchDate()));
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                ctv_timer.setText("N/A");
            }

        }

        /*public void setTimeStatic() {

            try {
                if (countDownTimer != null) countDownTimer.cancel();
                if (ctv_timer != null) {

                    // ctv_timer.setPaintFlags(ctv_timer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    if (
                            TimeUtils.isThisDateValid("2019-01-01 09:15:00 09:15 AM",
                                    "yyyy-MM-dd HH:mm:ss")) {
                        if (TimeUnit.MILLISECONDS.toHours(getRemainingTime(getAdapterPosition())) >
                                Constant.SHOW_TIME_LIMIT_HRS) {
                            ctv_timer.setText(TimeUtils.getMatchDateOnly("2019-01-01"));
                        } else {
                            //need to implement counter
                            countDownTimer = new CountDownTimer(getRemainingTime(getAdapterPosition()),
                                    TimeUnit.SECONDS.toMillis(1)) {
                                public void onTick(long millisUntilFinished) {
                                    if (ctv_timer != null)
                                        ctv_timer.setText(TimeUtils.getRemainsTime(millisUntilFinished));
                                }

                                public void onFinish() {
                                    if (ctv_timer != null)
                                        if ("2019-01-01" != null) {
                                            ctv_timer.setText(TimeUtils.getDisplayFullDate1(
                                                    "2019-01-01",
                                                    "09:15:00"));
                                        }
                                }
                            };
                            if (countDownTimer != null) {
                                countDownTimer.start();
                            }
                        }
                    } else {
                        ctv_timer.setText(TimeUtils.getMatchDateOnly("2019-01-01"));
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                ctv_timer.setText("N/A");
            }

        }*/
    }

    public void clear() {
        if (responseBeen == null) return;
        responseBeen.clear();
        notifyDataSetChanged();
    }

    public void addAllItem(List<MatchResponseOut.DataBean.RecordsBean> beanList) {
        if (beanList == null || responseBeen == null) return;
        for (int i = 0; i < beanList.size(); i++) {
            addItem(beanList.get(i));
        }
    }

    public void addItem(MatchResponseOut.DataBean.RecordsBean bean) {
        if (bean == null || responseBeen == null) return;
        responseBeen.add(bean);
        notifyItemInserted(responseBeen.size() - 1);
    }

    public long getRemainingTime(int position) {
        return TimeUtils.getTimeDifference(responseBeen.get(position).getMatchStartDateTime(),
                responseBeen.get(position).getCurrentDateTime());
    }
   /* public long getRemainingTime(int position) {
        return TimeUtils.getTimeDifference("2019-01-01 09:15:00 09:15 AM",
                "2018-12-29 20:43:13");
    }*/

    /*public String getMatchesId(int position) {
        return responseBeen.get(position).getMatchId();
    }

    public String getMatcheDate(int position) {
        return responseBeen.get(position).getMatchDate();
    }
    public String getMatcheTime(int position) {
        return responseBeen.get(position).getMatchTime();
    }
    public String getMatcheDateTime(int position) {
        return responseBeen.get(position).getMatchDateTime();
    }*/

    public MatchResponseOut.DataBean.RecordsBean getMatchItem(int position) {
        return responseBeen.get(position);
    }


    public void clearData() {
        responseBeen.clear();
        notifyDataSetChanged();
    }


}
