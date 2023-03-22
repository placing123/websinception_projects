package com.mw.fantasy.UI.forgotPassword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mw.fantasy.AppConfiguration;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.loginRagisterModule.Register;
import com.mw.fantasy.UI.resetPassword.ResetPasswordActivity;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanOutput.ForgetPasswordOut;
import com.mw.fantasy.customView.CustomEditText;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.ActivityUtils;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.DataValidationUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordView {

    private Context mContext;
    private ForgotPasswordPresenterImpl mForgotPasswordPresenterImpl;
    private ProgressDialog mProgressDialog;


    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.emailAddress_et)
    CustomEditText mCustomEditTextEmail;

    @BindView(R.id.submit_tv)
    CustomTextView submit_tv;
    String emailAddress = "";

    @OnClick(R.id.back)
    void onBackClick() {

        finish();
    }

    @OnClick(R.id.submit_tv)
    public void forgotPassword() {
        //captcha();
        verifyTokenOnServer("");
    }

    @OnClick(R.id.signUp_tv)
    public void onDontHaveAccount() {

        Register.start(mContext);
    }

    /* Butter Knife : view mapping */

    public static void start(Context context) {
        Intent starter = new Intent(context, ForgotPasswordActivity.class);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public void init() {
        ActivityUtils.performActionOnDone(mCustomEditTextEmail, submit_tv);//handle action done event of keyboard
        mContext = this;
        setActivityBackground();
        mForgotPasswordPresenterImpl = new ForgotPasswordPresenterImpl(this, new UserInteractor());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        if (mForgotPasswordPresenterImpl != null)
            mForgotPasswordPresenterImpl.forgotPasswordCancel();
        super.onDestroy();

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
    public void forgotPasswordSuccess(final ForgetPasswordOut responseLogin) {
        AppUtils.showToast(mContext, responseLogin.getMessage());


        if (DataValidationUtils.isValidEmail(emailAddress)) {
            ResetPasswordActivity.start(getContext(), emailAddress, ResetPasswordActivity.EMAIL);
        } else {
            ResetPasswordActivity.start(getContext(), emailAddress, ResetPasswordActivity.MOBILE);
        }

        finishActivity();

    }

    @Override
    public void forgotPasswordFailure(String errMsg) {
        hideLoading();
        showSnackBar(errMsg);
    }

    @Override
    public void showSnackBar(@NonNull String message) {
        AppUtils.showSnackBar(mContext, mCoordinatorLayout, message);
    }

    @Override
    public void setActivityBackground() {
        ActivityUtils.setActivityBackground(mContext, R.drawable.background);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void finishActivity() {
        finish();
    }

    public void captcha() {
        SafetyNet.getClient(this).verifyWithRecaptcha(AppConfiguration.SAFETY_NET_API_SITE_KEY)
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {

                        if (!response.getTokenResult().isEmpty()) {

                            // Received captcha token
                            // This token still needs to be validated on the server
                            // using the SECRET key
                            verifyTokenOnServer(response.getTokenResult());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;

                        } else {
                        }
                    }
                });
    }

    private void verifyTokenOnServer(String tokenResult) {

        emailAddress = mCustomEditTextEmail.getText().toString().trim();
        if (emailAddress.equals("")) {
            showSnackBar("Please enter email / mobile no.");
        } else {
            LoginInput mLoginInput = new LoginInput();
            mLoginInput.setKeyword(emailAddress);
            mLoginInput.setRequestType("APP");
            mLoginInput.setResponse(tokenResult);
            if (DataValidationUtils.isValidEmail(emailAddress)) {
                mLoginInput.setType("Email");
            } else if (DataValidationUtils.isValidPhoneNumber(emailAddress)) {
                mLoginInput.setType("Phone");
            } else {
                Toast.makeText(mContext, "Please enter valid email/mobile no.", Toast.LENGTH_SHORT).show();
                return;
            }
            mForgotPasswordPresenterImpl.actionForgotPasswordBtn(mLoginInput);
        }

    }


}
