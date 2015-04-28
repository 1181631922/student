package cn.edu.sjzc.student.uiActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import cn.edu.sjzc.student.R;

public class LoginActivity extends BaseActivity {

	private Button sign;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// �������ڷ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		super.setContentView(R.layout.activity_login);
		
		this.sign = (Button)super.findViewById(R.id.sign);
		this.sign.setOnClickListener(new LoginSetOnClickListenerImpl());
		

	}
	
	private class LoginSetOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent it = new Intent(LoginActivity.this,SignActivity.class);
			
			LoginActivity.this.startActivity(it);
		}
		
	}
	

}