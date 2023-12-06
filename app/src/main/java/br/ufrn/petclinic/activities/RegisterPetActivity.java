package br.ufrn.petclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;

import java.util.Arrays;
import java.util.stream.Stream;

import br.ufrn.petclinic.R;
import br.ufrn.petclinic.models.Pet;

public class RegisterPetActivity extends AppCompatActivity {

    Pet pet;
    private TextView path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet);

        pet = new Pet();
        path = findViewById(R.id.path);

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    String petPicturePath = uri.getPath();
                    path.setText(petPicturePath);
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });

        PickVisualMediaRequest pickMediaRequest = new PickVisualMediaRequest.Builder()
                .setMediaType((PickVisualMedia.VisualMediaType) PickVisualMedia.ImageOnly.INSTANCE)
                .build();

        Button selectPetPicture = findViewById(R.id.select_pet_picture);
        selectPetPicture.setOnClickListener(view -> pickMedia.launch(pickMediaRequest));

        Button registerPet = findViewById(R.id.register_pet);
        registerPet.setOnClickListener(view -> {
            String id = ((TextView) findViewById(R.id.id)).getText().toString();
            String name = ((TextView) findViewById(R.id.pet_name)).getText().toString();
            String appointmentDate = ((TextView) findViewById(R.id.pet_appointment_date)).getText().toString();
            String picturePath = path.getText().toString();

            if (Stream.of(id, name, appointmentDate, picturePath).anyMatch(str -> str == null || str.isEmpty())) {
                Toast.makeText(this, "Please, fill up all the fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            pet.setName(name);
            pet.setAppointmentDate(appointmentDate);
            pet.setId(id);
            pet.setPicturePath(picturePath);

            Toast.makeText(this, "Pet registered successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ManagePetsActivity.class));
        });
    }
}