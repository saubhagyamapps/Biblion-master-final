package app.biblion.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import app.biblion.R;

public class RegisterActivity extends AppCompatActivity {

    EditText edtRFullname, edtRUsername,edtRBirtthdate, edtREmail, edtRpwd, edtRConfPwd;
    Button btnRegistration;
    Calendar myCalendar;
    TextView txt_Login;
    RadioButton radioMale, radioFemale;
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRFullname =findViewById(R.id.edt_regfullname);
        edtRUsername = findViewById(R.id.edt_username);
        edtRBirtthdate = findViewById(R.id.edt_birthdate);
        edtREmail = findViewById(R.id.edt_email);
        edtRpwd =findViewById(R.id.edt_pwd);
        edtRConfPwd = findViewById(R.id.edt_cnfpwd);

        btnRegistration=findViewById(R.id.btn_registration);

        txt_Login = findViewById(R.id.txt_login_text);

        radioMale = findViewById(R.id.radiobtn_male);
        radioFemale = findViewById(R.id.radiobtn_female);

        btnRegistrationClick();

         myCalendar = Calendar.getInstance();
         date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edtRBirtthdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
                startActivity(new Intent(RegisterActivity.this,NavigationActivity.class));
                finish();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtRBirtthdate.setText(sdf.format(myCalendar.getTime()));
    }
}
