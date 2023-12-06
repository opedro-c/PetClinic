package br.ufrn.petclinic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.petclinic.Adapter;
import br.ufrn.petclinic.R;
import br.ufrn.petclinic.models.Pet;

public class ManagePetsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Pet> pets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_pets);

        Button registerPet = findViewById(R.id.register);
        Button deletePet = findViewById(R.id.delete);
        registerPet.setOnClickListener(view -> startActivity(new Intent(this, RegisterPetActivity.class)));
        deletePet.setOnClickListener(view -> startActivity(new Intent(this, DeletePetActivity.class)));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        createPets();

        recyclerView.setAdapter(new Adapter(pets));
    }

    private void createPets() {
        pets.add((new Pet("the bob", "Bob", "01/01/2001", "drawable://img.png")));
    }
}