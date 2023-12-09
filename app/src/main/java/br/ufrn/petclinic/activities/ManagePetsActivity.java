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
import br.ufrn.petclinic.PetsSingleton;
import br.ufrn.petclinic.R;
import br.ufrn.petclinic.models.Pet;
import br.ufrn.petclinic.repositories.PetRepository;

public class ManagePetsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

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

        PetRepository petRepository = new PetRepository(getApplicationContext());
        List<Pet> pets = petRepository.getAllPets();

        recyclerView.setAdapter(new Adapter(pets));
    }
}