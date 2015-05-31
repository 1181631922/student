package cn.edu.sjzc.student.uiActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.adapter.EvaluationHistoryAdapter;
import cn.edu.sjzc.student.adapter.ScheduleAdapter;
import cn.edu.sjzc.student.app.UserApplication;
import cn.edu.sjzc.student.bean.EvaluationHistoryBean;
import cn.edu.sjzc.student.bean.ScheduleBean;
import cn.edu.sjzc.student.dialog.NetCheckDialog;
import cn.edu.sjzc.student.layout.PullToRefreshLayout;
import cn.edu.sjzc.student.util.PostUtil;

public class MyCourseActivity extends BaseActivity{
    private ImageButton about_course_back;
    private ProgressBar course_show_progress;
    private ListView course_course_show;
    private List<Map<String, Object>> myList = new ArrayList<Map<String, Object>>();
    private List<ScheduleBean> scheduleBeanList = new ArrayList<ScheduleBean>();
    private boolean isNet = false;
    private String number, teacher, course;
    private PullToRefreshLayout ptrl;
    private ScheduleAdapter scheduleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        initView();
        initData();
        course_show_progress.setVisibility(View.VISIBLE);
        Thread loadThread = new Thread(new LoadThread());
        loadThread.start();
    }

    private void initView() {
        about_course_back = (ImageButton) findViewById(R.id.about_course_back);
        about_course_back.setOnClickListener(this);
        course_course_show = (ListView) findViewById(R.id.course_course_show);
        course_show_progress = (ProgressBar) findViewById(R.id.course_show_progress);
        ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_course_view));
        ptrl.setOnRefreshListener(new MyListener());
    }

    private void initData() {
        SharedPreferences userdata = this.getSharedPreferences(UserApplication.USER_DATA, 0);
        number = userdata.getString(UserApplication.USER_DATA_NUMBER, "");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    updateUI();
                    break;
            }
        }
    };

    private void updateUI() {
        course_show_progress.setVisibility(View.GONE);
        scheduleAdapter = new ScheduleAdapter(MyCourseActivity.this, scheduleBeanList);
        course_course_show.setAdapter(null);
        course_course_show.setAdapter(scheduleAdapter);

//        advice_show_listview.setOnItemClickListener(new OnCourseItemClick());
    }

    private class MyListener implements PullToRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (CheckNetworkState()) {
                        updateUI();
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    } else {
                        NetCheckDialog netCheckDialog = new NetCheckDialog(MyCourseActivity.this, R.style.mystyle, R.layout.dialog_custom);
                        netCheckDialog.show();
                    }

                }
            }.sendEmptyMessageDelayed(0, 3000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
//            加载操作

            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }

    class LoadThread implements Runnable {
        @Override
        public void run() {
            loadTeacherData();
        }
    }

    private void loadTeacherData() {
        isNet = false;
        scheduleBeanList.clear();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("number", number);
        try {
            String backMsg = PostUtil.postData(aBaseUrl + "course!findCourseAndroid.action", map);
            Log.d("-------teacher-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                JSONArray coursearray = jsonObject.getJSONArray("content");
                for (int i = 0; i < coursearray.length(); i++) {
                    ScheduleBean scheduleBean = new ScheduleBean(teacher, course);
                    JSONObject shceduleobj = coursearray.getJSONObject(i);
                    scheduleBean.setTitle(shceduleobj.getString("teacher_name"));
                    scheduleBean.setId(shceduleobj.getString("coursename"));

                    scheduleBeanList.add(scheduleBean);
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

    @Override
    protected void onStart() {
        super.onStart();
        CheckNetworkState();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.about_course_back:
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_course, menu);
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
