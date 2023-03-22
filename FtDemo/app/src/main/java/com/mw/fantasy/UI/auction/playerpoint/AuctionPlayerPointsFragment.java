package com.mw.fantasy.UI.auction.playerpoint;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseFragment;
import com.mw.fantasy.beanInput.GetDraftPlayerPointInput;
import com.mw.fantasy.beanOutput.GetDraftPlayerPointOutput;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuctionPlayerPointsFragment extends BaseFragment {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @BindView(R.id.stats_not_found)
    CustomTextView stats_not_found;

    private Context mContext;
    private String roundID, seriesID, playerGUID;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private GetDraftPlayerPointOutput mGetDraftPlayerPointOutput;
    private ArrayList<GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean> pointDataList = new ArrayList<>();
    private ArrayList<GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean> pointsData;

    public static Fragment getInstance(String playerGUID, String seriesId, String roundId) {
        AuctionPlayerPointsFragment fragment = new AuctionPlayerPointsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("playerGUID", playerGUID);
        bundle.putString("seriesId", seriesId);
        bundle.putString("roundId", roundId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_auction_player_points;
    }

    @Override
    public void init() {
        mContext = getActivity();
        mProgressDialog = new ProgressDialog(mContext);
        roundID = getArguments().getString("roundId");
        seriesID = getArguments().getString("seriesId");
        playerGUID = getArguments().getString("playerGUID");

        dummyList();

        apiCallingGetDraftPlayerPoints();
    }


    private void apiCallingGetDraftPlayerPoints() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            GetDraftPlayerPointInput getDraftPlayerPointInput = new GetDraftPlayerPointInput();
            getDraftPlayerPointInput.setPlayerGUID(playerGUID);
            if (AppSession.getInstance().getPlayMode()==2) {
                getDraftPlayerPointInput.setMatchGUID(roundID);
            }else {
                getDraftPlayerPointInput.setRoundID(roundID);
            }

            getDraftPlayerPointInput.setSeriesID(seriesID);
            getDraftPlayerPointInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getDraftPlayerPointInput.setParams("TeamFlagLocal,TeamFlagVisitor,MatchLocation,PlayerBattingStyle,PlayerBowlingStyle,PlayerID,PlayerRole,PlayerPic,PlayerCountry,MatchType,MatchNo,MatchDateTime,SeriesName,TeamGUID,PointsData,TotalPoints");
            new UserInteractor().getDraftPlayersPoint(getDraftPlayerPointInput, new IUserInteractor.OnGetDraftPlayersPointListener() {
                @Override
                public void onSuccess(GetDraftPlayerPointOutput getDraftPlayerPointOutput) {
                    mGetDraftPlayerPointOutput = getDraftPlayerPointOutput;
                    mProgressDialog.dismiss();

                    for (GetDraftPlayerPointOutput.DataBean.RecordsBean recordsBean : mGetDraftPlayerPointOutput.getData().getRecords()) {
                        pointDataList.addAll(recordsBean.getPointsData());
                    }

                    if (pointDataList.size() > 0) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        stats_not_found.setVisibility(View.GONE);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        mRecyclerView.setAdapter(new AllPointRecyclerAdapter(mContext, mGetDraftPlayerPointOutput,pointsData));
                        double res=0;
                        for (GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean pointsDataBean : pointDataList) {
                            res+=Double.parseDouble(pointsDataBean.getCalculatedPoints().trim());
                        }
                        ((AuctionPlayerStatsActivity)mContext).setPlayerPoint(res+"");
                    } else {
                        ((AuctionPlayerStatsActivity)mContext).setPlayerPoint("0");
                        mRecyclerView.setVisibility(View.GONE);
                        stats_not_found.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            apiCallingGetDraftPlayerPoints();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                        }
                    });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AlertDialog(mContext, AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                @Override
                public void onYesClick() {
                    mAlertDialog.hide();
                    apiCallingGetDraftPlayerPoints();
                }

                @Override
                public void onNoClick() {
                    mAlertDialog.hide();

                }
            });
            mAlertDialog.show();
        }
    }


    private void dummyList() {
        pointsData = new ArrayList<>();
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean0 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean0.setPointsTypeGUID("Starting Bonus");
        dataBean0.setDefinedPoints("0");
        dataBean0.setCalculatedPoints("0");
        pointsData.add(dataBean0);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean1 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean1.setPointsTypeGUID("Every Run Scored");
        dataBean1.setDefinedPoints("0");
        dataBean1.setCalculatedPoints("0");
        pointsData.add(dataBean1);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean2 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean2.setPointsTypeGUID("Four");
        dataBean2.setDefinedPoints("0");
        dataBean2.setCalculatedPoints("0");
        pointsData.add(dataBean2);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean3 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean3.setPointsTypeGUID("Six");
        dataBean3.setDefinedPoints("0");
        dataBean3.setCalculatedPoints("0");
        pointsData.add(dataBean3);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean4 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean4.setPointsTypeGUID("Strike Rate");
        dataBean4.setDefinedPoints("0");
        dataBean4.setCalculatedPoints("0");
        pointsData.add(dataBean4);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean5 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean5.setPointsTypeGUID("For 30 runs");
        dataBean5.setDefinedPoints("0");
        dataBean5.setCalculatedPoints("0");
        pointsData.add(dataBean5);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean6 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean6.setPointsTypeGUID("Duck");
        dataBean6.setDefinedPoints("0");
        dataBean6.setCalculatedPoints("0");
        pointsData.add(dataBean6);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean7 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean7.setPointsTypeGUID("Wicket");
        dataBean7.setDefinedPoints("0");
        dataBean7.setCalculatedPoints("0");
        pointsData.add(dataBean7);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean8 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean8.setPointsTypeGUID("Maiden");
        dataBean8.setDefinedPoints("0");
        dataBean8.setCalculatedPoints("0");
        pointsData.add(dataBean8);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean9 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean9.setPointsTypeGUID("Economy Rate");
        dataBean9.setDefinedPoints("0");
        dataBean9.setCalculatedPoints("0");
        pointsData.add(dataBean9);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean01 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean01.setPointsTypeGUID("Three Wickets");
        dataBean01.setDefinedPoints("0");
        dataBean01.setCalculatedPoints("0");
        pointsData.add(dataBean01);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean02 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean02.setPointsTypeGUID("Run OUT");
        dataBean02.setDefinedPoints("0");
        dataBean02.setCalculatedPoints("0");
        pointsData.add(dataBean02);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean03 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean03.setPointsTypeGUID("Stumping");
        dataBean03.setDefinedPoints("0");
        dataBean03.setCalculatedPoints("0");
        pointsData.add(dataBean03);
        GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean dataBean04 = new GetDraftPlayerPointOutput.DataBean.RecordsBean.PointsDataBean();
        dataBean04.setPointsTypeGUID("Catch");
        dataBean04.setDefinedPoints("0");
        dataBean04.setCalculatedPoints("0");
        pointsData.add(dataBean04);


    }


}
