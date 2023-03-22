package com.mw.fantasy.UI.allContest;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mw.fantasy.R;
import com.mw.fantasy.UI.inviteContest.InviteContestActivity;
import com.mw.fantasy.beanOutput.AllContestOutPut;
import com.mw.fantasy.beanOutput.MatchContestOutPut;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.OnItemClickListener;
import com.mw.fantasy.utility.OnWinnerClickListener;
import com.mw.fantasy.utility.TimeUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import io.reactivex.annotations.Nullable;

public class AllContestAdapter extends RecyclerView.Adapter<AllContestAdapter.MyViewHolder> {

    List<AllContestOutPut.DataBean.RecordsBean> responseBeen = new ArrayList<>();
    int layoutId = 0;
    OnItemClickListener.OnItemClickCallback onWinnerClickCallBack, onItemClickCallback, onJoinClickCallBack;
    private Context mContext;
    String matchTeamVS = "";

    public AllContestAdapter(int layoutId, Context mContext, List<AllContestOutPut.DataBean.RecordsBean> responseBeen,
                             OnItemClickListener.OnItemClickCallback onWinnerClickCallBack,
                             OnItemClickListener.OnItemClickCallback onItemClickCallback,
                             OnItemClickListener.OnItemClickCallback onJoinClickCallBack,
                             String matchTeamVS) {
        this.responseBeen = responseBeen;
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.onJoinClickCallBack = onJoinClickCallBack;
        this.onWinnerClickCallBack = onWinnerClickCallBack;
        this.onItemClickCallback = onItemClickCallback;
        this.matchTeamVS = matchTeamVS;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_contest_item, parent, false));

    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final AllContestOutPut.DataBean.RecordsBean recordsBean = responseBeen.get(position);
        MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer1 = recordsBean.getOffer1();
        MatchContestOutPut.DataBean.ResultsBean.RecordsBean.OfferBean offer2 = recordsBean.getOffer2();

        final boolean isInfinitePool = responseBeen.get(position).getContestSize().equals("Unlimited");
        final boolean isSmartPool = responseBeen.get(position).getSmartPool().equals("Yes");
        final int totalJoined = Integer.parseInt(recordsBean.getTotalJoined().trim());

        // Invite Button
        holder.ctv_invite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InviteContestActivity.start(mContext, recordsBean.getUserInvitationCode(), matchTeamVS);
            }
        });

        //Contest Name
        if (holder.contest_name != null) holder.contest_name.setText(recordsBean.getContestName());

        //1 st Prize
        if (isInfinitePool) {
            if (totalJoined == 0) {
                holder.customTextViewFirstPrize.setText("₹" + AppUtils.converDoubleInFormate(Double.parseDouble(recordsBean.getWinUpTo().trim())) + " X Winnings");
            } else {
                if (recordsBean.getCustomizeWinning().size() == 0) {
                    holder.customTextViewFirstPrize.setText("₹" + AppUtils.converDoubleInFormate(Double.parseDouble(recordsBean.getWinUpTo().trim())) + " X Winnings");
                } else {
                    holder.customTextViewFirstPrize.setText("₹" + AppUtils.converDoubleInFormate(Double.parseDouble(recordsBean.getCustomizeWinning().get(0).getWinningAmount())));
                }
            }
        } else {
            if (recordsBean.getCustomizeWinning().size() == 0) {
                holder.customTextViewFirstPrize.setText(R.string.bf_Glory_awaits);
            } else {
                if (isSmartPool) {
                    holder.customTextViewFirstPrize.setText(recordsBean.getCustomizeWinning().get(0).getProductName());
                } else {
                    holder.customTextViewFirstPrize.setText("₹" + AppUtils.converDoubleInFormate(Double.parseDouble(recordsBean.getCustomizeWinning().get(0).getWinningAmount().trim())));
                }
            }
        }

        //Multi-Entry Indicator
        if (responseBeen.get(position).getEntryType().equals("Multiple")) {
            if (responseBeen.get(position).getUserJoinLimit()==1) {
                holder.multi.setText("Single");
            }else {
                holder.multi.setText("Upto "+responseBeen.get(position).getUserJoinLimit());
            }
        } else {
            holder.multi.setText("Single");
        }
        holder.multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolTip(v,
                        recordsBean.getEntryType().equals("Multiple") ?
                                "You can join this contest with 11 teams" :
                                "You can join this contest with 1 team");
            }
        });

        // Confirm Indicator
        holder.confirm.setVisibility(View.GONE);
        if (recordsBean.getIsConfirm() != null && recordsBean.getIsConfirm().equals("Yes")) {
            holder.confirm.setVisibility(View.VISIBLE);
        }
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolTip(v, "This league is confirmed. It will go on irrespective of number of entries.");
            }
        });

        // Bonus Info
        holder.bonus_info.setVisibility(View.GONE);
        if (!recordsBean.getCashBonusContribution().trim().equals("")) {
            Float percent = Float.parseFloat(responseBeen.get(position).getCashBonusContribution().trim());
            if (percent != 0) {
                holder.bonus_info.setText(percent + "% Bonus");
                holder.bonus_info.setVisibility(View.VISIBLE);
            }
        }
        holder.bonus_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToolTip(view, "This league is bonus contribution contest. It will take some partial amount from your cash bonus.");
            }
        });


        if (isSmartPool) {
            holder.total_winnings_amount.setText(recordsBean.getSmartPoolText());
        } else {
            if (isInfinitePool) {
                holder.total_winnings_amount.setText(recordsBean.getSmartPoolText());
            } else {
                if (Double.parseDouble(recordsBean.getWinningAmount().trim()) > 0) {
                    holder.total_winnings_amount.setText("₹" + AppUtils.converDoubleInFormate(Double.parseDouble(recordsBean.getWinningAmount().trim())));
                } else {
                    holder.total_winnings_amount.setText(AppUtils.getStrFromRes(R.string.practice_contest));
                }
            }
        }

        holder.winners_count.setOnClickListener(new OnItemClickListener(position, onWinnerClickCallBack));
        if (isInfinitePool) {
            holder.winners_count.setText(recordsBean.getWinningRatio() + "%");
            if (totalJoined >= 2) {
                holder.winners_count.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                holder.winners_count.setEnabled(true);
            } else {
                holder.winners_count.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                holder.winners_count.setEnabled(false);
            }
        } else {
            holder.winners_count.setText(recordsBean.getNoOfWinners() + "");
            if (Integer.parseInt(recordsBean.getNoOfWinners().trim()) >= 2) {
                holder.winners_count.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                holder.winners_count.setEnabled(true);
            } else {
                holder.winners_count.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                holder.winners_count.setEnabled(false);
            }
        }


        holder.customTextViewDiscountLabel.setVisibility(View.INVISIBLE);
        holder.customTextViewDiscountInfo1.setVisibility(View.INVISIBLE);
        holder.customTextViewDiscountInfo2.setVisibility(View.GONE);
        holder.customTextViewOldPrice.setVisibility(View.GONE);
        if (responseBeen.get(position).getEntryFee().equalsIgnoreCase("0")) {
            holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.free));
        } else {
            holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(position).getEntryFee());
            if (recordsBean.getOffer2() != null && recordsBean.getOffer2().getID() != null) {
                holder.customTextViewDiscountInfo2.setVisibility(View.VISIBLE);
                holder.customTextViewDiscountInfo2.setText("Discount | Get " + recordsBean.getOffer2().getOfferPercent() + "% off from " + getOfferLimitExtension(recordsBean.getOffer2().getNoOfTeams()) + " join");
            }
            if (recordsBean.getOffer1() != null && recordsBean.getOffer1().getID() != null && holder.getRemainingTime(position) > 0 &&
                    recordsBean.getUserTeamDetails().size() < offer1.getNoOfTeams()) {
                holder.customTextViewDiscountLabel.setVisibility(View.VISIBLE);
                holder.customTextViewDiscountInfo1.setVisibility(View.VISIBLE);
                holder.customTextViewOldPrice.setVisibility(View.VISIBLE);
                holder.customTextViewOldPrice.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(position).getEntryFee());
                if (offer1.getOfferPrize() == 0) {
                    holder.customTextViewDiscountLabel.setText("Free Entry");
                    holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.free));
                } else {
                    holder.customTextViewDiscountLabel.setText("Discount");
                    holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + offer1.getOfferPrize());
                }
                holder.setTime();
            } else if (offer2 != null && offer2.getID() != null) {
                if (recordsBean.getUserTeamDetails().size() + 1 >= offer2.getNoOfTeams()) {
                    holder.customTextViewDiscountLabel.setVisibility(View.VISIBLE);
                    holder.customTextViewOldPrice.setVisibility(View.VISIBLE);
                    holder.customTextViewOldPrice.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(position).getEntryFee());
                    if (offer2.getOfferPrize() == 0) {
                        holder.customTextViewDiscountLabel.setText("Free Entry");
                        holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.free));
                    } else {
                        holder.customTextViewDiscountLabel.setText("Discount");
                        holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + offer2.getOfferPrize());
                    }
                }
            }


        }

       /* if (!responseBeen.get(position).getEntryFee().equalsIgnoreCase("0")) {
            holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(position).getEntryFee());
            if (recordsBean.getOffer1() != null && recordsBean.getOffer1().getID() != null && holder.getRemainingTime(position) > 0 && recordsBean.getU) {

            }

        }else {
            holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.free));

        }*/


        if (isInfinitePool) {
            holder.spotLeftCount.setText("Total joined : " + totalJoined);
            holder.teamsCount.setText(AppUtils.getStrFromRes(R.string.infinity));
            holder.seekBar.setMax(100 + (totalJoined - 1));
            holder.seekBar.setProgress(totalJoined);
        } else {
            holder.spotLeftCount.setText(AppUtils.getStrFromRes(R.string.only) + " " +
                    (Integer.valueOf(responseBeen.get(position).getContestSize()) -
                            Integer.valueOf(responseBeen.get(position).getTotalJoined())) + " " + AppUtils.getStrFromRes(R.string.spotLeft));
            holder.teamsCount.setText(responseBeen.get(position).getContestSize() + " " + AppUtils.getStrFromRes(R.string.teams));

            holder.seekBar.setMax((Integer.valueOf(responseBeen.get(position).getContestSize())));
            holder.seekBar.setProgress(Integer.valueOf(responseBeen.get(position).getTotalJoined()));
        }

        holder.joinButton.setVisibility(View.GONE);
        if (responseBeen.get(position).getIsJoined().equals("Yes")) {
            if (responseBeen.get(position).getEntryType().equals("Multiple") && recordsBean.getUserTeamDetails().size() < 11) {
                holder.joinButton.setText(AppUtils.getStrFromRes(R.string.joinplus));
                holder.joinButton.setVisibility(View.VISIBLE);

            }
        } else {
            holder.joinButton.setText(AppUtils.getStrFromRes(R.string.joinCaps));
            holder.joinButton.setVisibility(View.VISIBLE);
        }
        if (!isInfinitePool) {
            int spotLeft = Integer.valueOf(recordsBean.getContestSize()) - totalJoined;
            if (spotLeft <= 0) {
                holder.joinButton.setVisibility(View.GONE);
            }
        }
        holder.hi_main_card.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
        holder.joinButton.setOnClickListener(new OnItemClickListener(position, onJoinClickCallBack));


        if (true)
            return;


        if (holder.contest_name != null) {
            holder.contest_name.setText(responseBeen.get(position).getContestName());
        }

        holder.bonus_info.setVisibility(View.GONE);
        if (!responseBeen.get(position).getCashBonusContribution().trim().equals("")) {
            Float percent = Float.parseFloat(responseBeen.get(position).getCashBonusContribution().trim());
            if (percent != 0) {
//                holder.bonus_info.setText("Use "+percent+"% Cash Bonus");
                holder.bonus_info.setText(percent + "% Bonus");
                holder.bonus_info.setVisibility(View.VISIBLE);
            }
        }

        if (responseBeen.get(position).getContestType().equalsIgnoreCase("Infinity Pool")) {
            if (responseBeen.get(position).getWinningAmount().equalsIgnoreCase("0")) {
                BigDecimal decimal = new BigDecimal(responseBeen.get(position).getWinUpTo()).stripTrailingZeros();

                holder.total_winnings_amount.setText(decimal.toPlainString() + "x Winning");
            } else {
                if (responseBeen.get(position).getWinningType() != null && responseBeen.get(position).getWinningType().equalsIgnoreCase("Free Join Contest")) {
                    holder.total_winnings_amount.setText("Bonus " + responseBeen.get(position).getWinningAmount());
                } else {
                    holder.total_winnings_amount.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(position).getWinningAmount());

                }

            }
        } else if (responseBeen.get(position).getSmartPool().equals("Yes")) {
            holder.total_winnings_amount.setText(responseBeen.get(position).getSmartPoolText());
        } else if (responseBeen.get(position).getUnfilledWinningPercent().equals("GuranteedPool") || responseBeen.get(position).getSmartPool().equals("Yes")) {
            if (responseBeen.get(position).getWinningType() != null && responseBeen.get(position).getWinningType().equalsIgnoreCase("Free Join Contest")) {
                holder.total_winnings_amount.setText("Bonus " + responseBeen.get(position).getWinningAmount());
            } else {
                holder.total_winnings_amount.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(position).getWinningAmount());

            }

        } else {
            if (!responseBeen.get(position).getWinningAmount().equals("0")) {
                if (responseBeen.get(position).getWinningType() != null && responseBeen.get(position).getWinningType().equalsIgnoreCase("Free Join Contest")) {
                    holder.total_winnings_amount.setText("Bonus " + responseBeen.get(position).getWinningAmount());
                } else {
                    holder.total_winnings_amount.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(position).getWinningAmount());

                }
            } else {
                holder.total_winnings_amount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                holder.total_winnings_amount.setText(AppUtils.getStrFromRes(R.string.practice_contest));
                holder.total_winnings.setText(AppUtils.getStrFromRes(R.string.practice_contest_des));
            }
        }

