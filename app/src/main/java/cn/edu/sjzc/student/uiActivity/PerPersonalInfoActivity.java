package cn.edu.sjzc.student.uiActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import cn.edu.sjzc.student.R;

public class PerPersonalInfoActivity extends BaseActivity implements
        OnClickListener {

	private ImageButton personalinfo_back;
	private Button personalinfo_but;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// �������ڷ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		super.setContentView(R.layout.activity_per_personalinfo);

		init();
	}

	private void init() {
		ImageButton personalinfo_back = (ImageButton) this
				.findViewById(R.id.personalinfo_back);
		personalinfo_back.setOnClickListener(this);

		Button personalinfo_but = (Button) this
				.findViewById(R.id.personalinfo_but);
		personalinfo_but.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.personalinfo_back:

			PerPersonalInfoActivity.this.finish();

			break;

		case R.id.personalinfo_but:

			Intent it_personalinfo = new Intent(PerPersonalInfoActivity.this, PerChangeInfoActivity.class);
			PerPersonalInfoActivity.this.startActivity(it_personalinfo);

			break;

		default:
			break;
		}

	}

}