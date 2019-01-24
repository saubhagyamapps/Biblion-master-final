package app.biblion.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devs.readmoreoption.ReadMoreOption;

import java.io.File;

import app.biblion.R;
import app.biblion.notifacation.BackgroundNotificationService;

public class DetailELibraryFragment extends Fragment {

    View mView;
    private static final String TAG = "DetailELibraryFragment";
    RatingBar bookRatingBar;
    TextView txtDescription,btnBookDownload;
    public static final String PROGRESS_UPDATE = "progress_update";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail_elibrary, container, false);
        initialization();

        return mView;
    }

    private void initialization() {
        btnBookDownload=mView.findViewById(R.id.btnBookDownload);
        txtDescription = mView.findViewById(R.id.txtDescription);
        bookRatingBar = mView.findViewById(R.id.book_rating);
        bookRatingBar.setRating((float) 2.5);
        ReadMoreOption readMoreOption = new ReadMoreOption.Builder(getContext())
                .textLength(150)
                .moreLabel("Read More")
                .lessLabel("Read Leas")
                .moreLabelColor(getResources().getColor(R.color.colorPrimaryDark))
                .lessLabelColor(getResources().getColor(R.color.colorPrimaryDark))
                .labelUnderLine(true)
                .build();

        readMoreOption.addReadMoreTo(txtDescription, getString(R.string.long_desc));
        registerReceiver();
        btnBookDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startImageDownload();
            }
        });
    }



    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PROGRESS_UPDATE);
        bManager.registerReceiver(mBroadcastReceiver, intentFilter);

    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(PROGRESS_UPDATE)) {

                boolean downloadComplete = intent.getBooleanExtra("downloadComplete", false);
                //Log.d("API123", download.getProgress() + " current progress");

                if (downloadComplete) {

                    Toast.makeText(getActivity(), "File download completed", Toast.LENGTH_SHORT).show();

                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator +
                            "Book.EPUB");
                    Log.e(TAG, "onReceive: " + file);

                }
            }
        }
    };

    private void startImageDownload() {


        Intent intent = new Intent(getActivity(), BackgroundNotificationService.class);
        getActivity().startService(intent);

    }
}
