package com.bitleet.saraf.ghurao_beta;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {



    private FusedLocationProviderClient providerClient;
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private WeatherPagerAdapter pagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    private WeatherFragment weatherFragment;
    private ForecastWeatherFragment forecastWeatherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        providerClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_weather);

        weatherFragment = new WeatherFragment();
        forecastWeatherFragment = new ForecastWeatherFragment();
        fragmentList.add(weatherFragment);
        fragmentList.add(forecastWeatherFragment);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("Current Weather"));
        tabLayout.addTab(tabLayout.newTab().setText("Forecast Weather"));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.WHITE, Color.YELLOW);

        pagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkLocationPermission()){
            getDeviceLastLocation();
        }
    }

    private boolean checkLocationPermission(){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION_CODE);
            return false;
        }

        return true;
    }

    private void getDeviceLastLocation(){
        if(checkLocationPermission()){
            providerClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            double latitude;
                            double longitude;
                            if(location == null){

                                Toast.makeText(WeatherActivity.this, "Accurate location not found", Toast.LENGTH_SHORT).show();
//                                return;
                                latitude = 23.75;
                                longitude = 90.39;
                            }else {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }

                            weatherFragment.setLatLng(latitude, longitude);

                        }
                    });
        }
    }

    private class WeatherPagerAdapter extends FragmentPagerAdapter {
        public WeatherPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}