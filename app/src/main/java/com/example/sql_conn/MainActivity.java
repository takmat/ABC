package com.example.sql_conn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class MainActivity extends AppCompatActivity {


    Button login, button2, button3;
    EditText username, password;
    ProgressBar progressBar;

    Connection con;
    String un, pass, db, ip, port;

    String extraUsername;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().getDecorView().setSystemUiVisibility(
                        //View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                       // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        //| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                         View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        login = findViewById(R.id.button);
        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        progressBar = findViewById(R.id.progressBar);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        progressBar.setVisibility(View.GONE);

        ip = "193.6.33.140";
        db = "AbeceDb";
        un = "abece";
        pass = "Abece_1";
        port = "1433";

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLogin checkLogin = new CheckLogin();

                checkLogin.execute("");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reg regist = new Reg();

                regist.execute("");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Del delete = new Del();

                delete.execute("");
            }
        });

    }

    public class CheckLogin extends AsyncTask<String, String, String>
    {
        String z = "";
        Boolean isSuccess=false;

        @Override
        protected  void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params){
            String usernam =username.getText().toString();
            String passw = password.getText().toString();
            extraUsername = usernam;

            if (usernam.trim().equals("")||passw.trim().equals("")){
                z="Enter Username and password";
            }
            else{
                try{
                    con = connectionclass(un, pass, db, ip);
                    if (con==null){
                        z="Check Internet";
                    }
                    else{
                        String query = "select * from dbo.felhasznalok where dbo.felhasznalok.felhasznalonev= '" + usernam.toString() + "' and dbo.felhasznalok.jelszo= '" +passw.toString() +"'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()){
                            z="Login Successful";
                            id = rs.getString("id");
                            isSuccess=true;
                            con.close();
                        }
                        else{
                            z="Invalid credentials";
                            isSuccess=false;
                        }
                    }
                }
                catch (Exception ex){
                    isSuccess=false;
                    z=ex.getMessage();
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String r){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess){
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, MenuActivity.class);

                i.putExtra("username" ,extraUsername);
                i.putExtra("userID" ,id);
                startActivity(i);

            }
        }
    }

    public class Reg extends AsyncTask<String, String, String>
    {
        String z = "";
        Boolean isSuccess=false;

        @Override
        protected  void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params){
            String usernam =username.getText().toString();
            String passw = password.getText().toString();

            if (usernam.trim().equals("")||passw.trim().equals("")){
                z="Enter Username and password";
            }
            else{
                try{
                    con = connectionclass(un, pass, db, ip);
                    if (con==null){
                        z="Check Internet";
                    }
                    else{
                        String query = "insert into dbo.felhasznalok (felhasznalonev, jelszo) Values('"+usernam.toString()+"','"+passw.toString()+"')";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()){
                            z="Registration Successful";
                            isSuccess=true;
                            con.close();
                        }

                    }
                }
                catch (Exception ex){
                    isSuccess=false;
                    z=ex.getMessage();
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String r){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess){
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
               // Intent i;
                //i = new Intent(MainActivity.this,MenuActivity.class);
               // i.putExtra("userID",extraUsername.trim());
               // startActivity(i);

            }

        }
    }

    public class Del extends AsyncTask<String, String, String>
    {
        String z = "";
        Boolean isSuccess=false;

        @Override
        protected  void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params){
            String usernam =username.getText().toString();
            String passw = password.getText().toString();

            if (usernam.trim().equals("")||passw.trim().equals("")){
                z="Enter Username and password";
            }
            else{
                try{
                    con = connectionclass(un, pass, db, ip);
                    if (con==null){
                        z="Check Internet";
                    }
                    else{
                        String query = "delete from dbo.felhasznalok where felhasznalonev= '"+usernam.toString()+"' and jelszo= '"+passw.toString()+"'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()){
                            z="Account Deleted";
                            isSuccess=true;
                            con.close();
                        }

                    }
                }
                catch (Exception ex){
                    isSuccess=false;
                    z=ex.getMessage();
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String r){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess){

                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
               /* Intent i;
                i = new Intent(MainActivity.this,MenuActivity.class);
                startActivity(i);*/

            }


        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL="jdbc:jtds:sqlserver://" + ip +":"+port+";"
                     + db + ";user=" + un + ";password="
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
