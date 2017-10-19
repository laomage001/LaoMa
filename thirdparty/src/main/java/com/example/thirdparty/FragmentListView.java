package com.example.thirdparty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Dash
 * @date 2017/10/18
 * @description:
 */
public class FragmentListView extends Fragment {

    private PullToRefreshListView refreshListView;
    private List<DataDataBean.ResultsBean> list = new ArrayList<>();//记录当前展示的所有数据
    private ListViewAdapter listViewAdapter;
    private int page_num = 1;
    private ILoadingLayout startLabels;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview_layout,container,false);


        refreshListView = view.findViewById(R.id.refresh_list_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //1.设置listView的适配器
        getDataFromNet();


        //2.设置刷新模式
        /*设置pullToRefreshListView的刷新模式，BOTH代表支持上拉和下拉，PULL_FROM_END代表上拉,PULL_FROM_START代表下拉 */
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        //3.通过getLoadingLayoutProxy 方法来指定上拉和下拉时显示的状态的区别(也就是设置向下拉的时候头部里面显示的文字)
        //此时这里设置的是下拉刷新的时候显示的文字,所以第一个设置true表示现在是刷新,第二个设置为false
        startLabels = refreshListView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在刷新...");
        startLabels.setReleaseLabel("放开刷新");

        ILoadingLayout endLabels = refreshListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");


        //4.设置监听事件
        /**
         * 如果Mode设置成Mode.BOTH，需要设置刷新Listener为OnRefreshListener2，并实现onPullDownToRefresh()、
         * onPullUpToRefresh()两个方法。
         如果Mode设置成Mode.PULL_FROM_START或Mode.PULL_FROM_END，需要设置刷新Listener为OnRefreshListener，
         同时实现onRefresh()方法。
         当然也可以设置为OnRefreshListener2，但是Mode.PULL_FROM_START的时候只调用onPullDownToRefresh()方法，
         Mode.PULL_FROM_END的时候只调用onPullUpToRefresh()方法
         */
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新的时候调用的方法....请求第一页的数据,清空之前的数据,然后再添加设置适配器
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新....请求第一页的数据,清空之前的数据,然后再添加设置适配器
                getRefreshData();


            }

            //上拉刷新的时候调用的方法..page++,然后在请求数据
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                //上拉刷新/加载...加载的时候页数++
                page_num++;
                getDataFromNet();


            }
        });

    }

    /**
     * 下拉刷新获取数据
     */
    private void getRefreshData() {
        NetDataUtil.getData("http://gank.io/api/data/Android/10/1", getActivity(), new JsonCallBack() {
            @Override
            public void getJsonString(String json) {
                //解析
                Gson gson = new Gson();

                DataDataBean dataDataBean = gson.fromJson(json, DataDataBean.class);
                //先清空一下数据
                list.clear();

                //添加到集合的最前边,,,,(0,,,,)
                list.addAll(0,dataDataBean.getResults());

                //设置适配器
                setAdapter();

                //设置适配器之后停止刷新的操作
                refreshListView.onRefreshComplete();

                //可以设置刷新的时间....
                startLabels.setLastUpdatedLabel("上次更新时间:"+new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));//last最近的,最后一次update修改/更新
            }
        });

    }


    /**
     * 刚开始进入页面获取网络数据....还可以作为上拉加载获取数据的操作
     *
     *
     */
    private void getDataFromNet() {
        //第一个参数是接口,第二个上下文,第三个回调json数据
        NetDataUtil.getData("http://gank.io/api/data/Android/10/"+page_num, getActivity(), new JsonCallBack() {
            @Override
            public void getJsonString(String json) {

                //解析
                Gson gson = new Gson();

                DataDataBean dataDataBean = gson.fromJson(json, DataDataBean.class);

                //往后面添加...
                list.addAll(dataDataBean.getResults());

                //设置适配器
                setAdapter();

                //停止刷新
                refreshListView.onRefreshComplete();
            }
        });

    }

    private void setAdapter() {
        if (listViewAdapter == null){
            listViewAdapter = new ListViewAdapter(getActivity(),list);
            refreshListView.setAdapter(listViewAdapter);
        }else {
            listViewAdapter.notifyDataSetChanged();
        }

    }
}
