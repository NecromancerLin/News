package com.necromancer.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.necromancer.news.fragment.CaijingFragment;
import com.necromancer.news.fragment.JunshiFragment;
import com.necromancer.news.fragment.KejiFragment;
import com.necromancer.news.fragment.ShehuiFragment;
import com.necromancer.news.fragment.ShishangFragment;
import com.necromancer.news.fragment.TiyuFragment;
import com.necromancer.news.fragment.ToutiaoFragment;
import com.necromancer.news.fragment.YuleFragment;

/**
 * Created by HP-PC on 2018/4/10.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"头条", "社会", "娱乐", "体育", "军事", "科技", "财经", "时尚"};

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ToutiaoFragment();
        } else if (position == 1) {
            return new ShehuiFragment();
        } else if (position == 2) {
            return new YuleFragment();
        } else if (position == 3) {
            return new TiyuFragment();
        } else if (position == 4) {
            return new JunshiFragment();
        } else if (position == 5) {
            return new KejiFragment();
        } else if (position == 6) {
            return new CaijingFragment();
        } else {
            return new ShishangFragment();

        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
