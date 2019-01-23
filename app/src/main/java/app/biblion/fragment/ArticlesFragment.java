package app.biblion.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.biblion.R;
import app.biblion.adpater.ArticlesAdapter;
import app.biblion.adpater.util.PaginationScrollListenerLinear;
import app.biblion.model.ArticalModel;
import app.biblion.retrofit.ApiClient;
import app.biblion.retrofit.ApiInterface;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticlesFragment extends Fragment {
    private static final String TAG = "ArticlesFragment";
    View mView;
    Context context;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private int TOTAL_PAGES = 2;
    ArticlesAdapter articlesAdapter;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView_article;
    int[] num = new int[]{1,2,3,4,5,6,7,8,9,10};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.articals_fragment, container, false);
        getActivity().setTitle("Articles");
        init();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Fab Button Clicked", Snackbar.LENGTH_SHORT)
                        .show();

            }
        });
        return mView;
    }

    public void init()
    {
        floatingActionButton = mView.findViewById(R.id.btn_fab);
        recyclerView_article = mView.findViewById(R.id.recycle_article);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false);
        recyclerView_article.setLayoutManager(layoutManager);
        articlesAdapter = new ArticlesAdapter(getActivity());
        recyclerView_article.setAdapter(articlesAdapter);
        recyclerView_article.addOnScrollListener(new PaginationScrollListenerLinear((LinearLayoutManager) layoutManager) {
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

    private void loadFirstPage() {

        Log.d(TAG, "loadFirstPage: ");
        Call<ArticalModel> modelCall = Constant.apiService.getArticalList(currentPage);
        modelCall.enqueue(new Callback<ArticalModel>() {
            @Override
            public void onResponse(Call<ArticalModel> call, Response<ArticalModel> response) {

                List<ArticalModel.ResultBean> results = response.body().getResult();
                // progressBar.setVisibility(View.GONE);
                articlesAdapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) articlesAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<ArticalModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        Call<ArticalModel> modelCall = Constant.apiService.getArticalList(currentPage);
        modelCall.enqueue(new Callback<ArticalModel>() {
            @Override
            public void onResponse(Call<ArticalModel> call, Response<ArticalModel> response) {
                articlesAdapter.removeLoadingFooter();
                isLoading = false;

                List<ArticalModel.ResultBean> results = response.body().getResult();
                articlesAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) articlesAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<ArticalModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}