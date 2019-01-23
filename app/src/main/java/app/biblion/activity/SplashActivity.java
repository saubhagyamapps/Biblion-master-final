package app.biblion.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import app.biblion.R;
import app.biblion.sessionmanager.SessionManager;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    private final int SPLASH_TIME = 2500;
    SessionManager securityManager;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        securityManager = new SessionManager(SplashActivity.this);
        if(securityManager.checkLogin()){
            intent=new Intent(SplashActivity.this, LoginActivity.class);
        }else {
            intent=new Intent(SplashActivity.this, NavigationActivity.class);
        }
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        finish();
                    }
                }, SPLASH_TIME);
      /*  addSlide(AppIntroFragment.newInstance("STEP 1", "Tap to open instagram.", R.drawable.instaintro1, Color.parseColor("#e91e63")));
        addSlide(AppIntroFragment.newInstance("STEP 2", "Tap on more option icon over instagram post.", R.drawable.instaintro2, Color.parseColor("#4caf50")));
        addSlide(AppIntroFragment.newInstance("STEP 3", "Tap on 'Copy Share URL' option. NOTE : If users profile is private 'Copy share URL' option is not available", R.drawable.instaintro3, Color.parseColor("#00bcd4")));
        addSlide(AppIntroFragment.newInstance("STEP 4", "Yeah! Photo or Video is Automatically Saved to your device.", R.drawable.instaintro4, Color.parseColor("#9c27b0")));
        showSkipButton(false);*/
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
