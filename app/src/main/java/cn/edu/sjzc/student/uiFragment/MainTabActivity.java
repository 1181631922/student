package cn.edu.sjzc.student.uiFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.dialog.MainExitDialog;
import cn.edu.sjzc.student.uiActivity.RetroactionActivity;

/**
 */
public class MainTabActivity extends FragmentActivity implements View.OnClickListener{
    private FragmentTabHost mTabHost;

    private LayoutInflater layoutInflater;
    private MainExitDialog dialog1;
    private Dialog dialog;

    private Class fragmentArray[] = {HomePageFragment.class, FindEvaluationFragment.class, FindAdviceFragment.class, FindTeacherFragment.class, PersonalCenterFragment.class};

    private int mImageViewArray[] = {R.drawable.tab_home_btn, R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn,
            R.drawable.tab_square_btn, R.drawable.tab_more_btn};

    private String mTextviewArray[] = {"首页", "查看评估", "评价老师", "查找教师", "个人信息"};
    public static MainTabActivity instance = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_tab_layout);
        instance = this;
        initView();
    }

    /**
     */
    private void initView() {
        layoutInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    /**
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                ConfirmExit();
                break;
            case KeyEvent.KEYCODE_MENU:
                showDialog();
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void ConfirmExit() {// �˳�ȷ��
        MainExitDialog dialog1 = new MainExitDialog(this, R.style.mystyle, R.layout.dialog_exit_main);
        dialog1.show();
    }

    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_exit_choose,
                null);
        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 此处调用空指针异常，因为是activity现消除的
         */
//            dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_tab_retroaction:
                Intent it_retroaction = new Intent(MainTabActivity.this, RetroactionActivity.class);
                startActivity(it_retroaction);
                dialog.dismiss();
            break;
            case R.id.main_tab_exit:
                finish();
                dialog.dismiss();
            break;
            case R.id.main_tab_cancel:
                dialog.dismiss();
            break;
        }
    }
}
