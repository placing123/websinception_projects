package com.mw.fantasy.UI.draft.addPlayerInAssitant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.auction.playerpoint.AuctionInfoUtil;
import com.mw.fantasy.UI.draft.draftHome.DraftAssistantFragment;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanInput.AddPlayerInAssistantInput;
import com.mw.fantasy.beanInput.EditPlayerInAssistantInput;
import com.mw.fantasy.beanInput.GetAuctionPlayerInput;
import com.mw.fantasy.beanInput.GetDraftTeamsInput;
import com.mw.fantasy.beanOutput.AddPlayerInAssistantOutput;
import com.mw.fantasy.beanOutput.GetAuctionInfoOutput;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.beanOutput.GetDraftTeamsOutput;
import com.mw.fantasy.beanOutput.SimpleOutput;
import com.mw.fantasy.customView.CustomEditText;
import com.mw.fantasy.customView.CustomSpinner;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AuctionAlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class AddPlayerInDraftAssistantActivity extends BaseActivity {

    public static final int REQUEST_CODE = 564;

    @BindView(R.id.ctv_player_count)
    CustomTextView mCustomTextViewPlayerCount;

    @BindView(R.id.cs_role)
    CustomSpinner mCustomSpinnerRole;


    @BindView(R.id.ctv_credit_left)
    CustomTextView mCustomTextViewCreditLeft;

    @BindView(R.id.cs_team)
    CustomSpinner mCustomSpinnerTeam;

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

    @BindView(R.id.ctv_wk)
    CustomTextView mCustomTextViewWK;

    @BindView(R.id.ctv_bat)
    CustomTextView mCustomTextViewBAT;

    @BindView(R.id.ctv_ar)
    CustomTextView mCustomTextViewAR;

    @BindView(R.id.ctv_bowl)
    CustomTextView mCustomTextViewBOWL;


    @BindView(R.id.rv_player)
    RecyclerView mRecyclerViewPlayers;
    private GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria mDraftPlayerSelectionCriteria;
    private String searchText;
    private String sarchkey = "", budgetLeft, auctionStartTime, auctionStatus, seriesName;
    private String type = Constant.ROLE_WICKETKEEPER;


    public static void start(DraftAssistantFragment draftAssistantFragment,
                             HashMap<String, String> selectedPlayer,
                             String matchGUID,
                             String contestGUID,
                             String UserTeamGUID,
                             String UserTeamName,
                             String seriesID,
                             GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria draftPlayerSelectionCriteria,
                             String budgetLeft,


                             String seriesName,
                             String auctionStatus,
                             String auctionStartTime) {
        Intent starter = new Intent(draftAssistantFragment.getContext(), AddPlayerInDraftAssistantActivity.class);
        starter.putExtra("UserTeamGUID", UserTeamGUID);
        starter.putExtra("UserTeamName", UserTeamName);
        starter.putExtra("selectedPlayer", selectedPlayer);
        starter.putExtra("matchGUID", matchGUID);
        starter.putExtra("contestGUID", contestGUID);
        starter.putExtra("seriesID", seriesID);
        starter.putExtra("budgetLeft", budgetLeft);
        starter.putExtra("draftPlayerSelectionCriteria", draftPlayerSelectionCriteria);

        starter.putExtra("seriesName", seriesName);
        starter.putExtra("auctionStatus", auctionStatus);
        starter.putExtra("auctionStartTime", auctionStartTime);
        draftAssistantFragment.startActivityForResult(starter, REQUEST_CODE);
    }


    @BindView(R.id.asi_ctv_series_name)
    CustomTextView mCustomTextViewASI_SeriesName;

    @BindView(R.id.asi_ctv_series_status)
    CustomTextView mCustomTextViewASI_SeriesStatus;

    @OnClick(R.id.ctv_btn_clear)
    void clearBtnClick() {
        AppUtils.clickVibrate(this);
        mCustomSpinnerRole.setSelection(0);
        mCustomSpinnerTeam.setSelection(0);
    }

    @OnClick(R.id.iv_close)
    void onCloseBtnClick() {
        AppUtils.clickVibrate(this);
        onBackPressed();
    }

    @OnClick(R.id.ctv_btn_submit_players)
    void onSubmitPlayersBtnClick() {
        AppUtils.clickVibrate(this);
        if (validateTeam()) {
            if (userTeamGUID == null || userTeamGUID.equals("")) {
                apiCallAddTeam();
            } else {
                apiCallUpdateTeam();
            }
        }

    }


    private Context mContext;
    private HashMap<String, String> selectedPlayer;
    private AuctionAlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    private String contestGUID, matchGUID, userTeamGUID, userTeamName, seriesID;
    private IUserInteractor mInteractor;
    private Call<GetAuctionPlayerOutput> auctionPlayersCall;
    private DraftAssistantPlayersListAdapter mDraftAssistantPlayersListAdapter;
    private GetAuctionPlayerOutput mGetAuctionPlayerOutput;
    private List<GetDraftTeamsOutput.DataBean.RecordsBean> mListArrayListTeams;

    @Override
    public int getLayout() {
        return R.layout.activity_add_player_in_draft_assistant;
    }

    @Override
    public void init() {
        mContext = this;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        selectedPlayer = (HashMap<String, String>) getIntent().getExtras().get("selectedPlayer");
        mDraftPlayerSelectionCriteria = (GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria) getIntent().getExtras().get("draftPlayerSelectionCriteria");
        seriesName = getIntent().getExtras().getString("seriesName");
        auctionStatus = getIntent().getExtras().getString("auctionStatus");
        auctionStartTime = getIntent().getExtras().getString("auctionStartTime");

        budgetLeft = getIntent().getExtras().getString("budgetLeft");
        matchGUID = getIntent().getExtras().getString("matchGUID");
        seriesID = getIntent().getExtras().getString("seriesID");
        contestGUID = getIntent().getExtras().getString("contestGUID");
        userTeamGUID = getIntent().getExtras().getString("UserTeamGUID");
        userTeamName = getIntent().getExtras().getString("UserTeamName");
        mDraftPlayerSelectionCriteria = (GetAuctionInfoOutput.DataBean.DraftPlayerSelectionCriteria) getIntent().getExtras().getSerializable("draftPlayerSelectionCriteria");
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(this);
        apiCallGetDraftTeams();
        mCustomTextViewCreditLeft.setText(budgetLeft + "\nROUNDS");

        new AuctionInfoUtil(mCustomTextViewASI_SeriesName,
                mCustomTextViewASI_SeriesStatus, seriesName,
                auctionStartTime,
                auctionStatus).start();
    }

    public HashMap<String, String> getSelectedPlayer() {
        return selectedPlayer;
    }

    public void removeSelectedPlayer(String playerGUID) {
        selectedPlayer.remove(playerGUID);
        if (mDraftAssistantPlayersListAdapter != null) {
            mDraftAssistantPlayersListAdapter.notifyDataSetChanged();
        }
        updateCounter();
    }

    public void addPlayer(String playerGUID, String bid) {
        selectedPlayer.put(playerGUID, bid);
        if (mDraftAssistantPlayersListAdapter != null) {
            mDraftAssistantPlayersListAdapter.notifyDataSetChanged();
        }
        updateCounter();
    }

    private void apiCallUpdateTeam() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            EditPlayerInAssistantInput editPlayerInAssistantInput = new EditPlayerInAssistantInput();
            editPlayerInAssistantInput.setContestGUID(contestGUID);
            editPlayerInAssistantInput.setMatchGUID(matchGUID);
            editPlayerInAssistantInput.setSeriesID(seriesID);
            editPlayerInAssistantInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            editPlayerInAssistantInput.setUserTeamGUID(userTeamGUID);
            editPlayerInAssistantInput.setUserTeamName(userTeamName);
            editPlayerInAssistantInput.setUserTeamType("Draft");
            editPlayerInAssistantInput.setIsPreTeam("Yes");
            ArrayList<EditPlayerInAssistantInput.UserTeamPlayersBean> userTeamPlayersBeans = new ArrayList<>();
            for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
                if (selectedPlayer.containsKey(record.getPlayerGUID())) {
                    EditPlayerInAssistantInput.UserTeamPlayersBean tem = new EditPlayerInAssistantInput.UserTeamPlayersBean();
                    tem.setPlayerGUID(record.getPlayerGUID());
                    tem.setAuctionDraftAssistantPriority(selectedPlayer.get(record.getPlayerGUID()));
                    tem.setPlayerName(record.getPlayerName());
                    tem.setPlayerID(record.getPlayerID());
                    tem.setPlayerRole(record.getPlayerRole());
                    tem.setPlayerPosition("Player");
                    userTeamPlayersBeans.add(tem);
                }
            }
            editPlayerInAssistantInput.setUserTeamPlayers(userTeamPlayersBeans);
            if (userTeamPlayersBeans.size() == 0) {
                AppUtils.showToast(this, "Please add at least one player.");
                return;
            }
            mInteractor.editPlayerInDraftAssistant(editPlayerInAssistantInput, new IUserInteractor.OnSimpleResponseListener() {
                @Override
                public void onSuccess(SimpleOutput simpleOutput) {
                    mProgressDialog.dismiss();
                    AppUtils.showToast(AddPlayerInDraftAssistantActivity.this,
                            simpleOutput.getMessage());
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AuctionAlertDialog(AddPlayerInDraftAssistantActivity.this,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel), new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAlertDialog.hide();
                            apiCallUpdateTeam();
                        }

                        @Override
                        public void onCancel() {
                            mAlertDialog.hide();
                        }
                    });
                    mAlertDialog.show();
                }
            });

        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AuctionAlertDialog(this,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel), new AuctionAlertDialog.OnOkayBtnClickListener() {
                @Override
                public void onClick() {
                    mAlertDialog.hide();
                    apiCallUpdateTeam();
                }

                @Override
                public void onCancel() {
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
            addPlayerInAssistantInput.setMatchGUID(matchGUID);
            addPlayerInAssistantInput.setSeriesID(seriesID);
            addPlayerInAssistantInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            addPlayerInAssistantInput.setUserTeamType("Draft");
            addPlayerInAssistantInput.setIsPreTeam("Yes");
            ArrayList<AddPlayerInAssistantInput.UserTeamPlayersBean> userTeamPlayersBeans = new ArrayList<>();
            for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
                if (selectedPlayer.containsKey(record.getPlayerGUID())) {
                    AddPlayerInAssistantInput.UserTeamPlayersBean tem = new AddPlayerInAssistantInput.UserTeamPlayersBean();
                    tem.setPlayerGUID(record.getPlayerGUID());
                    tem.setAuctionDraftAssistantPriority(selectedPlayer.get(record.getPlayerGUID()));
                    tem.setPlayerName(record.getPlayerName());
                    tem.setPlayerID(record.getPlayerID());
                    tem.setPlayerPosition("Player");
                    tem.setPlayerRole(record.getPlayerRole());
                    userTeamPlayersBeans.add(tem);
                }
            }
            addPlayerInAssistantInput.setUserTeamPlayers(userTeamPlayersBeans);
            if (userTeamPlayersBeans.size() == 0) {
                AppUtils.showToast(AddPlayerInDraftAssistantActivity.this,
                        "Please add at least one player.");
                return;
            }
            mInteractor.addPlayerInDraftAssistant(addPlayerInAssistantInput, new IUserInteractor.OnAddPlayerInAssistantResponseListener() {
                @Override
                public void onSuccess(AddPlayerInAssistantOutput addPlayerInAssistantOutput) {
                    mProgressDialog.dismiss();

                    AppUtils.showToast(AddPlayerInDraftAssistantActivity.this,
                            addPlayerInAssistantOutput.getMessage());
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AuctionAlertDialog(AddPlayerInDraftAssistantActivity.this,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel), new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAlertDialog.hide();
                            apiCallAddTeam();
                        }

                        @Override
                        public void onCancel() {
                            mAlertDialog.hide();
                        }
                    });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AuctionAlertDialog(this,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel), new AuctionAlertDialog.OnOkayBtnClickListener() {
                @Override
                public void onClick() {
                    mAlertDialog.hide();
                    apiCallAddTeam();
                }

                @Override
                public void onCancel() {
                    mAlertDialog.hide();
                }
            });
            mAlertDialog.show();
        }
    }

    private void apiCallGetPlayers() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            GetAuctionPlayerInput getAuctionPlayerInput = new GetAuctionPlayerInput();
            //auctionDraftTemchanges getAuctionPlayerInput.setSeriesGUID(seriesGUID);
            getAuctionPlayerInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            getAuctionPlayerInput.setMatchGUID(matchGUID);
            getAuctionPlayerInput.setContestGUID(contestGUID);
            getAuctionPlayerInput.setPlayerBidStatus("Yes");
            getAuctionPlayerInput.setParams("PlayerStatus,PlayerID,PlayerRole,PlayerPic,PlayerCountry,PlayerBornPlace,PlayerBattingStyle,PlayerBowlingStyle,MatchType,MatchNo,MatchDateTime,SeriesName,TeamGUID,PlayerBattingStats,PlayerBowlingStats,IsPlaying,PointsData,PlayerSalary,TeamNameShort,PlayerSalaryCredit,TeamName,SeriesGUID,SeriesID");
            auctionPlayersCall = mInteractor.getDraftPlayers(getAuctionPlayerInput, new IUserInteractor.OnGetAuctionPlayersResponseListener() {
                @Override
                public void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput) {
                    if (auctionPlayersCall == null || auctionPlayersCall.isCanceled()) {
                        return;
                    }
                    mProgressDialog.dismiss();
                    if (getAuctionPlayerOutput.getData().getTotalRecords() != 0) {
                        mGetAuctionPlayerOutput = getAuctionPlayerOutput;
                        ll_wk.performClick();

                    } else {
                        mProgressDialog.dismiss();
                        mAlertDialog = new AuctionAlertDialog(AddPlayerInDraftAssistantActivity.this,
                                "No Player Available.", "Retry", "Cancel", new AuctionAlertDialog.OnOkayBtnClickListener() {
                            @Override
                            public void onClick() {
                                mAlertDialog.hide();
                                apiCallGetPlayers();
                            }

                            @Override
                            public void onCancel() {
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
                    mAlertDialog = new AuctionAlertDialog(AddPlayerInDraftAssistantActivity.this,
                            errorMsg, "Retry", "Cancel", new AuctionAlertDialog.OnOkayBtnClickListener() {
                        @Override
                        public void onClick() {
                            mAlertDialog.hide();
                            apiCallGetPlayers();
                        }

                        @Override
                        public void onCancel() {
                            mAlertDialog.hide();
                            onBackPressed();
                        }
                    });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AuctionAlertDialog(this, AppUtils.getStrFromRes(R.string.network_error), "Retry", "Cancel", new AuctionAlertDialog.OnOkayBtnClickListener() {
                @Override
                public void onClick() {
                    mAlertDialog.hide();
                    apiCallGetPlayers();
                }

                @Override
                public void onCancel() {
                    mAlertDialog.hide();
                    onBackPressed();
                }
            });
            mAlertDialog.show();
        }
    }

    private void apiCallGetDraftTeams() {
        if (NetworkUtils.isConnected(this)) {
            mProgressDialog.show();
            GetDraftTeamsInput getDraftTeamsInput = new GetDraftTeamsInput();
            getDraftTeamsInput.setMatchGUID(matchGUID);
            getDraftTeamsInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            mInteractor.getDraftTeams(getDraftTeamsInput, new IUserInteractor.OnGetDraftTeamsListener() {
                @Override
                public void onSuccess(GetDraftTeamsOutput getDraftTeamsOutput) {
                    mProgressDialog.dismiss();
                    if (getDraftTeamsOutput != null && getDraftTeamsOutput.getData() != null && getDraftTeamsOutput.getData().getRecords() != null) {
                        mListArrayListTeams = getDraftTeamsOutput.getData().getRecords();
                    } else {
                        mListArrayListTeams = new ArrayList<>();
                    }
                    GetDraftTeamsOutput.DataBean.RecordsBean tem = new GetDraftTeamsOutput.DataBean.RecordsBean();
                    tem.setTeamNameShort("Select");
                    mListArrayListTeams.add(0, tem);
                    apiCallGetPlayers();
                    setViews();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AuctionAlertDialog(mContext,
                            AppUtils.getStrFromRes(R.string.network_error),
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AuctionAlertDialog.OnOkayBtnClickListener() {
                                @Override
                                public void onClick() {
                                    mAlertDialog.hide();
                                    apiCallGetDraftTeams();
                                }


                                @Override
                                public void onCancel() {
                                    mAlertDialog.hide();
                                    onBackPressed();

                                }
                            });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AuctionAlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AuctionAlertDialog.OnOkayBtnClickListener() {


                        @Override
                        public void onClick() {
                            mAlertDialog.hide();
                            apiCallGetDraftTeams();
                        }

                        @Override
                        public void onCancel() {
                            mAlertDialog.hide();
                            onBackPressed();

                        }
                    });
            mAlertDialog.show();
        }
    }

    private void setViews() {
        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(this,
                R.array.player_role, R.layout.draft_spinner_item);
        roleAdapter.setDropDownViewResource(R.layout.draft_spinner_dropdown_item);
        mCustomSpinnerRole.setAdapter(roleAdapter);

        ArrayAdapter<GetDraftTeamsOutput.DataBean.RecordsBean> teamAdapter = new ArrayAdapter<GetDraftTeamsOutput.DataBean.RecordsBean>(
                this,
                R.layout.draft_spinner_item,
                mListArrayListTeams
        );
        teamAdapter.setDropDownViewResource(R.layout.draft_spinner_dropdown_item);
        mCustomSpinnerTeam.setAdapter(teamAdapter);

        mCustomSpinnerTeam.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                sort(searchText);
            }
        });
        mCustomSpinnerRole.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                sort(searchText);
            }
        });

        mCustomEditTextSearchPlayer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                sarchkey = s.toString();
                sort(type);
            }
        });
    }

    private void sort(String searchText) {
        String key = sarchkey;
        String roleKey = searchText;
        ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> records =
                new ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean>(mGetAuctionPlayerOutput.getData().getRecords());
        records = roleKey.equals("Select") ? records : filterByRole(records, roleKey);
        records = key.isEmpty() ? records : filterByName(records, key);
        mRecyclerViewPlayers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mDraftAssistantPlayersListAdapter = new DraftAssistantPlayersListAdapter(this,
                records, matchGUID);
        mRecyclerViewPlayers.setAdapter(mDraftAssistantPlayersListAdapter);
        updateCounter();
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

    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> filterByTeam(ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> records, String teamName) {
        ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> tem = new ArrayList<>();
        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : records) {
            if (record.getTeamNameShort().trim().equals(teamName)) {
                tem.add(record);
            }
        }
        return tem;
    }

    private ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> filterByRole(ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> records, String RoleKey) {
        switch (RoleKey) {
            case "Wicket Keeper":
                RoleKey = "WicketKeeper";
                break;
            case "Bowler":
                RoleKey = "Bowler";
                break;
            case "Batsman":
                RoleKey = "Batsman";
                break;
            case "All Rounder":
                RoleKey = "AllRounder";
                break;
        }
        ArrayList<GetAuctionPlayerOutput.DataBean.RecordsBean> tem = new ArrayList<>();
        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : records) {
            if (record.getPlayerRole().equals(RoleKey)) {
                tem.add(record);
            }
        }
        return tem;
    }

    private boolean validateTeam() {
        ArrayList<AddPlayerInAssistantInput.UserTeamPlayersBean> userTeamPlayersBeans = new ArrayList<>();
        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
            if (selectedPlayer.containsKey(record.getPlayerGUID())) {
                AddPlayerInAssistantInput.UserTeamPlayersBean tem = new AddPlayerInAssistantInput.UserTeamPlayersBean();
                tem.setPlayerGUID(record.getPlayerGUID());
                tem.setAuctionDraftAssistantPriority(selectedPlayer.get(record.getPlayerGUID()));
                tem.setPlayerName(record.getPlayerName());
                tem.setPlayerID(record.getPlayerID());
                tem.setPlayerPosition("Player");
                tem.setPlayerRole(record.getPlayerRole());
                userTeamPlayersBeans.add(tem);
            }
        }
        int wk = 0;
        int bat = 0;
        int ar = 0;
        int ball = 0;
        for (AddPlayerInAssistantInput.UserTeamPlayersBean userTeamPlayersBean : userTeamPlayersBeans) {
            switch (userTeamPlayersBean.getPlayerRole()) {
                case "WicketKeeper":
                    wk++;
                    break;
                case "Bowler":
                    ball++;
                    break;
                case "Batsman":
                    bat++;
                    break;
                case "AllRounder":
                    ar++;
                    break;
            }
        }

        if ((wk + ball + ar + bat) > 0) {
            return true;
        } else {

            AppUtils.showToast(this, "Please add at least one player.");
            return false;
        }

       /* if (wk >= mwk && ball >= mball && bat >= mbat && ar >= mar) {
            return true;
        } else {
            String msg = "Please select at least " + mwk + " Wicket Keeper, " + mbat + " Batsman, " + mball + " Bowler, " + mar + " All-Rounder.";
            AppUtils.showToast(this, msg);
            return false;
        }*/

    }


    private void updateCounter() {

        ArrayList<AddPlayerInAssistantInput.UserTeamPlayersBean> userTeamPlayersBeans = new ArrayList<>();
        for (GetAuctionPlayerOutput.DataBean.RecordsBean record : mGetAuctionPlayerOutput.getData().getRecords()) {
            if (selectedPlayer.containsKey(record.getPlayerGUID())) {
                AddPlayerInAssistantInput.UserTeamPlayersBean tem = new AddPlayerInAssistantInput.UserTeamPlayersBean();
                tem.setPlayerGUID(record.getPlayerGUID());
                tem.setAuctionDraftAssistantPriority(selectedPlayer.get(record.getPlayerGUID()));
                tem.setPlayerName(record.getPlayerName());
                tem.setPlayerID(record.getPlayerID());
                tem.setPlayerPosition("Player");
                tem.setPlayerRole(record.getPlayerRole());
                userTeamPlayersBeans.add(tem);
            }
        }
        int wk = 0;
        int bat = 0;
        int ar = 0;
        int ball = 0;
        for (AddPlayerInAssistantInput.UserTeamPlayersBean userTeamPlayersBean : userTeamPlayersBeans) {
            switch (userTeamPlayersBean.getPlayerRole()) {
                case "WicketKeeper":
                    wk++;
                    break;
                case "Bowler":
                    ball++;
                    break;
                case "Batsman":
                    bat++;
                    break;
                case "AllRounder":
                    ar++;
                    break;
            }
        }


        mCustomTextViewWK.setText(wk + "");
        mCustomTextViewBAT.setText(bat + "");
        mCustomTextViewBOWL.setText(ball + "");
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

        if (ball == 0) {
            mCustomTextViewBOWL.setBackgroundResource(R.drawable.circle_gray_bg_white);
        } else {
            mCustomTextViewBOWL.setBackgroundResource(R.drawable.circle_blue_bg_white);
        }

        if (ar == 0) {
            mCustomTextViewAR.setBackgroundResource(R.drawable.circle_gray_bg_white);
        } else {
            mCustomTextViewAR.setBackgroundResource(R.drawable.circle_blue_bg_white);
        }

        mCustomTextViewPlayerCount.setText((wk + bat + ball + ar) + "/" + (mGetAuctionPlayerOutput.getData().getRecords().size() > 100 ? 100 : mGetAuctionPlayerOutput.getData().getRecords().size()) + "\nPLAYERS");

    }

    public GetAuctionPlayerOutput getmGetAuctionPlayerOutput() {
        return mGetAuctionPlayerOutput;
    }


    @OnClick(R.id.ll_wk)
    public void WK(View view) {

        type = Constant.ROLE_WICKETKEEPER;
//        mtempList.clear();
//        mtempList.addAll(responseMatchPlayersWK);
//        sortData(searchText);

        sort(Constant.ROLE_WICKETKEEPER);
        selectPlayerBackground(Constant.ROLE_WICKETKEEPER);
    }

    @OnClick(R.id.ll_bat)
    public void BAT(View view) {
        type = Constant.ROLE_BATSMAN;
//        mtempList.clear();
//        mtempList.addAll(responseMatchPlayersBAT);
//        sortData(searchText);
        sort(Constant.ROLE_BATSMAN);
        selectPlayerBackground(Constant.ROLE_BATSMAN);
    }

    @OnClick(R.id.ll_ar)
    public void AR(View view) {
        type = Constant.ROLE_ALLROUNDER;
//        mtempList.clear();
//        mtempList.addAll(responseMatchPlayersAR);
//        sortData(searchText);
        sort(Constant.ROLE_ALLROUNDER);
        selectPlayerBackground(Constant.ROLE_ALLROUNDER);
    }

    @OnClick(R.id.ll_bowl)
    public void BOWL(View view) {
        type = Constant.ROLE_BOWLER;
//        mtempList.clear();
//        mtempList.addAll(responseMatchPlayersBOWL);
//        sortData(searchText);
        sort(Constant.ROLE_BOWLER);
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
