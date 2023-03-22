package com.mw.fantasy.UI.auction.auctionHome;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mw.fantasy.R;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.utility.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectCaptainDialog {
    public static final int margin = 35;
    private Context context;
    private AlertDialog alert;
    private OnSubmitClickListener mOnSubmitClickListener;
    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> selectedPlayers;

    @BindView(R.id.spinner_captain)
    Spinner mCustomSpinnerCaptain;

    @BindView(R.id.spinner_vise_captain)
    Spinner mCustomSpinnerVCaptain;

    @OnClick(R.id.iv_close)
    void closeBtnClick() {
        hide();
    }

    @OnClick(R.id.ctv_btn_submit)
    void submitBtnClick() {
        int selectedItemPosition1 = mCustomSpinnerCaptain.getSelectedItemPosition();
        int selectedItemPosition2 = mCustomSpinnerVCaptain.getSelectedItemPosition();
        String c_GUID=selectedItemPosition1==0?"":selectedPlayers.get(selectedItemPosition1).getPlayerGUID();
        String vc_GUID=selectedItemPosition2==0?"":selectedPlayers.get(selectedItemPosition2).getPlayerGUID();
        mOnSubmitClickListener.onClick(c_GUID,vc_GUID);
    }


    public SelectCaptainDialog(Context context,
                               OnSubmitClickListener onSubmitClickListener,
                               ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> selectedPlayers) {
        this.context = context;
        this.mOnSubmitClickListener = onSubmitClickListener;
        this.selectedPlayers = selectedPlayers;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater _inflater = LayoutInflater.from(context);
        View g = _inflater.inflate(R.layout.dialog_select_captain, null);
        ButterKnife.bind(this, g);
        builder.setView(g, margin, margin, margin, margin);
        alert = builder.create();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setWindowAnimations(R.style.AlertDialogTheme);

        GetAuctionPlayerOutput.DataBean.RecordsBean tem = new GetAuctionPlayerOutput.DataBean.RecordsBean();
        tem.setPlayerName("Please Select");
        selectedPlayers.add(0, tem);
        ArrayAdapter<GetAuctionPlayerOutput.DataBean.RecordsBean> adapter =
                new ArrayAdapter<GetAuctionPlayerOutput.DataBean.RecordsBean>(context, R.layout.sd_spinner_dropdown_item, selectedPlayers);
        adapter.setDropDownViewResource(R.layout.sd_spinner_dropdown_item1);
        mCustomSpinnerCaptain.setAdapter(adapter);
        mCustomSpinnerVCaptain.setAdapter(adapter);
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

    public interface OnSubmitClickListener {
        void onClick(String c, String p);
    }


    public void setPreselection() {
        if (alert != null&&alert.isShowing()) {
            for (int i = 0; i < selectedPlayers.size(); i++) {
                String playerPosition = selectedPlayers.get(i).getPlayerPosition();
                if (playerPosition!=null&&playerPosition.equals("Captain")) {
                    mCustomSpinnerCaptain.setSelection(i);
                }
                if (playerPosition!=null&&playerPosition.equals("ViceCaptain")) {
                    mCustomSpinnerVCaptain.setSelection(i);
                }
            }
        }
    }
}
