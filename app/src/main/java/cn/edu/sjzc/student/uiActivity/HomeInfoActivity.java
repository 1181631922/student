package cn.edu.sjzc.student.uiActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.dialog.HomeInfoDialog;

public class HomeInfoActivity extends BaseActivity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */
    WebView wv;
    ProgressDialog pd;
    Handler handler;
    private ProgressBar web_show_progress;
    private ImageButton web_show_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
        setContentView(R.layout.web_show);
        initView();
        initData();// ִ�г�ʼ������
        loadurl(wv, "http://www.sjzc.edu.cn/col/1270779355718/index.html");
    }

    private void initView() {
        this.web_show_progress = (ProgressBar) HomeInfoActivity.this.findViewById(R.id.web_show_progress);
        this.web_show_back = (ImageButton) HomeInfoActivity.this.findViewById(R.id.web_show_back);
        this.web_show_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web_show_back:
                finish();
                break;

        }
    }

    public void initData() {
        handler = new Handler() {
            public void handleMessage(Message msg) {// ����һ��Handler�����ڴ��������߳���UI��ͨѶ
                if (!Thread.currentThread().isInterrupted()) {
                    switch (msg.what) {
                        case 0:
                            web_show_progress.showContextMenu();
                            break;
                        case 1:
                            web_show_progress.setVisibility(View.GONE);
                            break;
                    }
                }
                super.handleMessage(msg);
            }
        };
        wv = (WebView) findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(0);
        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view,
                                                    final String url) {
                loadurl(view, url);
                return true;
            }// ��д�������,��webview����

        });
        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {// �����ȸı��
                if (progress == 100) {
                    handler.sendEmptyMessage(1);// ���ȫ������,���ؽ�ȶԻ���
                }
                super.onProgressChanged(view, progress);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {// ��׽���ؼ�
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            ConfirmExit();// ���˷��ؼ��Ѿ����ܷ��أ���ִ���˳�ȷ��
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ConfirmExit() {// �˳�ȷ��
        HomeInfoDialog dialog=new HomeInfoDialog(this, R.style.mystyle, R.layout.dialog_exit_main);
        dialog.show();
    }

    public void loadurl(final WebView view, final String url) {
        //自android 4.1以后web页面必须在ui的主线程进行更新，否则报错
        handler.sendEmptyMessage(0);
        view.loadUrl(url);// ������ҳ
    }


}