package com.mw.fantasy.UI.auction.auctionHome;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.addPlayerInAssitant.AddPlayerInAssistantActivity;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseFragment;
import com.mw.fantasy.base.Loader;
import com.mw.fantasy.beanInput.AssistSwitchInput;
import com.mw.fantasy.beanInput.GetAuctionPlayerInput;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AuctionAlertDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;
import com.rey.material.widget.Switch;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssistantFragment extends BaseFragment {


    private String contestGUID, roundId, auctionStatus, seriesId,auctionStartTime;
    private Loader loader;
    private IUserInteractor mInteractor;
    private AuctionAlertDialog mAuctionAlertDialog;

    @BindView(R.id.rv_assistant)
    RecyclerView mRecyclerViewAssistant;

    @BindView(R.id.switch_assistant)
    Switch mSwitchState;

    @BindView(R.id.rl_bottom_tab)
    RelativeLayout mRelativeLayoutBottomTab;

    @BindView(R.id.ctv_assistant_status)
    CustomTextView mCustomTextViewState;

    @BindView(R.id.ctv_no_player_label)
    CustomTextView mCustomTextViewNoPlayerLabel;

    @BindView(R.id.ll_container)
    LinearLayout mLinearLayoutContainer;

    @BindView(R.id.ctv_btn_add_players)
    CustomTextView mCustomTextViewAddPlayer;

    @BindView(R.id.ctv_value_label)
    CustomTextView mCustomTextViewValueLabel;

    @BindView(R.id.ll_data_root)
    LinearLayout mLinearLayoutDataRoot;

    @OnClick(R.id.ctv_btn_add_players)
    void onAddPlayerBtnClick() {
        AppUtils.clickVibrate(getActivity());
        HashMap<String, String> selectedPlayer = new HashMap<>();
        if (mGetAuctionPlayerOutput != null && mGetAuctionPlayerOutput.getData().getTotalRecords() != 0)
            for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
                selectedPlayer.put(record.getPlayerGUID(), record.getBidCredit());
            }
        if (mGetAuctionPlayerOutput != null) {
            //auctionDraftTemchanges

            AddPlayerInAssistantActivity.start(this,
                    selectedPlayer,
                    roundId,
                    seriesId,
                    contestGUID,
                    mGetAuctionPlayerOutput.getData().getUserTeamGUID(),
                    mGetAuctionPlayerOutput.getData().getUserTeamName(),
                    seriesName, seriesDeadLine, seriesStatus, budgetLeft,
                    auctionStatus,auctionStartTime);
        } else {

            AddPlayerInAssistantActivity.start(this,
                    selectedPlayer,
                    roundId,
                    seriesId,
                    contestGUID,
                    "",
                    "",
                    seriesName, seriesDeadLine, seriesStatus, budgetLeft,
                    auctionStatus,auctionStartTime);
        }

    }

    private Call<GetAuctionPlayerOutput> auctionPlayersCall;
    private AssistantListAdapter mAssistantListAdapter;
    private GetAuctionPlayerOutput mGetAuctionPlayerOutput;
    private boolean callTurnOnOffApi = false;
    private String seriesName, seriesDeadLine, budgetLeft;
    private int seriesStatus;
    private AuctionAlertDialog mAssistantEnabledAlert;


    public static AssistantFragment newInstance(String roundId, String seriesId, String contestGUID, String auctionStatus, String seriesName,
                                                String seriesDeadLine,
                                                int seriesStatus,
                                                String budgetLeft,
                                                String auctionStartTime)
    {

        Bundle args = new Bundle();
        args.putString("roundId", roundId);
        args.putString("seriesId", seriesId);
        args.putString("contestGUID", contestGUID);
        args.putString("auctionStatus", auctionStatus);
        args.putString("seriesName", seriesName);
        args.putString("seriesDeadLine", seriesDeadLine);
        args.putInt("seriesStatus", seriesStatus);
        args.putString("budgetLeft", budgetLeft);
        args.putString("auctionStartTime", auctionStartTime);
        AssistantFragment fragment = new AssistantFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_assistant;
    }

    @Override
    public void init() {
        auctionStartTime = getArguments().getString("auctionStartTime");
        roundId = getArguments().getString("roundId");
        seriesId = getArguments().getString("seriesId");
        contestGUID = getArguments().getString("contestGUID");
        auctionStatus = getArguments().getString("auctionStatus");
        seriesName = getArguments().getString("seriesName");
        seriesDeadLine = getArguments().getString("seriesDeadLine");
        budgetLeft = getArguments().getString("budgetLeft");
        seriesStatus = getArguments().getInt("seriesStatus");
        mCustomTextViewValueLabel.setText("Amount\nSanctioned");
        loader = new Loader(getCurrentView());
        mInteractor = new UserInteractor();
        getData();
        mSwitchState.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                AppUtils.clickVibrate(getActivity());
                if (checked) {

                    mCustomTextViewState.setTextColor(getResources().getColor(R.color.yellow));
                    mCustomTextViewState.setText("Assistant is Enabled");
                    mGetAuctionPlayerOutput.getData().setIsAssistant("Yes");
                } else {
                    mCustomTextViewState.setTextColor(getResources().getColor(R.color.white));
                    mCustomTextViewState.setText("Assistant is Disabled");
                    mGetAuctionPlayerOutput.getData().setIsAssistant("No");
                }
                mAssistantListAdapter.notifyDataSetChanged();
                apiCallTurnOnOfAssistant();

            }
        });
        if (auctionStatus.equals("Cancelled") || auctionStatus.equals("Completed")) {
            mRelativeLayoutBottomTab.setVisibility(View.GONE);
        } else {
            mRelativeLayoutBottomTab.setVisibility(View.VISIBLE);
        }
    }

    private void getData() {
        mLinearLayoutDataRoot.setVisibility(View.GONE);
        if (NetworkUtils.isConnected(getActivity())) {
            loader.start();
            GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setRoundID(roundId);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionPlayerInput.setParams("PlayerID,PlayerRole,PlayerPic,BidCredit,UserTeamGUID,UserTeamName,IsAssistant,SeriesGUID,SeriesID");
            getAuctionPlayerInput.setIsPreTeam("Yes");
            getAuctionPlayerInput.setMySquadPlayer("Yes");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            auctionPlayersCall = mInteractor.getAuctionPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    if (auctionPlayersCall == null || auctionPlayersCall.isCanceled()) {
                        return;
                    }
                    loader.hide();
                    mLinearLayoutDataRoot.setVisibility(View.VISIBLE);
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        mCustomTextViewNoPlayerLabel.setVisibility(View.GONE);
                        mLinearLayoutContainer.setVisibility(View.VISIBLE);
                        mGetAuctionPlayerOutput = getAuctionPlayerOutput;
                        mRecyclerViewAssistant.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        mAssistantListAdapter = new AssistantListAdapter(getContext(), roundId,mGetAuctionPlayerOutput);
                        mRecyclerViewAssistant.setAdapter(mAssistantListAdapter);
                        if (mGetAuctionPlayerOutput.getData().getIsAssistant().equals("No")) {
                            callTurnOnOffApi = true;
                        }
                        mCustomTextViewAddPlayer.setText("Edit Squad");
                        refreshAssistantView();
                    } else {
                        mCustomTextViewAddPlayer.setText("Add Squad");
                        loader.hide();
                        mCustomTextViewNoPlayerLabel.setVisibility(View.VISIBLE);
                        mLinearLayoutContainer.setVisibility(View.GONE);
                        mAuctionAlertDialog= new AuctionAlertDialog(getContext(),
                                "Hi, I'm your virtual assistant, I can place a bid on your selected player on behalf of you in your absence.",
                                "Let's Start",
                                "Cancel",
                                new AuctionAlertDialog.OnOkayBtnClickListener() {
                                    @Override
                                    public void onClick() {
                                        mAuctionAlertDialog.hide();
                                        HashMap<String, String> selectedPlayer = new HashMap<>();
                                        AddPlayerInAssistantActivity.start(AssistantFragment.this,
                                                selectedPlayer,
                                                roundId,
                                                seriesId,
                                                contestGUID,
                                                "",
                                                "",
                                                seriesName,
                                                seriesDeadLine,
                                                seriesStatus,
                                                budgetLeft,
                                                auctionStatus,auctionStartTime);

                                    }

                                    @Override
                                    public void onCancel() {
                                        mAuctionAlertDialog.hide();
                                    }
                                });
                        mAuctionAlertDialog.show();
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

    private void apiCallTurnOnOfAssistant() {
        if (!callTurnOnOffApi) {
            callTurnOnOffApi = true;
            return;
        }
        if (NetworkUtils.isConnected(getActivity())) {
            AssistSwitchInput assistSwitchInput = new AssistSwitchInput();
            assistSwitchInput.setContestGUID(contestGUID);
            assistSwitchInput.setRoundID(roundId);
            assistSwitchInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            assistSwitchInput.setUserTeamGUID(mGetAuctionPlayerOutput.getData().getUserTeamGUID());
            assistSwitchInput.setIsAssistant(mGetAuctionPlayerOutput.getData().getIsAssistant());
            mInteractor.auctionAssistantTeamOnOff(assistSwitchInput, new IUserInteractor.OnAuctionAssistantTeamOnOffResponseListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(String errorMsg) {
                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                    if (mGetAuctionPlayerOutput.getData().getIsAssistant().equals("Yes")) {
                        mGetAuctionPlayerOutput.getData().setIsAssistant("No");
                    } else {
                        mGetAuctionPlayerOutput.getData().setIsAssistant("Yes");
                    }
                    callTurnOnOffApi = false;
                    refreshAssistantView();
                }
            });
        } else {
            AppUtils.showToast(getActivity(),AppUtils.getStrFromRes(R.string.network_error));
        }
    }

    private void refreshAssistantView() {
        if (mAssistantEnabledAlert == null) {
            mAssistantEnabledAlert = new AuctionAlertDialog(getContext(),
                    "Hey, Please enable your assistant so the assistant can place bid behalf of you on your selected player.",
                    "Enable",
                    "Cancel",new AuctionAlertDialog.OnOkayBtnClickListener() {
                @Override
                public void onClick() {
                    mAssistantEnabledAlert.hide();
                    mSwitchState.setChecked(true);
                }

                @Override
                public void onCancel() {
                    mAssistantEnabledAlert.hide();
                }
            });

            if (mGetAuctionPlayerOutput.getData().getIsAssistant().equals("No")) {
                mAssistantEnabledAlert.show();
            }
        }
        if (mGetAuctionPlayerOutput.getData().getIsAssistant().equals("No")) {
            mSwitchState.setChecked(false);
            mCustomTextViewState.setText("Assistant is Disabled");
        } else {
            mSwitchState.setChecked(true);
            mCustomTextViewState.setText("Assistant is Enabled");
        }
        if (mAssistantListAdapter != null) {

            mAssistantListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddPlayerInAssistantActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                getData();
            }
        }
    }
}
