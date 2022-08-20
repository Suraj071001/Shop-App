package com.example.android.shopinventory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.shopinventory.data.Contract;

public class CustomAdapter extends CursorAdapter {
    public CustomAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_layout,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView itemNameView = view.findViewById(R.id.textView3);
        TextView itemBrandView = view.findViewById(R.id.textView4);
        TextView itemQuantityView = view.findViewById(R.id.textView5);

        String itemName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Inventory_data.COLUMN_NAME));
        String itemBrand = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Inventory_data.COLUMN_BRAND));
        String itemQuantity = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Inventory_data.COLUMN_QUANTITY));

        itemNameView.setText(itemName);
        itemBrandView.setText(itemBrand);
        itemQuantityView.setText(itemQuantity);
    }
}
