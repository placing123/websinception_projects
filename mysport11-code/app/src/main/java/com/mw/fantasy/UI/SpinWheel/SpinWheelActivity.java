package com.mw.fantasy.UI.SpinWheel;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.SpinWheel.model.LuckyItem;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanInput.LoginInput;
import com.mw.fantasy.beanOutput.SpinWheelOutput;
import com.mw.fantasy.beanOutput.UpdateSpinWheelOutput;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.dialog.AlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;

import com.mw.fantasy.utility.NetworkUtils;
import com.mw.fantasy.utility.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import butterknife.BindView;
import butterknife.OnClick;

public class SpinWheelActivity extends BaseActivity {


    private SpinWheelActivity mContext;
    private ProgressDialog mProgressDialog;
    private UserInteractor mInteractor;
    private AlertDialog mAlertDialog;
    private LuckyWheelView luckyWheelView;
    private SpinWheelOutput mspinWheelOutput;
    private List<Integer> selectList = new ArrayList<>();
    private CountDownTimer countDownTimer;


    @BindView(R.id.remaining_time)
    CustomTextView remainingTime;



    @BindView(R.id.activity_main)
    LinearLayout activity_main;


    @BindView(R.id.play)
    Button play;
    private String value = "";
    private WinningDialog winningDialog;
    private boolean isFrom = false;
    private boolean isRunning = true;

