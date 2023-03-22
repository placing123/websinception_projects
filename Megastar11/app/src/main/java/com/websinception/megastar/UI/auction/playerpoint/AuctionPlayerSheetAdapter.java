package com.websinception.megastar.UI.auction.playerpoint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.websinception.megastar.R;
import com.websinception.megastar.beanOutput.GetDraftPlayerPointOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AuctionPlayerSheetAdapter extends RecyclerView.Adapter<AuctionPlayerSheetViewHolder> {

    Context mContext;
    List<GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean> responseBeanList;
   // OnItemClickListener.OnItemClickCallback onItemClickCallback;

    Map<String, Object> map = new HashMap<String, Object>() {{
        put("StatringXI", "Starting Bonus");
        put("StrikeRate100N149.99", "Strike Rate");
        put("StrikeRate0N49.99", "Strike Rate");
        put("StrikeRate75N99.99", "Strike Rate");
        put("For30runs", "For 30 runs");
        put("EconomyRate0N5Balls", "Economy Rate");
        put("ThreeWickets", "Three Wickets");
        put("RunOUT", "Run OUT");
        put("RunOutCatcherThrower", "Run Out Catcher Thrower");
        put("EveryRunScored", "Every Run Scored");
        put("StrikeRate200NMore", "Strike Rate");
        put("StrikeRate150N199.99", "Strike Rate");
        put("StrikeRate50N74.99", "Strike Rate");
        put("MinimumBallsScoreStrikeRate", "Strike Rate");
        put("For100runs", "For 100 runs");
        put("EconomyRate5.01N7.00Balls", "Economy Rate");
        put("EconomyRate5.01N8.00Balls", "Economy Rate");
        put("EconomyRateAbove12.1Balls", "Economy Rate");
        put("EconomyRate10.01N12.00Balls", "Economy Rate");
        put("EconomyRate8.01N10.00Balls", "Economy Rate");
        put("MinimumOverEconomyRate", "Economy Rate");

    }};

    public AuctionPlayerSheetAdapter(Context mContext, List<GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean> responseBeanList) {
        this.mContext = mContext;
        this.responseBeanList = responseBeanList;
       // this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public AuctionPlayerSheetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.auction_point_breakup_item, parent, false);

        return new AuctionPlayerSheetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AuctionPlayerSheetViewHolder holder, int position) {


        holder.actual_value.setText(responseBeanList.get(position).getDefinedPoints());

        holder.points_value.setText(""+responseBeanList.get(position).getCalculatedPoints());
        if (map.containsKey(responseBeanList.get(position).getPointsTypeGUID())){

            holder.event_value.setText(map.get(responseBeanList.get(position).getPointsTypeGUID()).toString());
        }else {
            holder.event_value.setText(responseBeanList.get(position).getPointsTypeGUID());
        }




    }

    public void addAllItem(List<GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean> beanList) {
        if (beanList == null || responseBeanList == null) return;
        for (int i = 0; i < beanList.size(); i++) {
            addItem(beanList.get(i));
        }
    }
    public void addItem(GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean bean) {
        if (bean == null || responseBeanList == null) return;
        responseBeanList.add(bean);
        notifyItemInserted(responseBeanList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return responseBeanList.size()-1;
    }


   /* void shotByName(boolean bool){

        if(bool){
            Collections.sort(responseBeanList, new SortByNameDES());
        }else {
            Collections.sort(responseBeanList, new SortByNameASC());
        }


        notifyDataSetChanged();
    }
    void shotByPoint(boolean bool){

        if(bool){
            Collections.sort(responseBeanList, new PointSorterDES());
        }else {
            Collections.sort(responseBeanList, new PointSorterASC());
        }


        notifyDataSetChanged();
    }*/
}