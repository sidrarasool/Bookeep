package com.example.bookeep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class DeveloperActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        viewPager = findViewById(R.id.vp_dev);

        pagerAdapter = new ScreeSlidePagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
    }
    private class ScreeSlidePagerAdapter extends FragmentStatePagerAdapter{

        public ScreeSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return AddDevFragment.newInstance();
                case 1:
                    return ViewDevelopersFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "Add Dev";
                case 1:
                    return "View Devs";
                default:
                    return null;
            }
        }
    }
}

