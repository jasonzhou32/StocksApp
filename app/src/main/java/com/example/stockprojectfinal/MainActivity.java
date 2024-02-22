package com.example.stockprojectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.text.ParseException;


import com.google.logging.type.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;


public class MainActivity extends AppCompatActivity {

    private final String APIKEY = "05baf09f0cmsh227b9f321399e3ap11968ajsn9c0813e89941";
    private final String API_REQUEST = "https://apistocks.p.rapidapi.com/monthly?";

    public String ticker;
    public String dateStart;
    public String dateEnd;


    String url;
    private TextView output;
    private Button button;
    private EditText tickerET, startET, endET;

    private double[][] finalValues = new double[51][5];


    private final static String TAG = MainActivity.class.getSimpleName();

    private final static String RAPIDAPI_KEY = "05baf09f0cmsh227b9f321399e3ap11968ajsn9c0813e89941";
    private final static String RAPIDAPI_TRUEWAY_PLACES_HOST = "apistocks.p.rapidapi.com";

    private final OkHttpClient client = new OkHttpClient();

    public void getRapidApiAsync(String url, String rapidApiKey, String rapidApiHost, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", rapidApiKey)
                .addHeader("x-rapidapi-host", rapidApiHost)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void findPlacesByText(String text, String language, Callback callback) {
        getRapidApiAsync(String.format(Locale.US, "https://%s/monthly?symbol=AAPL&dateStart=2017-05-01&dateEnd=2021-07-31", RAPIDAPI_TRUEWAY_PLACES_HOST, text, language),
                RAPIDAPI_KEY,
                RAPIDAPI_TRUEWAY_PLACES_HOST,
                callback);
    }

    private double[][] showResults(String responseStr) {
        double[][] values = new double[51][5];
        double doubleOpen;
        double doubleClose;
        double doubleHigh;
        double doubleLow;
        double doubleVolume;
        int row=0;


        try {
            JSONObject jsonObj = new JSONObject(responseStr);
            // Getting results JSON Array node
            JSONArray results = jsonObj.getJSONArray("Results");


            Log.d(TAG, "found places: " + results.length());
            // looping through All Results
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String open = result.getString("Open");
                String close = result.getString("Close");
                String high = result.getString("High");
                String low = result.getString("Low");
                String volume = result.getString("Volume");
//                JSONObject location = result.getJSONObject("location");
//                double lat = location.getDouble("lat");
//                double lng = location.getDouble("lng");
//                Integer distance = result.has("distance") ? result.getInt("distance") : 0; // present for FindPlacesNearby only
                Log.d(TAG, String.format(Locale.US,"result[%s]: open=%s; close=%s; high=%s; low=%s; volume=%s", i, open, close, high, low, volume));
                doubleOpen = Double.parseDouble(open);
                doubleClose = Double.parseDouble(close);
                doubleHigh = Double.parseDouble(high);
                doubleLow = Double.parseDouble(low);
                doubleVolume = Double.parseDouble(volume);

                values[row][0] = doubleOpen;
                values[row][1] = doubleClose;
                values[row][2] = doubleHigh;
                values[row][3] = doubleLow;
                values[row][4] = doubleVolume;
                row++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        finalValues = values;

        for(int i = 0; i<51;i++){
            for(int j = 0; j<5; j++){
                Log.d("joe", String.valueOf(values[i][j]));
            }
        }

        return values;



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Log.d("Mithun", "On Stage 1");
        findPlacesByText("Children's Creativity Museum", "en",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Something went wrong
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseStr = response.body().string();
                            // Do what you want to do with the response.
                            Log.d(TAG, "findPlacesByText response: " + responseStr);
                            finalValues = showResults(responseStr);
                        } else {
                            // Request not successful
                        }
                    }
                });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double[] flattenedArray = Arrays.stream(finalValues)
                .flatMapToDouble(Arrays::stream)
                .toArray();

        Log.d("Mithun", "On Stage 2");
        Python py = Python.getInstance();
        PyObject pyObject = py.getModule("modelCreator");

// Assuming you have a flattened array named 'flattenedArray'
        PyObject funcResult = pyObject.callAttr("send_data", flattenedArray);


// Use the result from the Python function
        String pythonResult = funcResult.toString();
        Log.d("Mithun", pythonResult);
    }








//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        output = findViewById(R.id.output);
//
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://apistocks.p.rapidapi.com/monthly?symbol=AAPL&dateStart=2021-01-01&dateEnd=2021-07-31")
//                .get()
//                .addHeader("X-RapidAPI-Key", "05baf09f0cmsh227b9f321399e3ap11968ajsn9c0813e89941")
//                .addHeader("X-RapidAPI-Host", "apistocks.p.rapidapi.com")
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//
//    }



//            // Initialize UI components
//            private void setUp(){
//                output = findViewById(R.id.output);
//                button = findViewById(R.id.button);
//                tickerET = findViewById(R.id.tickerET);
//                startET = findViewById(R.id.startET);
//                endET = findViewById(R.id.endET);
//           


//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        output = findViewById(R.id.output);
//        url = "https://apistocks.p.rapidapi.com/monthly?symbol=AAPL&dateStart=2021-01-01&dateEnd=2021-01-01";
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    double open = response.getDouble("Open");
//                    output.setText(String.valueOf(open));
//                }catch (Exception e){
//
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        Volley.newRequestQueue(this).add(request);
//    }




}