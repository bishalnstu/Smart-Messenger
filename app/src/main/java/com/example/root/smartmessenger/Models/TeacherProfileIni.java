package com.example.root.smartmessenger.Models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.smartmessenger.R;

/**
 * Created by ROOT on 2/3/2018.
 */

public  class TeacherProfileIni extends RecyclerView.ViewHolder{

    private ImageView imageView;
    private TextView name,unread;
    public RelativeLayout layout;

    public TeacherProfileIni(View v, final Context context) {
        super(v);

        imageView=(ImageView) v.findViewById(R.id.profile_image);
        name=(TextView) v.findViewById(R.id.name);
        unread=(TextView) v.findViewById(R.id.unread);
        layout=(RelativeLayout) v.findViewById(R.id.message_popup);
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    public TextView getName() {
        return name;
    }

    public TextView getUnread() {
        return unread;
    }

    public ImageView getImageView() {
        return this.imageView;
    }
}
