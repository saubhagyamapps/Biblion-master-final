package app.biblion.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import app.biblion.R;
import app.biblion.adpater.CategoryBookAdepter;
import app.biblion.adpater.util.PaginationScrollListenerGridlaout;
import app.biblion.interfacea.BookClick;
import app.biblion.model.CategoryModel;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryBookFragment extends Fragment {
    private static final String TAG = "ArticlesFragment";
    View mView;
    Context context;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private int TOTAL_PAGES;
    CategoryBookAdepter articlesAdapter;
    FloatingActionButton floatingActionButton;
    RecyclerView recycle_category;
    String CaterogyName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.category_fragment, container, false);

        Bundle args = getArguments();
        CaterogyName = args.getString("CaterogyName");
        getActivity().setTitle(CaterogyName);
        init();
        return mView;
    }

    public void init() {

        recycle_category = mView.findViewById(R.id.recycle_category);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recycle_category.setLayoutManager(layoutManager);
        articlesAdapter = new CategoryBookAdepter(getActivity(), new BookClick() {
            @Override
            public void bookClick(String id) {

                ArticleClickFragment articleClickFragment = new ArticleClickFragment();
                Bundle args = new Bundle();
                args.putString("id", id);
                articleClickFragment.setArguments(args);
                getFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.contant_frame, articleClickFragment).commit();
            }
        });

        recycle_category.setAdapter(articlesAdapter);
        recycle_category.addOnScrollListener(new PaginationScrollListenerGridlaout((GridLayoutManager) layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (TOTAL_PAGES != 1) {
                            loadNextPage();
                        }else {
                            articlesAdapter.removeLoadingFooter();
                        }

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
        Constant.progressDialog(getActivity());
        Log.d(TAG, "loadFirstPage: ");
        Call<CategoryModel> modelCall = Constant.apiService.getCategoryBook(CaterogyName, currentPage);
        modelCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                TOTAL_PAGES = response.body().getTotalPages();
                List<CategoryModel.ResultBean> results = response.body().getResult();
                Constant.mImagesPath = response.body().getPath();
                articlesAdapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) articlesAdapter.addLoadingFooter();
                else isLastPage = true;

                Constant.progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Constant.progressBar.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        Call<CategoryModel> modelCall = Constant.apiService.getCategoryBook(CaterogyName, currentPage);
        modelCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                if (TOTAL_PAGES != 1) {
                    articlesAdapter.removeLoadingFooter();
                    isLoading = false;

                    List<CategoryModel.ResultBean> results = response.body().getResult();
                    articlesAdapter.addAll(results);

                    if (currentPage != TOTAL_PAGES) articlesAdapter.addLoadingFooter();
                    else isLastPage = true;
                } else {
                    articlesAdapter.removeLoadingFooter();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}