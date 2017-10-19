package com.example.thirdparty;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tl_tab);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        list = new ArrayList<>();
        list.add("ListView");
        list.add("GridView");
        list.add("ScrollView");

        viewPager.setOffscreenPageLimit(list.size());

        //1.设置适配器...fragmnePagerAdapter....getChildFragmentManager...getFragmentManager
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            //2.重写方法...返回的是viewPager当前页的标题
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }

            @Override
            public Fragment getItem(int position) {//0...listView,1...gridView,2..scrollView
                Fragment fragment = null;
                switch (position){
                    case 0:
                        fragment = new FragmentListView();
                        break;
                    case 1:
                        fragment = new FragmentGridView();
                        break;
                    case 2:
                        fragment = new FragmentScrollView();
                        break;
                    default:
                        break;
                }


                return fragment;
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        //3.tablayout与viewPager关联使用
        tabLayout.setupWithViewPager(viewPager);
    }
}
