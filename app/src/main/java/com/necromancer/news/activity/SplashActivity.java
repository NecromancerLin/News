package com.necromancer.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.necromancer.news.R;
import com.necromancer.news.utils.PreUtils;

public class SplashActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv = (ImageView) findViewById(R.id.activity_splash);
        //初始化动画
        initAnim();
    }

    private void initAnim() {
        //  透明度动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        //设置动画时长
        alpha.setDuration(3000);
        //动画运行完成，保留结束时的状态
        alpha.setFillAfter(true);
        //监听动画
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画运行完成，进入下一个页面
                jumpToNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv.startAnimation(alpha);
    }

    private void jumpToNextPage() {
        //判断是否第一次进入，默认是第一次
        boolean isFirst = PreUtils.getBoolean(this, "isFirst", true);
        Intent intent = new Intent();
        if (isFirst) {
            //进入引导界面
            intent.setClass(this, GuideActivity.class);
        } else {
            //进入功能界面
            intent.setClass(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }


}
