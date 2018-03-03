/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.mohammad.inventoryapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mohammad.inventoryapp.data.InventoryContract.ProductEntry;
import com.example.mohammad.inventoryapp.data.ProductDBHelper;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Allows user to create a new product or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    /** EditText field to enter the product's name */
    private EditText mNameEditText;

    /** EditText field to enter the product's quantity */
    private EditText mQuantityEditText;

    /** EditText field to enter the product's price */
    private EditText mPriceEditText;

    /** EditText field to enter the product's supplier name */
    private EditText mSupplierNameEditText;

    /** EditText field to enter the product's supplier name */
    private EditText mSupplierPhoneNumberEditText;

    /** ImageButton to choose the product's image */
    private ImageButton mProductImage;

    /** Button to add increase product quantity */
    private Button mAddProductButton;

    /** Button to add decrease product quantity */
    private Button mRejectProductButton;

    private final static int RESULT_LOAD_IMAGE = 2;


    private Uri currentProductUri;
    private static final int PRODUCT_LOADER_ID = 1;
    private boolean mProductHasChanged = false;
    private String imagePath;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mProductHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mQuantityEditText = (EditText) findViewById(R.id.edit_product_quantity);
        mPriceEditText = (EditText) findViewById(R.id.edit_product_price);
        mSupplierNameEditText = (EditText) findViewById(R.id.edit_supplier_name);
        mSupplierPhoneNumberEditText = (EditText) findViewById(R.id.edit_product_supplier_number);
        mProductImage = (ImageButton) findViewById(R.id.choose_product_image_button);
        mAddProductButton = (Button) findViewById(R.id.add_product_button);
        mRejectProductButton = (Button) findViewById(R.id.reject_product_button);


        //get the intent to get the uri of the product
        Intent i = getIntent();

        //getting the uri of the product
        currentProductUri = i.getData();

        //check which mode that the user chose (Add or Edit) product
        if(currentProductUri == null){
            setTitle(getString(R.string.activity_title_new_products));

            invalidateOptionsMenu();

            mAddProductButton.setVisibility(View.INVISIBLE);
            mRejectProductButton.setVisibility(View.INVISIBLE);
            mQuantityEditText.setEnabled(true);
        }else {
            setTitle(getString(R.string.activity_title_edit_products));

            mAddProductButton.setVisibility(View.VISIBLE);
            mRejectProductButton.setVisibility(View.VISIBLE);
            mQuantityEditText.setEnabled(false);


            getSupportLoaderManager().initLoader(PRODUCT_LOADER_ID, null, this);
        }


        mNameEditText.setOnTouchListener(mTouchListener);
        mQuantityEditText.setOnTouchListener(mTouchListener);
        mPriceEditText.setOnTouchListener(mTouchListener);
        mSupplierPhoneNumberEditText.setOnTouchListener(mTouchListener);
        mSupplierNameEditText.setOnTouchListener(mTouchListener);
        mProductImage.setOnTouchListener(mTouchListener);

        mProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create intent to open the gallery to choose image
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        mAddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(mQuantityEditText.getText().toString());
                mQuantityEditText.setText(String.valueOf(quantity + 1));
            }
        });

        mRejectProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(mQuantityEditText.getText().toString());
                mQuantityEditText.setText(String.valueOf(quantity - 1));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check for the desired intent and check if the image selected successfully or not.
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            //get the image Uri
            Uri selectedImage = data.getData();
            //choose the column to be selected from database
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            //query for the selected image to get it's path
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            // String imagePath contains the path of selected Image
            imagePath = cursor.getString(columnIndex);


            File file = new File(imagePath);
            Picasso.with(this).load(file).into(mProductImage);
            cursor.close();

        }
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the product.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        // If the product hasn't changed, continue with handling back button press
        if (!mProductHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void insertNewProduct(){

        Uri newRowUri = null;
        try {

            //get the data of the attributes from the fields.
            String productName = mNameEditText.getText().toString().trim();
            String supplierName = mSupplierNameEditText.getText().toString().trim();
            int quantityValue = Integer.parseInt(mQuantityEditText.getText().toString().trim());
            String supplierPhoneNumber = mSupplierPhoneNumberEditText.getText().toString().trim();
            int priceValue = Integer.parseInt(mPriceEditText.getText().toString().trim());


            //create ContentValues object to store the data of all attributes of the new row
            ContentValues newRowData = new ContentValues();

            //put the data of the row using put() method which save the data in key-value pairs.
            newRowData.put(ProductEntry.COLUMN_PRODUCT_NAME , productName);
            newRowData.put(ProductEntry.COLUMN_PRODUCT_QUANTITY , quantityValue);
            newRowData.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER , supplierPhoneNumber);
            newRowData.put(ProductEntry.COLUMN_PRODUCT_PRICE , priceValue);
            newRowData.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
            newRowData.put(ProductEntry.COLUMN_PRODUCT_IMAGE, imagePath);

            //use insert() method in the ContentResolver class to insert data stored in ContentValues into database.
            //this method will return the Uri of the row which inserted if it correctly work or will return null if there is an error occurred.
            newRowUri = getContentResolver().insert(ProductEntry.CONTENT_URI , newRowData);
        }catch (NumberFormatException ex){
            Toast.makeText(this, getString(R.string.fields_validation_error_message), Toast.LENGTH_SHORT).show();
            return;
        }catch (IllegalArgumentException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        if(newRowUri != null){
            Toast.makeText(this , getString(R.string.product_saved_message), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this , getString(R.string.product_not_saved_message) , Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the product.
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the product.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the product in the database.
     */
    private void deleteProduct() {
        // Only perform the delete if this is an existing product.
        if (currentProductUri != null) {
            // Call the ContentResolver to delete the product at the given content URI.
            // Pass in null for the selection and selection args because the currentProductUri
            // content URI already identifies the product that we want.
            int rowsDeleted = getContentResolver().delete(currentProductUri, null, null);
            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.no_deleted_products),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_product_successful), Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    private void updateProduct(){

        int rowsUpdated = 0;
        try {
            ContentValues values = new ContentValues();

            //get the data of the attributes from the fields.
            String productName = mNameEditText.getText().toString().trim();
            String supplierName = mSupplierNameEditText.getText().toString().trim();
            int quantityValue = Integer.parseInt(mQuantityEditText.getText().toString().trim());
            String supplierPhoneNumber = mSupplierPhoneNumberEditText.getText().toString().trim();
            int priceValue = Integer.parseInt(mPriceEditText.getText().toString().trim());

            //put the data of the row using put() method which save the data in key-value pairs.
            values.put(ProductEntry.COLUMN_PRODUCT_NAME , productName);
            values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY , quantityValue);
            values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER , supplierPhoneNumber);
            values.put(ProductEntry.COLUMN_PRODUCT_PRICE , priceValue);
            values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
            values.put(ProductEntry.COLUMN_PRODUCT_IMAGE, imagePath);

            rowsUpdated = getContentResolver().update(currentProductUri, values, null, null);

        }catch (NumberFormatException ex){
            Toast.makeText(this, getString(R.string.fields_validation_error_message), Toast.LENGTH_SHORT).show();
            return;
        }catch (IllegalArgumentException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        // Show a toast message depending on whether or not the update was successful.
        if (rowsUpdated == 0) {
            // If no rows were affected, then there was an error with the update.
            Toast.makeText(this, getString(R.string.editor_update_product_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_update_product_successful), Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    private void contactSupplier(){

        String supplierPhoneNumber = mSupplierPhoneNumberEditText.getText().toString().trim();

        if(!TextUtils.isEmpty(supplierPhoneNumber) &&
                !supplierPhoneNumber.equals(getString(R.string.default_phone_number)) &&
                !(supplierPhoneNumber.length() < 11) ){

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + supplierPhoneNumber));

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }else {

            Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:" + "sobhymohamad623@gmail.com"));
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "New order: " + mNameEditText.getText().toString().trim());

            String message = "We need to order new amount of: " + mNameEditText.getText().toString().trim() + "\n" +
                    "Please confirm that you can send to us ___ pieces." + "\n" +
                    "Thanks," + "\n";

            intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            startActivity(intent);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new product, hide the "Delete" menu item.
        if (currentProductUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete_product);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_insert_new_product:
                if(currentProductUri == null) {
                    insertNewProduct();
                }else {
                    updateProduct();
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete_product:
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home: {
                // If the product hasn't changed, continue with navigating up to parent activity
                // which is the {@link MainActivity}.
                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
            }
            case R.id.action_contact_supplier:{
                contactSupplier();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                currentProductUri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {

        if(data.moveToFirst()) {
            data.moveToFirst();
        }else {
            return;
        }

        int columnNameIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int columnQuantityIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int columnSupplierNameIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
        int columnPriceIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int columnSupplierPhoneIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER);
        int columnImageIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_IMAGE);

        String productName = data.getString(columnNameIndex);
        int productQuantity = data.getInt(columnQuantityIndex);
        String supplierName = data.getString(columnSupplierNameIndex);
        int productPrice = data.getInt(columnPriceIndex);
        String supplierPhoneNumber = data.getString(columnSupplierPhoneIndex);
        String imagePath = data.getString(columnImageIndex);

        mNameEditText.setText(productName);
        mQuantityEditText.setText(productQuantity + "");

        if(!TextUtils.isEmpty(supplierPhoneNumber)) {
            mSupplierPhoneNumberEditText.setText("" + supplierPhoneNumber);
        }else {
            mSupplierPhoneNumberEditText.setText(getString(R.string.default_phone_number));
        }

        mPriceEditText.setText(productPrice + "");
        mSupplierNameEditText.setText(supplierName);
        if(imagePath != null) {
            File file = new File(imagePath);
            Picasso.with(this).load(file).into(mProductImage);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}