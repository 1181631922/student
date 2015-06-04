package cn.edu.sjzc.student.uiFragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ABaseFragment extends Fragment {
    protected String ABaseUrl;
    protected Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        getPropertyFileContent();
    }

    private void getPropertyFileContent() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getActivity().getAssets().open("fanyafeng.properties");
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
