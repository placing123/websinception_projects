package com.websinception.megastar.dialog;

import android.content.Context;
import android.widget.TimePicker;

import com.websinception.megastar.R;
import com.websinception.megastar.utility.TimeUtils;
import com.websinception.megastar.utility.ViewUtils;

import java.util.Calendar;

public class TimePickerDialog {
    private Context context;
    private android.app.TimePickerDialog alertDialog;
    private Calendar mCalendar;
    private OnClickListener onClickListener;

    public void show() {
        alertDialog.show();
        ViewUtils.hideKeyboard(context);
    }

    public void hide() {
        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
            ViewUtils.hideKeyboard(context);
        }
    }

    public String getStandardTime() {
        if (mCalendar != null) {
            return TimeUtils.getStandardUTCTimeFormat(mCalendar.getTime());
        }
        return "";
    }

    public String getShowDate() {
        if (mCalendar != null) {
            return TimeUtils.getShowTimeFormat(mCalendar.getTime());
        }
        return "";
    }

    public interface OnClickListener {
        void onTimeSet(String date);

        void onStandardTimeFormat(String date);
    }

    public TimePickerDialog(final Context context, Calendar calendar, String mTitle, final OnClickListener onClickListener) {

        this.context = context;
        this.mCalendar = calendar;
        this.onClickListener = onClickListener;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        alertDialog = new android.app.TimePickerDialog(context, R.style.datepicker, onTimeSetListener, hour, minute, false);//Yes 24 hour time
        alertDialog.setTitle(mTitle);
    }

    final android.app.TimePickerDialog.OnTimeSetListener onTimeSetListener = new android.app.TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
            mCalendar.set(Calendar.MINUTE, selectedMinute);
            hide();
            if (onClickListener != null) {
                onClickListener.onTimeSet(TimeUtils.getShowTimeFormat(mCalendar.getTime()));
                onClickListener.onStandardTimeFormat(TimeUtils.getStandardUTCTimeFormat(mCalendar.getTime()));
            }
        }
    };
}