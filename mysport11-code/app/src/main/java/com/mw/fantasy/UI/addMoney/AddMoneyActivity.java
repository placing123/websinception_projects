package com.mw.fantasy.UI.addMoney;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gocashfree.cashfreesdk.CFPaymentService;
import com.google.gson.Gson;
import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.chooseMoney.ChooseMoneyCallback;
import com.mw.fantasy.UI.chooseMoney.ChooseMoneyOptionsFragment;
import com.mw.fantasy.UI.matchContest.MatchContestActivity;
import com.mw.fantasy.UI.myAccount.MyAccountActivity;
import com.mw.fantasy.UI.payTm.PayTmPresenterImpl;
import com.mw.fantasy.UI.payTm.PayTmView;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.base.Loader;
import com.mw.fantasy.beanInput.CheckPhonePeTransactionStatusInput;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.PaytmInput;
import com.mw.fantasy.beanInput.PromoCodeInput;
import com.mw.fantasy.beanInput.PromoCodeListInput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.PromoCodeListOutput;
import com.mw.fantasy.beanOutput.PromoCodeResponse;
import com.mw.fantasy.beanOutput.ResponsePayTmDetails;
import com.mw.fantasy.customView.CustomEditText;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.ActivityUtils;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;
import com.mw.fantasy.utility.OnItemClickListener;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.phonepe.intent.sdk.api.PhonePe;
import com.phonepe.intent.sdk.api.PhonePeInitException;
import com.phonepe.intent.sdk.api.ShowPhonePeCallback;
import com.phonepe.intent.sdk.api.TransactionRequest;
import com.phonepe.intent.sdk.api.TransactionRequestBuilder;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_NOTIFY_URL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;
import static com.mw.fantasy.AppConfiguration.MAIN_URL;


public class AddMoneyActivity extends BaseActivity implements AddMoneyView, PayTmView, ChooseMoneyCallback, PaymentResultListener {

    public static final String TAG = "AddMoneyActivity : ";
    AddMoneyPresenterImpl mPresenterImpl;
    ChooseMoneyOptionsFragment chooseMoneyOptionsFragment;
    private LinearLayoutManager layoutManager;
    @BindString(R.string.add_cash)
    String mResStringForgotPassword;
    //boolean isPhonePeInstalled = false;

