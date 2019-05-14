package com.example.bab3_appmobile_exerciceintegre_harrynguyen_federicofisicaro;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private static final String DB_NAME = "Exo_int.db";
    private static final String TABLE_NAME = "Data";
    private SQLiteDatabase db;

    Button back;
    TextView Result, nbrHeures;

    ArrayList<Integer> ids = new ArrayList<Integer>();
    ArrayList<String> nomsActivite = new ArrayList<String> ();
    ArrayList<String> dates = new ArrayList<String>();
    ArrayList<Integer> duree = new ArrayList<Integer>();

    int nbrtotH = 0;

    String nom;
    StringBuilder SBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME+ " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Nom TEXT, Date TEXT, Durée NUMBER, Actions TEXT);");

        Result=findViewById(R.id.tvAff);
        nbrHeures= findViewById(R.id.tvHTot);
        back=findViewById(R.id.btnBack);

        Intent intent= getIntent();
        nom = intent.getStringExtra("name");

        Cursor c = db.query(TABLE_NAME, new String[] { "Id", "Nom", "Date", "Durée", "Actions" }, null, null, null,
                null, "Date");
        if (c.getCount() == 0) {
            c.close();
            Toast.makeText(getApplicationContext(),"Aucune donnée disponible",Toast.LENGTH_LONG).show();
        }
        else{

            while(c.moveToNext()){

                if((c.getString(1) == nom)){

                    ids.add(c.getInt(0));
                    dates.add(c.getString(2));
                    duree.add(c.getInt(3));
                    nomsActivite.add(c.getString(4));
                }

            }

            for(int i=0;i<nomsActivite.size();i++){
                SBuilder.append("ID : " + (ids.get(i)).toString() + "\t Action : " + nomsActivite.get(i)+"\t Durée : "+ (duree.get(i)).toString()+"\t Date : " + dates.get(i));
                SBuilder.append("\n");
                nbrtotH=nbrtotH+duree.get(i);
            }

            Result.setText(SBuilder.toString());
            Result.setMovementMethod(new ScrollingMovementMethod());
            nbrHeures.setText(Integer.toString(nbrtotH));
            c.close();
        }
        db.close();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
}