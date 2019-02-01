package app.biblion.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.biblion.R;
import app.biblion.activity.LoginActivity;
import app.biblion.activity.NavigationActivity;
import app.biblion.activity.RegisterActivity;
import app.biblion.model.EditProfileModel;
import app.biblion.model.RegisterModel;
import app.biblion.sessionmanager.SessionManager;
import app.biblion.util.Constant;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import belka.us.androidtoggleswitch.widgets.util.ToggleSwitchButton;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment implements View.OnClickListener {


    View mView;
    private static final String TAG = "Accountfragment";
    Context mContext;
    EditText edtPFullname, edtPUsername, edtPBirtthdate, edtPEmail, edtPpwd, edtPConfPwd, edtPMobileno, edtPCountry, edtPState, edtPCity;
    Button btnUpdate;
    Calendar myCalendar;
    RadioButton prof_radioMale, prof_radioFemale;
    ImageView prof_camera_image,  edit_Username, edit_MobileNo, edit_DOB, edit_Country, edit_State, edit_City;
    CircleImageView prof_imageView;
    RadioGroup prof_radio_group;
    String filePath="null";
    String mGoogleimage="null";
    String mDevice_id;
    String mGnder = "Male";
    String profselectedCountry, profselectedState, profselectedCity;
    int mslectedGender;
    ToggleSwitchButton toggleSwitchButton;
    ToggleSwitch toggleSwitch;
    int togglePosition = 0;
    private SessionManager sessionManager;
    String pUserName, pFullName, pFirebase_id, pEmail, pBrithdate, pMobile_Number, pCountry, pState, pCity;
    private DatePickerDialog profDatePickerDialog;
    private AlertDialog prof_alertDialogObjectCountry, prof_alertDialogObjectState, prof_alertDialogObjectCity;
    Call<EditProfileModel> editProfileModelCall;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_account, container, false);
        getActivity().setTitle("Account Setting");
        sessionManager = new SessionManager(getActivity());
        initialization();
        getDataFromSession();
        return mView;
    }

    private void initialization()
    {
        mDevice_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        edtPMobileno = mView.findViewById(R.id.edt_profmobileno);
        edtPUsername = mView.findViewById(R.id.edt_profusername);
        edtPBirtthdate = mView.findViewById(R.id.edt_profbirthdate);
        edtPEmail = mView.findViewById(R.id.edt_profemail);
        edtPCountry = mView.findViewById(R.id.edt_profcountry);
        edtPState = mView.findViewById(R.id.edt_profstate);
        edtPCity = mView.findViewById(R.id.edt_profcity);
        btnUpdate = mView.findViewById(R.id.btn_profupdate);
       /* prof_radioMale = mView.findViewById(R.id.profradiobtn_male);
        prof_radioFemale = mView.findViewById(R.id.profradiobtn_female);
        prof_radio_group = mView.findViewById(R.id.profradio_group);*/
        prof_camera_image = mView.findViewById(R.id.prof_camera_image);
        prof_imageView = mView.findViewById(R.id.acc_profile_image);
        prof_camera_image.setClickable(true);
        edtPUsername.setInputType(InputType.TYPE_CLASS_TEXT);

        toggleSwitch = mView.findViewById(R.id.gender_switch);

        edit_Username = mView.findViewById(R.id.username_edit);
        edit_MobileNo = mView.findViewById(R.id.mobileno_edit);
        edit_DOB =mView.findViewById(R.id.birthdate_edit);
        edit_Country = mView.findViewById(R.id.country_edit);
        edit_State = mView.findViewById(R.id.state_edit);
        edit_City = mView.findViewById(R.id.city_edit);

        edtPEmail.setEnabled(false);
        edtPUsername.setEnabled(false);
        edtPMobileno.setEnabled(false);
        edtPBirtthdate.setEnabled(false);
        edtPCountry.setEnabled(false);
        edtPState.setEnabled(false);
        edtPCity.setEnabled(false);

        setAccountDateTimeField();
        ShowProfileCountryList();
        ShowProfileStateList();
        ShowProfileCityList();
        updateClicked();

       togglePosition = toggleSwitch.getCheckedTogglePosition();

       toggleSwitch.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {
           @Override
           public void onToggleSwitchChangeListener(int position, boolean isChecked) {

               switch (position)
               {
                   case 0:
                       mGnder ="Male";
                       Toast.makeText(getActivity(),mGnder, Toast.LENGTH_SHORT).show();
                       break;

                   case 1:
                       mGnder = "Female";
                       Toast.makeText(getActivity(),mGnder, Toast.LENGTH_SHORT).show();
                       break;
               }

           }
       });

        /*mslectedGender = prof_radio_group.getCheckedRadioButtonId();
        Log.e(TAG, "initialization: " + mslectedGender);
        prof_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.profradiobtn_male:
                        mGnder = "Male";
                        break;
                    case R.id.profradiobtn_female:
                        mGnder = "Female";
                        break;

                }
            }
        });*/

    }

    public void getDataFromSession()
    {
        HashMap<String, String> user = sessionManager.getUserDetails();

        String A_Name = user.get(sessionManager.KEY_NAME);
        String A_Email = user.get(sessionManager.KEY_EMAIL);
        String A_MobileNo = user.get(sessionManager.KEY_MOBILE);
        String A_DOB = user.get(sessionManager.KEY_DOB);
        String A_Gender = user.get(sessionManager.KEY_GENDER);

        edtPUsername.setText(A_Name);
        edtPEmail.setText(A_Email);
        edtPMobileno.setText(A_MobileNo);
        edtPBirtthdate.setText(A_DOB);



    }
    private void updateClicked()
    {

        edit_Username.setOnClickListener(this);
        edit_MobileNo.setOnClickListener(this);
        edit_DOB.setOnClickListener(this);
        edit_Country.setOnClickListener(this);
        edit_State.setOnClickListener(this);
        edit_City.setOnClickListener(this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValue();
                edit_Username.setVisibility(View.VISIBLE);
                edit_MobileNo.setVisibility(View.VISIBLE);
                edit_DOB.setVisibility(View.VISIBLE);
                edit_Country.setVisibility(View.VISIBLE);
                edit_State.setVisibility(View.VISIBLE);
                edit_City.setVisibility(View.VISIBLE);
                edtPUsername.setEnabled(false);
                edtPMobileno.setEnabled(false);
                edtPBirtthdate.setEnabled(false);
                edtPCountry.setEnabled(false);
                edtPState.setEnabled(false);
                edtPCity.setEnabled(false);
                Toast.makeText(getActivity(), "Updated" + mGnder, Toast.LENGTH_SHORT).show();

            }
        });

        edtPBirtthdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                profDatePickerDialog.show();
                return false;
            }
        });

    }
    private void setAccountDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        profDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                edtPBirtthdate.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        profDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void getValue() {

        pUserName = edtPUsername.getText().toString().trim();
        pEmail = edtPEmail.getText().toString().trim();
        pMobile_Number = edtPMobileno.getText().toString().trim();
        pBrithdate = edtPBirtthdate.getText().toString().trim();
        pCountry = edtPCountry.getText().toString().trim();
        pState = edtPState.getText().toString().trim();
        pCity = edtPCity.getText().toString().trim();
        validation();
    }
    private void validation() {
        if (pUserName.equalsIgnoreCase("")) {
            edtPUsername.setError("Required");
            edtPUsername.setFocusable(true);
        }  else if (pMobile_Number.equalsIgnoreCase("")) {
            edtPMobileno.setError("Required");
            edtPMobileno.setFocusable(true);
        } else if (pBrithdate.equalsIgnoreCase("")) {
            edtPBirtthdate.setError("Required");
            edtPBirtthdate.setFocusable(true);
        } /*else if (pCountry.equals("")) {
            edtPCountry.setError("Required");
            edtPCountry.setFocusable(true);
        } else if (pState.equals("")) {
            edtPState.setError("Required");
            edtPState.setFocusable(true);
        } else if (pCity.equals("")) {
            edtPCity.setError("Required");
            edtPCity.setFocusable(true);
        }*/
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case  R.id.username_edit:
                edtPUsername.setEnabled(true);
                edit_Username.setVisibility(View.INVISIBLE);
                break;
            case R.id.mobileno_edit:
                edtPMobileno.setEnabled(true);
                edit_MobileNo.setVisibility(View.INVISIBLE);
                break;
            case R.id.birthdate_edit:
                edtPBirtthdate.setEnabled(true);
                profDatePickerDialog.show();
                edit_DOB.setVisibility(View.INVISIBLE);
                break;
            case R.id.country_edit:
                edtPCountry.setEnabled(true);
                prof_alertDialogObjectCountry.show();
                edit_Country.setVisibility(View.INVISIBLE);
                break;
            case R.id.state_edit:
                edtPState.setEnabled(true);
                prof_alertDialogObjectState.show();
                edit_State.setVisibility(View.INVISIBLE);
                break;
            case R.id.city_edit:
                edtPCity.setEnabled(true);
                prof_alertDialogObjectCity.show();
                edit_City.setVisibility(View.INVISIBLE);
                break;
        }
    }
    private void UpdatedAPI_Call()
    {
        File file = new File(filePath);
        RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestfile);

        RequestBody u_Username =
                RequestBody.create(MediaType.parse("multipart/form-data"), pUserName);
        RequestBody u_MobileNo =
                MultipartBody.create(MediaType.parse("multipart/form-data"),pMobile_Number);
        RequestBody u_Birthdate =
                MultipartBody.create(MediaType.parse("multipart/form-data"),pBrithdate);
        RequestBody u_Firebase_id =
                RequestBody.create(MediaType.parse("multipart/form-data"), "fgdjsgjgha");
        RequestBody u_Device_id =
                RequestBody.create(MediaType.parse("multipart/form-data"), mDevice_id);
        RequestBody u_Gender =
                RequestBody.create(MediaType.parse("multipart/form-data"), mGnder);
        RequestBody u_Country =
                RequestBody.create(MediaType.parse("multipart/form-data"), pCountry);
        RequestBody u_State =
                RequestBody.create(MediaType.parse("multipart/form-data"), pState);
        RequestBody u_City =
                RequestBody.create(MediaType.parse("multipart/form-data"), pCity);

        Constant.progressDialog(getActivity());
        /*if(mGoogleimage.equals("null")) {
            editProfileModelCall = Constant.apiService.
                    getEditDetails(u_Usernameu_Firebase_id,u_Device_id,u_Birthdate,u_Gender,, u_MobileNo, u_Country, u_State, u_City, body);
        }else {
            modelCall = Constant.apiService.
                    getRegisterDetailsGoogle(mEmail, mPassword, "abcd", mDevice_id, mFullName, mBrithdate, mGnder, mUserName, mMobile_Number, mCountry, mState, mCity,mGoogleimage);
        }
        modelCall.enqueue(new Callback<EditProfileModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {

                if (response.body().getStatus().equals("1")) {
                    Constant.toast(response.body().getMessage(), RegisterActivity.this);
                    Constant.progressBar.dismiss();
                } else {
                    sessionManager.createLoginSession(mEmail, mPassword, response.body().getResult().getName(),
                            response.body().getResult().getGender(), response.body().getResult().getDob(),
                            response.body().getResult().getDevice_id(), response.body().getResult().getMobile(),
                            response.body().getResult().getFirebase_id());
                    Constant.intent(RegisterActivity.this, NavigationActivity.class);
                    Constant.progressBar.dismiss();

                }

            }
        });*/
    }

            public void ShowProfileCountryList() {
                List<String> mCountrylist = new ArrayList<String>();
                mCountrylist.add("USA");
                mCountrylist.add("RUSSIA");
                mCountrylist.add("INDIA");
                mCountrylist.add("UK");
                mCountrylist.add("AUSTRALIA");
                mCountrylist.add("SPAIN");
                //Create sequence of items
                final CharSequence[] Animals = mCountrylist.toArray(new String[mCountrylist.size()]);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Country");
                dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        profselectedCountry = Animals[item].toString();  //Selected item in listview
                        edtPCountry.setText(profselectedCountry);
                    }
                });
                //Create alert dialog object via builder
                prof_alertDialogObjectCountry = dialogBuilder.create();
                //Show the dialog
                //alertDialogObject.show();
            }

            public void ShowProfileStateList() {
                List<String> mStatelist = new ArrayList<String>();
                mStatelist.add("Gujarat");
                mStatelist.add("Maharashtra");
                mStatelist.add("Madhya Pradesh");
                mStatelist.add("Rajsthan");
                mStatelist.add("Punjab");
                mStatelist.add("Haryan");
                mStatelist.add("Delhi");
                mStatelist.add("Uttar Pradesh");
                mStatelist.add("Karnataka");
                mStatelist.add("Delhi");
                mStatelist.add("Andhra Pradesh");
                mStatelist.add("Himachal Pradesh");
                //Create sequence of items
                final CharSequence[] Animals = mStatelist.toArray(new String[mStatelist.size()]);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("State");
                dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        profselectedState = Animals[item].toString();  //Selected item in listview
                        edtPState.setText(profselectedState);
                    }
                });
                //Create alert dialog object via builder
                prof_alertDialogObjectState = dialogBuilder.create();
                //Show the dialog
                //alertDialogObject.show();
            }

            public void ShowProfileCityList() {
                List<String> mCitylist = new ArrayList<String>();
                mCitylist.add("Ahmedabad");
                mCitylist.add("Surat");
                mCitylist.add("Vadodara");
                mCitylist.add("Rajkot");
                mCitylist.add("Bhavnagar");
                mCitylist.add("Jamnagar");
                mCitylist.add("Junagadh");
                mCitylist.add("Anand");
                mCitylist.add("Surendranagar");
                mCitylist.add("Navsari");
                mCitylist.add("Anand");
                mCitylist.add("Kheda");
                //Create sequence of items
                final CharSequence[] Animals = mCitylist.toArray(new String[mCitylist.size()]);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("City");
                dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        profselectedCity = Animals[item].toString();  //Selected item in listview
                        edtPCity.setText(profselectedCity);
                    }
                });
                //Create alert dialog object via builder
                prof_alertDialogObjectCity = dialogBuilder.create();
                //Show the dialog
                //alertDialogObject.show();
            }
        }