package com.mw.fantasy.UI.auction.playerpoint;


import android.content.Context;
import android.content.Intent;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;


import com.facebook.drawee.view.SimpleDraweeView;
import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.base.BasePagerAdapter;
import com.mw.fantasy.beanInput.PlayerFantasyStatsInput;
import com.mw.fantasy.beanOutput.PlayersOutput;
import com.mw.fantasy.beanOutput.SinglePlayerData;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AuctionPlayerStatsActivity extends BaseActivity {


    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.ctv_player_name)
    CustomTextView ctv_player_name;

    @BindView(R.id.teamName)
    CustomTextView teamName;

    @BindView(R.id.ctv_total)
    CustomTextView ctv_total;

    @BindView(R.id.sdv)
    SimpleDraweeView mSimpleDraweeView;
    private String roundId = "";
    private String seriesId;
    private UserInteractor mInteractor;
    private AlertDialog mAlertDialog;
    private String playerGUID;


    @OnClick(R.id.iv_change_mode)
    public void onBack() {
        finish();
    }

    @BindView(R.id.ctv_game_type)
    CustomTextView ctvImageType;

   /* @BindView(R.id.ctv_player_name)
    CustomTextView ctv_player_name;*/

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private BasePagerAdapter mViewPagerAdapter;
    private PlayersOutput.DataBean.RecordsBean data;
    private String seriesGUID = "";
    private boolean IsSeriesStarted;

    @Override
    public int getLayout() {
        return R.layout.activity_auction_player_stats;
    }


    public static void start(Context context, String seriesId,
                             String playerGUID,
                             String teamId,
                             String s_id,
                             boolean IsSeriesStarted) {
        Intent starter = new Intent(context, AuctionPlayerStatsActivity.class);
        starter.putExtra("seriesGUID", seriesId);
        starter.putExtra("roundId", teamId);
        starter.putExtra("playerGUID", playerGUID);
        starter.putExtra("seriesId", s_id);
        starter.putExtra("IsSeriesStarted", IsSeriesStarted);
        context.startActivity(starter);
    }


    Map<String, Object> map = new HashMap<String, Object>() {{
        put("WicketKeeper", "WK");
        put("Batsman", "Bat");
        put("AllRounder", "AR");
        put("Bowler", "Bowl");

    }};

    @Override
    public void init() {
        mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        mInteractor = new UserInteractor();
        if (AppSession.getInstance().getPlayMode() == 1) {
            ctvImageType.setText("Auction");
        } else {
            ctvImageType.setText("Gully Fantasy");
        }

        if (getIntent().hasExtra("IsSeriesStarted")) {
            IsSeriesStarted = getIntent().getBooleanExtra("IsSeriesStarted", false);
        }

        if (getIntent().hasExtra("seriesGUID")) {
            seriesGUID = getIntent().getStringExtra("seriesGUID");
        }
        if (getIntent().hasExtra("roundId")) {
            roundId = getIntent().getStringExtra("roundId");
        }

        if (getIntent().hasExtra("seriesId")) {
            seriesId = getIntent().getStringExtra("seriesId");
        }

        if (getIntent().hasExtra("playerGUID")) {
            playerGUID = getIntent().getStringExtra("playerGUID");
        }
        getPlayer();


    }

    private void getPlayer() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            PlayerFantasyStatsInput mPlayerFantasyStatsInput = new PlayerFantasyStatsInput();
            mPlayerFantasyStatsInput.setPlayerGUID(playerGUID);
            mPlayerFantasyStatsInput.setSeriesGUID(seriesGUID);
            mPlayerFantasyStatsInput.setSeriesID(seriesId);
            if (AppSession.getInstance().getPlayMode()==2) {
                mPlayerFantasyStatsInput.setMatchGUID(roundId);
            }else {
                mPlayerFantasyStatsInput.setRoundID(roundId);
            }
            mPlayerFantasyStatsInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            mPlayerFantasyStatsInput.setParams("TeamFlagLocal,TeamFlagVisitor,MatchLocation," +
                    "PlayerBattingStyle,PlayerBowlingStyle" +
                    ",PlayerID,PlayerRole,PlayerPic,TeamNameShort,PlayerCountry," +
                    "MatchType,MatchNo,MatchDateTime,SeriesName,TeamGUID,PointsData,TotalPoints");

            mInteractor.getSingleAuctionPlayers(mPlayerFantasyStatsInput, new IUserInteractor.OnGetSingleAuctionPlayersPointListener() {

                @Override
                public void onSuccess(SinglePlayerData playersOutput) {
                    mProgressDialog.dismiss();
                    if (playersOutput.getData() != null) {
                        setPlayerData(playersOutput);
                    }
                }

                private void setPlayerData(SinglePlayerData playersOutput) {
                    ctv_player_name.setText(playersOutput.getData().getPlayerName());
                   // ctv_total.setText(playersOutput.getData().getTotalPoints());
                    teamName.setText(playersOutput.getData().getTeamNameShort().trim().replace(" ", "") + " - " + map.get(playersOutput.getData().getPlayerRole()));
                    mSimpleDraweeView.setImageURI(playersOutput.getData().getPlayerPic());


                    mViewPagerAdapter = new BasePagerAdapter(getSupportFragmentManager());
                    if (!IsSeriesStarted) {
                        mViewPagerAdapter.addFrag(AuctionPlayerStatsFragment.getInstance(playersOutput.getData().getPlayerGUID(), seriesGUID), "PLAYER STATS", 0);
                        mViewPagerAdapter.addFrag(AuctionPlayerPointsFragment.getInstance(playersOutput.getData().getPlayerGUID(), seriesId, roundId), "PLAYER POINTS", 1);

                    } else {
                        mViewPagerAdapter.addFrag(AuctionPlayerPointsFragment.getInstance(playersOutput.getData().getPlayerGUID(), seriesId, roundId), "PLAYER POINTS", 1);
                        mViewPagerAdapter.addFrag(AuctionPlayerStatsFragment.getInstance(playersOutput.getData().getPlayerGUID(), seriesGUID), "PLAYER STATS", 0);
                    }
                    mViewPager.setAdapter(mViewPagerAdapter);
                    mTabLayout.setupWithViewPager(mViewPager);
                    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                    AppUtils.applyFontTabLayoutAuction(AuctionPlayerStatsActivity.this, mTabLayout);
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new com.mw.fantasy.dialog.AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    getPlayer();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
                }


            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new com.mw.fantasy.dialog.AlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new com.mw.fantasy.dialog.AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            getPlayer();

                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }
    }


    public void setPlayerPoint(String point){
        ctv_total.setText(point);
    }
}
