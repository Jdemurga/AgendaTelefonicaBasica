package com.example.jdemu.agenda;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm = getFragmentManager();
    SQLiteDatabase basedatos;
    Fragment fragment;
    FragmentTransaction fts;
    Bundle b;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new Contacto();
                    fts = fm.beginTransaction();
                    fts.replace(R.id.container, fragment);
                    fragment.setArguments(b);
                    fts.commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragment = new Nuevo();
                    fts = fm.beginTransaction();
                    fts.replace(R.id.container, fragment);
                    fragment.setArguments(b);
                    fts.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = new Bundle();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        SQLiteOpenHelper sq = new SQLiteOpenHelper(this, "Agenda3", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String query = "CREATE TABLE contactos ( \n " +
                        "nombre TEXT DEFAULT NULL ,\n" +
                        "numero INTEGER DEFAULT NULL ,\n" +
                        "esfav TEXT DEFAULT NULL);";
                String prueba = "INSERT INTO contactos(nombre,numero,esfav) VALUES('jorge','648670517','N');";
                String prueba2 = "INSERT INTO contactos(nombre,numero,esfav) VALUES('murga','648670517','S');";
                String tabla2="CREATE TABLE fav ( \n " +
                        "nombre TEXT DEFAULT NULL ,\n" +
                        "numero INTEGER DEFAULT NULL);";
                String prueba3 = "INSERT INTO fav (nombre,numero) VALUES('murga','648670517');";
                db.execSQL(query);
                db.execSQL(tabla2);
                db.execSQL(prueba);
                db.execSQL(prueba2);
                db.execSQL(prueba3);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        basedatos = sq.getWritableDatabase();
        cambiarLista();
    }

    public ArrayList listaC() {
        ArrayList contactos = new ArrayList();
        String query = "SELECT * FROM contactos";
        Cursor c = basedatos.rawQuery(query, null);
        while (c.moveToNext()) {
            contactos.add(new Datos(c.getString(0), c.getString(1),c.getString(2)));
        }
        return contactos;
    }
    public ArrayList listaF() {
        ArrayList contactos = new ArrayList();
        String query = "SELECT * FROM fav";
        Cursor c = basedatos.rawQuery(query, null);
        while (c.moveToNext()) {
            contactos.add(new Datos(c.getString(0), c.getString(1),"S"));
        }
        return contactos;
    }

    public void añadir(String nombre, String numero) {
        String query = "INSERT INTO contactos (nombre,numero,esfav)VALUES('" + nombre + "','" + numero + "','N');";
        basedatos.execSQL(query);
    }
    public void añadirF(String nombre, String numero) {
        String query = "INSERT INTO fav (nombre,numero)VALUES('" + nombre + "','" + numero + "');";
        String query2 = "UPDATE contactos SET esfav = 'S' WHERE nombre = '" + nombre + "'AND numero ='" + numero + "';";
        basedatos.execSQL(query);
        basedatos.execSQL(query2);
    }
    public void borrarF(String nombre, String numero) {
        String query = "DELETE FROM fav WHERE nombre= '" + nombre + "' AND numero = '" + numero + "';";
        String query2 = "UPDATE contactos SET esfav = 'N' WHERE nombre = '" + nombre + "'AND numero ='" + numero + "';";
        basedatos.execSQL(query);
        basedatos.execSQL(query2);

    }

    public void cambiarEditar() {
        fragment = new editar();
        fts = fm.beginTransaction();
        fts.replace(R.id.container, fragment);
        fragment.setArguments(b);
        fts.commit();
    }

    public void cambiarLista() {
        fragment = new Contacto();
        fts = fm.beginTransaction();
        fts.replace(R.id.container, fragment);
        fragment.setArguments(b);
        fts.commit();
    }
    public void cambiarFav() {
        fragment = new Nuevo();
        fts = fm.beginTransaction();
        fts.replace(R.id.container, fragment);
        fragment.setArguments(b);
        fts.commit();
    }


    public void actualizar(String nombre, String numero, String Newnom, String Newnum) {
        String query = "UPDATE contactos SET nombre = '" + Newnom + "' , numero= '" + Newnum + "' WHERE nombre = '" + nombre + "'AND numero ='" + numero + "';";
        basedatos.execSQL(query);
    }

    public void borrar(String nombre, String numero) {
        String query = "DELETE FROM contactos WHERE nombre= '" + nombre + "' AND numero = '" + numero + "';";
        basedatos.execSQL(query);
    }
    @Override
    public void onBackPressed() {
        int pagina= (int)b.get("pagina");
        if(pagina==1){
            super.finish();
        }else if(pagina==4){
            cambiarFav();
        }else{
            cambiarLista();
        }

    }


}
