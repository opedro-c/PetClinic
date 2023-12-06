package br.ufrn.petclinic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrn.petclinic.models.Pet;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Pet> pets;

    public Adapter(List<Pet> pets) {
        this.pets = pets;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_adapter, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pet pet = pets.get(position);
        holder.name.setText(pet.getName());
        holder.birthday.setText(pet.getBirthday());
        holder.picture.setImageResource(R.drawable.img);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView birthday;
        private ImageView picture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            birthday = itemView.findViewById(R.id.birthday);
            picture = itemView.findViewById(R.id.picture);
        }
    }
}
