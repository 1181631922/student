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
import cn.edu.sjzc.student.uiActivity.AdviceTeacherActivity;
import cn.edu.sjzc.student.uiActivity.LoginDemoActivity;
import cn.edu.sjzc.student.uiFragment.MainTabActivity;

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
public class ConfirmDialog extends Dialog implements View.OnClickListener {
    private int layoutRes;
    private Context context;
    private Button confirmBtn;
    private TextView dialog_title, dialog_detail;

    public ConfirmDialog(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * @param context
     * @param resLayout
     */
    public ConfirmDialog(Context context, int resLayout) {
        super(context);
        this.context = context;
        this.layoutRes = resLayout;
    }

    /**
     * @param context
     * @param theme
     * @param resLayout
     */
    public ConfirmDialog(Context context, int theme, int resLayout) {
        super(context, theme);
        this.context = context;
        this.layoutRes = resLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layoutRes);

        dialog_title = (TextView) findViewById(R.id.dialog_title);
        dialog_title.setText("提交");
        dialog_detail = (TextView) findViewById(R.id.dialog_detail);
        dialog_detail.setText("恭喜您，提交成功！");

        confirmBtn = (Button) findViewById(R.id.dialog_confirm_btn);
        confirmBtn.setText("确定");

        confirmBtn.setTextColor(0xff1E90FF);

        confirmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_confirm_btn:
                Intent it_my_activity = new Intent(Intent.ACTION_CALL);
                it_my_activity.setClass(context, AdviceTeacherActivity.class);
                ((Activity) context).finish();
                ConfirmDialog.this.dismiss();
                break;
        }
    }

}