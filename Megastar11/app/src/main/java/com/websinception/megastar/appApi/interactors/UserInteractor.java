package com.websinception.megastar.appApi.interactors;

import android.util.Log;

import com.google.gson.Gson;
import com.websinception.megastar.AppController;
import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.appApi.APIError;
import com.websinception.megastar.appApi.ErrorUtils;
import com.websinception.megastar.appApi.ServiceWrapper;
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
import com.websinception.megastar.beanOutput.ResponseUpdateProfile;
import com.websinception.megastar.beanOutput.SeriesOutput;
import com.websinception.megastar.beanOutput.SimpleOutput;
import com.websinception.megastar.beanOutput.SinglePlayerData;
import com.websinception.megastar.beanOutput.SingleTeamOutput;
import com.websinception.megastar.beanOutput.SpinWheelOutput;
import com.websinception.megastar.beanOutput.SubjectOutput;
import com.websinception.megastar.beanOutput.TransactionsBean;
import com.websinception.megastar.beanOutput.UpdateSpinWheelOutput;
import com.websinception.megastar.beanOutput.VersonBean;
import com.websinception.megastar.beanOutput.WalletOutputBean;
import com.websinception.megastar.beanOutput.WinBreakupOutPut;
import com.websinception.megastar.beanOutput.WithDrawoutput;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserInteractor implements IUserInteractor {


    @Override
    public Call<VersonBean> appVersion(VersionInput versionInput, final OnVersonResponseListener onResponseListener) {

        final Call<VersonBean> responseLoginCallback = ServiceWrapper.getInstance().appVersion(versionInput);

        responseLoginCallback.enqueue(new Callback<VersonBean>() {
            @Override
            public void onResponse(Call<VersonBean> call, Response<VersonBean> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response != null && response.body() != null && response.isSuccessful()) {

                    if (response.body().getResponseCode() == 502) {

                        onResponseListener.onError(response.body().getMessage());

                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<VersonBean> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;


    }


    @Override
    public Call<LoginResponseOut> login(final LoginInput mLoginInput, final OnLoginResponseListener onResponseListener) {
        if (APIValidate.isLoginValid(mLoginInput, onResponseListener)) {
            final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().loginCall(mLoginInput);

            responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
                @Override
                public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response != null && response.body() != null && response.isSuccessful()) {

                        if (response.body().getResponseCode() == 501) {

                            onResponseListener.onError(response.body().getMessage());

                        } else if (response.body().getResponseCode() == 502) {

                            onResponseListener.onError(response.body().getMessage());

                            AppUtils.logoutSession();
                        } else if (response.body().getResponseCode() == 500) {

                            if (mLoginInput.getSource().equals(Constant.Facebook) || mLoginInput.getSource().equals(Constant.Google)) {
                                onResponseListener.onAccountAvailableForSignUp(response.body().getMessage());
                            } else {
                                onResponseListener.onError(response.body().getMessage());
                            }


                        } else if (response.body().getResponseCode() == 200 || response.body().getResponseCode() == 201) {

                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onAccountAvailableForSignUp(response.body().getMessage());
                        }

                    } else {
                        if (response != null && response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<ResponseFavoriteTeam> getFavoriteTeam(FavoriteTeamInput mFavoriteTeamInput, final OnGetFavoriteTeamListener mOnGetFavoriteTeamListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "users/getDefaultFavouriteTeams";
                break;
            case 2:
                url = "football/users/getDefaultFavouriteTeams";
                break;
        }
        final Call<ResponseFavoriteTeam> responseFavoriteTeamCallBack = ServiceWrapper.getInstance().getFavoriteTeam(url, mFavoriteTeamInput);
        responseFavoriteTeamCallBack.enqueue(new Callback<ResponseFavoriteTeam>() {
            @Override
            public void onResponse(Call<ResponseFavoriteTeam> call, Response<ResponseFavoriteTeam> response) {
                if (responseFavoriteTeamCallBack == null || responseFavoriteTeamCallBack.isCanceled())
                    return;

                if (response != null && response.body() != null && response.isSuccessful()) {

                    if (response.body().getResponseCode() == 502) {

                        mOnGetFavoriteTeamListener.onError(response.body().getMessage());

                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 500) {
                        mOnGetFavoriteTeamListener.onError(response.body().getMessage());

                    } else if (response.body().getResponseCode() == 200) {

                        mOnGetFavoriteTeamListener.onSuccess(response.body());

                    } else {

                        Log.d("jsonResponce", response.body().getData().getClass().getSimpleName());

                        mOnGetFavoriteTeamListener.onError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseFavoriteTeam> call, Throwable t) {
                if (responseFavoriteTeamCallBack == null || responseFavoriteTeamCallBack.isCanceled())
                    return;

                if (t != null)
                    mOnGetFavoriteTeamListener.onError(t.getMessage() + "");
                else
                    mOnGetFavoriteTeamListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseFavoriteTeamCallBack;
    }

    @Override
    public Call<DefaultRespose> makeFavoriteTeams(MakeFavoriteTeamInput mMakeFavoriteTeamInput, final OnMakeFavoriteTeamListener mOnMakeFavoriteTeamListener) {

        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "users/makeFavouriteTeams";
                break;
            case 2:
                url = "football/users/makeFavouriteTeams";
                break;
        }
        final Call<DefaultRespose> resposeCallBack = ServiceWrapper.getInstance().makeFavoriteTeams(url, mMakeFavoriteTeamInput);
        resposeCallBack.enqueue(new Callback<DefaultRespose>() {
            @Override
            public void onResponse(Call<DefaultRespose> call, Response<DefaultRespose> response) {
                if (resposeCallBack == null || resposeCallBack.isCanceled())
                    return;

                if (response != null && response.body() != null && response.isSuccessful()) {

                    if (response.body().getResponseCode() == 502) {

                        mOnMakeFavoriteTeamListener.onError(response.body().getMessage());

                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 500) {
                        mOnMakeFavoriteTeamListener.onError(response.body().getMessage());

                    } else if (response.body().getResponseCode() == 200) {

                        mOnMakeFavoriteTeamListener.onSuccess(response.body());

                    } else {

                        Log.d("jsonResponce", response.body().getData().getClass().getSimpleName());

                        mOnMakeFavoriteTeamListener.onError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultRespose> call, Throwable t) {

                if (resposeCallBack == null || resposeCallBack.isCanceled()) return;

                if (t != null)
                    mOnMakeFavoriteTeamListener.onError(t.getMessage() + "");
                else
                    mOnMakeFavoriteTeamListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });

        return resposeCallBack;
    }

    @Override
    public Call<ResponseDownloadTeam> downloadTeam(DownloadTeamInput mDownloadTeamInput, final OnDownloadTeamListener mOnDownloadTeamListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "contest/downloadTeams";
                break;
            case 2:
                url = "football/contest/downloadTeams";
                break;
        }
        final Call<ResponseDownloadTeam> responseDownloadTeamCallback = ServiceWrapper.getInstance().downloadTeam(url, mDownloadTeamInput);
        responseDownloadTeamCallback.enqueue(new Callback<ResponseDownloadTeam>() {
            @Override
            public void onResponse(Call<ResponseDownloadTeam> call, Response<ResponseDownloadTeam> response) {

                if (responseDownloadTeamCallback == null || responseDownloadTeamCallback.isCanceled())
                    return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {

                        mOnDownloadTeamListener.onError(response.body().getMessage());

                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 500) {
                        mOnDownloadTeamListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {

                        mOnDownloadTeamListener.onSuccess(response.body());

                    } else {
                        mOnDownloadTeamListener.onError(response.body().getMessage());

                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        mOnDownloadTeamListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());

                    } else {
                        mOnDownloadTeamListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseDownloadTeam> call, Throwable t) {
                if (responseDownloadTeamCallback == null || responseDownloadTeamCallback.isCanceled())
                    return;

                if (t != null)
                    mOnDownloadTeamListener.onError(t.getMessage() + "");
                else
                    mOnDownloadTeamListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseDownloadTeamCallback;
    }

    @Override
    public Call<ResponseDownloadTeam> auctiondownloadTeam(DownloadTeamInput mDownloadTeamInput, final OnDownloadTeamListener mOnDownloadTeamListener) {
        final Call<ResponseDownloadTeam> responseDownloadTeamCallback = ServiceWrapper.getInstance().auctiondownloadTeam(mDownloadTeamInput);
        responseDownloadTeamCallback.enqueue(new Callback<ResponseDownloadTeam>() {
            @Override
            public void onResponse(Call<ResponseDownloadTeam> call, Response<ResponseDownloadTeam> response) {

                if (responseDownloadTeamCallback == null || responseDownloadTeamCallback.isCanceled())
                    return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {

                        mOnDownloadTeamListener.onError(response.body().getMessage());

                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 500) {
                        mOnDownloadTeamListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {

                        mOnDownloadTeamListener.onSuccess(response.body());

                    } else {
                        mOnDownloadTeamListener.onError(response.body().getMessage());

                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        mOnDownloadTeamListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());

                    } else {
                        mOnDownloadTeamListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseDownloadTeam> call, Throwable t) {
                if (responseDownloadTeamCallback == null || responseDownloadTeamCallback.isCanceled())
                    return;

                if (t != null)
                    mOnDownloadTeamListener.onError(t.getMessage() + "");
                else
                    mOnDownloadTeamListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });

        return responseDownloadTeamCallback;
    }

    @Override
    public Call<ResponceSignup> signUp(SingupInput mSingupInput, final OnSignUpResponseListener onResponseListener) {
        if (APIValidate.isSignUpValid(mSingupInput, onResponseListener)) {
            final Call<ResponceSignup> responseLoginCallback = ServiceWrapper.getInstance().signUpCall(mSingupInput);
            responseLoginCallback.enqueue(new Callback<ResponceSignup>() {
                @Override
                public void onResponse(Call<ResponceSignup> call, Response<ResponceSignup> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response != null && response.body() != null && response.isSuccessful()) {

                        if (response.body().getResponseCode() == 502) {

                            onResponseListener.onError(response.body().getMessage());

                            AppUtils.logoutSession();
                        } else if (response.body().getResponseCode() == 200 || response.body().getResponseCode() == 201) {


//                            if (response.body().getData().getClass().getSimpleName().equals("LinkedTreeMap")) {
//
//                                Gson gson = new Gson();
//
//                                String responceString = AppUtils.gsonToJSON(response.body());
//
//                                ResponceSignup mResponceSignup = gson.fromJson(responceString, ResponceSignup.class);
//
//                                LoginResponseOut mLoginResponseOut = gson.fromJson(responceString, LoginResponseOut.class);

                            //AppSession.getInstance().setLoginSession(mLoginResponseOut);

                            onResponseListener.onSuccess(response.body());


//                            }


                        } else {

                            Log.d("jsonResponce", response.body().getData().getClass().getSimpleName());

                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response != null && response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponceSignup> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }


        return null;
    }

    @Override
    public Call<ForgetPasswordOut> forgotPassword(LoginInput mLoginInput, final OnForgetPasswordListener onResponseListener) {
        final Call<ForgetPasswordOut> responseLoginCallback = ServiceWrapper.getInstance().forgotPasswordCall(mLoginInput);
        responseLoginCallback.enqueue(new Callback<ForgetPasswordOut>() {
            @Override
            public void onResponse(Call<ForgetPasswordOut> call, Response<ForgetPasswordOut> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {

                        onResponseListener.onError(response.body().getMessage());

                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 200 || response.body().getResponseCode() == 201) {

                        onResponseListener.onSuccess(response.body());

                    } else {
                        onResponseListener.onError(response.body().getMessage());

                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());

                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordOut> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }

    @Override
    public Call<ForgetPasswordOut> resetPassword(LoginInput mLoginInput, final OnForgetPasswordListener onResponseListener) {

        if (APIValidate.isResetPasswordDataValid(mLoginInput, onResponseListener)) {
            Call<ForgetPasswordOut> responseLoginCallback = ServiceWrapper.getInstance().resetPassword(mLoginInput);

            responseLoginCallback.enqueue(new Callback<ForgetPasswordOut>() {
                @Override
                public void onResponse(Call<ForgetPasswordOut> call, Response<ForgetPasswordOut> response) {

                    if (response.body() != null && response.isSuccessful()) {

                        if (response.body().getResponseCode() == 502) {

                            onResponseListener.onError(response.body().getMessage());

                            AppUtils.logoutSession();
                        } else if (response.body().getResponseCode() == 200) {

                            onResponseListener.onSuccess(response.body());


                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ForgetPasswordOut> call, Throwable t) {
                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;

    }

    @Override
    public Call<LoginResponseOut> verifyEmail(LoginInput mLoginInput, final OnLoginResponseListener onResponseListener) {

        final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().verifyEmail(mLoginInput);
        responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
            @Override
            public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response != null && response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {

                        onResponseListener.onError(response.body().getMessage());

                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 200) {

                        onResponseListener.onSuccess(response.body());

                    } else {

                        Log.d("jsonResponce", response.body().getData().getClass().getSimpleName());

                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }

    @Override
    public Call<MatchResponseOut> matchesListing(MatchListInput matchListInput
            , final OnResponseMatchesListener onResponseListener) {

        if (APIValidate.isMatchesListValid(matchListInput, onResponseListener)) {

            String url = "";
            if (AppSession.getInstance().getPlayMode() == 2) {
                url = "snakeDrafts/getMatches";
            } else {
                switch (AppSession.getInstance().getGameType()) {
                    case 1:
                        url = "sports/getMatches";
                        break;
                    case 2:
                        url = "football/sports/getMatches";
                        break;
                }
            }
            final Call<MatchResponseOut> responseLoginCallback = ServiceWrapper.getInstance().matchesListingCall(url, matchListInput);

            responseLoginCallback.enqueue(new Callback<MatchResponseOut>() {
                @Override
                public void onResponse(Call<MatchResponseOut> call, Response<MatchResponseOut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {

                            onResponseListener.onError(response.body().getMessage());
                            onResponseListener.OnSessionExpire();
                            AppUtils.logoutSession();
                        } else if (response.body().getResponseCode() == 200) {

                            onResponseListener.onSuccess(response.body());


                        } else if (response.body().getResponseCode() == 501) {
                            onResponseListener.onError(response.body().getMessage());
                        } else {
                            onResponseListener.onNotFound(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MatchResponseOut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }


    @Override
    public Call<SingleTeamOutput> getSingleUserTeams(MyTeamInput mMyTeamInput, final OnResponseSingleTeamsListener onResponseListener) {
        if (APIValidate.isSingleTeamListValid(mMyTeamInput, onResponseListener)) {


            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "contest/getUserTeams";
                    break;
                case 2:
                    url = "football/contest/getUserTeams";
                    break;
            }

            final Call<SingleTeamOutput> responseLoginCallback = ServiceWrapper.getInstance().
                    getSingleUserTeams(url, mMyTeamInput);

            responseLoginCallback.enqueue(new Callback<SingleTeamOutput>() {
                @Override
                public void onResponse(Call<SingleTeamOutput> call, Response<SingleTeamOutput> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {
                            onResponseListener.onError(response.body().getMessage());
                            AppController.expirySession(response.body().getMessage());
                        } else if (response.body().getResponseCode() == 200) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onNotFound(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<SingleTeamOutput> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<JoinedContestOutput> getJoinedContests(JoinedContestInput mJoinedContestInput,
                                                       final OnResponseMyMatchesListener onResponseListener) {
        if (APIValidate.isMyMatchesListValid(mJoinedContestInput, onResponseListener)) {
            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "contest/getContests";
                    break;
                case 2:
                    url = "football/contest/getContests";
                    break;
            }
            final Call<JoinedContestOutput> responseLoginCallback =
                    ServiceWrapper.getInstance().getJoinedContests(url, mJoinedContestInput);

            responseLoginCallback.enqueue(new Callback<JoinedContestOutput>() {
                @Override
                public void onResponse(Call<JoinedContestOutput> call, Response<JoinedContestOutput> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {

                        if (response.body().getResponseCode() == 502) {
                            onResponseListener.onError(response.body().getMessage());

                            AppController.expirySession(response.body().getMessage());
                        } else if (response.body().getResponseCode() == 200) {


                            onResponseListener.onSuccess(response.body());

                        } else if (response.body().getResponseCode() == 501) {
                            onResponseListener.onError(response.body().getMessage());
                        } else {
                            onResponseListener.onNotFound(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JoinedContestOutput> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;

    }

    @Override
    public Call<LoginResponseOut> viewProfile(LoginInput mLoginInput, final OnResponseListener onResponseListener) {
        if (APIValidate.isViewProfileDataValid(mLoginInput, onResponseListener)) {

            final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().viewProfileCall(mLoginInput);

            responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
                @Override
                public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {

                            onResponseListener.onError(response.body().getMessage());

                            AppUtils.logoutSession();
                        } else if (response.body().getResponseCode() == 200) {

                            onResponseListener.onSuccess(response.body());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<ResponseBanner> bannersList(LoginInput mLoginInput, final OnResponseBannerListener onResponseListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "admin/config/bannerList";
                break;
            case 2:
                url = "football/admin/config/bannerList";
                break;
        }
        final Call<ResponseBanner> responseLoginCallback = ServiceWrapper.getInstance().bannersList(url, mLoginInput);
        responseLoginCallback.enqueue(new Callback<ResponseBanner>() {
            @Override
            public void onResponse(Call<ResponseBanner> call, Response<ResponseBanner> response) {

                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;


                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getResponseCode() == 502) {

                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());

                        AppUtils.logoutSession();

                    } else if (response.body().getResponseCode() == 200) {
                        // AppUtils.logoutSession();

                        if (!response.body().getData().getClass().equals(ArrayList.class)) {


                            onResponseListener.onSuccess(response.body());


                        } else {
                            onResponseListener.onNotFound(response.body().getMessage());
                        }

                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBanner> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }

    @Override
    public Call<MatchDetailOutPut> matchDetail(MatchDetailInput mMatchDetailInput,
                                               final OnResponseMatchDetailsListener onResponseListener) {
        if (APIValidate.isMatchesDetailsValid(mMatchDetailInput, onResponseListener)) {

            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "sports/getMatch";
                    break;
                case 2:
                    url = "football/sports/getMatch";
                    break;
            }

            final Call<MatchDetailOutPut> responseLoginCallback = ServiceWrapper.getInstance().matchDetail(url, mMatchDetailInput);

            responseLoginCallback.enqueue(new Callback<MatchDetailOutPut>() {
                @Override
                public void onResponse(Call<MatchDetailOutPut> call, Response<MatchDetailOutPut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;


                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {

                            onResponseListener.onError(response.body().getMessage());

                            AppUtils.logoutSession();
                        } else if (response.body().getResponseCode() == 200) {
                            //AppController.expirySession(response.body().getMessage());

                            //onResponseListener.onSuccess(response.body());

                            if (!response.body().getData().getClass().equals(ArrayList.class)) {

                                Gson gson = new Gson();

                                String responceString = AppUtils.gsonToJSON(response.body());

                                MatchDetailOutPut mMatchDetailOutPut = gson.fromJson(responceString, MatchDetailOutPut.class);

                                onResponseListener.onSuccess(mMatchDetailOutPut);
                            }

                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MatchDetailOutPut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<WalletOutputBean> viewAccount(WalletInput mWalletInput, final IUserInteractor.OnResponseAccountListener onResponseListener) {
        if (APIValidate.isSessionValid(mWalletInput.getSessionKey(), onResponseListener)) {

            final Call<WalletOutputBean> responseLoginCallback = ServiceWrapper.getInstance().getWallet(mWalletInput);

            responseLoginCallback.enqueue(new Callback<WalletOutputBean>() {
                @Override
                public void onResponse(Call<WalletOutputBean> call, Response<WalletOutputBean> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {

                        if (response.body().getResponseCode() == 502) {

                            onResponseListener.onError(response.body().getMessage());

                            AppUtils.logoutSession();
                        } else if (response.body().getResponseCode() == 200) {

                            if (!response.body().getData().getClass().equals(ArrayList.class)) {

                                Gson gson = new Gson();

                                String responceString = AppUtils.gsonToJSON(response.body());

                                WalletOutputBean mWalletOutputBean = gson.fromJson(responceString, WalletOutputBean.class);

                                onResponseListener.onSuccess(mWalletOutputBean);
                            } else {
                                onResponseListener.onError(response.body().getMessage());
                            }


                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }


                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<WalletOutputBean> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<DefaultRespose> updateProfile(UpdateProfileInput mUpdateProfileInput, final OnResponseUpdateProfileListener mOnResponseUpdateProfileListener) {
        final Call<DefaultRespose> responseUpdateProfileCall = ServiceWrapper.getInstance().
                updateProfileCall(mUpdateProfileInput);

        responseUpdateProfileCall.enqueue(new Callback<DefaultRespose>() {
            @Override
            public void onResponse(Call<DefaultRespose> call, Response<DefaultRespose> response) {

                if (responseUpdateProfileCall == null || responseUpdateProfileCall.isCanceled())
                    return;

                if (response.body() != null) {
                    if (response.body().getResponseCode() == 502) {

                        mOnResponseUpdateProfileListener.onError(response.body().getMessage());

                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 200) {


                        if (!response.body().getData().getClass().equals(ArrayList.class)) {

                            Gson gson = new Gson();

                            String responceString = AppUtils.gsonToJSON(response.body());

                            ResponseUpdateProfile mResponseUpdateProfile = gson.fromJson(responceString, ResponseUpdateProfile.class);

                            mOnResponseUpdateProfileListener.onSuccess(mResponseUpdateProfile);

                        } else {
                            mOnResponseUpdateProfileListener.onError(response.body().getMessage());
                        }

                    } else {
                        mOnResponseUpdateProfileListener.onError(response.body().getMessage());
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    mOnResponseUpdateProfileListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                }

            }

            @Override
            public void onFailure(Call<DefaultRespose> call, Throwable t) {

                mOnResponseUpdateProfileListener.onError(Constant.EXCEPTION_MESSAGE);

            }
        });

        return responseUpdateProfileCall;
    }


    @Override
    public Call<MatchContestOutPut> getContestsByType(MatchContestInput mMatchContestInput, final OnResponseContestListener onResponseListener) {
        if (APIValidate.isContestListValid(mMatchContestInput, onResponseListener)) {
            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "contest/getContestsByType";
                    break;
                case 2:
                    url = "football/contest/getContestsByType";
                    break;
            }

            final Call<MatchContestOutPut> responseLoginCallback = ServiceWrapper.getInstance().
                    getContestsByType(url, mMatchContestInput);

            responseLoginCallback.enqueue(new Callback<MatchContestOutPut>() {
                @Override
                public void onResponse(Call<MatchContestOutPut> call, Response<MatchContestOutPut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {

                            AppUtils.logoutSession();

                        } else if (response.body().getResponseCode() == 200) {

                            onResponseListener.onSuccess(response.body());

                        } else {
                            onResponseListener.onNotFound(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MatchContestOutPut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<MatchContestOutPut> getJoinedContestsByType(JoinedContestInput mMatchContestInput, final OnResponseContestListener onResponseListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "contest/getContestsByType";
                break;
            case 2:
                url = "football/contest/getContestsByType";
                break;
        }
        final Call<MatchContestOutPut> responseLoginCallback = ServiceWrapper.getInstance().getjoinedContestsByType(url, mMatchContestInput);
        responseLoginCallback.enqueue(new Callback<MatchContestOutPut>() {
            @Override
            public void onResponse(Call<MatchContestOutPut> call, Response<MatchContestOutPut> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onNotFound(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchContestOutPut> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;
                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }

    @Override
    public Call<AllContestOutPut> allContestListing(MatchContestInput mMatchContestInput, final OnResponseAllContestsListener
            onResponseListener) {
        if (APIValidate.isAllContestListValid(mMatchContestInput, onResponseListener)) {
            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "contest/getContests";
                    break;
                case 2:
                    url = "football/contest/getContests";
                    break;
            }
            final Call<AllContestOutPut> responseLoginCallback = ServiceWrapper.getInstance().
                    getAllContests(url, mMatchContestInput);

            responseLoginCallback.enqueue(new Callback<AllContestOutPut>() {
                @Override
                public void onResponse(Call<AllContestOutPut> call, Response<AllContestOutPut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {

                            AppUtils.logoutSession();

                        } else if (response.body().getResponseCode() == 200) {
                            if (response.body().getData().getTotalRecords() == 0) {
                                onResponseListener.onNotFound(AppUtils.getStrFromRes(R.string.contestNotFound));
                            } else {
                                onResponseListener.onSuccess(response.body());
                            }

                        } else {
                            onResponseListener.onNotFound(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<AllContestOutPut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;

        }

        return null;
    }


    @Override
    public Call<SpinWheelOutput> getSpinWheelData(LoginInput mMatchContestInput, final OnResponseSpinWheeListener onResponseListener) {

        final Call<SpinWheelOutput> responseLoginCallback = ServiceWrapper.getInstance().
                getSpinWheelData(mMatchContestInput);

        responseLoginCallback.enqueue(new Callback<SpinWheelOutput>() {
            @Override
            public void onResponse(Call<SpinWheelOutput> call, Response<SpinWheelOutput> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("502")) {

                        AppUtils.logoutSession();

                    } else if (response.body().getResponseCode().equalsIgnoreCase("200")) {
                        if (response.body().getData().getRecords().size() == 0) {
                            onResponseListener.onNotFound(AppUtils.getStrFromRes(R.string.contestNotFound));
                        } else {
                            onResponseListener.onSuccess(response.body());
                        }

                    } else {
                        onResponseListener.onNotFound(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SpinWheelOutput> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }


    @Override
    public Call<PopupData> getPopupData(LoginInput mMatchContestInput, final OnResponsePopupListener onResponseListener) {

        final Call<PopupData> responseLoginCallback = ServiceWrapper.getInstance().
                getPopupData(mMatchContestInput);

        responseLoginCallback.enqueue(new Callback<PopupData>() {
            @Override
            public void onResponse(Call<PopupData> call, Response<PopupData> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("502")) {

                        AppUtils.logoutSession();

                    } else if (response.body().getResponseCode().equalsIgnoreCase("200")) {
                        if (response.body().getData().getRecords() == null) {
                            onResponseListener.onNotFound(AppUtils.getStrFromRes(R.string.contestNotFound));
                        } else {
                            onResponseListener.onSuccess(response.body());
                        }

                    } else {
                        onResponseListener.onNotFound(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<PopupData> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }


    @Override
    public Call<PopupData> updateData(LoginInput mMatchContestInput, final OnResponsePopupListener onResponseListener) {

        final Call<PopupData> responseLoginCallback = ServiceWrapper.getInstance().
                updateData(mMatchContestInput);

        responseLoginCallback.enqueue(new Callback<PopupData>() {
            @Override
            public void onResponse(Call<PopupData> call, Response<PopupData> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("502")) {

                        AppUtils.logoutSession();

                    } else if (response.body().getResponseCode().equalsIgnoreCase("200")) {
                        if (response.body().getData().getRecords() == null) {
                            onResponseListener.onNotFound(AppUtils.getStrFromRes(R.string.contestNotFound));
                        } else {
                            onResponseListener.onSuccess(response.body());
                        }

                    } else {
                        onResponseListener.onNotFound(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<PopupData> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }


    @Override
    public Call<UpdateSpinWheelOutput> updateWinningSpinWheelData(LoginInput mMatchContestInput, final OnResponseUpdateSpinWheeListener onResponseListener) {

        final Call<UpdateSpinWheelOutput> responseLoginCallback = ServiceWrapper.getInstance().
                updateWinningSpinWheelData(mMatchContestInput);

        responseLoginCallback.enqueue(new Callback<UpdateSpinWheelOutput>() {
            @Override
            public void onResponse(Call<UpdateSpinWheelOutput> call, Response<UpdateSpinWheelOutput> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("502")) {

                        AppUtils.logoutSession();

                    } else if (response.body().getResponseCode().equalsIgnoreCase("200")) {

                        onResponseListener.onSuccess(response.body());


                    } else {
                        onResponseListener.onNotFound(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateSpinWheelOutput> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }


    @Override
    public Call<GetSettingsOutput> getSettings(final GetSettingsResponseListener getSettingsResponseListener) {
        final Call<GetSettingsOutput> settings = ServiceWrapper.getInstance().getSettings();
        settings.enqueue(new Callback<GetSettingsOutput>() {
            @Override
            public void onResponse(Call<GetSettingsOutput> call, Response<GetSettingsOutput> response) {
                if (settings == null || settings.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        getSettingsResponseListener.onSuccess(response.body().getData());

                    } else {
                        getSettingsResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        getSettingsResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        getSettingsResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }

            }

            @Override
            public void onFailure(Call<GetSettingsOutput> call, Throwable t) {
                if (settings == null || settings.isCanceled()) return;
                getSettingsResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return settings;
    }

    @Override
    public Call<SubjectOutput> getSubject(final OnResponseSubjectListener onResponseListener) {

        final Call<SubjectOutput> responseLoginCallback = ServiceWrapper.getInstance().
                getSubject();

        responseLoginCallback.enqueue(new Callback<SubjectOutput>() {
            @Override
            public void onResponse(Call<SubjectOutput> call, Response<SubjectOutput> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("502")) {

                        AppUtils.logoutSession();

                    } else if (response.body().getResponseCode().equalsIgnoreCase("200")) {

                        onResponseListener.onSuccess(response.body());


                    } else {
                        onResponseListener.onNotFound(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SubjectOutput> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }


    @Override
    public Call<MyContestMatchesOutput> myContestMatchesList(MyContestMatchesInput myContestMatchesInput, final OnResponseMyContestListener onResponseListener) {
        String url = "";
        if (AppSession.getInstance().getPlayMode() == 2) {
            url = "snakeDrafts/getMatches";
        } else {
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "sports/getMatches";
                    break;
                case 2:
                    url = "football/sports/getMatches";
                    break;
            }
        }
        final Call<MyContestMatchesOutput> responseMyContestMatchesOutputCall = ServiceWrapper.getInstance().myContestMatchesList(url, myContestMatchesInput);
        responseMyContestMatchesOutputCall.enqueue(new Callback<MyContestMatchesOutput>() {
            @Override
            public void onResponse(Call<MyContestMatchesOutput> call, Response<MyContestMatchesOutput> response) {

                if (responseMyContestMatchesOutputCall == null || responseMyContestMatchesOutputCall.isCanceled())
                    return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {

                        AppUtils.logoutSession();

                    } else if (response.body().getResponseCode() == 200) {
                        if (response.body().getData().getTotalRecords() == 0) {
                            onResponseListener.onNotFound(AppUtils.getStrFromRes(R.string.contestNotFound));
                        } else {
                            onResponseListener.onSuccess(response.body());
                        }

                    } else {
                        onResponseListener.onNotFound(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }

            }

            @Override
            public void onFailure(Call<MyContestMatchesOutput> call, Throwable t) {
                if (responseMyContestMatchesOutputCall == null || responseMyContestMatchesOutputCall.isCanceled())
                    return;

                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });

        return responseMyContestMatchesOutputCall;
    }

    @Override
    public Call<ContestDetailOutput> getContest(ContestDetailInput mMatchContestInput, final OnResponseContestDetailsListener onResponseListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "contest/getContest";
                break;
            case 2:
                url = "football/contest/getContest";
                break;
        }
        final Call<ContestDetailOutput> responseLoginCallback = ServiceWrapper.getInstance().getContest(url, mMatchContestInput);

        responseLoginCallback.enqueue(new Callback<ContestDetailOutput>() {
            @Override
            public void onResponse(Call<ContestDetailOutput> call, Response<ContestDetailOutput> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {

                        AppUtils.logoutSession();

                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ContestDetailOutput> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }

    @Override
    public Call<ContestUserOutput> getJoinedContestsUsers(ContestUserInput mContestUserInput, final OnResponseRanksListener onResponseListener) {
        if (APIValidate.isRankListValid(mContestUserInput, onResponseListener)) {
            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "contest/getJoinedContestsUsers";
                    break;
                case 2:
                    url = "football/contest/getJoinedContestsUsers";
                    break;
            }
            final Call<ContestUserOutput> responseLoginCallback = ServiceWrapper.getInstance().getJoinedContestsUsers(url, mContestUserInput);

            responseLoginCallback.enqueue(new Callback<ContestUserOutput>() {
                @Override
                public void onResponse(Call<ContestUserOutput> call, Response<ContestUserOutput> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {

                        if (response.body().getResponseCode() == 502) {

                            AppUtils.logoutSession();

                        } else if (response.body().getResponseCode() == 200) {

                            if (response.body().getData().getRecords() == null) {
                                onResponseListener.onNotFound("Users not found");
                            } else {
                                onResponseListener.onSuccess(response.body());
                            }

                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ContestUserOutput> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<PlayersOutput> getPlayers(PlayersInput mPlayersInput, final OnResponseMatchPlayersListener onResponseListener) {
        if (APIValidate.isMatchPlayersValid(mPlayersInput, onResponseListener)) {
            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "sports/getPlayers";
                    break;
                case 2:
                    url = "football/sports/getPlayers";
                    break;
            }
            final Call<PlayersOutput> responseLoginCallback = ServiceWrapper.getInstance().getPlayers(url, mPlayersInput);

            responseLoginCallback.enqueue(new Callback<PlayersOutput>() {
                @Override
                public void onResponse(Call<PlayersOutput> call, Response<PlayersOutput> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {

                            onResponseListener.onError(response.body().getMessage());
                            AppController.expirySession(response.body().getMessage());

                        } else if (response.body().getResponseCode() == 200) {


                            if (response.body().getData().getRecords() == null) {
                                onResponseListener.onNotFound("Players not found");
                            } else {
                                onResponseListener.onSuccess(response.body());
                            }
                        } else {
                            onResponseListener.onNotFound(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<PlayersOutput> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<LoginResponseOut> addUserTeam(CreateTeamInput mCreateTeamInput, final OnResponseListener onResponseListener) {
        if (APIValidate.isCreateTeamDataValid(mCreateTeamInput, onResponseListener)) {
            JSONArray jsonArray = null;
            try {
                //jsonArray = new JSONArray(new Gson().toJson(playerId));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "contest/addUserTeam";
                    break;
                case 2:
                    url = "football/contest/addUserTeam";
                    break;
            }
            final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().addUserTeam(url, mCreateTeamInput);

            responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
                @Override
                public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {

                            onResponseListener.onError(response.body().getMessage());
                            AppController.expirySession(response.body().getMessage());

                        } else if (response.body().getResponseCode() == 200) {
                            AppController.expirySession(response.body().getMessage());
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<LoginResponseOut> editUserTeam(CreateTeamInput mCreateTeamInput, final OnResponseListener onResponseListener) {
        if (APIValidate.isCreateTeamDataValid(mCreateTeamInput, onResponseListener)) {
            JSONArray jsonArray = null;
            try {
                //jsonArray = new JSONArray(new Gson().toJson(playerId));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "contest/editUserTeam";
                    break;
                case 2:
                    url = "football/contest/editUserTeam";
                    break;
            }

            final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().editUserTeam(url, mCreateTeamInput);

            responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
                @Override
                public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {

                            onResponseListener.onError(response.body().getMessage());
                            AppController.expirySession(response.body().getMessage());

                        } else if (response.body().getResponseCode() == 200) {
                            AppController.expirySession(response.body().getMessage());
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }


    @Override
    public Call<MyTeamOutput> teamList(MyTeamInput mMyTeamInput, final OnResponseTeamsListener onResponseListener) {
        if (APIValidate.isTeamListValid(mMyTeamInput, onResponseListener)) {
            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "contest/getUserTeams";
                    break;
                case 2:
                    url = "football/contest/getUserTeams";
                    break;
            }
            final Call<MyTeamOutput> responseLoginCallback = ServiceWrapper.getInstance().
                    getUserTeams(url, mMyTeamInput);

            responseLoginCallback.enqueue(new Callback<MyTeamOutput>() {
                @Override
                public void onResponse(Call<MyTeamOutput> call, Response<MyTeamOutput> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {
                            onResponseListener.onError(response.body().getMessage());
                            AppController.expirySession(response.body().getMessage());
                        } else if (response.body().getResponseCode() == 200) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onNotFound(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MyTeamOutput> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }


    @Override
    public Call<JoinContestOutput> joinContest(JoinContestInput mJoinContestInput, final OnResponseJoinListener onResponseListener) {

        if (APIValidate.isJoinContestValid(mJoinContestInput, onResponseListener)) {
            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    if (mJoinContestInput.getJoin().equalsIgnoreCase("1")) {
                        url = "contest/joinInvite";
                    } else {
                        url = "contest/join";
                    }
                    break;
                case 2:
                    if (mJoinContestInput.getJoin().equalsIgnoreCase("1")) {
                        url = "football/contest/joinInvite";
                    } else {
                        url = "football/contest/join";
                    }
                    break;
            }
            final Call<JoinContestOutput> responseLoginCallback = ServiceWrapper.getInstance().
                    joinContest(url, mJoinContestInput);
            responseLoginCallback.enqueue(new Callback<JoinContestOutput>() {
                @Override
                public void onResponse(Call<JoinContestOutput> call, Response<JoinContestOutput> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {
                            AppController.expirySession(response.body().getMessage());
                            onResponseListener.onError(response.body().getMessage());
                        } else if (response.body().getResponseCode() == 200) {
                            onResponseListener.onSuccess(response.body());
                        } else if (response.body().getResponseCode() == 500 && response.body().getMessage().equals("Insufficient wallet amount.")) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JoinContestOutput> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }


    @Override
    public Call<LoginResponseOut> verifyPhoneNumber(VerifyMobileInput mobileInput, final OnResponseListener onResponseListener) {
        if (APIValidate.isOTPValid(mobileInput.getOTP(), onResponseListener)) {
            final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().verifyPhoneNumber(mobileInput);

            responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
                @Override
                public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response != null && response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {

                            AppController.expirySession(response.body().getMessage());
                            onResponseListener.onError(response.body().getMessage());


                        } else if (response.body().getResponseCode() == 200 || response.body().getResponseCode() == 201) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response != null && response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;

    }

    @Override
    public Call<LoginResponseOut> resendverify(VerifyMobileInput mobileInput, final OnResponseListener onResponseListener) {
        final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().resendverify(mobileInput);
        responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
            @Override
            public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
//                            MyApplication.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());

                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;

    }


    @Override
    public Call<LoginResponseOut> sendMobileOtp(VerifyMobileInput mobileInput, final OnResponseListener onResponseListener) {

        final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().sendMobileOtp(mobileInput);
        responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
            @Override
            public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
//                            MyApplication.expirySession(response.body().getMessage());
//                            onResponseListener.onError(response.body().getMessage());
                        AppUtils.logoutSession();
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;

    }

    @Override
    public Call<LoginResponseOut> uploadImage(UploadImageInput uploadImageInput, final OnResponseListener onResponseListener) {
        if (APIValidate.isPanDataValid(uploadImageInput, onResponseListener)) {
            File file = new File(uploadImageInput.getFilePath());
            MultipartBody.Part multiPartBody = null;
            if (file != null && file.exists()) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                multiPartBody = MultipartBody.Part.createFormData("File", file.getName(), requestFile);
            }
            RequestBody userLoginSessionKeyRequestBody = RequestBody.create(MultipartBody.FORM, uploadImageInput.getSessionKey());
            RequestBody sectionRequestBody = RequestBody.create(MultipartBody.FORM, uploadImageInput.getSection());
            RequestBody mediaCaptionRequestBody = RequestBody.create(MultipartBody.FORM, uploadImageInput.getMediaCaption());


            Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().uploadImage(
                    userLoginSessionKeyRequestBody
                    , sectionRequestBody
                    , mediaCaptionRequestBody
                    , multiPartBody);

            responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
                @Override
                public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {

                    if (response.body() != null && response.isSuccessful()) {

                        if (response.body().getResponseCode() == 502) {
                            onResponseListener.onError(response.body().getMessage());
                            AppController.expirySession(response.body().getMessage());
                            AppUtils.logoutSession();
                        } else if (response.body().getResponseCode() == 200) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<DreamTeamOutput> allPlayersScore(DreamTeamInput dreamTeamInput,
                                                 final OnResponseDreamTeamsListener onResponseListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "sports/getMatchBestPlayers";
                break;
            case 2:
                url = "football/sports/getMatchBestPlayers";
                break;
        }

        final Call<DreamTeamOutput> responseLoginCallback = ServiceWrapper.getInstance().
                dreamTeam(url, dreamTeamInput);

        responseLoginCallback.enqueue(new Callback<DreamTeamOutput>() {
            @Override
            public void onResponse(Call<DreamTeamOutput> call, Response<DreamTeamOutput> response) {


                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) {

                    onResponseListener.onError(Constant.NOT_GETTING_RESPONSE);
                    return;
                }

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getResponseCode() == 502) {
                        onResponseListener.onError(response.body().getMessage());
                        AppController.expirySession(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onNotFound(response.body().getMessage());
                    }
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DreamTeamOutput> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;


    }


    @Override
    public Call<DreamTeamOutput> auctionallPlayersScore(DreamTeamInput dreamTeamInput,
                                                        final OnResponseDreamTeamsListener onResponseListener) {


        final Call<DreamTeamOutput> responseLoginCallback = ServiceWrapper.getInstance().
                auctionallPlayersScore(dreamTeamInput);

        responseLoginCallback.enqueue(new Callback<DreamTeamOutput>() {
            @Override
            public void onResponse(Call<DreamTeamOutput> call, Response<DreamTeamOutput> response) {


                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) {

                    onResponseListener.onError(Constant.NOT_GETTING_RESPONSE);
                    return;
                }

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getResponseCode() == 502) {
                        onResponseListener.onError(response.body().getMessage());
                        AppController.expirySession(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onNotFound(response.body().getMessage());
                    }
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DreamTeamOutput> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;

    }

    @Override
    public Call<CreateContestOutput> createContest(CreateContestInput createContestInput, final OnResponseCreateContestListener onResponseListener) {
        if (APIValidate.isCreateContestDataValid(createContestInput, onResponseListener)) {
            try {
                String url = "";
                switch (AppSession.getInstance().getGameType()) {
                    case 1:
                        url = "contest/add";
                        break;
                    case 2:
                        url = "football/contest/add";
                        break;
                }
                final Call<CreateContestOutput> responseLoginCallback = ServiceWrapper.getInstance().createContest(url, createContestInput);

                responseLoginCallback.enqueue(new Callback<CreateContestOutput>() {
                    @Override
                    public void onResponse(Call<CreateContestOutput> call, Response<CreateContestOutput> response) {
                        if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                            return;

                        if (response.body() != null && response.isSuccessful()) {
                            if (response.body().getResponseCode() == 502) {
                                AppController.expirySession(response.body().getMessage());
                                onResponseListener.onError(response.body().getMessage());
                            } else if (response.body().getResponseCode() == 200) {
                                onResponseListener.onSuccess(response.body());
                            } else {
                                onResponseListener.onError(response.body().getMessage());
                            }
                        } else {
                            if (response.errorBody() != null) {
                                APIError error = ErrorUtils.parseError(response);
                                onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                            } else {
                                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateContestOutput> call, Throwable t) {
                        if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                            return;

                        if (t != null)
                            onResponseListener.onError(t.getMessage() + "");
                        else
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                });
                return responseLoginCallback;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public Call<AuctionContestCreateOutput> auctioncreateContest(CreateContestInput createContestInput, final OnResponseAuctionCreateContestListener onResponseListener) {
        if (APIValidate.auction(createContestInput, onResponseListener)) {
            try {
                final Call<AuctionContestCreateOutput> responseLoginCallback = ServiceWrapper.getInstance().auctioncreateContest(createContestInput);

                responseLoginCallback.enqueue(new Callback<AuctionContestCreateOutput>() {
                    @Override
                    public void onResponse(Call<AuctionContestCreateOutput> call, Response<AuctionContestCreateOutput> response) {
                        if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                            return;

                        if (response.body() != null && response.isSuccessful()) {
                            if (response.body().getResponseCode().equalsIgnoreCase("502")) {
                                AppController.expirySession(response.body().getMessage());
                                onResponseListener.onError(response.body().getMessage());
                            } else if (response.body().getResponseCode().equalsIgnoreCase("200")) {
                                onResponseListener.onSuccess(response.body());
                            } else {
                                onResponseListener.onError(response.body().getMessage());
                            }
                        } else {
                            if (response.errorBody() != null) {
                                APIError error = ErrorUtils.parseError(response);
                                onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                            } else {
                                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AuctionContestCreateOutput> call, Throwable t) {
                        if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                            return;

                        if (t != null)
                            onResponseListener.onError(t.getMessage() + "");
                        else
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                });
                return responseLoginCallback;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public Call<AuctionContestCreateOutput> createPrivateSnakeContest(CreateContestInput createContestInput, final OnResponseAuctionCreateContestListener onResponseListener) {
        if (APIValidate.auction(createContestInput, onResponseListener)) {
            try {
                final Call<AuctionContestCreateOutput> responseLoginCallback = ServiceWrapper.getInstance().createPrivateSnakeContest(createContestInput);

                responseLoginCallback.enqueue(new Callback<AuctionContestCreateOutput>() {
                    @Override
                    public void onResponse(Call<AuctionContestCreateOutput> call, Response<AuctionContestCreateOutput> response) {
                        if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                            return;

                        if (response.body() != null && response.isSuccessful()) {
                            if (response.body().getResponseCode().equalsIgnoreCase("502")) {
                                AppController.expirySession(response.body().getMessage());
                                onResponseListener.onError(response.body().getMessage());
                            } else if (response.body().getResponseCode().equalsIgnoreCase("200")) {
                                onResponseListener.onSuccess(response.body());
                            } else {
                                onResponseListener.onError(response.body().getMessage());
                            }
                        } else {
                            if (response.errorBody() != null) {
                                APIError error = ErrorUtils.parseError(response);
                                onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                            } else {
                                onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AuctionContestCreateOutput> call, Throwable t) {
                        if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                            return;

                        if (t != null)
                            onResponseListener.onError(t.getMessage() + "");
                        else
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                });
                return responseLoginCallback;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Call<GetPlayerRoundOutput> getPlayersRound(CreateContestInput createContestInput, final OnResponseDraftCreateContestListener onResponseListener) {

        try {
            final Call<GetPlayerRoundOutput> responseLoginCallback = ServiceWrapper.getInstance().getPlayersRound(createContestInput);

            responseLoginCallback.enqueue(new Callback<GetPlayerRoundOutput>() {
                @Override
                public void onResponse(Call<GetPlayerRoundOutput> call, Response<GetPlayerRoundOutput> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                        return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode().equalsIgnoreCase("502")) {
                            AppController.expirySession(response.body().getMessage());
                            onResponseListener.onError(response.body().getMessage());
                        } else if (response.body().getResponseCode().equalsIgnoreCase("200")) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetPlayerRoundOutput> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                        return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public Call<LoginResponseOut> checkPhonePeTransactionStatus(CheckPhonePeTransactionStatusInput checkPhonePeTransactionStatusInput, final checkPhonePeTransactionStatusResponseListener checkPhonePeTransactionStatusResponseListener) {
        final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().checkPhonePeTransactionStatus(checkPhonePeTransactionStatusInput);
        responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
            @Override
            public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                    return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode()==200){
                        checkPhonePeTransactionStatusResponseListener.onSuccess(response.body().getMessage());
                    }else {
                        checkPhonePeTransactionStatusResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        checkPhonePeTransactionStatusResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        checkPhonePeTransactionStatusResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                    return;
                if (t != null)
                    checkPhonePeTransactionStatusResponseListener.onError(t.getMessage() + "");
                else
                    checkPhonePeTransactionStatusResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }


    @Override
    public Call<ResponsePlayerFantasyStats> playerFantasyStats(PlayerFantasyStatsInput mPlayerFantasyStatsInput, final OnPlayerFantasyStatsListener onPlayerFantasyStatsListener) {

        String url = "";
        if (AppSession.getInstance().getPlayMode() == 2) {
            url = "sports/getPlayerFantasyStats";
        } else {
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "sports/getPlayerFantasyStats";
                    break;
                case 2:
                    url = "football/sports/getPlayerFantasyStats";
                    break;
            }

        }

        final Call<ResponsePlayerFantasyStats> responsePlayerFantasyStatsCallback = ServiceWrapper.getInstance().playerFantasyStats(url, mPlayerFantasyStatsInput);
        responsePlayerFantasyStatsCallback.enqueue(new Callback<ResponsePlayerFantasyStats>() {
            @Override
            public void onResponse(Call<ResponsePlayerFantasyStats> call, Response<ResponsePlayerFantasyStats> response) {

                if (responsePlayerFantasyStatsCallback == null || responsePlayerFantasyStatsCallback.isCanceled())
                    return;

                if (response != null && response.body() != null && response.isSuccessful()) {

                    if (response.body().getResponseCode().equals("502")) {
                        AppController.expirySession(response.body().getMessage());
                        onPlayerFantasyStatsListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode().equals("200")) {
                        onPlayerFantasyStatsListener.onSuccess(response.body());
                    } else if (response.body().getResponseCode().equals("500")) {
                        onPlayerFantasyStatsListener.onError(response.body().getMessage());
                    } else {
                        onPlayerFantasyStatsListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onPlayerFantasyStatsListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onPlayerFantasyStatsListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponsePlayerFantasyStats> call, Throwable t) {

                if (responsePlayerFantasyStatsCallback == null || responsePlayerFantasyStatsCallback.isCanceled())
                    return;

                if (t != null)
                    onPlayerFantasyStatsListener.onError(t.getMessage() + "");
                else
                    onPlayerFantasyStatsListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });

        return responsePlayerFantasyStatsCallback;
    }

    @Override
    public Call<LoginResponseOut> changePassword(ChangePasswordInput mChangePasswordInput, final OnResponseListener onResponseListener) {
        if (APIValidate.isChangePasswordDataValid(mChangePasswordInput, onResponseListener)) {

            final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().changePassword(mChangePasswordInput);

            responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
                @Override
                public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {
                            AppController.expirySession(response.body().getMessage());
                            onResponseListener.onError(response.body().getMessage());
                        } else if (response.body().getResponseCode() == 200) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<AvatarListOutput> users_icon_list(LoginInput loginInput, final OnAvatarResponseListener onResponseListener) {
        if (APIValidate.isSessionValid(loginInput, onResponseListener)) {
            final Call<AvatarListOutput> responseLoginCallback = ServiceWrapper.getInstance().getAvtars(loginInput);

            responseLoginCallback.enqueue(new Callback<AvatarListOutput>() {
                @Override
                public void onResponse(Call<AvatarListOutput> call, Response<AvatarListOutput> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response != null && response.body() != null && response.isSuccessful()) {

                        if (response.body().getResponseCode() == 502) {
                            AppController.expirySession(response.body().getMessage());
                            onResponseListener.onError(response.body().getMessage());
                        } else if (response.body().getResponseCode() == 200) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response != null && response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<AvatarListOutput> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<RankingOutput> getRankings(RankingInput rankingInput, final OnRankingListener onRankingListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "contest/getRankings";
                break;
            case 2:
                url = "football/contest/getRankings";
                break;
        }

        final Call<RankingOutput> responseOverallLeaderboardCall =
                ServiceWrapper.getInstance().getRankings(url, rankingInput);
        responseOverallLeaderboardCall.enqueue(new Callback<RankingOutput>() {
            @Override
            public void onResponse(Call<RankingOutput> call, Response<RankingOutput> response) {

                if (response != null && response.body() != null && response.isSuccessful()) {

                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onRankingListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onRankingListener.onSuccess(response.body());
                    } else {
                        onRankingListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onRankingListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onRankingListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }

            }

            @Override
            public void onFailure(Call<RankingOutput> call, Throwable t) {
                onRankingListener.onError(t.getMessage());
            }
        });


        return responseOverallLeaderboardCall;
    }

    @Override
    public Call<WinBreakupOutPut> winning_breakup(WinnerBreakupInput mWinnerBreakupInput, final OnResponseWinBreakUpListener onResponseListener) {
        try {
            String url = "";
            switch (AppSession.getInstance().getGameType()) {
                case 1:
                    url = "contest/WinningBreakups";
                    break;
                case 2:
                    url = "football/contest/WinningBreakups";
                    break;
            }
            final Call<WinBreakupOutPut> responseLoginCallback = ServiceWrapper.getInstance().
                    winning_breakup(url, mWinnerBreakupInput);

            responseLoginCallback.enqueue(new Callback<WinBreakupOutPut>() {
                @Override
                public void onResponse(Call<WinBreakupOutPut> call, Response<WinBreakupOutPut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                        return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {
                            AppController.expirySession(response.body().getMessage());
                            onResponseListener.onError(response.body().getMessage());
                        } else if (response.body().getResponseCode() == 200) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<WinBreakupOutPut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled())
                        return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Call<SeriesOutput> matchSeriesCall(SeriesInput seriesInput, final OnResponseMatchSeriesListener mOnResponseMatchSeriesListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "sports/getSeries";
                break;
            case 2:
                url = "football/sports/getSeries";
                break;
        }
        final Call<SeriesOutput> responseMatchSeries = ServiceWrapper.getInstance().matchSeries(url, seriesInput);
        responseMatchSeries.enqueue(new Callback<SeriesOutput>() {
            @Override
            public void onResponse(Call<SeriesOutput> call, Response<SeriesOutput> response) {

                if (responseMatchSeries == null || responseMatchSeries.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        mOnResponseMatchSeriesListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        mOnResponseMatchSeriesListener.onSuccess(response.body());
                    } else {
                        mOnResponseMatchSeriesListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        mOnResponseMatchSeriesListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        mOnResponseMatchSeriesListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }

            }

            @Override
            public void onFailure(Call<SeriesOutput> call, Throwable t) {
                mOnResponseMatchSeriesListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });


        return responseMatchSeries;
    }


    @Override
    public Call<GetAuctionSeriesOutput> getAuctionSeries(GetAuctionSeriesInput getAuctionSeriesInput, final OnGetAuctionSeriesResponseListener onGetAuctionSeriesResponseListener) {
        final Call<GetAuctionSeriesOutput> auctionSeries = ServiceWrapper.getInstance().getAuctionSeries(getAuctionSeriesInput);
        auctionSeries.enqueue(new Callback<GetAuctionSeriesOutput>() {
            @Override
            public void onResponse(Call<GetAuctionSeriesOutput> call, Response<GetAuctionSeriesOutput> response) {
                if (auctionSeries == null || auctionSeries.isCanceled()) {
                    onGetAuctionSeriesResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetAuctionSeriesResponseListener.onSuccess(response.body());
                    } else {
                        onGetAuctionSeriesResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetAuctionSeriesResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetAuctionSeriesResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAuctionSeriesOutput> call, Throwable t) {
                onGetAuctionSeriesResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return auctionSeries;
    }

    @Override
    public Call<GetAuctionSeriesOutput> getSnackDraftSeries(GetAuctionSeriesInput getAuctionSeriesInput, final OnGetAuctionSeriesResponseListener onGetAuctionSeriesResponseListener) {
        final Call<GetAuctionSeriesOutput> auctionSeries = ServiceWrapper.getInstance().getSnackDraftSeries(getAuctionSeriesInput);
        auctionSeries.enqueue(new Callback<GetAuctionSeriesOutput>() {
            @Override
            public void onResponse(Call<GetAuctionSeriesOutput> call, Response<GetAuctionSeriesOutput> response) {
                if (auctionSeries == null || auctionSeries.isCanceled()) {
                    onGetAuctionSeriesResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetAuctionSeriesResponseListener.onSuccess(response.body());
                    } else {
                        onGetAuctionSeriesResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetAuctionSeriesResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetAuctionSeriesResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAuctionSeriesOutput> call, Throwable t) {
                onGetAuctionSeriesResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return auctionSeries;
    }

    @Override
    public Call<GetAuctionPlayerOutput> getAuctionPlayers(GetAuctionPlayerInput getAuctionPlayerInput, final OnGetAuctionPlayersResponseListener onGetAuctionPlayersResponseListener) {
        final Call<GetAuctionPlayerOutput> auctionPlayers = ServiceWrapper.getInstance().getAuctionPlayers(getAuctionPlayerInput);
        auctionPlayers.enqueue(new Callback<GetAuctionPlayerOutput>() {
            @Override
            public void onResponse(Call<GetAuctionPlayerOutput> call, Response<GetAuctionPlayerOutput> response) {
                if (auctionPlayers == null || auctionPlayers.isCanceled()) {
                    onGetAuctionPlayersResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetAuctionPlayersResponseListener.onSuccess(response.body());
                    } else {
                        onGetAuctionPlayersResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetAuctionPlayersResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetAuctionPlayersResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAuctionPlayerOutput> call, Throwable t) {
                onGetAuctionPlayersResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });

        return auctionPlayers;
    }

    @Override
    public Call<GetAuctionPlayerOutput> getDraftPlayers(GetAuctionPlayerInput getAuctionPlayerInput, final OnGetAuctionPlayersResponseListener onGetAuctionPlayersResponseListener) {
        final Call<GetAuctionPlayerOutput> auctionPlayers = ServiceWrapper.getInstance().getDraftPlayers(getAuctionPlayerInput);
        auctionPlayers.enqueue(new Callback<GetAuctionPlayerOutput>() {
            @Override
            public void onResponse(Call<GetAuctionPlayerOutput> call, Response<GetAuctionPlayerOutput> response) {
                if (auctionPlayers == null || auctionPlayers.isCanceled()) {
                    onGetAuctionPlayersResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetAuctionPlayersResponseListener.onSuccess(response.body());
                    } else {
                        onGetAuctionPlayersResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetAuctionPlayersResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetAuctionPlayersResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAuctionPlayerOutput> call, Throwable t) {
                onGetAuctionPlayersResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });

        return auctionPlayers;
    }

    @Override
    public Call<GetAuctionJoinedUserOutput> getAuctionJoinedUsers(GetAuctionJoinedUserInput getAuctionJoinedUserInput, final OnGetAuctionJoinedUserResponseListener onGetAuctionJoinedUserResponseListener) {
        final Call<GetAuctionJoinedUserOutput> auctionJoinedUsers = ServiceWrapper.getInstance().getAuctionJoinedUsers(getAuctionJoinedUserInput);
        auctionJoinedUsers.enqueue(new Callback<GetAuctionJoinedUserOutput>() {
            @Override
            public void onResponse(Call<GetAuctionJoinedUserOutput> call, Response<GetAuctionJoinedUserOutput> response) {
                if (auctionJoinedUsers == null || auctionJoinedUsers.isCanceled()) {
                    onGetAuctionJoinedUserResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetAuctionJoinedUserResponseListener.onSuccess(response.body());
                    } else {
                        onGetAuctionJoinedUserResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetAuctionJoinedUserResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetAuctionJoinedUserResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAuctionJoinedUserOutput> call, Throwable t) {
                onGetAuctionJoinedUserResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return auctionJoinedUsers;
    }

    @Override
    public Call<GetAuctionJoinedUserOutput> getDraftJoinedUsers(GetAuctionJoinedUserInput getAuctionJoinedUserInput, final OnGetAuctionJoinedUserResponseListener onGetAuctionJoinedUserResponseListener) {
        final Call<GetAuctionJoinedUserOutput> auctionJoinedUsers = ServiceWrapper.getInstance().getDraftJoinedUsers(getAuctionJoinedUserInput);
        auctionJoinedUsers.enqueue(new Callback<GetAuctionJoinedUserOutput>() {
            @Override
            public void onResponse(Call<GetAuctionJoinedUserOutput> call, Response<GetAuctionJoinedUserOutput> response) {
                if (auctionJoinedUsers == null || auctionJoinedUsers.isCanceled()) {
                    onGetAuctionJoinedUserResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetAuctionJoinedUserResponseListener.onSuccess(response.body());
                    } else {
                        onGetAuctionJoinedUserResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetAuctionJoinedUserResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetAuctionJoinedUserResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAuctionJoinedUserOutput> call, Throwable t) {
                onGetAuctionJoinedUserResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return auctionJoinedUsers;
    }

    @Override
    public Call<AssistSwichOutPut> auctionAssistantTeamOnOff(AssistSwitchInput assistSwitchInput, final OnAuctionAssistantTeamOnOffResponseListener onAuctionAssistantTeamOnOffResponseListener) {
        final Call<AssistSwichOutPut> assistSwichOutPutCall = ServiceWrapper.getInstance().auctionAssistantTeamOnOff(assistSwitchInput);
        assistSwichOutPutCall.enqueue(new Callback<AssistSwichOutPut>() {
            @Override
            public void onResponse(Call<AssistSwichOutPut> call, Response<AssistSwichOutPut> response) {
                if (assistSwichOutPutCall == null || assistSwichOutPutCall.isCanceled()) {
                    onAuctionAssistantTeamOnOffResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onAuctionAssistantTeamOnOffResponseListener.onSuccess();
                    } else {
                        onAuctionAssistantTeamOnOffResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onAuctionAssistantTeamOnOffResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onAuctionAssistantTeamOnOffResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AssistSwichOutPut> call, Throwable t) {
                onAuctionAssistantTeamOnOffResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return assistSwichOutPutCall;
    }


    @Override
    public Call<AssistSwichOutPut> draftAssistantTeamOnOff(AssistSwitchInput assistSwitchInput, final OnAuctionAssistantTeamOnOffResponseListener onAuctionAssistantTeamOnOffResponseListener) {
        final Call<AssistSwichOutPut> assistSwichOutPutCall = ServiceWrapper.getInstance().draftAssistantTeamOnOff(assistSwitchInput);
        assistSwichOutPutCall.enqueue(new Callback<AssistSwichOutPut>() {
            @Override
            public void onResponse(Call<AssistSwichOutPut> call, Response<AssistSwichOutPut> response) {
                if (assistSwichOutPutCall == null || assistSwichOutPutCall.isCanceled()) {
                    onAuctionAssistantTeamOnOffResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onAuctionAssistantTeamOnOffResponseListener.onSuccess();
                    } else {
                        onAuctionAssistantTeamOnOffResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onAuctionAssistantTeamOnOffResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onAuctionAssistantTeamOnOffResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AssistSwichOutPut> call, Throwable t) {
                onAuctionAssistantTeamOnOffResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return assistSwichOutPutCall;
    }

    @Override
    public Call<GetPrivateContestOutput> getPrivateContest(GetPrivateContestInput getPrivateContestInput, final OnGetPrivateContestListener onGetPrivateContestListener) {
        final Call<GetPrivateContestOutput> privateContestCall = ServiceWrapper.getInstance().getPrivateContest(getPrivateContestInput);
        privateContestCall.enqueue(new Callback<GetPrivateContestOutput>() {
            @Override
            public void onResponse(Call<GetPrivateContestOutput> call, Response<GetPrivateContestOutput> response) {
                if (privateContestCall == null || privateContestCall.isCanceled())
                    return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetPrivateContestListener.onSuccess(response.body());
                    } else {
                        onGetPrivateContestListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetPrivateContestListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetPrivateContestListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPrivateContestOutput> call, Throwable t) {
                if (privateContestCall == null || privateContestCall.isCanceled())
                    return;

                if (t != null)
                    onGetPrivateContestListener.onError(t.getMessage() + "");
                else
                    onGetPrivateContestListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return privateContestCall;
    }

    @Override
    public Call<GetPrivateContestOutput> getAuctionPrivateContest(GetPrivateContestInput getPrivateContestInput, final OnGetAuctionPrivateContestListener onGetAuctionPrivateContestListener) {
        final Call<GetPrivateContestOutput> privateContestCall = ServiceWrapper.getInstance().getAuctionPrivateContest(getPrivateContestInput);
        privateContestCall.enqueue(new Callback<GetPrivateContestOutput>() {
            @Override
            public void onResponse(Call<GetPrivateContestOutput> call, Response<GetPrivateContestOutput> response) {
                if (privateContestCall == null || privateContestCall.isCanceled())
                    return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetAuctionPrivateContestListener.onSuccess(response.body());
                    } else {
                        onGetAuctionPrivateContestListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetAuctionPrivateContestListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetAuctionPrivateContestListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPrivateContestOutput> call, Throwable t) {
                if (privateContestCall == null || privateContestCall.isCanceled())
                    return;

                if (t != null)
                    onGetAuctionPrivateContestListener.onError(t.getMessage() + "");
                else
                    onGetAuctionPrivateContestListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return privateContestCall;
    }

    @Override
    public Call<CreateprivateSnakeDraftOutput> createPrivateSnakeDraft(CreateprivateSnakeDraftInput createprivateSnakeDraftInput, final OnCreatePrivateSnakeDraftListener onCreatePrivateSnakeDraftListener) {
        final Call<CreateprivateSnakeDraftOutput> privateSnakeDraft = ServiceWrapper.getInstance().createPrivateSnakeDraft(createprivateSnakeDraftInput);
        privateSnakeDraft.enqueue(new Callback<CreateprivateSnakeDraftOutput>() {
            @Override
            public void onResponse(Call<CreateprivateSnakeDraftOutput> call, Response<CreateprivateSnakeDraftOutput> response) {
                if (privateSnakeDraft == null || privateSnakeDraft.isCanceled())
                    return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onCreatePrivateSnakeDraftListener.onSuccess(response.body());
                    } else {
                        onCreatePrivateSnakeDraftListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onCreatePrivateSnakeDraftListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onCreatePrivateSnakeDraftListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateprivateSnakeDraftOutput> call, Throwable t) {
                if (privateSnakeDraft == null || privateSnakeDraft.isCanceled())
                    return;

                if (t != null)
                    onCreatePrivateSnakeDraftListener.onError(t.getMessage() + "");
                else
                    onCreatePrivateSnakeDraftListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return privateSnakeDraft;
    }


    @Override
    public Call<AddPlayerInAssistantOutput> addPlayerInAssistant(AddPlayerInAssistantInput addPlayerInAssistantInput,
                                                                 final OnAddPlayerInAssistantResponseListener onAddPlayerInAssistantResponseListener) {
        final Call<AddPlayerInAssistantOutput> addPlayerInAssistantOutputCall = ServiceWrapper.getInstance().addPlayerInAssistant(addPlayerInAssistantInput);
        addPlayerInAssistantOutputCall.enqueue(new Callback<AddPlayerInAssistantOutput>() {
            @Override
            public void onResponse(Call<AddPlayerInAssistantOutput> call, Response<AddPlayerInAssistantOutput> response) {
                if (addPlayerInAssistantOutputCall == null || addPlayerInAssistantOutputCall.isCanceled()) {
                    onAddPlayerInAssistantResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onAddPlayerInAssistantResponseListener.onSuccess(response.body());
                    } else {
                        onAddPlayerInAssistantResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onAddPlayerInAssistantResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onAddPlayerInAssistantResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AddPlayerInAssistantOutput> call, Throwable t) {
                onAddPlayerInAssistantResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return addPlayerInAssistantOutputCall;
    }

    @Override
    public Call<SimpleOutput> editPlayerInAssistant(EditPlayerInAssistantInput editPlayerInAssistantInput, final OnSimpleResponseListener onSimpleResponseListener) {
        final Call<SimpleOutput> simpleOutputCall = ServiceWrapper.getInstance().editPlayerInAssistant(editPlayerInAssistantInput);
        simpleOutputCall.enqueue(new Callback<SimpleOutput>() {
            @Override
            public void onResponse(Call<SimpleOutput> call, Response<SimpleOutput> response) {
                if (simpleOutputCall == null || simpleOutputCall.isCanceled()) {
                    onSimpleResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onSimpleResponseListener.onSuccess(response.body());
                    } else {
                        onSimpleResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onSimpleResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onSimpleResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SimpleOutput> call, Throwable t) {
                onSimpleResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return simpleOutputCall;
    }

    @Override
    public Call<AddPlayerInAssistantOutput> addPlayerInDraftAssistant(AddPlayerInAssistantInput addPlayerInAssistantInput, final OnAddPlayerInAssistantResponseListener onAddPlayerInAssistantResponseListener) {
        final Call<AddPlayerInAssistantOutput> addPlayerInAssistantOutputCall = ServiceWrapper.getInstance().addPlayerInDraftAssistant(addPlayerInAssistantInput);
        addPlayerInAssistantOutputCall.enqueue(new Callback<AddPlayerInAssistantOutput>() {
            @Override
            public void onResponse(Call<AddPlayerInAssistantOutput> call, Response<AddPlayerInAssistantOutput> response) {
                if (addPlayerInAssistantOutputCall == null || addPlayerInAssistantOutputCall.isCanceled()) {
                    onAddPlayerInAssistantResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onAddPlayerInAssistantResponseListener.onSuccess(response.body());
                    } else {
                        onAddPlayerInAssistantResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onAddPlayerInAssistantResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onAddPlayerInAssistantResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AddPlayerInAssistantOutput> call, Throwable t) {
                onAddPlayerInAssistantResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return addPlayerInAssistantOutputCall;
    }


    @Override
    public Call<SimpleOutput> editPlayerInDraftAssistant(EditPlayerInAssistantInput editPlayerInAssistantInput, final OnSimpleResponseListener onSimpleResponseListener) {
        final Call<SimpleOutput> simpleOutputCall = ServiceWrapper.getInstance().editPlayerInDraftAssistant(editPlayerInAssistantInput);
        simpleOutputCall.enqueue(new Callback<SimpleOutput>() {
            @Override
            public void onResponse(Call<SimpleOutput> call, Response<SimpleOutput> response) {
                if (simpleOutputCall == null || simpleOutputCall.isCanceled()) {
                    onSimpleResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onSimpleResponseListener.onSuccess(response.body());
                    } else {
                        onSimpleResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onSimpleResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onSimpleResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SimpleOutput> call, Throwable t) {
                onSimpleResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return simpleOutputCall;
    }

    @Override
    public Call<GetAuctionInfoOutput> getAuctionInfo(GetAuctionInfoInput getAuctionInfoInput, final OnGetAuctionInfoResponseListener onGetAuctionInfoResponseListener) {
        final Call<GetAuctionInfoOutput> getAuctionInfoOutputCall = ServiceWrapper.getInstance().getAuctionInfo(getAuctionInfoInput);
        getAuctionInfoOutputCall.enqueue(new Callback<GetAuctionInfoOutput>() {
            @Override
            public void onResponse(Call<GetAuctionInfoOutput> call, Response<GetAuctionInfoOutput> response) {
                if (getAuctionInfoOutputCall == null || getAuctionInfoOutputCall.isCanceled()) {
                    onGetAuctionInfoResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetAuctionInfoResponseListener.onSuccess(response.body());
                    } else {
                        onGetAuctionInfoResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetAuctionInfoResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetAuctionInfoResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAuctionInfoOutput> call, Throwable t) {
                onGetAuctionInfoResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return getAuctionInfoOutputCall;
    }


    @Override
    public Call<GetAuctionInfoOutput> getAuctionMatchInfo(GetAuctionInfoInput getAuctionInfoInput, final OnGetAuctionInfoResponseListener onGetAuctionInfoResponseListener) {
        final Call<GetAuctionInfoOutput> getAuctionInfoOutputCall = ServiceWrapper.getInstance().getAuctionMatchInfo(getAuctionInfoInput);
        getAuctionInfoOutputCall.enqueue(new Callback<GetAuctionInfoOutput>() {
            @Override
            public void onResponse(Call<GetAuctionInfoOutput> call, Response<GetAuctionInfoOutput> response) {
                if (getAuctionInfoOutputCall == null || getAuctionInfoOutputCall.isCanceled()) {
                    onGetAuctionInfoResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetAuctionInfoResponseListener.onSuccess(response.body());
                    } else {
                        onGetAuctionInfoResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetAuctionInfoResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetAuctionInfoResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAuctionInfoOutput> call, Throwable t) {
                onGetAuctionInfoResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return getAuctionInfoOutputCall;
    }

    @Override
    public Call<GetAuctionInfoOutput> getDraftInfo(GetAuctionInfoInput getAuctionInfoInput, final OnGetAuctionInfoResponseListener onGetAuctionInfoResponseListener) {
        final Call<GetAuctionInfoOutput> getAuctionInfoOutputCall = ServiceWrapper.getInstance().getDraftInfo(getAuctionInfoInput);
        getAuctionInfoOutputCall.enqueue(new Callback<GetAuctionInfoOutput>() {
            @Override
            public void onResponse(Call<GetAuctionInfoOutput> call, Response<GetAuctionInfoOutput> response) {
                if (getAuctionInfoOutputCall == null || getAuctionInfoOutputCall.isCanceled()) {
                    onGetAuctionInfoResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetAuctionInfoResponseListener.onSuccess(response.body());
                    } else {
                        onGetAuctionInfoResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetAuctionInfoResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetAuctionInfoResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAuctionInfoOutput> call, Throwable t) {
                onGetAuctionInfoResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return getAuctionInfoOutputCall;
    }

    @Override
    public Call<GetCurrentLiveAuctionPlayerOutput> getCurrentLiveAuctionPlayer(GetAuctionInfoInput getAuctionInfoInput, final OnGetCurrentLiveAuctionPlayerResponseListener onGetCurrentLiveAuctionPlayerResponseListener) {
        final Call<GetCurrentLiveAuctionPlayerOutput> getCurrentLiveAuctionPlayerOutputCall = ServiceWrapper.getInstance().getCurrentLiveAuctionPlayer(getAuctionInfoInput);
        getCurrentLiveAuctionPlayerOutputCall.enqueue(new Callback<GetCurrentLiveAuctionPlayerOutput>() {
            @Override
            public void onResponse(Call<GetCurrentLiveAuctionPlayerOutput> call, Response<GetCurrentLiveAuctionPlayerOutput> response) {
                if (getCurrentLiveAuctionPlayerOutputCall == null || getCurrentLiveAuctionPlayerOutputCall.isCanceled()) {
                    onGetCurrentLiveAuctionPlayerResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetCurrentLiveAuctionPlayerResponseListener.onSuccess(response.body());
                    } else {
                        onGetCurrentLiveAuctionPlayerResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetCurrentLiveAuctionPlayerResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetCurrentLiveAuctionPlayerResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCurrentLiveAuctionPlayerOutput> call, Throwable t) {
                onGetCurrentLiveAuctionPlayerResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return getCurrentLiveAuctionPlayerOutputCall;
    }


    @Override
    public Call<GetSeriesAuctionContestOutput> getSeriesAuctionContest(GetSeriesAuctionContestInput getSeriesAuctionContestInput, final OnGetSeriesAuctionContestListener onGetSeriesAuctionContestListener) {
        final Call<GetSeriesAuctionContestOutput> getSeriesAuctionContestCall = ServiceWrapper.getInstance().getSeriesAuctionContest(getSeriesAuctionContestInput);
        getSeriesAuctionContestCall.enqueue(new Callback<GetSeriesAuctionContestOutput>() {
            @Override
            public void onResponse(Call<GetSeriesAuctionContestOutput> call, Response<GetSeriesAuctionContestOutput> response) {
                if (getSeriesAuctionContestCall == null || getSeriesAuctionContestCall.isCanceled()) {
                    onGetSeriesAuctionContestListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetSeriesAuctionContestListener.onSuccess(response.body());
                    } else {
                        onGetSeriesAuctionContestListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetSeriesAuctionContestListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetSeriesAuctionContestListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSeriesAuctionContestOutput> call, Throwable t) {
                onGetSeriesAuctionContestListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return getSeriesAuctionContestCall;
    }

    @Override
    public Call<GetSeriesAuctionContestByTypeOutput> getSeriesAuctionContestByType(GetSeriesAuctionContestByTypeInput getSeriesAuctionContestByTypeInput, final OnGetSeriesAuctionContestByTypeListener onGetSeriesAuctionContestByTypeListener) {
        final Call<GetSeriesAuctionContestByTypeOutput> seriesAuctionContestByTypeCall = ServiceWrapper.getInstance().getSeriesAuctionContestByType(getSeriesAuctionContestByTypeInput);
        seriesAuctionContestByTypeCall.enqueue(new Callback<GetSeriesAuctionContestByTypeOutput>() {
            @Override
            public void onResponse(Call<GetSeriesAuctionContestByTypeOutput> call, Response<GetSeriesAuctionContestByTypeOutput> response) {
                if (seriesAuctionContestByTypeCall == null || seriesAuctionContestByTypeCall.isCanceled()) {
                    onGetSeriesAuctionContestByTypeListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetSeriesAuctionContestByTypeListener.onSuccess(response.body());
                    } else {
                        onGetSeriesAuctionContestByTypeListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetSeriesAuctionContestByTypeListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetSeriesAuctionContestByTypeListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSeriesAuctionContestByTypeOutput> call, Throwable t) {
                onGetSeriesAuctionContestByTypeListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return seriesAuctionContestByTypeCall;
    }

    @Override
    public Call<GetSeriesAuctionContestByTypeOutput> getSeriesDraftContestByType(GetSeriesAuctionContestByTypeInput getSeriesAuctionContestByTypeInput, final OnGetSeriesAuctionContestByTypeListener onGetSeriesAuctionContestByTypeListener) {
        final Call<GetSeriesAuctionContestByTypeOutput> seriesAuctionContestByTypeCall = ServiceWrapper.getInstance().getSeriesDraftContestByType(getSeriesAuctionContestByTypeInput);
        seriesAuctionContestByTypeCall.enqueue(new Callback<GetSeriesAuctionContestByTypeOutput>() {
            @Override
            public void onResponse(Call<GetSeriesAuctionContestByTypeOutput> call, Response<GetSeriesAuctionContestByTypeOutput> response) {
                if (seriesAuctionContestByTypeCall == null || seriesAuctionContestByTypeCall.isCanceled()) {
                    onGetSeriesAuctionContestByTypeListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetSeriesAuctionContestByTypeListener.onSuccess(response.body());
                    } else {
                        onGetSeriesAuctionContestByTypeListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetSeriesAuctionContestByTypeListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetSeriesAuctionContestByTypeListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSeriesAuctionContestByTypeOutput> call, Throwable t) {
                onGetSeriesAuctionContestByTypeListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return seriesAuctionContestByTypeCall;
    }

    @Override
    public Call<GetSeriesAuctionContestOutput> getSeriesDraftContest(GetSeriesAuctionContestInput getSeriesAuctionContestInput, final OnGetSeriesAuctionContestListener onGetSeriesAuctionContestListener) {
        final Call<GetSeriesAuctionContestOutput> getSeriesAuctionContestCall = ServiceWrapper.getInstance().getSeriesDraftContest(getSeriesAuctionContestInput);
        getSeriesAuctionContestCall.enqueue(new Callback<GetSeriesAuctionContestOutput>() {
            @Override
            public void onResponse(Call<GetSeriesAuctionContestOutput> call, Response<GetSeriesAuctionContestOutput> response) {
                if (getSeriesAuctionContestCall == null || getSeriesAuctionContestCall.isCanceled()) {
                    onGetSeriesAuctionContestListener.onError(Constant.EXCEPTION_MESSAGE);
                } else if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetSeriesAuctionContestListener.onSuccess(response.body());
                    } else {
                        onGetSeriesAuctionContestListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetSeriesAuctionContestListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetSeriesAuctionContestListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSeriesAuctionContestOutput> call, Throwable t) {
                onGetSeriesAuctionContestListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return getSeriesAuctionContestCall;
    }

    @Override
    public Call<JoinAuctionOutput> joinAuction(JoinAuctionInput joinAuctionInput, final OnJoinAuctionListener onJoinAuctionListener) {
        String url;
        if (joinAuctionInput.getJoin().isEmpty()) {
            url = "auctionDrafts/join";
        } else {
            url = "auctionDrafts/joinInvite";
        }

        final Call<JoinAuctionOutput> joinAuctionCall = ServiceWrapper.getInstance().joinAuction(url, joinAuctionInput);
        joinAuctionCall.enqueue(new Callback<JoinAuctionOutput>() {
            @Override
            public void onResponse(Call<JoinAuctionOutput> call, Response<JoinAuctionOutput> response) {
                if (joinAuctionCall == null || joinAuctionCall.isCanceled()) {
                    return;
                }
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onJoinAuctionListener.onSuccess(response.body());
                    } else {
                        onJoinAuctionListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onJoinAuctionListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onJoinAuctionListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JoinAuctionOutput> call, Throwable t) {
                onJoinAuctionListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return joinAuctionCall;
    }

    @Override
    public Call<JoinAuctionOutput> joinDraft(JoinAuctionInput joinAuctionInput, final OnJoinAuctionListener onJoinAuctionListener) {

        String url;
        if (joinAuctionInput.getJoin().isEmpty()) {
            url = "snakeDrafts/join";
        } else {
            url = "snakeDrafts/joinInvite";
        }

        final Call<JoinAuctionOutput> joinAuctionCall = ServiceWrapper.getInstance().joinDraft(url, joinAuctionInput);
        joinAuctionCall.enqueue(new Callback<JoinAuctionOutput>() {
            @Override
            public void onResponse(Call<JoinAuctionOutput> call, Response<JoinAuctionOutput> response) {
                if (joinAuctionCall == null || joinAuctionCall.isCanceled()) {
                    return;
                }
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onJoinAuctionListener.onSuccess(response.body());
                    } else {
                        onJoinAuctionListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onJoinAuctionListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onJoinAuctionListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JoinAuctionOutput> call, Throwable t) {
                onJoinAuctionListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return joinAuctionCall;
    }

    @Override
    public Call<GetContestBidHistoryOutput> getContestBidHistory(GetContestBidHistoryInput getContestBidHistoryInput, final OnGetContestBidHistoryListener onGetContestBidHistoryListener) {
        final Call<GetContestBidHistoryOutput> getContestBidHistoryCall = ServiceWrapper.getInstance().getContestBidHistory(getContestBidHistoryInput);
        getContestBidHistoryCall.enqueue(new Callback<GetContestBidHistoryOutput>() {
            @Override
            public void onResponse(Call<GetContestBidHistoryOutput> call, Response<GetContestBidHistoryOutput> response) {
                if (getContestBidHistoryCall == null || getContestBidHistoryCall.isCanceled()) {
                    return;
                }
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetContestBidHistoryListener.onSuccess(response.body());
                    } else {
                        onGetContestBidHistoryListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetContestBidHistoryListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetContestBidHistoryListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetContestBidHistoryOutput> call, Throwable t) {
                onGetContestBidHistoryListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return getContestBidHistoryCall;
    }

    @Override
    public Call<DefaultRespose> submitAuctionsPlayers(SubmitAuctionsPlayersInput submitAuctionsPlayersInput, final OnSubmitAuctionsPlayersListener onSubmitAuctionsPlayersListener) {
        final Call<DefaultRespose> defaultResposeCall = ServiceWrapper.getInstance().submitAuctionsPlayers(submitAuctionsPlayersInput);
        defaultResposeCall.enqueue(new Callback<DefaultRespose>() {
            @Override
            public void onResponse(Call<DefaultRespose> call, Response<DefaultRespose> response) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onSubmitAuctionsPlayersListener.onSuccess(response.body());
                    } else {
                        onSubmitAuctionsPlayersListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onSubmitAuctionsPlayersListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onSubmitAuctionsPlayersListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultRespose> call, Throwable t) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (t != null)
                    onSubmitAuctionsPlayersListener.onError(t.getMessage() + "");
                else
                    onSubmitAuctionsPlayersListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return defaultResposeCall;
    }

    @Override
    public Call<DefaultRespose> submitDraftPlayers(SubmitAuctionsPlayersInput submitAuctionsPlayersInput, final OnSubmitAuctionsPlayersListener onSubmitAuctionsPlayersListener) {
        final Call<DefaultRespose> defaultResposeCall = ServiceWrapper.getInstance().submitDraftPlayers(submitAuctionsPlayersInput);
        defaultResposeCall.enqueue(new Callback<DefaultRespose>() {
            @Override
            public void onResponse(Call<DefaultRespose> call, Response<DefaultRespose> response) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onSubmitAuctionsPlayersListener.onSuccess(response.body());
                    } else {
                        onSubmitAuctionsPlayersListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onSubmitAuctionsPlayersListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onSubmitAuctionsPlayersListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultRespose> call, Throwable t) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (t != null)
                    onSubmitAuctionsPlayersListener.onError(t.getMessage() + "");
                else
                    onSubmitAuctionsPlayersListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return defaultResposeCall;
    }


    @Override
    public Call<DefaultRespose> sendMessage(HashMap<String, String> submitAuctionsPlayersInput, final OnSubmitAuctionsPlayersListener onSubmitAuctionsPlayersListener) {
        final Call<DefaultRespose> defaultResposeCall = ServiceWrapper.getInstance().sendMessage(submitAuctionsPlayersInput);
        defaultResposeCall.enqueue(new Callback<DefaultRespose>() {
            @Override
            public void onResponse(Call<DefaultRespose> call, Response<DefaultRespose> response) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onSubmitAuctionsPlayersListener.onSuccess(response.body());
                    } else {
                        onSubmitAuctionsPlayersListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onSubmitAuctionsPlayersListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onSubmitAuctionsPlayersListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultRespose> call, Throwable t) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (t != null)
                    onSubmitAuctionsPlayersListener.onError(t.getMessage() + "");
                else
                    onSubmitAuctionsPlayersListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return defaultResposeCall;
    }


    @Override
    public Call<PlayersOutput> getAuctionPlayers(PlayersInput submitAuctionsPlayersInput, final OnGetAuctionPlayersPointListener onSubmitAuctionsPlayersListener) {
        final Call<PlayersOutput> defaultResposeCall = ServiceWrapper.getInstance().getAuctionPlayers(submitAuctionsPlayersInput);
        defaultResposeCall.enqueue(new Callback<PlayersOutput>() {
            @Override
            public void onResponse(Call<PlayersOutput> call, Response<PlayersOutput> response) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onSubmitAuctionsPlayersListener.onSuccess(response.body());
                    } else {
                        onSubmitAuctionsPlayersListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onSubmitAuctionsPlayersListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onSubmitAuctionsPlayersListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<PlayersOutput> call, Throwable t) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (t != null)
                    onSubmitAuctionsPlayersListener.onError(t.getMessage() + "");
                else
                    onSubmitAuctionsPlayersListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return defaultResposeCall;
    }


    @Override
    public Call<SinglePlayerData> getSingleAuctionPlayers(PlayerFantasyStatsInput submitAuctionsPlayersInput, final OnGetSingleAuctionPlayersPointListener onSubmitAuctionsPlayersListener) {
        final Call<SinglePlayerData> defaultResposeCall = ServiceWrapper.getInstance().getSingleAuctionPlayers(submitAuctionsPlayersInput);
        defaultResposeCall.enqueue(new Callback<SinglePlayerData>() {
            @Override
            public void onResponse(Call<SinglePlayerData> call, Response<SinglePlayerData> response) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("200")) {
                        onSubmitAuctionsPlayersListener.onSuccess(response.body());
                    } else {
                        onSubmitAuctionsPlayersListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onSubmitAuctionsPlayersListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onSubmitAuctionsPlayersListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SinglePlayerData> call, Throwable t) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (t != null)
                    onSubmitAuctionsPlayersListener.onError(t.getMessage() + "");
                else
                    onSubmitAuctionsPlayersListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return defaultResposeCall;
    }


    @Override
    public Call<GetDraftTeamsOutput> getDraftTeams(GetDraftTeamsInput getDraftTeamsInput, final OnGetDraftTeamsListener onGetDraftTeamsListener) {
        final Call<GetDraftTeamsOutput> draftTeamsCall = ServiceWrapper.getInstance().getDraftTeams(getDraftTeamsInput);
        draftTeamsCall.enqueue(new Callback<GetDraftTeamsOutput>() {
            @Override
            public void onResponse(Call<GetDraftTeamsOutput> call, Response<GetDraftTeamsOutput> response) {
                if (draftTeamsCall == null || draftTeamsCall.isCanceled()) return;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        onGetDraftTeamsListener.onSuccess(response.body());
                    } else {
                        onGetDraftTeamsListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onGetDraftTeamsListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onGetDraftTeamsListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetDraftTeamsOutput> call, Throwable t) {
                if (draftTeamsCall == null || draftTeamsCall.isCanceled()) return;
                if (t != null)
                    onGetDraftTeamsListener.onError(t.getMessage() + "");
                else
                    onGetDraftTeamsListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return draftTeamsCall;
    }

    @Override
    public Call<CheckUserDraftInLiveOutput> checkUserDraftInlive(CheckUserDraftInLiveInput checkUserDraftInLiveInput, final OnCheckUserDraftListener onCheckUserDraftListener) {
        final Call<CheckUserDraftInLiveOutput> checkUserDraftInLiveOutputCall = ServiceWrapper.getInstance().checkUserDraftInlive(checkUserDraftInLiveInput);
        checkUserDraftInLiveOutputCall.enqueue(new Callback<CheckUserDraftInLiveOutput>() {
            @Override
            public void onResponse(Call<CheckUserDraftInLiveOutput> call, Response<CheckUserDraftInLiveOutput> response) {
                if (checkUserDraftInLiveOutputCall == null || checkUserDraftInLiveOutputCall.isCanceled())
                    return;
                onCheckUserDraftListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<CheckUserDraftInLiveOutput> call, Throwable t) {
                if (checkUserDraftInLiveOutputCall == null || checkUserDraftInLiveOutputCall.isCanceled())
                    return;
                if (t != null)
                    onCheckUserDraftListener.onError(t.getMessage() + "");
                else
                    onCheckUserDraftListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return checkUserDraftInLiveOutputCall;
    }


    @Override
    public Call<GetDraftPlayerPointOutput> getDraftPlayersPoint(final GetDraftPlayerPointInput getDraftPlayerPointInput, final OnGetDraftPlayersPointListener onGetDraftPlayersPointListener) {

        String url = "";
        switch (AppSession.getInstance().getPlayMode()) {
            case 0:
                if (AppSession.getInstance().getGameType() == 1) {

                    url = "sports/dfsPlayersPoint";
                } else {
                    url = "football/sports/dfsPlayersPoint";
                }
                break;
            case 1:
            case 2:
                url = "sports/dfsPlayersPoint";
                break;
        }
        final Call<GetDraftPlayerPointOutput> draftPlayersPointCall = ServiceWrapper.getInstance().getDraftPlayersPoint(url, getDraftPlayerPointInput);
        draftPlayersPointCall.enqueue(new Callback<GetDraftPlayerPointOutput>() {
            @Override
            public void onResponse(Call<GetDraftPlayerPointOutput> call, Response<GetDraftPlayerPointOutput> response) {
                if (draftPlayersPointCall == null || draftPlayersPointCall.isCanceled()) return;
                if (response.body().getResponseCode() == 200) {
                    onGetDraftPlayersPointListener.onSuccess(response.body());
                } else {
                    onGetDraftPlayersPointListener.onError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetDraftPlayerPointOutput> call, Throwable t) {
                if (draftPlayersPointCall == null || draftPlayersPointCall.isCanceled()) return;
                if (t != null)
                    onGetDraftPlayersPointListener.onError(t.getMessage() + "");
                else
                    onGetDraftPlayersPointListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return draftPlayersPointCall;
    }


    @Override
    public Call<PromoCodeResponse> promoCode(PromoCodeInput mPromoCodeInput, final OnPromoCodeResponseListener onResponseListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "store/validateCoupon";
                break;
            case 2:
                url = "football/store/validateCoupon";
                break;
        }
        final Call<PromoCodeResponse> promoCodeResponseCallBack = ServiceWrapper.getInstance().promoCode(url, mPromoCodeInput);
        promoCodeResponseCallBack.enqueue(new Callback<PromoCodeResponse>() {
            @Override
            public void onResponse(Call<PromoCodeResponse> call, Response<PromoCodeResponse> response) {

                if (promoCodeResponseCallBack == null || promoCodeResponseCallBack.isCanceled())
                    return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<PromoCodeResponse> call, Throwable t) {

                if (promoCodeResponseCallBack == null || promoCodeResponseCallBack.isCanceled())
                    return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);

            }
        });

        return promoCodeResponseCallBack;
    }

    @Override
    public Call<RequestOtpForSigninOutput> requestOtpForSigninCall(RequestOtpForSigninInput requestOtpForSigninInput, final OnRequestOtpForSigninListener onRequestOtpForSigninListener) {

        final Call<RequestOtpForSigninOutput> requestOtpForSigninOutputCall = ServiceWrapper.getInstance().requestOtpForSignin(requestOtpForSigninInput);
        requestOtpForSigninOutputCall.enqueue(new Callback<RequestOtpForSigninOutput>() {
            @Override
            public void onResponse(Call<RequestOtpForSigninOutput> call, Response<RequestOtpForSigninOutput> response) {
                if (requestOtpForSigninOutputCall == null || requestOtpForSigninOutputCall.isCanceled())
                    return;
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200 || response.body().getResponseCode() == 201) {
                        onRequestOtpForSigninListener.onSuccess(response.body());
                    } else {
                        onRequestOtpForSigninListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onRequestOtpForSigninListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onRequestOtpForSigninListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }

            }

            @Override
            public void onFailure(Call<RequestOtpForSigninOutput> call, Throwable t) {
                if (requestOtpForSigninOutputCall == null || requestOtpForSigninOutputCall.isCanceled())
                    return;
                if (t != null)
                    onRequestOtpForSigninListener.onError(t.getMessage() + "");
                else
                    onRequestOtpForSigninListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return requestOtpForSigninOutputCall;
    }

    @Override
    public Call<PointsList> getPointList(String type, PointsSystem system, final OnPointsResponseListener onResponseListener) {
        String url = "";
        switch (type) {
            case "0":
                url = "sports/getPointsApp";
                break;
            case "1":
                url = "football/sports/getPointsApp";
                break;
        }
        final Call<PointsList> notificationListCall = ServiceWrapper.getInstance().getList(url, system);

        notificationListCall.enqueue(new Callback<PointsList>() {
            @Override
            public void onResponse(Call<PointsList> call, Response<PointsList> response) {

                if (notificationListCall == null || notificationListCall.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<PointsList> call, Throwable t) {

                if (notificationListCall == null || notificationListCall.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return notificationListCall;
    }

    @Override
    public Call<PromoCodeListOutput> promocodeList(PromoCodeListInput mPromoCodeListInput, final OnRequestPromoCodeListListener onResponseListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "store/getCoupons";
                break;
            case 2:
                url = "football/store/getCoupons";
                break;
        }
        final Call<PromoCodeListOutput> promoCodeListOutputCallback = ServiceWrapper.getInstance().promocodeList(url, mPromoCodeListInput);
        promoCodeListOutputCallback.enqueue(new Callback<PromoCodeListOutput>() {
            @Override
            public void onResponse(Call<PromoCodeListOutput> call, Response<PromoCodeListOutput> response) {
                if (promoCodeListOutputCallback == null || promoCodeListOutputCallback.isCanceled())
                    return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode().equals("502")) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode().equals("200")) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<PromoCodeListOutput> call, Throwable t) {
                if (promoCodeListOutputCallback == null || promoCodeListOutputCallback.isCanceled())
                    return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });


        return promoCodeListOutputCallback;
    }

    @Override
    public Call<ReferralUsersResponse> getReferralUsers(LoginInput mLoginInput, final OnReferralUsersListener onReferralUsersListener) {

        final Call<ReferralUsersResponse> referralUsersResponseCallBack = ServiceWrapper.getInstance().getReferralUsers(mLoginInput);
        referralUsersResponseCallBack.enqueue(new Callback<ReferralUsersResponse>() {
            @Override
            public void onResponse(Call<ReferralUsersResponse> call, Response<ReferralUsersResponse> response) {
                if (referralUsersResponseCallBack == null || referralUsersResponseCallBack.isCanceled())
                    return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode().equals("502")) {
                        AppController.expirySession(response.body().getMessage());
                        onReferralUsersListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode().equals("200") || response.body().getResponseCode().equals("201")) {
                        onReferralUsersListener.onSuccess(response.body());
                    } else {
                        onReferralUsersListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onReferralUsersListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onReferralUsersListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ReferralUsersResponse> call, Throwable t) {
                if (referralUsersResponseCallBack == null || referralUsersResponseCallBack.isCanceled())
                    return;

                if (t != null)
                    onReferralUsersListener.onError(t.getMessage() + "");
                else
                    onReferralUsersListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return referralUsersResponseCallBack;
    }

    @Override
    public Call<DefaultRespose> getReferEarn(ReferEarnInput mReferEarnInput, final OnMakeFavoriteTeamListener onResponseListener) {
        final Call<DefaultRespose> resposeCallBack = ServiceWrapper.getInstance().getReferEarn(mReferEarnInput);
        resposeCallBack.enqueue(new Callback<DefaultRespose>() {
            @Override
            public void onResponse(Call<DefaultRespose> call, Response<DefaultRespose> response) {
                if (resposeCallBack == null || resposeCallBack.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200 || response.body().getResponseCode() == 201) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultRespose> call, Throwable t) {
                if (resposeCallBack == null || resposeCallBack.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return resposeCallBack;
    }


    @Override
    public Call<LoginResponseOut> switchTeam(SwitchTeamInput switchTeamInput, final OnResponseListener onResponseListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "contest/switchUserTeam";
                break;
            case 2:
                url = "football/contest/switchUserTeam";
                break;
        }
        final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().switchTeam(url, switchTeamInput);
        responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
            @Override
            public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }

    @Override
    public Call<ResponsePayTmDetails> payTm(PaytmInput paytmInput, final IUserInteractor.OnPayTmResponseListener onResponseListener) {
        if (APIValidate.payTm(paytmInput, onResponseListener)) {

            final Call<ResponsePayTmDetails> responseLoginCallback = ServiceWrapper.getInstance().payTm(
                    paytmInput);

            responseLoginCallback.enqueue(new Callback<ResponsePayTmDetails>() {
                @Override
                public void onResponse(Call<ResponsePayTmDetails> call, Response<ResponsePayTmDetails> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {
                            AppController.expirySession(response.body().getMessage());
                            onResponseListener.onError(response.body().getMessage());
                        } else if (response.body().getResponseCode() == 200) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponsePayTmDetails> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;
    }

    @Override
    public Call<LoginResponseOut> payTmResponse(PaytmInput paytmInput, final OnResponseListener onResponseListener) {
        final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().payTmResponse(paytmInput);

        responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
            @Override
            public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError("E" + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return responseLoginCallback;
    }

    @Override
    public Call<LoginResponseOut> notificationCount(LoginInput mLoginInput, final OnResponseListener onResponseListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "notifications/getNotificationCount";
                break;
            case 2:
                url = "notifications/getNotificationCount";
                break;
        }
        final Call<LoginResponseOut> responseOutCallback = ServiceWrapper.getInstance().notificationCount(url, mLoginInput);
        responseOutCallback.enqueue(new Callback<LoginResponseOut>() {
            @Override
            public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                if (responseOutCallback == null || responseOutCallback.isCanceled())
                    return;


                if (response != null && response.body() != null && response.isSuccessful()) {

                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseOut> call, Throwable t) {

                if (responseOutCallback == null || responseOutCallback.isCanceled())
                    return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });

        return responseOutCallback;
    }

    @Override
    public Call<DefaultRespose> notificationMarkRead(NotificationMarkReadInput mNotificationMarkReadInput, final OnMakeFavoriteTeamListener onResponseListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "notifications/markRead";
                break;
            case 2:
                url = "notifications/markRead";
                break;
        }
        final Call<DefaultRespose> resposeCallBack = ServiceWrapper.getInstance().notificationMarkRead(url, mNotificationMarkReadInput);
        resposeCallBack.enqueue(new Callback<DefaultRespose>() {
            @Override
            public void onResponse(Call<DefaultRespose> call, Response<DefaultRespose> response) {
                if (resposeCallBack == null || resposeCallBack.isCanceled())
                    return;
                if (response != null && response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultRespose> call, Throwable t) {
                if (resposeCallBack == null || resposeCallBack.isCanceled())
                    return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });

        return resposeCallBack;
    }


    @Override
    public Call<TransactionsBean> transactionsApp(TransactionInput transactionInput, final OnResponseTransactionListener onResponseListener) {
        final Call<TransactionsBean> responceTransactionCallBack = ServiceWrapper.getInstance().transactionsApp(transactionInput);

        responceTransactionCallBack.enqueue(new Callback<TransactionsBean>() {
            @Override
            public void onResponse(Call<TransactionsBean> call, Response<TransactionsBean> response) {
                if (responceTransactionCallBack == null || responceTransactionCallBack.isCanceled())
                    return;


                if (response != null && response.body() != null && response.isSuccessful()) {

                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionsBean> call, Throwable t) {
                if (responceTransactionCallBack == null || responceTransactionCallBack.isCanceled())
                    return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });

        return responceTransactionCallBack;
    }

    @Override
    public Call<NotificationsResponse> notificationList(NotificationInput notificationInput, final OnNotificationResponseListener onResponseListener) {

        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "notifications";
                break;
            case 2:
                url = "notifications";
                break;
        }
        final Call<NotificationsResponse> notificationListCall = ServiceWrapper.getInstance().notificationList(url, notificationInput);

        notificationListCall.enqueue(new Callback<NotificationsResponse>() {
            @Override
            public void onResponse(Call<NotificationsResponse> call, Response<NotificationsResponse> response) {

                if (notificationListCall == null || notificationListCall.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationsResponse> call, Throwable t) {

                if (notificationListCall == null || notificationListCall.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return notificationListCall;
    }

    @Override
    public Call<WithDrawoutput> withdrawal_add(WithdrawInput mWithdrawInput, final OnwithdrawalResponseListener onResponseListener) {
        final Call<WithDrawoutput> notificationListCall = ServiceWrapper.getInstance().withdrawal_add(mWithdrawInput);

        notificationListCall.enqueue(new Callback<WithDrawoutput>() {
            @Override
            public void onResponse(Call<WithDrawoutput> call, Response<WithDrawoutput> response) {

                if (notificationListCall == null || notificationListCall.isCanceled()) return;

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<WithDrawoutput> call, Throwable t) {

                if (notificationListCall == null || notificationListCall.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return notificationListCall;
    }

    @Override
    public Call<ResponseReferralDetails> getReferralDetail(final OnReferralDetailListener mOnReferralDetailListener) {
        final Call<ResponseReferralDetails> referralDetailsCallback = ServiceWrapper.getInstance().getReferralDetail();
        referralDetailsCallback.enqueue(new Callback<ResponseReferralDetails>() {
            @Override
            public void onResponse(Call<ResponseReferralDetails> call, Response<ResponseReferralDetails> response) {
                if (referralDetailsCallback == null || referralDetailsCallback.isCanceled()) return;

                if (response != null && response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {
                        mOnReferralDetailListener.onError(response.body().getMessage());
                    } else if (response.body().getResponseCode() == 200) {
                        mOnReferralDetailListener.onSuccess(response.body());
                    } else {
                        mOnReferralDetailListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        mOnReferralDetailListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        mOnReferralDetailListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseReferralDetails> call, Throwable t) {
                if (referralDetailsCallback == null || referralDetailsCallback.isCanceled()) return;

                if (t != null)
                    mOnReferralDetailListener.onError(t.getMessage() + "");
                else
                    mOnReferralDetailListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return referralDetailsCallback;
    }

    @Override
    public Call<LoginResponseOut> verifyEmailAddress(VerifyMobileInput mobileInput, final OnResponseListener onResponseListener) {
        if (APIValidate.isOTPValid(mobileInput.getOTP(), onResponseListener)) {
            final Call<LoginResponseOut> responseLoginCallback = ServiceWrapper.getInstance().verifyEmailAddress(mobileInput);

            responseLoginCallback.enqueue(new Callback<LoginResponseOut>() {
                @Override
                public void onResponse(Call<LoginResponseOut> call, Response<LoginResponseOut> response) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (response != null && response.body() != null && response.isSuccessful()) {
                        if (response.body().getResponseCode() == 502) {

                            AppController.expirySession(response.body().getMessage());
                            onResponseListener.onError(response.body().getMessage());


                        } else if (response.body().getResponseCode() == 200 || response.body().getResponseCode() == 201) {
                            onResponseListener.onSuccess(response.body());
                        } else {
                            onResponseListener.onError(response.body().getMessage());
                        }
                    } else {
                        if (response != null && response.errorBody() != null) {
                            APIError error = ErrorUtils.parseError(response);
                            onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                        } else {
                            onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseOut> call, Throwable t) {
                    if (responseLoginCallback == null || responseLoginCallback.isCanceled()) return;

                    if (t != null)
                        onResponseListener.onError(t.getMessage() + "");
                    else
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                }
            });
            return responseLoginCallback;
        }
        return null;

    }

    @Override
    public Call<DefaultRespose> deleteNotification(NotificationDeleteInput mDeleteInput, final OnMakeFavoriteTeamListener onResponseListener) {
        String url = "";
        switch (AppSession.getInstance().getGameType()) {
            case 1:
                url = "notifications/deleteAll";
                break;
            case 2:
                url = "notifications/deleteAll";
                break;
        }
        final Call<DefaultRespose> defaultResposeCallback = ServiceWrapper.getInstance().deleteNotification(url, mDeleteInput);
        defaultResposeCallback.enqueue(new Callback<DefaultRespose>() {
            @Override
            public void onResponse(Call<DefaultRespose> call, Response<DefaultRespose> response) {
                if (defaultResposeCallback == null || defaultResposeCallback.isCanceled()) return;

                if (response != null && response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 502) {

                        AppController.expirySession(response.body().getMessage());
                        onResponseListener.onError(response.body().getMessage());

                    } else if (response.body().getResponseCode() == 200) {
                        onResponseListener.onSuccess(response.body());
                    } else {
                        onResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        onResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultRespose> call, Throwable t) {
                if (defaultResposeCallback == null || defaultResposeCallback.isCanceled()) return;

                if (t != null)
                    onResponseListener.onError(t.getMessage() + "");
                else
                    onResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });


        return defaultResposeCallback;
    }

    @Override
    public Call<GetSubscriptionResponse> getSubscription(LoginInput loginInput, final GetSubscriptionResponseListener getSubscriptionResponseListener) {
        final Call<GetSubscriptionResponse> subscriptionCall = ServiceWrapper.getInstance().getSubscription(loginInput);
        subscriptionCall.enqueue(new Callback<GetSubscriptionResponse>() {
            @Override
            public void onResponse(Call<GetSubscriptionResponse> call, Response<GetSubscriptionResponse> response) {
                if (subscriptionCall == null || subscriptionCall.isCanceled()) return;

                if (response != null && response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        getSubscriptionResponseListener.onSuccess(response.body().getData());
                    } else {
                        getSubscriptionResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        getSubscriptionResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        getSubscriptionResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSubscriptionResponse> call, Throwable t) {
                if (subscriptionCall == null || subscriptionCall.isCanceled()) return;

                if (t != null)
                    getSubscriptionResponseListener.onError(t.getMessage() + "");
                else
                    getSubscriptionResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return subscriptionCall;
    }

    @Override
    public Call<DefaultRespose> buySubscription(BuySubscriptionInput loginInput, final BuySubscriptionResponseListener getSubscriptionResponseListener) {

        final Call<DefaultRespose> defaultResposeCall = ServiceWrapper.getInstance().buySubscription(loginInput);
        defaultResposeCall.enqueue(new Callback<DefaultRespose>() {
            @Override
            public void onResponse(Call<DefaultRespose> call, Response<DefaultRespose> response) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (response != null && response.body() != null && response.isSuccessful()) {
                    if (response.body().getResponseCode() == 200) {
                        getSubscriptionResponseListener.onSuccess(response.body().getMessage());
                    } else {
                        getSubscriptionResponseListener.onError(response.body().getMessage());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        APIError error = ErrorUtils.parseError(response);
                        getSubscriptionResponseListener.onError(AppUtils.getStrFromRes(R.string.exception) + error.getStatus_code() + "/" + error.getMessage());
                    } else {
                        getSubscriptionResponseListener.onError(Constant.EXCEPTION_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultRespose> call, Throwable t) {
                if (defaultResposeCall == null || defaultResposeCall.isCanceled()) return;
                if (t != null)
                    getSubscriptionResponseListener.onError(t.getMessage() + "");
                else
                    getSubscriptionResponseListener.onError(Constant.EXCEPTION_MESSAGE);
            }
        });
        return defaultResposeCall;
    }
}