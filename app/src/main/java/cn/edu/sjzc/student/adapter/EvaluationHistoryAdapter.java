package cn.edu.sjzc.student.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.bean.EvaluationHistoryBean;
import cn.edu.sjzc.student.bean.ScheduleBean;

/**
 * Created by 亚风 on 2015/05/26/0026.
 */
public class EvaluationHistoryAdapter extends BaseAdapter{
    private Context context;
    private List<EvaluationHistoryBean> evaluationHistoryBeanList;

    public EvaluationHistoryAdapter(Context context, List<EvaluationHistoryBean> evaluationHistoryBeanList) {
        this.context = context;
        this.evaluationHistoryBeanList = evaluationHistoryBeanList;
    }

    @Override
    public int getCount() {
        return evaluationHistoryBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return evaluationHistoryBeanList.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_listview_evaluation_histroy, null);
            holder = new ViewHolder();
            view.setTag(holder);
            holder.item_history = (TextView) view.findViewById(R.id.evaluation_history_time);
            holder.item_teacher = (TextView) view.findViewById(R.id.evaluation_history_teacher);
            holder.item_course = (TextView) view.findViewById(R.id.evaluation_history_course);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_history.setText(evaluationHistoryBeanList.get(position).getHistory());
        holder.item_teacher.setText(evaluationHistoryBeanList.get(position).getTeacher());
        holder.item_course.setText(evaluationHistoryBeanList.get(position).getCourse());

        return view;
    }

    static class ViewHolder{
        TextView item_history;
        TextView item_teacher;
        TextView item_course;
    }
}
