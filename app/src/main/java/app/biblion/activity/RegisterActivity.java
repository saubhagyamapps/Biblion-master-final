package app.biblion.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.biblion.R;
import app.biblion.model.RegisterModel;
import app.biblion.sessionmanager.SessionManager;
import app.biblion.util.Biblion;
import app.biblion.util.ConnectivityReceiver;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private static final String TAG = "RegisterActivity";
    EditText edtRFullname, edtRUsername, edtRBirtthdate, edtREmail, edtRpwd, edtRConfPwd, edtRmobileno;
    Button btnRegistration;
    Calendar myCalendar;
    TextView txt_Login;
    private DatePickerDialog mDatePickerDialog;
    RadioButton radioMale, radioFemale;
    DatePickerDialog.OnDateSetListener date;
    String mUserName, mFullName, mDevice_id, mFirebase_id, mPassword, mEmail, mBrithdate, mMobile_Number, mConfPwd;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String mGnder = "Male";
    RadioGroup radio_group;
    int mslectedGander;
    SessionManager sessionManager;

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
        btnRegistration = findViewById(R.id.btn_registration);
        txt_Login = findViewById(R.id.txt_login_text);
        radioMale = findViewById(R.id.radiobtn_male);
        radioFemale = findViewById(R.id.radiobtn_female);
        radio_group = findViewById(R.id.radio_group);
        sessionManager = new SessionManager(RegisterActivity.this);
        datePicker();
        setDateTimeField();
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
    }

    private void btnRegistrationClick() {
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValue();
                //checkConnection();
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
        validation();
    }

    private void validation() {
        if (mFullName.equalsIgnoreCase("")) {
            edtRFullname.setError("Required");
            edtRFullname.setFocusable(true);
        } else if (mUserName.equalsIgnoreCase("")) {
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
        } else if (!mEmail.matches(emailPattern)) {
            edtREmail.setError("invalid email");
            edtREmail.setFocusable(true);
        } else {
            RegistrationAPI_CAll();

        }

    }

    private void RegistrationAPI_CAll() {
        Constant.progressDialog(RegisterActivity.this);
        Call<RegisterModel> modelCall = Constant.apiService.
                getRegisterDetails(mEmail, mPassword, "fgdjsgjgha", mDevice_id, mFullName, mBrithdate, mGnder, mUserName, mMobile_Number);
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
            message ="Internet Not Connected! Please Connect";
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
}
