package com.necromancer.news.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.necromancer.news.R;
import com.necromancer.news.domain.NewsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by HP-PC on 2018/4/16.
 */

public class NewsListAdapter extends BaseAdapter {
    private List<NewsBean.ResultBean.DataBean> data;
    private Context mContext;

    public NewsListAdapter(List<NewsBean.ResultBean.DataBean> data, Context context) {
        this.data = data;
        this.mContext = context;
    }

    public void setData(List<NewsBean.ResultBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.item_news, null);
            holder = new ViewHolder();
            holder.ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.tvFrom = (TextView) view.findViewById(R.id.tv_from);
            holder.tvDate = (TextView) view.findViewById(R.id.tv_date);
            view.setTag(holder);
        }else {
            //复用convertView
            view=convertView;
            holder= (ViewHolder) view.getTag();
        }
        //数据填充
        NewsBean.ResultBean.DataBean dataBean=data.get(position);
        holder.tvTitle.setText(dataBean.getTitle());//标题
        holder.tvFrom.setText(dataBean.getAuthor_name());//发布源
        holder.tvDate.setText(dataBean.getDate());//时间
        if (!TextUtils.isEmpty(dataBean.getThumbnail_pic_s())){
            Picasso.with(mContext)
                    .load(dataBean.getThumbnail_pic_s())
                    .placeholder(R.drawable.zhanwei)
                    .error(R.drawable.zhanwei)
                    .into(holder.ivIcon);//图片
        }
        return view;
    }

    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle, tvFrom, tvDate;
    }
}
