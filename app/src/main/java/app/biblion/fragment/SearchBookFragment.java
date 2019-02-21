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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.biblion.R;
import app.biblion.adpater.CategoryBookAdepter;
import app.biblion.adpater.SearchBookAdepter;
import app.biblion.adpater.util.PaginationScrollListenerGridlaout;
import app.biblion.interfacea.BookClick;
import app.biblion.model.SearchModel;
import app.biblion.model.SearchModel;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchBookFragment extends Fragment {
    private static final String TAG = "ArticlesFragment";
    View mView;
    Context context;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private int TOTAL_PAGES=1;
    SearchBookAdepter bookAdepter;
    RecyclerView recycle_search;
    String mType,mValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.search_book_fragment, container, false);

        Bundle args = getArguments();
        mType = args.getString("type");
        mValue = args.getString("value");
        getActivity().setTitle(mType);
        init();
        return mView;
    }

    public void init() {

        recycle_search = mView.findViewById(R.id.recycle_search);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recycle_search.setLayoutManager(layoutManager);
        bookAdepter = new SearchBookAdepter(getActivity(), new BookClick() {
            @Override
            public void bookClick(String id) {

                DetailELibraryFragment articleClickFragment = new DetailELibraryFragment();
                Bundle args = new Bundle();
                args.putString("id", id);
                articleClickFragment.setArguments(args);
                getFragmentManager().beginTransaction().addToBackStack(null)
                        .add(R.id.contant_frame, articleClickFragment).commit();
            }
        });

        recycle_search.setAdapter(bookAdepter);
        recycle_search.addOnScrollListener(new PaginationScrollListenerGridlaout((GridLayoutManager) layoutManager) {
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
                            bookAdepter.removeLoadingFooter();
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
        Call<SearchModel> modelCall = Constant.apiService.getSearchList(mType,mValue,currentPage);
        modelCall.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                TOTAL_PAGES = response.body().getTotalPages();
                List<SearchModel.ResultBean> results = response.body().getResult();
                Constant.mImagesPath = response.body().getPath();
                bookAdepter.addAll(results);

                if (currentPage <= TOTAL_PAGES) bookAdepter.addLoadingFooter();
                else isLastPage = true;

                Constant.progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                Constant.progressBar.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        Call<SearchModel> modelCall = Constant.apiService.getSearchList(mType,mValue,currentPage);
        modelCall.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if (TOTAL_PAGES != 1) {
                    bookAdepter.removeLoadingFooter();
                    isLoading = false;

                    List<SearchModel.ResultBean> results = response.body().getResult();
                    bookAdepter.addAll(results);

                    if (currentPage != TOTAL_PAGES) bookAdepter.addLoadingFooter();
                    else isLastPage = true;
                } else {
                    bookAdepter.removeLoadingFooter();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}