package com.app.cjl20.lovemore.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.app.cjl20.lovemore.R;
import com.yalantis.euclid.library.EuclidActivity;
import com.yalantis.euclid.library.EuclidListAdapter;

import java.util.ArrayList;
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
//        Bundle bundle = new Bundle();
//        bundle.putInt(Integer.class.getName(), resId);
//        volunteerFragment.setArguments(bundle);
        return volunteerFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VolunteerFragment.this, "报名成功！！！", Toast.LENGTH_SHORT).show();
            }
        });



        mTitle.setText("志愿者活动");
    }


//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        res = getArguments().getInt(Integer.class.getName());
//    }

    @Override
    protected BaseAdapter getAdapter() {
        Map<String, Object> profileMap;
        List<Map<String, Object>> profilesList = new ArrayList<>();

        int[] avatars = {
                R.drawable.anastasia,
                R.drawable.andriy,
                R.drawable.dmitriy,
                R.drawable.dmitry_96,
                R.drawable.ed,
                R.drawable.illya,
                R.drawable.kirill,
                R.drawable.konstantin,
                R.drawable.oleksii,
                R.drawable.pavel,
                R.drawable.vadim};
        String[] names = getResources().getStringArray(R.array.array_names);

        for (int i = 0; i < avatars.length; i++) {
            profileMap = new HashMap<>();
            profileMap.put(EuclidListAdapter.KEY_AVATAR, avatars[i]);
            profileMap.put(EuclidListAdapter.KEY_NAME, names[i]);
            profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_SHORT, getString(R.string.lorem_ipsum_short));
            profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_FULL, getString(R.string.lorem_ipsum_long));
            profilesList.add(profileMap);
        }

        return new EuclidListAdapter(this, R.layout.list_item, profilesList);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(res, container, false);
//        return rootView;
//    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
