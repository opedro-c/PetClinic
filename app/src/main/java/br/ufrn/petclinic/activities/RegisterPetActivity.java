package br.ufrn.petclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;

import java.util.stream.Stream;

import br.ufrn.petclinic.R;
import br.ufrn.petclinic.models.Pet;
import br.ufrn.petclinic.repositories.PetRepository;

public class RegisterPetActivity extends AppCompatActivity {

    Pet pet;
    private TextView fileFeedback;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet);

        pet = new Pet();
        fileFeedback = findViewById(R.id.path);

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia = preparePickMedia();

        PickVisualMediaRequest pickMediaRequest = preparePickMediaRequest();

        Button selectPetPicture = findViewById(R.id.select_pet_picture);
        selectPetPicture.setOnClickListener(view -> pickMedia.launch(pickMediaRequest));

        Button registerPet = findViewById(R.id.register_pet);
        registerPet.setOnClickListener(view -> {
            String id = ((TextView) findViewById(R.id.id)).getText().toString();
            String name = ((TextView) findViewById(R.id.pet_name)).getText().toString();
            String appointmentDate = ((TextView) findViewById(R.id.pet_appointment_date)).getText().toString();
            String picturePath = path;
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
            int checkedRadio = radioGroup.getCheckedRadioButtonId();

            if (Stream.of(id, name, appointmentDate, picturePath).anyMatch(str -> str == null || str.isEmpty()) && checkedRadio == -1) {
                Toast.makeText(this, "Please, fill up all the fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            pet.setName(name);
            pet.setAppointmentDate(appointmentDate);
            pet.setId(id);
            pet.setPicturePath(picturePath);

            PetRepository petRepository = new PetRepository(getApplicationContext());
            petRepository.addPet(pet);

            Toast.makeText(this, "Pet registered successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ManagePetsActivity.class));
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        int id = view.getId();
        if (id == R.id.dog) {
            if (checked) {
                pet.setType("dog");
            }
        } else if (id == R.id.cat) {
            if (checked) {
                pet.setType("cat");
            }
        }
    }

    @NonNull
    private static PickVisualMediaRequest preparePickMediaRequest() {
        return new PickVisualMediaRequest.Builder()
                .setMediaType((PickVisualMedia.VisualMediaType) PickVisualMedia.ImageOnly.INSTANCE)
                .build();
    }

    @NonNull
    private ActivityResultLauncher<PickVisualMediaRequest> preparePickMedia() {
        return registerForActivityResult(new PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                String petPicturePath = uri.toString();
                path = petPicturePath;
                fileFeedback.setText("Picture loaded!");
                Log.d("PhotoPicker", "Selected URI: " + uri);
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });
    }
}