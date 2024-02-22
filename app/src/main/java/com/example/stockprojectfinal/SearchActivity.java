package com.example.stockprojectfinal;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    // creating variables for
    // our ui components.
    private RecyclerView courseRV;

    // variable for our adapter
    // class and array list
    private CourseAdapter adapter;
    private ArrayList<Stock> courseModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // initializing our variables.
        courseRV = findViewById(R.id.idRVCourses);

        // calling method to
        // build recycler view.
        buildRecyclerView();
    }

    // calling on create option menu
    // layout to inflate our menu file.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Stock> filteredlist = new ArrayList<Stock>();

        // running a for loop to compare elements.
        for (Stock item : courseModelArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getTicker().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {

        // below line we are creating a new array list
        courseModelArrayList = new ArrayList<Stock>();

        // below line is to add data to our array list.


        courseModelArrayList.add(new Stock("AAPL"));
        courseModelArrayList.add(new Stock("ADBE"));
        courseModelArrayList.add(new Stock("ADI"));
        courseModelArrayList.add(new Stock("ADP"));
        courseModelArrayList.add(new Stock("ADSK"));
        courseModelArrayList.add(new Stock("ALGN"));
        courseModelArrayList.add(new Stock("ALNY"));
        courseModelArrayList.add(new Stock("AMGN"));
        courseModelArrayList.add(new Stock("ANSS"));
        courseModelArrayList.add(new Stock("APPF"));
        courseModelArrayList.add(new Stock("ARGX"));
        courseModelArrayList.add(new Stock("ASML"));
        courseModelArrayList.add(new Stock("ATRI"));
        courseModelArrayList.add(new Stock("AVGO"));
        courseModelArrayList.add(new Stock("AXON"));
        courseModelArrayList.add(new Stock("AZPN"));
        courseModelArrayList.add(new Stock("BGNE"));
        courseModelArrayList.add(new Stock("BIIB"));
        courseModelArrayList.add(new Stock("BKNG"));
        courseModelArrayList.add(new Stock("CACC"));
        courseModelArrayList.add(new Stock("CAR"));
        courseModelArrayList.add(new Stock("CASY"));
        courseModelArrayList.add(new Stock("CDNS"));
        courseModelArrayList.add(new Stock("CDW"));
        courseModelArrayList.add(new Stock("CHRD"));
        courseModelArrayList.add(new Stock("CHTR"));
        courseModelArrayList.add(new Stock("CME"));
        courseModelArrayList.add(new Stock("COKE"));
        courseModelArrayList.add(new Stock("COO"));
        courseModelArrayList.add(new Stock("COST"));
        courseModelArrayList.add(new Stock("CRVL"));
        courseModelArrayList.add(new Stock("CRWD"));
        courseModelArrayList.add(new Stock("CSWI"));
        courseModelArrayList.add(new Stock("CTAS"));
        courseModelArrayList.add(new Stock("CVCO"));
        courseModelArrayList.add(new Stock("CYBR"));
        courseModelArrayList.add(new Stock("DHIL"));
        courseModelArrayList.add(new Stock("DJCO"));
        courseModelArrayList.add(new Stock("DUOL"));
        courseModelArrayList.add(new Stock("EQIX"));
        courseModelArrayList.add(new Stock("ERIE"));
        courseModelArrayList.add(new Stock("ESGR"));
        courseModelArrayList.add(new Stock("ESLT"));
        courseModelArrayList.add(new Stock("FANG"));
        courseModelArrayList.add(new Stock("FCNCA"));
        courseModelArrayList.add(new Stock("FFIV"));
        courseModelArrayList.add(new Stock("FIVE"));
        courseModelArrayList.add(new Stock("FSLR"));
        courseModelArrayList.add(new Stock("FSV"));
        courseModelArrayList.add(new Stock("HIFS"));
        courseModelArrayList.add(new Stock("HON"));
        courseModelArrayList.add(new Stock("ICLR"));
        courseModelArrayList.add(new Stock("IDXX"));
        courseModelArrayList.add(new Stock("INTU"));
        courseModelArrayList.add(new Stock("ISRG"));
        courseModelArrayList.add(new Stock("ITIC"));
        courseModelArrayList.add(new Stock("JBHT"));
        courseModelArrayList.add(new Stock("JJSF"));
        courseModelArrayList.add(new Stock("JKHY"));
        courseModelArrayList.add(new Stock("KLAC"));
        courseModelArrayList.add(new Stock("KRTX"));
        courseModelArrayList.add(new Stock("LANC"));
        courseModelArrayList.add(new Stock("LECO"));
        courseModelArrayList.add(new Stock("LFUS"));
        courseModelArrayList.add(new Stock("LIN"));
        courseModelArrayList.add(new Stock("LPLA"));
        courseModelArrayList.add(new Stock("LRCX"));
        courseModelArrayList.add(new Stock("LSTR"));
        courseModelArrayList.add(new Stock("LULU"));
        courseModelArrayList.add(new Stock("MANH"));
        courseModelArrayList.add(new Stock("MAR"));
        courseModelArrayList.add(new Stock("MDB"));
        courseModelArrayList.add(new Stock("MDGL"));
        courseModelArrayList.add(new Stock("MEDP"));
        courseModelArrayList.add(new Stock("MELI"));
        courseModelArrayList.add(new Stock("META"));
        courseModelArrayList.add(new Stock("MKTX"));
        courseModelArrayList.add(new Stock("MNDY"));
        courseModelArrayList.add(new Stock("MORN"));
        courseModelArrayList.add(new Stock("MPWR"));
        courseModelArrayList.add(new Stock("MSFT"));
        courseModelArrayList.add(new Stock("MSTR"));
        courseModelArrayList.add(new Stock("NDSN"));
        courseModelArrayList.add(new Stock("NFLX"));
        courseModelArrayList.add(new Stock("NICE"));
        courseModelArrayList.add(new Stock("NSIT"));
        courseModelArrayList.add(new Stock("NVDA"));
        courseModelArrayList.add(new Stock("NWLI"));
        courseModelArrayList.add(new Stock("NXPI"));
        courseModelArrayList.add(new Stock("ODFL"));
        courseModelArrayList.add(new Stock("OLED"));
        courseModelArrayList.add(new Stock("ORLY"));
        courseModelArrayList.add(new Stock("PANW"));
        courseModelArrayList.add(new Stock("PCTY"));
        courseModelArrayList.add(new Stock("PEP"));
        courseModelArrayList.add(new Stock("PODD"));
        courseModelArrayList.add(new Stock("POOL"));
        courseModelArrayList.add(new Stock("PTC"));
        courseModelArrayList.add(new Stock("QLYS"));
        courseModelArrayList.add(new Stock("REGN"));
        courseModelArrayList.add(new Stock("RGEN"));
        courseModelArrayList.add(new Stock("ROP"));
        courseModelArrayList.add(new Stock("SAIA"));
        courseModelArrayList.add(new Stock("SBAC"));
        courseModelArrayList.add(new Stock("SGEN"));
        courseModelArrayList.add(new Stock("SMCI"));
        courseModelArrayList.add(new Stock("SNPS"));
        courseModelArrayList.add(new Stock("SPLK"));
        courseModelArrayList.add(new Stock("SPSC"));
        courseModelArrayList.add(new Stock("SWAV"));
        courseModelArrayList.add(new Stock("TEAM"));
        courseModelArrayList.add(new Stock("TMUS"));
        courseModelArrayList.add(new Stock("TSCO"));
        courseModelArrayList.add(new Stock("TSLA"));
        courseModelArrayList.add(new Stock("TTEK"));
        courseModelArrayList.add(new Stock("TTWO"));
        courseModelArrayList.add(new Stock("TXN"));
        courseModelArrayList.add(new Stock("UFPT"));
        courseModelArrayList.add(new Stock("ULTA"));
        courseModelArrayList.add(new Stock("USLM"));
        courseModelArrayList.add(new Stock("UTHR"));
        courseModelArrayList.add(new Stock("VRSK"));
        courseModelArrayList.add(new Stock("VRSN"));
        courseModelArrayList.add(new Stock("VRTS"));
        courseModelArrayList.add(new Stock("VRTX"));
        courseModelArrayList.add(new Stock("WDAY"));
        courseModelArrayList.add(new Stock("WDFC"));
        courseModelArrayList.add(new Stock("WINA"));
        courseModelArrayList.add(new Stock("WING"));
        courseModelArrayList.add(new Stock("WIRE"));
        courseModelArrayList.add(new Stock("WTW"));
        courseModelArrayList.add(new Stock("ZBRA"));
        courseModelArrayList.add(new Stock("ZS"));






        // initializing our adapter class.
        adapter = new CourseAdapter(courseModelArrayList, SearchActivity.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        courseRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        courseRV.setAdapter(adapter);
    }

//    public void addStock() {
//
//        Stock m = new Stock(courseModelArrayList.get(position).getTicker());
//        SignInActivity.firebaseHelper.addData(m);
//
//        Intent intent = new Intent(this, ViewStocksActivity.class);
//        startActivity(intent);
//
//
//    }
    
}
