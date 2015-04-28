package cn.edu.sjzc.student.uiActivity;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.dialog.PerChangeInfoDialog;

public class PerChangeInfoActivity extends BaseActivity implements OnClickListener {

	private ImageButton changeperinfo_back;
	
	private Button changeperinfo_but;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// �������ڷ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		super.setContentView(R.layout.activity_per_changeperinfo);

		init();
	}

	private void init() {
		ImageButton changeperinfo_back = (ImageButton) this
				.findViewById(R.id.changeperinfo_back);
		changeperinfo_back.setOnClickListener(this);

		Button changeperinfo_but = (Button) this
				.findViewById(R.id.changeperinfo_but);
		changeperinfo_but.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.changeperinfo_back:

			PerChangeInfoActivity.this.finish();

			break;
		case R.id.changeperinfo_but:

			PerChangeInfoDialog dialog = new PerChangeInfoDialog(this,
					R.style.mystyle, R.layout.dialog_exit);
			dialog.show();

			break;

		default:
			break;
		}

	}

}