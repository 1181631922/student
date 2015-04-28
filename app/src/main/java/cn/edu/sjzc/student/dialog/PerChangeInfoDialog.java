package cn.edu.sjzc.student.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.edu.sjzc.student.R;

/**
 * <p>
 * Title: PerChangeInfoDialog
 * </p>
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * 
 * @author archie
 * @version 1.0
 */
public class PerChangeInfoDialog extends Dialog implements
		View.OnClickListener {
	int layoutRes;//
	Context context;

	private Button confirmBtn;

	private Button cancelBtn;

	public PerChangeInfoDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 
	 * 
	 * @param context
	 * @param resLayout
	 */
	public PerChangeInfoDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	/**
	 * 
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public PerChangeInfoDialog(Context context, int theme, int resLayout) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(layoutRes);

		confirmBtn = (Button) findViewById(R.id.confirm_btn);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);

		confirmBtn.setTextColor(0xff1E90FF);
		cancelBtn.setTextColor(0xff1E90FF);

		confirmBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm_btn:
            /**
             * 提交到服务器的方法
             * 必须要加上下一句
             * PerChangeInfoDialog.this.dismiss();
             *
             */
			break;
		case R.id.cancel_btn:
			PerChangeInfoDialog.this.dismiss();
			break;
		}
	}

}