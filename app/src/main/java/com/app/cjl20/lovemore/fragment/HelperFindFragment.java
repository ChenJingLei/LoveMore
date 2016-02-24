package com.app.cjl20.lovemore.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cjl20.lovemore.R;
import com.app.cjl20.lovemore.Utils.DateTimePickDialogUtil;
import com.app.cjl20.lovemore.config.NetConfig;
import com.app.cjl20.lovemore.model.CheckStatus;
import com.app.cjl20.lovemore.model.Helper;
import com.app.cjl20.lovemore.model.User;
import com.app.cjl20.lovemore.views.SelectPicPopupWindow;
import com.cengalabs.flatui.views.FlatEditText;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cjl20 on 2015/12/13.
 * PROJECT_NAME by LoveMore
 */
public class HelperFindFragment extends Fragment implements View.OnClickListener {

    private Button submit;
    private Button timesel;
    private FlatEditText title;
    private FlatEditText origation;
    private FlatEditText address;
    private FlatEditText date;
    private FlatEditText phone;
    private ImageView picImg;
    private View view;

    private Context mContext;
    private SelectPicPopupWindow menuWindow;
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    public static Uri photoUri;

    private static ProgressDialog pd;// 等待进度圈

    private String initDateTime = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA).format(new Date());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_helper, container, false);
        submit = (Button) view.findViewById(R.id.helperb);
        timesel = (Button) view.findViewById(R.id.timesel);

        mContext = getContext();

        title = (FlatEditText) view.findViewById(R.id.editText1);
        origation = (FlatEditText) view.findViewById(R.id.editText2);
        address = (FlatEditText) view.findViewById(R.id.editText3);
        date = (FlatEditText) view.findViewById(R.id.editText4);
        phone = (FlatEditText) view.findViewById(R.id.editText5);
        picImg = (ImageView) view.findViewById(R.id.picImg);

        submit.setOnClickListener(this);
        timesel.setOnClickListener(this);
        date.setOnClickListener(this);
        picImg.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.helperb:
                if (!title.getText().toString().equals("")
                        && !origation.getText().toString().equals("")
                        && !address.getText().toString().equals("")
                        && !date.getText().toString().equals("")
                        && !phone.getText().toString().equals("")) {
                    picImg.setDrawingCacheEnabled(true);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    picImg.getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 40, baos);
                    byte[] byteArray = baos.toByteArray();
                    System.out.println("size:::" + byteArray.length);
                    picImg.setDrawingCacheEnabled(false);
                    String datetime = date.getText().toString().replace("年", "-").replace("月", "-").replace("日", "") + ":00";
                    Helper helper = new Helper(title.getText().toString(),
                            origation.getText().toString(),
                            address.getText().toString(),
                            datetime,
                            phone.getText().toString(),
                            byteArray);
                    System.out.println(helper.toString());
                    pd = ProgressDialog.show(mContext, null, "正在上传，请稍候...");
                    new HttpRequestTask(NetConfig.url + "helper/addHelper", helper).execute();
                } else {
                    Toast.makeText(getActivity(), "请输入受助信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.editText4:
            case R.id.timesel:
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        getActivity(), initDateTime);
                dateTimePicKDialog.dateTimePicKDialog(date);
                break;
            case R.id.picImg:
                menuWindow = new SelectPicPopupWindow(mContext, itemsOnClick);
                menuWindow.showAtLocation(view.findViewById(R.id.picImg),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Boolean> {

        String url;
        Helper helper;

        public HttpRequestTask(String url, Helper helper) {
            this.url = url;
            this.helper = helper;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                Boolean bool = restTemplate.postForObject(url, helper, Boolean.class);
                return bool;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                Toast.makeText(getActivity(), "上传成功！！！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "上传失败，请稍后重试！！！", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
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
    private static final String IMAGE_FILE_NAME = "photos.jpg";

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
            photoUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
            getActivity().startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(getActivity(), "内存卡不存在", Toast.LENGTH_LONG).show();
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
        getActivity().startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

}
