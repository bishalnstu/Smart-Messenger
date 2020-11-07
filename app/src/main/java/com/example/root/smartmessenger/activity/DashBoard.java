package com.example.root.smartmessenger.activity;

import android.content.SharedPreferences;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.root.smartmessenger.Models.TeacherProfile;
import com.example.root.smartmessenger.Models.UnreadRequest;
import com.example.root.smartmessenger.Models.logincredentials;
import com.example.root.smartmessenger.R;
import com.example.root.smartmessenger.app.Config;
import com.example.root.smartmessenger.util.NotificationUtils;
import com.example.root.smartmessenger.util.RecyclerAdapter;
import com.example.root.smartmessenger.util.RetrofitApi;
import com.example.root.smartmessenger.util.apiInterface;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import java.util.ArrayList;

import static com.example.root.smartmessenger.activity.MainActivity.api;
import static com.example.root.smartmessenger.activity.MainActivity.call;

public class DashBoard extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    RecyclerView recyclerView;
    public ArrayList<Object> items= new ArrayList<>();
    private RecyclerAdapter adapter;
    public  String session;
    logincredentials retval=new logincredentials();

    private RecyclerView.LayoutManager layoutManager;
    UnreadRequest unreadRequest= new UnreadRequest();
    public static final String PREFS_NAME = "preferences";
    public static final String PREF_Session="session";
    public static final String DefaultValue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);

        recyclerView = (RecyclerView) findViewById(R.id.messages);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    //FirebaseMessaging.getInstance().unsubscribeFromTopic("Dhaka_WEATHER");

                } else if (intent.getAction().equals(Config.NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(),"onupdate",Toast.LENGTH_SHORT).show();
                    retval=loadPreferences(getApplicationContext());
                    unreadRequest.setSession(retval.getSession());
                    loadmessages();
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();

        retval=loadPreferences(getApplicationContext());
        //Toast.makeText(getApplicationContext(),session,Toast.LENGTH_SHORT).show();
        unreadRequest.setSession(retval.getSession());
        loadmessages();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    public static logincredentials loadPreferences(Context context) {

        String val;
        logincredentials ret=new logincredentials();
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        // Get value
         val= settings.getString(PREF_Session, DefaultValue);

        ret.setSession(val);
        val=settings.getString("email", DefaultValue);
        ret.setEmail(val);
        val=settings.getString("password", DefaultValue);
        ret.setPassword(val);
        return ret;
    }

    public void loadmessages()
    {
        items.clear();
        api= RetrofitApi.getClient().create(apiInterface.class);

        call=api.getTeacherNameList(unreadRequest);

        call.enqueue(new Callback<ArrayList<TeacherProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<TeacherProfile>> call, Response<ArrayList<TeacherProfile>> response) {

                Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();
                ArrayList<TeacherProfile> infolist = response.body();
                items.addAll(infolist);

                adapter = new RecyclerAdapter(items,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<TeacherProfile>> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Internet Connection Failed!!",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
