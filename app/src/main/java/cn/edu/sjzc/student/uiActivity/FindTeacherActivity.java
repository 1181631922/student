package cn.edu.sjzc.student.uiActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.adapter.SearchAdapter;
import cn.edu.sjzc.student.bean.SearchBean;

public class FindTeacherActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton findteacher_back;
    private ListView searchTeacherListview;
    private SearchAdapter searchAdapter;
    private List<SearchBean> searchBeans;
    private List<Map<String, Object>> searchList = new ArrayList<Map<String, Object>>();
    private Button find_teacher_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_find_teacher);
        initView();
//        initData();
    }

    private void initData() {
        ListView searchTeacherListview = (ListView) FindTeacherActivity.this.findViewById(R.id.find_teacher_listview);

        searchBeans = new ArrayList<SearchBean>();
        SearchBean[] findTeacherArray = new SearchBean[]{
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856"),
                new SearchBean("老师1", "13052658856")};

//        for (int i = 0; i < FindProjectArray.length; i++) {
//            FindProjectBean findProjectBeans = new FindProjectBean(ptitle);
//            String ptitle = FindProjectArray[i].getFindProjectTitle();
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("p_title", ptitle);
//            findProjectList.add(map);
//        }

        Arrays.sort(findTeacherArray);
        searchBeans = Arrays.asList(findTeacherArray);
        searchAdapter = new SearchAdapter(FindTeacherActivity.this,searchBeans);

        searchTeacherListview.setAdapter(searchAdapter);

        searchTeacherListview.setOnItemClickListener(new searchInfoOnItemClickListener());
    }

    public class searchInfoOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent it_searchinfo=new Intent(FindTeacherActivity.this,SearchInfoActivity.class);
            startActivity(it_searchinfo);
        }
    }

    private void initView() {
        this.findteacher_back = (ImageButton) FindTeacherActivity.this.findViewById(R.id.findteacher_back);
        this.findteacher_back.setOnClickListener(this);
        this.find_teacher_but=(Button)FindTeacherActivity.this.findViewById(R.id.find_teacher_but);
        this.find_teacher_but.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.findteacher_back:
                finish();
                break;
            case R.id.find_teacher_but:
                initData();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_teacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
