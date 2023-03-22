package com.websinception.megastar.appApi.interactors;

import com.websinception.megastar.beanInput.AddPlayerInAssistantInput;
import com.websinception.megastar.beanInput.AssistSwitchInput;
import com.websinception.megastar.beanInput.BuySubscriptionInput;
import com.websinception.megastar.beanInput.ChangePasswordInput;
import com.websinception.megastar.beanInput.CheckPhonePeTransactionStatusInput;
import com.websinception.megastar.beanInput.CheckUserDraftInLiveInput;
import com.websinception.megastar.beanInput.ContestDetailInput;
import com.websinception.megastar.beanInput.ContestUserInput;
import com.websinception.megastar.beanInput.CreateContestInput;
import com.websinception.megastar.beanInput.CreateTeamInput;
import com.websinception.megastar.beanInput.CreateprivateSnakeDraftInput;
import com.websinception.megastar.beanInput.DownloadTeamInput;
import com.websinception.megastar.beanInput.DreamTeamInput;
import com.websinception.megastar.beanInput.EditPlayerInAssistantInput;
import com.websinception.megastar.beanInput.FavoriteTeamInput;
import com.websinception.megastar.beanInput.GetAuctionInfoInput;
import com.websinception.megastar.beanInput.GetAuctionJoinedUserInput;
import com.websinception.megastar.beanInput.GetAuctionPlayerInput;
import com.websinception.megastar.beanInput.GetAuctionSeriesInput;
import com.websinception.megastar.beanInput.GetAuctionSeriesOutput;
import com.websinception.megastar.beanInput.GetContestBidHistoryInput;
import com.websinception.megastar.beanInput.GetDraftPlayerPointInput;
import com.websinception.megastar.beanInput.GetDraftTeamsInput;
import com.websinception.megastar.beanInput.GetPrivateContestInput;
import com.websinception.megastar.beanInput.GetSeriesAuctionContestByTypeInput;
import com.websinception.megastar.beanInput.GetSeriesAuctionContestInput;
import com.websinception.megastar.beanInput.JoinAuctionInput;
import com.websinception.megastar.beanInput.JoinContestInput;
import com.websinception.megastar.beanInput.JoinedContestInput;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.MakeFavoriteTeamInput;
import com.websinception.megastar.beanInput.MatchContestInput;
import com.websinception.megastar.beanInput.MatchDetailInput;
import com.websinception.megastar.beanInput.MatchListInput;
import com.websinception.megastar.beanInput.MyContestMatchesInput;
import com.websinception.megastar.beanInput.MyTeamInput;
import com.websinception.megastar.beanInput.NotificationDeleteInput;
import com.websinception.megastar.beanInput.NotificationInput;
import com.websinception.megastar.beanInput.NotificationMarkReadInput;
import com.websinception.megastar.beanInput.PaytmInput;
import com.websinception.megastar.beanInput.PlayerFantasyStatsInput;
import com.websinception.megastar.beanInput.PlayersInput;
import com.websinception.megastar.beanInput.PointsSystem;
import com.websinception.megastar.beanInput.PromoCodeInput;
import com.websinception.megastar.beanInput.PromoCodeListInput;
import com.websinception.megastar.beanInput.RankingInput;
import com.websinception.megastar.beanInput.ReferEarnInput;
import com.websinception.megastar.beanInput.RequestOtpForSigninInput;
import com.websinception.megastar.beanInput.SeriesInput;
import com.websinception.megastar.beanInput.SingupInput;
import com.websinception.megastar.beanInput.SubmitAuctionsPlayersInput;
import com.websinception.megastar.beanInput.SwitchTeamInput;
import com.websinception.megastar.beanInput.TransactionInput;
import com.websinception.megastar.beanInput.UpdateProfileInput;
import com.websinception.megastar.beanInput.UploadImageInput;
import com.websinception.megastar.beanInput.VerifyMobileInput;
import com.websinception.megastar.beanInput.VersionInput;
import com.websinception.megastar.beanInput.WalletInput;
import com.websinception.megastar.beanInput.WinnerBreakupInput;
import com.websinception.megastar.beanInput.WithdrawInput;
import com.websinception.megastar.beanOutput.AddPlayerInAssistantOutput;
import com.websinception.megastar.beanOutput.AllContestOutPut;
import com.websinception.megastar.beanOutput.AssistSwichOutPut;
import com.websinception.megastar.beanOutput.AuctionContestCreateOutput;
import com.websinception.megastar.beanOutput.AvatarListOutput;
import com.websinception.megastar.beanOutput.CheckContestBean;
import com.websinception.megastar.beanOutput.CheckUserDraftInLiveOutput;
import com.websinception.megastar.beanOutput.ContestDetailOutput;
import com.websinception.megastar.beanOutput.ContestUserOutput;
import com.websinception.megastar.beanOutput.CreateContestOutput;
import com.websinception.megastar.beanOutput.CreateprivateSnakeDraftOutput;
import com.websinception.megastar.beanOutput.DefaultRespose;
import com.websinception.megastar.beanOutput.DreamTeamOutput;
import com.websinception.megastar.beanOutput.ForgetPasswordOut;
import com.websinception.megastar.beanOutput.GetAuctionInfoOutput;
import com.websinception.megastar.beanOutput.GetAuctionJoinedUserOutput;
import com.websinception.megastar.beanOutput.GetAuctionPlayerOutput;
import com.websinception.megastar.beanOutput.GetContestBidHistoryOutput;
import com.websinception.megastar.beanOutput.GetCurrentLiveAuctionPlayerOutput;
import com.websinception.megastar.beanOutput.GetDraftPlayerPointOutput;
import com.websinception.megastar.beanOutput.GetDraftTeamsOutput;
import com.websinception.megastar.beanOutput.GetPlayerRoundOutput;
import com.websinception.megastar.beanOutput.GetPrivateContestOutput;
import com.websinception.megastar.beanOutput.GetSeriesAuctionContestByTypeOutput;
import com.websinception.megastar.beanOutput.GetSeriesAuctionContestOutput;
import com.websinception.megastar.beanOutput.GetSettingsOutput;
import com.websinception.megastar.beanOutput.GetSubscriptionResponse;
import com.websinception.megastar.beanOutput.JoinAuctionOutput;
import com.websinception.megastar.beanOutput.JoinContestOutput;
import com.websinception.megastar.beanOutput.JoinedContestBean;
import com.websinception.megastar.beanOutput.JoinedContestOutput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.MatchContestOutPut;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;
import com.websinception.megastar.beanOutput.MatchResponseOut;
import com.websinception.megastar.beanOutput.MyContestMatchesOutput;
import com.websinception.megastar.beanOutput.MyTeamOutput;
import com.websinception.megastar.beanOutput.NotificationsResponse;
import com.websinception.megastar.beanOutput.PlayersOutput;
import com.websinception.megastar.beanOutput.PointsList;
import com.websinception.megastar.beanOutput.PopupData;
import com.websinception.megastar.beanOutput.PromoCodeListOutput;
import com.websinception.megastar.beanOutput.PromoCodeResponse;
import com.websinception.megastar.beanOutput.RankingOutput;
import com.websinception.megastar.beanOutput.ReferralUsersResponse;
import com.websinception.megastar.beanOutput.RequestOtpForSigninOutput;
import com.websinception.megastar.beanOutput.ResponceSignup;
import com.websinception.megastar.beanOutput.ResponseBanner;
import com.websinception.megastar.beanOutput.ResponseCountries;
import com.websinception.megastar.beanOutput.ResponseDownloadTeam;
import com.websinception.megastar.beanOutput.ResponseFavoriteTeam;
import com.websinception.megastar.beanOutput.ResponseLogin;
import com.websinception.megastar.beanOutput.ResponsePayTmDetails;
import com.websinception.megastar.beanOutput.ResponsePayUMoneyDetails;
import com.websinception.megastar.beanOutput.ResponsePlayerDetails;
import com.websinception.megastar.beanOutput.ResponsePlayerFantasyStats;
import com.websinception.megastar.beanOutput.ResponseReferralDetails;
import com.websinception.megastar.beanOutput.ResponseUpdateProfile;
import com.websinception.megastar.beanOutput.SeriesOutput;
import com.websinception.megastar.beanOutput.SimpleOutput;
import com.websinception.megastar.beanOutput.SingleTeamOutput;
import com.websinception.megastar.beanOutput.SpinWheelOutput;
import com.websinception.megastar.beanOutput.SinglePlayerData;
import com.websinception.megastar.beanOutput.SubjectOutput;
import com.websinception.megastar.beanOutput.TransactionsBean;
import com.websinception.megastar.beanOutput.UpdateSpinWheelOutput;
import com.websinception.megastar.beanOutput.VersonBean;
import com.websinception.megastar.beanOutput.WalletOutputBean;
import com.websinception.megastar.beanOutput.WinBreakupOutPut;
import com.websinception.megastar.beanOutput.WithDrawoutput;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;


