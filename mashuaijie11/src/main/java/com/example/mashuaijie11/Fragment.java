package com.example.mashuaijie11;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.mashuaijie11.view.XListView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/10/12.
 */


public class Fragment extends android.support.v4.app.Fragment {

    private XListView xlv;
    private String name;
    int i=1;
    List<Data.ResultBean.DataBean> data1=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.child, container, false);
        xlv = (XListView) vv.findViewById(R.id.xlv);
        return vv;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xlv.setPullLoadEnable(true);
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),i+"",Toast.LENGTH_SHORT).show();
            }
        });
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

                AsyncTask<Void,Void,String> asyncTask=new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {

                        try {
                            String path="http://v.juhe.cn/toutiao/index?type="+name+"&key=c4479ad58f41e7f78a8fa073d0b1f1b5";
                            URL url;
                            url = new URL(path);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setReadTimeout(5000);
                            urlConnection.setConnectTimeout(5000);
                            int responseCode = urlConnection.getResponseCode();
                            if (responseCode==200){
                                InputStream inputStream = urlConnection.getInputStream();
                                String s = convertStreamToString(inputStream);
                                return s;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        Log.d("------",s);
                        Gson gson = new Gson();
                        Data data = gson.fromJson(s, Data.class);
                        data1.addAll(0,data.getResult().getData());
                        MyAdapyer myAdapyer = new MyAdapyer(data1, getActivity());
                        xlv.setAdapter(myAdapyer);
                        xlv.stopRefresh();
                    }
                };
                asyncTask.execute();
            }

            @Override
            public void onLoadMore() {
                getinto(name);

            }
        });
        //获取数据
        name = getArguments().getString("name", "");
        //判断
        if (name.equals("头条")){
            name = "top";
        }else if (name.equals("军事")){
            name = "junshi";
        }else if (name.equals("社会")){
            name = "shehui";
        }else if (name.equals("财经")){
            name = "caijing";
        }else if (name.equals("国内")){
            name = "guonei";
        }
        getinto(name);
    }

    private void getinto(final String name) {
        i++;
        AsyncTask<Void,Void,String> asyncTask=new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    String path="http://v.juhe.cn/toutiao/index?type="+name+"&key=c4479ad58f41e7f78a8fa073d0b1f1b5";
                    URL url;
                    url = new URL(path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(5000);
                    urlConnection.setConnectTimeout(5000);
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode==200){
                        InputStream inputStream = urlConnection.getInputStream();
                        String s = convertStreamToString(inputStream);
                        return s;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
//                Log.d("------",s);
                Gson gson = new Gson();
                Data data = gson.fromJson(s, Data.class);
                data1.addAll(data.getResult().getData());
                MyAdapyer myAdapyer = new MyAdapyer(data1, getActivity());
                xlv.setAdapter(myAdapyer);
                xlv.stopLoadMore();
            }
        };
        asyncTask.execute();

    }
    /**
     * 将输入流转成字符串
     *
     * @param is
     *            输入流
     * @return 返回字符串
     */
    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();







































































































































































































































































































































        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
