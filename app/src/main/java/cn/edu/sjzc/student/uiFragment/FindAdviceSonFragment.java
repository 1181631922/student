package cn.edu.sjzc.student.uiFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.sjzc.student.R;

public class FindAdviceSonFragment extends BaseFragment{
	
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
    private TextView textView,tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		layoutView= inflater.inflate(R.layout.fragment_advice, null);
//        initView();
//        init(getIndex());
        return layoutView;
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        init(getIndex());
    }
    public void initView(){
         tv = (TextView) layoutView.findViewById(R.id.textView111);
    }

    public void init(int i){
        if (i==0){
            tv.setText("根据课程评价");
            Log.d("---------------------------------------------------------------------------------------",1+"");
        }else if(i==1){
            tv.setText("根据教师评价");
            Log.d("---------------------------------------------------------------------------------------",2+"");
        }

//        switch (i){
//            case 1:
//                tv.setText( 1+ "");
//                Log.d("---------------------------------------------------------------------------------------",1+"");
//                break;
//            case 2:
//                tv.setText( 2+ "");
//                Log.d("---------------------------------------------------------------------------------------",2+"");
//                break;
//        }
    }
}