    /* Butter Knife : view mapping */

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.edt_amount)
    CustomEditText mCustomEditTextAmount;
    @BindView(R.id.title)
    CustomTextView mCustomTextViewTitle;
    @BindView(R.id.haveACode)
    CustomTextView haveACode;
    boolean isCodeValid = false;
    @BindView(R.id.ctv_add_cash)
    CustomTextView mCustomTextViewAddCash;
    @BindView(R.id.wireTransfer)
    CustomTextView wireTransfer;
    @BindView(R.id.ll_promocode)
    LinearLayout ll_promocode;

    @BindView(R.id.ctv_100)
    CustomTextView ctv_100;
    @BindView(R.id.ctv_200)
    CustomTextView ctv_200;
    @BindView(R.id.ctv_300)
    CustomTextView ctv_300;
    @BindView(R.id.promo_code)
    CustomEditText promo_code;
    @BindView(R.id.ll_promo_succses)
    LinearLayout ll_promo_succses;
    @BindView(R.id.couponCode_ctv)
    CustomTextView couponCode_ctv;
    @BindView(R.id.cashbonus_ctv)
    CustomTextView cashbonus_ctv;
    @BindView(R.id.promocode_list)
    RecyclerView mRecyclerView_promocode_list;


    String offerCode = "";
    int paybleAmount = 0;
    private Context mContext;
    private Loader loader;
    private ProgressDialog mProgressDialog;
    public PromoListAdapter mPromoListAdapter;
    List<PromoCodeListOutput.DataBean.RecordsBean> mRecordsBean;
    private String couponGUID = null;
    String walletId = "";
    String transactionID = "";
    String matchId = "";
    String statusId = "";
    private List<PromoCodeListOutput.DataBean.RecordsBean> mPromoList = new ArrayList<>();
    private String promoCode = "";
    public static final int REQUEST_CODE_PHONE_PE = 9021;


 /*   private void checkIfPhonePeExists() {
        try {
            isPhonePeInstalled = PhonePe.isAppInstalled();
        } catch (PhonePeInitException e) {
            e.printStackTrace();
        }
    }*/


    public static void start(Context context) {
        Intent starter = new Intent(context, AddMoneyActivity.class);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    public static void start(Context context, String offerCodeStr) {
        Intent starter = new Intent(context, AddMoneyActivity.class);
        starter.putExtra("offerCode", offerCodeStr);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    public static void start(Context context, String matchId, String statusId) {
        Intent starter = new Intent(context, AddMoneyActivity.class);
        starter.putExtra("matchId", matchId);
        starter.putExtra("statusId", statusId);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);
    }

    @OnClick(R.id.back)
    void onBackClick() {
        finish();
    }

    @OnClick(R.id.applypromo)
    void applyPromoCodeClick() {
        String amount = mCustomEditTextAmount.getText().toString().trim();
        String code = promoCode;

        if (TextUtils.isEmpty(amount)) {
            showSnackBar(AppUtils.getStrFromRes(R.string.err_amount_empty));
            return;
        }

        if (TextUtils.isEmpty(code)) {
            showSnackBar("Enter Promo Code");
            return;
        }

        PromoCodeInput promoCodeInput = new PromoCodeInput();
        promoCodeInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        promoCodeInput.setAmount(amount);
        promoCodeInput.setCouponCode(code);

        mPresenterImpl.promoCodeBtn(promoCodeInput);

    }

    @OnClick(R.id.ctv_add_cash)
    public void AddCash(View view) {

       /* if(AppSession.getInstance().getLoginSession().getData().getPhoneStatus()!=null){
            if (!AppSession.getInstance().getLoginSession().getData().getPhoneStatus().equals(Constant.Verified)){

                VerifyAccountActivity.start(mContext);
                showSnackBar(AppUtils.getStrFromRes(R.string.verify_your_mobile));
                return;
            }
        }

        if (!AppSession.getInstance().getLoginSession().getData().getEmailStatus().equals(Constant.Verified)){

            VerifyAccountActivity.start(mContext);

            showSnackBar(AppUtils.getStrFromRes(R.string.verify_your_email));
            return;
        }*/

    /*    String amount = mCustomEditTextAmount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            showSnackBar(AppUtils.getStrFromRes(R.string.err_amount_empty));
            return;
        }*/

        /*if (TextUtils.isEmpty(AppSession.getInstance().getLoginSession().getData().getFirstName())) {

            AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.empty_name));
            Intent starter = new Intent(getActivity(), PersonalDetailsActivity.class);
            startActivityForResult(starter, 111);

            return;
        }*/
/*        try {
            if (Double.parseDouble(amount) <= 0) {
                showSnackBar(AppUtils.getStrFromRes(R.string.err_amount_empty));
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            showSnackBar(AppUtils.getStrFromRes(R.string.err_amount_empty));
            return;
        }



        apiCallGetUserProfile();*/


        try {
            String amount = mCustomEditTextAmount.getText().toString().trim();
            if (TextUtils.isEmpty(amount)) {
                AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.err_amount_empty));
            } else if (Double.parseDouble(amount) <= 0) {
                AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.err_amount_empty));
            } else {
                paybleAmount = Integer.valueOf(amount);
                chooseMoneyOptionsFragment = new ChooseMoneyOptionsFragment();
                try {
                    PhonePe.shouldShow(new ShowPhonePeCallback() {
                        @Override
                        public void onResponse(boolean show) {
                            chooseMoneyOptionsFragment.setPhonePeInstalled(show);
                            chooseMoneyOptionsFragment.initCallback(AddMoneyActivity.this);
                            chooseMoneyOptionsFragment.show(getSupportFragmentManager(), chooseMoneyOptionsFragment.getTag());
                        }
                    });
                } catch (PhonePeInitException e) {
                    e.printStackTrace();
                    chooseMoneyOptionsFragment.setPhonePeInstalled(false);
                    chooseMoneyOptionsFragment.initCallback(this);
                    chooseMoneyOptionsFragment.show(getSupportFragmentManager(), chooseMoneyOptionsFragment.getTag());
                }
                //apiCallGetUserProfile();
            }
        } catch (Exception e) {
            AppUtils.showToast(mContext, e.getMessage());
        }

      /*  paybleAmount = Integer.valueOf(amount);
        chooseMoneyOptionsFragment = new ChooseMoneyOptionsFragment();
        chooseMoneyOptionsFragment.initCallback(this);
        chooseMoneyOptionsFragment.show(getSupportFragmentManager(), chooseMoneyOptionsFragment.getTag());*/
        // chooseMoneyOptionsFragment.startPayment();
