package com.websinception.megastar.UI.auction.auctionHome;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.playerpoint.AuctionPlayerStatsActivity;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuctionHomeOrderListAdapter extends RecyclerView.Adapter<AuctionHomeOrderListAdapter.OrderListViewHolder> {

    private  List<GetAuctionPlayerOutput.DataBean.RecordsBean> records;
    private Context mContext;
    private String roundId,seriesID,seriesGUID;





    public AuctionHomeOrderListAdapter(Context mContext, String roundId, String seriesID,
                                       String seriesGUID,  List<GetAuctionPlayerOutput.DataBean.RecordsBean> records) {
        this.mContext = mContext;
        this.roundId = roundId;
        this.seriesID = seriesID;
        this.seriesGUID = seriesGUID;
        this.records = records;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_auction_order_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        final GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = records.get(i);

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);


        ViewUtils.setImageUrl(orderListViewHolder.mCustomImageViewPic, recordsBean.getPlayerPic());
        orderListViewHolder.mCustomTextViewName.setText(recordsBean.getPlayerName());
        if (recordsBean.getPlayerStatus().equals("Live")) {
            orderListViewHolder.mCustomTextViewName.setBackgroundColor(Color.parseColor("#FBBF02"));
            orderListViewHolder.mCustomTextViewName.setTextColor(Color.parseColor("#000000"));
            orderListViewHolder.mCustomImageViewPic.setColorFilter(null);
            orderListViewHolder.mCustomImageViewPic.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            //orderListViewHolder.mViewRoot.setAlpha(0.3f);
            orderListViewHolder.mCustomImageViewPic.setColorFilter(new ColorMatrixColorFilter(matrix));
            orderListViewHolder.mCustomImageViewPic.setBackgroundColor(Color.parseColor("#FEFEFE"));
            orderListViewHolder.mCustomTextViewName.setBackgroundColor(Color.parseColor("#95969A"));
            orderListViewHolder.mCustomTextViewName.setTextColor(Color.parseColor("#BDBDBF"));
        }

        orderListViewHolder.mViewRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionPlayerStatsActivity.start(mContext,
                        seriesGUID,
                        recordsBean.getPlayerGUID(),
                        roundId,
                        seriesID,
                        false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ctv_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.ll_root)
        View mViewRoot;

        @BindView(R.id.civ_pic)
        CustomImageView mCustomImageViewPic;



        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
