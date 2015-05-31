package cn.edu.sjzc.student.uiActivity;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.app.UserApplication;
import cn.jpush.android.api.JPushInterface;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;

public class TestActivity extends BaseActivity {
    private TextView jpush_title, jpush_content;
    private String title, content, number;
    private ImageButton jpush_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private void initView() {
        TextView tv = new TextView(this);
        tv.setText("用户自定义打开的Activity");
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            content = bundle.getString(JPushInterface.EXTRA_ALERT);
        }
        jpush_back = (ImageButton) findViewById(R.id.jpush_back);
        jpush_back.setOnClickListener(this);
        jpush_title = (TextView) findViewById(R.id.jpush_title);
        jpush_content = (TextView) findViewById(R.id.jpush_content);

    }

    private void initData() {
        SharedPreferences userdata = this.getSharedPreferences(UserApplication.USER_DATA, 0);
        number = userdata.getString(UserApplication.USER_DATA_NUMBER, "");
        jpush_title.setText(title);
        jpush_content.setText(content);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.jpush_back:
                finish();
                break;
        }
    }
}
