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
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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


public class ELibraryFragment extends Fragment implements View.OnClickListener {
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
    HorizontalScrollView lianerTital;
    LinearLayout layoutMyLiabary;
    ScrollView scrollViewMyLibrary;
    Button btnOldTestament, btnNewTestament, btnTheology, btnPastoralcareAndCounseling, btnCommunication, btnReligion, btnOther;
    int mCurCheckPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.elibray_fragment, container, false);
        getActivity().setTitle("E Library");
        //init();
        setRetainInstance(true);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("E-Library");
        init();
    }

    public void init() {
        recyclerView_article = mView.findViewById(R.id.recyclerviewMyLibrary);
        etSerachView = mView.findViewById(R.id.etSerachView);
        serachView_layout = mView.findViewById(R.id.serachView_layout);
        lianerTital = mView.findViewById(R.id.lianerTital);
        layoutMyLiabary = mView.findViewById(R.id.layoutMyLiabary);

        btnOldTestament = mView.findViewById(R.id.btnOldTestament);
        btnNewTestament = mView.findViewById(R.id.btnNewTestament);
        btnTheology = mView.findViewById(R.id.btnTheology);
        btnPastoralcareAndCounseling = mView.findViewById(R.id.btnPastoralcareAndCounseling);
        btnCommunication = mView.findViewById(R.id.btnCommunication);
        btnReligion = mView.findViewById(R.id.btnReligion);
        btnOther = mView.findViewById(R.id.btnOther);


        btnOldTestament.setOnClickListener(this);
        btnNewTestament.setOnClickListener(this);
        btnTheology.setOnClickListener(this);
        btnPastoralcareAndCounseling.setOnClickListener(this);
        btnCommunication.setOnClickListener(this);
        btnReligion.setOnClickListener(this);
        btnOther.setOnClickListener(this);

        serrchButtonClick();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_article.setLayoutManager(layoutManager);
        myLibraryAdapter = new MyLibraryBookAdepter(getActivity(), new BookClick() {
            @Override
            public void bookClick(String id) {

                DetailELibraryFragment fragmentB = new DetailELibraryFragment();
                Bundle args = new Bundle();
                args.putString("id", id);
                fragmentB.setArguments(args);
                getFragmentManager().beginTransaction().addToBackStack(null)
                        .add(R.id.contant_frame, fragmentB)
                        .commit();
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
        GridLayoutManager layoutManager1 = new GridLayoutManager(context, 3);
        recycleviewTopDownload.setLayoutManager(layoutManager1);
        myLibraryBookTopDownloadAdepter = new MyLibraryBookTopDownloadAdepter(getActivity(), new BookClick() {
            @Override
            public void bookClick(String id) {

                DetailELibraryFragment fragmentB = new DetailELibraryFragment();
                Bundle args = new Bundle();
                args.putString("id", id);
                fragmentB.setArguments(args);
                getFragmentManager().beginTransaction().addToBackStack(null)
                        .add(R.id.contant_frame, fragmentB)
                        .commit();
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
                            lianerTital.setClickable(false);
                            lianerTital.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return true;
                                }
                            });

                        } else {
                            Flag = true;
                            serachView_layout.setVisibility(View.GONE);
                            lianerTital.setClickable(true);
                            lianerTital.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return false;
                                }
                            });
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOldTestament:
                setFragment(btnOldTestament.getText().toString());
                break;
            case R.id.btnNewTestament:
                setFragment(btnNewTestament.getText().toString());
                break;
            case R.id.btnTheology:
                setFragment(btnTheology.getText().toString());
                break;
            case R.id.btnPastoralcareAndCounseling:
                setFragment(btnPastoralcareAndCounseling.getText().toString());
                break;
            case R.id.btnCommunication:
                setFragment(btnCommunication.getText().toString());
                break;
            case R.id.btnReligion:
                setFragment(btnReligion.getText().toString());
                break;
            case R.id.btnOther:
                setFragment(btnOther.getText().toString());
                break;

        }
    }

    private void setFragment(String CaterogyName) {
        currentPageMYLiabray = 1;
        currentPageTopDownloadBook = 1;
        CategoryBookFragment fragmentB = new CategoryBookFragment();
        Bundle args = new Bundle();
        args.putString("CaterogyName", CaterogyName);
        fragmentB.setArguments(args);
        getFragmentManager().beginTransaction().addToBackStack("fragment")
                .add(R.id.contant_frame, fragmentB)
                .commit();

    }

}