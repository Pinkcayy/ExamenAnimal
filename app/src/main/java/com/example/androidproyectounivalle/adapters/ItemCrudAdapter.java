package com.example.androidproyectounivalle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.models.Animal;

import java.util.List;

public class ItemCrudAdapter extends RecyclerView.Adapter<ItemCrudAdapter.ItemViewHolder> {

    private List<Animal> animals;
    private Context context;
    private OnItemCrudListener listener;

    public interface OnItemCrudListener {
        void onEditClick(Animal animal, int position);
        void onDeleteClick(Animal animal, int position);
        void onItemClick(Animal animal, int position);
    }

    public ItemCrudAdapter(List<Animal> animals, Context context, OnItemCrudListener listener) {
        this.animals = animals;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crud, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Animal animal = animals.get(position);

        holder.tvItemTitle.setText(animal.getEspecie());
        holder.tvItemDescription.setText(animal.getNombreCientifico());

        // Configurar el indicador de estado según el estado de conservación
        boolean isEndangered = animal.getEstadoConservacion().toLowerCase().contains("en peligro");
        holder.statusIndicator.setBackgroundResource(
                isEndangered ? R.drawable.circle_red : R.drawable.circle_green);

        // Configurar listeners
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(animal, holder.getAdapterPosition());
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(animal, holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(animal, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public void updateItems(List<Animal> newAnimals) {
        this.animals = newAnimals;
        notifyDataSetChanged();
    }

    public void addItem(Animal animal) {
        animals.add(animal);
        notifyItemInserted(animals.size() - 1);
    }

    public void updateItem(Animal animal, int position) {
        animals.set(position, animal);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        animals.remove(position);
        notifyItemRemoved(position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemTitle;
        TextView tvItemDescription;
        View statusIndicator;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tvItemTitle);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
