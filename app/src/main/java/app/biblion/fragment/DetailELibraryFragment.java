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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devs.readmoreoption.ReadMoreOption;

import java.io.File;

import app.biblion.R;
import app.biblion.model.BookDetailsModel;
import app.biblion.notifacation.BackgroundNotificationService;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailELibraryFragment extends Fragment {

    View mView;
    private static final String TAG = "DetailELibraryFragment";
    RatingBar bookRatingBar;
    TextView txtDescription;
    Button btnBookDownload;
    public static final String PROGRESS_UPDATE = "progress_update";
    String mBookId;
    ReadMoreOption readMoreOption;
    ImageView imagesDetailsBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail_elibrary, container, false);
        Bundle args = getArguments();

        mBookId = args.getString("id");

        Log.e(TAG, "onCreateView: " + mBookId);
        initialization();

        return mView;
    }

    private void initialization() {
        btnBookDownload = mView.findViewById(R.id.btnBookDownload);
        imagesDetailsBook = mView.findViewById(R.id.imagesDetailsBook);
        txtDescription = mView.findViewById(R.id.txtDescription);
        bookRatingBar = mView.findViewById(R.id.book_rating);

        bookRatingBar.setRating((float) 2.5);
        readMoreOption = new ReadMoreOption.Builder(getContext())
                .textLength(150)
                .moreLabel("Read More")
                .lessLabel("Read Leas")
                .moreLabelColor(getResources().getColor(R.color.colorPrimaryDark))
                .lessLabelColor(getResources().getColor(R.color.colorPrimaryDark))
                .labelUnderLine(true)
                .build();


        registerReceiver();
        btnBookDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startImageDownload();
            }
        });
        APICALL();
    }

    private void APICALL() {
        Constant.progressDialog(getContext());
        Call<BookDetailsModel> modelCall = Constant.apiService.getBookDetails(mBookId);
        modelCall.enqueue(new Callback<BookDetailsModel>() {
            @Override
            public void onResponse(Call<BookDetailsModel> call, Response<BookDetailsModel> response) {
                readMoreOption.addReadMoreTo(txtDescription, response.body().getResult().get(0).getDescription());
                Glide.with(getActivity()).load("http://frozenkitchen.in/biblion_demo/public/images/" + response.body().getResult().get(0).getImage())
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.image_loader)
                        .crossFade()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imagesDetailsBook);
                Constant.progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<BookDetailsModel> call, Throwable t) {
                Constant.progressBar.dismiss();
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
        intent.putExtra("id", mBookId);
        getActivity().startService(intent);

    }
}
