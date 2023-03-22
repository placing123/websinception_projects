package com.websinception.megastar.UI.auction.auctionHome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

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

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment {

    private String contestGUID,roundId;
    private Loader loader;
    private IUserInteractor mInteractor;
    private Call<GetAuctionPlayerOutput> auctionPlayersCall;

    @BindView(R.id.rv_order)
    RecyclerView mRecyclerViewOreder;

    @BindView(R.id.ll_data_root)
    LinearLayout mLinearLayoutDataRoot;


    public static OrderFragment newInstance(String roundId, String contestGUID) {
        Bundle args = new Bundle();
        args.putString("roundId",roundId);
        args.putString("contestGUID",contestGUID);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_order;
    }

    @Override
    public void init() {
        roundId=getArguments().getString("roundId");
        contestGUID=getArguments().getString("contestGUID");
        loader = new Loader(getCurrentView());
        mInteractor = new UserInteractor();
        getData();
    }

    private void getData(){
        mLinearLayoutDataRoot.setVisibility(View.GONE);
        if (NetworkUtils.isConnected(getActivity())){
            loader.start();
            GetAuctionPlayerInput getAuctionPlayerInput= new GetAuctionPlayerInput();
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setRoundID(roundId);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionPlayerInput.setParams("BidSoldCredit,PlayerStatus,PlayerID,PlayerRole,PlayerPic,PlayerCountry,PlayerBornPlace,PlayerBattingStyle,PlayerBowlingStyle,MatchType,MatchNo,MatchDateTime,SeriesName,TeamGUID,PlayerBattingStats,PlayerBowlingStats,IsPlaying,PointsData,PlayerSalary,TeamNameShort,PlayerSalaryCredit,UserPlayerSoldName,SeriesGUID,SeriesID");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            auctionPlayersCall = mInteractor.getAuctionPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    if (auctionPlayersCall==null||auctionPlayersCall.isCanceled()) {
                        return;
                    }
                    loader.hide();
                    int index=-1;
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        for (int i = 0; i < getAuctionPlayerOutput.getData().getTotalRecords(); i++) {
                            if (getAuctionPlayerOutput.getData().getRecords().get(i).getPlayerStatus().equals("Live")) {
                                index=i;
                                break;
                            }
                        }
                        List<GetAuctionPlayerOutput.DataBean.RecordsBean> records = getAuctionPlayerOutput.getData().getRecords();
                        if (index!=-1) {
                            GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean = records.get(index);
                            records.remove(index);
                            records.add(0,recordsBean);
                        }

                        mRecyclerViewOreder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        mRecyclerViewOreder.setAdapter(new OrderListAdapter(getContext(),roundId,records));
                        mLinearLayoutDataRoot.setVisibility(View.VISIBLE);
                    } else {
                        loader.hide();
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
                    if (auctionPlayersCall==null||auctionPlayersCall.isCanceled()) {
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
        }else {
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
        if (auctionPlayersCall!=null) {
            auctionPlayersCall.cancel();
            auctionPlayersCall=null;
        }
        
    }
}