public interface IUserInteractor {

    //  SignUp API
    Call<ResponceSignup> signUp(SingupInput mSingupInput, OnSignUpResponseListener onResponseListener);

    //signin api
    Call<LoginResponseOut> login(LoginInput mLoginInput, OnLoginResponseListener onResponseListener);

    Call<LoginResponseOut> verifyEmailAddress(VerifyMobileInput mobileInput, OnResponseListener onResponseListener);

    Call<DefaultRespose> deleteNotification(NotificationDeleteInput mDeleteInput, OnMakeFavoriteTeamListener onResponseListener);

    Call<ResponseFavoriteTeam> getFavoriteTeam(FavoriteTeamInput mFavoriteTeamInput, OnGetFavoriteTeamListener mOnGetFavoriteTeamListener);

    Call<DefaultRespose> makeFavoriteTeams(MakeFavoriteTeamInput mMakeFavoriteTeamInput, OnMakeFavoriteTeamListener mOnMakeFavoriteTeamListener);

    Call<ResponseDownloadTeam> downloadTeam(DownloadTeamInput mDownloadTeamInput, OnDownloadTeamListener mOnDownloadTeamListener);

    Call<ResponseDownloadTeam> auctiondownloadTeam(DownloadTeamInput mDownloadTeamInput, OnDownloadTeamListener mOnDownloadTeamListener);

