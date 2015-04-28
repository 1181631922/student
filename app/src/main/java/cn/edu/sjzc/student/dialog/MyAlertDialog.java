package cn.edu.sjzc.student.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class MyAlertDialog extends AlertDialog implements OnClickListener {

	public MyAlertDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	protected MyAlertDialog(Context context, boolean cancelable,
                            OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

}
