package com.example.root.smartmessenger.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.root.smartmessenger.R;
import com.example.root.smartmessenger.util.RetrofitApi;
import com.example.root.smartmessenger.util.apiInterface;

import retrofit2.Call;

import static com.example.root.smartmessenger.activity.SecondFregment.ret;
import static com.example.root.smartmessenger.activity.ThirdFregment.PREFS_NAME;
import static com.example.root.smartmessenger.activity.ThirdFregment.PREF_CODE;

public class MainActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    public ViewPager vpPager;
    public static apiInterface api;
    public static Call call;
    public static TextView first,second,third,banner;
    final static String PATH_TO_poiret= "fonts/PoiretOne-Regular.ttf";
    final static String PATH_TO_fontawsome= "fonts/fontawesome-webfont.ttf";
    final static String PATH_TO_sairasemilight= "fonts/SairaSemiCondensed-Light.ttf";
    final static String PATH_TO_sairasemibolt= "fonts/SairaSemiCondensed-SemiBold.ttf";

    public static Typeface typeface_awsome,typeface_saira,typeface_saira_bolt,typeface_poiret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        typeface_poiret = Typeface.createFromAsset(getAssets(), PATH_TO_poiret);
        typeface_awsome = Typeface.createFromAsset(getAssets(), PATH_TO_fontawsome);
        typeface_saira = Typeface.createFromAsset(getAssets(), PATH_TO_sairasemilight);
        typeface_saira_bolt = Typeface.createFromAsset(getAssets(), PATH_TO_sairasemibolt);
        api= RetrofitApi.getClient().create(apiInterface.class);
        first=(TextView) findViewById(R.id.one);
        second=(TextView) findViewById(R.id.two);
        third=(TextView) findViewById(R.id.three);
        banner=(TextView) findViewById(R.id.banner);
        banner.setTypeface(typeface_poiret);
        vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MainActivity.MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(0);

        vpPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

               /* switch (position) {
                    case 0: // Fragment # 0 - This will show FirstFragment
                        Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
                    case 1: // Fragment # 1 - This will show SecondFragment
                        Toast.makeText(getApplicationContext(),"by",Toast.LENGTH_SHORT).show();
                    default:

                }*/


            }

            // optional
            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0: // Fragment # 0 - This will show FirstFragment
                        first.setBackground(getDrawable(R.drawable.on));
                        first.setTextColor(getResources().getColor(R.color.textoncolor));
                        second.setBackground(getDrawable(R.drawable.off));
                        second.setTextColor(getResources().getColor(R.color.textoffcolor));
                        third.setBackground(getDrawable(R.drawable.off));
                        third.setTextColor(getResources().getColor(R.color.textoffcolor));
                        // Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // Fragment # 1 - This will show SecondFragment
                        first.setBackground(getDrawable(R.drawable.off));
                        first.setTextColor(getResources().getColor(R.color.textoffcolor));
                        third.setBackground(getDrawable(R.drawable.off));
                        third.setTextColor(getResources().getColor(R.color.textoffcolor));
                        second.setBackground(getDrawable(R.drawable.on));
                        second.setTextColor(getResources().getColor(R.color.textoncolor));
                        // Toast.makeText(getApplicationContext(),"by",Toast.LENGTH_SHORT).show();
                        break;
                    case 2: // Fragment # 1 - This will show SecondFragment
                        first.setBackground(getDrawable(R.drawable.off));
                        first.setTextColor(getResources().getColor(R.color.textoffcolor));
                        third.setBackground(getDrawable(R.drawable.on));
                        third.setTextColor(getResources().getColor(R.color.textoncolor));
                        second.setBackground(getDrawable(R.drawable.off));
                        second.setTextColor(getResources().getColor(R.color.textoffcolor));
                        // Toast.makeText(getApplicationContext(),"by",Toast.LENGTH_SHORT).show();
                        break;
                    default:

                }
            }

            // optional
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FirstFregment.newInstance(0, "Page # 1");
                case 1: // Fragment # 1 - This will show SecondFragment
                    return SecondFregment.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return ThirdFregment.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }


    public ViewPager getVpPager() {
        return vpPager;
    }
}
