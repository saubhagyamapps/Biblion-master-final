package app.biblion.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.biblion.R;

public class Signup_SignInActivity extends AppCompatActivity {

    Button btn_SignUp,btn_Signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__sign_in);

        initialization();
        cliked();
    }

    private void initialization()
    {

        btn_SignUp = findViewById(R.id.btn_signup);
        btn_Signin = findViewById(R.id.btn_signin);

    }

    private void cliked()
    {
        btn_Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Signin.setBackground(getResources().getDrawable(R.drawable.signup_button));
                btn_SignUp.setBackgroundColor(getResources().getColor(R.color.white));
                btn_Signin.setTextColor(getResources().getColor(R.color.white));
                btn_SignUp.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_SignUp.setBackground(getResources().getDrawable(R.drawable.signup_button));
                btn_Signin.setBackgroundColor(getResources().getColor(R.color.white));
                btn_SignUp.setTextColor(getResources().getColor(R.color.white));
                btn_Signin.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
}
