package cn.edu.sjzc.student.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.uiActivity.LoginDemoActivity;

/**
 * <p>
 * </p>
 * <p>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 *
 * @version 1.0
 */
public class NetCheckDialog extends Dialog implements View.OnClickListener {
    private int layoutRes;
    private Context context;
    private Button confirmBtn;
    private Button cancelBtn;
    private TextView dialog_title, dialog_detail;

    public NetCheckDialog(Context context) {
        super(context);
        this.context = context;
    }
    /**
     * @param context
     * @param resLayout
     */
    public NetCheckDialog(Context context, int resLayout) {
        super(context);
        this.context = context;
        this.layoutRes = resLayout;
    }
    /**
     * @param context
     * @param theme
     * @param resLayout
     */
    public NetCheckDialog(Context context, int theme, int resLayout) {
        super(context, theme);
        this.context = context;
        this.layoutRes = resLayout;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layoutRes);

        dialog_title = (TextView) findViewById(R.id.dialog_title);
        dialog_title.setText("设置网络");
        dialog_detail = (TextView) findViewById(R.id.dialog_detail);
        dialog_detail.setText("网络未连接，是否这是网络？");

        confirmBtn = (Button) findViewById(R.id.confirm_btn);
        confirmBtn.setText("确定");
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        cancelBtn.setText("取消");

        confirmBtn.setTextColor(0xff1E90FF);
        cancelBtn.setTextColor(0xff1E90FF);

        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_btn:
                Intent it_my_activity = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                context.startActivity(it_my_activity);
                NetCheckDialog.this.dismiss();
                break;
            case R.id.cancel_btn:
                Intent it_activity = new Intent(Intent.ACTION_CALL);
                it_activity.setClass(context, LoginDemoActivity.class);
                ((Activity) context).finish();
                NetCheckDialog.this.dismiss();
                break;
        }
    }

}