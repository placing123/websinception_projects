package com.websinception.megastar.UI.auction.auctionHome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
public class CompletedSquadFragment extends BaseFragment {

    private String contestGUID, roundId, userTeamGUID, auctionStatus, seriesId, userGUID,userName;
    private Loader loader;
    private boolean isSeriesStarted;
    private IUserInteractor mInteractor;
    private Call<GetAuctionPlayerOutput> auctionPlayersCall;
    private CompletedSquadListAdapter mSquadListAdapter;
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
    private SelectCaptainDialog mSelectCaptainDialog;
    private SelectCaptainDialog.OnSubmitClickListener mOnSubmitClickListener = new SelectCaptainDialog.OnSubmitClickListener() {
        @Override
        public void onClick(String c_GUID, String v_GUID) {
            ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> selectedPlayers = mSquadListAdapter.getSelectedPlayers();
            if (c_GUID.equals("")) {
                Toast.makeText(getActivity(), "Please select Captain.", Toast.LENGTH_SHORT).show();
            } else if (c_GUID.equals(v_GUID)) {
                Toast.makeText(getActivity(), "Captain and vice captain can't be same.", Toast.LENGTH_SHORT).show();
            } else if (selectedPlayers.size() == 1) {
                mSelectCaptainDialog.hide();
                // call api
                apiCallSubmitPlayers(selectedPlayers, c_GUID, v_GUID);
            } else {
                if (v_GUID.equals("")) {
                    Toast.makeText(getActivity(), "Please select Vice Captain.", Toast.LENGTH_SHORT).show();
                } else {
                    if (selectedPlayers.size() > 16) {
                        Toast.makeText(getActivity(), "You can't submit more than 16 players.", Toast.LENGTH_SHORT).show();
                    } else {
                        mSelectCaptainDialog.hide();
                        apiCallSubmitPlayers(selectedPlayers, c_GUID, v_GUID);
                    }
                }
            }
        }
    };

    @OnClick(R.id.ctv_btn_submit_players)
    void onSubmitPlayerBtnClick() {
        AppUtils.clickVibrate(getActivity());
        if (mSquadListAdapter != null) {
            ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> selectedPlayers = mSquadListAdapter.getSelectedPlayers();
            if (selectedPlayers.size() != 0) {
                mSelectCaptainDialog = new SelectCaptainDialog(getActivity(), mOnSubmitClickListener, selectedPlayers);
                mSelectCaptainDialog.show();
                mSelectCaptainDialog.setPreselection();
            } else {
                Toast.makeText(getActivity(), "Please add players first.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static CompletedSquadFragment newInstance(
            String userGUID,
            String roundId,
            String userName,
            String seriesId,
            String contestGUID,
            String auctionStatus,
            boolean isSeriesStarted) {
        Bundle args = new Bundle();
        args.putString("userGUID", userGUID);
        args.putString("roundId", roundId);
        args.putString("userName", userName);
        args.putString("seriesId", seriesId);
        args.putString("contestGUID", contestGUID);
        args.putString("auctionStatus", auctionStatus);
        args.putString("auctionStatus", auctionStatus);
        args.putBoolean("isSeriesStarted", isSeriesStarted);

        CompletedSquadFragment fragment = new CompletedSquadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_squad;
    }

    @Override
    public void init() {
        roundId = getArguments().getString("roundId");
        seriesId = getArguments().getString("seriesId");
        contestGUID = getArguments().getString("contestGUID");
        auctionStatus = getArguments().getString("auctionStatus");
        userGUID = getArguments().getString("userGUID");
        userName = getArguments().getString("userName");
        isSeriesStarted = getArguments().getBoolean("isSeriesStarted");
        isMySquad = userGUID.equals(AppSession.getInstance().getLoginSession().getData().getUserGUID());
        loader = new Loader(getCurrentView());
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(getActivity());
        getData();
    }

    private void getData() {
        mLinearLayoutDataRoot.setVisibility(View.GONE);
        if (NetworkUtils.isConnected(getActivity())) {
            loader.start();
            final GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            getAuctionPlayerInput.setUserGUID(userGUID);
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setRoundID(roundId);
           // getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            //getAuctionPlayerInput.setParams("PlayerPosition,AuctionTopPlayerSubmitted,PlayerID,PlayerRole,PlayerPic,BidCredit,UserTeamGUID,UserTeamName,IsAssistant");
            getAuctionPlayerInput.setParams("AuctionPlayingPlayer,PlayerBattingStyle,PlayerBowlingStyle,PlayerBattingStats,PlayerBowlingStats,TeamNameShort,PlayerPosition,AuctionTopPlayerSubmitted,PlayerID,PlayerRole,PlayerPic,BidCredit,UserTeamGUID,UserTeamName,IsAssistant,SeriesGUID,SeriesID,Points");
            getAuctionPlayerInput.setIsAssistant("No");
            getAuctionPlayerInput.setIsPreTeam("No");
            getAuctionPlayerInput.setMySquadPlayer("Yes");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            getAuctionPlayerInput.setAuctionPlayingPlayer("Yes");
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
                        mSquadListAdapter = new CompletedSquadListAdapter(
                                getContext(),
                                roundId,seriesId,
                                auctionStatus, getAuctionPlayerOutput,isSeriesStarted);
                        mRecyclerViewSquad.setAdapter(mSquadListAdapter);
                        if (isSeriesStarted()) {
//                            mCustomTextViewSelectLabel.setVisibility(View.GONE);
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
//                                        mCustomTextViewSelectLabel.setVisibility(View.GONE);
                                        mRelativeLayoutBottomTab.setVisibility(View.GONE);
                                    }
                                }
                            }else {
//                                mCustomTextViewSelectLabel.setVisibility(View.GONE);
                                mRelativeLayoutBottomTab.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        loader.hide();
                        loader.getTryAgainView().setVisibility(View.GONE);
                        loader.error("No Player Available.");
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
            loader.getTryAgainView().setVisibility(View.GONE);
            loader.error(AppUtils.getStrFromRes(R.string.network_error));
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
}
