package com.mw.fantasy.UI.draft.playerPoint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanInput.GetDraftPlayerPointInput;
import com.mw.fantasy.beanOutput.GetDraftPlayerPointOutput;
import com.mw.fantasy.dialog.AlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class PlayerPointsActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    @OnClick(R.id.iv_close)
    void closeBtnClick() {
        AppUtils.clickVibrate(this);
        onBackPressed();
    }


    private Context mContext;
    private String roundID,seriesID, playerGUID;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private GetDraftPlayerPointOutput mGetDraftPlayerPointOutput;

    public static void start(Context context, String roundID,String seriesID, String playerGUID) {
        Intent starter = new Intent(context, PlayerPointsActivity.class);
        starter.putExtra("roundID", roundID);
        starter.putExtra("seriesID", seriesID);
        starter.putExtra("playerGUID", playerGUID);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.bottom_activity_enter_1, R.anim.bottom_activity_enter_2);

    }


    @Override
    public int getLayout() {
        return R.layout.activity_player_points2;
    }

    @Override
    public void init() {
        mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        roundID = getIntent().getExtras().getString("roundID");
        seriesID = getIntent().getExtras().getString("seriesID");
        playerGUID = getIntent().getExtras().getString("playerGUID");
        apiCallingGetDraftPlayerPoints();

    }

    private void apiCallingGetDraftPlayerPoints() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();
            GetDraftPlayerPointInput getDraftPlayerPointInput = new GetDraftPlayerPointInput();
            getDraftPlayerPointInput.setPlayerGUID(playerGUID);
            getDraftPlayerPointInput.setRoundID(roundID);
            getDraftPlayerPointInput.setSeriesID(seriesID);
            getDraftPlayerPointInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getDraftPlayerPointInput.setParams("TeamFlagLocal,TeamFlagVisitor,MatchLocation,PlayerBattingStyle,PlayerBowlingStyle,PlayerID,PlayerRole,PlayerPic,PlayerCountry,MatchType,MatchNo,MatchDateTime,SeriesName,TeamGUID,PointsData,TotalPoints");
            new UserInteractor().getDraftPlayersPoint(getDraftPlayerPointInput, new IUserInteractor.OnGetDraftPlayersPointListener() {
                @Override
                public void onSuccess(GetDraftPlayerPointOutput getDraftPlayerPointOutput) {
                    mGetDraftPlayerPointOutput = getDraftPlayerPointOutput;
                    mProgressDialog.dismiss();
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.setAdapter(new PointRecyclerAdapter(mGetDraftPlayerPointOutput));
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
                            onBackPressed();
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
                    onBackPressed();
                }
            });
            mAlertDialog.show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.bottom_activity_exit_1, R.anim.bottom_activity_exit_2);
    }
}
