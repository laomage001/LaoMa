package com.example.mashuaijie11;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dralayout;
    private ListView lv;
    private RelativeLayout rellayout;
    private FrameLayout fragment_list;
    private List<String> list;
    private Fragment fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //侧滑根目录的id
        dralayout = (DrawerLayout) findViewById(R.id.dralayout);
        //ListView的id
        lv = (ListView) findViewById(R.id.lv);
        //抽屉布局id
        rellayout = (RelativeLayout) findViewById(R.id.rellayout);
        //frgment的id
        fragment_list = (FrameLayout) findViewById(R.id.fragment_list);
        list = new ArrayList<>();
        list.add("头条");
        list.add("社会");
        list.add("国内");
        list.add("军事");
        list.add("财经");
        //Bundle传值；；；；；；；；；；；；Bundle相当于打包
        Bundle bundle=new Bundle();
        // 键值对的形式传
        bundle.putString("name",list.get(0));
        //实例化Fragment类
        fragment1 = new Fragment();
        //设置bundle传过去
        fragment1.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list,fragment1).commit();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //实例化Fragment类
                fragment1 = new Fragment();
                //隐藏侧滑菜单
                dralayout.closeDrawer(rellayout);

                //Bundle传值；；；；；；；；；；；；Bundle相当于打包
                Bundle bundle=new Bundle();
                // 键值对的形式传
                bundle.putString("name",list.get(i));
                //设置bundle传过去
                fragment1.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list,fragment1).commit();
            }
        });
    }
}
