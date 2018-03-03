package com.example.mohammad.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mohammad.inventoryapp.data.InventoryContract.ProductEntry;

/**
 * Created by mohammad on 16/02/18.
 */

public class ProductDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "inventory.db";

    private final String TEXT_TYPE = " TEXT ";
    private final String INTEGER_TYPE = " INTEGER ";
    private final String NOT_NULL = " NOT NULL ";
    private final String PRIMARY_KEY = " PRIMARY KEY ";
    private final String AUTO_INCREMENT = " AUTOINCREMENT ";
    private final String DEFAULT_VALUE = " DEFAULT '000 0000 0000' ";
    private final String COMA_SEP = ",";

    private final String SQL_CREATE_ENTRIES = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("
            + ProductEntry._ID + INTEGER_TYPE + PRIMARY_KEY + AUTO_INCREMENT + COMA_SEP
            + ProductEntry.COLUMN_PRODUCT_NAME + TEXT_TYPE + NOT_NULL + COMA_SEP
            + ProductEntry.COLUMN_PRODUCT_PRICE + INTEGER_TYPE + NOT_NULL + COMA_SEP
            + ProductEntry.COLUMN_PRODUCT_QUANTITY + INTEGER_TYPE + NOT_NULL + COMA_SEP
            + ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME + TEXT_TYPE + COMA_SEP
            + ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER + TEXT_TYPE + DEFAULT_VALUE + COMA_SEP
            + ProductEntry.COLUMN_PRODUCT_IMAGE + TEXT_TYPE + ");";

    private final String SQL_DELETE_ENTRIES = "DELETE TABLE IF EXISTS " + ProductEntry.TABLE_NAME;


    public ProductDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
