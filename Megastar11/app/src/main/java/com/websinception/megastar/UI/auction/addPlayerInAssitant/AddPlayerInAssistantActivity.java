package com.websinception.megastar.UI.auction.addPlayerInAssitant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.auctionHome.AssistantFragment;
import com.websinception.megastar.UI.auction.playerpoint.AuctionInfoUtil;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanInput.AddPlayerInAssistantInput;
import com.websinception.megastar.beanInput.EditPlayerInAssistantInput;
import com.websinception.megastar.beanInput.GetAuctionPlayerInput;
import com.websinception.megastar.beanOutput.AddPlayerInAssistantOutput;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.beanOutput.SimpleOutput;
import com.websinception.megastar.customView.CustomEditText;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.AlertDialog;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class AddPlayerInAssistantActivity extends BaseActivity {

    public static final int REQUEST_CODE = 564;


    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;

    @BindView(R.id.ctv_player_count)
    CustomTextView mCustomTextViewPlayerCount;

    @BindView(R.id.ctv_credit_left)
    CustomTextView mCustomTextViewCreditLeft;

    @BindView(R.id.ctv_wk)
    CustomTextView mCustomTextViewWK;

    @BindView(R.id.ctv_bat)
    CustomTextView mCustomTextViewBAT;

    @BindView(R.id.ctv_ar)
    CustomTextView mCustomTextViewAR;

    @BindView(R.id.ctv_bowl)
    CustomTextView mCustomTextViewBOWL;

    @BindView(R.id.cet_search_player)
    CustomEditText mCustomEditTextSearchPlayer;

    @BindView(R.id.ll_wk)
    LinearLayout ll_wk;

    @BindView(R.id.ic_wk)
    ImageView ic_wk;
    @BindView(R.id.ic_bat)
    ImageView ic_bat;
    @BindView(R.id.ic_allrounder)
    ImageView ic_allrounder;
    @BindView(R.id.ic_bowl)
    ImageView ic_bowl;


    @BindView(R.id.rv_player)
    RecyclerView mRecyclerViewPlayers;

    private Context mContext;
    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayersWK = new ArrayList<>();
    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayersBAT = new ArrayList<>();
    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayersAR = new ArrayList<>();
    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> responseMatchPlayersBOWL = new ArrayList<>();
    private String type;
    private String searchText = "";

    private String seriesName, seriesDeadLine,auctionStatus,auctionStartTime;
    private int seriesStatus;
    private String budgetLeft;

    public static void start(AssistantFragment assistantFragment,
                             HashMap<String, String> selectedPlayer,
                             String roundId,
                             String seriesId,
                             String contestGUID,
                             String UserTeamGUID,
                             String UserTeamName,
                             String seriesName,
                             String seriesDeadLine,
                             int seriesStatus,
                             String budgetLeft,
                             String auctionStatus,
                             String auctionStartTime) {
        Intent starter = new Intent(assistantFragment.getContext(), AddPlayerInAssistantActivity.class);
        starter.putExtra("UserTeamGUID", UserTeamGUID);
        starter.putExtra("UserTeamName", UserTeamName);
        starter.putExtra("selectedPlayer", selectedPlayer);
        starter.putExtra("roundId", roundId);
        starter.putExtra("seriesId", seriesId);
        starter.putExtra("contestGUID", contestGUID);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("budgetLeft", budgetLeft);
        starter.putExtra("auctionStatus", auctionStatus);
        starter.putExtra("auctionStartTime", auctionStartTime);
        assistantFragment.startActivityForResult(starter, REQUEST_CODE);
    }


    @OnClick(R.id.iv_close)
    void onCloseBtnClick() {
        AppUtils.clickVibrate(this);
        onBackPressed();
    }

    @OnClick(R.id.ctv_btn_submit_players)
    void onSubmitPlayersBtnClick() {
        AppUtils.clickVibrate(this);
        if (userTeamGUID == null || userTeamGUID.equals("")) {
            apiCallAddTeam();
        } else {
            apiCallUpdateTeam();
        }
    }


    private HashMap<String, String> selectedPlayer;
    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    private String contestGUID, roundId, userTeamGUID, userTeamName, seriesId;
    private IUserInteractor mInteractor;
    private Call<GetAuctionPlayerOutput> auctionPlayersCall;
    private PlayersListAdapter mPlayersListAdapter;
    private GetAuctionPlayerOutput mGetAuctionPlayerOutput;
    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> mRecordsBeanArrayList = new ArrayList<>();
    private List<GetAuctionPlayerOutput.DataBean.RecordsBean> mtempList = new ArrayList<>();


    @Override
    public int getLayout() {
        return R.layout.activity_add_player_in_assistant;
    }

    @Override
    public void init() {
        mContext = this;
        selectedPlayer = (HashMap<String, String>) getIntent().getExtras().get("selectedPlayer");
        auctionStartTime = getIntent().getExtras().getString("auctionStartTime");
        auctionStatus = getIntent().getExtras().getString("auctionStatus");
        roundId = getIntent().getExtras().getString("roundId");
        seriesId = getIntent().getExtras().getString("seriesId");
        contestGUID = getIntent().getExtras().getString("contestGUID");
        userTeamGUID = getIntent().getExtras().getString("UserTeamGUID");
        userTeamName = getIntent().getExtras().getString("UserTeamName");
        seriesName = getIntent().getExtras().getString("seriesName");
        seriesDeadLine = getIntent().getExtras().getString("seriesDeadLine");
        seriesStatus = getIntent().getExtras().getInt("seriesStatus");
        budgetLeft = getIntent().getExtras().getString("budgetLeft");
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(this);

        mCustomTextViewCreditLeft.setText(budgetLeft+"/100 Crs\nCREDITS LEFT");
        mCustomEditTextSearchPlayer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                sortData(s.toString().trim());
                searchText = s.toString().trim();
            }
        });
        getData();
        new AuctionInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName, auctionStartTime, auctionStatus).start();

    }


    private void getData() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            getAuctionPlayerInput.setRoundID(roundId);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setParams("BidSoldCredit,PlayerStatus,PlayerID,PlayerRole,PlayerPic,PlayerCountry,PlayerBornPlace,PlayerBattingStyle,PlayerBowlingStyle,MatchType,MatchNo,MatchDateTime,SeriesName,TeamGUID,PlayerBattingStats,PlayerBowlingStats,IsPlaying,PointsData,PlayerSalary,TeamNameShort,PlayerSalaryCredit,SeriesGUID,SeriesID");
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            auctionPlayersCall = mInteractor.getAuctionPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    if (auctionPlayersCall == null || auctionPlayersCall.isCanceled()) {
                        return;
                    }
                    mProgressDialog.dismiss();
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        mGetAuctionPlayerOutput = getAuctionPlayerOutput;

                        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : getAuctionPlayerOutput.getData().getRecords()) {
                            switch (record.getPlayerRole()) {
                                case Constant.ROLE_WICKETKEEPER:
                                    responseMatchPlayersWK.add(record);
                                    break;
                                case Constant.ROLE_BATSMAN:
                                    responseMatchPlayersBAT.add(record);
                                    break;
                                case Constant.ROLE_ALLROUNDER:
                                    responseMatchPlayersAR.add(record);
                                    break;
                                case Constant.ROLE_BOWLER:
                                    responseMatchPlayersBOWL.add(record);
                                    break;
                            }
                        }

                        updateCounter();
                        ll_wk.performClick();


                    } else {
                        mProgressDialog.dismiss();
                        mAlertDialog = new AlertDialog(mContext,
                                "No Player Available.", "Retry", "Cancel", new AlertDialog.OnBtnClickListener() {
                            @Override
                            public void onYesClick() {
                                mAlertDialog.hide();
                                getData();
                            }

                            @Override
                            public void onNoClick() {
                                mAlertDialog.hide();
                                onBackPressed();
                            }
                        });
                        mAlertDialog.show();
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    if (auctionPlayersCall == null || auctionPlayersCall.isCanceled()) {
                        return;
                    }
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg, "Retry", "Cancel", new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            getData();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            onBackPressed();
                        }
                    });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AlertDialog(this, AppUtils.getStrFromRes(R.string.network_error), "Retry", "Cancel", new AlertDialog.OnBtnClickListener() {
                @Override
                public void onYesClick() {
                    mAlertDialog.hide();
                    getData();
                }

                @Override
                public void onNoClick() {
                    mAlertDialog.hide();
                    onBackPressed();
                }
            });
            mAlertDialog.show();
        }
    }

    private void sortData(String key) {

        if (key.equals("")) {
            mRecordsBeanArrayList.clear();
            for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mtempList) {
                mRecordsBeanArrayList.add(record);
            }
        } else {
            mRecordsBeanArrayList.clear();
            for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mtempList) {
                if (record.getPlayerName().trim().toLowerCase().contains(key.toLowerCase())) {
                    mRecordsBeanArrayList.add(record);
                }
            }
        }
        mRecyclerViewPlayers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mPlayersListAdapter = new PlayersListAdapter(this,roundId, mRecordsBeanArrayList,seriesStatus);
        mRecyclerViewPlayers.setAdapter(mPlayersListAdapter);
    }

    public HashMap<String, String> getSelectedPlayer() {
        return selectedPlayer;
    }

    public void removeSelectedPlayer(String playerGUID) {
        selectedPlayer.remove(playerGUID);
        /*if (mPlayersListAdapter != null) {
            mPlayersListAdapter.notifyDataSetChanged();
        }*/
        updateCounter();
    }

    public void addPlayer(String playerGUID, String bid) {
        selectedPlayer.put(playerGUID, bid);
       /* if (mPlayersListAdapter != null) {
            mPlayersListAdapter.notifyDataSetChanged();
        }*/
        updateCounter();
    }

    private void apiCallUpdateTeam() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            EditPlayerInAssistantInput editPlayerInAssistantInput = new EditPlayerInAssistantInput();
            editPlayerInAssistantInput.setContestGUID(contestGUID);
            editPlayerInAssistantInput.setRoundID(roundId);
            editPlayerInAssistantInput.setSeriesID(seriesId);
            editPlayerInAssistantInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            editPlayerInAssistantInput.setUserTeamGUID(userTeamGUID);
            editPlayerInAssistantInput.setUserTeamName(userTeamName);
            editPlayerInAssistantInput.setUserTeamType("Auction");
            editPlayerInAssistantInput.setIsPreTeam("Yes");
            ArrayList<EditPlayerInAssistantInput.UserTeamPlayersBean> userTeamPlayersBeans = new ArrayList<>();
            for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
                if (selectedPlayer.containsKey(record.getPlayerGUID())) {
                    EditPlayerInAssistantInput.UserTeamPlayersBean tem = new EditPlayerInAssistantInput.UserTeamPlayersBean();
                    tem.setPlayerGUID(record.getPlayerGUID());
                    tem.setBidCredit(selectedPlayer.get(record.getPlayerGUID()));
                    tem.setPlayerName(record.getPlayerName());
                    tem.setPlayerID(record.getPlayerID());
                    tem.setPlayerPosition("Player");
                    userTeamPlayersBeans.add(tem);
                }
            }
            editPlayerInAssistantInput.setUserTeamPlayers(userTeamPlayersBeans);
            if (userTeamPlayersBeans.size() == 0) {
                mProgressDialog.dismiss();
                AppUtils.showToast(this,"Please add at least one player.");
                return;
            }
            mInteractor.editPlayerInAssistant(editPlayerInAssistantInput, new IUserInteractor.OnSimpleResponseListener() {
                @Override
                public void onSuccess(SimpleOutput simpleOutput) {
                    mProgressDialog.dismiss();
                    AppUtils.showToast(mContext,simpleOutput.getMessage());
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            apiCallUpdateTeam();
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
            mProgressDialog.dismiss();
            mAlertDialog = new AlertDialog(this,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                @Override
                public void onYesClick() {
                    mAlertDialog.hide();
                    apiCallUpdateTeam();
                }

                @Override
                public void onNoClick() {
                    mAlertDialog.hide();
                }
            });
            mAlertDialog.show();
        }
    }


    private void apiCallAddTeam() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            AddPlayerInAssistantInput addPlayerInAssistantInput = new AddPlayerInAssistantInput();
            addPlayerInAssistantInput.setContestGUID(contestGUID);
            addPlayerInAssistantInput.setRoundID(roundId);
            addPlayerInAssistantInput.setSeriesID(seriesId);
            addPlayerInAssistantInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            addPlayerInAssistantInput.setUserTeamType("Auction");
            addPlayerInAssistantInput.setIsPreTeam("Yes");
            ArrayList<AddPlayerInAssistantInput.UserTeamPlayersBean> userTeamPlayersBeans = new ArrayList<>();
            for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
                if (selectedPlayer.containsKey(record.getPlayerGUID())) {
                    AddPlayerInAssistantInput.UserTeamPlayersBean tem = new AddPlayerInAssistantInput.UserTeamPlayersBean();
                    tem.setPlayerGUID(record.getPlayerGUID());
                    tem.setBidCredit(selectedPlayer.get(record.getPlayerGUID()));
                    tem.setPlayerName(record.getPlayerName());
                    tem.setPlayerID(record.getPlayerID());
                    tem.setPlayerPosition("Player");
                    userTeamPlayersBeans.add(tem);
                }
            }
            addPlayerInAssistantInput.setUserTeamPlayers(userTeamPlayersBeans);
            if (userTeamPlayersBeans.size() == 0) {
                mProgressDialog.dismiss();
                AppUtils.showToast(this,"Please add at least one player.");
                return;
            }
            mInteractor.addPlayerInAssistant(addPlayerInAssistantInput, new IUserInteractor.OnAddPlayerInAssistantResponseListener() {
                @Override
                public void onSuccess(AddPlayerInAssistantOutput addPlayerInAssistantOutput) {
                    mProgressDialog.dismiss();
                    AppUtils.showToast(mContext,addPlayerInAssistantOutput.getMessage());

                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            apiCallAddTeam();
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
            mProgressDialog.dismiss();
            mAlertDialog = new AlertDialog(this,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                @Override
                public void onYesClick() {
                    mAlertDialog.hide();
                    apiCallAddTeam();
                }

                @Override
                public void onNoClick() {
                    mAlertDialog.hide();
                }
            });
            mAlertDialog.show();
        }
    }


    private void updateCounter() {
        int wk = 0, bat = 0, bowl = 0, ar = 0;
        Iterator it = selectedPlayer.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
                if (record.getPlayerGUID().equals(pair.getKey())) {
                    switch (record.getPlayerRole()) {
                        case "WicketKeeper":
                            wk++;
                            break;
                        case "Bowler":
                            bowl++;
                            break;
                        case "Batsman":
                            bat++;
                            break;
                        case "AllRounder":
                            ar++;
                            break;
                    }
                }
            }
            // avoids a ConcurrentModificationException
        }

        mCustomTextViewWK.setText(wk + "");
        mCustomTextViewBAT.setText(bat + "");
        mCustomTextViewBOWL.setText(bowl + "");
        mCustomTextViewAR.setText(ar + "");

        if (wk == 0) {
            mCustomTextViewWK.setBackgroundResource(R.drawable.circle_gray_bg_white);
        } else {
            mCustomTextViewWK.setBackgroundResource(R.drawable.circle_blue_bg_white);
        }

        if (bat == 0) {
            mCustomTextViewBAT.setBackgroundResource(R.drawable.circle_gray_bg_white);
        } else {
            mCustomTextViewBAT.setBackgroundResource(R.drawable.circle_blue_bg_white);
        }

        if (bowl == 0) {
            mCustomTextViewBOWL.setBackgroundResource(R.drawable.circle_gray_bg_white);
        } else {
            mCustomTextViewBOWL.setBackgroundResource(R.drawable.circle_blue_bg_white);
        }

        if (ar == 0) {
            mCustomTextViewAR.setBackgroundResource(R.drawable.circle_gray_bg_white);
        } else {
            mCustomTextViewAR.setBackgroundResource(R.drawable.circle_blue_bg_white);
        }

        mCustomTextViewPlayerCount.setText((wk + bat + bowl + ar)+"/"+mGetAuctionPlayerOutput.getData().getRecords().size()+"\nPLAYERS");

        mCustomTextViewCreditLeft.setText(budgetLeft+"/100 Crs\nCREDITS LEFT");
    }

    @OnClick(R.id.ll_wk)
    public void WK(View view) {

        type = Constant.ROLE_WICKETKEEPER;
        mtempList.clear();
        mtempList.addAll(responseMatchPlayersWK);
        sortData(searchText);
        selectPlayerBackground(Constant.ROLE_WICKETKEEPER);
    }

    @OnClick(R.id.ll_bat)
    public void BAT(View view) {
        type = Constant.ROLE_BATSMAN;
        mtempList.clear();
        mtempList.addAll(responseMatchPlayersBAT);
        sortData(searchText);
        selectPlayerBackground(Constant.ROLE_BATSMAN);
    }

    @OnClick(R.id.ll_ar)
    public void AR(View view) {
        type = Constant.ROLE_ALLROUNDER;
        mtempList.clear();
        mtempList.addAll(responseMatchPlayersAR);
        sortData(searchText);
        selectPlayerBackground(Constant.ROLE_ALLROUNDER);
    }

    @OnClick(R.id.ll_bowl)
    public void BOWL(View view) {
        type = Constant.ROLE_BOWLER;
        mtempList.clear();
        mtempList.addAll(responseMatchPlayersBOWL);
        sortData(searchText);
        selectPlayerBackground(Constant.ROLE_BOWLER);

    }

    private void selectPlayerBackground(String type) {
        switch (type) {
            case Constant.ROLE_WICKETKEEPER:
                ic_wk.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_on));
                ic_allrounder.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                ic_bat.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                ic_bowl.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                break;
            case Constant.ROLE_BATSMAN:
                ic_wk.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                ic_allrounder.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                ic_bat.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_on));
                ic_bowl.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                break;
            case Constant.ROLE_ALLROUNDER:
                ic_wk.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                ic_allrounder.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_on));
                ic_bat.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                ic_bowl.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                break;
            case Constant.ROLE_BOWLER:
                ic_wk.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                ic_allrounder.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                ic_bat.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off_auction));
                ic_bowl.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_on));
                break;
        }
    }


}
