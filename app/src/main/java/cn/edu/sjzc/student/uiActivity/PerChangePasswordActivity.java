package cn.edu.sjzc.student.uiActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.app.UserApplication;
import cn.edu.sjzc.student.uiFragment.MainTabActivity;
import cn.edu.sjzc.student.util.PostUtil;

public class PerChangePasswordActivity extends BaseActivity implements
        OnClickListener {

    private ImageButton changepassword_back;
    private EditText old_password, new_password, submit_password;
    private String oldPassword, newPassword, submitPassword;
    private String message;
    private Button change_password;
    private String number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.activity_per_changepassword);

        initView();
        initData();

    }

    private void initData() {
        SharedPreferences userdata = this.getSharedPreferences(UserApplication.USER_DATA, 0);
        number = userdata.getString(UserApplication.USER_DATA_NUMBER, "");

    }

    class ChangePasswordThread implements Runnable {
        @Override
        public void run() {
            changePassword();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(PerChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(PerChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();
                    MainTabActivity.instance.finish();
                    Intent intent = new Intent(PerChangePasswordActivity.this, LoginDemoActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void changePassword() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("old_password", oldPassword);
        map.put("new_password", newPassword);
        map.put("number", number);
        try {
            String backMsg = PostUtil.postData(changePasswordUrl, map);
            Log.d("-------changePasswordUrl-----------", changePasswordUrl);
            Log.d("-------changepassword-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                int state = jsonObject.getInt("state");
                switch (state) {
                    case 1:
                        message = jsonObject.getString("message");
                        Message message2 = Message.obtain();
                        message2.what = 1;
                        handler.sendMessage(message2);
                        break;
                    case 0:
                        message = jsonObject.getString("message");
                        Message message1 = Message.obtain();
                        message1.what = 0;
                        handler.sendMessage(message1);
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    Thread changePassword = new Thread(new ChangePasswordThread());
                    changePassword.start();
                } else {
                    Toast.makeText(PerChangePasswordActivity.this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }

    }

}