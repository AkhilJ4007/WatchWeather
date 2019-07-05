package com.example.assignment;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Path;
public interface ApiInter {
    @GET("/data/2.5/weather")
    Call<ApiResponse>getAnswers(@Query("lat") int latitude,@Query("lon") int longitude,@Query("appid") String appId);
}
