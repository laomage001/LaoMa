package com.example.mashuaijie11;

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


public class MyAdapyer extends BaseAdapter {
    List<Data.ResultBean.DataBean> list;
    Context con;
    int a=0;
    int b=1;
    public MyAdapyer(List<Data.ResultBean.DataBean> list, Context con) {
        this.list = list;
        this.con = con;
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
    public int getItemViewType(int position) {
        if (position%2==0){
            return a;
        }
        return b;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (getItemViewType(i)==a){
            ViewHolder1 vh;
            if (view==null){
                view=View.inflate(con, R.layout.adpter1,null);
                vh=new ViewHolder1();
                vh.imageView= (ImageView) view.findViewById(R.id.adapter_img);
                vh.textView= (TextView) view.findViewById(R.id.adapter_name);
                view.setTag(vh);
            }else {
                vh= (ViewHolder1) view.getTag();
            }
            vh.textView.setText(list.get(i).getTitle());
            ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),vh.imageView, ImageLodelUtil.getto());
        }
        if (getItemViewType(i)==b){
            ViewHolder2 vh;
            if (view==null){
                view=View.inflate(con, R.layout.adpter2,null);
                vh=new ViewHolder2();
                vh.imageView= (ImageView) view.findViewById(R.id.adapter_img);
                vh.textView= (TextView) view.findViewById(R.id.adapter_name);
                view.setTag(vh);
            }else {
                vh= (ViewHolder2) view.getTag();
            }
            vh.textView.setText(list.get(i).getTitle());
            ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),vh.imageView, ImageLodelUtil.getto());
        }
        return view;
    }
    class ViewHolder1{
        ImageView imageView;
        TextView textView;
    }
    class ViewHolder2{
        ImageView imageView;
        TextView textView;
    }
}
