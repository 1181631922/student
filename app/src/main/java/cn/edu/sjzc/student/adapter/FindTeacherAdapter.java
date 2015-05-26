package cn.edu.sjzc.student.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.bean.FindTeacherBean;

public class FindTeacherAdapter extends BaseAdapter {
	
	private Context context;
	
	private List<FindTeacherBean> FindTeacherBeans;
	
	

	public FindTeacherAdapter(Context context, List<FindTeacherBean> FindTeacherBeans) {
		
		this.context = context;
		this.FindTeacherBeans = FindTeacherBeans;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return FindTeacherBeans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return FindTeacherBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View view = convertView;
		ViewHolder holder = null;
		if(view == null){
			view = LayoutInflater.from(context).inflate(R.layout.item_listview_teachet_find, null);
			holder = new ViewHolder();
			view.setTag(holder);
			holder.item_find_teacher_name = (TextView) view.findViewById(R.id.item_find_teacher_name);
			holder.item_find_teacher_phone = (TextView) view.findViewById(R.id.item_find_teacher_phone);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.item_find_teacher_name.setText(FindTeacherBeans.get(position).getFindName());
		holder.item_find_teacher_phone.setText(FindTeacherBeans.get(position).getFindPhone());
		
		return view;
	}
	
	static class ViewHolder{
		TextView item_find_teacher_name;
		TextView item_find_teacher_phone;
	}

}
