package com.example.to_dolist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView list;
    private Button button;
    private EditText editText;
    ArrayAdapter<String> adapter;
    ArrayList<String> items;
    AlertDialog.Builder alertDialougeBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        list=findViewById(R.id.listID);
        button=findViewById(R.id.enterbuttonID);
        editText=findViewById(R.id.enteritemID);

        items=FileHelper.readData(this);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        list.setAdapter(adapter);

        button.setOnClickListener(this);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.enterbuttonID:

                String newItem=editText.getText().toString();
                adapter.add(newItem);
                FileHelper.writeData(items,this);
                editText.setText("");
                Toast.makeText(this,"Item added",Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        alertDialougeBuilder=new AlertDialog.Builder(MainActivity.this);

        alertDialougeBuilder.setTitle("Alert");

        alertDialougeBuilder.setMessage("Do you want to delete the item?");

        alertDialougeBuilder.setIcon(R.drawable.danger);

        alertDialougeBuilder.setCancelable(false);

        alertDialougeBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                items.remove(i);
                adapter.notifyDataSetChanged();
                FileHelper.writeData(items, MainActivity.this);
                Toast.makeText(MainActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialougeBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog=alertDialougeBuilder.create();
        alertDialog.show();

    }
}
