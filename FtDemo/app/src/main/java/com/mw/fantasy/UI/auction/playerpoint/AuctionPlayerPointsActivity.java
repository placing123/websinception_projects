package com.mw.fantasy.UI.auction.playerpoint;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.SeriesInfoUtil;
import com.mw.fantasy.UI.createTeam.CreateTeamPresenterImpl;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.base.Loader;
import com.mw.fantasy.beanInput.PlayersInput;
import com.mw.fantasy.beanOutput.PlayersOutput;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;
import com.mw.fantasy.utility.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

import static com.mw.fantasy.UI.auction.auctionSeriesListing.AuctionSeriesListingFragment.FIXTURE;


public class AuctionPlayerPointsActivity extends BaseActivity {


    RecyclerView mRecyclerView;
    Context mContext;
    ProgressDialog mProgressDialog;
    AcutionPlayerPointsAdapter mPlayerPointsAdapter;

    List<PlayersOutput.DataBean.RecordsBean> responseBeanList;
    PlayersOutput.DataBean.RecordsBean livePlayerStatusData = new PlayersOutput.DataBean.RecordsBean();
    PlayersOutput.DataBean.RecordsBean mPlayerPointsBreakup;
    @BindString(R.string.player_points)
    String title;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @BindView(R.id.ctv_game_type)
    CustomTextView ctvImageType;

    @BindView(R.id.playerText)
    CustomTextView playerText;

    @BindView(R.id.points)
    CustomTextView mPoints;

    @BindView(R.id.selected_by)
    CustomTextView mSelectedBy;


    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;


    String seriesId, matchId;
    String roundId = "";
    private Loader loader;
    private int flag;

    private IUserInteractor mInteractor;

