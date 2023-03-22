package com.websinception.megastar.UI.draft.draftHome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.GetAuctionPlayerInput;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;

import butterknife.BindView;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedSquadFragment extends BaseFragment {

    private String contestGUID, matchGUID, auctionStatus, seriesId, userGUID;
    private Loader loader;
    private IUserInteractor mInteractor;
    private Call<GetAuctionPlayerOutput> auctionPlayersCall;
    private CompletedSquadListAdapter mSquadListAdapter;



    @BindView(R.id.rv_squad)
    RecyclerView mRecyclerViewSquad;





    public static CompletedSquadFragment newInstance(
            String userGUID,
            String matchGUID,
            String seriesId,
            String contestGUID) {
        Bundle args = new Bundle();
        args.putString("userGUID", userGUID);
        args.putString("matchGUID", matchGUID);
        args.putString("seriesId", seriesId);
        args.putString("contestGUID", contestGUID);

        CompletedSquadFragment fragment = new CompletedSquadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_draft_completed_squad;
    }

    @Override
    public void init() {
        matchGUID = getArguments().getString("matchGUID");
        seriesId = getArguments().getString("seriesId");
        contestGUID = getArguments().getString("contestGUID");
        auctionStatus = getArguments().getString("auctionStatus");
        userGUID = getArguments().getString("userGUID");
        loader = new Loader(getCurrentView());
        mInteractor = new UserInteractor();
        getData();
    }

    private void getData() {
        if (NetworkUtils.isConnected(getActivity())) {
            loader.start();
            final GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setIsAssistant("No");
            getAuctionPlayerInput.setIsPreTeam("No");
            getAuctionPlayerInput.setMySquadPlayer("Yes");
            getAuctionPlayerInput.setParams("PlayerPosition,AuctionTopPlayerSubmitted,PlayerID,PlayerRole,PlayerPic,BidCredit,UserTeamGUID,UserTeamName,IsAssistant,Points,SeriesGUID,SeriesID");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            getAuctionPlayerInput.setMatchGUID(matchGUID);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionPlayerInput.setUserGUID(userGUID);

            auctionPlayersCall = mInteractor.getDraftPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    if (auctionPlayersCall == null || auctionPlayersCall.isCanceled()) {
                        return;
                    }
                    loader.hide();
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        mRecyclerViewSquad.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        mSquadListAdapter = new CompletedSquadListAdapter(
                                getContext(),
                                matchGUID,seriesId,
                                getAuctionPlayerOutput);
                        mRecyclerViewSquad.setAdapter(mSquadListAdapter);
                    } else {
                        loader.hide();
                        loader.dataNotFound("No Player Available.");
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

}
