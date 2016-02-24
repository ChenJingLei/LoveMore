package com.app.cjl20.lovemore.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cjl20.lovemore.R;
import com.app.cjl20.lovemore.config.NetConfig;
import com.app.cjl20.lovemore.model.Feedback;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Handler;

/**
 * Created by cjl20 on 2016/2/25.
 * PROJECT_NAME by LoveMore
 */
public class FeedbackActivity extends Activity implements View.OnClickListener {


    private EditText fdname;
    private EditText fdtitle;
    private EditText fdcontent;

    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        fdname = (EditText) findViewById(R.id.fdnameText);
        fdcontent = (EditText) findViewById(R.id.fdcontentText);
        fdtitle = (EditText) findViewById(R.id.fdtitle);

        submit = (Button) findViewById(R.id.fdsubmit);
        submit.setOnClickListener(this);
    }

    private static ProgressDialog pd;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fdsubmit:
                if (!fdname.getText().toString().equals("") &&
                        !fdtitle.getText().toString().equals("") &&
                        !fdcontent.getText().equals("")) {
                    pd = ProgressDialog.show(FeedbackActivity.this, null, "正在反馈，请稍候...");

                    Feedback feedback = new Feedback(fdname.getText().toString(),
                            fdtitle.getText().toString(),
                            fdcontent.getText().toString());
                    FeedbackThread thread = new FeedbackThread(NetConfig.url + "feedback/addFeedback", feedback);
                    thread.start();
                }
                break;
        }
    }

    class FeedbackThread extends Thread {

        String url;
        Feedback feedback;

        public FeedbackThread(String url, Feedback feedback) {
            this.url = url;
            this.feedback = feedback;
        }

        @Override
        public void run() {
            Message msg = new Message();
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                Boolean bool = restTemplate.postForObject(url, feedback, Boolean.class);
                if (bool) msg.obj = 1;
                else throw new Exception("反馈失败！！！");
            } catch (Exception e) {
                msg.obj = e.getMessage();
            }
            handler.sendMessage(msg);// 执行耗时的方法之后发送消给handler
        }
    }

    android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            pd.dismiss();
            if (msg.obj.equals(1)) {
                Toast.makeText(getApplicationContext(), "反馈成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });
}
