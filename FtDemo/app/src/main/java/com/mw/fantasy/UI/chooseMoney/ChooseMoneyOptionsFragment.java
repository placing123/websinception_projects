package com.mw.fantasy.UI.chooseMoney;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mw.fantasy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseMoneyOptionsFragment extends BottomSheetDialogFragment {

    private ChooseMoneyCallback chooseMoneyCallback;
    private ChooseMoneyPresenter chooseMoneyPresenter;
    private View mView;
    private boolean isPhonePeInstalled= false;

    @BindView(R.id.pud_phone_pe)
    View pud_phone_pe;

    public void setPhonePeInstalled(boolean phonePeInstalled) {
        isPhonePeInstalled = phonePeInstalled;
    }

    @OnClick(R.id.paytm)
    void paytm() {

        chooseMoneyCallback.onPaymentgatewaySelection(3);
       /*if (chooseMoneyPresenter!=null)

            chooseMoneyPresenter.actionPaytmBtn(ChooseMoneyPresenter.REQUEST_CODE_PAYTM);*/
    }


    @OnClick(R.id.cashfree)
    void cashfree() {

        chooseMoneyCallback.onPaymentgatewaySelection(1);
       /*if (chooseMoneyPresenter!=null)

            chooseMoneyPresenter.actionPaytmBtn(ChooseMoneyPresenter.REQUEST_CODE_PAYTM);*/
    }

    @OnClick(R.id.pud_razorPay)
    void razorPay() {
        chooseMoneyCallback.onPaymentgatewaySelection(2);
       /* if (chooseMoneyPresenter!=null)

            chooseMoneyPresenter.actionPaytmBtn(ChooseMoneyPresenter.REQUEST_CODE_RAZORPAY);*/
    }

    @OnClick(R.id.pud_bank)
    void wireTransfer() {

    }

    @OnClick(R.id.pud_phone_pe)
    void payUMoney() {
        chooseMoneyCallback.onPaymentgatewaySelection(4);
    }

    public void initCallback(ChooseMoneyCallback videoPickCallback) {
        chooseMoneyCallback = videoPickCallback;
        chooseMoneyPresenter = new ChooseMoneyPresenter(videoPickCallback);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_choose_payment_options, container, false);
        ButterKnife.bind(this, mView);
        if (isPhonePeInstalled) {
            pud_phone_pe.setVisibility(View.VISIBLE);
        }
        return mView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (chooseMoneyPresenter != null)
            chooseMoneyPresenter.onActivityResult(requestCode, resultCode, data);

    }

    public void startPayment() {
        if (chooseMoneyPresenter != null)
            chooseMoneyPresenter.actionPaytmBtn(ChooseMoneyPresenter.REQUEST_CODE_RAZORPAY);
        else
            dismiss();
    }
}
