package com.example.thirdparty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Dash
 * @date 2017/10/18
 * @description:
 */
public class FragmentGridView extends Fragment {

    private PullToRefreshGridView refreshGridView;
    private int page_num = 1;
    private List<DataDataBean.ResultsBean> list = new ArrayList<>();//记录当前展示的所有数据
    private ILoadingLayout startLabels;
    private GridViewAdapter gridViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gridview_layout,container,false);

        refreshGridView = view.findViewById(R.id.refresh_grid_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //1.默认的获取第一页的数据,然后设置适配器
        getDataFromNet();

        //2.设置可以刷新的模式
        refreshGridView.setMode(PullToRefreshBase.Mode.BOTH);

        //3.设置刷新的时候展示的文字(状态)
        startLabels = refreshGridView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在刷新...");
        startLabels.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = refreshGridView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");

        //4.设置下拉和上拉刷新的监听事件
        refreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                //下拉刷新....请求第一页的数据,清空之前的数据,然后再添加设置适配器
                getRefreshData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
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
                refreshGridView.onRefreshComplete();

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
                refreshGridView.onRefreshComplete();
            }
        });

    }

    /**
     * 设置适配器的方法
     */
    private void setAdapter() {
        if (gridViewAdapter == null){

            gridViewAdapter = new GridViewAdapter(getActivity(),list);
            refreshGridView.setAdapter(gridViewAdapter);

        }else {
            gridViewAdapter.notifyDataSetChanged();
        }
    }
}
