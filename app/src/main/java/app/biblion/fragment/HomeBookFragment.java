package app.biblion.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeBookFragment extends Fragment {
    private static final String TAG = "HomeBookFragment";
    View mView;
    Context context;
    ImageView devotion_image;
    TextView txt_BhaktiDesc, txtDevotion;
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
        txt_BhaktiDesc = mView.findViewById(R.id.bhakti_txt);
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

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getActivity().getWindow().getDecorView().findViewById(R.id.image_devotion);
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
        String textToShare = "Keshu odedara";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_TEXT, String.valueOf(dicription));
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
                        .replace("\\r\\n\\r\\n", "");

                Glide.with(getActivity()).load(response.body().getImage())
                        .thumbnail(0.5f)
                        .crossFade()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(devotion_image);
                String strinHtml = txtDesc.replace("&amp;nbsp;", "  ");
                dicription = (Html.fromHtml(strinHtml));
                txt_BhaktiDesc.setText(dicription);


            }

            @Override
            public void onFailure(Call<DevotionModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                txt_BhaktiDesc.setText(Html.fromHtml(getString(R.string.nice_html)));
            }
        });

    }
        /*recyclerView_Home = mView.findViewById(R.id.recyclerview_home);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_Home.setLayoutManager(layoutManager);
        homeViewPagerAdapter = new HomeViewPagerAdapter(getActivity(),dataBeanlist);
        recyclerView_Home.setAdapter(homeViewPagerAdapter);

            for (int i = 0; i<dataBeanlist.size(); i++)
            {
                dataBeanlist.add
            }*/
       /* recyclerView_Home.addOnScrollListener(new PaginationScrollListenerLinear((LinearLayoutManager) layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        loadFirstPage();
    }*/

       /* private void loadFirstPage () {
            Log.d(TAG, "loadFirstPage:");
            Call<HomeModel> modelCall = Constant.apiService.getHomeList(currentPage);
            modelCall.enqueue(new Callback<HomeModel>() {
                @Override
                public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                    Constant.mImagesPath = response.body().getPath();
                    List<HomeModel.ResultBean> resultBeans = response.body().getResult();

                    homeAdapter.addAll(resultBeans);
                    if (currentPage <= TOTAL_PAGES) homeAdapter.addLoadingFooter();
                    else isLastPage = true;
                }

                @Override
                public void onFailure(Call<HomeModel> call, Throwable t) {

                }
            });
        }

        private void loadNextPage () {
            Log.d(TAG, "loadNextPage:" + currentPage);
            Call<HomeModel> modelCall = Constant.apiService.getHomeList(currentPage);
            modelCall.enqueue(new Callback<HomeModel>() {
                @Override
                public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                    homeAdapter.removeLoadingFooter();
                    isLoading = false;

                    List<HomeModel.ResultBean> resultBeans = response.body().getResult();

                    homeAdapter.addAll(resultBeans);

                    if (currentPage != TOTAL_PAGES) homeAdapter.addLoadingFooter();
                    else isLastPage = true;
                }

                @Override
                public void onFailure(Call<HomeModel> call, Throwable t) {

                }
            });
        }*/

}