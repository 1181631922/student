package cn.edu.sjzc.student.uiFragment;

import android.support.v4.app.Fragment;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseFragment extends Fragment implements View.OnClickListener {

    private int index;
    public static String aBaseUrl = "http://172.16.114.2:8080/st/";


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static String getMilliToDate(String time) {
        Date date = new Date(Long.valueOf(time));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    @Override
    public void onClick(View v) {

    }
}
