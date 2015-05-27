package cn.edu.sjzc.student.uiActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.app.UserApplication;
import cn.edu.sjzc.student.dialog.ConfirmDialog;
import cn.edu.sjzc.student.dialog.MainExitDialog;
import cn.edu.sjzc.student.util.PostUtil;
import cn.sharesdk.onekeyshare.theme.classic.EditPage;

public class AdviceTeacherActivity extends BaseActivity {
    private ImageButton advice_teacher_back;
    private TextView advice_teacher_name;
    private EditText advice_teacher_evaluation;
    private Button advice_teacher_cancle, advice_teacher_submit;
    private String teacherId, teacherName, number, evaluation;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_teacher);
        Intent intent = this.getIntent();
        initView();
        initData();
    }

    private void initView() {
        advice_teacher_back = (ImageButton) findViewById(R.id.advice_teacher_back);
        advice_teacher_back.setOnClickListener(this);
        advice_teacher_name = (TextView) findViewById(R.id.advice_teacher_name);
        advice_teacher_evaluation = (EditText) findViewById(R.id.advice_teacher_evaluation);
        advice_teacher_cancle = (Button) findViewById(R.id.advice_teacher_cancle);
        advice_teacher_cancle.setOnClickListener(this);
        advice_teacher_submit = (Button) findViewById(R.id.advice_teacher_submit);
        advice_teacher_submit.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = this.getIntent();
        teacherId = intent.getStringExtra("teacher_id");
        teacherName = intent.getStringExtra("teacher_name");
        advice_teacher_name.setText(teacherName);
        SharedPreferences userdata = this.getSharedPreferences(UserApplication.USER_DATA, 0);
        number = userdata.getString(UserApplication.USER_DATA_NUMBER, "");
    }

    class EvaluationThread implements Runnable {
        @Override
        public void run() {
            evaluation();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.advice_teacher_back:
                finish();
                break;
            case R.id.advice_teacher_cancle:
                finish();
                break;
            case R.id.advice_teacher_submit:
                evaluation = advice_teacher_evaluation.getText().toString().trim();
                Thread changePassword = new Thread(new EvaluationThread());
                changePassword.start();
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(AdviceTeacherActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case 1:
                    Toast.makeText(AdviceTeacherActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    };

    private void evaluation() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("content", evaluation);
        map.put("teacherId", teacherId);
        map.put("number", number);
        try {
            String backMsg = PostUtil.postData(aBaseUrl + "result!addMessageAndroid.action", map);
            Log.d("-------changePasswordUrl-----------", aBaseUrl + "result!addMessageAndroid.action");
            Log.d("-------changepassword-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                message = jsonObject.getString("message");
                Message message1 = Message.obtain();
                message1.what = 0;
                handler.sendMessage(message1);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ConfirmExit() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this, R.style.mystyle, R.layout.dialog_confirm);
        confirmDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advice_teacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
