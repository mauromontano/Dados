package com.example.satiro.dados;

public class MiEntidad {

    /**
     * Variables locales
     */

    private int id;
    private String nombre;
    private int puntaje;

    /**
     * Constructor
     */

    public MiEntidad(String n, int p){
        //id= i;
        nombre= n;
        puntaje= p;
    }

    /**
     * Obtener el nombre de la entidad
     */

    public String getNombre(){
        return nombre;
    }

    /**
     * Modificar el nombre de la entidad
     */

    public void setNombre(String n){
        nombre= n;
    }

    /**
     * Obtener el puntaje de la entidad
     */

    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Obtener el id de la entidad
     */

    public int getId() {
        return id;
    }

    /**
     * Modificar el id de la entidad
     */

    public void setId(int id) {
        this.id = id;
    }

}