    Call<LoginResponseOut> viewProfile(LoginInput mLoginInput, OnResponseListener onResponseListener);

    Call<ForgetPasswordOut> forgotPassword(LoginInput mLoginInput, OnForgetPasswordListener onResponseListener);

    Call<ForgetPasswordOut> resetPassword(LoginInput mLoginInput, OnForgetPasswordListener onResponseListener);

    Call<LoginResponseOut> verifyEmail(LoginInput mLoginInput, OnLoginResponseListener onResponseListener);

    Call<MatchResponseOut> matchesListing(MatchListInput mMatchListInput, OnResponseMatchesListener onResponseListener);

    Call<JoinedContestOutput> getJoinedContests(JoinedContestInput mJoinedContestInput, OnResponseMyMatchesListener onResponseMyMatchesListener);

    Call<MatchDetailOutPut> matchDetail(MatchDetailInput matchDetailInput, OnResponseMatchDetailsListener onResponseListener);

    Call<WalletOutputBean> viewAccount(WalletInput mWalletInput, OnResponseAccountListener onResponseListener);

    Call<DefaultRespose> updateProfile(UpdateProfileInput mUpdateProfileInput,
                                       OnResponseUpdateProfileListener mOnResponseUpdateProfileListener);

    Call<ResponseBanner> bannersList(LoginInput mLoginInput, OnResponseBannerListener onResponseListener);

    Call<MatchContestOutPut> getContestsByType(MatchContestInput mMatchContestInput, OnResponseContestListener onResponseListener);

    Call<MatchContestOutPut> getJoinedContestsByType(JoinedContestInput mMatchContestInput, OnResponseContestListener onResponseListener);

    Call<AllContestOutPut> allContestListing(MatchContestInput mMatchContestInput, OnResponseAllContestsListener onResponseListener);

    Call<MyContestMatchesOutput> myContestMatchesList(MyContestMatchesInput myContestMatchesInput, OnResponseMyContestListener onResponseMyContestListener);


    Call<ContestDetailOutput> getContest(ContestDetailInput mMatchContestInput, OnResponseContestDetailsListener onResponseListener);

    Call<ContestUserOutput> getJoinedContestsUsers(ContestUserInput mContestUserInput, OnResponseRanksListener onResponseRanksListener);

    Call<PlayersOutput> getPlayers(PlayersInput mPlayersInput, OnResponseMatchPlayersListener onResponseListener);

    Call<LoginResponseOut> addUserTeam(CreateTeamInput mCreateTeamInput, OnResponseListener onResponseListener);

    Call<LoginResponseOut> editUserTeam(CreateTeamInput mCreateTeamInput, OnResponseListener onResponseListener);

    Call<MyTeamOutput> teamList(MyTeamInput mMyTeamInput, OnResponseTeamsListener onResponseListener);


    Call<JoinContestOutput> joinContest(JoinContestInput mJoinContestInput, OnResponseJoinListener onResponseListener);

    Call<LoginResponseOut> sendMobileOtp(VerifyMobileInput mobileInput, OnResponseListener onResponseListener);

    Call<LoginResponseOut> verifyPhoneNumber(VerifyMobileInput mobileInput, OnResponseListener onResponseListener);

    Call<LoginResponseOut> resendverify(VerifyMobileInput mobileInput, OnResponseListener onResponseListener);

    Call<LoginResponseOut> uploadImage(UploadImageInput imageUploadBean, OnResponseListener onResponseListener);

    Call<DreamTeamOutput> allPlayersScore(DreamTeamInput dreamTeamInput, OnResponseDreamTeamsListener onResponseListener);

    Call<DreamTeamOutput> auctionallPlayersScore(DreamTeamInput dreamTeamInput, OnResponseDreamTeamsListener onResponseListener);

    Call<CreateContestOutput> createContest(CreateContestInput createContestInput, OnResponseCreateContestListener onResponseListener);

    Call<AuctionContestCreateOutput> auctioncreateContest(CreateContestInput createContestInput, OnResponseAuctionCreateContestListener onResponseListener);

    Call<AuctionContestCreateOutput> createPrivateSnakeContest(CreateContestInput createContestInput, OnResponseAuctionCreateContestListener onResponseListener);

    Call<GetPlayerRoundOutput> getPlayersRound(CreateContestInput createContestInput, OnResponseDraftCreateContestListener onResponseListener);


    Call<LoginResponseOut> checkPhonePeTransactionStatus(CheckPhonePeTransactionStatusInput checkPhonePeTransactionStatusInput,
                                                             checkPhonePeTransactionStatusResponseListener checkPhonePeTransactionStatusResponseListener);