//        if (!responseBeen.get(position).getWinningAmount().equals("0")) {
//            holder.total_winnings_amount.setText(AppUtils.getStrFromRes(R.string.price_unit)+""+responseBeen.get(position).getWinningAmount());
//        } else {
//            holder.total_winnings_amount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            holder.total_winnings_amount.setText(AppUtils.getStrFromRes(R.string.practice_contest));
//
//        }


        if (responseBeen.get(position).getEntryFee().equalsIgnoreCase("0")) {
            holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.free));
        } else {
            holder.entry_fee_amount.setText(AppUtils.getStrFromRes(R.string.price_unit) + "" + responseBeen.get(position).getEntryFee());
        }
        holder.winners_count.setText(responseBeen.get(position).getNoOfWinners());

//        holder.spotLeftCount.setText(AppUtils.getStrFromRes(R.string.only) + " " +
//                (Integer.valueOf(responseBeen.get(position).getContestSize()) -
//                        Integer.valueOf(responseBeen.get(position).getTotalJoined())) + " " + AppUtils.getStrFromRes(R.string.spotLeft));


//        holder.teamsCount.setText(responseBeen.get(position).getContestSize() + " " + AppUtils.getStrFromRes(R.string.teams));


        holder.winners_count.setOnClickListener(new OnItemClickListener(position, onWinnerClickCallBack));

        holder.hi_main_card.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));


        holder.joinButton.setOnClickListener(new OnItemClickListener(position, onJoinClickCallBack));

