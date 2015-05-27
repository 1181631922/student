package cn.edu.sjzc.student.uiActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.dialog.ConfirmDialog;
import cn.edu.sjzc.student.dialog.MainExitDialog;
import cn.sharesdk.onekeyshare.theme.classic.EditPage;

public class AdviceTeacherActivity extends BaseActivity {
    private ImageButton advice_teacher_back;
    private TextView advice_teacher_name;
    private EditText advice_teacher_evaluation;
    private Button advice_teacher_cancle, advice_teacher_submit;
    private String teacherId, teacherName;

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

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.advice_teacher_back:
                finish();
                break;
            case R.id.advice_teacher_submit:
                ConfirmExit();
                break;
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
