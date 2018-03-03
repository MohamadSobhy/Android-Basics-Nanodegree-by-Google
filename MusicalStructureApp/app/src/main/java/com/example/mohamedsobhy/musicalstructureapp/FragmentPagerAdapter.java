package com.example.mohamedsobhy.musicalstructureapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Mohamed Sobhy on 23/11/2017.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private Context mContext;
    public FragmentPagerAdapter(FragmentManager fm , Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new PlaylistsFragment();
            case 1:
                return new SongsFragment();
            case 2:
                return new AlbumsFragment();
            case 3:
                return new ArtistsFragment();
            default:
                return null;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.playlists);
            case 1:
                return mContext.getString(R.string.songs);
            case 2:
                return mContext.getString(R.string.albums);
            case 3:
                return mContext.getString(R.string.artists);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
