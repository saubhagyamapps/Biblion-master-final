package app.biblion.notifacation;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.folioreader.Constants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import app.biblion.R;
import app.biblion.fragment.DetailELibraryFragment;
import app.biblion.retrofit.ApiInterface;
import app.biblion.util.Constant;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;


public class BackgroundNotificationService extends IntentService {

    public BackgroundNotificationService() {
        super("Service");
    }

    private static final String TAG = "BackgroundNotificationS";
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;


    @Override
    protected void onHandleIntent(Intent intent) {

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("id", "an", NotificationManager.IMPORTANCE_LOW);

            notificationChannel.setDescription("no sound");
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        notificationBuilder = new NotificationCompat.Builder(this, "id")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle("Download")
                .setContentText("Downloading Book")
                .setDefaults(0)
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());

        initRetrofit();

    }

    private void initRetrofit() {


        Call<ResponseBody> request = Constant.apiService.downloadImage("downloadbook/1");
        try {

            downloadImage(request.execute().body());

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private void downloadImage(ResponseBody body) throws IOException {

        int count;
        Log.e(TAG, "downloadImage: "+body.toString());
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Book.EPUB");
        OutputStream outputStream = new FileOutputStream(outputFile);
        long total = 0;
        boolean downloadComplete = false;
        //int totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));

        while ((count = inputStream.read(data)) != -1) {

            total += count;
            int progress = (int) ((double) (total * 100) / (double) fileSize);


            updateNotification(progress);
            outputStream.write(data, 0, count);
            downloadComplete = true;
        }
        onDownloadComplete(downloadComplete);
        outputStream.flush();
        outputStream.close();
        inputStream.close();

    }

    private void updateNotification(int currentProgress) {


        notificationBuilder.setProgress(100, currentProgress, false);
        notificationBuilder.setContentText("Downloaded: " + currentProgress + "%");
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void sendProgressUpdate(boolean downloadComplete) {

        Intent intent = new Intent(DetailELibraryFragment.PROGRESS_UPDATE);
        intent.putExtra("downloadComplete", downloadComplete);
        LocalBroadcastManager.getInstance(BackgroundNotificationService.this).sendBroadcast(intent);

    }

    private void onDownloadComplete(boolean downloadComplete) {
        sendProgressUpdate(downloadComplete);
        notificationBuilder.setSmallIcon(R.drawable.notifacation_icon);
        notificationManager.cancel(0);
        notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("Book Download Complete");
        notificationManager.notify(0, notificationBuilder.build());


      /*  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse("/storage/emulated/0/Download");
        intent.setDataAndType(uri, "text/csv");
        startActivity(Intent.createChooser(intent, "Open folder"));*/
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

}
