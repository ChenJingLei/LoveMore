package com.app.cjl20.lovemore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.cjl20.lovemore.R;

/**
 * Created by cjl20 on 2015/12/13.
 * PROJECT_NAME by LoveMore
 */
public class HelperFindFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_helper, container, false);
        return view;
    }
}
