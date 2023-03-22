package com.websinception.megastar.UI.contestDetailLeaderBoard.leaderBoard;

import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.ContestUserInput;
import com.websinception.megastar.beanInput.MyTeamInput;
import com.websinception.megastar.beanOutput.ContestUserOutput;
import com.websinception.megastar.beanOutput.SingleTeamOutput;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;


import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class LeaderBoardPresenterImpl implements ILeaderBoardPresenter {

    LeaderBoardView mView;
    IUserInteractor mInteractor;
    Call<ContestUserOutput> responseContestCall;
    private Call<SingleTeamOutput> responseCall;

    public LeaderBoardPresenterImpl(LeaderBoardView mView, IUserInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    public void actionListingCancel() {
        if (responseContestCall != null && !responseContestCall.isExecuted())
            responseContestCall.cancel();
    }

    public void actionCancel() {
        if (responseCall != null && !responseCall.isExecuted())
            responseCall.cancel();
    }


    @Override
    public void joinedUsers(final ContestUserInput mContestUserInput) {
        actionListingCancel();
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            if (mView.isLayoutAdded()) {
                if (mContestUserInput.getPageNo() == 1) {
                    mView.onHideLoading();
                    mView.onLoadingError(AppUtils.getStrFromRes(R.string.network_error));
                } else {
                    mView.onHideScrollLoading();
                    mView.onScrollLoadingError(AppUtils.getStrFromRes(R.string.network_error));
                }
            }
        } else {
            if (mContestUserInput.getPageNo() == 1) {
                mView.onShowLoading();
            } else {
                mView.onShowScrollLoading();
            }
            responseContestCall = mInteractor.getJoinedContestsUsers(mContestUserInput, new IUserInteractor.OnResponseRanksListener() {
                @Override
                public void onSuccess(ContestUserOutput response) {
                    if (mView.isLayoutAdded()) {
                        if (mContestUserInput.getPageNo() == 1) {
                            mView.onHideLoading();
                            mView.onLoadingSuccess(response);
                        } else {
                            mView.onHideScrollLoading();
                            mView.onScrollLoadingSuccess(response);
                        }
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (mView.isLayoutAdded()) {
                        if (mContestUserInput.getPageNo() == 1) {
                            mView.onHideLoading();
                            mView.onLoadingError(errorMsg);
                        } else {
                            mView.onHideScrollLoading();
                            mView.onScrollLoadingError(errorMsg);
                        }
                    }
                }

                @Override
                public void onNotFound(String error) {
                    if (mView.isLayoutAdded()) {
                        if (mContestUserInput.getPageNo() == 1) {
                            mView.onHideLoading();
                            mView.onLoadingNotFound(error);
                        } else {
                            mView.onHideScrollLoading();
                            mView.onScrollLoadingNotFound(error);
                        }
                    }
                }
            });
        }
    }


    @Override
    public void getUsersTeam(final MyTeamInput mContestUserInput) {
        actionCancel();
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            if (mView.isLayoutAdded()) {
                mView.onHideLoading();
                mView.onLoadingError(AppUtils.getStrFromRes(R.string.network_error));
            }

        } else {
            mView.onShowLoading();

            responseCall = mInteractor.getSingleUserTeams(mContestUserInput, new IUserInteractor.OnResponseSingleTeamsListener() {


                @Override
                public void onError(String errorMsg) {
                    if (mView.isLayoutAdded()) {
                        mView.onHideLoading();
                        mView.onTeamError(errorMsg);

                    }
                }

                @Override
                public void onSuccess(SingleTeamOutput responseTeams) {
                    if (mView.isLayoutAdded()) {
                        mView.onHideLoading();
                        mView.onTeamSuccess(responseTeams);

                    }
                }

                @Override
                public void onNotFound(String error) {
                    if (mView.isLayoutAdded()) {
                        mView.onHideLoading();
                        mView.onTeamError(error);
                    }
                }
            });
        }
    }
}
