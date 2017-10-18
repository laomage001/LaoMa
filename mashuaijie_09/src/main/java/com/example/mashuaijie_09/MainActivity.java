package com.example.mashuaijie_09;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mashuaijie_09.view.XListView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener{

    private XListView xListView;

    //定义一个大的集合,,,装当前页面所有的数据
    private List<DataDataBean.DataBean> list = new ArrayList<>();
    private MyAdapter myAdapter;

    //定义一个int值记录第几页
    private int page_num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xListView = (XListView) findViewById(R.id.x_list_view);

        //设置支持上拉还是下拉
        xListView.setPullRefreshEnable(true);//支持下拉刷新
        xListView.setPullLoadEnable(true);//支持上拉加载更多
        xListView.setXListViewListener(this);//设置xlistView的监听事件


        getDataFromNet();
    }

    private void getDataFromNet() {
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String path = "http://www.yulin520.com/a2a/impressApi/news/mergeList?pageSize=10&page=1";
                try {
                    URL url = new URL(path);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);

                    //获取
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200){
                        InputStream inputStream = connection.getInputStream();

                        String json = streamToString(inputStream,"utf-8");

                        return json;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                Gson gson = new Gson();

                DataDataBean dataDataBean = gson.fromJson(s, DataDataBean.class);

                //将解析到的集合数据添加到上面的大集合中
                list.addAll(dataDataBean.getData());

                //设置适配器...
                setAdapter();

                //上拉加载完成....停止加载
                xListView.stopLoadMore();
            }
        };

        asyncTask.execute();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (myAdapter == null){

            myAdapter = new MyAdapter(MainActivity.this, list);
            xListView.setAdapter(myAdapter);
        }else {
            myAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 这是下拉刷新的时候自动调用的方法
     *
     * 例如,,,下拉刷新的时候我们让他请求下一页的内容
     */
    @Override
    public void onRefresh() {
        page_num ++;

        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String path = "http://www.yulin520.com/a2a/impressApi/news/mergeList?pageSize=10&page="+page_num;
                try {
                    URL url = new URL(path);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);

                    //获取
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200){
                        InputStream inputStream = connection.getInputStream();

                        String json = streamToString(inputStream,"utf-8");

                        return json;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                Gson gson = new Gson();

                DataDataBean dataDataBean = gson.fromJson(s, DataDataBean.class);

                //下拉刷新的数据需要添加在大集合的最前边
                list.addAll(0,dataDataBean.getData());

                //设置适配器...
                setAdapter();

                //...............设置完数据之后刷新需要停止
                xListView.stopRefresh();//停止刷新

                //System.currentTimeMillis()....当前时间的long类型的值
                Date date = new Date(System.currentTimeMillis());
                //格式化....yyyy-MM-dd HH:mm
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                //设置本次刷新的时间
                xListView.setRefreshTime(simpleDateFormat.format(date));
            }
        };

        asyncTask.execute();
    }

    /**
     * 这是上拉加载的时候调用的方法
     *
     * 上拉加载更多......例如:让他请求第一页数据
     */
    @Override
    public void onLoadMore() {

        //再次调用
        getDataFromNet();
    }


    private String streamToString(InputStream inputStream,String charset) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,charset);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = null;
            StringBuilder builder = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null){
                builder.append(s);
            }

            bufferedReader.close();
            return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  null;
    }
}
