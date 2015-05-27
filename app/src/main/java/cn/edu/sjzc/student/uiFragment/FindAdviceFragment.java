package cn.edu.sjzc.student.uiFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.bean.ScheduleBean;
import cn.edu.sjzc.student.util.PostUtil;

public class FindAdviceFragment extends BaseFragment implements View.OnClickListener {

    public static BaseFragment newInstance(int index) {
        BaseFragment fragment = new FindAdviceFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        fragment.setIndex(index);
        return fragment;
    }

    private View layoutView;
    private FragmentTabHost mTabHost;
    private String COURSE_URL = aBaseUrl + "course!findCourseAndroid";
    private String number, courseid, title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        loadData();
    }

    private void loadData() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("number", "20111308011");
        try {
            String backMsg = PostUtil.postData(COURSE_URL, map);
            Log.d("-------couse-----------", backMsg);
            try {
                JSONObject jsonObject = new JSONObject(backMsg);
                JSONArray coursearray = jsonObject.getJSONArray("content");
                for (int i = 0; i < coursearray.length(); i++) {
                    ScheduleBean scheduleBean = new ScheduleBean(courseid, title);
                    JSONObject shceduleobj = coursearray.getJSONObject(i);
                    String id = shceduleobj.getString("courseId");
                    String name = shceduleobj.getString("coursename");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_tabs, null);
        layoutView.findViewById(R.id.button1).setOnClickListener(this);
        layoutView.findViewById(R.id.button1).setSelected(true);
        layoutView.findViewById(R.id.button2).setOnClickListener(this);

        if (savedInstanceState == null) {
            BaseFragment fragment = (BaseFragment) getChildFragmentManager()
                    .findFragmentByTag(0 + "");// getActivity().getSupportFragmentManager().findFragmentByTag(index+"");
            if (fragment == null) {
                init(0);
            }
        }

        return layoutView;
    }

    private void init(int index) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.realtabcontent, FindAdviceSonFragment.newInstance(index), index + "");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void onClick(View v) {

        allNoSelect();

        switch (v.getId()) {
            case R.id.button1:
                placeView(0);
                break;
            case R.id.button2:
                placeView(1);
                break;
            default:
                break;
        }

        v.setSelected(true);
    }

    private void allNoSelect() {
        layoutView.findViewById(R.id.button1).setSelected(false);
        layoutView.findViewById(R.id.button2).setSelected(false);
    }

    public void placeView(int index) {
        BaseFragment fragment = (BaseFragment) getChildFragmentManager()
                .findFragmentByTag(index + "");
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if (fragment == null) {
            switch (index) {
                case 0:
                    fragment = FindAdviceSonFragment.newInstance(index);// index=
                    return;
                case 1:
                    fragment = FindAdviceSonFragment.newInstance(index);// index=
                    break;
                default:

                    return;
            }

        }
        ft.replace(R.id.realtabcontent, fragment, index + "");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

    }

}