    @OnClick(R.id.iv_close)
    void onClick() {
        if (isRunning){
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        if (isRunning){
            finish();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_spin_wheel;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static void start(Context context, Boolean isFrom) {
        Intent starter = new Intent(context, SpinWheelActivity.class);
        starter.putExtra("Dialog", isFrom);

        context.startActivity(starter);
    }

    List<LuckyItem> data = new ArrayList<>();



    @Override
    public void init() {
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

        mContext = this;

        isFrom = getIntent().getBooleanExtra("Dialog", false);
      /*  if (isFrom) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(30, 10, 30, 30);
            activity_main.setLayoutParams(params);
        } else {*/
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            activity_main.setLayoutParams(params);
//        }


        mProgressDialog = new ProgressDialog(mContext);
        mInteractor = new UserInteractor();
        mProgressDialog.show();
        apiCallGetSpinData();
        luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);



        luckyWheelView.setRound(5);
        luckyWheelView.setTouchEnabled(false);
        luckyWheelView.setBorderColor(Color.WHITE);

        luckyWheelView.setLuckyWheelBackgrouldColor(Color.TRANSPARENT);


        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = false;

                AppUtils.clickVibrate(mContext);
                if (mspinWheelOutput.getData().getLuckyWheelActive().equalsIgnoreCase("Yes")) {

                    if (mspinWheelOutput.getData().getIsPlay().equalsIgnoreCase("Yes")) {
                        int index = getRandomIndex();
                        if (index >= selectList.size()) {
                            if (index != 0) {
                                luckyWheelView.startLuckyWheelWithTargetIndex(selectList.get(index - 1));
                            }
                        } else {
                            luckyWheelView.startLuckyWheelWithTargetIndex(selectList.get(index));
                        }

                    } else {
                        Toast.makeText(mContext, "You exceed your limit for today!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(mContext, mspinWheelOutput.getData().getLuckyWheelMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                isRunning = true;
                if (data.get(index).topText.equalsIgnoreCase("Better Luck Next Time")) {
                    apiCalludATESpinData(data.get(index).topText);
                    winningDialog = new WinningDialog(mContext, mOnSubmitClickListener, data.get(index).topText);
                    winningDialog.show();
                } else {
                    winningDialog = new WinningDialog(mContext, mOnSubmitClickListener, data.get(index).topText);
                    winningDialog.show();
                }
            }
        });
    }

    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(selectList.size()) + 0;
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }


    private WinningDialog.OnSubmitClickListener mOnSubmitClickListener = new WinningDialog.OnSubmitClickListener() {
        @Override
        public void onClick(String amt) {

            apiCalludATESpinData(amt);
        }
    };


    private void apiCallGetSpinData() {
        if (NetworkUtils.isConnected(mContext)) {

            LoginInput loginInput = new LoginInput();
            loginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            mInteractor.getSpinWheelData(loginInput, new IUserInteractor.OnResponseSpinWheeListener() {

                @Override
                public void onSuccess(SpinWheelOutput spinWheelOutput) {
                    mProgressDialog.dismiss();
                    mspinWheelOutput = spinWheelOutput;
                    data.clear();
                    selectList.clear();
                    if (spinWheelOutput.getData().getRecords().size() > 0) {
                        for (int i = 0; i < spinWheelOutput.getData().getRecords().size(); i++) {
                            if (spinWheelOutput.getData().getRecords().get(i).getPick().equalsIgnoreCase("Yes")) {
                                selectList.add(i);
                            }
                            LuckyItem luckyItem12 = new LuckyItem();
                            luckyItem12.topText = spinWheelOutput.getData().getRecords().get(i).getPoints();
                            luckyItem12.icon = R.drawable.test1;
                            luckyItem12.image = spinWheelOutput.getData().getRecords().get(i).getImage();
                            luckyItem12.color = Color.parseColor(spinWheelOutput.getData().getRecords().get(i).getColourCode());
//                            Random rand = new Random();


                            data.add(luckyItem12);
                        }
                        luckyWheelView.setData(data);
                        if (spinWheelOutput.getData().getIsPlay().equalsIgnoreCase("No")) {
                            setTime();


                        }

                    }
                }

                @Override
                public void onNotFound(String error) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            error,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    apiCallGetSpinData();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }


                            });
                    mAlertDialog.show();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    apiCallGetSpinData();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }


                            });
                    mAlertDialog.show();
                }

                @Override
                public void OnSessionExpire() {

                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            apiCallGetSpinData();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }


                    });
            mAlertDialog.show();

        }
    }


    private void apiCalludATESpinData(final String topText) {
        value = topText;
        if (NetworkUtils.isConnected(mContext)) {

            LoginInput loginInput = new LoginInput();
            loginInput.setValue(topText);
            loginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
            mInteractor.updateWinningSpinWheelData(loginInput, new IUserInteractor.OnResponseUpdateSpinWheeListener() {

                @Override
                public void onSuccess(UpdateSpinWheelOutput spinWheelOutput) {
                    mProgressDialog.dismiss();
                    mspinWheelOutput.getData().setIsPlay("No");
//                    winningInfo.setVisibility(View.VISIBLE);
//                    winningInfo.setTextSize(12f);
//                    winningInfo.setText("You Won Rs."+ topText+". this amount is added in your cash bonus enjoy!. you will get another chance after 24 hrs.");
                    apiCallGetSpinData();
                    Toast.makeText(mContext, spinWheelOutput.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNotFound(String error) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            error,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    apiCallGetSpinData();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }


                            });
                    mAlertDialog.show();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel),
                            new AlertDialog.OnBtnClickListener() {
                                @Override
                                public void onYesClick() {
                                    mAlertDialog.hide();
                                    apiCallGetSpinData();
                                }

                                @Override
                                public void onNoClick() {
                                    mAlertDialog.hide();
                                    finish();
                                }


                            });
                    mAlertDialog.show();
                }

                @Override
                public void OnSessionExpire() {

                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel),
                    new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            apiCallGetSpinData();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                            finish();
                        }


                    });
            mAlertDialog.show();

        }
    }


    public void setTime() {
        try {
            if (countDownTimer != null) countDownTimer.cancel();

            countDownTimer = new CountDownTimer((Long.parseLong(mspinWheelOutput.getData().getRenewTime()) * 1000), TimeUnit.SECONDS.toMillis(1)) {
                public void onTick(long millisUntilFinished) {
                    if (play != null)
                        play.setText(TimeUtils.getRemainsT(millisUntilFinished));
                }

                public void onFinish() {
                    if (play != null)
                        play.setText("Play");
                    mspinWheelOutput.getData().setIsPlay("Yes");
//                        remainingTime.setVisibility(View.INVISIBLE);
//                    remaining_time_up.setVisibility(View.INVISIBLE);
                }
            };
            if (countDownTimer != null) {
                countDownTimer.start();
            }


        } catch (Exception e1) {
            e1.printStackTrace();
            play.setText("N/A");
        }

    }

}