//           mChangePasswordPresenterImpl.actionChangePasswordBtn(AppSession.getInstance().getLoginSession().getResponse().getLoginSessionKey(),amount,amount,amount);
    }


    private void apiCallGetUserProfile(final int type) {
        if (!NetworkUtils.isNetworkConnected(this)) {
            AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mProgressDialog.show();
            LoginInput mLoginInput = new LoginInput();
            mLoginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            mLoginInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
            mLoginInput.setParams(Constant.GET_PROFILE_PARAMS);
            new UserInteractor().viewProfile(mLoginInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut responseLogin) {
                    mProgressDialog.dismiss();
                    if (!responseLogin.getData().getPhoneStatus().equals(Constant.Verified)) {
                        AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.pleaceVerifyMobile));
                    } else if (!responseLogin.getData().getEmailStatus().equals(Constant.Verified)) {
                        AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.pleaceVerifyEmail));
                    } else {
                        apiCallGetTransactionDetails(mCustomEditTextAmount.getText().toString().trim(),
                                responseLogin.getData().getPhoneNumber(),
                                responseLogin.getData().getEmail(),
                                responseLogin.getData().getFirstName(), type);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    AppUtils.showToast(mContext, errorMsg);
                }
            });
        }
    }


    private void apiCallGetTransactionDetails(String amount, String phoneNo, String email, String firstname, final int type) {
        if (!NetworkUtils.isNetworkConnected(this)) {
            AppUtils.showToast(mContext, AppUtils.getStrFromRes(R.string.network_error));
        } else {
            mProgressDialog.show();
            PaytmInput paytmInput = new PaytmInput();
            paytmInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            if (type == 1) {
                paytmInput.setPaymentGateway(Constant.CASHFREE);
            } else if (type == 2) {
                paytmInput.setPaymentGateway(Constant.RAZORPAY);
            } else if (type == 3) {
                paytmInput.setPaymentGateway(Constant.PAYTM);
            } else if (type == 4) {
                paytmInput.setPaymentGateway(Constant.PHONE_PE);
            }
            paytmInput.setRequestSource(Constant.MOBILE);
            paytmInput.setAmount(amount);
            paytmInput.setFirstName(firstname);
            paytmInput.setEmail(email);
            paytmInput.setPhoneNumber(phoneNo);
            if (couponGUID != null) {
                paytmInput.setCouponGUID(couponGUID);
            }
            new UserInteractor().payTm(paytmInput, new IUserInteractor.OnPayTmResponseListener() {
                @Override
                public void onSuccess(ResponsePayTmDetails user) {
                    mProgressDialog.dismiss();
                    initiatePayment(user, type);

                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    AppUtils.showToast(mContext, errorMsg);
                }
            });
        }


    }

    private void initiatePayment(final ResponsePayTmDetails responseLogin, int type) {
        String orderId = responseLogin.getData().getOrderID() + "";
        walletId = orderId;
        transactionID = responseLogin.getData().getTransactionID();
        String orderAmount = responseLogin.getData().getAmount() + "";
        String customerPhone = responseLogin.getData().getCustomerPhone();
        String customerEmail = responseLogin.getData().getCustomerEmail();
        String customerName = responseLogin.getData().getCustomerName();

        if (type == 1) {
            String stage = responseLogin.getData().getPaymentMode();
            String token = responseLogin.getData().getPaymentToken();
            String appId = responseLogin.getData().getAppID();

            String orderNote = "(Android)Order against-->" + new Gson().toJson(responseLogin);


            Map<String, String> params = new HashMap<>();
            params.put(PARAM_APP_ID, appId);
            params.put(PARAM_ORDER_ID, orderId);
            params.put(PARAM_ORDER_AMOUNT, orderAmount);
            params.put(PARAM_ORDER_NOTE, orderNote);
            params.put(PARAM_CUSTOMER_NAME, customerName);
            params.put(PARAM_CUSTOMER_PHONE, customerPhone);
            params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
            params.put(PARAM_NOTIFY_URL, responseLogin.getData().getNotifyURL());


            CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
            cfPaymentService.setOrientation(0);
            try {
                cfPaymentService.doPayment(getActivity(), params, token, stage);
            } catch (Exception e) {
                e.printStackTrace();
                AppUtils.showToast(mContext, e.getMessage());
            }
        } else if (type == 2) {
            try {
                final Checkout co = new Checkout();
                JSONObject node = new JSONObject();
                node.put("OrderID", orderId);
                node.put("UserID", String.valueOf(AppSession.getInstance().getLoginSession().getData().getUserID()));
                JSONObject options = new JSONObject();
                options.put("name", customerName);
                options.put("description", "Add Cash");
                options.put("currency", "INR");
                options.put("amount", responseLogin.getData().getAmount());
                options.put("notes", node);
                JSONObject preFill = new JSONObject();
                preFill.put("email", customerEmail);
                preFill.put("contact", customerPhone);
                options.put("prefill", preFill);
                co.open(this, options);

            } catch (Exception e) {
                AppUtils.showToast(mContext, e.getMessage());
            }
        } else if (type == 3) {

            PaytmPGService Service = PaytmPGService.getProductionService();
            final HashMap<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("CALLBACK_URL", responseLogin.getData().getCallbackURL());
            paramMap.put("CHANNEL_ID", responseLogin.getData().getChannelID());
            paramMap.put("CUST_ID", responseLogin.getData().getCustomerID());
            paramMap.put("INDUSTRY_TYPE_ID", responseLogin.getData().getIndustryTypeID());
            paramMap.put("MID", responseLogin.getData().getMerchantID());
            paramMap.put("ORDER_ID", String.valueOf(responseLogin.getData().getOrderID()));
            paramMap.put("TXN_AMOUNT", String.valueOf(responseLogin.getData().getAmount()));
            paramMap.put("WEBSITE", responseLogin.getData().getWebsite());
            paramMap.put("CHECKSUMHASH", responseLogin.getData().getCheckSumHash());


            Log.d("CALLBACKURL", " CALLBACKURL: " + responseLogin.getData().getCallbackURL());
            PaytmOrder Order = new PaytmOrder(paramMap);
            Service.initialize(Order, null);
            Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
                @Override
                public void onTransactionResponse(Bundle inResponse) {

                    Log.d("LOG", "Payment Transaction : " + inResponse);
                    Log.d("PAYfantasy", "inResponse:  " + inResponse);
                    JSONObject jsonResponce = new JSONObject();
                    Set<String> keys = inResponse.keySet();
                    try {
                        // json.put(key, bundle.get(key)); see edit below
                        for (String key : keys) {
                            jsonResponce.put(key, inResponse.get(key));

                            Log.d(key, inResponse.get(key) + "");
                        }
                        jsonResponce.put("CUST_ID", responseLogin.getData().getCustomerID());
                    } catch (JSONException e) {
                        //Handle exception here
                    }

                    String paymentGatwayStatus = "";
                    if (inResponse.getString("STATUS").equals("TXN_FAILURE")) {
                        paymentGatwayStatus = "Failed";
                    }
                    if (inResponse.getString("STATUS").equals("TXN_SUCCESS")) {
                        paymentGatwayStatus = "Success";
                    }
                    Log.d("inResponse", "Payment Transaction : " + jsonResponce.toString());
                    MyAccountActivity.isRefresh = true;

                    PaytmInput paytmInput = new PaytmInput();
                    paytmInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
                    paytmInput.setPaymentGatewayResponse(jsonResponce.toString());
                    paytmInput.setPaymentGateway(Constant.PAYTM);
                    paytmInput.setPaymentGatewayStatus(paymentGatwayStatus);
                    paytmInput.setWalletID(String.valueOf(responseLogin.getData().getOrderID()));
                    //mPayTmPresenterImpl.actionPayTmResponseBtn(paytmInput);
                    apiCallUpdateRazorPayTransaction("", "", Constant.Success, paytmInput);
                }

                @Override
                public void networkNotAvailable() {
                    showSnackBar(AppUtils.getStrFromRes(R.string.network_error));
                    apiCallUpdateRazorPayTransaction(Constant.PAYTM, "", Constant.Failed, null);

                }

                @Override
                public void clientAuthenticationFailed(String inErrorMessage) {
                    showSnackBar(inErrorMessage);
                    apiCallUpdateRazorPayTransaction(Constant.PAYTM, "", Constant.Failed, null);
                }

                @Override
                public void someUIErrorOccurred(String inErrorMessage) {
                    showSnackBar(AppUtils.getStrFromRes(R.string.EXCEPTION_MESSAGE));
                }

                @Override
                public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                    Log.d("PAYfantasy", "iniErrorCode:  " + iniErrorCode);
                    Log.d("PAYfantasy", "inErrorMessage: " + inErrorMessage);
                    Log.d("PAYfantasy", "inFailingUrl: " + inFailingUrl);
                }

                @Override
                public void onBackPressedCancelTransaction() {
                    Log.d("iniErrorCode", "onBackPressedCancelTransaction");
                }

                @Override
                public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                    Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                    Log.d("PAYfantasy", inErrorMessage);
                    showSnackBar("Payment Transaction Canceled ");
                    apiCallUpdateRazorPayTransaction(Constant.PAYTM, "", Constant.Failed, null);
                }
            });

        } else if (type == 4) {
            HashMap<String, String> headers = new HashMap();
        /*    headers.put("X-CALLBACK-URL","https://www.demoMerchant.com");  // Merchant server URL
            headers.put("X-CALL-MODE","POST");*/
            TransactionRequest debitRequest = new TransactionRequestBuilder()
                    .setData(responseLogin.getData().getBase64Body())
                    .setChecksum(responseLogin.getData().getChecksum())
                    .setUrl("/v4/debit")
                    .build();
            try {
                startActivityForResult(PhonePe.getTransactionIntent(debitRequest), REQUEST_CODE_PHONE_PE);
            } catch (PhonePeInitException e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                apiCallUpdateRazorPayTransaction(Constant.PHONE_PE, "", Constant.Failed, null);

            }
        }
    }







    /*private void initiatePayment(ResponsePayTmDetails responseLogin) {

        String stage = responseLogin.getData().getPaymentMode();
        String token = responseLogin.getData().getPaymentToken();
        String appId = responseLogin.getData().getAppID();
        String orderId =responseLogin.getData().getOrderID()+"";
        String orderAmount = responseLogin.getData().getAmount() + "";
        String orderNote = "(Android)Order against-->" + new Gson().toJson(responseLogin);
        String customerName = responseLogin.getData().getCustomerName();
        String customerPhone = responseLogin.getData().getCustomerPhone();
        String customerEmail = responseLogin.getData().getCustomerEmail();

        Map<String, String> params = new HashMap<>();
        params.put(PARAM_APP_ID, appId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(PARAM_NOTIFY_URL, responseLogin.getData().getNotifyURL());


        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(this, 0);
        cfPaymentService.doPayment(this, params,token, stage);

       *//* try {
     *//**//* cfPaymentService.doPayment(mContext, params, token, new CFClientInterface() {
                @Override
                public void onSuccess(Map<String, String> map) {
                    onBackPressed();
                }

                @Override
                public void onFailure(Map<String, String> map) {
                    onBackPressed();
                }

                @Override
                public void onNavigateBack() {

                }
            }, stage);*//**//*
        } catch (JSONException e) {
            e.printStackTrace();
            AppUtils.showToast(mContext, e.getMessage());
        }*//*

    }*/


    @OnClick(R.id.wireTransfer)
    void onWireTransferClick() {

        //  WireTransferBanks.start(mContext, mCustomEditTextAmount.getText().toString(), offerCode);
    }

    @OnClick(R.id.ctv_100)
    public void add100(View view) {
        AddCash("200");

    }
    /* Butter Knife : view mapping */

    @OnClick(R.id.ctv_200)
    public void add200(View view) {

        AddCash("400");
    }

    @OnClick(R.id.ctv_300)
    public void add300(View view) {

        AddCash("600");
    }

    private void AddCash(String am) {
        mCustomEditTextAmount.setText(am);
       /* String amount = mCustomEditTextAmount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            mCustomEditTextAmount.setText(am);
        } else {
            mCustomEditTextAmount.setText("" + (Integer.parseInt(amount) + Integer.parseInt(am)));
        }*/

    }

    @Override
    public int getLayout() {
        return R.layout.activity_add_money;
    }

    @Override
    public void init() {
        PhonePe.init(this);
        loader = new Loader(getActivity());
        mCustomTextViewTitle.setText(mResStringForgotPassword);
        ll_promocode.setVisibility(View.GONE);
        ll_promo_succses.setVisibility(View.GONE);

        ActivityUtils.performActionOnDone(mCustomEditTextAmount, mCustomTextViewAddCash);//handle action done event of keyboard
        mContext = this;
        setActivityBackground();

        mRecordsBean = new ArrayList<>();
        mRecyclerView_promocode_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPromoListAdapter = new PromoListAdapter(R.layout.promo_list_items, getActivity(), mRecordsBean, onWinnerClickCallBack);
        mRecyclerView_promocode_list.setAdapter(mPromoListAdapter);

        if (getIntent().hasExtra("offerCode")) {
            offerCode = getIntent().getStringExtra("offerCode");

            haveACode.setText(AppUtils.getStrFromRes(R.string.offer_code_applied));
            haveACode.setTextColor(ContextCompat.getColor(mContext, R.color.button_green));
            isCodeValid = true;
        }

        matchId = getIntent().getStringExtra("matchId");
        statusId = getIntent().getStringExtra("statusId");

        mPresenterImpl = new AddMoneyPresenterImpl(this, new UserInteractor());

        PromoCodeListInput promoCodeListInput = new PromoCodeListInput();
        promoCodeListInput.setPageNo(0);
        promoCodeListInput.setPageSize(10);
        promoCodeListInput.setStatus("Active");
        mPresenterImpl.actionPromoCodeList(promoCodeListInput);


        mCustomEditTextAmount.setDrawableClickListener(new CustomEditText.DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here
                        mCustomEditTextAmount.setText("");
                        break;

                    default:
                        break;
                }
            }

        });


