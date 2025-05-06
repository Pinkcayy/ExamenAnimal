package com.example.androidproyectounivalle.models;

import java.io.Serializable;
import java.util.List;

public class Animal implements Serializable {
    private String especie;
    private String nombreCientifico;
    private String habitat;
    private double pesoPromedio;
    private String estadoConservacion;
    private InformacionAdicional informacionAdicional;

    // Constructor vacío necesario para Gson
    public Animal() {
    }

    public Animal(String especie, String nombreCientifico, String habitat, double pesoPromedio, 
                 String estadoConservacion, InformacionAdicional informacionAdicional) {
        this.especie = especie;
        this.nombreCientifico = nombreCientifico;
        this.habitat = habitat;
        this.pesoPromedio = pesoPromedio;
        this.estadoConservacion = estadoConservacion;
        this.informacionAdicional = informacionAdicional;
    }

    // Getters
    public String getEspecie() {
        return especie;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public String getHabitat() {
        return habitat;
    }

    public double getPesoPromedio() {
        return pesoPromedio;
    }

    public String getEstadoConservacion() {
        return estadoConservacion;
    }

    public InformacionAdicional getInformacionAdicional() {
        return informacionAdicional;
    }

    // Setters
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public void setPesoPromedio(double pesoPromedio) {
        this.pesoPromedio = pesoPromedio;
    }

    public void setEstadoConservacion(String estadoConservacion) {
        this.estadoConservacion = estadoConservacion;
    }

    public void setInformacionAdicional(InformacionAdicional informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public static class InformacionAdicional implements Serializable {
        private int esperanzaVida;
        private List<Dato> datos;

        // Constructor vacío necesario para Gson
        public InformacionAdicional() {
        }

        public InformacionAdicional(int esperanzaVida, List<Dato> datos) {
            this.esperanzaVida = esperanzaVida;
            this.datos = datos;
        }

        public int getEsperanzaVida() {
            return esperanzaVida;
        }

        public void setEsperanzaVida(int esperanzaVida) {
            this.esperanzaVida = esperanzaVida;
        }

        public List<Dato> getDatos() {
            return datos;
        }

        public void setDatos(List<Dato> datos) {
            this.datos = datos;
        }
    }

    public static class Dato implements Serializable {
        private String nombreDato;
        private String valor;

        // Constructor vacío necesario para Gson
        public Dato() {
        }

        public Dato(String nombreDato, String valor) {
            this.nombreDato = nombreDato;
            this.valor = valor;
        }

        public String getNombreDato() {
            return nombreDato;
        }

        public void setNombreDato(String nombreDato) {
            this.nombreDato = nombreDato;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }
}
