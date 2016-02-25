package com.app.cjl20.lovemore.fragment;

/**
 * Created by cjl20 on 2016/2/25.
 * PROJECT_NAME by LoveMore
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.cjl20.lovemore.R;
import com.app.cjl20.lovemore.Utils.PictureUtil;
import com.app.cjl20.lovemore.model.Evaluate;
import com.app.cjl20.lovemore.views.CircleImg;

import java.util.List;

class ViewHolder {
    public CircleImg circleImg;
    public TextView rdname;
    public TextView rdcontent;
    public ImageView rdimg;
}

public class AnimalListAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;

    private List<Evaluate> list = null;

    public AnimalListAdapter(Context context, List<Evaluate> list) {
        super();
        this.list = list;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Evaluate evaluate = list.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_record, null);
            holder.circleImg = (CircleImg) convertView.findViewById(R.id.rdavatarImg);
            holder.rdname = (TextView) convertView.findViewById(R.id.rdname);
            holder.rdcontent = (TextView) convertView.findViewById(R.id.rdcontent);
            holder.rdimg = (ImageView) convertView.findViewById(R.id.rdimg);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.circleImg.setImageBitmap(PictureUtil.Bytes2Bimap(evaluate.getIcno()));
        holder.rdname.setText(evaluate.getName());
        holder.rdcontent.setText(evaluate.getContent());
        holder.rdimg.setImageBitmap(PictureUtil.Bytes2Bimap(evaluate.getImages()));

//        holder.speaker.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                System.out.println("Click on the speaker image on ListItem ");
//            }
//        });
        return convertView;
    }

}
