package com.example.androidproyectounivalle.models;

import java.io.Serializable;

public class Mamifero extends Animal implements Serializable {
    private double temperaturaCorporal;
    private int tiempoGestacion;
    private String alimentacion;

    // Constructor vacío necesario para Gson
    public Mamifero() {
        super();
    }

    public Mamifero(String especie, String nombreCientifico, String habitat, double pesoPromedio, 
                   String estadoConservacion, InformacionAdicional informacionAdicional,
                   double temperaturaCorporal, int tiempoGestacion, String alimentacion) {
        super(especie, nombreCientifico, habitat, pesoPromedio, estadoConservacion, informacionAdicional);
        this.temperaturaCorporal = temperaturaCorporal;
        this.tiempoGestacion = tiempoGestacion;
        this.alimentacion = alimentacion;
    }

    // Getters
    public double getTemperaturaCorporal() {
        return temperaturaCorporal;
    }

    public int getTiempoGestacion() {
        return tiempoGestacion;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    // Setters
    public void setTemperaturaCorporal(double temperaturaCorporal) {
        this.temperaturaCorporal = temperaturaCorporal;
    }

    public void setTiempoGestacion(int tiempoGestacion) {
        this.tiempoGestacion = tiempoGestacion;
    }

    public void setAlimentacion(String alimentacion) {
        this.alimentacion = alimentacion;
    }
}
