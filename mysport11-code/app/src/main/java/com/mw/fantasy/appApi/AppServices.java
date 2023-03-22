package com.mw.fantasy.appApi;


import android.support.annotation.NonNull;


import com.mw.fantasy.SubsDto;
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
import com.mw.fantasy.beanOutput.JoinedContestOutput;
import com.mw.fantasy.beanOutput.LoginResponseOut;
import com.mw.fantasy.beanOutput.MatchContestOutPut;
import com.mw.fantasy.beanOutput.MatchDetailOutPut;
import com.mw.fantasy.beanOutput.MatchResponseOut;
import com.mw.fantasy.beanOutput.MyContestMatchesOutput;
import com.mw.fantasy.beanOutput.MyTeamOutput;
import com.mw.fantasy.beanOutput.NotificationsResponse;
import com.mw.fantasy.beanOutput.PaytmWithdrawOutput;
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
import com.mw.fantasy.beanOutput.ResponseDownloadTeam;
import com.mw.fantasy.beanOutput.ResponseFavoriteTeam;
import com.mw.fantasy.beanOutput.ResponsePayTmDetails;
import com.mw.fantasy.beanOutput.ResponsePlayerFantasyStats;
import com.mw.fantasy.beanOutput.ResponseReferralDetails;
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

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * This interface will describe service methods.
 */

public interface AppServices {



    @Headers("Content-Type: application/json")
    @POST("utilities/getAppVersionDetails")
    Call<VersonBean> appVersion(@Body VersionInput mVersionInput);

