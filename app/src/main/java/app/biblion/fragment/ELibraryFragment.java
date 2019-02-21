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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private int TOTAL_PAGES_MyLibary;

    private boolean isLoadingTopDownloadBook = false;
    private boolean isLastPageTopDownloadBook = false;
    private static final int PAGE_START_TopDownloadBook = 1;
    private int currentPageTopDownloadBook = PAGE_START_TopDownloadBook;
    private int TOTAL_PAGES_TopDownloadBook;

    EditText etSerachView;
    LinearLayout serachView_layout;
    boolean Flag = true;
    HorizontalScrollView lianerTital;
    LinearLayout layoutMyLiabary;
    Button btnOldTestament, btnNewTestament, btnTheology, btnPastoralcareAndCounseling, btnCommunication, btnReligion, btnOther;

    int mCurCheckPosition;
    TextView filter_Title, filter_Publisher, filter_Year, filter_Author, filter_Language, filter_Apply;
    boolean mFilter = true, mPublisher = true, mYear = true, mAuthor = true, mLanguage = true;
    String FilterFlag = "1";
    boolean mKeyBordOpen = true;
    String mSelectType;
    String mApplyClick="0";

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
        etSerachView.setInputType(InputType.TYPE_NULL);

        btnOldTestament.setOnClickListener(this);
        btnNewTestament.setOnClickListener(this);
        btnTheology.setOnClickListener(this);
        btnPastoralcareAndCounseling.setOnClickListener(this);
        btnCommunication.setOnClickListener(this);
        btnReligion.setOnClickListener(this);
        btnOther.setOnClickListener(this);

        filter_Title = mView.findViewById(R.id.filter_title);
        filter_Publisher = mView.findViewById(R.id.filter_publisher);
        filter_Year = mView.findViewById(R.id.filter_year);
        filter_Year = mView.findViewById(R.id.filter_year);
        filter_Author = mView.findViewById(R.id.filter_author);
        filter_Language = mView.findViewById(R.id.filter_language);
        filter_Apply = mView.findViewById(R.id.filter_apply);

        onTouchClick();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Constant.hideKeyboard(getActivity(), mView);
    }

    private void showKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
    }

    private void serrchButtonClick() {
        etSerachView.performClick();
        etSerachView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    if (Flag) {
                        Flag = false;
                        serachView_layout.setVisibility(View.VISIBLE);
                        lianerTital.setClickable(false);
                        btnOldTestament.setClickable(false);
                        btnNewTestament.setClickable(false);
                        btnTheology.setClickable(false);
                        btnPastoralcareAndCounseling.setClickable(false);
                        btnCommunication.setClickable(false);
                        btnReligion.setClickable(false);
                        btnOther.setClickable(false);
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
                        btnOldTestament.setClickable(true);
                        btnNewTestament.setClickable(true);
                        btnTheology.setClickable(true);
                        btnPastoralcareAndCounseling.setClickable(true);
                        btnCommunication.setClickable(true);
                        btnReligion.setClickable(true);
                        btnOther.setClickable(true);
                        lianerTital.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                    if (event.getRawX() >= (etSerachView.getRight() - etSerachView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Log.e(TAG, "onTouch: ");
                        if (Flag) {
                            Flag = false;
                            serachView_layout.setVisibility(View.VISIBLE);
                            lianerTital.setClickable(false);
                            btnOldTestament.setClickable(false);
                            btnNewTestament.setClickable(false);
                            btnTheology.setClickable(false);
                            btnPastoralcareAndCounseling.setClickable(false);
                            btnCommunication.setClickable(false);
                            btnReligion.setClickable(false);
                            btnOther.setClickable(false);
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
                            btnOldTestament.setClickable(true);
                            btnNewTestament.setClickable(true);
                            btnTheology.setClickable(true);
                            btnPastoralcareAndCounseling.setClickable(true);
                            btnCommunication.setClickable(true);
                            btnReligion.setClickable(true);
                            btnOther.setClickable(true);
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
        Constant.progressDialog(getActivity());
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
                Constant.progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<MyLibraryBookModel> call, Throwable t) {
                t.printStackTrace();
                Constant.progressBar.dismiss();
            }
        });
    }

    private void loadNextPageMyLibrary() {
        Log.d(TAG, "loadNextPageMyLibrary:--> " + currentPageMYLiabray);
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
        Log.d(TAG, "loadNextPageTopDowloadBook:--> " + currentPageMYLiabray);
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


   private void onTouchClick()
   {
       filter_Title.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {

                   etSerachView.setSingleLine();
                   etSerachView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                   InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(context.INPUT_METHOD_SERVICE);
                   inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                   filter_Title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


               return false;
           }
       });

       filter_Publisher.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               return false;
           }
       });

       filter_Year.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               return false;
           }
       });

       filter_Author.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               return false;
           }
       });
       filter_Language.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               return false;
           }
       });
       filter_Apply.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               return false;
           }
       });
   }

    private void onTouchClick() {
        etSerachView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                serachView_layout.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSerachView.performClick();
        filter_Title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mFilter) {
                    mSelectType = filter_Title.getText().toString();
                    mFilter = false;
                    mPublisher = true;
                    mYear = true;
                    mAuthor = true;
                    mLanguage = true;
                    if (mKeyBordOpen) {
                        FilterFlag = "0";
                    }
               /*     etSerachView.setSingleLine();
                    etSerachView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    etSerachView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                   etSerachView.performClick();*/

                    filter_Title.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button_color));
                    filter_Publisher.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Year.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Author.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Language.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));

                } else {
                    FilterFlag = "1";
                    mFilter = true;
                    mKeyBordOpen = true;
                    etSerachView.setInputType(InputType.TYPE_NULL);
                    filter_Title.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    Constant.hideKeyboard(getActivity(), mView);
                }

                return false;
            }
        });

        filter_Publisher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mPublisher) {
                    mSelectType = filter_Publisher.getText().toString();
                    mFilter = true;
                    mPublisher = false;
                    mYear = true;
                    mAuthor = true;
                    mLanguage = true;
                    if (mKeyBordOpen) {
                        FilterFlag = "0";
                    }
                   /* etSerachView.setSingleLine();
                    etSerachView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    etSerachView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                    etSerachView.performClick();*/
                    filter_Title.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Publisher.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button_color));
                    filter_Year.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Author.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Language.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));

                } else {
                    FilterFlag = "1";
                    mPublisher = true;
                    mKeyBordOpen = true;
                    etSerachView.setInputType(InputType.TYPE_NULL);
                    filter_Publisher.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    Constant.hideKeyboard(getActivity(), mView);
                }

                return false;
            }
        });
        filter_Year.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mYear) {
                    mSelectType = filter_Year.getText().toString();
                    mFilter = true;
                    mPublisher = true;
                    mYear = false;
                    mAuthor = true;
                    mLanguage = true;
                    if (mKeyBordOpen) {
                        FilterFlag = "0";
                    }
                /*    etSerachView.setSingleLine();
                    etSerachView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    etSerachView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                    etSerachView.performClick();*/
                    filter_Title.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Publisher.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Year.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button_color));
                    filter_Author.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Language.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));

                } else {
                    FilterFlag = "1";
                    mYear = true;
                    mKeyBordOpen = true;
                    etSerachView.setInputType(InputType.TYPE_NULL);
                    filter_Year.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    Constant.hideKeyboard(getActivity(), mView);
                }

                return false;
            }
        });
        filter_Author.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mAuthor) {
                    mSelectType = filter_Author.getText().toString();
                    mFilter = true;
                    mPublisher = true;
                    mYear = true;
                    mAuthor = false;
                    mLanguage = true;
                    if (mKeyBordOpen) {
                        FilterFlag = "0";
                    }
                  /*  etSerachView.setSingleLine();
                    etSerachView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    etSerachView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                    etSerachView.performClick();*/
                    filter_Title.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Publisher.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Year.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Author.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button_color));
                    filter_Language.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));

                } else {
                    FilterFlag = "1";
                    mAuthor = true;
                    mKeyBordOpen = true;
                    etSerachView.setInputType(InputType.TYPE_NULL);
                    filter_Author.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    Constant.hideKeyboard(getActivity(), mView);
                }

                return false;
            }
        });
        filter_Language.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mLanguage) {
                    mSelectType = filter_Language.getText().toString();
                    mFilter = true;
                    mPublisher = true;
                    mYear = true;
                    mAuthor = true;
                    mLanguage = false;
                    if (mKeyBordOpen) {
                        FilterFlag = "0";
                    }
                /*    etSerachView.setSingleLine();
                    etSerachView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    etSerachView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                    etSerachView.performClick();*/
                    filter_Title.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Publisher.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Year.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Author.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Language.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button_color));

                } else {
                    FilterFlag = "1";
                    mLanguage = true;
                    mKeyBordOpen = true;
                    etSerachView.setInputType(InputType.TYPE_NULL);
                    filter_Language.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    Constant.hideKeyboard(getActivity(), mView);
                }

                return false;
            }
        });

        filter_Apply.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!mFilter || !mPublisher || !mYear || !mAuthor || !mLanguage) {
                    FilterFlag = "0";
                    mApplyClick = "1";
                    etSerachView.setSingleLine();
                    etSerachView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    etSerachView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                    etSerachView.performClick();
                    serachView_layout.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getActivity(), "Please select category", Toast.LENGTH_LONG).show();
                    FilterFlag = "1";
                    mLanguage = true;
                    mKeyBordOpen = true;
                    etSerachView.setInputType(InputType.TYPE_NULL);
                    filter_Language.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    Constant.hideKeyboard(getActivity(), mView);
                }
                return false;
            }
        });

        etSerachView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApplyClick.equals("1")) {
                    mApplyClick="0";
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    etSerachView.setSingleLine();
                    etSerachView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    etSerachView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lianerTital.setClickable(true);
                            btnOldTestament.setClickable(true);
                            btnNewTestament.setClickable(true);
                            btnTheology.setClickable(true);
                            btnPastoralcareAndCounseling.setClickable(true);
                            btnCommunication.setClickable(true);
                            btnReligion.setClickable(true);
                            btnOther.setClickable(true);
                            lianerTital.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return false;
                                }
                            });
                        }
                    }, 1000);
                }

            /*    if (FilterFlag.equals("0")) {
                    mKeyBordOpen = false;
                    FilterFlag = "1";
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                }*/
            }
        });
        etSerachView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    etSerachView.setInputType(InputType.TYPE_NULL);
                    Constant.hideKeyboard(getActivity(), mView);
                    mFilter = true;
                    mPublisher = true;
                    mYear = true;
                    mAuthor = true;
                    mLanguage = true;
                    if (mKeyBordOpen) {
                        FilterFlag = "0";
                    }
                    filter_Title.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Publisher.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Year.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Author.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    filter_Language.setBackground(getResources().getDrawable(R.drawable.shadow_effect_button));
                    SearchBookFragment fragmentB = new SearchBookFragment();
                    Bundle args = new Bundle();
                    args.putString("type", mSelectType);
                    args.putString("value", etSerachView.getText().toString());
                    etSerachView.setText("");
                    fragmentB.setArguments(args);
                    getFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.contant_frame, fragmentB)
                            .commit();

                    return true;
                }
                return false;
            }
        });
    }

}