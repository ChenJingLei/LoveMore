package com.app.cjl20.lovemore.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cjl20.lovemore.R;
import com.app.cjl20.lovemore.config.NetConfig;
import com.app.cjl20.lovemore.model.Evaluate;
import com.app.cjl20.lovemore.views.SelectPicPopupWindow;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;

public class AddRecordActivity extends Activity implements OnClickListener {
    private Context mContext;
    private Button backBtn;
    private Button funBtn;
    private TextView titleTxt;
    private ImageView picImg;
    private SelectPicPopupWindow menuWindow; // 自定义的头像编辑弹出框

    private EditText name;
    private EditText content;

    private Uri photoUri;
    /**
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /**
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    /**
     * 获取到的图片路径
     */
    private String picPath = "";
    private static ProgressDialog pd;
    private String recordUrl = NetConfig.url + "evaluate/addVolunteer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_upload);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_titlebar);

        mContext = AddRecordActivity.this;

        initViews();
    }

    /**
     * 初始化页面控件
     */
    private void initViews() {
        name = (EditText) findViewById(R.id.upnameText);
        content = (EditText) findViewById(R.id.upcontentText);

        backBtn = (Button) findViewById(R.id.backBtn);
        funBtn = (Button) findViewById(R.id.funBtn);
        titleTxt = (TextView) findViewById(R.id.titleTxt);
        picImg = (ImageView) findViewById(R.id.picImg);
        backBtn.setText("返回");
        funBtn.setText("发布");
        titleTxt.setText("活动记录");
        backBtn.setOnClickListener(this);
        funBtn.setOnClickListener(this);
        picImg.setOnClickListener(this);
    }

    private Evaluate evaluate;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:// 返回
            case R.id.funBtn:// 发布【没有处理】
                if (!name.getText().toString().equals("") &&
                        !content.getText().toString().equals("")) {
                    pd = ProgressDialog.show(mContext, null, "正在发布记录，请稍候...");
                    picImg.setDrawingCacheEnabled(true);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    picImg.getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] byteArray = baos.toByteArray();
                    System.out.println("size:::" + byteArray.length);
                    picImg.setDrawingCacheEnabled(false);
                    evaluate = new Evaluate(name.getText().toString(), content.getText().toString(), byteArray);
                    new Thread(uploadImageRunnable).start();
                } else {
                    Toast.makeText(getApplicationContext(), "请输入发布信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.picImg:// 添加图片点击事件
                // 从页面底部弹出一个窗体，选择拍照还是从相册选择已有图片
                menuWindow = new SelectPicPopupWindow(mContext, itemsOnClick);
                menuWindow.showAtLocation(findViewById(R.id.uploadLayout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            default:
                break;
        }
    }

    //为弹出窗口实现监听类
    private OnClickListener itemsOnClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // 隐藏弹出窗口
            menuWindow.dismiss();

            switch (v.getId()) {
                case R.id.takePhotoBtn:// 拍照
                    takePhoto();
                    break;
                case R.id.pickPhotoBtn:// 相册选择图片
                    pickPhoto();
                    break;
                case R.id.cancelBtn:// 取消
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent();
        // 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 点击取消按钮
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        // 可以使用同一个方法，这里分开写为了防止以后扩展不同的需求
        switch (requestCode) {
            case SELECT_PIC_BY_PICK_PHOTO:// 如果是直接从相册获取
                doPhoto(requestCode, data);
                break;
            case SELECT_PIC_BY_TACK_PHOTO:// 如果是调用相机拍照时
                doPhoto(requestCode, data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {

        // 从相册取图片，有些手机有异常情况，请注意
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
            if (data == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if (photoUri == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }

        String[] pojo = {MediaColumns.DATA};
        // The method managedQuery() from the type Activity is deprecated
        //Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        Cursor cursor = mContext.getContentResolver().query(photoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);

            // 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
            if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                cursor.close();
            }
        }

        // 如果图片符合要求将其上传到服务器
        if (picPath != null && (picPath.endsWith(".png") ||
                picPath.endsWith(".PNG") ||
                picPath.endsWith(".jpg") ||
                picPath.endsWith(".JPG"))) {


            BitmapFactory.Options option = new BitmapFactory.Options();
            // 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
            option.inSampleSize = 20;
            // 根据图片的SDCard路径读出Bitmap
            Bitmap bm = BitmapFactory.decodeFile(picPath, option);
            // 显示在图片控件上
            picImg.setImageBitmap(bm);

        } else {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }

    }

    Runnable uploadImageRunnable = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                Boolean bool = restTemplate.postForObject(recordUrl, evaluate, Boolean.class);
                if (bool) msg.obj = 1;
                else throw new Exception("发布失败！！！");
            } catch (Exception e) {
                msg.obj = e.getMessage();
            }
            handler.sendMessage(msg);// 执行耗时的方法之后发送消给handler
        }
    };

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            pd.dismiss();
            if (msg.obj.equals(1)) {
                Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });
}
