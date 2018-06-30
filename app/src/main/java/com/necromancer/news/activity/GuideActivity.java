package com.necromancer.news.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.necromancer.news.R;
import com.necromancer.news.utils.PreUtils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private Button btnSart;//开始体验
    private int[] imageIds = new int[]{R.drawable.guide_0, R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private ArrayList<ImageView> mImageViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        btnSart = (Button) findViewById(R.id.btn_start);

        btnSart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //表示新手引导已经展示过了
                PreUtils.putBoolean(GuideActivity.this, "isFirst", false);
                //跳到主界面
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //初始化展示的数据
        initData();
    }

    private void initData() {
        //初始化4张引导图片数据
        mImageViews = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(imageIds[i]);
            mImageViews.add(image);
        }
        GuideAdapter adapter = new GuideAdapter();
        // mViewpager设置数据
        mViewPager.setAdapter(adapter);
        //设置滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //当显示最后一个条目的时候，跳转按钮可见
                if (position==imageIds.length-1){
                    btnSart.setVisibility(View.VISIBLE);
                }
                else{
                    btnSart.setVisibility(View.GONE);
                }
            }
        });

    }

    class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化界面数据，类似getView
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mImageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