    Call<ResponsePlayerFantasyStats> playerFantasyStats(PlayerFantasyStatsInput mPlayerFantasyStatsInput, OnPlayerFantasyStatsListener onPlayerFantasyStatsListener);


    Call<LoginResponseOut> changePassword(ChangePasswordInput mChangePasswordInput, OnResponseListener onResponseListener);

    Call<AvatarListOutput> users_icon_list(LoginInput loginInput, OnAvatarResponseListener onResponseListener);

    Call<RankingOutput> getRankings(RankingInput rankingInput, OnRankingListener mResponseOverallLeaderboardListener);

    Call<LoginResponseOut> switchTeam(SwitchTeamInput switchTeamInput, OnResponseListener onResponseListener);

    Call<ResponsePayTmDetails> payTm(PaytmInput paytmInput, OnPayTmResponseListener onResponseListener);

    Call<LoginResponseOut> payTmResponse(PaytmInput paytmInput, OnResponseListener onResponseListener);

    Call<LoginResponseOut> notificationCount(LoginInput mLoginInput, OnResponseListener onResponseListener);

    Call<DefaultRespose> notificationMarkRead(NotificationMarkReadInput mNotificationMarkReadInput, OnMakeFavoriteTeamListener onResponseListener);

    Call<VersonBean> appVersion(VersionInput versionInput, OnVersonResponseListener onResponseListener);

    Call<TransactionsBean> transactionsApp(TransactionInput transactionInput, OnResponseTransactionListener onResponseListener);

    Call<WinBreakupOutPut> winning_breakup(WinnerBreakupInput mWinnerBreakupInput, OnResponseWinBreakUpListener onResponseListener);

    Call<SeriesOutput> matchSeriesCall(SeriesInput seriesInput, OnResponseMatchSeriesListener mOnResponseMatchSeriesListener);

    Call<NotificationsResponse> notificationList(NotificationInput notificationInput, OnNotificationResponseListener onResponseListener);

    Call<WithDrawoutput> withdrawal_add(WithdrawInput notificationInput, OnwithdrawalResponseListener onResponseListener);

    Call<ResponseReferralDetails> getReferralDetail(OnReferralDetailListener mOnReferralDetailListener);


    Call<PromoCodeResponse> promoCode(PromoCodeInput mPromoCodeInput, OnPromoCodeResponseListener promoCodeResponseListener);


    Call<RequestOtpForSigninOutput> requestOtpForSigninCall(RequestOtpForSigninInput requestOtpForSigninInput, OnRequestOtpForSigninListener onRequestOtpForSigninListener);


    Call<PointsList> getPointList(String type, PointsSystem system, OnPointsResponseListener mOnReferralDetailListener);

    Call<PromoCodeListOutput> promocodeList(PromoCodeListInput mPromoCodeListInput, OnRequestPromoCodeListListener onRequestPromoCodeListListener);

    Call<DefaultRespose> getReferEarn(ReferEarnInput mReferEarnInput, OnMakeFavoriteTeamListener mOnMakeFavoriteTeamListener);

    Call<ReferralUsersResponse> getReferralUsers(LoginInput mLoginInput, OnReferralUsersListener mOnReferralUsersListener);

    Call<SingleTeamOutput> getSingleUserTeams(MyTeamInput mMyTeamInput, OnResponseSingleTeamsListener onResponseListener);

    ////////////898


    Call<GetAuctionSeriesOutput> getAuctionSeries(GetAuctionSeriesInput getAuctionSeriesInput,
                                                  OnGetAuctionSeriesResponseListener onGetAuctionSeriesResponseListener);


    Call<GetAuctionSeriesOutput> getSnackDraftSeries(GetAuctionSeriesInput getAuctionSeriesInput,

                                                     OnGetAuctionSeriesResponseListener onGetAuctionSeriesResponseListener);

    Call<GetAuctionPlayerOutput> getAuctionPlayers(GetAuctionPlayerInput getAuctionPlayerInput,
                                                   OnGetAuctionPlayersResponseListener onGetAuctionPlayersResponseListener);


    Call<GetAuctionPlayerOutput> getDraftPlayers(GetAuctionPlayerInput getAuctionPlayerInput,
                                                 OnGetAuctionPlayersResponseListener onGetAuctionPlayersResponseListener);


    Call<GetAuctionJoinedUserOutput> getAuctionJoinedUsers(GetAuctionJoinedUserInput getAuctionJoinedUserInput,
                                                           OnGetAuctionJoinedUserResponseListener onGetAuctionJoinedUserResponseListener);


    Call<GetAuctionJoinedUserOutput> getDraftJoinedUsers(GetAuctionJoinedUserInput getAuctionJoinedUserInput,
                                                         OnGetAuctionJoinedUserResponseListener onGetAuctionJoinedUserResponseListener);


    Call<AssistSwichOutPut> auctionAssistantTeamOnOff(AssistSwitchInput assistSwitchInput,
                                                      OnAuctionAssistantTeamOnOffResponseListener onAuctionAssistantTeamOnOffResponseListener);


