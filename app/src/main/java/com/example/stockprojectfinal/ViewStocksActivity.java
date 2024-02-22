package com.example.stockprojectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



public class ViewStocksActivity extends AppCompatActivity {


    public void logOutClicked(View v) {
        SignInActivity.firebaseHelper.logOutUser();
        Log.i("Denna", "user logged out");
        Intent intent = new Intent(ViewStocksActivity.this, SignInActivity.class);
        startActivity(intent);
    }



    public void switchScreens(View view) {
//        EditText editText = findViewById(R.id.enteredName);
//        String name = editText.getText().toString();
//        SignInActivity.firebaseHelper.addData(new Stock("AAPL"));

        Intent intent = new Intent(this, SearchActivity.class);
//        intent.putExtra("NAME", name);
        startActivity(intent);
    }

    private ListView myMemoryListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stocks);


//        ColorDrawable seed = new ColorDrawable(this.getResources().getColor(R.color.seed));
//        myMemoryListView.setDivider(seed);
//        myMemoryListView.setDividerHeight(8);



        Intent intent = getIntent();
        // Gets the particular ArrayList that was passed in of the specific type.
        // In this case, of type Food.
//        Stock myStock= new Stock();
        
        
        ArrayList<Stock> dataToDisplay = SignInActivity.firebaseHelper.getStockArrayList();
//        Log.d("joe4", dataToDisplay.get(0).getTicker());
//        ArrayList<Stock> test = new ArrayList<Stock>();
//        test.add(myStock);
//        dataToDisplay.add(myStock);

        // The ArrayAdapter is what will take the data from the ArrayList and feed it to the ListView
        // You can create your own XML layout to describe how each row will look. This is the default layout,
        // calling the toString()
//        ArrayAdapter<Food> listAdapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_list_item_1, dataToDisplay);
        // This finds the listView and then adds the adapter to bind the data to this view
        ListView listView = (ListView) findViewById(R.id.allStocksListView);
//        listView.setAdapter(listAdapter);


        // OR use the custom Adapter to design a custom row
        StockAdapter myStockAdapter = new StockAdapter(this, dataToDisplay);

        // have the adapter refer to your custom class
        listView.setAdapter(myStockAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Creates an intent to go from the Specific Category to the specific Detail
                Intent intent = new Intent(ViewStocksActivity.this, PredictionsActivity.class);
                // Sends the specific object at index i to the Detail activity
                // In this case, it is sending the particular Food object
                intent.putExtra("chosen stock", dataToDisplay.get(position));

                startActivity(intent);
            }
        });









//        // find listView in xml
//        myMemoryListView = findViewById(R.id.allMemoriesListView);
//
//
//        ColorDrawable seed = new ColorDrawable(this.getResources().getColor(R.color.seed));
//        myMemoryListView.setDivider(seed);
//        myMemoryListView.setDividerHeight(8);
//
//
//        // get ArrayList of data from firebase
//        ArrayList<Memory> myList = SignInActivity.firebaseHelper.getMemoryArrayList();
//        // bind data to the ArrayAdapter (this is a default adapter
//        // The text shown is based on the Memory class toString
//        ArrayAdapter<Memory> listAdapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_list_item_1, myList);
//        // attaches the listAdapter to my listView
//        myMemoryListView.setAdapter(listAdapter);
//        // if did custom array set up, use this one

//         Create listener to listen for when a Food from the specific Category list is clicked on
//        myMemoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                // Creates an intent to go from the Specific Category to the specific Detail
//                Intent intent = new Intent(ViewAllMemoriesActivity.this, EditMemoryActivity2.class);
//                // Sends the specific object at index i to the Detail activity
//                // In this case, it is sending the particular Memory object
//                intent.putExtra("chosen memory", myList.get(position));
//                startActivity(intent);
//            }
//        });




    }

}

