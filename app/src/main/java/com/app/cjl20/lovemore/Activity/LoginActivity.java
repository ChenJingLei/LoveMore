package com.app.cjl20.lovemore.Activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.app.cjl20.lovemore.R;
import com.app.cjl20.lovemore.config.NetConfig;
import com.app.cjl20.lovemore.model.User;
import com.app.cjl20.lovemore.screenmanage.SysApplication;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.UnknownHostException;

public class LoginActivity extends ActionBarActivity {

    private EditText nameText = null;
    private EditText pwdText = null;
    private Button loginBtn = null;
    private ProgressDialog progressDialog = null;
    private Handler pBhandler = null;
    private TextView forgetTV = null;
    private TextView registerTV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        SysApplication.getInstance().finish();
        SysApplication.getInstance().addActivity(this);
        nameText = (EditText) findViewById(R.id.nameText);
        pwdText = (EditText) findViewById(R.id.pwdText);
        loginBtn = (Button) findViewById(R.id.submitButton);
        forgetTV = (TextView) findViewById(R.id.forgetpassword);
        registerTV = (TextView) findViewById(R.id.register);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("正在登陆");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);

        pBhandler = new PbHandler();
        loginBtn.setOnClickListener(new LoginButtonListener());
        forgetTV.setOnClickListener(new LoginViewListener());
        registerTV.setOnClickListener(new LoginViewListener());
//        /*
//        * 测试程序使用
//        */
//        nameText.setText("cjl");
//        pwdText.setText("cjl");

        loginBtn.callOnClick();

    }

    //此handler处理登陆信息
    class PbHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            //关闭ProgressDialog
            System.out.println("msg:" + msg.obj);
            progressDialog.dismiss();
            if (msg.obj.getClass() == String.class) {
                setToast(_setLoginInfo((String) msg.obj));
            } else if (msg.obj.getClass() == UnknownHostException.class) {
                Toast.makeText(getApplicationContext(), "提示：网络不可用，或者服务器正在维护中，请稍后再试", Toast.LENGTH_SHORT).show();
            } else if (msg.obj.getClass() == IOException.class) {
                Toast.makeText(getApplicationContext(), "提示：网络解析错误", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "提示：未知错误，请稍后重试", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    }

    public void setToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }


    //登陆按钮的监听器
    class LoginButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = nameText.getText().toString();
            String pwd = pwdText.getText().toString();
            System.out.println("name-->" + name + "--->pwd" + pwd);
            if ((name.isEmpty() && pwd.isEmpty())) {
                setToast("账号密码为空");
            } else {
                progressDialog.show();
                LoginThread loginThread = new LoginThread(name, pwd);
                loginThread.start();
            }
        }
    }

    //此线程处理登陆操作
    class LoginThread extends Thread {

        String name;
        String pwd;

        public LoginThread(String name, String pwd) {
            this.name = name;
            this.pwd = pwd;
        }

        @Override
        public void run() {

            Message msg = pBhandler.obtainMessage();
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                User user = new User(name, pwd);
                final String url = NetConfig.url + "user/login";
                System.out.println(user.toString());
                user = restTemplate.postForObject(url, user, User.class);
                if (user != null) {
                    msg.obj = "success";
                } else {
                    msg.obj = "fail";
                }
            } catch (Exception e) {
                e.printStackTrace();
                msg.obj = e;
            }
            pBhandler.sendMessage(msg);
            super.run();
        }
    }

    //此函数设置登录成功后的跳转或登陆失败刷新UI
    private String _setLoginInfo(String login) {
        String Info = null;
        if (login.equals("success")) {
            System.out.println("loginer：" + nameText.getText().toString() + pwdText.getText().toString());
            Info = "登陆成功";
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Info = "账号或密码错误";
        }
        return Info;
    }

    class LoginViewListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.forgetpassword:
                    break;
                case R.id.register:
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.register");
                    startActivity(intent);
                    break;
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            Builder isExit = new Builder(this);
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗");
            // 添加选择按钮并注册监听
            isExit.setPositiveButton("确认", listener);
            isExit.setNegativeButton("取消", null);
            // 显示对话框
            isExit.show();
        }
        return false;
    }

    /**
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    SysApplication.getInstance().exit();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };
}
