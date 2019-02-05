package app.biblion.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.biblion.R;
import app.biblion.adpater.FilltheBlanksAdapter;

public class FillBlanksFragment extends Fragment {
    View mView;
    private RecyclerView recyclerView_fillBlanks;
    private FilltheBlanksAdapter filltheBlanksAdapter;
    int[] num = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_fill_blanks, container, false);
        getActivity().setTitle("Fill in the blanks Quiz");

        init();
        return mView;
    }

    private void init() {
        recyclerView_fillBlanks = mView.findViewById(R.id.recycle_view_filltheblanks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView_fillBlanks.setLayoutManager(layoutManager);
        filltheBlanksAdapter = new FilltheBlanksAdapter(getActivity(), num);
        recyclerView_fillBlanks.setAdapter(filltheBlanksAdapter);

    }
}