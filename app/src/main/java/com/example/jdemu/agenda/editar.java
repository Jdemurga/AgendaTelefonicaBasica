package com.example.jdemu.agenda;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by jdemu on 13/12/2017.
 */

public class editar extends Fragment {
    EditText camNombre, camNumero;
    ImageView cambio, borrar, llamar, whats, favo;
    View vista;
    Bundle b;
    String nombre;
    String numero, favori;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (vista != null) {
            ViewGroup parent = (ViewGroup) vista.getParent();
            parent.removeView(vista);
        } else {
            vista = inflater.inflate(R.layout.editar, container, false);
            camNombre = (EditText) vista.findViewById(R.id.editNombre);
            camNumero = (EditText) vista.findViewById(R.id.editNumero);
            favo = (ImageView) vista.findViewById(R.id.imgfav);
            b = getArguments();
            nombre = b.getString("nombre");
            numero = b.getString("numero");
            favori = b.getString("fav");
            b.remove("nombre");
            b.remove("numero");
            b.remove("fav");
            if (favori.equals("S")) {
                favo.setImageResource(R.drawable.encen);
            } else {
                favo.setImageResource(R.drawable.apag);
            }
            camNombre.setText(nombre);
            camNumero.setText(numero);
            cambio = (ImageView) vista.findViewById(R.id.buttonCambio);
            llamar = (ImageView) vista.findViewById(R.id.btnLlamar);
            whats = (ImageView) vista.findViewById(R.id.btnWhat);
            borrar = (ImageView) vista.findViewById(R.id.buttonBorrar);
            cambio.setVisibility(View.INVISIBLE);
            cambio.setEnabled(false);
            camNombre.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!nombre.equals(String.valueOf(camNombre.getText()))){
                        cambio.setVisibility(View.VISIBLE);
                        cambio.setEnabled(true);
                        cambio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((MainActivity) getActivity()).actualizar(nombre, numero, String.valueOf(camNombre.getText()), String.valueOf(camNumero.getText()));
                                Toast alert = Toast.makeText(vista.getContext(), "Se ha modificado el contacto", Toast.LENGTH_SHORT);
                                alert.show();
                                nombre=String.valueOf(camNombre.getText());
                                cambio.setVisibility(View.INVISIBLE);
                                cambio.setEnabled(false);
                            }
                        });
                    }else{
                        cambio.setVisibility(View.INVISIBLE);
                        cambio.setEnabled(false);
                    }

                }
            });
            camNumero.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!numero.equals(String.valueOf(camNumero.getText()))){
                        cambio.setVisibility(View.VISIBLE);
                        cambio.setEnabled(true);
                        cambio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((MainActivity) getActivity()).actualizar(nombre, numero, String.valueOf(camNombre.getText()), String.valueOf(camNumero.getText()));
                                Toast alert = Toast.makeText(vista.getContext(), "Se ha modificado el contacto", Toast.LENGTH_SHORT);
                                alert.show();
                                numero=String.valueOf(camNumero.getText());
                                cambio.setVisibility(View.INVISIBLE);
                                cambio.setEnabled(false);
                            }
                        });
                    }else{
                        cambio.setVisibility(View.INVISIBLE);
                        cambio.setEnabled(false);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta();
                }
            });
            llamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri num = Uri.parse("tel:" + numero);
                    Intent intent = new Intent(Intent.ACTION_DIAL, num);
                    startActivity(intent);
                }
            });
            whats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("smsto:" + numero);
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.setPackage("com.whatsapp");
                    startActivity(Intent.createChooser(i, ""));
                }
            });
            favo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favori.equals("S")) {
                        ((MainActivity) getActivity()).borrarF(nombre, numero);
                        favo.setImageResource(R.drawable.apag);
                        favori="N";
                    } else {
                        ((MainActivity) getActivity()).añadirF(nombre, numero);
                        favo.setImageResource(R.drawable.encen);
                        favori="S";
                    }
                }
            });

        }

        return vista;
    }


    public void respuesta() {
        final boolean[] preuba = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.create();
        builder.setMessage("¿Desea borrar?")
                .setTitle("Confirmacion")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preuba[0] = true;
                        ((MainActivity) getActivity()).borrar(nombre, numero);
                        ((MainActivity) getActivity()).borrarF(nombre, numero);
                        ((MainActivity) getActivity()).cambiarLista();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preuba[0] = false;

                    }
                });
        builder.show();

    }


}
