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
        mView = inflater.inflate(R.layout.fragment_setting, container, false);
        getActivity().setTitle(R.string.frag_reset_password);

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
                validation();

            }
        });
    }

    private void validation() {
        mOldPassword = edt_OldPassword.getText().toString().trim();
        mNewPassword = edt_NewPassword.getText().toString().trim();
        mConfPassword = edt_NewConfPassword.getText().toString().trim();
        if (mOldPassword.equals("")) {
            edt_OldPassword.setError("Required");
        } else if (mNewPassword.equals("")) {
            edt_NewPassword.setError("Required");
        } else if (mConfPassword.equals("")) {
            edt_NewConfPassword.setError("Required");
        } else if (!mNewPassword.equals(mConfPassword)) {
            Constant.toast("New Password or Conform Password Does't maths", getActivity());
        } else {
            Apicall();
        }
    }

    private void Apicall() {
        Constant.progressDialog(getActivity());

        Call<ResetPasswordModel> modelCall = Constant.apiService.resetPassword(user.get(sessionManager.KEY_ID), mOldPassword, mNewPassword);
        modelCall.enqueue(new Callback<ResetPasswordModel>() {
            @Override
            public void onResponse(Call<ResetPasswordModel> call, Response<ResetPasswordModel> response) {
                if (response.body().getStatus().equals("0")) {
                    Constant.toast(response.body().getMessage(), getActivity());
                    edt_NewConfPassword.setText("");
                    edt_NewPassword.setText("");
                    edt_OldPassword.setText("");
                } else {
                    Constant.toast(response.body().getMessage(), getActivity());
                }
                Constant.progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<ResetPasswordModel> call, Throwable t) {
                Constant.progressBar.dismiss();
            }
        });
    }
}
