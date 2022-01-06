package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    Button btnSearch, btnChangeActivity;
    TextView txtName, txtCountry, txtTemp, txtStatus, txtHumidity, txtCloud, txtWind, txtDay;
    ImageView imgIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = editSearch.getText().toString();
                GetCurrentWeatherData(city);
            }
        });
    }

    public  void  GetCurrentWeatherData(String data)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=078c02765446283167b0d11fbb8444a9";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name= jsonObject.getString("name");
                            txtName.setText("Tên thành phố :" + name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-yyyy HH-mm-ss");
                            String Day = simpleDateFormat.format(date);

                            txtDay.setText(Day);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");
                            Log.d("ALO",icon);
                            Picasso.with(MainActivity.this).load("https://openweathermap.org/img/wn/"+icon+".png").into(imgIcon);
                            txtStatus.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String temp = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            Double dTemp = Double.valueOf(temp);
                            String strTemp = String.valueOf(dTemp.intValue());

                            txtTemp.setText(strTemp+"°C");
                            txtHumidity.setText(doam+"%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String strWind = jsonObjectWind.getString("speed");

                            txtWind.setText(strWind+"m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String strCloud = jsonObjectClouds.getString("all");
                            txtCloud.setText(strCloud+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String strCountry = jsonObjectSys.getString("country");
                            txtCountry.setText("Tên quốc gia :"+strCountry);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    private void anhXa() {
        editSearch = findViewById(R.id.editTextSearch);
        btnSearch = findViewById(R.id.buttonSearch);
        btnChangeActivity = findViewById(R.id.buttonChangeActivity);
        txtName = findViewById(R.id.textViewName);
        txtCountry = findViewById(R.id.textViewNameCountry);
        txtTemp = findViewById(R.id.nhietdo);
        txtStatus = findViewById(R.id.status);
        txtHumidity = findViewById(R.id.textViewHumidity);
        txtCloud = findViewById(R.id.textViewCloud);
        txtWind = findViewById(R.id.textViewWind);
        txtDay = findViewById(R.id.textViewDate);
        imgIcon = (ImageView) findViewById(R.id.imageIcon);
    }
}