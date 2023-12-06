package br.ufrn.petclinic.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.stream.Stream;

import br.ufrn.petclinic.R;
import br.ufrn.petclinic.models.Pet;

public class EditPetActivity extends AppCompatActivity {

    private String id;
    private TextView nameField;
    private TextView appointmentDateField;
    private TextView path;
    private Pet pet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);

        Bundle extras = getIntent().getExtras();

        nameField = findViewById(R.id.pet_name);
        appointmentDateField = findViewById(R.id.pet_appointment_date);

        id = extras.getString("id");
        nameField.setText(extras.getString("name"));
        appointmentDateField.setText(extras.getString("appointmentDate"));
        path = findViewById(R.id.path);
        pet = new Pet();

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia = preparePickMedia();

        PickVisualMediaRequest pickMediaRequest = preparePickMediaRequest();

        Button selectPetPicture = findViewById(R.id.select_pet_picture);
        selectPetPicture.setOnClickListener(view -> pickMedia.launch(pickMediaRequest));

        Button editPet = findViewById(R.id.edit_pet);
        editPet.setOnClickListener(view -> {
            String name = nameField.getText().toString();
            String appointmentDate = appointmentDateField.getText().toString();
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


    @NonNull
    private static PickVisualMediaRequest preparePickMediaRequest() {
        return new PickVisualMediaRequest.Builder()
                .setMediaType((ActivityResultContracts.PickVisualMedia.VisualMediaType) ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build();
    }

    @NonNull
    private ActivityResultLauncher<PickVisualMediaRequest> preparePickMedia() {
        return registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
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
    }
}