package com.example.bab3_appmobile_exerciceintegre_harrynguyen_federicofisicaro;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button Ajout, Suppression, Modif, Détails;
    EditText Nom;
    TextView DateAjout, DateModif;
    EditText DuréeAjout, ActionAjout;
    EditText DuréeModif, ActionModif ;
    EditText NumModif, NumSupp;

    private static final String DB_NAME = "Exo_Int.db";
    private static final String TABLE_NAME = "Data";
    private SQLiteDatabase db;
    private StringBuilder t_debug=new StringBuilder();

    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME+ " (Id INTEGER PRIMARY KEY AUTOINCREMENT," +"Nom TEXT, Date TEXT, Durée NUMBER, Actions TEXT);");


        Ajout=(Button)findViewById(R.id.btnAjout);
        Suppression=(Button)findViewById(R.id.btnSupp);
        Modif=(Button)findViewById(R.id.btnModif);
        Détails=(Button)findViewById(R.id.btnDétails);

        Nom = (EditText)findViewById(R.id.etNom);
        DateAjout = (TextView) findViewById(R.id.etDateAjout);
        DuréeAjout = (EditText)findViewById(R.id.etDuréeAjout);
        ActionAjout = (EditText)findViewById(R.id.etActionAjout);

        NumModif = (EditText)findViewById(R.id.etNumModif);
        DateModif = (TextView) findViewById(R.id.etDateModif);
        DuréeModif = (EditText)findViewById(R.id.etDuréeModif);
        ActionModif = (EditText)findViewById(R.id.etActionModif);

        NumSupp= (EditText)findViewById(R.id.etNumSupp);




        final DatePickerDialog.OnDateSetListener dateAjout = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelAjout();
            }

        };

        DateAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, dateAjout, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        Ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Étudiant= Nom.getText().toString();
                String Jour = DateAjout.getText().toString();
                String Temps=DuréeAjout.getText().toString();
                String Action = ActionAjout.getText().toString();

                if(Étudiant.length()!=0&& Jour.length()!=0 && Temps.length()!=0 && Action.length()!=0) {

                    int TempsInt = Integer.valueOf(DuréeAjout.getText().toString());
                    db.execSQL("INSERT INTO DATA (Nom, Date, Durée, Actions) " +
                            "VALUES ('"+Étudiant+"','"+Jour+"','"+TempsInt+"','"+Action+"')",new String[]{});
                   // db.close();

                    Toast.makeText(getApplicationContext(),"Bravo, vous avez ajouté une nouvelle tâche. Continuez comme ça !",Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs, svp.",Toast.LENGTH_LONG).show();


            }
        });


        Suppression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               int Numéro =Integer.valueOf(NumSupp.getText().toString());
               String Étudiant =  Nom.getText().toString();

               Cursor c = db.query(TABLE_NAME, new String[]{"Id","Nom"},"Id LIKE \""+Numéro+"\" AND Nom LIKE \""+ Étudiant+"\"",null,null,null,null);
               if (c.getCount()!=0){
                   db.delete(TABLE_NAME, "Id LIKE \"" + Numéro +"\" AND Nom LIKE \"" + Étudiant + "\" ", null);
                   //db.close();
                   Toast.makeText(getApplicationContext(),"Bravo, vous avez supprimé une tâche !",Toast.LENGTH_LONG).show();
               }
                else Toast.makeText(getApplicationContext(),"Vous n'avez pas précisé le numéro de la tâche et/ou le nom de l'étudiant",Toast.LENGTH_LONG).show();

            }
        });


        final DatePickerDialog.OnDateSetListener dateModif = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelModif();
            }

        };

        DateModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, dateModif, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Étudiant =  Nom.getText().toString();
                String Numéro= NumModif.getText().toString();
                String Jour = DateModif.getText().toString();
                String Temps=DuréeModif.getText().toString();

                String Action = ActionModif.getText().toString();

                if (Numéro.length()!=0) {
                    int NuméroInt =Integer.valueOf(NumModif.getText().toString());

                    Cursor c = db.query(TABLE_NAME, new String[]{"Id", "Nom"}, "Id LIKE \"" +NuméroInt+ "\" AND Nom LIKE \"" + Étudiant + "\"", null, null, null, null);
                   if (c.getCount() != 0) {
                        if (!Jour.isEmpty()) {

                            ContentValues content = new ContentValues();
                            content.put("Date", Jour);
                            db.update(TABLE_NAME, content, "Id = " + NuméroInt, null);
                        }

                        if (Temps.length()!= 0) {
                            int TempsInt = Integer.valueOf(DuréeModif.getText().toString());
                            ContentValues content = new ContentValues();
                            content.put("Durée", TempsInt);
                            db.update(TABLE_NAME, content, "Id = " + NuméroInt, null);
                        }

                        if (!Action.isEmpty()) {
                            ContentValues content = new ContentValues();
                            content.put("Actions", Action);
                            db.update(TABLE_NAME, content, "id = " + Integer.valueOf(Numéro), null);
                        }

                        //db.close();
                        Toast.makeText(getApplicationContext(), "Bravo, vous avez modifié une tâche !", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Il n'y a pas de tâche numéro "+ NuméroInt +" pour cet étudiant", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getApplicationContext(), "Vous n'avez pas précisé le numéro de la tâche", Toast.LENGTH_LONG).show();
            }
        });

        Détails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
              //  intent.putExtra("name",(Nom.getText()).toString());
                startActivity(intent);
            }
        });


    }


    private void updateLabelAjout() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        DateAjout.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelModif() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        DateModif.setText(sdf.format(myCalendar.getTime()));
    }

}
