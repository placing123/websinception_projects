package com.websinception.megastar.UI.draft.matchListing;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.home.HomeNavigation;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.MatchListInput;
import com.websinception.megastar.beanInput.MyContestMatchesInput;
import com.websinception.megastar.beanOutput.CheckContestBean;
import com.websinception.megastar.beanOutput.MatchResponseOut;
import com.websinception.megastar.beanOutput.MyContestMatchesOutput;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.NetworkUtils;
import com.websinception.megastar.utility.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DraftMatchListingFragment extends BaseFragment {


    @BindView(R.id.srl_root)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_match_list)
    RecyclerView mRecyclerView;

    private int pageNo = 0;
    private int pageSize = 10;
    private UserInteractor mUserInteractor;
    private DraftMatchAdapter mDraftMatchAdapter;
    private ArrayList<MatchResponseOut.DataBean.RecordsBean> mRecordsBeanArrayList_ALL = new ArrayList<>();
    private ArrayList<MyContestMatchesOutput.DataBean.RecordsBean> mRecordsBeanArrayList_MY = new ArrayList<>();
    private boolean isLoading = false;
    private boolean isLoadingDone = false;
    private Loader loader;
    private int type, seriesStatus;


    public static DraftMatchListingFragment newInstance(int type, int seriesStatus) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putInt("seriesStatus", seriesStatus);
        DraftMatchListingFragment fragment = new DraftMatchListingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_draft_match_listing;
    }

    @Override
    public void init() {
        type = getArguments().getInt("type");
        seriesStatus = getArguments().getInt("seriesStatus");
        loader = new Loader(getCurrentView());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading ) {
                    mRecordsBeanArrayList_ALL.clear();
                    mRecordsBeanArrayList_MY.clear();
                    mDraftMatchAdapter.notifyDataSetChanged();
                    pageNo = 0;
                    isLoading = false;
                    isLoadingDone = false;
                    apiCallGetM();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        mDraftMatchAdapter = new DraftMatchAdapter(getContext(), mRecordsBeanArrayList_ALL, mRecordsBeanArrayList_MY, type, seriesStatus);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mDraftMatchAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() > (mRecordsBeanArrayList_ALL.size() - 5)) {
                    apiCallGetM();
                }
            }
        });
        mUserInteractor = new UserInteractor();
        apiCallGetM();
    }


    private void apiCallGetM() {
        if (type == 1) {
            apiCallGetAllMatch();
        } else {
            apiCallMyMatch();
        }
    }


    private void apiCallGetAllMatch() {
        if (isLoading || isLoadingDone) {
            return;
        }
        loader.hide();
        if (mRecordsBeanArrayList_ALL.size() == 0) {
            loader.start();
        }
        if (NetworkUtils.isConnected(getContext())) {
            isLoading = true;
            MatchListInput matchListInput = new MatchListInput();
            matchListInput.setPageNo(pageNo + 1);
            matchListInput.setPageSize(pageSize);
            matchListInput.setParams("IsPlayingXINotificationSent,ContestAvailable,SeriesName,TeamPlayersAvailable,MatchType,MatchNo,MatchStartDateTime,MatchDate,MatchTime,CurrentDateTime,TeamNameLocal,TeamNameVisitor,TeamNameShortLocal,TeamNameShortVisitor,TeamFlagLocal,TeamFlagVisitor,MatchLocation,Status,StatusID,MatchScoreDetails,isJoinedContest,SeriesGUID,ContestsAvailable,MatchStartDateTimeUTC,SeriesID");
            matchListInput.setStatus(seriesStatus == 1 ? "Pending" : (seriesStatus == 2 ? "Running" : "Completed"));
            matchListInput.setIsPlayerOrTimeAvl("Yes");
            matchListInput.setFilter("AddDays");
            mUserInteractor.matchesListing(matchListInput, new IUserInteractor.OnResponseMatchesListener() {
                @Override
                public void onSuccess(MatchResponseOut responseMatches) {
                    if (responseMatches.getData().getRecords() != null) {
                        for (MatchResponseOut.DataBean.RecordsBean record : responseMatches.getData().getRecords()) {
                            mRecordsBeanArrayList_ALL.add(record);
                            mDraftMatchAdapter.notifyItemInserted(mRecordsBeanArrayList_ALL.size() - 1);
                        }
                        if (mSwipeRefreshLayout!=null) {
                            mSwipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }
                        pageNo++;
                        isLoading = false;
                        loader.hide();
                    } else {
                        loader.hide();
                        if (mRecordsBeanArrayList_ALL.size() == 0) {
                            loader.error("Contest Not Found");
                            loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    apiCallGetM();
                                }
                            });
                        }
                        isLoadingDone = true;
                        isLoading = false;
                    }

                }

                @Override
                public void onCheckContest(CheckContestBean mJoinedContestBean) {
                    loader.hide();
                    if (mSwipeRefreshLayout!=null) {
                        mSwipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                    isLoading = false;
                }

                @Override
                public void onNotFound(String error) {
                    loader.hide();
                    if (mRecordsBeanArrayList_ALL.size() == 0) {
                        loader.error(error);
                        loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                        loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiCallGetM();
                            }
                        });
                    } else {
                        AppUtils.showToast(getContext(), error);
                    }
                    if (mSwipeRefreshLayout!=null) {
                        mSwipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                    isLoading = false;
                }

                @Override
                public void onError(String errorMsg) {
                    loader.hide();
                    if (mRecordsBeanArrayList_ALL.size() == 0) {
                        loader.error(errorMsg);
                        loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                        loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiCallGetM();
                            }
                        });
                    } else {
                        AppUtils.showToast(getContext(), errorMsg);
                    }
                    if (mSwipeRefreshLayout!=null) {
                        mSwipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                    isLoading = false;
                }

                @Override
                public void OnSessionExpire() {
                    loader.hide();
                    if (mSwipeRefreshLayout!=null) {
                        mSwipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                    isLoading = false;
                }
            });
        } else {
            if (mSwipeRefreshLayout!=null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
            loader.hide();
            if (mRecordsBeanArrayList_ALL.size() == 0) {
                loader.error(AppUtils.getStrFromRes(R.string.network_error));
                loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        apiCallGetM();
                    }
                });
            }
        }


    }


    private void apiCallMyMatch() {
        if (isLoading || isLoadingDone) {
            return;
        }
        loader.hide();
        if (mRecordsBeanArrayList_MY.size() == 0) {
            loader.start();
        }

        if (NetworkUtils.isConnected(getContext())) {
            isLoading = true;
            String statusId = "1";
            MyContestMatchesInput myContestMatchesInput = new MyContestMatchesInput();
            myContestMatchesInput.setPageNo(pageNo + 1);
            myContestMatchesInput.setPageSize(Constant.PAGE_LIMIT);
            myContestMatchesInput.setParams(Constant.JOINEDCONTEST_PARAM+",MatchStartDateTimeUTC,MatchGUID");
            myContestMatchesInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            myContestMatchesInput.setStatus(Constant.Pending);
            myContestMatchesInput.setPrivacy("All");

            myContestMatchesInput.setMyJoinedContest("Yes");
            myContestMatchesInput.setFilter("MyJoinedMatch");

            switch (seriesStatus) {
                case 1:
                    statusId = Constant.Pending;
                    break;
                case 2:
                    statusId = Constant.Running;
                    myContestMatchesInput.setOrderBy("MatchStartDateTime");
                    myContestMatchesInput.setSequence("DESC");
                    break;
                case 3:
                    statusId = Constant.Completed;
                    myContestMatchesInput.setOrderBy("MatchStartDateTime");
                    myContestMatchesInput.setSequence("DESC");
                    break;
            }
            myContestMatchesInput.setStatus(statusId);

            mUserInteractor.myContestMatchesList(myContestMatchesInput, new IUserInteractor.OnResponseMyContestListener() {
                @Override
                public void onSuccess(MyContestMatchesOutput mAllContestOutPut) {
                    if (mAllContestOutPut.getData().getRecords() != null) {
                        for (MyContestMatchesOutput.DataBean.RecordsBean record : mAllContestOutPut.getData().getRecords()) {
                            mRecordsBeanArrayList_MY.add(record);
                            mDraftMatchAdapter.notifyItemInserted(mRecordsBeanArrayList_ALL.size() - 1);
                        }
                        if (mSwipeRefreshLayout!=null) {
                            mSwipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }

                        pageNo++;
                        isLoading = false;
                        loader.hide();
                    } else {
                        loader.hide();
                        if (mRecordsBeanArrayList_MY.size() == 0) {
                            loader.setNotFoundImage(getContext().getResources().getDrawable(R.drawable.not_found_img));
                            loader.dataNotFound("Contest Not Found");

                            loader.error("Contest Not Found");
                            loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.join_contest));

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                loader.getTryAgainView().setBackground(getResources().getDrawable(R.drawable.shedow_yellow_background));
                            }
                            loader.getTryAgainView().setPadding(ViewUtils.dpToPx(20), ViewUtils.dpToPx(10), ViewUtils.dpToPx(20), ViewUtils.dpToPx(10));
                            if (loader.getTryAgainView() != null)
                                loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HomeNavigation.start(getActivity());
                                    }
                                });
                        }
                        isLoadingDone = true;
                        isLoading = false;





                    }
                }

                @Override
                public void onNotFound(String error) {
                    loader.hide();
                    if (mRecordsBeanArrayList_MY.size() == 0) {
                        loader.setNotFoundImage(getContext().getResources().getDrawable(R.drawable.not_found_img));
                        loader.dataNotFound(error);

                        loader.error(error);
                        loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.join_contest));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            loader.getTryAgainView().setBackground(getResources().getDrawable(R.drawable.shedow_yellow_background));
                        }
                        loader.getTryAgainView().setPadding(ViewUtils.dpToPx(20), ViewUtils.dpToPx(10), ViewUtils.dpToPx(20), ViewUtils.dpToPx(10));
                        if (loader.getTryAgainView() != null)
                            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HomeNavigation.start(getActivity());
                                }
                            });
                    } else {
                        AppUtils.showToast(getContext(), error);
                    }
                    if (mSwipeRefreshLayout!=null) {
                        mSwipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                    isLoading = false;
                }

                @Override
                public void onError(String errorMsg) {
                    loader.hide();
                    if (mRecordsBeanArrayList_MY.size() == 0) {
                        loader.error(errorMsg);
                        loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                        loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiCallGetM();
                            }
                        });
                    } else {
                        AppUtils.showToast(getContext(), errorMsg);
                    }
                    if (mSwipeRefreshLayout!=null) {
                        mSwipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                    isLoading = false;
                }
            });
        } else {
            if (mSwipeRefreshLayout!=null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
            loader.hide();
            if (mRecordsBeanArrayList_MY.size() == 0) {
                loader.error(AppUtils.getStrFromRes(R.string.network_error));
                loader.getTryAgainView().setText(AppUtils.getStrFromRes(R.string.try_again));
                loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        apiCallGetM();
                    }
                });
            }
        }

    }

}
