package com.example.stockprojectfinal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StockAdapter extends ArrayAdapter<Stock> {
    public StockAdapter(Context context, ArrayList<Stock> stockList) {
        super(context, 0, stockList);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Stock myStock = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_stock_single_row_layout, parent, false);
        }
        // Step 1: Connect XML references for YOUR custom view
        TextView name = convertView.findViewById(R.id.stockName);

        Log.d("bba", myStock.getTicker());

        // Step 2: Populate the specific row data into the variables you made in step 1
        name.setText(myStock.getTicker());

        // Step 3: Return the completed view to render on screen
        return convertView;
    }
}

