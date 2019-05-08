package com.example.sql_conn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenuActivity extends AppCompatActivity implements Dialog.DialogListener {

    String actualLevel;
    int userID;
    String username;
    Connection con;
    String kodkapott;


   String ip = "193.6.33.140";
   String db = "AbeceDb";
    String un = "abece";
    String pass = "Abece_1";
    String port = "1433";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Intent menuapp = getIntent();
        userID = Integer.parseInt(menuapp.getStringExtra("userID"));
        username = menuapp.getStringExtra("username");
        checkLevel();




    }
    public void checkLevel(){


        try {

            con = connectionclass(un,pass,db,ip,port);
            if(con!=null)
            {
                String query = "select * from szint_elorehaladas where felhasznaloID='"+userID+"'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                String res="";
                String level="";
                while(rs.next())
                {
                    res=rs.getString(1);
                    level=rs.getString("szintID");
                }
                //et.setText(res);
                actualLevel = level;
                con.close();

            }

        }
        catch (Exception ex){

        }

        TextView tv = (TextView)findViewById(R.id.welcom);
        String hello="Helló "+username+"! Jelenlegi szinted: "+actualLevel;
        tv.setText(hello);


    }

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
                break;
            case  R.id.kodmegadas:
                uploadCode();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyText(String kod) {
        kodkapott=kod;

        String level="";
        try {

            con = connectionclass(un,pass,db,ip,port);
            if(con!=null)
            {
                String query = "select * from szint where kod='"+kodkapott+"'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                String res="";

                while(rs.next())
                {
                    res=rs.getString(1);
                    level=rs.getString("szintID");

                }
                if(level!=null)
                {
                    String query2 = "update szint_elorehaladas set szintID='"+level+"' where felhasznaloID="+userID;
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(query2);


                }
                con.close();

            }

        }
        catch (Exception ex){

        }
        checkLevel();


    }

    private void uploadCode() {

                openDialog();


    }
    public void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"dialog");
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
        int TableId = R.id.tableLayout2;

        int ButtId = R.id.letters;

        TableLayout layout = (TableLayout) findViewById(TableId);
        TableRow tr11 = findViewById(R.id.letterrow11);
        TableRow tr21 = findViewById(R.id.letterrow21);

        TableRow tr31 = findViewById(R.id.letterrow31);
        TableRow tr32 = findViewById(R.id.letterrow32);

        Button butt = (Button)findViewById(ButtId);

        if (actualLevel=="1")
        {
            tr11.setVisibility(View.VISIBLE);
            tr21.setVisibility(View.GONE);
            tr31.setVisibility(View.GONE);
            tr32.setVisibility(View.GONE);
        }
        else if(actualLevel =="2")
        {
            tr11.setVisibility(View.VISIBLE);
            tr21.setVisibility(View.VISIBLE);
            tr31.setVisibility(View.GONE);
            tr32.setVisibility(View.GONE);

        }
        else if(actualLevel=="3")
        {
            tr11.setVisibility(View.VISIBLE);
            tr21.setVisibility(View.VISIBLE);
            tr31.setVisibility(View.VISIBLE);
            tr32.setVisibility(View.VISIBLE);

        }

        if(layout.getVisibility() == View.VISIBLE)
        {
            layout.setVisibility(View.GONE);


        }

        else
        {
            layout.setVisibility(View.VISIBLE);


        }
    }

    public void menuItemThree(View view) {
        Intent puzzle  = new Intent(MenuActivity.this,PuzzleActivity.class);

        startActivity(puzzle);


    }
    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server,String port){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL="jdbc:jtds:sqlserver://" + server +":"+port+";"
                    + database + ";user=" + user + ";password="
                    + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se){
            Log.e("error here 1: ", se.getMessage());
        }
        catch (ClassNotFoundException e){
            Log.e("error here 2: ", e.getMessage());
        }
        catch (Exception e){
            Log.e("error here 3: ", e.getMessage());
        }
        return connection;
    }


}
