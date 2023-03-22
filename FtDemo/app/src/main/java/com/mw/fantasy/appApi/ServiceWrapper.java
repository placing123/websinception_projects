package com.mw.fantasy.appApi;


import android.support.annotation.NonNull;

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