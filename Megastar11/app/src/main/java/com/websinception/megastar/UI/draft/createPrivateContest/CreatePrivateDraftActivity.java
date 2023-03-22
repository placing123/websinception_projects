package com.websinception.megastar.UI.draft.createPrivateContest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.websinception.megastar.R;
import com.websinception.megastar.UI.auction.SeriesInfoUtil;
import com.websinception.megastar.UI.winnerNumberSelection.WinnerNumberSelectionActivity;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.beanOutput.GetPlayerRoundOutput;
import com.websinception.megastar.customView.CustomEditText;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.AuctionAlertDialog;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class CreatePrivateDraftActivity extends BaseActivity {


    @BindView(R.id.ctv_series_name)
    CustomTextView mCtvSeriesName;

    @BindView(R.id.ctv_series_status)
    CustomTextView mCtvSeriesStatus;

    @BindView(R.id.cet_contest_name)
    CustomEditText mCetContestName;

    @BindView(R.id.ctv_league_time)
    CustomTextView mCustomTextViewTime;

    @BindView(R.id.cet_winning_ammount)
    CustomEditText mCetWinningAmount;

    @BindView(R.id.cet_contest_size)
    CustomEditText mCetContestSize;

    @BindView(R.id.ctv_entry_fee)
    CustomTextView mCtvEntryFee;


    @OnClick(R.id.ctv_league_time)
    void setDateTime() {  setDate(); }

    @OnClick(R.id.back)
    void onBackClick() {
        finish();
    }






    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String totalWinningAmountStr = mCetWinningAmount.getText().toString().trim();
            String contestSizeStr = mCetContestSize.getText().toString().trim();
            if (totalWinningAmountStr.equals("")) totalWinningAmountStr = "0";
            if (contestSizeStr.equals("")) contestSizeStr = "0";
            int totalWinningAmount = Integer.parseInt(totalWinningAmountStr);
            int contestSize = Integer.parseInt(contestSizeStr);

            if (contestSize*totalWinningAmount==0) {
                mCtvEntryFee.setText(" ₹ 0");
            }else {
                mCtvEntryFee.setText(" ₹ "+new DecimalFormat("##.##").format(calculateFee(contestSizeStr,totalWinningAmountStr)));
            }
        }
    };

    private Context mContext;

    private int flag, seriesStatus, totalPlayers = 0;
    private UserInteractor mInteractor;
    private String roundID, seriesId, seriesName, seriesDeadLine, seriesDeadLineLocal;
    private Call<GetPlayerRoundOutput> getDraftRound;
    private ProgressDialog mProgressDialog;
    private GetPlayerRoundOutput mResponse;
    private AuctionAlertDialog mAuctionAlertDialog;


    public static void start(Context context,
                             String seriesName,
                             String roundId,
                             String seriesId,
                             int seriesStatus,
                             String seriesDeadLine,
                             String seriesDeadLineLocal,
                             int flag) {
        Intent starter = new Intent(context, CreatePrivateDraftActivity.class);
        starter.putExtra("seriesId", seriesId);
        starter.putExtra("RoundID", roundId);
        starter.putExtra("seriesStatus", seriesStatus);
        starter.putExtra("seriesName", seriesName);
        starter.putExtra("seriesDeadLine", seriesDeadLine);
        starter.putExtra("seriesDeadLineLocal", seriesDeadLineLocal);
        starter.putExtra("flag", flag);
        ((Activity) context).startActivityForResult(starter, 150);
    }



    @OnClick(R.id.ctv_save)
    void nextBtnClick() {
        String name = mCetContestName.getText().toString().trim();
        String time = mCustomTextViewTime.getText().toString().trim();

        String totalWinningAmountStr = mCetWinningAmount.getText().toString().trim();
        String contestSizeStr = mCetContestSize.getText().toString().trim();
        if (totalWinningAmountStr.equals("")) totalWinningAmountStr = "0";
        if (contestSizeStr.equals("")) contestSizeStr = "0";

        int totalWinningAmount = Integer.parseInt(totalWinningAmountStr);
        int contestSize = Integer.parseInt(contestSizeStr);
        String entryFee = ""+new DecimalFormat("##.##").format(calculateFee(contestSizeStr,totalWinningAmountStr));







        if (name.equals("")) {
            AppUtils.showToast(this, "Contest name is required.");
        }  else if (totalWinningAmount < 10) {
            AppUtils.showToast(this, "Minimum winning amount is 100.");
        } else if (totalWinningAmount > 10000) {
            AppUtils.showToast(this, "Maximum winning amount is 10000.");
        } else if (contestSize < 2) {
            AppUtils.showToast(this, "Minimum contest size is 2.");
        } else {
            if (flag == 1) {
                Intent starter = new Intent(this, WinnerNumberSelectionActivity.class);
                starter.putExtra("flag", flag);
                starter.putExtra("contest_name", name);
                starter.putExtra("total_winning_amount", totalWinningAmount + "");
                starter.putExtra("contest_sizes", contestSize + "");
                starter.putExtra("team_entry_fee", entryFee + "");
                starter.putExtra("SeriesID", seriesId);
                starter.putExtra("RoundID", roundID);
                starter.putExtra("leagueStartTime", time);
                starter.putExtra("seriesName", seriesName);
                starter.putExtra("seriesDeadLine", seriesDeadLine);
                starter.putExtra("seriesStatus", seriesStatus);
                startActivityForResult(starter, 150);
            } else {
                Intent starter = new Intent(this, WinnerNumberSelectionActivity.class);
                starter.putExtra("flag", flag);
                starter.putExtra("contest_name", name);
                starter.putExtra("total_winning_amount", totalWinningAmount + "");
                starter.putExtra("contest_sizes", contestSize + "");
                starter.putExtra("team_entry_fee", entryFee + "");
                starter.putExtra("SeriesID", seriesId);
                starter.putExtra("RoundID", roundID);
                starter.putExtra("leagueStartTime", time);
                starter.putExtra("seriesDeadLineLocal", seriesDeadLineLocal);
                startActivityForResult(starter, 150);
            }
        }

    }

    private float calculateFee(String size, String winnings) {
        float perTeem = 0;
        if (size.length() > 0 && winnings.length() > 0) {
            float fee = (Float.parseFloat(winnings.toString())/ Float.parseFloat(size.toString()));
            float total = (fee*15)/100+fee;
            perTeem = total;

        } else {

        }
        return perTeem;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_create_private_draft;
       /* if (getIntent().hasExtra("flag")) {
            if (getIntent().getExtras().getInt("flag") == 1) {
                return R.layout.activity_create_private_auction;
            } else {
                return R.layout.activity_create_private_draft;
            }
        } else {
            return R.layout.activity_create_private_auction;
        }*/

    }

    @Override
    public void init() {
        mContext = this;
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(mContext);
        flag = getIntent().getExtras().getInt("flag");
        seriesId = getIntent().getExtras().getString("seriesId");
        roundID = getIntent().getExtras().getString("RoundID");
        seriesStatus = getIntent().getExtras().getInt("seriesStatus");
        seriesName = getIntent().getExtras().getString("seriesName");
        seriesDeadLine = getIntent().getExtras().getString("seriesDeadLine");
        seriesDeadLineLocal = getIntent().getExtras().getString("seriesDeadLineLocal");
        mCetWinningAmount.addTextChangedListener(mTextWatcher);
        mCetContestSize.addTextChangedListener(mTextWatcher);
        mCustomTextViewTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Date().getTime() + (10 * 60 * 1000))));
        new SeriesInfoUtil(mCtvSeriesName,
                mCtvSeriesStatus, seriesName, seriesDeadLine, seriesStatus).start();
        if (flag == 2) {
            mCetContestSize.setEnabled(false);
        }else {
            mCetContestSize.setEnabled(true);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 150) {
            if (resultCode == RESULT_OK) {
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        }
    }






    private void setDate() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog dd = new DatePickerDialog(CreatePrivateDraftActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String dateInString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            Date date = formatter.parse(dateInString);
                            String dateStr = (formatter.format(date).toString());
                            setTime(dateStr);

                        } catch (Exception ex) {

                            ex.printStackTrace();
                        }

                    }
                }, mYear, mMonth, mDay);
        dd.getDatePicker().setMinDate(c.getTimeInMillis());

        dd.show();
    }

    private void setTime(final String date) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(CreatePrivateDraftActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mCustomTextViewTime.setText(date + " " + String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

}
