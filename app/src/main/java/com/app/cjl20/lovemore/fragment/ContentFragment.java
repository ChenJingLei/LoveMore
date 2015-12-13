package com.app.cjl20.lovemore.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.cjl20.lovemore.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends Fragment implements ScreenShotable {
    public static final String CLOSE = "Close";


    //    public static final String FIND = "find";
    public static final String CASE = "Case";
    public static final String SHOP = "Shop";
    public static final String PARTY = "Party";
    public static final String MOVIE = "Movie";

    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;

    public static ContentFragment newInstance(int resId) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("111->" + res);
        View rootView = inflater.inflate(res, container, false);

        ImgClickListener listener = new ImgClickListener();
        ImageView img1 = (ImageView) rootView.findViewById(R.id.image1);
        img1.setOnClickListener(listener);
        ImageView img2 = (ImageView) rootView.findViewById(R.id.image2);
        img2.setOnClickListener(listener);
        ImageView img3 = (ImageView) rootView.findViewById(R.id.image3);
        img3.setOnClickListener(listener);
        ImageView img4 = (ImageView) rootView.findViewById(R.id.image4);
        img4.setOnClickListener(listener);

        return rootView;
    }

    private class ImgClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image1:
                    startActivity(new Intent(getContext(), VolunteerFragment.class));
                    break;
                case R.id.image2:
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, HelpFragment.newInstance(R.layout.fragment_help))
                            .commit();
                    break;
                case R.id.image3:
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, FindFragment.newInstance(R.layout.fragment_find))
                            .commit();
                    break;
                case R.id.image4:
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, RecordFragment.newInstance(R.layout.fragment_record))
                            .commit();
                    break;
            }
        }
    }


    @Override
    public void takeScreenShot() {
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

