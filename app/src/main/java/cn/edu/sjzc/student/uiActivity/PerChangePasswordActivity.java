package cn.edu.sjzc.student.uiActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.edu.sjzc.student.R;

public class PerChangePasswordActivity extends BaseActivity implements
        OnClickListener {

    private ImageButton changepassword_back;
    private EditText old_password, new_password, submit_password;
    private String oldPassword, newPassword, submitPassword;
    private Button change_password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.activity_per_changepassword);

        initView();
    }

    private void initView() {
        this.old_password = (EditText) this.findViewById(R.id.old_password);
        this.new_password = (EditText) this.findViewById(R.id.new_password);
        this.submit_password = (EditText) this.findViewById(R.id.submit_password);
        this.changepassword_back = (ImageButton) this.findViewById(R.id.changepassword_back);
        this.changepassword_back.setOnClickListener(this);
        this.change_password = (Button) this.findViewById(R.id.change_password);
        this.change_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.changepassword_back:
                PerChangePasswordActivity.this.finish();
                break;
            case R.id.change_password:
                oldPassword = this.old_password.getText().toString().trim();

                newPassword = this.new_password.getText().toString().trim();
                submitPassword = this.submit_password.getText().toString().trim();
                if (newPassword.equals(submitPassword)) {

                } else {
                    Toast.makeText(PerChangePasswordActivity.this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }

    }

}