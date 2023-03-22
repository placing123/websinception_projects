package com.mw.fantasy.UI.subscription;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanInput.BuySubscriptionInput;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanOutput.GetSubscriptionResponse;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import butterknife.BindView;

public class SubscriptionListActivity extends BaseActivity {

    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    private ArrayList<GetSubscriptionResponse.RecordBean> subsriptionsList = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.cardViewSubscriptionInfoRoot)
    CardView cardViewSubscriptionInfoRoot;

    @BindView(R.id.customTextViewNoSubscriptionLabel)
    CustomTextView customTextViewNoSubscriptionLabel;

    @BindView(R.id.linearLayoutCurrentSubscriptionInfo)
    LinearLayout linearLayoutCurrentSubscriptionInfo;

    @BindView(R.id.customTextViewSubscriptionDetails)
    CustomTextView customTextViewSubscriptionDetails;

    @BindView(R.id.customTextViewSubscriptionDays)
    CustomTextView customTextViewSubscriptionDays;


    public static void start(Context context) {
        Intent starter = new Intent(context, SubscriptionListActivity.class);
        context.startActivity(starter);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_subscription_list;
    }

    @Override
    public void init() {
        mProgressDialog = new ProgressDialog(this);
        apiCallGetSubsriptionsList();
    }

    private void apiCallGetSubsriptionsList() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            LoginInput loginInput = new LoginInput();
            loginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            new UserInteractor().getSubscription(loginInput, new IUserInteractor.GetSubscriptionResponseListener() {

                @Override
                public void onSuccess(GetSubscriptionResponse.DataBean data) {
                    mProgressDialog.dismiss();
                    subsriptionsList.clear();
                    if (data.getRecords() != null) {
                        subsriptionsList.addAll(data.getRecords());
                    }
                    if (data.isSubscriptionsStatus()) {
                        if (data.getSubscription() != null && data.getSubscription().getID() != null) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                            try {
                                customTextViewSubscriptionDetails.setText("Subscription valid till : " + new SimpleDateFormat("dd MMM, yyyy, hh:mm:ss a").format(simpleDateFormat.parse(data.getSubscriptionEnd())));
                            } catch (ParseException e) {
                                e.printStackTrace();
                                customTextViewSubscriptionDetails.setText("Subscription valid till : " + data.getSubscriptionEnd());
                            }
                            if (Integer.parseInt(data.getSubscription().getDays().trim()) == 1) {
                                customTextViewSubscriptionDays.setText(data.getSubscription().getDays() + " Day");
                            } else {
                                customTextViewSubscriptionDays.setText(data.getSubscription().getDays() + " Days");
                            }
                            linearLayoutCurrentSubscriptionInfo.setVisibility(View.VISIBLE);
                            customTextViewNoSubscriptionLabel.setVisibility(View.GONE);
                            cardViewSubscriptionInfoRoot.setVisibility(View.VISIBLE);
                        } else {
                            cardViewSubscriptionInfoRoot.setVisibility(View.GONE);
                        }
                    } else {
                        linearLayoutCurrentSubscriptionInfo.setVisibility(View.GONE);
                        customTextViewNoSubscriptionLabel.setVisibility(View.VISIBLE);
                        cardViewSubscriptionInfoRoot.setVisibility(View.VISIBLE);
                    }

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(SubscriptionListActivity.this, LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.setAdapter(new SubscriptionsListAdapter(subsriptionsList, SubscriptionListActivity.this));
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(SubscriptionListActivity.this,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            apiCallGetSubsriptionsList();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                        }
                    });
                    mAlertDialog.show();
                }

            });
        } else {
            mAlertDialog = new AlertDialog(this, AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            apiCallGetSubsriptionsList();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
            mAlertDialog.show();
        }

    }


    public void buySubscription(final int positin) {
        mAlertDialog = new AlertDialog(this,
                "Are you sure want to buy subscription?",
                "Yes",
                "No",
                new AlertDialog.OnBtnClickListener() {
                    @Override
                    public void onYesClick() {
                        mAlertDialog.hide();
                        BuySubscriptionInput buySubscriptionInput = new BuySubscriptionInput();
                        buySubscriptionInput.setID(subsriptionsList.get(positin).getID());
                        buySubscriptionInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
                        apiCallingBuySubscription(buySubscriptionInput);
                    }

                    @Override
                    public void onNoClick() {
                        mAlertDialog.hide();
                    }
                });
        mAlertDialog.show();
    }

    private void apiCallingBuySubscription(final BuySubscriptionInput buySubscriptionInput) {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            new UserInteractor().buySubscription(buySubscriptionInput, new IUserInteractor.BuySubscriptionResponseListener() {
                @Override
                public void onSuccess(String msg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(SubscriptionListActivity.this,
                            msg,
                            AppUtils.getStrFromRes(R.string.okay),
                            "", new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            finish();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }
                    });
                    mAlertDialog.show();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(SubscriptionListActivity.this,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.okay),
                            "", new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                        }
                    });
                    mAlertDialog.show();
                }
            });
        } else {
            mAlertDialog = new AlertDialog(this, AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            apiCallingBuySubscription(buySubscriptionInput);
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                        }
                    });
            mAlertDialog.show();
        }
    }
}