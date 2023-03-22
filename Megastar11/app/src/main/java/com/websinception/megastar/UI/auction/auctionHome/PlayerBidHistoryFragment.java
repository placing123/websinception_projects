package com.websinception.megastar.UI.auction.auctionHome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.GetContestBidHistoryInput;
import com.websinception.megastar.beanOutput.GetContestBidHistoryOutput;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.EndlessRecyclerViewScrollListenerFab;
import com.websinception.megastar.utility.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerBidHistoryFragment extends BaseFragment {

    public static PlayerBidHistoryFragment newInstance(String roundId, String contestGUID, String playerGUID) {

        Bundle args = new Bundle();
        args.putString("roundId", roundId);
        args.putString("contestGUID", contestGUID);
        args.putString("playerGUID", playerGUID);
        PlayerBidHistoryFragment fragment = new PlayerBidHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_order)
    RecyclerView mRecyclerView;

    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;



    @BindView(R.id.ll_data_root)
    LinearLayout mLinearLayoutDataRoot;

    @BindView(R.id.ctv_value_label)
    CustomTextView mCustomTextViewValueLabel;



    private Loader loader;
    private IUserInteractor mInteractor;
    private Call<GetContestBidHistoryOutput> contestBidHistoryCall;
    private boolean allContestListed = false;
    private int currentPageNo = 1;
    private ArrayList<GetContestBidHistoryOutput.DataBean.RecordsBean> mRecordsBeanArrayList = new ArrayList<>();
    private BidHistoryListAdapter mBidHistoryListAdapter;
    private LinearLayoutManager layoutManager;
    private String contestGUID, roundId, playerGUID;


    @Override
    public int getLayout() {
        return R.layout.fragment_player_bid_history;
    }

    @Override
    public void init() {
        roundId = getArguments().getString("roundId");
        contestGUID = getArguments().getString("contestGUID");
        playerGUID = getArguments().getString("playerGUID");
        loader = new Loader(getCurrentView());
        mInteractor = new UserInteractor();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                allContestListed=false;
                currentPageNo=1;
                mRecordsBeanArrayList.clear();
                mBidHistoryListAdapter.notifyDataSetChanged();
                getData(1);
            }
        });
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListenerFab(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getData(currentPageNo);
            }

            @Override
            public void onShowFab(boolean show) {
            }
        });
        mBidHistoryListAdapter = new BidHistoryListAdapter(mRecordsBeanArrayList);
        mRecyclerView.setAdapter(mBidHistoryListAdapter);
        getData(currentPageNo);
    }


    private void getData(int page) {
        if (contestBidHistoryCall != null || allContestListed) {
            return;
        }
        if (NetworkUtils.isConnected(getActivity())) {
            if (currentPageNo == 1) {
                mLinearLayoutDataRoot.setVisibility(View.GONE);
                mCustomTextViewValueLabel.setVisibility(View.GONE);
                loader.start();
            }
            GetContestBidHistoryInput getContestBidHistoryInput = new GetContestBidHistoryInput();
            getContestBidHistoryInput.setContestGUID(contestGUID);
            getContestBidHistoryInput.setRoundID(roundId);
            getContestBidHistoryInput.setPlayerGUID(playerGUID);
            getContestBidHistoryInput.setPageSize(10);
            getContestBidHistoryInput.setPageNo(page);
            getContestBidHistoryInput.setParams("BidCredit,DateTime,FirstName,ProfilePic,Username");
            contestBidHistoryCall = mInteractor.getContestBidHistory(getContestBidHistoryInput, new IUserInteractor.OnGetContestBidHistoryListener() {
                @Override
                public void onSuccess(GetContestBidHistoryOutput getContestBidHistoryOutput) {
                    loader.hide();
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (getContestBidHistoryOutput.getData().getTotalRecords() == 0) {
                        if (currentPageNo==1) {
                            allContestListed = true;
                            mLinearLayoutDataRoot.setVisibility(View.GONE);
                            mCustomTextViewValueLabel.setVisibility(View.VISIBLE);
                            mCustomTextViewValueLabel.setText("It seems no one placed bid on this player yet.");
                        }else {
                            allContestListed = true;
                        }

                    } else {
                        mLinearLayoutDataRoot.setVisibility(View.VISIBLE);
                        mCustomTextViewValueLabel.setVisibility(View.GONE);
                        currentPageNo++;
                        for (GetContestBidHistoryOutput.DataBean.RecordsBean record : getContestBidHistoryOutput.getData().getRecords()) {
                            mRecordsBeanArrayList.add(record);
                        }
                        mBidHistoryListAdapter.notifyDataSetChanged();
                    }

                    contestBidHistoryCall.cancel();
                    contestBidHistoryCall = null;

                }

                @Override
                public void onError(String errorMsg) {
                    loader.hide();
                    mSwipeRefreshLayout.setRefreshing(false);
                    contestBidHistoryCall.cancel();
                    contestBidHistoryCall = null;
                    if (currentPageNo == 1) {
                        mLinearLayoutDataRoot.setVisibility(View.GONE);
                        mCustomTextViewValueLabel.setVisibility(View.GONE);


                        loader.error(errorMsg);
                        loader.getTryAgainView().setVisibility(View.GONE);
                        loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                        loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                allContestListed = false;
                                currentPageNo=1;
                                mRecordsBeanArrayList.clear();
                                mBidHistoryListAdapter.notifyDataSetChanged();
                                getData(1);
                            }
                        });
                    }
                }
            });
        } else {
            loader.hide();
            mSwipeRefreshLayout.setRefreshing(false);
            if (currentPageNo == 1) {


                mLinearLayoutDataRoot.setVisibility(View.GONE);
                mCustomTextViewValueLabel.setVisibility(View.GONE);
                loader.getTryAgainView().setVisibility(View.GONE);
                loader.error(AppUtils.getStrFromRes(R.string.network_error));
                loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentPageNo=1;
                        allContestListed = false;
                        mRecordsBeanArrayList.clear();
                        mBidHistoryListAdapter.notifyDataSetChanged();
                        getData(1);
                    }
                });
            }
        }
    }


}
