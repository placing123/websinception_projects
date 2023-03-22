package com.websinception.megastar.appApi;


import android.support.annotation.NonNull;

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

/**
 * <p/>
 * This Wrapper class will be mediator of service interface and calling class.
 * Whatever the features of retrofit and okhttp api will implement, need to be put into this class.
 * We can put common webservice security features to this class so that we can increase the reusability of code.
 */
public class ServiceWrapper {
    private static AppServices service = ServiceGenerator.createService(AppServices.class);
    private static ServiceWrapper serviceWrapper = new ServiceWrapper();

    private ServiceWrapper() {

    }

    public static ServiceWrapper getInstance() {
        if (service == null) service = ServiceGenerator.createService(AppServices.class);
        return serviceWrapper;
    }

    public Call<VersonBean> appVersion(VersionInput version) {
        return service.appVersion(version);
    }

    public Call<LoginResponseOut> loginCall(LoginInput mLoginInput) {
        return service.login(mLoginInput);
    }


    public Call<ForgetPasswordOut> resetPassword(LoginInput mLoginInput) {
        return service.resetPassword(mLoginInput);
    }

    public Call<LoginResponseOut> verifyEmail(LoginInput mLoginInput) {
        return service.verifyEmail(mLoginInput);

    }

    public Call<MatchContestOutPut> getjoinedContestsByType(String url,JoinedContestInput mMatchContestInput){
        return service.getjoinedContestsByType(url,mMatchContestInput);
    }


    public Call<LoginResponseOut> checkPhonePeTransactionStatus(CheckPhonePeTransactionStatusInput checkPhonePeTransactionStatusInput){
        return service.checkPhonePeTransactionStatus(checkPhonePeTransactionStatusInput);
    }



    public Call<LoginResponseOut> verifyEmailAddress(VerifyMobileInput mobileInput) {
        return service.verifyEmail(mobileInput);
    }

    public Call<ResponsePlayerFantasyStats> playerFantasyStats(String url,PlayerFantasyStatsInput mPlayerFantasyStatsInput){
        return service.playerFantasyStats(url,mPlayerFantasyStatsInput);
    }

    public Call<ResponseDownloadTeam> downloadTeam( String url,DownloadTeamInput mDownloadTeamInput){
        return service.downloadTeam(url,mDownloadTeamInput);
    }

    public Call<ResponseDownloadTeam> auctiondownloadTeam(DownloadTeamInput mDownloadTeamInput){
        return service.auctiondownloadTeam(mDownloadTeamInput);
    }

    public Call<DefaultRespose> deleteNotification(String url,NotificationDeleteInput mDeleteInput){
        return service.deleteNotification(url,mDeleteInput);
    }

    public Call<MatchResponseOut> matchesListingCall(String url,MatchListInput mMatchListInput) {
        return service.matchesListing(url,mMatchListInput);
    }


    public Call<SingleTeamOutput> getSingleUserTeams(String url, MyTeamInput mMyTeamListInput) {
        return service.getUsersingleTeams(url,mMyTeamListInput);
    }


    public Call<LoginResponseOut> viewProfileCall(LoginInput mLoginInput) {
        return service.viewProfile(mLoginInput);
    }

    public Call<LoginResponseOut> notificationCount(String url,LoginInput mLoginInput){
        return service.notificationCount(url,mLoginInput);
    }

    public Call<DefaultRespose> notificationMarkRead(String url,NotificationMarkReadInput mNotificationMarkReadInput){
        return service.notificationMarkRead(url,mNotificationMarkReadInput);
    }

    public Call<ResponseBanner> bannersList(String url,LoginInput mLoginInput) {
        return service.bannersList(url,mLoginInput);

    }

    public Call<MatchDetailOutPut> matchDetail(String url,MatchDetailInput mLoginInput) {
        return service.matchDetail(url,mLoginInput);
    }

    public Call<WalletOutputBean> getWallet(WalletInput mWalletInput) {
        return service.getWallet(mWalletInput);
    }

    public Call<ResponseFavoriteTeam> getFavoriteTeam(String url,FavoriteTeamInput mFavoriteTeamInput){
        return service.getFavoriteTeam(url,mFavoriteTeamInput);
    }

