package com.mw.fantasy.UI.pointSystem;

import android.content.Context;

import com.mw.fantasy.beanOutput.PointsList;

/**
 *
 */

public interface PointsFragmentView {
    void showLoading();

    void hideLoading();

    void onShowLoading();

    void onHideLoading();

    void onLoadingSuccess(PointsList responseMatches);

    void onLoadingError(String value);

    void onLoadingNotFound(String value);

    void onShowSnackBar(String message);

    boolean isLayoutAdded();

    Context getContext();

}
