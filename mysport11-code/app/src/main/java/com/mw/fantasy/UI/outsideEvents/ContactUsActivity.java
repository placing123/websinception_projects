package com.mw.fantasy.UI.outsideEvents;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.mw.fantasy.R;
import com.mw.fantasy.appApi.interactors.IUserInteractor;
import com.mw.fantasy.appApi.interactors.UserInteractor;
import com.mw.fantasy.base.BaseActivity;
import com.mw.fantasy.beanOutput.DefaultRespose;
import com.mw.fantasy.beanOutput.SubjectOutput;
import com.mw.fantasy.customView.CustomEditText;
import com.mw.fantasy.customView.CustomSpinner;
import com.mw.fantasy.dialog.AlertDialog;
import com.mw.fantasy.dialog.ProgressDialog;
import com.mw.fantasy.utility.AppUtils;
import com.mw.fantasy.utility.NetworkUtils;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ContactUsActivity extends BaseActivity {


    private ContactUsActivity mContext;
    private UserInteractor mInteractor;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private List<String> strings = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, ContactUsActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }


    @BindView(R.id.ctv_subject)
    CustomSpinner mCustomSpinnerRole;

    @BindView(R.id.cet_email)
    CustomEditText cet_email;

    @BindView(R.id.cet_mobile)
    CustomEditText cet_mobile;

    @BindView(R.id.cet_message)
    CustomEditText cet_message;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



    @OnClick(R.id.send_message)
    void sendMessage(){
       if (cet_email.getText().toString().isEmpty()){
           Toast.makeText(mContext, "Please enter email address.", Toast.LENGTH_SHORT).show();
       }else if (!cet_email.getText().toString().trim().matches(emailPattern)){
           Toast.makeText(mContext, "Please enter valid email address.", Toast.LENGTH_SHORT).show();
       }else  if (cet_mobile.getText().toString().isEmpty()){
           Toast.makeText(mContext, "Please enter mobile number.", Toast.LENGTH_SHORT).show();
       }else  if (cet_message.getText().toString().isEmpty()){
           Toast.makeText(mContext, "Please enter message.", Toast.LENGTH_SHORT).show();
       }else if (mCustomSpinnerRole.getSelectedTitle().equalsIgnoreCase("Subject")){
           Toast.makeText(mContext, "Please select subject.", Toast.LENGTH_SHORT).show();
       }else {
           sendMessageApi();
       }
    }



    @OnClick(R.id.back)
    void back(){
        finish();
    }


    @OnClick(R.id.btn_fb)
    void btnFb(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/fantasy/"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.btn_inta)
    void btnInsta(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/fantasy/"));
        startActivity(browserIntent);

    }

    @OnClick(R.id.btn_telegram)
    void btnTeleGram(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/joinchat/AAAAAEwiBJcPZ_0iPHivWg"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.btn_twitter)
    void btnTwitter(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/fantasy"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.btn_whatsapp)
    void btnWhatsapp(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919301148160/?text=Please+enable+fantasy+Whatsapp+support+on+my+number+%21"));
        startActivity(browserIntent);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_contact_us;
    }

    @Override
    public void init() {
        mContext = this;
        mInteractor = new UserInteractor();
        mProgressDialog = new ProgressDialog(this);


        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(this,
                R.array.player_role, R.layout.subject_spinner_item);

        getSubject();
    }


    private void sendMessageApi() {
        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();

            HashMap<String, String>  map = new HashMap<>();
            map.put("Email",cet_email.getText().toString());
            map.put("PhoneNumber",cet_mobile.getText().toString());
            map.put("Title",mCustomSpinnerRole.getSelectedTitle());
            map.put("Message",cet_message.getText().toString());

            mInteractor.sendMessage(map, new IUserInteractor.OnSubmitAuctionsPlayersListener() {
                @Override
                public void onSuccess(DefaultRespose defaultRespose) {
                    mProgressDialog.dismiss();
                    cet_email.setText("");
                    cet_mobile.setText("");
                    cet_message.setText("");
                    getSubject();
                    AppUtils.showToast(mContext, defaultRespose.getMessage());
                    //apiCallGetMySquad();
                }

                @Override
                public void onError(String errorMsg) {
                    mProgressDialog.dismiss();
                    mAlertDialog = new AlertDialog(mContext,
                            errorMsg,
                            AppUtils.getStrFromRes(R.string.try_again),
                            AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                        @Override
                        public void onYesClick() {
                            mAlertDialog.hide();
                            sendMessageApi();
                        }

                        @Override
                        public void onNoClick() {
                            mAlertDialog.hide();
                        }
                    });
                    mAlertDialog.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            mAlertDialog = new AlertDialog(mContext,
                    AppUtils.getStrFromRes(R.string.network_error),
                    AppUtils.getStrFromRes(R.string.try_again),
                    AppUtils.getStrFromRes(R.string.cancel), new AlertDialog.OnBtnClickListener() {
                @Override
                public void onYesClick() {
                    mAlertDialog.hide();
                    sendMessageApi();
                }

                @Override
                public void onNoClick() {
                    mAlertDialog.hide();
                }
            });
            mAlertDialog.show();
        }
    }


    private void getSubject() {

        if (NetworkUtils.isConnected(mContext)) {
            mProgressDialog.show();


            mInteractor.getSubject(new IUserInteractor.OnResponseSubjectListener() {


                @Override
                public void onSuccess(SubjectOutput subjectOutput) {
                    mProgressDialog.dismiss();
//                    if (subjectOutput.getData()!=null){
//                        for (int i = 0; i < subjectOutput.getData().size(); i++) {
//                            strings.add(subjectOutput.getData().get(i).getText());
//                        }
//                    }

                    ArrayList<HashMap<String, String>> values = new ArrayList<>();
                    HashMap<String, String> item1 = new HashMap<>();
                    item1.put("value", "");
                    item1.put("title", "Subject");
                    values.add(item1);
                    for (SubjectOutput.SubjectData keyword : subjectOutput.getData()) {
                        HashMap<String, String> item = new HashMap<>();
                        item.put("value", keyword.getID() + "");
                        item.put("title", keyword.getText());
                        values.add(item);
                    }

//                    mCustomSpinnerRole.

                    mCustomSpinnerRole.setCustomResource(new ArrayList<HashMap<String, String>>(values), R.layout.subject_spinner_item, R.layout.subject_item_spinner);

                    mCustomSpinnerRole.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(Spinner parent, View view, int position, long id) {
                            if (mCustomSpinnerRole.getSelectedTitle().equalsIgnoreCase("Subject")){
                                mCustomSpinnerRole.performClick();
                                AppUtils.showToast(mContext,"Please select subject");
                            }

                        }
                    });
//                    mCustomSpinnerRole.setOnItemClickListener(new Spinner.OnItemClickListener() {
//                        @Override
//                        public boolean onItemClick(Spinner parent, View view, int position, long id) {
//                            if (position ==0){
//
//                                AppUtils.showToast(mContext,"Please select subject");
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        mCustomSpinnerRole.performClick();
//                                    }
//                                },100);
//                            }else {
//                                mCustomSpinnerRole.getSelectedTitle();
//                            }
//
//
//                            return true;
//                        }
//                    });

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
                                    getSubject();
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
                                    getSubject();
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
                            getSubject();
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


}