    Call<AssistSwichOutPut> draftAssistantTeamOnOff(AssistSwitchInput assistSwitchInput,
                                                    OnAuctionAssistantTeamOnOffResponseListener onAuctionAssistantTeamOnOffResponseListener);


    Call<GetPrivateContestOutput> getPrivateContest(GetPrivateContestInput getPrivateContestInput,
                                                    OnGetPrivateContestListener onGetPrivateContestListener);


    Call<GetPrivateContestOutput> getAuctionPrivateContest(GetPrivateContestInput getPrivateContestInput,
                                                           OnGetAuctionPrivateContestListener onGetAuctionPrivateContestListener);


    Call<CreateprivateSnakeDraftOutput> createPrivateSnakeDraft(CreateprivateSnakeDraftInput createprivateSnakeDraftInput,
                                                                OnCreatePrivateSnakeDraftListener onCreatePrivateSnakeDraftListener);


    Call<AddPlayerInAssistantOutput> addPlayerInAssistant(AddPlayerInAssistantInput addPlayerInAssistantInput,
                                                          OnAddPlayerInAssistantResponseListener onAddPlayerInAssistantResponseListener);


    Call<SimpleOutput> editPlayerInAssistant(EditPlayerInAssistantInput editPlayerInAssistantInput,
                                             OnSimpleResponseListener onSimpleResponseListener);


    Call<AddPlayerInAssistantOutput> addPlayerInDraftAssistant(AddPlayerInAssistantInput addPlayerInAssistantInput,
                                                               OnAddPlayerInAssistantResponseListener onAddPlayerInAssistantResponseListener);


    Call<SimpleOutput> editPlayerInDraftAssistant(EditPlayerInAssistantInput editPlayerInAssistantInput,
                                                  OnSimpleResponseListener onSimpleResponseListener);


    Call<GetAuctionInfoOutput> getAuctionInfo(GetAuctionInfoInput getAuctionInfoInput,
                                              OnGetAuctionInfoResponseListener onGetAuctionInfoResponseListener);

    Call<GetAuctionInfoOutput> getAuctionMatchInfo(GetAuctionInfoInput getAuctionInfoInput,
                                                   OnGetAuctionInfoResponseListener onGetAuctionInfoResponseListener);


    Call<GetAuctionInfoOutput> getDraftInfo(GetAuctionInfoInput getAuctionInfoInput,
                                            OnGetAuctionInfoResponseListener onGetAuctionInfoResponseListener);


    Call<GetCurrentLiveAuctionPlayerOutput> getCurrentLiveAuctionPlayer(GetAuctionInfoInput getAuctionInfoInput,
                                                                        OnGetCurrentLiveAuctionPlayerResponseListener onGetCurrentLiveAuctionPlayerResponseListener);


    Call<GetSeriesAuctionContestOutput> getSeriesAuctionContest(GetSeriesAuctionContestInput getSeriesAuctionContestInput,
                                                                OnGetSeriesAuctionContestListener onGetSeriesAuctionContestListener);


    Call<GetSeriesAuctionContestByTypeOutput> getSeriesAuctionContestByType(GetSeriesAuctionContestByTypeInput getSeriesAuctionContestByTypeInput,
                                                                            OnGetSeriesAuctionContestByTypeListener onGetSeriesAuctionContestByTypeListener);


    Call<GetSeriesAuctionContestByTypeOutput> getSeriesDraftContestByType(GetSeriesAuctionContestByTypeInput getSeriesAuctionContestByTypeInput,
                                                                          OnGetSeriesAuctionContestByTypeListener onGetSeriesAuctionContestByTypeListener);


    Call<GetSeriesAuctionContestOutput> getSeriesDraftContest(GetSeriesAuctionContestInput getSeriesAuctionContestInput,
                                                              OnGetSeriesAuctionContestListener onGetSeriesAuctionContestListener);


    Call<JoinAuctionOutput> joinAuction(JoinAuctionInput joinAuctionInput,
                                        OnJoinAuctionListener onJoinAuctionListener);

    Call<JoinAuctionOutput> joinDraft(JoinAuctionInput joinAuctionInput,
                                      OnJoinAuctionListener onJoinAuctionListener);


    Call<GetContestBidHistoryOutput> getContestBidHistory(GetContestBidHistoryInput getContestBidHistoryInput,
                                                          OnGetContestBidHistoryListener onGetContestBidHistoryListener);


    Call<DefaultRespose> submitAuctionsPlayers(SubmitAuctionsPlayersInput submitAuctionsPlayersInput,
                                               OnSubmitAuctionsPlayersListener onSubmitAuctionsPlayersListener);


    Call<DefaultRespose> submitDraftPlayers(SubmitAuctionsPlayersInput submitAuctionsPlayersInput,
                                            OnSubmitAuctionsPlayersListener onSubmitAuctionsPlayersListener);


    Call<DefaultRespose> sendMessage(HashMap<String, String> submitAuctionsPlayersInput,
                                     OnSubmitAuctionsPlayersListener onSubmitAuctionsPlayersListener);


