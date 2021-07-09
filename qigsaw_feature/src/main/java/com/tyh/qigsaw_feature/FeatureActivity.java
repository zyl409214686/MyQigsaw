package com.tyh.qigsaw_feature;

import android.os.Bundle;
import android.widget.TextView;

import com.tyh.myqigsaw.BaseActivity;

import androidx.annotation.Nullable;

/**
 * <p>@author : tangyanghai</p>
 * <p>@time : 2021/7/7</p>
 * <p>@for : </p>
 * <p></p>
 */
public class FeatureActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView v = new TextView(this);
        v.setText("wo shi feature");
        setContentView(v);
    }
}
