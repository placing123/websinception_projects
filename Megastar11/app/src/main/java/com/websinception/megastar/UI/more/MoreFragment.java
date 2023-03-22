package com.websinception.megastar.UI.more;

import android.support.v7.widget.CardView;
import android.view.View;

import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.SpinWheel.SpinWheelActivity;
import com.websinception.megastar.UI.contestInviteCode.InviteCodes;
import com.websinception.megastar.UI.home.HomeNavigation;
import com.websinception.megastar.UI.inviteFriends.InviteFriendsActivity;
import com.websinception.megastar.UI.mlb.ReferralUsersActivity;
import com.websinception.megastar.UI.outsideEvents.ContactUsActivity;
import com.websinception.megastar.UI.outsideEvents.OutSideEvent;
import com.websinception.megastar.UI.pointSystem.FootballPointSystemActivity;
import com.websinception.megastar.UI.pointSystem.PointSystemActivity;
import com.websinception.megastar.UI.verifyAccount.VerifyAccountActivity;
import com.websinception.megastar.appApi.interactors.IUserInteractor;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseFragment;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanOutput.SpinWheelOutput;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.NetworkUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class MoreFragment extends BaseFragment {

    @BindView(R.id.inviteFriends)
    CustomTextView inviteFriends;

    @BindView(R.id.contestInviteCode)
    CustomTextView contestInviteCode;

    @BindView(R.id.fantasyPointSystem)
    CustomTextView fantasyPointSystem;



    @BindView(R.id.help_desk)
    CustomTextView help_desk;

    @BindView(R.id.workWithUs)
    CustomTextView workWithUs;

    @BindView(R.id.about_us)
    CustomTextView about_us;

    @BindView(R.id.legality)
    CustomTextView legality;

    @BindView(R.id.version)
    CustomTextView version;

    @BindView(R.id.spin)
    CardView spin;

    @BindView(R.id.verify_your_account)
    CustomTextView verify_your_account;
    private UserInteractor mInteractor;

    @OnClick(R.id.verifyYourAccountLin)
    void onVerifyAccountClick() {
        VerifyAccountActivity.start(getActivity());
    }

    @OnClick(R.id.offerLin)
    void onOfferLinClick() {

        // OffersActivity.start(getActivity());

    }

    @OnClick(R.id.inviteFriendsLin)
    void onInviteFriendsClick() {
        InviteFriendsActivity.start(getActivity());
    }

    @OnClick(R.id.myReferrals)
    void onReferAndEarnClick() {
        ReferralUsersActivity.start(getActivity());
    }

    @OnClick(R.id.contestInviteCodeLin)
    void onContestinviteClick() {
        InviteCodes.start(getActivity(), "0");
    }

    @OnClick(R.id.fantasyFootballPointSystemLin)
    void onFantasyFootballPointClick() {
        FootballPointSystemActivity.start(getActivity());
    }

    @OnClick(R.id.fantasyKabaddiPointSystemLin)
    void onFantasyKabaddiPointClick() {
        OutSideEvent.start(getActivity(), "POINT_SYSTEM", Constant.POINT_SYSTEM_KABADDI_URL);
    }

    @OnClick(R.id.fantasyPointSystemLin)
    void onFantasyPointClick() {
        OutSideEvent.start(getActivity(), AppUtils.getStrFromRes(R.string.how_to_play), Constant.POINT_SYSTEM_CRICKET_URL);

        //PointSystemActivity.start(getActivity());
    }

    @OnClick(R.id.how_to_playLin)
    void onHowToPlayClick() {
        OutSideEvent.start(getActivity(), AppUtils.getStrFromRes(R.string.how_to_play), Constant.HOW_TO_PLAY_URL);
    }


    @OnClick(R.id.how_to_playLinFootball)
    void onHowToPlayFootbalClick() {
        OutSideEvent.start(getActivity(), AppUtils.getStrFromRes(R.string.how_to_play_football), Constant.HOW_TO_PLAY_URL_FOOTBALL);
    }

    @OnClick(R.id.how_to_playLin_auction)
    void onHowToPlayAuctionClick() {
        OutSideEvent.start(getActivity(), AppUtils.getStrFromRes(R.string.how_to_play_auction), Constant.HOW_TO_PLAY_AUCTION_URL);
    }


    @OnClick(R.id.how_to_playLin_gully)
    void onHowToPlayDraftClick() {
        OutSideEvent.start(getActivity(), AppUtils.getStrFromRes(R.string.how_to_play_gc), Constant.HOW_TO_PLAY_GC_URL);
    }


    @OnClick(R.id.blog)
    void onBlogClick() {
        OutSideEvent.start(getActivity(), AppUtils.getStrFromRes(R.string.blog), Constant.BLOG);
    }

    @OnClick(R.id.how_to_playLin_football)
    void onHowToPlayFootballClick() {
        // OutSideEvent.start(getActivity(), AppUtils.getStrFromRes(R.string.how_to_play), Constant.HOW_TO_PLAY_FOOTBALL_URL);
    }

    @OnClick(R.id.how_to_playLin_kabaddi)
    void onHowToPlayKabaddiClick() {
        // OutSideEvent.start(getActivity(), AppUtils.getStrFromRes(R.string.how_to_play), Constant.HOW_TO_PLAY_KABADDI_URL);
    }

    @OnClick(R.id.help_deskLin)
    void onHelopdeskClick() {
        OutSideEvent.start(getActivity(), "HELP_DESK", Constant.HELP_DESK_URL);
    }

    @OnClick(R.id.workWithUsLin)
    void onworkWithUsClick() {
       OutSideEvent.start(getActivity(), "WORK_WITH_US", Constant.WORK_WITH_US);

//        ContactUsActivity.start(getActivity());
    }

    @OnClick(R.id.about_usLin)
    void onAboutUsClick() {
        OutSideEvent.start(getActivity(), "ABOUT_US", Constant.ABOUT_URL);
    }


    @OnClick(R.id.legalityLin)
    void onLegalClick() {
        OutSideEvent.start(getActivity(), "LEGALITY", Constant.LEGALITY_URL);
    }

    @OnClick(R.id.fantasy_cricket)
    void onFantasyCricketClick() {
        OutSideEvent.start(getActivity(), "LEGALITY", Constant.FANTASY_CRICKET);
    }

    @OnClick(R.id.spin)
    void onspinClick() {
        SpinWheelActivity.start(getActivity(),false);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_more_fragment;
    }

    @Override
    public void init() {

        mInteractor = new UserInteractor();
        version.setText(AppUtils.getVersionInfo());
        //apiCallGetSpinData();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((HomeNavigation) getActivity()).changeNavigationSelction(3);
    }



    private void apiCallGetSpinData() {
        if (NetworkUtils.isConnected(getActivity())) {

            LoginInput loginInput = new LoginInput();
            loginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            mInteractor.getSpinWheelData(loginInput, new IUserInteractor.OnResponseSpinWheeListener() {

                @Override
                public void onSuccess(SpinWheelOutput spinWheelOutput) {

                    if (spinWheelOutput.getData().getLuckyWheelActive().equalsIgnoreCase("Yes")) {
                       if (spin!= null)spin.setVisibility(View.VISIBLE);
                    }else {
                        if (spin!= null) spin.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNotFound(String error) {

                }

                @Override
                public void onError(String errorMsg) {

                }

                @Override
                public void OnSessionExpire() {

                }
            });
        }
    }

}
