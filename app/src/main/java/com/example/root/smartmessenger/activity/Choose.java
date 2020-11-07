package com.example.root.smartmessenger.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.root.smartmessenger.R;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class Choose extends AppCompatActivity {


    CircleMenu circleMenu;
    String email,tname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Bundle bundle = getIntent().getExtras();
        email=bundle.getString("email");
        tname=bundle.getString("name");
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w=dm.widthPixels;
        int h=dm.heightPixels;
        getWindow().setLayout((int)(w*.65),(int)(h*.50));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        circleMenu=(CircleMenu) findViewById(R.id.circlemenu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.add,R.drawable.remove)
                .addSubMenu(Color.parseColor("#256CFF"),R.drawable.individual)
                .addSubMenu(Color.parseColor("#FFE70F69"),R.drawable.group)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int i) {

                        if(i==0)
                        {

                        }
                        else
                        {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 100ms

                                    Intent intent = new Intent(getApplicationContext(),messagebox.class);
                                    intent.putExtra("email",email);
                                    intent.putExtra("name",tname);

                                    Choose.this.startActivity(intent);
                                }
                            }, 700);

                        }
                        //Toast.makeText(getApplicationContext(),"clicked" + Integer.toString(i),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
