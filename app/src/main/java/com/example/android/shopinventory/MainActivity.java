package com.example.android.shopinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    EditText nameEditText,brandEditText;
    TextView quantityTextView;
    ImageView minusImageView,plusImageView;
    Button insertButton,updateButton,deleteButton;

    String name;
    String brand;
    int quantity;

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

                Intent intent = getIntent();
                String id = intent.getStringExtra("uri");

                getContentResolver().update(Uri.parse(Contract.Inventory_data.CONTENT_URI+"/"+id),cv,null,null);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("uri");

                getContentResolver().delete(Uri.parse(Contract.Inventory_data.CONTENT_URI+"/"+id),null,null);
            }
        });

    }
    public void getData(){

        name = nameEditText.getText().toString();
        brand = brandEditText.getText().toString();
        quantity = Integer.parseInt(quantityTextView.getText().toString());
    }
}