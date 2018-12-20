package com.bitleet.saraf.ghurao_beta.WeatherDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherService {

    @GET
    Call<WeatherResponse> getWeatherResponse(@Url String endUrl);
}
