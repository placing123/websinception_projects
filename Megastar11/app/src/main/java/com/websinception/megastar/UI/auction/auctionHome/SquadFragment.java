package com.websinception.megastar.UI.auction.auctionHome;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.GetAuctionPlayerInput;
import com.websinception.megastar.beanInput.SubmitAuctionsPlayersInput;
import com.websinception.megastar.beanOutput.DefaultRespose;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.AlertDialog;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class SquadFragment extends BaseFragment {

    private String contestGUID, roundId,auctionStartTime, userTeamGUID, auctionStatus, seriesId, userGUID,userName;
    private Loader loader;
    private boolean isSeriesStarted;
    private IUserInteractor mInteractor;
    private Call<GetAuctionPlayerOutput> auctionPlayersCall;
    private SquadListAdapter mSquadListAdapter;
    private GetAuctionPlayerOutput mGetAuctionPlayerOutput;
    private boolean isMySquad;

    @BindView(R.id.ctv_select_label)
    CustomTextView mCustomTextViewSelectLabel;

    @BindView(R.id.ctv_btn_submit_players)
    CustomTextView mCustomTextViewSubmitBtn;

    @BindView(R.id.rv_squad)
    RecyclerView mRecyclerViewSquad;

    @BindView(R.id.rl_bottom_tab)
    RelativeLayout mRelativeLayoutBottomTab;


    @BindView(R.id.ll_data_root)
    LinearLayout mLinearLayoutDataRoot;

    @BindView(R.id.ctv_value_label)
    CustomTextView mCustomTextViewValueLabel;

    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;



/*
    private SelectCaptainDialog mSelectCaptainDialog;
*/
    private SelectCaptainDialog.OnSubmitClickListener mOnSubmitClickListener = new SelectCaptainDialog.OnSubmitClickListener() {
        @Override
        public void onClick(String c_GUID, String v_GUID) {
            ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> selectedPlayers = mSquadListAdapter.getSelectedPlayers();
            if (c_GUID.equals("")) {
                Toast.makeText(getActivity(), "Please select Captain.", Toast.LENGTH_SHORT).show();
            } else if (c_GUID.equals(v_GUID)) {
                Toast.makeText(getActivity(), "Captain and vice captain can't be same.", Toast.LENGTH_SHORT).show();
            } else if (selectedPlayers.size() == 1) {
               // mSelectCaptainDialog.hide();
                // call api
                apiCallSubmitPlayers(selectedPlayers, c_GUID, v_GUID);
            } else {
                if (v_GUID.equals("")) {
                    Toast.makeText(getActivity(), "Please select Vice Captain.", Toast.LENGTH_SHORT).show();
                } else {
                    if (selectedPlayers.size() > 11) {
                        Toast.makeText(getActivity(), "You can't submit more than 11 players.", Toast.LENGTH_SHORT).show();
                    } else {
                      //  mSelectCaptainDialog.hide();
                        apiCallSubmitPlayers(selectedPlayers, c_GUID, v_GUID);
                    }
                }
            }
        }
    };
    private String seriesName;
    private String seriesDeadLine;
    private int seriesStatus;

    @OnClick(R.id.ctv_btn_submit_players)
    void onSubmitPlayerBtnClick() {
        AppUtils.clickVibrate(getActivity());
        if (mSquadListAdapter != null) {
            ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> selectedPlayers = mSquadListAdapter.getSelectedPlayers();
            if (selectedPlayers.size() != 0) {
                if (selectedPlayers.size()>11){
                    Toast.makeText(getActivity(), "You can't submit more than 11 players.", Toast.LENGTH_SHORT).show();
                }else {
                    start(getActivity(),new Gson().toJson(selectedPlayers),seriesName, seriesDeadLine, seriesStatus);

                }

                /*mSelectCaptainDialog = new SelectCaptainDialog(getActivity(), mOnSubmitClickListener, selectedPlayers);
                mSelectCaptainDialog.show();
                mSelectCaptainDialog.setPreselection();*/
            } else {
                AppUtils.showToast(getActivity(),"Please add players first.");
            }
        }
    }


    public void start(Context context, String data, String seriesName, String seriesDeadLine, int seriesStatus) {
        Intent starter = new Intent(context, ChooseCaptainActivity.class);
        starter.putExtra("data", data);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("roundId", roundId);
        starter.putExtra("auctionStatus", auctionStatus);
        starter.putExtra("auctionStartTime", auctionStartTime);
        getActivity().startActivityForResult(starter,22);
    }

    public static SquadFragment newInstance(
            String userGUID,
            String roundId,
            String userName,
            String seriesId,
            String contestGUID,
            String auctionStatus,
            boolean isSeriesStarted, String seriesName,
            String seriesDeadLine, int seriesStatus,
            String auctionStartTime) {
        Bundle args = new Bundle();
        args.putString("userGUID", userGUID);
        args.putString("roundId", roundId);
        args.putString("userName", userName);
        args.putString("seriesId", seriesId);
        args.putString("contestGUID", contestGUID);
        args.putString("auctionStatus", auctionStatus);
        args.putString("auctionStatus", auctionStatus);
        args.putBoolean("isSeriesStarted", isSeriesStarted);
        args.putString("seriesName", seriesName);
        args.putString("seriesDeadLine", seriesDeadLine);
        args.putInt("seriesStatus", seriesStatus);
        args.putString("auctionStartTime", auctionStartTime);

        SquadFragment fragment = new SquadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_squad;
    }

    @Override
    public void init() {
        auctionStartTime = getArguments().getString("auctionStartTime");
        roundId = getArguments().getString("roundId");
        seriesId = getArguments().getString("seriesId");
        contestGUID = getArguments().getString("contestGUID");
        auctionStatus = getArguments().getString("auctionStatus");
        userGUID = getArguments().getString("userGUID");
        userName = getArguments().getString("userName");
        isSeriesStarted = getArguments().getBoolean("isSeriesStarted");
        seriesName = getArguments().getString("seriesName");
        seriesDeadLine = getArguments().getString("seriesDeadLine");
        seriesStatus = getArguments().getInt("seriesStatus");
        isMySquad = userGUID.equals(AppSession.getInstance().getLoginSession().getData().getUserGUID());
        loader = new Loader(getCurrentView());
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(getActivity());
        getData();
    }

    private void getData() {
        mLinearLayoutDataRoot.setVisibility(View.GONE);
        mCustomTextViewValueLabel.setVisibility(View.GONE);
        if (NetworkUtils.isConnected(getActivity())) {
            loader.start();
            final GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            getAuctionPlayerInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setRoundID(roundId);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            //getAuctionPlayerInput.setParams("PlayerPosition,AuctionTopPlayerSubmitted,PlayerID,PlayerRole,PlayerPic,BidCredit,UserTeamGUID,UserTeamName,IsAssistant");
            getAuctionPlayerInput.setParams("AuctionPlayingPlayer,PlayerBattingStyle,PlayerBowlingStyle,PlayerBattingStats,PlayerBowlingStats,TeamNameShort,PlayerPosition,AuctionTopPlayerSubmitted,PlayerID,PlayerRole,PlayerPic,BidCredit,UserTeamGUID,UserTeamName,IsAssistant,SeriesGUID,SeriesID");
            getAuctionPlayerInput.setIsAssistant("No");
            getAuctionPlayerInput.setIsPreTeam("No");
//            getAuctionPlayerInput.setSeriesGUID(seriesId);
            getAuctionPlayerInput.setMySquadPlayer("Yes");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            auctionPlayersCall = mInteractor.getAuctionPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    if (auctionPlayersCall == null || auctionPlayersCall.isCanceled()) {
                        return;
                    }
                    loader.hide();
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        mLinearLayoutDataRoot.setVisibility(View.VISIBLE);
                        userTeamGUID = getAuctionPlayerOutput.getData().getUserTeamGUID();
                        mGetAuctionPlayerOutput = getAuctionPlayerOutput;
                        mRecyclerViewSquad.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        mSquadListAdapter = new SquadListAdapter(
                                SquadFragment.this,
                                roundId,seriesId,
                                auctionStatus, getAuctionPlayerOutput,seriesStatus);
                        mRecyclerViewSquad.setAdapter(mSquadListAdapter);
                        if (isSeriesStarted()) {
                            mCustomTextViewSelectLabel.setVisibility(View.GONE);
                            mRelativeLayoutBottomTab.setVisibility(View.GONE);
                        }else {
                            if (isMySquad()) {
                                if (mGetAuctionPlayerOutput.getData().getAuctionTopPlayerSubmitted().equals("Yes")) {
                                    mCustomTextViewSelectLabel.setText("Select");
                                    mCustomTextViewSubmitBtn.setText("UPDATE TEAM");
                                    mRelativeLayoutBottomTab.setVisibility(View.VISIBLE);
                                } else {
                                    if (auctionStatus.equals("Completed")) {
                                        mCustomTextViewSelectLabel.setText("Select");
                                        mCustomTextViewSubmitBtn.setText("SUBMIT TEAM");
                                        mRelativeLayoutBottomTab.setVisibility(View.VISIBLE);
                                    } else {
                                        mCustomTextViewSelectLabel.setVisibility(View.GONE);
                                        mRelativeLayoutBottomTab.setVisibility(View.GONE);
                                    }
                                }
                            }else {
                                mCustomTextViewSelectLabel.setVisibility(View.GONE);
                                mRelativeLayoutBottomTab.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        mCustomTextViewValueLabel.setVisibility(View.VISIBLE);
                        if (auctionStatus.equals("Completed")) {
                            mCustomTextViewValueLabel.setText("Oops! it seems that you haven't purchased any player during auction.");
                        }else if (auctionStatus.equals("Running")){
                            mCustomTextViewValueLabel.setText("Auction is running! No player purchased yet.");
                        }else {
                            mCustomTextViewValueLabel.setText("Hey, auction not started yet. Once the auction starts you can place bid on your desire player and all your purchased player will be listed here.");
                        }
                        mCustomTextViewSelectLabel.setVisibility(View.VISIBLE);
                        loader.hide();
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (auctionPlayersCall == null || auctionPlayersCall.isCanceled()) {
                        return;
                    }
                    loader.hide();
                    loader.error(errorMsg);
                    loader.getTryAgainView().setVisibility(View.GONE);
                    loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                    loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
                }
            });
        } else {
            loader.hide();
            loader.error(AppUtils.getStrFromRes(R.string.network_error));
            loader.getTryAgainView().setVisibility(View.GONE);
            loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData();
                }
            });
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (auctionPlayersCall != null) {
            auctionPlayersCall.cancel();
            auctionPlayersCall = null;
        }

    }


    private void apiCallSubmitPlayers(final ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> selectedPlayers, final String c_GUID, final String v_GUID) {
        if (NetworkUtils.isConnected(getActivity())) {
            mProgressDialog.show();
            SubmitAuctionsPlayersInput submitAuctionsPlayersInput = new SubmitAuctionsPlayersInput();
            submitAuctionsPlayersInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            submitAuctionsPlayersInput.setRoundID(roundId);
            submitAuctionsPlayersInput.setSeriesID(seriesId);
            submitAuctionsPlayersInput.setUserTeamGUID(userTeamGUID);
            ArrayList<SubmitAuctionsPlayersInput.UserTeamPlayersBean> userTeamPlayersBeans = new ArrayList<>();
            for (GetAuctionPlayerOutput.DataBean.RecordsBean selectedPlayer : selectedPlayers) {
                SubmitAuctionsPlayersInput.UserTeamPlayersBean tem = new SubmitAuctionsPlayersInput.UserTeamPlayersBean();
                tem.setPlayerGUID(selectedPlayer.getPlayerGUID());
                tem.setPlayerName(selectedPlayer.getPlayerName());
                tem.setPlayerID(selectedPlayer.getPlayerID());
                tem.setBidCredit(selectedPlayer.getBidCredit());
                if (selectedPlayer.getPlayerGUID().equals(c_GUID)) {
                    tem.setPlayerPosition("Captain");
                } else if (selectedPlayer.getPlayerGUID().equals(v_GUID)) {
                    tem.setPlayerPosition("ViceCaptain");
                } else {
                    tem.setPlayerPosition("Player");
                }
                //auctionDraftTemchanges
                userTeamPlayersBeans.add(tem);
            }
            submitAuctionsPlayersInput.setUserTeamPlayers(userTeamPlayersBeans);
            mInteractor.submitAuctionsPlayers(submitAuctionsPlayersInput, new IUserInteractor.OnSubmitAuctionsPlayersListener() {
                @Override
                public void onSuccess(DefaultRespose defaultRespose) {
                    mProgressDialog.dismiss();
                    getData();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(getActivity(),
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            apiCallSubmitPlayers(selectedPlayers, c_GUID, v_GUID);
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
            mAlertDialog = new AlertDialog(getActivity(),
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                @Override
                public void onYesClick() {
                    mAlertDialog.hide();
                    apiCallSubmitPlayers(selectedPlayers, c_GUID, v_GUID);
                }

                @Override
                public void onNoClick() {
                    mAlertDialog.hide();
                }
            });
            mAlertDialog.show();
        }
    }

    public boolean isMySquad() {
        return isMySquad;
    }

    public boolean isSeriesStarted() {
        return isSeriesStarted;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)return;

        if (data.hasExtra("captain")&&data.hasExtra("vice_captain")){
          String  c_GUID = data.getStringExtra("captain") ;
          String  v_GUID = data.getStringExtra("vice_captain") ;

            ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> selectedPlayers = mSquadListAdapter.getSelectedPlayers();
            if (c_GUID.equals("")) {
                Toast.makeText(getActivity(), "Please select Captain.", Toast.LENGTH_SHORT).show();
            } else if (c_GUID.equals(v_GUID)) {
                Toast.makeText(getActivity(), "Captain and vice captain can't be same.", Toast.LENGTH_SHORT).show();
            } else if (selectedPlayers.size() == 1) {
            //    mSelectCaptainDialog.hide();
                // call api
                apiCallSubmitPlayers(selectedPlayers, c_GUID, v_GUID);
            } else {
                if (v_GUID.equals("")) {
                    Toast.makeText(getActivity(), "Please select Vice Captain.", Toast.LENGTH_SHORT).show();
                } else {
                    if (selectedPlayers.size() > 16) {
                        Toast.makeText(getActivity(), "You can't submit more than 16 players.", Toast.LENGTH_SHORT).show();
                    } else {
                      //  mSelectCaptainDialog.hide();
                        apiCallSubmitPlayers(selectedPlayers, c_GUID, v_GUID);
                    }
                }
            }

        }
    }
}
