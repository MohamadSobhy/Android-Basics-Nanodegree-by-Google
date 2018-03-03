package com.example.mohammad.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammad.inventoryapp.data.InventoryContract.ProductEntry;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by mohammad on 3/2/18.
 */

public class ProductCursorAdapter extends CursorAdapter{
    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param cursor      The cursor from which to get the data.
     */
    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false);
    }

    /**
     * This method binds the product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.product_name_text_view);
        TextView supplierNameTextView = (TextView) view.findViewById(R.id.supplier_name_text_view);
        TextView productPriceTextView = (TextView) view.findViewById(R.id.product_price_text_view);
        TextView productQuantityTextView = (TextView) view.findViewById(R.id.product_quantity_text_view);
        ImageView productImageView = (ImageView) view.findViewById(R.id.product_image_view);
        ImageButton buyImageButton = (ImageButton) view.findViewById(R.id.buy_image_button);

        // Find the columns of product attributes that we're interested in
        final int productIdColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int supplierNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_IMAGE);

        // Read the product attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        int productPrice = cursor.getInt(priceColumnIndex);
        final int productQuantity = cursor.getInt(quantityColumnIndex);
        String supplierName = cursor.getString(supplierNameColumnIndex);
        String imagePath = cursor.getString(imageColumnIndex);
        final int productId = cursor.getInt(productIdColumnIndex);

        // If the product supplier is empty string or null, then use some default text
        // that says "Unknown supplier", so the TextView isn't blank.
        if (TextUtils.isEmpty(supplierName)) {
            supplierName = "Unknown Supplier";
        }

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        supplierNameTextView.setText(supplierName);
        productQuantityTextView.setText(context.getString(R.string.in_stock, productQuantity));
        productPriceTextView.setText(productPrice + "$");
        if(!TextUtils.isEmpty(imagePath)) {
            File file = new File(imagePath);
            Picasso.with(context).load(file).into(productImageView);
        }

        buyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri productUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, productId);
                updateQuantity(context,productUri, productQuantity);
            }
        });

    }

    private void updateQuantity(Context context, Uri productUri, int oldQuantity){

        int currentQuantity = (oldQuantity > 1)? oldQuantity - 1 : 0;

        if (currentQuantity == 0) {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.product_out_of_stock), Toast.LENGTH_SHORT).show();
            context.getContentResolver().delete(productUri, null, null);
            return;
        }

        ContentValues values = new ContentValues();

        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, currentQuantity);

        int rowsUpdated = context.getContentResolver().update(productUri, values, null, null);

        if(rowsUpdated > 0){
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.product_bought_successfully), Toast.LENGTH_SHORT).show();
        }
    }


}
