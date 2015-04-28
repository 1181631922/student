package cn.edu.sjzc.student.uiActivity;

import android.os.Bundle;
import android.view.Window;

import cn.edu.sjzc.student.R;

public class SignActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// �������ڷ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		super.setContentView(R.layout.activity_sign);

	}

}