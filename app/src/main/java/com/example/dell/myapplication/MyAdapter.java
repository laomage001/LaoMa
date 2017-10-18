package com.example.dell.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by dell on 2017/10/10.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    List<DataDataBean.ResultBean.ListBean> list;

    private int TYTLE_ONLY = 0;//只有文字的形式
    private int IMAGE_LEFT = 1;//表示图片在左边,文字在右边
    private int IMAGE_RIGHT = 2;//* 2表示图片在右边,文字在左边
    private int IMAGE_BOTTOM = 3;// * 3表示文字在上边,图片在下面

    public MyAdapter(Context context, List<DataDataBean.ResultBean.ListBean> list) {
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

    /**
     * 1.适配器中重写这个getViewTypeCount方法...得到视图类型的数量,,,也就是listView要展示多少种不同的视图
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 4;
    }

    /**
     * 2.重写适配器中getItemViewType方法...返回当前条目视图的类型,,,返回值是int值
     *
     * int返回值是有限制的,,,,例如getViewTypeCount返回的是4,,,那么他的取值只能是0,1,2,3
     *
     * 0表示第一种,,只有一个表题的条目样式
     * 1表示图片在左边,文字在右边
     * 2表示图片在右边,文字在左边
     * 3表示文字在上边,图片在下面
     *
     * 实际开发中显示哪种条目的类型是根据接口数据中具体的那个字段值来决定的
     *
     * 现在,,例如...position%4
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        if (position%4 == 0){
            return TYTLE_ONLY;
        }else if (position %4==1){
            return IMAGE_LEFT;
        }else if (position %4==2){
            return IMAGE_RIGHT;
        }

        return IMAGE_BOTTOM;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //在做优化之前先判断一下当前条目展示的是哪一种类型,,,然后再进行关联布局进行优化
        if (getItemViewType(i) == TYTLE_ONLY){

            TitleHolder holder;
            if (view == null){
                view = View.inflate(context, R.layout.item_title_layout,null);
                holder = new TitleHolder();

                holder.textView = view.findViewById(R.id.text_title);

                view.setTag(holder);
            }else {
                holder = (TitleHolder) view.getTag();
            }

            //赋值
            holder.textView.setText(list.get(i).getTitle());

        }else if (getItemViewType(i) == IMAGE_LEFT){
            ImageLeftHolder holder;
            if (view == null){
                view = View.inflate(context, R.layout.item_layout,null);
                holder = new ImageLeftHolder();

                holder.textView = view.findViewById(R.id.text_title);
                holder.imageView = view.findViewById(R.id.image_view);

                view.setTag(holder);
            }else {
                holder = (ImageLeftHolder) view.getTag();
            }

            //赋值
            holder.textView.setText(list.get(i).getTitle());

            //显示图片
            ImageLoader.getInstance().displayImage(list.get(i).getFirstImg(),holder.imageView, ImageLoaderUtil.getDefaultOption());

        }else if (getItemViewType(i) == IMAGE_RIGHT){

            ImageRightHolder holder;
            if (view == null){
                view = View.inflate(context, R.layout.item_right_layout,null);
                holder = new ImageRightHolder();

                holder.textView = view.findViewById(R.id.text_title);
                holder.imageView = view.findViewById(R.id.image_view);

                view.setTag(holder);
            }else {
                holder = (ImageRightHolder) view.getTag();
            }

            //赋值
            holder.textView.setText(list.get(i).getTitle());

            //显示图片
            ImageLoader.getInstance().displayImage(list.get(i).getFirstImg(),holder.imageView, ImageLoaderUtil.getDefaultOption());


        }else if (getItemViewType(i) == IMAGE_BOTTOM){
            ImageBottomHolder holder;
            if (view == null){
                view = View.inflate(context, R.layout.item_bottom_layout,null);
                holder = new ImageBottomHolder();

                holder.textView = view.findViewById(R.id.text_title);
                holder.imageView = view.findViewById(R.id.image_view);

                view.setTag(holder);
            }else {
                holder = (ImageBottomHolder) view.getTag();
            }

            //赋值
            holder.textView.setText(list.get(i).getTitle());

            //显示图片
            ImageLoader.getInstance().displayImage(list.get(i).getFirstImg(),holder.imageView, ImageLoaderUtil.getDefaultOption());


        }

        return view;
    }

    private class TitleHolder{
        TextView textView;
    }
    private class ImageLeftHolder{
        TextView textView;
        ImageView imageView;
    }
    private class ImageRightHolder{
        TextView textView;
        ImageView imageView;
    }
    private class ImageBottomHolder{
        TextView textView;
        ImageView imageView;
    }


}
