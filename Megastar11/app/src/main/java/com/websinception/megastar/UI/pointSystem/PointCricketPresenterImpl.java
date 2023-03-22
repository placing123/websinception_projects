package com.websinception.megastar.UI.pointSystem;


import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.beanInput.PointsSystem;
import com.websinception.megastar.beanOutput.PointsList;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.NetworkUtils;

import retrofit2.Call;

/**
 * Created by hp on 06-07-2017.
 */

public class PointCricketPresenterImpl implements IPointsCricketPresenter {

    PointsFragmentView mView;
    IUserInteractor mImyProfileParentInteractor;
    private Call<PointsList> responseLoginCall;

    public PointCricketPresenterImpl(PointsFragmentView mView, IUserInteractor mImyProfileParentInteractor) {
        this.mView = mView;
        this.mImyProfileParentInteractor = mImyProfileParentInteractor;
    }



    public void actionLoginCancel() {
        if (responseLoginCall != null && !responseLoginCall.isExecuted())
            responseLoginCall.cancel();
    }



    @Override
    public void getPointList(PointsSystem system, String type) {
        actionLoginCancel();
        if (!NetworkUtils.isNetworkConnected(mView.getContext())) {
            mView.hideLoading();
            mView.onLoadingError(AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mView.showLoading();
            responseLoginCall = mImyProfileParentInteractor.getPointList(type,system, new IUserInteractor.OnPointsResponseListener() {

                @Override
                public void onSuccess(PointsList mNotificationBean) {
                    mView.onLoadingSuccess(mNotificationBean);

                }

                @Override
                public void onError(String errorMsg) {
                    mView.hideLoading();
                        mView.onLoadingError(errorMsg);

                }

                @Override
                public void onNotFound(String error) {

                        mView.onLoadingError(error);

                }
            });
        }
    }


}
