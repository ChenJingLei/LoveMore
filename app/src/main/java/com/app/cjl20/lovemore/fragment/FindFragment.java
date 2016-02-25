package com.app.cjl20.lovemore.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.cjl20.lovemore.Activity.AddRecordActivity;
import com.app.cjl20.lovemore.Activity.FeedbackActivity;
import com.app.cjl20.lovemore.Activity.MyActivitiesActivity;
import com.app.cjl20.lovemore.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by cjl20 on 2015/12/12.
 * PROJECT_NAME by LoveMore
 */
public class FindFragment extends Fragment implements ScreenShotable, View.OnClickListener {
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

    private FloatingActionButton loveroad = null;
    private FloatingActionButton myactivity = null;
    private static final int ADDEDONCE = 1;
    private static final int ADDEDTWICE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(res, container, false);
        FloatingActionsMenu rightLabels = (FloatingActionsMenu) rootView.findViewById(R.id.right_labels);

        FloatingActionButton addedOnce = new FloatingActionButton(getContext());
        addedOnce.setTag(ADDEDONCE);
        addedOnce.setTitle("邀请朋友");
        addedOnce.setSize(FloatingActionButton.SIZE_MINI);
        addedOnce.setImageResource(R.drawable.find_icon2);
        addedOnce.setOnClickListener(this);
        rightLabels.addButton(addedOnce);

        FloatingActionButton addedTwice = new FloatingActionButton(getContext());
        addedTwice.setTag(ADDEDTWICE);
        addedTwice.setTitle("给我们反馈");
        addedTwice.setColorNormalResId(R.color.white);
        addedTwice.setColorPressedResId(R.color.white_pressed);
        addedTwice.setImageResource(R.drawable.find_icon3);
        addedOnce.setSize(FloatingActionButton.SIZE_MINI);
        addedTwice.setOnClickListener(this);
        rightLabels.addButton(addedTwice);

        loveroad = (FloatingActionButton) rightLabels.findViewById(R.id.loveroad);
        loveroad.setOnClickListener(this);

        myactivity = (FloatingActionButton) rightLabels.findViewById(R.id.myactivity);
        myactivity.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loveroad:
                Intent intent = new Intent(getActivity(), AddRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.myactivity:
                Intent myactivity = new Intent(getActivity(), MyActivitiesActivity.class);
                startActivity(myactivity);
                break;
            default:
                switch (Integer.valueOf(v.getTag().toString())) {
                    case ADDEDONCE:
                        Intent mIntent = new Intent(Intent.ACTION_VIEW);
                        mIntent.putExtra("address", "13004165668");
                        mIntent.putExtra("sms_body", "testtesteetst");
                        mIntent.setType("vnd.android-dir/mms-sms");
                        startActivity(mIntent);
                        break;
                    case ADDEDTWICE:
                        Intent feedback = new Intent(getActivity(), FeedbackActivity.class);
                        startActivity(feedback);
                        break;
                }

        }
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

}

