package com.tyh.second_feature;

import android.os.Bundle;
import android.widget.TextView;

import com.tyh.myqigsaw.BaseActivity;

import androidx.annotation.Nullable;

/**
 * <p>@author : tangyanghai</p>
 * <p>@time : 2021/7/8</p>
 * <p>@for : </p>
 * <p></p>
 */
public class SecondFeatureActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView v = new TextView(this);
        v.setText("wo shi second feature");
        setContentView(v);
    }
}
