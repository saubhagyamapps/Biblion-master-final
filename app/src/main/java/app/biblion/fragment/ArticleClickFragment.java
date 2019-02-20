package app.biblion.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;

import app.biblion.R;
import app.biblion.model.ArticalDetailsModel;
import app.biblion.model.LikeModel;
import app.biblion.sessionmanager.SessionManager;
import app.biblion.util.Constant;
import me.biubiubiu.justifytext.library.JustifyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleClickFragment extends Fragment {
    View mView;
    String mArticaleId;
    ImageView clicked_Imagel, like_Image;
    TextView clicked_Desc_txt, Clicked_Title_txt, Clicked_Heading_txt, like_Count, article_Share;
    String Clicked_Heading;
    ActionBar actionBar;
    JustifyTextView Article_desc;
    SessionManager sessionManager;
    HashMap<String, String> user;
    int mLike;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_article_click, container, false);
//      getActivity().setTitle("Love Your Neighbour"like_count);
        Bundle args = getArguments();
        mArticaleId = args.getString("id");
        init();
        return mView;
    }


    private void init() {
        like_Image = mView.findViewById(R.id.like_image);

        clicked_Imagel = mView.findViewById(R.id.click_article_image);
        //clicked_Desc_txt = mView.findViewById(R.id.click_articaldesc);
        Clicked_Title_txt = mView.findViewById(R.id.click_article_title);
        Article_desc = mView.findViewById(R.id.click_articaldesc);
        like_Count = mView.findViewById(R.id.like_count);
        article_Share = mView.findViewById(R.id.share_article);
        sessionManager = new SessionManager(getActivity());
        user = sessionManager.getUserDetails();
        likedClicked();
        getDetailArtical();
        shareClicked();
    }

    private void shareClicked()
    {
        article_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                takeScreenshot();

            }
        });
    }

    private void likedClicked() {
        like_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mLike == 1) {
                    mLike = 0;
                    like_Image.setBackgroundResource(R.drawable.heart);
                } else {
                    mLike = 1;
                    like_Image.setBackgroundResource(R.drawable.heart_liked);
                }
                APICALL_LIKE();
            }
        });
    }

    private void APICALL_LIKE() {
        Constant.progressDialog(getActivity());
        Call<LikeModel> likeModelCall = Constant.apiService.getLikes(user.get(sessionManager.KEY_ID), mArticaleId, mLike);
        likeModelCall.enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(Call<LikeModel> call, Response<LikeModel> response) {
                like_Count.setText(String.valueOf(response.body().getLikes()));

                Constant.progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<LikeModel> call, Throwable t) {
                Constant.progressBar.dismiss();
            }
        });
    }

    private void getDetailArtical() {
        Constant.progressDialog(getActivity());
        Call<ArticalDetailsModel> articalClickModelCall = Constant.apiService.getArticalDetails(Integer.parseInt(mArticaleId));
        articalClickModelCall.enqueue(new Callback<ArticalDetailsModel>() {
            @Override
            public void onResponse(Call<ArticalDetailsModel> call, Response<ArticalDetailsModel> response) {

                // clicked_Desc_txt.setText(response.body().getResult().get(0).getDescription().replace("\\r\\n",""));
                Article_desc.setText(response.body().getResult().get(0).getDescription().replace("\\r\\n", ""));
                Clicked_Title_txt.setText(response.body().getResult().get(0).getTitle());
                Glide.with(getActivity()).load(response.body().getImage())
                        .thumbnail(0.5f)
                        .crossFade()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(clicked_Imagel);
                like_Count.setText(String.valueOf(response.body().getLike()));
                mLike = response.body().getLike_status();
                if (response.body().getLike_status() == 1) {
                    like_Image.setBackgroundResource(R.drawable.heart_liked);
                } else {
                    like_Image.setBackgroundResource(R.drawable.heart);
                }
                Constant.progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<ArticalDetailsModel> call, Throwable t) {

                Constant.progressBar.dismiss();
            }
        });

    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getActivity().getWindow().getDecorView().findViewById(R.id.artcile_layout_screenshot);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {

        Uri uri = Uri.fromFile(imageFile);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_TEXT,  Article_desc +"https://play.google.com/store/apps/dev?id=7665705150185283127");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share with"));
    }
}
