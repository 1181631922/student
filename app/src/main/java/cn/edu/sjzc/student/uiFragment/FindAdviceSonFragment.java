package cn.edu.sjzc.student.uiFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.adapter.ScheduleAdapter;
import cn.edu.sjzc.student.app.UserApplication;
import cn.edu.sjzc.student.bean.ScheduleBean;
import cn.edu.sjzc.student.layout.PullToRefreshLayout;
import cn.edu.sjzc.student.uiActivity.AdvStudentInfoActivity;
import cn.edu.sjzc.student.uiActivity.AdviceCourseActivity;
import cn.edu.sjzc.student.uiActivity.AdviceTeacherActivity;
import cn.edu.sjzc.student.util.PostUtil;

public class FindAdviceSonFragment extends BaseFragment {

    public static BaseFragment newInstance(int index) {
        BaseFragment fragment = new FindAdviceSonFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        fragment.setIndex(index);
        return fragment;
    }

    private View layoutView;
    private FragmentTabHost mTabHost;
    private TextView textView, tv;
    private ListView advice_show_listview;
    private PullToRefreshLayout ptrl;
    private List<ScheduleBean> scheduleBeanList = new ArrayList<ScheduleBean>();
    private String adviceid, advicetitle;
    private List<Map<String, Object>> myList = new ArrayList<Map<String, Object>>();
    private String COURSE_URL = aBaseUrl + "course!findCourseAndroid";
    private String number, courseid, title;
    private Button start;
    private LinearLayout advice_list;
    private ProgressBar advice_progressbar;
    private ScheduleAdapter scheduleAdapter;
    private boolean isNet = false;
    private static int countCourse = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_advice, null);
        return layoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        init(getIndex());

    }


    private void initData() {
        SharedPreferences userdata = getActivity().getSharedPreferences(UserApplication.USER_DATA, 0);
        number = userdata.getString(UserApplication.USER_DATA_NUMBER, "");
    }

    class LoadThread implements Runnable {
        @Override
        public void run() {
            loadCourseData();
        }
    }

    private void loadCourseData() {
        scheduleBeanList.clear();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("number", number);
        try {
            String backMsg = PostUtil.postData(COURSE_URL, map);
            Log.d("-------couse-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                JSONArray coursearray = jsonObject.getJSONArray("content");
                for (int i = 0; i < coursearray.length(); i++) {
                    ScheduleBean scheduleBean = new ScheduleBean(courseid, title);
                    JSONObject shceduleobj = coursearray.getJSONObject(i);
                    scheduleBean.setTitle(shceduleobj.getString("teacher_name"));
                    scheduleBean.setId(shceduleobj.getString("coursename"));

                    Map<String, Object> mapcourse = new HashMap<String, Object>();
                    mapcourse.put("mid", shceduleobj.getString("teacher_name"));
                    mapcourse.put("mtitle", shceduleobj.getString("coursename"));
                    mapcourse.put("teacher_id", shceduleobj.getString("teacher_id"));
                    mapcourse.put("courseId", shceduleobj.getString("courseId"));
                    myList.add(mapcourse);
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

    public void initView() {
        ptrl = ((PullToRefreshLayout) getActivity().findViewById(R.id.refresh_advice_view));
        ptrl.setOnRefreshListener(new MyListener());
        advice_show_listview = (ListView) getActivity().findViewById(R.id.advice_show);
        advice_progressbar = (ProgressBar) getActivity().findViewById(R.id.advice_progressbar);
        advice_list = (LinearLayout) getActivity().findViewById(R.id.advice_list);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initCourseView();
                    break;
            }
        }
    };

    private void initCourseView() {
        advice_progressbar.setVisibility(View.GONE);
        advice_list.setVisibility(View.VISIBLE);
        scheduleAdapter = new ScheduleAdapter(getActivity(), scheduleBeanList);
        advice_show_listview.setAdapter(null);
        advice_show_listview.setAdapter(scheduleAdapter);

        advice_show_listview.setOnItemClickListener(new OnCourseItemClick());
    }

    protected class OnCourseItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), AdviceCourseActivity.class);
            for (int i = 0; i <= position; i++) {
                if (position == i) {
                    Map map = (Map) myList.get(i);
                    intent.putExtra("teacher_name", (String) map.get("mid"));
                    intent.putExtra("coursename", (String) map.get("mtitle"));
                    intent.putExtra("teacher_id", (String) map.get("teacher_id"));
                    intent.putExtra("courseId", (String) map.get("courseId"));
                }
            }
            startActivity(intent);
        }
    }

    private void initTeacherView() {

        ScheduleBean[] scheduleBeanArray = new ScheduleBean[]{new ScheduleBean("韩冰", "13303116239"),
                new ScheduleBean("张海春", "18765432345"),
                new ScheduleBean("张海春", "18765432345"),
                new ScheduleBean("张海春", "18765432345"),
                new ScheduleBean("张海春", "18765432345"),
                new ScheduleBean("及徐冰", "18765432345")};

        for (int i = 0; i < scheduleBeanArray.length; i++) {
            ScheduleBean scheduleBean = new ScheduleBean(adviceid, advicetitle);
            String mid = scheduleBeanArray[i].getId();
            String mtitle = scheduleBeanArray[i].getTitle();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mid", mid);
            map.put("mtitle", mtitle);
            myList.add(map);
        }
        Arrays.sort(scheduleBeanArray);
        scheduleBeanList = Arrays.asList(scheduleBeanArray);
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getActivity(), scheduleBeanList);
        advice_show_listview.setAdapter(scheduleAdapter);
        advice_show_listview.setOnItemClickListener(new OnTeacherItemClick());
    }

    protected class OnTeacherItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), AdviceTeacherActivity.class);
            for (int i = 0; i <= position; i++) {
                if (position == i) {
                    Map map = (Map) myList.get(i);
                    intent.putExtra("mid", (String) map.get("mid"));
                    intent.putExtra("mtitle", (String) map.get("mtitle"));
                }
            }
            startActivity(intent);
        }
    }


    public void init(int i) {
        if (i == 0) {
            advice_progressbar.setVisibility(View.VISIBLE);
            Thread loadThread = new Thread(new LoadThread());
            loadThread.start();
        } else if (i == 1) {

        }

    }

}