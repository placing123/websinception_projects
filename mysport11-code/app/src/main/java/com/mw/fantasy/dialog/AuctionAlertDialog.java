package com.mw.fantasy.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.utility.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuctionAlertDialog {
    public static final int margin = 35;
    private Context context;
    private AlertDialog alert;
    private OnOkayBtnClickListener onOkayBtnClickListener;
    @BindView(R.id.dm_message)
    CustomTextView dm_message;


    @BindView(R.id.dm_cancel)
    CustomTextView dm_cancel;


    @BindView(R.id.ctv_game_type)
    CustomTextView ctvImageType;

    @BindView(R.id.dm_okay)
    CustomTextView dm_okay;

    @OnClick(R.id.dm_okay)
    void onOkayClick() {
        hide();
        if (onOkayBtnClickListener != null) {
            onOkayBtnClickListener.onClick();
        }
    }
    @OnClick(R.id.dm_cancel)
    void dm_cancelClick() {
        hide();
        if (onOkayBtnClickListener != null) {
            onOkayBtnClickListener.onCancel();
        }
    }


    public AuctionAlertDialog(Context context,String message,String okay,OnOkayBtnClickListener onOkayBtnClickListener){
        this.context = context;
        this.onOkayBtnClickListener = onOkayBtnClickListener;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater _inflater = LayoutInflater.from(context);
        View g = _inflater.inflate(R.layout.auction_dialog_message, null);
        ButterKnife.bind(this, g);
        builder.setView(g, margin, margin, margin, margin);
        setTitle(context);
        alert = builder.create();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setWindowAnimations(R.style.AlertDialogTheme);
        dm_cancel.setVisibility(View.GONE);
        dm_okay.setText(okay);
        dm_message.setText(message);
    }

    private void setTitle(Context context) {
        if (AppSession.getInstance().getPlayMode() == 1){
            ctvImageType.setText("Auction");
        }else {
            ctvImageType.setText("Gully Fantasy");
        }

    }


    public AuctionAlertDialog(Context context,String message,String okay,String cancel,OnOkayBtnClickListener onOkayBtnClickListener){

        this.context = context;
        this.onOkayBtnClickListener = onOkayBtnClickListener;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater _inflater = LayoutInflater.from(context);
        View g = _inflater.inflate(R.layout.auction_dialog_message, null);
        ButterKnife.bind(this, g);
        builder.setView(g, margin, margin, margin, margin);
        setTitle(context);
        alert = builder.create();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setWindowAnimations(R.style.AlertDialogTheme);
        dm_okay.setText(okay);
        dm_message.setText(message);
        if (cancel==null||cancel.trim().equals("")) {
            dm_cancel.setVisibility(View.GONE);
        }else {
            dm_cancel.setVisibility(View.VISIBLE);
            dm_cancel.setText(cancel);
        }
    }


    public void show() {
        if (alert != null) {
            alert.show();
            ViewUtils.hideKeyboard(context);
            alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        }
    }

    public void hide() {
        if (alert.isShowing()) {
            alert.dismiss();
            ViewUtils.hideKeyboard(context);
            alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }


    public void setMessage(String message) {
        dm_message.setText(message);
    }

    public interface OnOkayBtnClickListener {
        void onClick();
        void onCancel();
    }

    public void setOnOkayBtnClickListener(OnOkayBtnClickListener onOkayBtnClickListener) {
        this.onOkayBtnClickListener = onOkayBtnClickListener;
    }
}