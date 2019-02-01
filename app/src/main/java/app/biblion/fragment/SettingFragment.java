package app.biblion.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import app.biblion.R;
import app.biblion.model.ResetPasswordModel;
import app.biblion.sessionmanager.SessionManager;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFragment extends Fragment {
    SessionManager sessionManager;
    View mView;
    EditText edt_OldPassword, edt_NewPassword, edt_NewConfPassword;
    String mOldPassword, mNewPassword, mConfPassword;
    Button btn_profupdate;
    HashMap<String, String> user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_setting, container, false);
        getActivity().setTitle("Reset Password");

        initialization();
        return mView;
    }


    private void initialization() {
        sessionManager = new SessionManager(getActivity());
        user = sessionManager.getUserDetails();

        edt_OldPassword = mView.findViewById(R.id.old_password);
        edt_NewPassword = mView.findViewById(R.id.new_password);
        edt_NewConfPassword = mView.findViewById(R.id.confirm_new_password);
        btn_profupdate = mView.findViewById(R.id.btn_profupdate);
        updatePassword();
    }

    private void updatePassword() {
        btn_profupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Apicall();
            }
        });
    }

    private void Apicall() {
        mOldPassword = edt_OldPassword.getText().toString().trim();
        mNewPassword = edt_NewPassword.getText().toString().trim();
        mConfPassword = edt_NewConfPassword.getText().toString().trim();

        Call<ResetPasswordModel> modelCall = Constant.apiService.resetPassword(user.get(sessionManager.KEY_ID), mOldPassword, mNewPassword);
        modelCall.enqueue(new Callback<ResetPasswordModel>() {
            @Override
            public void onResponse(Call<ResetPasswordModel> call, Response<ResetPasswordModel> response) {
            
            }

            @Override
            public void onFailure(Call<ResetPasswordModel> call, Throwable t) {

            }
        });
    }
}