//        checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkbox.isChecked()) {
//                    ll_promocode.setVisibility(View.VISIBLE);
//                } else {
//                    ll_promocode.setVisibility(View.GONE);
//                }
//            }
//        });
        // checkIfPhonePeExists();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public Activity getActivity() {
        return this;
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
    public void payTmDetailsSuccess(ResponsePayTmDetails responseLogin) {
        finishActivity();

    }

    @Override
    public void payTmDetailsFailure(String errMsg) {

    }

    @Override
    public void payTmResponseSuccess(LoginResponseOut responseLogin) {

        finishActivity();
    }

    @Override
    public void payTmResponseFailure(String errMsg) {

    }


    private OnItemClickListener.OnItemClickCallback onWinnerClickCallBack = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            promoCode = mPromoList.get(position).getCouponCode();
            applyPromoCodeClick();
        }

    };

    @Override
    public void showSnackBar(@NonNull String message) {
        AppUtils.showToast(mContext, message);
    }

    @Override
    public void promoCodeSuccess(PromoCodeResponse mPromoCodeResponse) {

        hideLoading();
        couponGUID = mPromoCodeResponse.getData().getCouponGUID();
        ll_promocode.setVisibility(View.GONE);
        ll_promo_succses.setVisibility(View.VISIBLE);
        mCustomEditTextAmount.setEnabled(false);
        ctv_100.setEnabled(false);
        ctv_200.setEnabled(false);
        ctv_300.setEnabled(false);
//        checkbox.setChecked(true);
//        checkbox.setEnabled(false);
        couponCode_ctv.setText("Coupon Code :   " + mPromoCodeResponse.getData().getCouponCode());

        if (mPromoCodeResponse.getData().getCouponType().equals("Percentage")) {

            double amt = Double.parseDouble(mCustomEditTextAmount.getText().toString().trim());
            double value = (amt * Double.parseDouble(mPromoCodeResponse.getData().getCouponValue())) / 100;
            cashbonus_ctv.setText("Cash Bonus :    " + value);
        } else {
            cashbonus_ctv.setText("Cash Bonus :    " + mPromoCodeResponse.getData().getCouponValue());
        }


    }

    @Override
    public void promoCodeFaliure(String message) {

        ll_promocode.setVisibility(View.GONE);
        ll_promo_succses.setVisibility(View.GONE);
//        checkbox.setChecked(false);
//        checkbox.setEnabled(true);
        hideLoading();
        showSnackBar(message);
    }

    @Override
    public void promocodeListSuccess(PromoCodeListOutput mPromoCodeListOutput) {

        if (mPromoCodeListOutput.getData().getRecords() != null) {
            mPromoList = mPromoCodeListOutput.getData().getRecords();
            mPromoListAdapter.addAllItem(mPromoCodeListOutput.getData().getRecords());
        } else {
            loader.setNotFoundImage(getContext().getResources().getDrawable(R.drawable.not_found_img));
            loader.dataNotFound("No Offers Available");
        }

    }

    @Override
    public void promocodeListFailure(String msg) {

        showSnackBar(msg);
    }

    @Override
    public void onProfileSuccess(LoginResponseOut responseLogin) {

    }

    @Override
    public void onProfileFailure(String errMsg) {

    }

    @Override
    public void setActivityBackground() {
        //  ActivityUtils.setActivityBackground(mContext, R.drawable.app_bg);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void finishActivity() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onPaymentgatewaySelection(int type) {
        chooseMoneyOptionsFragment.dismiss();
        apiCallGetUserProfile(type);
    }

    @Override
    public void onPaymentComplete(String s) {
        try {
            if (chooseMoneyOptionsFragment != null) {
                chooseMoneyOptionsFragment.dismiss();
                chooseMoneyOptionsFragment = null;
                finishActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getOrderId(String orderId) {

        walletId = orderId;
    }

    @Override
    public int getAmount() {
        return paybleAmount;
    }

    @Override
    public String getCouponGUID() {
        return couponGUID;
    }

    @Override
    public String getCode() {
        return offerCode;
    }

    @Override
    public void payUMoneyResponseSuccess(LoginResponseOut responseLogin) {
        showSnackBar(responseLogin.getMessage());
        finishActivity();
    }

    @Override
    public void payUMoneyResponseFailure(String errMsg) {
        showSnackBar(errMsg);
    }

    @Override
    public void changePasswordSuccess(LoginResponseOut responseLogin) {

    }

    @Override
    public void changePasswordFailure(String errMsg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* if (requestCode == ValidateCodeActivity.REQUEST_CODE_VALIDATE && resultCode == Activity.RESULT_OK) {


            if (data.hasExtra("offerCode")) {
                offerCode = data.getStringExtra("offerCode");
            }

            haveACode.setText(AppUtils.getStrFromRes(R.string.offer_code_applied));
            haveACode.setTextColor(ContextCompat.getColor(mContext, R.color.button_green));
            isCodeValid = true;
        }*/
       /* if (chooseMoneyOptionsFragment != null)
            chooseMoneyOptionsFragment.onActivityResult(requestCode, resultCode, data);


        // Result Code is -1 send from Payumoney activity
        Log.i(TAG, "request code " + requestCode + " resultcode " + resultCode);
        MyAccountActivity.isRefresh = true;
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT &&
                resultCode == Activity.RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();
                Log.i(TAG, "" + "Payu's Data : " + payuResponse);
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    showSnackBar("Success Transaction.");
                    finishActivity();
                } else {
                    //Failure Transaction
                    showSnackBar("Failure Transaction.");
                }
            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
                finishActivity();
            }
        }
*/
        if (requestCode == REQUEST_CODE_PHONE_PE) {
            apiCallCheckPhonePeTransaction(walletId, transactionID);
        } else {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    if (bundle.getString("txStatus").equals("SUCCESS")) {
                        mProgressDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                                finishActivity();
                            }
                        }, 5000);
                    } else {
                        apiCallUpdateRazorPayTransaction(Constant.CASHFREE, bundle.getString("txStatus"), Constant.Failed, null);
                    }
                } else {
                    apiCallUpdateRazorPayTransaction(Constant.CASHFREE, "", Constant.Failed, null);
                }

            }
        }


    }


    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {


        try {
            Toast.makeText(getContext(), "Payment Successful ", Toast.LENGTH_SHORT).show();

          /*  Log.d(TAG, "onPaymentSuccess: " + razorpayPaymentID);
            mRazorPayPresenterImpl.actionRazorPay(AppSession.getInstance().getLoginSession().getResponse().getLoginSessionKey(),
                    razorpayPaymentID, AppSession.getInstance().getLoginSession().getResponse().getUserId());*/

           /* confirmPayment(razorpayPaymentID, Constant.Success);
            onPaymentComplete("Payment Successful ");*/
            apiCallUpdateRazorPayTransaction(Constant.RAZORPAY, razorpayPaymentID, Constant.Success, null);


        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {

        try {
            if (code == 0) {
                Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
            }

            apiCallUpdateRazorPayTransaction(Constant.RAZORPAY, String.valueOf(code), Constant.Failed, null);
            //confirmPayment(String.valueOf(code), Constant.Failed);
            Log.i(TAG, "onPaymentError: " + response + ", code: " + code);
            //onPaymentComplete("Payment Failed ");

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }

    }

    void confirmPayment(String razorpayPaymentID, String status) {
        MyAccountActivity.isRefresh = true;

        PayTmPresenterImpl mPayTmPresenterImpl = new PayTmPresenterImpl(this, new UserInteractor());

        PaytmInput paytmInput = new PaytmInput();
        paytmInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());

        paytmInput.setPaymentGateway(Constant.RAZORPAY);
        paytmInput.setPaymentGatewayStatus(status);
        paytmInput.setWalletID(walletId);
        paytmInput.setAmount(String.valueOf(getAmount()));
        paytmInput.setRazor_payment_id(razorpayPaymentID);
        mPayTmPresenterImpl.actionPayTmResponseBtn(paytmInput);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void apiCallUpdateRazorPayTransaction(String paymentGatway, String razorpayPaymentID, final String status, PaytmInput pi) {
        mProgressDialog.show();
        PaytmInput paytmInput = null;
        if (pi == null) {
            paytmInput = new PaytmInput();
            paytmInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            paytmInput.setPaymentGateway(paymentGatway);
            paytmInput.setPaymentGatewayStatus(status);
            paytmInput.setWalletID(walletId);
            paytmInput.setAmount(String.valueOf(getAmount()));
            paytmInput.setRazor_payment_id(razorpayPaymentID);
        } else {
            paytmInput = pi;
        }

        new UserInteractor().payTmResponse(paytmInput, new IUserInteractor.OnResponseListener() {
            @Override
            public void onSuccess(LoginResponseOut user) {
                mProgressDialog.dismiss();
                if (status.equals(Constant.Success)) {
                    if (matchId != null && statusId != null) {
                        if (!matchId.equals("") && !statusId.equals("")) {
                            MatchContestActivity.start(mContext, matchId, statusId);
                            finishActivity();
                        }
                    } else {
                        MyAccountActivity.start(mContext);
                        finishActivity();
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                mProgressDialog.dismiss();
                if (status.equals(Constant.Success)) {
                    if (matchId != null && statusId != null) {
                        if (!matchId.equals("") && !statusId.equals("")) {
                            MatchContestActivity.start(mContext, matchId, statusId);
                            finishActivity();
                        }
                    } else {
                        MyAccountActivity.start(mContext);
                        finishActivity();
                    }
                }
            }
        });
    }

    private void apiCallCheckPhonePeTransaction(String orderId, String transactionId) {
        mProgressDialog.show();
        CheckPhonePeTransactionStatusInput checkPhonePeTransactionStatusInput = new CheckPhonePeTransactionStatusInput();
        checkPhonePeTransactionStatusInput.setOrderId(orderId);
        checkPhonePeTransactionStatusInput.setTransactionId(transactionId);
        checkPhonePeTransactionStatusInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        new UserInteractor().checkPhonePeTransactionStatus(checkPhonePeTransactionStatusInput, new IUserInteractor.checkPhonePeTransactionStatusResponseListener() {
            @Override
            public void onSuccess(String status) {
                mProgressDialog.dismiss();
                switch (status) {
                    case "Pending":
                        Toast.makeText(mContext, "Your Transaction is under process!", Toast.LENGTH_LONG).show();
                        finishActivity();
                        break;
                    case "Failed":
                        Toast.makeText(mContext, "Your Transaction is failed!", Toast.LENGTH_LONG).show();
                        finishActivity();
                        break;
                    case "Success":
                        Toast.makeText(mContext, "Your Transaction is successful!", Toast.LENGTH_LONG).show();
                        if (matchId != null && statusId != null) {
                            if (!matchId.equals("") && !statusId.equals("")) {
                                MatchContestActivity.start(mContext, matchId, statusId);
                                finishActivity();
                            }
                        } else {
                            MyAccountActivity.start(mContext);
                            finishActivity();
                        }
                        break;
                    default:
                        Toast.makeText(mContext, getString(R.string.exception), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(String errorMsg) {
                mProgressDialog.dismiss();
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_LONG).show();
                finishActivity();
            }
        });
    }

}
