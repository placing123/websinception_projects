package com.websinception.megastar.UI.personalDetails;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.websinception.megastar.AppConfiguration;
import com.websinception.megastar.AppSession;
import com.websinception.megastar.R;
import com.websinception.megastar.UI.changePassword.ChangePasswordActivity;
import com.websinception.megastar.UI.favoriteTeam.SetFavoriteTeam;
import com.websinception.megastar.UI.verifyAccount.VerifyAccountActivity;
import com.websinception.megastar.appApi.interactors.UserInteractor;
import com.websinception.megastar.base.BaseActivity;
import com.websinception.megastar.base.Loader;
import com.websinception.megastar.beanInput.LoginInput;
import com.websinception.megastar.beanInput.UpdateProfileInput;
import com.websinception.megastar.beanOutput.LoginResponseOut;
import com.websinception.megastar.beanOutput.ResponseCountries;
import com.websinception.megastar.beanOutput.ResponseLogin;
import com.websinception.megastar.beanOutput.ResponseUpdateProfile;
import com.websinception.megastar.beanOutput.StatesBean;
import com.websinception.megastar.customView.CustomInputEditText;
import com.websinception.megastar.customView.CustomSpinner;
import com.websinception.megastar.customView.CustomTextView;
import com.websinception.megastar.dialog.ProgressDialog;
import com.websinception.megastar.utility.AppUtils;
import com.websinception.megastar.utility.Constant;
import com.websinception.megastar.utility.TimeUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class PersonalDetailsActivity extends BaseActivity implements PersonalDetailsView {


    PersonalDetailsPresenterImpl mPersonalDetailsPresenter;
    String gender;
    String notificationStatus;
    UpdateProfileInput mUpdateProfileInput;
    Context mContext;
    @BindView(R.id.relative_layout)
    RelativeLayout mRelativeLayout;
    @BindString(R.string.personalDetail)
    String title;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    CustomTextView mCustomTextViewTitle;
    @BindView(R.id.set_favorite_team)
    CustomTextView set_favorite_team;
    @BindView(R.id.ciet_name)
    CustomInputEditText mCustomInputEditTextName;
    @BindView(R.id.ciet_email)
    CustomInputEditText mCustomInputEditTextEmail;
    @BindView(R.id.ciet_password)
    CustomInputEditText mCustomInputEditTextPassword;
    @BindView(R.id.ciet_your_fav_team)
    CustomInputEditText mFavTeam;
    @BindView(R.id.ctv_set_dob)
    CustomTextView mCustomInputEditTextDob;
    @BindView(R.id.ciet_address)
    CustomInputEditText mCustomInputEditTextAddress;
    @BindView(R.id.ciet_city)
    CustomInputEditText mCustomInputEditTextCity;
    @BindView(R.id.ciet_pincode)
    CustomInputEditText mCustomInputEditTextPincode;
    @BindView(R.id.spinner_states)
    CustomSpinner mSpinnerStates;
    @BindView(R.id.rb_gender)
    RadioGroup mRadioGroupGender;
    @BindView(R.id.rb_female)
    RadioButton mRadioButtonFemale;
    @BindView(R.id.rb_male)
    RadioButton mRadioButtonMale;
    @BindView(R.id.toggle_btn)
    Switch mToggleButton;
    ArrayList<String> mTeamList = new ArrayList<>();
    ArrayList<String> mTeamListName = new ArrayList<>();
    ResponseLogin responseLogin;
    private ProgressDialog mProgressDialog;
    private Loader loader;
    private boolean isStateInitialise = false;

    public static void start(Context context) {
        Intent starter = new Intent(context, PersonalDetailsActivity.class);

        context.startActivity(starter);
    }
    public static Intent startForResult(Context context) {
        Intent starter = new Intent(context, PersonalDetailsActivity.class);

        return starter;
    }

    @OnClick(R.id.ctv_change_pwd)
    public void changePasswordOnClick() {
        ChangePasswordActivity.start(this);
    }

    @OnClick(R.id.ctv_verify)
    public void verifyAccOnClick() {
        VerifyAccountActivity.start(this);
    }

    @OnClick(R.id.ctv_set_dob)
    public void onClickSetDob() {
        setDate();
    }

    @OnClick(R.id.back)
    void onBackClick(){
        finish();
    }

    @OnClick(R.id.set_favorite_team)
    public void setFavorite_team() {

       // SetFavoriteTeam.start(this);
        Intent i = new Intent(this, SetFavoriteTeam.class);
        i.putStringArrayListExtra("mTeamList", mTeamListName);
        startActivityForResult(i, 1);
    }

    @OnClick(R.id.ctv_update_profile_btn)
    public void updateProfileOnClick() {

        String name = mCustomInputEditTextName.getText().toString().trim();
        String dob = mCustomInputEditTextDob.getText().toString().trim();
        String address = mCustomInputEditTextAddress.getText().toString().trim();
        String city = mCustomInputEditTextCity.getText().toString().trim();
        String state = mSpinnerStates.getSelectedTitle();

        String pincode = mCustomInputEditTextPincode.getText().toString().trim();
        String country = "101";
        JsonArray mJSONArray = new JsonArray();
        for (int i = 0; i < mTeamList.size(); i++) {
            mJSONArray.add(mTeamList.get(i));
        }

        mUpdateProfileInput.setFirstName(name);
        mUpdateProfileInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mUpdateProfileInput.setBirthDate(dob);
        mUpdateProfileInput.setCityName(city);
        mUpdateProfileInput.setPostal(pincode);
        mUpdateProfileInput.setAddress(address);
        mUpdateProfileInput.setCountryCode(country);

        mUpdateProfileInput.setStateName(state);
        mUpdateProfileInput.setGender(gender);

        mUpdateProfileInput.setAddress(address);

        Log.d("mJSONArrayData", mJSONArray.toString());
        //mUpdateProfileInput.setFavorite_teams(mJSONArray.toString());

        mPersonalDetailsPresenter.actionUpdateProfile(mUpdateProfileInput);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_personal_details;
    }

    @Override
    public void init() {

        mContext = this;
        setSupportActionBar(mToolbar);
        loader = new Loader(this);
        /*getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_back);*/
        mCustomTextViewTitle.setText(title);

        mUpdateProfileInput = new UpdateProfileInput();

        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            mRadioButtonMale.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_male,0,0,0);
            mRadioButtonFemale.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_female,0,0,0);
        }


        mPersonalDetailsPresenter = new PersonalDetailsPresenterImpl(this, new UserInteractor());

        try {
             Gson gson = new Gson();
            String jsonStates = AppUtils.AssetJSONFile("states.json", mContext);

            Log.d("jsonStatesS",jsonStates);
            StatesBean mStatesBean = gson.fromJson(jsonStates, StatesBean.class);

            mSpinnerStates.setJsonResource(mStatesBean);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // mPersonalDetailsPresenter.actionStatesBtn(AppSession.getInstance().getLoginSession().getData().getSessionKey(), "101");
        callViewProfile();

        mRadioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_male:
                        gender = Constant.Male;
                        break;
                    case R.id.rb_female:
                        gender = Constant.Female;
                        break;
                    default:
                        gender =Constant.Other;
                }
            }
        });

        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificationStatus = "1";
                } else
                    notificationStatus = "0";
            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null) mProgressDialog.dismiss();

    }



    @Override
    public void onProfileSuccess(final LoginResponseOut responseLogin) {
        hideLoading();
        mCustomInputEditTextName.setText(responseLogin.getData().getFirstName());
        mCustomInputEditTextEmail.setText(responseLogin.getData().getEmail());
        mCustomInputEditTextEmail.setEnabled(false);
        mCustomInputEditTextPassword.setText(getResources().getString(R.string.password));
        mCustomInputEditTextPassword.setEnabled(false);
        //2000-12-13 00:00:00
        mCustomInputEditTextDob.setText(TimeUtils.getDateByFormatInput(responseLogin.getData().getBirthDate(),
                "yyyy-MM-dd", "yyyy-MM-dd"));
        mCustomInputEditTextPincode.setText(responseLogin.getData().getPostal());
        /*if (!responseLogin.getData().getPinCode().equals("0")) {

        }*/


        mCustomInputEditTextCity.setText(responseLogin.getData().getCityName());
        if (responseLogin.getData().getStateName() != null) {
            setState(responseLogin.getData().getStateName());
        }
        if (!isStateInitialise && responseLogin != null && !TextUtils.isEmpty(responseLogin.getData().getStateName())) {
            isStateInitialise = true;
            mSpinnerStates.setTitle(responseLogin.getData().getStateName());
        } else {
            mSpinnerStates.setValue("");
        }
        String teamsName = "";

        for (int i = 0; responseLogin.getData().getMyFavouriteTeams()!=null&&i<responseLogin.getData().getMyFavouriteTeams().size(); i++) {
            mTeamListName.add(responseLogin.getData().getMyFavouriteTeams().get(i));

            if (teamsName.length() == 0) {
                teamsName = responseLogin.getData().getMyFavouriteTeams().get(i);
            } else {
                teamsName = teamsName + "," + responseLogin.getData().getMyFavouriteTeams().get(i);
            }
        }
        mFavTeam.setText(teamsName);

       /* switch (AppSession.getInstance().getGameType()) {

            case 1:

                for (int i = 0; i < responseLogin.getResponse().getFavoriteTeams().size(); i++) {
                    mTeamListName.add(responseLogin.getResponse().getFavoriteTeams().get(i).getTeamName());

                    if (teamsName.length() == 0) {
                        teamsName = responseLogin.getResponse().getFavoriteTeams().get(i).getTeamName();
                    } else {
                        teamsName = teamsName + "," + responseLogin.getResponse().getFavoriteTeams().get(i).getTeamName();
                    }
                }
                break;
            case 2:
                for (int i = 0; i < responseLogin.getResponse().getFootballFavoriteTeams().size(); i++) {
                    mTeamListName.add(responseLogin.getResponse().getFootballFavoriteTeams().get(i).getTeamName());

                    if (teamsName.length() == 0) {
                        teamsName = responseLogin.getResponse().getFootballFavoriteTeams().get(i).getTeamName();
                    } else {
                        teamsName = teamsName + "," + responseLogin.getResponse().getFootballFavoriteTeams().get(i).getTeamName();
                    }
                }
                break;
            case 3:

                break;
        }

        mFavTeam.setText(teamsName);
*/

        if (responseLogin.getData().getGender() != null) {
            if (responseLogin.getData().getGender().equals(Constant.Female)) {
                gender = Constant.Female;
                mRadioButtonFemale.setChecked(true);
            } else {
                gender = Constant.Male;
                mRadioButtonMale.setChecked(true);
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onProfileFailure(String errMsg) {
        hideLoading();
        showSnackBar(errMsg);
    }

    @Override
    public void onStatesSuccess(ResponseCountries responseStates) {
        hideLoading();
        ArrayList<HashMap<String, String>> values = new ArrayList<>();
        HashMap<String, String> item1 = new HashMap<>();
        item1.put("value", "");
        item1.put("title", AppUtils.getStrFromRes(R.string.select_state));
        values.add(item1);
        for (ResponseCountries.ResponseBean keyword : responseStates.getResponse()) {
            HashMap<String, String> item = new HashMap<>();
            item.put("value", keyword.getId() + "");
            item.put("title", keyword.getName());
            values.add(item);
        }
        mSpinnerStates.setCustomResource(new ArrayList<HashMap<String, String>>(values));
        if (!isStateInitialise && responseLogin != null && !TextUtils.isEmpty(responseLogin.getResponse().getState())) {
            isStateInitialise = true;
            mSpinnerStates.setValue(responseLogin.getResponse().getState());
        } else {
            mSpinnerStates.setValue("");
        }

        callViewProfile();

    }

    void callViewProfile() {
        LoginInput mLoginInput = new LoginInput();
        mLoginInput.setSessionKey(AppSession.getInstance().getLoginSession().getData().getSessionKey());
        mLoginInput.setUserGUID(AppSession.getInstance().getLoginSession().getData().getUserGUID());
        mLoginInput.setParams(Constant.GET_PROFILE_PARAMS);
        mPersonalDetailsPresenter.actionViewProfile(mLoginInput);
    }

    @Override
    public void onStatesFailure(String errMsg) {
        hideLoading();
        showSnackBar(errMsg);
    }

    public void setState(@NonNull String state) {
        mSpinnerStates.setValue(state);
    }

    void setDate() {

        Calendar c = Calendar.getInstance();


        c.add(Calendar.YEAR, AppConfiguration.MINIMUM_AGE);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog dd = new DatePickerDialog(PersonalDetailsActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String dateInString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            Date date = formatter.parse(dateInString);
                            String dateStr = (formatter.format(date).toString());
                            mCustomInputEditTextDob.setText(dateStr);

                        } catch (Exception ex) {

                            ex.printStackTrace();
                        }

                    }
                }, mYear, mMonth, mDay);
        dd.getDatePicker().setMaxDate(c.getTimeInMillis());
        dd.show();


    }

    @Override
    public void showSnackBar(String message) {
        hideLoading();
        AppUtils.showSnackBar(this, mRelativeLayout, message);
    }

    @Override
    public void onUpdateSuccess(ResponseUpdateProfile updateProfile) {
        hideLoading();

        showSnackBar(updateProfile.getMessage());
        Intent in = getIntent();
        in.putExtra("updateProfile", updateProfile);
        setResult(RESULT_OK, in);
        finish();

    }

    @Override
    public void onUpdateFailure(String errMsg) {
        hideLoading();
        showSnackBar(errMsg);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    String teamsName = "";
                    ArrayList<String> myList;
                    try {
                         myList = data.getStringArrayListExtra("favouriteTeamsList");

                        if (myList != null) {
                            for (int i =0 ; i<myList.size() ; i++){
                                if (teamsName.length() == 0) {
                                    teamsName = myList.get(i);
                                } else {
                                    teamsName = teamsName + "," + myList.get(i);
                                }
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    mFavTeam.setText(teamsName);
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

    }
}