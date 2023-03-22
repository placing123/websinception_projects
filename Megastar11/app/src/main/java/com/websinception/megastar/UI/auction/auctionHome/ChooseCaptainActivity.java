package com.websinception.megastar.UI.auction.auctionHome;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.playerpoint.AuctionInfoUtil;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseCaptainActivity extends BaseActivity {

    public ChooseCaptainAdapter adapter;
    @BindView(R.id.main_frag)
    RelativeLayout relativeLayoutMain;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.ctv_title)
    CustomTextView customTextViewTITLE;
    @BindView(R.id.ctv_description)
    CustomTextView customTextViewDESCRIPTION;
    @BindView(R.id.ctv_next)
    CustomTextView customTextViewNext;
    ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> response;
    String loginSessionKey = "";
    String userId = "";
    private String roundId;

    List<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayersWK = new ArrayList<>();
    List<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayersBAT = new ArrayList<>();
    List<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayersAR = new ArrayList<>();
    List<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayersBOWL = new ArrayList<>();


    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;

    @OnClick(R.id.iv_change_mode)
    public void back() {
        finish();
    }



    private LinearLayoutManager layoutManager;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    private String seriesName;
    private String auctionStartTime,auctionStatus;
    private String seriesDeadLine;
    private int seriesStatus;

    private OnItemClickListener.OnItemClickCallback onCaptionItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, final int position) {
            adapter.setCaption(position);
            setNextButton();
        }
    };
    private OnItemClickListener.OnItemClickCallback onViceCaptionItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            adapter.setViceCaption(position);

            setNextButton();
        }
    };

    private OnItemClickListener.OnItemClickCallback onItemClickCallBack = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            Gson gson = new Gson();
            String s = gson.toJson(adapter.getItemData(position));
