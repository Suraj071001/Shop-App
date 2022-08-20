package com.example.android.shopinventory.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {
    public static final String CONTENT_AUTHORITY = "com.example.android.shopinventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String INVENTORY_PATH = "inventory";

    public static class Inventory_data implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,INVENTORY_PATH);
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_QUANTITY = "quantity";


    }
}
