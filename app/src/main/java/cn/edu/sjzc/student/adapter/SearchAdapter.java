package cn.edu.sjzc.student.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.bean.SearchBean;

public class SearchAdapter extends BaseAdapter {

	private Context context;

	private List<SearchBean> searchBeans;


    public SearchAdapter(Context context, List<SearchBean> searchBeans) {
        this.context = context;
        this.searchBeans = searchBeans;
    }

    @Override
	public int getCount() {
		// TODO Auto-generated method stub
		return searchBeans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return searchBeans.get(position);
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
			
			view = LayoutInflater.from(context).inflate(R.layout.item_listview_searche_teacher, null);
			
			holder = new ViewHolder();
			view.setTag(holder);
			
			holder.searche_name = (TextView) view.findViewById(R.id.searche_name);
			holder.searche_phone = (TextView) view.findViewById(R.id.searche_phone);
			
			
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.searche_name.setText(searchBeans.get(position).getSearchName());
		holder.searche_phone.setText(searchBeans.get(position).getSearchPhone());
		
		return view;
	}
	
	static class ViewHolder{
		TextView searche_name;
		TextView searche_phone;
	}

}
