package com.websinception.megastar.UI.createTeam;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.websinception.megastar.AppController;
import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.createCaption.CreateCaptionActivity;
import com.websinception.megastar.UI.matchControlet.MatchDetailPresenterImpl;
import com.websinception.megastar.UI.matchControlet.MatchInfoView;
import com.websinception.megastar.UI.previewTeam.BottomSheetFootballTeamPreviewFragment;
import com.websinception.megastar.UI.previewTeam.PlayerPreviewActivity;
import com.websinception.megastar.UI.previewTeam.PlayerPreviewCallback;
import com.websinception.megastar.UI.previewTeam.PlayerRecord;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.MatchDetailInput;
import com.websinception.megastar.beanInput.PlayersInput;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;
import com.websinception.megastar.beanOutput.MyTeamOutput;
import com.websinception.megastar.beanOutput.PlayersOutput;
import com.websinception.megastar.customView.CustomImageView;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.OnItemClickListener;
import com.websinception.megastar.utility.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFootballTeamFragment extends BaseFragment implements CreateTeamView, MatchInfoView {

    public CreateTeamAdapter adapter;
    @BindView(R.id.main_frag)
    RelativeLayout relativeLayoutMain;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    String seriesId = "", MatchGUID = "", visitorteamId = "", teamName = "", localteamId = "", teamId = "", contestId = "", chip = "", joiningAmount = "", userInviteCode = "", NEED = "";
    @BindView(R.id.civ_wk)
    CustomImageView customImageViewWK;
    @BindView(R.id.civ_bat)
    CustomImageView customImageViewBAT;
    @BindView(R.id.civ_ar)
    CustomImageView customImageViewAR;
    @BindView(R.id.civ_bowl)
    CustomImageView customImageViewBOWL;
    @BindView(R.id.ctv_wk)
    CustomTextView customTextViewWK;
    @BindView(R.id.ctv_bat)
    CustomTextView customTextViewBAT;
    @BindView(R.id.ctv_ar)
    CustomTextView customTextViewAR;
    @BindView(R.id.ctv_bowl)
    CustomTextView customTextViewBOWL;
    @BindView(R.id.ctv_title)
    CustomTextView customTextViewTITLE;
    @BindView(R.id.ctv_credit_left)
    CustomTextView customTextViewCreditLeft;
    @BindView(R.id.ctv_players)
    CustomTextView customTextViewPlayers;
    @BindView(R.id.ctv_next)
    CustomTextView customTextViewNext;
    int gametype;
    @BindView(R.id.ctv_team_1_count)
    CustomTextView ctvTeam1Count;
    @BindView(R.id.ctv_team_2_count)
    CustomTextView ctvTeam2Count;
    @BindView(R.id.ctv_team_1_name)
    CustomTextView ctvTeam1Name;
    @BindView(R.id.ctv_team_2_name)
    CustomTextView ctvTeam2Name;
    @BindView(R.id.teamsVS)
    @Nullable
    CustomTextView teamsVS;
    @BindView(R.id.ctv_full_time)
    @Nullable
    CustomTextView customTextViewFullTime;
    @BindView(R.id.recycler_view_player)
    RecyclerView mRecyclerViewPlayer;

    @BindView(R.id.ic_wk)
    ImageView ic_wk;

    @BindView(R.id.ic_bat)
    ImageView ic_bat;

    @BindView(R.id.ic_allrounder)
    ImageView ic_allrounder;

    @BindView(R.id.ic_bowl)
    ImageView ic_bowl;

    @BindView(R.id.Gk)
    CustomTextView Gk;

    @BindView(R.id.DEF)
    CustomTextView DEF;

    @BindView(R.id.ll_wk)
    LinearLayout ll_wk;

    @BindView(R.id.mid)
    CustomTextView mid;

    @BindView(R.id.st)
    CustomTextView st;

    @BindView(R.id.playerText)
    CustomTextView playerText;
    @BindView(R.id.pointsText)
    CustomTextView pointsText;
    @BindView(R.id.creditsText)
    CustomTextView creditsText;
    private String isPlaying = "no";


    String teamType1 = "", teamType2 = "", team1 = "";
    String localTeamName = "";
    int playingFlag = 0;
    MyTeamOutput.DataBean.RecordsBean team = null;
    List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayersGK = new ArrayList<>();
    List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayersDEF = new ArrayList<>();
    List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayersST = new ArrayList<>();
    List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayersMED = new ArrayList<>();
    String SELECTED_ROLE = Constant.ROLE_GOALKEEPER;
    private LinearLayoutManager layoutManager;
    private CreateTeamPresenterImpl presenterImpl;
    private MatchDetailPresenterImpl matchDetailPresenter;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    public TeamPlayerFootballAdapter adapterteam;
    private Loader loader;

    boolean ROLE_GK, ROLE_DEF, ROLE_MID, ROLE_ST;


    private String tournamentCode = "";
    private BroadcastReceiver updates_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            try {
                if (i.getAction().equals(CreateFootballTeamFragment.class.getSimpleName())) {
                    if (i.hasExtra("KEY")) {
                        if (i.getStringExtra("KEY").equals("updateCaptain")) {
                            setCaption(i.getStringExtra("VALUE"));
                        } else if (i.getStringExtra("KEY").equals("updateViceCaptain")) {
                            setViceCaption(i.getStringExtra("VALUE"));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private boolean animationView = false;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, final int position) {

            if (animationView) return;

            if (!adapter.isSelected(position)) {
                if (getTotalSelectedPlayers() == 11) {
//                    itemWarningAnimation(view);
                    onShowSnackBar(AppUtils.getStrFromRes(R.string.max_11_players_allowed));
                    return;
                } else if (getSelectedTeamMemberCount(adapter.getTeamId(position)) == 7) {
//                    itemWarningAnimation(view);
                    onShowSnackBar(AppUtils.getStrFromRes(R.string.max_7_players_from_1_team));
                    return;
                } else if (100 - getTotalPlayersCredit() < Float.parseFloat(adapter.getPoints(position))) {
//                    itemWarningAnimation(view);
                    onShowSnackBar(String.format(AppUtils.getStrFromRes(R.string.only_credit_left), 100 - getTotalPlayersCredit() + ""));
                    return;
                } else if (!canSelectMorePlayers(adapter.getPlayRole(position))) {

                    if (!adapter.getPlayRole(position).equals(Constant.ROLE_GOALKEEPER) && getIndividualCount(Constant.ROLE_GOALKEEPER) < 1) {
//                        itemWarningAnimation(view);
                        onShowSnackBar(AppUtils.getStrFromRes(R.string.every_team_needs_atleast_one_goal_keeper));
                        return;
                    } else if (!adapter.getPlayRole(position).equals(Constant.ROLE_DEFENDER) && getIndividualCount(Constant.ROLE_DEFENDER) < 3) {
//                        itemWarningAnimation(view);
                        onShowSnackBar(AppUtils.getStrFromRes(R.string.every_team_needs_atleast_3_defenders));
                        return;
                    } else if (!adapter.getPlayRole(position).equals(Constant.ROLE_FORWARD) && getIndividualCount(Constant.ROLE_FORWARD) < 1) {
//                        itemWa/rningAnimation(view);
                        onShowSnackBar(AppUtils.getStrFromRes(R.string.every_team_needs_atleast_one_forwards));
                        return;
                    } else if (!adapter.getPlayRole(position).equals(Constant.ROLE_MIDFIELDER) && getIndividualCount(Constant.ROLE_MIDFIELDER) < 3) {
//                        itemWarningAnimation(view);
                        onShowSnackBar(AppUtils.getStrFromRes(R.string.every_team_needs_atleast_3_midfielders));
                        return;
                    }
                } else if ((11 - getTotalSelectedPlayers()) <= (1 - getIndividualCount(Constant.ROLE_GOALKEEPER))
                        && !adapter.getPlayRole(position).equals(Constant.ROLE_GOALKEEPER)) {
//                    itemWarningAnimation(view);
                    onShowSnackBar(AppUtils.getStrFromRes(R.string.every_team_needs_atleast_one_goal_keeper));
                    return;
                } else if ((11 - getTotalSelectedPlayers()) <= (1 - getIndividualCount(Constant.ROLE_FORWARD))
                        && !adapter.getPlayRole(position).equals(Constant.ROLE_FORWARD)) {
//                    itemWarningAnimation(view);
                    onShowSnackBar(AppUtils.getStrFromRes(R.string.every_team_needs_atleast_one_forwards));
                    return;
                } else if ((11 - getTotalSelectedPlayers()) <= (3 - getIndividualCount(Constant.ROLE_DEFENDER))
                        && !adapter.getPlayRole(position).equals(Constant.ROLE_DEFENDER)) {
//                    itemWarningAnimation(view);
                    onShowSnackBar(AppUtils.getStrFromRes(R.string.every_team_needs_atleast_3_defenders));
                    return;
                } else if ((11 - getTotalSelectedPlayers()) <= (3 - getIndividualCount(Constant.ROLE_MIDFIELDER))
                        && !adapter.getPlayRole(position).equals(Constant.ROLE_MIDFIELDER)) {
//                    itemWarningAnimation(view);
                    onShowSnackBar(AppUtils.getStrFromRes(R.string.every_team_needs_atleast_3_midfielders));
                    return;
                }
            }


            switch (adapter.getPlayRole(position)) {
                case Constant.ROLE_GOALKEEPER:
                    if (!adapter.isSelected(position) && adapter.getSelectedCount() >= 1) {
//                        itemWarningAnimation(view);
                        onShowSnackBar(AppUtils.getStrFromRes(R.string.only_one_goal_keeper));
                    } else {
                        itemSelectedAnimation(view, position);
                    }
                    break;
                case Constant.ROLE_DEFENDER:
                    if (!adapter.isSelected(position) && adapter.getSelectedCount() >= 5) {
//                        itemWarningAnimation(view);
                        onShowSnackBar(AppUtils.getStrFromRes(R.string.max_5_defender_allowed));
                    } else {
                        itemSelectedAnimation(view, position);
                    }
                    break;
                case Constant.ROLE_FORWARD:
                    if (!adapter.isSelected(position) && adapter.getSelectedCount() >= 3) {
//                        itemWarningAnimation(view);
                        onShowSnackBar(AppUtils.getStrFromRes(R.string.max_3_forward_allowed));
                    } else {
                        itemSelectedAnimation(view, position);
                    }
                    break;
                case Constant.ROLE_MIDFIELDER:
                    if (!adapter.isSelected(position) && adapter.getSelectedCount() >= 5) {
//                        itemWarningAnimation(view);
                        onShowSnackBar(AppUtils.getStrFromRes(R.string.max_5_midfielder_allowed));
                    } else {
                        itemSelectedAnimation(view, position);
                    }
                    break;
            }
        }
    };
    private OnItemClickListener.OnItemClickCallback onViewItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
//            PlayerActivity.start(mContext, adapter.getPlayer(position), 2, MatchGUID);
            PlayerPreviewActivity.start(mContext, adapter.getPlayer(position).getPlayerName(),
                    String.valueOf(adapter.getPlayer(position).getPointCredits()), adapter.getPlayer(position).getTotalPoints(),
                    adapter.getPlayer(position).getPlayerBattingStyle(), adapter.getPlayer(position).getPlayerBowlingStyle(),
                    adapter.getPlayer(position).getPlayerCountry(), adapter.getPlayer(position).getPlayerPic(), adapter.getPlayer(position).getSeriesGUID(),
                    adapter.getPlayer(position).getPlayerGUID(),
                    MatchGUID,adapter.getPlayer(position).getPlayerSelectedPercent(),"Pending",
                    adapter.getPlayer(position).getTeamNameShort(), adapter.getPlayer(position).getPlayerRole());
        }
    };
    private int actionTag = 0;
    private String join;

    public static CreateFootballTeamFragment getInstance(Bundle bundle) {
        CreateFootballTeamFragment friendsFragment = new CreateFootballTeamFragment();
        friendsFragment.setArguments(bundle);
        return friendsFragment;
    }

    @OnClick(R.id.ctv_next)
    public void next(View view) {
        PlayersOutput.DataBean response = new PlayersOutput.DataBean();
        List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayers = new ArrayList<>();
        addHeadingOfPlayers(responseMatchPlayers, AppUtils.getStrFromRes(R.string.goal_keeper));
        responseMatchPlayers.addAll(getSelectedPlayersData(responseMatchPlayersGK));
        addHeadingOfPlayers(responseMatchPlayers, AppUtils.getStrFromRes(R.string.defenders));
        responseMatchPlayers.addAll(getSelectedPlayersData(responseMatchPlayersDEF));
        addHeadingOfPlayers(responseMatchPlayers, AppUtils.getStrFromRes(R.string.forwards));
        responseMatchPlayers.addAll(getSelectedPlayersData(responseMatchPlayersST));
        addHeadingOfPlayers(responseMatchPlayers, AppUtils.getStrFromRes(R.string.midfielders));
        responseMatchPlayers.addAll(getSelectedPlayersData(responseMatchPlayersMED));
        response.setRecords(responseMatchPlayers);

        if (actionTag == 1) {
            teamId = "";
        }
        CreateCaptionActivityStart(mContext, seriesId, MatchGUID, localteamId, visitorteamId, new Gson().toJson(response), teamId, tournamentCode, 2);
    }

    @OnClick(R.id.ctv_team_preview)
    public void teamPreview(View view) {
        if (getTotalSelectedPlayers() == 0) {
            onShowSnackBar(AppUtils.getStrFromRes(R.string.no_players_selected_yet));
        } else {
            List<PlayerRecord> recordList = new ArrayList<>();

            recordList.addAll(getSelectedPlayersRecord(responseMatchPlayersGK));
            recordList.addAll(getSelectedPlayersRecord(responseMatchPlayersDEF));
            recordList.addAll(getSelectedPlayersRecord(responseMatchPlayersMED));
            recordList.addAll(getSelectedPlayersRecord(responseMatchPlayersST));


            showPreview(recordList);
        }
    }

    private List<PlayerRecord> getSelectedPlayersRecord(List<PlayersOutput.DataBean.RecordsBean> response) {

        Log.d("getTotalPoints", AppUtils.gsonToJSON(response));
        List<PlayerRecord> responseMatchPlayers = new ArrayList<>();
        for (int i = 0; i < response.size(); i++) {

            if (response.get(i).isSelected()) {

                PlayerRecord player = new PlayerRecord();
                player.setPlayerGUID(response.get(i).getPlayerGUID());
                player.setPlayerName(response.get(i).getPlayerName());
                player.setPlayerRole(response.get(i).getPlayerRole());
                player.setPlayerPic(response.get(i).getPlayerPic());

                player.setPoints(response.get(i).getPlayerSalary());
                player.setPointCredits(String.valueOf(response.get(i).getPlayerSalary()));
                player.setTotalPoints(String.valueOf(response.get(i).getPointCredits()));

                player.setPlayerCountry(response.get(i).getPlayerCountry());
                player.setPlayerBattingStyle(response.get(i).getPlayerBattingStyle());
                player.setPlayerBowlingStyle(response.get(i).getPlayerBowlingStyle());
                player.setTeamGUID(response.get(i).getTeamGUID());
                player.setIsPlaying(response.get(i).getIsPlaying());
                player.setTeamNameShort(response.get(i).getTeamNameShort());
                player.setLocalTeamName(response.get(0).getTeamGUID());
                player.setSeriesGUID(response.get(i).getSeriesGUID());

                player.setPlayerSelectedPercent(response.get(i).getPlayerSelectedPercent());

                responseMatchPlayers.add(player);
            }
        }
        return responseMatchPlayers;
    }

    private void showPreview(final List<PlayerRecord> responseMatchPlayers) {
        final BottomSheetFootballTeamPreviewFragment dialogFragment = new BottomSheetFootballTeamPreviewFragment();
        dialogFragment.setUpdateable(new PlayerPreviewCallback() {
            @Override
            public void close() {

            }

            @Override
            public void edit() {

            }

            @Override
            public void refresh() {

            }


            @Override
            public String getTeamName() {
                String name = "";
                if (AppSession.getInstance().getLoginSession().getData().getUsername() != null) {
                    name = AppSession.getInstance().getLoginSession().getData().getUsername() + "'s\n";
                }

                if (TextUtils.isEmpty(teamName))
                    return name.concat("Team 1");
                else return name.concat(teamName);
            }

            @Override
            public boolean isTeamPoints() {
                return false;
            }

            @Override
            public String totalPoints() {
                return "0";
            }

            @Override
            public String getMatchID() {
                return MatchGUID;
            }

            @Override
            public String getStatus() {
                return "Pending";
            }

            @Override
            public List<PlayerRecord> getPlayers() {
                return responseMatchPlayers;
            }

            @Override
            public Context getContext() {
                return mContext;
            }

            @Override
            public String isPlaying11Avaible() {
                return isPlaying;
            }

            @Override
            public String getLocalTeamGUID() {
                return "";
            }
        });
        dialogFragment.show(getChildFragmentManager(), dialogFragment.getTag());
        dialogFragment.setPointLaval("Cr");
    }

    public void CreateCaptionActivityStart(Context context, String seriesId, String matchId, String localteamId,
                                           String visitorteamId, String data, String teamId, String tournamentCode, int gametype) {
        Intent starter = new Intent(context, CreateCaptionActivity.class);
        starter.putExtra("seriesId", seriesId);
        starter.putExtra("matchId", matchId);

        if (team != null) {
            starter.putExtra("teamName", team.getUserTeamName());
        }
        starter.putExtra("localteamId", localteamId);
        starter.putExtra("visitorteamId", visitorteamId);
        starter.putExtra("data", data);
        starter.putExtra("teamId", teamId);
        starter.putExtra("contestId", contestId);
        starter.putExtra("joiningAmount", joiningAmount);
        starter.putExtra("userInviteCode", userInviteCode);
        starter.putExtra("chip", chip);
        starter.putExtra("tournamentCode", tournamentCode);
        starter.putExtra("NEED", NEED);
        starter.putExtra("join", join);
        starter.putExtra("gametype", gametype);
        startActivityForResult(starter, BaseActivity.REQUEST_CODE_CREATE_TEAM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode", "requestCode: " + BaseActivity.REQUEST_CODE_CREATE_TEAM);
        if (requestCode == BaseActivity.REQUEST_CODE_CREATE_TEAM && resultCode == getActivity().RESULT_OK) {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        } else if (requestCode == BaseActivity.REQUEST_CODE_JOIN_CONTESTS && resultCode == getActivity().RESULT_OK) {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }

    private void addHeadingOfPlayers(List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayers, String type) {
        PlayersOutput.DataBean.RecordsBean bean = new PlayersOutput.DataBean.RecordsBean();
        bean.setViewType(1);
        bean.setPlayerName(type);
        responseMatchPlayers.add(bean);
    }

    @OnClick(R.id.ll_wk)
    public void WK(View view) {
        SELECTED_ROLE = Constant.ROLE_GOALKEEPER;
        viewSelected(Constant.ROLE_GOALKEEPER);

        ROLE_GK = true;
        ROLE_DEF = false;
        ROLE_ST = false;
        ROLE_MID = false;


        ic_wk.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_on));
        ic_bowl.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));
        ic_allrounder.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));
        ic_bat.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));

        st.setTextColor(getResources().getColor(R.color.black));
        mid.setTextColor(getResources().getColor(R.color.black));
        DEF.setTextColor(getResources().getColor(R.color.black));
        Gk.setTextColor(getResources().getColor(R.color.createTeamSeector));


        if (!canSelectMorePlayers(Constant.ROLE_GOALKEEPER)) {
            adapter.disableItems(true, Constant.ROLE_GOALKEEPER, 1);
        } else {
            adapter.disableItems(false, Constant.ROLE_GOALKEEPER, getIndividualCount(responseMatchPlayersGK));
        }
    }

    @OnClick(R.id.ll_bat)
    public void BAT(View view) {
        SELECTED_ROLE = Constant.ROLE_DEFENDER;
        viewSelected(Constant.ROLE_DEFENDER);

        ROLE_GK = false;
        ROLE_DEF = true;
        ROLE_ST = false;
        ROLE_MID = false;


        ic_wk.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));
        ic_bowl.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));
        ic_allrounder.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));
        ic_bat.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_on));

        st.setTextColor(getResources().getColor(R.color.black));
        mid.setTextColor(getResources().getColor(R.color.black));
        DEF.setTextColor(getResources().getColor(R.color.createTeamSeector));
        Gk.setTextColor(getResources().getColor(R.color.black));


        if (!canSelectMorePlayers(Constant.ROLE_DEFENDER)) {
            adapter.disableItems(true, Constant.ROLE_DEFENDER, 3);
        } else {
            adapter.disableItems(false, Constant.ROLE_DEFENDER, getIndividualCount(responseMatchPlayersDEF));
        }


    }

    @OnClick(R.id.ll_ar)
    public void AR(View view) {
        SELECTED_ROLE = Constant.ROLE_MIDFIELDER;
        viewSelected(Constant.ROLE_MIDFIELDER);

        ROLE_GK = false;
        ROLE_DEF = false;
        ROLE_ST = false;
        ROLE_MID = true;


        ic_wk.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));
        ic_bowl.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));
        ic_allrounder.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_on));
        ic_bat.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));

        st.setTextColor(getResources().getColor(R.color.black));
        mid.setTextColor(getResources().getColor(R.color.createTeamSeector));
        DEF.setTextColor(getResources().getColor(R.color.black));
        Gk.setTextColor(getResources().getColor(R.color.black));


        if (!canSelectMorePlayers(Constant.ROLE_MIDFIELDER)) {
            adapter.disableItems(true, Constant.ROLE_MIDFIELDER, 3);
        } else {
            adapter.disableItems(false, Constant.ROLE_MIDFIELDER, getIndividualCount(responseMatchPlayersMED));
        }

    }


    @OnClick(R.id.playerText)
    void onInfoPlayerClick() {

        playerText.setTextColor(getResources().getColor(R.color.selected_blue));
        pointsText.setTextColor(getResources().getColor(R.color.unselected_blue));
        creditsText.setTextColor(getResources().getColor(R.color.unselected_blue));

        pointsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        creditsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        if (playerText.isSelected()) {
            adapter.shotByName(true);
            playerText.setSelected(false);
            playerText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_up, 0);

        } else {
            adapter.shotByName(false);
            playerText.setSelected(true);

            playerText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_down, 0);
        }

    }

    @OnClick(R.id.pointsText)
    void onPointClick() {

        playerText.setTextColor(getResources().getColor(R.color.unselected_blue));
        pointsText.setTextColor(getResources().getColor(R.color.selected_blue));
        creditsText.setTextColor(getResources().getColor(R.color.unselected_blue));

        playerText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        creditsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        if (pointsText.isSelected()) {
            adapter.shotByPoint(true);
            pointsText.setSelected(false);
            pointsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_up, 0);

        } else {
            adapter.shotByPoint(false);
            pointsText.setSelected(true);
            pointsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_down, 0);
        }


    }

    @OnClick(R.id.creditsText)
    void onCreaditClick() {

        playerText.setTextColor(getResources().getColor(R.color.unselected_blue));
        pointsText.setTextColor(getResources().getColor(R.color.unselected_blue));
        creditsText.setTextColor(getResources().getColor(R.color.selected_blue));

        playerText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        pointsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        if (creditsText.isSelected()) {
            adapter.shotByCredit(true);
            creditsText.setSelected(false);
            creditsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_up, 0);

        } else {
            adapter.shotByCredit(false);
            creditsText.setSelected(true);
            creditsText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_down, 0);
        }

    }

    @OnClick(R.id.ll_bowl)
    public void BOWL(View view) {

        SELECTED_ROLE = Constant.ROLE_FORWARD;
        viewSelected(Constant.ROLE_FORWARD);

        ROLE_GK = false;
        ROLE_DEF = false;
        ROLE_ST = true;
        ROLE_MID = false;


        ic_wk.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));
        ic_bowl.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_on));
        ic_allrounder.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));
        ic_bat.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_player_role_off));

        st.setTextColor(getResources().getColor(R.color.createTeamSeector));
        mid.setTextColor(getResources().getColor(R.color.black));
        DEF.setTextColor(getResources().getColor(R.color.black));
        Gk.setTextColor(getResources().getColor(R.color.black));

        if (!canSelectMorePlayers(Constant.ROLE_FORWARD)) {
            adapter.disableItems(true, Constant.ROLE_FORWARD, 1);
        } else {
            adapter.disableItems(false, Constant.ROLE_FORWARD, getIndividualCount(responseMatchPlayersST));
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (getArguments() != null) {


            if (getArguments().containsKey("actionTag")) {
                actionTag = getArguments().getInt("actionTag");
            }
            if (getArguments().containsKey("seriesId")) {
                seriesId = getArguments().getString("seriesId");
            }
            if (getArguments().containsKey("MatchGUID")) {
                MatchGUID = getArguments().getString("MatchGUID");
            }
            if (getArguments().containsKey("visitorteamId")) {
                visitorteamId = getArguments().getString("visitorteamId");
            }
            if (getArguments().containsKey("localteamId")) {
                localteamId = getArguments().getString("localteamId");
            }
            if (getArguments().containsKey("contestId")) {
                contestId = getArguments().getString("contestId");
            }
            if (getArguments().containsKey("joiningAmount")) {
                joiningAmount = getArguments().getString("joiningAmount");
            }
            if (getArguments().containsKey("chip")) {
                chip = getArguments().getString("chip");
            }
            if (getArguments().containsKey("userInviteCode")) {
                userInviteCode = getArguments().getString("userInviteCode");
            }
            if (getArguments().containsKey("NEED")) {
                NEED = getArguments().getString("NEED");
            }
            if (getArguments().containsKey("teamName")) {
                teamName = getArguments().getString("teamName");
            }

            if (getArguments().containsKey("tournamentCode")) {
                tournamentCode = getArguments().getString("tournamentCode");
            }

            if (getArguments().containsKey("join")) {
                join = getArguments().getString("join");

            }

            if (getArguments().containsKey("teamData") && !TextUtils.isEmpty(getArguments().getString("teamData"))) {
                team = new Gson().fromJson(getArguments().getString("teamData"), MyTeamOutput.DataBean.RecordsBean.class);
            }
        }
        LocalBroadcastManager.getInstance(mContext).registerReceiver(updates_receiver, new IntentFilter(CreateFootballTeamFragment.class.getSimpleName()));

    }

    public void setCaption(String playerId) {
        setCaption(responseMatchPlayersGK, playerId);
        setCaption(responseMatchPlayersST, playerId);
        setCaption(responseMatchPlayersDEF, playerId);
        setCaption(responseMatchPlayersMED, playerId);
    }

    public void setViceCaption(String playerId) {
        setViceCaption(responseMatchPlayersGK, playerId);
        setViceCaption(responseMatchPlayersST, playerId);
        setViceCaption(responseMatchPlayersDEF, playerId);
        setViceCaption(responseMatchPlayersMED, playerId);
    }

    public void setCaption(List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayers, String playerId) {
        if (responseMatchPlayers == null) return;
        for (int i = 0; i < responseMatchPlayers.size(); i++) {
            if (responseMatchPlayers.get(i).isSelected()) {
                if (responseMatchPlayers.get(i).getPlayerGUID().equals(playerId)) {
                    responseMatchPlayers.get(i).setPosition(Constant.POSITION_CAPTAIN);
                } else {
                    if (responseMatchPlayers.get(i).getPosition().equals(Constant.POSITION_CAPTAIN))
                        responseMatchPlayers.get(i).setPosition(Constant.POSITION_PLAYER);
                }

            }
        }
    }

    public void setViceCaption(List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayers, String playerId) {
        if (responseMatchPlayers == null) return;
        for (int i = 0; i < responseMatchPlayers.size(); i++) {
            if (responseMatchPlayers.get(i).isSelected()) {
                if (responseMatchPlayers.get(i).getPlayerGUID().equals(playerId)) {
                    responseMatchPlayers.get(i).setPosition(Constant.POSITION_VICE_CAPTAIN);
                } else {
                    if (responseMatchPlayers.get(i).getPosition().equals(Constant.POSITION_VICE_CAPTAIN))
                        responseMatchPlayers.get(i).setPosition(Constant.POSITION_PLAYER);
                }
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(updates_receiver);

        if (presenterImpl != null) presenterImpl.actionListingCancel();
    }

    @Override
    public int getLayout() {
        return R.layout.create_football_team_fragment;
    }

    List<PlayersOutput.DataBean.RecordsBean> recordsBeans = new ArrayList<>();


    @Override
    public void init() {
        mContext = getActivity();
        loader = new Loader(getCurrentView());

        ROLE_GK = true;
        ROLE_DEF = false;
        ROLE_ST = false;
        ROLE_MID = false;


        mRecyclerView.setNestedScrollingEnabled(true);
        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerViewPlayer.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        adapterteam = new TeamPlayerFootballAdapter(R.layout.team_player_adapter, this);
        mRecyclerViewPlayer.setAdapter(adapterteam);

        if (loader.getTryAgainView() != null)
            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callTask();
                }
            });
        matchDetailPresenter = new MatchDetailPresenterImpl(this, new UserInteractor());


        presenterImpl = new CreateTeamPresenterImpl(this, new UserInteractor());
        adapter = new CreateTeamAdapter(R.layout.list_item_players, getActivity(), recordsBeans, onItemClickCallback, onViewItemClickCallback, playingFlag, "Football");
        mRecyclerView.setAdapter(adapter);
        animationView = false;
        callMatchDetail();
        callTask();
    }

    void callMatchDetail() {
        MatchDetailInput mMatchDetailInput = new MatchDetailInput();
        mMatchDetailInput.setPrivacy("No");
        mMatchDetailInput.setMatchGUID(MatchGUID);
        mMatchDetailInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mMatchDetailInput.setStatus(Constant.Pending);
        mMatchDetailInput.setParams(Constant.MATCH_PARAMS);
        matchDetailPresenter.actionMatchdetail(mMatchDetailInput);
    }

    public void callTask() {

        PlayersInput mPlayersInput = new PlayersInput();
        mPlayersInput.setMatchGUID(MatchGUID);
        mPlayersInput.setParams(Constant.PLAYERS_PARAM);
        mPlayersInput.setIsActive("Yes");
        mPlayersInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        presenterImpl.actionMatchPlayers(mPlayersInput);
        // presenterImpl.footballMatchPlayers(AppSession.getInstance().getLoginSession().getResponse().getLoginSessionKey(), seriesId, matchId, localteamId, visitorteamId);
    }

    private void itemSelectedAnimation(final View view, final int position) {
        final ImageView ivCross = (ImageView) view.findViewById(R.id.iv_cross);
        final View view1 = view.findViewById(R.id.view_shadow);
        Animation startRotateAnimation = AnimationUtils.loadAnimation(AppController.getContext(), adapter.isSelected(position) ? R.anim.android_rotate_animation1 : R.anim.android_rotate_animation);
        startRotateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                animationView = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                adapter.toggleSelected(position);
                adapter.setCrossButton(view1, ivCross, position);
                counterUpdate();
                animationView = false;
                adapter.pointsRemaining = (100 - getTotalPlayersCredit());

                if (ROLE_MID) {
                    if (!canSelectMorePlayers(Constant.ROLE_MIDFIELDER)) {
                        adapter.disableItems(true, Constant.ROLE_MIDFIELDER, 3);
                    } else {
                        adapter.disableItems(false, Constant.ROLE_MIDFIELDER, getIndividualCount(responseMatchPlayersMED));
                    }
                }

                if (ROLE_DEF) {
                    if (!canSelectMorePlayers(Constant.ROLE_DEFENDER)) {
                        adapter.disableItems(true, Constant.ROLE_DEFENDER, 3);
                    } else {
                        adapter.disableItems(false, Constant.ROLE_DEFENDER, getIndividualCount(responseMatchPlayersDEF));
                    }
                }

                if (ROLE_ST) {

                    if (!canSelectMorePlayers(Constant.ROLE_FORWARD)) {
                        adapter.disableItems(true, Constant.ROLE_FORWARD, 1);
                    } else {
                        adapter.disableItems(false, Constant.ROLE_FORWARD, getIndividualCount(responseMatchPlayersST));
                    }
                }

                if (ROLE_GK) {
                    if (!canSelectMorePlayers(Constant.ROLE_GOALKEEPER)) {
                        adapter.disableItems(true, Constant.ROLE_GOALKEEPER, 1);
                    } else {
                        adapter.disableItems(false, Constant.ROLE_GOALKEEPER, getIndividualCount(responseMatchPlayersGK));
                    }
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivCross.startAnimation(startRotateAnimation);
    }

    private void itemWarningAnimation(View view) {
        Animation startRotateAnimation = AnimationUtils.loadAnimation(AppController.getContext(),
                R.anim.android_vibration_animation);
        startRotateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                animationView = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationView = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(startRotateAnimation);
    }

    @Override
    public void onLoadingError(String value) {
        loader.error(value);
    }

    @Override
    public void onLoadingSuccess(PlayersOutput response) {
        if (isLayoutAdded() && mRecyclerView != null) {
            isPlaying = response.getData().getPlaying11Announce();

            if (response.getData().getRecords().size() > 0) {
                for (int i = 0; i < response.getData().getRecords().size(); i++) {
                    if (i == 0) {
                        ctvTeam1Name.setText(response.getData().getRecords().get(0).getTeamNameShort());
                        teamType1 = response.getData().getRecords().get(0).getTeamGUID();
                        team1 = response.getData().getRecords().get(0).getTeamNameShort();
                    } else if (!response.getData().getRecords().get(0).getTeamGUID().equals(response.getData().getRecords().get(i).getTeamGUID())) {
                        ctvTeam2Name.setText(response.getData().getRecords().get(i).getTeamNameShort());
                        teamType2 = response.getData().getRecords().get(i).getTeamGUID();
                    }

                    if (response.getData().getRecords().get(i).getIsPlaying().equals("Yes")) {
                        adapter.playingFlag = 1;
                        break;
                    } else
                        adapter.playingFlag = 0;
                }
            }

            if (team != null) {
                updatePlayersData(response);
            }
            initPlayersData(response);

            if (getTotalPlayersCredit() > 100) {
                customTextViewNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_next_bg_white));
                customTextViewNext.setEnabled(false);

                customTextViewNext.setTextColor(getResources().getColor(R.color.grey_white));
            }


        }
    }

    @Override
    public void onHideLoading() {
        loader.hide();

    }

    @Override
    public void onShowLoading() {
        loader.start();
    }

    @Override
    public void onLoadingNotFound(String value) {
        loader.setNotFoundImage(getContext().getResources().getDrawable(R.drawable.ic_gallery));
        loader.dataNotFound(value);
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public boolean isLayoutAdded() {
        return (isAdded() && getActivity() != null);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }

    @Override
    public void onMatchSuccess(MatchDetailOutPut mMatchDetailOutPut) {
        if (isAdded() && getActivity() != null) {
            loader.hide();
            teamsVS.setText(mMatchDetailOutPut.getData().getTeamNameShortLocal()
                    + " " + AppUtils.getStrFromRes(R.string.vs)
                    + " " + mMatchDetailOutPut.getData().getTeamNameShortVisitor());

            localTeamName = mMatchDetailOutPut.getData().getTeamNameShortLocal();

            if (customTextViewFullTime != null || mMatchDetailOutPut.getData() != null) {
                if (mMatchDetailOutPut.getData().getStatus() != null) {
                    switch (mMatchDetailOutPut.getData().getStatus()) {
                        case Constant.Pending:
                            setTime(mMatchDetailOutPut.getData().getMatchStartDateTime(), mMatchDetailOutPut.getData().getMatchDate(),
                                    mMatchDetailOutPut.getData().getMatchTime(), mMatchDetailOutPut.getData().getCurrentDateTime());
                            break;
                        case Constant.Running:
                            customTextViewFullTime.setText(mMatchDetailOutPut.getData().getStatus());
                            customTextViewFullTime.setTextColor(getResources().getColor(R.color.yellow));
                            break;
                        case Constant.Completed:
                            customTextViewFullTime.setText(mMatchDetailOutPut.getData().getStatus());
                            customTextViewFullTime.setTextColor(getResources().getColor(R.color.green));
                            break;
                        default:
                            customTextViewFullTime.setText(mMatchDetailOutPut.getData().getStatus());
                            break;
                    }
                }
            }
        }
    }


    CountDownTimer countDownTimer;

    public void setTime(String matchDateTime, final String matchDate, final String matchTime, String currentTime) {
        customTextViewFullTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_time, 0, 0, 0);
        try {
            if (countDownTimer != null) countDownTimer.cancel();
            if (customTextViewFullTime != null) {

                long remainingTime = TimeUtils.getTimeDifference(matchDateTime,
                        currentTime);

                //ctv_timer.setPaintFlags(ctv_timer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                if (TimeUtils.isThisDateValid(matchDateTime, "yyyy-MM-dd HH:mm:ss")) {
                    if (TimeUnit.MILLISECONDS.toHours(remainingTime) > Constant.SHOW_TIME_LIMIT_HRS) {
                        customTextViewFullTime.setText(TimeUtils.getMatchDateOnly(matchDate));
                    } else {
                        //need to implement counter
                        countDownTimer = new CountDownTimer(remainingTime, TimeUnit.SECONDS.toMillis(1)) {
                            public void onTick(long millisUntilFinished) {
                                if (customTextViewFullTime != null)
                                    customTextViewFullTime.setText(TimeUtils.getRemainsTime(millisUntilFinished));
                            }

                            public void onFinish() {
                                if (customTextViewFullTime != null)
                                    customTextViewFullTime.setText(TimeUtils.getDisplayFullDate1(matchDate, matchTime));
                            }
                        };
                        if (countDownTimer != null) {
                            countDownTimer.start();
                        }
                    }
                } else {
                    customTextViewFullTime.setText(TimeUtils.getMatchDateOnly(matchDate));
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            customTextViewFullTime.setText("N/A");
        }
    }

    @Override
    public void onMatchFailure(String errMsg) {

        if (isAdded() && getActivity() != null) {
            loader.error(errMsg);
        }
    }

    @Override
    public void onShowSnackBar(@NonNull String message) {
        // Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        AppUtils.showToast(mContext, message);
    }


    private List<PlayersOutput.DataBean.RecordsBean> getSelectedPlayersData(List<PlayersOutput.DataBean.RecordsBean> response) {
        List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayers = new ArrayList<>();
        for (int i = 0; i < response.size(); i++) {
            if (response.get(i).isSelected()) responseMatchPlayers.add(response.get(i));
        }
        return responseMatchPlayers;
    }

    private void updatePlayersData(PlayersOutput response) {
        if (team == null || response == null) return;

        teamId = team.getUserTeamGUID();
        for (int i = 0; i < team.getUserTeamPlayers().size(); i++) {
            for (int k = 0; k < response.getData().getRecords().size(); k++) {
                if (team.getUserTeamPlayers().get(i).getPlayerGUID().equals(response.getData().getRecords().get(k).getPlayerGUID())) {
                    response.getData().getRecords().get(k).setSelected(true);
                    response.getData().getRecords().get(k).setPosition(team.getUserTeamPlayers().get(i).getPlayerPosition());
                }
            }
        }
    }

    private void initPlayersData(PlayersOutput response) {

        for (int i = 0; i < response.getData().getRecords().size(); i++) {
            initPlayers(response.getData().getRecords().get(i));
        }

        counterUpdate();

        ll_wk.performClick();
//        SELECTED_ROLE = Constant.ROLE_GOALKEEPER;
//        viewSelected(Constant.ROLE_GOALKEEPER);
    }

    private void initPlayers(PlayersOutput.DataBean.RecordsBean bean) {
        switch (bean.getPlayerRole()) {
            case Constant.ROLE_GOALKEEPER:
                responseMatchPlayersGK.add(bean);
                break;
            case Constant.ROLE_DEFENDER:
                responseMatchPlayersDEF.add(bean);
                break;
            case Constant.ROLE_FORWARD:
                responseMatchPlayersST.add(bean);
                break;
            case Constant.ROLE_MIDFIELDER:
                responseMatchPlayersMED.add(bean);
                break;
        }
    }

    private void viewSelected(String type) {
        switch (type) {
            case Constant.ROLE_GOALKEEPER:
                customTextViewTITLE.setText(AppUtils.getStrFromRes(R.string.pick_one_gc));
                adapter.updateTeamData(responseMatchPlayersGK);
                break;
            case Constant.ROLE_DEFENDER:
                customTextViewTITLE.setText(AppUtils.getStrFromRes(R.string.pick_three_five_DEF));
                adapter.updateTeamData(responseMatchPlayersDEF);
                break;
            case Constant.ROLE_FORWARD:
                customTextViewTITLE.setText(AppUtils.getStrFromRes(R.string.pick_one_three_ST));
                adapter.updateTeamData(responseMatchPlayersST);
                break;
            case Constant.ROLE_MIDFIELDER:
                customTextViewTITLE.setText(AppUtils.getStrFromRes(R.string.pick_three_five_MID));
                adapter.updateTeamData(responseMatchPlayersMED);
                break;
            default:

                break;
        }
        selectPlayerBackground(type);
        counterUpdate();
    }

    private void counterUpdate() {

        adapterteam.notifyDataSetChanged();

        customTextViewWK.setText(getIndividualCount(Constant.ROLE_GOALKEEPER) + "");
        customTextViewBAT.setText(getIndividualCount(Constant.ROLE_DEFENDER) + "");
        customTextViewAR.setText(getIndividualCount(Constant.ROLE_MIDFIELDER) + "");
        customTextViewBOWL.setText(getIndividualCount(Constant.ROLE_FORWARD) + "");

        customTextViewPlayers.setText(getTotalSelectedPlayers() + "/11");
        customTextViewCreditLeft.setText((100 - getTotalPlayersCredit()) + "/100");

        ctvTeam1Count.setText("" + getSelectedTeamMemberCount(teamType1));
        ctvTeam2Count.setText("" + getSelectedTeamMemberCount(teamType2));

        if (getTotalSelectedPlayers() >= 11) {
            customTextViewNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_next_bg_green));
            customTextViewNext.setTextColor(getResources().getColor(R.color.black));
            customTextViewNext.setEnabled(true);
            counter11Selected();
        } else {
            customTextViewNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_next_bg_white));
            customTextViewNext.setEnabled(false);
            customTextViewNext.setTextColor(getResources().getColor(R.color.grey_white));


            if (getIndividualCount(Constant.ROLE_GOALKEEPER) == 0) {
                customTextViewWK.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_bg_white));
            } else {
                customTextViewWK.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_blue_bg_white));
            }

            if (getIndividualCount(Constant.ROLE_DEFENDER) == 0) {
                customTextViewBAT.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_bg_white));
            } else if (getIndividualCount(Constant.ROLE_DEFENDER) == 5) {
                customTextViewBAT.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_blue_bg_white));
            } else {
                customTextViewBAT.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_yellow_bg_white));
            }

            if (getIndividualCount(Constant.ROLE_FORWARD) == 0) {
                customTextViewBOWL.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_bg_white));
            } else if (getIndividualCount(Constant.ROLE_FORWARD) == 3) {
                customTextViewBOWL.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_blue_bg_white));
            } else {
                customTextViewBOWL.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_yellow_bg_white));
            }

            if (getIndividualCount(Constant.ROLE_MIDFIELDER) == 0) {
                customTextViewAR.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_bg_white));
            } else if (getIndividualCount(Constant.ROLE_MIDFIELDER) == 5) {
                customTextViewAR.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_blue_bg_white));
            } else {
                customTextViewAR.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_yellow_bg_white));
            }

        }

    }

    private void counter11Selected() {
        customTextViewWK.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_blue_bg_white));
        customTextViewBAT.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_blue_bg_white));
        customTextViewAR.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_blue_bg_white));
        customTextViewBOWL.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_blue_bg_white));
    }

    private void selectPlayerBackground(String type) {
        switch (type) {
            case Constant.ROLE_GOALKEEPER:
                customImageViewWK.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_white_border_light));
                customImageViewBAT.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                customImageViewAR.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                customImageViewBOWL.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                break;
            case Constant.ROLE_DEFENDER:
                customImageViewWK.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                customImageViewBAT.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_white_border_light));
                customImageViewAR.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                customImageViewBOWL.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                break;
            case Constant.ROLE_FORWARD:
                customImageViewWK.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                customImageViewBAT.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                customImageViewAR.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_white_border_light));
                customImageViewBOWL.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                break;
            case Constant.ROLE_MIDFIELDER:
                customImageViewWK.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                customImageViewBAT.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                customImageViewAR.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray_border_gray));
                customImageViewBOWL.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_white_border_light));
                break;
        }
    }

    public int getTotalSelectedPlayers() {
        Log.d("roleeeeeeees", "onAnimationEnd: type "+getIndividualCount(responseMatchPlayersGK)
                +"     "+ getIndividualCount(responseMatchPlayersDEF)
                +"    " + getIndividualCount(responseMatchPlayersST)
                +"    "  + getIndividualCount(responseMatchPlayersMED) );

        return getIndividualCount(responseMatchPlayersGK)
                + getIndividualCount(responseMatchPlayersDEF)
                + getIndividualCount(responseMatchPlayersST)
                + getIndividualCount(responseMatchPlayersMED);
    }




    private int getNeedToSelectPlayers(String type) {
        int count = 0;
        if (!type.equals(Constant.ROLE_GOALKEEPER) && getIndividualCount(responseMatchPlayersGK) < 1) {
            count = count + 1;
        }
        if (!type.equals(Constant.ROLE_DEFENDER) && getIndividualCount(responseMatchPlayersDEF) < 3) {
            count = count + (3 - getIndividualCount(responseMatchPlayersDEF));
        }
        if (!type.equals(Constant.ROLE_FORWARD) && getIndividualCount(responseMatchPlayersST) < 1) {
            count = count + 1;
        }
        if (!type.equals(Constant.ROLE_MIDFIELDER) && getIndividualCount(responseMatchPlayersMED) < 3) {
            count = count + (3 - getIndividualCount(responseMatchPlayersMED));
        }
        return count;
    }

    private static final String TAG = "CriteriaTest";
    private int getRemainsPlayersSelection() {
        Log.d(TAG, "getRemainsPlayersSelection: getTotalSelectedPlayers()"+getTotalSelectedPlayers());
        return  11 - getTotalSelectedPlayers();

    }

    private boolean canSelectMorePlayers(String type) {
        Log.d(TAG, "canSelectMorePlayers: getRemainsPlayersSelection()->"+getRemainsPlayersSelection());
        Log.d(TAG, "canSelectMorePlayers: getNeedToSelectPlayers()->"+getNeedToSelectPlayers(type));
        int individualCount = getIndividualCount(type);
        switch (type) {
            case Constant.ROLE_GOALKEEPER:
                if (individualCount==1) return false;
                break;
            case Constant.ROLE_DEFENDER:
                if (individualCount==5) return false;
                break;
            case Constant.ROLE_FORWARD:
                if (individualCount==3) return false;
                break;
            case Constant.ROLE_MIDFIELDER:
                if (individualCount==5) return false;
                break;
        }
        return getRemainsPlayersSelection() - getNeedToSelectPlayers(type) > 0;
    }

    private int getIndividualCount(String type) {
        switch (type) {
            case Constant.ROLE_GOALKEEPER:
                return getIndividualCount(responseMatchPlayersGK);
            case Constant.ROLE_DEFENDER:
                return getIndividualCount(responseMatchPlayersDEF);
            case Constant.ROLE_FORWARD:
                return getIndividualCount(responseMatchPlayersST);
            case Constant.ROLE_MIDFIELDER:
                return getIndividualCount(responseMatchPlayersMED);
        }
        return 0;
    }

    public int getIndividualCount(List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayers) {
        if (responseMatchPlayers == null) return 0;
        int count = 0;
        for (int i = 0; i < responseMatchPlayers.size(); i++) {
            if (responseMatchPlayers.get(i).isSelected()) count++;
        }
        return count;
    }

    private int getSelectedTeamMemberCount(String type) {
        return getSelectedTeamMemberCount(responseMatchPlayersGK, type)
                + getSelectedTeamMemberCount(responseMatchPlayersDEF, type)
                + getSelectedTeamMemberCount(responseMatchPlayersST, type)
                + getSelectedTeamMemberCount(responseMatchPlayersMED, type);
    }

    public int getSelectedTeamMemberCount(List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayers, String team) {
        if (responseMatchPlayers == null) return 0;
        int count = 0;
        for (int i = 0; i < responseMatchPlayers.size(); i++) {
            if (responseMatchPlayers.get(i).getTeamGUID().equals(team) && responseMatchPlayers.get(i).isSelected())
                count++;
        }
        return count;
    }

    private float getTotalPlayersCredit() {
        return getTotalPlayersCredit(responseMatchPlayersGK)
                + getTotalPlayersCredit(responseMatchPlayersDEF)
                + getTotalPlayersCredit(responseMatchPlayersST)
                + getTotalPlayersCredit(responseMatchPlayersMED);
    }

    public float getTotalPlayersCredit(List<PlayersOutput.DataBean.RecordsBean> responseMatchPlayers) {
        if (responseMatchPlayers == null) return 0;
        float points = 0;
        for (int i = 0; i < responseMatchPlayers.size(); i++) {
            if (responseMatchPlayers.get(i).isSelected()) {
                try {
                    points = points + Float.parseFloat(responseMatchPlayers.get(i).getPlayerSalary());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return points;
    }
}