//        holder.seekBar.setMax(Integer.valueOf(responseBeen.get(position).getContestSize()));
        //holder.seekBar.setProgress(Integer.valueOf(responseBeen.get(position).getContestSize())-responseBeen.get(position).getSpots_left());

//        holder.seekBar.setProgress(Integer.valueOf(responseBeen.get(position).getTotalJoined()));

        holder.seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        if (!responseBeen.get(position).getNoOfWinners().equals("1")) {

            if (!responseBeen.get(position).getNoOfWinners().equals("0")) {
                holder.winners_count.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                holder.winners_count.setCompoundDrawablePadding(10);

                holder.winners_count.setOnClickListener(new OnItemClickListener(position, onWinnerClickCallBack));
            }
        }


        if (responseBeen.get(position).getEntryType().equals("Multiple")) {
            holder.multi.setText("Upto 11 Entry");
            //holder.multi.setVisibility(View.VISIBLE);
        } else {
            holder.multi.setText("Single Entry");
            // holder.multi.setVisibility(View.GONE);
        }

      /*  if (responseBeen.get(position).getEntryType().equals("Multiple")) {
            holder.multi.setVisibility(View.VISIBLE);
        } else {
            holder.multi.setVisibility(View.GONE);
        }*/

        if (responseBeen.get(position).getIsConfirm() != null && responseBeen.get(position).getIsConfirm().equals("Yes")) {
            holder.confirm.setVisibility(View.VISIBLE);
        } else {
            holder.confirm.setVisibility(View.GONE);
        }
        if (responseBeen.get(position).getContestSize().equals("Unlimited")) {
            holder.spotLeftCount.setVisibility(View.VISIBLE);
            holder.spotLeftCount.setText("Total joined : " + responseBeen.get(position).getTotalJoined());
            holder.teamsCount.setText(AppUtils.getStrFromRes(R.string.infinity));
            holder.seekBar.setProgress(Integer.valueOf(responseBeen.get(position).getTotalJoined()));

//            holder.seekBar.setVisibility(View.GONE);
            holder.winners_count.setText(responseBeen.get(position).getWinningRatio() + "%");

            if (Integer.parseInt(responseBeen.get(position).getTotalJoined()) >= 2) {
                holder.winners_count.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                holder.winners_count.setOnClickListener(new OnItemClickListener(position, onWinnerClickCallBack));

            } else {
                holder.winners_count.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        } else {
            holder.spotLeftCount.setVisibility(View.VISIBLE);
            holder.winners_count.setEnabled(true);
            holder.spotLeftCount.setText(AppUtils.getStrFromRes(R.string.only) + " " +
                    (Integer.valueOf(responseBeen.get(position).getContestSize()) -
                            Integer.valueOf(responseBeen.get(position).getTotalJoined())) + " " + AppUtils.getStrFromRes(R.string.spotLeft));
            holder.teamsCount.setText(responseBeen.get(position).getContestSize() + " " + AppUtils.getStrFromRes(R.string.teams));
            holder.seekBar.setVisibility(View.VISIBLE);
            holder.seekBar.setMax(Integer.valueOf(responseBeen.get(position).getContestSize()));
            holder.seekBar.setProgress(Integer.valueOf(responseBeen.get(position).getTotalJoined()));
        }

        if (responseBeen.get(position).getIsJoined().equals("Yes")) {
            holder.joinButton.setText(AppUtils.getStrFromRes(R.string.invite));
            /*if (responseBeen.get(position).getContestSize().equals("Unlimited")) {
                if (responseBeen.get(position).getEntryType().equals("Multiple")) {
                    holder.joinButton.setText(AppUtils.getStrFromRes(R.string.joinplus));
                } else if (responseBeen.get(position).getEntryType().equals("Single")) {
                    holder.joinButton.setText(AppUtils.getStrFromRes(R.string.invite));
                    holder.joinButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InviteContestActivity.start(mContext, responseBeen.get(position).getUserInvitationCode(), matchTeamVS);
                        }
                    });
                }
            } else {
                int spotLeft = Integer.valueOf(responseBeen.get(position).getContestSize()) - Integer.valueOf(responseBeen.get(position).getTotalJoined());

                if (spotLeft != 0 && responseBeen.get(position).getEntryType().equals("Multiple")) {
                    holder.joinButton.setText(AppUtils.getStrFromRes(R.string.joinplus));
                } else if (spotLeft == 0) {
                    holder.joinButton.setText(AppUtils.getStrFromRes(R.string.joined));
                    holder.joinButton.setEnabled(false);
                } else if (spotLeft != 0 && responseBeen.get(position).getEntryType().equals("Single")) {
                    holder.joinButton.setText(AppUtils.getStrFromRes(R.string.invite));
                    holder.joinButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InviteContestActivity.start(mContext, responseBeen.get(position).getUserInvitationCode(), matchTeamVS);
                        }
                    });
                }
            }*/
        } else {
            holder.joinButton.setText(AppUtils.getStrFromRes(R.string.joinCaps));
        }
        if (responseBeen.get(position).getContestSize().equalsIgnoreCase(responseBeen.get(position).getTotalJoined())) {
            holder.joinButton.setVisibility(View.GONE);
        } else {
            holder.joinButton.setVisibility(View.VISIBLE);
        }

        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showPopupright((Activity) mContext, v, "This league is confirmed. It will go on irrespective of number of entries.");

            }
        });

        holder.multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showPopupright((Activity) mContext, v, "This league is multiple. So a user can join with multiple teams.");

            }
        });

        holder.bonus_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showPopupright((Activity) mContext, v, "This league is bonus contribution contest. It will take some partial amount from your cash bonus.");

            }
        });


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

    public void addAllItem(List<AllContestOutPut.DataBean.RecordsBean> beanList) {
        if (beanList == null || responseBeen == null) return;
        for (int i = 0; i < beanList.size(); i++) {
            addItem(beanList.get(i));
        }
    }

    public void addItem(AllContestOutPut.DataBean.RecordsBean bean) {
        if (bean == null || responseBeen == null) return;
        responseBeen.add(bean);
        notifyItemInserted(responseBeen.size() - 1);
    }

    public String getContestGUID(int position) {


        return responseBeen.get(position).getContestGUID();
    }

    public AllContestOutPut.DataBean.RecordsBean getItem(int position) {


        return responseBeen.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ctv_invite_btn)
        CustomTextView ctv_invite_btn;

        @BindView(R.id.customTextViewFirstPrize)
        CustomTextView customTextViewFirstPrize;

        @BindView(R.id.multi)
        CustomTextView multi;

        @BindView(R.id.confirm)
        View confirm;

        @BindView(R.id.bonus_info)
        CustomTextView bonus_info;


        @BindView(R.id.total_winnings_amount)
        CustomTextView total_winnings_amount;

        @BindView(R.id.winners_count)
        CustomTextView winners_count;


        @BindView(R.id.total_winnings)
        CustomTextView total_winnings;


        @BindView(R.id.entry_fee_amount)
        CustomTextView entry_fee_amount;


        @BindView(R.id.hi_main_card)
        CardView hi_main_card;


        @BindView(R.id.spotLeftCount)
        CustomTextView spotLeftCount;

        @BindView(R.id.teamsCount)
        CustomTextView teamsCount;

        @BindView(R.id.joinButton)
        CustomTextView joinButton;


        @Nullable
        @BindView(R.id.contest_name)
        CustomTextView contest_name;

        @BindView(R.id.seekBar)
        ProgressBar seekBar;

        @Nullable
        @BindView(R.id.customTextViewOldPrice)
        CustomTextView customTextViewOldPrice;

        @Nullable
        @BindView(R.id.customTextViewDiscountLabel)
        CustomTextView customTextViewDiscountLabel;

        @Nullable
        @BindView(R.id.customTextViewDiscountInfo1)
        CustomTextView customTextViewDiscountInfo1;

        @Nullable
        @BindView(R.id.customTextViewDiscountInfo2)
        CustomTextView customTextViewDiscountInfo2;









     /*   @BindView(R.id.total_winnings)
        CustomTextView total_winnings;

        @BindView(R.id.total_winnings_amount)
        CustomTextView total_winnings_amount;

        @BindView(R.id.winners_count)
        CustomTextView winners_count;

        @BindView(R.id.entry_fee_amount)
        CustomTextView entry_fee_amount;

        @BindView(R.id.hi_main_card)
        CardView hi_main_card;


        @BindView(R.id.multi)
        CustomTextView multi;

        @BindView(R.id.confirm)
        View confirm;

        @BindView(R.id.bonus_info)
        CustomTextView bonus_info;


        @BindView(R.id.spotLeftCount)
        CustomTextView spotLeftCount;

        @BindView(R.id.teamsCount)
        CustomTextView teamsCount;

        @BindView(R.id.joinButton)
        CustomTextView joinButton;

        @BindView(R.id.seekBar)
        ProgressBar seekBar;

        @Nullable
        @BindView(R.id.contest_name)
        CustomTextView contest_name;*/


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        CountDownTimer countDownTimer;

        public void setTime() {
            try {
                if (customTextViewDiscountInfo1 != null) {
                    if (TimeUtils.isThisDateValid(responseBeen.get(getAdapterPosition()).getOffer1().getOfferDateTime(),
                            "yyyy-MM-dd HH:mm:ss")) {
                        countDownTimer = new CountDownTimer(getRemainingTime(getAdapterPosition()), TimeUnit.SECONDS.toMillis(1)) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (customTextViewDiscountInfo1 != null) {
                                    try {
                                        customTextViewDiscountInfo1.setText("* valid For first " + responseBeen.get(getAdapterPosition()).getOffer1().getNoOfTeams() + " entry | Expire in " + TimeUtils.getRemainsTimeOffer(millisUntilFinished));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        customTextViewDiscountInfo1.setText("ER");
                                    }
                                }

                            }

                            @Override
                            public void onFinish() {
                                notifyDataSetChanged();
                            }
                        };
                        if (countDownTimer != null) {
                            countDownTimer.start();
                        }
                    }
                }
            } catch (Exception e) {
                customTextViewDiscountInfo1.setText(e.getMessage());
            }


        }

        public long getRemainingTime(int position) {
            return TimeUtils.getTimeDifference(responseBeen.get(position).getOffer1().getOfferDateTime(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        }


    }


    private String getOfferLimitExtension(int post) {
        if (post == 1) {
            return "1 st";
        } else if (post == 2) {
            return "2 nd";
        } else if (post == 3) {
            return "3 rd";
        } else {
            return post + " th";
        }
    }


    private void showToolTip(View view, String message) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        new SimpleTooltip.Builder(mContext)
                .anchorView(view)
                .text(message)
                .gravity(height - location[1] > 100 ? Gravity.BOTTOM : Gravity.TOP)
                .animated(false)
                .transparentOverlay(true)
                .build()
                .show();
    }

}
