package com.mw.fantasy.UI.auction.auctionHome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseFragment;
import com.mw.fantasy.base.Loader;
import com.mw.fantasy.beanInput.GetAuctionJoinedUserInput;
import com.mw.fantasy.beanOutput.GetAuctionJoinedUserOutput;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;

import butterknife.BindView;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends BaseFragment {

    private String contestGUID,roundId;
    private Loader loader;
    private IUserInteractor mInteractor;

    @BindView(R.id.vp_user)
    RecyclerView mRecyclerViewUser;

    @BindView(R.id.ll_data_root)
    LinearLayout mLinearLayoutDataRoot;

    private Call<GetAuctionJoinedUserOutput> auctionJoinedUsersCall;


    public static BudgetFragment newInstance(String roundId, String contestGUID) {

        Bundle args = new Bundle();
        args.putString("roundId",roundId);
        args.putString("contestGUID",contestGUID);
        BudgetFragment fragment = new BudgetFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_budget;
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

            GetAuctionJoinedUserInput getAuctionJoinedUserInput= new GetAuctionJoinedUserInput();
            getAuctionJoinedUserInput.setContestGUID(contestGUID);
            getAuctionJoinedUserInput.setRoundID(roundId);
            getAuctionJoinedUserInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionJoinedUserInput.setParams("FirstName,Username,UserGUID,ProfilePic,AuctionTimeBank,AuctionBudget,AuctionUserStatus");
            auctionJoinedUsersCall = mInteractor.getAuctionJoinedUsers(getAuctionJoinedUserInput, new IUserInteractor.OnGetAuctionJoinedUserResponseListener() {
                @Override
                public void onSuccess(GetAuctionJoinedUserOutput getAuctionJoinedUserOutput) {
                    if (auctionJoinedUsersCall == null || auctionJoinedUsersCall.isCanceled()) {
                        return;
                    }
                    loader.hide();
                    if (getAuctionJoinedUserOutput.getData().getTotalRecords() != 0) {
                        mLinearLayoutDataRoot.setVisibility(View.VISIBLE);
                        mRecyclerViewUser.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        mRecyclerViewUser.setAdapter(new BudgetListAdapter(getAuctionJoinedUserOutput));
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
                public void onError(String errorMsg) {
                    if (auctionJoinedUsersCall == null || auctionJoinedUsersCall.isCanceled()) {
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
        }else {
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
        if (auctionJoinedUsersCall!=null) {
            auctionJoinedUsersCall.cancel();
            auctionJoinedUsersCall=null;
        }

    }


}
