package com.example.contrasena;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditPasswordActivity extends AppCompatActivity {

    private EditText siteNameEditText, usernameEditText, passwordEditText, notesEditText;
    private String passwordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        siteNameEditText = findViewById(R.id.siteNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        notesEditText = findViewById(R.id.notesEditText);

        // Obtener el ID de la contraseña desde el Intent
        passwordId = getIntent().getStringExtra("passwordId");

        // Cargar los datos de la contraseña desde Firebase
        loadPasswordData();
    }

    private void loadPasswordData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("passwords").document(passwordId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Password password = documentSnapshot.toObject(Password.class);
                        siteNameEditText.setText(password.getSiteName());
                        usernameEditText.setText(password.getUsername());
                        passwordEditText.setText(password.getPassword());
                        notesEditText.setText(password.getNotes());
                    } else {
                        Toast.makeText(EditPasswordActivity.this, "Contraseña no encontrada", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(EditPasswordActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show());
    }
}
