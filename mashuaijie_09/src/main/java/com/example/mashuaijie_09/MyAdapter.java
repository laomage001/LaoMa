package com.example.mashuaijie_09;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by dell on 2017/10/12.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    List<DataDataBean.DataBean> list;

    public MyAdapter(Context context, List<DataDataBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 获取当前条目展示的内容
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    /**
     * 获取当前条目的id,,,,只要唯一就可以
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            view = View.inflate(context,R.layout.item_layout,null);
            holder = new ViewHolder();

            holder.imageView = view.findViewById(R.id.image_view);
            holder.textView = view.findViewById(R.id.text_title);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textView.setText(list.get(i).getTitle());
        ImageLoader.getInstance().displayImage(list.get(i).getImg(),holder.imageView);


        return view;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}