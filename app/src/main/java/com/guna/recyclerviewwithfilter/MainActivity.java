package com.guna.recyclerviewwithfilter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;

import com.guna.adapter.NumbersAdapter;
import com.guna.entity.Number;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView list;

    ArrayList<Number> numbers;
    private NumbersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);

        numbers = new ArrayList<>();
        String ONEs[] = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN"};
        String TENs[] = {"ZERO", "TEN", "TWENTY", "THIRTY", "FOURTY", "FIFTY", "SIXTY",  "SEVENTY", "EIGHTY", "NINETY", "HUNDRED"};
        String HUNDREDS[] = {"ZERO", "HUNDRED", "TWO HUNDRED", "THREE HUNDRED", "FOUR HUNDRED", "FIVE HUNDRED", "SIX HUNDRED", "SEVEN HUNDRED", "EIGHT HUNDRED", "NINE HUNDRED", "THOUSAND"};
        for (int i = 0; i <= 10; i++) {
            Number number = new Number();
            number.setONEs(i+"");
            number.setTENs(i * 10+"");
            number.setHUNDREDs(i * 100+"");
            number.setTextONEs(ONEs[i]);
            number.setTextTENs(TENs[i]);
            number.setTextHUNDREDs(HUNDREDS[i]);
            this.numbers.add(number);
        }
        adapter = new NumbersAdapter(this.numbers);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Number> filteredModelList = filter(numbers, newText);
        Log.v("App", newText + ", " + numbers.size() + ", " + filteredModelList.size());
        adapter.animateTo(filteredModelList);
        list.scrollToPosition(0);
        return true;
    }

    private List<Number> filter(List<Number> numbers, String query) {
        query = query.toLowerCase();

        ArrayList<Number> filteredCompanyList = new ArrayList<>();
        for (Number client : numbers) {
            final String textOne = client.getTextONEs().toLowerCase();
            final String textTen = client.getTextTENs().toLowerCase();
            final String textHundred = client.getTextHUNDREDs().toLowerCase();
            final String TENs = client.getTENs();
            final String ONEs = client.getONEs();
            final String HUNDREDs = client.getHUNDREDs();
            if (textOne.contains(query) || TENs.contains(query) || ONEs.contains(query) || HUNDREDs.contains(query) || textTen.contains(query) || textHundred.contains(query)) {
                filteredCompanyList.add(client);
            }
        }
        return filteredCompanyList;
    }
}
