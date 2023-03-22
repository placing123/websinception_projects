package com.mw.fantasy.UI.inviteFriends;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.outsideEvents.OutSideEvent;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanOutput.ResponseReferralDetails;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;

public class InviteFriendsActivity extends BaseActivity {

    private Context context;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;

    public static void start(Context context) {
        Intent starter = new Intent(context, InviteFriendsActivity.class);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    @OnClick(R.id.back)
    void onBackClick() {

        onBackPressed();
    }

    @OnClick(R.id.invite_code)
    void copyCode() {

        String getstring = invite_code.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", getstring);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }


    @BindView(R.id.invite_code)
    CustomTextView invite_code;

    @OnClick(R.id.howItWorks)
    void howItWorks() {
        OutSideEvent.start(this, "HOW_IT_WORKS", Constant.HOW_IT_WORKS_URL);
    }

   /* @OnClick(R.id.rulesOfFairPlay)
    void onRulesOfFairPlay() {
        OutSideEvent.start(this, "RULES_OF_FAIRPLAY", Constant.FAIR_PLAY_URL);
    }*/

    @OnClick(R.id.inviteFriendsMore)
    void onShareClick() {
        apiCallGetReferralsBonusDetails();

    }

    @Override
    public int getLayout() {
        return R.layout.activity_invite_friends;
    }

    @Override
    public void init() {
        context = this;
        progressDialog = new ProgressDialog(context);
        invite_code.setText(AppSession.getInstance().
                getLoginSession().getData().getReferralCode());
        apiCallGetReferralsBonusDetails();
    }


    private void apiCallGetReferralsBonusDetails() {
        if (NetworkUtils.isConnected(context)) {
            progressDialog.show();
            new UserInteractor().getReferralDetail(new IUserInteractor.OnReferralDetailListener() {
                @Override
                public void onSuccess(ResponseReferralDetails user) {
                    progressDialog.dismiss();
                    if (user.getData() != null) {
                        AppUtils.shareTextUrl(context, AppUtils.getStrFromRes(R.string.inviteFriendsMore),
                                "Here's Rs." + user.getData().getReferToBonus() + " to play " + AppUtils.getStrFromRes(R.string.app_name) + " Cricket with me on " + AppUtils.getStrFromRes(R.string.app_name) + ". Click https://www.demo.com to download the " + AppUtils.getStrFromRes(R.string.app_name) + " app and use my code " +
                                        AppSession.getInstance().
                                                getLoginSession().getData().getReferralCode() + " to register.",
                                AppUtils.getStrFromRes(R.string.app_name));
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    progressDialog.dismiss();
                    alertDialog = new AlertDialog(context,
                            errorMsg,
                            "Retry",
                            "Cancel",
                            new AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    alertDialog.hide();
                                    apiCallGetReferralsBonusDetails();
                                }

                                @Override
                                public void onNoClick() {
                                    alertDialog.hide();
                                }
                            });
                    alertDialog.show();
                }
            });
        } else {
            alertDialog = new AlertDialog(context,
                    getString(R.string.network_error),
                    "Retry",
                    "Cancel",
                    new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            alertDialog.hide();
                            apiCallGetReferralsBonusDetails();
                        }

                        @Override
                        public void onNoClick() {
                            alertDialog.hide();
                        }
                    });
            alertDialog.show();
        }
    }

}
