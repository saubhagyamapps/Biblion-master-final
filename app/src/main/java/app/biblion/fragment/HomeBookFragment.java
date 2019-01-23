package app.biblion.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.biblion.R;
import app.biblion.adpater.HomeAdapter;
import app.biblion.adpater.util.PaginationScrollListenerLinear;
import app.biblion.model.HomeModel;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeBookFragment extends Fragment {
    private static final String TAG = "HomeBookFragment";
    View mView;
    Context context;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private int TOTAL_PAGES = 2;
    HomeAdapter homeAdapter;
    RecyclerView recyclerView_Home;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.home_fragment, container, false);
        getActivity().setTitle("Home");
        init();
        return mView;
    }

    public void init()
    {
        recyclerView_Home =mView.findViewById(R.id.recyclerview_home);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_Home.setLayoutManager(layoutManager);
        homeAdapter = new HomeAdapter(getActivity());
        recyclerView_Home.setAdapter(homeAdapter);
        recyclerView_Home.addOnScrollListener(new PaginationScrollListenerLinear((LinearLayoutManager) layoutManager) {
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
    }

    private void loadFirstPage ()
    {
        Log.d(TAG, "loadFirstPage:");
        Call<HomeModel> modelCall = Constant.apiService.getHomeList(currentPage);
        modelCall.enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

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

    private void loadNextPage ()
    {
        Log.d(TAG, "loadNextPage:" +currentPage);
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
    }
}