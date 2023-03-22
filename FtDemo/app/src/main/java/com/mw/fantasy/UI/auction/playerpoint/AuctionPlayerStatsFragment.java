package com.mw.fantasy.UI.auction.playerpoint;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.player.PlayerActivityPresenterImpl;
import com.mw.fantasy.UI.player.PlayerActivityView;

import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseFragment;
import com.mw.fantasy.beanInput.PlayerFantasyStatsInput;
import com.mw.fantasy.beanOutput.ResponsePlayerFantasyStats;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuctionPlayerStatsFragment extends BaseFragment implements PlayerActivityView {


    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;


    @BindView(R.id.ctv_no_stats_batting_odi)
    CustomTextView ctv_no_stats_batting_odi;
    @BindView(R.id.ctv_no_stats_batting_t20)
    CustomTextView ctv_no_stats_batting_t20;
    @BindView(R.id.ctv_no_stats_batting_test)
    CustomTextView ctv_no_stats_batting_test;
    @BindView(R.id.ctv_no_stats_batting_t10)
    CustomTextView ctv_no_stats_batting_t10;

    @BindView(R.id.ctv_no_stats_fielding_odi)
    CustomTextView ctv_no_stats_fielding_odi;
    @BindView(R.id.ctv_no_stats_fielding_t20)
    CustomTextView ctv_no_stats_fielding_t20;
    @BindView(R.id.ctv_no_stats_fielding_test)
    CustomTextView ctv_no_stats_fielding_test;


    @BindView(R.id.ctv_no_stats_fielding_t10)
    CustomTextView ctv_no_stats_fielding_t10;


    @BindView(R.id.t10_bat_avg)
    CustomTextView t10_bat_avg;
    @BindView(R.id.t10_bat_str)
    CustomTextView t10_bat_str;
    @BindView(R.id.t10_bat_runs)
    CustomTextView t10_bat_runs;


    @BindView(R.id.test_bat_avg)
    CustomTextView test_bat_avg;
    @BindView(R.id.test_bat_str)
    CustomTextView test_bat_str;
    @BindView(R.id.test_bat_runs)
    CustomTextView test_bat_runs;
    @BindView(R.id.odi_bat_avg)
    CustomTextView odi_bat_avg;
    @BindView(R.id.odi_bat_str)
    CustomTextView odi_bat_str;
    @BindView(R.id.odi_bat_runs)
    CustomTextView odi_bat_runs;
    @BindView(R.id.t20_bat_avg)
    CustomTextView t20_bat_avg;
    @BindView(R.id.t20_bat_str)
    CustomTextView t20_bat_str;
    @BindView(R.id.t20_bat_runs)
    CustomTextView t20_bat_runs;

    @BindView(R.id.ll_main)
    LinearLayout ll_main;

    @BindView(R.id.stats_not_found)
    CustomTextView stats_not_found;

    @BindView(R.id.rl)
    RelativeLayout mRelativeLayoutStats;

    @BindView(R.id.odi_field_catches)
    CustomTextView odi_field_catches;
    @BindView(R.id.odi_field_st)
    CustomTextView odi_field_st;
    @BindView(R.id.t20_field_catches)
    CustomTextView t20_field_catches;
    @BindView(R.id.t20_field_st)
    CustomTextView t20_field_st;

    @BindView(R.id.t10_field_catches)
    CustomTextView t10_field_catches;
    @BindView(R.id.t10_field_st)
    CustomTextView t10_field_st;


    @BindView(R.id.test_field_catches)
    CustomTextView test_field_catches;
    @BindView(R.id.test_field_st)
    CustomTextView test_field_st;

    @BindView(R.id.ll_batting)
    LinearLayout ll_batting;

    @BindView(R.id.ll_fielding)
    LinearLayout ll_fielding;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private Context mContext;
    private ProgressDialog mProgressDialog;
    private String playerGUID, seriesId;
    private PlayerActivityPresenterImpl mPlayerActivityPresenter;
    private AuctionPlayerAdapter mPlayerAdapter;
    private ResponsePlayerFantasyStats response;

    public static Fragment getInstance(String playerGUID, String seriesId) {
        AuctionPlayerStatsFragment fragment = new AuctionPlayerStatsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("playerGUID", playerGUID);
        bundle.putString("seriesId", seriesId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_auction_player_stats;
    }

    @Override
    public void init() {
        mContext = getActivity();

        mProgressDialog = new ProgressDialog(mContext);

        if (getArguments().containsKey("playerGUID")) {
            playerGUID = getArguments().getString("playerGUID");
        }
        if (getArguments().containsKey("seriesId")) {
            seriesId = getArguments().getString("seriesId");
        }
        mPlayerActivityPresenter = new PlayerActivityPresenterImpl(this, new UserInteractor());


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mTabLayout.addTab(mTabLayout.newTab().setText("Batting"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Bowling"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Fielding"));

        AppUtils.applyFontedTabLayout(getActivity(), mTabLayout);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ll_batting.setVisibility(View.VISIBLE);
                        ll_fielding.setVisibility(View.GONE);
                        battingStats(response);
                        break;
                    case 1:
                        ll_batting.setVisibility(View.VISIBLE);
                        ll_fielding.setVisibility(View.GONE);
                        bowlingStats(response);
                        break;
                    case 2:
                        ll_batting.setVisibility(View.GONE);
                        ll_fielding.setVisibility(View.VISIBLE);
                        fieldStats(response);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getPlayerData();
    }

    private void getPlayerData() {
        PlayerFantasyStatsInput mPlayerFantasyStatsInput = new PlayerFantasyStatsInput();
        mPlayerFantasyStatsInput.setPlayerGUID(playerGUID);
        mPlayerFantasyStatsInput.setSeriesGUID(seriesId);
        mPlayerFantasyStatsInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mPlayerFantasyStatsInput.setParams(Constant.PLAYER_FANTASY_STATS);

        mPlayerActivityPresenter.actionPlayerStats(mPlayerFantasyStatsInput);
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onPlayerStatsSuccess(ResponsePlayerFantasyStats responseLogin) {
        response = responseLogin;

        battingStats(responseLogin);

        List<ResponsePlayerFantasyStats.DataBean.RecordsBean> responseBeanList = responseLogin.getData().getRecords();
        if (responseBeanList != null) {
            mPlayerAdapter = new AuctionPlayerAdapter(mContext, responseBeanList);
            mRecyclerView.setAdapter(mPlayerAdapter);
        } else {
            mRelativeLayoutStats.setVisibility(View.INVISIBLE);
            stats_not_found.setVisibility(View.VISIBLE);
            //Toast.makeText(mContext, "Player Stats Data not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void bowlingStats(ResponsePlayerFantasyStats responseLogin) {
        if (responseLogin.getData().getPlayerBowlingStats().getODI() != null) {
            if (responseLogin.getData().getPlayerBowlingStats().getTest().getAverage() == 0.0 &&
                    responseLogin.getData().getPlayerBowlingStats().getTest().getStrikeRate() == 0.0 &&
                    responseLogin.getData().getPlayerBowlingStats().getTest().getRuns() == 0.0) {
                ctv_no_stats_batting_test.setVisibility(View.VISIBLE);
                test_bat_avg.setVisibility(View.GONE);
                test_bat_str.setVisibility(View.GONE);
                test_bat_runs.setVisibility(View.GONE);
            } else {
                ctv_no_stats_batting_test.setVisibility(View.GONE);
                test_bat_avg.setVisibility(View.VISIBLE);
                test_bat_str.setVisibility(View.VISIBLE);
                test_bat_runs.setVisibility(View.VISIBLE);
                test_bat_avg.setText(responseLogin.getData().getPlayerBowlingStats().getTest().getAverage() + "");
                test_bat_str.setText(responseLogin.getData().getPlayerBowlingStats().getTest().getStrikeRate() + "");
                test_bat_runs.setText(responseLogin.getData().getPlayerBowlingStats().getTest().getRuns() + "");
            }

            if (responseLogin.getData().getPlayerBowlingStats().getODI().getAverage() == 0.0 &&
                    responseLogin.getData().getPlayerBowlingStats().getODI().getStrikeRate() == 0.0 &&
                    responseLogin.getData().getPlayerBowlingStats().getODI().getRuns() == 0.0) {
                ctv_no_stats_batting_odi.setVisibility(View.VISIBLE);
                odi_bat_avg.setVisibility(View.GONE);
                odi_bat_str.setVisibility(View.GONE);
                odi_bat_runs.setVisibility(View.GONE);
            } else {

                ctv_no_stats_batting_odi.setVisibility(View.GONE);
                odi_bat_avg.setVisibility(View.VISIBLE);
                odi_bat_str.setVisibility(View.VISIBLE);
                odi_bat_runs.setVisibility(View.VISIBLE);
                odi_bat_avg.setText(responseLogin.getData().getPlayerBowlingStats().getODI().getAverage() + "");
                odi_bat_str.setText(responseLogin.getData().getPlayerBowlingStats().getODI().getStrikeRate() + "");
                odi_bat_runs.setText(responseLogin.getData().getPlayerBowlingStats().getODI().getRuns() + "");

            }

            if (responseLogin.getData().getPlayerBowlingStats().getT20().getAverage() == 0.0 &&
                    responseLogin.getData().getPlayerBowlingStats().getT20().getStrikeRate() == 0.0 &&
                    responseLogin.getData().getPlayerBowlingStats().getT20().getRuns() == 0.0) {
                ctv_no_stats_batting_t20.setVisibility(View.VISIBLE);
                t20_bat_avg.setVisibility(View.GONE);
                t20_bat_str.setVisibility(View.GONE);
                t20_bat_runs.setVisibility(View.GONE);
            } else {
                ctv_no_stats_batting_t20.setVisibility(View.GONE);
                t20_bat_avg.setVisibility(View.VISIBLE);
                t20_bat_str.setVisibility(View.VISIBLE);
                t20_bat_runs.setVisibility(View.VISIBLE);
                t20_bat_avg.setText(responseLogin.getData().getPlayerBowlingStats().getT20().getAverage() + "");
                t20_bat_str.setText(responseLogin.getData().getPlayerBowlingStats().getT20().getStrikeRate() + "");
                t20_bat_runs.setText(responseLogin.getData().getPlayerBowlingStats().getT20().getRuns() + "");
            }

            if (responseLogin.getData().getPlayerBowlingStats().getT10() == null || responseLogin.getData().getPlayerBowlingStats().getT10().getAverage() == 0.0 &&
                    responseLogin.getData().getPlayerBowlingStats().getT10().getStrikeRate() == 0.0 &&
                    responseLogin.getData().getPlayerBowlingStats().getT10().getRuns() == 0.0) {
                ctv_no_stats_batting_t10.setVisibility(View.VISIBLE);
                t10_bat_avg.setVisibility(View.GONE);
                t10_bat_str.setVisibility(View.GONE);
                t10_bat_runs.setVisibility(View.GONE);
            } else {
                ctv_no_stats_batting_t10.setVisibility(View.GONE);
                t10_bat_avg.setVisibility(View.VISIBLE);
                t10_bat_str.setVisibility(View.VISIBLE);
                t10_bat_runs.setVisibility(View.VISIBLE);
                t10_bat_avg.setText(responseLogin.getData().getPlayerBowlingStats().getT10().getAverage() + "");
                t10_bat_str.setText(responseLogin.getData().getPlayerBowlingStats().getT10().getStrikeRate() + "");
                t10_bat_runs.setText(responseLogin.getData().getPlayerBowlingStats().getT10().getRuns() + "");
            }

        } else {
            ctv_no_stats_batting_test.setVisibility(View.VISIBLE);
            test_bat_avg.setVisibility(View.GONE);
            test_bat_str.setVisibility(View.GONE);
            test_bat_runs.setVisibility(View.GONE);

            ctv_no_stats_batting_odi.setVisibility(View.VISIBLE);
            odi_bat_avg.setVisibility(View.GONE);
            odi_bat_str.setVisibility(View.GONE);
            odi_bat_runs.setVisibility(View.GONE);

            ctv_no_stats_batting_t20.setVisibility(View.VISIBLE);
            t20_bat_avg.setVisibility(View.GONE);
            t20_bat_str.setVisibility(View.GONE);
            t20_bat_runs.setVisibility(View.GONE);

            ctv_no_stats_batting_t10.setVisibility(View.VISIBLE);
            t10_bat_avg.setVisibility(View.GONE);
            t10_bat_str.setVisibility(View.GONE);
            t10_bat_runs.setVisibility(View.GONE);
        }

    }

    private void fieldStats(ResponsePlayerFantasyStats responseLogin) {
        if (responseLogin.getData().getPlayerFieldingStats().getODI() != null) {
            if (responseLogin.getData().getPlayerFieldingStats().getTest().getCatches() == 0.0 &&
                    responseLogin.getData().getPlayerFieldingStats().getTest().getStumpings() == 0.0) {
                ctv_no_stats_fielding_test.setVisibility(View.VISIBLE);
                test_field_catches.setVisibility(View.GONE);
                test_field_st.setVisibility(View.GONE);
            } else {
                ctv_no_stats_fielding_test.setVisibility(View.GONE);
                test_field_catches.setVisibility(View.VISIBLE);
                test_field_st.setVisibility(View.VISIBLE);
                test_field_catches.setText(responseLogin.getData().getPlayerFieldingStats().getTest().getCatches() + "");
                test_field_st.setText(responseLogin.getData().getPlayerFieldingStats().getTest().getStumpings() + "");
            }

            if (responseLogin.getData().getPlayerFieldingStats().getODI().getCatches() == 0.0 &&
                    responseLogin.getData().getPlayerFieldingStats().getODI().getStumpings() == 0.0) {
                ctv_no_stats_fielding_odi.setVisibility(View.VISIBLE);
                odi_field_catches.setVisibility(View.GONE);
                odi_field_st.setVisibility(View.GONE);
            } else {
                ctv_no_stats_fielding_odi.setVisibility(View.GONE);
                odi_field_catches.setVisibility(View.VISIBLE);
                odi_field_st.setVisibility(View.VISIBLE);
                odi_field_catches.setText(responseLogin.getData().getPlayerFieldingStats().getODI().getCatches() + "");
                odi_field_st.setText(responseLogin.getData().getPlayerFieldingStats().getODI().getStumpings() + "");
            }

            if (responseLogin.getData().getPlayerFieldingStats().getT20().getCatches() == 0.0 &&
                    responseLogin.getData().getPlayerFieldingStats().getT20().getStumpings() == 0.0) {
                ctv_no_stats_fielding_t20.setVisibility(View.VISIBLE);
                t20_field_catches.setVisibility(View.GONE);
                t20_field_st.setVisibility(View.GONE);
            } else {
                ctv_no_stats_fielding_t20.setVisibility(View.GONE);
                t20_field_catches.setVisibility(View.VISIBLE);
                t20_field_st.setVisibility(View.VISIBLE);
                t20_field_catches.setText(responseLogin.getData().getPlayerFieldingStats().getT20().getCatches() + "");
                t20_field_st.setText(responseLogin.getData().getPlayerFieldingStats().getT20().getStumpings() + "");
            }

            if (responseLogin.getData().getPlayerFieldingStats().getT10()==null||responseLogin.getData().getPlayerFieldingStats().getT10().getCatches() == 0.0 &&
                    responseLogin.getData().getPlayerFieldingStats().getT10().getStumpings() == 0.0) {
                ctv_no_stats_fielding_t10.setVisibility(View.VISIBLE);
                t10_field_catches.setVisibility(View.GONE);
                t10_field_st.setVisibility(View.GONE);
            } else {
                ctv_no_stats_fielding_t10.setVisibility(View.GONE);
                t10_field_catches.setVisibility(View.VISIBLE);
                t10_field_st.setVisibility(View.VISIBLE);
                t10_field_catches.setText(responseLogin.getData().getPlayerFieldingStats().getT10().getCatches() + "");
                t10_field_st.setText(responseLogin.getData().getPlayerFieldingStats().getT10().getStumpings() + "");
            }
        } else {
            ctv_no_stats_fielding_test.setVisibility(View.VISIBLE);
            test_field_catches.setVisibility(View.GONE);
            test_field_st.setVisibility(View.GONE);
            ctv_no_stats_fielding_odi.setVisibility(View.VISIBLE);
            odi_field_catches.setVisibility(View.GONE);
            odi_field_st.setVisibility(View.GONE);

            ctv_no_stats_fielding_t20.setVisibility(View.VISIBLE);
            t20_field_catches.setVisibility(View.GONE);
            t20_field_st.setVisibility(View.GONE);

            ctv_no_stats_fielding_t10.setVisibility(View.VISIBLE);
            t10_field_catches.setVisibility(View.GONE);
            t10_field_st.setVisibility(View.GONE);
        }

    }

    private void battingStats(ResponsePlayerFantasyStats responseLogin) {
        if (responseLogin.getData().getPlayerBattingStats().getODI() != null) {
            if (responseLogin.getData().getPlayerBattingStats().getODI().getAverage() == 0.0 &&
                    responseLogin.getData().getPlayerBattingStats().getODI().getStrikeRate() == 0.0 &&
                    responseLogin.getData().getPlayerBattingStats().getODI().getRuns() == 0.0) {
                ctv_no_stats_batting_odi.setVisibility(View.VISIBLE);
                odi_bat_avg.setVisibility(View.GONE);
                odi_bat_str.setVisibility(View.GONE);
                odi_bat_runs.setVisibility(View.GONE);
            } else {
                ctv_no_stats_batting_odi.setVisibility(View.GONE);
                odi_bat_avg.setVisibility(View.VISIBLE);
                odi_bat_str.setVisibility(View.VISIBLE);
                odi_bat_runs.setVisibility(View.VISIBLE);
                odi_bat_avg.setText(responseLogin.getData().getPlayerBattingStats().getODI().getAverage() + "");
                odi_bat_str.setText(responseLogin.getData().getPlayerBattingStats().getODI().getStrikeRate() + "");
                odi_bat_runs.setText(responseLogin.getData().getPlayerBattingStats().getODI().getRuns() + "");
            }

            if (responseLogin.getData().getPlayerBattingStats().getTest().getAverage() == 0.0 &&
                    responseLogin.getData().getPlayerBattingStats().getTest().getStrikeRate() == 0.0 &&
                    responseLogin.getData().getPlayerBattingStats().getTest().getRuns() == 0.0) {

                ctv_no_stats_batting_test.setVisibility(View.VISIBLE);
                test_bat_avg.setVisibility(View.GONE);
                test_bat_str.setVisibility(View.GONE);
                test_bat_runs.setVisibility(View.GONE);
            } else {
                ctv_no_stats_batting_test.setVisibility(View.GONE);
                test_bat_avg.setVisibility(View.VISIBLE);
                test_bat_str.setVisibility(View.VISIBLE);
                test_bat_runs.setVisibility(View.VISIBLE);
                test_bat_avg.setText(responseLogin.getData().getPlayerBattingStats().getTest().getAverage() + "");
                test_bat_str.setText(responseLogin.getData().getPlayerBattingStats().getTest().getStrikeRate() + "");
                test_bat_runs.setText(responseLogin.getData().getPlayerBattingStats().getTest().getRuns() + "");
            }

            if (responseLogin.getData().getPlayerBattingStats().getT20().getAverage() == 0.0 &&
                    responseLogin.getData().getPlayerBattingStats().getT20().getStrikeRate() == 0.0 &&
                    responseLogin.getData().getPlayerBattingStats().getT20().getRuns() == 0.0) {

                ctv_no_stats_batting_t20.setVisibility(View.VISIBLE);
                t20_bat_avg.setVisibility(View.GONE);
                t20_bat_str.setVisibility(View.GONE);
                t20_bat_runs.setVisibility(View.GONE);
            } else {
                ctv_no_stats_batting_t20.setVisibility(View.GONE);
                t20_bat_avg.setVisibility(View.VISIBLE);
                t20_bat_str.setVisibility(View.VISIBLE);
                t20_bat_runs.setVisibility(View.VISIBLE);
                t20_bat_avg.setText(responseLogin.getData().getPlayerBattingStats().getT20().getAverage() + "");
                t20_bat_str.setText(responseLogin.getData().getPlayerBattingStats().getT20().getStrikeRate() + "");
                t20_bat_runs.setText(responseLogin.getData().getPlayerBattingStats().getT20().getRuns() + "");
            }

            if (responseLogin.getData().getPlayerBattingStats().getT10() == null || responseLogin.getData().getPlayerBattingStats().getT10().getAverage() == 0.0 &&
                    responseLogin.getData().getPlayerBattingStats().getT10().getStrikeRate() == 0.0 &&
                    responseLogin.getData().getPlayerBattingStats().getT10().getRuns() == 0.0) {

                ctv_no_stats_batting_t10.setVisibility(View.VISIBLE);
                t10_bat_avg.setVisibility(View.GONE);
                t10_bat_str.setVisibility(View.GONE);
                t10_bat_runs.setVisibility(View.GONE);
            } else {
                ctv_no_stats_batting_t10.setVisibility(View.GONE);
                t10_bat_avg.setVisibility(View.VISIBLE);
                t10_bat_str.setVisibility(View.VISIBLE);
                t10_bat_runs.setVisibility(View.VISIBLE);
                t10_bat_avg.setText(responseLogin.getData().getPlayerBattingStats().getT10().getAverage() + "");
                t10_bat_str.setText(responseLogin.getData().getPlayerBattingStats().getT10().getStrikeRate() + "");
                t10_bat_runs.setText(responseLogin.getData().getPlayerBattingStats().getT10().getRuns() + "");
            }

        } else {
            ctv_no_stats_batting_test.setVisibility(View.VISIBLE);
            test_bat_avg.setVisibility(View.GONE);
            test_bat_str.setVisibility(View.GONE);
            test_bat_runs.setVisibility(View.GONE);

            ctv_no_stats_batting_odi.setVisibility(View.VISIBLE);
            odi_bat_avg.setVisibility(View.GONE);
            odi_bat_str.setVisibility(View.GONE);
            odi_bat_runs.setVisibility(View.GONE);

            ctv_no_stats_batting_t20.setVisibility(View.VISIBLE);
            t20_bat_avg.setVisibility(View.GONE);
            t20_bat_str.setVisibility(View.GONE);
            t20_bat_runs.setVisibility(View.GONE);


            ctv_no_stats_batting_t10.setVisibility(View.VISIBLE);
            t10_bat_avg.setVisibility(View.GONE);
            t10_bat_str.setVisibility(View.GONE);
            t10_bat_runs.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPlayerStatsFailure(String errMsg) {
        hideLoading();
        showSnackBar(errMsg);
    }

    @Override
    public void showSnackBar(String message) {
        hideLoading();
        AppUtils.showSnackBar(mContext, ll_main, message);
    }
}