    public Call<DefaultRespose> updateProfileCall(UpdateProfileInput mUpdateProfileInput) {
        return service.updateProfileCall(mUpdateProfileInput);
    }

    public Call<DefaultRespose> makeFavoriteTeams(String url,MakeFavoriteTeamInput makeFavoriteTeamInput){
        return service.makeFavoriteTeams(url,makeFavoriteTeamInput);
    }

    public Call<MatchContestOutPut> getContestsByType(String url,MatchContestInput mMatchContestInput) {
        return service.getContestsByType(url,mMatchContestInput);
    }

    public Call<AllContestOutPut> getAllContests(String url,MatchContestInput mMatchContestInput) {
        return service.getAllContests(url,mMatchContestInput);
    }



    public Call<MyContestMatchesOutput> myContestMatchesList(String url,MyContestMatchesInput myContestMatchesInput){
        return service.myContestMatchesList(url,myContestMatchesInput);
    }


    public Call<ContestDetailOutput> getContest(String url,ContestDetailInput mContestDetailInput) {
        return service.getContest(url,mContestDetailInput);
    }

    public Call<ContestUserOutput> getJoinedContestsUsers(String url,ContestUserInput mContestUserInput) {
        return service.getJoinedContestsUsers(url,mContestUserInput);
    }

    public Call<PlayersOutput> getPlayers(String url,PlayersInput mPlayersInput) {
        return service.getPlayers(url,mPlayersInput);
    }

    public Call<LoginResponseOut> addUserTeam(String url,CreateTeamInput mPlayersInput) {
        return service.addUserTeam(url,mPlayersInput);
    }

    public Call<LoginResponseOut> editUserTeam(String url,CreateTeamInput mPlayersInput) {
        return service.editUserTeam(url,mPlayersInput);
    }

    public Call<PromoCodeResponse> promoCode(String url,PromoCodeInput mPromoCodeInput){
        return service.promoCode(url,mPromoCodeInput);
    }

    public Call<MyTeamOutput> getUserTeams(String url,MyTeamInput mMyTeamListInput) {
        return service.getUserTeams(url,mMyTeamListInput);
    }

    public Call<JoinContestOutput> joinContest(String url,JoinContestInput mJoinContestOutput) {
        return service.joinContest(url, mJoinContestOutput);
    }



    public Call<JoinedContestOutput> getJoinedContests(String url,JoinedContestInput mJoinedContestInput) {
        return service.getJoinedContests(url,mJoinedContestInput);
    }

    public Call<DreamTeamOutput> dreamTeam(String url,DreamTeamInput mDreamTeamInput) {
        return service.bestPlayer(url,mDreamTeamInput);
    }

    public Call<DreamTeamOutput> auctionallPlayersScore(DreamTeamInput mDreamTeamInput) {
        return service.auctionallPlayersScore(mDreamTeamInput);
    }

    public Call<LoginResponseOut> sendMobileOtp(VerifyMobileInput mobileInput) {
        return service.sendMobileOtp(mobileInput);
    }



    public Call<LoginResponseOut> sendMobileUserOtp(VerifyMobileInput mobileInput) {
        return service.sendMobileUserOtp(mobileInput);
    }

    public Call<ResponseReferralDetails> getReferralDetail(){
        return service.getReferralDetail();
    }

    public Call<LoginResponseOut> verifyPhoneNumber(VerifyMobileInput mobileInput) {
        return service.verifyPhoneNumber(mobileInput);
    }

    public Call<LoginResponseOut> resendverify(VerifyMobileInput mobileInput) {
        return service.resendverify(mobileInput);
    }

    public Call<LoginResponseOut> uploadImage(
            RequestBody SessionKeyRequestBody
            , RequestBody sectionRequestBody
            , RequestBody mediaCaptionRequestBody
            , MultipartBody.Part file) {
        return service.uploadImage(SessionKeyRequestBody
                , sectionRequestBody
                , mediaCaptionRequestBody
                , file);
    }