    private CreateTeamPresenterImpl presenterImpl;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {


            livePlayerStatusData = responseBeanList.get(position);


         //    AppUtils.showToast(mContext, "Player Details");


            AuctionPlayerStatsActivity.start(mContext,seriesId,responseBeanList.get(position).getPlayerGUID(),roundId,s_id, seriesStatus==FIXTURE?false:true);

        }
    };
    private Call<PlayersOutput> getAuctionSeriesCall;
    private String seriesName, seriesDeadLine;
    private int seriesStatus;
    private String s_id = "" ;

    public static void start(Context context, int flag, String seriesId, String matchId, String roundId, String seriesName, String seriesDeadLine, int seriesStatus, String seriesID) {
        Intent starter = new Intent(context, AuctionPlayerPointsActivity.class);
        starter.putExtra("flag", flag);
        starter.putExtra("seriesId", seriesId);
        starter.putExtra("matchId", matchId);
        starter.putExtra("roundId", roundId);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("S_id", seriesID);

        context.startActivity(starter);
    }


    @OnClick(R.id.iv_change_mode)
    void onBackClick() {
        finish();
    }

    @OnClick(R.id.selected_by)
    void onSelectedBy() {

        playerText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        mPoints.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        if (mSelectedBy.isSelected()) {
            mPlayerPointsAdapter.shotBySelectedpercentage(true);
            mSelectedBy.setSelected(false);

            mSelectedBy.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_up, 0);

        } else {

            mPlayerPointsAdapter.shotBySelectedpercentage(false);
            mSelectedBy.setSelected(true);
            mSelectedBy.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_down, 0);
        }
    }

    @OnClick(R.id.points)
    void onPoints() {

        playerText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        mSelectedBy.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        if (mPoints.isSelected()) {
            mPlayerPointsAdapter.shotByPoint(true);
            mPoints.setSelected(false);
            mPoints.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_up, 0);

        } else {
            mPlayerPointsAdapter.shotByPoint(false);
            mPoints.setSelected(true);
            mPoints.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_down, 0);
        }

    }

    @OnClick(R.id.playerText)
    void onPlayerSelected() {
        mPoints.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        mSelectedBy.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        if (playerText.isSelected()) {
            mPlayerPointsAdapter.shotByName(true);
            playerText.setSelected(false);

            playerText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_up, 0);

        } else {
            mPlayerPointsAdapter.shotByName(false);
            playerText.setSelected(true);

            playerText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_down, 0);
        }

    }

    @Override
    public int getLayout() {
        return R.layout.activity_auction_player_points;
    }

    @Override
    public void init() {
        mContext = this;
        mPlayerPointsBreakup = new PlayersOutput.DataBean.RecordsBean();

        mInteractor = new UserInteractor();
        loader = new Loader(this);
        mProgressDialog = new ProgressDialog(mContext);

        flag = getIntent().getIntExtra("flag",1);

        if (getIntent().hasExtra("matchId")) {


            matchId = getIntent().getStringExtra("matchId");
            seriesId = getIntent().getStringExtra("seriesId");
        }
        if (getIntent().hasExtra("roundId")) {
            roundId = getIntent().getStringExtra("roundId");
        }
        seriesName = getIntent().getExtras().getString("seriesName");
        seriesDeadLine = getIntent().getExtras().getString("seriesDeadLine");
        seriesStatus = getIntent().getExtras().getInt("seriesStatus");
        s_id = getIntent().getExtras().getString("S_id");


        if (AppSession.getInstance().getPlayMode() == 1){
            ctvImageType.setText("Auction");
        }else {
            ctvImageType.setText("Gully Fantasy");
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        responseBeanList = new ArrayList<>();
        mPlayerPointsAdapter = new AcutionPlayerPointsAdapter(mContext, responseBeanList, onItemClickCallback);
        mRecyclerView.setAdapter(mPlayerPointsAdapter);
        apiCallingForAuction();

        new SeriesInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName, seriesDeadLine, seriesStatus).start();

    }


    private void apiCallingForAuction() {
        responseBeanList.clear();
        mPlayerPointsAdapter.notifyDataSetChanged();
        if (!NetworkUtils.isNetworkConnected(mContext)) {

            loader.hide();
            loader.error(AppUtils.getStrFromRes(R.string.network_error));
            loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apiCallingForAuction();
                }
            });
        } else {
            //loader.start();
            mProgressDialog.show();
            PlayersInput mPlayersInput = new PlayersInput();
            mPlayersInput.setSeriesGUID(seriesId);

            mPlayersInput.setContestGUID(matchId);
            mPlayersInput.setIsPlaying("Yes");
            mPlayersInput.setParams(Constant.PLAYER_STATE_PARAMS);
            mPlayersInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            mPlayersInput.setCustomOrderBy("PointCredits");
            mPlayersInput.setSequence("DESC");
            if (flag==2) {
                mPlayersInput.setMatchGUID(roundId);
            }else {
                mPlayersInput.setRoundID(roundId);
            }
            mPlayersInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            getAuctionSeriesCall = mInteractor.getAuctionPlayers(mPlayersInput, new IUserInteractor.OnGetAuctionPlayersPointListener() {
                @Override
                public void onSuccess(PlayersOutput getAuctionSeriesOutput) {
                    if (getAuctionSeriesCall == null || getAuctionSeriesCall.isCanceled()) {
                        return;
                    }
                    mProgressDialog.dismiss();


                    // loader.hide();
                    if (getAuctionSeriesOutput.getData().getRecords() != null && getAuctionSeriesOutput.getData().getRecords().size() != 0) {
                        responseBeanList = getAuctionSeriesOutput.getData().getRecords();

                        mPlayerPointsAdapter.addAllItem(responseBeanList);
                    } else {
                        loader.setNotFoundImage(getResources().getDrawable(R.drawable.ic_trophy));
                        loader.dataNotFound("No player stats found.");

                    }
                }


                @Override
                public void onError(String errorMsg) {
                    if (getAuctionSeriesCall == null || getAuctionSeriesCall.isCanceled()) {
                        return;
                    }
                    mProgressDialog.dismiss();


                    // loader.hide();
                    loader.error(errorMsg);
                    loader.getTryAgainView().setVisibility(View.GONE);
                    loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                    loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            apiCallingForAuction();
                        }
                    });


                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
