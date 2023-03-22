package com.mw.fantasy.utility;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mw.fantasy.AppConfiguration;
import com.mw.fantasy.AppController;
import com.mw.fantasy.AppSession;
import com.mw.fantasy.R;
import com.mw.fantasy.UI.loginRagisterModule.LoginScreen;
import com.mw.fantasy.customView.CustomTextView;
import com.mw.fantasy.services.LogoutService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class AppUtils {

  /*  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.background);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavigationBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }*/

    public static String getStrFromRes(int resId) {
        return AppController.getContext().getString(resId);
    }

    public static Drawable getDrawableFromRes(int resId) {

        return AppController.getContext().getResources().getDrawable(resId);
    }

    public static String appVersionName() {
        String versionCode = "";
        try {
            PackageInfo packageInfo = AppController.getContext().getPackageManager().getPackageInfo(AppController.getContext().getPackageName(), 0);
            // versionName = packageInfo.versionName;
            versionCode = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return String.valueOf(versionCode);
    }

    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    public static void showSnackBar(@NonNull Context mContext, @NonNull View mView, @NonNull String message) {
        final Snackbar snackbar = Snackbar.make(mView, "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        View snackView = LayoutInflater.from(mContext).inflate(R.layout.my_snackbar, null);
        layout.addView(snackView, 0);
        TextView textView = (TextView) snackbar.getView().findViewById(R.id.snackbar_tv);
        textView.setText(message);
        textView.setTypeface(AppUtils.getNormalTypeface(mContext));
        snackbar.show();
    }

    public static void applyFontedTab(Activity activity, ViewPager viewPager, TabLayout tabLayout) {
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            CustomTextView tv = (CustomTextView) activity.getLayoutInflater().inflate(R.layout.item_tab, null);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    public static void applyFontedMyContestTab(Activity activity, ViewPager viewPager, TabLayout tabLayout) {
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            CustomTextView tv = (CustomTextView) activity.getLayoutInflater().inflate(R.layout.item_tab_my_contest, null);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    public static void applyFontedMyContestsTab(Activity activity, ViewPager viewPager, TabLayout tabLayout) {
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            CustomTextView tv = (CustomTextView) activity.getLayoutInflater().inflate(R.layout.item_tab_my_contests, null);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }




    public static void applyFontedTabSmall(Activity activity, ViewPager viewPager, TabLayout tabLayout) {
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            CustomTextView tv = (CustomTextView) activity.getLayoutInflater().inflate(R.layout.item_tab_small, null);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }


    public static void applyFontedTabLayout(Activity activity, TabLayout tabLayout) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            CustomTextView tv = (CustomTextView) activity.getLayoutInflater().inflate(R.layout.item_tab_layout, null);
            tv.setText(tabLayout.getTabAt(i).getText());
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    public static void applyHomeFontedTabLayout(Activity activity, TabLayout tabLayout) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            CustomTextView tv = (CustomTextView) activity.getLayoutInflater().inflate(R.layout.item_tab, null);
            tv.setText(tabLayout.getTabAt(i).getText());
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }



    public static void applyIocnFontedTab(Activity activity,  TabLayout tabLayout) {
        int[] imageResId = {
                R.drawable.ic_type_crik,
                R.drawable.ic_type_foot
        };
        ArrayList<String> games = new ArrayList<>();
        games.add(AppUtils.getStrFromRes(R.string.cricket));
        games.add(AppUtils.getStrFromRes(R.string.football));


        for (int i = 0; i < games.size(); i++) {
            LayoutInflater inflater = LayoutInflater.from(activity);

            ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity

                    .findViewById(android.R.id.content)).getChildAt(0);
            //CustomTextView tv = (CustomTextView) activity.getLayoutInflater().inflate(R.layout.item_tab, null);
            View mView = inflater.inflate(R.layout.selector_item_tab, viewGroup,false);
            ImageView tabIcon = (ImageView) mView.findViewById(R.id.tabIcon);
            CustomTextView snackbar_tv = (CustomTextView) mView.findViewById(R.id.snackbar_tv);

            snackbar_tv.setText(games.get(i));
            tabIcon.setImageResource(imageResId[i]);
            tabLayout.getTabAt(i).setCustomView(mView);


        }
    }

    public static void changeTabIcon(FragmentActivity activity, TabLayout tab_sportSelector, int position){

        int[] imageResId = {
                R.drawable.ic_type_yellow_crik,
                R.drawable.ic_type_yellow_foot
        };
        ArrayList<String> games = new ArrayList<>();
        games.add(AppUtils.getStrFromRes(R.string.cricket));
        games.add(AppUtils.getStrFromRes(R.string.football));

        LayoutInflater inflater = LayoutInflater.from(activity);

        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity

                .findViewById(android.R.id.content)).getChildAt(0);
        //CustomTextView tv = (CustomTextView) activity.getLayoutInflater().inflate(R.layout.item_tab, null);
        View mView = inflater.inflate(R.layout.selector_item_tab, viewGroup,false);
        ImageView tabIcon = (ImageView) mView.findViewById(R.id.tabIcon);

        tabIcon.setImageResource(imageResId[position]);
    }

    public static Typeface getNormalTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), AppConfiguration.APP_FONT_REGULAR);
    }

    /*public static void showToast(@NonNull Context mContext, @NonNull String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }*/


    public static void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }


            tabLayout.requestLayout();
        }
    }

    private static void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }


    public static void showToast(@NonNull Context mContext, @NonNull String message) {
        // Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

      /*  Toast toast= Toast.makeText(mContext,
                message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();*/

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toast, null);

        CustomTextView text = (CustomTextView) view.findViewById(R.id.text);
        text.setText(message);

        Toast toast = new Toast(AppController.getContext());
        //toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
        toast.setView(view);
    }

    public static void showPopup(final Activity context, View view, String message) {

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.info_popup, viewGroup);

        int popupWidth = width;
        int popupHeight = ViewUtils.dpToPx(100);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.
        CustomTextView messageText = (CustomTextView) layout.findViewById(R.id.info_text);

        messageText.setText(message);


    }




    public static void showPopup(final Activity context, View view, View root, String message) {

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = root.getWidth();

        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.info_popup, viewGroup);

        int popupWidth = width;
        int popupHeight = ViewUtils.dpToPx(100);
        ;

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        Point centerPoint = getCenterPointOfView(view);


        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        int x = (centerPoint.x);
        int y =  centerPoint.y;

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY,70 ,y);

        // Getting a reference to Close button, and close the popup when clicked.
        CustomTextView messageText = (CustomTextView) layout.findViewById(R.id.info_text);

        messageText.setText(message);


    }

    public static void showPopupBonus(final Activity context, View view, View root, String message) {

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = root.getWidth();

        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.info_popup_bonus, viewGroup);

        int popupWidth = width;
        int popupHeight = ViewUtils.dpToPx(100);
        ;

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        Point centerPoint = getCenterPointOfView(view);


        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        int x = (centerPoint.x);
        int y =  centerPoint.y;

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY,70 ,y);

        // Getting a reference to Close button, and close the popup when clicked.
        CustomTextView messageText = (CustomTextView) layout.findViewById(R.id.info_text);

        messageText.setText(message);


    }


    private static Point getCenterPointOfView(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0] + view.getWidth() / 2;
        int y = location[1] + view.getHeight() / 2;
        return new Point(x, y);
    }



    public static void showPopupright(final Activity context, View view, String message) {

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.info_text_left, viewGroup);

        int popupWidth = width;
        int popupHeight = ViewUtils.dpToPx(100);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.
        CustomTextView messageText = (CustomTextView) layout.findViewById(R.id.info_text);

        messageText.setText(message);


    }

    public static void logoutSession() {
        AppSession.getInstance().clearSession();
        Context context = AppController.getContext();
        Intent startActivity = new Intent();
        startActivity.setClass(context, LoginScreen.class);
        startActivity.setAction(LoginScreen.class.getName());
        startActivity.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(startActivity);
    }

    public static String getNameCharacters(String name) {
        if (TextUtils.isEmpty(name)) return "";
        String first = "";
        String last = "";
        String[] nm = name.split(" ");
        if (nm.length > 0) {
            first = nm[0];
        }
        if (nm.length > 1) {
            last = nm[1];
        }
        return ((String.valueOf(TextUtils.isEmpty(first) ? "" : first.charAt(0))) + (String.valueOf(TextUtils.isEmpty(last) ? "" : last.charAt(0)))) + "";
    }

    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return sslSocketFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            return null;
        }

    }

    public static X509TrustManager getX509TrustManager() {
        return new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        };
    }

    //get the current version number and name
    public static String getVersionInfo() {
        String versionName = "";

        try {
            PackageInfo packageInfo = AppController.getContext().getPackageManager().
                    getPackageInfo(AppController.getContext().getPackageName(), 0);
            versionName = getStrFromRes(R.string.version) + packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    public static void shareTextUrl(Context mContext, String title, String url, String titleText) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, title);
        share.putExtra(Intent.EXTRA_TEXT, url);

        mContext.startActivity(Intent.createChooser(share, titleText));
    }

    public static String gsonToJSON(Object object) {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(object);

        return jsonInString;
    }

    public static String getRawToJson(int resId) {
        InputStream inputStream = AppController.getContext().getResources().openRawResource(resId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString();
    }

    public static void startLogoutService(Context context, String message, boolean onCurrentTime) {
        try {
            Calendar cal = Calendar.getInstance();
            Intent intent = new Intent(context, LogoutService.class);
            intent.putExtra("message", message);
            intent.setData((Uri.parse("SERVICE_playwin_LOGOUT_1001")));
            PendingIntent pintent = PendingIntent.getService(context, 0,
                    intent, 0);
            AlarmManager alarm = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                alarm.setRepeating(AlarmManager.RTC_WAKEUP,
                        cal.getTimeInMillis(),
                        15 * 1000, pintent);
            } else {
                if (onCurrentTime)
                    alarm.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()
                            + (0 * 1000), pintent);
                else alarm.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()
                        + (30 * 1000), pintent);
            }
            Log.i("startLogoutService", "startLogoutService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLogoutService(Context context) {
        try {
            Intent intent = new Intent(context, LogoutService.class);
            intent.setData((Uri.parse("SERVICE_playwin_LOGOUT_1001")));
            PendingIntent pintent = PendingIntent.getService(context, 0, intent, 0);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pintent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String showPriceInIndia(float price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        format.setMaximumFractionDigits(2);
        return format.format(price).trim();
    }

    public static Boolean isValidPassWord(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
        ;
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static String getVersionCode() {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = AppController.getContext().getPackageManager().
                    getPackageInfo(AppController.getContext().getPackageName(), 0);
            versionName = getStrFromRes(R.string.version) + packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return String.valueOf(versionCode);
    }


    public static void clickVibrate(Context context) {
        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(40);

    }


    public static long getSystemTime() {

        /*Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.getTime().getTime();*/

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        return cal.getTimeInMillis();
    }


    public static ArrayList<String> getBidArray() {
        ArrayList<String> tem = new ArrayList<>();
       /* for (int i = 1; i <100 ; i++) {
            tem.add(i+" Lacs");
        }*/
        for (int i = 1; i <= 100; i++) {
            tem.add(i + " Crs");
        }
        return tem;
    }


    public static void applyFontTabLayoutAuction(Activity activity, TabLayout tabLayout) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            CustomTextView tv = (CustomTextView) activity.getLayoutInflater().inflate(R.layout.item_tab_layout_auction, null);
            tv.setText(tabLayout.getTabAt(i).getText());
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }


    public static void auctionBtnEnabledDisable(CustomTextView mCustomTextView, boolean enable) {
        mCustomTextView.setEnabled(enable);
        if (enable) {
            mCustomTextView.setBackgroundResource(R.drawable.bg_auc_btn);
        } else {
            mCustomTextView.setBackgroundResource(R.drawable.bg_auction_btn_yellow_disabled);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String shortPlayerName(String name) {
        String[] splitName = name.split(" ");
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < splitName.length; i++) {
            if (!splitName[i].equalsIgnoreCase(" ")||!splitName[i].isEmpty()){
                stringList.add(splitName[i]);
            }
        }

        String shortName = "";
        for (int i = 0; i < stringList.size(); i++) {
            if (i != (shortName.length() - 1)) {
                if (!stringList.get(i).trim().isEmpty()) {
                    shortName += stringList.get(i).toString().toUpperCase().substring(0, 1) + ".";
                }
            } else {
                shortName += " " + stringList.get(i).substring(0, 1).toUpperCase() + stringList.get(i).substring(1).toLowerCase();
            }
        }
        return shortName.trim();
    }


    public static  String GAME_URL;
    public static String getSportSelectUrl() {

        int gametype = AppSession.getInstance().getGameType();
        switch (gametype) {
            case 1:
                return "";
            case 2:
                return "/football";
            default:
                return "";
        }
    }

    public static final DecimalFormat mDecimalFormat = new DecimalFormat("0.#");

    public static String converDoubleInFormate(double doubleStr) {
        return mDecimalFormat.format(doubleStr);
    }

}