    public Call<CreateContestOutput> createContest(String url,CreateContestInput mobileInput) {
        return service.createContest(url,mobileInput);
    }


    public Call<LoginResponseOut> switchTeam(String url,SwitchTeamInput switchTeamInput) {
        return service.switchTeam(url,switchTeamInput);
    }

    public Call<ResponsePayTmDetails> payTm(PaytmInput paytmInput) {
        return service.payTm(paytmInput);
    }

    public Call<LoginResponseOut> payTmResponse(PaytmInput paytmInput) {


        return service.payTmResponse(paytmInput);
    }


    public Call<ResponseBody> downloadFileByUrlCall(@NonNull String fileUrl) {
        return service.downloadFileByUrl(fileUrl);
    }


    public Call<TransactionsBean> transactionsApp(TransactionInput transactionInput) {
        return service.transactionsApp(transactionInput);
    }


    public Call<ResponceSignup> signUpCall(SingupInput mSingupInput) {
        return service.signUp(mSingupInput);


    }

    public Call<ForgetPasswordOut> forgotPasswordCall(LoginInput mLoginInput) {
        return service.forgotPassword(mLoginInput);
    }



    public Call<WinBreakupOutPut> winning_breakup(String url,WinnerBreakupInput mWinnerBreakupInput) {
        return service.winning_breakup(url,mWinnerBreakupInput);
    }


    public Call<LoginResponseOut> changePassword(ChangePasswordInput mChangePasswordInput) {
        return service.changePassword(mChangePasswordInput);
    }

    public Call<AvatarListOutput> getAvtars(LoginInput mLoginInput) {
        return service.getAvtars(mLoginInput);
    }
    public Call<RankingOutput> getRankings(String url,RankingInput mRankingInput) {
        return service.getRankings(url,mRankingInput);
    }

    public Call<SeriesOutput> matchSeries(String url,SeriesInput seriesInput) {
        return service.getSeries(url,seriesInput);
    }
    public Call<NotificationsResponse> notificationList(String url,NotificationInput notificationInput) {
        return service.notification(url,notificationInput);
    }
    public Call<WithDrawoutput> withdrawal_add(WithdrawInput mWithdrawInput) {
        return service.withdrawal_add(mWithdrawInput);
    }


    public Call<GetAuctionSeriesOutput> getAuctionSeries(GetAuctionSeriesInput getAuctionSeriesInput) {
        return service.getAuctionSeries(getAuctionSeriesInput);
    }


    public Call<RequestOtpForSigninOutput> requestOtpForSignin(RequestOtpForSigninInput requestOtpForSigninInput) {
        return service.requestOtpForSignin(requestOtpForSigninInput);
    }

    public Call<PointsList> getList(String url,PointsSystem system) {
        return service.getPoints(url,system);
    }

    public Call<PromoCodeListOutput> promocodeList (String url,PromoCodeListInput mPromoCodeListOutput){
        return service.promocodeList(url,mPromoCodeListOutput);
    }
    public Call<PaytmWithdrawOutput> withdrawal_confirm(WithdrawInput mWithdrawInput) {
        return service.withdrawal(mWithdrawInput);
    }


    public Call<ReferralUsersResponse> getReferralUsers(LoginInput mLoginInput){
        return service.getReferralUsers(mLoginInput);
    }

    public  Call<DefaultRespose> getReferEarn(ReferEarnInput mReferEarnInput) {
        return service.getReferEarn(mReferEarnInput);
    }

    public Call<GetAuctionPlayerOutput> getAuctionPlayers(GetAuctionPlayerInput getAuctionPlayerInput) {
        return service.getAuctionPlayers(getAuctionPlayerInput);
    }

    public Call<GetAuctionJoinedUserOutput> getAuctionJoinedUsers(GetAuctionJoinedUserInput getAuctionJoinedUserInput) {
        return service.getAuctionJoinedUsers(getAuctionJoinedUserInput);
    }


    public Call<AssistSwichOutPut> auctionAssistantTeamOnOff(AssistSwitchInput assistSwitchInput) {
        return service.auctionAssistantTeamOnOff(assistSwitchInput);
    }


