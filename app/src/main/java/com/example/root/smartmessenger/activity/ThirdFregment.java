package com.example.root.smartmessenger.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.smartmessenger.Models.Registrationfeedback;
import com.example.root.smartmessenger.Models.Status;
import com.example.root.smartmessenger.R;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.root.smartmessenger.activity.MainActivity.api;
import static com.example.root.smartmessenger.activity.MainActivity.call;
import static com.example.root.smartmessenger.activity.MainActivity.typeface_awsome;
import static com.example.root.smartmessenger.activity.MainActivity.typeface_poiret;
import static com.example.root.smartmessenger.activity.SecondFregment.messageReq;
import static com.example.root.smartmessenger.activity.SecondFregment.ret;

/**
 * Created by ROOT on 2/3/2018.
 */

public class ThirdFregment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private String token="";
    TextView codeicon,banner;
    Registrationfeedback retval;
    EditText code;
    Button confirm;
    public static final String PREFS_NAME = "preferences";
    public static final String PREF_CODE = "session";

    // newInstance constructor for creating fragment with arguments
    public static ThirdFregment newInstance(int page, String title) {
        ThirdFregment thirdFregment = new ThirdFregment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        thirdFregment.setArguments(args);
        return thirdFregment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        retval=new Registrationfeedback();
        Toast.makeText(getActivity(),title,Toast.LENGTH_SHORT).show();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirmation, container, false);
        codeicon=(TextView) view.findViewById(R.id.codeicon);
        codeicon.setTypeface(typeface_awsome);
        code=(EditText)view.findViewById(R.id.code);
        banner=(TextView) view.findViewById(R.id.conbanner);
        banner.setTypeface(typeface_poiret);
        confirm=(Button) view.findViewById(R.id.confirm);
        confirm.setTypeface(typeface_awsome);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(code.getText().toString().equals(ret.getCode()))
                {

                    messageReq.setSession(ret.getSession());
                    call=api.register(messageReq);

                    call.enqueue(new Callback<Registrationfeedback>() {

                        @Override
                        public void onResponse(Call<Registrationfeedback> call, Response<Registrationfeedback> response) {

                            retval = response.body();

                            if(retval.getExists())
                            {
                                Toast.makeText(getActivity(),"Already Registered!!!",Toast.LENGTH_SHORT).show();
                                ((MainActivity) getActivity()).getVpPager().setCurrentItem(0);
                            }
                            else
                            {
                                if(retval.getFlag())
                                {
                                    Toast.makeText(getActivity(),"Successfully Registered!!!",Toast.LENGTH_SHORT).show();

                                    FirebaseMessaging.getInstance().subscribeToTopic(ret.getSession());

                                    String temp=messageReq.getEmail();
                                    token="";

                                    for(int i=0;i<temp.length();i++)
                                    {
                                        if(temp.charAt(i)!='@')
                                            token+=temp.charAt(i);
                                    }
                                    Toast.makeText(getActivity(),token,Toast.LENGTH_SHORT).show();
                                    FirebaseMessaging.getInstance().subscribeToTopic(token);
                                    savePreferences(getActivity(),ret.getSession(),messageReq.getEmail(),messageReq.getPassword());

                                    ((MainActivity) getActivity()).getVpPager().setCurrentItem(0);
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"Try Again!!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Registrationfeedback> call, Throwable t) {

                            Toast.makeText(getActivity(),"Internet Connection Failed!!",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(getActivity(),"Code didn't matched!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public static void savePreferences(Context context,String session,String email,String pass) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        editor.putString(PREF_CODE,session);
        editor.putString("email",email);
        editor.putString("password",pass);
        editor.apply();
    }

}
