package cn.edu.sjzc.student.uiActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.adapter.ScheduleAdapter;
import cn.edu.sjzc.student.app.UserApplication;
import cn.edu.sjzc.student.bean.ScheduleBean;
import cn.edu.sjzc.student.dialog.HomeInfoDialog;
import cn.edu.sjzc.student.layout.PullToRefreshLayout;
import cn.edu.sjzc.student.util.PostUtil;

public class HomeInfoActivity extends BaseActivity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    private ProgressBar teacher_show_progress;
    private ImageButton teacher_evaluation_back;
    private String number;
    private boolean isNet = false;
    private ListView evaluation_teacher_show;
    private PullToRefreshLayout ptrl;
    private List<ScheduleBean> scheduleBeanList = new ArrayList<ScheduleBean>();
    private List<Map<String, Object>> myList = new ArrayList<Map<String, Object>>();
    private ScheduleAdapter scheduleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
        setContentView(R.layout.activity_home_info);
        initView();
        initData();
        teacher_show_progress.setVisibility(View.VISIBLE);
        Thread loadThread = new Thread(new LoadLoadThread());
        loadThread.start();
    }

    private void initView() {
        teacher_evaluation_back = (ImageButton) findViewById(R.id.teacher_evaluation_back);
        teacher_evaluation_back.setOnClickListener(this);
        teacher_show_progress = (ProgressBar) findViewById(R.id.teacher_show_progress);
        ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_teacher_view));
        ptrl.setOnRefreshListener(new MyListener());
        evaluation_teacher_show = (ListView) findViewById(R.id.evaluation_teacher_show);
    }

    private class MyListener implements PullToRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }

    public void initData() {
        SharedPreferences userdata = this.getSharedPreferences(UserApplication.USER_DATA, 0);
        number = userdata.getString(UserApplication.USER_DATA_NUMBER, "");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initCourseView();
                    break;
                case 1:
                    teacher_show_progress.setVisibility(View.GONE);
                    Toast.makeText(HomeInfoActivity.this, "教师未发起评价！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void initCourseView() {
        teacher_show_progress.setVisibility(View.GONE);
        scheduleAdapter = new ScheduleAdapter(HomeInfoActivity.this, scheduleBeanList);
        evaluation_teacher_show.setAdapter(null);
        evaluation_teacher_show.setAdapter(scheduleAdapter);

        evaluation_teacher_show.setOnItemClickListener(new OnCourseItemClick());
    }

    protected class OnCourseItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(HomeInfoActivity.this, AdviceCourseActivity.class);
            for (int i = 0; i <= position; i++) {
                if (position == i) {
                    Map map = (Map) myList.get(i);
                    intent.putExtra("teacher_name", (String) map.get("mid"));
                    intent.putExtra("coursename", (String) map.get("mtitle"));
                    intent.putExtra("courseId", (String) map.get("courseId"));
                    intent.putExtra("request_id", (String) map.get("request_id"));
                }
            }
            startActivity(intent);
        }
    }

    class LoadLoadThread implements Runnable {
        @Override
        public void run() {
            loadCourseData();
        }
    }

    private void loadCourseData() {
        isNet = false;
        scheduleBeanList.clear();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("number", number);
        try {
            String backMsg = PostUtil.postData(aBaseUrl + "evaluationRequest!showRequestListAndroid.action", map);
            Log.d("-------couse-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                JSONArray coursearray = jsonObject.getJSONArray("content");
                int mess = jsonObject.getInt("message");
                if (mess == 0) {
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
                } else {
                    for (int i = 0; i < coursearray.length(); i++) {
                        ScheduleBean scheduleBean = new ScheduleBean(null, null);
                        JSONObject shceduleobj = coursearray.getJSONObject(i);
                        scheduleBean.setTitle(shceduleobj.getString("teacher_name"));
                        scheduleBean.setId(shceduleobj.getString("course_name"));

                        Map<String, Object> mapcourse = new HashMap<String, Object>();
                        mapcourse.put("mid", shceduleobj.getString("teacher_name"));
                        mapcourse.put("mtitle", shceduleobj.getString("course_name"));
                        mapcourse.put("courseId", shceduleobj.getString("course_id"));
                        mapcourse.put("request_id", shceduleobj.getString("request_id"));
                        myList.add(mapcourse);
                        scheduleBeanList.add(scheduleBean);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.teacher_evaluation_back:
                finish();
                break;

        }
    }


    public void ConfirmExit() {// �˳�ȷ��
        HomeInfoDialog dialog = new HomeInfoDialog(this, R.style.mystyle, R.layout.dialog_exit_main);
        dialog.show();
    }


}