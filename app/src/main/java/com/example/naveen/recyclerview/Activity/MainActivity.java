package com.example.naveen.recyclerview.Activity;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.example.naveen.recyclerview.Adapter.Adapter;
import com.example.naveen.recyclerview.Utility.ListItem;
import com.example.naveen.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton floatingActionButton;
    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_add);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        for(int i=0;i<10;i++){
            ListItem listItem = new ListItem(
                    "Title"+(i+1),null
            );
            listItems.add(listItem);
        }

        adapter = new Adapter(listItems,this);

        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PaintViewEditorActivity.class);
                startActivity(i);
            }
        });
    }

}
