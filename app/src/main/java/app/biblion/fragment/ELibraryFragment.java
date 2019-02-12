package app.biblion.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import app.biblion.R;
import app.biblion.adpater.MyLibraryBookAdepter;
import app.biblion.adpater.MyLibraryBookTopDownloadAdepter;
import app.biblion.adpater.util.PaginationScrollListenerGridlaout;
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
    MyLibraryBookTopDownloadAdepter myLibraryBookTopDownloadAdepter;
    RecyclerView recyclerView_article;
    RecyclerView recycleviewTopDownload;

    private boolean isLoadingMyLibary = false;
    private boolean isLastPageMylibrary = false;
    private static final int PAGE_START_MY_LIBRARY = 1;
    private int currentPageMYLiabray = PAGE_START_MY_LIBRARY;
    private int TOTAL_PAGES_MyLibary = 2;

    private boolean isLoadingTopDownloadBook = false;
    private boolean isLastPageTopDownloadBook = false;
    private static final int PAGE_START_TopDownloadBook = 1;
    private int currentPageTopDownloadBook = PAGE_START_TopDownloadBook;
    private int TOTAL_PAGES_TopDownloadBook = 2;
    EditText etSerachView;
    LinearLayout serachView_layout;
    boolean Flag = true;

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
        etSerachView = mView.findViewById(R.id.etSerachView);
        serachView_layout = mView.findViewById(R.id.serachView_layout);
        serrchButtonClick();
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
                isLoadingMyLibary = true;
                currentPageMYLiabray += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPageMyLibrary();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES_MyLibary;
            }

            @Override
            public boolean isLastPage() {
                return isLastPageMylibrary;
            }

            @Override
            public boolean isLoading() {
                return isLoadingMyLibary;
            }
        });

        recycleviewTopDownload = mView.findViewById(R.id.recycleviewTopDownload);
        GridLayoutManager layoutManager1 = new GridLayoutManager(context, 2);
        recycleviewTopDownload.setLayoutManager(layoutManager1);
        myLibraryBookTopDownloadAdepter = new MyLibraryBookTopDownloadAdepter(getActivity(), new BookClick() {
            @Override
            public void bookClick() {
                getFragmentManager().beginTransaction().replace(R.id.contant_frame, new DetailELibraryFragment()).addToBackStack("fragment").commit();
            }
        });
        recycleviewTopDownload.setAdapter(myLibraryBookTopDownloadAdepter);


        recycleviewTopDownload.addOnScrollListener(new PaginationScrollListenerGridlaout((GridLayoutManager) layoutManager1) {
            @Override
            protected void loadMoreItems() {
                isLoadingTopDownloadBook = true;
                currentPageTopDownloadBook += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPageTopDowloadBook();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES_TopDownloadBook;
            }

            @Override
            public boolean isLastPage() {
                return isLastPageTopDownloadBook;
            }

            @Override
            public boolean isLoading() {
                return isLoadingTopDownloadBook;
            }
        });


        loadFirstPageMyLibrary();
        loadFirstPageTopDowloadBook();
    }

    private void serrchButtonClick() {
        etSerachView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etSerachView.getRight() - etSerachView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Log.e(TAG, "onTouch: ");
                        if (Flag) {
                            Flag = false;
                            serachView_layout.setVisibility(View.VISIBLE);
                        } else {
                            Flag = true;
                            serachView_layout.setVisibility(View.GONE);
                        }


                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void loadFirstPageMyLibrary() {

        Log.d(TAG, "loadFirstPage: ");
        Call<MyLibraryBookModel> modelCall = Constant.apiService.getMyLibraryBook(currentPageMYLiabray);
        modelCall.enqueue(new Callback<MyLibraryBookModel>() {
            @Override
            public void onResponse(Call<MyLibraryBookModel> call, Response<MyLibraryBookModel> response) {
                //Constant.mImagesPath=response.body().getPath();
                Constant.mImagesPath = "http://frozenkitchen.in/biblion/public/images/";

                List<MyLibraryBookModel.ResultBean> results = response.body().getResult();
                TOTAL_PAGES_MyLibary = response.body().getTotalPages();
                myLibraryAdapter.addAll(results);

                if (currentPageMYLiabray <= TOTAL_PAGES_MyLibary)
                    myLibraryAdapter.addLoadingFooter();
                else isLastPageMylibrary = true;
            }

            @Override
            public void onFailure(Call<MyLibraryBookModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadNextPageMyLibrary() {
        Log.d(TAG, "loadNextPage: " + currentPageMYLiabray);
        Call<MyLibraryBookModel> modelCall = Constant.apiService.getMyLibraryBook(currentPageMYLiabray);
        modelCall.enqueue(new Callback<MyLibraryBookModel>() {
            @Override
            public void onResponse(Call<MyLibraryBookModel> call, Response<MyLibraryBookModel> response) {
                myLibraryAdapter.removeLoadingFooter();
                isLoadingMyLibary = false;

                List<MyLibraryBookModel.ResultBean> results = response.body().getResult();
                myLibraryAdapter.addAll(results);

                if (currentPageMYLiabray != TOTAL_PAGES_MyLibary)
                    myLibraryAdapter.addLoadingFooter();
                else isLastPageMylibrary = true;
            }

            @Override
            public void onFailure(Call<MyLibraryBookModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadFirstPageTopDowloadBook() {

        Log.d(TAG, "loadFirstPage: ");
        Call<MyLibraryBookModel> modelCall = Constant.apiService.getMyLibraryBook(currentPageTopDownloadBook);
        modelCall.enqueue(new Callback<MyLibraryBookModel>() {
            @Override
            public void onResponse(Call<MyLibraryBookModel> call, Response<MyLibraryBookModel> response) {
                //Constant.mImagesPath=response.body().getPath();
                Constant.mImagesPath = "http://frozenkitchen.in/biblion/public/images/";

                List<MyLibraryBookModel.ResultBean> results = response.body().getResult();
                TOTAL_PAGES_TopDownloadBook = response.body().getTotalPages();
                myLibraryBookTopDownloadAdepter.addAll(results);

                if (currentPageTopDownloadBook <= TOTAL_PAGES_TopDownloadBook)
                    myLibraryBookTopDownloadAdepter.addLoadingFooter();
                else isLastPageTopDownloadBook = true;
            }

            @Override
            public void onFailure(Call<MyLibraryBookModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadNextPageTopDowloadBook() {
        Log.d(TAG, "loadNextPage: " + currentPageMYLiabray);
        Call<MyLibraryBookModel> modelCall = Constant.apiService.getMyLibraryBook(currentPageTopDownloadBook);
        modelCall.enqueue(new Callback<MyLibraryBookModel>() {
            @Override
            public void onResponse(Call<MyLibraryBookModel> call, Response<MyLibraryBookModel> response) {
                myLibraryBookTopDownloadAdepter.removeLoadingFooter();
                isLoadingMyLibary = false;

                List<MyLibraryBookModel.ResultBean> results = response.body().getResult();
                myLibraryBookTopDownloadAdepter.addAll(results);

                if (currentPageTopDownloadBook != TOTAL_PAGES_TopDownloadBook)
                    myLibraryBookTopDownloadAdepter.addLoadingFooter();
                else isLastPageTopDownloadBook = true;
            }

            @Override
            public void onFailure(Call<MyLibraryBookModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}