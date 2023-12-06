package br.ufrn.petclinic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import br.ufrn.petclinic.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button managePets = findViewById(R.id.manage_pets);
        Button manageAppointments = findViewById(R.id.manage_appointments);

        managePets.setOnClickListener(view -> startActivity(new Intent(this, ManagePetsActivity.class)));

        manageAppointments.setOnClickListener(view -> startActivity(new Intent(this, ManageAppointmentsActivity.class)));
    }
}