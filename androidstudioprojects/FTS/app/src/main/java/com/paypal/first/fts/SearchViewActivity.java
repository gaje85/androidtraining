package com.paypal.first.fts;
import java.util.Calendar;
 
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
 
public class SearchViewActivity extends Activity implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener {
 
    private ListView mListView;
    private SearchView searchView;
    private CustomersDbAdapter mDbHelper;
 
 //   private TextView inspectionDate;
    private TextView customerText;
    private TextView nameText;
    private TextView addressText;
    private TextView cityText;
    private TextView stateText;
    private TextView zipCodeText;
    private LocationManager mylocationManager;
    private Location mylocation;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mylocationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
        mylocation = mylocationManager.
                getLastKnownLocation(LocationManager.GPS_PROVIDER);
     //   mylocation = mylocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.i("test", " Loc   :" + mylocation);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
 
        mListView = (ListView) findViewById(R.id.list);
     //   inspectionDate = (TextView) findViewById(R.id.inspectionDate);
        displayDate();
 
        mDbHelper = new CustomersDbAdapter(this);
        mDbHelper.open();
 
        //Clean all Customers
        mDbHelper.deleteAllCustomers();
        //Add some Customer data as a sample
        mDbHelper.createCustomer("PIZZA1", "Pizza Hut", "1107 West Adams Boulevard", "", "Los Angeles", "CA", "90007");
        mDbHelper.createCustomer("PIZZA2", "Pizza Hut", "1562 West Pico Boulevard", "", "Los Angeles", "CA", "90015");
        mDbHelper.createCustomer("PIZZA3", "Pizza Hut", "718 South Los Angeles Street", "", "Los Angeles", "CA", "90014");
        mDbHelper.createCustomer("PIZZA4", "Pizza Hut", "2542 West Temple Street", "", "Los Angeles", "CA", "90026");
        mDbHelper.createCustomer("PIZZA5", "Pizza Hut", "4329 North Figueroa Street", "", "Los Angeles", "CA", "90065");
        mDbHelper.createCustomer("PIZZA6", "Pizza Hut", "4351 South Central Avenue", "", "Los Angeles", "CA", "90011");
        mDbHelper.createCustomer("SUB1", "Subway", "975 West Jefferson", "", "Los Angeles", "CA", "90007");
        mDbHelper.createCustomer("SUB2", "Subway", "2805 South Figueroa Street", "", "Los Angeles", "CA", "90007");
        mDbHelper.createCustomer("SUB3", "Subway", "198 South Vermont Avenue", "", "Los Angeles", "CA", "90004");
        mDbHelper.createCustomer("SUB4", "Subway", "504 West Olympic Boulevard", "", "Los Angeles", "CA", "90015");
 
    }
 
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDbHelper  != null) {
            mDbHelper.close();
        }
    }
 
    public boolean onQueryTextChange(String newText) {
        showResults(newText + "*");
        return false;
    }
 
    public boolean onQueryTextSubmit(String query) {
        showResults(query + "*");
        return false;
    }
 
    public boolean onClose() {
        showResults("");
        return false;
    }
 
    private void showResults(String query) {
 
        Cursor cursor = mDbHelper.searchCustomer((query != null ? query.toString() : "@@@@"));
 
        if (cursor == null) {
            //
        } else {
            // Specify the columns we want to display in the result
            String[] from = new String[] {
                    CustomersDbAdapter.KEY_CUSTOMER,
                    CustomersDbAdapter.KEY_NAME,
                    CustomersDbAdapter.KEY_ADDRESS,
                    CustomersDbAdapter.KEY_CITY,
                    CustomersDbAdapter.KEY_STATE,
                    CustomersDbAdapter.KEY_ZIP};   
 
            // Specify the Corresponding layout elements where we want the columns to go
            int[] to = new int[] {     R.id.scustomer,
                    R.id.sname,
                    R.id.saddress,
                    R.id.scity,
                    R.id.sstate,
                    R.id.szipCode};
 
            // Create a simple cursor adapter for the definitions and apply them to the ListView
            SimpleCursorAdapter customers = new SimpleCursorAdapter(this,R.layout.customerresult, cursor, from, to);
            mListView.setAdapter(customers);
 
            // Define the on-click listener for the list items
            mListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the cursor, positioned to the corresponding row in the result set
                    Cursor cursor = (Cursor) mListView.getItemAtPosition(position);
 
                    // Get the state's capital from this row in the database.
                    String customer = cursor.getString(cursor.getColumnIndexOrThrow("customer"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                    String city = cursor.getString(cursor.getColumnIndexOrThrow("city"));
                    String state = cursor.getString(cursor.getColumnIndexOrThrow("state"));
                    String zipCode = cursor.getString(cursor.getColumnIndexOrThrow("zipCode"));
                
                    //Check if the Layout already exists
                    LinearLayout customerLayout = (LinearLayout)findViewById(R.id.customerLayout);
                    if(customerLayout == null){
                        //Inflate the Customer Information View 
                        LinearLayout leftLayout = (LinearLayout)findViewById(R.id.rightLayout);
                        View customerInfo = getLayoutInflater().inflate(R.layout.customerinfo, leftLayout, false);
                        leftLayout.addView(customerInfo);
                    }
 
                    //Get References to the TextViews
                    customerText = (TextView) findViewById(R.id.customer);
                    nameText = (TextView) findViewById(R.id.name);
                    addressText = (TextView) findViewById(R.id.address);
                    cityText = (TextView) findViewById(R.id.city);
                    stateText = (TextView) findViewById(R.id.state);
                    zipCodeText = (TextView) findViewById(R.id.zipCode);
                
                    // Update the parent class's TextView
                    customerText.setText(customer);
                    nameText.setText(name);
                    addressText.setText(address);
                    cityText.setText(city);
                    stateText.setText(state);
                    zipCodeText.setText(zipCode);
            
                    searchView.setQuery("",true);
                }
            });
        }
    }
 
    private void displayDate() {
 
        final Calendar c = Calendar.getInstance();
 
  /*      inspectionDate.setText(
                new StringBuilder()
                // Month is 0 based so add 1
                .append(c.get(Calendar.MONTH) + 1).append("/")
                .append(c.get(Calendar.DAY_OF_MONTH)).append("/")
                .append(c.get(Calendar.YEAR)).append(" ")); */
    }
}
