package app.biblion.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import app.biblion.R;
import app.biblion.sessionmanager.SessionManager;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    private final int SPLASH_TIME = 100;
    SessionManager securityManager;
    Intent intent;

    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        securityManager = new SessionManager(SplashActivity.this);
        if (securityManager.checkLogin()) {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent = new Intent(SplashActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                    }
                }, SPLASH_TIME);
        call_permissions();
      /*  addSlide(AppIntroFragment.newInstance("STEP 1", "Tap to open instagram.", R.drawable.instaintro1, Color.parseColor("#e91e63")));
        addSlide(AppIntroFragment.newInstance("STEP 2", "Tap on more option icon over instagram post.", R.drawable.instaintro2, Color.parseColor("#4caf50")));
        addSlide(AppIntroFragment.newInstance("STEP 3", "Tap on 'Copy Share URL' option. NOTE : If users profile is private 'Copy share URL' option is not available", R.drawable.instaintro3, Color.parseColor("#00bcd4")));
        addSlide(AppIntroFragment.newInstance("STEP 4", "Yeah! Photo or Video is Automatically Saved to your device.", R.drawable.instaintro4, Color.parseColor("#9c27b0")));
        showSkipButton(false);*/
    }


    private void call_permissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
        }
        return;
    }
  /*  @Override
    public void onSkipPressed(Fragment currentFragment) {

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE).edit();
        editor.putBoolean("AppIntro", false);
        editor.apply();
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
    }*/
}
