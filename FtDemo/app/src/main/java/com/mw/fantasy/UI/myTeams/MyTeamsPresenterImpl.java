package com.mw.fantasy.UI.myTeams;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.MyTeamInput;
import com.mw.fantasy.beanInput.SwitchTeamInput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.MyTeamOutput;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;


import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class MyTeamsPresenterImpl implements IMyTeamsPresenter {

    MyTeamsView view;
    IUserInteractor mInteractor;
    Call<MyTeamOutput> responseMatchesCall;

    public MyTeamsPresenterImpl(MyTeamsView view, IUserInteractor mInteractor) {
        this.view = view;
        this.mInteractor = mInteractor;
    }

    public void actionListingCancel() {
        if (responseMatchesCall != null && !responseMatchesCall.isExecuted())
            responseMatchesCall.cancel();
    }

    @Override
    public void actionTeamList(MyTeamInput myTeamInput) {
        actionListingCancel();
        if (!NetworkUtils.isNetworkConnected(view.getContext())) {
            if (view.isLayoutAdded()) {
                view.onHideLoading();
                view.onLoadingError(AppUtils.getStrFromRes(R.string.network_error));
            }
        } else {
            view.onShowLoading();
            responseMatchesCall = mInteractor.teamList(myTeamInput, new IUserInteractor.OnResponseTeamsListener() {
                @Override
                public void onSuccess(MyTeamOutput response) {
                    if (view.isLayoutAdded()) {
                        view.onHideLoading();
                        view.onLoadingSuccess(response);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (view.isLayoutAdded()) {
                        view.onHideLoading();
                        view.onLoadingError(errorMsg);
                    }
                }

                @Override
                public void onNotFound(String error) {
                    if (view.isLayoutAdded()) {
                        view.onHideLoading();
                        view.onLoadingNotFound(error);
                    }
                }
            });
        }
    }

    @Override
    public void actionSwitchBtn(SwitchTeamInput switchTeamInput) {
        if (!NetworkUtils.isNetworkConnected(view.getContext())) {
            view.hideLoading();
            view.onSwitchError(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            view.showLoading();
            mInteractor.switchTeam(switchTeamInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    if (responseLogin != null) {
                        view.hideLoading();
                        view.onSwitchSuccess(responseLogin);
                    } else {
                        view.hideLoading();
                        view.onSwitchError(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    view.hideLoading();
                    view.onSwitchError(errorMsg);
                }

            });
        }
    }

}
