package com.example.exam;

import com.example.exam.Model.TiempoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apicall {
   @GET("weather?")
    Call<TiempoModel> getWeather(
                                 @Query("lat") double lat,
                                 @Query("lon") double lon,
                                 @Query("appid") String appid);
}
