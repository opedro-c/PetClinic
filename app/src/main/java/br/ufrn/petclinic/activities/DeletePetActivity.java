package br.ufrn.petclinic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrn.petclinic.R;

public class DeletePetActivity extends AppCompatActivity {

    private TextView id;
    private Button deletePet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_pet);

        id = findViewById(R.id.id);
        deletePet = findViewById(R.id.delete);

        deletePet.setOnClickListener(view -> {
            if (id.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter pet id!", Toast.LENGTH_SHORT).show();
                return;
            }
            // enter delete logic
            Toast.makeText(this, "Successfully deleted!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ManagePetsActivity.class));
        });
    }
}