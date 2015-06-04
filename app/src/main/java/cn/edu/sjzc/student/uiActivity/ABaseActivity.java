package cn.edu.sjzc.student.uiActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.edu.sjzc.student.R;

public class ABaseActivity extends Activity {
    protected String ABaseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPropertyFileContent();
    }

    private void getPropertyFileContent() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getAssets().open("fanyafeng.properties");
            properties.load(inputStream);
            ABaseUrl = properties.getProperty("ServerUrl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getServerURL() {
        return ABaseUrl;
    }
}
