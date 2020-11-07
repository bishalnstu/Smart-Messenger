package com.example.root.smartmessenger.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.smartmessenger.Models.DefaultHolder;
import com.example.root.smartmessenger.Models.MessageModel;
import com.example.root.smartmessenger.Models.MessageModelini;
import com.example.root.smartmessenger.Models.TeacherProfile;
import com.example.root.smartmessenger.R;
import com.example.root.smartmessenger.activity.Choose;
import com.example.root.smartmessenger.activity.messagebox;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by ROOT on 2/3/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,View.OnLongClickListener {

    public ArrayList<Object> objectlist;
    private final Context context;
    WeakReference<Context> mContextWeakReference;

    private final int Message = 1, Details = 2;

    public RecyclerAdapter(ArrayList<Object> objectlist, Context context) {
        this.objectlist = objectlist;
        this.mContextWeakReference = new WeakReference<Context>(context);
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case Details:
                View v1 = inflater.inflate(R.layout.message_popup, parent, false);
                viewHolder = new ViewHolder(v1,context);
                break;
            case Message:
                View v2 = inflater.inflate(R.layout.messages, parent, false);
                viewHolder = new MessageModelini(v2);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder = new DefaultHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case Details:
                final ViewHolder vh1 = (ViewHolder) viewHolder;
                final TeacherProfile tmp = (TeacherProfile) objectlist.get(position);
                vh1.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context,tmp.getEmail().toString(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,Choose.class);
                        intent.putExtra("email",tmp.getEmail());
                        intent.putExtra("name",tmp.getName());

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                configureViewHolder1(vh1, position);
                break;
            case Message:
                MessageModelini vh2 = (MessageModelini) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            default:
                DefaultHolder vh = (DefaultHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }


    @Override
    public int getItemCount() {

        return objectlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        //More to come

        if (objectlist.get(position) instanceof TeacherProfile) {
            return Details;
        }
        else if (objectlist.get(position) instanceof MessageModel) {
            return Message;
        }
        else
            return -1;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView name,unread;
        public CardView cardView;

        public ViewHolder(View itemView,Context context) {
            super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.profile_image);
            name=(TextView) itemView.findViewById(R.id.name);
            unread=(TextView) itemView.findViewById(R.id.unread);
            cardView = (CardView) itemView.findViewById(R.id.card);

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

    private void configureDefaultViewHolder(DefaultHolder vh, int position) {

    }

    private void configureViewHolder1(ViewHolder vh1, int position) {

        TeacherProfile ret= (TeacherProfile) objectlist.get(position);

        String iconUrl = "http://imbishal.me/smartsystem/img.png";

        Picasso.with(context).load(iconUrl).into(vh1.getImageView());
        vh1.getName().setText(ret.getName());
        vh1.getUnread().setText(ret.getUnread());

    }

    private void configureViewHolder2(MessageModelini vh2, int position) {

        MessageModel ret= (MessageModel) objectlist.get(position);

        //Toast.makeText(context,ret.getMessage(),Toast.LENGTH_SHORT).show();
        vh2.getT1().setText(ret.getMessage());
        vh2.getT2().setText(ret.getTimedate());
    }

    public Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }


}