    Call<GetDraftTeamsOutput> getDraftTeams(GetDraftTeamsInput getDraftTeamsInput,
                                            OnGetDraftTeamsListener onGetDraftTeamsListener);

    Call<CheckUserDraftInLiveOutput> checkUserDraftInlive(CheckUserDraftInLiveInput checkUserDraftInLiveInput,
                                                          OnCheckUserDraftListener onCheckUserDraftListener);


    Call<GetDraftPlayerPointOutput> getDraftPlayersPoint(GetDraftPlayerPointInput getDraftPlayerPointInput,
                                                         OnGetDraftPlayersPointListener onGetDraftPlayersPointListener);


    Call<PlayersOutput> getAuctionPlayers(PlayersInput playersInput, OnGetAuctionPlayersPointListener onGetDraftPlayersPointListener);

    Call<SinglePlayerData> getSingleAuctionPlayers(PlayerFantasyStatsInput playersInput, OnGetSingleAuctionPlayersPointListener onGetDraftPlayersPointListener);

    Call<SpinWheelOutput> getSpinWheelData(LoginInput loginInput,
                                           OnResponseSpinWheeListener onGetDraftPlayersPointListener);

    Call<PopupData> getPopupData(LoginInput loginInput,
                                 OnResponsePopupListener onResponsePopupListener);

    Call<PopupData> updateData(LoginInput loginInput,
                               OnResponsePopupListener onResponsePopupListener);

    Call<UpdateSpinWheelOutput> updateWinningSpinWheelData(LoginInput loginInput,
                                                           OnResponseUpdateSpinWheeListener onGetDraftPlayersPointListener);

    Call<SubjectOutput> getSubject(OnResponseSubjectListener onResponseSubjectListener);


    Call<GetSubscriptionResponse> getSubscription(LoginInput loginInput,
                                                  GetSubscriptionResponseListener getSubscriptionResponseListener);


    Call<DefaultRespose> buySubscription(BuySubscriptionInput loginInput,
                                         BuySubscriptionResponseListener getSubscriptionResponseListener);


    Call<GetSettingsOutput> getSettings(GetSettingsResponseListener getSettingsResponseListener);


    interface GetSettingsResponseListener {

        void onSuccess(GetSettingsOutput.DataBean data);

        void onError(String errorMsg);


    }


    interface BuySubscriptionResponseListener {

        void onSuccess(String msg);

        void onError(String errorMsg);


    }


    interface GetSubscriptionResponseListener {

        void onSuccess(GetSubscriptionResponse.DataBean data);

        void onError(String errorMsg);

    }


    interface OnAuctionAssistantTeamOnOffResponseListener {

        void onSuccess();

        void onError(String errorMsg);

    }


    interface OnGetAuctionJoinedUserResponseListener {

        void onSuccess(GetAuctionJoinedUserOutput getAuctionJoinedUserOutput);

        void onError(String errorMsg);

    }


    interface OnCreatePrivateSnakeDraftListener {

        void onSuccess(CreateprivateSnakeDraftOutput createprivateSnakeDraftOutput);

        void onError(String errorMsg);

    }


    interface OnGetAuctionPrivateContestListener {

        void onSuccess(GetPrivateContestOutput getPrivateContestOutput);

        void onError(String errorMsg);

    }

    interface OnGetPrivateContestListener {

        void onSuccess(GetPrivateContestOutput getPrivateContestOutput);

        void onError(String errorMsg);

    }

    interface OnAddPlayerInAssistantResponseListener {

        void onSuccess(AddPlayerInAssistantOutput addPlayerInAssistantOutput);

        void onError(String errorMsg);

    }


    interface OnSimpleResponseListener {

        void onSuccess(SimpleOutput simpleOutput);

        void onError(String errorMsg);

    }


    interface OnGetAuctionInfoResponseListener {

        void onSuccess(GetAuctionInfoOutput getAuctionInfoOutput);

        void onError(String errorMsg);

    }

    interface OnGetCurrentLiveAuctionPlayerResponseListener {

        void onSuccess(GetCurrentLiveAuctionPlayerOutput getCurrentLiveAuctionPlayerOutput);

        void onError(String errorMsg);

    }


    interface OnGetSeriesAuctionContestByTypeListener {

        void onSuccess(GetSeriesAuctionContestByTypeOutput getSeriesAuctionContestByTypeOutput);

        void onError(String errorMsg);

    }

    interface OnGetSeriesAuctionContestListener {

        void onSuccess(GetSeriesAuctionContestOutput getSeriesAuctionContestOutput);

        void onError(String errorMsg);

    }


    interface OnJoinAuctionListener {

        void onSuccess(JoinAuctionOutput joinAuctionOutput);

        void onError(String errorMsg);

    }

    interface OnGetContestBidHistoryListener {

        void onSuccess(GetContestBidHistoryOutput getContestBidHistoryOutput);

        void onError(String errorMsg);

    }


    interface OnSubmitAuctionsPlayersListener {

        void onSuccess(DefaultRespose defaultRespose);

        void onError(String errorMsg);

    }

    interface OnGetAuctionPlayersResponseListener {

