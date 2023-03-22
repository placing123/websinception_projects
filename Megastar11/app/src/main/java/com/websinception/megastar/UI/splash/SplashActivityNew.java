package com.websinception.megastar.UI.splash;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.SelectMode.SelectModeActivity;
import com.websinception.megastar.UI.loginRagisterModule.VerifyPhoneNumber;
import com.websinception.megastar.UI.startup.StartupActivity;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.DeviceUtility;

public class SplashActivityNew extends BaseActivity implements SplashView {


    private Context mContext;

    public static void start(Context context) {

        Intent starter = new Intent(context, SplashActivityNew.class);
        context.startActivity(starter);
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    public void showAlertDialogAndExitApp(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // getWindow().setBackgroundDrawableResource(R.drawable.demo_splash);
        // ATTENTION: This was auto-generated to handle app links.

       /* Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        Bundle b = new Bundle();
        if (appLinkData != null) {

            String[] separated = appLinkData.toString().split("ccode=");
            //   Toast.makeText(this, separated[1], Toast.LENGTH_SHORT).show();

            b.putString("appLinkData", separated[1]);
            Log.d("appLinkData",separated[1]);

            InviteCodes.start(mContext,"0");

        }
*/
    }

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        mContext = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_id);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {

                Object value = getIntent().getExtras().get(key);
                Log.d("Firebase", "Key: " + key + " Value: " + value);
            }
        }


        //AppUtils.setStatusBarGradiant(this);
        Log.d("jsonInString", new Gson().toJson(AppSession.getInstance().getLoginSession()));
        if (DeviceUtility.isDeviceRooted()) {
            showAlertDialogAndExitApp("This device is rooted. You can't use this app.");
        } else {
            new SplashImplementer(this).startThread(Constant.SPLASH_MILLISECOND_TIME);
        }
    }

    @Override
    public void isLogin() {
        SelectModeActivity.start(mContext);
        finish();

    }

    @Override
    public void isLogout() {
        StartupActivity.start(mContext);
        finish();
    }

    @Override
    public void notVerify() {
        VerifyPhoneNumber.start(mContext);
        finish();

    }

    @Override
    public Context getContext() {
        return null;
    }
}
