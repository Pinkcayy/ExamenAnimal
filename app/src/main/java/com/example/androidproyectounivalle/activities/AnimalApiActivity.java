package com.example.androidproyectounivalle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.adapters.PokemonAdapter;
import com.example.androidproyectounivalle.models.Animal;
import com.example.androidproyectounivalle.models.Ave;
import com.example.androidproyectounivalle.models.AveRapaz;
import com.example.androidproyectounivalle.models.Mamifero;
import com.example.androidproyectounivalle.models.Animal.InformacionAdicional;
import com.example.androidproyectounivalle.models.Animal.Dato;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnimalApiActivity extends AppCompatActivity implements PokemonAdapter.OnAnimalClickListener {

    private RecyclerView rvAnimales;
    private PokemonAdapter adapter;
    private List<Animal> animalList;
    private ProgressBar progressBar;
    private Button btnCargar;
    private FloatingActionButton fabAddAnimal;
    private RequestQueue requestQueue;
    private final String API_URL = "https://raw.githubusercontent.com/adancondori/TareaAPI/refs/heads/main/api/animales.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_api);

        // Inicializar vistas
        rvAnimales = findViewById(R.id.rvPokemon);
        progressBar = findViewById(R.id.progressBar);
        btnCargar = findViewById(R.id.btnCargar);
        fabAddAnimal = findViewById(R.id.fabAddAnimal);

        // Inicializar Volley Request Queue
        requestQueue = Volley.newRequestQueue(this);

        // Inicializar lista y adaptador
        animalList = new ArrayList<>();
        adapter = new PokemonAdapter(animalList, this, this);
        rvAnimales.setLayoutManager(new LinearLayoutManager(this));
        rvAnimales.setAdapter(adapter);

        // Ocultar la lista al inicio
        rvAnimales.setVisibility(View.GONE);

        // Configurar botón de carga
        btnCargar.setOnClickListener(v -> cargarAnimales());

        // Configurar FAB
        fabAddAnimal.setOnClickListener(v -> {
            Intent intent = new Intent(AnimalApiActivity.this, ItemCrudActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String animalJson = data.getStringExtra("animal_json");
            int position = data.getIntExtra("position", -1);
            
            if (animalJson != null) {
                Animal animalActualizado = new Gson().fromJson(animalJson, Animal.class);
                if (position != -1) {
                    // Actualizar animal existente
                    animalList.set(position, animalActualizado);
                    adapter.updateAnimalList(animalList);
                } else {
                    // Agregar nuevo animal
                    animalList.add(animalActualizado);
                    adapter.updateAnimalList(animalList);
                }
                
                // Guardar la lista actualizada en SharedPreferences
                com.example.androidproyectounivalle.models.AnimalStorage.guardarTodosLosAnimales(this, animalList);
            }
        }
    }

    private void cargarAnimales() {
        progressBar.setVisibility(View.VISIBLE);
        animalList.clear();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                API_URL,
                null,
                response -> {
                    try {
                        JSONArray animales = response.getJSONArray("animales");
                        for (int i = 0; i < animales.length(); i++) {
                            JSONObject animalJson = animales.getJSONObject(i);
                            Animal animal = parseAnimal(animalJson);
                            if (animal != null) {
                                animalList.add(animal);
                            }
                        }
                        
                        // Cargar animales guardados localmente
                        List<Animal> animalesLocales = com.example.androidproyectounivalle.models.AnimalStorage.obtenerTodosLosAnimales(this);
                        
                        // Filtrar duplicados basados en especie y nombre científico
                        for (Animal animalLocal : animalesLocales) {
                            boolean esDuplicado = false;
                            for (Animal animalApi : animalList) {
                                if (animalLocal.getEspecie().equals(animalApi.getEspecie()) &&
                                    animalLocal.getNombreCientifico().equals(animalApi.getNombreCientifico())) {
                                    esDuplicado = true;
                                    break;
                                }
                            }
                            if (!esDuplicado) {
                                animalList.add(animalLocal);
                            }
                        }
                        
                        adapter.updateAnimalList(animalList);
                        rvAnimales.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Error al procesar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(request);
    }

    private Animal parseAnimal(JSONObject animalJson) throws JSONException {
        String tipo = animalJson.getString("tipo");
        String especie = animalJson.getString("especie");
        String nombreCientifico = animalJson.getString("nombreCientifico");
        String habitat = animalJson.getString("habitat");
        double pesoPromedio = animalJson.getDouble("pesoPromedio");
        String estadoConservacion = animalJson.getString("estadoConservacion");

        // Parsear información adicional
        JSONObject infoAdicionalJson = animalJson.getJSONObject("informacionAdicional");
        int esperanzaVida = infoAdicionalJson.getInt("esperanzaVida");
        JSONArray datosJson = infoAdicionalJson.getJSONArray("datos");
        List<Dato> datos = new ArrayList<>();
        for (int i = 0; i < datosJson.length(); i++) {
            JSONObject datoJson = datosJson.getJSONObject(i);
            datos.add(new Dato(
                datoJson.getString("nombreDato"),
                datoJson.getString("valor")
            ));
        }
        InformacionAdicional informacionAdicional = new InformacionAdicional(esperanzaVida, datos);

        switch (tipo.toLowerCase()) {
            case "mamifero":
                double temperaturaCorporal = animalJson.getDouble("temperaturaCorporal");
                int tiempoGestacion = animalJson.getInt("tiempoGestacion");
                String alimentacion = animalJson.getString("alimentacion");
                return new Mamifero(especie, nombreCientifico, habitat, pesoPromedio, 
                                  estadoConservacion, informacionAdicional,
                                  temperaturaCorporal, tiempoGestacion, alimentacion);

            case "ave":
                double envergaduraAlas = animalJson.getDouble("envergaduraAlas");
                String colorPlumaje = animalJson.getString("colorPlumaje");
                String tipoPico = animalJson.getString("tipoPico");
                return new Ave(especie, nombreCientifico, habitat, pesoPromedio, 
                             estadoConservacion, informacionAdicional,
                             envergaduraAlas, colorPlumaje, tipoPico);

            case "averapaz":
                envergaduraAlas = animalJson.getDouble("envergaduraAlas");
                colorPlumaje = animalJson.getString("colorPlumaje");
                tipoPico = animalJson.getString("tipoPico");
                double velocidadVuelo = animalJson.getDouble("velocidadVuelo");
                String tipoPresa = animalJson.getString("tipoPresa");
                return new AveRapaz(especie, nombreCientifico, habitat, pesoPromedio, 
                                  estadoConservacion, informacionAdicional,
                                  envergaduraAlas, colorPlumaje, tipoPico,
                                  velocidadVuelo, tipoPresa);

            default:
                return null;
        }
    }

    @Override
    public void onAnimalClick(Animal animal, int position) {
        // Abrir actividad de detalle
        Intent intent = new Intent(this, DetalleAnimalActivity.class);
        intent.putExtra("animal", animal);
        startActivity(intent);
    }

    @Override
    public void onEditClick(Animal animal, int position) {
        // Abrir actividad de edición
        Intent intent = new Intent(this, ItemCrudActivity.class);
        String animalJson = new Gson().toJson(animal);
        intent.putExtra("animal_json", animalJson);
        intent.putExtra("position", position);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onDeleteClick(Animal animal, int position) {
        // Eliminar animal de la lista
        animalList.remove(position);
        adapter.updateAnimalList(animalList);
        
        // Actualizar SharedPreferences
        com.example.androidproyectounivalle.models.AnimalStorage.guardarTodosLosAnimales(this, animalList);
        
        Toast.makeText(this, "Animal eliminado", Toast.LENGTH_SHORT).show();
    }
}
