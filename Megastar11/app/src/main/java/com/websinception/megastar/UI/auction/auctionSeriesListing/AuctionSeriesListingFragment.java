package com.websinception.megastar.UI.auction.auctionSeriesListing;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.auctionListingHome.AuctionListingHomeFragment;
import com.websinception.megastar.UI.home.HomeNavigation;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.GetAuctionSeriesInput;
import com.websinception.megastar.beanInput.GetAuctionSeriesOutput;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;

import static com.websinception.megastar.UI.auction.auctionListingHome.AuctionListingHomeFragment.JOINED;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuctionSeriesListingFragment extends BaseFragment {


    public static AuctionSeriesListingFragment newInstance(int flag, int type, int seriesStatus) {
        Bundle args = new Bundle();
        args.putInt("flag", flag); // draft/Auction
        args.putInt("type", type); // my / All
        args.putInt("seriesStatus", seriesStatus); // live / result / upcoming
        AuctionSeriesListingFragment fragment = new AuctionSeriesListingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    public static final int FIXTURE = 1, LIVE = 2, COMPLETED = 3;
    private int type, flag, seriesStatus;// type=home or My Contest | flag = Draft or Auction | seriesStstus = live, upcoming & Result
    private IUserInteractor mInteractor;
    private Call<GetAuctionSeriesOutput> getAuctionSeriesCall;
    private Loader loader;


    private AuctionSeriesListAdapter mAuctionSeriesListAdapter;
    private ArrayList<GetAuctionSeriesOutput.DataBean.RecordsBean> mDataBeanArrayList = new ArrayList<>();


    @Override
    public int getLayout() {
        return R.layout.fragment_auction_listing;
    }

    @Override
    public void init() {
        type = getArguments().getInt("type");
        flag = getArguments().getInt("flag");
        seriesStatus = getArguments().getInt("seriesStatus");
        mInteractor = new UserInteractor();
        loader = new Loader(getCurrentView());
        mAuctionSeriesListAdapter = new AuctionSeriesListAdapter(this,
                mDataBeanArrayList,
                type,
                flag,
                seriesStatus);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAuctionSeriesListAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        getData();
    }

    private void getData() {
        if (flag == 1) {
            apiCallingForAuction();
        } else if (flag == 2) {
            apiCallingForSnackDraft();
        }
    }

    private void apiCallingForAuction() {
        mDataBeanArrayList.clear();
        mAuctionSeriesListAdapter.notifyDataSetChanged();
        if (!NetworkUtils.isNetworkConnected(getContext())) {
            swipeRefreshLayout.setRefreshing(false);
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
        } else {
            loader.start();
            GetAuctionSeriesInput getAuctionSeriesInput = new GetAuctionSeriesInput();
            getAuctionSeriesInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());

            getAuctionSeriesInput.setParams("SeriesName,SeriesGUID,StatusID,SeriesStartDate,Status,SeriesID,SeriesMatchStartDate,SeriesEndDateUTC");
            getAuctionSeriesInput.setDraftAuctionPlay("Yes");
            getAuctionSeriesInput.setIsPlayRounds("Yes");
            getAuctionSeriesInput.setIsPlayerOrTimeAvl("Yes");
//            getAuctionSeriesInput.setFilter("AddDays");
            switch (seriesStatus) {
                case FIXTURE:
                    getAuctionSeriesInput.setOrderBy("SeriesStartDate");
                    getAuctionSeriesInput.setSequence("ASC");
                    getAuctionSeriesInput.setStatus("Pending");
                    break;
                case LIVE:
                    getAuctionSeriesInput.setOrderBy("SeriesStartDate");
                    getAuctionSeriesInput.setSequence("ASC");
                    getAuctionSeriesInput.setStatus("Running");
                    break;
                case COMPLETED:
                    getAuctionSeriesInput.setOrderBy("RoundStartDate");
                    getAuctionSeriesInput.setSequence("DESC");
                    getAuctionSeriesInput.setStatus("Completed");
                    break;
            }
            if (type == JOINED) {
                getAuctionSeriesInput.setMyJoinedSeries("Yes");
            }
            getAuctionSeriesCall = mInteractor.getAuctionSeries(getAuctionSeriesInput, new IUserInteractor.OnGetAuctionSeriesResponseListener() {
                @Override
                public void onSuccess(GetAuctionSeriesOutput getAuctionSeriesOutput) {
                    if (getAuctionSeriesCall == null || getAuctionSeriesCall.isCanceled()) {
                        return;
                    }
                    if (swipeRefreshLayout != null && loader != null) {
                        swipeRefreshLayout.setRefreshing(false);
                        loader.hide();
                        if (getAuctionSeriesOutput.getData().getRecords() != null && getAuctionSeriesOutput.getData().getRecords().size() != 0) {
                            mDataBeanArrayList.addAll(getAuctionSeriesOutput.getData().getRecords());
                            mAuctionSeriesListAdapter.notifyDataSetChanged();
                        } else {
                            Drawable unwrappedDrawable = getContext().getResources().getDrawable(R.drawable.not_found_img);
                            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                            DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
                            loader.setNotFoundImage(wrappedDrawable);
                            if (type == AuctionListingHomeFragment.ALL) {
                                switch (seriesStatus) {
                                    case FIXTURE:
                                        loader.dataNotFound("No upcoming auction found.");
                                        break;
                                    case LIVE:
                                        loader.dataNotFound("No live auction found.");
                                        break;
                                    case COMPLETED:
                                        loader.dataNotFound("No completed auction found.");
                                        break;
                                }
                            } else {
                                switch (seriesStatus) {
                                    case FIXTURE:
                                        loader.error("Sorry, you don't have any contest for upcoming series.");
                                        break;
                                    case LIVE:
                                        loader.error("Sorry, you don't have any contest for live series. ");
                                        break;
                                    case COMPLETED:
                                        loader.error("Sorry, you don't have any contest for completed series.");
                                        break;
                                }
                                loader.getTryAgainView().setVisibility(View.GONE);
                                loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.join_auction));
                                loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HomeNavigation.start(getActivity());
                                    }
                                });
                            }
                        }
                    }

                }

                @Override
                public void onError(String errorMsg) {
                    if (getAuctionSeriesCall == null || getAuctionSeriesCall.isCanceled()) {
                        return;
                    }
                    if (swipeRefreshLayout != null && loader != null) {
                        swipeRefreshLayout.setRefreshing(false);
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

                }
            });
        }
    }


    private void apiCallingForSnackDraft() {
        mDataBeanArrayList.clear();
        mAuctionSeriesListAdapter.notifyDataSetChanged();
        if (!NetworkUtils.isNetworkConnected(getContext())) {
            swipeRefreshLayout.setRefreshing(false);
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
        } else {
            loader.start();
            GetAuctionSeriesInput getAuctionSeriesInput = new GetAuctionSeriesInput();
            getAuctionSeriesInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionSeriesInput.setOrderBy("SeriesStartDate");
            getAuctionSeriesInput.setSequence("ASC");
            getAuctionSeriesInput.setParams("SeriesName,SeriesGUID,StatusID,SeriesStartDate,Status,SeriesID,SeriesMatchStartDate,SeriesEndDateUTC");
            getAuctionSeriesInput.setDraftAuctionPlay("Yes");
            switch (seriesStatus) {
                case FIXTURE:
                    getAuctionSeriesInput.setStatus("Pending");
                    break;
                case LIVE:
                    getAuctionSeriesInput.setStatus("Running");
                    break;
                case COMPLETED:
                    getAuctionSeriesInput.setStatus("Completed");
                    break;
            }
            if (type == JOINED) {
                getAuctionSeriesInput.setMyJoinedSeries("Yes");
            }
            getAuctionSeriesCall = mInteractor.getSnackDraftSeries(getAuctionSeriesInput, new IUserInteractor.OnGetAuctionSeriesResponseListener() {
                @Override
                public void onSuccess(GetAuctionSeriesOutput getAuctionSeriesOutput) {
                    if (getAuctionSeriesCall == null || getAuctionSeriesCall.isCanceled()) {
                        return;
                    }
                    if (swipeRefreshLayout != null && loader != null) {
                        swipeRefreshLayout.setRefreshing(false);
                        loader.hide();
                        if (getAuctionSeriesOutput.getData().getRecords() != null && getAuctionSeriesOutput.getData().getRecords().size() != 0) {
                            mDataBeanArrayList.addAll(getAuctionSeriesOutput.getData().getRecords());
                            mAuctionSeriesListAdapter.notifyDataSetChanged();
                        } else {
                            Drawable unwrappedDrawable = getContext().getResources().getDrawable(R.drawable.not_found_img);
                            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                            DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
                            loader.setNotFoundImage(wrappedDrawable);
                            if (type == AuctionListingHomeFragment.ALL) {
                                switch (seriesStatus) {
                                    case FIXTURE:
                                        loader.dataNotFound("No upcoming draft found.");
                                        break;
                                    case LIVE:
                                        loader.dataNotFound("No live draft found.");
                                        break;
                                    case COMPLETED:
                                        loader.dataNotFound("No completed draft found.");
                                        break;
                                }
                            } else {
                                switch (seriesStatus) {
                                    case FIXTURE:
                                        loader.error("Sorry, you don't have any contest for upcoming match.");
                                        break;
                                    case LIVE:
                                        loader.error("Sorry, you don't have any contest for live match.");
                                        break;
                                    case COMPLETED:
                                        loader.error("Sorry, you don't have any contest for completed match.");
                                        break;
                                }
                                loader.getTryAgainView().setVisibility(View.GONE);
                                loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.join_draft));
                                loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HomeNavigation.start(getActivity());
                                    }
                                });
                            }
                        }
                    }

                }

                @Override
                public void onError(String errorMsg) {
                    if (getAuctionSeriesCall == null || getAuctionSeriesCall.isCanceled()) {
                        return;
                    }
                    if (swipeRefreshLayout != null && loader != null) {
                        swipeRefreshLayout.setRefreshing(false);
                        loader.hide();
                        loader.getTryAgainView().setVisibility(View.GONE);
                        loader.error(errorMsg);
                        loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                        loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                    }

                }
            });
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mAuctionSeriesListAdapter = null;
        loader = null;
        if (getAuctionSeriesCall != null) {
            if (!getAuctionSeriesCall.isCanceled()) {
                getAuctionSeriesCall.cancel();
            }
            getAuctionSeriesCall = null;
        }

    }
}
