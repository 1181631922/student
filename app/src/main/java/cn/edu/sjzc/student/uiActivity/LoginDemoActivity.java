package cn.edu.sjzc.student.uiActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.uiFragment.MainTabActivity;
import cn.edu.sjzc.student.util.PostUtil;

public class LoginDemoActivity extends BaseActivity implements OnClickListener {
    /**
     * Called when the activity is first created.
     */
    private Button loginBtn, registerBtn;
    private EditText inputUsername, inputPassword;
    private CheckBox saveInfoItem;
    private ProgressDialog mDialog;
    private String responseMsg = "";
    private SharedPreferences sp;
    private ProgressBar login_progress;
    private String backMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_first);
//		初始化视图控�?
        initView();
        // 初始化数�?
        LoadUserdata();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // 检查网络
        CheckNetworkState();
    }

    private void initView() {
        loginBtn = (Button) findViewById(R.id.login_btn_login);
        loginBtn.setOnClickListener(this);
        this.login_progress = (ProgressBar) this.findViewById(R.id.login_progress);
        inputUsername = (EditText) findViewById(R.id.login_edit_account);
        inputPassword = (EditText) findViewById(R.id.login_edit_pwd);
        // 监听记住密码选项
        saveInfoItem = (CheckBox) findViewById(R.id.login_cb_savepwd);
        saveInfoItem.setOnCheckedChangeListener(new checkBoxOnChangeListener());
    }

    private class checkBoxOnChangeListener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // TODO Auto-generated method stub
            // 载入用户信息
            Editor editor = sp.edit();
            if (saveInfoItem.isChecked()) {
                // 获取已经存在的用户名和密码
                String realUsername = sp.getString("username", "");
                String realPassword = sp.getString("password", "");
                editor.putBoolean("checkstatus", true);
                editor.commit();
                if ((!realUsername.equals("")) && !(realUsername == null)
                        && (!realPassword.equals(""))
                        && !(realPassword == null)) {
                    // 清空输入框
                    inputUsername.setText("");
                    inputPassword.setText("");
                    // 设置已有帐号密码
                    inputUsername.setText(realUsername);
                    inputPassword.setText(realPassword);
                }
            } else {
                editor.putBoolean("checkstatus", false);
                editor.commit();
                // 清空输入框
                inputUsername.setText("");
                inputPassword.setText("");
            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            // 登录
            case R.id.login_btn_login:
                login_progress.setVisibility(View.VISIBLE);
                Thread loginThread = new Thread(new LoginThread());
                loginThread.start();
                break;
            default:
                break;
        }
    }

    private boolean loginServer(String username, String password)
            throws UnsupportedEncodingException {
        boolean loginValidate = false;
        // 使用apache HTTP客户端实�?
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("number", username);
        map.put("password", password);
        StringBuilder url = new StringBuilder(loginUrl);
        try {
            String backMsg = PostUtil.postData(loginUrl, map);
            Log.d("----------backmsg----------", backMsg);
            try {
                JSONObject jsonObjece = new JSONObject(backMsg);
//                jsonObjece
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 判断是否记住密码，默认记�?
    private boolean isRemembered() {
        try {
            if (saveInfoItem.isChecked()) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    // 初始化用户数�?
    private void LoadUserdata() {
        sp = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        boolean checkstatus = sp.getBoolean("checkstatus", false);
        if (checkstatus) {
            saveInfoItem.setChecked(true);
            // 载入用户信息
            // 获取已经存在的用户名和密�?
            String realUsername = sp.getString("username", "");
            String realPassword = sp.getString("password", "");
            if ((!realUsername.equals("")) && !(realUsername == null)
                    || (!realPassword.equals("")) || !(realPassword == null)) {
                inputUsername.setText("");
                inputPassword.setText("");
                inputUsername.setText(realUsername);
                inputPassword.setText(realPassword);
            }
        } else {
            saveInfoItem.setChecked(false);
            inputUsername.setText("");
            inputPassword.setText("");
        }
    }


    // LoginThread线程�?
    class LoginThread implements Runnable {
        @Override
        public void run() {
            String username = inputUsername.getText().toString();
            String password = inputPassword.getText().toString();
            boolean checkstatus = sp.getBoolean("checkstatus", false);
            if (checkstatus) {
                // 获取已经存在的用户名和密�?
                String realUsername = sp.getString("username", "");
                String realPassword = sp.getString("password", "");
                if ((!realUsername.equals("")) && !(realUsername == null) || (!realPassword.equals("")) || !(realPassword == null)) {
                    if (username.equals(realUsername) && password.equals(realPassword)) {
                        username = inputUsername.getText().toString();
                        password = inputPassword.getText().toString();
                    }
                }
            } else {
                password = password;
            }
            // URL合法，但是这并不验证密码是否正确
            boolean loginValidate = false;
            try {
                loginValidate = loginServer(username, password);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Message msg = handler.obtainMessage();
            if (loginValidate) {
                if (responseMsg.equals("0")) {
                    msg.what = 0;
                    handler.sendMessage(msg);
                } else {
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            } else {
                msg.what = 2;
                handler.sendMessage(msg);
            }
        }
    }

    // Handler
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mDialog.cancel();
                    login_progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginDemoActivity.this, MainTabActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    login_progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    login_progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "URL验证失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    /**
     * MD5单向加密�?2位，用于加密密码，因为明文密码在信道中传输不安全，明文保存在本地也不安全
     *
     * @param str
     * @return
     */

    // public static String md5(String str) {
    // MessageDigest md5 = null;
    // try {
    // md5 = MessageDigest.getInstance("MD5");
    // } catch (Exception e) {
    // e.printStackTrace();
    // return "";
    // }
    //
    // char[] charArray = str.toCharArray();
    // byte[] byteArray = new byte[charArray.length];
    //
    // for (int i = 0; i < charArray.length; i++) {
    // byteArray[i] = (byte) charArray[i];
    // }
    // byte[] md5Bytes = md5.digest(byteArray);
    //
    // StringBuffer hexValue = new StringBuffer();
    // for (int i = 0; i < md5Bytes.length; i++) {
    // int val = ((int) md5Bytes[i]) & 0xff;
    // if (val < 16) {
    // hexValue.append("0");
    // }
    // hexValue.append(Integer.toHexString(val));
    // }
    // return hexValue.toString();
    // }

}
