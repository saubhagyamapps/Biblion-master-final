package app.biblion.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.biblion.R;
import app.biblion.model.LoginModel;
import app.biblion.sessionmanager.SessionManager;
import app.biblion.util.Biblion;
import app.biblion.util.ConnectivityReceiver;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity";
    private GoogleApiClient mGoogleApiClient;
    EditText edtEmail, edtPwd;
    Button btnLogin;
    TextView txtForgotPwd, txtSignup;
    String mEmail, mPassword, mDevice_id;
    private static final int RC_SIGN_IN = 007;
    SessionManager session;
    ImageView btn_sign_in_gmail, btn_sign_in_fb, btn_sign_in_twitter;
    private ProgressDialog mProgressDialog;
    CallbackManager callbackManager;
    AccessToken accessToken;


    String fbName, fbEmail, fbId, fbBirthday, fbLoaction,fbImageurl;
    boolean boolean_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
        accessToken = AccessToken.getCurrentAccessToken();
        setContentView(R.layout.activity_login);
        mDevice_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        checkConnection();
        init();

    }

    public void init() {
        session = new SessionManager(getApplicationContext());
        edtEmail = findViewById(R.id.input_email);
        edtPwd = findViewById(R.id.input_password);
        btn_sign_in_gmail = findViewById(R.id.btn_sign_in_gmail);
        btn_sign_in_fb = findViewById(R.id.btn_sign_in_fb);
        btn_sign_in_twitter = findViewById(R.id.btn_sign_in_twitter);
        btnLogin = findViewById(R.id.btn_login);
        //btnFb = findViewById(R.id.btn_fb);

        txtForgotPwd = findViewById(R.id.link_forgotpwd);
        txtSignup = findViewById(R.id.link_signup);
        clicked();
        btn_sign_in_gmail.setOnClickListener(this);
        btn_sign_in_fb.setOnClickListener(this);
        btn_sign_in_twitter.setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
        signIn();
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
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
                checkConnection();
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

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (!isConnected) {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
            Snackbar snackbar = Snackbar.make(findViewById(R.id.viewSnackbar), message, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Biblion.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("name", personName);
            intent.putExtra("email", email);
            intent.putExtra("images", personPhotoUrl);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_sign_in_gmail:
                signOut();

                break;
            case R.id.btn_sign_in_fb:

                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile,email"));
                    facebookLogin();

                break;

            case R.id.btn_sign_in_twitter:
                revokeAccess();
                break;
        }
    }

    private void facebookLogin() {

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                {
                                    try {

                                        boolean_login = true;
                                        Log.e("object", object.toString());
                                        fbName = object.getString("name");
                                        fbEmail = object.getString("email");
                                        fbId = object.getString("id");

                                        fbImageurl = "https://graph.facebook.com/" + fbId + "/picture?type=normal";
                                        Log.e("Picture", fbImageurl);
                                        Log.e(TAG, "Name: " + fbName + ", email: " + fbEmail
                                        + ", DOB " + fbBirthday );

                                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                        intent.putExtra("name", fbName);
                                        intent.putExtra("email", fbEmail);
                                        intent.putExtra("images",fbImageurl);
                                        startActivity(intent);

                                    } catch (Exception e) {
                                            e.printStackTrace();
                                    }
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

                Log.d(TAG, "Login attempt cancelled.");
                if (AccessToken.getCurrentAccessToken() == null) {
                    return; // already logged out
                }
                new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        LoginManager.getInstance().logOut();
                        accessToken.setCurrentAccessToken(null);
                        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile,email"));
                        facebookLogin();

                    }
                }); //.executeAsync();
            }

            @Override
            public void onError(FacebookException e) {
                Log.e("ON ERROR", "Login attempt failed.");

                AccessToken.setCurrentAccessToken(null);
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile,email,user_birthday"));
            }
        });
    }


}
