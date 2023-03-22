package com.mw.fantasy.UI.draft.draftHome;


import android.app.Activity;
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
import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.auctionHome.ChooseCaptainActivity;
import com.mw.fantasy.UI.auction.auctionHome.SelectCaptainDialog;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseFragment;
import com.mw.fantasy.base.Loader;
import com.mw.fantasy.beanInput.GetAuctionJoinedUserInput;
import com.mw.fantasy.beanInput.GetAuctionPlayerInput;
import com.mw.fantasy.beanInput.SubmitAuctionsPlayersInput;
import com.mw.fantasy.beanOutput.DefaultRespose;
import com.mw.fantasy.beanOutput.GetAuctionInfoOutput;
import com.mw.fantasy.beanOutput.GetAuctionJoinedUserOutput;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySquadFragment extends BaseFragment {

    public static final int MY_SQUAD = 1;
    public static final int OTHER_SQUAD = 2;
    private String contestGUID, matchGUID, userTeamGUID, auctionStartTime,auctionStatus, seriesID, seriesDeadLine, seriesName,userGUID;
    private int type, seriesStatus;
    private Loader loader;
    private IUserInteractor mInteractor;
    private Call<GetAuctionPlayerOutput> auctionPlayersCall;
    private Call<GetAuctionJoinedUserOutput> draftJoinedUsersCall;
    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> mRecordsBeanList = new ArrayList<>();
    private MySquadListAdapter mMySquadListAdapter;


    @BindView(R.id.ll_other_user_root)
    LinearLayout mLinearLayoutOtherUserRoot;

    @BindView(R.id.ll_container)
    LinearLayout mLinearLayoutTeamContainer;

    @BindView(R.id.ctv_no_player_label)
    CustomTextView mCustomTextViewNoPlayerLable;

    @BindView(R.id.rv_users)
    RecyclerView mRecyclerViewUsers;


    @BindView(R.id.rv_squad)
    RecyclerView mRecyclerViewSquad;

    @BindView(R.id.ctv_btn_submit_players)
    CustomTextView mCustomTextViewSubmitBtn;

    @BindView(R.id.rl_bottom_tab)
    RelativeLayout mRelativeLayoutBottomTab;


    @BindView(R.id.ll_root)
    LinearLayout mLinearLayoutRoot;


    private GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria mDraftPlayerSelectionCriteria;
    private List<GetAuctionJoinedUserOutput.DataBean.RecordsBean> joinedUserList = new ArrayList<>();
    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    private SelectCaptainDialog mSelectCaptainDialog;
    private SelectCaptainDialog.OnSubmitClickListener mOnSubmitClickListener = new SelectCaptainDialog.OnSubmitClickListener() {
        @Override
        public void onClick(String c_GUID, String v_GUID) {
            AppUtils.clickVibrate(getActivity());
            ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> selectedPlayers = new ArrayList<>(mRecordsBeanList);
            if (c_GUID.equals("")) {
                Toast.makeText(getActivity(), "Please select Captain.", Toast.LENGTH_SHORT).show();
            } else if (v_GUID.equals("")) {
                Toast.makeText(getActivity(), "Please select Vice Captain.", Toast.LENGTH_SHORT).show();
            } else if (c_GUID.equals(v_GUID)) {
                Toast.makeText(getActivity(), "Captain and vice captain can't be same.", Toast.LENGTH_SHORT).show();
            } else {
                if (v_GUID.equals("")) {
                    Toast.makeText(getActivity(), "Please select Vice Captain.", Toast.LENGTH_SHORT).show();
                } else {
                    mSelectCaptainDialog.hide();
                    apiCallSubmitPlayers(selectedPlayers, c_GUID, v_GUID);
                }
            }
        }
    };

    @OnClick(R.id.ctv_btn_submit_players)
    void onSubmitPlayerBtnClick() {
        AppUtils.clickVibrate(getActivity());

        AppUtils.clickVibrate(getActivity());
        if (mMySquadListAdapter != null) {
            if (mRecordsBeanList.size() != 0) {
                for (GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean : mRecordsBeanList) {
                    recordsBean.setAuctionPlayingPlayer("Yes");
                }

                start(getActivity(), new Gson().toJson(mRecordsBeanList), seriesName, seriesDeadLine, seriesStatus);
            }
        }


       /* mSelectCaptainDialog = new SelectCaptainDialog(getActivity(), mOnSubmitClickListener,
                new ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean>(mRecordsBeanList));
        mSelectCaptainDialog.show();
        mSelectCaptainDialog.setPreselection();*/
    }


    public void start(Context context, String data, String seriesName,
                      String seriesDeadLine,
                      int seriesStatus) {
        Intent starter = new Intent(context, ChooseCaptainActivity.class);
        starter.putExtra("data", data);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("matchGUID", matchGUID);
        starter.putExtra("auctionStatus", auctionStatus);
        starter.putExtra("auctionStartTime", auctionStartTime);
        startActivityForResult(starter,22);
    }


    public static MySquadFragment newInstance(String matchGUID,
                                              String seriesID,
                                              String contestGUID,
                                              String auctionStatus,
                                              int type,
                                              GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria draftPlayerSelectionCriteria,
                                              String seriesName,
                                              String seriesDeadLine,
                                              int seriesStatus,
                                              String userGUID,
                                              String auctionStartTime) {
        Bundle args = new Bundle();
        args.putString("seriesID", seriesID);
        args.putString("matchGUID", matchGUID);
        args.putString("contestGUID", contestGUID);
        args.putString("userGUID", userGUID);
        args.putString("auctionStatus", auctionStatus);
        args.putInt("type", type);
        args.putSerializable("draftPlayerSelectionCriteria", draftPlayerSelectionCriteria);
        args.putString("seriesName", seriesName);
        args.putString("seriesDeadLine", seriesDeadLine);
        args.putInt("seriesStatus", seriesStatus);
        args.putString("auctionStartTime", auctionStartTime);
        MySquadFragment fragment = new MySquadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_draft_squad;
    }

    @Override
    public void init() {
        userGUID = getArguments().getString("userGUID");
        matchGUID = getArguments().getString("matchGUID");
        seriesID = getArguments().getString("seriesID");
        contestGUID = getArguments().getString("contestGUID");
        auctionStatus = getArguments().getString("auctionStatus");
        auctionStartTime = getArguments().getString("auctionStartTime");
        seriesName = getArguments().getString("seriesName");
        seriesDeadLine = getArguments().getString("seriesDeadLine");
        seriesStatus = getArguments().getInt("seriesStatus");
        mDraftPlayerSelectionCriteria = (GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria) getArguments().getSerializable("draftPlayerSelectionCriteria");
        type = getArguments().getInt("type");
        loader = new Loader(getCurrentView());
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(getActivity());
        if (type == MY_SQUAD) {
            mLinearLayoutOtherUserRoot.setVisibility(View.GONE);
            apiCallGetMySquad();
        } else {
            mLinearLayoutOtherUserRoot.setVisibility(View.VISIBLE);
            mRelativeLayoutBottomTab.setVisibility(View.GONE);
            apiCallGetJoinedUser();
        }
        mRecyclerViewSquad.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mMySquadListAdapter = new MySquadListAdapter(getContext(), type, matchGUID,auctionStatus, mRecordsBeanList,seriesStatus);
        mRecyclerViewSquad.setAdapter(mMySquadListAdapter);

    }


    private void apiCallGetJoinedUser() {
        mLinearLayoutRoot.setVisibility(View.GONE);
        loader.hide();
        if (NetworkUtils.isConnected(getActivity())) {
            loader.start();
            GetAuctionJoinedUserInput getAuctionJoinedUserInput = new GetAuctionJoinedUserInput();
            getAuctionJoinedUserInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionJoinedUserInput.setContestGUID(contestGUID);
            getAuctionJoinedUserInput.setMatchGUID(matchGUID);
            getAuctionJoinedUserInput.setSeriesID(seriesID);
            getAuctionJoinedUserInput.setParams("FirstName,Username,UserGUID,ProfilePic,AuctionTimeBank,AuctionBudget,AuctionUserStatus");
            draftJoinedUsersCall = mInteractor.getDraftJoinedUsers(getAuctionJoinedUserInput, new IUserInteractor.OnGetAuctionJoinedUserResponseListener() {
                @Override
                public void onSuccess(GetAuctionJoinedUserOutput getAuctionJoinedUserOutput) {
                    if (draftJoinedUsersCall == null || draftJoinedUsersCall.isCanceled()) {
                        return;
                    }
                    loader.hide();
                    joinedUserList.clear();
                    if (getAuctionJoinedUserOutput.getData().getTotalRecords() != 0) {
                        joinedUserList = getAuctionJoinedUserOutput.getData().getRecords();
                        for (int i = 0; i < joinedUserList.size(); i++) {
                            if (joinedUserList.get(i).getUserGUID().equals(AppSession.getInstance().getLoginSession().getData().getUserGUID())) {
                                joinedUserList.remove(i);
                                break;
                            }
                        }
                        if (joinedUserList.size() > 0) {
                            mLinearLayoutRoot.setVisibility(View.VISIBLE);
                            mRecyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            int index=0;
                            for (int i = 0; i < joinedUserList.size(); i++) {
                                if (joinedUserList.get(i).equals(userGUID)) {
                                    index=i;
                                    break;
                                }
                            }
                            mRecyclerViewUsers.setAdapter(new MySquadParticipantListAdapter(MySquadFragment.this, joinedUserList, index));
                            apiCallGetUserSquad(joinedUserList.get(index).getUserGUID());
                            return;
                        }

                    }
                    loader.error("No Joined User!");
                    loader.getTryAgainView().setVisibility(View.GONE);
                    loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                    loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            apiCallGetJoinedUser();
                        }
                    });

                }

                @Override
                public void onError(String errorMsg) {
                    if (draftJoinedUsersCall == null || draftJoinedUsersCall.isCanceled()) {
                        return;
                    }
                    loader.hide();
                    loader.error(errorMsg);
                    loader.getTryAgainView().setVisibility(View.GONE);
                    loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                    loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            apiCallGetJoinedUser();
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
                    apiCallGetJoinedUser();
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
            submitAuctionsPlayersInput.setMatchGUID(matchGUID);
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
                userTeamPlayersBeans.add(tem);
            }
            submitAuctionsPlayersInput.setUserTeamPlayers(userTeamPlayersBeans);
            mInteractor.submitDraftPlayers(submitAuctionsPlayersInput, new IUserInteractor.OnSubmitAuctionsPlayersListener() {
                @Override
                public void onSuccess(DefaultRespose defaultRespose) {
                    mProgressDialog.dismiss();
                    AppUtils.showToast(getActivity(), defaultRespose.getMessage());
                    //apiCallGetMySquad();
                    getActivity().finish();
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

    public void apiCallGetUserSquad(final String userGUID) {
        mLinearLayoutTeamContainer.setVisibility(View.GONE);
        mCustomTextViewNoPlayerLable.setVisibility(View.GONE);
        loader.hide();
        if (NetworkUtils.isConnected(getActivity())) {

            mProgressDialog.show();
            final GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            getAuctionPlayerInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setMatchGUID(matchGUID);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionPlayerInput.setParams("PlayerPosition,AuctionTopPlayerSubmitted,PlayerID,PlayerRole,PlayerPic,BidCredit,UserTeamGUID,UserTeamName,IsAssistant,SeriesGUID,SeriesID");
            getAuctionPlayerInput.setIsAssistant("No");
            getAuctionPlayerInput.setIsPreTeam("No");
            getAuctionPlayerInput.setMySquadPlayer("Yes");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            getAuctionPlayerInput.setUserGUID(userGUID);
            auctionPlayersCall = mInteractor.getDraftPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    if (auctionPlayersCall == null || auctionPlayersCall.isCanceled()) {
                        return;
                    }
                    mRecordsBeanList.clear();
                    mProgressDialog.dismiss();
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        mRecordsBeanList.addAll(getAuctionPlayerOutput.getData().getRecords());
                        mLinearLayoutTeamContainer.setVisibility(View.VISIBLE);
                    } else {
                        mCustomTextViewNoPlayerLable.setVisibility(View.VISIBLE);
                        mCustomTextViewNoPlayerLable.setText("No player drafted yet!");
                    }
                    mMySquadListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String errorMsg) {
                    if (auctionPlayersCall == null || auctionPlayersCall.isCanceled()) return;
                    mProgressDialog.dismiss();
                    mCustomTextViewNoPlayerLable.setVisibility(View.VISIBLE);
                    mCustomTextViewNoPlayerLable.setText(errorMsg);
                }
            });
        } else {
            mCustomTextViewNoPlayerLable.setVisibility(View.VISIBLE);
            mCustomTextViewNoPlayerLable.setText(AppUtils.getStrFromRes(R.string.network_error));
            mProgressDialog.dismiss();
        }
    }

    private void apiCallGetMySquad() {
        mLinearLayoutRoot.setVisibility(View.GONE);
        loader.hide();
        if (NetworkUtils.isConnected(getActivity())) {
            loader.start();
            final GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            getAuctionPlayerInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setMatchGUID(matchGUID);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionPlayerInput.setParams("PlayerPosition,AuctionTopPlayerSubmitted,PlayerID,PlayerRole,PlayerPic,BidCredit,UserTeamGUID,UserTeamName,IsAssistant,SeriesGUID,SeriesID");
            getAuctionPlayerInput.setIsAssistant("No");
            getAuctionPlayerInput.setIsPreTeam("No");
            getAuctionPlayerInput.setMySquadPlayer("Yes");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            getAuctionPlayerInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            auctionPlayersCall = mInteractor.getDraftPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    if (auctionPlayersCall == null || auctionPlayersCall.isCanceled()) {
                        return;
                    }
                    loader.hide();
                    if (auctionStatus.equals("Completed")) {
                        mRelativeLayoutBottomTab.setVisibility(View.VISIBLE);
                        if (getAuctionPlayerOutput.getData().getAuctionTopPlayerSubmitted().equals("Yes")) {
                            mCustomTextViewSubmitBtn.setText("Update Team");
                        } else {
                            mCustomTextViewSubmitBtn.setText("Submit Team");
                        }
                    } else {
                        mRelativeLayoutBottomTab.setVisibility(View.GONE);
                    }
                    mRecordsBeanList.clear();
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        userTeamGUID = getAuctionPlayerOutput.getData().getUserTeamGUID();
                        mRecordsBeanList.addAll(getAuctionPlayerOutput.getData().getRecords());
                        mMySquadListAdapter.notifyDataSetChanged();
                        mLinearLayoutRoot.setVisibility(View.VISIBLE);
                    } else {
//                        ll_rv_heading.setVisibility(View.GONE);
                        loader.hide();
                        loader.error("No player drafted yet!");
                        loader.getTryAgainView().setVisibility(View.GONE);
                        loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                        loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiCallGetMySquad();
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
                            apiCallGetMySquad();
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
                    apiCallGetMySquad();
                }
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==22) {
            if (resultCode== Activity.RESULT_OK) {
                String  c_GUID =  data.getStringExtra("captain");
                String  v_GUID="";
                if (data.hasExtra("vice_captain")) {
                    v_GUID = data.getStringExtra("vice_captain") ;
                }
                apiCallSubmitPlayers((ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean>) mRecordsBeanList, c_GUID, v_GUID);
            }
        }

    }


}


