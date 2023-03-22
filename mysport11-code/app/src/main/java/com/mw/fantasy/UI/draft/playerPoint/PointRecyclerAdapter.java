package com.mw.fantasy.UI.draft.playerPoint;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mw.fantasy.R;
import com.mw.fantasy.beanOutput.GetDraftPlayerPointOutput;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private GetDraftPlayerPointOutput mGetDraftPlayerPointOutput;

    public PointRecyclerAdapter(GetDraftPlayerPointOutput mGetDraftPlayerPointOutput) {
        this.mGetDraftPlayerPointOutput = mGetDraftPlayerPointOutput;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new PointsViewHolderHeader(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_draft_point_header, viewGroup, false));
        } else {
            return new PointsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_draft_point_data, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PointsViewHolderHeader) {
            GetDraftPlayerPointOutput.DataBean.RecordsBean recordsBean = mGetDraftPlayerPointOutput.getData().getRecords().get(0);
            PointsViewHolderHeader pointsViewHolderHeader = (PointsViewHolderHeader) viewHolder;
            pointsViewHolderHeader.mCustomTextViewName.setText(recordsBean.getPlayerName());
            ViewUtils.setImageUrl(pointsViewHolderHeader.mCustomImageViewPic, recordsBean.getPlayerPic());
            pointsViewHolderHeader.mCustomTextViewBatStyle.setText(recordsBean.getPlayerBattingStyle());
            pointsViewHolderHeader.mCustomTextViewBowlingStyle.setText(recordsBean.getPlayerBowlingStyle());
            pointsViewHolderHeader.mCustomTextViewContry.setText(recordsBean.getPlayerCountry());
            switch (recordsBean.getPlayerRole()) {
                case "WicketKeeper":
                    pointsViewHolderHeader.mCustomTextViewRole.setText("Wicket Keeper");
                    break;
                case "Bowler":
                    pointsViewHolderHeader.mCustomTextViewRole.setText("Bowler");
                    break;
                case "Batsman":
                    pointsViewHolderHeader.mCustomTextViewRole.setText("Batsman");
                    break;
                case "AllRounder":
                    pointsViewHolderHeader.mCustomTextViewRole.setText("All-Rounder");
                    break;
            }
        } else {
            PointsViewHolder pointsViewHolder = (PointsViewHolder) viewHolder;
            GetDraftPlayerPointOutput.DataBean.RecordsBean recordsBean = mGetDraftPlayerPointOutput.getData().getRecords().get(i - 1);
            ViewUtils.setImageUrl(pointsViewHolder.mCustomImageViewTeam1, recordsBean.getTeamFlagLocal());
            ViewUtils.setImageUrl(pointsViewHolder.mCustomImageViewTeam2, recordsBean.getTeamFlagVisitor());
            pointsViewHolder.mCtvTeam1Name.setText(recordsBean.getTeamNameShortLocal());
            pointsViewHolder.mCtvTeam2Name.setText(recordsBean.getTeamNameShortVisitor());
            List<GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean> pointsData = recordsBean.getPointsData();
            if (pointsData.size()!=0) {
                pointsViewHolder.ctv_sp.setText(pointsData.get(0).getCalculatedPoints());
                pointsViewHolder.ctv_trsp.setText(pointsData.get(1).getCalculatedPoints());
                pointsViewHolder.ctv_fsp.setText(pointsData.get(2).getCalculatedPoints());
                pointsViewHolder.ctv_ssp.setText(pointsData.get(3).getCalculatedPoints());
                pointsViewHolder.ctv_srbp.setText(pointsData.get(4).getCalculatedPoints());
                pointsViewHolder.ctv_bbp.setText(pointsData.get(5).getCalculatedPoints());
                pointsViewHolder.ctv_dp.setText(pointsData.get(6).getCalculatedPoints());
                pointsViewHolder.ctv_wtp.setText(pointsData.get(7).getCalculatedPoints());
                pointsViewHolder.ctv_mobp.setText(pointsData.get(8).getCalculatedPoints());
                pointsViewHolder.ctv_ebp.setText(pointsData.get(9).getCalculatedPoints());
                pointsViewHolder.ctv_bow_bp.setText(pointsData.get(10).getCalculatedPoints());
                pointsViewHolder.ctv_trop.setText(pointsData.get(11).getCalculatedPoints());
                pointsViewHolder.ctv_stump_p.setText(pointsData.get(12).getCalculatedPoints());
                pointsViewHolder.ctv_cp.setText(pointsData.get(13).getCalculatedPoints());
                pointsViewHolder.ctv_total.setText(recordsBean.getTotalPoints());
            }else {
                pointsViewHolder.ctv_sp.setText("0");
                pointsViewHolder.ctv_trsp.setText("0");
                pointsViewHolder.ctv_fsp.setText("0");
                pointsViewHolder.ctv_ssp.setText("0");
                pointsViewHolder.ctv_srbp.setText("0");
                pointsViewHolder.ctv_bbp.setText("0");
                pointsViewHolder.ctv_dp.setText("0");
                pointsViewHolder.ctv_wtp.setText("0");
                pointsViewHolder.ctv_mobp.setText("0");
                pointsViewHolder.ctv_ebp.setText("0");
                pointsViewHolder.ctv_bow_bp.setText("0");
                pointsViewHolder.ctv_trop.setText("0");
                pointsViewHolder.ctv_stump_p.setText("0");
                pointsViewHolder.ctv_cp.setText("0");
                pointsViewHolder.ctv_total.setText("0.0");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mGetDraftPlayerPointOutput.getStatus() == 1 ? mGetDraftPlayerPointOutput.getData().getRecords().size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class PointsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_team1)
        CustomImageView mCustomImageViewTeam1;

        @BindView(R.id.civ_team2)
        CustomImageView mCustomImageViewTeam2;


        @BindView(R.id.ctv_team1)
        CustomTextView mCtvTeam1Name;

        @BindView(R.id.ctv_team2)
        CustomTextView mCtvTeam2Name;

        @BindView(R.id.ctv_sp)
        CustomTextView ctv_sp;

        @BindView(R.id.ctv_trsp)
        CustomTextView ctv_trsp;


        @BindView(R.id.ctv_fsp)
        CustomTextView ctv_fsp;


        @BindView(R.id.ctv_ssp)
        CustomTextView ctv_ssp;


        @BindView(R.id.ctv_srbp)
        CustomTextView ctv_srbp;


        @BindView(R.id.ctv_bbp)
        CustomTextView ctv_bbp;


        @BindView(R.id.ctv_dp)
        CustomTextView ctv_dp;


        @BindView(R.id.ctv_wtp)
        CustomTextView ctv_wtp;


        @BindView(R.id.ctv_mobp)
        CustomTextView ctv_mobp;

        @BindView(R.id.ctv_ebp)
        CustomTextView ctv_ebp;


        @BindView(R.id.ctv_bow_bp)
        CustomTextView ctv_bow_bp;

        @BindView(R.id.ctv_trop)
        CustomTextView ctv_trop;

        @BindView(R.id.ctv_stump_p)
        CustomTextView ctv_stump_p;

        @BindView(R.id.ctv_cp)
        CustomTextView ctv_cp;


        @BindView(R.id.ctv_total)
        CustomTextView ctv_total;


        public PointsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class PointsViewHolderHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_player_img)
        CustomImageView mCustomImageViewPic;

        @BindView(R.id.ctv_player_name)
        CustomTextView mCustomTextViewName;

        @BindView(R.id.ctv_country)
        CustomTextView mCustomTextViewContry;

        @BindView(R.id.ctv_role)
        CustomTextView mCustomTextViewRole;

        @BindView(R.id.ctv_batting_style)
        CustomTextView mCustomTextViewBatStyle;

        @BindView(R.id.ctv_bowling_style)
        CustomTextView mCustomTextViewBowlingStyle;


        public PointsViewHolderHeader(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
