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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.app.UserApplication;
import cn.edu.sjzc.student.bean.ScheduleBean;
import cn.edu.sjzc.student.util.PostUtil;

public class AdviceCourseActivity extends BaseActivity {
    private Button button_one, button_two, button_three, button_four, button_five;
    private RadioGroup radiogroup_one, radiogroup_two, radiogroup_three, radiogroup_four, radiogroup_five;
    private RadioButton advice_onegroup, advice_twogroup, advice_threegroup, advice_fourgroup, advice_fivegroup;
    private LinearLayout layout_one, layout_two, layout_three, layout_four, layout_five;
    private String teacher_name, coursename, teacher_id, courseId, request_id;
    private ImageButton advice_course_back;
    private TextView advice_course_name, advice_course_evaluation;
    private TextView advice_one_title, advice_two_title, advice_three_title, advice_four_title, advice_five_title;
    private String one_title, two_title, three_title, four_title, five_title;
    //    第一组内容
    private TextView advice_onetext_one, advice_onetext_two, advice_onetext_three;
    private String onetext_one, onetext_two, onetext_three;
    private RadioButton advice_onegroup_three;
    //    第er组内容
    private TextView advice_twotext_one, advice_twotext_two, advice_twotext_three;
    private String twotext_one, twotext_two, twotext_three;
    private RadioButton advice_twogroup_three;
    //    第san组内容
    private TextView advice_threetext_one, advice_threetext_two, advice_threetext_three;
    private String threetext_one, threetext_two, threetext_three;
    private RadioButton advice_threegroup_three;
    //    第si组内容
    private TextView advice_fourtext_one, advice_fourtext_two, advice_fourtext_three;
    private String fourtext_one, fourtext_two, fourtext_three;
    private RadioButton advice_fourgroup_three;
    //    第wu组内容
    private TextView advice_fivetext_one, advice_fivetext_two, advice_fivetext_three;
    private String fivetext_one, fivetext_two, fivetext_three;
    private RadioButton advice_fivegroup_three;

