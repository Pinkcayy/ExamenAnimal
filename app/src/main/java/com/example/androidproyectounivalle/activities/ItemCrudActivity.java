package com.example.androidproyectounivalle.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.models.Animal;
import com.example.androidproyectounivalle.models.Ave;
import com.example.androidproyectounivalle.models.AveRapaz;
import com.example.androidproyectounivalle.models.Mamifero;
import com.example.androidproyectounivalle.models.Animal.InformacionAdicional;
import com.example.androidproyectounivalle.models.Animal.Dato;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ItemCrudActivity extends AppCompatActivity {

    private TextInputEditText etEspecie, etNombreCientifico, etHabitat, etEstadoConservacion, etPesoPromedio;
    private TextInputEditText etTemperaturaCorporal, etTiempoGestacion, etAlimentacion;
    private TextInputEditText etEnvergaduraAlas, etColorPlumaje, etTipoPico;
    private TextInputEditText etVelocidadVuelo, etTipoPresa;
    private TextInputEditText etEsperanzaVida;
    private Spinner spTipoAnimal;
    private Button btnGuardar;
    private View layoutMamifero, layoutAve, layoutAveRapaz;
    private int editPosition = -1;
    private Animal animalEditado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_crud);

        // Inicializar vistas
        inicializarVistas();

        // Revisar si se recibe un animal para editar
        String animalJson = getIntent().getStringExtra("animal_json");
        if (animalJson != null) {
            animalEditado = new Gson().fromJson(animalJson, Animal.class);
            editPosition = getIntent().getIntExtra("position", -1);
            llenarCamposConAnimal(animalEditado);
            btnGuardar.setText("Actualizar");
        }

        // Configurar spinner de tipos de animal
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_animal, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoAnimal.setAdapter(adapter);

        // Configurar listener del spinner
        spTipoAnimal.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                actualizarLayoutsVisibles(position);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        // Configurar botón guardar/actualizar
        btnGuardar.setOnClickListener(v -> validarYGuardar());
    }

    private void inicializarVistas() {
        etEspecie = findViewById(R.id.etTitle);
        etNombreCientifico = findViewById(R.id.etDescription);
        etHabitat = findViewById(R.id.etHabitat);
        etEstadoConservacion = findViewById(R.id.etEstadoConservacion);
        etPesoPromedio = findViewById(R.id.etPesoPromedio);
        spTipoAnimal = findViewById(R.id.spTipoAnimal);
        btnGuardar = findViewById(R.id.btnSave);

        // Layouts específicos
        layoutMamifero = findViewById(R.id.layoutMamifero);
        layoutAve = findViewById(R.id.layoutAve);
        layoutAveRapaz = findViewById(R.id.layoutAveRapaz);

        // Campos de mamífero
        etTemperaturaCorporal = findViewById(R.id.etTemperaturaCorporal);
        etTiempoGestacion = findViewById(R.id.etTiempoGestacion);
        etAlimentacion = findViewById(R.id.etAlimentacion);

        // Campos de ave
        etEnvergaduraAlas = findViewById(R.id.etEnvergaduraAlas);
        etColorPlumaje = findViewById(R.id.etColorPlumaje);
        etTipoPico = findViewById(R.id.etTipoPico);

        // Campos de ave rapaz
        etVelocidadVuelo = findViewById(R.id.etVelocidadVuelo);
        etTipoPresa = findViewById(R.id.etTipoPresa);

        // Campo de información adicional
        etEsperanzaVida = findViewById(R.id.etEsperanzaVida);
    }

    private void actualizarLayoutsVisibles(int tipoSeleccionado) {
        layoutMamifero.setVisibility(tipoSeleccionado == 0 ? View.VISIBLE : View.GONE);
        layoutAve.setVisibility(tipoSeleccionado == 1 ? View.VISIBLE : View.GONE);
        layoutAveRapaz.setVisibility(tipoSeleccionado == 2 ? View.VISIBLE : View.GONE);
    }

    private void llenarCamposConAnimal(Animal animal) {
        etEspecie.setText(animal.getEspecie());
        etNombreCientifico.setText(animal.getNombreCientifico());
        etHabitat.setText(animal.getHabitat());
        etEstadoConservacion.setText(animal.getEstadoConservacion());
        etPesoPromedio.setText(String.valueOf(animal.getPesoPromedio()));
        etEsperanzaVida.setText(String.valueOf(animal.getInformacionAdicional().getEsperanzaVida()));
        // Aquí puedes agregar lógica para llenar los campos específicos según el tipo
    }

    private void validarYGuardar() {
        if (validarCampos()) {
            mostrarDialogoConfirmacion();
        }
    }

    private boolean validarCampos() {
        boolean valido = true;

        if (TextUtils.isEmpty(etEspecie.getText())) {
            etEspecie.setError("Campo obligatorio");
            valido = false;
        }
        if (TextUtils.isEmpty(etNombreCientifico.getText())) {
            etNombreCientifico.setError("Campo obligatorio");
            valido = false;
        }
        if (TextUtils.isEmpty(etHabitat.getText())) {
            etHabitat.setError("Campo obligatorio");
            valido = false;
        }
        if (TextUtils.isEmpty(etEstadoConservacion.getText())) {
            etEstadoConservacion.setError("Campo obligatorio");
            valido = false;
        }
        if (TextUtils.isEmpty(etPesoPromedio.getText())) {
            etPesoPromedio.setError("Campo obligatorio");
            valido = false;
        }

        // Validar campos según tipo de animal
        int tipo = spTipoAnimal.getSelectedItemPosition();
        if (tipo == 0) { // Mamífero
            if (TextUtils.isEmpty(etTemperaturaCorporal.getText())) {
                etTemperaturaCorporal.setError("Campo obligatorio");
                valido = false;
            }
            if (TextUtils.isEmpty(etTiempoGestacion.getText())) {
                etTiempoGestacion.setError("Campo obligatorio");
                valido = false;
            }
            if (TextUtils.isEmpty(etAlimentacion.getText())) {
                etAlimentacion.setError("Campo obligatorio");
                valido = false;
            }
        } else if (tipo == 1) { // Ave
            if (TextUtils.isEmpty(etEnvergaduraAlas.getText())) {
                etEnvergaduraAlas.setError("Campo obligatorio");
                valido = false;
            }
            if (TextUtils.isEmpty(etColorPlumaje.getText())) {
                etColorPlumaje.setError("Campo obligatorio");
                valido = false;
            }
            if (TextUtils.isEmpty(etTipoPico.getText())) {
                etTipoPico.setError("Campo obligatorio");
                valido = false;
            }
        } else if (tipo == 2) { // Ave Rapaz
            if (TextUtils.isEmpty(etVelocidadVuelo.getText())) {
                etVelocidadVuelo.setError("Campo obligatorio");
                valido = false;
            }
            if (TextUtils.isEmpty(etTipoPresa.getText())) {
                etTipoPresa.setError("Campo obligatorio");
                valido = false;
            }
        }

        return valido;
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar")
                .setMessage("¿Deseas guardar este nuevo animal?")
                .setPositiveButton("Sí", (dialog, which) -> guardarAnimal())
                .setNegativeButton("No", null)
                .show();
    }

    private void guardarAnimal() {
        String especie = etEspecie.getText().toString();
        String nombreCientifico = etNombreCientifico.getText().toString();
        String habitat = etHabitat.getText().toString();
        String estadoConservacion = etEstadoConservacion.getText().toString();
        double pesoPromedio = Double.parseDouble(etPesoPromedio.getText().toString());
        int esperanzaVida = Integer.parseInt(etEsperanzaVida.getText().toString());

        // Crear información adicional con todos los datos relevantes
        List<Dato> datos = new ArrayList<>();
        datos.add(new Dato("Esperanza de vida", String.valueOf(esperanzaVida)));

        Animal animal = null;
        int tipo = spTipoAnimal.getSelectedItemPosition();

        switch (tipo) {
            case 0: // Mamífero
                double temperaturaCorporal = Double.parseDouble(etTemperaturaCorporal.getText().toString());
                int tiempoGestacion = Integer.parseInt(etTiempoGestacion.getText().toString());
                String alimentacion = etAlimentacion.getText().toString();
                datos.add(new Dato("Temperatura corporal", String.valueOf(temperaturaCorporal)));
                datos.add(new Dato("Tiempo de gestación", String.valueOf(tiempoGestacion)));
                datos.add(new Dato("Alimentación", alimentacion));
                animal = new Mamifero(especie, nombreCientifico, habitat, pesoPromedio,
                        estadoConservacion, new InformacionAdicional(esperanzaVida, datos),
                        temperaturaCorporal, tiempoGestacion, alimentacion);
                break;
            case 1: // Ave
                double envergaduraAlas = Double.parseDouble(etEnvergaduraAlas.getText().toString());
                String colorPlumaje = etColorPlumaje.getText().toString();
                String tipoPico = etTipoPico.getText().toString();
                datos.add(new Dato("Envergadura de alas", String.valueOf(envergaduraAlas)));
                datos.add(new Dato("Color de plumaje", colorPlumaje));
                datos.add(new Dato("Tipo de pico", tipoPico));
                animal = new Ave(especie, nombreCientifico, habitat, pesoPromedio,
                        estadoConservacion, new InformacionAdicional(esperanzaVida, datos),
                        envergaduraAlas, colorPlumaje, tipoPico);
                break;
            case 2: // Ave Rapaz
                envergaduraAlas = Double.parseDouble(etEnvergaduraAlas.getText().toString());
                colorPlumaje = etColorPlumaje.getText().toString();
                tipoPico = etTipoPico.getText().toString();
                double velocidadVuelo = Double.parseDouble(etVelocidadVuelo.getText().toString());
                String tipoPresa = etTipoPresa.getText().toString();
                datos.add(new Dato("Envergadura de alas", String.valueOf(envergaduraAlas)));
                datos.add(new Dato("Color de plumaje", colorPlumaje));
                datos.add(new Dato("Tipo de pico", tipoPico));
                datos.add(new Dato("Velocidad de vuelo", String.valueOf(velocidadVuelo)));
                datos.add(new Dato("Tipo de presa", tipoPresa));
                animal = new AveRapaz(especie, nombreCientifico, habitat, pesoPromedio,
                        estadoConservacion, new InformacionAdicional(esperanzaVida, datos),
                        envergaduraAlas, colorPlumaje, tipoPico,
                        velocidadVuelo, tipoPresa);
                break;
        }

        if (animal != null) {
            // Preparar el resultado
            Intent resultIntent = new Intent();
            resultIntent.putExtra("animal_json", new Gson().toJson(animal));
            resultIntent.putExtra("position", editPosition);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }
}
