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

    private static final String DB_NAME = "Exo_Int.db";
    private static final String TABLE_NAME = "Data";
    private SQLiteDatabase db;

    Button back;
    TextView Result, nbrHeures;

    ArrayList<Integer> Ids = new ArrayList<Integer>();
    ArrayList<String> Actions = new ArrayList<String> ();
    ArrayList<String> Dates = new ArrayList<String>();
    ArrayList<Integer> Durées = new ArrayList<Integer>();



    String nom;
    StringBuilder SBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        back=findViewById(R.id.btnBack);
        db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME+ " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Nom TEXT, Date TEXT, Durée NUMBER, Actions TEXT);");

        int nbrtotH = 0;

        Result=findViewById(R.id.tvAff);
        nbrHeures= findViewById(R.id.tvHTot);


        Intent intent= getIntent();
        nom = intent.getStringExtra("name");
       // Toast.makeText(getApplicationContext(), "Nom de l'étudiant "+nom+"", Toast.LENGTH_LONG).show();

        Cursor c = db.query(TABLE_NAME, new String[] { "Id", "Nom", "Date", "Durée", "Actions" }, "Nom LIKE \"" + nom + "\"", null, null, null,
                "Date", null);


        if (c.getCount() == 0) {
            c.close();
            Toast.makeText(getApplicationContext(),"Aucune donnée disponible pour l'étudiant " +nom+"",Toast.LENGTH_LONG).show();
        }

        else{

            while(c.moveToNext()){


                    Ids.add(c.getInt(0));
                    Dates.add(c.getString(2));
                    Durées.add(c.getInt(3));
                    Actions.add(c.getString(4));

            }

            for(int i=0;i<c.getCount();i++){
                SBuilder.append(" ID : " + (Ids.get(i)).toString() + "\n Action : " + Actions.get(i)+"\n Durée : "+ (Durées.get(i)).toString()+"\n Date : " + Dates.get(i));
                SBuilder.append("\n\n");
                nbrtotH=nbrtotH+Durées.get(i);
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