package com.example.sql_conn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //configuringNextButton();
        this.getWindow().getDecorView().setSystemUiVisibility(
                //View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                //| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }
    /*private void configuringNextButton(){
        Button nextButton = (Button) findViewById(R.id.drawapp);
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, DrawActivity.class));

            }
        });
        Button puzzleButton = (Button) findViewById(R.id.puzzle);
        puzzleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( MenuActivity.this,PuzzleActivity.class));
            }
        });
    }*/





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close_menu:
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public void menuItemOne(View view) {
        int TableId = R.id.tableLayout1;

        int ButtId = R.id.level1;

        TableLayout layout = (TableLayout) findViewById(TableId);

        Button butt = (Button)findViewById(ButtId);

        if(layout.getVisibility() == View.VISIBLE)
        {
            layout.setVisibility(View.GONE);


        }

        else
        {
            layout.setVisibility(View.VISIBLE);


        }
    }

    public void drawThisChar(View view) {
        Intent draw = new Intent(MenuActivity.this,DrawActivity.class);
        String path = view.getTag().toString();
        draw.putExtra("filename", path);
        startActivity(draw);

    }

    public void menuItemTwo(View view) {

    }

    public void menuItemThree(View view) {
        /*Intent puzzle = new Intent(MenuActivity.this,PuzzleActivity.class);
        startActivity(puzzle);*/
    }
}
