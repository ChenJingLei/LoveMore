package com.app.cjl20.lovemore.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.app.cjl20.lovemore.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by cjl20 on 2015/12/12.
 * PROJECT_NAME by LoveMore
 */
public class HelpFragment extends Fragment implements ScreenShotable, AdapterView.OnItemClickListener, TabHost.TabContentFactory, TabHost.OnTabChangeListener {
    public static final String HELP = "help";
    protected int res;

    public static HelpFragment newInstance(int resId) {
        HelpFragment helpFragment = new HelpFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        helpFragment.setArguments(bundle);
        return helpFragment;
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
        View layout = inflater.inflate(res, container, false);
//        LinearLayout layout = (LinearLayout) inflater.inflate(R.id.layout, container, false);
        TabHost tabHost = (TabHost) layout.findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("受助方").setContent(R.id.frag1));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("志愿者").setContent(R.id.frag2));

        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(this);
        return layout;

    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onTabChanged(String tabId) {

    }

    @Override
    public View createTabContent(String tag) {
        return null;
    }
}
