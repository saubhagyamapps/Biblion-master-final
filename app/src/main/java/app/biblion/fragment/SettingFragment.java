package app.biblion.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import app.biblion.R;

public class SettingFragment extends Fragment {
    View mView;
    EditText edt_OldPassword, edt_NewPassword, edt_NewConfPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =inflater.inflate(R.layout.fragment_setting, container, false);
        getActivity().setTitle("Reset Password");

        initialization();
        return mView;
    }


    private void initialization()
    {
        edt_OldPassword = mView.findViewById(R.id.old_password);
        edt_NewPassword = mView.findViewById(R.id.new_password);
        edt_NewConfPassword = mView.findViewById(R.id.confirm_new_password);
    }
}