        void onSuccess(GetAuctionPlayerOutput getAuctionPlayerOutput);

        void onError(String errorMsg);

    }

    interface OnGetDraftPlayersPointListener {

        void onSuccess(GetDraftPlayerPointOutput getDraftPlayerPointOutput);

        void onError(String errorMsg);

    }

    interface OnGetDraftTeamsListener {

        void onSuccess(GetDraftTeamsOutput getDraftTeamsOutput);

        void onError(String errorMsg);

    }


    interface OnCheckUserDraftListener {

        void onSuccess(CheckUserDraftInLiveOutput checkUserDraftInLiveOutput);

        void onError(String errorMsg);

    }


    interface OnGetAuctionSeriesResponseListener {

        void onSuccess(GetAuctionSeriesOutput getAuctionSeriesOutput);

        void onError(String errorMsg);

    }


    interface OnResponseSingleTeamsListener {
        void onSuccess(SingleTeamOutput responseTeams);

        void onNotFound(String error);

        void onError(String errorMsg);
    }


    ////////////898


    interface OnRequestOtpForSigninListener {

        void onSuccess(RequestOtpForSigninOutput requestOtpForSigninOutput);

        void onError(String errorMsg);

    }

    interface OnRequestPromoCodeListListener {

        void onSuccess(PromoCodeListOutput mPromoCodeListOutput);

        void onError(String errorMsg);

    }


    interface OnPointsResponseListener {

        void onSuccess(PointsList mNotificationBean);


        void onError(String errorMsg);

        void onNotFound(String error);


    }


    interface OnPromoCodeResponseListener {

        void onSuccess(PromoCodeResponse mPromoCodeResponse);

        void onError(String errorMsg);

    }


    interface OnwithdrawalResponseListener {

        void onSuccess(WithDrawoutput mNotificationBean);


        void onError(String errorMsg);

        void onNotFound(String error);


    }

    interface OnNotificationResponseListener {

        void onSuccess(NotificationsResponse mNotificationBean);

        void onError(String errorMsg);

        void onNotFound(String error);


    }

    interface OnSignUpResponseListener {
        void onNotVerify(ResponceSignup user);

        void onSuccess(ResponceSignup user);

        void onError(String errorMsg);
    }

    interface OnLoginResponseListener {
        void onSuccess(LoginResponseOut user);//in case of response success (status 1)

        void onAccountNotVerify(LoginResponseOut user);//in case of account not verify (status 5)

        void onOTPRecevied(LoginResponseOut user);

        void onAccountAvailableForSignUp(String errorMsg);//in case of account available for sign up (status 6)

        void onError(String errorMsg);//in case of error (status 0)
    }

    interface OnForgetPasswordListener {
        void onSuccess(ForgetPasswordOut user);

        void onError(String errorMsg);
    }

    interface OnReferralDetailListener {
        void onSuccess(ResponseReferralDetails user);

        void onError(String errorMsg);
    }

    interface OnReferralUsersListener {
        void onSuccess(ReferralUsersResponse user);

        void onError(String errorMsg);
    }

    interface OnPlayerFantasyStatsListener {
        void onSuccess(ResponsePlayerFantasyStats user);

        void onError(String errorMsg);
    }


    interface OnDownloadTeamListener {
        void onSuccess(ResponseDownloadTeam user);

        void onError(String errorMsg);
    }

    interface OnGetFavoriteTeamListener {
        void onSuccess(ResponseFavoriteTeam user);

        void onError(String errorMsg);
    }

    interface OnMakeFavoriteTeamListener {
        void onSuccess(DefaultRespose user);

        void onError(String errorMsg);
    }

    interface OnResponseMatchesListener {
        void onSuccess(MatchResponseOut responseMatches);

        void onCheckContest(CheckContestBean mJoinedContestBean);

        void onNotFound(String error);

        void onError(String errorMsg);

        void OnSessionExpire();
    }

    interface OnResponseMyMatchesListener {
        void onSuccess(JoinedContestOutput responseMatches);

        void onNotFound(String error);

        void onError(String errorMsg);

        void OnSessionExpire();
    }

    interface OnResponseMatchDetailsListener {
        void onSuccess(MatchDetailOutPut user);

        void onError(String errorMsg);
    }

    interface OnResponseAccountListener {
        void onSuccess(WalletOutputBean user);

        void onError(String errorMsg);
    }

    interface OnResponseContestListener {

        void onSuccess(MatchContestOutPut mResponseContest);

        void onNotFound(String error);

        void onError(String errorMsg);

        void OnSessionExpire();
    }

    interface OnResponseContestDetailsListener {
        void onSuccess(ContestDetailOutput mContestDetailOutput);

        void onError(String errorMsg);
    }

    interface OnResponseRanksListener {
        void onSuccess(ContestUserOutput mContestUserOutput);

        void onNotFound(String error);

        void onError(String errorMsg);
    }

    interface OnResponseMatchPlayersListener {
        void onSuccess(PlayersOutput mPlayersOutput);

        void onNotFound(String error);

        void onError(String errorMsg);
    }

