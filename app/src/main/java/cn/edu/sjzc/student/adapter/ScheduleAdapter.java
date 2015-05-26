package cn.edu.sjzc.student.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.bean.ScheduleBean;

/**
 * Created by 亚风 on 2015/05/26/0026.
 */
public class ScheduleAdapter extends BaseAdapter{
    private Context context;
    private List<ScheduleBean> scheduleBeanList;

    public ScheduleAdapter(Context context, List<ScheduleBean> scheduleBeanList) {
        this.context = context;
        this.scheduleBeanList = scheduleBeanList;
    }

    @Override
    public int getCount() {
        return scheduleBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_listview_advice, null);
            holder = new ViewHolder();
            view.setTag(holder);
            holder.item_advice_id = (TextView) view.findViewById(R.id.advice_id);
            holder.item_advice_course = (TextView) view.findViewById(R.id.advice_course);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_advice_id.setText(scheduleBeanList.get(position).getId());
        holder.item_advice_course.setText(scheduleBeanList.get(position).getTitle());

        return view;
    }

    static class ViewHolder{
        TextView item_advice_id;
        TextView item_advice_course;
    }
}
