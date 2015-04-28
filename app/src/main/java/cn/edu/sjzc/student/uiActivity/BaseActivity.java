package cn.edu.sjzc.student.uiActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.sjzc.student.bean.UserBean;

public class BaseActivity extends Activity {

	public static final int REQUEST_TIMEOUT = 5 * 1000;// 设置请求超时10秒钟
	public static final int SO_TIMEOUT = 10 * 1000; // 设置等待数据超时时间10秒钟
	public static int LOGIN_OK = 1;
	private static String COOKIE = "logincookie";
	public static String SID = "login";
	public static UserBean userBean = new UserBean();
	public static String baseUrl = "http://hhhccckkk3.jsp.fjjsp.net/hck/";
	public static String loginUrl = "http://hhhccckkk3.jsp.fjjsp.net/hck/login";
	public static String registerUrl = "http://hhhccckkk3.jsp.fjjsp.net/hck/register";

	// 初始化HttpClient，并设置超时
	public static HttpClient getHttpClient() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		return client;
	}

	public static void removeCookie(Context context) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		CookieSyncManager.getInstance().sync();
	}

	public static void savePreference(Context context, String key, String value) {
		SharedPreferences preference = context.getSharedPreferences("All",
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreference(Context context, String key) {

		SharedPreferences preference = context.getSharedPreferences("All",
				Context.MODE_PRIVATE);

		return preference.getString(key, "");

	}

	public static boolean isCookid(Context context) {

		// SharedPreferences preference =
		// context.getSharedPreferences(context.coo, mode)

		return false;

	}

	public static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			// 设置超时时间�?000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			// 连接设置获得数据�?
			conn.setDoInput(true);
			// 不使用缓�?
			conn.setUseCaches(true);
			// 这句可有可无，没有影�?
			// conn.connect();
			// 得到数据�?
			InputStream is = conn.getInputStream();
			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据�?
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;

	}

    public static String getMilliToDate(String time){
        Date date = new Date(Long.valueOf(time));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

}
