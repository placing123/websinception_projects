package com.mw.fantasy.UI.userProfile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.addMoney.AddMoneyActivity;
import com.mw.fantasy.UI.changePassword.ChangePasswordActivity;
import com.mw.fantasy.UI.changeTeamName.ChangeTeamName;
import com.mw.fantasy.UI.home.HomeNavigation;
import com.mw.fantasy.UI.inviteFriends.InviteFriendsActivity;
import com.mw.fantasy.UI.leaderboardRanking.LeaderboardRankingActivity;
import com.mw.fantasy.UI.loginRagisterModule.LoginScreen;
import com.mw.fantasy.UI.myAccount.MyAccountActivity;
import com.mw.fantasy.UI.myAccount.MyAccountParentPresenterImpl;
import com.mw.fantasy.UI.outsideEvents.OutSideEvent;
import com.mw.fantasy.UI.personalDetails.PersonalDetailsActivity;
import com.mw.fantasy.UI.transections.TransactionActivity;
import com.mw.fantasy.UI.verifyAccount.VerifyAccountActivity;
import com.mw.fantasy.UI.withdrawAmount.WithdrawAmountDialogActivity;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseFragment;
import com.mw.fantasy.base.Loader;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.UploadImageInput;
import com.mw.fantasy.beanOutput.AvatarListOutput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.customView.CustomImageView;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.textdrawable.TextDrawable;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.Constant;
import com.mw.fantasy.utility.NetworkUtils;
import com.mw.fantasy.utility.OnItemClickListener;
import com.mw.fantasy.utility.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class UserProfileFragment extends BaseFragment implements MyProfileParentView, AppBarLayout.OnOffsetChangedListener {

    public static final int RQ_OPEN_GALLERY = 788;
    public static final int RQ_PERMISSION = 789;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    final Uri imageUri = Uri.parse("");


    //https://www.funtush11.com/images/logo.png
    // http://i.imgur.com/VIlcLfg.jpg


    public static final int REQUEST_CODE_AVATAR = 402;
    public static final int REQUEST_TEAM_NAME = 403;


    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    //      private LogoutPresenterImpl mMainPresenterImpl;
    AlertDialog alertLogoutDialog;
    Double winningamt = 0.0;
    private ProgressDialog progressDialog;

    @BindView(R.id.parentLayout)
    LinearLayout parentLayout;

    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;

    @BindView(R.id.imageview_placeholder)
    ImageView coverImage;

    @BindView(R.id.userName)
    CustomTextView userName;

    @BindView(R.id.framelayout_title)
    FrameLayout framelayoutTitle;

    @BindView(R.id.linearlayout_title)
    LinearLayout linearlayoutTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textview_title)
    CustomTextView textviewTitle;

    @BindView(R.id.avatar)
    CustomImageView avatar;

    @BindView(R.id.seekBar)
    SeekBar seek_bar;

    @BindView(R.id.levelLeft)
    CustomTextView levelLeft;

    @BindView(R.id.levelRight)
    CustomTextView levelRight;

    @BindView(R.id.championLevelTitle)
    CustomTextView championLevelTitle;

    @BindView(R.id.playFor)
    CustomTextView playFor;

    @BindView(R.id.playForeAmmount)
    CustomTextView playForeAmmount;

    @BindView(R.id.more)
    CustomTextView more;

    @BindView(R.id.join)
    CustomTextView join;

    @BindView(R.id.yourAccount)
    CustomTextView yourAccount;

    @BindView(R.id.joinAmmount)
    CustomTextView joinAmmount;

    @BindView(R.id.cashContain)
    CustomTextView cashContain;

    @BindView(R.id.unlockRewardText)
    CustomTextView unlockRewardText;

    @BindView(R.id.cashBonusLin)
    LinearLayout cashBonusLin;

    @BindView(R.id.cashBonusCard)
    CustomTextView cashBonusCard;

    @BindView(R.id.noRewardEarnd)
    CustomTextView noRewardEarnd;

    @BindView(R.id.noRewardEarndtext)
    CustomTextView noRewardEarndtext;

    @BindView(R.id.unutilized)
    CustomTextView unutilized;

    @BindView(R.id.unutilizedAmmount)
    CustomTextView unutilizedAmmount;

    @BindView(R.id.winnings)
    CustomTextView winnings;

    @BindView(R.id.winningsAmount)
    CustomTextView winningsAmount;

    @BindView(R.id.cashBonusText)
    CustomTextView cashBonusText;

    @BindView(R.id.cashBonusAmount)
    CustomTextView cashBonusAmount;

    @BindView(R.id.addcash)
    CustomTextView addcash;

    @BindView(R.id.withdraw)
    CustomTextView withdraw;

    @BindView(R.id.contest)
    CustomTextView contest;

    @BindView(R.id.contestCount)
    CustomTextView contestCount;

    @BindView(R.id.matches)
    CustomTextView matches;

    @BindView(R.id.matchesCount)
    CustomTextView matchesCount;

    @BindView(R.id.series)
    CustomTextView series;

    @BindView(R.id.seriesCount)
    CustomTextView seriesCount;

    @BindView(R.id.wines)
    CustomTextView wines;

    @BindView(R.id.winsCount)
    CustomTextView winesCount;

    @BindView(R.id.noRanking)
    CustomTextView noRanking;

    @BindView(R.id.nofriend)
    CustomTextView nofriend;

    @BindView(R.id.inviteFriendsText)
    CustomTextView inviteFriendsText;

    @BindView(R.id.inviteFriends)
    CustomTextView inviteFriends;

    @BindView(R.id.fullProfile)
    CustomTextView fullProfile;

    @BindView(R.id.name_tv)
    CustomTextView name_tv;

    @BindView(R.id.email_tv)
    CustomTextView email_tv;

    @BindView(R.id.changePassword)
    CustomTextView changePassword;

    @BindView(R.id.logout)
    CustomTextView logout;

    @BindView(R.id.mCustomeTextInviteFriends)
    CustomTextView mCustomeTextInviteFriends;

    @BindView(R.id.info_level)
    ImageView info_level;

    @BindView(R.id.invitedFriends)
    LinearLayout invitedFriends;

    @BindView(R.id.noFriendsLin)
    LinearLayout noFriendsLin;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.inviteListLin)
    LinearLayout inviteListLin;

    int gametype;
    Loader loader;
    Context mContext;
    private int imageCaptureType = 0;
    private AlertDialog alertDialog;

    @OnClick(R.id.fl_pic_root)
    void onProfilePictureClick() {

        imageCaptureType = 1;
        checkPermission();
        /*PopupMenu menu = new PopupMenu(getContext(), avatar);
        menu.getMenu().add("Open Camera");
        menu.getMenu().add("Open Gallery");
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Open Camera")) {
                    imageCaptureType = 2;
                } else {
                    imageCaptureType = 1;
                }
                checkPermission();
                return true;
            }
        });*/

       /* Intent in = new Intent(mContext, UserAvatarActivity.class);

        startActivityForResult(in, REQUEST_CODE_AVATAR);*/

    }


    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (imageCaptureType == 1) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RQ_OPEN_GALLERY);
            } else if (imageCaptureType == 2) {

            }
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, RQ_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RQ_PERMISSION) {
            checkPermission();
        }
    }

    @OnClick(R.id.cashBonusText)
    void cashBonusText() {
        if (!responseLogin.getData().getCashbonusMessage().equalsIgnoreCase("")) {
            AppUtils.showPopupBonus(getActivity(), cashBonusText, parentLayout, responseLogin.getData().getCashbonusMessage());
        }

    }

    @OnClick(R.id.recentTransaction)
    void onRecentTransactioneClick() {

        TransactionActivity.start(mContext);

    }


    @Override
    public void onResume() {

        callViewProfile();
        super.onResume();
    }

    @OnClick(R.id.userName)
    void onUserNameChange() {
        // ChangeTeamName.start(getActivity());

        Intent in = new Intent(mContext, ChangeTeamName.class);
        if (responseLogin.getData().getUsername().length() == 0) {
            in.putExtra("teamName", "");
        } else {
            in.putExtra("teamName", userName.getText().toString());
        }
        startActivityForResult(in, REQUEST_TEAM_NAME);
    }


  /*  @OnClick(R.id.mCustomeTextInviteFriends)
    void onFriendsClick() {

        if (myAccount.getResponse().getInvitedFriendsList().size() != 0) {
            // MyInvitedFriends.start(getActivity());
        }

    }*/

    @OnClick(R.id.info_level)
    void onInfoClick() {
        OutSideEvent.start(getActivity(), "LEVEL", Constant.LEVEL_URL);
    }

    @OnClick(R.id.changePassword)
    public void onClickChangePassword() {
        ChangePasswordActivity.start(getActivity());
    }

    @OnClick(R.id.ll_fullProfile_root)
    public void onClickFullProfile() {
        //PersonalDetailsActivity.start(getActivity());

        startActivityForResult(PersonalDetailsActivity.startForResult(mContext), 111);
    }

    @OnClick(R.id.ctv_ranking)
    public void onClickleaderboardRanking() {

        LeaderboardRankingActivity.start(getActivity());
    }

    @OnClick(R.id.ll_logout_root)
    void onClickLogout() {

        if (alertLogoutDialog == null) {
            alertLogoutDialog = new AlertDialog(getActivity(),
                    AppUtils.getStrFromRes(R.string.logout_app), AppUtils.getStrFromRes(R.string.okay), AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                @Override
                public void onYesClick() {

                    AppSession.getInstance().clearSession();

                    LoginScreen.start(mContext, "");
                    signOut();

                    // mMainPresenterImpl.actionLogoutBtn(AppSession.getInstance().getLoginSession().getResponse().getUserId(), DeviceUtils.getDeviceId(getContext()));
                }

                @Override
                public void onNoClick() {

                }
            });
        }
        alertLogoutDialog.show();


    }


    @OnClick(R.id.inviteFriends)
    void onClickInviteFriends() {
        // InviteFriendsActivity.start(getActivity());

    }

    @OnClick(R.id.inviteFriends1)
    void onClickInviteFriends1() {
        // InviteFriendsActivity.start(getActivity());

        InviteFriendsActivity.start(getActivity());

    }

    @OnClick(R.id.addcash)
    void onClickAddCash() {

        Intent starter = new Intent(getActivity(), AddMoneyActivity.class);
        startActivityForResult(starter, 112);
        ((Activity) getActivity()).overridePendingTransition(R.anim.dialog_open, R.anim.dialog_close);

    }

    @OnClick(R.id.yourAccount)
    void onClickMyAccount() {

        MyAccountActivity.start(getActivity());
    }


    @OnClick(R.id.withdraw)
    void onClickWithdrawal() {
        if (responseLogin != null) {

            if ((
                    responseLogin.getData().getEmailStatus().equals("Verified")
                            && responseLogin.getData().getPhoneStatus().equals("Verified")
                            && responseLogin.getData().getPanStatus().equals("Verified"))
            ) {
                if (winningamt == 0) {
                    showSnackBar(AppUtils.getStrFromRes(R.string.Sorry_Insufficient_winning_amount));
                    return;
                } else {
                    WithdrawAmountDialogActivity.start(mContext);
                }
            } else {
                VerifyAccountActivity.start(mContext);
            }
        }

    }

    private MyProfileParentPresenterImpl mMyProfileParentPresenterImpl;
    private MyAccountParentPresenterImpl mMyAccountParentPresenterImpl;

    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

        }
    };

    @Override
    public int getLayout() {
        return R.layout.user_profile;
    }

    @Override
    public void init() {
        progressDialog = new ProgressDialog(getContext());
        Fresco.initialize(getActivity());

        toolbar.setTitle("");
        appbar.addOnOffsetChangedListener(this);

        startAlphaAnimation(textviewTitle, 0, View.INVISIBLE);

        //set avatar and cover
        avatar.setImageURI(imageUri);
        coverImage.setImageResource(R.drawable.profile_banner);

        if (isAttached()) {
            mContext = getActivity();
            //initiate loader
            loader = new Loader(getCurrentView());
            loader.getTryAgainView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callViewProfile();
//                    mMyAccountParentPresenterImpl.actionViewAccount(AppSession.getInstance().getLoginSession().getResponse().getLoginSessionKey());
                }
            });
            gametype = getArguments().getInt("gametype", gametype);

            //mMainPresenterImpl = new LogoutPresenterImpl(this, new UserInteractor());
            //view profile calling
            mMyProfileParentPresenterImpl = new MyProfileParentPresenterImpl(this, new UserInteractor());

            //  mMyAccountParentPresenterImpl = new MyAccountParentPresenterImpl(this, new UserInteractor());


            // mMyAccountParentPresenterImpl.actionViewAccount(AppSession.getInstance().getLoginSession().getResponse().getLoginSessionKey());

            callViewProfile();

        }
    }

    void callViewProfile() {
        LoginInput mLoginInput = new LoginInput();
        mLoginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mLoginInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
        mLoginInput.setParams(Constant.GET_PROFILE_PARAMS);
        mLoginInput.setCashbonusExpiry("Yes");
        mMyProfileParentPresenterImpl.actionViewProfile(mLoginInput);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
//
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);

        if (toolbar != null)
            toolbar.setAlpha(percentage);

    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(textviewTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(textviewTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(linearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(linearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMyProfileParentPresenterImpl != null)
            mMyProfileParentPresenterImpl.actionLoginCancel();
    }


    @Override
    public boolean isAttached() {
        return isAdded() && getActivity() != null;
    }


    @Override
    public void hideLoading() {

        if (isAdded() && getActivity() != null) {
//            loader.hide();
        }
    }

    LoginResponseOut responseLogin;


    @Override
    public void onProfileSuccess(LoginResponseOut responseLogin) {

        hideLoading();
        if (isAdded() && getActivity() != null) {
            loader.hide();

            winningamt = Double.valueOf(responseLogin.getData().getWinningAmount());

            if ((
                    responseLogin.getData().getEmailStatus().equals("Verified")
                            && responseLogin.getData().getPhoneStatus().equals("Verified")
                            && responseLogin.getData().getPanStatus().equals("Verified")
                            && responseLogin.getData().getBankStatus().equals("Verified"))
            ) {
                if (winningamt == 0) {
                    // withdraw.setTextColor(getResources().getColor(R.color.warm_grey));
                    //withdraw.setBackground(getResources().getDrawable(R.drawable.shedow_blue_background));
                    withdraw.setText(R.string.withdraw);
                } else {
                    //  withdraw.setTextColor(getResources().getColor(R.color.white));
                    // withdraw.setBackground(getResources().getDrawable(R.drawable.shedow_yellow_background));
                    withdraw.setText(R.string.withdraw);
                }
            } else {
                // withdraw.setTextColor(getResources().getColor(R.color.white));
                //withdraw.setBackground(getResources().getDrawable(R.drawable.shedow_yellow_background));
                withdraw.setText(R.string.Verify);
            }

        /*    if (winningamt == 0) {
                withdraw.setTextColor(getResources().getColor(R.color.black));
                withdraw.setBackground(getResources().getDrawable(R.drawable.shedow_yellow_background));
                if (responseLogin.getData().getEmailStatus().equals("Verified")
                        && responseLogin.getData().getPhoneStatus().equals("Verified")
                        && responseLogin.getData().getPanStatus().equals("Verified")) {
                    if (responseLogin.getData().getBankStatus().equals("Verified")
                            || responseLogin.getData().getAadharStatus().equals("Verified")) {
                        withdraw.setText(R.string.WITHDRAW);
                    } else {
                        withdraw.setText(R.string.VERIFY);
                    }
                } else {
                    withdraw.setText(R.string.VERIFY);
                }
            } else {
                withdraw.setBackground(getResources().getDrawable(R.drawable.shedow_blue_background));
                withdraw.setText(R.string.WITHDRAW);
            }*/

            // AppSession.getInstance().setLoginSession(responseLogin);
            this.responseLogin = responseLogin;
            onSetProfilePicture(responseLogin.getData().getProfilePic());
            textviewTitle.setText(responseLogin.getData().getFullName());


            email_tv.setText(responseLogin.getData().getEmail());
            name_tv.setText(responseLogin.getData().getFirstName());

            if (responseLogin.getData().getIsUsernameUpdateded().equals("Yes")) {
                userName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                userName.setEnabled(false);
            }else {
                userName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);
                userName.setEnabled(true);

            }
            userName.setCompoundDrawablePadding(10);


            if (responseLogin.getData().getCashbonusMessage().equalsIgnoreCase("")) {
                cashBonusText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                cashBonusText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_small_info, 0);


            }


            if (responseLogin.getData().getUsername().length() == 0) {
                userName.setText(responseLogin.getData().getUsername());

            } else {

                userName.setText(responseLogin.getData().getUsername());
            }


            unutilizedAmmount.setText(AppUtils.getStrFromRes(R.string.rs) + responseLogin.getData().getWalletAmount());
            winningsAmount.setText(AppUtils.getStrFromRes(R.string.rs) + responseLogin.getData().getWinningAmount());
            cashBonusAmount.setText(AppUtils.getStrFromRes(R.string.rs) + responseLogin.getData().getCashBonus());


            contestCount.setText("" + responseLogin.getData().getPlayingHistory().getTotalJoinedContest());
            matchesCount.setText("" + responseLogin.getData().getPlayingHistory().getTotalJoinedMatches());
            seriesCount.setText("" + responseLogin.getData().getPlayingHistory().getTotalJoinedSeries());
            winesCount.setText("" + responseLogin.getData().getPlayingHistory().getTotalJoinedContestWinning());

        }

    }


    @Override
    public void showLoading() {
        if (isAdded() && getActivity() != null) {
            loader.start();
        }
    }

    @Override
    public void onShowSnackBar(String message) {
        AppUtils.showToast(getActivity(), message);
    }

    @Override
    public void onUploadPictureFailure(String errMsg) {
        AppUtils.showToast(getActivity(), errMsg);
    }

    @Override
    public void onProfileFailure(String error) {
        if (isAdded() && getActivity() != null) {
            loader.error(error);
        }
    }


    public LoginResponseOut getResponseLogin() {
        return responseLogin;
    }


    @Override
    public void onSetProfilePicture(@NonNull String value) {
        if (isAdded() && getActivity() != null) {
            if (!TextUtils.isEmpty(value))
                ViewUtils.setImageUrl(avatar, value);
            else {
                TextDrawable drawable2 = TextDrawable.builder().beginConfig()
                        .fontSize(45)   // size in px
                        .bold()
                        .toUpperCase()
                        .endConfig()
                        .buildRound(AppUtils.getNameCharacters(responseLogin.getData().getFullName()), AppUtils.getRandomColor());
                if (drawable2 != null) avatar.setImageDrawable(drawable2);
            }
        }
    }

    private ProgressDialog mProgressDialog;

    @Override
    public void onShowLoading() {
//        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public void onHideLoading() {
        if (mProgressDialog != null) mProgressDialog.dismiss();

    }

    @Override
    public void onUploadPictureSuccess(LoginResponseOut responseLogin, String filePath) {

    }

    public void showSnackBar(String message) {
//        AppUtils.showSnackBar(mContext, parentLayout, message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 112) {
            callViewProfile();
        }
        if (requestCode == 111) {
            if (data != null) {
                if (data.hasExtra("updateProfile")) {
                    callViewProfile();
                }
            }

        }
        if (requestCode == REQUEST_CODE_AVATAR && resultCode == getActivity().RESULT_OK && data != null) {


            AvatarListOutput.DataBean.RecordsBean selectedIcon = new AvatarListOutput.DataBean.RecordsBean();
            if (data.hasExtra("selectedIcon")) {

                selectedIcon = (AvatarListOutput.DataBean.RecordsBean) data.getSerializableExtra("selectedIcon");
                Log.d("selectedIcon", AppUtils.gsonToJSON(selectedIcon));

//                avatar.setImageURI(selectedIcon.getAvatarURL());


                Log.d("selectedIcon", selectedIcon.getAvatarURL());


            }
        }
        if (requestCode == REQUEST_TEAM_NAME && resultCode == getActivity().RESULT_OK && data != null) {


            if (data.hasExtra("changeName")) {

//                userName.setText(data.getStringExtra("changeName"));

                Log.d("changeName", data.getStringExtra("changeName"));


            }

        }

        if (requestCode == RQ_OPEN_GALLERY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                apiCallUploadPick(AppSession.getInstance().getLoginSession().getData().getSessionKey(), "Profile Pic", "ProfilePic", picturePath);
            } else {
                imageCaptureType = 0;
            }
        }

    }

    private void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();

