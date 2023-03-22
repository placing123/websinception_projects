package com.mw.fantasy.UI.subscription;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mw.fantasy.R;
import com.mw.fantasy.beanOutput.GetSubscriptionResponse;
import com.mw.fantasy.customView.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscriptionsListAdapter extends RecyclerView.Adapter<SubscriptionsListAdapter.SubsriptionViewHolder> {

    private ArrayList<GetSubscriptionResponse.RecordBean> subsriptionsList;
    private SubscriptionListActivity subscriptionListActivity;
    private int selectedIndex = 0;

    public SubscriptionsListAdapter(ArrayList<GetSubscriptionResponse.RecordBean> subsriptionsList, SubscriptionListActivity subscriptionListActivity) {
        this.subsriptionsList = subsriptionsList;
        this.subscriptionListActivity = subscriptionListActivity;
    }

    @NonNull
    @Override
    public SubsriptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SubsriptionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_subsription, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SubsriptionViewHolder subsriptionViewHolder, final int i) {
        GetSubscriptionResponse.RecordBean recordBean = subsriptionsList.get(i);
        subsriptionViewHolder.ctv_days.setText(recordBean.getDays());
        if (Integer.parseInt(recordBean.getDays().trim()) > 1) {
            subsriptionViewHolder.ctv_day_label.setText("days");
        } else {
            subsriptionViewHolder.ctv_day_label.setText("day");
        }
        try {
            double price = Double.parseDouble(recordBean.getPrice().trim());
            double discountedPrice = Double.parseDouble(recordBean.getDiscountPrice().trim());
            if (price == discountedPrice) {
                subsriptionViewHolder.ctv_discounted_amount.setText("₹ " + recordBean.getDiscountPrice());
                subsriptionViewHolder.ctv_actual_price.setVisibility(View.INVISIBLE);
            } else {
                subsriptionViewHolder.ctv_discounted_amount.setText("₹ " + recordBean.getDiscountPrice());
                subsriptionViewHolder.ctv_actual_price.setText("₹ " + recordBean.getPrice());
                subsriptionViewHolder.ctv_actual_price.setPaintFlags(subsriptionViewHolder.ctv_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                subsriptionViewHolder.ctv_actual_price.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            subsriptionViewHolder.ctv_discounted_amount.setText("₹ " + recordBean.getDiscountPrice());
            subsriptionViewHolder.ctv_actual_price.setText("₹ " + recordBean.getPrice());
            subsriptionViewHolder.ctv_actual_price.setVisibility(View.VISIBLE);
            subsriptionViewHolder.ctv_actual_price.setPaintFlags(subsriptionViewHolder.ctv_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (selectedIndex == i) {
            subsriptionViewHolder.linearLayoutLabelRoot.setVisibility(View.VISIBLE);
            subsriptionViewHolder.card_start.setVisibility(View.VISIBLE);
        } else {
            subsriptionViewHolder.linearLayoutLabelRoot.setVisibility(View.GONE);
            subsriptionViewHolder.card_start.setVisibility(View.GONE);
        }

        subsriptionViewHolder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIndex==i) {
                    selectedIndex = -1;
                }else {
                    selectedIndex = i;
                }

                notifyDataSetChanged();
            }
        });

        subsriptionViewHolder.card_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscriptionListActivity.buySubscription(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return subsriptionsList.size();
    }

    public class SubsriptionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ctv_days)
        CustomTextView ctv_days;

        @BindView(R.id.ctv_day_label)
        CustomTextView ctv_day_label;

        @BindView(R.id.ctv_discounted_amount)
        CustomTextView ctv_discounted_amount;

        @BindView(R.id.card_start)
        CardView card_start;

        @BindView(R.id.rl_root)
        View rl_root;

        @BindView(R.id.ctv_actual_price)
        CustomTextView ctv_actual_price;


        @BindView(R.id.linearLayoutLabelRoot)
        LinearLayout linearLayoutLabelRoot;

        public SubsriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
