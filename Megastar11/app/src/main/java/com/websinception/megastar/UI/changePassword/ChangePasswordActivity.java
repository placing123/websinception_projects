package com.websinception.megastar.UI.changePassword;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanInput.ChangePasswordInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.customView.CustomInputEditText;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class ChangePasswordActivity extends BaseActivity implements ChangePasswordView {

    ChangePasswordInput mChangePasswordInput;
    ChangePasswordPresenterImpl mPresenter;
    @BindView(R.id.rl_root)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.cet_old_pwd)
    CustomInputEditText mOldPassword;
    @BindView(R.id.cet_new_pwd)
    CustomInputEditText mNewPassword;
    @BindView(R.id.cet_confirm_pwd)
    CustomInputEditText mConfirmPassword;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    public static void start(Context context) {
        Intent starter = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(starter);
    }

    @OnClick(R.id.back)
    public void onBackClick(){
        finish();
    }

    @OnClick(R.id.cet_change_pwd_btn)
    public void changePasswordBtnOnClick() {

        String oldPwd = mOldPassword.getText().toString().trim();
        String newPwd = mNewPassword.getText().toString().trim();
        String confirmPwd = mConfirmPassword.getText().toString().trim();

        mChangePasswordInput.setCurrentPassword(oldPwd);
        mChangePasswordInput.setPassword(newPwd);
        mChangePasswordInput.setConfirmPassword(confirmPwd);
        mChangePasswordInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());

        mPresenter.submitAction(mChangePasswordInput);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    public void init() {
        mChangePasswordInput = new ChangePasswordInput();
        mContext = this;
        mPresenter = new ChangePasswordPresenterImpl(this, new UserInteractor());

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // mToolbar.setNavigationIcon(R.drawable.ic_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }


    @Override
    public void showLoading() {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null) mProgressDialog.dismiss();

    }


    @Override
    public void loginSuccess(LoginResponseOut responseChangePassword) {
        hideLoading();
        //Toast.makeText(mContext, responseChangePassword.getMessage(), Toast.LENGTH_SHORT).show();

        AppUtils.showToast(mContext, responseChangePassword.getMessage());

        finish();

    }

    @Override
    public void loginFailure(String errMsg) {
        hideLoading();
        showSnackBar(errMsg);
    }

    @Override
    public void showSnackBar(String message) {
        AppUtils.showSnackBar(mContext, mRelativeLayout, message);

    }

    @Override
    public Context getContext() {
        return mContext;
    }

}
