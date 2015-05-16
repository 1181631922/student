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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.app.UserApplication;
import cn.edu.sjzc.student.util.PostUtil;

public class PerPersonalInfoActivity extends BaseActivity implements
        OnClickListener {

    private ImageButton personalinfo_back;
    private Button personalinfo_but;
    private ProgressBar personalinfo_progressbar;
    private TextView per_user_id, per_user_name, per_user_sex, per_user_number,
            per_user_major, per_user_old, per_user_email, per_user_phone, per_user_address;
    private String number;
    private String sex, idNum, address, tel, email, major;
    private int age;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.activity_per_personalinfo);

        initView();
        initData();
        Thread loadThread = new Thread(new LoadThread());
        loadThread.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            sex = bundle.getString("sex");
            idNum = bundle.getString("idNum");
            address = bundle.getString("address");
            tel = bundle.getString("tel");
            email = bundle.getString("emai");
            age = bundle.getInt("age");
            major = bundle.getString("major");
            per_user_sex.setText(sex);
            per_user_number.setText(idNum);
            per_user_major.setText(major);
            per_user_email.setText(email);
            per_user_phone.setText(tel);
            per_user_address.setText(address);
            per_user_old.setText(age + "");
            personalinfo_progressbar.setVisibility(View.GONE);

        }
    };

    class LoadThread implements Runnable {
        @Override
        public void run() {
            loadData();
        }
    }

    private void loadData() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request;
        try {
            String PERSONALINFOURL = personInfomationUrl + number;
            String backMsg = PostUtil.postData(PERSONALINFOURL, null);
            Log.d("-------person-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("sex", jsonObject.getString("sex"));
                bundle.putString("idNum", jsonObject.getString("idNum"));
                bundle.putString("address", jsonObject.getString("address"));
                bundle.putString("tel", jsonObject.getString("tel"));
                bundle.putString("email", jsonObject.getString("e-mail"));
                bundle.putInt("age", jsonObject.getInt("age"));
                bundle.putString("major", jsonObject.getString("major"));
                message.setData(bundle);
                handler.sendMessage(message);

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
        this.per_user_id.setText(number);
        String name = userdata.getString(UserApplication.USER_DATA_USERNAME, "");
        this.per_user_name.setText(name);
    }

    private void initView() {
        this.per_user_id = (TextView) this.findViewById(R.id.per_user_id);
        this.per_user_name = (TextView) this.findViewById(R.id.per_user_name);
        this.per_user_sex = (TextView) this.findViewById(R.id.per_user_sex);
        this.per_user_number = (TextView) this.findViewById(R.id.per_user_number);
        this.per_user_major = (TextView) this.findViewById(R.id.per_user_major);
        this.per_user_old = (TextView) this.findViewById(R.id.per_user_old);
        this.per_user_email = (TextView) this.findViewById(R.id.per_user_email);
        this.per_user_phone = (TextView) this.findViewById(R.id.per_user_phone);
        this.per_user_address = (TextView) this.findViewById(R.id.per_user_address);
        this.personalinfo_progressbar = (ProgressBar) this.findViewById(R.id.personalinfo_progressbar);
        this.personalinfo_progressbar.setVisibility(View.VISIBLE);
        this.personalinfo_back = (ImageButton) this.findViewById(R.id.personalinfo_back);
        this.personalinfo_back.setOnClickListener(this);
        this.personalinfo_but = (Button) this.findViewById(R.id.personalinfo_but);
        this.personalinfo_but.setOnClickListener(this);
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