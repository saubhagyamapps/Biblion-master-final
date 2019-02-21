package app.biblion.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.biblion.R;
import app.biblion.adpater.CountryLeaderAdapter;
import app.biblion.adpater.GlobalLeaderAdapter;
import de.hdodenhof.circleimageview.CircleImageView;


public class GlobalFragment extends Fragment {

    View mView;
    RecyclerView recyclerView_global;
    int[] num = new int[]
            {
                    1, 2, 3, 4, 5, 6, 7, 8, 9, 10
            };
    private GlobalLeaderAdapter globalLeaderAdapter;
    private CircleImageView First_Rank_Image, Second_Rank_Image, Third_Rank_Image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_global, container, false);
        init();
        return mView;
    }
    private void init()
    {
        First_Rank_Image = mView.findViewById(R.id.first_rank_image_global);
        Second_Rank_Image = mView.findViewById(R.id.second_rank_image_global);
        Third_Rank_Image = mView.findViewById(R.id.third_rank_image_global);
        recyclerView_global = mView.findViewById(R.id.global_leader_recyclerview);
        globalLeaderAdapter = new GlobalLeaderAdapter(getActivity(),num);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView_global.setLayoutManager(layoutManager);
        recyclerView_global.setHasFixedSize(true);
        globalLeaderAdapter.notifyDataSetChanged();
        recyclerView_global.setAdapter(globalLeaderAdapter);
    }
}
