package cn.edu.sjzc.student.uiFragment;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.adapter.StudentAdapter;
import cn.edu.sjzc.student.bean.StudentUserBean;
import cn.edu.sjzc.student.uiActivity.AdvStudentInfoActivity;
import cn.edu.sjzc.student.uiActivity.FindTeacherActivity;
import cn.edu.sjzc.student.util.PinyinUtils;
import cn.edu.sjzc.student.view.RefreshableView;
import cn.edu.sjzc.student.view.StudentSideBarView;
import cn.edu.sjzc.student.view.StudentSideBarView.OnTouchingLetterChangedListener;

public class FindStudentFragment extends BaseFragment implements
        OnTouchingLetterChangedListener, View.OnClickListener ,SensorEventListener {

    public static BaseFragment newInstance(int index) {
        BaseFragment fragment = new FindStudentFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        fragment.setIndex(index);
        return fragment;
    }

    private ListView lvShow;
    private List<StudentUserBean> studentUserBeans;
    private TextView overlay;
    private StudentSideBarView myView;
    private StudentAdapter adapter;
    private static List<Map<String, Object>> studentList = new ArrayList<Map<String, Object>>();
    private String sname, sphone;
    private RefreshableView refreshableView;
    private Button find_teacher;

    private OverlayThread overlayThread = new OverlayThread();
    private SensorManager mSensorManager;
    private Vibrator mVibrator;
    //传感系数
    private final int ROCKPOWER = 15;


    // public String[] userinfoArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findteacher, container,
                false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRock();
        init();
        initView();

    }
    private void initRock(){
        mSensorManager=(SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mVibrator=(Vibrator)getActivity().getSystemService(Service.VIBRATOR_SERVICE);
    }

    private void initView() {
        this.find_teacher = (Button) getActivity().findViewById(R.id.find_teacher);
        this.find_teacher.setOnClickListener(this);
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
                    Toast.makeText(getActivity(), "检测到摇晃，执行操作！", Toast.LENGTH_SHORT).show();
//                    Refresh();
                    break;
            }
        }

    };

    @Override
    public void onTouchingLetterChanged(String s) {
        // TODO Auto-generated method stub
        Log.i("coder", "s:" + s);

        overlay.setText(s);
        overlay.setVisibility(View.VISIBLE);
        handler.removeCallbacks(overlayThread);
        handler.postDelayed(overlayThread, 1000);
        if (alphaIndexer(s) > 0) {
            int position = alphaIndexer(s);
            Log.i("coder", "position:" + position);
            lvShow.setSelection(position);

        }
    }


    private class OverlayThread implements Runnable {

        public void run() {
            overlay.setVisibility(View.GONE);
        }

    }

    public void init() {
        lvShow = (ListView) getActivity().findViewById(R.id.lvShow);
        myView = (StudentSideBarView) getActivity().findViewById(R.id.myView);

        overlay = (TextView) getActivity().findViewById(R.id.tvLetter);
        refreshableView = (RefreshableView) getActivity().findViewById(R.id.events_refreshable);
        lvShow.setTextFilterEnabled(true);
        overlay.setVisibility(View.INVISIBLE);

        getUserInfos();

        Log.i("coder", "studentUserBeans.size" + studentUserBeans.size());
        adapter = new StudentAdapter(getActivity(), studentUserBeans);

        lvShow.setAdapter(adapter);

        myView.setOnTouchingLetterChangedListener(this);

        lvShow.setOnItemClickListener(new studentInfoOnItemClickListener());
    }

    public void getUserInfos() {
        StudentUserBean[] userinfoArray = new StudentUserBean[]{
                new StudentUserBean("韩冰", "13303116239", PinyinUtils.getAlpha("韩冰")),
                new StudentUserBean("张海春", "18765432345", PinyinUtils.getAlpha("张海春")),
                new StudentUserBean("及徐冰", "18765432345", PinyinUtils.getAlpha("及徐冰")),
                new StudentUserBean("邻里中", "18765432345", PinyinUtils.getAlpha("邻里中")),
                new StudentUserBean("宋红卫", "18765432345", PinyinUtils.getAlpha("宋红卫")),
                new StudentUserBean("何东滨", "18765432345", PinyinUtils.getAlpha("何东滨")),
                new StudentUserBean("刘絮凝", "18765432345", PinyinUtils.getAlpha("刘絮凝")),
                new StudentUserBean("王丽娜", "18765432345", PinyinUtils.getAlpha("王丽娜")),
                new StudentUserBean("长子李", "18765432345", PinyinUtils.getAlpha("长子李")),
                new StudentUserBean("孙警务", "18765432345", PinyinUtils.getAlpha("孙警务")),
                new StudentUserBean("张军", "18765432345", PinyinUtils.getAlpha("张军")),
                new StudentUserBean("张兴华", "18765432345", PinyinUtils.getAlpha("张兴华")),
                new StudentUserBean("赵颖号", "18765432345", PinyinUtils.getAlpha("赵颖号")),
                new StudentUserBean("刘志国", "18765432345", PinyinUtils.getAlpha("刘志国")),
                new StudentUserBean("段理应", "18765432345", PinyinUtils.getAlpha("段理应")),
                new StudentUserBean("尽情用", "18765432345", PinyinUtils.getAlpha("尽情用")),
                new StudentUserBean("利亚", "18765432345", PinyinUtils.getAlpha("利亚")),
                new StudentUserBean("问这", "18765432345", PinyinUtils.getAlpha("问这")),
                new StudentUserBean("董倩", "18765432345", PinyinUtils.getAlpha("董倩")),
                new StudentUserBean("张舒梅", "18765432345", PinyinUtils.getAlpha("张舒梅")),
                new StudentUserBean("与帖中", "18765432345", PinyinUtils.getAlpha("与帖中")),
                new StudentUserBean("梦军营", "18765432345", PinyinUtils.getAlpha("梦军营")),
                new StudentUserBean("梨园", "18765432345", PinyinUtils.getAlpha("梨园")),
                new StudentUserBean("硫化", "18765432345", PinyinUtils.getAlpha("硫化"))};

        for (int i = 0; i < userinfoArray.length; i++) {

            StudentUserBean su = new StudentUserBean(sname, sphone, null);
            String st = userinfoArray[i].getUserName();
            String sp = userinfoArray[i].getPhoneNum();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("s_name", st);
            map.put("s_phone", sp);
            studentList.add(map);
        }

        Arrays.sort(userinfoArray);

        studentUserBeans = Arrays.asList(userinfoArray);
        Refresh();

    }

    private void Refresh() {
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(2000);
                    Log.d("-----------------------------------------------------------------------------------","onrefresh");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);
    }

    protected class studentInfoOnItemClickListener implements
            OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub

            Intent it_student_info = new Intent(getActivity(),
                    AdvStudentInfoActivity.class);
            for (int i = 0; i <= position; i++) {
                if (position == i) {
                    Map map = (Map) studentList.get(i);
                    String mstu = (String) map.get("s_name");
                    String mpho = (String) map.get("s_phone");
                    it_student_info.putExtra("student_name", mstu);
                    it_student_info.putExtra("student_phone", mpho);
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
        Log.i("coder", "i" + position + studentUserBeans.get(position));
        return position;
    }

}