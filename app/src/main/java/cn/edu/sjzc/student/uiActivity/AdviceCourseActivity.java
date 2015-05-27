package cn.edu.sjzc.student.uiActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.sjzc.student.R;

public class AdviceCourseActivity extends BaseActivity {
    private Button button1;
    private TextView advice_one_title;
    private RadioGroup radiogroup_one;
    private RadioButton advice_onegroup, advice_twogroup, advice_threegroup, advice_fourgroup, advice_fivegroup;
    private LinearLayout layout_one, layout_two;
    private String teacher_name, coursename, teacher_id, courseId;
    private ImageButton advice_course_back;
    private TextView advice_course_name, advice_course_evaluation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_course);
        initView();
        initData();
    }

    private void initView() {
        Intent intent = this.getIntent();
        teacher_name = intent.getStringExtra("teacher_name");
        coursename = intent.getStringExtra("coursename");
        teacher_id = intent.getStringExtra("teacher_id");
        courseId = intent.getStringExtra("courseId");

        advice_course_name = (TextView) findViewById(R.id.advice_course_name);
        advice_course_name.setText(teacher_name);
        advice_course_evaluation = (TextView) findViewById(R.id.advice_course_evaluation);
        advice_course_evaluation.setText(coursename);
        advice_course_back = (ImageButton) findViewById(R.id.advice_course_back);
        advice_course_back.setOnClickListener(this);
        layout_one = (LinearLayout) findViewById(R.id.layout_one);
        layout_two = (LinearLayout) findViewById(R.id.layout_two);
        radiogroup_one = (RadioGroup) findViewById(R.id.radiogroup_one);
        button1 = (Button) findViewById(R.id.button1);
    }

    private void initData() {
        addListenerRadioGroup1();
    }

    private void addListenerRadioGroup1() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radiogroup_one.getCheckedRadioButtonId();
                advice_onegroup = (RadioButton) findViewById(selected);
                layout_one.setVisibility(View.GONE);
                layout_two.setVisibility(View.VISIBLE);
                Toast.makeText(AdviceCourseActivity.this, advice_onegroup.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.advice_course_back:
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advice_course, menu);
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
