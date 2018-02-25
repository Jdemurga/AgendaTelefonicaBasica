package com.example.jdemu.agenda;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jdemu on 13/12/2017.
 */

public class Nuevo extends Fragment {
    ListView lv;
    View vista;
    Bundle b;
    ArrayList todos;
    int pagina=2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        if (vista != null) {
            ViewGroup parent = (ViewGroup) vista.getParent();
            parent.removeView(vista);
        } else {
            vista = inflater.inflate(R.layout.nuevo, container, false);
            lv=(ListView)vista.findViewById(R.id.favor);
            todos = ((MainActivity) getActivity()).listaF();
            b = getArguments();
            b.remove("pagina");
            b.putInt("pagina",pagina);
            ArrayList<String> nombre = new ArrayList<String>();
            for (int i = 0; i < todos.size(); i++) {
                Datos contac = (Datos) todos.get(i);
                nombre.add(contac.getNombre());
            }
            ArrayAdapter adapter = new ArrayAdapter(vista.getContext(), android.R.layout.simple_list_item_1, nombre);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Datos c = (Datos) todos.get(position);
                    b.putString("nombre", c.getNombre());
                    b.putString("numero", c.getNumero());
                    b.putString("fav",c.getFav());
                    ((MainActivity) getActivity()).cambiarEditar();
                    b.remove("pagina");
                    b.putInt("pagina",4);
                }
            });

        }


        return vista;
    }

}