    @Headers("Content-Type: application/json")
    @POST("signup")
    Call<ResponceSignup> signUp(@Body SingupInput mSingupInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<ResponsePlayerFantasyStats> playerFantasyStats(@Url String url,@Body PlayerFantasyStatsInput mPlayerFantasyStatsInput);

    @Headers("Content-Type: application/json")
    @POST("signin")
    Call<LoginResponseOut> login(@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST("recovery")
    Call<ForgetPasswordOut> forgotPassword(@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<DefaultRespose> deleteNotification(@Url String url,@Body NotificationDeleteInput mDeleteInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<ResponseDownloadTeam> downloadTeam(@Url String url,@Body DownloadTeamInput mDownloadTeamInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/downloadSnakeAuctionTeams")
    Call<ResponseDownloadTeam> auctiondownloadTeam(@Body DownloadTeamInput mDownloadTeamInput);

    @Headers("Content-Type: application/json")
    @POST("recovery/setPassword")
    Call<ForgetPasswordOut> resetPassword(@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST("signup/verifyEmail")
    Call<LoginResponseOut> verifyEmail(@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<MatchResponseOut> matchesListing(@Url String url, @Body MatchListInput mMatchListInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<MyContestMatchesOutput> myContestMatchesList(@Url String url,@Body MyContestMatchesInput myContestMatchesInput);


    @Headers("Content-Type: application/json")
    @POST
    Call<SingleTeamOutput> getUsersingleTeams(@Url String url, @Body MyTeamInput mMyTeamOutput);

    @Headers("Content-Type: application/json")
    @POST("users/getProfile")
    Call<LoginResponseOut> viewProfile(@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<LoginResponseOut> notificationCount(@Url String url,@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<DefaultRespose> notificationMarkRead(@Url String url,@Body NotificationMarkReadInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<ResponseBanner> bannersList(@Url String url,@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<MatchDetailOutPut> matchDetail(@Url String url,@Body MatchDetailInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST("wallet/getWallet")
    Call<WalletOutputBean> getWallet(@Body WalletInput mWalletInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<ResponseFavoriteTeam> getFavoriteTeam(@Url String url,@Body FavoriteTeamInput mFavoriteTeamInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<DefaultRespose> makeFavoriteTeams(@Url String url,@Body MakeFavoriteTeamInput mFavoriteTeamInput);

    @Headers("Content-Type: application/json")
    @POST("users/updateUserInfo")
    Call<DefaultRespose> updateProfileCall(@Body UpdateProfileInput mUpdateProfileInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<MatchContestOutPut> getContestsByType(@Url String url,@Body MatchContestInput mMatchContestInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<MatchContestOutPut> getjoinedContestsByType(@Url String url,@Body JoinedContestInput mMatchContestInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<AllContestOutPut> getAllContests(@Url String url,@Body MatchContestInput mMatchContestInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<ContestDetailOutput> getContest(@Url String url,@Body ContestDetailInput mContestDetailInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<ContestUserOutput> getJoinedContestsUsers(@Url String url,@Body ContestUserInput mContestDetailInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<PlayersOutput> getPlayers(@Url String url,@Body PlayersInput mPlayersInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<LoginResponseOut> addUserTeam(@Url String url,@Body CreateTeamInput mCreateTeamInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<LoginResponseOut> editUserTeam(@Url String url,@Body CreateTeamInput mCreateTeamInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<MyTeamOutput> getUserTeams(@Url String url,@Body MyTeamInput mMyTeamOutput);




    @Headers("Content-Type: application/json")
    @POST
    Call<JoinContestOutput> joinContest(@Url String url,@Body JoinContestInput mJoinContestInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<PromoCodeResponse> promoCode(@Url String url,@Body PromoCodeInput mPromoCodeInput);

    @Headers("Content-Type: application/json")
    @POST("users/getRefferalUsers")
    Call<ReferralUsersResponse> getReferralUsers(@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST("users/referEarn")
    Call<DefaultRespose> getReferEarn(@Body ReferEarnInput mReferEarnInput);


/*

    @Headers("Content-Type: application/json")
    @POST("contest/getContests")
    Call<JoinedContestOutput> getJoinedContests(@Body JoinedContestInput mJoinedContestInput);
*/


    @Headers("Content-Type: application/json")
    @POST
    Call<JoinedContestOutput> getJoinedContests(@Url String url,@Body JoinedContestInput mJoinedContestInput);

    @Headers("Content-Type: application/json")
    @POST("users/updateUserInfoPhone")
    Call<LoginResponseOut> sendMobileUserOtp(@Body VerifyMobileInput mobileInput);


    @Headers("Content-Type: application/json")
    @POST("users/updateUserInfo")
    Call<LoginResponseOut> sendMobileOtp(@Body VerifyMobileInput mobileInput);

    @Headers("Content-Type: application/json")
    @POST("signup/verifyEmail")
    Call<LoginResponseOut> verifyEmail(@Body VerifyMobileInput mobileInput);

//
//    @Headers("Content-Type: application/json")
//    @POST("signup/verifyPhoneNumber")
//    Call<LoginResponseOut> verifyPhoneNumber(@Body VerifyMobileInput mobileInput);



    @Headers("Content-Type: application/json")
    @POST("signup/verifyPhoneNumberOTP")
    Call<LoginResponseOut> verifyPhoneNumber(@Body VerifyMobileInput mobileInput);

    @Headers("Content-Type: application/json")
    @POST("signup/resendverify")
    Call<LoginResponseOut> resendverify(@Body VerifyMobileInput mobileInput);

    @Multipart
    @POST("upload/image")
    Call<LoginResponseOut> uploadImage(
            @Part("SessionKey") RequestBody userLoginSessionKey,
            @Part("Section") RequestBody nameRequestBody,
             @Part("MediaCaption") RequestBody aadharNumberRequestBody,
            @NonNull @Part MultipartBody.Part file);






    @Headers("Content-Type: application/json")
    @POST
    Call<DreamTeamOutput> bestPlayer(@Url String url,@Body DreamTeamInput mDreamTeamInput);

    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getMatchBestPlayers")
    Call<DreamTeamOutput> auctionallPlayersScore(@Body DreamTeamInput mDreamTeamInput);

    @Headers("Content-Type: application/json")
    @POST("signin/signout")
    Call<LoginResponseOut> signout(@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<CreateContestOutput> createContest(@Url String url,@Body CreateContestInput mCreateContestInput);

    @Headers("Content-Type: application/json")
    @POST("utilities/getReferralDetails")
    Call<ResponseReferralDetails> getReferralDetail();

    @Headers("Content-Type: application/json")
    @POST
    Call<WinBreakupOutPut> winning_breakup(@Url String url,@Body WinnerBreakupInput winnerBreakupInput);

    @Headers("Content-Type: application/json")
    @POST("users/changePassword")
    Call<LoginResponseOut> changePassword(@Body ChangePasswordInput mChnagePasswordInput);

    @Headers("Content-Type: application/json")
    @POST("users/getAvtars")
    Call<AvatarListOutput> getAvtars(@Body LoginInput loginInput);


    @Headers("Content-Type: application/json")
    @POST
    Call<SeriesOutput> getSeries(@Url String url,@Body SeriesInput mSeriesInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getSeriesRounds")
    Call<GetAuctionSeriesOutput> getAuctionSeries(@Body GetAuctionSeriesInput getAuctionSeriesInput);


    @Headers("Content-Type: application/json")
    @POST
    Call<RankingOutput> getRankings(@Url String url,@Body RankingInput mRankingInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<LoginResponseOut> switchTeam(@Url String url,@Body SwitchTeamInput switchTeamInput );

    @Headers("Content-Type: application/json")
    @POST("wallet/add")
    Call<ResponsePayTmDetails> payTm(@Body PaytmInput paytmInput);

    @Headers("Content-Type: application/json")
    @POST("wallet/confirmApp")
    Call<LoginResponseOut> payTmResponse(@Body PaytmInput paytmInput);

    @Headers("Content-Type: application/json")
    @POST("Wallet/getWallet")
    Call<TransactionsBean> transactionsApp(@Body TransactionInput transactionInput );

    @Headers("Content-Type: application/json")
    @POST
    Call<NotificationsResponse> notification(@Url String url,@Body NotificationInput notificationInput );

    @Headers("Content-Type: application/json")
    @POST("wallet/withdrawal")
    Call<WithDrawoutput> withdrawal_add(@Body WithdrawInput mWithdrawInput);


    @Headers("Content-Type: application/json")
    @POST("wallet/statusCheckPhonePeForAPP")
    Call<LoginResponseOut> checkPhonePeTransactionStatus(@Body CheckPhonePeTransactionStatusInput checkPhonePeTransactionStatusInput);



    @Streaming
    @GET
    Call<ResponseBody> downloadFileByUrl(@Url String fileUrl);

    @Headers("Content-Type: application/json")
    @POST
    Call<PointsList> getPoints(@Url String url,@Body PointsSystem system);

    @Headers("Content-Type: application/json")
    @POST
    Call<PromoCodeListOutput> promocodeList(@Url String url,@Body PromoCodeListInput system);

    @Headers("Content-Type: application/json")
    @POST("signin/OtpSignIn")
    Call<RequestOtpForSigninOutput> requestOtpForSignin(@Body RequestOtpForSigninInput requestOtpForSigninInput);


    @Headers("Content-Type: application/json")
    @POST("wallet/withdrawal_confirm")
    Call<PaytmWithdrawOutput> withdrawal(@Body WithdrawInput mWithdrawInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getPlayers")
    Call<GetAuctionPlayerOutput> getAuctionPlayers(@Body GetAuctionPlayerInput getAuctionPlayerInput);

    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getJoinedContestsUsers")
    Call<GetAuctionJoinedUserOutput> getAuctionJoinedUsers(@Body GetAuctionJoinedUserInput getAuctionJoinedUserInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/assistantTeamOnOff")
    Call<AssistSwichOutPut> auctionAssistantTeamOnOff(@Body AssistSwitchInput assistSwitchInput);

    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/addUserTeam")
    Call<AddPlayerInAssistantOutput> addPlayerInAssistant(@Body AddPlayerInAssistantInput addPlayerInAssistantInput);

    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/editUserTeam")
    Call<SimpleOutput> editPlayerInAssistant(@Body EditPlayerInAssistantInput editPlayerInAssistantInput);

    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getContest")
    Call<GetAuctionInfoOutput> getAuctionInfo(@Body GetAuctionInfoInput getAuctionInfoInput);

    @Headers("Content-Type: application/json")
    @POST("sports/getMatchAuction")
    Call<GetAuctionInfoOutput> getAuctionMatchInfo(@Body GetAuctionInfoInput getAuctionInfoInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getPlayerBid")
    Call<GetCurrentLiveAuctionPlayerOutput> getCurrentLiveAuctionPlayer(@Body GetAuctionInfoInput getAuctionInfoInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getContests")
    Call<GetSeriesAuctionContestOutput> getSeriesAuctionContest(@Body GetSeriesAuctionContestInput getSeriesAuctionContestInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getContestsByType")
    Call<GetSeriesAuctionContestByTypeOutput> getSeriesAuctionContestByType(@Body GetSeriesAuctionContestByTypeInput getSeriesAuctionContestByTypeInput);


    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/getContestsByType")
    Call<GetSeriesAuctionContestByTypeOutput> getSeriesDraftContestByType(@Body GetSeriesAuctionContestByTypeInput getSeriesAuctionContestByTypeInput);


    @Headers("Content-Type: application/json")
    @POST
    Call<JoinAuctionOutput> joinAuction(@Url String url,@Body JoinAuctionInput joinAuctionInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getContestBidHistory")
    Call<GetContestBidHistoryOutput> getContestBidHistory(@Body GetContestBidHistoryInput getContestBidHistoryInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/auctionTeamPlayersSubmit")
    Call<DefaultRespose> submitAuctionsPlayers(@Body SubmitAuctionsPlayersInput submitAuctionsPlayersInput);


    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/getSeriesRounds")
    Call<GetAuctionSeriesOutput> getSnackDraftSeries(@Body GetAuctionSeriesInput getAuctionSeriesInput);


    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/getContests")
    Call<GetSeriesAuctionContestOutput> getSeriesDraftContest(@Body GetSeriesAuctionContestInput getSeriesAuctionContestInput);


    @Headers("Content-Type: application/json")
    @POST
    Call<JoinAuctionOutput> joinDraft(@Url String url,@Body JoinAuctionInput joinAuctionInput);


    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/getPlayers")
    Call<GetAuctionPlayerOutput> getDraftPlayers(@Body GetAuctionPlayerInput getAuctionPlayerInput);


    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/getTeams")
    Call<GetDraftTeamsOutput> getDraftTeams(@Body GetDraftTeamsInput getDraftTeamsInput);

    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/getContest")
    Call<GetAuctionInfoOutput> getDraftInfo(@Body GetAuctionInfoInput getAuctionInfoInput);

    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/getJoinedContestsUsers")
    Call<GetAuctionJoinedUserOutput> getDraftJoinedUsers(@Body GetAuctionJoinedUserInput getAuctionJoinedUserInput);


    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/draftTeamPlayersSubmit")
    Call<DefaultRespose> submitDraftPlayers(@Body SubmitAuctionsPlayersInput submitAuctionsPlayersInput);

    @Headers("Content-Type: application/json")
    @POST("utilities/contact")
    Call<DefaultRespose> sendMessage(@Body HashMap<String,String> map);


    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/assistantTeamOnOff")
    Call<AssistSwichOutPut> draftAssistantTeamOnOff(@Body AssistSwitchInput assistSwitchInput);

    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/checkUserDraftInlive")
    Call<CheckUserDraftInLiveOutput> checkUserDraftInlive(@Body CheckUserDraftInLiveInput checkUserDraftInLiveInput);

    @Headers("Content-Type: application/json")
    @POST
    Call<GetDraftPlayerPointOutput> getDraftPlayersPoint(@Url String url,@Body GetDraftPlayerPointInput getDraftPlayerPointInput);


    @Headers("Content-Type: application/json")
    @POST("SnakeDrafts/editUserTeam")
    Call<SimpleOutput> editPlayerInDraftAssistant(@Body EditPlayerInAssistantInput editPlayerInAssistantInput);


    @Headers("Content-Type: application/json")
    @POST("SnakeDrafts/addUserTeam")
    Call<AddPlayerInAssistantOutput> addPlayerInDraftAssistant(@Body AddPlayerInAssistantInput addPlayerInAssistantInput);


    @Headers("Content-Type: application/json")
    @POST("SnakeDrafts/getPrivateContest")
    Call<GetPrivateContestOutput> getPrivateContest(@Body GetPrivateContestInput getPrivateContestInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getPrivateContest")
    Call<GetPrivateContestOutput> getAuctionPrivateContest(@Body GetPrivateContestInput getPrivateContestInput);


    @Headers("Content-Type: application/json")
    @POST("SnakeDrafts/add")
    Call<CreateprivateSnakeDraftOutput> createPrivateSnakeDraft(@Body CreateprivateSnakeDraftInput createprivateSnakeDraftInput);


    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getPlayers")
    Call<PlayersOutput> getAuctionPlayers(@Body PlayersInput mPlayersInput);

    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/getPlayer")
    Call<SinglePlayerData> getSingleAuctionPlayers(@Body PlayerFantasyStatsInput mPlayersInput);

    @Headers("Content-Type: application/json")
    @POST("auctionDrafts/add")
    Call<AuctionContestCreateOutput> auctioncreateContest(@Body CreateContestInput mCreateContestInput);

    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/getRoundPlayers")
    Call<GetPlayerRoundOutput> getPlayersRound(@Body CreateContestInput mCreateContestInput);

    @Headers("Content-Type: application/json")
    @POST("snakeDrafts/add")
    Call<AuctionContestCreateOutput> createPrivateSnakeContest(@Body CreateContestInput mCreateContestInput);

    @Headers("Content-Type: application/json")
    @POST("LuckyWheel/getWheelPoints")
    Call<SpinWheelOutput> getSpinWheelData(@Body LoginInput mLoginInput);


    @Headers("Content-Type: application/json")
    @POST("notifications/getCongratulationsNotification")
    Call<PopupData> getPopupData(@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST("notifications/updateNotificationStatus")
    Call<PopupData> updateData(@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST("LuckyWheel/addWheelPoints")
    Call<UpdateSpinWheelOutput> updateWinningSpinWheelData(@Body LoginInput mLoginInput);


    @Headers("Content-Type: application/json")
    @POST("subscription/getSubscription")
    Call<GetSubscriptionResponse> getSubscription(@Body LoginInput mLoginInput);

    @Headers("Content-Type: application/json")
    @POST("subscription/buySubscription")
    Call<DefaultRespose> buySubscription(@Body BuySubscriptionInput buySubscriptionInput);

    @Headers("Content-Type: application/json")
    @GET("utilities/supportMessage")
    Call<SubjectOutput> getSubject();

    @Headers("Content-Type: application/json")
    @GET("Utilities/setting")
    Call<GetSettingsOutput> getSettings();


    @FormUrlEncoded
    @POST("subscription/getSubscription")
    Call<SubsDto> getSubsData(@Field("SessionKey") String SessionKey);




}