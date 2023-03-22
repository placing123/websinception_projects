package com.websinception.megastar.UI.draft.draftHome.addPlayer;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.draft.draftHome.DraftHomeActivity;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.beanOutput.GetDraftTeamsOutput;
import com.websinception.megastar.customView.CustomEditText;
import com.websinception.megastar.customView.CustomSpinner;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BottomSheetAddPlayerFragment extends BottomSheetDialogFragment {

    private String roundId;
    private View root;
    private Unbinder unbinder;
    private DraftHomeActivity mDraftHomeActivity;
    private OnPlayerSelec mOnPlayerSeleceListner;
    private String roleKey="WicketKeeper";



    @BindView(R.id.cs_team)
    CustomSpinner mCustomSpinnerTeam;

    @BindView(R.id.rv_player)
    RecyclerView mRecyclerView;

    @BindView(R.id.cet_search)
    CustomEditText mCustomEditTextSearch;


    @OnClick(R.id.ctv_btn_clear)
    void clearBtnClick() {
        AppUtils.clickVibrate(getActivity());
        mCustomSpinnerTeam.setSelection(0);
        mCustomEditTextSearch.setText("");
    }

    @BindView(R.id.ctv_wk)
    CustomTextView mCustomTextViewWk;

    @BindView(R.id.ctv_bat)
    CustomTextView mCustomTextViewBat;

    @BindView(R.id.ctv_ar)
    CustomTextView mCustomTextViewAr;

    @BindView(R.id.ctv_bowl)
    CustomTextView mCustomTextViewBowl;

    @OnClick({R.id.ctv_wk,R.id.ctv_bat,R.id.ctv_ar,R.id.ctv_bowl})
    void roleSwitchBtnClick(View view) {
        AppUtils.clickVibrate(getActivity());
        mCustomTextViewWk.setBackground(null);
        mCustomTextViewAr.setBackground(null);
        mCustomTextViewBat.setBackground(null);
        mCustomTextViewBowl.setBackground(null);
        view.setBackgroundResource(R.drawable.shedow_yellow_background);
        switch (view.getId()) {
            case R.id.ctv_wk:
                roleKey = "WicketKeeper";
                break;
            case R.id.ctv_bat:
                roleKey = "Batsman";
                break;
            case R.id.ctv_ar:
                roleKey = "AllRounder";
                break;
            case R.id.ctv_bowl:
                roleKey = "Bowler";
                break;
        }
        sort();
    }

    @OnClick(R.id.iv_info)
    void infoBtnClick() {
        AppUtils.clickVibrate(getActivity());
        AppUtils.showToast(mDraftHomeActivity, AppUtils.getStrFromRes(R.string.draft_info_addPlayer));

    }




    private List<GetDraftTeamsOutput.DataBean.RecordsBean> mListArrayListTeams;
    private GetAuctionPlayerOutput mGetAuctionPlayerOutput;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.bottom_sheet_dialog_draft_add_player, container, false);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
        mListArrayListTeams = null;
        mGetAuctionPlayerOutput = null;
        mDraftHomeActivity = (DraftHomeActivity) getActivity();
        roundId=mDraftHomeActivity.getRoundId();
        mGetAuctionPlayerOutput = mDraftHomeActivity.getmGetAuctionPlayerOutput();
        if (mGetAuctionPlayerOutput!=null&&mGetAuctionPlayerOutput.getData()!=null&&mGetAuctionPlayerOutput.getData().getRecords()!=null) {
          /*  List<GetAuctionPlayerOutput.DataBean.RecordsBean> tem= new ArrayList<>();
            List<GetAuctionPlayerOutput.DataBean.RecordsBean> originalRecored = mGetAuctionPlayerOutput.getData().getRecords();
            for (GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean : originalRecored) {
                if (recordsBean.getPlayerStatus().equals("Upcoming")) {
                    tem.add(recordsBean);
                }
            }
            GetAuctionPlayerOutput.DataBean data = mGetAuctionPlayerOutput.getData();
            data.setRecords(tem);
            data.setTotalRecords(tem.size());
            mGetAuctionPlayerOutput.setData(data);*/
        }

        mListArrayListTeams = mDraftHomeActivity.getmListArrayListTeams();
        ArrayAdapter<GetDraftTeamsOutput.DataBean.RecordsBean> teamAdapter = new ArrayAdapter<GetDraftTeamsOutput.DataBean.RecordsBean>(
                getActivity(),
                R.layout.draft_spinner_item,
                mListArrayListTeams
        );
        teamAdapter.setDropDownViewResource(R.layout.draft_spinner_dropdown_item);
        mCustomSpinnerTeam.setAdapter(teamAdapter);
        mCustomEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                sort();
            }
        });
        mCustomSpinnerTeam.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                sort();
            }
        });
        sort();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }


    private void sort() {
        String key = mCustomEditTextSearch.getText().toString().trim().toLowerCase();
        String teamKey = mCustomSpinnerTeam.getSelectedItem().toString();
        ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> records = new ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean>(mGetAuctionPlayerOutput.getData().getRecords());
        records = filterByRole(records, roleKey);
        records = teamKey.equals("Select") ? records : filterByTeam(records, teamKey);
        records = key.isEmpty() ? records : filterByName(records, key);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new PlayerListAdapter(getContext(),records,
                mOnPlayerSeleceListner,roundId));
    }


    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> filterByName(ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> records, String key) {
        ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> tem = new ArrayList<>();
        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : records) {
            if (record.getPlayerName().trim().toLowerCase().contains(key)) {
                tem.add(record);
            }
        }
        return tem;
    }


    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> filterByTeam(ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> records,
                                                                                String teamName) {
        ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> tem = new ArrayList<>();
        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : records) {
            if (record.getTeamNameShort().trim().equals(teamName)) {
                tem.add(record);
            }
        }
        return tem;
    }

    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> filterByRole(ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> records, String role) {
        ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> tem = new ArrayList<>();
        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : records) {
            if (record.getPlayerRole().equals(role)) {
                tem.add(record);
            }
        }
        return tem;
    }


    public interface OnPlayerSelec{
        public void onSelect(GetAuctionPlayerOutput.DataBean.RecordsBean recordsBean);
    }

    public void setmOnPlayerSeleceListner(OnPlayerSelec mOnPlayerSeleceListner) {
        this.mOnPlayerSeleceListner = mOnPlayerSeleceListner;
    }



}
