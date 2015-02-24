package com.blogspot.androidgaidamak.acustomgooglesearchclient;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.io.UnsupportedEncodingException;


public class MainActivity extends Activity {
    public static final String BASE_URL = "https://www.googleapis.com/customsearch/v1";
    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem menuItem = menu.findItem(R.id.search);
        final SearchView search = (SearchView) menuItem.getActionView();
        Log.v(TAG, "SearchView=" + search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.v(TAG, "onQueryTextSubmit(" + "query=" + query + ")");
                mViewPager.setCurrentItem(0);
                try {
                    ((ViewPagerAdapter) ((ViewPager) findViewById(R.id.pager)).getAdapter())
                            .getSearchResultsFragment().performQuery(query);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        Fragment[] fragments =
                new Fragment[] {new SearchResultsFragment(), new FavouritesFragment()};
        String[] titles = new String[] {"Search", "Favourites"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public SearchResultsFragment getSearchResultsFragment() {
            return (SearchResultsFragment) fragments[0];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
