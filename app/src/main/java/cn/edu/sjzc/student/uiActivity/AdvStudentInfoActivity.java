package cn.edu.sjzc.student.uiActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.edu.sjzc.student.R;

public class AdvStudentInfoActivity extends BaseActivity implements OnClickListener {

    private ImageButton studentinfo_back;
    private TextView student_info_name, student_info_phone;
    private Button adv_message_to, adv_phone_to;
    private String student_name, student_phone;
    private EditText adv_info_message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.activity_adv_student_info);

        initView();
        initData();
    }

    private void initData() {

        Intent it = this.getIntent();

        student_name = it.getStringExtra("student_name");
        student_phone = it.getStringExtra("student_phone");

        this.student_info_name.setText(student_name);
        this.student_info_phone.setText(student_phone);
    }

    private void initView() {
        ImageButton changepassword_back = (ImageButton) this.findViewById(R.id.studentinfo_back);
        changepassword_back.setOnClickListener(this);
        this.adv_message_to = (Button) this.findViewById(R.id.adv_message_to);
        this.adv_message_to.setOnClickListener(this);
        this.adv_phone_to = (Button) this.findViewById(R.id.adv_phone_to);
        this.adv_phone_to.setOnClickListener(this);

        this.student_info_name = (TextView) super.findViewById(R.id.adv_info_name);
        this.student_info_phone = (TextView) super.findViewById(R.id.adv_info_phone);

        this.adv_info_message = (EditText) AdvStudentInfoActivity.this.findViewById(R.id.adv_info_message);


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.studentinfo_back:
                AdvStudentInfoActivity.this.finish();
                break;
            case R.id.adv_message_to:
                String info_message = adv_info_message.getText().toString();
                sendSMS(student_phone, info_message);
                break;
            case R.id.adv_phone_to:
                phoneBody(student_phone);
                break;

            default:
                break;
        }

    }

    private void phoneBody(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        AdvStudentInfoActivity.this.startActivity(intent);
    }

    /**
     * 此方法可以传两个参数
     *
     * @param number 号码
     * @param detail 内容
     */
    private void sendSMS(String number, String smsBody)

    {

        Uri smsToUri = Uri.parse("smsto:" + number);

        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

        intent.putExtra("sms_body", smsBody);

        startActivity(intent);

    }

}