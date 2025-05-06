package com.example.androidproyectounivalle.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.models.Animal;
import com.example.androidproyectounivalle.models.Ave;
import com.example.androidproyectounivalle.models.AveRapaz;
import com.example.androidproyectounivalle.models.Mamifero;

public class DetalleAnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_animal);

        Animal animal = (Animal) getIntent().getSerializableExtra("animal");
        if (animal != null) {
            mostrarDetallesAnimal(animal);
        }
    }

    private void mostrarDetallesAnimal(Animal animal) {
        // Configurar imagen según el tipo
        ImageView ivAnimal = findViewById(R.id.ivAnimal);
        int imageResource = R.drawable.simbolomamifero; // por defecto
        if (animal instanceof Mamifero) {
            imageResource = R.drawable.simbolomamifero;
        } else if (animal instanceof AveRapaz) {
            imageResource = R.drawable.simboloaverapaz;
        } else if (animal instanceof Ave) {
            imageResource = R.drawable.simboloave;
        }
        ivAnimal.setImageResource(imageResource);

        // Mostrar información común
        ((TextView) findViewById(R.id.tvEspecie)).setText(animal.getEspecie());
        ((TextView) findViewById(R.id.tvNombreCientifico)).setText(animal.getNombreCientifico());
        ((TextView) findViewById(R.id.tvHabitat)).setText(animal.getHabitat());
        ((TextView) findViewById(R.id.tvEstadoConservacion)).setText(animal.getEstadoConservacion());
        ((TextView) findViewById(R.id.tvPesoPromedio)).setText(animal.getPesoPromedio() + " kg");
        ((TextView) findViewById(R.id.tvEsperanzaVida)).setText(
            animal.getInformacionAdicional().getEsperanzaVida() + " años");

        // Mostrar información específica según el tipo
        if (animal instanceof Mamifero) {
            Mamifero mamifero = (Mamifero) animal;
            findViewById(R.id.layoutMamifero).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.tvTemperaturaCorporal)).setText(
                mamifero.getTemperaturaCorporal() + "°C");
            ((TextView) findViewById(R.id.tvTiempoGestacion)).setText(
                mamifero.getTiempoGestacion() + " días");
            ((TextView) findViewById(R.id.tvAlimentacion)).setText(mamifero.getAlimentacion());
        } else if (animal instanceof AveRapaz) {
            AveRapaz aveRapaz = (AveRapaz) animal;
            findViewById(R.id.layoutAveRapaz).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.tvVelocidadVuelo)).setText(
                aveRapaz.getVelocidadVuelo() + " km/h");
            ((TextView) findViewById(R.id.tvTipoPresa)).setText(aveRapaz.getTipoPresa());
        } else if (animal instanceof Ave) {
            Ave ave = (Ave) animal;
            findViewById(R.id.layoutAve).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.tvEnvergaduraAlas)).setText(
                ave.getEnvergaduraAlas() + " cm");
            ((TextView) findViewById(R.id.tvColorPlumaje)).setText(ave.getColorPlumaje());
        }

        // Mostrar datos adicionales
        if (!animal.getInformacionAdicional().getDatos().isEmpty()) {
            findViewById(R.id.layoutDatosAdicionales).setVisibility(View.VISIBLE);
            StringBuilder datos = new StringBuilder();
            for (Animal.Dato dato : animal.getInformacionAdicional().getDatos()) {
                datos.append(dato.getNombreDato()).append(": ")
                    .append(dato.getValor()).append("\n");
            }
            ((TextView) findViewById(R.id.tvDatosAdicionales)).setText(datos.toString());
        }
    }
} 