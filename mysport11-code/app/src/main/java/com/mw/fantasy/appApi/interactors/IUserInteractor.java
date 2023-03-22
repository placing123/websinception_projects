package com.mw.fantasy.appApi.interactors;

import com.mw.fantasy.beanInput.AddPlayerInAssistantInput;
import com.mw.fantasy.beanInput.AssistSwitchInput;
import com.mw.fantasy.beanInput.BuySubscriptionInput;
import com.mw.fantasy.beanInput.ChangePasswordInput;
import com.mw.fantasy.beanInput.CheckPhonePeTransactionStatusInput;
import com.mw.fantasy.beanInput.CheckUserDraftInLiveInput;
import com.mw.fantasy.beanInput.ContestDetailInput;
import com.mw.fantasy.beanInput.ContestUserInput;
import com.mw.fantasy.beanInput.CreateContestInput;
import com.mw.fantasy.beanInput.CreateTeamInput;
import com.mw.fantasy.beanInput.CreateprivateSnakeDraftInput;
import com.mw.fantasy.beanInput.DownloadTeamInput;
import com.mw.fantasy.beanInput.DreamTeamInput;
import com.mw.fantasy.beanInput.EditPlayerInAssistantInput;
import com.mw.fantasy.beanInput.FavoriteTeamInput;
import com.mw.fantasy.beanInput.GetAuctionInfoInput;
import com.mw.fantasy.beanInput.GetAuctionJoinedUserInput;
import com.mw.fantasy.beanInput.GetAuctionPlayerInput;
import com.mw.fantasy.beanInput.GetAuctionSeriesInput;
import com.mw.fantasy.beanInput.GetAuctionSeriesOutput;
import com.mw.fantasy.beanInput.GetContestBidHistoryInput;
import com.mw.fantasy.beanInput.GetDraftPlayerPointInput;
import com.mw.fantasy.beanInput.GetDraftTeamsInput;
import com.mw.fantasy.beanInput.GetPrivateContestInput;
import com.mw.fantasy.beanInput.GetSeriesAuctionContestByTypeInput;
import com.mw.fantasy.beanInput.GetSeriesAuctionContestInput;
import com.mw.fantasy.beanInput.JoinAuctionInput;
import com.mw.fantasy.beanInput.JoinContestInput;
import com.mw.fantasy.beanInput.JoinedContestInput;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanInput.MakeFavoriteTeamInput;
import com.mw.fantasy.beanInput.MatchContestInput;
import com.mw.fantasy.beanInput.MatchDetailInput;
import com.mw.fantasy.beanInput.MatchListInput;
import com.mw.fantasy.beanInput.MyContestMatchesInput;
import com.mw.fantasy.beanInput.MyTeamInput;
import com.mw.fantasy.beanInput.NotificationDeleteInput;
import com.mw.fantasy.beanInput.NotificationInput;
import com.mw.fantasy.beanInput.NotificationMarkReadInput;
import com.mw.fantasy.beanInput.PaytmInput;
import com.mw.fantasy.beanInput.PlayerFantasyStatsInput;
import com.mw.fantasy.beanInput.PlayersInput;
import com.mw.fantasy.beanInput.PointsSystem;
import com.mw.fantasy.beanInput.PromoCodeInput;
import com.mw.fantasy.beanInput.PromoCodeListInput;
import com.mw.fantasy.beanInput.RankingInput;
import com.mw.fantasy.beanInput.ReferEarnInput;
import com.mw.fantasy.beanInput.RequestOtpForSigninInput;
import com.mw.fantasy.beanInput.SeriesInput;
import com.mw.fantasy.beanInput.SingupInput;
import com.mw.fantasy.beanInput.SubmitAuctionsPlayersInput;
import com.mw.fantasy.beanInput.SwitchTeamInput;
import com.mw.fantasy.beanInput.TransactionInput;
import com.mw.fantasy.beanInput.UpdateProfileInput;
import com.mw.fantasy.beanInput.UploadImageInput;
import com.mw.fantasy.beanInput.VerifyMobileInput;
import com.mw.fantasy.beanInput.VersionInput;
import com.mw.fantasy.beanInput.WalletInput;
import com.mw.fantasy.beanInput.WinnerBreakupInput;
import com.mw.fantasy.beanInput.WithdrawInput;
import com.mw.fantasy.beanOutput.AddPlayerInAssistantOutput;
import com.mw.fantasy.beanOutput.AllContestOutPut;
import com.mw.fantasy.beanOutput.AssistSwichOutPut;
import com.mw.fantasy.beanOutput.AuctionContestCreateOutput;
import com.mw.fantasy.beanOutput.AvatarListOutput;
import com.mw.fantasy.beanOutput.CheckContestBean;
import com.mw.fantasy.beanOutput.CheckUserDraftInLiveOutput;
import com.mw.fantasy.beanOutput.ContestDetailOutput;
import com.mw.fantasy.beanOutput.ContestUserOutput;
import com.mw.fantasy.beanOutput.CreateContestOutput;
import com.mw.fantasy.beanOutput.CreateprivateSnakeDraftOutput;
import com.mw.fantasy.beanOutput.DefaultRespose;
import com.mw.fantasy.beanOutput.DreamTeamOutput;
import com.mw.fantasy.beanOutput.ForgetPasswordOut;
import com.mw.fantasy.beanOutput.GetAuctionInfoOutput;
import com.mw.fantasy.beanOutput.GetAuctionJoinedUserOutput;
import com.mw.fantasy.beanOutput.GetAuctionPlayerOutput;
import com.mw.fantasy.beanOutput.GetContestBidHistoryOutput;
import com.mw.fantasy.beanOutput.GetCurrentLiveAuctionPlayerOutput;
import com.mw.fantasy.beanOutput.GetDraftPlayerPointOutput;
import com.mw.fantasy.beanOutput.GetDraftTeamsOutput;
import com.mw.fantasy.beanOutput.GetPlayerRoundOutput;
import com.mw.fantasy.beanOutput.GetPrivateContestOutput;
import com.mw.fantasy.beanOutput.GetSeriesAuctionContestByTypeOutput;
import com.mw.fantasy.beanOutput.GetSeriesAuctionContestOutput;
import com.mw.fantasy.beanOutput.GetSettingsOutput;
import com.mw.fantasy.beanOutput.GetSubscriptionResponse;
import com.mw.fantasy.beanOutput.JoinAuctionOutput;
import com.mw.fantasy.beanOutput.JoinContestOutput;
import com.mw.fantasy.beanOutput.JoinedContestBean;
import com.mw.fantasy.beanOutput.JoinedContestOutput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.MatchContestOutPut;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.beanOutput.MatchResponseOut;
import com.mw.fantasy.beanOutput.MyContestMatchesOutput;
import com.mw.fantasy.beanOutput.MyTeamOutput;
import com.mw.fantasy.beanOutput.NotificationsResponse;
import com.mw.fantasy.beanOutput.PlayersOutput;
import com.mw.fantasy.beanOutput.PointsList;
import com.mw.fantasy.beanOutput.PopupData;
import com.mw.fantasy.beanOutput.PromoCodeListOutput;
import com.mw.fantasy.beanOutput.PromoCodeResponse;
import com.mw.fantasy.beanOutput.RankingOutput;
import com.mw.fantasy.beanOutput.ReferralUsersResponse;
import com.mw.fantasy.beanOutput.RequestOtpForSigninOutput;
import com.mw.fantasy.beanOutput.ResponceSignup;
import com.mw.fantasy.beanOutput.ResponseBanner;
import com.mw.fantasy.beanOutput.ResponseCountries;
import com.mw.fantasy.beanOutput.ResponseDownloadTeam;
import com.mw.fantasy.beanOutput.ResponseFavoriteTeam;
import com.mw.fantasy.beanOutput.ResponseLogin;
import com.mw.fantasy.beanOutput.ResponsePayTmDetails;
import com.mw.fantasy.beanOutput.ResponsePayUMoneyDetails;
import com.mw.fantasy.beanOutput.ResponsePlayerDetails;
import com.mw.fantasy.beanOutput.ResponsePlayerFantasyStats;
import com.mw.fantasy.beanOutput.ResponseReferralDetails;
import com.mw.fantasy.beanOutput.ResponseUpdateProfile;
import com.mw.fantasy.beanOutput.SeriesOutput;
import com.mw.fantasy.beanOutput.SimpleOutput;
import com.mw.fantasy.beanOutput.SingleTeamOutput;
import com.mw.fantasy.beanOutput.SpinWheelOutput;
import com.mw.fantasy.beanOutput.SinglePlayerData;
import com.mw.fantasy.beanOutput.SubjectOutput;
import com.mw.fantasy.beanOutput.TransactionsBean;
import com.mw.fantasy.beanOutput.UpdateSpinWheelOutput;
import com.mw.fantasy.beanOutput.VersonBean;
import com.mw.fantasy.beanOutput.WalletOutputBean;
import com.mw.fantasy.beanOutput.WinBreakupOutPut;
import com.mw.fantasy.beanOutput.WithDrawoutput;

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
