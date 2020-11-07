package com.example.root.smartmessenger.Models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.root.smartmessenger.R;

/**
 * Created by ROOT on 2/3/2018.
 */

public class MessageModelini extends  RecyclerView.ViewHolder{

    private TextView t1,t2;

    public MessageModelini(View v) {
        super(v);

        t1 =(TextView) v.findViewById(R.id.msg);
        t2=(TextView) v.findViewById(R.id.timedate);
    }

    public TextView getT1() {
        return t1;
    }

    public TextView getT2() {
        return t2;
    }
}
