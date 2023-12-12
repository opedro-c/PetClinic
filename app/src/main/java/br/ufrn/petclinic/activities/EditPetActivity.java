package br.ufrn.petclinic.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.stream.Stream;

import br.ufrn.petclinic.R;
import br.ufrn.petclinic.models.Pet;
import br.ufrn.petclinic.repositories.PetRepository;

public class EditPetActivity extends AppCompatActivity {

    private String id;
    private TextView nameField;
    private TextView appointmentDateField;
    private RadioGroup radioGroup;
    private TextView fileFeedback;
    private Pet pet;
    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);

        Bundle extras = getIntent().getExtras();

        getFields();

        loadFields(extras);

        pet = new Pet();

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia = preparePickMedia();

        PickVisualMediaRequest pickMediaRequest = preparePickMediaRequest();

        Button selectPetPicture = findViewById(R.id.select_pet_picture);
        selectPetPicture.setOnClickListener(view -> pickMedia.launch(pickMediaRequest));

        Button editPet = findViewById(R.id.edit_pet);
        editPet.setOnClickListener(view -> {
            String name = nameField.getText().toString();
            String appointmentDate = appointmentDateField.getText().toString();
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
            petRepository.updatePet(pet);

            Toast.makeText(this, "Pet registered successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ManagePetsActivity.class));
        });
    }

    private void getFields() {
        nameField = findViewById(R.id.pet_name);
        appointmentDateField = findViewById(R.id.pet_appointment_date);
        radioGroup = findViewById(R.id.radioGroup);
    }

    private void loadFields(Bundle extras) {
        id = extras.getString("id");
        nameField.setText(extras.getString("name"));
        appointmentDateField.setText(extras.getString("appointmentDate"));
        switch (extras.getString("type")) {
            case "dog":
                radioGroup.check(R.id.dog);
                break;
            case "cat":
                radioGroup.check(R.id.cat);
        }
        fileFeedback = findViewById(R.id.path);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        int viewId = view.getId();
        if (viewId == R.id.dog) {
            if (checked) {
                pet.setType("dog");
            }
        } else if (viewId == R.id.cat) {
            if (checked) {
                pet.setType("cat");
            }
        }
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