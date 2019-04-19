package app.biblion.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.biblion.R;

public class LeaderBoardFragment extends Fragment {

    View mView;
    ViewPager viewPager;
    TabLayout tabLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_leader_board, container, false);
        getActivity().setTitle(R.string.leaderboard);
        init();
        return mView;
    }

    private void init()
    {
        viewPager = mView.findViewById(R.id.vpPager);
        tabLayout = mView.findViewById(R.id.tab);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }




    private void setUpViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter  viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new FriendsFragment(), "FRIENDS");
        viewPagerAdapter.addFragment(new CountryFragment(), "COUNTRY");
        viewPagerAdapter.addFragment(new GlobalFragment(), "GLOBAL");

        viewPager.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            fragmentTitles.add(name);
        }
    }
}
