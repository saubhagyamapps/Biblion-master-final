package app.biblion.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.biblion.R;
import app.biblion.activity.NavigationActivity;
import app.biblion.model.EditProfileModel;
import app.biblion.sessionmanager.SessionManager;
import app.biblion.util.Constant;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment implements View.OnClickListener {


    View mView;
    private static final String TAG = "Accountfragment";
    Context mContext;
    EditText edtPFullname, edtPUsername, edtPBirtthdate, edtPEmail, edtPpwd, edtPConfPwd, edtPMobileno,
            edtPCountry, edtPState, edtPCity, edtLanguage;
    Button btnUpdate;
    Calendar myCalendar;
    ImageView camera_image;
    RadioButton prof_radioMale, prof_radioFemale;
    ImageView prof_camera_image, edit_Username, edit_MobileNo, edit_DOB, edit_Country, edit_State, edit_City;
    RadioGroup prof_radio_group;
    String filePath = "null";
    String mGoogleimage = "null";
    Fragment fragment = null;
    String mDevice_id;
    String mGnder;
    String profselectedCountry, profselectedState, profselectedCity;
    int mslectedGender;
    ToggleSwitch toggleSwitch;
    int togglePosition = 0;
    private SessionManager sessionManager;
    String pUserName, pFullName, pFirebase_id, pEmail, pBrithdate, pMobile_Number, pCountry, pState, pCity;
    String A_Name, A_Email, A_MobileNo, A_DOB, A_Gender, A_DeviceId, A_FirebaseID, A_Password,
            A_Id, A_City, A_State, A_Country,A_Language;
    private DatePickerDialog profDatePickerDialog;
    private AlertDialog prof_alertDialogObjectCountry, prof_alertDialogObjectState,
            prof_alertDialogObjectCity, prof_alertDialogueObjectLanguage;
    Call<EditProfileModel> editProfileModelCall;
    private final static int IMAGE_RESULT = 200;
    CircleImageView imageView;
    Uri picUri;
    String mImagesPath;
    String mLanguageCode, mLanguageName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_account, container, false);
        getActivity().setTitle(R.string.frag_account_setting);
        sessionManager = new SessionManager(getActivity());
        initialization();
        getDataFromSession();
        return mView;
    }

    private void initialization() {
        mDevice_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        edtPMobileno = mView.findViewById(R.id.edt_profmobileno);
        edtPUsername = mView.findViewById(R.id.edt_profusername);
        edtPBirtthdate = mView.findViewById(R.id.edt_profbirthdate);
        edtPEmail = mView.findViewById(R.id.edt_profemail);
        edtPCountry = mView.findViewById(R.id.edt_profcountry);
        edtPState = mView.findViewById(R.id.edt_profstate);
        edtPCity = mView.findViewById(R.id.edt_profcity);
        btnUpdate = mView.findViewById(R.id.btn_profupdate);
        edtLanguage = mView.findViewById(R.id.edt_proflanguage);

       /* prof_radioMale = mView.findViewById(R.id.profradiobtn_male);
        prof_radioFemale = mView.findViewById(R.id.profradiobtn_female);
        prof_radio_group = mView.findViewById(R.id.profradio_group);*/
        edtPUsername.setInputType(InputType.TYPE_CLASS_TEXT);
        toggleSwitch = mView.findViewById(R.id.gender_switch);
        imageView = mView.findViewById(R.id.acc_profile_image);
        camera_image = mView.findViewById(R.id.prof_camera_image);
        edit_Username = mView.findViewById(R.id.username_edit);
        edit_MobileNo = mView.findViewById(R.id.mobileno_edit);
        edit_DOB = mView.findViewById(R.id.birthdate_edit);
        edit_Country = mView.findViewById(R.id.country_edit);
        edit_State = mView.findViewById(R.id.state_edit);
        edit_City = mView.findViewById(R.id.city_edit);


        edtPEmail.setEnabled(false);
        edtPUsername.setEnabled(false);
        edtPMobileno.setEnabled(false);
        edtPBirtthdate.setEnabled(false);
        edtPCountry.setEnabled(false);
        edtPState.setEnabled(false);
        edtPCity.setEnabled(false);

        setAccountDateTimeField();
        ShowProfileCountryList();
        ShowProfileStateList();
        ShowProfileCityList();
        showLanguageList();
        // getCemaraImages();
        updateClicked();
        getCemaraImages();

        toggleSwitch.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {

                switch (position) {
                    case 0:
                        mGnder = "Male";
                        //Toast.makeText(getActivity(), mGnder, Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        mGnder = "Female";
                        //Toast.makeText(getActivity(), mGnder, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    public void getDataFromSession() {
        HashMap<String, String> user = sessionManager.getUserDetails();

        A_Id = user.get(sessionManager.KEY_ID);
        A_Name = user.get(sessionManager.KEY_NAME);
        A_Email = user.get(sessionManager.KEY_EMAIL);
        A_MobileNo = user.get(sessionManager.KEY_MOBILE);
        A_Password = user.get(sessionManager.KEY_PASSWORD);
        A_DOB = user.get(sessionManager.KEY_DOB);
        A_Gender = user.get(sessionManager.KEY_GENDER);
        A_DeviceId = user.get(sessionManager.KEY_DEVICE_ID);
        A_FirebaseID = user.get(sessionManager.KEY_FIREBASE_ID);
        A_City = user.get(sessionManager.KEY_CITY);
        A_State = user.get(sessionManager.KEY_STATE);
        A_Country = user.get(sessionManager.KEY_COUNTRY);
        A_Language = user.get(sessionManager.KEY_LANGUAGE);



        edtPUsername.setText(A_Name);
        edtPEmail.setText(A_Email);
        edtPMobileno.setText(A_MobileNo);
        edtPBirtthdate.setText(A_DOB);
        edtPCity.setText(A_City);
        edtPState.setText(A_State);
        edtPCountry.setText(A_Country);
        edtLanguage.setText(A_Language);
        mGnder = A_Gender;
        if (mGnder.equals("Male")) {
            toggleSwitch.setCheckedTogglePosition(0);
            mGnder = "Male";
        } else {
            toggleSwitch.setCheckedTogglePosition(1);
            mGnder = "Female";
        }
        Glide.with(getActivity()).load(user.get(sessionManager.KEY_IMAGE))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    private void updateClicked() {


        edit_Username.setOnClickListener(this);
        edit_MobileNo.setOnClickListener(this);
        edit_DOB.setOnClickListener(this);
        edit_Country.setOnClickListener(this);
        edit_State.setOnClickListener(this);
        edit_City.setOnClickListener(this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValue();
                edit_Username.setVisibility(View.VISIBLE);
                edit_MobileNo.setVisibility(View.VISIBLE);
                edit_DOB.setVisibility(View.VISIBLE);
                edit_Country.setVisibility(View.VISIBLE);
                edit_State.setVisibility(View.VISIBLE);
                edit_City.setVisibility(View.VISIBLE);
                edtPUsername.setEnabled(false);
                edtPMobileno.setEnabled(false);
                edtPBirtthdate.setEnabled(false);
                edtPCountry.setEnabled(false);
                edtPState.setEnabled(false);
                edtPCity.setEnabled(false);


            }
        });

        edtPBirtthdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                profDatePickerDialog.show();
                return false;
            }
        });

        edtPCountry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                prof_alertDialogObjectCountry.show();
                return false;
            }
        });
        edtPState.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                prof_alertDialogObjectCity.show();
                return false;
            }
        });
        edtPCity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                prof_alertDialogObjectCity.show();
                return false;
            }
        });
        edtLanguage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                prof_alertDialogueObjectLanguage.show();
                return false;
            }
        });

    }

    private void setAccountDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        profDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                edtPBirtthdate.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        profDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void getValue() {

        pUserName = edtPUsername.getText().toString().trim();
        pFullName = edtPUsername.getText().toString().trim();
        pMobile_Number = edtPMobileno.getText().toString().trim();
        pBrithdate = edtPBirtthdate.getText().toString().trim();
        pCountry = edtPCountry.getText().toString().trim();
        pState = edtPState.getText().toString().trim();
        pCity = edtPCity.getText().toString().trim();

        validation();
    }


    private void validation() {
        if (pUserName.equalsIgnoreCase("")) {
            edtPUsername.setError("Required");
            edtPUsername.setFocusable(true);
        } else if (pMobile_Number.equalsIgnoreCase("")) {
            edtPMobileno.setError("Required");
            edtPMobileno.setFocusable(true);
        } else if (pBrithdate.equalsIgnoreCase("")) {
            edtPBirtthdate.setError("Required");
            edtPBirtthdate.setFocusable(true);
        } /*else if (pCountry.equals("")) {
            edtPCountry.setError("Required");
            edtPCountry.setFocusable(true);
        } else if (pState.equals("")) {
            edtPState.setError("Required");
            edtPState.setFocusable(true);
        } else if (pCity.equals("")) {
            edtPCity.setError("Required");
            edtPCity.setFocusable(true);
        }*/
        UpdatedAPI_Call();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.username_edit:
                edtPUsername.setEnabled(true);
                edit_Username.setVisibility(View.INVISIBLE);
                break;
            case R.id.mobileno_edit:
                edtPMobileno.setEnabled(true);
                edit_MobileNo.setVisibility(View.INVISIBLE);
                break;
            case R.id.birthdate_edit:
                edtPBirtthdate.setEnabled(true);
                profDatePickerDialog.show();
                edit_DOB.setVisibility(View.INVISIBLE);
                break;
            case R.id.country_edit:
                edtPCountry.setEnabled(true);
                prof_alertDialogObjectCountry.show();
                edit_Country.setVisibility(View.INVISIBLE);
                break;
            case R.id.state_edit:
                edtPState.setEnabled(true);
                prof_alertDialogObjectState.show();
                edit_State.setVisibility(View.INVISIBLE);
                break;
            case R.id.city_edit:
                edtPCity.setEnabled(true);
                prof_alertDialogObjectCity.show();
                edit_City.setVisibility(View.INVISIBLE);
                break;
        }

    }

    private void getCemaraImages() {
        camera_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });

    }

    private Intent getPickImageChooserIntent() {
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();

        PackageManager packageManager = getActivity().getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getActivity().getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == IMAGE_RESULT) {

                filePath = getImageFilePath(data);
                if (filePath != null) {
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    imageView.setImageBitmap(selectedImage);
                }
            }

        }
    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        // picUri = savedInstanceState.getParcelable("pic_uri");
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void UpdatedAPI_Call() {
        MultipartBody.Part body = null;
        try {
            File file = new File(filePath);
            final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            body = MultipartBody.Part.createFormData("image", file.getName(), requestfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody u_Id =
                RequestBody.create(MediaType.parse("multipart/form-data"), A_Id);
        RequestBody u_Username =
                RequestBody.create(MediaType.parse("multipart/form-data"), pUserName);
        /*RequestBody u_Fullname =
                RequestBody.create(MediaType.parse("multipart/form-data"), pFullName);*/
        RequestBody u_Email =
                MultipartBody.create(MediaType.parse("multipart/form-data"), A_Email);
        /*RequestBody u_Password =
                MultipartBody.create(MediaType.parse("multipart/form-data"),A_Password);*/
        RequestBody u_MobileNo =
                MultipartBody.create(MediaType.parse("multipart/form-data"), pMobile_Number);
        RequestBody u_Birthdate =
                MultipartBody.create(MediaType.parse("multipart/form-data"), pBrithdate);
        /*RequestBody u_Firebase_id =
                RequestBody.create(MediaType.parse("multipart/form-data"), "fgdjsgjgha");
        RequestBody u_Device_id =
                RequestBody.create(MediaType.parse("multipart/form-data"), mDevice_id);*/
        RequestBody u_Gender =
                RequestBody.create(MediaType.parse("multipart/form-data"), mGnder);
        RequestBody u_Country =
                RequestBody.create(MediaType.parse("multipart/form-data"), pCountry);
        RequestBody u_State =
                RequestBody.create(MediaType.parse("multipart/form-data"), pState);
        RequestBody u_City =
                RequestBody.create(MediaType.parse("multipart/form-data"), pCity);
        RequestBody u_Language =
                RequestBody.create(MediaType.parse("multipart/form-data"), mLanguageName);

        Constant.progressDialog(getActivity());

        if (!filePath.equals("null")) {
            editProfileModelCall = Constant.apiService.
                    getEditDetailsWithImag(u_Id, u_Username, u_Email, u_Gender, u_Birthdate, u_MobileNo, u_Country, u_State, u_City,u_Language, body);

        } else {
            editProfileModelCall = Constant.apiService.getEditDetails(A_Id, pUserName, A_Email, mGnder, pBrithdate, pMobile_Number, pCountry, pState, pCity,mLanguageName);
        }

        editProfileModelCall.enqueue(new Callback<EditProfileModel>() {
            @Override
            public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {
                if (response.body().getStatus().equals("1")) {
                    Constant.toast(response.body().getMessage(), getActivity());
                    Constant.progressBar.dismiss();
                } else {
                    if (response.body().getResult().get(0).getImage() != null && !response.body().getResult().get(0).getImage().isEmpty() &&
                            !response.body().getResult().get(0).getImage().equals("null")) {
                        mImagesPath = response.body().getResult().get(0).getImage();
                    } else {
                        mImagesPath = response.body().getResult().get(0).getGoogleimage();
                    }
                    sessionManager.createLoginSession(response.body().getResult().get(0).getId(), A_Email, A_Password, response.body().getResult().get(0).getName(),
                            response.body().getResult().get(0).getGender(), response.body().getResult().get(0).getDob(),
                            response.body().getResult().get(0).getDevice_id(), response.body().getResult().get(0).getMobile(),
                            response.body().getResult().get(0).getFirebase_id(), response.body().getResult().get(0).getCity(),
                            response.body().getResult().get(0).getState(), response.body().getResult().get(0).getCountry(), mImagesPath,mLanguageName);
                    Constant.progressBar.dismiss();
                    Log.e(TAG, "LANGUAGE>>>>>: "+mLanguageName );
                    getFragmentManager()
                            .beginTransaction()
                            .detach(AccountFragment.this)
                            .attach(AccountFragment.this)
                            .commit();
                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),NavigationActivity.class);
                    startActivity(intent);

                    Log.e(TAG, "Tstingggggggggggggg" );
                }


            }

            @Override
            public void onFailure(Call<EditProfileModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                Constant.progressBar.dismiss();
            }
        });
    }

    public void ShowProfileCountryList() {
        List<String> mCountrylist = new ArrayList<String>();
        mCountrylist.add("USA");
        mCountrylist.add("RUSSIA");
        mCountrylist.add("INDIA");
        mCountrylist.add("UK");
        mCountrylist.add("AUSTRALIA");
        mCountrylist.add("SPAIN");
        //Create sequence of items
        final CharSequence[] Animals = mCountrylist.toArray(new String[mCountrylist.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Country");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                profselectedCountry = Animals[item].toString();  //Selected item in listview
                edtPCountry.setText(profselectedCountry);
            }
        });
        //Create alert dialog object via builder
        prof_alertDialogObjectCountry = dialogBuilder.create();
        //Show the dialog
        //alertDialogObject.show();
    }

    public void ShowProfileStateList() {
        List<String> mStatelist = new ArrayList<String>();
        mStatelist.add("Gujarat");
        mStatelist.add("Maharashtra");
        mStatelist.add("Madhya Pradesh");
        mStatelist.add("Rajsthan");
        mStatelist.add("Punjab");
        mStatelist.add("Haryan");
        mStatelist.add("Delhi");
        mStatelist.add("Uttar Pradesh");
        mStatelist.add("Karnataka");
        mStatelist.add("Delhi");
        mStatelist.add("Andhra Pradesh");
        mStatelist.add("Himachal Pradesh");
        //Create sequence of items
        final CharSequence[] Animals = mStatelist.toArray(new String[mStatelist.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("State");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                profselectedState = Animals[item].toString();  //Selected item in listview
                edtPState.setText(profselectedState);
            }
        });
        //Create alert dialog object via builder
        prof_alertDialogObjectState = dialogBuilder.create();
        //Show the dialog
        //alertDialogObject.show();
    }

    public void ShowProfileCityList() {
        List<String> mCitylist = new ArrayList<String>();
        mCitylist.add("Ahmedabad");
        mCitylist.add("Surat");
        mCitylist.add("Vadodara");
        mCitylist.add("Rajkot");
        mCitylist.add("Bhavnagar");
        mCitylist.add("Jamnagar");
        mCitylist.add("Junagadh");
        mCitylist.add("Anand");
        mCitylist.add("Surendranagar");
        mCitylist.add("Navsari");
        mCitylist.add("Anand");
        mCitylist.add("Kheda");
        //Create sequence of items
        final CharSequence[] Animals = mCitylist.toArray(new String[mCitylist.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("City");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                profselectedCity = Animals[item].toString();  //Selected item in listview
                edtPCity.setText(profselectedCity);
            }
        });
        //Create alert dialog object via builder
        prof_alertDialogObjectCity = dialogBuilder.create();
        //Show the dialog
        //alertDialogObject.show();
    }

    public void showLanguageList()
    {
        List<String> mLanguageList = new ArrayList<>();
        mLanguageList.add("English");
        mLanguageList.add("Gujarati");
        final CharSequence[] lists = mLanguageList.toArray(new String[mLanguageList.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Language");
        dialogBuilder.setItems(lists, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                mLanguageName = lists[item].toString();
                edtLanguage.setText(mLanguageName);

                switch (mLanguageName){

                    case "Gujarati":
                        mLanguageCode = "gj";
                        Constant.changeLanguage(mLanguageCode, getActivity());
                        /*Locale locale = new Locale(mLanguageCode);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getActivity().getResources().
                                updateConfiguration(config,getActivity().getResources().getDisplayMetrics());*/
                        break;
                    case "English":
                        mLanguageCode = "en";
                        Constant.changeLanguage(mLanguageCode,getActivity());
                       /* locale = new Locale(mLanguageCode);
                        Locale.setDefault(locale);
                        config = new Configuration();
                        config.locale = locale;
                        getActivity().getResources().
                                updateConfiguration(config,getActivity().getResources().getDisplayMetrics());*/
                }
            }
        });
        prof_alertDialogueObjectLanguage= dialogBuilder.create();
    }



}