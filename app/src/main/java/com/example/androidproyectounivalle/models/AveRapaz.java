package com.example.androidproyectounivalle.models;

import java.io.Serializable;

public class AveRapaz extends Ave implements Serializable {
    private double velocidadVuelo;
    private String tipoPresa;

    // Constructor vacío necesario para Gson
    public AveRapaz() {
        super();
    }

    public AveRapaz(String especie, String nombreCientifico, String habitat, double pesoPromedio, 
                   String estadoConservacion, InformacionAdicional informacionAdicional,
                   double envergaduraAlas, String colorPlumaje, String tipoPico,
                   double velocidadVuelo, String tipoPresa) {
        super(especie, nombreCientifico, habitat, pesoPromedio, estadoConservacion, 
              informacionAdicional, envergaduraAlas, colorPlumaje, tipoPico);
        this.velocidadVuelo = velocidadVuelo;
        this.tipoPresa = tipoPresa;
    }

    // Getters
    public double getVelocidadVuelo() {
        return velocidadVuelo;
    }

    public String getTipoPresa() {
        return tipoPresa;
    }

    // Setters
    public void setVelocidadVuelo(double velocidadVuelo) {
        this.velocidadVuelo = velocidadVuelo;
    }

    public void setTipoPresa(String tipoPresa) {
        this.tipoPresa = tipoPresa;
    }
}
