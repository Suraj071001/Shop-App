package com.example.android.shopinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.shopinventory.data.Contract;
import com.example.android.shopinventory.data.Inventory_data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Catalog extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    FloatingActionButton floatingActionButton;
    ListView listView;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        floatingActionButton = findViewById(R.id.fab);
        listView = findViewById(R.id.listview);

//        Inventory_data inventory_data = new Inventory_data(this);
//        SQLiteDatabase database = inventory_data.getReadableDatabase();
//        Cursor cursor = database.query(Contract.Inventory_data.TABLE_NAME,null,null,null,null,null,null);


        customAdapter = new CustomAdapter(this,null);
        listView.setAdapter(customAdapter);
        LoaderManager.getInstance(this).initLoader(0, null, this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(Catalog.this,MainActivity.class);
                intent.putExtra("uri",""+id);
                intent.putExtra("title","Edit Item");
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Catalog.this,MainActivity.class);
                intent.putExtra("title","Add New Item");
                startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, Contract.Inventory_data.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        customAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        customAdapter.swapCursor(null);
    }
}