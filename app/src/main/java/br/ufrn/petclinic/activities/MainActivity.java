package br.ufrn.petclinic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import br.ufrn.petclinic.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton paw = findViewById(R.id.paw);
        paw.setOnClickListener(view -> startActivity(new Intent(this, ManagePetsActivity.class)));
    }
}