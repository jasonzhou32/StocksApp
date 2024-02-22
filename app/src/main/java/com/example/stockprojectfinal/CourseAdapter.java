package com.example.stockprojectfinal;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private static ArrayList<Stock> courseModelArrayList;

    static String currentTicker;
    static int globalposition;

    private static Context context;

    // creating a constructor for our variables.
    public CourseAdapter(ArrayList<Stock> courseModelArrayList, Context context) {
        this.courseModelArrayList = courseModelArrayList;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Stock> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        courseModelArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Stock model = courseModelArrayList.get(position);
        holder.courseNameTV.setText(model.getTicker());


    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return courseModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private final TextView courseNameTV;

        Stock myStock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);

            context = itemView.getContext();

            itemView.findViewById(R.id.idTVDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Stock m = new Stock(courseNameTV.toString());
//                    SignInActivity.firebaseHelper.addData(m);
                    globalposition = getAdapterPosition();
                    Log.d("joe2", courseModelArrayList.get(globalposition).getTicker());
                    Stock m = new Stock(courseModelArrayList.get(globalposition).getTicker());
                    Log.d("joe3", m.getTicker());
                    SignInActivity.firebaseHelper.addData(m);

                    final Intent intent;
                    intent =  new Intent(context, ViewStocksActivity.class);
                    context.startActivity(intent);

                }
            });

        }
    }



}
