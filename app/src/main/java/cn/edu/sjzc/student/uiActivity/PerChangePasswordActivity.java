package cn.edu.sjzc.student.uiActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import cn.edu.sjzc.student.R;

public class PerChangePasswordActivity extends BaseActivity implements
        OnClickListener {

	private ImageButton changepassword_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// �������ڷ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		super.setContentView(R.layout.activity_per_changepassword);
		
		init();
	}

	private void init() {
		ImageButton changepassword_back = (ImageButton) this
				.findViewById(R.id.changepassword_back);
		changepassword_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.changepassword_back:

			PerChangePasswordActivity.this.finish();

			break;

		default:
			break;
		}

	}

}