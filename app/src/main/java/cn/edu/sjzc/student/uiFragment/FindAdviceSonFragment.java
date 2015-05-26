package cn.edu.sjzc.student.uiFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.adapter.ScheduleAdapter;
import cn.edu.sjzc.student.bean.ScheduleBean;
import cn.edu.sjzc.student.layout.PullToRefreshLayout;

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
    private List<ScheduleBean> scheduleBeanList;
    private String adviceid, advicetitle;
    private List<Map<String, Object>> myList = new ArrayList<Map<String, Object>>();

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
        init(getIndex());
    }

    public void initView() {
        ptrl = ((PullToRefreshLayout) getActivity().findViewById(R.id.refresh_advice_view));
        ptrl.setOnRefreshListener(new MyListener());
        advice_show_listview = (ListView) getActivity().findViewById(R.id.advice_show);
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

    private void initCourseView() {

        ScheduleBean[] scheduleBeanArray = new ScheduleBean[]{new ScheduleBean("韩冰", "13303116239"),
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
        advice_show_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "LongClick on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        advice_show_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), " Click on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
            }
        });
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
        advice_show_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "LongClick on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        advice_show_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), " Click on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void init(int i) {
        if (i == 0) {
            initCourseView();
        } else if (i == 1) {
            initTeacherView();
        }

    }
}