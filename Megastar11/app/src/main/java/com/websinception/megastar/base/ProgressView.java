package com.websinception.megastar.base;

import android.content.Context;

/**
 * Created by mobiweb on 25/1/18.
 */

public interface ProgressView {
    void onProgressShow();

    void onProgressHide();

    void onProgressError(String value);

    void onProgressSuccess(Object response);

    Context getContext();
}
