package com.example.thirdparty;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;



/**
 * @author Dash
 * @date 2017/10/18
 * @description:
 */
public class GridViewAdapter extends BaseAdapter {
    Context context;
    List<DataDataBean.ResultsBean> list;

    public GridViewAdapter(Context context, List<DataDataBean.ResultsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            view = View.inflate(context,R.layout.item_gridviewlayout,null);
            holder = new ViewHolder();

            holder.textView = view.findViewById(R.id.grid_text);
            holder.imageView = view.findViewById(R.id.grid_image);

            view.setTag(holder);

        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textView.setText(list.get(i).getDesc());
        //展示图片的时候,,,getImages这个集合有的有,有的为空...所以要进行.判断
        if (list.get(i).getImages() != null){
            //此时加载图片显示
            ImageLoader.getInstance().displayImage(list.get(i).getImages().get(0),holder.imageView, ImageLoaderUtil.getDefaultOption());
        }

        return view;
    }

    private class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}