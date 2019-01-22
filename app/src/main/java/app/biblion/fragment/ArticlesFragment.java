package app.biblion.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.biblion.R;
import app.biblion.adpater.ArticlesAdapter;


public class ArticlesFragment extends Fragment {
    View mView;
    Context context;
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
        articlesAdapter = new ArticlesAdapter(context, num);
        recyclerView_article.setAdapter(articlesAdapter);
    }
}