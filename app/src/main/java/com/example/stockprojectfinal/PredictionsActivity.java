package com.example.stockprojectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PredictionsActivity extends AppCompatActivity {
    EditText nameET;
    Stock currentStock;
    EditText predictionsET;

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


    private final static String TAG = "predict";

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

    public void findPlacesByText(String text, String language, String ticker, Callback callback) {
        getRapidApiAsync(String.format(Locale.US, "https://%s/monthly?symbol=" + ticker + "&dateStart=2017-05-01&dateEnd=2021-07-31", RAPIDAPI_TRUEWAY_PLACES_HOST, text, language),
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
        setContentView(R.layout.activity_predictions);

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        nameET = findViewById(R.id.stockNameTV);

        // gets intent from ViewAllMemoriesActivity and retrieves the selected Memory
        // the viewer wanted to see.
        Intent intent = getIntent();
        currentStock = intent.getParcelableExtra("chosen stock");

        // Sets the name and desc from the chosen memory
        nameET.setText(currentStock.getTicker());

        String myTicker = currentStock.getTicker();



        findPlacesByText("Children's Creativity Museum", "en", myTicker,
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


        PyObject funcResult = pyObject.callAttr("send_data", flattenedArray);


        //result from the Python function
        String pythonResult = funcResult.toString();
        Log.d("Mithun", pythonResult);

        predictionsET = findViewById(R.id.predictionsTV);
        predictionsET.setText("$" + pythonResult);

    }


    public void deleteStock(View v) {
        Log.d("Denna", "deleting stock " + currentStock.getTicker());
        SignInActivity.firebaseHelper.deleteData(currentStock, this);
    }




    public void goBack(View view) {
//        EditText editText = findViewById(R.id.enteredName);
//        String name = editText.getText().toString();
        Intent intent = new Intent(this, ViewStocksActivity.class);
//        intent.putExtra("NAME", name);
        startActivity(intent);
    }











}
