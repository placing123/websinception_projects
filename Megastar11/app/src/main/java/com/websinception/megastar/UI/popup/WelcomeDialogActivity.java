package com.websinception.megastar.UI.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.versionUpdate.UpdateVersionView;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.AlertDialog;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeDialogActivity extends BaseActivity implements UpdateVersionView {

    private Context mContext;

    private ProgressDialog mProgressDialog;

    /* Butter Knife : view mapping */

    String downloadLink = "";

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;


    @BindView(R.id.ctv_save)
    CustomTextView mCustomTextViewSave;

    @BindView(R.id.head_mesge)
    CustomTextView head_mesge;


    @BindView(R.id.img_view)
    ImageView img_view;

    @BindView(R.id.ctv_message)
    CustomTextView ctvMessage;
    private String message, notificationtype, notificationID;


    @OnClick(R.id.ctv_save)
    public void save(android.view.View view) {
        Intent intent = new Intent();
        intent.putExtra("notificationID", notificationID);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.iv_cross)
    public void cancel(android.view.View view) {
        Intent intent = new Intent();
        intent.putExtra("notificationID", notificationID);
        setResult(RESULT_OK, intent);
        finish();
    }


    /* Butter Knife : view mapping */

    public static void start(Context context, String message, String notificationtype, String notificationID) {
        Intent starter = new Intent(context, WelcomeDialogActivity.class);
        starter.putExtra("message", message);
        starter.putExtra("notificationtype", notificationtype);
        starter.putExtra("notificationID", notificationID);
        ((Activity) context).startActivityForResult(starter, 1001);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }


    @Override
    public int getLayout() {
        return R.layout.dialog_welcome;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init() {
        mContext = this;
        setActivityBackground();
        if (getIntent() != null && getIntent().hasExtra("notificationID"))
            notificationID = getIntent().getStringExtra("notificationID");
        if (getIntent() != null && getIntent().hasExtra("notificationtype"))
            notificationtype = getIntent().getStringExtra("notificationtype");
        if (getIntent() != null && getIntent().hasExtra("message"))
            message = getIntent().getStringExtra("message");
        ctvMessage.setText(Html.fromHtml(message));


        if (notificationtype.equalsIgnoreCase("Welcome to fantasy!")) {
            head_mesge.setText(getString(R.string.welcome));
            head_mesge.setBackgroundColor(getResources().getColor(R.color.white));
            img_view.setImageDrawable(getResources().getDrawable(R.drawable.cash_image));
        } else if (notificationtype.equalsIgnoreCase("Referred Bonus Added")) {
            head_mesge.setText(getString(R.string.bravo));
            head_mesge.setBackgroundColor(getResources().getColor(R.color.very_light_blue));
            img_view.setImageDrawable(getResources().getDrawable(R.drawable.refreal));
        } else {
            head_mesge.setText(getString(R.string.awesome));
            head_mesge.setBackgroundColor(getResources().getColor(R.color.yellow));
            img_view.setImageDrawable(getResources().getDrawable(R.drawable.cash_image));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null) mProgressDialog.dismiss();

    }

    @Override
    public void updateSuccess(LoginResponseOut responseLogin) {

    }

    @Override
    public void updateFailure(String errMsg) {

    }


    AlertDialog alertExitDialog;


    @Override
    public void showSnackBar(@NonNull String message) {
        AppUtils.showSnackBar(mContext, mCoordinatorLayout, message);
    }

    @Override
    public void setActivityBackground() {
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void finishActivity() {
        finish();
    }


    private void onShowSnackBar(String message) {
        AppUtils.showToast(getApplicationContext(), message);
    }
}
