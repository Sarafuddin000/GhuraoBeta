package com.bitleet.saraf.ghurao_beta;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitleet.saraf.ghurao_beta.WeatherDetail.WeatherResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {


    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    public void setLatLng(double lat, double lon){
        String unit = "metric";
        String apiKey = getString(R.string.api_key);
        /*String latS = String.valueOf(lat);
        String lonS = String.valueOf(lng);*/
        //weather?lat=35&lon=139&units=metric&appid=380199723cebdb85ef2e16cc30cee5b6
        String endUrl = String
                .format("weather?lat=%f&lon=%f&units=%s&appid=9a50b64a25124b07d5e15e3772731879",
                        lat,lon,unit);
        RetrofitHelper.getService().getWeatherResponse(endUrl)
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if(response.isSuccessful()){
                            WeatherResponse weatherResponse = response.body();
                            double temp = weatherResponse.getMain().getTemp();
                            String city = weatherResponse.getName();
                            String country = weatherResponse.getSys().getCountry();
                            String icon = weatherResponse.getWeather().get(0).getIcon();
                            String description = weatherResponse.getWeather().get(0).getDescription();
                            TextView text = getView().findViewById(R.id.textView);
                            text.setText(String.valueOf(temp)
                                    + "\n" + description
                                    + "\n" + city
                                    + "\n" + country
                            );
                            ImageView iv = getView().findViewById(R.id.imgIV);
                            Picasso.get().load(RetrofitHelper.getIconLinkFromName(icon)).into(iv);
                        }else {
                            Toast.makeText(getActivity(), "error:" + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Log.e("err", "onFailure: " + t.getMessage() );
                    }

});}}
