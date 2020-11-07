package com.example.root.smartmessenger.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.smartmessenger.Models.MessageReq;
import com.example.root.smartmessenger.Models.Status;
import com.example.root.smartmessenger.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.root.smartmessenger.activity.MainActivity.api;
import static com.example.root.smartmessenger.activity.MainActivity.call;
import static com.example.root.smartmessenger.activity.MainActivity.typeface_awsome;

/**
 * Created by ROOT on 2/3/2018.
 */
public class SecondFregment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    public static  Status ret;
    public static MessageReq messageReq;
    TextView name,email,pass,passcon;
    EditText inputname,inputemail,inputpass,conpass;

    Button reg,back;
    // newInstance constructor for creating fragment with arguments
    public static SecondFregment newInstance(int page, String title) {
        SecondFregment secondFregment = new SecondFregment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        secondFregment.setArguments(args);
        return secondFregment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        messageReq=new MessageReq();
        Toast.makeText(getActivity(),title,Toast.LENGTH_SHORT).show();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registerpage, container, false);
        name=(TextView) view.findViewById(R.id.nameicon);
        back=(Button) view.findViewById(R.id.backtologin);
        back.setTypeface(typeface_awsome);
        name.setTypeface(typeface_awsome);
        email=(TextView) view.findViewById(R.id.emailicon);
        email.setTypeface(typeface_awsome);
        pass=(TextView) view.findViewById(R.id.passicon);
        pass.setTypeface(typeface_awsome);
        passcon =(TextView) view.findViewById(R.id.passiconconfirm);
        passcon.setTypeface(typeface_awsome);
        reg=(Button) view.findViewById(R.id.adduser);
        reg.setTypeface(typeface_awsome);
        inputname=(EditText) view.findViewById(R.id.inputname);
        inputemail=(EditText) view.findViewById(R.id.inputemail);
        inputpass=(EditText) view.findViewById(R.id.pass);
        conpass=(EditText) view.findViewById(R.id.passcon);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).getVpPager().setCurrentItem(0);
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),inputemail.getText().toString(),Toast.LENGTH_SHORT).show();
                messageReq.setEmail(inputemail.getText().toString());
                messageReq.setName(inputname.getText().toString());
                messageReq.setPassword(inputpass.getText().toString());

                String a,b;
                a=inputpass.getText().toString();
                b= conpass.getText().toString();

                Toast.makeText(getActivity(),a+" "+b,Toast.LENGTH_SHORT).show();

                if(a.equals(b))
                {
                    call=api.checkValidation(messageReq);

                    call.enqueue(new Callback<Status>() {

                        @Override
                        public void onResponse(Call<Status> call, Response<Status>response) {

                            ret = response.body();

                            if(ret.getregflag() == true && ret.getsendflag()==true)
                            {
                                ((MainActivity) getActivity()).getVpPager().setCurrentItem(2);
                                //Toast.makeText(getActivity(),"Successfully Registered!!!",Toast.LENGTH_SHORT).show();
                            }
                            else if(ret.getregflag()!=true)
                                Toast.makeText(getActivity(),"This email is not Registered!!!",Toast.LENGTH_SHORT).show();
                            else if(ret.getsendflag()!=true)
                                Toast.makeText(getActivity(),"Try Again!!!",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {

                            Toast.makeText(getActivity(),"Internet Connection Failed!!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getActivity(),inputpass.getText().toString()+" "+conpass.getText().toString()+ "Password didn't match!!!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
}
