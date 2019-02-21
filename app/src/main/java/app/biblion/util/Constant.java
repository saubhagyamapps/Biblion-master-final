package app.biblion.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.LogTime;

import javax.security.auth.login.LoginException;

import app.biblion.R;
import app.biblion.retrofit.ApiClient;
import app.biblion.retrofit.ApiInterface;

public class Constant {

    public static String mBaseUrl = "http://frozenkitchen.in/biblion_demo/";
    public static String mImagesPath;
    public static ProgressDialog progressBar;
    public static Button btnNext;
    public static TextView txtTitle;
    public static LinearLayout screenShortLayout;
    public static ImageView ssImagesView;


    public static ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    public static void progressDialog(Context applicationContext) {
        progressBar = new ProgressDialog(applicationContext);
        progressBar.setCancelable(false);
        progressBar.setTitle(applicationContext.getResources().getString(R.string.progress_dialog_tital));
        progressBar.setMessage(applicationContext.getResources().getString(R.string.progress_dialog_msg));
        progressBar.show();

    }

    public static void toast(String message, Context applicationContext) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show();
    }

    public static void intent(Context classOne, Class classTwo) {
        Intent intent = new Intent(classOne, classTwo);
        classOne.startActivity(intent);

    }
    public static void hideKeyboard(Activity activity, View viewToHide) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(viewToHide.getWindowToken(), 0);
    }
}