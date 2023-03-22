package com.websinception.megastar.appApi;


import android.support.annotation.NonNull;


import com.websinception.megastar.SubsDto;
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
import com.websinception.megastar.beanOutput.JoinedContestOutput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.MatchContestOutPut;
import com.websinception.megastar.beanOutput.MatchDetailOutPut;
import com.websinception.megastar.beanOutput.MatchResponseOut;
import com.websinception.megastar.beanOutput.MyContestMatchesOutput;
import com.websinception.megastar.beanOutput.MyTeamOutput;
import com.websinception.megastar.beanOutput.NotificationsResponse;
import com.websinception.megastar.beanOutput.PaytmWithdrawOutput;
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
import com.websinception.megastar.beanOutput.ResponseDownloadTeam;
import com.websinception.megastar.beanOutput.ResponseFavoriteTeam;
import com.websinception.megastar.beanOutput.ResponsePayTmDetails;
import com.websinception.megastar.beanOutput.ResponsePlayerFantasyStats;
import com.websinception.megastar.beanOutput.ResponseReferralDetails;
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