    private boolean isNet = false;
    private String number, message;
    private String ADVICE_COURSE = aBaseUrl + "course!findEvaluationListAndroid";
    private ProgressBar advice_progressbar;
    private int A = 0;
    private int B = 0;
    private int C = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_course);
        initView();
        initData();
        advice_progressbar.setVisibility(View.VISIBLE);
        Thread loadThread = new Thread(new LoadThread());
        loadThread.start();
    }

    class LoadThread implements Runnable {
        @Override
        public void run() {
            loadData();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    advice_progressbar.setVisibility(View.GONE);
                    break;
                case 2:
                    advice_progressbar.setVisibility(View.GONE);
                    Toast.makeText(AdviceCourseActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case 10:
                    advice_one_title.setText(one_title);
                    advice_onetext_one.setText(onetext_one);
                    advice_onetext_two.setText(onetext_two);
                    if (onetext_three.equals(null) || onetext_three.equals("")) {
                        advice_onegroup_three.setVisibility(View.GONE);
                    } else {
                        advice_onetext_three.setText(onetext_three);
                    }
                    break;
                case 11:
                    advice_two_title.setText(two_title);
                    advice_twotext_one.setText(twotext_one);
                    advice_twotext_two.setText(twotext_two);
//                    advice_twotext_three.setText(twotext_three);
                    if (twotext_three.equals(null) || twotext_three.equals("")) {
                        advice_twogroup_three.setVisibility(View.GONE);
                    } else {
                        advice_twotext_three.setText(twotext_three);
                    }
                    break;
                case 12:
                    advice_three_title.setText(three_title);
                    advice_threetext_one.setText(threetext_one);
                    advice_threetext_two.setText(threetext_two);
//                    advice_threetext_three.setText(threetext_three);
                    if (threetext_three.equals(null) || threetext_three.equals("")) {
                        advice_threegroup_three.setVisibility(View.GONE);
                    } else {
                        advice_threetext_three.setText(threetext_three);
                    }
                    break;
                case 13:
                    advice_four_title.setText(four_title);
                    advice_fourtext_one.setText(fourtext_one);
                    advice_fourtext_two.setText(fourtext_two);
//                    advice_fourtext_three.setText(fourtext_three);
                    if (fourtext_three.equals(null) || fourtext_three.equals("")) {
                        advice_threegroup_three.setVisibility(View.GONE);
                    } else {
                        advice_fourtext_three.setText(fourtext_three);
                    }
                    break;
                case 14:
                    advice_five_title.setText(five_title);
                    advice_fivetext_one.setText(fivetext_one);
                    advice_fivetext_two.setText(fivetext_two);
//                    advice_fivetext_three.setText(fivetext_three);
                    if (fivetext_three.equals(null) || fivetext_three.equals("")) {
                        advice_fivegroup_three.setVisibility(View.GONE);
                    } else {
                        advice_fivetext_three.setText(fivetext_three);
                    }
                    break;
            }
        }
    };

    private void loadData() {
        isNet = false;
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("courseId", courseId);
        try {
            String backMsg = PostUtil.postData(ADVICE_COURSE, map);
            Log.d("-------evaluation-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                JSONArray advicearray = jsonObject.getJSONArray("content");
                for (int i = 0; i < advicearray.length(); i++) {

                    if (i == 0) {
                        JSONObject adviceobj = advicearray.getJSONObject(i);
                        one_title = adviceobj.getString("evaluationContent");
                        onetext_one = adviceobj.getString("A");
                        onetext_two = adviceobj.getString("B");
                        onetext_three = adviceobj.getString("C");
                        Message message1 = Message.obtain();
                        message1.what = 10;
                        handler.sendMessage(message1);

//                        advice_one_title.setText(adviceobj.getString("evaluationContent"));
//                        advice_onetext_one.setText(adviceobj.getString("A"));
//                        advice_onetext_two.setText(adviceobj.getString("B"));
//                        advice_onetext_three.setText(adviceobj.getString("C"));
                    }
                    if (i == 1) {
                        JSONObject adviceobj = advicearray.getJSONObject(i);
                        two_title = adviceobj.getString("evaluationContent");
                        twotext_one = adviceobj.getString("A");
                        twotext_two = adviceobj.getString("B");
                        twotext_three = adviceobj.getString("C");
                        Message message1 = Message.obtain();
                        message1.what = 11;
                        handler.sendMessage(message1);
//                        advice_two_title.setText(adviceobj.getString("evaluationContent"));
//                        advice_twotext_one.setText(adviceobj.getString("A"));
//                        advice_twotext_two.setText(adviceobj.getString("B"));
//                        advice_twotext_three.setText(adviceobj.getString("C"));
                    }
                    if (i == 2) {
                        JSONObject adviceobj = advicearray.getJSONObject(i);
                        three_title = adviceobj.getString("evaluationContent");
                        threetext_one = adviceobj.getString("A");
                        threetext_two = adviceobj.getString("B");
                        threetext_three = adviceobj.getString("C");
                        Message message1 = Message.obtain();
                        message1.what = 12;
                        handler.sendMessage(message1);
//                        advice_three_title.setText(adviceobj.getString("evaluationContent"));
//                        advice_threetext_one.setText(adviceobj.getString("A"));
//                        advice_threetext_two.setText(adviceobj.getString("B"));
//                        advice_threetext_three.setText(adviceobj.getString("C"));
                    }
                    if (i == 3) {
                        JSONObject adviceobj = advicearray.getJSONObject(i);
                        four_title = adviceobj.getString("evaluationContent");
                        fourtext_one = adviceobj.getString("A");
                        fourtext_two = adviceobj.getString("B");
                        fourtext_three = adviceobj.getString("C");
                        Message message1 = Message.obtain();
                        message1.what = 13;
                        handler.sendMessage(message1);
//                        advice_four_title.setText(adviceobj.getString("evaluationContent"));
//                        advice_fourtext_one.setText(adviceobj.getString("A"));
//                        advice_fourtext_two.setText(adviceobj.getString("B"));
//                        advice_fourtext_three.setText(adviceobj.getString("C"));
                    }
                    if (i == 4) {
                        JSONObject adviceobj = advicearray.getJSONObject(i);
                        five_title = adviceobj.getString("evaluationContent");
                        fivetext_one = adviceobj.getString("A");
                        fivetext_two = adviceobj.getString("B");
                        fivetext_three = adviceobj.getString("C");
                        Message message1 = Message.obtain();
                        message1.what = 14;
                        handler.sendMessage(message1);
//                        advice_five_title.setText(adviceobj.getString("evaluationContent"));
//                        advice_fivetext_one.setText(adviceobj.getString("A"));
//                        advice_fivetext_two.setText(adviceobj.getString("B"));
//                        advice_fivetext_three.setText(adviceobj.getString("C"));
                    }

                }
                isNet = true;
            } catch (JSONException e) {
                e.printStackTrace();
                isNet = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            isNet = false;
        }
        Message message = Message.obtain();
        if (isNet) {
            message.what = 0;
            handler.sendMessage(message);
        } else {
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    private void initView() {
        Intent intent = this.getIntent();
        teacher_name = intent.getStringExtra("teacher_name");
        coursename = intent.getStringExtra("coursename");
        teacher_id = intent.getStringExtra("teacher_id");
        courseId = intent.getStringExtra("courseId");
        if (!intent.getStringExtra("courseId").equals(null) && !intent.getStringExtra("courseId").equals("")) {
            request_id = intent.getStringExtra("courseId");
        }


        advice_progressbar = (ProgressBar) findViewById(R.id.advise_progressbar);
        advice_course_name = (TextView) findViewById(R.id.advice_course_name);
        advice_course_name.setText(teacher_name);
        advice_course_evaluation = (TextView) findViewById(R.id.advice_course_evaluation);
        advice_course_evaluation.setText(coursename);
        advice_course_back = (ImageButton) findViewById(R.id.advice_course_back);
        advice_course_back.setOnClickListener(this);

//        五个点评的项的各自布局
        layout_one = (LinearLayout) findViewById(R.id.layout_one);
        layout_two = (LinearLayout) findViewById(R.id.layout_two);
        layout_three = (LinearLayout) findViewById(R.id.layout_three);
        layout_four = (LinearLayout) findViewById(R.id.layout_four);
        layout_five = (LinearLayout) findViewById(R.id.layout_five);

//        五组评价
        radiogroup_one = (RadioGroup) findViewById(R.id.radiogroup_one);
        radiogroup_two = (RadioGroup) findViewById(R.id.radiogroup_two);
        radiogroup_three = (RadioGroup) findViewById(R.id.radiogroup_three);
        radiogroup_four = (RadioGroup) findViewById(R.id.radiogroup_four);
        radiogroup_five = (RadioGroup) findViewById(R.id.radiogroup_five);

//五个确定按钮
        button_one = (Button) findViewById(R.id.button_one);
        button_two = (Button) findViewById(R.id.button_two);
        button_three = (Button) findViewById(R.id.button_three);
        button_four = (Button) findViewById(R.id.button_four);
        button_five = (Button) findViewById(R.id.button_five);

//        biaoti
        advice_one_title = (TextView) findViewById(R.id.advise_one_title);
        advice_two_title = (TextView) findViewById(R.id.advice_two_title);
        advice_three_title = (TextView) findViewById(R.id.advice_three_title);
        advice_four_title = (TextView) findViewById(R.id.advice_four_title);
        advice_five_title = (TextView) findViewById(R.id.advice_five_title);

        //    第一组内容
        advice_onetext_one = (TextView) findViewById(R.id.advice_onetext_one);
        advice_onetext_two = (TextView) findViewById(R.id.advice_onetext_two);
        advice_onetext_three = (TextView) findViewById(R.id.advice_onetext_three);
        advice_onegroup_three = (RadioButton) findViewById(R.id.advice_onegroup_three);
        //    第er组内容
        advice_twotext_one = (TextView) findViewById(R.id.advice_twotext_one);
        advice_twotext_two = (TextView) findViewById(R.id.advice_twotext_two);
        advice_twotext_three = (TextView) findViewById(R.id.advice_twotext_three);
        advice_twogroup_three = (RadioButton) findViewById(R.id.advice_twogroup_three);
        //    第san组内容
        advice_threetext_one = (TextView) findViewById(R.id.advice_threetext_one);
        advice_threetext_two = (TextView) findViewById(R.id.advice_threetext_two);
        advice_threetext_three = (TextView) findViewById(R.id.advice_threetext_three);
        advice_threegroup_three = (RadioButton) findViewById(R.id.advice_threegroup_three);
        //    第si组内容
        advice_fourtext_one = (TextView) findViewById(R.id.advice_fourtext_one);
        advice_fourtext_two = (TextView) findViewById(R.id.advice_fourtext_two);
        advice_fourtext_three = (TextView) findViewById(R.id.advice_fourtext_three);
        advice_fourgroup_three = (RadioButton) findViewById(R.id.advice_fourgroup_three);
        //    第wu组内容
        advice_fivetext_one = (TextView) findViewById(R.id.advice_fivetext_one);
        advice_fivetext_two = (TextView) findViewById(R.id.advice_fivetext_two);
        advice_fivetext_three = (TextView) findViewById(R.id.advice_fivetext_three);
        advice_fivegroup_three = (RadioButton) findViewById(R.id.advice_fivegroup_three);
    }

    private void initData() {
        SharedPreferences userdata = this.getSharedPreferences(UserApplication.USER_DATA, 0);
        number = userdata.getString(UserApplication.USER_DATA_NUMBER, "");
        addListenerRadioGroupOne();
        addListenerRadioGroupTwo();
        addListenerRadioGroupThree();
        addListenerRadioGroupFour();
        addListenerRadioGroupFive();
    }

    private void addListenerRadioGroupOne() {
        button_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radiogroup_one.getCheckedRadioButtonId();
                advice_onegroup = (RadioButton) findViewById(selected);
                if (advice_onegroup.getText().equals("A")) {
                    A += 1;
                } else if (advice_onegroup.getText().equals("B")) {
                    B += 1;
                } else if (advice_onegroup.getText().equals("C")) {
                    C += 1;
                }
                layout_one.setVisibility(View.GONE);
                layout_two.setVisibility(View.VISIBLE);
                Toast.makeText(AdviceCourseActivity.this, advice_onegroup.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addListenerRadioGroupTwo() {
        button_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radiogroup_two.getCheckedRadioButtonId();
                advice_twogroup = (RadioButton) findViewById(selected);
                if (advice_twogroup.getText().equals("A")) {
                    A += 1;
                } else if (advice_twogroup.getText().equals("B")) {
                    B += 1;
                } else if (advice_twogroup.getText().equals("C")) {
                    C += 1;
                }
                layout_two.setVisibility(View.GONE);
                layout_three.setVisibility(View.VISIBLE);
                Toast.makeText(AdviceCourseActivity.this, advice_twogroup.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addListenerRadioGroupThree() {
        button_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radiogroup_three.getCheckedRadioButtonId();
                advice_threegroup = (RadioButton) findViewById(selected);
                if (advice_threegroup.getText().equals("A")) {
                    A += 1;
                } else if (advice_threegroup.getText().equals("B")) {
                    B += 1;
                } else if (advice_threegroup.getText().equals("C")) {
                    C += 1;
                }
                layout_three.setVisibility(View.GONE);
                layout_four.setVisibility(View.VISIBLE);
                Toast.makeText(AdviceCourseActivity.this, advice_threegroup.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addListenerRadioGroupFour() {
        button_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radiogroup_four.getCheckedRadioButtonId();
                advice_fourgroup = (RadioButton) findViewById(selected);
                if (advice_fourgroup.getText().equals("A")) {
                    A += 1;
                } else if (advice_fourgroup.getText().equals("B")) {
                    B += 1;
                } else if (advice_fourgroup.getText().equals("C")) {
                    C += 1;
                }
                layout_four.setVisibility(View.GONE);
                layout_five.setVisibility(View.VISIBLE);
                Toast.makeText(AdviceCourseActivity.this, advice_fourgroup.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addListenerRadioGroupFive() {
        button_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radiogroup_five.getCheckedRadioButtonId();
                advice_fivegroup = (RadioButton) findViewById(selected);
                if (advice_fivegroup.getText().equals("A")) {
                    A += 1;
                } else if (advice_fivegroup.getText().equals("B")) {
                    B += 1;
                } else if (advice_fivegroup.getText().equals("C")) {
                    C += 1;
                }
                advice_progressbar.setVisibility(View.VISIBLE);
                Toast.makeText(AdviceCourseActivity.this, advice_fivegroup.getText(), Toast.LENGTH_SHORT).show();
                Thread changePassword = new Thread(new SubmitThread());
                changePassword.start();
                layout_five.setVisibility(View.GONE);


            }
        });
    }

    class SubmitThread implements Runnable {
        @Override
        public void run() {
            submit();
        }
    }

    private void submit() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("A", A + "");
        map.put("B", B + "");
        map.put("C", C + "");
        map.put("courseId", courseId);
        map.put("number", number);
        if (request_id.equals(null) && request_id.equals("")) {
            map.put("request_id", request_id);
        }
        try {
            String backMsg = PostUtil.postData(aBaseUrl + "result!updateCourseResultAndroid.action", map);
            Log.d("-------submitUrl-----------", aBaseUrl + "result!updateCourseResultAndroid.action");
            Log.d("-------changepassword-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                message = jsonObject.getString("message");
                Message message2 = Message.obtain();
                message2.what = 2;
                handler.sendMessage(message2);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
