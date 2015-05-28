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
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.app.UserApplication;
import cn.edu.sjzc.student.dialog.PerChangeInfoDialog;
import cn.edu.sjzc.student.util.PostUtil;

public class PerChangeInfoActivity extends BaseActivity implements OnClickListener {

    private ImageButton changeperinfo_back;

    private Button changeperinfo_but;
    private String address, email, tel, number, message;
    private ProgressBar change_person_progressbar;
    private EditText change_email, change_tel, change_address;
    private String changeemail, changetel, changeaddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);// �������ڷ���
        requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
        super.setContentView(R.layout.activity_per_changeperinfo);

        initView();
        initData();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    change_person_progressbar.setVisibility(View.VISIBLE);
                    Toast.makeText(PerChangeInfoActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    };

    class LoadThread implements Runnable {
        @Override
        public void run() {
            changePassword();
        }
    }

    private void changePassword() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("address", changeaddress);
        map.put("email", changeemail);
        map.put("tel", changetel);
        map.put("number", number);
        try {
            String backMsg = PostUtil.postData(aBaseUrl + "person!updateInformationAndroid.action", map);
            Log.d("-------changeUrl-----------", aBaseUrl + "person!updateinformationAndroid.action");
            Log.d("-------changepassword-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                message = jsonObject.getString("message");
                Message message2 = Message.obtain();
                message2.what = 1;
                handler.sendMessage(message2);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        SharedPreferences userdata = this.getSharedPreferences(UserApplication.USER_DATA, 0);
        number = userdata.getString(UserApplication.USER_DATA_NUMBER, "");
    }

    private void initView() {
        Intent intent = this.getIntent();
        email = intent.getStringExtra("email");
        tel = intent.getStringExtra("tel");
        address = intent.getStringExtra("address");
        ImageButton changeperinfo_back = (ImageButton) this.findViewById(R.id.changeperinfo_back);
        changeperinfo_back.setOnClickListener(this);

        change_person_progressbar = (ProgressBar) findViewById(R.id.change_person_progressbar);
        Button changeperinfo_but = (Button) this.findViewById(R.id.changeperinfo_but);
        changeperinfo_but.setOnClickListener(this);

        change_email = (EditText) findViewById(R.id.change_email);
        change_tel = (EditText) findViewById(R.id.change_tel);
        change_address = (EditText) findViewById(R.id.change_address);
        change_email.setText(email);
        change_tel.setText(tel);
        change_address.setText(address);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.changeperinfo_back:

                PerChangeInfoActivity.this.finish();

                break;
            case R.id.changeperinfo_but:
                changeemail = change_email.getText().toString().trim();
                changetel = change_tel.getText().toString().trim();
                changeaddress = change_address.getText().toString().trim();
                change_person_progressbar.setVisibility(View.VISIBLE);
                Thread loadThread = new Thread(new LoadThread());
                loadThread.start();
//                PerChangeInfoDialog dialog = new PerChangeInfoDialog(this, R.style.mystyle, R.layout.dialog_exit);
//                dialog.show();

                break;

            default:
                break;
        }

    }

}