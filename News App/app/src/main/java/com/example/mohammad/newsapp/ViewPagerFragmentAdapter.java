package com.example.mohammad.newsapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mohammad on 06/02/18.
 */

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;
    public ViewPagerFragmentAdapter(FragmentManager fm , Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new SportsFragment();
            case 1:
                return new MediaFragment();
            case 2:
                return new PoliticsFragment();
            default:
                return null;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return mContext.getString(R.string.sports_fragment_title);
            case 1:
                return mContext.getString(R.string.media_fragment_title);
            case 2:
                return mContext.getString(R.string.politics_fragment_title);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
