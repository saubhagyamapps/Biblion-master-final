package app.biblion.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import app.biblion.R;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPwd;
    Button btnLogin, btnFb;
    TextView txtForgotPwd, txtSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.input_email);
        edtPwd = findViewById(R.id.input_password);

        btnLogin = findViewById(R.id.btn_login);
        //btnFb = findViewById(R.id.btn_fb);

        txtForgotPwd = findViewById(R.id.link_forgotpwd);
        txtSignup = findViewById(R.id.link_signup);

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
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
