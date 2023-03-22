package com.mw.fantasy.UI.createTeam;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.PlayersInput;
import com.mw.fantasy.beanOutput.PlayersOutput;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;


import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class CreateTeamPresenterImpl implements ICreateTeamPresenter {

    CreateTeamView mView;
    IUserInteractor mInteractor;
    Call<PlayersOutput> responseMatchesCall;

    public CreateTeamPresenterImpl(CreateTeamView mView, IUserInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    public void actionListingCancel() {
        if (responseMatchesCall != null && !responseMatchesCall.isExecuted())
            responseMatchesCall.cancel();
    }

    @Override
    public void actionMatchPlayers(PlayersInput mPlayersInput) {
        actionListingCancel();
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            if (mView.isLayoutAdded()) {
                mView.onHideLoading();
                mView.onLoadingError(AppUtils.getStrFromRes(R.string.network_error));
            }
        } else {
            mView.onShowLoading();
            responseMatchesCall = mInteractor.getPlayers(mPlayersInput, new IUserInteractor.OnResponseMatchPlayersListener() {
                @Override
                public void onSuccess(PlayersOutput response) {
                    if (mView.isLayoutAdded()) {
                        mView.onHideLoading();
                        mView.onLoadingSuccess(response);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (mView.isLayoutAdded()) {
                        mView.onHideLoading();
                        mView.onLoadingError(errorMsg);
                    }
                }

                @Override
                public void onNotFound(String error) {
                    if (mView.isLayoutAdded()) {
                        mView.onHideLoading();
                        mView.onLoadingNotFound(error);
                    }
                }
            });
        }
    }




}
