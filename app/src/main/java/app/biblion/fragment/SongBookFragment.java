package app.biblion.fragment;

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
import app.biblion.adpater.SongBookAdapter;
import app.biblion.adpater.util.PaginationScrollListenerLinear;
import app.biblion.model.AllSongListModel;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SongBookFragment extends Fragment {
    View mView;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private int TOTAL_PAGES;
    RecyclerView mRecyclerView;
    SongBookAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private static final String TAG = "SongBookFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.song_book_fragment, container, false);
        getActivity().setTitle("Song Book");
        initialization();
        return mView;
    }

    private void initialization() {
        mRecyclerView = mView.findViewById(R.id.recycleViewSongList);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new SongBookAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new PaginationScrollListenerLinear((LinearLayoutManager) layoutManager) {
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
        Call<AllSongListModel> modelCall = Constant.apiService.getSongList(currentPage);
        modelCall.enqueue(new Callback<AllSongListModel>() {
            @Override
            public void onResponse(Call<AllSongListModel> call, Response<AllSongListModel> response) {
                TOTAL_PAGES=response.body().getTotalPages();
                List<AllSongListModel.ResultBean> results = response.body().getResult();
                // progressBar.setVisibility(View.GONE);
                mAdapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) mAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<AllSongListModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadNextPage() {

        Log.d(TAG, "loadNextPage: " + currentPage);
        Call<AllSongListModel> modelCall = Constant.apiService.getSongList(currentPage);
        modelCall.enqueue(new Callback<AllSongListModel>() {
            @Override
            public void onResponse(Call<AllSongListModel> call, Response<AllSongListModel> response) {
                mAdapter.removeLoadingFooter();
                isLoading = false;

                List<AllSongListModel.ResultBean> results = response.body().getResult();
                mAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) mAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<AllSongListModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}