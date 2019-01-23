package app.biblion.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import app.biblion.R;
import app.biblion.retrofit.ApiClient;
import app.biblion.retrofit.ApiInterface;

public class Constant {
    public static String mBaseUrl = "http://192.168.1.200/biblion-API/";
    public static ProgressDialog progressBar;
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
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void intent(Context classOne, Class classTwo) {
        Intent intent = new Intent(classOne, classTwo);
        classOne.startActivity(intent);

    }
}
