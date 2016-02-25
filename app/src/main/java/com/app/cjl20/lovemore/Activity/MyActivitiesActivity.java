package com.app.cjl20.lovemore.Activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.app.cjl20.lovemore.R;
import com.app.cjl20.lovemore.config.NetConfig;
import com.app.cjl20.lovemore.model.MyActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by cjl20 on 2016/2/25.
 * PROJECT_NAME by LoveMore
 */
public class MyActivitiesActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myactivity);
        pd = ProgressDialog.show(MyActivitiesActivity.this, null, "正在获取内容，请稍候...");
        GetMyActivityInfoThread thread = new GetMyActivityInfoThread(NetConfig.url + "activity/getActivity/cjl");
        thread.start();
    }

    private ProgressDialog pd;

    class GetMyActivityInfoThread extends Thread {

        String url;

        public GetMyActivityInfoThread(String url) {
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
                List<MyActivity> list = gson.fromJson(json, new TypeToken<List<MyActivity>>() {
                }.getType());
                System.out.println(list.toString());
                msg.obj = list;
                msg.what = 1;
            } catch (Exception e) {
                msg.obj = e.getMessage();
                msg.what = 0;
            }
            handler.sendMessage(msg);
        }
    }

    List<MyActivity> myActivityList = new ArrayList<>();
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            pd.dismiss();
            switch (msg.what) {
                case 1:
                    myActivityList.addAll((List<MyActivity>) msg.obj);
                    List<HashMap<String, String>> mList = new ArrayList<>();
                    for (int i = 0; i < myActivityList.size(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("name", myActivityList.get(i).getName());
                        map.put("context", myActivityList.get(i).getContent());
                        mList.add(map);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), mList,
                            android.R.layout.simple_list_item_2, new String[]{"name", "context"},
                            new int[]{android.R.id.text1, android.R.id.text2});
                    setListAdapter(adapter);
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    });
}
