package br.ufrn.petclinic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import br.ufrn.petclinic.activities.EditPetActivity;
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
        holder.type = pet.getType();
        holder.appointmentDate.setText(pet.getAppointmentDate());
        Uri imageURI = Uri.parse(pet.getPicturePath());
        holder.picture.setImageURI(imageURI);
        holder.id = pet.getId();
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {

        private String id;
        private TextView name;

        private String type;
        private TextView appointmentDate;
        private ImageView picture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            appointmentDate = itemView.findViewById(R.id.birthday);
            picture = itemView.findViewById(R.id.picture);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), EditPetActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("type", type);
                intent.putExtra("appointmentDate", appointmentDate.getText().toString());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
