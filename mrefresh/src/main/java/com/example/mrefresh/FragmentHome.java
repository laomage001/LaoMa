package com.example.mrefresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dash
 * @date 2017/10/16
 * @description:
 */
public class FragmentHome extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout,container,false);

        tabLayout = view.findViewById(R.id.tab);
        viewPager = view.findViewById(R.id.view_pager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = new ArrayList<>();
        list.add("动态");
        list.add("热门");
        list.add("发现");


        //设置适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment =null;

                switch (position){
                    case 0:
                        fragment = new FragmentDongTai();
                        break;
                    case 1:
                        fragment = new FragmentReMen();
                        break;
                    case 2:
                        fragment = new FragmentFaXian();
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

        //关联
        tabLayout.setupWithViewPager(viewPager);
    }
}