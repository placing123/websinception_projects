package com.mw.fantasy.UI.changeTeamName;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.beanInput.UpdateProfileInput;
import com.mw.fantasy.beanOutput.ResponseUpdateProfile;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;


import retrofit2.Call;

/**
 *
 */

public class ChangeTeamNamePresenterImpl implements IChangeTeamNamePresenter {

    ChangeTeamNameView mChangeTeamNameView;
    IUserInteractor mIChangeTeamNameViewInteractor;
    Call<ResponseUpdateProfile> ChangeTeamNameViewCallback = null;

    public ChangeTeamNamePresenterImpl(ChangeTeamNameView mChangeTeamNameView, IUserInteractor mIChangeTeamNameViewInteractor) {
        this.mChangeTeamNameView = mChangeTeamNameView;
        this.mIChangeTeamNameViewInteractor = mIChangeTeamNameViewInteractor;
    }

    public void ChangeTeamNameViewCancel() {
        if (ChangeTeamNameViewCallback != null && !ChangeTeamNameViewCallback.isExecuted()) {
            ChangeTeamNameViewCallback.cancel();
        }
    }



    @Override
    public void actionUpdateProfile(UpdateProfileInput mUpdateProfileInput) {

        if (!NetworkUtils.isNetworkConnected(mChangeTeamNameView.getContext())) {
            mChangeTeamNameView.hideLoading();
            mChangeTeamNameView.onUpdateFailure(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mChangeTeamNameView.showLoading();
            mIChangeTeamNameViewInteractor.updateProfile(mUpdateProfileInput, new IUserInteractor.OnResponseUpdateProfileListener() {
                @Override
                public void onSuccess(ResponseUpdateProfile responseUpdate) {
                    if (responseUpdate != null) {
                        mChangeTeamNameView.showLoading();
                        mChangeTeamNameView.onUpdateSuccess(responseUpdate);
                    } else {
                        mChangeTeamNameView.hideLoading();
                        mChangeTeamNameView.onUpdateFailure(Constant.NULL_DATA_MESSAGE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mChangeTeamNameView.hideLoading();
                    mChangeTeamNameView.onUpdateFailure(errorMsg);
                }
            });
        }
    }

}
