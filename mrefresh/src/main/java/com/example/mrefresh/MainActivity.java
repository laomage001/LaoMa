package com.example.mrefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;


public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        //默认显示
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new FragmentHome()).commit();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_01:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new FragmentHome()).commit();
                        break;
                    case R.id.radio_02:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new FragmentXiangFa()).commit();
                        break;
                    case R.id.radio_03:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new FragmentMarket()).commit();
                        break;
                    case R.id.radio_04:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new FragmentNotify()).commit();
                        break;
                    case R.id.radio_05:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new FragmentMore()).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}

