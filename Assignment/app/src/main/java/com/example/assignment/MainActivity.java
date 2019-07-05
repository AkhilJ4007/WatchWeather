package com.example.assignment;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends WearableActivity {

    ImageView imageV;
    TextView tempLabel;
    String celsiusText;
    String fText;

    String url = "https://samples.openweathermap.org/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageV = findViewById(R.id.iconImage);
        tempLabel = findViewById(R.id.label);
        // rertreo

        //Log.d("ADebugTag","kjhkjhk");

        Retrofit retro = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

        ApiInter response = retro.create(ApiInter.class);

        Call <ApiResponse> responses = response.getAnswers(35,139,"b6907d289e10d714a6e88b30761fae22");

        final String uuu = responses.request().toString();

        Log.d("ADebugTag","Url is "+uuu );


        responses.enqueue(new Callback<ApiResponse>() {


            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("ADebugTag","sucessssssssssss");
                    ApiResponse weather = response.body();
                    Double temp = weather.getMain().getTemp();
                    Double celsius = temp - 273.16;
                    Double farenheit = (((temp - 273) * 9/5) + 32);
                    Integer Celsius = (int)Math.round(celsius);
                    Integer f = (int)Math.round(farenheit);
                     celsiusText = Celsius.toString() + "C";
                     fText = f.toString() + "F";
                    // label
                    tempLabel.setText(celsiusText);
                    System.out.println(temp);
                    String weath = weather.getWeather().get(0).getMain();
                    System.out.println(weath);
                    if(weath == "Clear") {

                        imageV.setImageResource(R.drawable.sunny);
                    }
                    else if(weath == "Rain"){
                        imageV.setImageResource(R.drawable.rain);
                    }
                    else if(weath == "Haze"){
                        imageV.setImageResource(R.drawable.fog);
                    }
                }
                else {
                    int statusCode = response.code();
                    String err = Integer.toString(statusCode);
                    // handle request errors depending on status code
                    Log.d("ADebugTag","Error is " + err +" "+uuu );

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                String err = t.getMessage();
                Log.d("ADebugTag","Error is " + err);

            }
        });


    }

    
}



