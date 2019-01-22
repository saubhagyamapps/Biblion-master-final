package app.biblion.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.biblion.R;
import app.biblion.adpater.MyLibraryAdapter;
import app.biblion.interfacea.BookClick;


public class ELibraryFragment extends Fragment {
    View mView;
    Context context;
    MyLibraryAdapter myLibraryAdapter;
    RecyclerView recyclerView_article;
    RecyclerView recycleviewTopDownload;
    int[] num = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

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
        myLibraryAdapter = new MyLibraryAdapter(context, num, new BookClick() {
            @Override
            public void bookClick() {
                getFragmentManager().beginTransaction().replace(R.id.contant_frame, new DetailELibraryFragment()).addToBackStack("fragment").commit();
            }
        });
        recyclerView_article.setAdapter(myLibraryAdapter);
        recycleviewTopDownload = mView.findViewById(R.id.recycleviewTopDownload);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycleviewTopDownload.setLayoutManager(layoutManager1);
        myLibraryAdapter = new MyLibraryAdapter(context, num, new BookClick() {
            @Override
            public void bookClick() {
                getFragmentManager().beginTransaction().replace(R.id.contant_frame, new DetailELibraryFragment()).addToBackStack("fragment").commit();

            }
        });
        recycleviewTopDownload.setAdapter(myLibraryAdapter);
    }


}