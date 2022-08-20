package com.example.android.shopinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.shopinventory.data.Contract;
import com.example.android.shopinventory.data.Inventory_data;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    EditText nameEditText,brandEditText;
    TextView quantityTextView;
    ImageView minusImageView,plusImageView;
    Button insertButton,updateButton,deleteButton;

    String name;
    String brand;
    int quantity;

    Intent intent;
    String id;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.editText1);
        brandEditText = findViewById(R.id.editText2);
        quantityTextView = findViewById(R.id.textView9);
        minusImageView = findViewById(R.id.imageView1);
        plusImageView = findViewById(R.id.imageView2);
        insertButton = findViewById(R.id.button);
        updateButton = findViewById(R.id.button2);
        deleteButton = findViewById(R.id.button3);

        intent = getIntent();

        title = intent.getStringExtra("title");
        getSupportActionBar().setTitle(title);

        if(title.equals("Edit Item")){
            id = intent.getStringExtra("uri");
            LoaderManager.getInstance(this).initLoader(1,null,this);
        }
        id = intent.getStringExtra("uri");

        minusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer quantity = Integer.parseInt(quantityTextView.getText().toString());
                if (quantity>0){
                    quantity = quantity - 1;
                    quantityTextView.setText(""+quantity);
                }
            }
        });

        plusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer quantity = Integer.parseInt(quantityTextView.getText().toString());
                quantity = quantity +1;
                quantityTextView.setText(""+quantity);
            }
        });

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                ContentValues cv = new ContentValues();
                cv.put(Contract.Inventory_data.COLUMN_NAME,name);
                cv.put(Contract.Inventory_data.COLUMN_BRAND,brand);
                cv.put(Contract.Inventory_data.COLUMN_QUANTITY,quantity);
//                Inventory_data inventory_data = new Inventory_data(MainActivity.this);
//                SQLiteDatabase database = inventory_data.getWritableDatabase();
//                database.insert(Contract.Inventory_data.TABLE_NAME,null,cv);
                getContentResolver().insert(Contract.Inventory_data.CONTENT_URI,cv);
                Intent intent1 = new Intent(MainActivity.this,Catalog.class);
                startActivity(intent1);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                ContentValues cv = new ContentValues();
                cv.put(Contract.Inventory_data.COLUMN_NAME,name);
                cv.put(Contract.Inventory_data.COLUMN_BRAND,brand);
                cv.put(Contract.Inventory_data.COLUMN_QUANTITY,quantity);

                getContentResolver().update(Uri.parse(Contract.Inventory_data.CONTENT_URI+"/"+id),cv,null,null);
                Intent intent1 = new Intent(MainActivity.this,Catalog.class);
                startActivity(intent1);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaderManager.getInstance(MainActivity.this).destroyLoader(1);
                getContentResolver().delete(Uri.parse(Contract.Inventory_data.CONTENT_URI+"/"+id),null,null);
                finish();

                Intent intent1 = new Intent(MainActivity.this,Catalog.class);
                startActivity(intent1);
            }
        });

    }
    public void getData(){

        name = nameEditText.getText().toString();
        brand = brandEditText.getText().toString();
        quantity = Integer.parseInt(quantityTextView.getText().toString());
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String id1 = intent.getStringExtra("uri");
        return new CursorLoader(this,Uri.parse(Contract.Inventory_data.CONTENT_URI+"/"+id1),null,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();

        String name_data = data.getString(data.getColumnIndexOrThrow(Contract.Inventory_data.COLUMN_NAME));
        String brand_data = data.getString(data.getColumnIndexOrThrow(Contract.Inventory_data.COLUMN_BRAND));
        String quantity_data = data.getString(data.getColumnIndexOrThrow(Contract.Inventory_data.COLUMN_QUANTITY));

        nameEditText.setText(name_data);
        brandEditText.setText(brand_data);
        quantityTextView.setText(""+quantity_data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        nameEditText.setText(null);
        brandEditText.setText(null);
        quantityTextView.setText(null);
    }
}