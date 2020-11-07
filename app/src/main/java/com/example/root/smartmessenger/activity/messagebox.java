package com.example.root.smartmessenger.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import java.util.Collections;
import android.widget.Toast;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.root.smartmessenger.Models.MessageModel;
import com.example.root.smartmessenger.Models.MessageReq;
import com.example.root.smartmessenger.Models.logincredentials;
import com.example.root.smartmessenger.R;
import com.example.root.smartmessenger.util.RecyclerAdapter;
import com.example.root.smartmessenger.util.RetrofitApi;
import com.example.root.smartmessenger.util.apiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.root.smartmessenger.activity.DashBoard.loadPreferences;
import static com.example.root.smartmessenger.activity.MainActivity.api;
import static com.example.root.smartmessenger.activity.MainActivity.call;
import static com.example.root.smartmessenger.activity.MainActivity.typeface_awsome;
import static com.example.root.smartmessenger.activity.MainActivity.typeface_saira;

public class messagebox extends AppCompatActivity {

    RecyclerView recyclerView;
    MessageModel model;
    TextView name2;
    Button send;
    EditText msg;
    ImageView imageView;

    public ArrayList<Object> msgitems= new ArrayList<>();
    private RecyclerAdapter adapter;
    String email,tname;
    logincredentials retval=new logincredentials();
    MessageReq req = new MessageReq();
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagebox);
        Bundle bundle = getIntent().getExtras();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        //linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setReverseLayout(true);

        adapter = new RecyclerAdapter(msgitems,getApplicationContext());
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                linearLayoutManager.smoothScrollToPosition(recyclerView,null,0);
            }
        });

        model=new MessageModel();
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w=dm.widthPixels;
        int h=dm.heightPixels;
        getWindow().setLayout((int)(w*1.0),(int)(h*1.0));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        email=bundle.getString("email");
        tname=bundle.getString("name");
        retval=loadPreferences(getApplicationContext());

        recyclerView=(RecyclerView) findViewById(R.id.messageslist);
        send=(Button) findViewById(R.id.send);
        send.setTypeface(typeface_awsome);
        msg=(EditText) findViewById(R.id.writemsg);
        msg.setTypeface(typeface_saira);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        name2=(TextView) findViewById(R.id.name2);
        imageView=(ImageView) findViewById(R.id.profimg1);
        String iconUrl = "http://imbishal.me/smartsystem/img.png";

        Picasso.with(messagebox.this).load(iconUrl).into(imageView);
        name2.setText(tname);
        req.setSession(retval.getSession());
        req.setEmail(email);
        //session="fdf";
        if(retval.getSession()!=null)
        {
            //Toast.makeText(getApplicationContext(),session,Toast.LENGTH_LONG).show();

            call=api.getMessageList(req);

            call.enqueue(new Callback<ArrayList<MessageModel>>() {
                @Override
                public void onResponse(Call<ArrayList<MessageModel>> call, Response<ArrayList<MessageModel>> response) {


                    ArrayList<MessageModel> msglist = response.body();
                    //Toast.makeText(getApplicationContext(),msglist.get(0).getMessage(),Toast.LENGTH_SHORT).show();
                    Collections.reverse(msglist);
                    msgitems.addAll(msglist);

                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<ArrayList<MessageModel>> call, Throwable t) {

                    Toast.makeText(getApplicationContext(),"Internet Connection Failed!!",Toast.LENGTH_SHORT).show();
                }
            });

        }
        //Toast.makeText(getApplicationContext(),session,Toast.LENGTH_LONG).show();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               model.setMessage(msg.getText().toString());
                String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new java.util.Date());

                model.setTimedate(timeStamp);
                msgitems.add(0,model);
                msg.setText("");

               adapter.notifyItemInserted(0);
            }
        });
    }


}
