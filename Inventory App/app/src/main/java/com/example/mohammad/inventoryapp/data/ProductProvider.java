package com.example.mohammad.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.mohammad.inventoryapp.R;
import com.example.mohammad.inventoryapp.data.InventoryContract.ProductEntry;

/**
 * Created by mohammad on 3/2/18.
 */

public class ProductProvider extends ContentProvider {

    private static final int PRODUCTS_URI_CODE = 100;
    private static final int PRODUCT_ID_URI_CODE = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY , ProductEntry.PRODUCTS_PATH , PRODUCTS_URI_CODE);

        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY , ProductEntry.PRODUCTS_PATH + "/#" , PRODUCT_ID_URI_CODE);

    }

    private final static String VALIDATION_SUCCEED = "Done Successfully";

    private ProductDBHelper mProductDBHelper;
    private String LOG_TAG = ProductProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        //initialize the database to can get and SQLiteDatabase object to manipulate with database.
        mProductDBHelper = new ProductDBHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //create SQLiteDatabase object which has an access to the database.
        SQLiteDatabase productsDatabase = mProductDBHelper.getReadableDatabase();

        //define Cursor object to get the query in it
        Cursor cursor;

        //use sUriMatcher to determine which uri is requested.
        int requestedUriCode = sUriMatcher.match(uri);

        //handle each case of  the uri code
        switch (requestedUriCode){
            case PRODUCTS_URI_CODE:{

                //use the productsDatabase.query() method to get cursor with the products table data
                cursor = productsDatabase.query(ProductEntry.TABLE_NAME , projection , selection , selectionArgs , null , null , sortOrder);
                break;
            }
            case PRODUCT_ID_URI_CODE:{
                //for single row query
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                //use the productsDatabase.query() method to get cursor with the products table data
                cursor = productsDatabase.query(ProductEntry.TABLE_NAME , projection , selection , selectionArgs , null , null , sortOrder);
                break;
            }
            default:{
                throw new IllegalArgumentException("Can't Query UnKnown Uri");
            }
        }

        //setting the notification uti
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS_URI_CODE:
                return ProductEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ID_URI_CODE:
                return ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }

    @Override
    public Uri insert( Uri uri, ContentValues values) {

        //check field validation
        if(!checkValidation(values).equals(VALIDATION_SUCCEED)){
            throw new IllegalArgumentException(checkValidation(values));
        }

        //use sUriMatcher to determine which uri is requested.
        int requestedUriCode = sUriMatcher.match(uri);

        //notify that the database is changed
        getContext().getContentResolver().notifyChange(uri , null);

        switch (requestedUriCode){
            case PRODUCTS_URI_CODE:{
                return insertProduct(uri , values);
            }
            default:{
                throw new IllegalArgumentException("Can't Query UnKnown Uri");
            }
        }

    }

    /**
     * helper method to insert new product in the database
     * @param uri the requested ContentProvider uri which has access to the specified database.
     * @param values the data to be inserted to the database.
     * @return the uri of the new inserted row.
     */
    private Uri insertProduct(Uri uri , ContentValues values){

        SQLiteDatabase productsDatabase = mProductDBHelper.getWritableDatabase();

        //call the productsDatabase.insert() method to insert the data in the products table and store the id of new row in rowId variable.
        long rowId = productsDatabase.insert(ProductEntry.TABLE_NAME , null , values);

        //check if the row inserted successfully or not.
        if (rowId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //return the uri of the new row by appending the PRODUCTS_URI by the id of the row.
        return ContentUris.withAppendedId(uri , rowId);
    }

    /**
     * checks the fields data before inserting it to the database.
     * @param values the content values object which have the fields data
     * @return string "Done Successfully" if there is no validation error or return descriptive error message.
     */
    private String checkValidation(ContentValues values){

        if(isEmpty(values.getAsString(ProductEntry.COLUMN_PRODUCT_NAME))){
            return getContext().getString(R.string.name_validation_error_message);
        }
        else if(values.getAsInteger(ProductEntry.COLUMN_PRODUCT_PRICE) < 1){
            return getContext().getString(R.string.fields_validation_error_message);
        }
        else if(values.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUANTITY) < 1){
            return getContext().getString(R.string.fields_validation_error_message);
        }else {
            return VALIDATION_SUCCEED;
        }
    }

    /**
     * check if the string is empty or equal to null
     * @param s String Object to be checked
     * @return true if it empty or equal to null or false otherwise.
     */
    private boolean isEmpty(String s){
        return s == null || s.length() < 1;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase productsDatabase = mProductDBHelper.getWritableDatabase();

        int requestedUriCode = sUriMatcher.match(uri);

        int rowsDeleted = 0;

        switch (requestedUriCode){
            case PRODUCTS_URI_CODE:{
                rowsDeleted = productsDatabase.delete(ProductEntry.TABLE_NAME , selection , selectionArgs);
                // If 1 or more rows were deleted, then notify all listeners that the data at the
                // given URI has changed
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            }
            case PRODUCT_ID_URI_CODE:{
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                rowsDeleted = productsDatabase.delete(ProductEntry.TABLE_NAME , selection , selectionArgs);
                // If 1 or more rows were deleted, then notify all listeners that the data at the
                // given URI has changed
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            }
            default:{
                throw new IllegalArgumentException("Delete is not supported for URI: " + uri);
            }
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int requestedUriCode = sUriMatcher.match(uri);

        switch (requestedUriCode){
            case PRODUCTS_URI_CODE:{
                return updateProducts(uri , values , selection , selectionArgs);
            }
            case PRODUCT_ID_URI_CODE:{
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                return updateProducts(uri , values , selection , selectionArgs);
            }
            default:{
                throw new IllegalArgumentException("Update is not supported for URI: " + uri);
            }
        }

    }

    private int updateProducts(Uri uri , ContentValues values , String selection , String[] selectionArgs){

        if(values.containsKey(ProductEntry.COLUMN_PRODUCT_NAME)){
            String name = values.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
            if(isEmpty(name)){
                throw new IllegalArgumentException(getContext().getString(R.string.fields_validation_error_message));
            }
        }

        if(values.containsKey(ProductEntry.COLUMN_PRODUCT_QUANTITY)){
            int quantity = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            if(quantity < 1){
                throw new IllegalArgumentException(getContext().getString(R.string.fields_validation_error_message));
            }
        }

        if(values.containsKey(ProductEntry.COLUMN_PRODUCT_PRICE)){
            int price = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_PRICE);
            if(price < 1){
                throw new IllegalArgumentException(getContext().getString(R.string.fields_validation_error_message));
            }
        }

        if (values.size() == 0){
            return -1;
        }

        SQLiteDatabase productsDatabase = mProductDBHelper.getWritableDatabase();

        int rowsUpdated = productsDatabase.update(ProductEntry.TABLE_NAME , values, selection , selectionArgs);

        if (rowsUpdated != 0) {
            //notify that the database is changed
            getContext().getContentResolver().notifyChange(uri , null);
        }
        return rowsUpdated;
    }
}
