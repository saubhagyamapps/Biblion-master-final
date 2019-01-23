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
import app.biblion.adpater.MyLibraryBookAdepter;
import app.biblion.adpater.util.PaginationScrollListenerLinear;
import app.biblion.interfacea.BookClick;
import app.biblion.model.MyLibraryBookModel;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ELibraryFragment extends Fragment {
    private static final String TAG = "ELibraryFragment";
    View mView;
    Context context;
    MyLibraryBookAdepter myLibraryAdapter;
    RecyclerView recyclerView_article;
    RecyclerView recycleviewTopDownload;
    int[] num = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private int TOTAL_PAGES = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.elibray_fragment, container, false);
        getActivity().setTitle("E Library");
        init();
        return mView;
    }

    public void init() {
        recyclerView_article = mView.findViewById(R.id.recyclerviewMyLibrary);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_article.setLayoutManager(layoutManager);
        myLibraryAdapter = new MyLibraryBookAdepter(getActivity(), new BookClick() {
            @Override
            public void bookClick() {
                getFragmentManager().beginTransaction().replace(R.id.contant_frame, new DetailELibraryFragment()).addToBackStack("fragment").commit();
            }
        });
        recyclerView_article.setAdapter(myLibraryAdapter);
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


   /*     myLibraryAdapter = new MyLibraryBookAdepter(context, num, new BookClick() {
            @Override
            public void bookClick() {
                getFragmentManager().beginTransaction().replace(R.id.contant_frame, new DetailELibraryFragment()).addToBackStack("fragment").commit();
            }
        });
        */
        recycleviewTopDownload = mView.findViewById(R.id.recycleviewTopDownload);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycleviewTopDownload.setLayoutManager(layoutManager1);
      /*  myLibraryAdapter = new MyLibraryAdapter(context, num, new BookClick() {
            @Override
            public void bookClick() {
                getFragmentManager().beginTransaction().replace(R.id.contant_frame, new DetailELibraryFragment()).addToBackStack("fragment").commit();

            }
        });*/

        loadFirstPage();
    }

    private void loadFirstPage() {

        Log.d(TAG, "loadFirstPage: ");
        Call<MyLibraryBookModel> modelCall = Constant.apiService.getMyLibraryBook(currentPage);
        modelCall.enqueue(new Callback<MyLibraryBookModel>() {
            @Override
            public void onResponse(Call<MyLibraryBookModel> call, Response<MyLibraryBookModel> response) {

                List<MyLibraryBookModel.ResultBean> results = response.body().getResult();
                TOTAL_PAGES=response.body().getTotalPages();
                myLibraryAdapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) myLibraryAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MyLibraryBookModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        Call<MyLibraryBookModel> modelCall = Constant.apiService.getMyLibraryBook(currentPage);
        modelCall.enqueue(new Callback<MyLibraryBookModel>() {
            @Override
            public void onResponse(Call<MyLibraryBookModel> call, Response<MyLibraryBookModel> response) {
                myLibraryAdapter.removeLoadingFooter();
                isLoading = false;

                List<MyLibraryBookModel.ResultBean> results = response.body().getResult();
                myLibraryAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) myLibraryAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MyLibraryBookModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}