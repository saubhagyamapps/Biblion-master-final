package app.biblion.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devs.readmoreoption.ReadMoreOption;

import java.io.File;

import app.biblion.R;
import app.biblion.notifacation.BackgroundNotificationService;

public class DetailELibraryFragment extends AppCompatActivity {


    private static final String TAG = "DetailELibraryFragment";
    RatingBar bookRatingBar;
    TextView txtDescription;
    Button btnBookDownload;
    public static final String PROGRESS_UPDATE = "progress_update";
    String mBookId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_elibrary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle extras = getIntent().getExtras();
        mBookId = extras.getString("id");
        initialization();
    }


    private void initialization() {
        btnBookDownload = findViewById(R.id.btnBookDownload);
        txtDescription = findViewById(R.id.txtDescription);
        bookRatingBar = findViewById(R.id.book_rating);
        bookRatingBar.setRating((float) 2.5);
        ReadMoreOption readMoreOption = new ReadMoreOption.Builder(DetailELibraryFragment.this)
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

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(DetailELibraryFragment.this);
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

                    Toast.makeText(DetailELibraryFragment.this, "File download completed", Toast.LENGTH_SHORT).show();

                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator +
                            "Book.EPUB");
                    Log.e(TAG, "onReceive: " + file);

                }
            }
        }
    };

    private void startImageDownload() {
        Intent intent = new Intent(DetailELibraryFragment.this, BackgroundNotificationService.class);
        intent.putExtra("id", mBookId);
        DetailELibraryFragment.this.startService(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
