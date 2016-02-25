package com.app.cjl20.lovemore.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.app.cjl20.lovemore.config.NetConfig;
import com.app.cjl20.lovemore.model.Evaluate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by cjl20 on 2015/12/12.
 * PROJECT_NAME by LoveMore
 */
public class RecordFragment extends ListFragment implements ScreenShotable {
    public static final String RECORD = "record";
    protected int res;

    public static RecordFragment newInstance(int resId) {
        RecordFragment recordFragment = new RecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        recordFragment.setArguments(bundle);
        return recordFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private AnimalListAdapter adapter = null;
    List<Evaluate> evlist = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());

        evlist = new ArrayList<Evaluate>();
//        list.add(new Evaluate("aaasdaaa", "bbasdasbb", new byte[]{1, 1}));
//        list.add(new Evaluate("cccc", "dddd", new byte[]{1, 1}));
//        list.add(new Evaluate("eeee", "ffff", new byte[]{1, 1}));
//        list.add(new Evaluate("gggg", "hhhh", new byte[]{1, 1}));

        pd = ProgressDialog.show(getActivity(), null, "正在获取内容，请稍候...");
        Thread t = new GetRecordInfoThread(NetConfig.url + "evaluate/getAll");
        t.start();
    }

    private ProgressDialog pd;

    class GetRecordInfoThread extends Thread {

        String url;

        public GetRecordInfoThread(String url) {
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
                List<Evaluate> list = gson.fromJson(json, new TypeToken<List<Evaluate>>() {
                }.getType());
                System.out.println(list.toString());
                msg.obj = list;
                msg.what = 1;
//                evlist.addAll(list);
            } catch (Exception e) {
                msg.obj = e.getMessage();
                msg.what = 0;
            }
            handler.sendMessage(msg);
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            pd.dismiss();
            switch (msg.what) {
                case 1:
                    evlist.addAll((List<Evaluate>) msg.obj);
                    adapter = new AnimalListAdapter(getActivity(), evlist);
                    setListAdapter(adapter);
                    break;
                case 0:
                    Toast.makeText(getActivity(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
            HandlerFinish = true;
            return true;
        }
    });

    private static boolean HandlerFinish = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(res, container, false);
        return rootView;
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
