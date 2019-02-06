package app.biblion.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import app.biblion.R;


public class ForgotPasswordActivity extends AppCompatActivity {
    TextView txtBackToLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialization();

    }

    private void initialization() {
        txtBackToLogin=findViewById(R.id.txtBackToLogin);
        backToLogin();
    }

    private void backToLogin() {
        txtBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
