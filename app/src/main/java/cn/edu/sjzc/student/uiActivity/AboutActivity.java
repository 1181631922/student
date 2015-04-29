package cn.edu.sjzc.student.uiActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.dialog.AboutDialog;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class AboutActivity extends BaseActivity implements View.OnClickListener{
    private WebView wv;
    private ProgressDialog pd;
    Handler handler;
    private ProgressBar web_show_progress;
    private ImageButton web_show_back,about_show_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about);
        initView();
        initData();// ִ�г�ʼ������
        loadurl(wv, "http://eqxiu.com/s/4NdsxskD");
    }

    private void initView() {
        this.web_show_progress = (ProgressBar) AboutActivity.this.findViewById(R.id.about_show_progress);
        this.web_show_back = (ImageButton) AboutActivity.this.findViewById(R.id.about_show_back);
        this.web_show_back.setOnClickListener(this);
        this.about_show_share=(ImageButton)AboutActivity.this.findViewById(R.id.about_show_share);
        this.about_show_share.setOnClickListener(this);
    }

    public void initData() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
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
        wv = (WebView) findViewById(R.id.about_wv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(0);
        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view,
                                                    final String url) {
                loadurl(view, url);
                return true;
            }

        });
        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    handler.sendEmptyMessage(1);
                }
                super.onProgressChanged(view, progress);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            ConfirmExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ConfirmExit() {
        AboutDialog dialog=new AboutDialog(this, R.style.mystyle, R.layout.dialog_about);
        dialog.show();
    }

    public void loadurl(final WebView view, final String url) {
        //自android 4.1以后web页面必须在ui的主线程进行更新，否则报错
        handler.sendEmptyMessage(0);
        view.loadUrl(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_show_back:
                finish();
                break;
            case R.id.about_show_share:
                showShare();
                break;

        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
        // oks.setNotification(R.drawable.ic_launcher,
        // getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://eqxiu.com/s/4NdsxskD");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("樊亚风测试分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/fanyafeng/fanyafeng.png");// 确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://eqxiu.com/s/4NdsxskD");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("樊亚风测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://eqxiu.com/s/4NdsxskD");

        // 启动分享GUI
        oks.show(this);
    }
}
