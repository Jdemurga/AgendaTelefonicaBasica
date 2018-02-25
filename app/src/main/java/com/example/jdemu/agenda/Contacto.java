package com.example.jdemu.agenda;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jdemu on 13/12/2017.
 */

public class Contacto extends Fragment {
    View vista;
    ListView listv;
    Bundle b;
    ArrayList todo;
    FloatingActionButton fab;
    int pagina =1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (vista != null) {
            ViewGroup parent = (ViewGroup) vista.getParent();
            parent.removeView(vista);
        } else {
            vista = inflater.inflate(R.layout.lista, container, false);
            listv = (ListView) vista.findViewById(R.id.listv);
            todo = ((MainActivity) getActivity()).listaC();
            fab=(FloatingActionButton)vista.findViewById(R.id.fab);
            b = getArguments();
            b.remove("pagina");
            b.putInt("pagina",pagina);
            ArrayList<String> nombre = new ArrayList<String>();
            for (int i = 0; i < todo.size(); i++) {
                Datos contac = (Datos) todo.get(i);
                nombre.add(contac.getNombre());
            }
            ArrayAdapter adapter = new ArrayAdapter(vista.getContext(), android.R.layout.simple_list_item_1, nombre);
            listv.setAdapter(adapter);
        }
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Datos c = (Datos) todo.get(position);
                b.putString("nombre", c.getNombre());
                b.putString("numero", c.getNumero());
                b.putString("fav",c.getFav());
                ((MainActivity) getActivity()).cambiarEditar();
                b.remove("pagina");
                b.putInt("pagina",3);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });
        return vista;
    }
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(vista.getContext());
        LayoutInflater inflater =this.getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        final EditText edt2 = (EditText) dialogView.findViewById(R.id.edit2);


        dialogBuilder.setTitle("Nuevo contacto");
        dialogBuilder.setMessage("Introduzca los datos");
        dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(!(String.valueOf(edt.getText()).equals("") || String.valueOf(edt2.getText()).equals(""))){
                ((MainActivity)getActivity()).añadir(String.valueOf(edt.getText()),String.valueOf(edt2.getText()));
                ((MainActivity)getActivity()).cambiarLista();}
            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
