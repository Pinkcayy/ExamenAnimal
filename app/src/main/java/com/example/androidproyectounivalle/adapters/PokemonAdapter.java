package com.example.androidproyectounivalle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.models.Animal;
import com.example.androidproyectounivalle.models.Ave;
import com.example.androidproyectounivalle.models.AveRapaz;
import com.example.androidproyectounivalle.models.Mamifero;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.AnimalViewHolder> {

    private List<Animal> animalList;
    private Context context;
    private OnAnimalClickListener listener;

    public interface OnAnimalClickListener {
        void onAnimalClick(Animal animal, int position);
        void onEditClick(Animal animal, int position);
        void onDeleteClick(Animal animal, int position);
    }

    public PokemonAdapter(List<Animal> animalList, Context context, OnAnimalClickListener listener) {
        this.animalList = animalList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);

        // Configurar datos básicos
        holder.tvName.setText(animal.getEspecie());
        holder.tvEsperanzaVida.setText("Esperanza de vida: " + 
            animal.getInformacionAdicional().getEsperanzaVida() + " años");

        // Configurar tipo de animal y estilo
        if (animal instanceof Mamifero) {
            holder.tvTipoAnimal.setText("Mamífero");
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.mamifero_bg));
            holder.ivAnimal.setImageResource(R.drawable.simbolomamifero);
        } else if (animal instanceof AveRapaz) {
            holder.tvTipoAnimal.setText("Ave Rapaz");
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.averapaz_bg));
            holder.ivAnimal.setImageResource(R.drawable.simboloaverapaz);
        } else if (animal instanceof Ave) {
            holder.tvTipoAnimal.setText("Ave");
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.ave_bg));
            holder.ivAnimal.setImageResource(R.drawable.simboloave);
        }

        // Configurar listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAnimalClick(animal, holder.getAdapterPosition());
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(animal, holder.getAdapterPosition());
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                .setTitle("Eliminar Animal")
                .setMessage("¿Estás seguro que deseas eliminar este animal?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    if (listener != null) {
                        listener.onDeleteClick(animal, holder.getAdapterPosition());
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public void updateAnimalList(List<Animal> newAnimalList) {
        this.animalList = newAnimalList;
        notifyDataSetChanged();
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView tvName, tvEsperanzaVida, tvTipoAnimal;
        ImageView ivAnimal;
        ImageButton btnEdit, btnDelete;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            tvName = itemView.findViewById(R.id.tvPokemonName);
            tvEsperanzaVida = itemView.findViewById(R.id.tvEsperanzaVida);
            tvTipoAnimal = itemView.findViewById(R.id.tvTipoAnimal);
            ivAnimal = itemView.findViewById(R.id.ivPokemon);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
