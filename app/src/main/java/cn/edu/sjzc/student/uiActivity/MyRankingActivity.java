package cn.edu.sjzc.student.uiActivity;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.adapter.MyRankingAdapter;
import cn.edu.sjzc.student.bean.MyRankingBean;
import cn.edu.sjzc.student.view.HorizontalListView;

public class MyRankingActivity extends BaseActivity implements View.OnClickListener,SensorEventListener {
    Context context;
    private HorizontalListView mListView;
    private MyRankingAdapter myRankingAdapter;
    private List<MyRankingBean> data;
    private String knowledgename[] = {"课程1", "课程2", "课程3", "课程4",
            "课程5", "课程6", "课程7", "课程8", "课程9", "课程10"};
    private int pencent[] = {13, 56, 78, 35, 57, 78, 45, 26, 44, 100};
    private TextView ranking_title, ranking_detail;
    private ImageButton rankging_back;

    private SensorManager mSensorManager;
    private Vibrator mVibrator;
    private final int ROCKPOWER = 20;// 这是传感器系数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher_ranking);
        context = MyRankingActivity.this;
        viewInit();
        initView();
        initRock();
    }

    private void initRock(){
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
    }

    private void initView() {
        this.ranking_title = (TextView) MyRankingActivity.this.findViewById(R.id.ranking_title);
        this.ranking_detail = (TextView) MyRankingActivity.this.findViewById(R.id.ranking_detail);
        this.rankging_back = (ImageButton) MyRankingActivity.this.findViewById(R.id.rankging_back);
        this.rankging_back.setOnClickListener(this);
    }

    public void viewInit() {
        mListView = (HorizontalListView) findViewById(R.id.listview);
        myRankingAdapter = new MyRankingAdapter(context);
        //另一种加载方式，可以完成下拉刷新
        data = new ArrayList<MyRankingBean>();

        for (int i = 0; i < knowledgename.length; i++) {
            MyRankingBean myRankingBean = new MyRankingBean();
            myRankingBean.setKnowledgename(knowledgename[i]);
            myRankingBean.setPencent(pencent[i]);

            data.add(myRankingBean);

        }
        myRankingAdapter.setData(data);
        mListView.setAdapter(myRankingAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rankging_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 因为Android版本问题，不得不用最新的方法，不然根本编译不过去
        if (mSensorManager != null) {// 注册监听器
            mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSensorManager != null) {// 取消监听器
            mSensorManager.unregisterListener(sensorEventListener);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

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
                    Toast.makeText(MyRankingActivity.this, "检测到摇晃，执行操作！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };
}
