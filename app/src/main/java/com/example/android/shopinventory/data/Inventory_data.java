package com.example.android.shopinventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Inventory_data extends SQLiteOpenHelper {
    private static final String NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public Inventory_data(Context context) {
        super(context,NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE "+ Contract.Inventory_data.TABLE_NAME +" ("+
                Contract.Inventory_data.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Contract.Inventory_data.COLUMN_NAME + " TEXT NOT NULL, " +
                Contract.Inventory_data.COLUMN_BRAND + " TEXT, " +
                Contract.Inventory_data.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0"+");";
        sqLiteDatabase.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
