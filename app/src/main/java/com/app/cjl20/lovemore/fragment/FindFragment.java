package com.app.cjl20.lovemore.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by cjl20 on 2015/12/12.
 * PROJECT_NAME by LoveMore
 */
public class FindFragment extends Fragment implements ScreenShotable {
    public static final String FIND = "find";
    protected int res;

    public static FindFragment newInstance(int resId) {
        FindFragment findFragment = new FindFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        findFragment.setArguments(bundle);
        return findFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

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

