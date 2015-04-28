package cn.edu.sjzc.student.broadcasterceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by fanyafeng on 2015/4/15/0015.
 */
public class SoundBroadCasteReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
//switch case可以接收，并进行相应的操作
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }
}
