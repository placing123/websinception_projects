package com.websinception.megastar.UI.SpinWheel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


import com.websinception.megastar.R;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.ViewUtils;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WinningDialog {
    public static final int margin = 35;
    private final String amount;
    private Context context;
    private AlertDialog alert;
    private OnSubmitClickListener mOnSubmitClickListener;

    @BindView(R.id.winningInfo)
    CustomTextView winningInfo;

    @BindView(R.id.iv_close)
    CustomTextView iv_close;

    @BindView(R.id.image)
    ImageView image;

    @OnClick(R.id.iv_close)
    void closeBtnClick() {
            mOnSubmitClickListener.onClick(amount);
            hide();

    }




    public WinningDialog(Context context,
                         OnSubmitClickListener onSubmitClickListener,
                         String winning_amt) {
        this.context = context;
        this.mOnSubmitClickListener = onSubmitClickListener;
        amount = winning_amt;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater _inflater = LayoutInflater.from(context);
        View g = _inflater.inflate(R.layout.dialog_winner, null);
        ButterKnife.bind(this, g);
        builder.setView(g, margin, margin, margin, margin);
        alert = builder.create();
        alert.setCancelable(true);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setWindowAnimations(R.style.AlertDialogTheme);
        if (winning_amt.equalsIgnoreCase("Better Luck Next Time")){
            winningInfo.setText( winning_amt);
            winningInfo.setVisibility(View.GONE);
            iv_close.setVisibility(View.GONE);
            image.setImageResource(R.drawable.better_luck);

        }else {
            winningInfo.setText(context.getString(R.string.price_unit) + " " + winning_amt+" Bonus");
            winningInfo.setVisibility(View.VISIBLE);
            iv_close.setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.win_top);
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
            AppUtils.clickVibrate(context);
            alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    public interface OnSubmitClickListener {
        void onClick(String s);
    }

}
