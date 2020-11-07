package com.example.root.smartmessenger.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.smartmessenger.Models.LoginReq;
import com.example.root.smartmessenger.Models.Registrationfeedback;
import com.example.root.smartmessenger.Models.Status;
import com.example.root.smartmessenger.Models.logincredentials;
import com.example.root.smartmessenger.R;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.root.smartmessenger.activity.DashBoard.loadPreferences;
import static com.example.root.smartmessenger.activity.MainActivity.api;
import static com.example.root.smartmessenger.activity.MainActivity.call;
import static com.example.root.smartmessenger.activity.MainActivity.first;
import static com.example.root.smartmessenger.activity.MainActivity.second;
import static com.example.root.smartmessenger.activity.MainActivity.typeface_awsome;
import static com.example.root.smartmessenger.activity.MainActivity.typeface_saira;
import static com.example.root.smartmessenger.activity.MainActivity.typeface_saira_bolt;
import static com.example.root.smartmessenger.activity.SecondFregment.messageReq;
import static com.example.root.smartmessenger.activity.SecondFregment.ret;
import static com.example.root.smartmessenger.activity.ThirdFregment.savePreferences;

/**
 * Created by ROOT on 2/3/2018.
 */

public class FirstFregment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    LoginReq loginReq;
    TextView emailicon,passicon,footer;
    EditText email,pass;
    Button enter,signup,back;
    private String token;
    logincredentials retval=new logincredentials();
    Registrationfeedback ret;
    // newInstance constructor for creating fragment with arguments
    public static FirstFregment newInstance(int page, String title) {
        FirstFregment fragmentFirst = new FirstFregment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);

        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        loginReq= new LoginReq();

        //Toast.makeText(getActivity(),title,Toast.LENGTH_SHORT).show();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loginpage, container, false);
        footer=(TextView) view.findViewById(R.id.footer);
        footer.setTypeface(typeface_saira);
        enter=(Button) view.findViewById(R.id.enter);
        back=(Button) view.findViewById(R.id.back);
        back.setTypeface(typeface_awsome);
        signup=(Button) view.findViewById(R.id.signup);
        signup.setTypeface(typeface_saira_bolt);
        email=(EditText) view.findViewById(R.id.email);
        email.setTypeface(typeface_saira);
        pass=(EditText) view.findViewById(R.id.pass);
        pass.setTypeface(typeface_saira);
        enter.setTypeface(typeface_awsome);
        emailicon=(TextView) view.findViewById(R.id.eicon);
        emailicon.setTypeface(typeface_awsome);
        passicon=(TextView) view.findViewById(R.id.picon);
        passicon.setTypeface(typeface_awsome);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                first.setBackground(getActivity().getDrawable(R.drawable.off));
                first.setTextColor(getResources().getColor(R.color.textoffcolor));
                second.setBackground(getActivity().getDrawable(R.drawable.on));
                second.setTextColor(getResources().getColor(R.color.textoncolor));
                ((MainActivity) getActivity()).getVpPager().setCurrentItem(1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).getVpPager().setCurrentItem(0);
            }
        });


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginReq.setEmail(email.getText().toString());
                loginReq.setPass(pass.getText().toString());

                call=api.login(loginReq);

                call.enqueue(new Callback<Registrationfeedback>() {

                    @Override
                    public void onResponse(Call<Registrationfeedback> call, Response<Registrationfeedback> response) {

                        ret = response.body();


                        if(!ret.getExists())
                        {
                            Toast.makeText(getActivity(),"Email is not Registered!!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            if(ret.getFlag())
                            {
                                retval=loadPreferences(getActivity());
                                Toast.makeText(getActivity(),"hy "+retval.getSession(),Toast.LENGTH_SHORT).show();

                                 if(retval.getSession()==null)
                                {
                                        Toast.makeText(getActivity(),ret.getSession(),Toast.LENGTH_SHORT).show();
                                        FirebaseMessaging.getInstance().subscribeToTopic(ret.getSession());

                                        token="";
                                        String temp=email.getText().toString();
                                        for(int i=0;i<temp.length();i++)
                                        {
                                            if(temp.charAt(i)!='@')
                                                token+=temp.charAt(i);
                                        }
                                        FirebaseMessaging.getInstance().subscribeToTopic(token);
                                        Toast.makeText(getActivity(),token,Toast.LENGTH_SHORT).show();


                                    savePreferences(getActivity(),ret.getSession(),email.getText().toString(),pass.getText().toString());
                                }
                                else
                                {

                                    if(!retval.getSession().equals(ret.getSession()))
                                    {
                                        FirebaseMessaging.getInstance().unsubscribeFromTopic(retval.getSession());
                                        Toast.makeText(getActivity(),ret.getSession(),Toast.LENGTH_SHORT).show();
                                        FirebaseMessaging.getInstance().subscribeToTopic(ret.getSession());
                                    }

                                    if(!retval.getEmail().equals(email.getText().toString()))
                                    {
                                        token=formatsubscription(retval.getEmail());
                                        FirebaseMessaging.getInstance().unsubscribeFromTopic(token);
                                        token="";
                                        String temp=email.getText().toString();
                                        token=formatsubscription(temp);
                                        FirebaseMessaging.getInstance().subscribeToTopic(token);
                                        Toast.makeText(getActivity(),token,Toast.LENGTH_SHORT).show();
                                    }

                                    savePreferences(getActivity(),ret.getSession(),email.getText().toString(),pass.getText().toString());
                                }


                                Intent intent=new Intent(getActivity(),DashBoard.class);

                                getActivity().startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Email or password didn't match!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Registrationfeedback> call, Throwable t) {

                        Toast.makeText(getActivity(),"Internet Connection Failed!!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }


    public String formatsubscription(String temp)
    {

        String token="";
        for(int i=0;i<temp.length();i++)
        {
            if(temp.charAt(i)!='@')
                token+=temp.charAt(i);
        }
        return token;
    }
}