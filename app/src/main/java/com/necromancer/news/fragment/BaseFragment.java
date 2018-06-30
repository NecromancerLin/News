package com.necromancer.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.necromancer.news.R;
import com.necromancer.news.activity.AboutActivity;
import com.necromancer.news.activity.WebViewActivity;
import com.necromancer.news.adapter.NewsListAdapter;
import com.necromancer.news.domain.NewsBean;
import com.necromancer.news.engine.JNI;
import com.necromancer.news.engine.NetWork;
import com.necromancer.news.widget.FloatingActionButton;
import com.necromancer.news.widget.FloatingActionMenu;
import com.necromancer.news.widget.RefreshLayout;
import com.necromancer.news.widget.SubActionButton;
import com.necromancer.news.widget.animation.SlideInAnimationHandler;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by HP-PC on 2018/4/19.
 */

public abstract class BaseFragment extends Fragment {
    RefreshLayout mRefreshLayout;
    ListView mListView;
    ImageView ivError;//加载失败时图片
    ProgressBar mProgressBar;//第一次加载时显示的圆形进度条
    NewsListAdapter mNewsListAdapter;//ListView适配器
    List<NewsBean.ResultBean.DataBean> datas;//加载的数据



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toutiao, container, false);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        mListView = (ListView) view.findViewById(R.id.list_view);
        ivError = (ImageView) view.findViewById(R.id.iv_error);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_Bar);
        initView();
        initData();

        //功能按钮（+）
        ImageView fabContent = new ImageView(getActivity());
        fabContent.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_settings));

        FloatingActionButton darkButton = new FloatingActionButton.Builder(getActivity())
                .setTheme(FloatingActionButton.THEME_DARK)
                .setContentView(fabContent)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(getActivity())
                .setTheme(SubActionButton.THEME_DARK);

        ImageView rlIcon1 = new ImageView(getActivity());
        ImageView rlIcon2 = new ImageView(getActivity());
        ImageView rlIcon3 = new ImageView(getActivity());
        ImageView rlIcon4 = new ImageView(getActivity());
        ImageView rlIcon5 = new ImageView(getActivity());

        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_github));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_place));
        SubActionButton button1 = rLSubBuilder.setContentView(rlIcon1).build();
        SubActionButton button2 = rLSubBuilder.setContentView(rlIcon2).build();
        SubActionButton button3 = rLSubBuilder.setContentView(rlIcon3).build();
        SubActionButton button4 = rLSubBuilder.setContentView(rlIcon4).build();
        SubActionButton button5 = rLSubBuilder.setContentView(rlIcon5).build();

        // Set 4 SubActionButtons
        FloatingActionMenu centerBottomMenu = new FloatingActionMenu.Builder(getActivity())
                .setStartAngle(0)
                .setEndAngle(-180)
                .setAnimationHandler(new SlideInAnimationHandler())

                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)

                .attachTo(darkButton)
                .build();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = WebViewActivity.newIntent(getContext(), "https://github.com/NecromancerLin/News");
                getActivity().startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AboutActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }


    private void initData() {
        mProgressBar.setVisibility(View.VISIBLE);
        ivError.setVisibility(View.GONE);//隐藏错误的信息
        refresh();
    }

    private void initView() {
        //给ListView设置适配器，从而显示到界面上
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟网络交互
                refresh();
            }
        });
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                load();
            }
        });
        //没有更多数据
        mRefreshLayout.setHasMore(false);
        //加载失败，点击重连
        ivError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        //ListView条目的点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean.ResultBean.DataBean dataBean = datas.get(position);
                Intent intent = WebViewActivity.newIntent(getContext(), dataBean.getUrl());
                getActivity().startActivity(intent);
            }
        });
    }

    private void load() {
        NetWork.createApi().getNews(JNI.getAppKey(), getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsBean>() {
                    @Override
                    public void call(NewsBean newsBean) {
                        mRefreshLayout.setEnabled(true);//允许下拉刷新
                        mProgressBar.setVisibility(View.GONE);//隐藏进度条
                        mRefreshLayout.setRefreshing(false);//停止下拉刷新
                        datas = newsBean.getResult().getData();
                        mNewsListAdapter = new NewsListAdapter(datas, getContext());
                        mListView.setAdapter(mNewsListAdapter);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ivError.setVisibility(View.VISIBLE);
                        mRefreshLayout.setEnabled(false);
                        mProgressBar.setVisibility(View.GONE);
                        mRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "请检查网络连接，然后重试", Toast.LENGTH_LONG).show();
                        throwable.printStackTrace();
                    }
                });
    }


    private void refresh() {
        //top对应的是头条新闻
        NetWork.createApi().getNews(JNI.getAppKey(), getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsBean>() {
                    @Override
                    public void call(NewsBean newsBean) {
                        mRefreshLayout.setEnabled(true);//允许下拉刷新
                        mProgressBar.setVisibility(View.GONE);//隐藏进度条
                        mRefreshLayout.setRefreshing(false);//停止下拉刷新
                        datas = newsBean.getResult().getData();
                        mNewsListAdapter = new NewsListAdapter(datas, getContext());
                        mListView.setAdapter(mNewsListAdapter);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ivError.setVisibility(View.VISIBLE);
                        mRefreshLayout.setEnabled(false);
                        mProgressBar.setVisibility(View.GONE);
                        mRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "请检查网络连接，然后重试", Toast.LENGTH_LONG).show();
                        throwable.printStackTrace();
                    }
                });

    }

    /**
     * 返回新闻类型,改为抽象方法
     */
    protected abstract String getType();

}