//        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);

//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                    }
//                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((HomeNavigation) getActivity()).changeNavigationSelction(2);
    }


    private void apiCallUploadPick(final String sessionKey, final String mediaCaption, final String section, final String filePath) {
        progressDialog.show();
        if (NetworkUtils.isConnected(getContext())) {
            UploadImageInput uploadImageInput = new UploadImageInput();
            uploadImageInput.setSessionKey(sessionKey);
            uploadImageInput.setFilePath(filePath);
            uploadImageInput.setMediaCaption(mediaCaption);
            uploadImageInput.setSection(section);
            new UserInteractor().uploadImage(uploadImageInput, new IUserInteractor.OnResponseListener() {
                @Override
                public void onSuccess(LoginResponseOut user) {
                    progressDialog.dismiss();
                    callViewProfile();
                }

                @Override
                public void onError(String errorMsg) {
                    progressDialog.dismiss();
                    alertDialog = new AlertDialog(getContext(),
                            errorMsg,
                            "Retry",
                            "Cancel",
                            new AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    alertDialog.hide();
                                    apiCallUploadPick(sessionKey, mediaCaption, section, filePath);
                                }

                                @Override
                                public void onNoClick() {
                                    alertDialog.hide();
                                }
                            });
                    alertDialog.show();
                }
            });
        } else {
            progressDialog.dismiss();
            alertDialog = new AlertDialog(getContext(),
                    getString(R.string.network_error),
                    "Retry",
                    "Cancel",
                    new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            alertDialog.hide();
                            apiCallUploadPick(sessionKey, mediaCaption, section, filePath);
                        }

                        @Override
                        public void onNoClick() {
                            alertDialog.hide();
                        }
                    });
            alertDialog.show();
        }
    }


}