//            AuctionPlayerStatsActivity.start(mContext,seriesId,s,teamId,s_id);
        }
    };





    @OnClick(R.id.ctv_next)
    public void saveTeam(View view) {

        Intent intent = new Intent();
        intent.putExtra("captain", adapter.getCaptain());
        intent.putExtra("vice_captain", adapter.getViceCaptain());
        setResult(RESULT_OK, intent); // You can also send result without any data using setResult(int resultCode)
        finish();


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int getLayout() {
        return R.layout.choose_captain_fragment;
    }

    @Override
    public void init() {
        mContext = this;

        if (AppSession.getInstance().getLoginSession() != null) {
            loginSessionKey = AppSession.getInstance().getLoginSession().getData().getSessionKey();
            userId = AppSession.getInstance().getLoginSession().getData().getUserID();
        }

        if (getIntent().hasExtra("data")) {
            response = new Gson().fromJson(getIntent().getStringExtra("data"),
                    new TypeToken<ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean>>() {
            }.getType());

            Log.d("dataResponce", AppUtils.gsonToJSON(response));
        }

        auctionStartTime = getIntent().getStringExtra("auctionStartTime");
        auctionStatus = getIntent().getStringExtra("auctionStatus");
        roundId = getIntent().getStringExtra("roundId");
        seriesName = getIntent().getStringExtra("seriesName");
        seriesDeadLine = getIntent().getStringExtra("seriesDeadLine");
        seriesStatus = getIntent().getIntExtra("seriesStatus", 0);

        new AuctionInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName,
                auctionStartTime,auctionStatus

                ).start();

    /*    new SeriesInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus,
                seriesName,
                seriesDeadLine,
                seriesStatus).start();*/
        getPlayers();

        GetAuctionPlayerOutput.DataBean dataBean = new GetAuctionPlayerOutput.DataBean();
        List<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayers = new ArrayList<>();
        if (responseMatchPlayersWK.size() > 0) {
            addHeadingOfPlayers(responseMatchPlayers, AppUtils.getStrFromRes(R.string.wicket_keeper));
            responseMatchPlayers.addAll(getSelectedPlayersData(responseMatchPlayersWK));
        }
        if (responseMatchPlayersBAT.size() > 0) {
            addHeadingOfPlayers(responseMatchPlayers, AppUtils.getStrFromRes(R.string.batsmen));
            responseMatchPlayers.addAll(getSelectedPlayersData(responseMatchPlayersBAT));
        }
        if (responseMatchPlayersAR.size() > 0) {
            addHeadingOfPlayers(responseMatchPlayers, AppUtils.getStrFromRes(R.string.all_rounders));
            responseMatchPlayers.addAll(getSelectedPlayersData(responseMatchPlayersAR));
        }
        if (responseMatchPlayersBOWL.size() > 0) {
            addHeadingOfPlayers(responseMatchPlayers, AppUtils.getStrFromRes(R.string.bowlers));
            responseMatchPlayers.addAll(getSelectedPlayersData(responseMatchPlayersBOWL));
        }
        dataBean.setRecords(responseMatchPlayers);



        mRecyclerView.setNestedScrollingEnabled(true);
        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        String text = "Choose CAPTAIN <font color='#DA473D'>(C)</font> & Vice CAPTAIN <font color='#DA473D'>(VC)</font>";
        customTextViewDESCRIPTION.setText(Html.fromHtml(text));
        if (dataBean != null) {
            adapter = new ChooseCaptainAdapter(this, roundId,dataBean.getRecords(), onCaptionItemClickCallback, onViceCaptionItemClickCallback,onItemClickCallBack);
            mRecyclerView.setAdapter(adapter);
        }
        setNextButton();

    }

    private void getPlayers() {
        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : response) {


            switch (record.getPlayerRole()) {
                case Constant.ROLE_WICKETKEEPER:
                    responseMatchPlayersWK.add(record);
                    break;
                case Constant.ROLE_BATSMAN:
                    responseMatchPlayersBAT.add(record);
                    break;
                case Constant.ROLE_ALLROUNDER:
                    responseMatchPlayersAR.add(record);
                    break;
                case Constant.ROLE_BOWLER:
                    responseMatchPlayersBOWL.add(record);
                    break;
            }
        }
    }


    private void setNextButton() {

        Log.i("CaptionAndVoice", "isCaptionAndVoiceCaptionExist:" + adapter.isCaptionAndVoiceCaptionExist());


        if (adapter.isCaptionAndVoiceCaptionExist()) {
            customTextViewNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_next_bg_green));
            customTextViewNext.setTextColor(getResources().getColor(R.color.black));
            customTextViewNext.setEnabled(true);
        } else {
            customTextViewNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_next_bg_white));
            customTextViewNext.setTextColor(getResources().getColor(R.color.white));
            customTextViewNext.setEnabled(false);
        }
    }


    private void addHeadingOfPlayers(List<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayers, String type) {
        GetAuctionPlayerOutput.DataBean.RecordsBean bean = new GetAuctionPlayerOutput.DataBean.RecordsBean();
        bean.setViewType(1);
        bean.setPlayerName(type);
        responseMatchPlayers.add(bean);
    }

    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> getSelectedPlayersData(List<GetAuctionPlayerOutput.DataBean.RecordsBean> response) {
        List<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayers = new ArrayList<>();
        for (int i = 0; i < response.size(); i++) {
            if (response.get(i).getAuctionPlayingPlayer().equals("Yes"))
                responseMatchPlayers.add(response.get(i));
        }
        return responseMatchPlayers;
    }
 /*   @Override
    public void onSaveError(String value) {
        // Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();

        AppUtils.showToast(mContext, value);
    }

    @Override
    public void onSaveSuccess(LoginResponseOut response) {

        hideLoading();

        if (!contestId.equals("0")) {
            teamId = response.getData().getUserTeamGUID() + "";

            if (!TextUtils.isEmpty(contestId)) {
                JoinContestActivityStart(mContext, matchId, teamId, contestId, joiningAmount, chip, userInviteCode);
            } else if (!TextUtils.isEmpty(NEED)) {
                if (NEED.equals("JOIN_CONTEST")) {
                   // CreateContestActivityStart(mContext, seriesId, matchId, teamId, localteamId, visitorteamId);
                }
            }

            Intent intent = new Intent(MatchContestActivity.class.getSimpleName());
            intent.putExtra("KEY", "REFRESH");
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

            // Toast.makeText(mContext, response.getMessage(), Toast.LENGTH_SHORT).show();

            AppUtils.showToast(mContext, response.getMessage());
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        } else {

        }
    }



    @Override
    public void showLoading() {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public boolean isLayoutAdded() {
        return (isAdded() && getActivity() != null);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }

    @Override
    public void onMatchSuccess(MatchDetailOutPut mMatchDetailOutPut) {
        if (isAdded() && getActivity() != null) {
            teamsVS.setText(mMatchDetailOutPut.getData().getTeamNameShortLocal()
                    + " " + AppUtils.getStrFromRes(R.string.vs)
                    + " " +mMatchDetailOutPut.getData().getTeamNameShortVisitor());
            if (mMatchDetailOutPut.getData().getStatus() != null) {
                if (customTextViewFullTime != null) {
                    switch (mMatchDetailOutPut.getData().getStatus()) {
                        case Constant.Pending:
                            setTime(mMatchDetailOutPut.getData().getMatchStartDateTime(), mMatchDetailOutPut.getData().getMatchDate(),
                                    mMatchDetailOutPut.getData().getMatchTime(), mMatchDetailOutPut.getData().getCurrentDateTime());

                            break;
                        case Constant.Running:
                            customTextViewFullTime.setText(mMatchDetailOutPut.getData().getStatus());
                            customTextViewFullTime.setTextColor(getResources().getColor(R.color.yellow));
                            break;
                        case Constant.Completed:
                            customTextViewFullTime.setText(mMatchDetailOutPut.getData().getStatus());
                            customTextViewFullTime.setTextColor(getResources().getColor(R.color.green));
                            break;

                        default:
                            customTextViewFullTime.setText(mMatchDetailOutPut.getData().getStatus());

                            break;
                    }
                }
            }
        }
    }

    CountDownTimer countDownTimer;
    public void setTime(String matchDateTime, final String matchDate, final String matchTime, String currentTime) {
        customTextViewFullTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_time, 0, 0, 0);
        try {
            if (countDownTimer != null) countDownTimer.cancel();
            if (customTextViewFullTime != null) {

                long remainingTime = TimeUtils.getTimeDifference(matchDateTime,
                        currentTime);

                //ctv_timer.setPaintFlags(ctv_timer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                if (TimeUtils.isThisDateValid(matchDateTime, "yyyy-MM-dd HH:mm:ss")) {
                    if (TimeUnit.MILLISECONDS.toHours(remainingTime) > Constant.SHOW_TIME_LIMIT_HRS) {
                        customTextViewFullTime.setText(TimeUtils.getMatchDateOnly(matchDate));
                    } else {
                        //need to implement counter
                        countDownTimer = new CountDownTimer(remainingTime, TimeUnit.SECONDS.toMillis(1)) {
                            public void onTick(long millisUntilFinished) {
                                if (customTextViewFullTime != null)
                                    customTextViewFullTime.setText(TimeUtils.getRemainsTime(millisUntilFinished));
                            }

                            public void onFinish() {
                                if (customTextViewFullTime != null)
                                    customTextViewFullTime.setText(TimeUtils.getDisplayFullDate1(matchDate, matchTime));
                            }
                        };
                        if (countDownTimer != null) {
                            countDownTimer.start();
                        }
                    }
                } else {
                    customTextViewFullTime.setText(TimeUtils.getMatchDateOnly(matchDate));
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            customTextViewFullTime.setText("N/A");
        }

    }

    @Override
    public void onMatchFailure(String errMsg) {

    }

    @Override
    public void onShowSnackBar(@NonNull String message) {
        // Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        AppUtils.showSnackBar(mContext, relativeLayoutMain, message);
    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode", "requestCode: " + BaseActivity.REQUEST_CODE_CREATE_TEAM);
        if (requestCode == BaseActivity.REQUEST_CODE_CREATE_TEAM && resultCode == RESULT_OK) {
            setResult(Activity.RESULT_OK);
            finish();
        } else if (requestCode == BaseActivity.REQUEST_CODE_JOIN_CONTESTS && resultCode == RESULT_OK) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
