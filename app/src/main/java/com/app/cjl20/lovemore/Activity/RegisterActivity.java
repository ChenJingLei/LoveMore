package com.app.cjl20.lovemore.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cjl20.lovemore.R;
import com.app.cjl20.lovemore.config.NetConfig;
import com.app.cjl20.lovemore.views.SelectPicPopupWindow;
import com.app.cjl20.lovemore.Utils.FileUtil;
import com.app.cjl20.lovemore.model.CheckStatus;
import com.app.cjl20.lovemore.model.User;
import com.app.cjl20.lovemore.views.CircleImg;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by cjl on 2015/2/24.
 */
public class RegisterActivity extends Activity {

    private Button backBtn = null;
    private Context mContext;
    private CircleImg avatarImg;// 头像图片
    private SelectPicPopupWindow menuWindow; // 自定义的头像编辑弹出框
    // 上传服务器的路径【一般不硬编码到程序中】

    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";// 头像文件名称
    private String urlpath;// 图片本地路径
    private static ProgressDialog pd;// 等待进度圈
    private static final int REQUESTCODE_PICK = 0;        // 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;        // 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2;    //

    private Button check;//发送验证码按钮
    private Button register; // 注册按钮

    private EditText phoneText;
    private EditText verificationText;
    private EditText rg_username_Text;
    private EditText rg_password_Text;

    private String checkcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = RegisterActivity.this;

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_resiger);//自定义ActionBar布局
        backBtn = (Button) findViewById(R.id.back);
        backBtn.setOnClickListener(new ButtonListener());

        avatarImg = (CircleImg) findViewById(R.id.avatarImg);
        avatarImg.setOnClickListener(new ButtonListener());

        check = (Button) findViewById(R.id.button);
        check.setOnClickListener(new ButtonListener());

        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new ButtonListener());

        phoneText = (EditText) findViewById(R.id.phoneText);
        verificationText = (EditText) findViewById(R.id.verificationText);
        rg_username_Text = (EditText) findViewById(R.id.rg_username_Text);
        rg_password_Text = (EditText) findViewById(R.id.rg_password_Text);
    }


    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.avatarImg:
                    menuWindow = new SelectPicPopupWindow(RegisterActivity.this, itemsOnClick);
                    menuWindow.showAtLocation(findViewById(R.id.mainLayout),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
                case R.id.back:
                    finish();
                    break;
                case R.id.button:
                    User user = new User();
                    if (phoneText.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    } else {
                        user.setPhone(phoneText.getText().toString());
                        pd = ProgressDialog.show(mContext, null, "正在获取验证码，请稍候...");
                        new HttpRequestTask(NetConfig.url + "user/check", user).execute();
                    }
                    break;
                case R.id.registerButton:
                    if (verificationText.getText().toString().equals(checkcode)) {
                        if (!rg_username_Text.getText().toString().isEmpty() && !rg_password_Text.getText().toString().isEmpty()) {
                            avatarImg.setDrawingCacheEnabled(true);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            avatarImg.getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 40, baos);
                            byte[] byteArray = baos.toByteArray();
                            System.out.println(byteArray.length);
                            avatarImg.setDrawingCacheEnabled(false);
                            User register = new User(rg_username_Text.getText().toString(),
                                    rg_password_Text.getText().toString(),
                                    phoneText.getText().toString(),
                                    byteArray);
                            pd = ProgressDialog.show(mContext, null, "正在注册，请稍候...");
                            new HttpRequestTask(NetConfig.url + "user/register", register).execute();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, CheckStatus> {

        String url;
        User user;

        public HttpRequestTask(String url, User user) {
            this.url = url;
            this.user = user;
        }

        @Override
        protected CheckStatus doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                CheckStatus checkStatus = restTemplate.postForObject(url, user, CheckStatus.class);
                return checkStatus;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new CheckStatus(e.getMessage(), "");
            }
        }

        @Override
        protected void onPostExecute(CheckStatus checkStatus) {
            System.out.println(checkStatus.toString());
            if (!checkStatus.getStatus().equals("successful")) {
                Toast.makeText(getApplicationContext(), checkStatus.getStatus(), Toast.LENGTH_SHORT).show();
            } else if (checkStatus.getResult().equals("5001")) {
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                finish();
            } else {
                checkcode = checkStatus.getResult();
            }
            pd.dismiss();
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.takePhotoBtn:
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    break;
                // 相册选择图片
                case R.id.pickPhotoBtn:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }
                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            urlpath = FileUtil.saveFile(mContext, "temphead.jpg", photo);
            avatarImg.setImageDrawable(drawable);

        }
    }


}
