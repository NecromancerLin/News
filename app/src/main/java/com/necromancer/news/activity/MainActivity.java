package com.necromancer.news.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.necromancer.news.R;
import com.necromancer.news.adapter.MainPagerAdapter;
import com.necromancer.news.utils.AppManager;
import com.necromancer.news.utils.DoubleClickExitHelper;
import com.shizhefei.view.indicator.Indicator;

/**
 * @author HP-PC
 */
public class MainActivity extends AppCompatActivity {

    private DoubleClickExitHelper mDoubleClickExit = new DoubleClickExitHelper(this);

    ViewPager mViewPager;
    MainPagerAdapter pagerAdapter;
    TabLayout mTabLayout;
    Indicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        AppManager.getAppManager().addActivity(this);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mIndicator = (Indicator) findViewById(R.id.tabmain_indicator);

        //适配器
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        //TabLayout绑定Viewpager
        mTabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }


}
