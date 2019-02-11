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


public class CountryFragment extends Fragment {
    View mView;
    RecyclerView recyclerView_country;
    int[] num = new int[]
            {
                    1, 2, 3, 4, 5, 6, 7, 8, 9, 10
            };
    private CountryLeaderAdapter countryLeaderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_country, container, false);
        init();
        return mView;
    }

    private void init()
    {
        recyclerView_country = mView.findViewById(R.id.country_leader_recyclerview);
        countryLeaderAdapter = new CountryLeaderAdapter(getActivity(),num);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView_country.setLayoutManager(layoutManager);
        recyclerView_country.setHasFixedSize(true);
        countryLeaderAdapter.notifyDataSetChanged();
        recyclerView_country.setAdapter(countryLeaderAdapter);
    }

}