    interface OnResponseListener {
        void onSuccess(LoginResponseOut user);

        void onError(String errorMsg);
    }

    interface OnResponseTeamsListener {
        void onSuccess(MyTeamOutput responseTeams);

        void onNotFound(String error);

        void onError(String errorMsg);
    }


    interface OnResponseJoinListener {
        void onSuccess(JoinContestOutput mJoinContestOutput);

        void onNotFound(String error);

        void onError(String errorMsg);
    }

    interface OnResponseCreateContestListener {
        void onSuccess(CreateContestOutput mCreateContestOutput);

        void onError(String errorMsg);
    }

    interface OnResponseAuctionCreateContestListener {
        void onSuccess(AuctionContestCreateOutput mCreateContestOutput);

        void onError(String errorMsg);
    }


    interface OnResponseDraftCreateContestListener {
        void onSuccess(GetPlayerRoundOutput mCreateContestOutput);

        void onError(String errorMsg);
    }

    interface OnAvatarResponseListener {
        void onSuccess(AvatarListOutput user);//in case of response success (status 1)

        void onNotFound(String error);

        void onError(String errorMsg);//in case of error (status 0)
    }

    interface OnRankingListener {
        void onSuccess(RankingOutput mRankingOutput);

        void onError(String errorMsg);
    }

    interface checkPhonePeTransactionStatusResponseListener {
        void onSuccess(String status);
        void onError(String errorMsg);
    }


    interface OnPayTmResponseListener {
        void onSuccess(ResponsePayTmDetails user);

        void onError(String errorMsg);
    }


    interface OnResponseTransactionListener {
        void onSuccess(TransactionsBean responseMatches);

        void onNotFound(String error);

        void onError(String errorMsg);
    }

    interface OnResponseMatchSeriesListener {
        void onSuccess(SeriesOutput responseSeries);

        void onError(String errorMsg);
    }


    interface OnResponseWinBreakUpListener {
        void onSuccess(WinBreakupOutPut user);

        void onError(String errorMsg);
    }


    interface OnAvatarUpdateListener {
        void onSuccess(ResponseLogin user);

        void onFailed(String errorMsg);

        void onError(String errorMsg);
    }


    interface OnResponseDreamTeamsListener {
        void onSuccess(DreamTeamOutput responseTeams);

        void onNotFound(String error);

        void onError(String errorMsg);
    }

    interface OnVersonResponseListener {
        void onSuccess(VersonBean user);//in case of response success (status 1)


        void onFailed(String errorMsg);


        void onError(String errorMsg);//in case of error (status 0)
    }


    interface OnResponseBannerListener {
        void onSuccess(ResponseBanner responseBanner);

        void onNotFound(String error);

        void onError(String errorMsg);
    }

    interface OnResponseAllContestsListener {
        void onSuccess(AllContestOutPut mAllContestOutPut);

        void onNotFound(String error);

        void onError(String errorMsg);

        void OnSessionExpire();
    }

    interface OnResponseSpinWheeListener {
        void onSuccess(SpinWheelOutput spinWheelOutput);

        void onNotFound(String error);

        void onError(String errorMsg);

        void OnSessionExpire();
    }

    interface OnResponsePopupListener {
        void onSuccess(PopupData popupData);

        void onNotFound(String error);

        void onError(String errorMsg);

        void OnSessionExpire();
    }


    interface OnResponseUpdateSpinWheeListener {
        void onSuccess(UpdateSpinWheelOutput spinWheelOutput);

        void onNotFound(String error);

        void onError(String errorMsg);

        void OnSessionExpire();
    }


    interface OnResponseSubjectListener {
        void onSuccess(SubjectOutput spinWheelOutput);

        void onNotFound(String error);

        void onError(String errorMsg);

        void OnSessionExpire();
    }

    interface OnResponseMyContestListener {
        void onSuccess(MyContestMatchesOutput mAllContestOutPut);

        void onNotFound(String error);

        void onError(String errorMsg);

    }


    interface OnResponseJoinedContesListener {
        void onSuccess(JoinedContestBean responseMatches);

        void onNotFound(String error);

        void onError(String errorMsg);

        void OnSessionExpire();
    }


    interface OnCountryResponseListener {
        void onSuccess(ResponseCountries user);

        void onError(String errorMsg);
    }

    interface OnPayUMoneyResponseListener {
        void onSuccess(ResponsePayUMoneyDetails user);

        void onError(String errorMsg);
    }


    interface OnResponseUpdateProfileListener {
        void onSuccess(ResponseUpdateProfile responseUpdate);

        void onError(String errorMsg);
    }

    interface OnResponsePlayerDetailsListener {
        void onSuccess(ResponsePlayerDetails user);

        void onError(String errorMsg);
    }


    interface OnGetAuctionPlayersPointListener {
        void onSuccess(PlayersOutput playersOutput);

        void onError(String errorMsg);
    }

    interface OnGetSingleAuctionPlayersPointListener {
        void onSuccess(SinglePlayerData playersOutput);

        void onError(String errorMsg);
    }
}
