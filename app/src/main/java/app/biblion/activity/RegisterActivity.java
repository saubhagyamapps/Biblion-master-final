package app.biblion.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import app.biblion.R;
import app.biblion.model.RegisterModel;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtRFullname, edtRUsername, edtRBirtthdate, edtREmail, edtRpwd, edtRConfPwd;
    Button btnRegistration;
    Calendar myCalendar;
    TextView txt_Login;
    RadioButton radioMale, radioFemale;
    DatePickerDialog.OnDateSetListener date;
    String mUserName, mFullName, mGnder, mDevice_id, mFirebase_id, mPassword, mEmail, mBrithdate, mMobile_Number, mConfPwd;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialization();

    }

    private void initialization() {
        mDevice_id = Settings.Secure.getString(RegisterActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        edtRFullname = findViewById(R.id.edt_regfullname);
        edtRUsername = findViewById(R.id.edt_username);
        edtRBirtthdate = findViewById(R.id.edt_birthdate);
        edtREmail = findViewById(R.id.edt_email);
        edtRpwd = findViewById(R.id.edt_pwd);
        edtRConfPwd = findViewById(R.id.edt_cnfpwd);

        btnRegistration = findViewById(R.id.btn_registration);

        txt_Login = findViewById(R.id.txt_login_text);

        radioMale = findViewById(R.id.radiobtn_male);
        radioFemale = findViewById(R.id.radiobtn_female);
        datePicker();
        btnRegistrationClick();
    }

    private void datePicker() {
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                updateLabel();
            }

        };

        edtRBirtthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
            }
        });
    }

    private void getValue() {
        mFullName = edtRFullname.getText().toString().trim();
        mUserName = edtRUsername.getText().toString().trim();
        mEmail = edtREmail.getText().toString().trim();
        mPassword = edtRpwd.getText().toString().trim();
        mMobile_Number = edtREmail.getText().toString().trim();
        mGnder = edtREmail.getText().toString().trim();
        mBrithdate = edtRBirtthdate.getText().toString().trim();
        mConfPwd = edtRConfPwd.getText().toString().trim();
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
        } else if (mConfPwd.equalsIgnoreCase("")) {
            edtRConfPwd.setError("Required");
            edtRConfPwd.setFocusable(true);
        } else if (!mConfPwd.equals(mPassword)) {
            edtRpwd.setFocusable(true);
            Constant.toast(getResources().getString(R.string.password_is_not_matched), RegisterActivity.this);
        }else if (!mEmail.matches(emailPattern)) {
            edtREmail.setError("invalid email");
            edtREmail.setFocusable(true);
        }else {
            Constant.toast("okkkkkkkkkkkk",RegisterActivity.this);
        }

    }

    private void RegistrationAPI_CAll() {
        Constant.progressDialog(RegisterActivity.this);
        Call<RegisterModel> modelCall = Constant.apiService.
                getRegisterDetails(mEmail, mPassword, mFirebase_id, mDevice_id, mFullName, mBrithdate, mGnder, mGnder, mMobile_Number);
        modelCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                Constant.progressBar.dismiss();

            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Constant.progressBar.dismiss();

            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtRBirtthdate.setText(sdf.format(myCalendar.getTime()));
    }
}
