package app.biblion.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.biblion.R;
import app.biblion.model.RegisterModel;
import app.biblion.sessionmanager.SessionManager;
import app.biblion.util.Biblion;
import app.biblion.util.ConnectivityReceiver;
import app.biblion.util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private static final String TAG = "RegisterActivity";
    EditText edtRFullname, edtRUsername, edtRBirtthdate, edtREmail, edtRpwd, edtRConfPwd, edtRmobileno, edtRCountry, edtRState, edtRCity;
    Button btnRegistration;
    Calendar myCalendar;
    TextView txt_Login;
    private DatePickerDialog mDatePickerDialog;
    private AlertDialog alertDialogObjectCountry, alertDialogObjectState, alertDialogObjectCity;
    RadioButton radioMale, radioFemale;
    DatePickerDialog.OnDateSetListener date;
    String mUserName, mFullName, mDevice_id, mFirebase_id, mPassword, mEmail, mBrithdate, mMobile_Number, mConfPwd, mCountry, mState, mCity;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String mGnder = "Male";
    RadioGroup radio_group;
    int mslectedGander;
    SessionManager sessionManager;
    String selectedCountry, selectedState, selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialization();

    }

    private void initialization() {
        mDevice_id = Settings.Secure.getString(RegisterActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        edtRmobileno = findViewById(R.id.txt_mobileno);
        edtRUsername = findViewById(R.id.edt_username);
        edtRBirtthdate = findViewById(R.id.edt_birthdate);
        edtREmail = findViewById(R.id.edt_email);
        edtRpwd = findViewById(R.id.edt_pwd);
        edtRConfPwd = findViewById(R.id.edt_cnfpwd);
        edtRCountry = findViewById(R.id.edt_country);
        edtRState = findViewById(R.id.edt_state);
        edtRCity = findViewById(R.id.edt_city);
        btnRegistration = findViewById(R.id.btn_registration);
        txt_Login = findViewById(R.id.txt_login_text);
        radioMale = findViewById(R.id.radiobtn_male);
        radioFemale = findViewById(R.id.radiobtn_female);
        radio_group = findViewById(R.id.radio_group);
        sessionManager = new SessionManager(RegisterActivity.this);
        datePicker();
        setDateTimeField();
        ShowCountryList();
        ShowStateList();
        ShowCityList();
        btnRegistrationClick();
        mslectedGander = radio_group.getCheckedRadioButtonId();
        Log.e(TAG, "initialization: " + mslectedGander);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobtn_male:
                        mGnder = "Male";
                        break;
                    case R.id.radiobtn_female:
                        mGnder = "Female";
                        break;

                }
            }
        });
    }


    private void datePicker() {

        edtRBirtthdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog.show();
                return false;
            }
        });

        txt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        edtRCountry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                alertDialogObjectCountry.show();
                return false;
            }
        });
        edtRState.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                alertDialogObjectState.show();
                return false;
            }
        });
        edtRCity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                alertDialogObjectCity.show();
                return false;
            }
        });
    }

    private void btnRegistrationClick() {
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValue();
                checkConnection();
            }
        });
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                edtRBirtthdate.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void getValue() {

        mFullName = edtRUsername.getText().toString().trim();
        mUserName = edtRUsername.getText().toString().trim();
        mEmail = edtREmail.getText().toString().trim();
        mPassword = edtRpwd.getText().toString().trim();
        mMobile_Number = edtRmobileno.getText().toString().trim();
        mBrithdate = edtRBirtthdate.getText().toString().trim();
        mCountry = edtRCountry.getText().toString().trim();
        mState = edtRState.getText().toString().trim();
        mCity = edtRCity.getText().toString().trim();
        mConfPwd = edtRConfPwd.getText().toString().trim();
        validation();
    }

    private void validation() {
        if (mUserName.equalsIgnoreCase("")) {
            edtRUsername.setError("Required");
            edtRUsername.setFocusable(true);
        } else if (mBrithdate.equalsIgnoreCase("")) {
            edtRBirtthdate.setError("Required");
            edtRBirtthdate.setFocusable(true);
        } else if (mMobile_Number.equalsIgnoreCase("")) {
            edtREmail.setError("Required");
            edtREmail.setFocusable(true);
        } else if (mEmail.equalsIgnoreCase("")) {
            edtREmail.setError("Required");
            edtREmail.setFocusable(true);
        } else if (mPassword.equalsIgnoreCase("")) {
            edtRpwd.setError("Required");
            edtRpwd.setFocusable(true);
        }  else if (mConfPwd.equalsIgnoreCase("")) {
            edtRConfPwd.setError("Required");
            edtRConfPwd.setFocusable(true);
        } else if (!mEmail.matches(emailPattern)) {
            edtREmail.setError("invalid email");
            edtREmail.setFocusable(true);
        } else if (mCountry.equals("")) {
            edtRCountry.setError("Required");
            edtRCountry.setFocusable(true);
        } else if (mState.equals("")) {
            edtRState.setError("Required");
            edtRState.setFocusable(true);
        } else if (mCity.equals("")) {
            edtRCity.setError("Required");
            edtRCity.setFocusable(true);
        } else if (!mPassword.equals(mConfPwd)) {
            Constant.toast("Password not match", RegisterActivity.this);
        } else {
            RegistrationAPI_CAll();
        }

    }

    private void RegistrationAPI_CAll() {

        File file = new File("/storage/emulated/0/Download/abc.jpg");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody Email =
                RequestBody.create(MediaType.parse("multipart/form-data"), mEmail);
        RequestBody Password =
                RequestBody.create(MediaType.parse("multipart/form-data"), mPassword);
        RequestBody firebase_id =
                RequestBody.create(MediaType.parse("multipart/form-data"), "fgdjsgjgha");
        RequestBody Device_id =
                RequestBody.create(MediaType.parse("multipart/form-data"), mDevice_id);
        RequestBody FullName =
                RequestBody.create(MediaType.parse("multipart/form-data"), mFullName);
        RequestBody Brithdate =
                RequestBody.create(MediaType.parse("multipart/form-data"), mBrithdate);
        RequestBody Gnder =
                RequestBody.create(MediaType.parse("multipart/form-data"), mGnder);
        RequestBody UserName =
                RequestBody.create(MediaType.parse("multipart/form-data"), mUserName);
        RequestBody Mobile_Number =
                RequestBody.create(MediaType.parse("multipart/form-data"), mMobile_Number);
        RequestBody country =
                RequestBody.create(MediaType.parse("multipart/form-data"), mCountry);
        RequestBody State =
                RequestBody.create(MediaType.parse("multipart/form-data"), mState);
        RequestBody city =
                RequestBody.create(MediaType.parse("multipart/form-data"), mCity);

        Constant.progressDialog(RegisterActivity.this);
        Call<RegisterModel> modelCall = Constant.apiService.
                getRegisterDetails(Email, Password, firebase_id, Device_id, FullName, Brithdate, Gnder, UserName, Mobile_Number, country, State, city, body);
        modelCall.enqueue(new Callback<RegisterModel>() {
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
                    finish();
                }


            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Constant.progressBar.dismiss();

            }
        });

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtRBirtthdate.setText(sdf.format(myCalendar.getTime()));
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (!isConnected) {
            message = "Internet Not Connected! Please Connect";
            color = Color.RED;
            Snackbar snackbar = Snackbar.make(findViewById(R.id.rviewsnack), message, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        Biblion.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    public void ShowCountryList() {
        List<String> mCountrylist = new ArrayList<String>();
        mCountrylist.add("USA");
        mCountrylist.add("RUSSIA");
        mCountrylist.add("INDIA");
        mCountrylist.add("UK");
        mCountrylist.add("AUSTRALIA");
        mCountrylist.add("SPAIN");
        //Create sequence of items
        final CharSequence[] Animals = mCountrylist.toArray(new String[mCountrylist.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Country");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selectedCountry = Animals[item].toString();  //Selected item in listview
                edtRCountry.setText(selectedCountry);
            }
        });
        //Create alert dialog object via builder
        alertDialogObjectCountry = dialogBuilder.create();
        //Show the dialog
        //alertDialogObject.show();
    }

    public void ShowStateList() {
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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("State");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selectedState = Animals[item].toString();  //Selected item in listview
                edtRState.setText(selectedState);
            }
        });
        //Create alert dialog object via builder
        alertDialogObjectState = dialogBuilder.create();
        //Show the dialog
        //alertDialogObject.show();
    }

    public void ShowCityList() {
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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("City");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selectedCity = Animals[item].toString();  //Selected item in listview
                edtRCity.setText(selectedCity);
            }
        });
        //Create alert dialog object via builder
        alertDialogObjectCity = dialogBuilder.create();
        //Show the dialog
        //alertDialogObject.show();
    }
}
