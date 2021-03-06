package app.biblion.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.biblion.R;
import app.biblion.adpater.SlidingImage_Adapter;
import app.biblion.model.DevotionModel;
import app.biblion.model.HomeModel;
import app.biblion.util.Constant;
import me.biubiubiu.justifytext.library.JustifyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeBookFragment extends Fragment {
    private static final String TAG = "HomeBookFragment";
    View mView;
    Context context;
    ImageView devotion_image, ssImagesView;
    TextView txt_BhaktiDesc, txtDevotion, titleDevotion;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = 0;
    private int NUM_PAGES = 0;
    RecyclerView recyclerView_Home;
    private List<HomeModel.ResultBean> dataBeanlist;
    private static ViewPager viewPager;
    CirclePageIndicator indicator;
    FloatingActionButton fabShare;
    Uri uri;
    Bitmap bitmap;
    Spanned dicription;
    LinearLayout layoutScreenShort;

    @RequiresApi(api = Build.VERSION_CODES.O)
    CardView Card_Bible, Card_Quiz, Card_SongBook, Card_Article, Card_E_Library;
    JustifyTextView justifyTextView;
    TextView txtImagesTitel;
    TextView txtAppLink;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.home_fragment, container, false);
        getActivity().setTitle("Home");
        init();
        return mView;
    }

    public void init() {

        viewPager = mView.findViewById(R.id.pager);
        devotion_image = mView.findViewById(R.id.image_devotion);
        // txt_BhaktiDesc = mView.findViewById(R.id.bhakti_txt);
        titleDevotion = mView.findViewById(R.id.devotion_title);
        justifyTextView = mView.findViewById(R.id.bhakti_txt);
        layoutScreenShort = mView.findViewById(R.id.layoutScreenShort);
        txtImagesTitel = mView.findViewById(R.id.txtImagesTitel);
        ssImagesView = mView.findViewById(R.id.ssImagesView);


        Card_Bible = mView.findViewById(R.id.home_card_bible);
        Card_Quiz = mView.findViewById(R.id.home_card_quiz);
        Card_SongBook = mView.findViewById(R.id.home_card_songbook);
        Card_Article = mView.findViewById(R.id.home_card_article);
        Card_E_Library = mView.findViewById(R.id.home_card_e_library);
        clicked();
        fabShare = mView.findViewById(R.id.fabShare);
        txtDevotion = mView.findViewById(R.id.txtDevotion);
        getImagedata();
        LoadBhaktidata();
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                takeScreenshot();
            }
        });
    }

    private void clicked() {
        Card_Bible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BibleBookFragment bibleBookFragment = new BibleBookFragment();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.contant_frame, bibleBookFragment)
                        .commit();
            }
        });
        Card_Quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizFragment quizFragment = new QuizFragment();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.contant_frame, quizFragment)
                        .commit();
            }
        });

        Card_SongBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongBookFragment songBookFragment = new SongBookFragment();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.contant_frame, songBookFragment)
                        .commit();
            }
        });

        Card_Article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticlesFragment articlesFragment = new ArticlesFragment();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.contant_frame, articlesFragment)
                        .commit();
            }
        });
        Card_E_Library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ELibraryFragment eLibraryFragment = new ELibraryFragment();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.contant_frame, eLibraryFragment)
                        .commit();
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
            View v1 = getActivity().getWindow().getDecorView().findViewById(R.id.layoutScreenShort);
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
        share.putExtra(Intent.EXTRA_TEXT, String.valueOf(dicription)+"https://play.google.com/store/apps/dev?id=7665705150185283127");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share with"));
    }

    private void getImagedata() {

        Log.d(TAG, "LoadImage:");
        Call<HomeModel> modelcall = Constant.apiService.getHomeList();
        modelcall.enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                //Constant.mImagesPath = response.body().getPath();
                List<HomeModel.ResultBean> resultBeans = response.body().getResult();

                if (getActivity() != null) {
                    if (resultBeans.size() != 0) {
                        viewPager.setAdapter(new SlidingImage_Adapter(getActivity(), response.body().getResult()));

                        indicator = (CirclePageIndicator) mView.findViewById(R.id.indicator);

                        indicator.setViewPager(viewPager);


                        handlerCall(response.body().getResult().size());
                    }
                }

            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {

            }
        });


    }

    private void handlerCall(int size) {
        NUM_PAGES = size;

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    private void LoadBhaktidata() {

        Log.d(TAG, "LoadImage:");
        Call<DevotionModel> modelCall = Constant.apiService.getDevotiondata();

        modelCall.enqueue(new Callback<DevotionModel>() {
            @Override
            public void onResponse(Call<DevotionModel> call, Response<DevotionModel> response) {


                String txtDesc = response.body().getDescription().replace("&lt;", "<").replace("&gt;", ">")
                        .replace("\\r\\n\\r\\n", "").replace("\\r\\n", "");

                Glide.with(getActivity()).load(response.body().getImage())
                        .thumbnail(0.5f)
                        .crossFade()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(devotion_image);
                String strinHtml = txtDesc.replace("&amp;nbsp;", "  ");
                dicription = (Html.fromHtml(strinHtml));
                //txt_BhaktiDesc.setText(dicription);
                // SpannableString spannableString = new SpannableString(response.body().getDescription());
                //description (Html.fromHtml(strinHtml));
                justifyTextView.setText(String.valueOf(dicription));


                SpannableString content = new SpannableString(response.body().getTitle());
                content.setSpan(new UnderlineSpan(), 0, response.body().getTitle().length(), 0);
                titleDevotion.setText(content);
                txtImagesTitel.setText(response.body().getTitle());


            }

            @Override
            public void onFailure(Call<DevotionModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });


    }
}