package cn.edu.sjzc.student.uiFragment;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
import cn.edu.sjzc.student.adapter.StudentAdapter;
import cn.edu.sjzc.student.app.UserApplication;
import cn.edu.sjzc.student.bean.ScheduleBean;
import cn.edu.sjzc.student.bean.StudentUserBean;
import cn.edu.sjzc.student.dialog.NetCheckDialog;
import cn.edu.sjzc.student.layout.PullToRefreshLayout;
import cn.edu.sjzc.student.uiActivity.AdvStudentInfoActivity;
import cn.edu.sjzc.student.uiActivity.FindTeacherActivity;
import cn.edu.sjzc.student.util.PinyinUtils;
import cn.edu.sjzc.student.util.PostUtil;
import cn.edu.sjzc.student.view.RefreshableView;
import cn.edu.sjzc.student.view.StudentSideBarView;
import cn.edu.sjzc.student.view.StudentSideBarView.OnTouchingLetterChangedListener;

public class FindTeacherFragment extends BaseFragment implements
        OnTouchingLetterChangedListener, View.OnClickListener, SensorEventListener {

    public static BaseFragment newInstance(int index) {
        BaseFragment fragment = new FindTeacherFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        fragment.setIndex(index);
        return fragment;
    }

    private ListView lvShow;
    private List<StudentUserBean> studentUserBeans = new ArrayList<StudentUserBean>();
    private TextView overlay;
    private StudentSideBarView myView;
    private StudentAdapter adapter;
    private static List<Map<String, Object>> studentList = new ArrayList<Map<String, Object>>();
    private String sname, sphone;
    private RefreshableView refreshableView;
    private Button find_teacher;
    private PullToRefreshLayout ptrl;
    private String number, office, email, teacher_name, tel, teacher_id;
    private String FindTeacherUrl = aBaseUrl + "course!findTeacherAndroid.action";
    private boolean isNet = false;
    private ProgressBar findteacher_progressbar;


    private OverlayThread overlayThread = new OverlayThread();
    private SensorManager mSensorManager;
    private Vibrator mVibrator;
    //传感系数
    private final int ROCKPOWER = 15;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findteacher, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRock();
        initView();
        initData();
        findteacher_progressbar.setVisibility(View.VISIBLE);
        Thread loadThread = new Thread(new LoadThread());
        loadThread.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        CheckNetworkState();
    }

    private void initData() {
        SharedPreferences userdata = getActivity().getSharedPreferences(UserApplication.USER_DATA, 0);
        number = userdata.getString(UserApplication.USER_DATA_NUMBER, "");
    }

    class LoadThread implements Runnable {
        @Override
        public void run() {
            loadData();
        }
    }

    private void loadData() {
        studentUserBeans.clear();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("number", number);
        try {
            String backMsg = PostUtil.postData(FindTeacherUrl, map);
            Log.d("-------couse-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                JSONArray coursearray = jsonObject.getJSONArray("content");
                for (int i = 0; i < coursearray.length(); i++) {
                    StudentUserBean studentUserBean = new StudentUserBean(teacher_name, tel, null);
                    JSONObject studentuserobj = coursearray.getJSONObject(i);
                    studentUserBean.setUserName(studentuserobj.getString("teacher_name"));
                    studentUserBean.setPhoneNum(studentuserobj.getString("tel"));
                    studentUserBean.setPy(studentuserobj.getString("teacher_name"));

                    Map<String, Object> mapcourse = new HashMap<String, Object>();
                    mapcourse.put("teacher_name", studentuserobj.getString("teacher_name"));
                    mapcourse.put("tel", studentuserobj.getString("tel"));
                    mapcourse.put("office", studentuserobj.getString("office"));
                    mapcourse.put("email", studentuserobj.getString("email"));
                    mapcourse.put("teacher_id", studentuserobj.getString("teacher_id"));
                    studentList.add(mapcourse);
                    studentUserBeans.add(studentUserBean);


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
            findhandler.sendMessage(message);
        } else {
            message.what = 1;
            findhandler.sendMessage(message);
        }
    }

    Handler findhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initContent();
                    break;
            }
        }
    };

    public void initContent() {
        findteacher_progressbar.setVisibility(View.GONE);
        Log.i("coder", "studentUserBeans.size" + studentUserBeans.size());
        adapter = new StudentAdapter(getActivity(), studentUserBeans);
        lvShow.setAdapter(adapter);
        myView.setOnTouchingLetterChangedListener(this);
        lvShow.setOnItemClickListener(new studentInfoOnItemClickListener());
    }

    private void initRock() {
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mVibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
    }

    private void initView() {
        lvShow = (ListView) getActivity().findViewById(R.id.lvShow);
        myView = (StudentSideBarView) getActivity().findViewById(R.id.myView);
        overlay = (TextView) getActivity().findViewById(R.id.tvLetter);
        lvShow.setTextFilterEnabled(true);
        overlay.setVisibility(View.INVISIBLE);
        findteacher_progressbar = (ProgressBar) getActivity().findViewById(R.id.findteacher_progressbar);
        this.find_teacher = (Button) getActivity().findViewById(R.id.find_teacher);
        this.find_teacher.setOnClickListener(this);
        ptrl = ((PullToRefreshLayout) getActivity().findViewById(R.id.refresh_show_view));
        ptrl.setOnRefreshListener(new MyListener());
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (CheckNetworkState()) {
                        findteacher_progressbar.setVisibility(View.VISIBLE);
                        Thread loadThread = new Thread(new LoadThread());
                        loadThread.start();
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    } else {
                        NetCheckDialog netCheckDialog = new NetCheckDialog(getActivity(), R.style.mystyle, R.layout.dialog_custom);
                        netCheckDialog.show();
                    }

                }
            }.sendEmptyMessageDelayed(0, 2000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.find_teacher:
                Intent it_find_teacher = new Intent(getActivity(), FindTeacherActivity.class);
                startActivity(it_find_teacher);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSensorManager != null) {// 注册监听器
            mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mSensorManager != null) {// 取消监听器
            mSensorManager.unregisterListener(sensorEventListener);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }


    /**
     * 重力感应监听
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int medumValue = 19;// 如果不敏感请自行调低该数值,低于10的话就不行了,因为z轴上的加速度本身就已经达到10了
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                mVibrator.vibrate(200);
                Message msg = new Message();
                msg.what = ROCKPOWER;
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    /**
     * 动作执行
     */
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ROCKPOWER:
//                    if (CheckNetworkState()) {
//                        findteacher_progressbar.setVisibility(View.VISIBLE);
//                        Thread loadThread = new Thread(new LoadThread());
//                        loadThread.start();
//                    } else {
//                        NetCheckDialog netCheckDialog = new NetCheckDialog(getActivity(), R.style.mystyle, R.layout.dialog_custom);
//                        netCheckDialog.show();
//                    }
                    Toast.makeText(getActivity(), "检测到摇晃，执行操作！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    @Override
    public void onTouchingLetterChanged(String s) {
        // TODO Auto-generated method stub

        overlay.setText(s);
        overlay.setVisibility(View.VISIBLE);
        handler.removeCallbacks(overlayThread);
        handler.postDelayed(overlayThread, 1000);
        if (alphaIndexer(s) > 0) {
            int position = alphaIndexer(s);
            lvShow.setSelection(position);

        }
    }


    private class OverlayThread implements Runnable {

        public void run() {
            overlay.setVisibility(View.GONE);
        }

    }


    protected class studentInfoOnItemClickListener implements
            OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            Intent it_student_info = new Intent(getActivity(), AdvStudentInfoActivity.class);
            for (int i = 0; i <= position; i++) {
                if (position == i) {
                    Map map = (Map) studentList.get(i);
                    it_student_info.putExtra("teacher_name", (String) map.get("teacher_name"));
                    it_student_info.putExtra("tel", (String) map.get("tel"));
                    it_student_info.putExtra("office", (String) map.get("office"));
                    it_student_info.putExtra("email", (String) map.get("email"));
                    it_student_info.putExtra("teacher_id", (String) map.get("teacher_id"));
                }
            }
            startActivity(it_student_info);
        }
    }

    private Handler handler1 = new Handler() {
    };

    public int alphaIndexer(String s) {
        int position = 0;
        for (int i = 0; i < studentUserBeans.size(); i++) {

            if (studentUserBeans.get(i).getPy().startsWith(s)) {
                position = i;
                break;
            }
        }
        return position;
    }

}