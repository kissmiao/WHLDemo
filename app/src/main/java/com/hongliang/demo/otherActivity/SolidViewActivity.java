package com.hongliang.demo.otherActivity;

import android.app.Activity;
import android.os.Bundle;

import com.hongliang.demo.R;
import com.hongliang.demo.view.SolidView;


/**
 * Created by Administrator on 2016/7/11.
 */
public class SolidViewActivity extends Activity {
    private SolidView filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_solid);
        filter= (SolidView) findViewById(R.id.filter);
        filter.StartAnim();
    }
}
