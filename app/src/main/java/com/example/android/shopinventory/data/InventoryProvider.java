package com.example.android.shopinventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InventoryProvider extends ContentProvider {
    Inventory_data inventory_data;

    private static final int INVENTORY = 100;
    private static final int INVENTORY_ID = 101;

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,Contract.INVENTORY_PATH,INVENTORY);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,Contract.INVENTORY_PATH+"/#",INVENTORY_ID);
    }

    @Override
    public boolean onCreate() {
        inventory_data = new Inventory_data(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String s1) {
        SQLiteDatabase database = inventory_data.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match){
            case INVENTORY:
                cursor = database.query(Contract.Inventory_data.TABLE_NAME,projection,selection,selectionArgs,null,null,s1);
                break;
            case INVENTORY_ID:
                selection = Contract.Inventory_data.COLUMN_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                 cursor = database.query(Contract.Inventory_data.TABLE_NAME,projection,selection,selectionArgs,null,null,s1);
                break;
            default:
                throw new IllegalArgumentException("Query not execute"+ uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match){
            case INVENTORY:
                return insertData(uri,contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for" + uri);
        }
    }

    private Uri insertData(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database = inventory_data.getWritableDatabase();
        long insert = database.insert(Contract.Inventory_data.TABLE_NAME,null,contentValues);

        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,insert);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = inventory_data.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match){
            case INVENTORY:
                getContext().getContentResolver().notifyChange(uri,null);
                return database.delete(Contract.Inventory_data.TABLE_NAME,selection,selectionArgs);
            case INVENTORY_ID:
                selection = Contract.Inventory_data.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                getContext().getContentResolver().notifyChange(uri,null);
                return database.delete(Contract.Inventory_data.TABLE_NAME,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("delete not working for "+uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        switch (match){
            case INVENTORY_ID:
                selection = Contract.Inventory_data.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateData(uri,contentValues,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Update not working for "+ uri);
        }
    }

    private int updateData(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase database = inventory_data.getWritableDatabase();
         int update = database.update(Contract.Inventory_data.TABLE_NAME,contentValues,selection,selectionArgs);

         getContext().getContentResolver().notifyChange(uri,null);
         return update;
    }
}
