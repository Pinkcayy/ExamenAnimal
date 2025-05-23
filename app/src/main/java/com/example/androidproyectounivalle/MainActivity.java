package com.example.androidproyectounivalle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproyectounivalle.activities.AnimalApiActivity;
import com.example.androidproyectounivalle.adapters.MenuAdapter;
import com.example.androidproyectounivalle.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridViewMenu;
    private MenuAdapter menuAdapter;
    private List<MenuItem> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Establecer el título de la actividad
        setTitle("Catálogo de Animales");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar GridView
        gridViewMenu = findViewById(R.id.gridViewMenu);

        // Crear elementos del menú
        initMenuItems();

        // Configurar adaptador
        menuAdapter = new MenuAdapter(this, menuItems);
        gridViewMenu.setAdapter(menuAdapter);

        // Configurar evento de clic
        gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Manejar la selección del menú
                handleMenuSelection(position);
            }
        });
    }

    private void initMenuItems() {
        menuItems = new ArrayList<>();
        // Solo mantener el botón de Animales
        menuItems.add(new MenuItem("Animales", android.R.drawable.ic_menu_compass));
    }

    private void handleMenuSelection(int position) {
        Intent intent = null;

        switch (position) {
            case 0: // Animales
                intent = new Intent(this, AnimalApiActivity.class);
                break;
            default:
                Toast.makeText(this, "Opción no implementada aún", Toast.LENGTH_SHORT).show();
                return;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
