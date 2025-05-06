package com.example.androidproyectounivalle.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AnimalStorage {
    private static final String PREFS_NAME = "animal_prefs";
    private static final String KEY_ANIMALES = "animales";

    public static void guardarTodosLosAnimales(Context context, List<Animal> lista) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lista);
        editor.putString(KEY_ANIMALES, json);
        editor.apply();
    }

    public static List<Animal> obtenerTodosLosAnimales(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_ANIMALES, null);
        if (json == null) return new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Animal>>(){}.getType();
        return gson.fromJson(json, type);
    }
} 