    public Call<AddPlayerInAssistantOutput> addPlayerInAssistant(AddPlayerInAssistantInput addPlayerInAssistantInput) {
        return service.addPlayerInAssistant(addPlayerInAssistantInput);
    }

    public Call<SimpleOutput> editPlayerInAssistant(EditPlayerInAssistantInput editPlayerInAssistantInput) {
        return service.editPlayerInAssistant(editPlayerInAssistantInput);
    }

    public Call<GetAuctionInfoOutput> getAuctionInfo(GetAuctionInfoInput getAuctionInfoInput) {
        return service.getAuctionInfo(getAuctionInfoInput);
    }

    public Call<GetAuctionInfoOutput> getAuctionMatchInfo(GetAuctionInfoInput getAuctionInfoInput) {
        return service.getAuctionMatchInfo(getAuctionInfoInput);
    }

    public Call<GetCurrentLiveAuctionPlayerOutput> getCurrentLiveAuctionPlayer(GetAuctionInfoInput getAuctionInfoInput) {
        return service.getCurrentLiveAuctionPlayer(getAuctionInfoInput);
    }

    public Call<GetSeriesAuctionContestOutput> getSeriesAuctionContest(GetSeriesAuctionContestInput getSeriesAuctionContestInput) {
        return service.getSeriesAuctionContest(getSeriesAuctionContestInput);
    }


    public Call<GetSeriesAuctionContestByTypeOutput> getSeriesAuctionContestByType(GetSeriesAuctionContestByTypeInput getSeriesAuctionContestByTypeInput) {
        return service.getSeriesAuctionContestByType(getSeriesAuctionContestByTypeInput);
    }


    public Call<GetSeriesAuctionContestByTypeOutput> getSeriesDraftContestByType(GetSeriesAuctionContestByTypeInput getSeriesAuctionContestByTypeInput) {
        return service.getSeriesDraftContestByType(getSeriesAuctionContestByTypeInput);
    }



    public Call<JoinAuctionOutput> joinAuction(String url,JoinAuctionInput joinAuctionInput) {
        return service.joinAuction(url,joinAuctionInput);
    }


    public Call<GetContestBidHistoryOutput> getContestBidHistory(GetContestBidHistoryInput getContestBidHistoryInput) {
        return service.getContestBidHistory(getContestBidHistoryInput);
    }

    public Call<DefaultRespose> submitAuctionsPlayers(SubmitAuctionsPlayersInput submitAuctionsPlayersInput) {
        return service.submitAuctionsPlayers(submitAuctionsPlayersInput);
    }


    public Call<GetAuctionSeriesOutput> getSnackDraftSeries(GetAuctionSeriesInput getAuctionSeriesInput) {
        return service.getSnackDraftSeries(getAuctionSeriesInput);
    }

    public Call<GetSeriesAuctionContestOutput> getSeriesDraftContest(GetSeriesAuctionContestInput getSeriesAuctionContestInput) {
        return service.getSeriesDraftContest(getSeriesAuctionContestInput);
    }

    public Call<JoinAuctionOutput> joinDraft(String url,JoinAuctionInput joinAuctionInput) {
        return service.joinDraft(url,joinAuctionInput);
    }



    public Call<GetAuctionPlayerOutput> getDraftPlayers(GetAuctionPlayerInput getAuctionPlayerInput) {
        return service.getDraftPlayers(getAuctionPlayerInput);
    }


    public Call<GetDraftTeamsOutput> getDraftTeams(GetDraftTeamsInput getDraftTeamsInput) {
        return service.getDraftTeams(getDraftTeamsInput);
    }

    public Call<GetAuctionInfoOutput> getDraftInfo(GetAuctionInfoInput getAuctionInfoInput) {
        return service.getDraftInfo(getAuctionInfoInput);
    }

    public Call<GetAuctionJoinedUserOutput> getDraftJoinedUsers(GetAuctionJoinedUserInput getAuctionJoinedUserInput) {
        return service.getDraftJoinedUsers(getAuctionJoinedUserInput);
    }

