package com.app.cjl20.lovemore.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.app.cjl20.lovemore.R;
import com.app.cjl20.lovemore.config.NetConfig;
import com.app.cjl20.lovemore.model.Enroll;
import com.app.cjl20.lovemore.model.Volunteer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yalantis.euclid.library.EuclidActivity;
import com.yalantis.euclid.library.EuclidListAdapter;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by cjl20 on 2015/12/12.
 * PROJECT_NAME by LoveMore
 */
public class VolunteerFragment extends EuclidActivity implements ScreenShotable {
    public static final String VOLUNTEER = "volunteer";
    protected int res;

    public static VolunteerFragment newInstance(int resId) {
        VolunteerFragment volunteerFragment = new VolunteerFragment();
        return volunteerFragment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Volunteer volunteer = vollist.get(mItemPosition);
                System.out.println("选中的item-->" + volunteer.toString());
                Enroll enroll = new Enroll(NetConfig.loginer.getUsername(), volunteer.getId());
                pd = ProgressDialog.show(VolunteerFragment.this, null, "正在报名，请稍候...");
                new HttpRequestTask(NetConfig.url + "activity/enroll", enroll).execute();
//                Toast.makeText(VolunteerFragment.this, "报名成功！！！", Toast.LENGTH_SHORT).show();
            }
        });
        mTitle.setText("志愿者活动");

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        String url;
        Enroll enroll;

        public HttpRequestTask(String url, Enroll enroll) {
            this.url = url;
            this.enroll = enroll;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                Boolean bool = restTemplate.postForObject(url, enroll, Boolean.class);
                String str = "报名成功";
                if (!bool) {
                    str = "不可重复报名";
                }
                return str;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "报名失败，原因：" + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String str) {
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

    private ProgressDialog pd;

    class GetVolInfoThread extends Thread {

        String url;

        public GetVolInfoThread(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            Message msg = new Message();
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                String json = restTemplate.getForObject(url, String.class);
                Gson gson = new Gson();
                List<Volunteer> list = gson.fromJson(json, new TypeToken<List<Volunteer>>() {
                }.getType());
                System.out.println(list.toString());
                msg.obj = list;
                msg.what = 1;
                vollist.addAll(list);
            } catch (Exception e) {
                msg.obj = e.getMessage();
                msg.what = 0;
            }
            handler.sendMessage(msg);
        }
    }

    List<Volunteer> vollist = new ArrayList<>();
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            pd.dismiss();
            switch (msg.what) {
                case 1:
//                    vollist.addAll((List<Volunteer>) msg.obj);

                    System.out.println(vollist.size());
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
            HandlerFinish = true;
            return true;
        }
    });
    private static boolean HandlerFinish = false;

    @Override
    protected BaseAdapter getAdapter() {
        pd = ProgressDialog.show(VolunteerFragment.this, null, "正在获取内容，请稍候...");
        Map<String, Object> profileMap;
        List<Map<String, Object>> profilesList = new ArrayList<>();
//        Volunteer vol = new Volunteer("aaaaa", "bbbb", "cccc", "dddd", "eeee", new byte[]{1, 2});
//        vollist.add(vol);
//        if (vollist != null) {
        try {
            GetVolInfoThread thread = new GetVolInfoThread(NetConfig.url + "volunteer/getAll");
            thread.start();
            thread.join();
            int size = vollist.size();
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + size);
            String[] names = new String[size];
            String[] short_content = new String[size];
            String[] long_content = new String[size];
            String[] avatarsImg = new String[size];
//            int[] avatars = {
//                    R.drawable.anastasia,
//                    R.drawable.andriy,
//                    R.drawable.dmitriy,
//                    R.drawable.dmitry_96,
//                    R.drawable.ed,
//                    R.drawable.illya,
//                    R.drawable.kirill,
//                    R.drawable.konstantin,
//                    R.drawable.oleksii,
//                    R.drawable.pavel,
//                    R.drawable.vadim};

            for (int i = 0; i < size; i++) {
                Volunteer v = vollist.get(i);
                names[i] = v.getPrincipal();
                short_content[i] = v.getTitle();
                long_content[i] = v.getMember();
                String folder = Environment.getExternalStorageDirectory() + "/lovemore/volunteer";
                String filename = new Date().getTime() + "v" + i + ".jpg";
                String path = folder + "/" + filename;
                File file = new File(path);
                if (!file.exists() && !file.isDirectory()) {
                    System.out.println("aaaaaaaaaaaaaaaaaaaa");
                    file.createNewFile();
                }


                FileOutputStream fout = new FileOutputStream(path);
                fout.write(v.getImage(), 0, v.getImage().length);
                fout.close();
                avatarsImg[i] = path;
            }
            for (int i = 0; i < size; i++) {
                profileMap = new HashMap<>();
                profileMap.put(EuclidListAdapter.KEY_AVATAR, avatarsImg[i]);
                profileMap.put(EuclidListAdapter.KEY_NAME, names[i]);
                profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_SHORT, short_content[i]);
                profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_FULL, long_content[i]);
                profilesList.add(profileMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
//        int[] avatars = {
//                R.drawable.anastasia,
//                R.drawable.andriy,
//                R.drawable.dmitriy,
//                R.drawable.dmitry_96,
//                R.drawable.ed,
//                R.drawable.illya,
//                R.drawable.kirill,
//                R.drawable.konstantin,
//                R.drawable.oleksii,
//                R.drawable.pavel,
//                R.drawable.vadim};
//        String[] names = getResources().getStringArray(R.array.array_names);


        return new EuclidListAdapter(this, R.layout.list_item, profilesList);
    }


    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
