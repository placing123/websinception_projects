package com.mw.fantasy.UI.auction.playerpoint;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanInput.DreamTeamInput;
import com.mw.fantasy.beanOutput.DreamTeamOutput;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;
import com.mw.fantasy.utility.OnItemClickListener;

import butterknife.BindView;
import butterknife.OnClick;

public class AuctionBestTeamActivity extends BaseActivity {


    @BindView(R.id.rv_players)
    RecyclerView mRecyclerView;

    @BindView(R.id.totalPoints)
    CustomTextView totalPoints;

    @BindView(R.id.ctv_game_type)
    CustomTextView ctvImageType;

    private Context mContext;
    private UserInteractor mInteractor;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private String roundId,seriesID;
    private BestTeamPreviewAdapter adapter;
    private int flag;

    public static void start(int flag, Context context, String seriesID, String roundId) {
        Intent starter = new Intent(context, AuctionBestTeamActivity.class);
        starter.putExtra("flag", flag);
        starter.putExtra("seriesID", seriesID);
        starter.putExtra("roundId", roundId);
        context.startActivity(starter);
    }

    @OnClick(R.id.iv_change_mode)
    public void onBack(){
        finish();
    }



    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {


         /*   PlayerPreviewActivity.start(getActivity(), responseMatchPlayersWK.get(position).getPlayerName(),
                    responseMatchPlayersWK.get(position).getPointCredits(), responseMatchPlayersWK.get(position).getTotalPoints(),
                    responseMatchPlayersWK.get(position).getPlayerBattingStyle(), responseMatchPlayersWK.get(position).getPlayerBowlingStyle(),
                    responseMatchPlayersWK.get(position).getPlayerCountry(), responseMatchPlayersWK.get(position).getPlayerPic(),
                    responseMatchPlayersWK.get(position).getSeriesGUID(), responseMatchPlayersWK.get(position).getPlayerGUID(),
                    callback.getMatchID(),responseMatchPlayersWK.get(position).getPlayerSelectedPercent(),callback.getStatus());


            if (callback.isTeamPoints()) {

                setPointsData(responseMatchPlayersWK.get(position));
            } else {
                AppSession.getInstance().playerPoints = null;
            }*/

        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_auction_best_team;
    }

    @Override
    public void init() {
        mContext = this;
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(mContext);

        flag = getIntent().getExtras().getInt("flag");
        roundId = getIntent().getExtras().getString("roundId");
        seriesID = getIntent().getExtras().getString("seriesID");
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        if (flag == 1) {
            ctvImageType.setText("Auction");
        } else {
            ctvImageType.setText("Gully Fantasy");
        }

        apiCallDownloadteams();
    }

    private void apiCallDownloadteams() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            DreamTeamInput downloadTeamInput = new DreamTeamInput();
            downloadTeamInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
//            downloadTeamInput.setContestGUID(contestGUID);
            if (flag==1) {
                downloadTeamInput.setRoundID(roundId);
            }else {
                downloadTeamInput.setMatchGUID(roundId);
            }

            downloadTeamInput.setSeriesID(seriesID);
            mInteractor.auctionallPlayersScore(downloadTeamInput, new IUserInteractor.OnResponseDreamTeamsListener() {


                @Override
                public void onSuccess(DreamTeamOutput responseTeams) {
                   if (responseTeams.getData()!=null) {

                       mProgressDialog.dismiss();
                       totalPoints.setText(responseTeams.getData().getTotalPoints()+"");
                       if (responseTeams.getData().getRecords() != null && responseTeams.getData().getRecords().size() > 0) {
                           adapter = new BestTeamPreviewAdapter(R.layout.best_list_item_players_preview, mContext, responseTeams.getData().getRecords(), onItemClickCallback);
                           mRecyclerView.setAdapter(adapter);
                       }
                   }
                }

                @Override
                public void onNotFound(String error) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            error,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new com.mw.fantasy.dialog.AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    apiCallDownloadteams();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }
                            });
                    mAlertDialog.show();
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
                                    apiCallDownloadteams();
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
                            apiCallDownloadteams();

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
}
