package com.mw.fantasy.UI.draft.addPlayerInAssitant;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftAssistantPlayersListAdapter extends RecyclerView.Adapter<DraftAssistantPlayersListAdapter.OrderListViewHolder> {

    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> mRecordsBeanArrayList;
    private Context mContext;
    private String matchGUID;

    public DraftAssistantPlayersListAdapter(Context context,
                                            List<GetAuctionPlayerOutput.DataBean.RecordsBean> recordsBeanArrayList,
                                            String matchGUID) {
        this.mContext = context;
        this.mRecordsBeanArrayList = recordsBeanArrayList;
        this.matchGUID = matchGUID;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_dfraft_assistant_add_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListViewHolder orderListViewHolder, final int i) {
        orderListViewHolder.mCustomSpinner.setOnItemSelectedListener(null);
        final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = mRecordsBeanArrayList.get(i);
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getPlayerName());
        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getPlayerPic());
        String info = "";
        switch (recordsBean.getPlayerRole()) {
            case "WicketKeeper":
                info = "WK";
                break;
            case "Bowler":
                info = "BOWL";
                break;
            case "Batsman":
                info = "BAT";
                break;
            case "AllRounder":
                info = "AR";
                break;
        }
        info = info + " | " + recordsBean.getTeamNameShort();
        orderListViewHolder.mCustomTextViewInfo.setText(info);

        ArrayList<String> selection = new ArrayList<>();
        for (int j = 1; j < 101; j++) {
            boolean isAdded=false;
            List<GetAuctionPlayerOutput.DataBean.RecordsBean> records = ((AddPlayerInDraftAssistantActivity) mContext).getmGetAuctionPlayerOutput().getData().getRecords();
            HashMap<String, String> selectedPlayer = ((AddPlayerInDraftAssistantActivity) mContext).getSelectedPlayer();
            for (int i1 = 0; i1 < records.size(); i1++) {
                if (selectedPlayer.containsKey(records.get(i1).getPlayerGUID())) {
                    if (selectedPlayer.get(records.get(i1).getPlayerGUID()).equals(j+"")) {
                        if (!records.get(i1).getPlayerGUID().equals(recordsBean.getPlayerGUID())) {
                            isAdded=true;
                            break;
                        }
                    }
                }
            }
            if (!isAdded) {
                selection.add(j + "");
            }
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                mContext,
                R.layout.bid_spinner_item_assistant,
                selection);
        adapter.setDropDownViewResource(R.layout.bid_spinner_dropdown_item);
        orderListViewHolder.mCustomSpinner.setAdapter(adapter);


        String priority = getPriority(i);
        Log.d("prioritypriority", "priority: "+priority);
        if (priority.equals("")) {
            orderListViewHolder.mCustomSpinner.setSelection(0);
        } else {
            for (int i1 = 1; i1 < selection.size(); i1++) {
                if (selection.get(i1).equals(priority)) {
                    orderListViewHolder.mCustomSpinner.setSelection(i1);
                    break;
                }
            }
        }
        setCrossButton(orderListViewHolder.mImageViewAdd, i);
        orderListViewHolder.mImageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelected(i)) {
                    if (((AddPlayerInDraftAssistantActivity) mContext).getSelectedPlayer().size() > 100) {
                        AppUtils.showToast(mContext, "You can't add more than 100 players.");
                    }/* else if (orderListViewHolder.mCustomSpinner.getSelectedItemPosition() == 0) {
                        AppUtils.showToast(mContext, "Please select player priority.");
                    }*/ else {
                        String txt = orderListViewHolder.mCustomSpinner.getSelectedItem().toString();
                        HashMap<String, String> selectedPlayer = ((AddPlayerInDraftAssistantActivity) mContext).getSelectedPlayer();
                        List<GetAuctionPlayerOutput.DataBean.RecordsBean> records = ((AddPlayerInDraftAssistantActivity) mContext).getmGetAuctionPlayerOutput().getData().getRecords();
                        for (int i1 = 0; i1 < records.size(); i1++) {
                            if (selectedPlayer.containsKey(records.get(i1).getPlayerGUID())) {
                                if (selectedPlayer.get(records.get(i1).getPlayerGUID()).equals(txt)) {
                                    AppUtils.showToast(mContext, "Can't select same priority for different players. "+records.get(i1).getPlayerName()+" already selected for "+txt+" priority.");
                                    return;
                                }
                            }
                        }
                        ((AddPlayerInDraftAssistantActivity) mContext).addPlayer(recordsBean.getPlayerGUID(), txt);
                    }
                } else {
                    ((AddPlayerInDraftAssistantActivity) mContext).removeSelectedPlayer(recordsBean.getPlayerGUID());
                }
            }
        });

        orderListViewHolder.mCustomImageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionPlayerStatsActivity.start(mContext,
                        recordsBean.getSeriesGUID(),
                        recordsBean.getPlayerGUID(),
                        matchGUID,
                        recordsBean.getSeriesID(),false);
            }
        });


        if (isSelected(i)) {
            orderListViewHolder.mViewRoot.setBackgroundColor(Color.parseColor("#B3FFFFFF"));
        }else {
            orderListViewHolder.mViewRoot.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }



        orderListViewHolder.mCustomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSelected(i)) {
                    String txt = orderListViewHolder.mCustomSpinner.getSelectedItem().toString();
                    if (!getPriority(i).equals(txt)) {
                        HashMap<String, String> selectedPlayer = ((AddPlayerInDraftAssistantActivity) mContext).getSelectedPlayer();
                        List<GetAuctionPlayerOutput.DataBean.RecordsBean> records = ((AddPlayerInDraftAssistantActivity) mContext).getmGetAuctionPlayerOutput().getData().getRecords();
                        for (int i1 = 0; i1 < records.size(); i1++) {
                            if (selectedPlayer.containsKey(records.get(i1).getPlayerGUID())) {
                                if (selectedPlayer.get(records.get(i1).getPlayerGUID()).equals(txt)) {
                                    AppUtils.showToast(mContext, "Can't select same priority for different players. "+records.get(i1).getPlayerName()+" already selected for "+txt+" priority.");
                                    return;
                                }
                            }
                        }
                        orderListViewHolder.mCustomSpinner.setOnItemSelectedListener(null);
                        ((AddPlayerInDraftAssistantActivity) mContext).addPlayer(recordsBean.getPlayerGUID(), txt);
                    }
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
        if (((AddPlayerInDraftAssistantActivity) mContext).getSelectedPlayer().containsKey(mRecordsBeanArrayList.get(position).getPlayerGUID())) {
            return true;
        } else {
            return false;
        }
    }

    private String getPriority(int position) {
        if (((AddPlayerInDraftAssistantActivity) mContext).getSelectedPlayer().containsKey(mRecordsBeanArrayList.get(position).getPlayerGUID())) {
            return ((AddPlayerInDraftAssistantActivity) mContext).getSelectedPlayer().get(mRecordsBeanArrayList.get(position).getPlayerGUID());
        } else {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return mRecordsBeanArrayList.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_root)
        View mViewRoot;

        @BindView(R.id.iv_cross)
        ImageView mImageViewAdd;

        @BindView(R.id.cs_bid_value)
        Spinner mCustomSpinner;

        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.ctv_info)
        CustomTextView mCustomTextViewInfo;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