    public Call<DefaultRespose> submitDraftPlayers(SubmitAuctionsPlayersInput submitAuctionsPlayersInput) {
        return service.submitDraftPlayers(submitAuctionsPlayersInput);
    }


    public Call<DefaultRespose> sendMessage(HashMap<String, String> submitAuctionsPlayersInput) {
        return service.sendMessage(submitAuctionsPlayersInput);
    }


    public Call<AssistSwichOutPut> draftAssistantTeamOnOff(AssistSwitchInput assistSwitchInput) {
        return service.draftAssistantTeamOnOff(assistSwitchInput);
    }

    public Call<CheckUserDraftInLiveOutput> checkUserDraftInlive(CheckUserDraftInLiveInput checkUserDraftInLiveInput) {
        return service.checkUserDraftInlive(checkUserDraftInLiveInput);
    }

    public Call<GetDraftPlayerPointOutput> getDraftPlayersPoint(String url,GetDraftPlayerPointInput getDraftPlayerPointInput) {
        return service.getDraftPlayersPoint(url,getDraftPlayerPointInput);
    }


    public Call<SimpleOutput> editPlayerInDraftAssistant(EditPlayerInAssistantInput editPlayerInAssistantInput) {
        return service.editPlayerInDraftAssistant(editPlayerInAssistantInput);
    }


    public Call<AddPlayerInAssistantOutput> addPlayerInDraftAssistant(AddPlayerInAssistantInput addPlayerInAssistantInput) {
        return service.addPlayerInDraftAssistant(addPlayerInAssistantInput);
    }


    public Call<GetPrivateContestOutput> getPrivateContest(GetPrivateContestInput getPrivateContestInput){
        return service.getPrivateContest(getPrivateContestInput);
    }

    public Call<GetPrivateContestOutput> getAuctionPrivateContest(GetPrivateContestInput getPrivateContestInput){
        return service.getAuctionPrivateContest(getPrivateContestInput);
    }


    public Call<CreateprivateSnakeDraftOutput> createPrivateSnakeDraft(CreateprivateSnakeDraftInput createprivateSnakeDraftInput){
        return service.createPrivateSnakeDraft(createprivateSnakeDraftInput);
    }

    public Call<PlayersOutput> getAuctionPlayers(PlayersInput createprivateSnakeDraftInput){
        return service.getAuctionPlayers(createprivateSnakeDraftInput);
    }


    public Call<SinglePlayerData> getSingleAuctionPlayers(PlayerFantasyStatsInput createprivateSnakeDraftInput){
        return service.getSingleAuctionPlayers(createprivateSnakeDraftInput);
    }


    public Call<AuctionContestCreateOutput> auctioncreateContest(CreateContestInput createContestInput){
        return service.auctioncreateContest(createContestInput);
    }

    public Call<AuctionContestCreateOutput> createPrivateSnakeContest(CreateContestInput createContestInput){
        return service.createPrivateSnakeContest(createContestInput);
    }

    public Call<GetPlayerRoundOutput> getPlayersRound(CreateContestInput createContestInput){
        return service.getPlayersRound(createContestInput);
    }




    public Call<SpinWheelOutput> getSpinWheelData(LoginInput createContestInput){
        return service.getSpinWheelData(createContestInput);
    }

    public Call<PopupData> getPopupData(LoginInput createContestInput){
        return service.getPopupData(createContestInput);
    }

    public Call<PopupData> updateData(LoginInput createContestInput){
        return service.updateData(createContestInput);
    }

    public Call<UpdateSpinWheelOutput> updateWinningSpinWheelData(LoginInput createContestInput){
        return service.updateWinningSpinWheelData(createContestInput);
    }

    public Call<GetSubscriptionResponse> getSubscription(LoginInput createContestInput){
        return service.getSubscription(createContestInput);
    }

    public Call<DefaultRespose> buySubscription(BuySubscriptionInput buySubscriptionInput){
        return service.buySubscription(buySubscriptionInput);
    }


    public Call<SubjectOutput> getSubject(){
        return service.getSubject();
    }



    public Call<GetSettingsOutput> getSettings(){
        return service.getSettings();
    }






}