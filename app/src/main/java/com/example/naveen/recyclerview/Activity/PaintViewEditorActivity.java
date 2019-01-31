package com.example.naveen.recyclerview.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.naveen.recyclerview.Utility.PaintView;
import com.example.naveen.recyclerview.R;

public class PaintViewEditorActivity extends AppCompatActivity {

    private PaintView paintView;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_imageditor);
        paintView=(PaintView)findViewById(R.id.pv_main);
        buttonSave=(Button)findViewById(R.id.bt_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            paintView.saveView();
            }
        });
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
