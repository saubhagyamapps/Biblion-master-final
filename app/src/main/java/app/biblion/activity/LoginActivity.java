package app.biblion.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.biblion.R;
import app.biblion.model.LoginModel;
import app.biblion.sessionmanager.SessionManager;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPwd;
    Button btnLogin;
    TextView txtForgotPwd, txtSignup;
    String mEmail, mPassword, mDevice_id;
    private static final String TAG = "LoginActivity";
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        mDevice_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        init();

    }

    public void init() {
        session = new SessionManager(getApplicationContext());
        edtEmail = findViewById(R.id.input_email);
        edtPwd = findViewById(R.id.input_password);

        btnLogin = findViewById(R.id.btn_login);
        //btnFb = findViewById(R.id.btn_fb);

        txtForgotPwd = findViewById(R.id.link_forgotpwd);
        txtSignup = findViewById(R.id.link_signup);
        clicked();

    }

    public void clicked() {

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();

            }
        });
    }

    private void validation() {
        mEmail = edtEmail.getText().toString();
        mPassword = edtPwd.getText().toString();
        if (!isValidEmail(mEmail)) {
            edtEmail.setError("Invalid Email");
        } else if (!isValidPassword(mPassword)) {
            edtPwd.setError("Invalid Password");
        } else {
            LoginAPI_CAll();
        }
    }

    private void LoginAPI_CAll() {
        Constant.progressDialog(LoginActivity.this);
        Call<LoginModel> loginModelCall = Constant.apiService.getLoginDetails(mEmail, mPassword, mDevice_id, "");
        loginModelCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Constant.progressBar.dismiss();
                Log.e(TAG, "onResponse: " + "" + response.body().getStatus());
                if (response.body().getStatus().equals("1")) {
                    Constant.toast(getResources().getString(R.string.email_or_password_dont_matched), LoginActivity.this);
                } else {

                    session.createLoginSession(mEmail, mPassword, response.body().getResult().get(0).getName(),
                            response.body().getResult().get(0).getGender(), response.body().getResult().get(0).getDob(),
                            response.body().getResult().get(0).getDevice_id(), response.body().getResult().get(0).getMobile(),
                            response.body().getResult().get(0).getFirebase_id());
                    Constant.intent(LoginActivity.this, NavigationActivity.class);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Constant.progressBar.dismiss();

            }
        });
    }

    // validating email with retype email
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }
}
