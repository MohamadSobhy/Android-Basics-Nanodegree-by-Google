package com.example.mohammad.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohammad.inventoryapp.data.InventoryContract.ProductEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ListView mProductsListView;
    private ProductCursorAdapter mCursorAdapter;
    private final static int PRODUCT_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mProductsListView = (ListView) findViewById(R.id.list_view_products);

        View emptyStateView = findViewById(R.id.empty_view);

        mProductsListView.setEmptyView(emptyStateView);

        mCursorAdapter = new ProductCursorAdapter(this , null);
        mProductsListView.setAdapter(mCursorAdapter);

        mProductsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToEditActivity = new Intent(MainActivity.this, EditorActivity.class);

                Uri columnURI = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
                goToEditActivity.setData(columnURI);

                startActivity(goToEditActivity);
            }
        });

        getSupportLoaderManager().initLoader(PRODUCT_LOADER, null, this);
    }

    /**
     * add new product to the database
     */
    private void insertDummyProduct(){

        //create ContentValues object to store the data of all attributes of the new row
        ContentValues newProductInfo = new ContentValues();

        //put the data of the row using put() method which save the data in key-value pairs.
        newProductInfo.put(ProductEntry.COLUMN_PRODUCT_NAME , "Infinix Hot Note Pro");
        newProductInfo.put(ProductEntry.COLUMN_PRODUCT_PRICE , 1400);
        newProductInfo.put(ProductEntry.COLUMN_PRODUCT_QUANTITY , 2);
        newProductInfo.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME , "Infinix");
        newProductInfo.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER , "01155861582");

        Uri newRowUri = getContentResolver().insert(ProductEntry.CONTENT_URI, newProductInfo);

        if(newRowUri != null){
            Toast.makeText(this, getString(R.string.product_saved_message), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getString(R.string.product_not_saved_message), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * deletes all data from the database
     */
    private void deleteAllProducts(){

        int rowsDeleted = getContentResolver().delete(ProductEntry.CONTENT_URI, null,null);

        if(rowsDeleted != 0){
            Toast.makeText(this, getString(R.string.deleted_products, rowsDeleted), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getString(R.string.no_deleted_products), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_menu , menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.insert_dummy_data:
                insertDummyProduct();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.delete_all_products:
                deleteAllProducts();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ProductEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
