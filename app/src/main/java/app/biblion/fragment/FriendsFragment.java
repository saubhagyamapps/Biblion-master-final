package app.biblion.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.biblion.R;

public class FriendsFragment extends Fragment {

    View mView;
    RecyclerView recyclerView_friends;
    int[] num = new int[]
            {
                    1, 2, 3, 4, 5, 6, 7, 8, 9, 10
            };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_friends, container, false);

        return mView;
    }

    private void init()
    {
        recyclerView_friends = mView.findViewById(R.id.friends_leader_recyclerview);
    }

}