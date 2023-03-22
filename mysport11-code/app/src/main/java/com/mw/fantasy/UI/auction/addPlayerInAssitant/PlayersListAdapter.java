package com.mw.fantasy.UI.auction.addPlayerInAssitant;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mw.fantasy.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.FIXTURE;

public class PlayersListAdapter extends RecyclerView.Adapter<PlayersListAdapter.OrderListViewHolder> {

    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> mRecordsBeanArrayList;
    private Context mContext;
    private String roundID;
    private int seriesStatus;
    ArrayList<String> bidArray = new ArrayList<>();

    public PlayersListAdapter(Context context, String roundID, List<GetAuctionPlayerOutput.DataBean.RecordsBean> recordsBeanArrayList, int seriesStatus) {
        this.mContext = context;
        this.roundID = roundID;
        this.seriesStatus = seriesStatus;
        this.mRecordsBeanArrayList = recordsBeanArrayList;

        bidArray.addAll(AppUtils.getBidArray());
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_auction_assistant_add_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListViewHolder orderListViewHolder, final int i) {
        orderListViewHolder.mCustomSpinner.setOnItemSelectedListener(null);
        final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = mRecordsBeanArrayList.get(i);
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getPlayerPic());
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getPlayerName());
        if (recordsBean.getPlayerRole() != null && !recordsBean.getPlayerRole().trim().equals("")) {
            switch (recordsBean.getPlayerRole()) {
                case "Batsman":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort() + " - BAT");
                    break;
                case "WicketKeeper":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort() + " - WK");
                    break;
                case "AllRounder":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort() + " - AR");
                    break;
                case "Bowler":
                    orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort() + " - BOWL");
                    break;
            }
        } else {
            orderListViewHolder.mCustomTextViewPlayerInfo.setText(recordsBean.getTeamNameShort());
        }

      /*  final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.bid_arrays_assistant, R.layout.bid_spinner_item_assistant);*/


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(mContext,
                        R.layout.bid_spinner_item_assistant, bidArray);


        adapter.setDropDownViewResource(R.layout.bid_spinner_dropdown_item);
        orderListViewHolder.mCustomSpinner.setAdapter(adapter);

        if (getBid(i).equals("")) {
            orderListViewHolder.mCustomSpinner.setSelection(0);
        } else {
            int bid = Integer.parseInt(getBid(i));
            String txt = "";
            if (bid != 0) {
                if (bid < 10000000) {
                    txt = bid / 100000 + " Lacs";
                } else {
                    txt = bid / 10000000 + " Crs";
                }
            }

          /*  AppUtils.getBidArray();
            String[] bid_arrays = mContext.getResources().getStringArray(R.array.bid_arrays_assistant);
            for (int j = 0; j < bid_arrays.length; j++) {
                if (bid_arrays[j].equals(txt)) {
                    orderListViewHolder.mCustomSpinner.setSelection(j);
                }
            }*/

            for (int i1 = 0; i1 < bidArray.size(); i1++) {
                if (bidArray.get(i1).equals(txt)) {
                    orderListViewHolder.mCustomSpinner.setSelection(i1);
                }
            }
        }


        setCrossButton(orderListViewHolder.mImageViewAdd, i);

        orderListViewHolder.mImageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelected(i)) {
                    if (((AddPlayerInAssistantActivity) mContext).getSelectedPlayer().size() > 20) {
                        Toast.makeText(mContext, "You can't add more than 20 players.", Toast.LENGTH_SHORT).show();
                    } /*else if (orderListViewHolder.mCustomSpinner.getSelectedItemPosition() == 0) {
                        Toast.makeText(mContext, "Please select player bid amount.", Toast.LENGTH_SHORT).show();
                    }*/ else {
                        String txt = orderListViewHolder.mCustomSpinner.getSelectedItem().toString();
                        if (txt.contains("Lacs")) {
                            txt = (txt.substring(0, txt.length() - 5)) + "00000";
                        } else {
                            txt = (txt.substring(0, txt.length() - 4)) + "0000000";
                        }
                        ((AddPlayerInAssistantActivity) mContext).addPlayer(recordsBean.getPlayerGUID(), txt);
                        setCrossButton(orderListViewHolder.mImageViewAdd, i);
                        orderListViewHolder.mViewRoot.setBackgroundColor(Color.parseColor("#B3FFFFFF"));

                    }
                } else {
                    ((AddPlayerInAssistantActivity) mContext).removeSelectedPlayer(recordsBean.getPlayerGUID());
                    setCrossButton(orderListViewHolder.mImageViewAdd, i);
                    orderListViewHolder.mViewRoot.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        });


       /* orderListViewHolder.mImageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelected(i)) {
                    if (((AddPlayerInAssistantActivity) mContext).getSelectedPlayer().size()>100) {
                        AppUtils.showToast(mContext,"You can't add more than 100 players.");
                    }else if (orderListViewHolder.mCustomSpinner.getSelectedItemPosition() == 0) {
                        AppUtils.showToast(mContext,"Please select player bid amount.");
                    } else {
                        String txt=orderListViewHolder.mCustomSpinner.getSelectedItem().toString();
                        if (txt.contains("Lacs")) {
                            txt=(txt.substring(0, txt.length()-5))+"00000";
                        }else {
                            txt=(txt.substring(0, txt.length()-4))+"0000000";
                        }
                        ((AddPlayerInAssistantActivity) mContext).addPlayer(recordsBean.getPlayerGUID(), txt);
                    }
                } else {
                    ((AddPlayerInAssistantActivity) mContext).removeSelectedPlayer(recordsBean.getPlayerGUID());
                }
            }
        });*/


        orderListViewHolder.mCustomImageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionPlayerStatsActivity.start(mContext,
                        recordsBean.getSeriesGUID(),
                        recordsBean.getPlayerGUID(),
                        roundID,
                        recordsBean.getSeriesID(),
                        seriesStatus==FIXTURE?false:true);
            }
        });


        if (isSelected(i)) {
            orderListViewHolder.mViewRoot.setBackgroundColor(Color.parseColor("#B3FFFFFF"));
        } else {
            orderListViewHolder.mViewRoot.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }


        orderListViewHolder.mCustomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSelected(i)) {

                    String txt = orderListViewHolder.mCustomSpinner.getSelectedItem().toString();
                    if (txt.contains("Lacs")) {
                        txt = (txt.substring(0, txt.length() - 5)) + "00000";
                    } else {
                        txt = (txt.substring(0, txt.length() - 4)) + "0000000";
                    }
                    ((AddPlayerInAssistantActivity) mContext).addPlayer(recordsBean.getPlayerGUID(), txt);

                    /*if (position == 0) {
                        ((AddPlayerInAssistantActivity) mContext).removeSelectedPlayer(recordsBean.getPlayerGUID());
                        setCrossButton(orderListViewHolder.mImageViewAdd, i);
                        orderListViewHolder.mViewRoot.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    } else {
                        String txt = orderListViewHolder.mCustomSpinner.getSelectedItem().toString();
                        if (txt.contains("Lacs")) {
                            txt = (txt.substring(0, txt.length() - 5)) + "00000";
                        } else {
                            txt = (txt.substring(0, txt.length() - 4)) + "0000000";
                        }
                        ((AddPlayerInAssistantActivity) mContext).addPlayer(recordsBean.getPlayerGUID(), txt);
                    }*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void setCrossButton(ImageView ivCross, int position) {
        ivCross.setImageResource(isSelected(position) ? R.drawable.ic_cancel : R.drawable.ic_add_button);
        ivCross.setColorFilter(Color.parseColor(!isSelected(position) ? "#26BA38" : "#ed0a0a"), PorterDuff.Mode.SRC_IN);
    }

    private boolean isSelected(int position) {
        if (((AddPlayerInAssistantActivity) mContext).getSelectedPlayer().containsKey(mRecordsBeanArrayList.get(position).getPlayerGUID())) {
            return true;
        } else {
            return false;
        }
    }

    private String getBid(int position) {
        if (((AddPlayerInAssistantActivity) mContext).getSelectedPlayer().containsKey(mRecordsBeanArrayList.get(position).getPlayerGUID())) {
            return ((AddPlayerInAssistantActivity) mContext).getSelectedPlayer().get(mRecordsBeanArrayList.get(position).getPlayerGUID());
        } else {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return mRecordsBeanArrayList.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cross)
        ImageView mImageViewAdd;

        @BindView(R.id.cs_bid_value)
        Spinner mCustomSpinner;

        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.ctv_info)
        CustomTextView mCustomTextViewPlayerInfo;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        @BindView(R.id.ll_root)
        View mViewRoot;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
