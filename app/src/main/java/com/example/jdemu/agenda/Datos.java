package com.example.jdemu.agenda;

/**
 * Created by jdemu on 13/12/2017.
 */

public class Datos {
    String nombre;
    String numero;
    String fav;
    public Datos(String nombre, String numero,String fav) {
        this.nombre = nombre;
        this.numero = numero;
        this.fav=fav;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFav() {
        return fav;
    }

    public String getNumero() {
        return numero;
    }
}
